/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class VolumeProvider {
    public static final int VOLUME_CONTROL_ABSOLUTE = 2;
    public static final int VOLUME_CONTROL_FIXED = 0;
    public static final int VOLUME_CONTROL_RELATIVE = 1;
    private Callback mCallback;
    private final int mControlType;
    private int mCurrentVolume;
    private final int mMaxVolume;

    public VolumeProvider(int n, int n2, int n3) {
        this.mControlType = n;
        this.mMaxVolume = n2;
        this.mCurrentVolume = n3;
    }

    public final int getCurrentVolume() {
        return this.mCurrentVolume;
    }

    public final int getMaxVolume() {
        return this.mMaxVolume;
    }

    public final int getVolumeControl() {
        return this.mControlType;
    }

    public void onAdjustVolume(int n) {
    }

    public void onSetVolumeTo(int n) {
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public final void setCurrentVolume(int n) {
        this.mCurrentVolume = n;
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onVolumeChanged(this);
        }
    }

    public static abstract class Callback {
        public abstract void onVolumeChanged(VolumeProvider var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ControlType {
    }

}

