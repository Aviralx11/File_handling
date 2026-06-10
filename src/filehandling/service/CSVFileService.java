package filehandling.service;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import filehandling.util.FileUtils;
import filehandling.util.LoggerConfig;

public class CSVFileService {

    private static final Logger logger = LoggerConfig.getLogger(CSVFileService.class);

    public void writeCSV(String fileName, String id, String name, String marks) {
        try {
            FileUtils.validateFileName(fileName);

            if (!FileUtils.hasExtension(fileName, ".csv")) {
                fileName += ".csv";
            }

            logger.info("Writing CSV record to: " + fileName + " [id=" + id + ", name=" + name + ", marks=" + marks + "]");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.write(String.join(",", id, name, marks));
                writer.newLine();
                System.out.println("Record Added Successfully");
                logger.info("CSV record added successfully to: " + fileName);
            }

        } 
        catch (Exception e) {
            System.out.println("Error writing CSV: " + e.getMessage());
            logger.log(Level.SEVERE, "Error writing CSV: " + fileName, e);
        }
    }

    public void readCSV(String fileName) {
        try {
            FileUtils.validateFileName(fileName);

            if (!FileUtils.hasExtension(fileName, ".csv")) {
                System.out.println("File must have .csv extension");
                logger.warning("Read CSV rejected - invalid extension: " + fileName);
                return;
            }

            if (!FileUtils.fileExists(fileName)) {
                System.out.println("File does not exist");
                logger.warning("Read CSV failed - file does not exist: " + fileName);
                return;
            }

            logger.info("Reading CSV file: " + fileName);

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                int recordCount = 0;

                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");

                    if (data.length < 3) {
                        System.out.println("Invalid record: " + line);
                        logger.warning("Invalid CSV record found in " + fileName + ": " + line);
                        continue;
                    }

                    System.out.println("Id : " + data[0]);
                    System.out.println("Name : " + data[1]);
                    System.out.println("Marks : " + data[2]);
                    System.out.println("--------------");
                    recordCount++;
                }

                logger.info("CSV read complete. Records read: " + recordCount + " from: " + fileName);
            }

        } 
        catch (Exception e) {
            System.out.println("Error reading CSV: " + e.getMessage());
            logger.log(Level.SEVERE, "Error reading CSV: " + fileName, e);
        }
    }
}