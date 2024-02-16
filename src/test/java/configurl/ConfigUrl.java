package configurl;

import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigUrl {

    private static final Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream("C:\\Dev\\url.properties.txt"));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o arquivo de propriedades", e);
        }

        try {
            // Carrega as propriedades uma vez quando a classe é carregada
            properties.load(new FileInputStream("C:\\Dev\\dados.properties.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Não foi possível carregar o arquivo de propriedades.", e);
        }
    }

    public static @NotNull String getUrl() {
        String url = properties.getProperty("url");
        if (url == null || url.isEmpty()) {
            throw new RuntimeException("A propriedade 'url' não foi definida no arquivo de propriedades");
        }
        return url;
    }

    // Método público para acessar as propriedades
    public static String getProperty(String key) {

        return properties.getProperty(key);
    }
}


