/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.app.Application;
import android.content.pm.IPackageManager;

public class AppGlobals {
    @UnsupportedAppUsage
    public static Application getInitialApplication() {
        return ActivityThread.currentApplication();
    }

    @UnsupportedAppUsage
    public static String getInitialPackage() {
        return ActivityThread.currentPackageName();
    }

    public static int getIntCoreSetting(String string2, int n) {
        ActivityThread activityThread = ActivityThread.currentActivityThread();
        if (activityThread != null) {
            return activityThread.getIntCoreSetting(string2, n);
        }
        return n;
    }

    @UnsupportedAppUsage
    public static IPackageManager getPackageManager() {
        return ActivityThread.getPackageManager();
    }
}

