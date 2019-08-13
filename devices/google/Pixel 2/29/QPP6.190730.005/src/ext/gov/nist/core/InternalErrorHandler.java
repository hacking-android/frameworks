/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import gov.nist.core.StackLogger;
import java.io.PrintStream;

public class InternalErrorHandler {
    public static void handleException(Exception exception) throws RuntimeException {
        PrintStream printStream = System.err;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected internal error FIXME!! ");
        stringBuilder.append(exception.getMessage());
        printStream.println(stringBuilder.toString());
        exception.printStackTrace();
        stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected internal error FIXME!! ");
        stringBuilder.append(exception.getMessage());
        throw new RuntimeException(stringBuilder.toString(), exception);
    }

    public static void handleException(Exception exception, StackLogger object) {
        PrintStream printStream = System.err;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected internal error FIXME!! ");
        stringBuilder.append(exception.getMessage());
        printStream.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("UNEXPECTED INTERNAL ERROR FIXME ");
        stringBuilder.append(exception.getMessage());
        object.logError(stringBuilder.toString());
        exception.printStackTrace();
        object.logException(exception);
        object = new StringBuilder();
        ((StringBuilder)object).append("Unexpected internal error FIXME!! ");
        ((StringBuilder)object).append(exception.getMessage());
        throw new RuntimeException(((StringBuilder)object).toString(), exception);
    }

    public static void handleException(String string) {
        new Exception().printStackTrace();
        System.err.println("Unexepcted INTERNAL ERROR FIXME!!");
        System.err.println(string);
        throw new RuntimeException(string);
    }

    public static void handleException(String string, StackLogger stackLogger) {
        stackLogger.logStackTrace();
        stackLogger.logError("Unexepcted INTERNAL ERROR FIXME!!");
        stackLogger.logFatalError(string);
        throw new RuntimeException(string);
    }
}

