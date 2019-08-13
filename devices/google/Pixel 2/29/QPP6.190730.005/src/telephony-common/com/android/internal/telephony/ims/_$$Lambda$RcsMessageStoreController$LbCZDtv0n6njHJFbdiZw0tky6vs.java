/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.ims.RcsEventQueryParams
 *  android.telephony.ims.RcsEventQueryResultDescriptor
 */
package com.android.internal.telephony.ims;

import android.telephony.ims.RcsEventQueryParams;
import android.telephony.ims.RcsEventQueryResultDescriptor;
import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$LbCZDtv0n6njHJFbdiZw0tky6vs
implements RcsMessageStoreController.ThrowingSupplier {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ RcsEventQueryParams f$1;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$LbCZDtv0n6njHJFbdiZw0tky6vs(RcsMessageStoreController rcsMessageStoreController, RcsEventQueryParams rcsEventQueryParams) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = rcsEventQueryParams;
    }

    public final Object get() {
        return this.f$0.lambda$getEvents$8$RcsMessageStoreController(this.f$1);
    }
}

