/*
 * Decompiled with CFR 0.145.
 */
package android.service.voice;

import android.os.Bundle;
import android.os.CancellationSignal;
import android.service.voice.VoiceInteractionSession;
import java.util.function.Consumer;

public final class _$$Lambda$VoiceInteractionSession$2YI2merL0_kdgL83g93OW541J8w
implements Consumer {
    private final /* synthetic */ CancellationSignal f$0;

    public /* synthetic */ _$$Lambda$VoiceInteractionSession$2YI2merL0_kdgL83g93OW541J8w(CancellationSignal cancellationSignal) {
        this.f$0 = cancellationSignal;
    }

    public final void accept(Object object) {
        VoiceInteractionSession.lambda$performDirectAction$3(this.f$0, (Bundle)object);
    }
}

