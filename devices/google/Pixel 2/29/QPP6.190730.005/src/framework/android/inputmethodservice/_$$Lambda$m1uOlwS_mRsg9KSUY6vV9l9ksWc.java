/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.inputmethodservice.-$
 *  android.inputmethodservice.-$$Lambda
 *  android.inputmethodservice.-$$Lambda$m1uOlwS-mRsg9KSUY6vV9l9ksWc
 */
package android.inputmethodservice;

import android.inputmethodservice.-$;
import android.inputmethodservice.MultiClientInputMethodClientCallbackAdaptor;
import android.os.ResultReceiver;
import com.android.internal.util.function.TriConsumer;

public final class _$$Lambda$m1uOlwS_mRsg9KSUY6vV9l9ksWc
implements TriConsumer {
    public static final /* synthetic */ -$.Lambda.m1uOlwS-mRsg9KSUY6vV9l9ksWc INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$m1uOlwS_mRsg9KSUY6vV9l9ksWc();
    }

    private /* synthetic */ _$$Lambda$m1uOlwS_mRsg9KSUY6vV9l9ksWc() {
    }

    public final void accept(Object object, Object object2, Object object3) {
        ((MultiClientInputMethodClientCallbackAdaptor.CallbackImpl)object).showSoftInput((Integer)object2, (ResultReceiver)object3);
    }
}

