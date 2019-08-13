/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.stub;

import android.telephony.ims.aidl.IImsRegistrationCallback;
import android.telephony.ims.stub.ImsRegistrationImplBase;
import java.util.function.Consumer;

public final class _$$Lambda$ImsRegistrationImplBase$sbjuTvW_brOSWMR74UInSZEIQB0
implements Consumer {
    private final /* synthetic */ int f$0;

    public /* synthetic */ _$$Lambda$ImsRegistrationImplBase$sbjuTvW_brOSWMR74UInSZEIQB0(int n) {
        this.f$0 = n;
    }

    public final void accept(Object object) {
        ImsRegistrationImplBase.lambda$onRegistering$1(this.f$0, (IImsRegistrationCallback)object);
    }
}

