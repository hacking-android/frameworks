/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jNtyZYh5ZAuvyDZA_6f30zhW_dI
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ PhoneStateListener.IPhoneStateListenerStub f$0;
    private final /* synthetic */ PhoneStateListener f$1;
    private final /* synthetic */ byte[] f$2;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jNtyZYh5ZAuvyDZA_6f30zhW_dI(PhoneStateListener.IPhoneStateListenerStub iPhoneStateListenerStub, PhoneStateListener phoneStateListener, byte[] arrby) {
        this.f$0 = iPhoneStateListenerStub;
        this.f$1 = phoneStateListener;
        this.f$2 = arrby;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onOemHookRawEvent$39$PhoneStateListener$IPhoneStateListenerStub(this.f$1, this.f$2);
    }
}

