/*
 * Decompiled with CFR 0.145.
 */
package android.service.voice;

import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class _$$Lambda$VoiceInteractionSession$9GV3ALC6LWOMyg5zazTo6TodsHU
implements Consumer {
    private final /* synthetic */ Executor f$0;
    private final /* synthetic */ Consumer f$1;

    public /* synthetic */ _$$Lambda$VoiceInteractionSession$9GV3ALC6LWOMyg5zazTo6TodsHU(Executor executor, Consumer consumer) {
        this.f$0 = executor;
        this.f$1 = consumer;
    }

    public final void accept(Object object) {
        VoiceInteractionSession.lambda$performDirectAction$6(this.f$0, this.f$1, (Bundle)object);
    }
}

