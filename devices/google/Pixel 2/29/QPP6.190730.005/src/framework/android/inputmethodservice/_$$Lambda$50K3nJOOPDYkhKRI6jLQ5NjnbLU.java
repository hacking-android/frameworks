/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.inputmethodservice.-$
 *  android.inputmethodservice.-$$Lambda
 *  android.inputmethodservice.-$$Lambda$50K3nJOOPDYkhKRI6jLQ5NjnbLU
 */
package android.inputmethodservice;

import android.inputmethodservice.-$;
import android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor;
import java.util.function.Consumer;

public final class _$$Lambda$50K3nJOOPDYkhKRI6jLQ5NjnbLU
implements Consumer {
    public static final /* synthetic */ -$.Lambda.50K3nJOOPDYkhKRI6jLQ5NjnbLU INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$50K3nJOOPDYkhKRI6jLQ5NjnbLU();
    }

    private /* synthetic */ _$$Lambda$50K3nJOOPDYkhKRI6jLQ5NjnbLU() {
    }

    public final void accept(Object object) {
        ((MultiClientInputMethodClientCallbackAdaptor.CallbackImpl)object).finishSession();
    }
}

