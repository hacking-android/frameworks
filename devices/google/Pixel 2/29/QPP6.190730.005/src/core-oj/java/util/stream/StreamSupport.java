/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Supplier;
import java.util.stream.DoublePipeline;
import java.util.stream.DoubleStream;
import java.util.stream.IntPipeline;
import java.util.stream.IntStream;
import java.util.stream.LongPipeline;
import java.util.stream.LongStream;
import java.util.stream.ReferencePipeline;
import java.util.stream.Stream;
import java.util.stream.StreamOpFlag;

public final class StreamSupport {
    private StreamSupport() {
    }

    public static DoubleStream doubleStream(Spliterator.OfDouble ofDouble, boolean bl) {
        return new DoublePipeline.Head(ofDouble, StreamOpFlag.fromCharacteristics(ofDouble), bl);
    }

    public static DoubleStream doubleStream(Supplier<? extends Spliterator.OfDouble> supplier, int n, boolean bl) {
        return new DoublePipeline.Head(supplier, StreamOpFlag.fromCharacteristics(n), bl);
    }

    public static IntStream intStream(Spliterator.OfInt ofInt, boolean bl) {
        return new IntPipeline.Head(ofInt, StreamOpFlag.fromCharacteristics(ofInt), bl);
    }

    public static IntStream intStream(Supplier<? extends Spliterator.OfInt> supplier, int n, boolean bl) {
        return new IntPipeline.Head(supplier, StreamOpFlag.fromCharacteristics(n), bl);
    }

    public static LongStream longStream(Spliterator.OfLong ofLong, boolean bl) {
        return new LongPipeline.Head(ofLong, StreamOpFlag.fromCharacteristics(ofLong), bl);
    }

    public static LongStream longStream(Supplier<? extends Spliterator.OfLong> supplier, int n, boolean bl) {
        return new LongPipeline.Head(supplier, StreamOpFlag.fromCharacteristics(n), bl);
    }

    public static <T> Stream<T> stream(Spliterator<T> spliterator, boolean bl) {
        Objects.requireNonNull(spliterator);
        return new ReferencePipeline.Head(spliterator, StreamOpFlag.fromCharacteristics(spliterator), bl);
    }

    public static <T> Stream<T> stream(Supplier<? extends Spliterator<T>> supplier, int n, boolean bl) {
        Objects.requireNonNull(supplier);
        return new ReferencePipeline.Head(supplier, StreamOpFlag.fromCharacteristics(n), bl);
    }
}

