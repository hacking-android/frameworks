/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.AudioGainConfig;
import android.media.AudioMixPort;
import android.media.AudioPort;
import android.media.AudioPortConfig;

public class AudioMixPortConfig
extends AudioPortConfig {
    @UnsupportedAppUsage
    AudioMixPortConfig(AudioMixPort audioMixPort, int n, int n2, int n3, AudioGainConfig audioGainConfig) {
        super(audioMixPort, n, n2, n3, audioGainConfig);
    }

    @Override
    public AudioMixPort port() {
        return (AudioMixPort)this.mPort;
    }
}

