/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;
import java.util.stream.BaseStream;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.StreamSpliterators;
import java.util.stream.StreamSupport;
import java.util.stream.Streams;

public interface Stream<T>
extends BaseStream<T, Stream<T>> {
    public static <T> Builder<T> builder() {
        return new Streams.StreamBuilderImpl();
    }

    public static <T> Stream<T> concat(Stream<? extends T> stream, Stream<? extends T> stream2) {
        Objects.requireNonNull(stream);
        Objects.requireNonNull(stream2);
        Streams.ConcatSpliterator.OfRef ofRef = new Streams.ConcatSpliterator.OfRef(stream.spliterator(), stream2.spliterator());
        boolean bl = stream.isParallel() || stream2.isParallel();
        return (Stream)StreamSupport.stream(ofRef, bl).onClose(Streams.composedClose(stream, stream2));
    }

    public static <T> Stream<T> empty() {
        return StreamSupport.stream(Spliterators.emptySpliterator(), false);
    }

    public static <T> Stream<T> generate(Supplier<T> supplier) {
        Objects.requireNonNull(supplier);
        return StreamSupport.stream(new StreamSpliterators.InfiniteSupplyingSpliterator.OfRef<T>(Long.MAX_VALUE, supplier), false);
    }

    public static <T> Stream<T> iterate(final T t, final UnaryOperator<T> unaryOperator) {
        Objects.requireNonNull(unaryOperator);
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<T>(){
            T t = Streams.NONE;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                Object object = this.t == Streams.NONE ? t : unaryOperator.apply(this.t);
                this.t = object;
                return (T)object;
            }
        }, 1040), false);
    }

    public static <T> Stream<T> of(T t) {
        return StreamSupport.stream(new Streams.StreamBuilderImpl<T>(t), false);
    }

    @SafeVarargs
    public static <T> Stream<T> of(T ... arrT) {
        return Arrays.stream(arrT);
    }

    public boolean allMatch(Predicate<? super T> var1);

    public boolean anyMatch(Predicate<? super T> var1);

    public <R> R collect(Supplier<R> var1, BiConsumer<R, ? super T> var2, BiConsumer<R, R> var3);

    public <R, A> R collect(Collector<? super T, A, R> var1);

    public long count();

    public Stream<T> distinct();

    public Stream<T> filter(Predicate<? super T> var1);

    public Optional<T> findAny();

    public Optional<T> findFirst();

    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> var1);

    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> var1);

    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> var1);

    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> var1);

    public void forEach(Consumer<? super T> var1);

    public void forEachOrdered(Consumer<? super T> var1);

    public Stream<T> limit(long var1);

    public <R> Stream<R> map(Function<? super T, ? extends R> var1);

    public DoubleStream mapToDouble(ToDoubleFunction<? super T> var1);

    public IntStream mapToInt(ToIntFunction<? super T> var1);

    public LongStream mapToLong(ToLongFunction<? super T> var1);

    public Optional<T> max(Comparator<? super T> var1);

    public Optional<T> min(Comparator<? super T> var1);

    public boolean noneMatch(Predicate<? super T> var1);

    public Stream<T> peek(Consumer<? super T> var1);

    public <U> U reduce(U var1, BiFunction<U, ? super T, U> var2, BinaryOperator<U> var3);

    public T reduce(T var1, BinaryOperator<T> var2);

    public Optional<T> reduce(BinaryOperator<T> var1);

    public Stream<T> skip(long var1);

    public Stream<T> sorted();

    public Stream<T> sorted(Comparator<? super T> var1);

    public Object[] toArray();

    public <A> A[] toArray(IntFunction<A[]> var1);

    public static interface Builder<T>
    extends Consumer<T> {
        @Override
        public void accept(T var1);

        default public Builder<T> add(T t) {
            this.accept(t);
            return this;
        }

        public Stream<T> build();
    }

}

