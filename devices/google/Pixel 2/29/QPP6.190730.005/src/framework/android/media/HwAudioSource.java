/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.SystemApi;
import android.media.AudioAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioDevicePort;
import android.media.AudioPortConfig;
import android.media.AudioSystem;
import android.media.PlayerBase;
import android.media.VolumeShaper;
import com.android.internal.util.Preconditions;

@SystemApi
public class HwAudioSource
extends PlayerBase {
    private final AudioAttributes mAudioAttributes;
    private final AudioDeviceInfo mAudioDeviceInfo;
    private int mNativeHandle;

    private HwAudioSource(AudioDeviceInfo audioDeviceInfo, AudioAttributes audioAttributes) {
        super(audioAttributes, 14);
        Preconditions.checkNotNull(audioDeviceInfo);
        Preconditions.checkNotNull(audioAttributes);
        Preconditions.checkArgument(audioDeviceInfo.isSource(), "Requires a source device");
        this.mAudioDeviceInfo = audioDeviceInfo;
        this.mAudioAttributes = audioAttributes;
        this.baseRegisterPlayer();
    }

    public boolean isPlaying() {
        boolean bl = this.mNativeHandle != 0;
        return bl;
    }

    @Override
    int playerApplyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation) {
        return 0;
    }

    @Override
    VolumeShaper.State playerGetVolumeShaperState(int n) {
        return new VolumeShaper.State(1.0f, 1.0f);
    }

    @Override
    void playerPause() {
        this.stop();
    }

    @Override
    int playerSetAuxEffectSendLevel(boolean bl, float f) {
        return 0;
    }

    @Override
    void playerSetVolume(boolean bl, float f, float f2) {
    }

    @Override
    void playerStart() {
        this.start();
    }

    @Override
    void playerStop() {
        this.stop();
    }

    public void start() {
        Preconditions.checkState(this.isPlaying() ^ true, "HwAudioSource is currently playing");
        this.baseStart();
        this.mNativeHandle = AudioSystem.startAudioSource(this.mAudioDeviceInfo.getPort().activeConfig(), this.mAudioAttributes);
    }

    public void stop() {
        this.baseStop();
        int n = this.mNativeHandle;
        if (n > 0) {
            AudioSystem.stopAudioSource(n);
            this.mNativeHandle = 0;
        }
    }

    public static final class Builder {
        private AudioAttributes mAudioAttributes;
        private AudioDeviceInfo mAudioDeviceInfo;

        public HwAudioSource build() {
            Preconditions.checkNotNull(this.mAudioDeviceInfo);
            if (this.mAudioAttributes == null) {
                this.mAudioAttributes = new AudioAttributes.Builder().setUsage(1).build();
            }
            return new HwAudioSource(this.mAudioDeviceInfo, this.mAudioAttributes);
        }

        public Builder setAudioAttributes(AudioAttributes audioAttributes) {
            Preconditions.checkNotNull(audioAttributes);
            this.mAudioAttributes = audioAttributes;
            return this;
        }

        public Builder setAudioDeviceInfo(AudioDeviceInfo audioDeviceInfo) {
            Preconditions.checkNotNull(audioDeviceInfo);
            Preconditions.checkArgument(audioDeviceInfo.isSource());
            this.mAudioDeviceInfo = audioDeviceInfo;
            return this;
        }
    }

}

