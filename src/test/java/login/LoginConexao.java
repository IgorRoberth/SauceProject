package login;

import driver.Driver;
import browser.Navegadores;
import elementos.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.After;
import org.junit.runners.MethodSorters;
import static configurl.ConfigUrl.*;
import static metodos.Metodos.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginConexao extends Driver {
    @Before
    public void setup() {
        Navegadores navegadores = new Navegadores("Edge");
        driver = navegadores.configurarDriver();
        driver.get(getUrl());
    }

    @After
    public void fechar() {

        driver.quit();
    }

    final static String produto = "Sauce Labs Backpack";
    final static String msgFinal = "Thank you for your order!";

    @Test
    public void t1_compracomsucesso() {

        escrever(Elementos.login, getUser());
        escrever(Elementos.senha, getSenha());
        clicar(Elementos.button);
        clicar(selecionarItem.validarItem);
        clicar(selecionarItem.adcCarrinho);
        clicar(selecionarItem.carrinho);
        validarItem(selecionarItem.validarItem, produto);
        clicar(selecionarItem.check);
        escrever(DadosPessoais.nome, getNome());
        escrever(DadosPessoais.sobrenome, getSobrenome());
        escrever(DadosPessoais.cep, getCep());
        clicar(DadosPessoais.Continue);
        clicar(DadosPessoais.finish);
        validarItem(DadosPessoais.msgFinal, msgFinal);
        System.out.println("\n===== Produto " + produto + " e mensagem " + msgFinal + " validado com sucesso =====");
    }

    final static String msgErro = "Epic sadface: Sorry, this user has been locked out.";

    @Test
    public void t2_usuariocombloqueio() {

        escrever(Elementos.login, getUserBloq());
        escrever(Elementos.senha, getSenha());
        clicar(Elementos.button);
        validarItem(UserBloq.validarErro, msgErro);
        System.out.println("======== Mensagem de erro validada com sucesso: " + msgErro + "=======");
    }

    final static String userProblem = "Test.allTheThings() T-Shirt (Red)";

    @Test
    public void t3_usuariocomproblemadeinterface() {

        escrever(Elementos.login, getUserProblem());
        escrever(Elementos.senha, getSenha());
        clicar(Elementos.button);
        clicar(UserProblem.sauceLabs);
        validarItem(UserProblem.produtoRetornado, userProblem);
        System.out.println("======== Produto esperado validado com sucesso: " + userProblem + "=======");
    }

    final static String msgsenhaIncorreta = "Epic sadface: Username and password do not match any user in this service ";

    @Test
    public void t4_tentativadelogincomsenhaincorreta() {

        escrever(Elementos.login, getUser());
        escrever(Elementos.senha, getSenhaIncorreta());
        clicar(Elementos.button);
        validarItem(Elementos.msgSenhaIncorreta, msgsenhaIncorreta);
        System.out.println("======== Mensagem de erro validada com sucesso: " + msgsenhaIncorreta + "========");
    }
}