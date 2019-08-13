/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.DataConnectionRealTimeInfo;
import android.telephony.PhoneStateListener;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$OfwFKKtcQHRmtv70FCopw6FDAAU
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ PhoneStateListener.IPhoneStateListenerStub f$0;
    private final /* synthetic */ PhoneStateListener f$1;
    private final /* synthetic */ DataConnectionRealTimeInfo f$2;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$OfwFKKtcQHRmtv70FCopw6FDAAU(PhoneStateListener.IPhoneStateListenerStub iPhoneStateListenerStub, PhoneStateListener phoneStateListener, DataConnectionRealTimeInfo dataConnectionRealTimeInfo) {
        this.f$0 = iPhoneStateListenerStub;
        this.f$1 = phoneStateListener;
        this.f$2 = dataConnectionRealTimeInfo;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onDataConnectionRealTimeInfoChanged$29$PhoneStateListener$IPhoneStateListenerStub(this.f$1, this.f$2);
    }
}

