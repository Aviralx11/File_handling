package filehandling.util;

import java.io.File;

public class FileUtils {

    public static boolean hasExtension(String fileName, String extension) {
        return fileName != null && fileName.toLowerCase().endsWith(extension);
    }

    public static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public static void validateFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid file name");
        }
    }
}