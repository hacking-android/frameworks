/*
 * Decompiled with CFR 0.145.
 */
package java.util.logging;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public abstract class Formatter {
    protected Formatter() {
    }

    public abstract String format(LogRecord var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String formatMessage(LogRecord object) {
        synchronized (this) {
            String string;
            block8 : {
                block9 : {
                    string = ((LogRecord)object).getMessage();
                    ResourceBundle resourceBundle = ((LogRecord)object).getResourceBundle();
                    if (resourceBundle != null) {
                        try {
                            string = resourceBundle.getString(((LogRecord)object).getMessage());
                        }
                        catch (MissingResourceException missingResourceException) {
                            string = ((LogRecord)object).getMessage();
                        }
                    }
                    try {
                        object = ((LogRecord)object).getParameters();
                        if (object == null) return string;
                        if (((Object)object).length == 0) break block8;
                        if (string.indexOf("{0") >= 0 || string.indexOf("{1") >= 0 || string.indexOf("{2") >= 0) break block9;
                        int n = string.indexOf("{3");
                        if (n < 0) return string;
                    }
                    catch (Exception exception) {
                        return string;
                    }
                }
                return MessageFormat.format(string, (Object[])object);
            }
            return string;
        }
    }

    public String getHead(Handler handler) {
        return "";
    }

    public String getTail(Handler handler) {
        return "";
    }
}

