package login;

import browser.Navegadores;
import driver.Driver;
import elementos.*;
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

    private final static String produto = "Sauce Labs Backpack";
    private final static String msgFinal = "Thank you for your order!";

    @Test
    public void t1_CompraComSucesso() {

        escrever(Login.login, getUser());
        escrever(Login.senha, getSenha());
        clicar(Login.button);

        clicar(selecionarItem.Item);
        clicar(selecionarItem.adcCarrinho);
        clicar(selecionarItem.carrinho);
        validarItem(selecionarItem.Item, produto);
        clicar(selecionarItem.check);

        escrever(DadosPessoais.nome, getNome());
        escrever(DadosPessoais.sobrenome, getSobrenome());
        escrever(DadosPessoais.cep, getCep());
        clicar(DadosPessoais.Continue);
        clicar(DadosPessoais.finish);
        validarItem(DadosPessoais.msgFinal, msgFinal);
        System.out.println("\n===== Produto " + produto + " e mensagem " + msgFinal + " validadas com sucesso =====");
    }

    private final static String msgErro = "Epic sadface: Sorry, this user has been locked out.";

    @Test
    public void t2_UsuarioComBloqueio() {

        escrever(Login.login, getUserBloq());
        escrever(Login.senha, getSenha());
        clicar(Login.button);
        validarItem(UserBloq.validarErro, msgErro);
        System.out.println("======== Mensagem de erro validada com sucesso: " + msgErro + "=======");
    }

    private final static String userProblem = "Test.allTheThings() T-Shirt (Red) ";

    @Test
    public void t3_UsuarioComProblemaDeInterface() {

        escrever(Login.login, getUserProblem());
        escrever(Login.senha, getSenha());
        clicar(Login.button);
        clicar(UserProblem.sauceLabs);
        validarItem(UserProblem.produtoRetornado, userProblem);
        System.out.println("======== Produto esperado validado com sucesso: " + userProblem + "=======");
    }

    private final static String msgsenhaIncorreta = "Epic sadface: Username and password do not match any user in this service";

    @Test
    public void t4_TentativaDeLoginComSenhaIncorreta() {

        escrever(Login.login, getUser());
        escrever(Login.senha, getSenhaIncorreta());
        clicar(Login.button);
        validarItem(Login.msgSenhaIncorreta, msgsenhaIncorreta);
        System.out.println("======== Mensagem de erro validada com sucesso: " + msgsenhaIncorreta + "========");
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

    private static final String ImagemSelecionada = "https://www.saucedemo.com/static/media/sl-404.168b1cce.jpg";
    private static final String ImagemApresentada = "https://www.saucedemo.com/static/media/sauce-backpack-1200x1500.0a0b85a3.jpg";

    @Test
    public void t6_ValidarDiferencaEntreImagensDeProdutos() {

        escrever(Login.login, getVisual_User());
        escrever(Login.senha, getSenha());
        clicar(Login.button);

        String urlPrimeiraImagem = obterUrlDaImagem(ImagemSelecionada);
        String urlSegundaImagem = obterUrlDaImagem(ImagemApresentada);
        Assert.assertNotEquals(urlPrimeiraImagem, urlSegundaImagem);
        System.out.println("As URLs das imagens dos produtos não deveriam ser iguais.");
    }

    private String obterUrlDaImagem(String urlImagem) {
        driver.get(urlImagem);
        return driver.getCurrentUrl();
    }

}