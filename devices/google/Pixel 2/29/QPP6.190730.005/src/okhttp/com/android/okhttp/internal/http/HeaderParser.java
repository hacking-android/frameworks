/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.okhttp.internal.http;

import dalvik.annotation.compat.UnsupportedAppUsage;

public final class HeaderParser {
    private HeaderParser() {
    }

    public static int parseSeconds(String string, int n) {
        long l;
        block3 : {
            try {
                l = Long.parseLong(string);
                if (l > Integer.MAX_VALUE) {
                    return Integer.MAX_VALUE;
                }
                if (l >= 0L) break block3;
                return 0;
            }
            catch (NumberFormatException numberFormatException) {
                return n;
            }
        }
        return (int)l;
    }

    @UnsupportedAppUsage
    public static int skipUntil(String string, int n, String string2) {
        while (n < string.length() && string2.indexOf(string.charAt(n)) == -1) {
            ++n;
        }
        return n;
    }

    @UnsupportedAppUsage
    public static int skipWhitespace(String string, int n) {
        char c;
        while (n < string.length() && ((c = string.charAt(n)) == ' ' || c == '\t')) {
            ++n;
        }
        return n;
    }
}

