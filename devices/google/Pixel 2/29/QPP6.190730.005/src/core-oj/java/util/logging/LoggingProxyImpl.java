/*
 * Decompiled with CFR 0.145.
 */
package java.util.logging;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import sun.util.logging.LoggingProxy;

class LoggingProxyImpl
implements LoggingProxy {
    static final LoggingProxy INSTANCE = new LoggingProxyImpl();

    private LoggingProxyImpl() {
    }

    @Override
    public Object getLevel(Object object) {
        return ((Logger)object).getLevel();
    }

    @Override
    public String getLevelName(Object object) {
        return ((Level)object).getLevelName();
    }

    @Override
    public int getLevelValue(Object object) {
        return ((Level)object).intValue();
    }

    @Override
    public Object getLogger(String string) {
        return Logger.getPlatformLogger(string);
    }

    @Override
    public String getLoggerLevel(String string) {
        return LogManager.getLoggingMXBean().getLoggerLevel(string);
    }

    @Override
    public List<String> getLoggerNames() {
        return LogManager.getLoggingMXBean().getLoggerNames();
    }

    @Override
    public String getParentLoggerName(String string) {
        return LogManager.getLoggingMXBean().getParentLoggerName(string);
    }

    @Override
    public String getProperty(String string) {
        return LogManager.getLogManager().getProperty(string);
    }

    @Override
    public boolean isLoggable(Object object, Object object2) {
        return ((Logger)object).isLoggable((Level)object2);
    }

    @Override
    public void log(Object object, Object object2, String string) {
        ((Logger)object).log((Level)object2, string);
    }

    @Override
    public void log(Object object, Object object2, String string, Throwable throwable) {
        ((Logger)object).log((Level)object2, string, throwable);
    }

    @Override
    public void log(Object object, Object object2, String string, Object ... arrobject) {
        ((Logger)object).log((Level)object2, string, arrobject);
    }

    @Override
    public Object parseLevel(String string) {
        Serializable serializable = Level.findLevel(string);
        if (serializable != null) {
            return serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unknown level \"");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append("\"");
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    @Override
    public void setLevel(Object object, Object object2) {
        ((Logger)object).setLevel((Level)object2);
    }

    @Override
    public void setLoggerLevel(String string, String string2) {
        LogManager.getLoggingMXBean().setLoggerLevel(string, string2);
    }
}

