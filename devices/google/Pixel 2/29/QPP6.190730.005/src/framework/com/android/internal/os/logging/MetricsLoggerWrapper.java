/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os.logging;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Pair;
import android.util.StatsLog;
import com.android.internal.logging.MetricsLogger;

public class MetricsLoggerWrapper {
    private static final int METRIC_VALUE_DISMISSED_BY_DRAG = 1;
    private static final int METRIC_VALUE_DISMISSED_BY_TAP = 0;

    private static int getUid(Context context, ComponentName componentName, int n) {
        int n2 = -1;
        if (componentName == null) {
            return -1;
        }
        try {
            n = context.getPackageManager().getApplicationInfoAsUser((String)componentName.getPackageName(), (int)0, (int)n).uid;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            n = n2;
        }
        return n;
    }

    public static void logAppOverlayEnter(int n, String string2, boolean bl, int n2, boolean bl2) {
        if (bl) {
            if (n2 != 2038) {
                StatsLog.write(59, n, string2, true, 1);
            } else if (!bl2) {
                StatsLog.write(59, n, string2, false, 1);
            }
        }
    }

    public static void logAppOverlayExit(int n, String string2, boolean bl, int n2, boolean bl2) {
        if (bl) {
            if (n2 != 2038) {
                StatsLog.write(59, n, string2, true, 2);
            } else if (!bl2) {
                StatsLog.write(59, n, string2, false, 2);
            }
        }
    }

    public static void logPictureInPictureDismissByDrag(Context context, Pair<ComponentName, Integer> pair) {
        MetricsLogger.action(context, 822, 1);
        StatsLog.write(52, MetricsLoggerWrapper.getUid(context, (ComponentName)pair.first, (Integer)pair.second), ((ComponentName)pair.first).flattenToString(), 4);
    }

    public static void logPictureInPictureDismissByTap(Context context, Pair<ComponentName, Integer> pair) {
        MetricsLogger.action(context, 822, 0);
        StatsLog.write(52, MetricsLoggerWrapper.getUid(context, (ComponentName)pair.first, (Integer)pair.second), ((ComponentName)pair.first).flattenToString(), 4);
    }

    public static void logPictureInPictureEnter(Context context, int n, String string2, boolean bl) {
        MetricsLogger.action(context, 819, bl);
        StatsLog.write(52, n, string2, 1);
    }

    public static void logPictureInPictureFullScreen(Context context, int n, String string2) {
        MetricsLogger.action(context, 820);
        StatsLog.write(52, n, string2, 2);
    }

    public static void logPictureInPictureMenuVisible(Context context, boolean bl) {
        MetricsLogger.visibility(context, 823, bl);
    }

    public static void logPictureInPictureMinimize(Context context, boolean bl, Pair<ComponentName, Integer> pair) {
        MetricsLogger.action(context, 821, bl);
        StatsLog.write(52, MetricsLoggerWrapper.getUid(context, (ComponentName)pair.first, (Integer)pair.second), ((ComponentName)pair.first).flattenToString(), 3);
    }
}

