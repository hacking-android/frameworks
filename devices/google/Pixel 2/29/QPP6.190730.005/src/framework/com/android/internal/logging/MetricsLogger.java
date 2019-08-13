/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.logging;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.metrics.LogMaker;
import android.os.Build;
import android.util.SparseArray;
import android.util.StatsLog;
import com.android.internal.logging.EventLogTags;

public class MetricsLogger {
    public static final int LOGTAG = 524292;
    public static final int VIEW_UNKNOWN = 0;
    private static MetricsLogger sMetricsLogger;

    @Deprecated
    public static void action(Context context, int n) {
        MetricsLogger.getLogger().action(n);
    }

    @Deprecated
    public static void action(Context context, int n, int n2) {
        MetricsLogger.getLogger().action(n, n2);
    }

    @Deprecated
    public static void action(Context context, int n, String string2) {
        MetricsLogger.getLogger().action(n, string2);
    }

    @Deprecated
    public static void action(Context context, int n, boolean bl) {
        MetricsLogger.getLogger().action(n, bl);
    }

    @Deprecated
    public static void action(LogMaker logMaker) {
        MetricsLogger.getLogger().write(logMaker);
    }

    @Deprecated
    public static void count(Context context, String string2, int n) {
        MetricsLogger.getLogger().count(string2, n);
    }

    private static MetricsLogger getLogger() {
        if (sMetricsLogger == null) {
            sMetricsLogger = new MetricsLogger();
        }
        return sMetricsLogger;
    }

    @Deprecated
    public static void hidden(Context context, int n) throws IllegalArgumentException {
        MetricsLogger.getLogger().hidden(n);
    }

    @Deprecated
    public static void histogram(Context context, String string2, int n) {
        MetricsLogger.getLogger().histogram(string2, n);
    }

    @Deprecated
    public static void visibility(Context context, int n, int n2) throws IllegalArgumentException {
        boolean bl = n2 == 0;
        MetricsLogger.visibility(context, n, bl);
    }

    @Deprecated
    public static void visibility(Context context, int n, boolean bl) throws IllegalArgumentException {
        MetricsLogger.getLogger().visibility(n, bl);
    }

    @Deprecated
    public static void visible(Context context, int n) throws IllegalArgumentException {
        MetricsLogger.getLogger().visible(n);
    }

    public void action(int n) {
        this.saveLog(new LogMaker(n).setType(4));
    }

    public void action(int n, int n2) {
        this.saveLog(new LogMaker(n).setType(4).setSubtype(n2));
    }

    public void action(int n, String string2) {
        if (Build.IS_DEBUGGABLE && n == 0) {
            throw new IllegalArgumentException("Must define metric category");
        }
        this.saveLog(new LogMaker(n).setType(4).setPackageName(string2));
    }

    public void action(int n, boolean bl) {
        this.saveLog(new LogMaker(n).setType(4).setSubtype((int)bl));
    }

    public void count(String string2, int n) {
        this.saveLog(new LogMaker(803).setCounterName(string2).setCounterValue(n));
    }

    public void hidden(int n) throws IllegalArgumentException {
        if (Build.IS_DEBUGGABLE && n == 0) {
            throw new IllegalArgumentException("Must define metric category");
        }
        this.saveLog(new LogMaker(n).setType(2));
    }

    public void histogram(String string2, int n) {
        this.saveLog(new LogMaker(804).setCounterName(string2).setCounterBucket(n).setCounterValue(1));
    }

    protected void saveLog(LogMaker logMaker) {
        EventLogTags.writeSysuiMultiAction(logMaker.serialize());
        StatsLog.write(83, 0, logMaker.getEntries());
    }

    public void visibility(int n, int n2) throws IllegalArgumentException {
        boolean bl = n2 == 0;
        this.visibility(n, bl);
    }

    public void visibility(int n, boolean bl) throws IllegalArgumentException {
        if (bl) {
            this.visible(n);
        } else {
            this.hidden(n);
        }
    }

    public void visible(int n) throws IllegalArgumentException {
        if (Build.IS_DEBUGGABLE && n == 0) {
            throw new IllegalArgumentException("Must define metric category");
        }
        this.saveLog(new LogMaker(n).setType(1));
    }

    @UnsupportedAppUsage
    public void write(LogMaker logMaker) {
        if (logMaker.getType() == 0) {
            logMaker.setType(4);
        }
        this.saveLog(logMaker);
    }
}

