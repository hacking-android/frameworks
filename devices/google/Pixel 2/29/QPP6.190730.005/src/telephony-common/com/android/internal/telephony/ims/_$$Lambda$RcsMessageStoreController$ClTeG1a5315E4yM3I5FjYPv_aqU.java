/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.ims.RcsThreadQueryParams
 *  android.telephony.ims.RcsThreadQueryResultParcelable
 */
package com.android.internal.telephony.ims;

import android.telephony.ims.RcsThreadQueryParams;
import android.telephony.ims.RcsThreadQueryResultParcelable;
import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$ClTeG1a5315E4yM3I5FjYPv_aqU
implements RcsMessageStoreController.ThrowingSupplier {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ RcsThreadQueryParams f$1;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$ClTeG1a5315E4yM3I5FjYPv_aqU(RcsMessageStoreController rcsMessageStoreController, RcsThreadQueryParams rcsThreadQueryParams) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = rcsThreadQueryParams;
    }

    public final Object get() {
        return this.f$0.lambda$getRcsThreads$2$RcsMessageStoreController(this.f$1);
    }
}

