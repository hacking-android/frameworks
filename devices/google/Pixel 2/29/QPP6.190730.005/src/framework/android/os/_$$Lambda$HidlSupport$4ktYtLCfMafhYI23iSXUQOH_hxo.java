/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.HidlSupport;
import java.util.function.IntPredicate;

public final class _$$Lambda$HidlSupport$4ktYtLCfMafhYI23iSXUQOH_hxo
implements IntPredicate {
    private final /* synthetic */ Object[] f$0;
    private final /* synthetic */ Object[] f$1;

    public /* synthetic */ _$$Lambda$HidlSupport$4ktYtLCfMafhYI23iSXUQOH_hxo(Object[] arrobject, Object[] arrobject2) {
        this.f$0 = arrobject;
        this.f$1 = arrobject2;
    }

    @Override
    public final boolean test(int n) {
        return HidlSupport.lambda$deepEquals$0(this.f$0, this.f$1, n);
    }
}

