package site.stellarburger.pageobjectsAndAPI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;

public class PasswordRecoveryPageObjects {
    WebDriver driver;
    // конструктор экземпляра страницы
    public PasswordRecoveryPageObjects(WebDriver driver) {
        this.driver = driver;
    }

    // локатор для кнопки "Выход". Кнопка видна только у авторизованного пользователя
    private final By signInLink = By.xpath(".//*[text()='Войти']");

    public void scrollPageTillSignInLinkAndClick() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();",
                driver.findElement(signInLink));
        driver.findElement(signInLink).click();
    }
}
