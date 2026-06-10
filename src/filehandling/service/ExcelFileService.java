package filehandling.service;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import filehandling.util.FileUtils;
import filehandling.util.LoggerConfig;

public class ExcelFileService {

    private static final Logger logger = LoggerConfig.getLogger(ExcelFileService.class);

    public void writeExcel(String fileName, String id, String name, String marks) {
        try {
            FileUtils.validateFileName(fileName);
            if (!FileUtils.hasExtension(fileName, ".xlsx")) {
                fileName += ".xlsx";
            }
            logger.info("Writing Excel file: " + fileName + " [id=" + id + ", name=" + name + ", marks=" + marks + "]");
            Workbook workbook;
            Sheet sheet;
            File file = new File(fileName);
            if (file.exists()) {
                logger.info("Excel file exists, appending to: " + fileName);
                try (FileInputStream fis = new FileInputStream(fileName)) {
                    workbook = new XSSFWorkbook(fis);
                    sheet = workbook.getSheetAt(0);
                }
            } else {
                logger.info("Creating new Excel file: " + fileName);
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Students");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("ID");
                header.createCell(1).setCellValue("Name");
                header.createCell(2).setCellValue("Marks");
            }
            int lastRow = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(lastRow);
            row.createCell(0).setCellValue(id);
            row.createCell(1).setCellValue(name);
            row.createCell(2).setCellValue(marks);
            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                workbook.write(fos);
            }
            workbook.close();
            System.out.println("Excel updated successfully");
            logger.info("Excel updated successfully: " + fileName + " (row " + lastRow + ")");
        } 
        catch (Exception e) {
            System.out.println("Error writing Excel: " + e.getMessage());
            logger.log(Level.SEVERE, "Error writing Excel: " + fileName, e);
        }
    }

    public void readExcel(String fileName) {
        try {
            FileUtils.validateFileName(fileName);
            if (!FileUtils.hasExtension(fileName, ".xlsx")) {
                System.out.println("File must have .xlsx extension");
                logger.warning("Read Excel rejected - invalid extension: " + fileName);
                return;
            }
            if (!FileUtils.fileExists(fileName)) {
                System.out.println("File does not exist");
                logger.warning("Read Excel failed - file does not exist: " + fileName);
                return;
            }
            logger.info("Reading Excel file: " + fileName);
            try (FileInputStream fis = new FileInputStream(fileName);
                 Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheetAt(0);
                System.out.println("\n----- Excel Data -----\n");
                int rowCount = 0;
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        System.out.print(cell.toString() + "\t");
                    }
                    System.out.println();
                    rowCount++;
                }
                logger.info("Excel read complete. Rows: " + rowCount + " from: " + fileName);
            }
        } 
        catch (Exception e) {
            System.out.println("Error reading Excel: " + e.getMessage());
            logger.log(Level.SEVERE, "Error reading Excel: " + fileName, e);
        }
    }
}