/*
 * Decompiled with CFR 0.145.
 */
package java.util.function;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function._$$Lambda$BinaryOperator$V_WUclL0kAOZvMw9EtWtwAvmNJc;
import java.util.function._$$Lambda$BinaryOperator$WKN0kahVeFfmJEk_tKszY8tRayo;

@FunctionalInterface
public interface BinaryOperator<T>
extends BiFunction<T, T, T> {
    public static /* synthetic */ Object lambda$maxBy$1(Comparator comparator, Object object, Object object2) {
        if (comparator.compare(object, object2) < 0) {
            object = object2;
        }
        return object;
    }

    public static /* synthetic */ Object lambda$minBy$0(Comparator object, Object object2, Object object3) {
        object = object.compare(object2, object3) <= 0 ? object2 : object3;
        return object;
    }

    public static <T> BinaryOperator<T> maxBy(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        return new _$$Lambda$BinaryOperator$V_WUclL0kAOZvMw9EtWtwAvmNJc(comparator);
    }

    public static <T> BinaryOperator<T> minBy(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        return new _$$Lambda$BinaryOperator$WKN0kahVeFfmJEk_tKszY8tRayo(comparator);
    }
}

