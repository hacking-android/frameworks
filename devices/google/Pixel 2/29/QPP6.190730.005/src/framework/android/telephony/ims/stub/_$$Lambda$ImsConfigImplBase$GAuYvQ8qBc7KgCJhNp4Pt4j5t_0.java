/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.stub;

import android.telephony.ims.aidl.IImsConfigCallback;
import android.telephony.ims.stub.ImsConfigImplBase;
import java.util.function.Consumer;

public final class _$$Lambda$ImsConfigImplBase$GAuYvQ8qBc7KgCJhNp4Pt4j5t_0
implements Consumer {
    private final /* synthetic */ int f$0;
    private final /* synthetic */ String f$1;

    public /* synthetic */ _$$Lambda$ImsConfigImplBase$GAuYvQ8qBc7KgCJhNp4Pt4j5t_0(int n, String string2) {
        this.f$0 = n;
        this.f$1 = string2;
    }

    public final void accept(Object object) {
        ImsConfigImplBase.lambda$notifyConfigChanged$1(this.f$0, this.f$1, (IImsConfigCallback)object);
    }
}

