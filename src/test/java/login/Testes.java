package login;

import browser.Navegadores;
import driver.Driver;
import elementos.*;
import io.cucumber.java.eo.Se;
import org.junit.*;
import org.junit.runners.MethodSorters;
import static configurl.ConfigUrl.*;
import static metodos.Metodos.*;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Testes extends Driver {
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

    @Test
    public void t1_CompraComSucesso() {

        escrever(Login.login, getUser());
        escrever(Login.senha, getSenha());
        clicar(Login.button);

        clicar(SelecionarItem.Item);
        clicar(SelecionarItem.adcCarrinho);
        clicar(SelecionarItem.carrinho);
        validarItem(SelecionarItem.Item, getMochila());
        clicar(SelecionarItem.check);

        escrever(DadosPessoais.nome, getNome());
        escrever(DadosPessoais.sobrenome, getSobrenome());
        escrever(DadosPessoais.cep, getCep());
        clicar(DadosPessoais.Continue);
        clicar(DadosPessoais.finish);
        validarItem(DadosPessoais.msgFinal, getMsgFinal());
        System.out.println("\n===== Produto " + getMochila() + " e mensagem " + getMsgFinal() + " validadas com sucesso =====");
    }

    @Test
    public void t2_UsuarioComBloqueio() {

        escrever(Login.login, getUserBloq());
        escrever(Login.senha, getSenha());
        clicar(Login.button);
        validarItem(UserBloq.validarErro, getMsgErro());
        System.out.println("======== Mensagem de erro validada com sucesso: " + getMsgErro() + " =======");
    }

    @Test
    public void t3_UsuarioComProblemaDeInterface() {

        escrever(Login.login, getUserProblem());
        escrever(Login.senha, getSenha());
        clicar(Login.button);
        clicar(UserProblem.sauceLabs);
        validarItem(UserProblem.produtoRetornado, getProdProblem());
        System.out.println("======== Produto esperado validado com sucesso: " + getProdProblem() + " =======");
    }

    @Test
    public void t4_TentativaDeLoginComSenhaIncorreta() {

        escrever(Login.login, getUser());
        escrever(Login.senha, getSenhaIncorreta());
        clicar(Login.button);
        validarItem(Login.msgSenhaIncorreta, getMsgSenhaIncorreta());
        System.out.println("======== Mensagem de erro validada com sucesso: " + getMsgSenhaIncorreta() + " ========");
    }

    @Test
    public void t5_ValidacaoDePerformace() {

        long startTime = System.currentTimeMillis();
        escrever(Login.login, getPerformanceGlitchUser());
        escrever(Login.senha, getSenha());
        clicar(Login.button);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        double durationInSeconds = duration / 1000.0;
        System.out.println("O login demorou: " + durationInSeconds + " segundos.");
        assertTrue("O login demorou mais do que o esperado", durationInSeconds < 6);
    }

    @Test
    public void t6_ValidarUsuarioComError() {

        escrever(Login.login, getErrorUser());
        escrever(Login.senha, getSenha());
        clicar(Login.button);
        clicar(SelecionarItem.adcCarrinho);
        clicar(SelecionarItem.carrinho);
        clicar(SelecionarItem.check);

        escrever(DadosPessoais.nome, getNome());
        escrever(DadosPessoais.sobrenome, getSobrenome());
        escrever(DadosPessoais.cep, getCep());
        clicar(DadosPessoais.Continue);
        clicar(DadosPessoais.finish);

    }

    @Test
    public void t7_ValidarDiferencaEntreImagensDeProdutos() {

        escrever(Login.login, getVisualUser());
        escrever(Login.senha, getSenha());
        clicar(Login.button);

        driver.get(getImagemSelecionada());
        String urlPrimeiraImagem = driver.getCurrentUrl();
        driver.get(getImagemApresentada());
        String urlSegundaImagem = driver.getCurrentUrl();

        // Verifica se as URLs são diferentes e falha o teste se forem iguais
        Assert.assertNotEquals("As URLs das imagens dos produtos não deveriam ser iguais.", urlPrimeiraImagem, urlSegundaImagem);

    }
}