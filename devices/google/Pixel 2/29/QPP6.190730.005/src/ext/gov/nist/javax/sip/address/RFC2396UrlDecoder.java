/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.address;

import java.io.UnsupportedEncodingException;

public class RFC2396UrlDecoder {
    public static String decode(String string) {
        StringBuffer stringBuffer = new StringBuffer(string.length());
        byte[] arrby = new byte[string.length() / 3];
        int n = 0;
        int n2 = string.length();
        do {
            int n3 = 0;
            if (n >= n2) break;
            if (string.charAt(n) == '%') {
                while (n < n2 && string.charAt(n) == '%') {
                    if (n + 2 < n2) {
                        try {
                            arrby[n3] = (byte)Integer.parseInt(string.substring(n + 1, n + 3), 16);
                            ++n3;
                            n += 3;
                            continue;
                        }
                        catch (NumberFormatException numberFormatException) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Illegal hex characters in pattern %");
                            stringBuilder.append(string.substring(n + 1, n + 3));
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                    }
                    throw new IllegalArgumentException("% character should be followed by 2 hexadecimal characters.");
                }
                try {
                    String string2 = new String(arrby, 0, n3, "UTF-8");
                    stringBuffer.append(string2);
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    throw new RuntimeException("Problem in decodePath: UTF-8 encoding not supported.");
                }
            }
            stringBuffer.append(string.charAt(n));
            ++n;
        } while (true);
        return stringBuffer.toString();
    }
}

