/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

public final class _$$Lambda$Map$Entry$g8sc1MgjjhwTaK8zHulzMasixMw
implements Comparator,
Serializable {
    private final /* synthetic */ Comparator f$0;

    public /* synthetic */ _$$Lambda$Map$Entry$g8sc1MgjjhwTaK8zHulzMasixMw(Comparator comparator) {
        this.f$0 = comparator;
    }

    public final int compare(Object object, Object object2) {
        return Map.Entry.lambda$comparingByKey$6d558cbf$1(this.f$0, (Map.Entry)object, (Map.Entry)object2);
    }
}

