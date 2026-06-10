package filehandling.service;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import filehandling.util.FileUtils;
import filehandling.util.LoggerConfig;

public class TextFileService {

    private static final Logger logger = LoggerConfig.getLogger(TextFileService.class);

    public void writeToFile(String fileName, String content) {
        try {
            FileUtils.validateFileName(fileName);

            if (!FileUtils.hasExtension(fileName, ".txt")) {
                System.out.println("File must have .txt extension");
                logger.warning("Write rejected - invalid extension for file: " + fileName);
                return;
            }

            logger.info("Writing to text file: " + fileName);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write(content);
                System.out.println("Content written successfully");
                logger.info("Content written successfully to: " + fileName);
            }

        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
            logger.log(Level.SEVERE, "Error writing text file: " + fileName, e);
        }
    }

    public void readFromFile(String fileName) {
        try {
            FileUtils.validateFileName(fileName);

            if (!FileUtils.fileExists(fileName)) {
                System.out.println("File does not exist");
                logger.warning("Read failed - file does not exist: " + fileName);
                return;
            }

            logger.info("Reading from text file: " + fileName);

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                logger.info("File read successfully: " + fileName);
            }

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
            logger.log(Level.SEVERE, "Error reading text file: " + fileName, e);
        }
    }

    public void appendToFile(String fileName, String content) {
        try {
            FileUtils.validateFileName(fileName);

            if (!FileUtils.hasExtension(fileName, ".txt")) {
                System.out.println("File must have .txt extension");
                logger.warning("Append rejected - invalid extension for file: " + fileName);
                return;
            }

            logger.info("Appending to text file: " + fileName);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.newLine();
                writer.write(content);
                System.out.println("Content appended successfully");
                logger.info("Content appended successfully to: " + fileName);
            }

        } catch (Exception e) {
            System.out.println("Error appending file: " + e.getMessage());
            logger.log(Level.SEVERE, "Error appending to text file: " + fileName, e);
        }
    }
}