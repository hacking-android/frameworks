/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.inputmethodservice.-$
 *  android.inputmethodservice.-$$Lambda
 *  android.inputmethodservice.-$$Lambda$GapYa6Lyify6RwP-rgkklzmDV8I
 */
package android.inputmethodservice;

import android.inputmethodservice.-$;
import android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor;
import com.android.internal.util.function.TriConsumer;

public final class _$$Lambda$GapYa6Lyify6RwP_rgkklzmDV8I
implements TriConsumer {
    public static final /* synthetic */ -$.Lambda.GapYa6Lyify6RwP-rgkklzmDV8I INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$GapYa6Lyify6RwP_rgkklzmDV8I();
    }

    private /* synthetic */ _$$Lambda$GapYa6Lyify6RwP_rgkklzmDV8I() {
    }

    public final void accept(Object object, Object object2, Object object3) {
        ((MultiClientInputMethodClientCallbackAdaptor.CallbackImpl)object).toggleSoftInput((Integer)object2, (Integer)object3);
    }
}

