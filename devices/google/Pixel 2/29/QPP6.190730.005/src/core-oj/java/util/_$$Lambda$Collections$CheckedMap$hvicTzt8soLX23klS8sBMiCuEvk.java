/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Collections;
import java.util.function.Function;

public final class _$$Lambda$Collections$CheckedMap$hvicTzt8soLX23klS8sBMiCuEvk
implements Function {
    private final /* synthetic */ Collections.CheckedMap f$0;
    private final /* synthetic */ Function f$1;

    public /* synthetic */ _$$Lambda$Collections$CheckedMap$hvicTzt8soLX23klS8sBMiCuEvk(Collections.CheckedMap checkedMap, Function function) {
        this.f$0 = checkedMap;
        this.f$1 = function;
    }

    public final Object apply(Object object) {
        return this.f$0.lambda$computeIfAbsent$1$Collections$CheckedMap(this.f$1, object);
    }
}

