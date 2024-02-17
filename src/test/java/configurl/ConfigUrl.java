package configurl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigUrl {
    private static final Map<String, String> properties = new HashMap<>();

    static {
        try (FileInputStream file = new FileInputStream("C:\\Dev\\LoginInfo.xlsx")) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1); // Obtém a segunda linha (índice 1)

            if (row != null) {
                // Lê a célula do usuário na coluna "A" (índice 0)
                Cell userCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                properties.put("User", userCell.getStringCellValue());

                // Lê a célula da senha na coluna "B" (índice 1)
                Cell senhaCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                properties.put("Senha", senhaCell.getStringCellValue());

                // Lê a célula do nome na coluna "C" (índice 2)
                Cell nomeCell = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                properties.put("Nome", nomeCell.getStringCellValue());

                // Lê a célula do sobrenome na coluna "D" (índice 3)
                Cell sobrenomeCell = row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                properties.put("Sobrenome", sobrenomeCell.getStringCellValue());

                // Lê a célula do CEP na coluna "E" (índice 4)
                Cell cepCell = row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                properties.put("Cep", cepCell.getStringCellValue());

                // Lê a célula da URL na coluna "F" (índice 5)
                Cell urlCell = row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                properties.put("Url", urlCell.getStringCellValue());
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o arquivo Excel", e);
        }
    }
    public static @NotNull String getProperty(String key) {
        String value = properties.get(key);
        if (value == null || value.isEmpty())
            throw new RuntimeException("A propriedade '" + key + "' não foi definida no arquivo Excel");
        return value;
    }

    public static @NotNull String getUrl() {
        return getProperty("Url");
    }

    public static @NotNull String getUser() {
        return getProperty("User");
    }

    public static @NotNull String getSenha() {
        return getProperty("Senha");
    }
    public static @NotNull String getNome(){

        return getProperty("Nome");
    }
    public static @NotNull String getSobrenome(){

        return getProperty("Sobrenome");
    }
    public static @NotNull String getCep() {

        return getProperty("Cep");
    }
}