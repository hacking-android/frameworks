/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import java.util.concurrent.CountDownLatch;

public final class _$$Lambda$5k6tNlswoNAjCdgttrkQIe8VHVs
implements Runnable {
    private final /* synthetic */ CountDownLatch f$0;

    public /* synthetic */ _$$Lambda$5k6tNlswoNAjCdgttrkQIe8VHVs(CountDownLatch countDownLatch) {
        this.f$0 = countDownLatch;
    }

    @Override
    public final void run() {
        this.f$0.countDown();
    }
}

