/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.inputmethodservice.-$
 *  android.inputmethodservice.-$$Lambda
 *  android.inputmethodservice.-$$Lambda$zVy_pAXuQfncxA_AL_8DWyVpYXc
 */
package android.inputmethodservice;

import android.inputmethodservice.-$;
import android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor;
import com.android.internal.os.SomeArgs;
import java.util.function.BiConsumer;

public final class _$$Lambda$zVy_pAXuQfncxA_AL_8DWyVpYXc
implements BiConsumer {
    public static final /* synthetic */ -$.Lambda.zVy_pAXuQfncxA_AL_8DWyVpYXc INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$zVy_pAXuQfncxA_AL_8DWyVpYXc();
    }

    private /* synthetic */ _$$Lambda$zVy_pAXuQfncxA_AL_8DWyVpYXc() {
    }

    public final void accept(Object object, Object object2) {
        ((MultiClientInputMethodClientCallbackAdaptor.CallbackImpl)object).updateSelection((SomeArgs)object2);
    }
}

