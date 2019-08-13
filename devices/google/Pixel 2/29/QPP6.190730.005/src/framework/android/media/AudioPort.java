/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.AudioGain;
import android.media.AudioGainConfig;
import android.media.AudioHandle;
import android.media.AudioPortConfig;

public class AudioPort {
    public static final int ROLE_NONE = 0;
    public static final int ROLE_SINK = 2;
    public static final int ROLE_SOURCE = 1;
    private static final String TAG = "AudioPort";
    public static final int TYPE_DEVICE = 1;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_SESSION = 3;
    public static final int TYPE_SUBMIX = 2;
    @UnsupportedAppUsage
    private AudioPortConfig mActiveConfig;
    private final int[] mChannelIndexMasks;
    private final int[] mChannelMasks;
    private final int[] mFormats;
    @UnsupportedAppUsage
    private final AudioGain[] mGains;
    @UnsupportedAppUsage
    AudioHandle mHandle;
    private final String mName;
    @UnsupportedAppUsage
    protected final int mRole;
    private final int[] mSamplingRates;

    @UnsupportedAppUsage
    AudioPort(AudioHandle audioHandle, int n, String string2, int[] arrn, int[] arrn2, int[] arrn3, int[] arrn4, AudioGain[] arraudioGain) {
        this.mHandle = audioHandle;
        this.mRole = n;
        this.mName = string2;
        this.mSamplingRates = arrn;
        this.mChannelMasks = arrn2;
        this.mChannelIndexMasks = arrn3;
        this.mFormats = arrn4;
        this.mGains = arraudioGain;
    }

    public AudioPortConfig activeConfig() {
        return this.mActiveConfig;
    }

    public AudioPortConfig buildConfig(int n, int n2, int n3, AudioGainConfig audioGainConfig) {
        return new AudioPortConfig(this, n, n2, n3, audioGainConfig);
    }

    public int[] channelIndexMasks() {
        return this.mChannelIndexMasks;
    }

    public int[] channelMasks() {
        return this.mChannelMasks;
    }

    public boolean equals(Object object) {
        if (object != null && object instanceof AudioPort) {
            object = (AudioPort)object;
            return this.mHandle.equals(((AudioPort)object).handle());
        }
        return false;
    }

    public int[] formats() {
        return this.mFormats;
    }

    AudioGain gain(int n) {
        AudioGain[] arraudioGain;
        if (n >= 0 && n < (arraudioGain = this.mGains).length) {
            return arraudioGain[n];
        }
        return null;
    }

    public AudioGain[] gains() {
        return this.mGains;
    }

    AudioHandle handle() {
        return this.mHandle;
    }

    public int hashCode() {
        return this.mHandle.hashCode();
    }

    @UnsupportedAppUsage
    public int id() {
        return this.mHandle.id();
    }

    public String name() {
        return this.mName;
    }

    @UnsupportedAppUsage
    public int role() {
        return this.mRole;
    }

    public int[] samplingRates() {
        return this.mSamplingRates;
    }

    public String toString() {
        String string2 = Integer.toString(this.mRole);
        int n = this.mRole;
        if (n != 0) {
            if (n != 1) {
                if (n == 2) {
                    string2 = "SINK";
                }
            } else {
                string2 = "SOURCE";
            }
        } else {
            string2 = "NONE";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{mHandle: ");
        stringBuilder.append(this.mHandle);
        stringBuilder.append(", mRole: ");
        stringBuilder.append(string2);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

