/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanScanner;

public final class _$$Lambda$LowpanScanner$2$n8MSb22N9MEsazioSumvyQhW3Z4
implements Runnable {
    private final /* synthetic */ LowpanScanner.Callback f$0;

    public /* synthetic */ _$$Lambda$LowpanScanner$2$n8MSb22N9MEsazioSumvyQhW3Z4(LowpanScanner.Callback callback) {
        this.f$0 = callback;
    }

    @Override
    public final void run() {
        LowpanScanner.2.lambda$onEnergyScanFinished$1(this.f$0);
    }
}

