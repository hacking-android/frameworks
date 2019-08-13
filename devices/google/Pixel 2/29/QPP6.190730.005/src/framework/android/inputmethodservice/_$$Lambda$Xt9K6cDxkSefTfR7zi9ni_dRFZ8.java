/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.inputmethodservice.-$
 *  android.inputmethodservice.-$$Lambda
 *  android.inputmethodservice.-$$Lambda$Xt9K6cDxkSefTfR7zi9ni-dRFZ8
 */
package android.inputmethodservice;

import android.inputmethodservice.-$;
import android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor;
import com.android.internal.os.SomeArgs;
import java.util.function.BiConsumer;

public final class _$$Lambda$Xt9K6cDxkSefTfR7zi9ni_dRFZ8
implements BiConsumer {
    public static final /* synthetic */ -$.Lambda.Xt9K6cDxkSefTfR7zi9ni-dRFZ8 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$Xt9K6cDxkSefTfR7zi9ni_dRFZ8();
    }

    private /* synthetic */ _$$Lambda$Xt9K6cDxkSefTfR7zi9ni_dRFZ8() {
    }

    public final void accept(Object object, Object object2) {
        ((MultiClientInputMethodClientCallbackAdaptor.CallbackImpl)object).startInputOrWindowGainedFocus((SomeArgs)object2);
    }
}

