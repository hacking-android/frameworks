/*
 * Decompiled with CFR 0.145.
 */
package java.util.logging;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public class ConsoleHandler
extends StreamHandler {
    public ConsoleHandler() {
        this.sealed = false;
        this.configure();
        this.setOutputStream(System.err);
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

    @Override
    public void close() {
        this.flush();
    }

    @Override
    public void publish(LogRecord logRecord) {
        super.publish(logRecord);
        this.flush();
    }
}

