/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;

public final class RotationPolicy {
    private static final int CURRENT_ROTATION = -1;
    public static final int NATURAL_ROTATION = 0;
    private static final String TAG = "RotationPolicy";

    private RotationPolicy() {
    }

    private static boolean areAllRotationsAllowed(Context context) {
        return context.getResources().getBoolean(17891340);
    }

    public static int getRotationLockOrientation(Context object) {
        if (!RotationPolicy.areAllRotationsAllowed((Context)object)) {
            Point point;
            int n;
            IWindowManager iWindowManager;
            block6 : {
                block5 : {
                    point = new Point();
                    iWindowManager = WindowManagerGlobal.getWindowManagerService();
                    object = ((Context)object).getDisplay();
                    if (object == null) break block5;
                    n = ((Display)object).getDisplayId();
                    break block6;
                }
                n = 0;
            }
            try {
                iWindowManager.getInitialDisplaySize(n, point);
                int n2 = point.x;
                n = point.y;
                n = n2 < n ? 1 : 2;
                return n;
            }
            catch (RemoteException remoteException) {
                Log.w(TAG, "Unable to get the display size");
            }
        }
        return 0;
    }

    public static boolean isRotationLockToggleVisible(Context context) {
        boolean bl;
        block0 : {
            boolean bl2 = RotationPolicy.isRotationSupported(context);
            bl = false;
            if (!bl2 || Settings.System.getIntForUser(context.getContentResolver(), "hide_rotation_lock_toggle_for_accessibility", 0, -2) != 0) break block0;
            bl = true;
        }
        return bl;
    }

    public static boolean isRotationLocked(Context object) {
        object = ((Context)object).getContentResolver();
        boolean bl = false;
        if (Settings.System.getIntForUser((ContentResolver)object, "accelerometer_rotation", 0, -2) == 0) {
            bl = true;
        }
        return bl;
    }

    public static boolean isRotationSupported(Context context) {
        PackageManager packageManager = context.getPackageManager();
        boolean bl = packageManager.hasSystemFeature("android.hardware.sensor.accelerometer") && packageManager.hasSystemFeature("android.hardware.screen.portrait") && packageManager.hasSystemFeature("android.hardware.screen.landscape") && context.getResources().getBoolean(17891529);
        return bl;
    }

    public static void registerRotationPolicyListener(Context context, RotationPolicyListener rotationPolicyListener) {
        RotationPolicy.registerRotationPolicyListener(context, rotationPolicyListener, UserHandle.getCallingUserId());
    }

    public static void registerRotationPolicyListener(Context context, RotationPolicyListener rotationPolicyListener, int n) {
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor("accelerometer_rotation"), false, rotationPolicyListener.mObserver, n);
        context.getContentResolver().registerContentObserver(Settings.System.getUriFor("hide_rotation_lock_toggle_for_accessibility"), false, rotationPolicyListener.mObserver, n);
    }

    public static void setRotationLock(Context context, boolean bl) {
        int n = RotationPolicy.areAllRotationsAllowed(context) ? -1 : 0;
        RotationPolicy.setRotationLockAtAngle(context, bl, n);
    }

    private static void setRotationLock(boolean bl, final int n) {
        AsyncTask.execute(new Runnable(){

            @Override
            public void run() {
                try {
                    IWindowManager iWindowManager = WindowManagerGlobal.getWindowManagerService();
                    if (val$enabled) {
                        iWindowManager.freezeRotation(n);
                    } else {
                        iWindowManager.thawRotation();
                    }
                }
                catch (RemoteException remoteException) {
                    Log.w(RotationPolicy.TAG, "Unable to save auto-rotate setting");
                }
            }
        });
    }

    public static void setRotationLockAtAngle(Context context, boolean bl, int n) {
        Settings.System.putIntForUser(context.getContentResolver(), "hide_rotation_lock_toggle_for_accessibility", 0, -2);
        RotationPolicy.setRotationLock(bl, n);
    }

    public static void setRotationLockForAccessibility(Context context, boolean bl) {
        Settings.System.putIntForUser(context.getContentResolver(), "hide_rotation_lock_toggle_for_accessibility", (int)bl, -2);
        RotationPolicy.setRotationLock(bl, 0);
    }

    public static void unregisterRotationPolicyListener(Context context, RotationPolicyListener rotationPolicyListener) {
        context.getContentResolver().unregisterContentObserver(rotationPolicyListener.mObserver);
    }

    public static abstract class RotationPolicyListener {
        final ContentObserver mObserver = new ContentObserver(new Handler()){

            @Override
            public void onChange(boolean bl, Uri uri) {
                RotationPolicyListener.this.onChange();
            }
        };

        public abstract void onChange();

    }

}

