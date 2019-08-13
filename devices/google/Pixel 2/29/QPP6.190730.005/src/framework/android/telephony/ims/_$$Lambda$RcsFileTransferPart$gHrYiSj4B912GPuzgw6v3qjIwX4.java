/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.net.Uri;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsFileTransferPart;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsFileTransferPart$gHrYiSj4B912GPuzgw6v3qjIwX4
implements RcsControllerCall.RcsServiceCallWithNoReturn {
    private final /* synthetic */ RcsFileTransferPart f$0;
    private final /* synthetic */ Uri f$1;

    public /* synthetic */ _$$Lambda$RcsFileTransferPart$gHrYiSj4B912GPuzgw6v3qjIwX4(RcsFileTransferPart rcsFileTransferPart, Uri uri) {
        this.f$0 = rcsFileTransferPart;
        this.f$1 = uri;
    }

    @Override
    public final void methodOnIRcs(IRcs iRcs, String string2) {
        this.f$0.lambda$setContentUri$2$RcsFileTransferPart(this.f$1, iRcs, string2);
    }
}

