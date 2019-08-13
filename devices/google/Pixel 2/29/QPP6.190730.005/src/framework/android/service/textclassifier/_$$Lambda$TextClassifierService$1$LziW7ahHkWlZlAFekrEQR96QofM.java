/*
 * Decompiled with CFR 0.145.
 */
package android.service.textclassifier;

import android.service.textclassifier.ITextClassifierCallback;
import android.service.textclassifier.TextClassifierService;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationSessionId;

public final class _$$Lambda$TextClassifierService$1$LziW7ahHkWlZlAFekrEQR96QofM
implements Runnable {
    private final /* synthetic */ TextClassifierService.1 f$0;
    private final /* synthetic */ TextClassificationSessionId f$1;
    private final /* synthetic */ TextClassification.Request f$2;
    private final /* synthetic */ ITextClassifierCallback f$3;

    public /* synthetic */ _$$Lambda$TextClassifierService$1$LziW7ahHkWlZlAFekrEQR96QofM(TextClassifierService.1 var1_1, TextClassificationSessionId textClassificationSessionId, TextClassification.Request request, ITextClassifierCallback iTextClassifierCallback) {
        this.f$0 = var1_1;
        this.f$1 = textClassificationSessionId;
        this.f$2 = request;
        this.f$3 = iTextClassifierCallback;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onClassifyText$1$TextClassifierService$1(this.f$1, this.f$2, this.f$3);
    }
}

