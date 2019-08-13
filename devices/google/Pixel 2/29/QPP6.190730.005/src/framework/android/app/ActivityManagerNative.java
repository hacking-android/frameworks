/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;

@Deprecated
public abstract class ActivityManagerNative {
    @UnsupportedAppUsage
    public static IActivityManager asInterface(IBinder iBinder) {
        return IActivityManager.Stub.asInterface(iBinder);
    }

    @UnsupportedAppUsage
    public static void broadcastStickyIntent(Intent intent, String string2, int n) {
        ActivityManagerNative.broadcastStickyIntent(intent, string2, -1, n);
    }

    public static void broadcastStickyIntent(Intent intent, String string2, int n, int n2) {
        ActivityManager.broadcastStickyIntent(intent, n, n2);
    }

    @UnsupportedAppUsage
    public static IActivityManager getDefault() {
        return ActivityManager.getService();
    }

    @UnsupportedAppUsage
    public static boolean isSystemReady() {
        return ActivityManager.isSystemReady();
    }

    public static void noteAlarmFinish(PendingIntent pendingIntent, int n, String string2) {
        ActivityManager.noteAlarmFinish(pendingIntent, null, n, string2);
    }

    public static void noteAlarmStart(PendingIntent pendingIntent, int n, String string2) {
        ActivityManager.noteAlarmStart(pendingIntent, null, n, string2);
    }

    public static void noteWakeupAlarm(PendingIntent pendingIntent, int n, String string2, String string3) {
        ActivityManager.noteWakeupAlarm(pendingIntent, null, n, string2, string3);
    }
}

