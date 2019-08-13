/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5Uf5OZWCyPD0lZtySzbYw18FWhU
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ boolean f$1;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$5Uf5OZWCyPD0lZtySzbYw18FWhU(PhoneStateListener phoneStateListener, boolean bl) {
        this.f$0 = phoneStateListener;
        this.f$1 = bl;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onUserMobileDataStateChanged$36(this.f$0, this.f$1);
    }
}

