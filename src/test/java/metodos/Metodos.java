package metodos;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import driver.Driver;
import org.apache.commons.io.FileUtils;

import java.time.Duration;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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