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
    public void t1_CompraComSucesso() {

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
        System.out.println("\n===== Produto " + produto + " e mensagem " + msgFinal + " validadas com sucesso =====");
    }

    final static String msgErro = "Epic sadface: Sorry, this user has been locked out.";

    @Test
    public void t2_UsuarioComBloqueio() {

        escrever(Elementos.login, getUserBloq());
        escrever(Elementos.senha, getSenha());
        clicar(Elementos.button);
        validarItem(UserBloq.validarErro, msgErro);
        System.out.println("======== Mensagem de erro validada com sucesso: " + msgErro + "=======");
    }

    final static String userProblem = "Test.allTheThings() T-Shirt (Red) ";

    @Test
    public void t3_UsuarioComProblemaDeInterface() {

        escrever(Elementos.login, getUserProblem());
        escrever(Elementos.senha, getSenha());
        clicar(Elementos.button);
        clicar(UserProblem.sauceLabs);
        validarItem(UserProblem.produtoRetornado, userProblem);
        System.out.println("======== Produto esperado validado com sucesso: " + userProblem + "=======");
    }

    final static String msgsenhaIncorreta = "Epic sadface: Username and password do not match any user in this service";

    @Test
    public void t4_TentativaDeLoginComSenhaIncorreta() {

        escrever(Elementos.login, getUser());
        escrever(Elementos.senha, getSenhaIncorreta());
        clicar(Elementos.button);
        validarItem(Elementos.msgSenhaIncorreta, msgsenhaIncorreta);
        System.out.println("======== Mensagem de erro validada com sucesso: " + msgsenhaIncorreta + "========");
    }

    @Test
    public void t5_ValidacaoDePerformace() {

        long startTime = System.currentTimeMillis();
        escrever(Elementos.login, getPerformanceGlitchUser());
        escrever(Elementos.senha, getSenha());
        clicar(Elementos.button);

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

        escrever(Elementos.login, getVisual_User());
        escrever(Elementos.senha, getSenha());
        clicar(Elementos.button);

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