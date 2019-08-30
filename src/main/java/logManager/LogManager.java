package logManager;

import org.apache.log4j.*;
import propertyManager.PropertyManager;

/**
 * Usage: Utility Logger Class to log each event in console, html log file and a text file
 * <p>
 * Implementation:
 * 1. Call setup method in beforeSuite
 * 2. All methods are static so call methods using className eg: Log.info().
 *
 * @author Anuj Gupta
 */
public class LogManager {

    // Initializes logger class and gives a name to the logger
    private static Logger logger = Logger.getLogger(" - ");

    private static ConsoleAppender consoleAppender = new ConsoleAppender();
    private static String conversionPattern;

    // To prevent other classes to create object of this utility class
    private LogManager() {

        throw new IllegalStateException("Utility class");
    }

    /**
     * Setup method to create setup for console, HTML & file logs
     */
    public static void setup() {

        setConsoleLogs();
        setUserLogs();
        setHTMLogs();
    }// End of setup

    /**
     * Creates setup for HTML Logs
     */
    private static void setHTMLogs() {

        try {
            HTMLLayout htmlLayout = new HTMLLayout();

            // Create HTML file where logs will be generated
            FileAppender fileAppender = new FileAppender(htmlLayout, PropertyManager.getProperty("htmlLogsPath"), false);

            // Generates new logs on each run by overwriting old logs and not appending to existing ones
            fileAppender.setAppend(false);

            // Prepares the appender for use
            fileAppender.activateOptions();

            // Add newAppender to the list of appenders
            logger.addAppender(fileAppender);

            // In order to avoid this redundancy, set the additivity property of Log4j logger to false and then the log messages which are coming to that logge will not be propagated to it’s parent loggers
            logger.setAdditivity(false);

        } catch (Exception e) {
            error(e.getMessage());
        }
    }// End of setHTMLogs

    /**
     * Creates setup for console Logs
     */
    private static void setConsoleLogs() {

        // Sets default configuration for the console appender
        BasicConfigurator.configure();

        // Prepares the appender for use
        consoleAppender.activateOptions();

        // Add newAppender to the list of appenders
        logger.addAppender(consoleAppender);

        // Set the additivity flag to false
        logger.setAdditivity(false);
    }// End of setConsoleLogs

    /**
     * Creates setup for user Logs
     */
    @SuppressWarnings("depricated")
    private static void setUserLogs() {

        try {
            freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_NONE);

            // Initialize reference variable for file appender
            FileAppender fileAppender = new FileAppender();

            // Create text log file where logs will be generated
            fileAppender.setFile(PropertyManager.getProperty("userLogsPath"));

            // Initialize reference variable for pattern layout class
            PatternLayout layout = new PatternLayout();

            // Set format for logs to be displayed in text file
            String fileConversionPattern = "%d{yyyy-MM-dd HH:mm:ss}%-1c%-5p %m %n";

            // Apply format to file
            layout.setConversionPattern(fileConversionPattern);
            fileAppender.setLayout(layout);

            // Generates new logs on each run by overwriting old logs and not appending to existing ones
            fileAppender.setAppend(false);

            // Prepares the appender for use
            fileAppender.activateOptions();

            // Add newAppender to the list of appenders
            logger.addAppender(fileAppender);

            // Set the additivity flag to false
            logger.setAdditivity(false);

        } catch (Exception e) {
            error(e.getMessage());
        }
    }// End of setUserLogs

    /**
     * Setup for Info level logs
     *
     * @param message Message to be displayed in logs
     */
    public static void info(String message) {

        try {
            // Initialize reference variable for pattern layout class
            PatternLayout infoLayout = new PatternLayout();

            // Set format for info logs to be displayed
            conversionPattern = "\u001b[m%d{yyyy-MM-dd HH:mm:ss}%-1c%-5p " + getStackTrace() + "%1c%m\u001b[m%n";

            // Apply format to appender
            infoLayout.setConversionPattern(conversionPattern);
            consoleAppender.setLayout(infoLayout);

            // Prepares the appender for use
            consoleAppender.activateOptions();

            // Add newAppender to the list of appenders
            logger.addAppender(consoleAppender);

            // Append message to log
            logger.info(message);

        } catch (Exception e) {
            error(e.getMessage());
        }
    }// End of info


    /**
     * Setup for Error level logs
     *
     * @param message Message to be displayed in logs
     */
    public static void error(String message) {

        try {
            // Initialize reference variable for pattern layout class
            PatternLayout errorLayout = new PatternLayout();

            // Set format for info logs to be displayed
            conversionPattern = "\u001b[31;1m%d{yyyy-MM-dd HH:mm:ss}%-1c%-5p " + getStackTrace() + "%1c%m\u001b[m%n";

            // Apply format to file
            errorLayout.setConversionPattern(conversionPattern);
            consoleAppender.setLayout(errorLayout);

            // Prepares the appender for use
            consoleAppender.activateOptions();

            // Add newAppender to the list of appenders
            logger.addAppender(consoleAppender);

            // Append message to log
            logger.error(message);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }// End of error

    /**
     * Setup for Error level logs
     *
     * @param message Message to be displayed in logs
     */
    public static void debug(String message) {

        try {
            // Initialize reference variable for pattern layout class
            PatternLayout debugLayout = new PatternLayout();

            // Set format for info logs to be displayed
            conversionPattern = "\u001b[m%d{yyyy-MM-dd HH:mm:ss}%-1c%-5p " + getStackTrace() + "%1c%m\u001b[m%n";

            // Apply format to file
            debugLayout.setConversionPattern(conversionPattern);
            consoleAppender.setLayout(debugLayout);

            // Prepares the appender for use
            consoleAppender.activateOptions();

            // Add newAppender to the list of appenders
            logger.addAppender(consoleAppender);

            // Append message to log
            logger.debug(message);

        } catch (Exception e) {
            error(e.getMessage());
        }
    }// End of debug

    /**
     * Setup to display class name, method name, & line number of the calling method in logs
     */
    private static String getStackTrace() {

        try {
            // Stores class name along with its packagae name
            String fullClassName = Thread.currentThread().getStackTrace()[5].getClassName();

            // Stores just the class name
            String className = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);

            // Stores method name under execution
            String methodName = Thread.currentThread().getStackTrace()[5].getMethodName();

            // Stores line number under execution
            int lineNumber = Thread.currentThread().getStackTrace()[5].getLineNumber();

            // Stores class, method and line number in a particular format
            return "(" + className + "." + methodName + ":" + lineNumber + ")";

        } catch (Exception e) {
            error(e.getMessage());
        }
        return "";
    }// End of getStackTrace
}
