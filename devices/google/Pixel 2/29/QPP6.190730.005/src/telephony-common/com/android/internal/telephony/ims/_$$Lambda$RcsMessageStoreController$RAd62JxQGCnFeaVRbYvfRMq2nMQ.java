/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.ims;

import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$RAd62JxQGCnFeaVRbYvfRMq2nMQ
implements RcsMessageStoreController.ThrowingRunnable {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ long f$2;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$RAd62JxQGCnFeaVRbYvfRMq2nMQ(RcsMessageStoreController rcsMessageStoreController, int n, long l) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = n;
        this.f$2 = l;
    }

    @Override
    public final void run() {
        this.f$0.lambda$set1To1ThreadFallbackThreadId$18$RcsMessageStoreController(this.f$1, this.f$2);
    }
}

