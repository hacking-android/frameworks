/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.CellInfo;
import android.telephony.TelephonyScanManager;

public final class _$$Lambda$TelephonyScanManager$1$jmXulbd8FzO5Qb8_Hi_Z6s_nJWI
implements Runnable {
    private final /* synthetic */ CellInfo[] f$0;
    private final /* synthetic */ TelephonyScanManager.NetworkScanCallback f$1;

    public /* synthetic */ _$$Lambda$TelephonyScanManager$1$jmXulbd8FzO5Qb8_Hi_Z6s_nJWI(CellInfo[] arrcellInfo, TelephonyScanManager.NetworkScanCallback networkScanCallback) {
        this.f$0 = arrcellInfo;
        this.f$1 = networkScanCallback;
    }

    @Override
    public final void run() {
        TelephonyScanManager.1.lambda$handleMessage$0(this.f$0, this.f$1);
    }
}

