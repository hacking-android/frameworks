/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;

public final class _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$2cMrwdqnKBpixpApeIX38rmRLak
implements Runnable {
    private final /* synthetic */ PhoneStateListener f$0;
    private final /* synthetic */ CellLocation f$1;

    public /* synthetic */ _$$Lambda$PhoneStateListener$IPhoneStateListenerStub$2cMrwdqnKBpixpApeIX38rmRLak(PhoneStateListener phoneStateListener, CellLocation cellLocation) {
        this.f$0 = phoneStateListener;
        this.f$1 = cellLocation;
    }

    @Override
    public final void run() {
        PhoneStateListener.IPhoneStateListenerStub.lambda$onCellLocationChanged$8(this.f$0, this.f$1);
    }
}

