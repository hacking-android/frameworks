/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.app.ContextImpl;
import android.content.Context;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.os.Process;
import android.os.VibrationEffect;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class Vibrator {
    private static final String TAG = "Vibrator";
    public static final int VIBRATION_INTENSITY_HIGH = 3;
    public static final int VIBRATION_INTENSITY_LOW = 1;
    public static final int VIBRATION_INTENSITY_MEDIUM = 2;
    public static final int VIBRATION_INTENSITY_OFF = 0;
    private int mDefaultHapticFeedbackIntensity;
    private int mDefaultNotificationVibrationIntensity;
    private int mDefaultRingVibrationIntensity;
    private final String mPackageName;

    @UnsupportedAppUsage
    public Vibrator() {
        this.mPackageName = ActivityThread.currentPackageName();
        this.loadVibrationIntensities(ActivityThread.currentActivityThread().getSystemContext());
    }

    protected Vibrator(Context context) {
        this.mPackageName = context.getOpPackageName();
        this.loadVibrationIntensities(context);
    }

    private int loadDefaultIntensity(Context context, int n) {
        n = context != null ? context.getResources().getInteger(n) : 2;
        return n;
    }

    private void loadVibrationIntensities(Context context) {
        this.mDefaultHapticFeedbackIntensity = this.loadDefaultIntensity(context, 17694771);
        this.mDefaultNotificationVibrationIntensity = this.loadDefaultIntensity(context, 17694778);
        this.mDefaultRingVibrationIntensity = this.loadDefaultIntensity(context, 17694781);
    }

    public abstract void cancel();

    public int getDefaultHapticFeedbackIntensity() {
        return this.mDefaultHapticFeedbackIntensity;
    }

    public int getDefaultNotificationVibrationIntensity() {
        return this.mDefaultNotificationVibrationIntensity;
    }

    public int getDefaultRingVibrationIntensity() {
        return this.mDefaultRingVibrationIntensity;
    }

    public abstract boolean hasAmplitudeControl();

    public abstract boolean hasVibrator();

    public abstract void vibrate(int var1, String var2, VibrationEffect var3, String var4, AudioAttributes var5);

    @Deprecated
    public void vibrate(long l) {
        this.vibrate(l, null);
    }

    @Deprecated
    public void vibrate(long l, AudioAttributes audioAttributes) {
        try {
            this.vibrate(VibrationEffect.createOneShot(l, -1), audioAttributes);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            Log.e("Vibrator", "Failed to create VibrationEffect", illegalArgumentException);
        }
    }

    public void vibrate(VibrationEffect vibrationEffect) {
        this.vibrate(vibrationEffect, null);
    }

    public void vibrate(VibrationEffect vibrationEffect, AudioAttributes audioAttributes) {
        this.vibrate(Process.myUid(), this.mPackageName, vibrationEffect, null, audioAttributes);
    }

    @Deprecated
    public void vibrate(long[] arrl, int n) {
        this.vibrate(arrl, n, null);
    }

    @Deprecated
    public void vibrate(long[] arrl, int n, AudioAttributes object) {
        if (n >= -1 && n < arrl.length) {
            try {
                this.vibrate(VibrationEffect.createWaveform(arrl, n), (AudioAttributes)object);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                Log.e("Vibrator", "Failed to create VibrationEffect", illegalArgumentException);
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("vibrate called with repeat index out of bounds (pattern.length=");
        ((StringBuilder)object).append(arrl.length);
        ((StringBuilder)object).append(", index=");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(")");
        Log.e("Vibrator", ((StringBuilder)object).toString());
        throw new ArrayIndexOutOfBoundsException();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface VibrationIntensity {
    }

}

