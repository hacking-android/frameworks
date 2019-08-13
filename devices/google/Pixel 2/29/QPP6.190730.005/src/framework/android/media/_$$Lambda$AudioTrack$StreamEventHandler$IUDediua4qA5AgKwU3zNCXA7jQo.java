/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioTrack;
import android.os.Message;

public final class _$$Lambda$AudioTrack$StreamEventHandler$IUDediua4qA5AgKwU3zNCXA7jQo
implements Runnable {
    private final /* synthetic */ AudioTrack.StreamEventHandler f$0;
    private final /* synthetic */ AudioTrack.StreamEventCbInfo f$1;
    private final /* synthetic */ Message f$2;

    public /* synthetic */ _$$Lambda$AudioTrack$StreamEventHandler$IUDediua4qA5AgKwU3zNCXA7jQo(AudioTrack.StreamEventHandler streamEventHandler, AudioTrack.StreamEventCbInfo streamEventCbInfo, Message message) {
        this.f$0 = streamEventHandler;
        this.f$1 = streamEventCbInfo;
        this.f$2 = message;
    }

    @Override
    public final void run() {
        this.f$0.lambda$handleMessage$0$AudioTrack$StreamEventHandler(this.f$1, this.f$2);
    }
}

