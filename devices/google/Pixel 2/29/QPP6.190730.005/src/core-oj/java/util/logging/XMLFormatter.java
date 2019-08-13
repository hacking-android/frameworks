/*
 * Decompiled with CFR 0.145.
 */
package java.util.logging;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

public class XMLFormatter
extends Formatter {
    private LogManager manager = LogManager.getLogManager();

    private void a2(StringBuilder stringBuilder, int n) {
        if (n < 10) {
            stringBuilder.append('0');
        }
        stringBuilder.append(n);
    }

    private void appendISO8601(StringBuilder stringBuilder, long l) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(l);
        stringBuilder.append(gregorianCalendar.get(1));
        stringBuilder.append('-');
        this.a2(stringBuilder, gregorianCalendar.get(2) + 1);
        stringBuilder.append('-');
        this.a2(stringBuilder, gregorianCalendar.get(5));
        stringBuilder.append('T');
        this.a2(stringBuilder, gregorianCalendar.get(11));
        stringBuilder.append(':');
        this.a2(stringBuilder, gregorianCalendar.get(12));
        stringBuilder.append(':');
        this.a2(stringBuilder, gregorianCalendar.get(13));
    }

    private void escape(StringBuilder stringBuilder, String string) {
        String string2 = string;
        if (string == null) {
            string2 = "<null>";
        }
        for (int i = 0; i < string2.length(); ++i) {
            char c = string2.charAt(i);
            if (c == '<') {
                stringBuilder.append("&lt;");
                continue;
            }
            if (c == '>') {
                stringBuilder.append("&gt;");
                continue;
            }
            if (c == '&') {
                stringBuilder.append("&amp;");
                continue;
            }
            stringBuilder.append(c);
        }
    }

    @Override
    public String format(LogRecord serializable) {
        int n;
        StringBuilder stringBuilder = new StringBuilder(500);
        stringBuilder.append("<record>\n");
        stringBuilder.append("  <date>");
        this.appendISO8601(stringBuilder, ((LogRecord)serializable).getMillis());
        stringBuilder.append("</date>\n");
        stringBuilder.append("  <millis>");
        stringBuilder.append(((LogRecord)serializable).getMillis());
        stringBuilder.append("</millis>\n");
        stringBuilder.append("  <sequence>");
        stringBuilder.append(((LogRecord)serializable).getSequenceNumber());
        stringBuilder.append("</sequence>\n");
        Object object = ((LogRecord)serializable).getLoggerName();
        if (object != null) {
            stringBuilder.append("  <logger>");
            this.escape(stringBuilder, (String)object);
            stringBuilder.append("</logger>\n");
        }
        stringBuilder.append("  <level>");
        this.escape(stringBuilder, ((LogRecord)serializable).getLevel().toString());
        stringBuilder.append("</level>\n");
        if (((LogRecord)serializable).getSourceClassName() != null) {
            stringBuilder.append("  <class>");
            this.escape(stringBuilder, ((LogRecord)serializable).getSourceClassName());
            stringBuilder.append("</class>\n");
        }
        if (((LogRecord)serializable).getSourceMethodName() != null) {
            stringBuilder.append("  <method>");
            this.escape(stringBuilder, ((LogRecord)serializable).getSourceMethodName());
            stringBuilder.append("</method>\n");
        }
        stringBuilder.append("  <thread>");
        stringBuilder.append(((LogRecord)serializable).getThreadID());
        stringBuilder.append("</thread>\n");
        if (((LogRecord)serializable).getMessage() != null) {
            object = this.formatMessage((LogRecord)serializable);
            stringBuilder.append("  <message>");
            this.escape(stringBuilder, (String)object);
            stringBuilder.append("</message>");
            stringBuilder.append("\n");
        } else {
            stringBuilder.append("<message/>");
            stringBuilder.append("\n");
        }
        object = ((LogRecord)serializable).getResourceBundle();
        if (object != null) {
            try {
                if (((ResourceBundle)object).getString(((LogRecord)serializable).getMessage()) != null) {
                    stringBuilder.append("  <key>");
                    this.escape(stringBuilder, ((LogRecord)serializable).getMessage());
                    stringBuilder.append("</key>\n");
                    stringBuilder.append("  <catalog>");
                    this.escape(stringBuilder, ((LogRecord)serializable).getResourceBundleName());
                    stringBuilder.append("</catalog>\n");
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if ((object = ((LogRecord)serializable).getParameters()) != null && ((Object[])object).length != 0 && ((LogRecord)serializable).getMessage().indexOf("{") == -1) {
            for (n = 0; n < ((Object[])object).length; ++n) {
                stringBuilder.append("  <param>");
                try {
                    this.escape(stringBuilder, object[n].toString());
                }
                catch (Exception exception) {
                    stringBuilder.append("???");
                }
                stringBuilder.append("</param>\n");
            }
        }
        if (((LogRecord)serializable).getThrown() != null) {
            serializable = ((LogRecord)serializable).getThrown();
            stringBuilder.append("  <exception>\n");
            stringBuilder.append("    <message>");
            this.escape(stringBuilder, ((Throwable)serializable).toString());
            stringBuilder.append("</message>\n");
            serializable = ((Throwable)serializable).getStackTrace();
            for (n = 0; n < ((Serializable)serializable).length; ++n) {
                object = serializable[n];
                stringBuilder.append("    <frame>\n");
                stringBuilder.append("      <class>");
                this.escape(stringBuilder, ((StackTraceElement)object).getClassName());
                stringBuilder.append("</class>\n");
                stringBuilder.append("      <method>");
                this.escape(stringBuilder, ((StackTraceElement)object).getMethodName());
                stringBuilder.append("</method>\n");
                if (((StackTraceElement)object).getLineNumber() >= 0) {
                    stringBuilder.append("      <line>");
                    stringBuilder.append(((StackTraceElement)object).getLineNumber());
                    stringBuilder.append("</line>\n");
                }
                stringBuilder.append("    </frame>\n");
            }
            stringBuilder.append("  </exception>\n");
        }
        stringBuilder.append("</record>\n");
        return stringBuilder.toString();
    }

    @Override
    public String getHead(Handler object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<?xml version=\"1.0\"");
        String string = object != null ? ((Handler)object).getEncoding() : null;
        object = string;
        if (string == null) {
            object = Charset.defaultCharset().name();
        }
        try {
            string = Charset.forName((String)object).name();
            object = string;
        }
        catch (Exception exception) {
            // empty catch block
        }
        stringBuilder.append(" encoding=\"");
        stringBuilder.append((String)object);
        stringBuilder.append("\"");
        stringBuilder.append(" standalone=\"no\"?>\n");
        stringBuilder.append("<!DOCTYPE log SYSTEM \"logger.dtd\">\n");
        stringBuilder.append("<log>\n");
        return stringBuilder.toString();
    }

    @Override
    public String getTail(Handler handler) {
        return "</log>\n";
    }
}

