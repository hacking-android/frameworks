/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.locale;

import android.icu.impl.Utility;

public final class AsciiUtil {
    public static int caseIgnoreCompare(String string, String string2) {
        if (Utility.sameObjects(string, string2)) {
            return 0;
        }
        return AsciiUtil.toLowerString(string).compareTo(AsciiUtil.toLowerString(string2));
    }

    public static boolean caseIgnoreMatch(String string, String string2) {
        char c;
        char c2;
        int n;
        boolean bl = Utility.sameObjects(string, string2);
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        int n2 = string.length();
        if (n2 != string2.length()) {
            return false;
        }
        for (n = 0; n < n2 && ((c = string.charAt(n)) == (c2 = string2.charAt(n)) || AsciiUtil.toLower(c) == AsciiUtil.toLower(c2)); ++n) {
        }
        if (n != n2) {
            bl2 = false;
        }
        return bl2;
    }

    public static boolean isAlpha(char c) {
        boolean bl = c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z';
        return bl;
    }

    public static boolean isAlphaNumeric(char c) {
        boolean bl = AsciiUtil.isAlpha(c) || AsciiUtil.isNumeric(c);
        return bl;
    }

    public static boolean isAlphaNumericString(String string) {
        boolean bl;
        boolean bl2 = true;
        int n = 0;
        do {
            bl = bl2;
            if (n >= string.length()) break;
            if (!AsciiUtil.isAlphaNumeric(string.charAt(n))) {
                bl = false;
                break;
            }
            ++n;
        } while (true);
        return bl;
    }

    public static boolean isAlphaString(String string) {
        boolean bl;
        boolean bl2 = true;
        int n = 0;
        do {
            bl = bl2;
            if (n >= string.length()) break;
            if (!AsciiUtil.isAlpha(string.charAt(n))) {
                bl = false;
                break;
            }
            ++n;
        } while (true);
        return bl;
    }

    public static boolean isNumeric(char c) {
        boolean bl = c >= '0' && c <= '9';
        return bl;
    }

    public static boolean isNumericString(String string) {
        boolean bl;
        boolean bl2 = true;
        int n = 0;
        do {
            bl = bl2;
            if (n >= string.length()) break;
            if (!AsciiUtil.isNumeric(string.charAt(n))) {
                bl = false;
                break;
            }
            ++n;
        } while (true);
        return bl;
    }

    public static char toLower(char c) {
        char c2 = c;
        if (c >= 'A') {
            c2 = c;
            if (c <= 'Z') {
                c2 = c = (char)(c + 32);
            }
        }
        return c2;
    }

    public static String toLowerString(String string) {
        int n;
        char c;
        for (n = 0; n < string.length() && ((c = string.charAt(n)) < 'A' || c > 'Z'); ++n) {
        }
        if (n == string.length()) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.substring(0, n));
        while (n < string.length()) {
            stringBuilder.append(AsciiUtil.toLower(string.charAt(n)));
            ++n;
        }
        return stringBuilder.toString();
    }

    public static String toTitleString(String string) {
        int n;
        if (string.length() == 0) {
            return string;
        }
        int n2 = 0;
        char c = string.charAt(0);
        if (c < 'a' || c > 'z') {
            n = 1;
            do {
                n2 = ++n;
                if (n >= string.length()) break;
                if (c < 'A' || c > 'Z') continue;
                n2 = n;
                break;
            } while (true);
        }
        if (n2 == string.length()) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.substring(0, n2));
        n = n2;
        if (n2 == 0) {
            stringBuilder.append(AsciiUtil.toUpper(string.charAt(n2)));
            n = n2 + 1;
        }
        while (n < string.length()) {
            stringBuilder.append(AsciiUtil.toLower(string.charAt(n)));
            ++n;
        }
        return stringBuilder.toString();
    }

    public static char toUpper(char c) {
        char c2 = c;
        if (c >= 'a') {
            c2 = c;
            if (c <= 'z') {
                c2 = c = (char)(c - 32);
            }
        }
        return c2;
    }

    public static String toUpperString(String string) {
        int n;
        char c;
        for (n = 0; n < string.length() && ((c = string.charAt(n)) < 'a' || c > 'z'); ++n) {
        }
        if (n == string.length()) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.substring(0, n));
        while (n < string.length()) {
            stringBuilder.append(AsciiUtil.toUpper(string.charAt(n)));
            ++n;
        }
        return stringBuilder.toString();
    }

    public static class CaseInsensitiveKey {
        private int _hash;
        private String _key;

        public CaseInsensitiveKey(String string) {
            this._key = string;
            this._hash = AsciiUtil.toLowerString(string).hashCode();
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object instanceof CaseInsensitiveKey) {
                return AsciiUtil.caseIgnoreMatch(this._key, ((CaseInsensitiveKey)object)._key);
            }
            return false;
        }

        public int hashCode() {
            return this._hash;
        }
    }

}

