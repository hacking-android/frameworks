/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;
import android.telephony.PreciseCallState;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$4NHt5Shg_DHV_T1IxfcQLHP5_j0
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ PreciseCallState f$1;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$4NHt5Shg_DHV_T1IxfcQLHP5_j0(PhoneStateListener phoneStateListener, PreciseCallState preciseCallState) {
        this.f$0 = phoneStateListener;
        this.f$1 = preciseCallState;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onPreciseCallStateChanged$22(this.f$0, this.f$1);
    }
}

