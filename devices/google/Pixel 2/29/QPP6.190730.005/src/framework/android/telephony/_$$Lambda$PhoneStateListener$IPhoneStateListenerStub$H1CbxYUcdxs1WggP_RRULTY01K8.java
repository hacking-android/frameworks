/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$H1CbxYUcdxs1WggP_RRULTY01K8
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$H1CbxYUcdxs1WggP_RRULTY01K8(PhoneStateListener phoneStateListener, int n) {
        this.f$0 = phoneStateListener;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onOtaspChanged$18(this.f$0, this.f$1);
    }
}

