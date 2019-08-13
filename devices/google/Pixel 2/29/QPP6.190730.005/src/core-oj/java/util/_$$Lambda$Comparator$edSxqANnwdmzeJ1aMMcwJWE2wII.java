/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.function.ToDoubleFunction;

public final class _$$Lambda$Comparator$edSxqANnwdmzeJ1aMMcwJWE2wII
implements Comparator,
Serializable {
    private final /* synthetic */ ToDoubleFunction f$0;

    public /* synthetic */ _$$Lambda$Comparator$edSxqANnwdmzeJ1aMMcwJWE2wII(ToDoubleFunction toDoubleFunction) {
        this.f$0 = toDoubleFunction;
    }

    public final int compare(Object object, Object object2) {
        return Comparator.lambda$comparingDouble$8dcf42ea$1(this.f$0, object, object2);
    }
}

