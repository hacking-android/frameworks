/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.contentsuggestions.-$
 *  android.service.contentsuggestions.-$$Lambda
 *  android.service.contentsuggestions.-$$Lambda$Mv-op4AGm9iWERwfXEFnqOVKWt0
 */
package android.service.contentsuggestions;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.service.contentsuggestions.-$;
import android.service.contentsuggestions.ContentSuggestionsService;
import com.android.internal.util.function.QuadConsumer;

public final class _$$Lambda$Mv_op4AGm9iWERwfXEFnqOVKWt0
implements QuadConsumer {
    public static final /* synthetic */ -$.Lambda.Mv-op4AGm9iWERwfXEFnqOVKWt0 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$Mv_op4AGm9iWERwfXEFnqOVKWt0();
    }

    private /* synthetic */ _$$Lambda$Mv_op4AGm9iWERwfXEFnqOVKWt0() {
    }

    public final void accept(Object object, Object object2, Object object3, Object object4) {
        ((ContentSuggestionsService)object).onProcessContextImage((Integer)object2, (Bitmap)object3, (Bundle)object4);
    }
}

