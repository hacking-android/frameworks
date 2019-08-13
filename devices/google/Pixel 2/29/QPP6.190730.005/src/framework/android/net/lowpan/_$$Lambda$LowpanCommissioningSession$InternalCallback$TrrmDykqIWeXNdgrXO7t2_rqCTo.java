/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanCommissioningSession;

public final class _$$Lambda$LowpanCommissioningSession$InternalCallback$TrrmDykqIWeXNdgrXO7t2_rqCTo
implements Runnable {
    private final /* synthetic */ LowpanCommissioningSession.InternalCallback f$0;
    private final /* synthetic */ byte[] f$1;

    public /* synthetic */ _$$Lambda$LowpanCommissioningSession$InternalCallback$TrrmDykqIWeXNdgrXO7t2_rqCTo(LowpanCommissioningSession.InternalCallback internalCallback, byte[] arrby) {
        this.f$0 = internalCallback;
        this.f$1 = arrby;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onReceiveFromCommissioner$0$LowpanCommissioningSession$InternalCallback(this.f$1);
    }
}

