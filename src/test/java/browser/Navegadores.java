package browser;

import driver.Driver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class Navegadores extends Driver {

    private String Navegador;

    public Navegadores(String navegador) {

        setNavegadorEscolhido(navegador);
    }

    private void setNavegadorEscolhido(String navegador) {

        if (!"Chrome".equalsIgnoreCase(navegador) && !"Firefox".equalsIgnoreCase(navegador)
                && !"Edge".equalsIgnoreCase(navegador)) {
            throw new IllegalArgumentException("Navegador inválido: " + navegador);
        }
        this.Navegador = navegador;
    }

    public WebDriver configurarDriver() {

        switch (Navegador.toLowerCase()) {

            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-plugins-discovery");
                chromeOptions.addArguments("--disable-popup-blocking");
                chromeOptions.addArguments("--disable-infobars");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--disable-extensions");
                firefoxOptions.addArguments("--disable-plugins");
                firefoxOptions.addArguments("--disable-popup-blocking");
                firefoxOptions.addArguments("--no-sandbox");
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--remote-allow-origins=*");
                edgeOptions.addArguments("--disable-extensions");
                edgeOptions.addArguments("--disable-plugins-discovery");
                edgeOptions.addArguments("--disable-popup-blocking");
                edgeOptions.addArguments("--disable-features=RendererCodeIntegrity");
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new IllegalArgumentException("Navegador inválido: " + Navegador);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }
}