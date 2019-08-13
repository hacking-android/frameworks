/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Collections;
import java.util.function.BiFunction;

public final class _$$Lambda$Collections$CheckedMap$UHd_dm3NjSdMnE1bZpQe0MXp_Gk
implements BiFunction {
    private final /* synthetic */ Collections.CheckedMap f$0;
    private final /* synthetic */ BiFunction f$1;

    public /* synthetic */ _$$Lambda$Collections$CheckedMap$UHd_dm3NjSdMnE1bZpQe0MXp_Gk(Collections.CheckedMap checkedMap, BiFunction biFunction) {
        this.f$0 = checkedMap;
        this.f$1 = biFunction;
    }

    public final Object apply(Object object, Object object2) {
        return this.f$0.lambda$merge$2$Collections$CheckedMap(this.f$1, object, object2);
    }
}

