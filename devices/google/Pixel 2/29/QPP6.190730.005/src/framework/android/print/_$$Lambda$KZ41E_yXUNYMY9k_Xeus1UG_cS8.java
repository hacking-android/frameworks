/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.print.PrintManager;

public final class _$$Lambda$KZ41E_yXUNYMY9k_Xeus1UG_cS8
implements Runnable {
    private final /* synthetic */ PrintManager.PrintServiceRecommendationsChangeListener f$0;

    public /* synthetic */ _$$Lambda$KZ41E_yXUNYMY9k_Xeus1UG_cS8(PrintManager.PrintServiceRecommendationsChangeListener printServiceRecommendationsChangeListener) {
        this.f$0 = printServiceRecommendationsChangeListener;
    }

    @Override
    public final void run() {
        this.f$0.onPrintServiceRecommendationsChanged();
    }
}

