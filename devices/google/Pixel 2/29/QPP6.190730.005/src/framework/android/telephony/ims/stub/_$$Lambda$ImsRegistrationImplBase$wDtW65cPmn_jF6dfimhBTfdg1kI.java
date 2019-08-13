/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.stub;

import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.aidl.IImsRegistrationCallback;
import android.telephony.ims.stub.ImsRegistrationImplBase;
import java.util.function.Consumer;

public final class _$$Lambda$ImsRegistrationImplBase$wDtW65cPmn_jF6dfimhBTfdg1kI
implements Consumer {
    private final /* synthetic */ int f$0;
    private final /* synthetic */ ImsReasonInfo f$1;

    public /* synthetic */ _$$Lambda$ImsRegistrationImplBase$wDtW65cPmn_jF6dfimhBTfdg1kI(int n, ImsReasonInfo imsReasonInfo) {
        this.f$0 = n;
        this.f$1 = imsReasonInfo;
    }

    public final void accept(Object object) {
        ImsRegistrationImplBase.lambda$onTechnologyChangeFailed$3(this.f$0, this.f$1, (IImsRegistrationCallback)object);
    }
}

