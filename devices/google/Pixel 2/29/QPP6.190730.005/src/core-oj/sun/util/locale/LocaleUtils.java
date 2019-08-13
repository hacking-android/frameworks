/*
 * Decompiled with CFR 0.145.
 */
package sun.util.locale;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class LocaleUtils {
    private LocaleUtils() {
    }

    static int caseIgnoreCompare(String string, String string2) {
        if (string == string2) {
            return 0;
        }
        return LocaleUtils.toLowerString(string).compareTo(LocaleUtils.toLowerString(string2));
    }

    public static boolean caseIgnoreMatch(String string, String string2) {
        if (string == string2) {
            return true;
        }
        int n = string.length();
        if (n != string2.length()) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            char c;
            char c2 = string.charAt(i);
            if (c2 == (c = string2.charAt(i)) || LocaleUtils.toLower(c2) == LocaleUtils.toLower(c)) continue;
            return false;
        }
        return true;
    }

    static boolean isAlpha(char c) {
        boolean bl = c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z';
        return bl;
    }

    static boolean isAlphaNumeric(char c) {
        boolean bl = LocaleUtils.isAlpha(c) || LocaleUtils.isNumeric(c);
        return bl;
    }

    public static boolean isAlphaNumericString(String string) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            if (LocaleUtils.isAlphaNumeric(string.charAt(i))) continue;
            return false;
        }
        return true;
    }

    static boolean isAlphaString(String string) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            if (LocaleUtils.isAlpha(string.charAt(i))) continue;
            return false;
        }
        return true;
    }

    static boolean isEmpty(String string) {
        boolean bl = string == null || string.length() == 0;
        return bl;
    }

    static boolean isEmpty(List<?> list) {
        boolean bl = list == null || list.isEmpty();
        return bl;
    }

    static boolean isEmpty(Map<?, ?> map) {
        boolean bl = map == null || map.isEmpty();
        return bl;
    }

    static boolean isEmpty(Set<?> set) {
        boolean bl = set == null || set.isEmpty();
        return bl;
    }

    private static boolean isLower(char c) {
        boolean bl = c >= 'a' && c <= 'z';
        return bl;
    }

    static boolean isNumeric(char c) {
        boolean bl = c >= '0' && c <= '9';
        return bl;
    }

    static boolean isNumericString(String string) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            if (LocaleUtils.isNumeric(string.charAt(i))) continue;
            return false;
        }
        return true;
    }

    private static boolean isUpper(char c) {
        boolean bl = c >= 'A' && c <= 'Z';
        return bl;
    }

    static char toLower(char c) {
        char c2;
        char c3;
        c = LocaleUtils.isUpper(c) ? (c3 = (char)(c + 32)) : (c2 = c);
        return c;
    }

    public static String toLowerString(String string) {
        int n;
        int n2 = string.length();
        for (n = 0; n < n2 && !LocaleUtils.isUpper(string.charAt(n)); ++n) {
        }
        if (n == n2) {
            return string;
        }
        char[] arrc = new char[n2];
        for (int i = 0; i < n2; ++i) {
            char c = string.charAt(i);
            char c2 = i < n ? c : LocaleUtils.toLower(c);
            arrc[i] = c2;
        }
        return new String(arrc);
    }

    static String toTitleString(String string) {
        int n;
        int n2 = string.length();
        if (n2 == 0) {
            return string;
        }
        int n3 = 0;
        if (!LocaleUtils.isLower(string.charAt(0))) {
            n = 1;
            do {
                n3 = ++n;
                if (n >= n2) break;
                if (!LocaleUtils.isUpper(string.charAt(n))) continue;
                n3 = n;
                break;
            } while (true);
        }
        if (n3 == n2) {
            return string;
        }
        char[] arrc = new char[n2];
        for (n = 0; n < n2; ++n) {
            char c = string.charAt(n);
            arrc[n] = n == 0 && n3 == 0 ? LocaleUtils.toUpper(c) : (n < n3 ? c : LocaleUtils.toLower(c));
        }
        return new String(arrc);
    }

    static char toUpper(char c) {
        char c2;
        char c3;
        c = LocaleUtils.isLower(c) ? (c3 = (char)(c - 32)) : (c2 = c);
        return c;
    }

    static String toUpperString(String string) {
        int n;
        int n2 = string.length();
        for (n = 0; n < n2 && !LocaleUtils.isLower(string.charAt(n)); ++n) {
        }
        if (n == n2) {
            return string;
        }
        char[] arrc = new char[n2];
        for (int i = 0; i < n2; ++i) {
            char c = string.charAt(i);
            char c2 = i < n ? c : LocaleUtils.toUpper(c);
            arrc[i] = c2;
        }
        return new String(arrc);
    }
}

