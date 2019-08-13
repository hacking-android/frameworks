/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.prediction.AppPredictor;
import com.android.internal.app.ChooserActivity;
import java.util.List;

public final class _$$Lambda$ChooserActivity$DtVuD6Mjmx25X89cZXV33qf48uk
implements AppPredictor.Callback {
    private final /* synthetic */ ChooserActivity f$0;

    public /* synthetic */ _$$Lambda$ChooserActivity$DtVuD6Mjmx25X89cZXV33qf48uk(ChooserActivity chooserActivity) {
        this.f$0 = chooserActivity;
    }

    public final void onTargetsAvailable(List list) {
        this.f$0.lambda$onCreate$0$ChooserActivity(list);
    }
}

