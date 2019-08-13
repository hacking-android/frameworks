/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class _$$Lambda$BiFunction$q_2HhQ1fzCu6oYNirKhp1W_vpSM
implements BiFunction {
    private final /* synthetic */ BiFunction f$0;
    private final /* synthetic */ Function f$1;

    public /* synthetic */ _$$Lambda$BiFunction$q_2HhQ1fzCu6oYNirKhp1W_vpSM(BiFunction biFunction, Function function) {
        this.f$0 = biFunction;
        this.f$1 = function;
    }

    public final Object apply(Object object, Object object2) {
        return BiFunction.lambda$andThen$0(this.f$0, this.f$1, object, object2);
    }
}

