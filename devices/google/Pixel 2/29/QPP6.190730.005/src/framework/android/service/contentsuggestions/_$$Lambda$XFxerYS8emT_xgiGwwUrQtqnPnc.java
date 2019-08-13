/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.contentsuggestions.-$
 *  android.service.contentsuggestions.-$$Lambda
 *  android.service.contentsuggestions.-$$Lambda$XFxerYS8emT_xgiGwwUrQtqnPnc
 */
package android.service.contentsuggestions;

import android.os.Bundle;
import android.service.contentsuggestions.-$;
import android.service.contentsuggestions.ContentSuggestionsService;
import com.android.internal.util.function.TriConsumer;

public final class _$$Lambda$XFxerYS8emT_xgiGwwUrQtqnPnc
implements TriConsumer {
    public static final /* synthetic */ -$.Lambda.XFxerYS8emT_xgiGwwUrQtqnPnc INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$XFxerYS8emT_xgiGwwUrQtqnPnc();
    }

    private /* synthetic */ _$$Lambda$XFxerYS8emT_xgiGwwUrQtqnPnc() {
    }

    public final void accept(Object object, Object object2, Object object3) {
        ((ContentSuggestionsService)object).onNotifyInteraction((String)object2, (Bundle)object3);
    }
}

