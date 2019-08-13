/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;
import android.telephony.PreciseCallState;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$bELzxgwsPigyVKYkAXBO2BjcSm8
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ PhoneStateListener.IPhoneStateListenerStub f$0;
    private final /* synthetic */ PhoneStateListener f$1;
    private final /* synthetic */ PreciseCallState f$2;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$bELzxgwsPigyVKYkAXBO2BjcSm8(PhoneStateListener.IPhoneStateListenerStub iPhoneStateListenerStub, PhoneStateListener phoneStateListener, PreciseCallState preciseCallState) {
        this.f$0 = iPhoneStateListenerStub;
        this.f$1 = phoneStateListener;
        this.f$2 = preciseCallState;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onPreciseCallStateChanged$23$PhoneStateListener$IPhoneStateListenerStub(this.f$1, this.f$2);
    }
}

