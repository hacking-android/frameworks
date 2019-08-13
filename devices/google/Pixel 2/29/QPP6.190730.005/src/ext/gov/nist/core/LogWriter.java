/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import gov.nist.core.StackLogger;
import java.util.Properties;

public class LogWriter
implements StackLogger {
    private static final String TAG = "SIP_STACK";
    private boolean mEnabled = true;

    @Override
    public void disableLogging() {
        this.mEnabled = false;
    }

    @Override
    public void enableLogging() {
        this.mEnabled = true;
    }

    @Override
    public int getLineCount() {
        return 0;
    }

    @Override
    public String getLoggerName() {
        return "Android SIP Logger";
    }

    @Override
    public boolean isLoggingEnabled() {
        return this.mEnabled;
    }

    @Override
    public boolean isLoggingEnabled(int n) {
        return this.mEnabled;
    }

    @Override
    public void logDebug(String string) {
    }

    @Override
    public void logError(String string) {
    }

    @Override
    public void logError(String string, Exception exception) {
    }

    @Override
    public void logException(Throwable throwable) {
    }

    @Override
    public void logFatalError(String string) {
    }

    @Override
    public void logInfo(String string) {
    }

    @Override
    public void logStackTrace() {
    }

    @Override
    public void logStackTrace(int n) {
    }

    @Override
    public void logTrace(String string) {
    }

    @Override
    public void logWarning(String string) {
    }

    @Override
    public void setBuildTimeStamp(String string) {
    }

    @Override
    public void setStackProperties(Properties properties) {
    }
}

