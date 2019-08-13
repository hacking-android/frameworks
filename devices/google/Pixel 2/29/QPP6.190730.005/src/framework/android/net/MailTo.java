/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.ParseException;
import android.net.Uri;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MailTo {
    private static final String BODY = "body";
    private static final String CC = "cc";
    public static final String MAILTO_SCHEME = "mailto:";
    private static final String SUBJECT = "subject";
    private static final String TO = "to";
    private HashMap<String, String> mHeaders = new HashMap();

    private MailTo() {
    }

    public static boolean isMailTo(String string2) {
        return string2 != null && string2.startsWith(MAILTO_SCHEME);
    }

    public static MailTo parse(String object) throws ParseException {
        if (object != null) {
            if (MailTo.isMailTo((String)object)) {
                Object object2;
                Object object3 = Uri.parse(object.substring(MAILTO_SCHEME.length()));
                MailTo mailTo = new MailTo();
                object = ((Uri)object3).getQuery();
                if (object != null) {
                    object2 = object.split("&");
                    int n = ((String[])object2).length;
                    for (int i = 0; i < n; ++i) {
                        object = object2[i].split("=");
                        if (((String[])object).length == 0) continue;
                        HashMap<String, String> hashMap = mailTo.mHeaders;
                        String string2 = Uri.decode(object[0]).toLowerCase(Locale.ROOT);
                        object = ((String[])object).length > 1 ? Uri.decode(object[1]) : null;
                        hashMap.put(string2, (String)object);
                    }
                }
                if ((object3 = ((Uri)object3).getPath()) != null) {
                    object2 = mailTo.getTo();
                    object = object3;
                    if (object2 != null) {
                        object = new StringBuilder();
                        object.append((String)object3);
                        object.append(", ");
                        object.append((String)object2);
                        object = object.toString();
                    }
                    mailTo.mHeaders.put(TO, (String)object);
                }
                return mailTo;
            }
            throw new ParseException("Not a mailto scheme");
        }
        throw new NullPointerException();
    }

    public String getBody() {
        return this.mHeaders.get(BODY);
    }

    public String getCc() {
        return this.mHeaders.get(CC);
    }

    public Map<String, String> getHeaders() {
        return this.mHeaders;
    }

    public String getSubject() {
        return this.mHeaders.get(SUBJECT);
    }

    public String getTo() {
        return this.mHeaders.get(TO);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(MAILTO_SCHEME);
        stringBuilder.append('?');
        for (Map.Entry<String, String> entry : this.mHeaders.entrySet()) {
            stringBuilder.append(Uri.encode(entry.getKey()));
            stringBuilder.append('=');
            stringBuilder.append(Uri.encode(entry.getValue()));
            stringBuilder.append('&');
        }
        return stringBuilder.toString();
    }
}

