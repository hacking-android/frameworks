/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.inputmethodservice.-$
 *  android.inputmethodservice.-$$Lambda
 *  android.inputmethodservice.-$$Lambda$BAvs3tw1MzE4gOJqYOA5MCJasPE
 */
package android.inputmethodservice;

import android.inputmethodservice.-$;
import android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor;
import android.view.inputmethod.CursorAnchorInfo;
import java.util.function.BiConsumer;

public final class _$$Lambda$BAvs3tw1MzE4gOJqYOA5MCJasPE
implements BiConsumer {
    public static final /* synthetic */ -$.Lambda.BAvs3tw1MzE4gOJqYOA5MCJasPE INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$BAvs3tw1MzE4gOJqYOA5MCJasPE();
    }

    private /* synthetic */ _$$Lambda$BAvs3tw1MzE4gOJqYOA5MCJasPE() {
    }

    public final void accept(Object object, Object object2) {
        ((MultiClientInputMethodClientCallbackAdaptor.CallbackImpl)object).updateCursorAnchorInfo((CursorAnchorInfo)object2);
    }
}

