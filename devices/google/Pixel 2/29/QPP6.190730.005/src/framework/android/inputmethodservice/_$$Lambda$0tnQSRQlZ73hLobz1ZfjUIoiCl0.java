/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.inputmethodservice.-$
 *  android.inputmethodservice.-$$Lambda
 *  android.inputmethodservice.-$$Lambda$0tnQSRQlZ73hLobz1ZfjUIoiCl0
 */
package android.inputmethodservice;

import android.inputmethodservice.-$;
import android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor;
import android.os.ResultReceiver;
import com.android.internal.util.function.TriConsumer;

public final class _$$Lambda$0tnQSRQlZ73hLobz1ZfjUIoiCl0
implements TriConsumer {
    public static final /* synthetic */ -$.Lambda.0tnQSRQlZ73hLobz1ZfjUIoiCl0 INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$0tnQSRQlZ73hLobz1ZfjUIoiCl0();
    }

    private /* synthetic */ _$$Lambda$0tnQSRQlZ73hLobz1ZfjUIoiCl0() {
    }

    public final void accept(Object object, Object object2, Object object3) {
        ((MultiClientInputMethodClientCallbackAdaptor.CallbackImpl)object).hideSoftInput((Integer)object2, (ResultReceiver)object3);
    }
}

