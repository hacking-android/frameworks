/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LongSummaryStatistics;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSpliterators;
import java.util.stream.StreamSupport;
import java.util.stream.Streams;

public interface LongStream
extends BaseStream<Long, LongStream> {
    public static Builder builder() {
        return new Streams.LongStreamBuilderImpl();
    }

    public static LongStream concat(LongStream longStream, LongStream longStream2) {
        Objects.requireNonNull(longStream);
        Objects.requireNonNull(longStream2);
        Streams.ConcatSpliterator.OfLong ofLong = new Streams.ConcatSpliterator.OfLong(longStream.spliterator(), longStream2.spliterator());
        boolean bl = longStream.isParallel() || longStream2.isParallel();
        return (LongStream)StreamSupport.longStream(ofLong, bl).onClose(Streams.composedClose(longStream, longStream2));
    }

    public static LongStream empty() {
        return StreamSupport.longStream(Spliterators.emptyLongSpliterator(), false);
    }

    public static LongStream generate(LongSupplier longSupplier) {
        Objects.requireNonNull(longSupplier);
        return StreamSupport.longStream(new StreamSpliterators.InfiniteSupplyingSpliterator.OfLong(Long.MAX_VALUE, longSupplier), false);
    }

    public static LongStream iterate(final long l, final LongUnaryOperator longUnaryOperator) {
        Objects.requireNonNull(longUnaryOperator);
        return StreamSupport.longStream(Spliterators.spliteratorUnknownSize(new PrimitiveIterator.OfLong(){
            long t;
            {
                this.t = l;
            }

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public long nextLong() {
                long l2 = this.t;
                this.t = longUnaryOperator.applyAsLong(this.t);
                return l2;
            }
        }, 1296), false);
    }

    public static LongStream of(long l) {
        return StreamSupport.longStream(new Streams.LongStreamBuilderImpl(l), false);
    }

    public static LongStream of(long ... arrl) {
        return Arrays.stream(arrl);
    }

    public static LongStream range(long l, long l2) {
        if (l >= l2) {
            return LongStream.empty();
        }
        if (l2 - l < 0L) {
            long l3 = Long.divideUnsigned(l2 - l, 2L) + l + 1L;
            return LongStream.concat(LongStream.range(l, l3), LongStream.range(l3, l2));
        }
        return StreamSupport.longStream(new Streams.RangeLongSpliterator(l, l2, false), false);
    }

    public static LongStream rangeClosed(long l, long l2) {
        if (l > l2) {
            return LongStream.empty();
        }
        if (l2 - l + 1L <= 0L) {
            long l3 = Long.divideUnsigned(l2 - l, 2L) + l + 1L;
            return LongStream.concat(LongStream.range(l, l3), LongStream.rangeClosed(l3, l2));
        }
        return StreamSupport.longStream(new Streams.RangeLongSpliterator(l, l2, true), false);
    }

    public boolean allMatch(LongPredicate var1);

    public boolean anyMatch(LongPredicate var1);

    public DoubleStream asDoubleStream();

    public OptionalDouble average();

    public Stream<Long> boxed();

    public <R> R collect(Supplier<R> var1, ObjLongConsumer<R> var2, BiConsumer<R, R> var3);

    public long count();

    public LongStream distinct();

    public LongStream filter(LongPredicate var1);

    public OptionalLong findAny();

    public OptionalLong findFirst();

    public LongStream flatMap(LongFunction<? extends LongStream> var1);

    public void forEach(LongConsumer var1);

    public void forEachOrdered(LongConsumer var1);

    public PrimitiveIterator.OfLong iterator();

    public LongStream limit(long var1);

    public LongStream map(LongUnaryOperator var1);

    public DoubleStream mapToDouble(LongToDoubleFunction var1);

    public IntStream mapToInt(LongToIntFunction var1);

    public <U> Stream<U> mapToObj(LongFunction<? extends U> var1);

    public OptionalLong max();

    public OptionalLong min();

    public boolean noneMatch(LongPredicate var1);

    @Override
    public LongStream parallel();

    public LongStream peek(LongConsumer var1);

    public long reduce(long var1, LongBinaryOperator var3);

    public OptionalLong reduce(LongBinaryOperator var1);

    @Override
    public LongStream sequential();

    public LongStream skip(long var1);

    public LongStream sorted();

    public Spliterator.OfLong spliterator();

    public long sum();

    public LongSummaryStatistics summaryStatistics();

    public long[] toArray();

    public static interface Builder
    extends LongConsumer {
        @Override
        public void accept(long var1);

        default public Builder add(long l) {
            this.accept(l);
            return this;
        }

        public LongStream build();
    }

}

