/*
 * Decompiled with CFR 0.145.
 */
package java.util.logging;

import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.logging.ErrorManager;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

public abstract class Handler {
    private static final int offValue = Level.OFF.intValue();
    private volatile String encoding;
    private volatile ErrorManager errorManager = new ErrorManager();
    private volatile Filter filter;
    private volatile Formatter formatter;
    private volatile Level logLevel = Level.ALL;
    private final LogManager manager = LogManager.getLogManager();
    boolean sealed = true;

    protected Handler() {
    }

    void checkPermission() throws SecurityException {
        if (this.sealed) {
            this.manager.checkPermission();
        }
    }

    public abstract void close() throws SecurityException;

    public abstract void flush();

    public String getEncoding() {
        return this.encoding;
    }

    public ErrorManager getErrorManager() {
        this.checkPermission();
        return this.errorManager;
    }

    public Filter getFilter() {
        return this.filter;
    }

    public Formatter getFormatter() {
        return this.formatter;
    }

    public Level getLevel() {
        return this.logLevel;
    }

    public boolean isLoggable(LogRecord logRecord) {
        int n = this.getLevel().intValue();
        if (logRecord.getLevel().intValue() >= n && n != offValue) {
            Filter filter = this.getFilter();
            if (filter == null) {
                return true;
            }
            return filter.isLoggable(logRecord);
        }
        return false;
    }

    public abstract void publish(LogRecord var1);

    protected void reportError(String string, Exception exception, int n) {
        try {
            this.errorManager.error(string, exception, n);
        }
        catch (Exception exception2) {
            System.err.println("Handler.reportError caught:");
            exception2.printStackTrace();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setEncoding(String string) throws SecurityException, UnsupportedEncodingException {
        synchronized (this) {
            this.checkPermission();
            if (string != null) {
                try {
                    if (!Charset.isSupported(string)) {
                        UnsupportedEncodingException unsupportedEncodingException = new UnsupportedEncodingException(string);
                        throw unsupportedEncodingException;
                    }
                }
                catch (IllegalCharsetNameException illegalCharsetNameException) {
                    UnsupportedEncodingException unsupportedEncodingException = new UnsupportedEncodingException(string);
                    throw unsupportedEncodingException;
                }
            }
            this.encoding = string;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setErrorManager(ErrorManager object) {
        synchronized (this) {
            this.checkPermission();
            if (object != null) {
                this.errorManager = object;
                return;
            }
            object = new NullPointerException();
            throw object;
        }
    }

    public void setFilter(Filter filter) throws SecurityException {
        synchronized (this) {
            this.checkPermission();
            this.filter = filter;
            return;
        }
    }

    public void setFormatter(Formatter formatter) throws SecurityException {
        synchronized (this) {
            this.checkPermission();
            formatter.getClass();
            this.formatter = formatter;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setLevel(Level serializable) throws SecurityException {
        synchronized (this) {
            Throwable throwable2;
            if (serializable != null) {
                try {
                    this.checkPermission();
                    this.logLevel = serializable;
                    return;
                }
                catch (Throwable throwable2) {}
            } else {
                serializable = new NullPointerException();
                throw serializable;
            }
            throw throwable2;
        }
    }
}

