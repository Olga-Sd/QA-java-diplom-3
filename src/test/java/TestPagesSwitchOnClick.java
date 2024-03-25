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

// Данный тестовый класс содержит проверки переходов:
//         - переход по клику на «Личный кабинет» - для авторизованного и неавторизованного пользователей
//         -переход из личного кабинета в конструктор
//                переход по клику на «Конструктор» и на логотип Stellar Burgers.
//
//         - в разделе "Конструктор" работают переходы к разделам:
//                «Булки»,
//                «Соусы»,
//                «Начинки».
public class TestPagesSwitchOnClick {
    WebDriver driver;
    User user;
    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

//        String browserType =  "chrome"; //System.getProperty("webdriver.driver");
//        Browser testBrowser = new Browser(browserType);
//        driver = testBrowser.getDriver();

        RestAssured.baseURI = Configuration.URL_STELLAR_BURGERS;
        user = new User();
    }

    @Test
    @DisplayName("Testing pages switch after 'Personal Account' link click by an unauthorized user")
    public void testPagesSwitchAfterPersonalAccLinkClickUnauthorizedUser() {
        driver.get(Configuration.STELLAR_BURGER_URL);
        // Заходим на главную страницу и кликаем кнопку "Войти в аккаунт"
        MainPageObjects mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.signInButtonClick();
        // Проверяем, произошел ли переход на страницу авторизации: если виден заголовок "Вход", то всё ок
        AuthorizationPageObjects authorizationPage = new AuthorizationPageObjects(driver);
        authorizationPage.waitForAuthorizationPageLoad();
        assertEquals(authorizationPage.isTitleEntranceVisible(), true);
    }

    @Test
    @DisplayName("Testing pages switch after 'Personal Account' link click by an authorized user")
    public void testPagesSwitchAfterPersonalAccLinkClickAuthorizedUser() {
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
        // Проверяем, произошел ли переход на страницу личного кабинета: если видна ссылка "Выход", то всё ок
        ProfilePageObjects profilePage = new ProfilePageObjects(driver);
        assertEquals(profilePage.isExitButtonVisible(), true);
    }

    @Test
    @DisplayName("Testing pages switch after 'Constructor' link click")
    public void testPagesSwitchAfterConstructorLinkClick() {
        driver.get(Configuration.STELLAR_BURGER_URL);
        // Заходим на главную страницу и кликаем кнопку "Войти в аккаунт"
        MainPageObjects mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.signInButtonClick();
        // Заходим на страницу авторизации, кликаем ссылку "Конструктор"
        AuthorizationPageObjects authorizationPage = new AuthorizationPageObjects(driver);
        authorizationPage.waitForAuthorizationPageLoad();
        authorizationPage.constructorLinkClick();
        //// переходим на главную страницу
        mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        // Проверяем, произошел ли переход на главную страницу: если виден заголовок "Соберите бургер", то всё ок
        assertEquals(mainPage.isTitleDesignBurgerVisible(), true);
    }

    @Test
    @DisplayName("Testing pages switch after header Logo link click")
    public void testPagesSwitchAfterHeaderLogoClick() {
        driver.get(Configuration.STELLAR_BURGER_URL);
        // Заходим на главную страницу и кликаем кнопку "Войти в аккаунт"
        MainPageObjects mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.signInButtonClick();
        // Заходим на страницу авторизации, кликаем логотип в хедере
        AuthorizationPageObjects authorizationPage = new AuthorizationPageObjects(driver);
        authorizationPage.waitForAuthorizationPageLoad();
        authorizationPage.headerLogoClick();
        // переходим на главную страницу
        mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        // Проверяем, произошел ли переход на главную страницу: если виден заголовок "Соберите бургер", то всё ок
        assertEquals(mainPage.isTitleDesignBurgerVisible(), true);
   }

    @Test
    @DisplayName("Test Bun section switch")
    public void testBunSectionSwitch() {
        driver.get(Configuration.STELLAR_BURGER_URL);
        driver.manage().window().maximize();
        // Заходим на главную страницу и кликаем по разделам конструктора "Соус", потом "Булки"
        MainPageObjects mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.souseSectionSwitcherClick();
        mainPage.bunsSectionSwitcherClick();
        // Если раздел с булками стал активным, то переход прошел успешно.
        assertTrue(mainPage.isBunSectionVisible());
    }
    @Test
    @DisplayName("Test Souse section switch")
    public void testSouseSectionSwitch() {
        driver.get(Configuration.STELLAR_BURGER_URL);
        driver.manage().window().maximize();
        // Заходим на главную страницу и кликаем по разделу конструктора "Соус"
        MainPageObjects mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.souseSectionSwitcherClick();
        // Если раздел с соусами стал активным, то переход прошел успешно.
        assertTrue(mainPage.isSouseSectionVisible());
    }

    @Test
    @DisplayName("Test Fillings section switch")
    public void testFillingsSectionSwitch() {
        driver.get(Configuration.STELLAR_BURGER_URL);
        driver.manage().window().maximize();
        // Заходим на главную страницу и кликаем по разделу конструктора "Начинки"
        MainPageObjects mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.fillingSectionSwitcherClick();
        // Если раздел с начинками стал активным, то переход прошел успешно.
        assertTrue(mainPage.isFillingSectionVisible());
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
