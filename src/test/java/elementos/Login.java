package elementos;

import org.openqa.selenium.By;

public class Login {
    public static By login = By.cssSelector("#user-name");
    public static By senha = By.cssSelector("#password");
    public static By button = By.cssSelector("#login-button");
    public static By msgSenhaIncorreta = By.xpath("//*[text()='Epic sadface: Username and password do not match any user in this service']");
}

