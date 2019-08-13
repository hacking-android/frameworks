/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.AudioDevicePort;
import android.media.AudioGainConfig;
import android.media.AudioPort;
import android.media.AudioPortConfig;

public class AudioDevicePortConfig
extends AudioPortConfig {
    @UnsupportedAppUsage
    AudioDevicePortConfig(AudioDevicePort audioDevicePort, int n, int n2, int n3, AudioGainConfig audioGainConfig) {
        super(audioDevicePort, n, n2, n3, audioGainConfig);
    }

    AudioDevicePortConfig(AudioDevicePortConfig audioDevicePortConfig) {
        this(audioDevicePortConfig.port(), audioDevicePortConfig.samplingRate(), audioDevicePortConfig.channelMask(), audioDevicePortConfig.format(), audioDevicePortConfig.gain());
    }

    @Override
    public AudioDevicePort port() {
        return (AudioDevicePort)this.mPort;
    }
}

