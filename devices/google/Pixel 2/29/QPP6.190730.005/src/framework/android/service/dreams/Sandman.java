/*
 * Decompiled with CFR 0.145.
 */
package android.service.dreams;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.service.dreams.IDreamManager;
import android.util.Slog;

public final class Sandman {
    private static final ComponentName SOMNAMBULATOR_COMPONENT = new ComponentName("com.android.systemui", "com.android.systemui.Somnambulator");
    private static final String TAG = "Sandman";

    private Sandman() {
    }

    private static boolean isScreenSaverActivatedOnDock(Context context) {
        boolean bl = context.getResources().getBoolean(17891422);
        boolean bl2 = true;
        int n = bl ? 1 : 0;
        if (Settings.Secure.getIntForUser(context.getContentResolver(), "screensaver_activate_on_dock", n, -2) == 0) {
            bl2 = false;
        }
        return bl2;
    }

    private static boolean isScreenSaverEnabled(Context context) {
        boolean bl = context.getResources().getBoolean(17891424);
        boolean bl2 = true;
        int n = bl ? 1 : 0;
        if (Settings.Secure.getIntForUser(context.getContentResolver(), "screensaver_enabled", n, -2) == 0) {
            bl2 = false;
        }
        return bl2;
    }

    public static boolean shouldStartDockApp(Context object, Intent intent) {
        boolean bl = (object = intent.resolveActivity(((Context)object).getPackageManager())) != null && !((ComponentName)object).equals(SOMNAMBULATOR_COMPONENT);
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void startDream(Context context, boolean bl) {
        try {
            IDreamManager iDreamManager = IDreamManager.Stub.asInterface(ServiceManager.getService("dreams"));
            if (iDreamManager == null) return;
            if (iDreamManager.isDreaming()) return;
            if (bl) {
                Slog.i(TAG, "Activating dream while docked.");
                context.getSystemService(PowerManager.class).wakeUp(SystemClock.uptimeMillis(), 3, "android.service.dreams:DREAM");
            } else {
                Slog.i(TAG, "Activating dream by user request.");
            }
            iDreamManager.dream();
            return;
        }
        catch (RemoteException remoteException) {
            Slog.e(TAG, "Could not start dream when docked.", remoteException);
        }
    }

    public static void startDreamByUserRequest(Context context) {
        Sandman.startDream(context, false);
    }

    public static void startDreamWhenDockedIfAppropriate(Context context) {
        if (Sandman.isScreenSaverEnabled(context) && Sandman.isScreenSaverActivatedOnDock(context)) {
            Sandman.startDream(context, true);
            return;
        }
        Slog.i(TAG, "Dreams currently disabled for docks.");
    }
}

