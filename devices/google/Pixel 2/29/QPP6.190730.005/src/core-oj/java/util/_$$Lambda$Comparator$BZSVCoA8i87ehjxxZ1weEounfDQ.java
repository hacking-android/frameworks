/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.Serializable;
import java.util.Comparator;

public final class _$$Lambda$Comparator$BZSVCoA8i87ehjxxZ1weEounfDQ
implements Comparator,
Serializable {
    private final /* synthetic */ Comparator f$0;
    private final /* synthetic */ Comparator f$1;

    public /* synthetic */ _$$Lambda$Comparator$BZSVCoA8i87ehjxxZ1weEounfDQ(Comparator comparator, Comparator comparator2) {
        this.f$0 = comparator;
        this.f$1 = comparator2;
    }

    public final int compare(Object object, Object object2) {
        return Comparator.lambda$thenComparing$36697e65$1(this.f$0, this.f$1, object, object2);
    }
}

