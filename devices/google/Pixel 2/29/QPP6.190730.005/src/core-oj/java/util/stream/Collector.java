/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface Collector<T, A, R> {
    public static <T, A, R> Collector<T, A, R> of(Supplier<A> supplier, BiConsumer<A, T> biConsumer, BinaryOperator<A> binaryOperator, Function<A, R> function, Characteristics ... arrcharacteristics) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(biConsumer);
        Objects.requireNonNull(binaryOperator);
        Objects.requireNonNull(function);
        Objects.requireNonNull(arrcharacteristics);
        Set<Characteristics> set = Collectors.CH_NOID;
        if (arrcharacteristics.length > 0) {
            set = EnumSet.noneOf(Characteristics.class);
            Collections.addAll(set, arrcharacteristics);
            set = Collections.unmodifiableSet(set);
        }
        return new Collectors.CollectorImpl<T, A, R>(supplier, biConsumer, binaryOperator, function, set);
    }

    public static <T, R> Collector<T, R, R> of(Supplier<R> supplier, BiConsumer<R, T> biConsumer, BinaryOperator<R> binaryOperator, Characteristics ... object) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(biConsumer);
        Objects.requireNonNull(binaryOperator);
        Objects.requireNonNull(object);
        object = ((Characteristics[])object).length == 0 ? Collectors.CH_ID : Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH, object));
        return new Collectors.CollectorImpl(supplier, biConsumer, binaryOperator, (Set<Characteristics>)object);
    }

    public BiConsumer<A, T> accumulator();

    public Set<Characteristics> characteristics();

    public BinaryOperator<A> combiner();

    public Function<A, R> finisher();

    public Supplier<A> supplier();

    public static enum Characteristics {
        CONCURRENT,
        UNORDERED,
        IDENTITY_FINISH;
        
    }

}

