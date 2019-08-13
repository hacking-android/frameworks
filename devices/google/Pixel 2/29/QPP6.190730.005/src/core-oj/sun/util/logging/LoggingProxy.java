/*
 * Decompiled with CFR 0.145.
 */
package sun.util.logging;

import java.util.List;

public interface LoggingProxy {
    public Object getLevel(Object var1);

    public String getLevelName(Object var1);

    public int getLevelValue(Object var1);

    public Object getLogger(String var1);

    public String getLoggerLevel(String var1);

    public List<String> getLoggerNames();

    public String getParentLoggerName(String var1);

    public String getProperty(String var1);

    public boolean isLoggable(Object var1, Object var2);

    public void log(Object var1, Object var2, String var3);

    public void log(Object var1, Object var2, String var3, Throwable var4);

    public void log(Object var1, Object var2, String var3, Object ... var4);

    public Object parseLevel(String var1);

    public void setLevel(Object var1, Object var2);

    public void setLoggerLevel(String var1, String var2);
}

