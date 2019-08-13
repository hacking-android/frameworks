/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.app.PendingIntent;
import android.view.View;
import android.view.textclassifier.TextClassification;

public final class _$$Lambda$TextClassification$ysasaE5ZkXkkzjVWIJ06GTV92_g
implements View.OnClickListener {
    private final /* synthetic */ PendingIntent f$0;

    public /* synthetic */ _$$Lambda$TextClassification$ysasaE5ZkXkkzjVWIJ06GTV92_g(PendingIntent pendingIntent) {
        this.f$0 = pendingIntent;
    }

    @Override
    public final void onClick(View view) {
        TextClassification.lambda$createIntentOnClickListener$0(this.f$0, view);
    }
}

