/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.Iterator;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSpliterators;
import java.util.stream.StreamSupport;
import java.util.stream.Streams;

public interface DoubleStream
extends BaseStream<Double, DoubleStream> {
    public static Builder builder() {
        return new Streams.DoubleStreamBuilderImpl();
    }

    public static DoubleStream concat(DoubleStream doubleStream, DoubleStream doubleStream2) {
        Objects.requireNonNull(doubleStream);
        Objects.requireNonNull(doubleStream2);
        Streams.ConcatSpliterator.OfDouble ofDouble = new Streams.ConcatSpliterator.OfDouble(doubleStream.spliterator(), doubleStream2.spliterator());
        boolean bl = doubleStream.isParallel() || doubleStream2.isParallel();
        return (DoubleStream)StreamSupport.doubleStream(ofDouble, bl).onClose(Streams.composedClose(doubleStream, doubleStream2));
    }

    public static DoubleStream empty() {
        return StreamSupport.doubleStream(Spliterators.emptyDoubleSpliterator(), false);
    }

    public static DoubleStream generate(DoubleSupplier doubleSupplier) {
        Objects.requireNonNull(doubleSupplier);
        return StreamSupport.doubleStream(new StreamSpliterators.InfiniteSupplyingSpliterator.OfDouble(Long.MAX_VALUE, doubleSupplier), false);
    }

    public static DoubleStream iterate(final double d, final DoubleUnaryOperator doubleUnaryOperator) {
        Objects.requireNonNull(doubleUnaryOperator);
        return StreamSupport.doubleStream(Spliterators.spliteratorUnknownSize(new PrimitiveIterator.OfDouble(){
            double t;
            {
                this.t = d;
            }

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public double nextDouble() {
                double d2 = this.t;
                this.t = doubleUnaryOperator.applyAsDouble(this.t);
                return d2;
            }
        }, 1296), false);
    }

    public static DoubleStream of(double d) {
        return StreamSupport.doubleStream(new Streams.DoubleStreamBuilderImpl(d), false);
    }

    public static DoubleStream of(double ... arrd) {
        return Arrays.stream(arrd);
    }

    public boolean allMatch(DoublePredicate var1);

    public boolean anyMatch(DoublePredicate var1);

    public OptionalDouble average();

    public Stream<Double> boxed();

    public <R> R collect(Supplier<R> var1, ObjDoubleConsumer<R> var2, BiConsumer<R, R> var3);

    public long count();

    public DoubleStream distinct();

    public DoubleStream filter(DoublePredicate var1);

    public OptionalDouble findAny();

    public OptionalDouble findFirst();

    public DoubleStream flatMap(DoubleFunction<? extends DoubleStream> var1);

    public void forEach(DoubleConsumer var1);

    public void forEachOrdered(DoubleConsumer var1);

    public PrimitiveIterator.OfDouble iterator();

    public DoubleStream limit(long var1);

    public DoubleStream map(DoubleUnaryOperator var1);

    public IntStream mapToInt(DoubleToIntFunction var1);

    public LongStream mapToLong(DoubleToLongFunction var1);

    public <U> Stream<U> mapToObj(DoubleFunction<? extends U> var1);

    public OptionalDouble max();

    public OptionalDouble min();

    public boolean noneMatch(DoublePredicate var1);

    @Override
    public DoubleStream parallel();

    public DoubleStream peek(DoubleConsumer var1);

    public double reduce(double var1, DoubleBinaryOperator var3);

    public OptionalDouble reduce(DoubleBinaryOperator var1);

    @Override
    public DoubleStream sequential();

    public DoubleStream skip(long var1);

    public DoubleStream sorted();

    public Spliterator.OfDouble spliterator();

    public double sum();

    public DoubleSummaryStatistics summaryStatistics();

    public double[] toArray();

    public static interface Builder
    extends DoubleConsumer {
        @Override
        public void accept(double var1);

        default public Builder add(double d) {
            this.accept(d);
            return this;
        }

        public DoubleStream build();
    }

}

