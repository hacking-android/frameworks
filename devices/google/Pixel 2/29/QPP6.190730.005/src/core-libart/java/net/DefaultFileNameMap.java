/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.net.FileNameMap;
import libcore.net.MimeUtils;

class DefaultFileNameMap
implements FileNameMap {
    DefaultFileNameMap() {
    }

    @Override
    public String getContentTypeFor(String string) {
        int n;
        if (string.endsWith("/")) {
            return MimeUtils.guessMimeTypeFromExtension("html");
        }
        int n2 = n = string.lastIndexOf(35);
        if (n < 0) {
            n2 = string.length();
        }
        n = string.lastIndexOf(46) + 1;
        String string2 = "";
        if (n > string.lastIndexOf(47)) {
            string2 = string.substring(n, n2);
        }
        return MimeUtils.guessMimeTypeFromExtension(string2);
    }
}

