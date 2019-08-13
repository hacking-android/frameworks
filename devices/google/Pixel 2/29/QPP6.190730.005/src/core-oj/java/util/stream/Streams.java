/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Comparator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.SpinedBuffer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

final class Streams {
    static final Object NONE = new Object();

    private Streams() {
        throw new Error("no instances");
    }

    static Runnable composeWithExceptions(final Runnable runnable, final Runnable runnable2) {
        return new Runnable(){

            @Override
            public void run() {
                try {
                    runnable.run();
                    runnable2.run();
                    return;
                }
                catch (Throwable throwable) {
                    try {
                        runnable2.run();
                    }
                    catch (Throwable throwable2) {
                        try {
                            throwable.addSuppressed(throwable2);
                        }
                        catch (Throwable throwable3) {}
                    }
                    throw throwable;
                }
            }
        };
    }

    static Runnable composedClose(final BaseStream<?, ?> baseStream, final BaseStream<?, ?> baseStream2) {
        return new Runnable(){

            @Override
            public void run() {
                try {
                    baseStream.close();
                    baseStream2.close();
                    return;
                }
                catch (Throwable throwable) {
                    try {
                        baseStream2.close();
                    }
                    catch (Throwable throwable2) {
                        try {
                            throwable.addSuppressed(throwable2);
                        }
                        catch (Throwable throwable3) {}
                    }
                    throw throwable;
                }
            }
        };
    }

    private static abstract class AbstractStreamBuilderImpl<T, S extends Spliterator<T>>
    implements Spliterator<T> {
        int count;

        private AbstractStreamBuilderImpl() {
        }

        @Override
        public int characteristics() {
            return 17488;
        }

        @Override
        public long estimateSize() {
            return -this.count - 1;
        }

        public S trySplit() {
            return null;
        }
    }

    static abstract class ConcatSpliterator<T, T_SPLITR extends Spliterator<T>>
    implements Spliterator<T> {
        protected final T_SPLITR aSpliterator;
        protected final T_SPLITR bSpliterator;
        boolean beforeSplit;
        final boolean unsized;

        public ConcatSpliterator(T_SPLITR T_SPLITR, T_SPLITR T_SPLITR2) {
            this.aSpliterator = T_SPLITR;
            this.bSpliterator = T_SPLITR2;
            boolean bl = true;
            this.beforeSplit = true;
            if (T_SPLITR.estimateSize() + T_SPLITR2.estimateSize() >= 0L) {
                bl = false;
            }
            this.unsized = bl;
        }

        @Override
        public int characteristics() {
            if (this.beforeSplit) {
                int n = this.aSpliterator.characteristics();
                int n2 = this.bSpliterator.characteristics();
                int n3 = this.unsized ? 16448 : 0;
                return n & n2 & (n3 | 5);
            }
            return this.bSpliterator.characteristics();
        }

        @Override
        public long estimateSize() {
            if (this.beforeSplit) {
                long l = this.aSpliterator.estimateSize() + this.bSpliterator.estimateSize();
                if (l < 0L) {
                    l = Long.MAX_VALUE;
                }
                return l;
            }
            return this.bSpliterator.estimateSize();
        }

        @Override
        public void forEachRemaining(Consumer<? super T> consumer) {
            if (this.beforeSplit) {
                this.aSpliterator.forEachRemaining(consumer);
            }
            this.bSpliterator.forEachRemaining(consumer);
        }

        @Override
        public Comparator<? super T> getComparator() {
            if (!this.beforeSplit) {
                return this.bSpliterator.getComparator();
            }
            throw new IllegalStateException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> consumer) {
            boolean bl;
            if (this.beforeSplit) {
                boolean bl2;
                bl = bl2 = this.aSpliterator.tryAdvance(consumer);
                if (!bl2) {
                    this.beforeSplit = false;
                    bl = this.bSpliterator.tryAdvance(consumer);
                }
            } else {
                bl = this.bSpliterator.tryAdvance(consumer);
            }
            return bl;
        }

        public T_SPLITR trySplit() {
            Object object = this.beforeSplit ? this.aSpliterator : this.bSpliterator.trySplit();
            this.beforeSplit = false;
            return object;
        }

        static class OfDouble
        extends OfPrimitive<Double, DoubleConsumer, Spliterator.OfDouble>
        implements Spliterator.OfDouble {
            OfDouble(Spliterator.OfDouble ofDouble, Spliterator.OfDouble ofDouble2) {
                super(ofDouble, ofDouble2, null);
            }
        }

        static class OfInt
        extends OfPrimitive<Integer, IntConsumer, Spliterator.OfInt>
        implements Spliterator.OfInt {
            OfInt(Spliterator.OfInt ofInt, Spliterator.OfInt ofInt2) {
                super(ofInt, ofInt2, null);
            }
        }

        static class OfLong
        extends OfPrimitive<Long, LongConsumer, Spliterator.OfLong>
        implements Spliterator.OfLong {
            OfLong(Spliterator.OfLong ofLong, Spliterator.OfLong ofLong2) {
                super(ofLong, ofLong2, null);
            }
        }

        private static abstract class OfPrimitive<T, T_CONS, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>>
        extends ConcatSpliterator<T, T_SPLITR>
        implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR> {
            private OfPrimitive(T_SPLITR T_SPLITR, T_SPLITR T_SPLITR2) {
                super(T_SPLITR, T_SPLITR2);
            }

            /* synthetic */ OfPrimitive(Spliterator.OfPrimitive ofPrimitive, Spliterator.OfPrimitive ofPrimitive2, 1 var3_3) {
                this(ofPrimitive, ofPrimitive2);
            }

            @Override
            public void forEachRemaining(T_CONS T_CONS) {
                if (this.beforeSplit) {
                    ((Spliterator.OfPrimitive)this.aSpliterator).forEachRemaining(T_CONS);
                }
                ((Spliterator.OfPrimitive)this.bSpliterator).forEachRemaining(T_CONS);
            }

            @Override
            public boolean tryAdvance(T_CONS T_CONS) {
                boolean bl;
                if (this.beforeSplit) {
                    boolean bl2;
                    bl = bl2 = ((Spliterator.OfPrimitive)this.aSpliterator).tryAdvance(T_CONS);
                    if (!bl2) {
                        this.beforeSplit = false;
                        bl = ((Spliterator.OfPrimitive)this.bSpliterator).tryAdvance(T_CONS);
                    }
                } else {
                    bl = ((Spliterator.OfPrimitive)this.bSpliterator).tryAdvance(T_CONS);
                }
                return bl;
            }
        }

        static class OfRef<T>
        extends ConcatSpliterator<T, Spliterator<T>> {
            OfRef(Spliterator<T> spliterator, Spliterator<T> spliterator2) {
                super(spliterator, spliterator2);
            }
        }

    }

