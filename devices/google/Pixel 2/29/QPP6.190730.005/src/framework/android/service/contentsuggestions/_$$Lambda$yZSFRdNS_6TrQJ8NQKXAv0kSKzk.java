/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.contentsuggestions.-$
 *  android.service.contentsuggestions.-$$Lambda
 *  android.service.contentsuggestions.-$$Lambda$yZSFRdNS_6TrQJ8NQKXAv0kSKzk
 */
package android.service.contentsuggestions;

import android.app.contentsuggestions.ContentSuggestionsManager;
import android.app.contentsuggestions.SelectionsRequest;
import android.service.contentsuggestions.-$;
import android.service.contentsuggestions.ContentSuggestionsService;
import com.android.internal.util.function.TriConsumer;

public final class _$$Lambda$yZSFRdNS_6TrQJ8NQKXAv0kSKzk
implements TriConsumer {
    public static final /* synthetic */ -$.Lambda.yZSFRdNS_6TrQJ8NQKXAv0kSKzk INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$yZSFRdNS_6TrQJ8NQKXAv0kSKzk();
    }

    private /* synthetic */ _$$Lambda$yZSFRdNS_6TrQJ8NQKXAv0kSKzk() {
    }

    public final void accept(Object object, Object object2, Object object3) {
        ((ContentSuggestionsService)object).onSuggestContentSelections((SelectionsRequest)object2, (ContentSuggestionsManager.SelectionsCallback)object3);
    }
}

