/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

public final class ParseUtils {
    private ParseUtils() {
    }

    public static boolean parseBoolean(String string2, boolean bl) {
        boolean bl2 = "true".equals(string2);
        boolean bl3 = true;
        if (bl2) {
            return true;
        }
        if ("false".equals(string2)) {
            return false;
        }
        if (ParseUtils.parseInt(string2, (int)bl) == 0) {
            bl3 = false;
        }
        return bl3;
    }

    public static double parseDouble(String string2, double d) {
        if (string2 == null) {
            return d;
        }
        try {
            double d2 = Double.parseDouble(string2);
            return d2;
        }
        catch (NumberFormatException numberFormatException) {
            return d;
        }
    }

    public static float parseFloat(String string2, float f) {
        if (string2 == null) {
            return f;
        }
        try {
            float f2 = Float.parseFloat(string2);
            return f2;
        }
        catch (NumberFormatException numberFormatException) {
            return f;
        }
    }

    public static int parseInt(String string2, int n) {
        return ParseUtils.parseIntWithBase(string2, 10, n);
    }

    public static int parseIntWithBase(String string2, int n, int n2) {
        if (string2 == null) {
            return n2;
        }
        try {
            n = Integer.parseInt(string2, n);
            return n;
        }
        catch (NumberFormatException numberFormatException) {
            return n2;
        }
    }

    public static long parseLong(String string2, long l) {
        return ParseUtils.parseLongWithBase(string2, 10, l);
    }

    public static long parseLongWithBase(String string2, int n, long l) {
        if (string2 == null) {
            return l;
        }
        try {
            long l2 = Long.parseLong(string2, n);
            return l2;
        }
        catch (NumberFormatException numberFormatException) {
            return l;
        }
    }
}

