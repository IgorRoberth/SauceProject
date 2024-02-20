package metodos;

import driver.Driver;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import static org.junit.Assert.assertTrue;

public class Metodos extends Driver {

    public static void screenShot(String nome) throws IOException {

        TakesScreenshot srcShot = ((TakesScreenshot) driver);
        File srcFile = srcShot.getScreenshotAs(OutputType.FILE);
        File desFile = new File("./Evidencias/" + nome + ".png");
        FileUtils.copyFile(srcFile, desFile);

    }

    public static void clicar(By elemento) {
        try {
            driver.findElement(elemento).click();
        } catch (Exception e) {
            System.err.println("-------erro ao clicar---------" + e.getMessage());
            System.err.println("-------causa do erro---------" + e.getCause());
        }
    }

    public static void escrever(By elemento, String texto) {

        driver.findElement(elemento).sendKeys(texto);

    }

    public static void validarItem(By elemento, @NotNull String textoEsperado) {
        Duration timeout = Duration.ofSeconds(120);
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elemento));
        String textoCapturado = element.getText();
        assertTrue(textoEsperado.contains(textoCapturado));
    }
}