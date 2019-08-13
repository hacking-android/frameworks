/*
 * Decompiled with CFR 0.145.
 */
package android.service.voice;

import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import java.util.function.Consumer;

public final class _$$Lambda$VoiceInteractionSession$sg0qPgWHpOBD2lLJ7BGNEVSsBdo
implements Runnable {
    private final /* synthetic */ Consumer f$0;
    private final /* synthetic */ Bundle f$1;

    public /* synthetic */ _$$Lambda$VoiceInteractionSession$sg0qPgWHpOBD2lLJ7BGNEVSsBdo(Consumer consumer, Bundle bundle) {
        this.f$0 = consumer;
        this.f$1 = bundle;
    }

    @Override
    public final void run() {
        VoiceInteractionSession.lambda$performDirectAction$4(this.f$0, this.f$1);
    }
}

