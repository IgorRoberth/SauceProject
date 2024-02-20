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
    private static final String[] propertyKeys = {"User", "Senha", "Nome", "Sobrenome", "Cep", "Url", "Produtos", "Msg Final"};
    private static final String EXCEL_FILE_PATH = "C:\\Dev\\LoginInfo.xlsx";

    static {
        try {
            loadPropertiesFromExcel();
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Erro ao carregar o arquivo Excel: " + e.getMessage());
        }
    }

    private static void loadPropertiesFromExcel() throws IOException {
        try (FileInputStream file = new FileInputStream(EXCEL_FILE_PATH);
             Workbook workbook = new XSSFWorkbook(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            readProperties(sheet);
            readOptionalProperties(sheet);
        }
    }

    private static void readProperties(@NotNull Sheet sheet) {
        Row row = sheet.getRow(1);
        if (row == null) {
            throw new IllegalStateException("A linha especificada não existe no arquivo Excel");
        }

        for (int columnIndex = 0; columnIndex < propertyKeys.length; columnIndex++) {
            Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String cellValue = getCellValue(cell);
            if (!cellValue.isEmpty()) {
                properties.put(propertyKeys[columnIndex], cellValue);
            }
        }
    }

    private static void readOptionalProperties(Sheet sheet) {
        readOptionalProperty(sheet, "UserBloq", 2);
        readOptionalProperty(sheet, "UserProblem", 3);
        readOptionalProperty(sheet, "senhaIncorreta", 2, 1);
        readOptionalProperty(sheet, "performance_glitch_user", 4);
        readOptionalProperty(sheet, "error_user", 5);
        readOptionalProperty(sheet, "visual_user", 6);
        readOptionalProperty(sheet, "ImagemSelecionada", 2, 5);
        readOptionalProperty(sheet, "ImagemApresentada", 3, 5);
        readOptionalProperty(sheet, "Mochila", 1, 6);
        readOptionalProperty(sheet, "T_Shirt", 2, 6);
        readOptionalProperty(sheet, "MsgFinal", 1, 7);
        readOptionalProperty(sheet, "MsgErro", 2, 7);
        readOptionalProperty(sheet, "MsgSenhaIncorreta", 3, 7);
    }

    private static void readOptionalProperty(Sheet sheet, String key, int rowIndex) {
        readOptionalProperty(sheet, key, rowIndex, 0);
    }

    private static void readOptionalProperty(@NotNull Sheet sheet, String key, int rowIndex, int columnIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row != null) {
            Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String cellValue = getCellValue(cell);
            if (!cellValue.isEmpty()) {
                properties.put(key, cellValue);
            }
        }
    }

    private static @NotNull String getCellValue(Cell cell) {
        return cell == null ? "" : cell.toString().trim();
    }

    public static @NotNull String getProperty(String key) {
        String value = properties.get(key);
        if (value == null) {
            throw new IllegalArgumentException("A propriedade '" + key + "' não foi definida no arquivo Excel");
        }
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

    public static @NotNull String getSenhaIncorreta() {

        return getProperty("senhaIncorreta");
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

    public static @NotNull String getMochila() {

        return getProperty("Mochila");
    }

    public static @NotNull String getMsgFinal() {

        return getProperty("MsgFinal");
    }

    public static @NotNull String getMsgErro() {

        return getProperty("MsgErro");
    }

    public static @NotNull String getMsgSenhaIncorreta() {

        return getProperty("MsgSenhaIncorreta");
    }

    public static @NotNull String getUserBloq() {

        return getProperty("UserBloq");
    }

    public static @NotNull String getUserProblem() {

        return getProperty("UserProblem");
    }

    public static @NotNull String getProdProblem() {

        return getProperty("T_Shirt");
    }

    public static @NotNull String getPerformanceGlitchUser() {

        return getProperty("performance_glitch_user");
    }

    public static @NotNull String getErrorUser() {

        return getProperty("error_user");
    }

    public static @NotNull String getVisualUser() {

        return getProperty("visual_user");
    }

    public static @NotNull String getImagemSelecionada() {

        return getProperty("ImagemSelecionada");
    }

    public static @NotNull String getImagemApresentada() {

        return getProperty("ImagemApresentada");
    }
}