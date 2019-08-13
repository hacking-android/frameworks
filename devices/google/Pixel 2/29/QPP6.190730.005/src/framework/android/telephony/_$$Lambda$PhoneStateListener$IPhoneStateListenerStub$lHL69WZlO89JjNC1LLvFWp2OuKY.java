/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneCapability;
import android.telephony.PhoneStateListener;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$lHL69WZlO89JjNC1LLvFWp2OuKY
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ PhoneCapability f$1;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$lHL69WZlO89JjNC1LLvFWp2OuKY(PhoneStateListener phoneStateListener, PhoneCapability phoneCapability) {
        this.f$0 = phoneStateListener;
        this.f$1 = phoneCapability;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onPhoneCapabilityChanged$46(this.f$0, this.f$1);
    }
}

