/*
 * Decompiled with CFR 0.145.
 */
package android.service.voice;

import android.service.voice.VoiceInteractionSession;
import java.util.function.Consumer;

public final class _$$Lambda$VoiceInteractionSession$bujvs7MJfXO9xSx9M8NS3hINZ_k
implements Runnable {
    private final /* synthetic */ Consumer f$0;

    public /* synthetic */ _$$Lambda$VoiceInteractionSession$bujvs7MJfXO9xSx9M8NS3hINZ_k(Consumer consumer) {
        this.f$0 = consumer;
    }

    @Override
    public final void run() {
        VoiceInteractionSession.lambda$performDirectAction$5(this.f$0);
    }
}

