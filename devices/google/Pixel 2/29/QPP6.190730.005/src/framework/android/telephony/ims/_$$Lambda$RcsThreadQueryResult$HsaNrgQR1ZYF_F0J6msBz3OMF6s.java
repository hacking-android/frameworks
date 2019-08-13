/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsThread;
import android.telephony.ims.RcsThreadQueryResult;
import com.android.ims.RcsTypeIdPair;
import java.util.function.Function;

public final class _$$Lambda$RcsThreadQueryResult$HsaNrgQR1ZYF_F0J6msBz3OMF6s
implements Function {
    private final /* synthetic */ RcsThreadQueryResult f$0;

    public /* synthetic */ _$$Lambda$RcsThreadQueryResult$HsaNrgQR1ZYF_F0J6msBz3OMF6s(RcsThreadQueryResult rcsThreadQueryResult) {
        this.f$0 = rcsThreadQueryResult;
    }

    public final Object apply(Object object) {
        return this.f$0.lambda$getThreads$0$RcsThreadQueryResult((RcsTypeIdPair)object);
    }
}

