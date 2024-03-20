import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium. сюда Яндекс!!!

public class Driver {

    WebDriver driver;
    public Driver(String browserName) {
        ChromeOptions options = new ChromeOptions(); // Драйвер для браузера
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                this.driver = new ChromeDriver();
            case "yandex":
                System.setProperty("webdriver.chrome.driver", "C:/WebDriver/YandexDriver/yandexdriver-24.1.0.2570-win64");
                options.setBinary("\"C:\\WebDriver\\YandexDriver\\yandexdriver-24.1.0.2570-win64\"");
                this.driver = new ChromeDriver(options);

            default:
                WebDriverManager.firefoxdriver().setup();
                this.driver = new FirefoxDriver();
        }
    }
}
