/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.media.AudioAttributes;
import android.os.Binder;
import android.os.IBinder;
import android.os.IVibratorService;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

public class SystemVibrator
extends Vibrator {
    private static final String TAG = "Vibrator";
    private final IVibratorService mService = IVibratorService.Stub.asInterface(ServiceManager.getService("vibrator"));
    private final Binder mToken = new Binder();

    @UnsupportedAppUsage
    public SystemVibrator() {
    }

    @UnsupportedAppUsage
    public SystemVibrator(Context context) {
        super(context);
    }

    private static int usageForAttributes(AudioAttributes audioAttributes) {
        int n = audioAttributes != null ? audioAttributes.getUsage() : 0;
        return n;
    }

    @Override
    public void cancel() {
        IVibratorService iVibratorService = this.mService;
        if (iVibratorService == null) {
            return;
        }
        try {
            iVibratorService.cancelVibrate(this.mToken);
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Failed to cancel vibration.", remoteException);
        }
    }

    @Override
    public boolean hasAmplitudeControl() {
        IVibratorService iVibratorService = this.mService;
        if (iVibratorService == null) {
            Log.w(TAG, "Failed to check amplitude control; no vibrator service.");
            return false;
        }
        try {
            boolean bl = iVibratorService.hasAmplitudeControl();
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public boolean hasVibrator() {
        IVibratorService iVibratorService = this.mService;
        if (iVibratorService == null) {
            Log.w(TAG, "Failed to vibrate; no vibrator service.");
            return false;
        }
        try {
            boolean bl = iVibratorService.hasVibrator();
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Override
    public void vibrate(int n, String string2, VibrationEffect vibrationEffect, String string3, AudioAttributes audioAttributes) {
        IVibratorService iVibratorService = this.mService;
        if (iVibratorService == null) {
            Log.w(TAG, "Failed to vibrate; no vibrator service.");
            return;
        }
        try {
            iVibratorService.vibrate(n, string2, vibrationEffect, SystemVibrator.usageForAttributes(audioAttributes), string3, this.mToken);
        }
        catch (RemoteException remoteException) {
            Log.w(TAG, "Failed to vibrate.", remoteException);
        }
    }
}

