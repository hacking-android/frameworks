/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.media.AudioAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class NullVibrator
extends Vibrator {
    private static final NullVibrator sInstance = new NullVibrator();

    private NullVibrator() {
    }

    public static NullVibrator getInstance() {
        return sInstance;
    }

    @Override
    public void cancel() {
    }

    @Override
    public boolean hasAmplitudeControl() {
        return false;
    }

    @Override
    public boolean hasVibrator() {
        return false;
    }

    @Override
    public void vibrate(int n, String string2, VibrationEffect vibrationEffect, String string3, AudioAttributes audioAttributes) {
    }
}

