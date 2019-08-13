/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.TelephonyScanManager;

public final class _$$Lambda$TelephonyScanManager$1$X9SMshZoHjJ6SzCbmgVMwQip2Q0
implements Runnable {
    private final /* synthetic */ int f$0;
    private final /* synthetic */ TelephonyScanManager.NetworkScanCallback f$1;

    public /* synthetic */ _$$Lambda$TelephonyScanManager$1$X9SMshZoHjJ6SzCbmgVMwQip2Q0(int n, TelephonyScanManager.NetworkScanCallback networkScanCallback) {
        this.f$0 = n;
        this.f$1 = networkScanCallback;
    }

    @Override
    public final void run() {
        TelephonyScanManager.1.lambda$handleMessage$1(this.f$0, this.f$1);
    }
}

