package filehandling.util;

import java.io.IOException;
import java.util.logging.*;

public class LoggerConfig {

    private static final String LOG_FILE = "filehandling.log";
    private static boolean configured = false;

    public static void configure() {
        if (configured) return;

        try {
            Logger rootLogger = Logger.getLogger("filehandling");
            rootLogger.setLevel(Level.ALL);

            // File handler - logs everything to file
            FileHandler fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(fileHandler);

            // Console handler - logs only warnings and errors
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.WARNING);
            consoleHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(consoleHandler);

            // Prevent duplicate logging to parent handlers
            rootLogger.setUseParentHandlers(false);

            configured = true;

            rootLogger.info("Logger initialized successfully");

        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    public static Logger getLogger(Class<?> cls) {
        if (!configured) {
            configure();
        }
        return Logger.getLogger("filehandling." + cls.getSimpleName());
    }
}