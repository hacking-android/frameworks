/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.print.PrintManager;

public final class _$$Lambda$c2Elb5E1w2yc6lr236iX_RUAL5Q
implements Runnable {
    private final /* synthetic */ PrintManager.PrintServicesChangeListener f$0;

    public /* synthetic */ _$$Lambda$c2Elb5E1w2yc6lr236iX_RUAL5Q(PrintManager.PrintServicesChangeListener printServicesChangeListener) {
        this.f$0 = printServicesChangeListener;
    }

    @Override
    public final void run() {
        this.f$0.onPrintServicesChanged();
    }
}

