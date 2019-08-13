/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.TelephonyScanManager;

public final class _$$Lambda$TelephonyScanManager$1$tGSpVQaVhc4GKIxjcECV_jCGYw4
implements Runnable {
    private final /* synthetic */ TelephonyScanManager.NetworkScanCallback f$0;

    public /* synthetic */ _$$Lambda$TelephonyScanManager$1$tGSpVQaVhc4GKIxjcECV_jCGYw4(TelephonyScanManager.NetworkScanCallback networkScanCallback) {
        this.f$0 = networkScanCallback;
    }

    @Override
    public final void run() {
        TelephonyScanManager.1.lambda$handleMessage$2(this.f$0);
    }
}

