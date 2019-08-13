/*
 * Decompiled with CFR 0.145.
 */
package java.util.logging;

import java.util.List;

public interface LoggingMXBean {
    public String getLoggerLevel(String var1);

    public List<String> getLoggerNames();

    public String getParentLoggerName(String var1);

    public void setLoggerLevel(String var1, String var2);
}

