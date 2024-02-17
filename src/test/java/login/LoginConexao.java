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
        Navegadores navegadores = new Navegadores("Edge");
        driver = navegadores.configurarDriver();
        driver.get(ConfigUrl.getUrl());
    }
    @After
    public void fechar(){
        driver.quit();
    }

    final static String produto = "Sauce Labs Backpack";
    final static String msgFinal = "Thank you for your order!";
        @Test
        public void sauceproject() {

            Metodos.escrever(Elementos.login, ConfigUrl.getUser());
            Metodos.escrever(Elementos.senha, ConfigUrl.getSenha());
            Metodos.clicar(Elementos.button);
            Metodos.clicar(selecionarItem.validarItem);
            Metodos.clicar(selecionarItem.adcCarrinho);
            Metodos.clicar(selecionarItem.carrinho);
            Metodos.validarItem(selecionarItem.validarItem, produto);
            Metodos.clicar(selecionarItem.check);
            Metodos.escrever(DadosPessoais.nome, ConfigUrl.getNome());
            Metodos.escrever(DadosPessoais.sobrenome, ConfigUrl.getSobrenome());
            Metodos.escrever(DadosPessoais.cep, ConfigUrl.getCep());
            Metodos.clicar(DadosPessoais.Continue);
            Metodos.clicar(DadosPessoais.finish);
            Metodos.validarItem(DadosPessoais.msgFinal, msgFinal);
            System.out.println("\n=====Produto "+ produto + "validar_produto" +" e mensagem " + msgFinal + " validado com sucesso=====");
        }
}