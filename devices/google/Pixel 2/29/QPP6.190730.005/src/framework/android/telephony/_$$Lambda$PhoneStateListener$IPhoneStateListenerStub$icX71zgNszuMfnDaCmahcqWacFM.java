/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$icX71zgNszuMfnDaCmahcqWacFM
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ PhoneStateListener.IPhoneStateListenerStub f$0;
    private final /* synthetic */ PhoneStateListener f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ int f$3;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$icX71zgNszuMfnDaCmahcqWacFM(PhoneStateListener.IPhoneStateListenerStub iPhoneStateListenerStub, PhoneStateListener phoneStateListener, int n, int n2) {
        this.f$0 = iPhoneStateListenerStub;
        this.f$1 = phoneStateListener;
        this.f$2 = n;
        this.f$3 = n2;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onCallDisconnectCauseChanged$25$PhoneStateListener$IPhoneStateListenerStub(this.f$1, this.f$2, this.f$3);
    }
}

