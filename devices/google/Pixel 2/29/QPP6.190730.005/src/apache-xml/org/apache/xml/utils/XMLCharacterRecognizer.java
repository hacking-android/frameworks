/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

public class XMLCharacterRecognizer {
    public static boolean isWhiteSpace(char c) {
        boolean bl = c == ' ' || c == '\t' || c == '\r' || c == '\n';
        return bl;
    }

    public static boolean isWhiteSpace(String string) {
        if (string != null) {
            int n = string.length();
            for (int i = 0; i < n; ++i) {
                if (XMLCharacterRecognizer.isWhiteSpace(string.charAt(i))) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean isWhiteSpace(StringBuffer stringBuffer) {
        int n = stringBuffer.length();
        for (int i = 0; i < n; ++i) {
            if (XMLCharacterRecognizer.isWhiteSpace(stringBuffer.charAt(i))) continue;
            return false;
        }
        return true;
    }

    public static boolean isWhiteSpace(char[] arrc, int n, int n2) {
        for (int i = n; i < n + n2; ++i) {
            if (XMLCharacterRecognizer.isWhiteSpace(arrc[i])) continue;
            return false;
        }
        return true;
    }
}

