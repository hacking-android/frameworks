/*
 * Decompiled with CFR 0.145.
 */
package android.service.textclassifier;

import android.service.textclassifier.TextClassifierService;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.TextClassificationSessionId;

public final class _$$Lambda$TextClassifierService$1$_Nsl56ysLPoVPJ4Gu0VUwYCh4wE
implements Runnable {
    private final /* synthetic */ TextClassifierService.1 f$0;
    private final /* synthetic */ TextClassificationSessionId f$1;
    private final /* synthetic */ SelectionEvent f$2;

    public /* synthetic */ _$$Lambda$TextClassifierService$1$_Nsl56ysLPoVPJ4Gu0VUwYCh4wE(TextClassifierService.1 var1_1, TextClassificationSessionId textClassificationSessionId, SelectionEvent selectionEvent) {
        this.f$0 = var1_1;
        this.f$1 = textClassificationSessionId;
        this.f$2 = selectionEvent;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onSelectionEvent$3$TextClassifierService$1(this.f$1, this.f$2);
    }
}

