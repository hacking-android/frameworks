/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$0s34qsuHFsa43jUHrTkD62ni6Ds
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ SignalStrength f$1;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$0s34qsuHFsa43jUHrTkD62ni6Ds(PhoneStateListener phoneStateListener, SignalStrength signalStrength) {
        this.f$0 = phoneStateListener;
        this.f$1 = signalStrength;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onSignalStrengthsChanged$16(this.f$0, this.f$1);
    }
}

