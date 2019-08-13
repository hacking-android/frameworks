/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.ims;

import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$cI3HfQAJmekiQsJsCcTNShYosxw
implements RcsMessageStoreController.ThrowingRunnable {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$cI3HfQAJmekiQsJsCcTNShYosxw(RcsMessageStoreController rcsMessageStoreController, int n) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$deleteFileTransfer$61$RcsMessageStoreController(this.f$1);
    }
}

