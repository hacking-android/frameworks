/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.appprediction.-$
 *  android.service.appprediction.-$$Lambda
 *  android.service.appprediction.-$$Lambda$GvHA1SFwOCThMjcs4Yg4JTLin4Y
 */
package android.service.appprediction;

import android.app.prediction.AppPredictionSessionId;
import android.app.prediction.AppTargetId;
import android.service.appprediction.-$;
import android.service.appprediction.AppPredictionService;
import com.android.internal.util.function.QuadConsumer;
import java.util.List;

public final class _$$Lambda$GvHA1SFwOCThMjcs4Yg4JTLin4Y
implements QuadConsumer {
    public static final /* synthetic */ -$.Lambda.GvHA1SFwOCThMjcs4Yg4JTLin4Y INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$GvHA1SFwOCThMjcs4Yg4JTLin4Y();
    }

    private /* synthetic */ _$$Lambda$GvHA1SFwOCThMjcs4Yg4JTLin4Y() {
    }

    public final void accept(Object object, Object object2, Object object3, Object object4) {
        ((AppPredictionService)object).onLaunchLocationShown((AppPredictionSessionId)object2, (String)object3, (List)object4);
    }
}

