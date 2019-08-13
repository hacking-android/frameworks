/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.ims;

import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$QuCMf_ZjaL38JKfdVdaXlhUciYg
implements RcsMessageStoreController.ThrowingRunnable {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ String f$2;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$QuCMf_ZjaL38JKfdVdaXlhUciYg(RcsMessageStoreController rcsMessageStoreController, int n, String string) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = n;
        this.f$2 = string;
    }

    @Override
    public final void run() {
        this.f$0.lambda$setFileTransferContentType$67$RcsMessageStoreController(this.f$1, this.f$2);
    }
}

