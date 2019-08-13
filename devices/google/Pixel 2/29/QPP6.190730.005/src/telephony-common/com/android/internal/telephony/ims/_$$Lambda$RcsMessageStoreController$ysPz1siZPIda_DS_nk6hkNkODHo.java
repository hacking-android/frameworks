/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.ims.RcsParticipantQueryParams
 *  android.telephony.ims.RcsParticipantQueryResultParcelable
 */
package com.android.internal.telephony.ims;

import android.telephony.ims.RcsParticipantQueryParams;
import android.telephony.ims.RcsParticipantQueryResultParcelable;
import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$ysPz1siZPIda_DS_nk6hkNkODHo
implements RcsMessageStoreController.ThrowingSupplier {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ RcsParticipantQueryParams f$1;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$ysPz1siZPIda_DS_nk6hkNkODHo(RcsMessageStoreController rcsMessageStoreController, RcsParticipantQueryParams rcsParticipantQueryParams) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = rcsParticipantQueryParams;
    }

    public final Object get() {
        return this.f$0.lambda$getParticipants$4$RcsMessageStoreController(this.f$1);
    }
}

