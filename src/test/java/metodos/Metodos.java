package metodos;

import driver.Driver;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Metodos extends Driver {

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
        Assert.assertTrue(textoEsperado.contains(textoCapturado));
    }
}