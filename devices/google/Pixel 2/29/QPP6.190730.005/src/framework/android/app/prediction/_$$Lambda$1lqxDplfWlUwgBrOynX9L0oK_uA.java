/*
 * Decompiled with CFR 0.145.
 */
package android.app.prediction;

import android.app.prediction.AppPredictor;
import android.app.prediction.AppTarget;
import java.util.List;
import java.util.function.Consumer;

public final class _$$Lambda$1lqxDplfWlUwgBrOynX9L0oK_uA
implements Consumer {
    private final /* synthetic */ AppPredictor.Callback f$0;

    public /* synthetic */ _$$Lambda$1lqxDplfWlUwgBrOynX9L0oK_uA(AppPredictor.Callback callback) {
        this.f$0 = callback;
    }

    public final void accept(Object object) {
        this.f$0.onTargetsAvailable((List)object);
    }
}

