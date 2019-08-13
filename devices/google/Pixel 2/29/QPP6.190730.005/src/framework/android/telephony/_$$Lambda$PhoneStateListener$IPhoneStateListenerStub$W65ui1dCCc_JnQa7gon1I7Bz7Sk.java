/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$W65ui1dCCc_JnQa7gon1I7Bz7Sk
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$W65ui1dCCc_JnQa7gon1I7Bz7Sk(PhoneStateListener phoneStateListener, int n) {
        this.f$0 = phoneStateListener;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onDataActivationStateChanged$34(this.f$0, this.f$1);
    }
}

