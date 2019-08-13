/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.res;

import java.text.MessageFormat;
import java.util.ListResourceBundle;
import java.util.Locale;
import org.apache.xml.res.XMLErrorResources;

public class XMLMessages {
    protected static final String BAD_CODE = "BAD_CODE";
    protected static final String FORMAT_FAILED = "FORMAT_FAILED";
    private static ListResourceBundle XMLBundle = new XMLErrorResources();
    protected Locale fLocale = Locale.getDefault();

    public static final String createMsg(ListResourceBundle object, String string, Object[] object2) {
        boolean bl = false;
        String string2 = null;
        if (string != null) {
            string2 = object.getString(string);
        }
        string = string2;
        if (string2 == null) {
            string = object.getString(BAD_CODE);
            bl = true;
        }
        if (object2 != null) {
            int n = ((Object[])object2).length;
            for (int i = 0; i < n; ++i) {
                if (object2[i] != null) continue;
                object2[i] = "";
            }
            try {
                object = object2 = MessageFormat.format(string, object2);
            }
            catch (Exception exception) {
                object = object.getString(FORMAT_FAILED);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)object);
                stringBuilder.append(" ");
                stringBuilder.append(string);
                object = stringBuilder.toString();
            }
        } else {
            object = string;
        }
        if (!bl) {
            return object;
        }
        throw new RuntimeException((String)object);
    }

    public static final String createXMLMessage(String string, Object[] arrobject) {
        return XMLMessages.createMsg(XMLBundle, string, arrobject);
    }

    public Locale getLocale() {
        return this.fLocale;
    }

    public void setLocale(Locale locale) {
        this.fLocale = locale;
    }
}

