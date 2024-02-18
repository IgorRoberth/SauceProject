package elementos;

import org.openqa.selenium.By;
public class Elementos {
        public static By login = By.cssSelector("#user-name");
        public static By senha = By.cssSelector("#password");
        public static By button = By.cssSelector("#login-button");
        public static By msgSenhaIncorreta = By.xpath("//*[text()='Epic sadface: Username and password do not match any user in this service']");
        public static By imagemSelecionada = By.cssSelector("#item_4_img_link > img");
        public static By imagemApresentada = By.cssSelector("#inventory_item_container > div > div > div.inventory_details_img_container > img");
}