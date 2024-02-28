package login;

import browser.Navegadores;
import com.itextpdf.text.DocumentException;
import driver.Driver;
import elementos.*;
import metodos.Metodos;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pdfscreenshot.take.TakeScreenshot;

import java.io.IOException;
import java.time.Duration;

import static configurl.ConfigUrl.*;
import static metodos.Metodos.*;

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
    public void t1_CompraComSucesso() throws IOException, DocumentException {

        escrever(Login.login, getUser());
        escrever(Login.senha, getSenha());
        TakeScreenshot.screenShot("Login", 1);
        clicar(Login.button);

        clicar(SelecionarItem.Item);
        clicar(SelecionarItem.adcCarrinho);
        TakeScreenshot.screenShot("Mochila", 1);
        clicar(SelecionarItem.carrinho);
        validarItem(SelecionarItem.Item, getMochila());
        clicar(SelecionarItem.check);

        escrever(DadosPessoais.nome, getNome());
        escrever(DadosPessoais.sobrenome, getSobrenome());
        escrever(DadosPessoais.cep, getCep());
        clicar(DadosPessoais.Continue);
        clicar(DadosPessoais.finish);
        TakeScreenshot.screenShot("MsgFinal", 1);
        validarItem(DadosPessoais.msgFinal, getMsgFinal());
        final String msgFinal = "\n======= Produto " + getMochila() + " e mensagem " + getMsgFinal() + " validados com sucesso =======";
        System.out.println(msgFinal);

        TakeScreenshot.GeradorPDF.gerarPDF("./EvidenciasT1", ".png", "T1.pdf", "T1_Validação de compra com sucesso.");
    }

    @Test
    public void t2_UsuarioComBloqueio() throws IOException, DocumentException {

        final String msgErro = "======== Mensagem de erro validada com sucesso: " + getMsgErro() + " ========";
        escrever(Login.login, getUserBloq());
        escrever(Login.senha, getSenha());
        clicar(Login.button);
        TakeScreenshot.screenShot("MsgErro", 2);
        validarItem(UserBloq.validarErro, getMsgErro());
        System.out.println(msgErro);
        TakeScreenshot.GeradorPDF.gerarPDF("./EvidenciasT2", ".png", "T2.pdf", "T2_Validar usuário com bloqueio.");

    }

    @Test
    public void t3_UsuarioComProblemaDeInterface() throws IOException, DocumentException {

        escrever(Login.login, getUserProblem());
        escrever(Login.senha, getSenha());
        TakeScreenshot.screenShot("User Problem", 3);
        clicar(Login.button);
        TakeScreenshot.screenShot("Produto Selecionado", 3);
        clicar(UserProblem.sauceLabs);
        TakeScreenshot.screenShot("Produto Apresentado", 3);
        validarItem(UserProblem.produtoRetornado, getProdProblem());
        final String produtoesperado = "======== Produto esperado validado com sucesso: " + getProdProblem() + " ========";
        System.out.println(produtoesperado);

        TakeScreenshot.GeradorPDF.gerarPDF("./EvidenciasT3", ".png", "T3.pdf", "T3_Usuário com problema de interface.");
    }

    @Test
    public void t4_TentativaDeLoginComSenhaIncorreta() throws IOException, DocumentException {

        escrever(Login.login, getUser());
        escrever(Login.senha, getSenhaIncorreta());
        clicar(Login.button);
        TakeScreenshot.screenShot("MsgSenhaIncorreta", 4);
        validarItem(Login.msgSenhaIncorreta, getMsgSenhaIncorreta());
        final String msgErro = "======== Mensagem de erro validada com sucesso: " + getMsgSenhaIncorreta() + " ========";
        System.out.println(msgErro);

        TakeScreenshot.GeradorPDF.gerarPDF("./EvidenciasT4", ".png", "T4.pdf", "T4_Tentativa de login com senha incorreta");

    }

    @Test
    public void t5_ValidacaoDePerformaceDoSite() throws IOException, DocumentException {

        long startTime = System.currentTimeMillis();
        escrever(Login.login, getPerformanceGlitchUser());
        escrever(Login.senha, getSenha());
        TakeScreenshot.screenShot("Performance", 5);
        clicar(Login.button);
        TakeScreenshot.screenShot("Logado",5);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        double durationInSeconds = duration / 1000.0;
        final String msgDemora = "======= O login demorou: " + durationInSeconds + " segundos. ===========";
        Assert.assertTrue(msgDemora, durationInSeconds < 6);
        System.out.println(msgDemora);

        String pdfcontent = "T5_Validacao de performace do site. ";
        pdfcontent += "Tempo de login: " + durationInSeconds + " segundos\n";
        TakeScreenshot.GeradorPDF.gerarPDF("./EvidenciasT5", ".png", "T5.pdf", pdfcontent);

    }

    @Test
    public void t6_ValidarSeElementoDoBotaoFinishEstaVisivel() throws IOException, DocumentException {

        escrever(Login.login, getErrorUser());
        escrever(Login.senha, getSenha());
        TakeScreenshot.screenShot("Login", 6);
        clicar(Login.button);
        TakeScreenshot.screenShot("Adicionando", 6);
        clicar(SelecionarItem.adcCarrinho);
        clicar(SelecionarItem.carrinho);
        TakeScreenshot.screenShot("Check", 6);
        clicar(SelecionarItem.check);

        escrever(DadosPessoais.nome, getNome());
        escrever(DadosPessoais.sobrenome, getSobrenome());
        escrever(DadosPessoais.cep, getCep());
        TakeScreenshot.screenShot("Botão Continue", 6);
        clicar(DadosPessoais.Continue);
        Metodos.scroll(0, 400);
        TakeScreenshot.screenShot("Botão finish",6);

        Duration timeout = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        WebElement finishButton;

        try {
            finishButton = wait.until(ExpectedConditions.presenceOfElementLocated(DadosPessoais.finish));
            final String sucesso = "======= O botão 'finish' está visível na tela. ======= " + finishButton;
            Assert.assertTrue(sucesso, finishButton.isDisplayed());
            System.out.println(sucesso);
        } catch (TimeoutException e) {
            final String falha = "======= O botão 'finish' não está visível na tela. ======= ";
            System.out.println(falha);
        } finally {
            String pdfcontent = "T6_Validar se elemento do botao finish está visível. - ";
            pdfcontent += "Elemento validado: " + DadosPessoais.finish;
            TakeScreenshot.GeradorPDF.gerarPDF("./EvidenciasT6", ".png", "./T6.pdf", pdfcontent);
        }
    }

    @Test
    public void t7_ValidarFrontDoSiteComDefeito() throws IOException, DocumentException {

        escrever(Login.login, getVisualUser());
        escrever(Login.senha, getSenha());
        TakeScreenshot.screenShot("Visual User", 7);
        clicar(Login.button);

        clicar(VisualUser.produtoSelecionado);
        TakeScreenshot.screenShot("Imagem Selecionada", 7);
        clicar(VisualUser.carrinhoSele);
        clicar(SelecionarItem.check);

        TakeScreenshot.screenShot("TelaDados", 7);
        escrever(DadosPessoais.nome, getNome());
        escrever(DadosPessoais.sobrenome, getSobrenome());
        escrever(DadosPessoais.cep, getCep());
        clicar(DadosPessoais.Continue);
        TakeScreenshot.screenShot("Dados Pessoais", 7);
        clicar(DadosPessoais.finish);
        TakeScreenshot.screenShot("Conclusão", 7);
        TakeScreenshot.GeradorPDF.gerarPDF("./EvidenciasT7", ".png", "./T7.pdf", "T7_Validar front do site com defeito.");

    }
}