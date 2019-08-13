/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import android.util.Log;

public final class Slog {
    private Slog() {
    }

    @UnsupportedAppUsage
    public static int d(String string2, String string3) {
        return Log.println_native(3, 3, string2, string3);
    }

    @UnsupportedAppUsage
    public static int d(String string2, String string3, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string3);
        stringBuilder.append('\n');
        stringBuilder.append(Log.getStackTraceString(throwable));
        return Log.println_native(3, 3, string2, stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public static int e(String string2, String string3) {
        return Log.println_native(3, 6, string2, string3);
    }

    @UnsupportedAppUsage
    public static int e(String string2, String string3, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string3);
        stringBuilder.append('\n');
        stringBuilder.append(Log.getStackTraceString(throwable));
        return Log.println_native(3, 6, string2, stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public static int i(String string2, String string3) {
        return Log.println_native(3, 4, string2, string3);
    }

    public static int i(String string2, String string3, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string3);
        stringBuilder.append('\n');
        stringBuilder.append(Log.getStackTraceString(throwable));
        return Log.println_native(3, 4, string2, stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public static int println(int n, String string2, String string3) {
        return Log.println_native(3, n, string2, string3);
    }

    @UnsupportedAppUsage
    public static int v(String string2, String string3) {
        return Log.println_native(3, 2, string2, string3);
    }

    public static int v(String string2, String string3, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string3);
        stringBuilder.append('\n');
        stringBuilder.append(Log.getStackTraceString(throwable));
        return Log.println_native(3, 2, string2, stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public static int w(String string2, String string3) {
        return Log.println_native(3, 5, string2, string3);
    }

    @UnsupportedAppUsage
    public static int w(String string2, String string3, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string3);
        stringBuilder.append('\n');
        stringBuilder.append(Log.getStackTraceString(throwable));
        return Log.println_native(3, 5, string2, stringBuilder.toString());
    }

    public static int w(String string2, Throwable throwable) {
        return Log.println_native(3, 5, string2, Log.getStackTraceString(throwable));
    }

    @UnsupportedAppUsage
    public static int wtf(String string2, String string3) {
        return Log.wtf(3, string2, string3, null, false, true);
    }

    @UnsupportedAppUsage
    public static int wtf(String string2, String string3, Throwable throwable) {
        return Log.wtf(3, string2, string3, throwable, false, true);
    }

    public static int wtf(String string2, Throwable throwable) {
        return Log.wtf(3, string2, throwable.getMessage(), throwable, false, true);
    }

    public static void wtfQuiet(String string2, String string3) {
        Log.wtfQuiet(3, string2, string3, true);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static int wtfStack(String string2, String string3) {
        return Log.wtf(3, string2, string3, null, true, true);
    }
}

