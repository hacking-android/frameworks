/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import gov.nist.core.StackLogger;

public class Debug {
    public static boolean debug = false;
    public static boolean parserDebug = false;
    static StackLogger stackLogger;

    public static void logError(String string, Exception exception) {
        StackLogger stackLogger;
        if ((parserDebug || debug) && (stackLogger = Debug.stackLogger) != null) {
            stackLogger.logError(string, exception);
        }
    }

    public static void printStackTrace(Exception exception) {
        StackLogger stackLogger;
        if ((parserDebug || debug) && (stackLogger = Debug.stackLogger) != null) {
            stackLogger.logError("Stack Trace", exception);
        }
    }

    public static void println(String string) {
        StackLogger stackLogger;
        if ((parserDebug || debug) && (stackLogger = Debug.stackLogger) != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("\n");
            stackLogger.logDebug(stringBuilder.toString());
        }
    }

    public static void setStackLogger(StackLogger stackLogger) {
        Debug.stackLogger = stackLogger;
    }
}

