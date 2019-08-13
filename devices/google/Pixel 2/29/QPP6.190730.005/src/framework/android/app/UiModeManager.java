/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.IUiModeManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class UiModeManager {
    public static String ACTION_ENTER_CAR_MODE = "android.app.action.ENTER_CAR_MODE";
    public static String ACTION_ENTER_DESK_MODE;
    public static String ACTION_EXIT_CAR_MODE;
    public static String ACTION_EXIT_DESK_MODE;
    public static final int DISABLE_CAR_MODE_GO_HOME = 1;
    public static final int ENABLE_CAR_MODE_ALLOW_SLEEP = 2;
    public static final int ENABLE_CAR_MODE_GO_CAR_HOME = 1;
    public static final int MODE_NIGHT_AUTO = 0;
    public static final int MODE_NIGHT_NO = 1;
    public static final int MODE_NIGHT_YES = 2;
    private static final String TAG = "UiModeManager";
    private IUiModeManager mService = IUiModeManager.Stub.asInterface(ServiceManager.getServiceOrThrow("uimode"));

    static {
        ACTION_EXIT_CAR_MODE = "android.app.action.EXIT_CAR_MODE";
        ACTION_ENTER_DESK_MODE = "android.app.action.ENTER_DESK_MODE";
        ACTION_EXIT_DESK_MODE = "android.app.action.EXIT_DESK_MODE";
    }

    @UnsupportedAppUsage
    UiModeManager() throws ServiceManager.ServiceNotFoundException {
    }

    public void disableCarMode(int n) {
        IUiModeManager iUiModeManager = this.mService;
        if (iUiModeManager != null) {
            try {
                iUiModeManager.disableCarMode(n);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void enableCarMode(int n) {
        IUiModeManager iUiModeManager = this.mService;
        if (iUiModeManager != null) {
            try {
                iUiModeManager.enableCarMode(n);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public int getCurrentModeType() {
        IUiModeManager iUiModeManager = this.mService;
        if (iUiModeManager != null) {
            try {
                int n = iUiModeManager.getCurrentModeType();
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 1;
    }

    public int getNightMode() {
        IUiModeManager iUiModeManager = this.mService;
        if (iUiModeManager != null) {
            try {
                int n = iUiModeManager.getNightMode();
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return -1;
    }

    public boolean isNightModeLocked() {
        IUiModeManager iUiModeManager = this.mService;
        if (iUiModeManager != null) {
            try {
                boolean bl = iUiModeManager.isNightModeLocked();
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return true;
    }

    public boolean isUiModeLocked() {
        IUiModeManager iUiModeManager = this.mService;
        if (iUiModeManager != null) {
            try {
                boolean bl = iUiModeManager.isUiModeLocked();
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return true;
    }

    public void setNightMode(int n) {
        IUiModeManager iUiModeManager = this.mService;
        if (iUiModeManager != null) {
            try {
                iUiModeManager.setNightMode(n);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface NightMode {
    }

}

