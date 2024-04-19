import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.openqa.selenium.chrome.ChromeOptions;
import site.stellarburger.pageobjectsAndAPI.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// Тестовый класс содержит проверки для входа и выхода из приложения зарегистрированного пользователя:
//        -вход по кнопке «Войти в аккаунт» на главной,
//        -вход через кнопку «Личный кабинет»,
//        -вход через кнопку в форме регистрации,
//        -вход через кнопку в форме восстановления пароля
//        -выход по кнопке "Выйти" в личном кабинете
public class TestSignInAndOutApp {

    WebDriver driver;
    User user;

    @Before
    public void setUp() {
        //Используем менеджер для подготовки драйверов
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

//        String browserType =  System.getProperty("webdriver.driver");
//        Browser testBrowser = new Browser(browserType);
//        driver = testBrowser.getDriver();

        RestAssured.baseURI = Configuration.STELLAR_BURGER_URL;
        user = new User();
        UserAPI.createUser(user);

    }

    @Test
    @DisplayName("Login with button 'Sign in account' on the main page")
    public void testMainPageSignInButtonLogin() {
        driver.get(Configuration.STELLAR_BURGER_URL);
        driver.manage().window().maximize();

        // Заходим на главную страницу и кликаем кнопку "Войти в аккаунт"
        MainPageObjects mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.signInButtonClick();

        // Заходим на страницу авторизации, заполняем поля, жмем кнопку "Войти"
        AuthorizationPageObjects authorizationPage = new AuthorizationPageObjects(driver);
        authorizationPage.waitForAuthorizationPageLoad();
        authorizationPage.fillSignInFieldsAndClickSignInButton(user);

        // На главной странице нажимаем кнопку "Личный кабинет" в хедере и входим на страницу аккаунта
        mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.personalAccountLinkClick();

        ProfilePageObjects profilePage = new ProfilePageObjects(driver);

        // Суть проверки: если после загрузки страницы видна кнопка "Выход", то значит пользователь вошел в систему
        assertTrue(profilePage.isExitButtonVisible());
    }

    @Test
    @DisplayName("Login with button 'Personal account' on the main page header")
    public void testMainPageSignInButtonPersonalAccount() {
        driver.get(Configuration.STELLAR_BURGER_URL);
        // Заходим на главную страницу и кликаем кнопку "Личный кабинет" в хедере
        MainPageObjects mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.personalAccountLinkClick();
        // Заходим на страницу авторизации, заполняем поля, жмем кнопку "Войти"
        AuthorizationPageObjects authorizationPage = new AuthorizationPageObjects(driver);
        authorizationPage.waitForAuthorizationPageLoad();
        authorizationPage.fillSignInFieldsAndClickSignInButton(user);

        // На главной странице нажимаем кнопку "Личный кабинет" в хедере и входим на страницу аккаунта
        mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.personalAccountLinkClick();
        ProfilePageObjects profilePage = new ProfilePageObjects(driver);
        // Суть проверки: если после загрузки страницы видна кнопка "Выход", то значит пользователь вошел в систему
        assertTrue(profilePage.isExitButtonVisible());
    }

    @Test
    @DisplayName("Login with button 'Log in' on the registration page bottom")
    public void testRegistrationPageSignInButton() {
        driver.get(Configuration.STELLAR_BURGER_URL);
        driver.manage().window().maximize();
        // Заходим на главную страницу и кликаем кнопку "Личный кабинет" в хедере
        MainPageObjects mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.personalAccountLinkClick();
        // Заходим на страницу авторизации, прокручиваем до ссылки "Зарегистрироваться" и жмем на нее,
        // прокручиваем и переходим по ссылке "Войти", заполняем поля, жмем кнопку "Войти"
        AuthorizationPageObjects authorizationPage = new AuthorizationPageObjects(driver);
        authorizationPage.waitForAuthorizationPageLoad();
        authorizationPage.scrollPageTillSignUpLink();
        authorizationPage.signUpLinkClick();
        authorizationPage.scrollPageTillSignInLinkAndClick();
        authorizationPage.waitForAuthorizationPageLoad();
        authorizationPage.fillSignInFieldsAndClickSignInButton(user);

        // На главной странице нажимаем кнопку "Личный кабинет" в хедере и входим на страницу аккаунта
        mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.personalAccountLinkClick();
        ProfilePageObjects profilePage = new ProfilePageObjects(driver);
        // Суть проверки: если после загрузки страницы видна кнопка "Выход", то значит пользователь вошел в систему
        assertTrue(profilePage.isExitButtonVisible());
    }

