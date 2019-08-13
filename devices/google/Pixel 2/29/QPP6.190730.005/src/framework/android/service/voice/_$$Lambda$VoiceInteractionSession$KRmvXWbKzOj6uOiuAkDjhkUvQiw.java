/*
 * Decompiled with CFR 0.145.
 */
package android.service.voice;

import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.RemoteCallback;
import android.service.voice.VoiceInteractionSession;

public final class _$$Lambda$VoiceInteractionSession$KRmvXWbKzOj6uOiuAkDjhkUvQiw
implements RemoteCallback.OnResultListener {
    private final /* synthetic */ CancellationSignal f$0;

    public /* synthetic */ _$$Lambda$VoiceInteractionSession$KRmvXWbKzOj6uOiuAkDjhkUvQiw(CancellationSignal cancellationSignal) {
        this.f$0 = cancellationSignal;
    }

    @Override
    public final void onResult(Bundle bundle) {
        VoiceInteractionSession.lambda$requestDirectActions$0(this.f$0, bundle);
    }
}

