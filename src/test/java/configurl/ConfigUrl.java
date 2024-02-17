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
        try (FileInputStream file = new FileInputStream("C:\\Dev\\LoginInfo.xlsx");
             Workbook workbook = new XSSFWorkbook(file)) { // Fechamento automático do Workbook
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1); // Obtém a segunda linha (índice 1)

            if (row == null) {
                throw new RuntimeException("A linha especificada não existe no arquivo Excel");
            }

            for (int columnIndex = 0; columnIndex <= 5; columnIndex++) {
                Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String cellValue = cell.getStringCellValue();
                if (cellValue == null || cellValue.trim().isEmpty()) {
                    throw new RuntimeException("Célula vazia encontrada na coluna: " + columnIndex);
                }
                String propertyName = getPropertyNameByIndex(columnIndex);
                properties.put(propertyName, cellValue);
            }

            // Adicionando a leitura da propriedade 'UserBloq'
            Row userBloqRow = sheet.getRow(2); // Índice 2 para a segunda linha
            if (userBloqRow != null) {
                Cell userBloqCell = userBloqRow.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK); // Índice 0 para a coluna A
                String userBloqValue = userBloqCell.getStringCellValue();
                if (userBloqValue != null && !userBloqValue.trim().isEmpty()) {
                    properties.put("UserBloq", userBloqValue);
                }
            }
            Row userProblemRow = sheet.getRow(3); // Índice 3 para a terceira linha
            if (userProblemRow != null) {
                Cell userProblemCell = userProblemRow.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK); // Índice 0 para a coluna A
                String userProblemValue = userProblemCell.getStringCellValue();
                if (userProblemValue != null && !userProblemValue.trim().isEmpty()) {
                    properties.put("UserProblem", userProblemValue);
                }
            }
            Row senhaIncorretaRow = sheet.getRow(2);
            if (senhaIncorretaRow != null) {
                Cell senhaIncorretaCell = senhaIncorretaRow.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String senhaIncorretaValue = senhaIncorretaCell.getStringCellValue();
                if (senhaIncorretaValue != null && !senhaIncorretaValue.trim().isEmpty()) {
                    properties.put("senhaIncorreta", senhaIncorretaValue);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o arquivo Excel", e);
        }
    }

    private static String getPropertyNameByIndex(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "User";
            case 1:
                return "Senha";
            case 2:
                return "Nome";
            case 3:
                return "Sobrenome";
            case 4:
                return "Cep";
            case 5:
                return "Url";
            default:
                throw new IllegalArgumentException("Índice de coluna inválido: " + columnIndex);
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

    public static @NotNull String getNome() {

        return getProperty("Nome");
    }

    public static @NotNull String getSobrenome() {

        return getProperty("Sobrenome");
    }

    public static @NotNull String getCep() {

        return getProperty("Cep");
    }

    public static @NotNull String getUserBloq() {

        return getProperty("UserBloq");
    }

    public static @NotNull String getUserProblem() {

        return getProperty("UserProblem");
    }

    public static @NotNull String getSenhaIncorreta() {

        return getProperty("senhaIncorreta");
    }
}