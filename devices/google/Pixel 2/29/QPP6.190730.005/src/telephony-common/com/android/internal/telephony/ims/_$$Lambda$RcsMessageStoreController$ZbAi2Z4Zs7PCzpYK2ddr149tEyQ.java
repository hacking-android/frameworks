/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.ims;

import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$ZbAi2Z4Zs7PCzpYK2ddr149tEyQ
implements RcsMessageStoreController.ThrowingRunnable {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ boolean f$2;
    private final /* synthetic */ long f$3;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$ZbAi2Z4Zs7PCzpYK2ddr149tEyQ(RcsMessageStoreController rcsMessageStoreController, int n, boolean bl, long l) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = n;
        this.f$2 = bl;
        this.f$3 = l;
    }

    @Override
    public final void run() {
        this.f$0.lambda$setMessageSeenTimestamp$44$RcsMessageStoreController(this.f$1, this.f$2, this.f$3);
    }
}

