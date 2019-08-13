/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.telephony.DataFailCause;
import java.util.function.IntPredicate;

public final class _$$Lambda$DataFailCause$djkZSxdG_s_w2L5rQKiGu6OudyY
implements IntPredicate {
    private final /* synthetic */ int f$0;

    public /* synthetic */ _$$Lambda$DataFailCause$djkZSxdG_s_w2L5rQKiGu6OudyY(int n) {
        this.f$0 = n;
    }

    @Override
    public final boolean test(int n) {
        return DataFailCause.lambda$isRadioRestartFailure$0(this.f$0, n);
    }
}

