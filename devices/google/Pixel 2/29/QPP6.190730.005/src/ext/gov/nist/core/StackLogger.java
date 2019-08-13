/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import gov.nist.core.LogLevels;
import java.util.Properties;

public interface StackLogger
extends LogLevels {
    public void disableLogging();

    public void enableLogging();

    public int getLineCount();

    public String getLoggerName();

    public boolean isLoggingEnabled();

    public boolean isLoggingEnabled(int var1);

    public void logDebug(String var1);

    public void logError(String var1);

    public void logError(String var1, Exception var2);

    public void logException(Throwable var1);

    public void logFatalError(String var1);

    public void logInfo(String var1);

    public void logStackTrace();

    public void logStackTrace(int var1);

    public void logTrace(String var1);

    public void logWarning(String var1);

    public void setBuildTimeStamp(String var1);

    public void setStackProperties(Properties var1);
}

