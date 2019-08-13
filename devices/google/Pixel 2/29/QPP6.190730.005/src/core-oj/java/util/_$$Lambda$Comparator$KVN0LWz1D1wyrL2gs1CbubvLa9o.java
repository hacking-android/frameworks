/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.function.Function;

public final class _$$Lambda$Comparator$KVN0LWz1D1wyrL2gs1CbubvLa9o
implements Comparator,
Serializable {
    private final /* synthetic */ Comparator f$0;
    private final /* synthetic */ Function f$1;

    public /* synthetic */ _$$Lambda$Comparator$KVN0LWz1D1wyrL2gs1CbubvLa9o(Comparator comparator, Function function) {
        this.f$0 = comparator;
        this.f$1 = function;
    }

    public final int compare(Object object, Object object2) {
        return Comparator.lambda$comparing$ea9a8b3a$1(this.f$0, this.f$1, object, object2);
    }
}

