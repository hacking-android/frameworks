/*
 * Decompiled with CFR 0.145.
 */
package android.service.textclassifier;

import android.service.textclassifier.TextClassifierService;
import android.view.textclassifier.TextLanguage;

public final class _$$Lambda$TextClassifierService$9kfVuo6FJ1uQiU277_n9JgliEEc
implements Runnable {
    private final /* synthetic */ TextClassifierService f$0;
    private final /* synthetic */ TextClassifierService.Callback f$1;
    private final /* synthetic */ TextLanguage.Request f$2;

    public /* synthetic */ _$$Lambda$TextClassifierService$9kfVuo6FJ1uQiU277_n9JgliEEc(TextClassifierService textClassifierService, TextClassifierService.Callback callback, TextLanguage.Request request) {
        this.f$0 = textClassifierService;
        this.f$1 = callback;
        this.f$2 = request;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onDetectLanguage$0$TextClassifierService(this.f$1, this.f$2);
    }
}

