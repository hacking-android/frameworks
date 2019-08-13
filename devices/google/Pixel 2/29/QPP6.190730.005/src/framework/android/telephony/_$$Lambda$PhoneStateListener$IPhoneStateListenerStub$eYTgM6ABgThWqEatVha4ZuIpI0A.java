/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;
import android.telephony.ims.ImsReasonInfo;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$eYTgM6ABgThWqEatVha4ZuIpI0A
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ ImsReasonInfo f$1;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$eYTgM6ABgThWqEatVha4ZuIpI0A(PhoneStateListener phoneStateListener, ImsReasonInfo imsReasonInfo) {
        this.f$0 = phoneStateListener;
        this.f$1 = imsReasonInfo;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onImsCallDisconnectCauseChanged$54(this.f$0, this.f$1);
    }
}

