/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.ims;

import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$lpHB7xyEmqwVVQoFOttd3ccPVsk
implements RcsMessageStoreController.ThrowingRunnable {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$lpHB7xyEmqwVVQoFOttd3ccPVsk(RcsMessageStoreController rcsMessageStoreController, int n, int n2) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = n;
        this.f$2 = n2;
    }

    @Override
    public final void run() {
        this.f$0.lambda$setFileTransferWidth$75$RcsMessageStoreController(this.f$1, this.f$2);
    }
}