    @Test
    @DisplayName("Login with link 'Log in' on the password recovery page bottom")
    public void testPasswordRecoveryPageSignInLink() {
        driver.get(Configuration.STELLAR_BURGER_URL);
        driver.manage().window().maximize();
        // Заходим на главную страницу и кликаем кнопку "Личный кабинет" в хедере
        MainPageObjects mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.personalAccountLinkClick();
        // Заходим на страницу авторизации, прокручиваем до ссылки "Восстановить пароль" и жмем на нее,
        AuthorizationPageObjects authorizationPage = new AuthorizationPageObjects(driver);
        authorizationPage.waitForAuthorizationPageLoad();
        authorizationPage.scrollPageTillPasswordRecoveryLinkAndClick();
        // прокручиваем страницу восстановления пароля и переходим по ссылке "Войти", заполняем поля, жмем кнопку "Войти"
        PasswordRecoveryPageObjects passwordRecoveryPage = new PasswordRecoveryPageObjects(driver);
        passwordRecoveryPage.scrollPageTillSignInLinkAndClick();
        authorizationPage = new AuthorizationPageObjects(driver);
        authorizationPage.waitForAuthorizationPageLoad();
        authorizationPage.fillSignInFieldsAndClickSignInButton(user);
        // На главной странице нажимаем кнопку "Личный кабинет" в хедере и входим на страницу аккаунта
        mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.personalAccountLinkClick();
        ProfilePageObjects profilePage = new ProfilePageObjects(driver);

        assertTrue(profilePage.isExitButtonVisible());
    }

    @Test
    @DisplayName("Log out with button 'Exit' on the personal Acc page")
    public void testPersonalAccExitButton() {
        driver.get(Configuration.STELLAR_BURGER_URL);
        UserAPI.createUser(user);
        // Заходим на главную страницу и кликаем кнопку "Войти в аккаунт"
        MainPageObjects mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.signInButtonClick();
        // Заходим на страницу авторизации, заполняем поля, жмем кнопку "Войти"
        AuthorizationPageObjects authorizationPage = new AuthorizationPageObjects(driver);
        authorizationPage.waitForAuthorizationPageLoad();
        authorizationPage.fillSignInFieldsAndClickSignInButton(user);
        // На главной странице нажимаем кнопку "Личный кабинет" в хедере и входим на страницу аккаунта
        mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.personalAccountLinkClick();
        // ждем загрузку страницы личного кабинета и кликаем кнопку "Выход"
        ProfilePageObjects profilePage = new ProfilePageObjects(driver);
        profilePage.isExitButtonVisible();
        profilePage.exitButtonClick();

        authorizationPage = new AuthorizationPageObjects(driver);
        authorizationPage.waitForAuthorizationPageLoad();

        //Если на загрузившейся странице видна надпись "Вход", то переход произошел верно
        assertTrue(authorizationPage.isTitleEntranceVisible());
    }

    @After
    @Description("Close browser and remove user account if exists")
    public void teardown() {
        String token;
        // Закрываем браузер
        driver.quit();
        // Удаляем созданного пользователя из базы
        try {
            Response responseLogin = UserAPI.loginUserAndGetToken(user);
            if (responseLogin.path("success").equals(true)) {
                token = responseLogin.path("accessToken");
                UserAPI.deleteUser(user, token);
            }
        } catch (NullPointerException e) {
        }
    }
}
