/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.PhoneStateListener;
import android.telephony.PreciseDataConnectionState;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$RC2x2ijetA_pQrLa4QakzMBjh_k
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ PhoneStateListener.IPhoneStateListenerStub f$0;
    private final /* synthetic */ PhoneStateListener f$1;
    private final /* synthetic */ PreciseDataConnectionState f$2;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$RC2x2ijetA_pQrLa4QakzMBjh_k(PhoneStateListener.IPhoneStateListenerStub iPhoneStateListenerStub, PhoneStateListener phoneStateListener, PreciseDataConnectionState preciseDataConnectionState) {
        this.f$0 = iPhoneStateListenerStub;
        this.f$1 = phoneStateListener;
        this.f$2 = preciseDataConnectionState;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onPreciseDataConnectionStateChanged$27$PhoneStateListener$IPhoneStateListenerStub(this.f$1, this.f$2);
    }
}

