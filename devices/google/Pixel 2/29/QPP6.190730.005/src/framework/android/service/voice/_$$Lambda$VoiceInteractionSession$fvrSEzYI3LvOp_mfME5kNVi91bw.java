/*
 * Decompiled with CFR 0.145.
 */
package android.service.voice;

import android.service.voice.VoiceInteractionSession;
import java.util.List;
import java.util.function.Consumer;

public final class _$$Lambda$VoiceInteractionSession$fvrSEzYI3LvOp_mfME5kNVi91bw
implements Runnable {
    private final /* synthetic */ Consumer f$0;
    private final /* synthetic */ List f$1;

    public /* synthetic */ _$$Lambda$VoiceInteractionSession$fvrSEzYI3LvOp_mfME5kNVi91bw(Consumer consumer, List list) {
        this.f$0 = consumer;
        this.f$1 = list;
    }

    @Override
    public final void run() {
        VoiceInteractionSession.lambda$requestDirectActions$1(this.f$0, this.f$1);
    }
}

