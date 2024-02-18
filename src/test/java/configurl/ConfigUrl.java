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
    private static final String[] propertyKeys = {"User", "Senha", "Nome", "Sobrenome", "Cep", "Url"};
    private static final String EXCEL_FILE_PATH = "C:\\Dev\\LoginInfo.xlsx";

    static {
        loadPropertiesFromExcel();
    }

    private static void loadPropertiesFromExcel() {
        try (FileInputStream file = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(file)) {
            readProperties(workbook.getSheetAt(0)); // Leitura das propriedades principais
            readOptionalProperty(workbook.getSheetAt(0), "UserBloq", 2); // Leitura da propriedade opcional UserBloq
            readOptionalProperty(workbook.getSheetAt(0), "UserProblem", 3); // Leitura da propriedade opcional UserProblem
            readOptionalProperty(workbook.getSheetAt(0), "senhaIncorreta", 2, 1); // Leitura da propriedade opcional senhaIncorreta
            readOptionalProperty(workbook.getSheetAt(0), "performance_glitch_user", 4);
            readOptionalProperty(workbook.getSheetAt(0), "error_user", 5);
            readOptionalProperty(workbook.getSheetAt(0), "visual_user", 6);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar o arquivo Excel", e);
        }
    }

    private static void readProperties(@NotNull Sheet sheet) {
        Row row = sheet.getRow(1); // Obtém a segunda linha (índice 1)
        if (row == null) {
            throw new RuntimeException("A linha especificada não existe no arquivo Excel");
        }

        for (int columnIndex = 0; columnIndex < propertyKeys.length; columnIndex++) {
            Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String cellValue = cell.getStringCellValue();
            if (cellValue == null || cellValue.trim().isEmpty()) {
                throw new RuntimeException("Célula vazia encontrada na coluna: " + columnIndex);
            }
            properties.put(propertyKeys[columnIndex], cellValue);
        }
    }

    private static void readOptionalProperty(Sheet sheet, String key, int rowIndex) {
        readOptionalProperty(sheet, key, rowIndex, 0);
    }

    private static void readOptionalProperty(@NotNull Sheet sheet, String key, int rowIndex, int columnIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row != null) {
            Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String cellValue = cell.getStringCellValue();
            if (cellValue != null && !cellValue.trim().isEmpty()) {
                properties.put(key, cellValue);
            }
        }
    }

    public static @NotNull String getProperty(String key) {
        String value = properties.get(key);
        if (value == null || value.isEmpty())
            throw new RuntimeException("A propriedade '" + key + "' não foi definida no arquivo Excel");
        return value;
    }

    // Métodos de acesso para as propriedades
    public static @NotNull String getUrl() {

        return getProperty("Url");
    }

    public static @NotNull String getUser() {

        return getProperty("User");
    }

    public static @NotNull String getPerformanceGlitchUser() {

        return getProperty("performance_glitch_user");
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
    public static @NotNull String getError_User () {

        return getProperty("error_user");
    }
    public static @NotNull String getVisual_User () {

        return getProperty("visual_user");
    }
}