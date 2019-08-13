/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.AudioGainConfig;
import android.media.AudioPort;

public class AudioPortConfig {
    static final int CHANNEL_MASK = 2;
    static final int FORMAT = 4;
    static final int GAIN = 8;
    static final int SAMPLE_RATE = 1;
    @UnsupportedAppUsage
    private final int mChannelMask;
    @UnsupportedAppUsage
    int mConfigMask;
    @UnsupportedAppUsage
    private final int mFormat;
    @UnsupportedAppUsage
    private final AudioGainConfig mGain;
    @UnsupportedAppUsage
    final AudioPort mPort;
    @UnsupportedAppUsage
    private final int mSamplingRate;

    @UnsupportedAppUsage
    AudioPortConfig(AudioPort audioPort, int n, int n2, int n3, AudioGainConfig audioGainConfig) {
        this.mPort = audioPort;
        this.mSamplingRate = n;
        this.mChannelMask = n2;
        this.mFormat = n3;
        this.mGain = audioGainConfig;
        this.mConfigMask = 0;
    }

    public int channelMask() {
        return this.mChannelMask;
    }

    public int format() {
        return this.mFormat;
    }

    public AudioGainConfig gain() {
        return this.mGain;
    }

    @UnsupportedAppUsage
    public AudioPort port() {
        return this.mPort;
    }

    public int samplingRate() {
        return this.mSamplingRate;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{mPort:");
        stringBuilder.append(this.mPort);
        stringBuilder.append(", mSamplingRate:");
        stringBuilder.append(this.mSamplingRate);
        stringBuilder.append(", mChannelMask: ");
        stringBuilder.append(this.mChannelMask);
        stringBuilder.append(", mFormat:");
        stringBuilder.append(this.mFormat);
        stringBuilder.append(", mGain:");
        stringBuilder.append(this.mGain);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

