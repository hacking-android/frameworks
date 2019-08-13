/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.android.internal.telephony.ims;

import android.net.Uri;
import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$kwvr7eXsIkjI91KRQHY7Ht_2JB4
implements RcsMessageStoreController.ThrowingSupplier {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$kwvr7eXsIkjI91KRQHY7Ht_2JB4(RcsMessageStoreController rcsMessageStoreController, int n) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = n;
    }

    public final Object get() {
        return this.f$0.lambda$getFileTransferContentUri$66$RcsMessageStoreController(this.f$1);
    }
}

