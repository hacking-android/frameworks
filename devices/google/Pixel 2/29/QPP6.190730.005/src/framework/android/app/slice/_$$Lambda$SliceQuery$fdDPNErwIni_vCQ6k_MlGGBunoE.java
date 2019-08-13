/*
 * Decompiled with CFR 0.145.
 */
package android.app.slice;

import android.app.slice.SliceItem;
import android.app.slice.SliceQuery;
import java.util.function.Predicate;

public final class _$$Lambda$SliceQuery$fdDPNErwIni_vCQ6k_MlGGBunoE
implements Predicate {
    private final /* synthetic */ SliceItem f$0;

    public /* synthetic */ _$$Lambda$SliceQuery$fdDPNErwIni_vCQ6k_MlGGBunoE(SliceItem sliceItem) {
        this.f$0 = sliceItem;
    }

    public final boolean test(Object object) {
        return SliceQuery.lambda$contains$0(this.f$0, (SliceItem)object);
    }
}

