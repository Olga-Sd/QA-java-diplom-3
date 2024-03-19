package site.stellarburger.pageobjectsAndAPI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class authorizationPageObjects {

    WebDriver driver;

    // конструктор экземпляра страницы
    public authorizationPageObjects(WebDriver driver) {
        this.driver = driver;
    }

    //локатор кнопки "Войти"
    private final By enterButton = By.xpath(".//button[text()='Войти']");
    // локатор ссылки "Зарегистрироваться
    private final By signUpLink = By.xpath(".//*[text()='Зарегистрироваться']");
    //локатор надписи поля "Имя"
    private final By nameLabel = By.xpath(".//label[text()='Имя']");
    // локатор полей ввода "Имя" и "Email"
    private final By nameInputField = By.xpath(".//*[@name='name']");

    // локатор поля ввода "Пароль"
    private final By passwordInputField = By.xpath(".//*[@name='Пароль']");
    // локатор кнопки "Зарегистрироваться"
    private final By signUpButton = By.xpath(".//button[text()='Зарегистрироваться']");

    private final By tooShortPasswordWarning = By.xpath(".//*[text()='Некорректный пароль']");





    // метод ожидания загрузки страницы - ожидаем видимость кнопки "Войти"
    public void waitForAuthorizationPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver ->
                driver.findElement(enterButton).getText() != null);
    }

    // прокрутка страницы до ссылки "Зарегистрироваться"
    public void scrollPageTillSignUpLink() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
                driver.findElement(signUpLink));
    }

    // клик по кнопке "Зарегистрироваться" и ожидание появления поля "Имя"
    public void signUpLinkClick() {
        driver.findElement(signUpLink).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver ->
                driver.findElement(nameLabel).getText() != null);
    }

    // Заполняем поля для регистрации и нажимаем кнопку "Зарегистрироваться"
    public void fillSignUpFieldsAndClickSignUpButton(User user) {

        List<WebElement> elements = driver.findElements(nameInputField);

        elements.get(0).sendKeys(user.getName());
        elements.get(1).sendKeys(user.getEmail());

        driver.findElement(passwordInputField).click();
        driver.findElement(passwordInputField).sendKeys(user.getPassword());

        driver.findElement(signUpButton).click();


    }

    public boolean isTooShortPasswordWarningVisible(){
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver ->
                driver.findElement(tooShortPasswordWarning).getText() != null);
        return driver.findElement(tooShortPasswordWarning).isDisplayed();
    }


}
