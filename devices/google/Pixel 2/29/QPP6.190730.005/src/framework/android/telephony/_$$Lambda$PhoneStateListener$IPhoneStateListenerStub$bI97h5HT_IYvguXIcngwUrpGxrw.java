/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$bI97h5HT_IYvguXIcngwUrpGxrw
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$bI97h5HT_IYvguXIcngwUrpGxrw(PhoneStateListener phoneStateListener, int n) {
        this.f$0 = phoneStateListener;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onRadioPowerStateChanged$48(this.f$0, this.f$1);
    }
}

