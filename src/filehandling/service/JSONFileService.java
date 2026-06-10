package filehandling.service;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import filehandling.util.FileUtils;
import filehandling.util.LoggerConfig;

public class JSONFileService {

    private static final Logger logger = LoggerConfig.getLogger(JSONFileService.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public void writeJSON(String fileName, String id, String name, String marks) {
        try {
            FileUtils.validateFileName(fileName);

            if (!FileUtils.hasExtension(fileName, ".json")) {
                fileName += ".json";
            }

            logger.info("Writing JSON file: " + fileName + " [id=" + id + ", name=" + name + ", marks=" + marks + "]");

            ObjectNode student = mapper.createObjectNode();
            student.put("id", id);
            student.put("name", name);
            student.put("marks", marks);

            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), student);

            System.out.println("JSON File Created Successfully");
            logger.info("JSON file created successfully: " + fileName);

        } 
        catch (Exception e) {
            System.out.println("Error writing JSON: " + e.getMessage());
            logger.log(Level.SEVERE, "Error writing JSON: " + fileName, e);
        }
    }

    public void readJSON(String fileName) {
    	
        try {
            FileUtils.validateFileName(fileName);

            if (!FileUtils.hasExtension(fileName, ".json")) {
                System.out.println("File must have .json extension");
                logger.warning("Read JSON rejected - invalid extension: " + fileName);
                return;
            }

            if (!FileUtils.fileExists(fileName)) {
                System.out.println("File does not exist");
                logger.warning("Read JSON failed - file does not exist: " + fileName);
                return;
            }

            logger.info("Reading JSON file: " + fileName);

            JsonNode student = mapper.readTree(new File(fileName));

            System.out.println("ID : " + student.path("id").asText("N/A"));
            System.out.println("Name : " + student.path("name").asText("N/A"));
            System.out.println("Marks : " + student.path("marks").asText("N/A"));

            logger.info("JSON read complete: " + fileName);

        } 
        catch (Exception e) {
            System.out.println("Error reading JSON: " + e.getMessage());
            logger.log(Level.SEVERE, "Error reading JSON: " + fileName, e);
        }
    }
}