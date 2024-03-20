package site.stellarburger.pageobjectsAndAPI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePageObjects {
    WebDriver driver;
    // конструктор экземпляра страницы
    public ProfilePageObjects(WebDriver driver) {
        this.driver = driver;
    }

    // локатор для кнопки "Выход". Кнопка видна только у авторизованного пользователя
    private final By exitButton = By.xpath(".//button[text()='Выход']");

    public boolean isExitButtonVisible(){
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver ->
                driver.findElement(exitButton).getText() != null);
        return driver.findElement(exitButton).isDisplayed();
    }

}
