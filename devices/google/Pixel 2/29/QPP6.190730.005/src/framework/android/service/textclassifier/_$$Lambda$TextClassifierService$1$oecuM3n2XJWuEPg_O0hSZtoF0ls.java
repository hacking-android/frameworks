/*
 * Decompiled with CFR 0.145.
 */
package android.service.textclassifier;

import android.service.textclassifier.TextClassifierService;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationSessionId;

public final class _$$Lambda$TextClassifierService$1$oecuM3n2XJWuEPg_O0hSZtoF0ls
implements Runnable {
    private final /* synthetic */ TextClassifierService.1 f$0;
    private final /* synthetic */ TextClassificationContext f$1;
    private final /* synthetic */ TextClassificationSessionId f$2;

    public /* synthetic */ _$$Lambda$TextClassifierService$1$oecuM3n2XJWuEPg_O0hSZtoF0ls(TextClassifierService.1 var1_1, TextClassificationContext textClassificationContext, TextClassificationSessionId textClassificationSessionId) {
        this.f$0 = var1_1;
        this.f$1 = textClassificationContext;
        this.f$2 = textClassificationSessionId;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onCreateTextClassificationSession$7$TextClassifierService$1(this.f$1, this.f$2);
    }
}

