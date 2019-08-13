/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.function.-$
 *  java.util.function.-$$Lambda
 *  java.util.function.-$$Lambda$Function
 *  java.util.function.-$$Lambda$Function$1mm3dZ9IMG2T6zAULCCEh3eoHSY
 */
package java.util.function;

import java.util.Objects;
import java.util.function.-$;
import java.util.function._$$Lambda$Function$1mm3dZ9IMG2T6zAULCCEh3eoHSY;
import java.util.function._$$Lambda$Function$T8wYIfMRq5hbW0Q4qNkHIIrI_BA;
import java.util.function._$$Lambda$Function$kjgb589uNKoZ3YFTNw1Kwl_DNBo;

@FunctionalInterface
public interface Function<T, R> {
    public static <T> Function<T, T> identity() {
        return _$$Lambda$Function$1mm3dZ9IMG2T6zAULCCEh3eoHSY.INSTANCE;
    }

    public static /* synthetic */ Object lambda$andThen$1(Function function, Function function2, Object object) {
        return function2.apply(function.apply(object));
    }

    public static /* synthetic */ Object lambda$compose$0(Function function, Function function2, Object object) {
        return function.apply(function2.apply(object));
    }

    public static /* synthetic */ Object lambda$identity$2(Object object) {
        return object;
    }

    default public <V> Function<T, V> andThen(Function<? super R, ? extends V> function) {
        Objects.requireNonNull(function);
        return new _$$Lambda$Function$T8wYIfMRq5hbW0Q4qNkHIIrI_BA(this, function);
    }

    public R apply(T var1);

    default public <V> Function<V, R> compose(Function<? super V, ? extends T> function) {
        Objects.requireNonNull(function);
        return new _$$Lambda$Function$kjgb589uNKoZ3YFTNw1Kwl_DNBo(this, function);
    }
}

