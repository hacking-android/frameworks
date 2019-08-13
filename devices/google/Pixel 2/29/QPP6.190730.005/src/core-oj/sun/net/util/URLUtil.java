/*
 * Decompiled with CFR 0.145.
 */
package sun.net.util;

import java.net.URL;

public class URLUtil {
    public static String urlNoFragString(URL object) {
        StringBuilder stringBuilder = new StringBuilder();
        String string = ((URL)object).getProtocol();
        if (string != null) {
            stringBuilder.append(string.toLowerCase());
            stringBuilder.append("://");
        }
        if ((string = ((URL)object).getHost()) != null) {
            int n;
            stringBuilder.append(string.toLowerCase());
            int n2 = n = ((URL)object).getPort();
            if (n == -1) {
                n2 = ((URL)object).getDefaultPort();
            }
            if (n2 != -1) {
                stringBuilder.append(":");
                stringBuilder.append(n2);
            }
        }
        if ((object = ((URL)object).getFile()) != null) {
            stringBuilder.append((String)object);
        }
        return stringBuilder.toString();
    }
}

