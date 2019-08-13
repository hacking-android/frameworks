/*
 * Decompiled with CFR 0.145.
 */
package android.service.contentsuggestions;

import android.app.contentsuggestions.ContentSuggestionsManager;
import android.app.contentsuggestions.ISelectionsCallback;
import android.service.contentsuggestions.ContentSuggestionsService;
import java.util.List;

public final class _$$Lambda$ContentSuggestionsService$Cq6WuwbJQLqgS0UnqLBYUMft1GM
implements ContentSuggestionsManager.SelectionsCallback {
    private final /* synthetic */ ISelectionsCallback f$0;

    public /* synthetic */ _$$Lambda$ContentSuggestionsService$Cq6WuwbJQLqgS0UnqLBYUMft1GM(ISelectionsCallback iSelectionsCallback) {
        this.f$0 = iSelectionsCallback;
    }

    public final void onContentSelectionsAvailable(int n, List list) {
        ContentSuggestionsService.lambda$wrapSelectionsCallback$0(this.f$0, n, list);
    }
}

