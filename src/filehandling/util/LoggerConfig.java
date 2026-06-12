package filehandling.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

public class LoggerConfig {

    private static final String LOG_FILE = "filehandling.log";
    private static boolean configured = false;
    private static FileHandler fileHandler;

    public static void configure() {
        if (configured) return;

        try {
            // Clean up stale lock files from previous unclean shutdowns
            cleanStaleLockFiles();

            Logger rootLogger = Logger.getLogger("filehandling");
            rootLogger.setLevel(Level.ALL);

            // File handler - logs everything to file
            fileHandler = new FileHandler(LOG_FILE, true);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(fileHandler);

            // Console handler - logs only warnings and severe
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.WARNING);
            consoleHandler.setFormatter(new SimpleFormatter());
            rootLogger.addHandler(consoleHandler);

            // Prevent duplicate logging to parent handlers
            rootLogger.setUseParentHandlers(false);

            // Shutdown hook to properly close handlers and remove lock files
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                rootLogger.info("Application shutting down, closing logger");
                if (fileHandler != null) {
                    fileHandler.close();
                }
            }));

            configured = true;

            rootLogger.info("Logger initialized successfully");

        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    private static void cleanStaleLockFiles() {
        File dir = new File("."); 
        File[] lockFiles = dir.listFiles((d, name) -> name.startsWith("filehandling.log") && name.endsWith(".lck"));
        if (lockFiles != null) {
            for (File lck : lockFiles) {
                lck.delete();
            }
        }
    }

    public static Logger getLogger(Class<?> cls) {
        if (!configured) {
            configure();
        }
        return Logger.getLogger("filehandling." + cls.getSimpleName());
    }
}