/*
 * Decompiled with CFR 0.145.
 */
package android.app.slice;

import android.app.slice.SliceItem;
import android.app.slice.SliceQuery;
import java.util.function.Predicate;

public final class _$$Lambda$SliceQuery$hLToAajdaMbaf8BUtZ8fpGK980E
implements Predicate {
    private final /* synthetic */ String f$0;
    private final /* synthetic */ String[] f$1;
    private final /* synthetic */ String[] f$2;

    public /* synthetic */ _$$Lambda$SliceQuery$hLToAajdaMbaf8BUtZ8fpGK980E(String string2, String[] arrstring, String[] arrstring2) {
        this.f$0 = string2;
        this.f$1 = arrstring;
        this.f$2 = arrstring2;
    }

    public final boolean test(Object object) {
        return SliceQuery.lambda$findAll$1(this.f$0, this.f$1, this.f$2, (SliceItem)object);
    }
}

