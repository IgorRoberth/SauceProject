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
    private static final String EXCEL_FILE_PATH = "C:\\Dev\\LoginInfo.xlsx";
    private static final String[] urlKey = {"User", "Senha", "Nome", "Sobrenome", "Cep", "Url", "Produtos", "Msg Final"};

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
            Sheet excel = workbook.getSheetAt(0);
            readProperties(excel);
            readOptionalProperties(excel);
        }
    }

    private static void readProperties(@NotNull Sheet excel) {
        Row row = excel.getRow(1);
        if (row == null) {
            throw new IllegalStateException("A linha especificada não existe no arquivo Excel");
        }

        for (int columnIndex = 0; columnIndex < urlKey.length; columnIndex++) {
            Cell cell = row.getCell(columnIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            String cellValue = getCellValue(cell);
            if (!cellValue.isEmpty()) {
                properties.put(urlKey[columnIndex], cellValue);
            }
        }
    }

    private static void readOptionalProperties(Sheet excel) {
        //Coluna 0
        readOptionalProperty(excel, "UserBloq", 2);
        readOptionalProperty(excel, "UserProblem", 3);
        readOptionalProperty(excel, "performance_glitch_user", 4);
        readOptionalProperty(excel, "error_user", 5);
        readOptionalProperty(excel, "visual_user", 6);
        //Coluna 1
        readOptionalProperty(excel, "senhaIncorreta", 2, 1);
        //Coluna 2, 3 e 4 é buscada como prioridade para o teste
        //Coluna 5
        readOptionalProperty(excel, "ImagemSelecionada", 2, 5);
        readOptionalProperty(excel, "ImagemApresentada", 3, 5);
        //Coluna 6
        readOptionalProperty(excel, "Mochila", 1, 6);
        readOptionalProperty(excel, "T_Shirt", 2, 6);
        //Coluna 7
        readOptionalProperty(excel, "MsgFinal", 1, 7);
        readOptionalProperty(excel, "MsgErro", 2, 7);
        readOptionalProperty(excel, "MsgSenhaIncorreta", 3, 7);
    }

    private static void readOptionalProperty(Sheet excel, String key, int rowIndex) {
        readOptionalProperty(excel, key, rowIndex, 0);
    }

    private static void readOptionalProperty(@NotNull Sheet excel, String key, int rowIndex, int columnIndex) {
        Row row = excel.getRow(rowIndex);
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