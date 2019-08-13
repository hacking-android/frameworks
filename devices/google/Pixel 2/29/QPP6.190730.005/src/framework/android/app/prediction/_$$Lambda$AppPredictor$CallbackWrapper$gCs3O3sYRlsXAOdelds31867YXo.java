/*
 * Decompiled with CFR 0.145.
 */
package android.app.prediction;

import android.app.prediction.AppPredictor;
import android.content.pm.ParceledListSlice;

public final class _$$Lambda$AppPredictor$CallbackWrapper$gCs3O3sYRlsXAOdelds31867YXo
implements Runnable {
    private final /* synthetic */ AppPredictor.CallbackWrapper f$0;
    private final /* synthetic */ ParceledListSlice f$1;

    public /* synthetic */ _$$Lambda$AppPredictor$CallbackWrapper$gCs3O3sYRlsXAOdelds31867YXo(AppPredictor.CallbackWrapper callbackWrapper, ParceledListSlice parceledListSlice) {
        this.f$0 = callbackWrapper;
        this.f$1 = parceledListSlice;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onResult$0$AppPredictor$CallbackWrapper(this.f$1);
    }
}

