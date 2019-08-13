/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.biometrics;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.biometrics.IBiometricConfirmDeviceCredentialCallback;
import android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback;
import android.hardware.biometrics.IBiometricService;
import android.os.RemoteException;
import android.util.Slog;
import java.lang.annotation.Annotation;

public class BiometricManager {
    public static final int BIOMETRIC_ERROR_HW_UNAVAILABLE = 1;
    public static final int BIOMETRIC_ERROR_NONE_ENROLLED = 11;
    public static final int BIOMETRIC_ERROR_NO_HARDWARE = 12;
    public static final int BIOMETRIC_SUCCESS = 0;
    private static final String TAG = "BiometricManager";
    private final Context mContext;
    private final boolean mHasHardware;
    private final IBiometricService mService;

    public BiometricManager(Context context, IBiometricService iBiometricService) {
        this.mContext = context;
        this.mService = iBiometricService;
        this.mHasHardware = BiometricManager.hasBiometrics(context);
    }

    public static boolean hasBiometrics(Context object) {
        boolean bl = ((PackageManager)(object = ((Context)object).getPackageManager())).hasSystemFeature("android.hardware.fingerprint") || ((PackageManager)object).hasSystemFeature("android.hardware.biometrics.iris") || ((PackageManager)object).hasSystemFeature("android.hardware.biometrics.face");
        return bl;
    }

    @BiometricError
    public int canAuthenticate() {
        IBiometricService iBiometricService = this.mService;
        if (iBiometricService != null) {
            try {
                int n = iBiometricService.canAuthenticate(this.mContext.getOpPackageName());
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        if (!this.mHasHardware) {
            return 12;
        }
        Slog.w(TAG, "hasEnrolledBiometrics(): Service not connected");
        return 1;
    }

    public void onConfirmDeviceCredentialError(int n, String string2) {
        IBiometricService iBiometricService = this.mService;
        if (iBiometricService != null) {
            try {
                iBiometricService.onConfirmDeviceCredentialError(n, string2);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        } else {
            Slog.w(TAG, "onConfirmDeviceCredentialError(): Service not connected");
        }
    }

    public void onConfirmDeviceCredentialSuccess() {
        IBiometricService iBiometricService = this.mService;
        if (iBiometricService != null) {
            try {
                iBiometricService.onConfirmDeviceCredentialSuccess();
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        } else {
            Slog.w(TAG, "onConfirmDeviceCredentialSuccess(): Service not connected");
        }
    }

    public void registerCancellationCallback(IBiometricConfirmDeviceCredentialCallback iBiometricConfirmDeviceCredentialCallback) {
        IBiometricService iBiometricService = this.mService;
        if (iBiometricService != null) {
            try {
                iBiometricService.registerCancellationCallback(iBiometricConfirmDeviceCredentialCallback);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        } else {
            Slog.w(TAG, "registerCancellationCallback(): Service not connected");
        }
    }

    public void registerEnabledOnKeyguardCallback(IBiometricEnabledOnKeyguardCallback iBiometricEnabledOnKeyguardCallback) {
        IBiometricService iBiometricService = this.mService;
        if (iBiometricService != null) {
            try {
                iBiometricService.registerEnabledOnKeyguardCallback(iBiometricEnabledOnKeyguardCallback);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        } else {
            Slog.w(TAG, "registerEnabledOnKeyguardCallback(): Service not connected");
        }
    }

    public void resetLockout(byte[] arrby) {
        IBiometricService iBiometricService = this.mService;
        if (iBiometricService != null) {
            try {
                iBiometricService.resetLockout(arrby);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        } else {
            Slog.w(TAG, "resetLockout(): Service not connected");
        }
    }

    public void setActiveUser(int n) {
        IBiometricService iBiometricService = this.mService;
        if (iBiometricService != null) {
            try {
                iBiometricService.setActiveUser(n);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        } else {
            Slog.w(TAG, "setActiveUser(): Service not connected");
        }
    }

    static @interface BiometricError {
    }

}

