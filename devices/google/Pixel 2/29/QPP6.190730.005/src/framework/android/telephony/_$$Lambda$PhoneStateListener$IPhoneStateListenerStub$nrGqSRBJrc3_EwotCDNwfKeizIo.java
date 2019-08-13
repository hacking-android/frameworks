/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$nrGqSRBJrc3_EwotCDNwfKeizIo
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ ServiceState f$1;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$nrGqSRBJrc3_EwotCDNwfKeizIo(PhoneStateListener phoneStateListener, ServiceState serviceState) {
        this.f$0 = phoneStateListener;
        this.f$1 = serviceState;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onServiceStateChanged$0(this.f$0, this.f$1);
    }
}

