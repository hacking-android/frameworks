/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanScanner;

public final class _$$Lambda$LowpanScanner$1$lUw1npYnRpaO9LS5odGyASQYaic
implements Runnable {
    private final /* synthetic */ LowpanScanner.Callback f$0;

    public /* synthetic */ _$$Lambda$LowpanScanner$1$lUw1npYnRpaO9LS5odGyASQYaic(LowpanScanner.Callback callback) {
        this.f$0 = callback;
    }

    @Override
    public final void run() {
        LowpanScanner.1.lambda$onNetScanFinished$1(this.f$0);
    }
}