    static final class DoubleStreamBuilderImpl
    extends AbstractStreamBuilderImpl<Double, Spliterator.OfDouble>
    implements DoubleStream.Builder,
    Spliterator.OfDouble {
        SpinedBuffer.OfDouble buffer;
        double first;

        DoubleStreamBuilderImpl() {
        }

        DoubleStreamBuilderImpl(double d) {
            this.first = d;
            this.count = -2;
        }

        @Override
        public void accept(double d) {
            block6 : {
                block5 : {
                    block4 : {
                        if (this.count != 0) break block4;
                        this.first = d;
                        ++this.count;
                        break block5;
                    }
                    if (this.count <= 0) break block6;
                    if (this.buffer == null) {
                        this.buffer = new SpinedBuffer.OfDouble();
                        this.buffer.accept(this.first);
                        ++this.count;
                    }
                    this.buffer.accept(d);
                }
                return;
            }
            throw new IllegalStateException();
        }

        @Override
        public DoubleStream build() {
            int n = this.count;
            if (n >= 0) {
                this.count = -this.count - 1;
                DoubleStream doubleStream = n < 2 ? StreamSupport.doubleStream(this, false) : StreamSupport.doubleStream(this.buffer.spliterator(), false);
                return doubleStream;
            }
            throw new IllegalStateException();
        }

        @Override
        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            Objects.requireNonNull(doubleConsumer);
            if (this.count == -2) {
                doubleConsumer.accept(this.first);
                this.count = -1;
            }
        }

