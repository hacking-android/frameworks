/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.appprediction.-$
 *  android.service.appprediction.-$$Lambda
 *  android.service.appprediction.-$$Lambda$hL9oFxwFQPM7PIyu9fQyFqB_mBk
 */
package android.service.appprediction;

import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.AppTarget;
import android.os.CancellationSignal;
import android.service.appprediction.-$;
import android.service.appprediction.AppPredictionService;
import com.android.internal.util.function.QuintConsumer;
import java.util.List;
import java.util.function.Consumer;

public final class _$$Lambda$hL9oFxwFQPM7PIyu9fQyFqB_mBk
implements QuintConsumer {
    public static final /* synthetic */ -$.Lambda.hL9oFxwFQPM7PIyu9fQyFqB_mBk INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$hL9oFxwFQPM7PIyu9fQyFqB_mBk();
    }

    private /* synthetic */ _$$Lambda$hL9oFxwFQPM7PIyu9fQyFqB_mBk() {
    }

    public final void accept(Object object, Object object2, Object object3, Object object4, Object object5) {
        ((AppPredictionService)object).onSortAppTargets((AppPredictionSessionId)object2, (List)object3, (CancellationSignal)object4, (AppPredictionService.CallbackWrapper)object5);
    }
}

