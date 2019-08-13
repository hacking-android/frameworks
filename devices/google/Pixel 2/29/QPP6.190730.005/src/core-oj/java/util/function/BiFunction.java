/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.Objects;
import java.util.function.Function;
import java.util.function._$$Lambda$BiFunction$q_2HhQ1fzCu6oYNirKhp1W_vpSM;

@FunctionalInterface
public interface BiFunction<T, U, R> {
    public static /* synthetic */ Object lambda$andThen$0(BiFunction biFunction, Function function, Object object, Object object2) {
        return function.apply(biFunction.apply(object, object2));
    }

    default public <V> BiFunction<T, U, V> andThen(Function<? super R, ? extends V> function) {
        Objects.requireNonNull(function);
        return new _$$Lambda$BiFunction$q_2HhQ1fzCu6oYNirKhp1W_vpSM(this, function);
    }

    public R apply(T var1, U var2);
}

