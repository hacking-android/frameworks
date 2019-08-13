/*
 * Decompiled with CFR 0.145.
 */
package android.app.contentsuggestions;

import android.app.contentsuggestions.ContentSuggestionsManager;
import java.util.List;

public final class _$$Lambda$ContentSuggestionsManager$SelectionsCallbackWrapper$1Py0lukljawDYbfwobeRIUDvpNM
implements Runnable {
    private final /* synthetic */ ContentSuggestionsManager.SelectionsCallbackWrapper f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ List f$2;

    public /* synthetic */ _$$Lambda$ContentSuggestionsManager$SelectionsCallbackWrapper$1Py0lukljawDYbfwobeRIUDvpNM(ContentSuggestionsManager.SelectionsCallbackWrapper selectionsCallbackWrapper, int n, List list) {
        this.f$0 = selectionsCallbackWrapper;
        this.f$1 = n;
        this.f$2 = list;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onContentSelectionsAvailable$0$ContentSuggestionsManager$SelectionsCallbackWrapper(this.f$1, this.f$2);
    }
}

