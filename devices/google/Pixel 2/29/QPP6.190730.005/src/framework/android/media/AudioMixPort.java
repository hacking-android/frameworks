/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.AudioGain;
import android.media.AudioGainConfig;
import android.media.AudioHandle;
import android.media.AudioMixPortConfig;
import android.media.AudioPort;
import android.media.AudioPortConfig;

public class AudioMixPort
extends AudioPort {
    private final int mIoHandle;

    @UnsupportedAppUsage
    AudioMixPort(AudioHandle audioHandle, int n, int n2, String string2, int[] arrn, int[] arrn2, int[] arrn3, int[] arrn4, AudioGain[] arraudioGain) {
        super(audioHandle, n2, string2, arrn, arrn2, arrn3, arrn4, arraudioGain);
        this.mIoHandle = n;
    }

    @Override
    public AudioMixPortConfig buildConfig(int n, int n2, int n3, AudioGainConfig audioGainConfig) {
        return new AudioMixPortConfig(this, n, n2, n3, audioGainConfig);
    }

    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof AudioMixPort) {
            AudioMixPort audioMixPort = (AudioMixPort)object;
            if (this.mIoHandle != audioMixPort.ioHandle()) {
                return false;
            }
            return super.equals(object);
        }
        return false;
    }

    @UnsupportedAppUsage
    public int ioHandle() {
        return this.mIoHandle;
    }
}

