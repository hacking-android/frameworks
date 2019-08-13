/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsMessage;
import android.telephony.ims.RcsMessageQueryResult;
import com.android.ims.RcsTypeIdPair;
import java.util.function.Function;

public final class _$$Lambda$RcsMessageQueryResult$20XnTdVu75hlh0utIOyf1L_ZpTE
implements Function {
    private final /* synthetic */ RcsMessageQueryResult f$0;

    public /* synthetic */ _$$Lambda$RcsMessageQueryResult$20XnTdVu75hlh0utIOyf1L_ZpTE(RcsMessageQueryResult rcsMessageQueryResult) {
        this.f$0 = rcsMessageQueryResult;
    }

    public final Object apply(Object object) {
        return this.f$0.lambda$getMessages$0$RcsMessageQueryResult((RcsTypeIdPair)object);
    }
}

