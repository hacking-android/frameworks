/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.appprediction.-$
 *  android.service.appprediction.-$$Lambda
 *  android.service.appprediction.-$$Lambda$AppPredictionService
 *  android.service.appprediction.-$$Lambda$AppPredictionService$1
 *  android.service.appprediction.-$$Lambda$AppPredictionService$1$3o4A2wryMBwv4mIbcQKrEaoUyik
 */
package android.service.appprediction;

import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.IPredictionCallback;
import android.service.appprediction.-$;
import android.service.appprediction.AppPredictionService;
import com.android.internal.util.function.TriConsumer;

public final class _$$Lambda$AppPredictionService$1$3o4A2wryMBwv4mIbcQKrEaoUyik
implements TriConsumer {
    public static final /* synthetic */ -$.Lambda.AppPredictionService.1.3o4A2wryMBwv4mIbcQKrEaoUyik INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$AppPredictionService$1$3o4A2wryMBwv4mIbcQKrEaoUyik();
    }

    private /* synthetic */ _$$Lambda$AppPredictionService$1$3o4A2wryMBwv4mIbcQKrEaoUyik() {
    }

    public final void accept(Object object, Object object2, Object object3) {
        AppPredictionService.1.lambda$unregisterPredictionUpdates$2((AppPredictionService)object, (AppPredictionSessionId)object2, (IPredictionCallback)object3);
    }
}

