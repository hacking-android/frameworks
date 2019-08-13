/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$aysbwPqxcLV_5w6LP0TzZu2D_ew
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ PhoneStateListener.IPhoneStateListenerStub f$0;
    private final /* synthetic */ PhoneStateListener f$1;
    private final /* synthetic */ SignalStrength f$2;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$aysbwPqxcLV_5w6LP0TzZu2D_ew(PhoneStateListener.IPhoneStateListenerStub iPhoneStateListenerStub, PhoneStateListener phoneStateListener, SignalStrength signalStrength) {
        this.f$0 = iPhoneStateListenerStub;
        this.f$1 = phoneStateListener;
        this.f$2 = signalStrength;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onSignalStrengthsChanged$17$PhoneStateListener$IPhoneStateListenerStub(this.f$1, this.f$2);
    }
}

