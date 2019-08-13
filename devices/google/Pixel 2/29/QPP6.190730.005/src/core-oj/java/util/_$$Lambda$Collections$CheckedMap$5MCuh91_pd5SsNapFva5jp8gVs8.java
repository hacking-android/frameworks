/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Collections;
import java.util.function.BiFunction;

public final class _$$Lambda$Collections$CheckedMap$5MCuh91_pd5SsNapFva5jp8gVs8
implements BiFunction {
    private final /* synthetic */ Collections.CheckedMap f$0;
    private final /* synthetic */ BiFunction f$1;

    public /* synthetic */ _$$Lambda$Collections$CheckedMap$5MCuh91_pd5SsNapFva5jp8gVs8(Collections.CheckedMap checkedMap, BiFunction biFunction) {
        this.f$0 = checkedMap;
        this.f$1 = biFunction;
    }

    public final Object apply(Object object, Object object2) {
        return this.f$0.lambda$typeCheck$0$Collections$CheckedMap(this.f$1, object, object2);
    }
}

