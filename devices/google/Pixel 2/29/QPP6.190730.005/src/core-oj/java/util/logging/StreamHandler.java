/*
 * Decompiled with CFR 0.145.
 */
package java.util.logging;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class StreamHandler
extends Handler {
    private boolean doneHeader;
    private OutputStream output;
    private volatile Writer writer;

    public StreamHandler() {
        this.sealed = false;
        this.configure();
        this.sealed = true;
    }

    public StreamHandler(OutputStream outputStream, Formatter formatter) {
        this.sealed = false;
        this.configure();
        this.setFormatter(formatter);
        this.setOutputStream(outputStream);
        this.sealed = true;
    }

    private void configure() {
        LogManager logManager = LogManager.getLogManager();
        String string = this.getClass().getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".level");
        this.setLevel(logManager.getLevelProperty(stringBuilder.toString(), Level.INFO));
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".filter");
        this.setFilter(logManager.getFilterProperty(stringBuilder.toString(), null));
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".formatter");
        this.setFormatter(logManager.getFormatterProperty(stringBuilder.toString(), new SimpleFormatter()));
        try {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(".encoding");
            this.setEncoding(logManager.getStringProperty(stringBuilder.toString(), null));
        }
        catch (Exception exception) {
            try {
                this.setEncoding(null);
            }
            catch (Exception exception2) {
                // empty catch block
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void flushAndClose() throws SecurityException {
        synchronized (this) {
            this.checkPermission();
            Writer writer = this.writer;
            if (writer == null) return;
            if (!this.doneHeader) {
                this.writer.write(this.getFormatter().getHead(this));
                this.doneHeader = true;
            }
            this.writer.write(this.getFormatter().getTail(this));
            this.writer.flush();
            this.writer.close();
            {
                this.writer = null;
                this.output = null;
                return;
            }
        }
    }

    @Override
    public void close() throws SecurityException {
        synchronized (this) {
            this.flushAndClose();
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void flush() {
        synchronized (this) {
            Writer writer = this.writer;
            if (writer == null) return;
            this.writer.flush();
            return;
        }
    }

    @Override
    public boolean isLoggable(LogRecord logRecord) {
        if (this.writer != null && logRecord != null) {
            return super.isLoggable(logRecord);
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void publish(LogRecord object) {
        synchronized (this) {
            boolean bl = this.isLoggable((LogRecord)object);
            if (!bl) {
                return;
            }
            try {
                object = this.getFormatter().format((LogRecord)object);
            }
            catch (Exception exception) {
                this.reportError(null, exception, 5);
                return;
            }
            try {
                if (!this.doneHeader) {
                    this.writer.write(this.getFormatter().getHead(this));
                    this.doneHeader = true;
                }
                this.writer.write((String)object);
            }
            catch (Exception exception) {
                this.reportError(null, exception, 1);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setEncoding(String object) throws SecurityException, UnsupportedEncodingException {
        synchronized (this) {
            super.setEncoding((String)object);
            OutputStream outputStream = this.output;
            if (outputStream == null) {
                return;
            }
            this.flush();
            if (object == null) {
                this.writer = object = new OutputStreamWriter(this.output);
            } else {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.output, (String)object);
                this.writer = outputStreamWriter;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    protected void setOutputStream(OutputStream object) throws SecurityException {
        // MONITORENTER : this
        if (object != null) {
            this.flushAndClose();
            this.output = object;
            this.doneHeader = false;
            object = this.getEncoding();
            if (object == null) {
                this.writer = object = new OutputStreamWriter(this.output);
                return;
            }
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.output, (String)object);
                this.writer = outputStreamWriter;
                // MONITOREXIT : this
                return;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected exception ");
                stringBuilder.append(unsupportedEncodingException);
                Error error = new Error(stringBuilder.toString());
                throw error;
            }
        }
        object = new NullPointerException();
        throw object;
    }
}

