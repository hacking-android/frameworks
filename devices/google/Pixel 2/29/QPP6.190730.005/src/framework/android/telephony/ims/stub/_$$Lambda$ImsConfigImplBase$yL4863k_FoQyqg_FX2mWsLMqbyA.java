/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.stub;

import android.telephony.ims.aidl.IImsConfigCallback;
import android.telephony.ims.stub.ImsConfigImplBase;
import java.util.function.Consumer;

public final class _$$Lambda$ImsConfigImplBase$yL4863k_FoQyqg_FX2mWsLMqbyA
implements Consumer {
    private final /* synthetic */ int f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$ImsConfigImplBase$yL4863k_FoQyqg_FX2mWsLMqbyA(int n, int n2) {
        this.f$0 = n;
        this.f$1 = n2;
    }

    public final void accept(Object object) {
        ImsConfigImplBase.lambda$notifyConfigChanged$0(this.f$0, this.f$1, (IImsConfigCallback)object);
    }
}

