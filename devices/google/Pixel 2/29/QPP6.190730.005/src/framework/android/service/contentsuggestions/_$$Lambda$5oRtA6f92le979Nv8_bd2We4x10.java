/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.contentsuggestions.-$
 *  android.service.contentsuggestions.-$$Lambda
 *  android.service.contentsuggestions.-$$Lambda$5oRtA6f92le979Nv8-bd2We4x10
 */
package android.service.contentsuggestions;

import android.app.contentsuggestions.ClassificationsRequest;
import android.app.contentsuggestions.ContentSuggestionsManager;
import android.service.contentsuggestions.-$;
import android.service.contentsuggestions.ContentSuggestionsService;
import com.android.internal.util.function.TriConsumer;

public final class _$$Lambda$5oRtA6f92le979Nv8_bd2We4x10
implements TriConsumer {
    public static final /* synthetic */ -$.Lambda.5oRtA6f92le979Nv8-bd2We4x10 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$5oRtA6f92le979Nv8_bd2We4x10();
    }

    private /* synthetic */ _$$Lambda$5oRtA6f92le979Nv8_bd2We4x10() {
    }

    public final void accept(Object object, Object object2, Object object3) {
        ((ContentSuggestionsService)object).onClassifyContentSelections((ClassificationsRequest)object2, (ContentSuggestionsManager.ClassificationsCallback)object3);
    }
}

