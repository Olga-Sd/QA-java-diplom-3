import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.chrome.ChromeOptions;
import site.stellarburger.pageobjectsAndAPI.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;


// Тестовый класс содержит 2 проверки:
//  -успешная регистрация
//  -ошибку для некорректного пороля (менее 6 символов)
public class TestRegistration {

    WebDriver driver;

    User user;

    @Before
    public void setUp() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

//        String browserType =  System.getProperty("webdriver.driver");
//        Browser testBrowser = new Browser(browserType);
//        driver = testBrowser.getDriver();

        RestAssured.baseURI = Configuration.URL_STELLAR_BURGERS;
        user = new User();

    }

    @Test
    @DisplayName("Testing proper registration")
    public void testUserCanBeRegistered() {

        driver.get(Configuration.STELLAR_BURGER_URL);

        MainPageObjects mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.personalAccountLinkClick();

        AuthorizationPageObjects authorizationPage = new AuthorizationPageObjects(driver);
        authorizationPage.waitForAuthorizationPageLoad();
        authorizationPage.scrollPageTillSignUpLink();
        authorizationPage.signUpLinkClick();
        authorizationPage.fillSignUpFieldsAndClickSignUpButton(user);

        Response responseLogin = UserAPI.loginUserAndGetToken(user);
        responseLogin.then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("User gets a mistake if password is shorter than 6 symbols")
    public void canNotSignUpUserIfPasswordShorter6Symbols(){

        String pwdKeeper = user.getPassword();
        String wrongPassword = "1234";

        driver.get(Configuration.STELLAR_BURGER_URL);

        MainPageObjects mainPage = new MainPageObjects(driver);
        mainPage.waitForMainPageLoad();
        mainPage.personalAccountLinkClick();

        AuthorizationPageObjects authorizationPage = new AuthorizationPageObjects(driver);
        authorizationPage.waitForAuthorizationPageLoad();
        authorizationPage.scrollPageTillSignUpLink();
        authorizationPage.signUpLinkClick();

        user.setPassword(wrongPassword);

        authorizationPage.fillSignUpFieldsAndClickSignUpButton(user);

        assertEquals(authorizationPage.isTooShortPasswordWarningVisible(), true);

        user.setPassword(pwdKeeper);

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
