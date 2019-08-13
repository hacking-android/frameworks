/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsEventDescriptor;
import android.telephony.ims.RcsEventQueryResultDescriptor;
import java.util.function.Function;

public final class _$$Lambda$RcsEventQueryResultDescriptor$0eoTyoA0JNoBx53J3zXvi1fQcnA
implements Function {
    private final /* synthetic */ RcsControllerCall f$0;

    public /* synthetic */ _$$Lambda$RcsEventQueryResultDescriptor$0eoTyoA0JNoBx53J3zXvi1fQcnA(RcsControllerCall rcsControllerCall) {
        this.f$0 = rcsControllerCall;
    }

    public final Object apply(Object object) {
        return RcsEventQueryResultDescriptor.lambda$getRcsEventQueryResult$0(this.f$0, (RcsEventDescriptor)object);
    }
}

