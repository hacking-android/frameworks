/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import java.util.Locale;

public class LocaleUtility {
    public static Locale fallback(Locale locale) {
        int n;
        String[] arrstring = new String[]{locale.getLanguage(), locale.getCountry(), locale.getVariant()};
        for (n = 2; n >= 0; --n) {
            if (arrstring[n].length() == 0) continue;
            arrstring[n] = "";
            break;
        }
        if (n < 0) {
            return null;
        }
        return new Locale(arrstring[0], arrstring[1], arrstring[2]);
    }

    public static Locale getLocaleFromName(String string) {
        String string2;
        String string3 = "";
        String string4 = "";
        int n = string.indexOf(95);
        if (n < 0) {
            string2 = string;
            string = string3;
        } else {
            string2 = string.substring(0, n);
            int n2 = n + 1;
            n = string.indexOf(95, n2);
            if (n < 0) {
                string = string.substring(n2);
            } else {
                string3 = string.substring(n2, n);
                string4 = string.substring(n + 1);
                string = string3;
            }
        }
        return new Locale(string2, string, string4);
    }

    public static boolean isFallbackOf(String string, String string2) {
        boolean bl = string2.startsWith(string);
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        int n = string.length();
        if (n == string2.length() || string2.charAt(n) == '_') {
            bl2 = true;
        }
        return bl2;
    }

    public static boolean isFallbackOf(Locale locale, Locale locale2) {
        return LocaleUtility.isFallbackOf(locale.toString(), locale2.toString());
    }
}

