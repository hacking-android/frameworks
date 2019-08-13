/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.UnsupportedAppUsage;
import android.net.ParseException;
import android.net.Uri;
import android.net.WebAddress;
import android.webkit.MimeTypeMap;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class URLUtil {
    static final String ASSET_BASE = "file:///android_asset/";
    static final String CONTENT_BASE = "content:";
    private static final Pattern CONTENT_DISPOSITION_PATTERN = Pattern.compile("attachment;\\s*filename\\s*=\\s*(\"?)([^\"]*)\\1\\s*$", 2);
    static final String FILE_BASE = "file:";
    private static final String LOGTAG = "webkit";
    static final String PROXY_BASE = "file:///cookieless_proxy/";
    static final String RESOURCE_BASE = "file:///android_res/";
    private static final boolean TRACE = false;

    public static String composeSearchUrl(String string2, String string3, String string4) {
        int n = string3.indexOf(string4);
        if (n < 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string3.substring(0, n));
        try {
            stringBuilder.append(URLEncoder.encode(string2, "utf-8"));
            stringBuilder.append(string3.substring(string4.length() + n));
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return null;
        }
        return stringBuilder.toString();
    }

    public static byte[] decode(byte[] arrby) throws IllegalArgumentException {
        if (arrby.length == 0) {
            return new byte[0];
        }
        byte[] arrby2 = new byte[arrby.length];
        int n = 0;
        int n2 = 0;
        while (n2 < arrby.length) {
            byte by = arrby[n2];
            int n3 = n2;
            byte by2 = by;
            if (by == 37) {
                if (arrby.length - n2 > 2) {
                    by2 = (byte)(URLUtil.parseHex(arrby[n2 + 1]) * 16 + URLUtil.parseHex(arrby[n2 + 2]));
                    n3 = n2 + 2;
                } else {
                    throw new IllegalArgumentException("Invalid format");
                }
            }
            arrby2[n] = by2;
            n2 = n3 + 1;
            ++n;
        }
        arrby = new byte[n];
        System.arraycopy(arrby2, 0, arrby, 0, n);
        return arrby;
    }

    public static final String guessFileName(String charSequence, String charSequence2, String string2) {
        int n;
        String string3 = null;
        String string4 = null;
        Object var5_5 = null;
        String string5 = string3;
        if (!false) {
            string5 = string3;
            if (charSequence2 != null) {
                charSequence2 = URLUtil.parseContentDisposition((String)charSequence2);
                string5 = charSequence2;
                if (charSequence2 != null) {
                    n = ((String)charSequence2).lastIndexOf(47) + 1;
                    string5 = charSequence2;
                    if (n > 0) {
                        string5 = ((String)charSequence2).substring(n);
                    }
                }
            }
        }
        charSequence2 = string5;
        if (string5 == null) {
            string3 = Uri.decode((String)charSequence);
            charSequence2 = string5;
            if (string3 != null) {
                n = string3.indexOf(63);
                charSequence = string3;
                if (n > 0) {
                    charSequence = string3.substring(0, n);
                }
                charSequence2 = string5;
                if (!((String)charSequence).endsWith("/")) {
                    n = ((String)charSequence).lastIndexOf(47) + 1;
                    charSequence2 = string5;
                    if (n > 0) {
                        charSequence2 = ((String)charSequence).substring(n);
                    }
                }
            }
        }
        string5 = charSequence2;
        if (charSequence2 == null) {
            string5 = "downloadfile";
        }
        if ((n = string5.indexOf(46)) < 0) {
            charSequence2 = var5_5;
            if (string2 != null) {
                charSequence2 = charSequence = MimeTypeMap.getSingleton().getExtensionFromMimeType(string2);
                if (charSequence != null) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append(".");
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    charSequence2 = ((StringBuilder)charSequence2).toString();
                }
            }
            string4 = string5;
            charSequence = charSequence2;
            if (charSequence2 == null) {
                if (string2 != null && string2.toLowerCase(Locale.ROOT).startsWith("text/")) {
                    if (string2.equalsIgnoreCase("text/html")) {
                        charSequence = ".html";
                        string4 = string5;
                    } else {
                        charSequence = ".txt";
                        string4 = string5;
                    }
                } else {
                    charSequence = ".bin";
                    string4 = string5;
                }
            }
        } else {
            charSequence = string4;
            if (string2 != null) {
                int n2 = string5.lastIndexOf(46);
                charSequence2 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(string5.substring(n2 + 1));
                charSequence = string4;
                if (charSequence2 != null) {
                    charSequence = string4;
                    if (!((String)charSequence2).equalsIgnoreCase(string2)) {
                        charSequence = charSequence2 = MimeTypeMap.getSingleton().getExtensionFromMimeType(string2);
                        if (charSequence2 != null) {
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append(".");
                            ((StringBuilder)charSequence).append((String)charSequence2);
                            charSequence = ((StringBuilder)charSequence).toString();
                        }
                    }
                }
            }
            charSequence2 = charSequence;
            if (charSequence == null) {
                charSequence2 = string5.substring(n);
            }
            string4 = string5.substring(0, n);
            charSequence = charSequence2;
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append(string4);
        ((StringBuilder)charSequence2).append((String)charSequence);
        return ((StringBuilder)charSequence2).toString();
    }

    public static String guessUrl(String charSequence) {
        if (((String)charSequence).length() == 0) {
            return charSequence;
        }
        if (((String)charSequence).startsWith("about:")) {
            return charSequence;
        }
        if (((String)charSequence).startsWith("data:")) {
            return charSequence;
        }
        if (((String)charSequence).startsWith(FILE_BASE)) {
            return charSequence;
        }
        if (((String)charSequence).startsWith("javascript:")) {
            return charSequence;
        }
        Object object = charSequence;
        if (((String)charSequence).endsWith(".")) {
            object = ((String)charSequence).substring(0, ((String)charSequence).length() - 1);
        }
        try {
            object = new WebAddress((String)object);
        }
        catch (ParseException parseException) {
            return charSequence;
        }
        if (((WebAddress)object).getHost().indexOf(46) == -1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("www.");
            ((StringBuilder)charSequence).append(((WebAddress)object).getHost());
            ((StringBuilder)charSequence).append(".com");
            ((WebAddress)object).setHost(((StringBuilder)charSequence).toString());
        }
        return ((WebAddress)object).toString();
    }

    public static boolean isAboutUrl(String string2) {
        boolean bl = string2 != null && string2.startsWith("about:");
        return bl;
    }

    public static boolean isAssetUrl(String string2) {
        boolean bl = string2 != null && string2.startsWith(ASSET_BASE);
        return bl;
    }

    public static boolean isContentUrl(String string2) {
        boolean bl = string2 != null && string2.startsWith(CONTENT_BASE);
        return bl;
    }

    @Deprecated
    public static boolean isCookielessProxyUrl(String string2) {
        boolean bl = string2 != null && string2.startsWith(PROXY_BASE);
        return bl;
    }

    public static boolean isDataUrl(String string2) {
        boolean bl = string2 != null && string2.startsWith("data:");
        return bl;
    }

    public static boolean isFileUrl(String string2) {
        boolean bl = string2 != null && string2.startsWith(FILE_BASE) && !string2.startsWith(ASSET_BASE) && !string2.startsWith(PROXY_BASE);
        return bl;
    }

    public static boolean isHttpUrl(String string2) {
        boolean bl;
        block0 : {
            bl = false;
            if (string2 == null || string2.length() <= 6 || !string2.substring(0, 7).equalsIgnoreCase("http://")) break block0;
            bl = true;
        }
        return bl;
    }

    public static boolean isHttpsUrl(String string2) {
        boolean bl;
        block0 : {
            bl = false;
            if (string2 == null || string2.length() <= 7 || !string2.substring(0, 8).equalsIgnoreCase("https://")) break block0;
            bl = true;
        }
        return bl;
    }

    public static boolean isJavaScriptUrl(String string2) {
        boolean bl = string2 != null && string2.startsWith("javascript:");
        return bl;
    }

    public static boolean isNetworkUrl(String string2) {
        boolean bl = false;
        if (string2 != null && string2.length() != 0) {
            if (URLUtil.isHttpUrl(string2) || URLUtil.isHttpsUrl(string2)) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    @UnsupportedAppUsage
    public static boolean isResourceUrl(String string2) {
        boolean bl = string2 != null && string2.startsWith(RESOURCE_BASE);
        return bl;
    }

    public static boolean isValidUrl(String string2) {
        boolean bl = false;
        if (string2 != null && string2.length() != 0) {
            if (URLUtil.isAssetUrl(string2) || URLUtil.isResourceUrl(string2) || URLUtil.isFileUrl(string2) || URLUtil.isAboutUrl(string2) || URLUtil.isHttpUrl(string2) || URLUtil.isHttpsUrl(string2) || URLUtil.isJavaScriptUrl(string2) || URLUtil.isContentUrl(string2)) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    @UnsupportedAppUsage
    static String parseContentDisposition(String object) {
        try {
            object = CONTENT_DISPOSITION_PATTERN.matcher((CharSequence)object);
            if (((Matcher)object).find()) {
                object = ((Matcher)object).group(2);
                return object;
            }
        }
        catch (IllegalStateException illegalStateException) {
            // empty catch block
        }
        return null;
    }

    private static int parseHex(byte by) {
        if (by >= 48 && by <= 57) {
            return by - 48;
        }
        if (by >= 65 && by <= 70) {
            return by - 65 + 10;
        }
        if (by >= 97 && by <= 102) {
            return by - 97 + 10;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid hex char '");
        stringBuilder.append(by);
        stringBuilder.append("'");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static String stripAnchor(String string2) {
        int n = string2.indexOf(35);
        if (n != -1) {
            return string2.substring(0, n);
        }
        return string2;
    }

    @UnsupportedAppUsage
    static boolean verifyURLEncoding(String string2) {
        int n = string2.length();
        if (n == 0) {
            return false;
        }
        for (int i = string2.indexOf((int)37); i >= 0 && i < n; ++i) {
            if (i < n - 2) {
                ++i;
                try {
                    URLUtil.parseHex((byte)string2.charAt(i));
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    return false;
                }
                URLUtil.parseHex((byte)string2.charAt(i));
                i = string2.indexOf(37, i + 1);
                continue;
            }
            return false;
        }
        return true;
    }
}

