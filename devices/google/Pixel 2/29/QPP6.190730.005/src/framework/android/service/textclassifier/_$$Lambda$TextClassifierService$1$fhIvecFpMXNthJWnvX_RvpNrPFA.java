/*
 * Decompiled with CFR 0.145.
 */
package android.service.textclassifier;

import android.service.textclassifier.TextClassifierService;
import android.view.textclassifier.TextClassificationSessionId;

public final class _$$Lambda$TextClassifierService$1$fhIvecFpMXNthJWnvX_RvpNrPFA
implements Runnable {
    private final /* synthetic */ TextClassifierService.1 f$0;
    private final /* synthetic */ TextClassificationSessionId f$1;

    public /* synthetic */ _$$Lambda$TextClassifierService$1$fhIvecFpMXNthJWnvX_RvpNrPFA(TextClassifierService.1 var1_1, TextClassificationSessionId textClassificationSessionId) {
        this.f$0 = var1_1;
        this.f$1 = textClassificationSessionId;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onDestroyTextClassificationSession$8$TextClassifierService$1(this.f$1);
    }
}

