/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.ims.RcsOutgoingMessageCreationParams
 */
package com.android.internal.telephony.ims;

import android.telephony.ims.RcsOutgoingMessageCreationParams;
import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$40atfWQEcRbpUIloB6mwL9gyuIc
implements RcsMessageStoreController.ThrowingSupplier {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ RcsOutgoingMessageCreationParams f$2;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$40atfWQEcRbpUIloB6mwL9gyuIc(RcsMessageStoreController rcsMessageStoreController, int n, RcsOutgoingMessageCreationParams rcsOutgoingMessageCreationParams) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = n;
        this.f$2 = rcsOutgoingMessageCreationParams;
    }

    public final Object get() {
        return this.f$0.lambda$addOutgoingMessage$32$RcsMessageStoreController(this.f$1, this.f$2);
    }
}

