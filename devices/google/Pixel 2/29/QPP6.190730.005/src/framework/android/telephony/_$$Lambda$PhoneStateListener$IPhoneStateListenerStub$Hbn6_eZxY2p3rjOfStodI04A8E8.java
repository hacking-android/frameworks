/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import com.android.internal.util.FunctionalUtils;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Hbn6_eZxY2p3rjOfStodI04A8E8
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ PhoneStateListener.IPhoneStateListenerStub f$0;
    private final /* synthetic */ PhoneStateListener f$1;
    private final /* synthetic */ CellLocation f$2;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$Hbn6_eZxY2p3rjOfStodI04A8E8(PhoneStateListener.IPhoneStateListenerStub iPhoneStateListenerStub, PhoneStateListener phoneStateListener, CellLocation cellLocation) {
        this.f$0 = iPhoneStateListenerStub;
        this.f$1 = phoneStateListener;
        this.f$2 = cellLocation;
    }

    @Override
    public final void runOrThrow() {
        this.f$0.lambda$onCellLocationChanged$9$PhoneStateListener$IPhoneStateListenerStub(this.f$1, this.f$2);
    }
}

