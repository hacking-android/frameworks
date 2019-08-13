/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioManager;
import android.media.AudioRecordingConfiguration;
import java.util.concurrent.Executor;

public interface AudioRecordingMonitor {
    public AudioRecordingConfiguration getActiveRecordingConfiguration();

    public void registerAudioRecordingCallback(Executor var1, AudioManager.AudioRecordingCallback var2);

    public void unregisterAudioRecordingCallback(AudioManager.AudioRecordingCallback var1);
}

