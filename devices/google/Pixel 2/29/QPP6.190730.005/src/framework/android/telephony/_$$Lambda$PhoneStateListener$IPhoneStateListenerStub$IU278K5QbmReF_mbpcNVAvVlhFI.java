/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.DataConnectionRealTimeInfo;
import android.telephony.PhoneStateListener;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$IU278K5QbmReF_mbpcNVAvVlhFI
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ DataConnectionRealTimeInfo f$1;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$IU278K5QbmReF_mbpcNVAvVlhFI(PhoneStateListener phoneStateListener, DataConnectionRealTimeInfo dataConnectionRealTimeInfo) {
        this.f$0 = phoneStateListener;
        this.f$1 = dataConnectionRealTimeInfo;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onDataConnectionRealTimeInfoChanged$28(this.f$0, this.f$1);
    }
}

