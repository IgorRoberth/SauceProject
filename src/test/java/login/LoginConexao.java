package login;

import configurl.ConfigUrl;
import driver.Driver;
import browser.Navegadores;
import elementos.DadosPessoais;
import elementos.Elementos;
import elementos.selecionarItem;
import metodos.Metodos;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

public class LoginConexao extends Driver {

        @Before
        public void setup() {
            Navegadores nevegadores = new Navegadores("Edge");
            driver = nevegadores.configurarDriver();
            driver.get(ConfigUrl.getUrl());
    }
  //  @After
  //  public void fechar(){
  //          driver.quit();
        @Test
        public void sauceproject() {
            Metodos.escrever(Elementos.login, ConfigUrl.getProperty("userSucess"));
            Metodos.escrever(Elementos.senha, ConfigUrl.getProperty("senhaSucess"));
            Metodos.clicar(Elementos.button);
            Metodos.clicar(selecionarItem.validarItem);
            Metodos.clicar(selecionarItem.adcCarrinho);
            Metodos.clicar(selecionarItem.carrinho);
   //         Metodos.validarItem(selecionarItem.validarItem, "");
   //         Metodos.clicar(selecionarItem.check);
     //       Metodos.escrever(DadosPessoais.nome, "");
      //      Metodos.escrever(DadosPessoais.sobrenome, "");
       //     Metodos.escrever(DadosPessoais.cep, "");
        //    Metodos.clicar(DadosPessoais.Continue);
         //   Metodos.clicar(DadosPessoais.finish);
          //  Metodos.validarItem(DadosPessoais.msgFinal, "");
           // System.out.println("\n=====Produto "+ "validar_produto" +" e mensagem " + "msg_final" + " validado com sucesso=====");
        }
}