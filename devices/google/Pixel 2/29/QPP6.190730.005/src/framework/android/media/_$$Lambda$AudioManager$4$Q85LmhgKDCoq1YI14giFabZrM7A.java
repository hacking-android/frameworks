/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioManager;

public final class _$$Lambda$AudioManager$4$Q85LmhgKDCoq1YI14giFabZrM7A
implements Runnable {
    private final /* synthetic */ AudioManager.AudioServerStateCallback f$0;

    public /* synthetic */ _$$Lambda$AudioManager$4$Q85LmhgKDCoq1YI14giFabZrM7A(AudioManager.AudioServerStateCallback audioServerStateCallback) {
        this.f$0 = audioServerStateCallback;
    }

    @Override
    public final void run() {
        AudioManager.4.lambda$dispatchAudioServerStateChange$0(this.f$0);
    }
}

