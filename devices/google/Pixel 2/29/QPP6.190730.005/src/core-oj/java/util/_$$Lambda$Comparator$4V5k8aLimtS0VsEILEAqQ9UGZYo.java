/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.function.ToLongFunction;

public final class _$$Lambda$Comparator$4V5k8aLimtS0VsEILEAqQ9UGZYo
implements Comparator,
Serializable {
    private final /* synthetic */ ToLongFunction f$0;

    public /* synthetic */ _$$Lambda$Comparator$4V5k8aLimtS0VsEILEAqQ9UGZYo(ToLongFunction toLongFunction) {
        this.f$0 = toLongFunction;
    }

    public final int compare(Object object, Object object2) {
        return Comparator.lambda$comparingLong$6043328a$1(this.f$0, object, object2);
    }
}

