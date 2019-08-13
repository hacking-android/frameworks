/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.ims.RcsMessageQueryResultParcelable
 *  android.telephony.ims.RcsQueryContinuationToken
 */
package com.android.internal.telephony.ims;

import android.telephony.ims.RcsMessageQueryResultParcelable;
import android.telephony.ims.RcsQueryContinuationToken;
import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$yDvbl7WU_iKHa8m_gKb4dC0kTmU
implements RcsMessageStoreController.ThrowingSupplier {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ RcsQueryContinuationToken f$1;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$yDvbl7WU_iKHa8m_gKb4dC0kTmU(RcsMessageStoreController rcsMessageStoreController, RcsQueryContinuationToken rcsQueryContinuationToken) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = rcsQueryContinuationToken;
    }

    public final Object get() {
        return this.f$0.lambda$getMessagesWithToken$7$RcsMessageStoreController(this.f$1);
    }
}

