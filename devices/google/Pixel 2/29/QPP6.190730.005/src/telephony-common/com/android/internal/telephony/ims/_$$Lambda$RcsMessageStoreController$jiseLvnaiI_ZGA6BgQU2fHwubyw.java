/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.ims.RcsIncomingMessageCreationParams
 */
package com.android.internal.telephony.ims;

import android.telephony.ims.RcsIncomingMessageCreationParams;
import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$jiseLvnaiI_ZGA6BgQU2fHwubyw
implements RcsMessageStoreController.ThrowingSupplier {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ RcsIncomingMessageCreationParams f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$jiseLvnaiI_ZGA6BgQU2fHwubyw(RcsMessageStoreController rcsMessageStoreController, RcsIncomingMessageCreationParams rcsIncomingMessageCreationParams, int n) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = rcsIncomingMessageCreationParams;
        this.f$2 = n;
    }

    public final Object get() {
        return this.f$0.lambda$addIncomingMessage$31$RcsMessageStoreController(this.f$1, this.f$2);
    }
}

