package filehandling.main;

import java.util.*;
import java.util.logging.Logger;
import filehandling.service.*;
import filehandling.util.LoggerConfig;

public class Main {

    private static final Logger logger = LoggerConfig.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("Application started");

        Scanner sc = new Scanner(System.in);

        FileService service = new FileService();
        TextFileService textService = new TextFileService();
        CSVFileService csvService = new CSVFileService();
        XMLFileService xmlService = new XMLFileService();
        DOCFileservice docService = new DOCFileservice();
        ExcelFileService excelService = new ExcelFileService();
        JSONFileService jsonService = new JSONFileService();
        PDFFileService pdfService = new PDFFileService();

        while (true) {

            printMenu();

            int choice = getChoice(sc);
            if (choice == -1) continue;

            logger.info("User selected menu option: " + choice);

            switch (choice) {

                case 0:
                    System.out.println("Exiting...");
                    logger.info("Application exiting");
                    sc.close();
                    return;

                case 1:
                    service.checkFileExists(getInput(sc, "Enter file name"));
                    break;

                case 2:
                    service.createFile(getInput(sc, "Enter file name"));
                    break;

                case 3:
                    service.displayFileInfo(getInput(sc, "Enter file name"));
                    break;

                case 4:
                    textService.writeToFile(getInput(sc, "Enter file name"),
                            getInput(sc, "Enter content")
                    );
                    break;

                case 5:
                    textService.readFromFile(getInput(sc, "Enter file name"));
                    break;

                case 6:
                    textService.appendToFile(getInput(sc, "Enter file name"),
                            getInput(sc, "Enter content")
                    );
                    break;

                case 7:
                    service.deleteFile(getInput(sc, "Enter file name"));
                    break;

                case 8: {
                    String fileName = getInput(sc, "Enter CSV file name");
                    String[] data = getStudentData(sc);
                    csvService.writeCSV(fileName, data[0], data[1], data[2]);
                    break;
                }

                case 9:
                    csvService.readCSV(getInput(sc, "Enter CSV file name"));
                    break;

                case 10: {
                    String fileName = getInput(sc, "Enter XML file name");
                    String[] data = getStudentData(sc);
                    xmlService.writeXML(fileName, data[0], data[1], data[2]);
                    break;
                }

                case 11:
                    xmlService.readXML(getInput(sc, "Enter XML file name"));
                    break;

                case 12:
                    docService.writeDOC(
                            getInput(sc, "Enter DOCX file name"),
                            getInput(sc, "Enter content")
                    );
                    break;

                case 13:
                    docService.readDOC(getInput(sc, "Enter DOCX file name"));
                    break;

                case 14: {
                    String fileName = getInput(sc, "Enter Excel file name");
                    String[] data = getStudentData(sc);
                    excelService.writeExcel(fileName, data[0], data[1], data[2]);
                    break;
                }

                case 15:
                    excelService.readExcel(getInput(sc, "Enter Excel file name"));
                    break;

                case 16: {
                    String fileName = getInput(sc, "Enter JSON file name");
                    String[] data = getStudentData(sc);
                    jsonService.writeJSON(fileName, data[0], data[1], data[2]);
                    break;
                }

                case 17:
                    jsonService.readJSON(getInput(sc, "Enter JSON file name"));
                    break;
                    
                case 18:
                	pdfService.writePDF(getInput(sc,"Enter PDF file name"), 
                			getInput(sc,"Enter Content"));
                	break;
                	
                case 19:
                	pdfService.readPDF(getInput(sc,"Enter PDF file name"));
                	break;

                default:
                    System.out.println("Invalid choice");
                    logger.warning("Invalid menu choice entered: " + choice);
            }
        }
    }

 
    private static void printMenu() {
        System.out.println("\n-------- FILE MANAGEMENT SYSTEM --------");
        System.out.println("0. Exit");
        System.out.println("1. Check if file exists");
        System.out.println("2. Create File");
        System.out.println("3. Display File info");
        System.out.println("4. Write to text file");
        System.out.println("5. Read from file");
        System.out.println("6. Append to file");
        System.out.println("7. Delete file");
        System.out.println("8. Write CSV");
        System.out.println("9. Read CSV");
        System.out.println("10. Write XML");
        System.out.println("11. Read XML");
        System.out.println("12. Write DOCX");
        System.out.println("13. Read DOCX");
        System.out.println("14. Write Excel");
        System.out.println("15. Read Excel");
        System.out.println("16. Write JSON");
        System.out.println("17. Read JSON");
        System.out.println("18. Write PDF");
        System.out.println("19. Read PDF");
    }

    private static int getChoice(Scanner sc) {
        try {
            int choice = sc.nextInt();
            sc.nextLine();
            return choice;
        } catch (Exception e) {
            System.out.println("Invalid input. Enter a number.");
            logger.warning("Invalid input entered: " + e.getMessage());
            sc.nextLine();
            return -1;
        }
    }


    private static String getInput(Scanner sc, String message) {
        System.out.println(message);
        return sc.nextLine().trim();
    }


    private static String[] getStudentData(Scanner sc) {
        String id = getInput(sc, "Enter Id");
        String name = getInput(sc, "Enter Name");
        String marks = getInput(sc, "Enter Marks");
        return new String[]{id, name, marks};
    }
}