/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.AudioHandle;
import android.media.AudioPortConfig;

public class AudioPatch {
    @UnsupportedAppUsage
    private final AudioHandle mHandle;
    private final AudioPortConfig[] mSinks;
    private final AudioPortConfig[] mSources;

    @UnsupportedAppUsage
    AudioPatch(AudioHandle audioHandle, AudioPortConfig[] arraudioPortConfig, AudioPortConfig[] arraudioPortConfig2) {
        this.mHandle = audioHandle;
        this.mSources = arraudioPortConfig;
        this.mSinks = arraudioPortConfig2;
    }

    public int id() {
        return this.mHandle.id();
    }

    @UnsupportedAppUsage
    public AudioPortConfig[] sinks() {
        return this.mSinks;
    }

    @UnsupportedAppUsage
    public AudioPortConfig[] sources() {
        return this.mSources;
    }

    public String toString() {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mHandle: ");
        stringBuilder.append(this.mHandle.toString());
        stringBuilder.append(" mSources: {");
        AudioPortConfig[] arraudioPortConfig = this.mSources;
        int n2 = arraudioPortConfig.length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            stringBuilder.append(arraudioPortConfig[n].toString());
            stringBuilder.append(", ");
        }
        stringBuilder.append("} mSinks: {");
        arraudioPortConfig = this.mSinks;
        n2 = arraudioPortConfig.length;
        for (n = n3; n < n2; ++n) {
            stringBuilder.append(arraudioPortConfig[n].toString());
            stringBuilder.append(", ");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

