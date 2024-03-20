package site.stellarburger.pageobjectsAndAPI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class MainPageObjects {

    WebDriver driver;
    // конструктор экземпляра страницы
    public MainPageObjects(WebDriver driver) {
        this.driver = driver;
    }

    //локатор кнопки "Личный кабинет" в правом верхнем углу хедера
    private final By personalAccountLink =  By.xpath(".//*[text()='Личный Кабинет']");

    // локатор кнопки "Войти в аккаунт"
    private final By signInButton = By.xpath(".//button[text()='Войти в аккаунт']");


    public void waitForMainPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver ->
                driver.findElement(personalAccountLink).getText() != null);
    }

    public void personalAccountLinkClick(){
        driver.findElement(personalAccountLink).click();
    }

    public void signInButtonClick(){
        driver.findElement(signInButton).click();
    }

}
