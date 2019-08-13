/*
 * Decompiled with CFR 0.145.
 */
package java.util.logging;

import java.io.Serializable;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class MemoryHandler
extends Handler {
    private static final int DEFAULT_SIZE = 1000;
    private LogRecord[] buffer;
    int count;
    private volatile Level pushLevel;
    private int size;
    int start;
    private Handler target;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public MemoryHandler() {
        this.sealed = false;
        this.configure();
        this.sealed = true;
        Object object = LogManager.getLogManager();
        String string = this.getClass().getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".target");
        object = ((LogManager)object).getProperty(stringBuilder.toString());
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("The handler ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" does not specify a target");
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        try {
            this.target = (Handler)ClassLoader.getSystemClassLoader().loadClass((String)object).newInstance();
        }
        catch (Exception exception) {
            try {
                this.target = (Handler)Thread.currentThread().getContextClassLoader().loadClass((String)object).newInstance();
            }
            catch (Exception exception2) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("MemoryHandler can't load handler target \"");
                stringBuilder.append((String)object);
                stringBuilder.append("\"");
                throw new RuntimeException(stringBuilder.toString(), exception2);
            }
        }
        this.init();
    }

    public MemoryHandler(Handler handler, int n, Level level) {
        if (handler != null && level != null) {
            if (n > 0) {
                this.sealed = false;
                this.configure();
                this.sealed = true;
                this.target = handler;
                this.pushLevel = level;
                this.size = n;
                this.init();
                return;
            }
            throw new IllegalArgumentException();
        }
        throw new NullPointerException();
    }

    private void configure() {
        LogManager logManager = LogManager.getLogManager();
        String string = this.getClass().getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".push");
        this.pushLevel = logManager.getLevelProperty(stringBuilder.toString(), Level.SEVERE);
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".size");
        this.size = logManager.getIntProperty(stringBuilder.toString(), 1000);
        if (this.size <= 0) {
            this.size = 1000;
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".level");
        this.setLevel(logManager.getLevelProperty(stringBuilder.toString(), Level.ALL));
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".filter");
        this.setFilter(logManager.getFilterProperty(stringBuilder.toString(), null));
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".formatter");
        this.setFormatter(logManager.getFormatterProperty(stringBuilder.toString(), new SimpleFormatter()));
    }

    private void init() {
        this.buffer = new LogRecord[this.size];
        this.start = 0;
        this.count = 0;
    }

    @Override
    public void close() throws SecurityException {
        this.target.close();
        this.setLevel(Level.OFF);
    }

    @Override
    public void flush() {
        this.target.flush();
    }

    public Level getPushLevel() {
        return this.pushLevel;
    }

    @Override
    public boolean isLoggable(LogRecord logRecord) {
        return super.isLoggable(logRecord);
    }

    @Override
    public void publish(LogRecord logRecord) {
        synchronized (this) {
            block7 : {
                boolean bl = this.isLoggable(logRecord);
                if (bl) break block7;
                return;
            }
            int n = this.start++;
            int n2 = this.count;
            int n3 = this.buffer.length;
            this.buffer[(n + n2) % n3] = logRecord;
            if (this.count < this.buffer.length) {
                ++this.count;
            } else {
                this.start %= this.buffer.length;
            }
            if (logRecord.getLevel().intValue() >= this.pushLevel.intValue()) {
                this.push();
            }
            return;
        }
    }

    public void push() {
        synchronized (this) {
            int n = 0;
            do {
                if (n >= this.count) break;
                int n2 = this.start;
                int n3 = this.buffer.length;
                LogRecord logRecord = this.buffer[(n2 + n) % n3];
                this.target.publish(logRecord);
                ++n;
            } while (true);
            this.start = 0;
            this.count = 0;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setPushLevel(Level serializable) throws SecurityException {
        synchronized (this) {
            Throwable throwable2;
            if (serializable != null) {
                try {
                    this.checkPermission();
                    this.pushLevel = serializable;
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

