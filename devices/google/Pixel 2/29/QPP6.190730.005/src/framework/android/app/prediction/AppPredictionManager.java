/*
 * Decompiled with CFR 0.145.
 */
package android.app.prediction;

import android.annotation.SystemApi;
import android.app.prediction.AppPredictionContext;
import android.app.prediction.AppPredictor;
import android.content.Context;
import com.android.internal.util.Preconditions;

@SystemApi
public final class AppPredictionManager {
    private final Context mContext;

    public AppPredictionManager(Context context) {
        this.mContext = Preconditions.checkNotNull(context);
    }

    public AppPredictor createAppPredictionSession(AppPredictionContext appPredictionContext) {
        return new AppPredictor(this.mContext, appPredictionContext);
    }
}

