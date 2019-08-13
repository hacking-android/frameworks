/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.net.Uri;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsMessageStore;
import android.telephony.ims.aidl.IRcs;

public final class _$$Lambda$RcsMessageStore$g309WUVpYx8N7s_uWdUAGJXtJOs
implements RcsControllerCall.RcsServiceCall {
    private final /* synthetic */ int[] f$0;
    private final /* synthetic */ String f$1;
    private final /* synthetic */ Uri f$2;

    public /* synthetic */ _$$Lambda$RcsMessageStore$g309WUVpYx8N7s_uWdUAGJXtJOs(int[] arrn, String string2, Uri uri) {
        this.f$0 = arrn;
        this.f$1 = string2;
        this.f$2 = uri;
    }

    public final Object methodOnIRcs(IRcs iRcs, String string2) {
        return RcsMessageStore.lambda$createGroupThread$9(this.f$0, this.f$1, this.f$2, iRcs, string2);
    }
}

