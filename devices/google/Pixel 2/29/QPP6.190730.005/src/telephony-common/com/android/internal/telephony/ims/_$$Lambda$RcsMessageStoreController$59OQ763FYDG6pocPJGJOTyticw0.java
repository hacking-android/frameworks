/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.ims.RcsQueryContinuationToken
 *  android.telephony.ims.RcsThreadQueryResultParcelable
 */
package com.android.internal.telephony.ims;

import android.telephony.ims.RcsQueryContinuationToken;
import android.telephony.ims.RcsThreadQueryResultParcelable;
import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$59OQ763FYDG6pocPJGJOTyticw0
implements RcsMessageStoreController.ThrowingSupplier {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ RcsQueryContinuationToken f$1;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$59OQ763FYDG6pocPJGJOTyticw0(RcsMessageStoreController rcsMessageStoreController, RcsQueryContinuationToken rcsQueryContinuationToken) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = rcsQueryContinuationToken;
    }

    public final Object get() {
        return this.f$0.lambda$getRcsThreadsWithToken$3$RcsMessageStoreController(this.f$1);
    }
}

