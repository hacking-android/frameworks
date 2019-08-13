/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.lang.invoke.SerializedLambda;
import java.util.Collections;
import java.util.Comparators;
import java.util.Objects;
import java.util._$$Lambda$Comparator$4V5k8aLimtS0VsEILEAqQ9UGZYo;
import java.util._$$Lambda$Comparator$BZSVCoA8i87ehjxxZ1weEounfDQ;
import java.util._$$Lambda$Comparator$DNgpxUFZqmT4lOBzlVyPjWwvEvw;
import java.util._$$Lambda$Comparator$KVN0LWz1D1wyrL2gs1CbubvLa9o;
import java.util._$$Lambda$Comparator$SPB8K9Yj7Pw1mljm7LpasV7zxWw;
import java.util._$$Lambda$Comparator$edSxqANnwdmzeJ1aMMcwJWE2wII;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

@FunctionalInterface
public interface Comparator<T> {
    public static <T, U extends Comparable<? super U>> Comparator<T> comparing(Function<? super T, ? extends U> function) {
        Objects.requireNonNull(function);
        return new _$$Lambda$Comparator$SPB8K9Yj7Pw1mljm7LpasV7zxWw(function);
    }

    public static <T, U> Comparator<T> comparing(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        Objects.requireNonNull(function);
        Objects.requireNonNull(comparator);
        return new _$$Lambda$Comparator$KVN0LWz1D1wyrL2gs1CbubvLa9o(comparator, function);
    }

    public static <T> Comparator<T> comparingDouble(ToDoubleFunction<? super T> toDoubleFunction) {
        Objects.requireNonNull(toDoubleFunction);
        return new _$$Lambda$Comparator$edSxqANnwdmzeJ1aMMcwJWE2wII(toDoubleFunction);
    }

    public static <T> Comparator<T> comparingInt(ToIntFunction<? super T> toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        return new _$$Lambda$Comparator$DNgpxUFZqmT4lOBzlVyPjWwvEvw(toIntFunction);
    }

    public static <T> Comparator<T> comparingLong(ToLongFunction<? super T> toLongFunction) {
        Objects.requireNonNull(toLongFunction);
        return new _$$Lambda$Comparator$4V5k8aLimtS0VsEILEAqQ9UGZYo(toLongFunction);
    }

    public static /* synthetic */ int lambda$comparing$77a9974f$1(Function function, Object object, Object object2) {
        return ((Comparable)function.apply(object)).compareTo(function.apply(object2));
    }

    public static /* synthetic */ int lambda$comparing$ea9a8b3a$1(Comparator comparator, Function function, Object object, Object object2) {
        return comparator.compare(function.apply(object), function.apply(object2));
    }

    public static /* synthetic */ int lambda$comparingDouble$8dcf42ea$1(ToDoubleFunction toDoubleFunction, Object object, Object object2) {
        return Double.compare(toDoubleFunction.applyAsDouble(object), toDoubleFunction.applyAsDouble(object2));
    }

    public static /* synthetic */ int lambda$comparingInt$7b0bb60$1(ToIntFunction toIntFunction, Object object, Object object2) {
        return Integer.compare(toIntFunction.applyAsInt(object), toIntFunction.applyAsInt(object2));
    }

    public static /* synthetic */ int lambda$comparingLong$6043328a$1(ToLongFunction toLongFunction, Object object, Object object2) {
        return Long.compare(toLongFunction.applyAsLong(object), toLongFunction.applyAsLong(object2));
    }

    public static /* synthetic */ int lambda$thenComparing$36697e65$1(Comparator comparator, Comparator comparator2, Object object, Object object2) {
        int n = comparator.compare(object, object2);
        if (n == 0) {
            n = comparator2.compare(object, object2);
        }
        return n;
    }

    public static <T extends Comparable<? super T>> Comparator<T> naturalOrder() {
        return Comparators.NaturalOrderComparator.INSTANCE;
    }

    public static <T> Comparator<T> nullsFirst(Comparator<? super T> comparator) {
        return new Comparators.NullComparator<T>(true, comparator);
    }

    public static <T> Comparator<T> nullsLast(Comparator<? super T> comparator) {
        return new Comparators.NullComparator<T>(false, comparator);
    }

    public static <T extends Comparable<? super T>> Comparator<T> reverseOrder() {
        return Collections.reverseOrder();
    }

    public int compare(T var1, T var2);

    public boolean equals(Object var1);

    default public Comparator<T> reversed() {
        return Collections.reverseOrder(this);
    }

    default public Comparator<T> thenComparing(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator);
        return new _$$Lambda$Comparator$BZSVCoA8i87ehjxxZ1weEounfDQ(this, comparator);
    }

    default public <U extends Comparable<? super U>> Comparator<T> thenComparing(Function<? super T, ? extends U> function) {
        return this.thenComparing(Comparator.comparing(function));
    }

    default public <U> Comparator<T> thenComparing(Function<? super T, ? extends U> function, Comparator<? super U> comparator) {
        return this.thenComparing(Comparator.comparing(function, comparator));
    }

    default public Comparator<T> thenComparingDouble(ToDoubleFunction<? super T> toDoubleFunction) {
        return this.thenComparing(Comparator.comparingDouble(toDoubleFunction));
    }

    default public Comparator<T> thenComparingInt(ToIntFunction<? super T> toIntFunction) {
        return this.thenComparing(Comparator.comparingInt(toIntFunction));
    }

    default public Comparator<T> thenComparingLong(ToLongFunction<? super T> toLongFunction) {
        return this.thenComparing(Comparator.comparingLong(toLongFunction));
    }
}

