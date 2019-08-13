/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanCommissioningSession;

public final class _$$Lambda$LowpanCommissioningSession$jqpl_iUq_e7YuWqkG33P8PNe7Ag
implements Runnable {
    private final /* synthetic */ LowpanCommissioningSession f$0;

    public /* synthetic */ _$$Lambda$LowpanCommissioningSession$jqpl_iUq_e7YuWqkG33P8PNe7Ag(LowpanCommissioningSession lowpanCommissioningSession) {
        this.f$0 = lowpanCommissioningSession;
    }

    @Override
    public final void run() {
        this.f$0.lambda$lockedCleanup$0$LowpanCommissioningSession();
    }
}

