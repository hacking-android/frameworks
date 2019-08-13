/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.AudioGainConfig;

public class AudioGain {
    public static final int MODE_CHANNELS = 2;
    public static final int MODE_JOINT = 1;
    public static final int MODE_RAMP = 4;
    private final int mChannelMask;
    private final int mDefaultValue;
    private final int mIndex;
    private final int mMaxValue;
    private final int mMinValue;
    private final int mMode;
    private final int mRampDurationMaxMs;
    private final int mRampDurationMinMs;
    private final int mStepValue;

    @UnsupportedAppUsage
    AudioGain(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        this.mIndex = n;
        this.mMode = n2;
        this.mChannelMask = n3;
        this.mMinValue = n4;
        this.mMaxValue = n5;
        this.mDefaultValue = n6;
        this.mStepValue = n7;
        this.mRampDurationMinMs = n8;
        this.mRampDurationMaxMs = n9;
    }

    public AudioGainConfig buildConfig(int n, int n2, int[] arrn, int n3) {
        return new AudioGainConfig(this.mIndex, this, n, n2, arrn, n3);
    }

    public int channelMask() {
        return this.mChannelMask;
    }

    public int defaultValue() {
        return this.mDefaultValue;
    }

    public int maxValue() {
        return this.mMaxValue;
    }

    public int minValue() {
        return this.mMinValue;
    }

    public int mode() {
        return this.mMode;
    }

    public int rampDurationMaxMs() {
        return this.mRampDurationMaxMs;
    }

    public int rampDurationMinMs() {
        return this.mRampDurationMinMs;
    }

    public int stepValue() {
        return this.mStepValue;
    }
}

