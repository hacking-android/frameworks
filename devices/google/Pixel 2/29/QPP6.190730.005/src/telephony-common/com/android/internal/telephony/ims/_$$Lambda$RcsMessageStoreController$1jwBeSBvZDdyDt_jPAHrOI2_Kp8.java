/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.ims.RcsFileTransferCreationParams
 */
package com.android.internal.telephony.ims;

import android.telephony.ims.RcsFileTransferCreationParams;
import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$1jwBeSBvZDdyDt_jPAHrOI2_Kp8
implements RcsMessageStoreController.ThrowingSupplier {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ RcsFileTransferCreationParams f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$1jwBeSBvZDdyDt_jPAHrOI2_Kp8(RcsMessageStoreController rcsMessageStoreController, RcsFileTransferCreationParams rcsFileTransferCreationParams, int n) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = rcsFileTransferCreationParams;
        this.f$2 = n;
    }

    public final Object get() {
        return this.f$0.lambda$storeFileTransfer$62$RcsMessageStoreController(this.f$1, this.f$2);
    }
}

