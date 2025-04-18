package org.example.api.agencymode;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ReaderFile {
    @SneakyThrows
    public static void main(String[] args) {
        String pdfFilePath = "C:\\Users\\Administrator\\Desktop\\zhuomian\\A.pdf";
        String outputImagePath = "C:\\Users\\Administrator\\Desktop\\zhuomian\\image.png";
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            if (document.isEncrypted()) {
                System.err.println("The PDF file is encrypted.");
                return;
            }
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300);//第一页，300 DPI
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                ImageIO.write(bim, "png", baos); // 第二个参数指定格式（png/jpeg等）
                byte[] imageBytes = baos.toByteArray(); // 获取字节数组
                // 若需要转成输入流
                InputStream inputStream = new ByteArrayInputStream(imageBytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  /*  @SneakyThrows
    public static void main(String[] args) {
        String pdfFilePath = "C:\\Users\\Administrator\\Desktop\\zhuomian\\A.pdf";
        String outputImagePath = "C:\\Users\\Administrator\\Desktop\\zhuomian\\image.png";
        try (PDDocument document = PDDocument.load(new File(pdfFilePath))) {
            if (!document.isEncrypted()) {
                PDFRenderer pdfRenderer = new PDFRenderer(document);
                BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300);//第一页，300 DPI
// 保存为 PNG 图片

                ImageIO.write(bim, "png", new File(outputImagePath));
                System.out.println("PDF page saved as image:" + outputImagePath);
            } else {
                System.err.println("The PDF file is encrypted.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
