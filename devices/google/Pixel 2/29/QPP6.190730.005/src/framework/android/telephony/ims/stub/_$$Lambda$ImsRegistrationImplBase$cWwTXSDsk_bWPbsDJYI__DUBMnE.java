/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.stub;

import android.telephony.ims.aidl.IImsRegistrationCallback;
import android.telephony.ims.stub.ImsRegistrationImplBase;
import java.util.function.Consumer;

public final class _$$Lambda$ImsRegistrationImplBase$cWwTXSDsk_bWPbsDJYI__DUBMnE
implements Consumer {
    private final /* synthetic */ int f$0;

    public /* synthetic */ _$$Lambda$ImsRegistrationImplBase$cWwTXSDsk_bWPbsDJYI__DUBMnE(int n) {
        this.f$0 = n;
    }

    public final void accept(Object object) {
        ImsRegistrationImplBase.lambda$onRegistered$0(this.f$0, (IImsRegistrationCallback)object);
    }
}

