/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.ims.RcsMessageQueryParams
 *  android.telephony.ims.RcsMessageQueryResultParcelable
 */
package com.android.internal.telephony.ims;

import android.telephony.ims.RcsMessageQueryParams;
import android.telephony.ims.RcsMessageQueryResultParcelable;
import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$8kZ0Whs2V3H2_A7PZtgThC9cHrM
implements RcsMessageStoreController.ThrowingSupplier {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ RcsMessageQueryParams f$1;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$8kZ0Whs2V3H2_A7PZtgThC9cHrM(RcsMessageStoreController rcsMessageStoreController, RcsMessageQueryParams rcsMessageQueryParams) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = rcsMessageQueryParams;
    }

    public final Object get() {
        return this.f$0.lambda$getMessages$6$RcsMessageStoreController(this.f$1);
    }
}

