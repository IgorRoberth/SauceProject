package pdfscreenshot.take;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import driver.Driver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TakeScreenshot extends Driver {

    public static void screenShot(String nome, int numeroTeste) throws IOException {
        TakesScreenshot srcShot = ((TakesScreenshot) driver);
        File srcFile = srcShot.getScreenshotAs(OutputType.FILE);

        String pastaEvidencias = "./EvidenciasT" + numeroTeste + "/";
        File desFile = new File(pastaEvidencias + nome + ".png");
        FileUtils.copyFile(srcFile, desFile);
    }

    public static class GeradorPDF {

        public static void gerarPDF(String caminhoImagens, String extensaoImagens, String caminhoPDF, String titulo) throws IOException, DocumentException {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(caminhoPDF));
            document.open();

            // Adiciona o título ao documento
            if (titulo != null && !titulo.isEmpty()) {
                Paragraph titleParagraph = new Paragraph(titulo);
                titleParagraph.setAlignment(Paragraph.ALIGN_CENTER); // Centraliza o título
                document.add(titleParagraph);
                document.add(new Paragraph(" ")); // Adiciona um espaço em branco após o título
            }

            File diretorio = new File(caminhoImagens);
            File[] arquivos = diretorio.listFiles((dir, nome) -> nome.endsWith(extensaoImagens));

            if (arquivos != null && arquivos.length > 0) {
                for (File arquivo : arquivos) {
                    Image imagem = Image.getInstance(arquivo.getAbsolutePath());
                    Rectangle pageSize = document.getPageSize();
                    float scalePercent = ((pageSize.getWidth() - document.leftMargin() - document.rightMargin()) / imagem.getWidth()) * 100;
                    imagem.scalePercent(scalePercent);
                    imagem.setCompressionLevel((int) 0.5f);
                    document.add(imagem);
                }
            } else {
                throw new IOException("Nenhuma imagem encontrada para adicionar ao PDF.");
            }

            document.close();
        }

        public static void terminar() {
            try {
                File diretorioEvidencias = new File("./");
                File[] subdiretorios = diretorioEvidencias.listFiles(File::isDirectory);

                if (subdiretorios != null) {
                    for (File subdiretorio : subdiretorios) {
                        if (subdiretorio.getName().startsWith("EvidenciasT")) {
                            String caminhoPDF = subdiretorio.getAbsolutePath().replace("EvidenciasT", "EvidenciasPDFt") + ".pdf";
                            gerarPDF(subdiretorio.getAbsolutePath(), ".png", caminhoPDF, "Teste");
                        }
                    }
                }
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }
        }
    }
}