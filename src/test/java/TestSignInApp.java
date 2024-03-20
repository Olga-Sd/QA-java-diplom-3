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


public class TestSignInApp {

    WebDriver driver;

    User user;

    @Before
    public void setUp() {
        //Используем менеджер для подготовки драйверов
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
//        WebDriverManager.firefoxdriver().setup();
//        driver = new FirefoxDriver();

        RestAssured.baseURI = Configuration.URL_STELLAR_BURGERS;
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
        assertEquals(profilePage.isExitButtonVisible(), true);

    }

    @Test
    @DisplayName("Login with button 'Personal account' on the main page header")
    public void testMainPageSignInButtonPersonalAccount() {
        driver.get(Configuration.STELLAR_BURGER_URL);
        driver.manage().window().maximize();

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
        assertEquals(profilePage.isExitButtonVisible(), true);

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
        assertEquals(profilePage.isExitButtonVisible(), true);

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

        assertEquals(profilePage.isExitButtonVisible(), true);

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