        @Override
        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            Objects.requireNonNull(doubleConsumer);
            if (this.count == -2) {
                doubleConsumer.accept(this.first);
                this.count = -1;
                return true;
            }
            return false;
        }
    }

    static final class IntStreamBuilderImpl
    extends AbstractStreamBuilderImpl<Integer, Spliterator.OfInt>
    implements IntStream.Builder,
    Spliterator.OfInt {
        SpinedBuffer.OfInt buffer;
        int first;

        IntStreamBuilderImpl() {
        }

        IntStreamBuilderImpl(int n) {
            this.first = n;
            this.count = -2;
        }

        @Override
        public void accept(int n) {
            block6 : {
                block5 : {
                    block4 : {
                        if (this.count != 0) break block4;
                        this.first = n;
                        ++this.count;
                        break block5;
                    }
                    if (this.count <= 0) break block6;
                    if (this.buffer == null) {
                        this.buffer = new SpinedBuffer.OfInt();
                        this.buffer.accept(this.first);
                        ++this.count;
                    }
                    this.buffer.accept(n);
                }
                return;
            }
            throw new IllegalStateException();
        }

        @Override
        public IntStream build() {
            int n = this.count;
            if (n >= 0) {
                this.count = -this.count - 1;
                IntStream intStream = n < 2 ? StreamSupport.intStream(this, false) : StreamSupport.intStream(this.buffer.spliterator(), false);
                return intStream;
            }
            throw new IllegalStateException();
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            if (this.count == -2) {
                intConsumer.accept(this.first);
                this.count = -1;
            }
        }

        @Override
        public boolean tryAdvance(IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            if (this.count == -2) {
                intConsumer.accept(this.first);
                this.count = -1;
                return true;
            }
            return false;
        }
    }

    static final class LongStreamBuilderImpl
    extends AbstractStreamBuilderImpl<Long, Spliterator.OfLong>
    implements LongStream.Builder,
    Spliterator.OfLong {
        SpinedBuffer.OfLong buffer;
        long first;

        LongStreamBuilderImpl() {
        }

        LongStreamBuilderImpl(long l) {
            this.first = l;
            this.count = -2;
        }

        @Override
        public void accept(long l) {
            block6 : {
                block5 : {
                    block4 : {
                        if (this.count != 0) break block4;
                        this.first = l;
                        ++this.count;
                        break block5;
                    }
                    if (this.count <= 0) break block6;
                    if (this.buffer == null) {
                        this.buffer = new SpinedBuffer.OfLong();
                        this.buffer.accept(this.first);
                        ++this.count;
                    }
                    this.buffer.accept(l);
                }
                return;
            }
            throw new IllegalStateException();
        }

        @Override
        public LongStream build() {
            int n = this.count;
            if (n >= 0) {
                this.count = -this.count - 1;
                LongStream longStream = n < 2 ? StreamSupport.longStream(this, false) : StreamSupport.longStream(this.buffer.spliterator(), false);
                return longStream;
            }
            throw new IllegalStateException();
        }

        @Override
        public void forEachRemaining(LongConsumer longConsumer) {
            Objects.requireNonNull(longConsumer);
            if (this.count == -2) {
                longConsumer.accept(this.first);
                this.count = -1;
            }
        }

        @Override
        public boolean tryAdvance(LongConsumer longConsumer) {
            Objects.requireNonNull(longConsumer);
            if (this.count == -2) {
                longConsumer.accept(this.first);
                this.count = -1;
                return true;
            }
            return false;
        }
    }

    static final class RangeIntSpliterator
    implements Spliterator.OfInt {
        private static final int BALANCED_SPLIT_THRESHOLD = 16777216;
        private static final int RIGHT_BALANCED_SPLIT_RATIO = 8;
        private int from;
        private int last;
        private final int upTo;

        private RangeIntSpliterator(int n, int n2, int n3) {
            this.from = n;
            this.upTo = n2;
            this.last = n3;
        }

        RangeIntSpliterator(int n, int n2, boolean bl) {
            this(n, n2, (int)bl);
        }

        private int splitPoint(long l) {
            int n = l < 0x1000000L ? 2 : 8;
            return (int)(l / (long)n);
        }

        @Override
        public int characteristics() {
            return 17749;
        }

        @Override
        public long estimateSize() {
            return (long)this.upTo - (long)this.from + (long)this.last;
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            int n;
            Objects.requireNonNull(intConsumer);
            int n2 = this.upTo;
            int n3 = this.last;
            this.from = this.upTo;
            this.last = 0;
            for (n = this.from; n < n2; ++n) {
                intConsumer.accept(n);
            }
            if (n3 > 0) {
                intConsumer.accept(n);
            }
        }

        @Override
        public Comparator<? super Integer> getComparator() {
            return null;
        }

        @Override
        public boolean tryAdvance(IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            int n = this.from++;
            if (n < this.upTo) {
                intConsumer.accept(n);
                return true;
            }
            if (this.last > 0) {
                this.last = 0;
                intConsumer.accept(n);
                return true;
            }
            return false;
        }

        @Override
        public Spliterator.OfInt trySplit() {
            RangeIntSpliterator rangeIntSpliterator;
            long l = this.estimateSize();
            if (l <= 1L) {
                rangeIntSpliterator = null;
            } else {
                int n;
                int n2 = this.from;
                this.from = n = this.splitPoint(l) + n2;
                rangeIntSpliterator = new RangeIntSpliterator(n2, n, 0);
            }
            return rangeIntSpliterator;
        }
    }

    static final class RangeLongSpliterator
    implements Spliterator.OfLong {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long BALANCED_SPLIT_THRESHOLD = 0x1000000L;
        private static final long RIGHT_BALANCED_SPLIT_RATIO = 8L;
        private long from;
        private int last;
        private final long upTo;

        private RangeLongSpliterator(long l, long l2, int n) {
            this.from = l;
            this.upTo = l2;
            this.last = n;
        }

        RangeLongSpliterator(long l, long l2, boolean bl) {
            this(l, l2, (int)bl);
        }

        private long splitPoint(long l) {
            long l2 = l < 0x1000000L ? 2L : 8L;
            return l / l2;
        }

        @Override
        public int characteristics() {
            return 17749;
        }

        @Override
        public long estimateSize() {
            return this.upTo - this.from + (long)this.last;
        }

        @Override
        public void forEachRemaining(LongConsumer longConsumer) {
            long l;
            Objects.requireNonNull(longConsumer);
            long l2 = this.upTo;
            int n = this.last;
            this.from = this.upTo;
            this.last = 0;
            for (l = this.from; l < l2; l = 1L + l) {
                longConsumer.accept(l);
            }
            if (n > 0) {
                longConsumer.accept(l);
            }
        }

        @Override
        public Comparator<? super Long> getComparator() {
            return null;
        }

        @Override
        public boolean tryAdvance(LongConsumer longConsumer) {
            Objects.requireNonNull(longConsumer);
            long l = this.from++;
            if (l < this.upTo) {
                longConsumer.accept(l);
                return true;
            }
            if (this.last > 0) {
                this.last = 0;
                longConsumer.accept(l);
                return true;
            }
            return false;
        }

        @Override
        public Spliterator.OfLong trySplit() {
            RangeLongSpliterator rangeLongSpliterator;
            long l = this.estimateSize();
            if (l <= 1L) {
                rangeLongSpliterator = null;
            } else {
                long l2 = this.from;
                this.from = l = this.splitPoint(l) + l2;
                rangeLongSpliterator = new RangeLongSpliterator(l2, l, 0);
            }
            return rangeLongSpliterator;
        }
    }

    static final class StreamBuilderImpl<T>
    extends AbstractStreamBuilderImpl<T, Spliterator<T>>
    implements Stream.Builder<T> {
        SpinedBuffer<T> buffer;
        T first;

        StreamBuilderImpl() {
        }

        StreamBuilderImpl(T t) {
            this.first = t;
            this.count = -2;
        }

        @Override
        public void accept(T t) {
            block6 : {
                block5 : {
                    block4 : {
                        if (this.count != 0) break block4;
                        this.first = t;
                        ++this.count;
                        break block5;
                    }
                    if (this.count <= 0) break block6;
                    if (this.buffer == null) {
                        this.buffer = new SpinedBuffer();
                        this.buffer.accept(this.first);
                        ++this.count;
                    }
                    this.buffer.accept(t);
                }
                return;
            }
            throw new IllegalStateException();
        }

        @Override
        public Stream.Builder<T> add(T t) {
            this.accept(t);
            return this;
        }

        @Override
        public Stream<T> build() {
            int n = this.count;
            if (n >= 0) {
                this.count = -this.count - 1;
                Stream stream = n < 2 ? StreamSupport.stream(this, false) : StreamSupport.stream(this.buffer.spliterator(), false);
                return stream;
            }
            throw new IllegalStateException();
        }

        @Override
        public void forEachRemaining(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            if (this.count == -2) {
                consumer.accept(this.first);
                this.count = -1;
            }
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            if (this.count == -2) {
                consumer.accept(this.first);
                this.count = -1;
                return true;
            }
            return false;
        }
    }

}

