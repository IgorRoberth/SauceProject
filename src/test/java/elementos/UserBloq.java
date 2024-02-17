package elementos;

import org.openqa.selenium.By;

public class UserBloq {

    public static By userBloq = By.cssSelector("input[id=user-name]");
    public static By validarErro = By.xpath("//*[text()='Epic sadface: Sorry, this user has been locked out.']");

}
