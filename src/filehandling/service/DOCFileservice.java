package filehandling.service;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.xwpf.usermodel.*;

import filehandling.util.FileUtils;
import filehandling.util.LoggerConfig;

public class DOCFileservice {

    private static final Logger logger = LoggerConfig.getLogger(DOCFileservice.class);

    public void writeDOC(String fileName, String content) {
        try {
            FileUtils.validateFileName(fileName);
            if (!FileUtils.hasExtension(fileName, ".docx")) {
                fileName += ".docx"; 
            }
            logger.info("Writing DOCX file: " + fileName);
            try (XWPFDocument document = new XWPFDocument();
                 FileOutputStream fos = new FileOutputStream(fileName)) {
                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText(content);
                document.write(fos);
                System.out.println("DOCX File Created Successfully");
                logger.info("DOCX file created successfully: " + fileName);
            }
        } 
        catch (Exception e) {
            System.out.println("Error writing DOCX: " + e.getMessage());
            logger.log(Level.SEVERE, "Error writing DOCX: " + fileName, e);
        }
    }

    public void readDOC(String fileName) {
        try {
            FileUtils.validateFileName(fileName);
            if (!FileUtils.hasExtension(fileName, ".docx")) {
                System.out.println("File must have .docx extension");
                logger.warning("Read DOCX rejected - invalid extension: " + fileName);
                return;
            }
            if (!FileUtils.fileExists(fileName)) {
                System.out.println("File does not exist");
                logger.warning("Read DOCX failed - file does not exist: " + fileName);
                return;
            }
            logger.info("Reading DOCX file: " + fileName);
            try (FileInputStream fis = new FileInputStream(fileName);
                 XWPFDocument document = new XWPFDocument(fis)) {
                List<XWPFParagraph> paragraphs = document.getParagraphs();
                System.out.println("\n----- Document Content -----\n");
                for (XWPFParagraph paragraph : paragraphs) {
                    System.out.println(paragraph.getText());
                }
                logger.info("DOCX read complete. Paragraphs: " + paragraphs.size() + " from: " + fileName);
            }
        } 
        catch (Exception e) {
            System.out.println("Error reading DOCX: " + e.getMessage());
            logger.log(Level.SEVERE, "Error reading DOCX: " + fileName, e);
        }
    }
}