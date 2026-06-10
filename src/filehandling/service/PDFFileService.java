package filehandling.service;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.text.PDFTextStripper;

import filehandling.util.FileUtils;
import filehandling.util.LoggerConfig;

public class PDFFileService {

    private static final Logger logger = LoggerConfig.getLogger(PDFFileService.class);

    public void writePDF(String fileName, String content) {
        try {
            FileUtils.validateFileName(fileName);
            if (!FileUtils.hasExtension(fileName, ".pdf")) {
                fileName += ".pdf";
            }
            logger.info("Writing PDF file: " + fileName);
            File file = new File(fileName);
            if (file.exists()) {
                System.out.println("File already exists. It will be overwritten.");
                logger.warning("PDF file will be overwritten: " + fileName);
            }
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);
                try (PDPageContentStream stream =
                             new PDPageContentStream(document, page)) {
                    PDFont font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
                    stream.setFont(font, 12);
                    stream.beginText();
                    stream.newLineAtOffset(50, 700);
                    // ✅ Handle long text (simple line wrapping)
                    int maxCharsPerLine = 80;
                    String[] words = content.split(" ");
                    StringBuilder line = new StringBuilder();
                    for (String word : words) {
                        if (line.length() + word.length() > maxCharsPerLine) {
                            stream.showText(line.toString());
                            stream.newLineAtOffset(0, -15);
                            line = new StringBuilder();
                        }
                        line.append(word).append(" ");
                    }
                    if (!line.isEmpty()) {
                        stream.showText(line.toString());
                    }
                    stream.endText();
                }
                document.save(fileName);
                System.out.println("PDF Created Successfully");
                logger.info("PDF created successfully: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error writing PDF: " + e.getMessage());
            logger.log(Level.SEVERE, "Error writing PDF: " + fileName, e);
        }
    }

    public void readPDF(String fileName) {
        try {
            FileUtils.validateFileName(fileName);
            if (!FileUtils.hasExtension(fileName, ".pdf")) {
                System.out.println("File must have .pdf extension");
                logger.warning("Read PDF rejected - invalid extension: " + fileName);
                return;
            }
            if (!FileUtils.fileExists(fileName)) {
                System.out.println("File does not exist");
                logger.warning("Read PDF failed - file does not exist: " + fileName);
                return;
            }
            logger.info("Reading PDF file: " + fileName);
            try (PDDocument document = Loader.loadPDF(new File(fileName))) {
                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(document);
                System.out.println("\n----- PDF Content -----\n");
                System.out.println(text);
                logger.info("PDF read complete. Pages: " + document.getNumberOfPages() + " from: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error reading PDF: " + e.getMessage());
            logger.log(Level.SEVERE, "Error reading PDF: " + fileName, e);
        }
    }
}