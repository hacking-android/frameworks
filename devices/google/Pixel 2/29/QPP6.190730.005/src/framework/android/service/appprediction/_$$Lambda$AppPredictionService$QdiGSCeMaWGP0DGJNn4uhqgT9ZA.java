/*
 * Decompiled with CFR 0.145.
 */
package android.service.appprediction;

import android.service.appprediction.AppPredictionService;
import java.util.ArrayList;

public final class _$$Lambda$AppPredictionService$QdiGSCeMaWGP0DGJNn4uhqgT9ZA
implements Runnable {
    private final /* synthetic */ AppPredictionService f$0;
    private final /* synthetic */ ArrayList f$1;
    private final /* synthetic */ AppPredictionService.CallbackWrapper f$2;

    public /* synthetic */ _$$Lambda$AppPredictionService$QdiGSCeMaWGP0DGJNn4uhqgT9ZA(AppPredictionService appPredictionService, ArrayList arrayList, AppPredictionService.CallbackWrapper callbackWrapper) {
        this.f$0 = appPredictionService;
        this.f$1 = arrayList;
        this.f$2 = callbackWrapper;
    }

    @Override
    public final void run() {
        this.f$0.lambda$doRegisterPredictionUpdates$0$AppPredictionService(this.f$1, this.f$2);
    }
}

