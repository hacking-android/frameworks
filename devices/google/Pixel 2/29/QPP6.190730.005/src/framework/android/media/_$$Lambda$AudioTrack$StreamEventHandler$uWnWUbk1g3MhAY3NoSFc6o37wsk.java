/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioTrack;

public final class _$$Lambda$AudioTrack$StreamEventHandler$uWnWUbk1g3MhAY3NoSFc6o37wsk
implements Runnable {
    private final /* synthetic */ AudioTrack.StreamEventHandler f$0;
    private final /* synthetic */ AudioTrack.StreamEventCbInfo f$1;

    public /* synthetic */ _$$Lambda$AudioTrack$StreamEventHandler$uWnWUbk1g3MhAY3NoSFc6o37wsk(AudioTrack.StreamEventHandler streamEventHandler, AudioTrack.StreamEventCbInfo streamEventCbInfo) {
        this.f$0 = streamEventHandler;
        this.f$1 = streamEventCbInfo;
    }

    @Override
    public final void run() {
        this.f$0.lambda$handleMessage$1$AudioTrack$StreamEventHandler(this.f$1);
    }
}

