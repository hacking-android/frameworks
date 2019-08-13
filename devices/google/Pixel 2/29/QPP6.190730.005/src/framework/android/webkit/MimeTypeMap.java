/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.net.MimeUtils
 */
package android.webkit;

import android.text.TextUtils;
import android.webkit.URLUtil;
import java.util.regex.Pattern;
import libcore.net.MimeUtils;

public class MimeTypeMap {
    private static final MimeTypeMap sMimeTypeMap = new MimeTypeMap();

    private MimeTypeMap() {
    }

    public static String getFileExtensionFromUrl(String string2) {
        if (!TextUtils.isEmpty(string2)) {
            int n = string2.lastIndexOf(35);
            String string3 = string2;
            if (n > 0) {
                string3 = string2.substring(0, n);
            }
            n = string3.lastIndexOf(63);
            string2 = string3;
            if (n > 0) {
                string2 = string3.substring(0, n);
            }
            if ((n = string2.lastIndexOf(47)) >= 0) {
                string2 = string2.substring(n + 1);
            }
            if (!string2.isEmpty() && Pattern.matches("[a-zA-Z_0-9\\.\\-\\(\\)\\%]+", string2) && (n = string2.lastIndexOf(46)) >= 0) {
                return string2.substring(n + 1);
            }
        }
        return "";
    }

    public static MimeTypeMap getSingleton() {
        return sMimeTypeMap;
    }

    private static String mimeTypeFromExtension(String string2) {
        return MimeUtils.guessMimeTypeFromExtension((String)string2);
    }

    public String getExtensionFromMimeType(String string2) {
        return MimeUtils.guessExtensionFromMimeType((String)string2);
    }

    public String getMimeTypeFromExtension(String string2) {
        return MimeUtils.guessMimeTypeFromExtension((String)string2);
    }

    public boolean hasExtension(String string2) {
        return MimeUtils.hasExtension((String)string2);
    }

    public boolean hasMimeType(String string2) {
        return MimeUtils.hasMimeType((String)string2);
    }

    String remapGenericMimeType(String string2, String string3, String string4) {
        if (!"text/plain".equals(string2) && !"application/octet-stream".equals(string2)) {
            if ("text/vnd.wap.wml".equals(string2)) {
                string3 = "text/plain";
            } else {
                string3 = string2;
                if ("application/vnd.wap.xhtml+xml".equals(string2)) {
                    string3 = "application/xhtml+xml";
                }
            }
        } else {
            String string5 = null;
            if (string4 != null) {
                string5 = URLUtil.parseContentDisposition(string4);
            }
            if (string5 != null) {
                string3 = string5;
            }
            if ((string3 = this.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(string3))) != null) {
                string2 = string3;
            }
            string3 = string2;
        }
        return string3;
    }
}

