package filehandling.service;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import filehandling.exception.FileHandlingException;
import filehandling.util.FileUtils;
import filehandling.util.LoggerConfig;

public class FileService {

    private static final Logger logger = LoggerConfig.getLogger(FileService.class);

    public void checkFileExists(String fileName) {
        try {
            FileUtils.validateFileName(fileName);
            logger.info("Checking if file exists: " + fileName);

            if (FileUtils.fileExists(fileName)) {
                System.out.println("File exists");
                logger.info("File exists: " + fileName);
            } else {
                throw new FileHandlingException("File not found: " + fileName);
            }

        }
        catch (FileHandlingException e) {
            System.out.println(e.getMessage());
            logger.log(Level.SEVERE, "FileHandlingException: " + e.getMessage(), e);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            logger.log(Level.SEVERE, "Error checking file existence: " + fileName, e);
        }
    }

    public void createFile(String fileName) {
        try {
            FileUtils.validateFileName(fileName);
            logger.info("Attempting to create file: " + fileName);

            File file = new File(fileName);

            if (file.exists()) {
                throw new FileHandlingException("File already exists: " + fileName);
            }

            if (file.createNewFile()) {
                System.out.println("File created successfully");
                logger.info("File created successfully: " + fileName);
            } else {
                throw new FileHandlingException("Failed to create file: " + fileName);
            }

        }
        catch (FileHandlingException e) {
            System.out.println(e.getMessage());
            logger.log(Level.SEVERE, "FileHandlingException: " + e.getMessage(), e);
        }
        catch (Exception e) {
            System.out.println("Error creating file: " + e.getMessage());
            logger.log(Level.SEVERE, "Error creating file: " + fileName, e);
        }
    }

    public void displayFileInfo(String fileName) {
        try {
            FileUtils.validateFileName(fileName);
            logger.info("Displaying file info: " + fileName);

            File file = new File(fileName);

            if (!file.exists()) {
                throw new FileHandlingException("File not found: " + fileName);
            }

            System.out.println("File Name: " + file.getName());
            System.out.println("Path: " + file.getAbsolutePath());
            System.out.println("Size: " + file.length() + " bytes");
            System.out.println("Readable: " + file.canRead());
            System.out.println("Writable: " + file.canWrite());

            logger.info("File info displayed for: " + fileName + " (size=" + file.length() + " bytes)");

        }
        catch (FileHandlingException e) {
            System.out.println(e.getMessage());
            logger.log(Level.SEVERE, "FileHandlingException: " + e.getMessage(), e);
        }
        catch (Exception e) {
            System.out.println("Error reading file info: " + e.getMessage());
            logger.log(Level.SEVERE, "Error reading file info: " + fileName, e);
        }
    }

    public void deleteFile(String fileName) {
        try {
            FileUtils.validateFileName(fileName);
            logger.info("Attempting to delete file: " + fileName);

            File file = new File(fileName);

            if (!file.exists()) {
                throw new FileHandlingException("File not found: " + fileName);
            }

            if (file.delete()) {
                System.out.println("File deleted successfully");
                logger.info("File deleted successfully: " + fileName);
            } else {
                throw new FileHandlingException("Unable to delete file: " + fileName);
            }

        }
        catch (FileHandlingException e) {
            System.out.println(e.getMessage());
            logger.log(Level.SEVERE, "FileHandlingException: " + e.getMessage(), e);
        }
        catch (Exception e) {
            System.out.println("Error deleting file: " + e.getMessage());
            logger.log(Level.SEVERE, "Error deleting file: " + fileName, e);
        }
    }
}