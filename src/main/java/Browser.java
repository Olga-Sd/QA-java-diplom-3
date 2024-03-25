import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;


public class Browser {

    WebDriver driver;
    public Browser(String browserName) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        switch (browserName) {
            case "chrome":

                WebDriverManager.chromedriver().setup();
                this.driver = new ChromeDriver();
                break;
            case "yandex":
                System.setProperty("webdriver.chrome.driver", "resources/yandexdriver-24.1.0.2570-win64/yandexdriver.exe");
                options.setBinary("C:/Users/user/AppData/Local/Yandex/YaPin/YandexWorking.exe");
                WebDriverManager.chromedriver().setup();
                this.driver = new ChromeDriver(options);
                break;
            default:
                WebDriverManager.firefoxdriver().setup();
                this.driver = new FirefoxDriver();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
