package login;

import browser.Navegadores;
import driver.Driver;
import elementos.*;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
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
        final String msgFinal = "\n======= Produto " + getMochila() + " e mensagem " + getMsgFinal() + " validados com sucesso =======";
        System.out.println(msgFinal);
    }

    @Test
    public void t2_UsuarioComBloqueio() {

        final String msgErro = "======== Mensagem de erro validada com sucesso: " + getMsgErro() + " ========";
        escrever(Login.login, getUserBloq());
        escrever(Login.senha, getSenha());
        clicar(Login.button);
        validarItem(UserBloq.validarErro, getMsgErro());
        System.out.println(msgErro);
    }

    @Test
    public void t3_UsuarioComProblemaDeInterface() {

        escrever(Login.login, getUserProblem());
        escrever(Login.senha, getSenha());
        clicar(Login.button);
        clicar(UserProblem.sauceLabs);
        validarItem(UserProblem.produtoRetornado, getProdProblem());
        final String produtoesperado = "======== Produto esperado validado com sucesso: " + getProdProblem() + " ========";
        System.out.println(produtoesperado);
    }

    @Test
    public void t4_TentativaDeLoginComSenhaIncorreta() {

        escrever(Login.login, getUser());
        escrever(Login.senha, getSenhaIncorreta());
        clicar(Login.button);
        validarItem(Login.msgSenhaIncorreta, getMsgSenhaIncorreta());
        final String msgSucesso = "======== Mensagem de erro validada com sucesso: " + getMsgSenhaIncorreta() +  " ========";
        System.out.println(msgSucesso);
    }

    @Test
    public void t5_ValidacaoDePerformaceDoSite() {

        long startTime = System.currentTimeMillis();
        escrever(Login.login, getPerformanceGlitchUser());
        escrever(Login.senha, getSenha());
        clicar(Login.button);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        double durationInSeconds = duration / 1000.0;
        final String msgDemora = "======= O login demorou: " + durationInSeconds + " segundos. ===========";
        assertTrue(msgDemora, durationInSeconds < 6);
        System.out.println(msgDemora);

    }

    @Test
    public void t6_ValidarSeElementoDoBotaoFinishEstaVisivel() {

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

        Duration timeout = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        WebElement finishButton;

        try {
            finishButton = wait.until(ExpectedConditions.presenceOfElementLocated(DadosPessoais.finish));
            final String sucesso = "======= O botão 'finish' está visível na tela. ======= " + finishButton;
            assertTrue(sucesso, finishButton.isDisplayed());
            System.out.println(sucesso);
        } catch (TimeoutException e) {
            final String falha = "======= O botão 'finish' não está visível na tela. ======= ";
            System.out.println(falha);
        }
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

        final String sucesso = "======= As URLs das imagens dos produtos são diferentes. ======= " + "Primeira Url: " + getImagemSelecionada() + " Segunda Url: " + getImagemApresentada();
        final String falha = "======= As URLs das imagens dos produtos não deveriam ser iguais. ======= ";

        if (!urlPrimeiraImagem.equals(urlSegundaImagem)) {
            System.out.println(sucesso);
        } else {
            System.out.println(falha);
        }
    }
}