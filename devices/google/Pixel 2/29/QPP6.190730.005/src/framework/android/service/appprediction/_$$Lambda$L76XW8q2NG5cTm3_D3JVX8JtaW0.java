/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.appprediction.-$
 *  android.service.appprediction.-$$Lambda
 *  android.service.appprediction.-$$Lambda$L76XW8q2NG5cTm3_D3JVX8JtaW0
 */
package android.service.appprediction;

import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.AppTargetEvent;
import android.service.appprediction.-$;
import android.service.appprediction.AppPredictionService;
import com.android.internal.util.function.TriConsumer;

public final class _$$Lambda$L76XW8q2NG5cTm3_D3JVX8JtaW0
implements TriConsumer {
    public static final /* synthetic */ -$.Lambda.L76XW8q2NG5cTm3_D3JVX8JtaW0 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$L76XW8q2NG5cTm3_D3JVX8JtaW0();
    }

    private /* synthetic */ _$$Lambda$L76XW8q2NG5cTm3_D3JVX8JtaW0() {
    }

    public final void accept(Object object, Object object2, Object object3) {
        ((AppPredictionService)object).onAppTargetEvent((AppPredictionSessionId)object2, (AppTargetEvent)object3);
    }
}

