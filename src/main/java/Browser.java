import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium. сюда Хром!!!

public class Browser {

    public WebDriver getWebDriver(String browserName) {
        WebDriver driver;
        switch (browserName) {
            case "chrome":
                driver =  new ChromeDriver();
            case "yandex":
                driver =  new FirefoxDriver();
            default:
                driver =  new FirefoxDriver();
        }
        return driver;
    }
}
