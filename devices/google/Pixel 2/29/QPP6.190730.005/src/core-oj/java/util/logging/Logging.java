/*
 * Decompiled with CFR 0.145.
 */
package java.util.logging;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

class Logging
implements LoggingMXBean {
    private static String EMPTY_STRING;
    private static LogManager logManager;

    static {
        logManager = LogManager.getLogManager();
        EMPTY_STRING = "";
    }

    Logging() {
    }

    @Override
    public String getLoggerLevel(String object) {
        if ((object = logManager.getLogger((String)object)) == null) {
            return null;
        }
        if ((object = ((Logger)object).getLevel()) == null) {
            return EMPTY_STRING;
        }
        return ((Level)object).getLevelName();
    }

    @Override
    public List<String> getLoggerNames() {
        Enumeration<String> enumeration = logManager.getLoggerNames();
        ArrayList<String> arrayList = new ArrayList<String>();
        while (enumeration.hasMoreElements()) {
            arrayList.add(enumeration.nextElement());
        }
        return arrayList;
    }

    @Override
    public String getParentLoggerName(String object) {
        if ((object = logManager.getLogger((String)object)) == null) {
            return null;
        }
        if ((object = ((Logger)object).getParent()) == null) {
            return EMPTY_STRING;
        }
        return ((Logger)object).getName();
    }

    @Override
    public void setLoggerLevel(String object, String charSequence) {
        if (object != null) {
            Logger logger = logManager.getLogger((String)object);
            if (logger != null) {
                object = null;
                if (charSequence != null && (object = Level.findLevel((String)charSequence)) == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown level \"");
                    ((StringBuilder)object).append((String)charSequence);
                    ((StringBuilder)object).append("\"");
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                logger.setLevel((Level)object);
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Logger ");
            ((StringBuilder)charSequence).append((String)object);
            ((StringBuilder)charSequence).append("does not exist");
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        throw new NullPointerException("loggerName is null");
    }
}

