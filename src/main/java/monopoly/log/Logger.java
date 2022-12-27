package monopoly.log;

import java.io.OutputStream;
import java.io.PrintStream;

public class Logger {
    private static PrintStream printStream = System.out;
    private static LoggerMessageType messageType = LoggerMessageType.TRACE;
    private static String format = "LOGGER[%s]: %s \n";

    public static void setOutputStream(OutputStream outputStream) {
        printStream = new PrintStream(outputStream);
    }

    public static void trace(String message) {
        if (messageType.equals(LoggerMessageType.TRACE)) log(LoggerMessageType.TRACE, message);
    }

    public static void warning(String message) {
        if (!messageType.equals(LoggerMessageType.ERROR)) log(LoggerMessageType.WARNING, message);
    }

    public static void error(String message) {
        log(LoggerMessageType.ERROR, message);
    }

    public static void setLevel(LoggerMessageType loggerMessageType) {
        messageType = loggerMessageType;
    }

    public static void setFormat(String format) {
        Logger.format = format;
    }

    private static void log(LoggerMessageType type, String message) {
        printStream.printf(format, type.toString(), message);
    }
}
