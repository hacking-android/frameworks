/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$dUc3j82sK9P9Zpaq_91n9bk_Rpc
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$dUc3j82sK9P9Zpaq_91n9bk_Rpc(PhoneStateListener phoneStateListener, int n, int n2) {
        this.f$0 = phoneStateListener;
        this.f$1 = n;
        this.f$2 = n2;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onDataConnectionStateChanged$12(this.f$0, this.f$1, this.f$2);
    }
}

