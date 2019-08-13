/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.inputmethodservice.-$
 *  android.inputmethodservice.-$$Lambda
 *  android.inputmethodservice.-$$Lambda$nzQNVb4Z0e33hB95nNP1BM-A3r4
 */
package android.inputmethodservice;

import android.inputmethodservice.-$;
import android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor;
import android.os.Bundle;
import com.android.internal.util.function.TriConsumer;

public final class _$$Lambda$nzQNVb4Z0e33hB95nNP1BM_A3r4
implements TriConsumer {
    public static final /* synthetic */ -$.Lambda.nzQNVb4Z0e33hB95nNP1BM-A3r4 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$nzQNVb4Z0e33hB95nNP1BM_A3r4();
    }

    private /* synthetic */ _$$Lambda$nzQNVb4Z0e33hB95nNP1BM_A3r4() {
    }

    public final void accept(Object object, Object object2, Object object3) {
        ((MultiClientInputMethodClientCallbackAdaptor.CallbackImpl)object).appPrivateCommand((String)object2, (Bundle)object3);
    }
}

