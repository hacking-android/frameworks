/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.AudioGain;

public class AudioGainConfig {
    @UnsupportedAppUsage
    private final int mChannelMask;
    AudioGain mGain;
    @UnsupportedAppUsage
    private final int mIndex;
    @UnsupportedAppUsage
    private final int mMode;
    @UnsupportedAppUsage
    private final int mRampDurationMs;
    @UnsupportedAppUsage
    private final int[] mValues;

    @UnsupportedAppUsage
    AudioGainConfig(int n, AudioGain audioGain, int n2, int n3, int[] arrn, int n4) {
        this.mIndex = n;
        this.mGain = audioGain;
        this.mMode = n2;
        this.mChannelMask = n3;
        this.mValues = arrn;
        this.mRampDurationMs = n4;
    }

    public int channelMask() {
        return this.mChannelMask;
    }

    int index() {
        return this.mIndex;
    }

    public int mode() {
        return this.mMode;
    }

    public int rampDurationMs() {
        return this.mRampDurationMs;
    }

    public int[] values() {
        return this.mValues;
    }
}

