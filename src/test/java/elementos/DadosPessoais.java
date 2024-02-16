package elementos;

import org.openqa.selenium.By;

public class DadosPessoais {
    public static By nome = By.cssSelector("#first-name");
    public static By sobrenome = By.cssSelector("#last-name");
    public static By cep = By.cssSelector("#postal-code");
    public static By Continue = By.cssSelector("#continue");
    public static By finish = By.cssSelector("#finish");
    public static By msgFinal = By.cssSelector("#checkout_complete_container > h2");
}
