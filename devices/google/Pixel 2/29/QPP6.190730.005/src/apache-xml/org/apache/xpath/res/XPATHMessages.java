/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.res;

import java.text.MessageFormat;
import java.util.ListResourceBundle;
import org.apache.xml.res.XMLMessages;
import org.apache.xpath.res.XPATHErrorResources;

public class XPATHMessages
extends XMLMessages {
    private static ListResourceBundle XPATHBundle = new XPATHErrorResources();
    private static final String XPATH_ERROR_RESOURCES = "org.apache.xpath.res.XPATHErrorResources";

    public static final String createXPATHMessage(String string, Object[] arrobject) {
        return XPATHMessages.createXPATHMsg(XPATHBundle, string, arrobject);
    }

    public static final String createXPATHMsg(ListResourceBundle object, String string, Object[] object2) {
        boolean bl = false;
        String string2 = null;
        if (string != null) {
            string2 = object.getString(string);
        }
        string = string2;
        if (string2 == null) {
            string = object.getString("BAD_CODE");
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
                object = object.getString("FORMAT_FAILED");
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

    public static final String createXPATHWarning(String string, Object[] arrobject) {
        return XPATHMessages.createXPATHMsg(XPATHBundle, string, arrobject);
    }
}

