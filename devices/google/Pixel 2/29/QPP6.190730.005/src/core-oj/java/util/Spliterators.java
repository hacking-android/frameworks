/*
 * Decompiled with CFR 0.145.
 */
package java.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public final class Spliterators {
    private static final Spliterator.OfDouble EMPTY_DOUBLE_SPLITERATOR;
    private static final Spliterator.OfInt EMPTY_INT_SPLITERATOR;
    private static final Spliterator.OfLong EMPTY_LONG_SPLITERATOR;
    private static final Spliterator<Object> EMPTY_SPLITERATOR;

    static {
        EMPTY_SPLITERATOR = new EmptySpliterator.OfRef<Object>();
        EMPTY_INT_SPLITERATOR = new EmptySpliterator.OfInt();
        EMPTY_LONG_SPLITERATOR = new EmptySpliterator.OfLong();
        EMPTY_DOUBLE_SPLITERATOR = new EmptySpliterator.OfDouble();
    }

    private Spliterators() {
    }

    private static void checkFromToBounds(int n, int n2, int n3) {
        if (n2 <= n3) {
            if (n2 >= 0) {
                if (n3 <= n) {
                    return;
                }
                throw new ArrayIndexOutOfBoundsException(n3);
            }
            throw new ArrayIndexOutOfBoundsException(n2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("origin(");
        stringBuilder.append(n2);
        stringBuilder.append(") > fence(");
        stringBuilder.append(n3);
        stringBuilder.append(")");
        throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
    }

    public static Spliterator.OfDouble emptyDoubleSpliterator() {
        return EMPTY_DOUBLE_SPLITERATOR;
    }

    public static Spliterator.OfInt emptyIntSpliterator() {
        return EMPTY_INT_SPLITERATOR;
    }

    public static Spliterator.OfLong emptyLongSpliterator() {
        return EMPTY_LONG_SPLITERATOR;
    }

    public static <T> Spliterator<T> emptySpliterator() {
        return EMPTY_SPLITERATOR;
    }

    public static <T> Iterator<T> iterator(Spliterator<? extends T> spliterator) {
        Objects.requireNonNull(spliterator);
        return spliterator.new 1Adapter();
    }

    public static PrimitiveIterator.OfDouble iterator(Spliterator.OfDouble ofDouble) {
        Objects.requireNonNull(ofDouble);
        return ofDouble.new 4Adapter();
    }

    public static PrimitiveIterator.OfInt iterator(Spliterator.OfInt ofInt) {
        Objects.requireNonNull(ofInt);
        return ofInt.new 2Adapter();
    }

    public static PrimitiveIterator.OfLong iterator(Spliterator.OfLong ofLong) {
        Objects.requireNonNull(ofLong);
        return ofLong.new 3Adapter();
    }

    public static Spliterator.OfDouble spliterator(PrimitiveIterator.OfDouble ofDouble, long l, int n) {
        return new DoubleIteratorSpliterator(Objects.requireNonNull(ofDouble), l, n);
    }

    public static Spliterator.OfDouble spliterator(double[] arrd, int n) {
        return new DoubleArraySpliterator(Objects.requireNonNull(arrd), n);
    }

    public static Spliterator.OfDouble spliterator(double[] arrd, int n, int n2, int n3) {
        Spliterators.checkFromToBounds(Objects.requireNonNull(arrd).length, n, n2);
        return new DoubleArraySpliterator(arrd, n, n2, n3);
    }

    public static Spliterator.OfInt spliterator(PrimitiveIterator.OfInt ofInt, long l, int n) {
        return new IntIteratorSpliterator(Objects.requireNonNull(ofInt), l, n);
    }

    public static Spliterator.OfInt spliterator(int[] arrn, int n) {
        return new IntArraySpliterator(Objects.requireNonNull(arrn), n);
    }

    public static Spliterator.OfInt spliterator(int[] arrn, int n, int n2, int n3) {
        Spliterators.checkFromToBounds(Objects.requireNonNull(arrn).length, n, n2);
        return new IntArraySpliterator(arrn, n, n2, n3);
    }

    public static Spliterator.OfLong spliterator(PrimitiveIterator.OfLong ofLong, long l, int n) {
        return new LongIteratorSpliterator(Objects.requireNonNull(ofLong), l, n);
    }

    public static Spliterator.OfLong spliterator(long[] arrl, int n) {
        return new LongArraySpliterator(Objects.requireNonNull(arrl), n);
    }

    public static Spliterator.OfLong spliterator(long[] arrl, int n, int n2, int n3) {
        Spliterators.checkFromToBounds(Objects.requireNonNull(arrl).length, n, n2);
        return new LongArraySpliterator(arrl, n, n2, n3);
    }

    public static <T> Spliterator<T> spliterator(Collection<? extends T> collection, int n) {
        return new IteratorSpliterator<T>(Objects.requireNonNull(collection), n);
    }

    public static <T> Spliterator<T> spliterator(Iterator<? extends T> iterator, long l, int n) {
        return new IteratorSpliterator<T>(Objects.requireNonNull(iterator), l, n);
    }

    public static <T> Spliterator<T> spliterator(Object[] arrobject, int n) {
        return new ArraySpliterator(Objects.requireNonNull(arrobject), n);
    }

    public static <T> Spliterator<T> spliterator(Object[] arrobject, int n, int n2, int n3) {
        Spliterators.checkFromToBounds(Objects.requireNonNull(arrobject).length, n, n2);
        return new ArraySpliterator(arrobject, n, n2, n3);
    }

    public static Spliterator.OfDouble spliteratorUnknownSize(PrimitiveIterator.OfDouble ofDouble, int n) {
        return new DoubleIteratorSpliterator(Objects.requireNonNull(ofDouble), n);
    }

    public static Spliterator.OfInt spliteratorUnknownSize(PrimitiveIterator.OfInt ofInt, int n) {
        return new IntIteratorSpliterator(Objects.requireNonNull(ofInt), n);
    }

    public static Spliterator.OfLong spliteratorUnknownSize(PrimitiveIterator.OfLong ofLong, int n) {
        return new LongIteratorSpliterator(Objects.requireNonNull(ofLong), n);
    }

    public static <T> Spliterator<T> spliteratorUnknownSize(Iterator<? extends T> iterator, int n) {
        return new IteratorSpliterator<T>(Objects.requireNonNull(iterator), n);
    }

    class 1Adapter
    implements Iterator<T>,
    Consumer<T> {
        T nextElement;
        boolean valueReady = false;

        1Adapter() {
        }

        @Override
        public void accept(T t) {
            this.valueReady = true;
            this.nextElement = t;
        }

        @Override
        public boolean hasNext() {
            if (!this.valueReady) {
                Spliterator.this.tryAdvance(this);
            }
            return this.valueReady;
        }

        @Override
        public T next() {
            if (!this.valueReady && !this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            return this.nextElement;
        }
    }

    class 2Adapter
    implements PrimitiveIterator.OfInt,
    IntConsumer {
        int nextElement;
        boolean valueReady = false;

        2Adapter() {
        }

        @Override
        public void accept(int n) {
            this.valueReady = true;
            this.nextElement = n;
        }

        @Override
        public boolean hasNext() {
            if (!this.valueReady) {
                OfInt.this.tryAdvance(this);
            }
            return this.valueReady;
        }

        @Override
        public int nextInt() {
            if (!this.valueReady && !this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            return this.nextElement;
        }
    }

    class 3Adapter
    implements PrimitiveIterator.OfLong,
    LongConsumer {
        long nextElement;
        boolean valueReady = false;

        3Adapter() {
        }

        @Override
        public void accept(long l) {
            this.valueReady = true;
            this.nextElement = l;
        }

        @Override
        public boolean hasNext() {
            if (!this.valueReady) {
                OfLong.this.tryAdvance(this);
            }
            return this.valueReady;
        }

        @Override
        public long nextLong() {
            if (!this.valueReady && !this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            return this.nextElement;
        }
    }

    class 4Adapter
    implements PrimitiveIterator.OfDouble,
    DoubleConsumer {
        double nextElement;
        boolean valueReady = false;

        4Adapter() {
        }

        @Override
        public void accept(double d) {
            this.valueReady = true;
            this.nextElement = d;
        }

        @Override
        public boolean hasNext() {
            if (!this.valueReady) {
                OfDouble.this.tryAdvance(this);
            }
            return this.valueReady;
        }

        @Override
        public double nextDouble() {
            if (!this.valueReady && !this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            return this.nextElement;
        }
    }

    public static abstract class AbstractDoubleSpliterator
    implements Spliterator.OfDouble {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        private int batch;
        private final int characteristics;
        private long est;

        protected AbstractDoubleSpliterator(long l, int n) {
            this.est = l;
            if ((n & 64) != 0) {
                n |= 16384;
            }
            this.characteristics = n;
        }

        @Override
        public int characteristics() {
            return this.characteristics;
        }

        @Override
        public long estimateSize() {
            return this.est;
        }

        @Override
        public Spliterator.OfDouble trySplit() {
            HoldingDoubleConsumer holdingDoubleConsumer = new HoldingDoubleConsumer();
            long l = this.est;
            if (l > 1L && this.tryAdvance(holdingDoubleConsumer)) {
                int n;
                int n2;
                int n3 = n = this.batch + 1024;
                if ((long)n > l) {
                    n3 = (int)l;
                }
                n = n3;
                if (n3 > 33554432) {
                    n = 33554432;
                }
                double[] arrd = new double[n];
                n3 = 0;
                do {
                    arrd[n3] = holdingDoubleConsumer.value;
                    n2 = n3 + 1;
                    if (n2 >= n) break;
                    n3 = n2;
                } while (this.tryAdvance(holdingDoubleConsumer));
                this.batch = n2;
                l = this.est;
                if (l != Long.MAX_VALUE) {
                    this.est = l - (long)n2;
                }
                return new DoubleArraySpliterator(arrd, 0, n2, this.characteristics());
            }
            return null;
        }

        static final class HoldingDoubleConsumer
        implements DoubleConsumer {
            double value;

            HoldingDoubleConsumer() {
            }

            @Override
            public void accept(double d) {
                this.value = d;
            }
        }

    }

    public static abstract class AbstractIntSpliterator
    implements Spliterator.OfInt {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        private int batch;
        private final int characteristics;
        private long est;

        protected AbstractIntSpliterator(long l, int n) {
            this.est = l;
            if ((n & 64) != 0) {
                n |= 16384;
            }
            this.characteristics = n;
        }

        @Override
        public int characteristics() {
            return this.characteristics;
        }

        @Override
        public long estimateSize() {
            return this.est;
        }

        @Override
        public Spliterator.OfInt trySplit() {
            HoldingIntConsumer holdingIntConsumer = new HoldingIntConsumer();
            long l = this.est;
            if (l > 1L && this.tryAdvance(holdingIntConsumer)) {
                int n;
                int n2;
                int n3 = n = this.batch + 1024;
                if ((long)n > l) {
                    n3 = (int)l;
                }
                n = n3;
                if (n3 > 33554432) {
                    n = 33554432;
                }
                int[] arrn = new int[n];
                n3 = 0;
                do {
                    arrn[n3] = holdingIntConsumer.value;
                    n2 = n3 + 1;
                    if (n2 >= n) break;
                    n3 = n2;
                } while (this.tryAdvance(holdingIntConsumer));
                this.batch = n2;
                l = this.est;
                if (l != Long.MAX_VALUE) {
                    this.est = l - (long)n2;
                }
                return new IntArraySpliterator(arrn, 0, n2, this.characteristics());
            }
            return null;
        }

        static final class HoldingIntConsumer
        implements IntConsumer {
            int value;

            HoldingIntConsumer() {
            }

            @Override
            public void accept(int n) {
                this.value = n;
            }
        }

    }

    public static abstract class AbstractLongSpliterator
    implements Spliterator.OfLong {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        private int batch;
        private final int characteristics;
        private long est;

        protected AbstractLongSpliterator(long l, int n) {
            this.est = l;
            if ((n & 64) != 0) {
                n |= 16384;
            }
            this.characteristics = n;
        }

        @Override
        public int characteristics() {
            return this.characteristics;
        }

        @Override
        public long estimateSize() {
            return this.est;
        }

        @Override
        public Spliterator.OfLong trySplit() {
            HoldingLongConsumer holdingLongConsumer = new HoldingLongConsumer();
            long l = this.est;
            if (l > 1L && this.tryAdvance(holdingLongConsumer)) {
                int n;
                int n2;
                int n3 = n = this.batch + 1024;
                if ((long)n > l) {
                    n3 = (int)l;
                }
                n = n3;
                if (n3 > 33554432) {
                    n = 33554432;
                }
                long[] arrl = new long[n];
                n3 = 0;
                do {
                    arrl[n3] = holdingLongConsumer.value;
                    n2 = n3 + 1;
                    if (n2 >= n) break;
                    n3 = n2;
                } while (this.tryAdvance(holdingLongConsumer));
                this.batch = n2;
                l = this.est;
                if (l != Long.MAX_VALUE) {
                    this.est = l - (long)n2;
                }
                return new LongArraySpliterator(arrl, 0, n2, this.characteristics());
            }
            return null;
        }

        static final class HoldingLongConsumer
        implements LongConsumer {
            long value;

            HoldingLongConsumer() {
            }

            @Override
            public void accept(long l) {
                this.value = l;
            }
        }

    }

    public static abstract class AbstractSpliterator<T>
    implements Spliterator<T> {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        private int batch;
        private final int characteristics;
        private long est;

        protected AbstractSpliterator(long l, int n) {
            this.est = l;
            if ((n & 64) != 0) {
                n |= 16384;
            }
            this.characteristics = n;
        }

        @Override
        public int characteristics() {
            return this.characteristics;
        }

        @Override
        public long estimateSize() {
            return this.est;
        }

        @Override
        public Spliterator<T> trySplit() {
            HoldingConsumer holdingConsumer = new HoldingConsumer();
            long l = this.est;
            if (l > 1L && this.tryAdvance(holdingConsumer)) {
                int n;
                int n2;
                int n3 = n = this.batch + 1024;
                if ((long)n > l) {
                    n3 = (int)l;
                }
                n = n3;
                if (n3 > 33554432) {
                    n = 33554432;
                }
                Object[] arrobject = new Object[n];
                n3 = 0;
                do {
                    arrobject[n3] = holdingConsumer.value;
                    n2 = n3 + 1;
                    if (n2 >= n) break;
                    n3 = n2;
                } while (this.tryAdvance(holdingConsumer));
                this.batch = n2;
                l = this.est;
                if (l != Long.MAX_VALUE) {
                    this.est = l - (long)n2;
                }
                return new ArraySpliterator(arrobject, 0, n2, this.characteristics());
            }
            return null;
        }

        static final class HoldingConsumer<T>
        implements Consumer<T> {
            Object value;

            HoldingConsumer() {
            }

            @Override
            public void accept(T t) {
                this.value = t;
            }
        }

    }

    static final class ArraySpliterator<T>
    implements Spliterator<T> {
        private final Object[] array;
        private final int characteristics;
        private final int fence;
        private int index;

        public ArraySpliterator(Object[] arrobject, int n) {
            this(arrobject, 0, arrobject.length, n);
        }

        public ArraySpliterator(Object[] arrobject, int n, int n2, int n3) {
            this.array = arrobject;
            this.index = n;
            this.fence = n2;
            this.characteristics = n3 | 64 | 16384;
        }

        @Override
        public int characteristics() {
            return this.characteristics;
        }

        @Override
        public long estimateSize() {
            return this.fence - this.index;
        }

        @Override
        public void forEachRemaining(Consumer<? super T> consumer) {
            if (consumer != null) {
                Object[] arrobject = this.array;
                int n = arrobject.length;
                int n2 = this.fence;
                if (n >= n2) {
                    int n3;
                    n = n3 = this.index;
                    if (n3 >= 0) {
                        this.index = n2;
                        if (n < n2) {
                            do {
                                consumer.accept(arrobject[n]);
                                n = n3 = n + 1;
                            } while (n3 < n2);
                        }
                    }
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public Comparator<? super T> getComparator() {
            if (this.hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> consumer) {
            if (consumer != null) {
                int n = this.index;
                if (n >= 0 && n < this.fence) {
                    Object[] arrobject = this.array;
                    this.index = n + 1;
                    consumer.accept(arrobject[n]);
                    return true;
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public Spliterator<T> trySplit() {
            Object object;
            int n = this.index;
            int n2 = this.fence + n >>> 1;
            if (n >= n2) {
                object = null;
            } else {
                object = this.array;
                this.index = n2;
                object = new ArraySpliterator<T>((Object[])object, n, n2, this.characteristics);
            }
            return object;
        }
    }

    static final class DoubleArraySpliterator
    implements Spliterator.OfDouble {
        private final double[] array;
        private final int characteristics;
        private final int fence;
        private int index;

        public DoubleArraySpliterator(double[] arrd, int n) {
            this(arrd, 0, arrd.length, n);
        }

        public DoubleArraySpliterator(double[] arrd, int n, int n2, int n3) {
            this.array = arrd;
            this.index = n;
            this.fence = n2;
            this.characteristics = n3 | 64 | 16384;
        }

        @Override
        public int characteristics() {
            return this.characteristics;
        }

        @Override
        public long estimateSize() {
            return this.fence - this.index;
        }

        @Override
        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            if (doubleConsumer != null) {
                double[] arrd = this.array;
                int n = arrd.length;
                int n2 = this.fence;
                if (n >= n2) {
                    int n3;
                    n = n3 = this.index;
                    if (n3 >= 0) {
                        this.index = n2;
                        if (n < n2) {
                            do {
                                doubleConsumer.accept(arrd[n]);
                                n = n3 = n + 1;
                            } while (n3 < n2);
                        }
                    }
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public Comparator<? super Double> getComparator() {
            if (this.hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }

        @Override
        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            if (doubleConsumer != null) {
                int n = this.index;
                if (n >= 0 && n < this.fence) {
                    double[] arrd = this.array;
                    this.index = n + 1;
                    doubleConsumer.accept(arrd[n]);
                    return true;
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public Spliterator.OfDouble trySplit() {
            Object object;
            int n = this.index;
            int n2 = this.fence + n >>> 1;
            if (n >= n2) {
                object = null;
            } else {
                object = this.array;
                this.index = n2;
                object = new DoubleArraySpliterator((double[])object, n, n2, this.characteristics);
            }
            return object;
        }
    }

    static final class DoubleIteratorSpliterator
    implements Spliterator.OfDouble {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        private int batch;
        private final int characteristics;
        private long est;
        private PrimitiveIterator.OfDouble it;

        public DoubleIteratorSpliterator(PrimitiveIterator.OfDouble ofDouble, int n) {
            this.it = ofDouble;
            this.est = Long.MAX_VALUE;
            this.characteristics = n & -16449;
        }

        public DoubleIteratorSpliterator(PrimitiveIterator.OfDouble ofDouble, long l, int n) {
            this.it = ofDouble;
            this.est = l;
            if ((n & 4096) == 0) {
                n = n | 64 | 16384;
            }
            this.characteristics = n;
        }

        @Override
        public int characteristics() {
            return this.characteristics;
        }

        @Override
        public long estimateSize() {
            return this.est;
        }

        @Override
        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            if (doubleConsumer != null) {
                this.it.forEachRemaining(doubleConsumer);
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public Comparator<? super Double> getComparator() {
            if (this.hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }

        @Override
        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            if (doubleConsumer != null) {
                if (this.it.hasNext()) {
                    doubleConsumer.accept(this.it.nextDouble());
                    return true;
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public Spliterator.OfDouble trySplit() {
            PrimitiveIterator.OfDouble ofDouble = this.it;
            long l = this.est;
            if (l > 1L && ofDouble.hasNext()) {
                int n;
                int n2;
                int n3 = n = this.batch + 1024;
                if ((long)n > l) {
                    n3 = (int)l;
                }
                n = n3;
                if (n3 > 33554432) {
                    n = 33554432;
                }
                double[] arrd = new double[n];
                n3 = 0;
                do {
                    arrd[n3] = ofDouble.nextDouble();
                    n2 = n3 + 1;
                    if (n2 >= n) break;
                    n3 = n2;
                } while (ofDouble.hasNext());
                this.batch = n2;
                l = this.est;
                if (l != Long.MAX_VALUE) {
                    this.est = l - (long)n2;
                }
                return new DoubleArraySpliterator(arrd, 0, n2, this.characteristics);
            }
            return null;
        }
    }

    private static abstract class EmptySpliterator<T, S extends Spliterator<T>, C> {
        EmptySpliterator() {
        }

        public int characteristics() {
            return 16448;
        }

        public long estimateSize() {
            return 0L;
        }

        public void forEachRemaining(C c) {
            Objects.requireNonNull(c);
        }

        public boolean tryAdvance(C c) {
            Objects.requireNonNull(c);
            return false;
        }

        public S trySplit() {
            return null;
        }

        private static final class OfDouble
        extends EmptySpliterator<Double, Spliterator.OfDouble, DoubleConsumer>
        implements Spliterator.OfDouble {
            OfDouble() {
            }
        }

        private static final class OfInt
        extends EmptySpliterator<Integer, Spliterator.OfInt, IntConsumer>
        implements Spliterator.OfInt {
            OfInt() {
            }
        }

        private static final class OfLong
        extends EmptySpliterator<Long, Spliterator.OfLong, LongConsumer>
        implements Spliterator.OfLong {
            OfLong() {
            }
        }

        private static final class OfRef<T>
        extends EmptySpliterator<T, Spliterator<T>, Consumer<? super T>>
        implements Spliterator<T> {
            OfRef() {
            }
        }

    }

    static final class IntArraySpliterator
    implements Spliterator.OfInt {
        private final int[] array;
        private final int characteristics;
        private final int fence;
        private int index;

        public IntArraySpliterator(int[] arrn, int n) {
            this(arrn, 0, arrn.length, n);
        }

        public IntArraySpliterator(int[] arrn, int n, int n2, int n3) {
            this.array = arrn;
            this.index = n;
            this.fence = n2;
            this.characteristics = n3 | 64 | 16384;
        }

        @Override
        public int characteristics() {
            return this.characteristics;
        }

        @Override
        public long estimateSize() {
            return this.fence - this.index;
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            if (intConsumer != null) {
                int[] arrn = this.array;
                int n = arrn.length;
                int n2 = this.fence;
                if (n >= n2) {
                    int n3;
                    n = n3 = this.index;
                    if (n3 >= 0) {
                        this.index = n2;
                        if (n < n2) {
                            do {
                                intConsumer.accept(arrn[n]);
                                n = n3 = n + 1;
                            } while (n3 < n2);
                        }
                    }
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public Comparator<? super Integer> getComparator() {
            if (this.hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }

        @Override
        public boolean tryAdvance(IntConsumer intConsumer) {
            if (intConsumer != null) {
                int n = this.index;
                if (n >= 0 && n < this.fence) {
                    int[] arrn = this.array;
                    this.index = n + 1;
                    intConsumer.accept(arrn[n]);
                    return true;
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public Spliterator.OfInt trySplit() {
            Object object;
            int n = this.index;
            int n2 = this.fence + n >>> 1;
            if (n >= n2) {
                object = null;
            } else {
                object = this.array;
                this.index = n2;
                object = new IntArraySpliterator((int[])object, n, n2, this.characteristics);
            }
            return object;
        }
    }

    static final class IntIteratorSpliterator
    implements Spliterator.OfInt {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        private int batch;
        private final int characteristics;
        private long est;
        private PrimitiveIterator.OfInt it;

        public IntIteratorSpliterator(PrimitiveIterator.OfInt ofInt, int n) {
            this.it = ofInt;
            this.est = Long.MAX_VALUE;
            this.characteristics = n & -16449;
        }

        public IntIteratorSpliterator(PrimitiveIterator.OfInt ofInt, long l, int n) {
            this.it = ofInt;
            this.est = l;
            if ((n & 4096) == 0) {
                n = n | 64 | 16384;
            }
            this.characteristics = n;
        }

        @Override
        public int characteristics() {
            return this.characteristics;
        }

        @Override
        public long estimateSize() {
            return this.est;
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            if (intConsumer != null) {
                this.it.forEachRemaining(intConsumer);
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public Comparator<? super Integer> getComparator() {
            if (this.hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }

        @Override
        public boolean tryAdvance(IntConsumer intConsumer) {
            if (intConsumer != null) {
                if (this.it.hasNext()) {
                    intConsumer.accept(this.it.nextInt());
                    return true;
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public Spliterator.OfInt trySplit() {
            PrimitiveIterator.OfInt ofInt = this.it;
            long l = this.est;
            if (l > 1L && ofInt.hasNext()) {
                int n;
                int n2;
                int n3 = n = this.batch + 1024;
                if ((long)n > l) {
                    n3 = (int)l;
                }
                n = n3;
                if (n3 > 33554432) {
                    n = 33554432;
                }
                int[] arrn = new int[n];
                n3 = 0;
                do {
                    arrn[n3] = ofInt.nextInt();
                    n2 = n3 + 1;
                    if (n2 >= n) break;
                    n3 = n2;
                } while (ofInt.hasNext());
                this.batch = n2;
                l = this.est;
                if (l != Long.MAX_VALUE) {
                    this.est = l - (long)n2;
                }
                return new IntArraySpliterator(arrn, 0, n2, this.characteristics);
            }
            return null;
        }
    }

    static class IteratorSpliterator<T>
    implements Spliterator<T> {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        private int batch;
        private final int characteristics;
        private final Collection<? extends T> collection;
        private long est;
        private Iterator<? extends T> it;

        public IteratorSpliterator(Collection<? extends T> collection, int n) {
            this.collection = collection;
            this.it = null;
            if ((n & 4096) == 0) {
                n = n | 64 | 16384;
            }
            this.characteristics = n;
        }

        public IteratorSpliterator(Iterator<? extends T> iterator, int n) {
            this.collection = null;
            this.it = iterator;
            this.est = Long.MAX_VALUE;
            this.characteristics = n & -16449;
        }

        public IteratorSpliterator(Iterator<? extends T> iterator, long l, int n) {
            this.collection = null;
            this.it = iterator;
            this.est = l;
            if ((n & 4096) == 0) {
                n = n | 64 | 16384;
            }
            this.characteristics = n;
        }

        @Override
        public int characteristics() {
            return this.characteristics;
        }

        @Override
        public long estimateSize() {
            if (this.it == null) {
                long l;
                this.it = this.collection.iterator();
                this.est = l = (long)this.collection.size();
                return l;
            }
            return this.est;
        }

        @Override
        public void forEachRemaining(Consumer<? super T> consumer) {
            if (consumer != null) {
                Iterator<T> iterator;
                Iterator<T> iterator2 = iterator = this.it;
                if (iterator == null) {
                    iterator2 = this.collection.iterator();
                    this.it = iterator2;
                    this.est = this.collection.size();
                }
                iterator2.forEachRemaining(consumer);
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public Comparator<? super T> getComparator() {
            if (this.hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> consumer) {
            if (consumer != null) {
                if (this.it == null) {
                    this.it = this.collection.iterator();
                    this.est = this.collection.size();
                }
                if (this.it.hasNext()) {
                    consumer.accept(this.it.next());
                    return true;
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public Spliterator<T> trySplit() {
            long l;
            Object[] arrobject;
            Object[] arrobject2 = arrobject = this.it;
            if (arrobject == null) {
                arrobject2 = this.collection.iterator();
                this.it = arrobject2;
                this.est = l = (long)this.collection.size();
            } else {
                l = this.est;
            }
            if (l > 1L && arrobject2.hasNext()) {
                int n;
                int n2;
                int n3 = n = this.batch + 1024;
                if ((long)n > l) {
                    n3 = (int)l;
                }
                n = n3;
                if (n3 > 33554432) {
                    n = 33554432;
                }
                arrobject = new Object[n];
                n3 = 0;
                do {
                    arrobject[n3] = arrobject2.next();
                    n2 = n3 + 1;
                    if (n2 >= n) break;
                    n3 = n2;
                } while (arrobject2.hasNext());
                this.batch = n2;
                l = this.est;
                if (l != Long.MAX_VALUE) {
                    this.est = l - (long)n2;
                }
                return new ArraySpliterator(arrobject, 0, n2, this.characteristics);
            }
            return null;
        }
    }

    static final class LongArraySpliterator
    implements Spliterator.OfLong {
        private final long[] array;
        private final int characteristics;
        private final int fence;
        private int index;

        public LongArraySpliterator(long[] arrl, int n) {
            this(arrl, 0, arrl.length, n);
        }

        public LongArraySpliterator(long[] arrl, int n, int n2, int n3) {
            this.array = arrl;
            this.index = n;
            this.fence = n2;
            this.characteristics = n3 | 64 | 16384;
        }

        @Override
        public int characteristics() {
            return this.characteristics;
        }

        @Override
        public long estimateSize() {
            return this.fence - this.index;
        }

        @Override
        public void forEachRemaining(LongConsumer longConsumer) {
            if (longConsumer != null) {
                long[] arrl = this.array;
                int n = arrl.length;
                int n2 = this.fence;
                if (n >= n2) {
                    int n3;
                    n = n3 = this.index;
                    if (n3 >= 0) {
                        this.index = n2;
                        if (n < n2) {
                            do {
                                longConsumer.accept(arrl[n]);
                                n = n3 = n + 1;
                            } while (n3 < n2);
                        }
                    }
                }
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public Comparator<? super Long> getComparator() {
            if (this.hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }

        @Override
        public boolean tryAdvance(LongConsumer longConsumer) {
            if (longConsumer != null) {
                int n = this.index;
                if (n >= 0 && n < this.fence) {
                    long[] arrl = this.array;
                    this.index = n + 1;
                    longConsumer.accept(arrl[n]);
                    return true;
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public Spliterator.OfLong trySplit() {
            Object object;
            int n = this.index;
            int n2 = this.fence + n >>> 1;
            if (n >= n2) {
                object = null;
            } else {
                object = this.array;
                this.index = n2;
                object = new LongArraySpliterator((long[])object, n, n2, this.characteristics);
            }
            return object;
        }
    }

    static final class LongIteratorSpliterator
    implements Spliterator.OfLong {
        static final int BATCH_UNIT = 1024;
        static final int MAX_BATCH = 33554432;
        private int batch;
        private final int characteristics;
        private long est;
        private PrimitiveIterator.OfLong it;

        public LongIteratorSpliterator(PrimitiveIterator.OfLong ofLong, int n) {
            this.it = ofLong;
            this.est = Long.MAX_VALUE;
            this.characteristics = n & -16449;
        }

        public LongIteratorSpliterator(PrimitiveIterator.OfLong ofLong, long l, int n) {
            this.it = ofLong;
            this.est = l;
            if ((n & 4096) == 0) {
                n = n | 64 | 16384;
            }
            this.characteristics = n;
        }

        @Override
        public int characteristics() {
            return this.characteristics;
        }

        @Override
        public long estimateSize() {
            return this.est;
        }

        @Override
        public void forEachRemaining(LongConsumer longConsumer) {
            if (longConsumer != null) {
                this.it.forEachRemaining(longConsumer);
                return;
            }
            throw new NullPointerException();
        }

        @Override
        public Comparator<? super Long> getComparator() {
            if (this.hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }

        @Override
        public boolean tryAdvance(LongConsumer longConsumer) {
            if (longConsumer != null) {
                if (this.it.hasNext()) {
                    longConsumer.accept(this.it.nextLong());
                    return true;
                }
                return false;
            }
            throw new NullPointerException();
        }

        @Override
        public Spliterator.OfLong trySplit() {
            PrimitiveIterator.OfLong ofLong = this.it;
            long l = this.est;
            if (l > 1L && ofLong.hasNext()) {
                int n;
                int n2;
                int n3 = n = this.batch + 1024;
                if ((long)n > l) {
                    n3 = (int)l;
                }
                n = n3;
                if (n3 > 33554432) {
                    n = 33554432;
                }
                long[] arrl = new long[n];
                n3 = 0;
                do {
                    arrl[n3] = ofLong.nextLong();
                    n2 = n3 + 1;
                    if (n2 >= n) break;
                    n3 = n2;
                } while (ofLong.hasNext());
                this.batch = n2;
                l = this.est;
                if (l != Long.MAX_VALUE) {
                    this.est = l - (long)n2;
                }
                return new LongArraySpliterator(arrl, 0, n2, this.characteristics);
            }
            return null;
        }
    }

}

