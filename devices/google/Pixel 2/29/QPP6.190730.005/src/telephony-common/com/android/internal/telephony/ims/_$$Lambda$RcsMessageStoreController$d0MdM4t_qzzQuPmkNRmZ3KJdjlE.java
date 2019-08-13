/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.android.internal.telephony.ims;

import android.net.Uri;
import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$d0MdM4t_qzzQuPmkNRmZ3KJdjlE
implements RcsMessageStoreController.ThrowingRunnable {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ int f$1;
    private final /* synthetic */ Uri f$2;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$d0MdM4t_qzzQuPmkNRmZ3KJdjlE(RcsMessageStoreController rcsMessageStoreController, int n, Uri uri) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = n;
        this.f$2 = uri;
    }

    @Override
    public final void run() {
        this.f$0.lambda$setFileTransferPreviewUri$81$RcsMessageStoreController(this.f$1, this.f$2);
    }
}

