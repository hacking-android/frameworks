/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanScanner;

public final class _$$Lambda$LowpanScanner$2$GBDCgjndr24KQueHMX2qGNtfLPg
implements Runnable {
    private final /* synthetic */ LowpanScanner.Callback f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$LowpanScanner$2$GBDCgjndr24KQueHMX2qGNtfLPg(LowpanScanner.Callback callback, int n, int n2) {
        this.f$0 = callback;
        this.f$1 = n;
        this.f$2 = n2;
    }

    @Override
    public final void run() {
        LowpanScanner.2.lambda$onEnergyScanResult$0(this.f$0, this.f$1, this.f$2);
    }
}

