/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.AudioDevicePortConfig;
import android.media.AudioGain;
import android.media.AudioGainConfig;
import android.media.AudioHandle;
import android.media.AudioManager;
import android.media.AudioPort;
import android.media.AudioPortConfig;
import android.media.AudioSystem;

public class AudioDevicePort
extends AudioPort {
    private final String mAddress;
    private final int mType;

    @UnsupportedAppUsage
    AudioDevicePort(AudioHandle audioHandle, String string2, int[] arrn, int[] arrn2, int[] arrn3, int[] arrn4, AudioGain[] arraudioGain, int n, String string3) {
        int n2 = AudioManager.isInputDevice(n) ? 1 : 2;
        super(audioHandle, n2, string2, arrn, arrn2, arrn3, arrn4, arraudioGain);
        this.mType = n;
        this.mAddress = string3;
    }

    public String address() {
        return this.mAddress;
    }

    @Override
    public AudioDevicePortConfig buildConfig(int n, int n2, int n3, AudioGainConfig audioGainConfig) {
        return new AudioDevicePortConfig(this, n, n2, n3, audioGainConfig);
    }

    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof AudioDevicePort) {
            AudioDevicePort audioDevicePort = (AudioDevicePort)object;
            if (this.mType != audioDevicePort.type()) {
                return false;
            }
            if (this.mAddress == null && audioDevicePort.address() != null) {
                return false;
            }
            if (!this.mAddress.equals(audioDevicePort.address())) {
                return false;
            }
            return super.equals(object);
        }
        return false;
    }

    @Override
    public String toString() {
        String string2 = this.mRole == 1 ? AudioSystem.getInputDeviceName(this.mType) : AudioSystem.getOutputDeviceName(this.mType);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(super.toString());
        stringBuilder.append(", mType: ");
        stringBuilder.append(string2);
        stringBuilder.append(", mAddress: ");
        stringBuilder.append(this.mAddress);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @UnsupportedAppUsage
    public int type() {
        return this.mType;
    }
}

