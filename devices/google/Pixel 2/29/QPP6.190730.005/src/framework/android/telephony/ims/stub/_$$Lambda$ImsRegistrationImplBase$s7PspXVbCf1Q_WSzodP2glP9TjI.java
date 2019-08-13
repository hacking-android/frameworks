/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.stub;

import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.aidl.IImsRegistrationCallback;
import android.telephony.ims.stub.ImsRegistrationImplBase;
import java.util.function.Consumer;

public final class _$$Lambda$ImsRegistrationImplBase$s7PspXVbCf1Q_WSzodP2glP9TjI
implements Consumer {
    private final /* synthetic */ ImsReasonInfo f$0;

    public /* synthetic */ _$$Lambda$ImsRegistrationImplBase$s7PspXVbCf1Q_WSzodP2glP9TjI(ImsReasonInfo imsReasonInfo) {
        this.f$0 = imsReasonInfo;
    }

    public final void accept(Object object) {
        ImsRegistrationImplBase.lambda$onDeregistered$2(this.f$0, (IImsRegistrationCallback)object);
    }
}

