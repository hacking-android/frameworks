/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.inputmethodservice.-$
 *  android.inputmethodservice.-$$Lambda
 *  android.inputmethodservice.-$$Lambda$RawqPImrGiEy8dXqjapbiFcFS9w
 */
package android.inputmethodservice;

import android.inputmethodservice.-$;
import android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor;
import android.view.inputmethod.CompletionInfo;
import java.util.function.BiConsumer;

public final class _$$Lambda$RawqPImrGiEy8dXqjapbiFcFS9w
implements BiConsumer {
    public static final /* synthetic */ -$.Lambda.RawqPImrGiEy8dXqjapbiFcFS9w INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$RawqPImrGiEy8dXqjapbiFcFS9w();
    }

    private /* synthetic */ _$$Lambda$RawqPImrGiEy8dXqjapbiFcFS9w() {
    }

    public final void accept(Object object, Object object2) {
        ((MultiClientInputMethodClientCallbackAdaptor.CallbackImpl)object).displayCompletions((CompletionInfo[])object2);
    }
}

