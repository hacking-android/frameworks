/*
 * Decompiled with CFR 0.145.
 */
package android.service.contentsuggestions;

import android.app.contentsuggestions.ContentSuggestionsManager;
import android.app.contentsuggestions.IClassificationsCallback;
import android.service.contentsuggestions.ContentSuggestionsService;
import java.util.List;

public final class _$$Lambda$ContentSuggestionsService$EMLezZyRGdfK3m_N1TAvrHKUEII
implements ContentSuggestionsManager.ClassificationsCallback {
    private final /* synthetic */ IClassificationsCallback f$0;

    public /* synthetic */ _$$Lambda$ContentSuggestionsService$EMLezZyRGdfK3m_N1TAvrHKUEII(IClassificationsCallback iClassificationsCallback) {
        this.f$0 = iClassificationsCallback;
    }

    public final void onContentClassificationsAvailable(int n, List list) {
        ContentSuggestionsService.lambda$wrapClassificationCallback$1(this.f$0, n, list);
    }
}

