/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.android.internal.telephony.ims;

import android.net.Uri;
import com.android.internal.telephony.ims.RcsMessageStoreController;

public final class _$$Lambda$RcsMessageStoreController$wo_oijl9hUm68TegPWxMOAOSGs8
implements RcsMessageStoreController.ThrowingSupplier {
    private final /* synthetic */ RcsMessageStoreController f$0;
    private final /* synthetic */ String f$1;
    private final /* synthetic */ Uri f$2;
    private final /* synthetic */ int[] f$3;
    private final /* synthetic */ String f$4;

    public /* synthetic */ _$$Lambda$RcsMessageStoreController$wo_oijl9hUm68TegPWxMOAOSGs8(RcsMessageStoreController rcsMessageStoreController, String string, Uri uri, int[] arrn, String string2) {
        this.f$0 = rcsMessageStoreController;
        this.f$1 = string;
        this.f$2 = uri;
        this.f$3 = arrn;
        this.f$4 = string2;
    }

    public final Object get() {
        return this.f$0.lambda$createGroupThread$11$RcsMessageStoreController(this.f$1, this.f$2, this.f$3, this.f$4);
    }
}

