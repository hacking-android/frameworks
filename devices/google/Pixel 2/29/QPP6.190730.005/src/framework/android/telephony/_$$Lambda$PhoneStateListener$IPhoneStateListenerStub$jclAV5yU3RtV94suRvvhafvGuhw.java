/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jclAV5yU3RtV94suRvvhafvGuhw
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ byte[] f$1;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$jclAV5yU3RtV94suRvvhafvGuhw(PhoneStateListener phoneStateListener, byte[] arrby) {
        this.f$0 = phoneStateListener;
        this.f$1 = arrby;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onOemHookRawEvent$38(this.f$0, this.f$1);
    }
}

