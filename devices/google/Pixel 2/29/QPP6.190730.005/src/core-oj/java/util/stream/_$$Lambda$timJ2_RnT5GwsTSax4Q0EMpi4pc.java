/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$timJ2_RnT5GwsTSax4Q0EMpi4pc
 */
package java.util.stream;

import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.-$;

public final class _$$Lambda$timJ2_RnT5GwsTSax4Q0EMpi4pc
implements Predicate {
    public static final /* synthetic */ -$.Lambda.timJ2_RnT5GwsTSax4Q0EMpi4pc INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$timJ2_RnT5GwsTSax4Q0EMpi4pc();
    }

    private /* synthetic */ _$$Lambda$timJ2_RnT5GwsTSax4Q0EMpi4pc() {
    }

    public final boolean test(Object object) {
        return ((OptionalInt)object).isPresent();
    }
}

