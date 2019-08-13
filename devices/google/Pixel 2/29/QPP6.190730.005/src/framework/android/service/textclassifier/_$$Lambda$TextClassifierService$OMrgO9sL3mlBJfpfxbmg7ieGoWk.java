/*
 * Decompiled with CFR 0.145.
 */
package android.service.textclassifier;

import android.service.textclassifier.TextClassifierService;
import android.view.textclassifier.ConversationActions;

public final class _$$Lambda$TextClassifierService$OMrgO9sL3mlBJfpfxbmg7ieGoWk
implements Runnable {
    private final /* synthetic */ TextClassifierService f$0;
    private final /* synthetic */ TextClassifierService.Callback f$1;
    private final /* synthetic */ ConversationActions.Request f$2;

    public /* synthetic */ _$$Lambda$TextClassifierService$OMrgO9sL3mlBJfpfxbmg7ieGoWk(TextClassifierService textClassifierService, TextClassifierService.Callback callback, ConversationActions.Request request) {
        this.f$0 = textClassifierService;
        this.f$1 = callback;
        this.f$2 = request;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onSuggestConversationActions$1$TextClassifierService(this.f$1, this.f$2);
    }
}

