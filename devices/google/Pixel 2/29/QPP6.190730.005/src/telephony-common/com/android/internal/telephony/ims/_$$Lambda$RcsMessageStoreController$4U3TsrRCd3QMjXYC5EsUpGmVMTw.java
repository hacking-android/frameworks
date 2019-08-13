/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.android.internal.telephony.ims;

import android.net.Uri;
import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$4U3TsrRCd3QMjXYC5EsUpGmVMTw
implements RcsMessageStoreController.ThrowingSupplier {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ Uri f$1;
    private final /* synthetic */ long f$2;
    private final /* synthetic */ int f$3;
    private final /* synthetic */ int f$4;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$4U3TsrRCd3QMjXYC5EsUpGmVMTw(RcsMessageStoreController rcsMessageStoreController, Uri uri, long l, int n, int n2) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = uri;
        this.f$2 = l;
        this.f$3 = n;
        this.f$4 = n2;
    }

    public final Object get() {
        return this.f$0.lambda$createGroupThreadIconChangedEvent$86$RcsMessageStoreController(this.f$1, this.f$2, this.f$3, this.f$4);
    }
}

