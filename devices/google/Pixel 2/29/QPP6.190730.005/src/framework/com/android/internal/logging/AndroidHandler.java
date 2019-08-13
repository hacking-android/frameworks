/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.DalvikLogHandler
 *  dalvik.system.DalvikLogging
 */
package com.android.internal.logging;

import android.util.Log;
import com.android.internal.util.FastPrintWriter;
import dalvik.system.DalvikLogHandler;
import dalvik.system.DalvikLogging;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class AndroidHandler
extends Handler
implements DalvikLogHandler {
    private static final Formatter THE_FORMATTER = new Formatter(){

        @Override
        public String format(LogRecord logRecord) {
            Throwable throwable = logRecord.getThrown();
            if (throwable != null) {
                StringWriter stringWriter = new StringWriter();
                FastPrintWriter fastPrintWriter = new FastPrintWriter(stringWriter, false, 256);
                stringWriter.write(logRecord.getMessage());
                stringWriter.write("\n");
                throwable.printStackTrace(fastPrintWriter);
                ((PrintWriter)fastPrintWriter).flush();
                return stringWriter.toString();
            }
            return logRecord.getMessage();
        }
    };

    public AndroidHandler() {
        this.setFormatter(THE_FORMATTER);
    }

    static int getAndroidLevel(Level level) {
        int n = level.intValue();
        if (n >= 1000) {
            return 6;
        }
        if (n >= 900) {
            return 5;
        }
        if (n >= 800) {
            return 4;
        }
        return 3;
    }

    @Override
    public void close() {
    }

    @Override
    public void flush() {
    }

    @Override
    public void publish(LogRecord logRecord) {
        int n = AndroidHandler.getAndroidLevel(logRecord.getLevel());
        String string2 = DalvikLogging.loggerNameToTag((String)logRecord.getLoggerName());
        if (!Log.isLoggable(string2, n)) {
            return;
        }
        try {
            Log.println(n, string2, this.getFormatter().format(logRecord));
        }
        catch (RuntimeException runtimeException) {
            Log.e("AndroidHandler", "Error logging message.", runtimeException);
        }
    }

    public void publish(Logger logger, String string2, Level level, String string3) {
        int n = AndroidHandler.getAndroidLevel(level);
        if (!Log.isLoggable(string2, n)) {
            return;
        }
        try {
            Log.println(n, string2, string3);
        }
        catch (RuntimeException runtimeException) {
            Log.e("AndroidHandler", "Error logging message.", runtimeException);
        }
    }

}

