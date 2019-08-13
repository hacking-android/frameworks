/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;
import android.telephony.PreciseDataConnectionState;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$HEcWn_J1WRb0wLERu2qoMIZDfjY
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ PreciseDataConnectionState f$1;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$HEcWn_J1WRb0wLERu2qoMIZDfjY(PhoneStateListener phoneStateListener, PreciseDataConnectionState preciseDataConnectionState) {
        this.f$0 = phoneStateListener;
        this.f$1 = preciseDataConnectionState;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onPreciseDataConnectionStateChanged$26(this.f$0, this.f$1);
    }
}

