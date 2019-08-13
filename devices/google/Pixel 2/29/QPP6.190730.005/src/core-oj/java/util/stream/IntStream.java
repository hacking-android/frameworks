/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSpliterators;
import java.util.stream.StreamSupport;
import java.util.stream.Streams;

public interface IntStream
extends BaseStream<Integer, IntStream> {
    public static Builder builder() {
        return new Streams.IntStreamBuilderImpl();
    }

    public static IntStream concat(IntStream intStream, IntStream intStream2) {
        Objects.requireNonNull(intStream);
        Objects.requireNonNull(intStream2);
        Streams.ConcatSpliterator.OfInt ofInt = new Streams.ConcatSpliterator.OfInt(intStream.spliterator(), intStream2.spliterator());
        boolean bl = intStream.isParallel() || intStream2.isParallel();
        return (IntStream)StreamSupport.intStream(ofInt, bl).onClose(Streams.composedClose(intStream, intStream2));
    }

    public static IntStream empty() {
        return StreamSupport.intStream(Spliterators.emptyIntSpliterator(), false);
    }

    public static IntStream generate(IntSupplier intSupplier) {
        Objects.requireNonNull(intSupplier);
        return StreamSupport.intStream(new StreamSpliterators.InfiniteSupplyingSpliterator.OfInt(Long.MAX_VALUE, intSupplier), false);
    }

    public static IntStream iterate(final int n, final IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        return StreamSupport.intStream(Spliterators.spliteratorUnknownSize(new PrimitiveIterator.OfInt(){
            int t;
            {
                this.t = n;
            }

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public int nextInt() {
                int n2 = this.t;
                this.t = intUnaryOperator.applyAsInt(this.t);
                return n2;
            }
        }, 1296), false);
    }

    public static IntStream of(int n) {
        return StreamSupport.intStream(new Streams.IntStreamBuilderImpl(n), false);
    }

    public static IntStream of(int ... arrn) {
        return Arrays.stream(arrn);
    }

    public static IntStream range(int n, int n2) {
        if (n >= n2) {
            return IntStream.empty();
        }
        return StreamSupport.intStream(new Streams.RangeIntSpliterator(n, n2, false), false);
    }

    public static IntStream rangeClosed(int n, int n2) {
        if (n > n2) {
            return IntStream.empty();
        }
        return StreamSupport.intStream(new Streams.RangeIntSpliterator(n, n2, true), false);
    }

    public boolean allMatch(IntPredicate var1);

    public boolean anyMatch(IntPredicate var1);

    public DoubleStream asDoubleStream();

    public LongStream asLongStream();

    public OptionalDouble average();

    public Stream<Integer> boxed();

    public <R> R collect(Supplier<R> var1, ObjIntConsumer<R> var2, BiConsumer<R, R> var3);

    public long count();

    public IntStream distinct();

    public IntStream filter(IntPredicate var1);

    public OptionalInt findAny();

    public OptionalInt findFirst();

    public IntStream flatMap(IntFunction<? extends IntStream> var1);

    public void forEach(IntConsumer var1);

    public void forEachOrdered(IntConsumer var1);

    public PrimitiveIterator.OfInt iterator();

    public IntStream limit(long var1);

    public IntStream map(IntUnaryOperator var1);

    public DoubleStream mapToDouble(IntToDoubleFunction var1);

    public LongStream mapToLong(IntToLongFunction var1);

    public <U> Stream<U> mapToObj(IntFunction<? extends U> var1);

    public OptionalInt max();

    public OptionalInt min();

    public boolean noneMatch(IntPredicate var1);

    @Override
    public IntStream parallel();

    public IntStream peek(IntConsumer var1);

    public int reduce(int var1, IntBinaryOperator var2);

    public OptionalInt reduce(IntBinaryOperator var1);

    @Override
    public IntStream sequential();

    public IntStream skip(long var1);

    public IntStream sorted();

    public Spliterator.OfInt spliterator();

    public int sum();

    public IntSummaryStatistics summaryStatistics();

    public int[] toArray();

    public static interface Builder
    extends IntConsumer {
        @Override
        public void accept(int var1);

        default public Builder add(int n) {
            this.accept(n);
            return this;
        }

        public IntStream build();
    }

}

