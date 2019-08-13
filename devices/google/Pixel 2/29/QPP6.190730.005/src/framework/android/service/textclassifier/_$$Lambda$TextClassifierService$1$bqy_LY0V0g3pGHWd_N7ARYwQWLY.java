/*
 * Decompiled with CFR 0.145.
 */
package android.service.textclassifier;

import android.service.textclassifier.TextClassifierService;
import android.view.textclassifier.TextClassificationSessionId;
import android.view.textclassifier.TextClassifierEvent;

public final class _$$Lambda$TextClassifierService$1$bqy_LY0V0g3pGHWd_N7ARYwQWLY
implements Runnable {
    private final /* synthetic */ TextClassifierService.1 f$0;
    private final /* synthetic */ TextClassificationSessionId f$1;
    private final /* synthetic */ TextClassifierEvent f$2;

    public /* synthetic */ _$$Lambda$TextClassifierService$1$bqy_LY0V0g3pGHWd_N7ARYwQWLY(TextClassifierService.1 var1_1, TextClassificationSessionId textClassificationSessionId, TextClassifierEvent textClassifierEvent) {
        this.f$0 = var1_1;
        this.f$1 = textClassificationSessionId;
        this.f$2 = textClassifierEvent;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onTextClassifierEvent$4$TextClassifierService$1(this.f$1, this.f$2);
    }
}

