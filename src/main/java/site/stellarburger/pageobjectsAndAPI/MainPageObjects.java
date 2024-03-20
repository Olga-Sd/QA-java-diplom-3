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
    // локатор надписи "Соберите бургер"
    private final By designeBurgerTitle = By.xpath(".//*[text()='Соберите бургер']");
    // локатор переключателя раздела "Булки"
    private final By bunsSectionSwitcher = By.xpath(".//*[text()='Булки']");
    private final By souseSectionSwitcher = By.xpath(".//*[text()='Соусы']");
    private final By fillingSectionSwitcher = By.xpath(".//*[text()='Начинки']");

    private final By bunsSectionHeader = By.xpath(".//h2[text()='Булки']");
    private final By souseSectionHeader = By.xpath(".//h2[text()='Соусы']");
    private final By fillingSectionHeader = By.xpath(".//h2[text()='Начинки']");


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

    public boolean isTitleDesignBurgerVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver ->
                driver.findElement(designeBurgerTitle).getText() != null);
        return driver.findElement(designeBurgerTitle).isDisplayed();
    }

    public void bunsSectionSwitcherClick(){
        driver.findElement(bunsSectionSwitcher).click();
    }

    public void souseSectionSwitcherClick(){
        driver.findElement(souseSectionSwitcher).click();
    }

    public void fillingSectionSwitcherClick(){
        driver.findElement(fillingSectionSwitcher).click();
    }

    public boolean isBunSectionHeaderVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver ->
                driver.findElement(bunsSectionHeader).getText() != null);
        return driver.findElement(bunsSectionHeader).isDisplayed();
    }

    public boolean isSouseSectionHeaderVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver ->
                driver.findElement(souseSectionHeader).getText() != null);
        return driver.findElement(souseSectionHeader).isDisplayed();
    }

    public boolean isFillingSectionHeaderVisible() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver ->
                driver.findElement(fillingSectionHeader).getText() != null);
        return driver.findElement(fillingSectionHeader).isDisplayed();
    }

}
