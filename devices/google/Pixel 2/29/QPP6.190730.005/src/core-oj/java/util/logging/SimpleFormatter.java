/*
 * Decompiled with CFR 0.145.
 */
package java.util.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import sun.util.logging.LoggingSupport;

public class SimpleFormatter
extends Formatter {
    private static final String format = LoggingSupport.getSimpleFormat();
    private final Date dat = new Date();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String format(LogRecord object) {
        synchronized (this) {
            Object object2;
            CharSequence charSequence;
            this.dat.setTime(((LogRecord)object).getMillis());
            if (((LogRecord)object).getSourceClassName() != null) {
                object2 = ((LogRecord)object).getSourceClassName();
                charSequence = object2;
                if (((LogRecord)object).getSourceMethodName() != null) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append((String)object2);
                    ((StringBuilder)charSequence).append(" ");
                    ((StringBuilder)charSequence).append(((LogRecord)object).getSourceMethodName());
                    charSequence = ((StringBuilder)charSequence).toString();
                }
            } else {
                charSequence = ((LogRecord)object).getLoggerName();
            }
            String string = this.formatMessage((LogRecord)object);
            object2 = "";
            if (((LogRecord)object).getThrown() != null) {
                object2 = new StringWriter();
                PrintWriter printWriter = new PrintWriter((Writer)object2);
                printWriter.println();
                ((LogRecord)object).getThrown().printStackTrace(printWriter);
                printWriter.close();
                object2 = ((StringWriter)object2).toString();
            }
            return String.format(format, this.dat, charSequence, ((LogRecord)object).getLoggerName(), ((LogRecord)object).getLevel().getLocalizedLevelName(), string, object2);
        }
    }
}

