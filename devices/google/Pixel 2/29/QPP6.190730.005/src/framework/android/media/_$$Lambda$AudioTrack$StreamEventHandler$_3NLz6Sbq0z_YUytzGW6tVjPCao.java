/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioTrack;

public final class _$$Lambda$AudioTrack$StreamEventHandler$_3NLz6Sbq0z_YUytzGW6tVjPCao
implements Runnable {
    private final /* synthetic */ AudioTrack.StreamEventHandler f$0;
    private final /* synthetic */ AudioTrack.StreamEventCbInfo f$1;

    public /* synthetic */ _$$Lambda$AudioTrack$StreamEventHandler$_3NLz6Sbq0z_YUytzGW6tVjPCao(AudioTrack.StreamEventHandler streamEventHandler, AudioTrack.StreamEventCbInfo streamEventCbInfo) {
        this.f$0 = streamEventHandler;
        this.f$1 = streamEventCbInfo;
    }

    @Override
    public final void run() {
        this.f$0.lambda$handleMessage$2$AudioTrack$StreamEventHandler(this.f$1);
    }
}

