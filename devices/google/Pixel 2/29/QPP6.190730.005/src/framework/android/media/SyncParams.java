/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class SyncParams {
    public static final int AUDIO_ADJUST_MODE_DEFAULT = 0;
    public static final int AUDIO_ADJUST_MODE_RESAMPLE = 2;
    public static final int AUDIO_ADJUST_MODE_STRETCH = 1;
    private static final int SET_AUDIO_ADJUST_MODE = 2;
    private static final int SET_FRAME_RATE = 8;
    private static final int SET_SYNC_SOURCE = 1;
    private static final int SET_TOLERANCE = 4;
    public static final int SYNC_SOURCE_AUDIO = 2;
    public static final int SYNC_SOURCE_DEFAULT = 0;
    public static final int SYNC_SOURCE_SYSTEM_CLOCK = 1;
    public static final int SYNC_SOURCE_VSYNC = 3;
    private int mAudioAdjustMode = 0;
    private float mFrameRate = 0.0f;
    private int mSet = 0;
    private int mSyncSource = 0;
    private float mTolerance = 0.0f;

    public SyncParams allowDefaults() {
        this.mSet |= 7;
        return this;
    }

    public int getAudioAdjustMode() {
        if ((this.mSet & 2) != 0) {
            return this.mAudioAdjustMode;
        }
        throw new IllegalStateException("audio adjust mode not set");
    }

    public float getFrameRate() {
        if ((this.mSet & 8) != 0) {
            return this.mFrameRate;
        }
        throw new IllegalStateException("frame rate not set");
    }

    public int getSyncSource() {
        if ((this.mSet & 1) != 0) {
            return this.mSyncSource;
        }
        throw new IllegalStateException("sync source not set");
    }

    public float getTolerance() {
        if ((this.mSet & 4) != 0) {
            return this.mTolerance;
        }
        throw new IllegalStateException("tolerance not set");
    }

    public SyncParams setAudioAdjustMode(int n) {
        this.mAudioAdjustMode = n;
        this.mSet |= 2;
        return this;
    }

    public SyncParams setFrameRate(float f) {
        this.mFrameRate = f;
        this.mSet |= 8;
        return this;
    }

    public SyncParams setSyncSource(int n) {
        this.mSyncSource = n;
        this.mSet |= 1;
        return this;
    }

    public SyncParams setTolerance(float f) {
        if (!(f < 0.0f) && !(f >= 1.0f)) {
            this.mTolerance = f;
            this.mSet |= 4;
            return this;
        }
        throw new IllegalArgumentException("tolerance must be less than one and non-negative");
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AudioAdjustMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SyncSource {
    }

}

