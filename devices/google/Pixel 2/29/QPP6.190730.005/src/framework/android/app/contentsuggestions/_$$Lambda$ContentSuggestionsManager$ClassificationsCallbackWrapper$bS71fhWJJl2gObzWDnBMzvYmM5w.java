/*
 * Decompiled with CFR 0.145.
 */
package android.app.contentsuggestions;

import android.app.contentsuggestions.ContentSuggestionsManager;
import java.util.List;

public final class _$$Lambda$ContentSuggestionsManager$ClassificationsCallbackWrapper$bS71fhWJJl2gObzWDnBMzvYmM5w
implements Runnable {
    private final /* synthetic */ ContentSuggestionsManager.ClassificationsCallbackWrapper f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ List f$2;

    public /* synthetic */ _$$Lambda$ContentSuggestionsManager$ClassificationsCallbackWrapper$bS71fhWJJl2gObzWDnBMzvYmM5w(ContentSuggestionsManager.ClassificationsCallbackWrapper classificationsCallbackWrapper, int n, List list) {
        this.f$0 = classificationsCallbackWrapper;
        this.f$1 = n;
        this.f$2 = list;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onContentClassificationsAvailable$0$ContentSuggestionsManager$ClassificationsCallbackWrapper(this.f$1, this.f$2);
    }
}

