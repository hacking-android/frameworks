/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.SpinedBuffer$OfPrimitive.BaseSpliterator
 */
package java.util.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import java.util.stream.AbstractSpinedBuffer;
import java.util.stream.SpinedBuffer;
import java.util.stream.Tripwire;
import java.util.stream._$$Lambda$KCZEjYEMhxUzZZN6W26atKG9NZc;

public class SpinedBuffer<E>
extends AbstractSpinedBuffer
implements Consumer<E>,
Iterable<E> {
    private static final int SPLITERATOR_CHARACTERISTICS = 16464;
    protected E[] curChunk;
    protected E[][] spine;

    public SpinedBuffer() {
        this.curChunk = new Object[1 << this.initialChunkPower];
    }

    public SpinedBuffer(int n) {
        super(n);
        this.curChunk = new Object[1 << this.initialChunkPower];
    }

    private void inflateSpine() {
        if (this.spine == null) {
            this.spine = new Object[8][];
            this.priorElementCount = new long[8];
            this.spine[0] = this.curChunk;
        }
    }

    @Override
    public void accept(E e) {
        E[] arrE;
        int n;
        if (this.elementIndex == this.curChunk.length) {
            this.inflateSpine();
            n = this.spineIndex;
            arrE = this.spine;
            if (n + 1 >= arrE.length || arrE[this.spineIndex + 1] == null) {
                this.increaseCapacity();
            }
            this.elementIndex = 0;
            ++this.spineIndex;
            this.curChunk = this.spine[this.spineIndex];
        }
        arrE = this.curChunk;
        n = this.elementIndex;
        this.elementIndex = n + 1;
        arrE[n] = e;
    }

    public E[] asArray(IntFunction<E[]> arrE) {
        long l = this.count();
        if (l < 0x7FFFFFF7L) {
            arrE = arrE.apply((int)l);
            this.copyInto(arrE, 0);
            return arrE;
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    protected long capacity() {
        long l = this.spineIndex == 0 ? (long)this.curChunk.length : this.priorElementCount[this.spineIndex] + (long)this.spine[this.spineIndex].length;
        return l;
    }

    @Override
    public void clear() {
        E[][] arrE = this.spine;
        if (arrE != null) {
            this.curChunk = arrE[0];
            for (int i = 0; i < (arrE = this.curChunk).length; ++i) {
                arrE[i] = null;
            }
            this.spine = null;
            this.priorElementCount = null;
        } else {
            for (int i = 0; i < this.elementIndex; ++i) {
                this.curChunk[i] = null;
            }
        }
        this.elementIndex = 0;
        this.spineIndex = 0;
    }

    public void copyInto(E[] arrE, int n) {
        long l = (long)n + this.count();
        if (l <= (long)arrE.length && l >= (long)n) {
            if (this.spineIndex == 0) {
                System.arraycopy(this.curChunk, 0, arrE, n, this.elementIndex);
            } else {
                for (int i = 0; i < this.spineIndex; ++i) {
                    E[][] arrE2 = this.spine;
                    System.arraycopy(arrE2[i], 0, arrE, n, arrE2[i].length);
                    n += this.spine[i].length;
                }
                if (this.elementIndex > 0) {
                    System.arraycopy(this.curChunk, 0, arrE, n, this.elementIndex);
                }
            }
            return;
        }
        throw new IndexOutOfBoundsException("does not fit");
    }

    protected final void ensureCapacity(long l) {
        long l2 = this.capacity();
        if (l > l2) {
            this.inflateSpine();
            int n = this.spineIndex + 1;
            while (l > l2) {
                int n2;
                E[][] arrE = this.spine;
                if (n >= arrE.length) {
                    n2 = arrE.length * 2;
                    this.spine = (Object[][])Arrays.copyOf(arrE, n2);
                    this.priorElementCount = Arrays.copyOf(this.priorElementCount, n2);
                }
                n2 = this.chunkSize(n);
                this.spine[n] = new Object[n2];
                this.priorElementCount[n] = this.priorElementCount[n - 1] + (long)this.spine[n - 1].length;
                l2 += (long)n2;
                ++n;
            }
        }
    }

    @Override
    public void forEach(Consumer<? super E> consumer) {
        int n;
        for (n = 0; n < this.spineIndex; ++n) {
            E[] arrE = this.spine[n];
            int n2 = arrE.length;
            for (int i = 0; i < n2; ++i) {
                consumer.accept(arrE[i]);
            }
        }
        for (n = 0; n < this.elementIndex; ++n) {
            consumer.accept(this.curChunk[n]);
        }
    }

    public E get(long l) {
        if (this.spineIndex == 0) {
            if (l < (long)this.elementIndex) {
                return this.curChunk[(int)l];
            }
            throw new IndexOutOfBoundsException(Long.toString(l));
        }
        if (l < this.count()) {
            for (int i = 0; i <= this.spineIndex; ++i) {
                long l2 = this.priorElementCount[i];
                E[][] arrE = this.spine;
                if (l >= l2 + (long)arrE[i].length) continue;
                return arrE[i][(int)(l - this.priorElementCount[i])];
            }
            throw new IndexOutOfBoundsException(Long.toString(l));
        }
        throw new IndexOutOfBoundsException(Long.toString(l));
    }

    protected void increaseCapacity() {
        this.ensureCapacity(this.capacity() + 1L);
    }

    @Override
    public Iterator<E> iterator() {
        return Spliterators.iterator(this.spliterator());
    }

    @Override
    public Spliterator<E> spliterator() {
        return new 1Splitr(0, this.spineIndex, 0, this.elementIndex);
    }

    public String toString() {
        ArrayList arrayList = new ArrayList();
        Objects.requireNonNull(arrayList);
        this.forEach(new _$$Lambda$KCZEjYEMhxUzZZN6W26atKG9NZc(arrayList));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SpinedBuffer:");
        stringBuilder.append(((Object)arrayList).toString());
        return stringBuilder.toString();
    }

    class 1Splitr
    implements Spliterator<E> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        final int lastSpineElementFence;
        final int lastSpineIndex;
        E[] splChunk;
        int splElementIndex;
        int splSpineIndex;

        1Splitr(int n, int n2, int n3, int n4) {
            this.splSpineIndex = n;
            this.lastSpineIndex = n2;
            this.splElementIndex = n3;
            this.lastSpineElementFence = n4;
            SpinedBuffer.this = SpinedBuffer.this.spine == null ? SpinedBuffer.this.curChunk : SpinedBuffer.this.spine[n];
            this.splChunk = SpinedBuffer.this;
        }

        @Override
        public int characteristics() {
            return 16464;
        }

        @Override
        public long estimateSize() {
            long l = this.splSpineIndex == this.lastSpineIndex ? (long)this.lastSpineElementFence - (long)this.splElementIndex : SpinedBuffer.this.priorElementCount[this.lastSpineIndex] + (long)this.lastSpineElementFence - SpinedBuffer.this.priorElementCount[this.splSpineIndex] - (long)this.splElementIndex;
            return l;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            int n = this.splSpineIndex;
            int n2 = this.lastSpineIndex;
            if (n < n2 || n == n2 && this.splElementIndex < this.lastSpineElementFence) {
                int n3;
                E[] arrE;
                n2 = this.splElementIndex;
                for (n = this.splSpineIndex; n < (n3 = this.lastSpineIndex); ++n) {
                    arrE = SpinedBuffer.this.spine[n];
                    while (n2 < arrE.length) {
                        consumer.accept(arrE[n2]);
                        ++n2;
                    }
                    n2 = 0;
                }
                arrE = this.splSpineIndex == n3 ? this.splChunk : SpinedBuffer.this.spine[this.lastSpineIndex];
                n = this.lastSpineElementFence;
                while (n2 < n) {
                    consumer.accept(arrE[n2]);
                    ++n2;
                }
                this.splSpineIndex = this.lastSpineIndex;
                this.splElementIndex = this.lastSpineElementFence;
            }
        }

        @Override
        public boolean tryAdvance(Consumer<? super E> consumer) {
            Objects.requireNonNull(consumer);
            int n = this.splSpineIndex;
            int n2 = this.lastSpineIndex;
            if (n >= n2 && (n != n2 || this.splElementIndex >= this.lastSpineElementFence)) {
                return false;
            }
            E[] arrE = this.splChunk;
            n = this.splElementIndex;
            this.splElementIndex = n + 1;
            consumer.accept(arrE[n]);
            if (this.splElementIndex == this.splChunk.length) {
                this.splElementIndex = 0;
                ++this.splSpineIndex;
                if (SpinedBuffer.this.spine != null && this.splSpineIndex <= this.lastSpineIndex) {
                    this.splChunk = SpinedBuffer.this.spine[this.splSpineIndex];
                }
            }
            return true;
        }

        @Override
        public Spliterator<E> trySplit() {
            int n = this.splSpineIndex;
            int n2 = this.lastSpineIndex;
            if (n < n2) {
                Object object = SpinedBuffer.this;
                object = (SpinedBuffer)object.new 1Splitr(n, n2 - 1, this.splElementIndex, ((SpinedBuffer)object).spine[this.lastSpineIndex - 1].length);
                this.splSpineIndex = this.lastSpineIndex;
                this.splElementIndex = 0;
                this.splChunk = SpinedBuffer.this.spine[this.splSpineIndex];
                return object;
            }
            if (n == n2) {
                n2 = this.lastSpineElementFence;
                n = this.splElementIndex;
                if ((n2 = (n2 - n) / 2) == 0) {
                    return null;
                }
                Spliterator<E> spliterator = Arrays.spliterator(this.splChunk, n, n + n2);
                this.splElementIndex += n2;
                return spliterator;
            }
            return null;
        }
    }

    public static class OfDouble
    extends OfPrimitive<Double, double[], DoubleConsumer>
    implements DoubleConsumer {
        public OfDouble() {
        }

        public OfDouble(int n) {
            super(n);
        }

        @Override
        public void accept(double d) {
            this.preAccept();
            double[] arrd = (double[])this.curChunk;
            int n = this.elementIndex;
            this.elementIndex = n + 1;
            arrd[n] = d;
        }

        @Override
        protected void arrayForEach(double[] arrd, int n, int n2, DoubleConsumer doubleConsumer) {
            while (n < n2) {
                doubleConsumer.accept(arrd[n]);
                ++n;
            }
        }

        @Override
        protected int arrayLength(double[] arrd) {
            return arrd.length;
        }

        @Override
        public void forEach(Consumer<? super Double> consumer) {
            if (consumer instanceof DoubleConsumer) {
                this.forEach((DoubleConsumer)((Object)consumer));
            } else {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(this.getClass(), "{0} calling SpinedBuffer.OfDouble.forEach(Consumer)");
                }
                this.spliterator().forEachRemaining(consumer);
            }
        }

        public double get(long l) {
            int n = this.chunkFor(l);
            if (this.spineIndex == 0 && n == 0) {
                return ((double[])this.curChunk)[(int)l];
            }
            return ((double[][])this.spine)[n][(int)(l - this.priorElementCount[n])];
        }

        public PrimitiveIterator.OfDouble iterator() {
            return Spliterators.iterator(this.spliterator());
        }

        @Override
        public double[] newArray(int n) {
            return new double[n];
        }

        protected double[][] newArrayArray(int n) {
            return new double[n][];
        }

        public Spliterator.OfDouble spliterator() {
            return new 1Splitr(0, this.spineIndex, 0, this.elementIndex);
        }

        public String toString() {
            double[] arrd = (double[])this.asPrimitiveArray();
            if (arrd.length < 200) {
                return String.format("%s[length=%d, chunks=%d]%s", this.getClass().getSimpleName(), arrd.length, this.spineIndex, Arrays.toString(arrd));
            }
            double[] arrd2 = Arrays.copyOf(arrd, 200);
            return String.format("%s[length=%d, chunks=%d]%s...", this.getClass().getSimpleName(), arrd.length, this.spineIndex, Arrays.toString(arrd2));
        }

        class 1Splitr
        extends BaseSpliterator<Spliterator.OfDouble>
        implements Spliterator.OfDouble {
            1Splitr(int n, int n2, int n3, int n4) {
                super(n, n2, n3, n4);
            }

            void arrayForOne(double[] arrd, int n, DoubleConsumer doubleConsumer) {
                doubleConsumer.accept(arrd[n]);
            }

            Spliterator.OfDouble arraySpliterator(double[] arrd, int n, int n2) {
                return Arrays.spliterator(arrd, n, n + n2);
            }

            1Splitr newSpliterator(int n, int n2, int n3, int n4) {
                return new 1Splitr(n, n2, n3, n4);
            }
        }

    }

    public static class OfInt
    extends OfPrimitive<Integer, int[], IntConsumer>
    implements IntConsumer {
        public OfInt() {
        }

        public OfInt(int n) {
            super(n);
        }

        @Override
        public void accept(int n) {
            this.preAccept();
            int[] arrn = (int[])this.curChunk;
            int n2 = this.elementIndex;
            this.elementIndex = n2 + 1;
            arrn[n2] = n;
        }

        @Override
        protected void arrayForEach(int[] arrn, int n, int n2, IntConsumer intConsumer) {
            while (n < n2) {
                intConsumer.accept(arrn[n]);
                ++n;
            }
        }

        @Override
        protected int arrayLength(int[] arrn) {
            return arrn.length;
        }

        @Override
        public void forEach(Consumer<? super Integer> consumer) {
            if (consumer instanceof IntConsumer) {
                this.forEach((IntConsumer)((Object)consumer));
            } else {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(this.getClass(), "{0} calling SpinedBuffer.OfInt.forEach(Consumer)");
                }
                this.spliterator().forEachRemaining(consumer);
            }
        }

        public int get(long l) {
            int n = this.chunkFor(l);
            if (this.spineIndex == 0 && n == 0) {
                return ((int[])this.curChunk)[(int)l];
            }
            return ((int[][])this.spine)[n][(int)(l - this.priorElementCount[n])];
        }

        public PrimitiveIterator.OfInt iterator() {
            return Spliterators.iterator(this.spliterator());
        }

        @Override
        public int[] newArray(int n) {
            return new int[n];
        }

        protected int[][] newArrayArray(int n) {
            return new int[n][];
        }

        public Spliterator.OfInt spliterator() {
            return new 1Splitr(0, this.spineIndex, 0, this.elementIndex);
        }

        public String toString() {
            int[] arrn = (int[])this.asPrimitiveArray();
            if (arrn.length < 200) {
                return String.format("%s[length=%d, chunks=%d]%s", this.getClass().getSimpleName(), arrn.length, this.spineIndex, Arrays.toString(arrn));
            }
            int[] arrn2 = Arrays.copyOf(arrn, 200);
            return String.format("%s[length=%d, chunks=%d]%s...", this.getClass().getSimpleName(), arrn.length, this.spineIndex, Arrays.toString(arrn2));
        }

        class 1Splitr
        extends BaseSpliterator<Spliterator.OfInt>
        implements Spliterator.OfInt {
            1Splitr(int n, int n2, int n3, int n4) {
                super(n, n2, n3, n4);
            }

            void arrayForOne(int[] arrn, int n, IntConsumer intConsumer) {
                intConsumer.accept(arrn[n]);
            }

            Spliterator.OfInt arraySpliterator(int[] arrn, int n, int n2) {
                return Arrays.spliterator(arrn, n, n + n2);
            }

            1Splitr newSpliterator(int n, int n2, int n3, int n4) {
                return new 1Splitr(n, n2, n3, n4);
            }
        }

    }

    public static class OfLong
    extends OfPrimitive<Long, long[], LongConsumer>
    implements LongConsumer {
        public OfLong() {
        }

        public OfLong(int n) {
            super(n);
        }

        @Override
        public void accept(long l) {
            this.preAccept();
            long[] arrl = (long[])this.curChunk;
            int n = this.elementIndex;
            this.elementIndex = n + 1;
            arrl[n] = l;
        }

        @Override
        protected void arrayForEach(long[] arrl, int n, int n2, LongConsumer longConsumer) {
            while (n < n2) {
                longConsumer.accept(arrl[n]);
                ++n;
            }
        }

        @Override
        protected int arrayLength(long[] arrl) {
            return arrl.length;
        }

        @Override
        public void forEach(Consumer<? super Long> consumer) {
            if (consumer instanceof LongConsumer) {
                this.forEach((LongConsumer)((Object)consumer));
            } else {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(this.getClass(), "{0} calling SpinedBuffer.OfLong.forEach(Consumer)");
                }
                this.spliterator().forEachRemaining(consumer);
            }
        }

        public long get(long l) {
            int n = this.chunkFor(l);
            if (this.spineIndex == 0 && n == 0) {
                return ((long[])this.curChunk)[(int)l];
            }
            return ((long[][])this.spine)[n][(int)(l - this.priorElementCount[n])];
        }

        public PrimitiveIterator.OfLong iterator() {
            return Spliterators.iterator(this.spliterator());
        }

        @Override
        public long[] newArray(int n) {
            return new long[n];
        }

        protected long[][] newArrayArray(int n) {
            return new long[n][];
        }

        public Spliterator.OfLong spliterator() {
            return new 1Splitr(0, this.spineIndex, 0, this.elementIndex);
        }

        public String toString() {
            long[] arrl = (long[])this.asPrimitiveArray();
            if (arrl.length < 200) {
                return String.format("%s[length=%d, chunks=%d]%s", this.getClass().getSimpleName(), arrl.length, this.spineIndex, Arrays.toString(arrl));
            }
            long[] arrl2 = Arrays.copyOf(arrl, 200);
            return String.format("%s[length=%d, chunks=%d]%s...", this.getClass().getSimpleName(), arrl.length, this.spineIndex, Arrays.toString(arrl2));
        }

        class 1Splitr
        extends BaseSpliterator<Spliterator.OfLong>
        implements Spliterator.OfLong {
            1Splitr(int n, int n2, int n3, int n4) {
                super(n, n2, n3, n4);
            }

            void arrayForOne(long[] arrl, int n, LongConsumer longConsumer) {
                longConsumer.accept(arrl[n]);
            }

            Spliterator.OfLong arraySpliterator(long[] arrl, int n, int n2) {
                return Arrays.spliterator(arrl, n, n + n2);
            }

            1Splitr newSpliterator(int n, int n2, int n3, int n4) {
                return new 1Splitr(n, n2, n3, n4);
            }
        }

    }

    static abstract class OfPrimitive<E, T_ARR, T_CONS>
    extends AbstractSpinedBuffer
    implements Iterable<E> {
        T_ARR curChunk;
        T_ARR[] spine;

        OfPrimitive() {
            this.curChunk = this.newArray(1 << this.initialChunkPower);
        }

        OfPrimitive(int n) {
            super(n);
            this.curChunk = this.newArray(1 << this.initialChunkPower);
        }

        private void inflateSpine() {
            if (this.spine == null) {
                this.spine = this.newArrayArray(8);
                this.priorElementCount = new long[8];
                this.spine[0] = this.curChunk;
            }
        }

        protected abstract void arrayForEach(T_ARR var1, int var2, int var3, T_CONS var4);

        protected abstract int arrayLength(T_ARR var1);

        public T_ARR asPrimitiveArray() {
            long l = this.count();
            if (l < 0x7FFFFFF7L) {
                T_ARR T_ARR = this.newArray((int)l);
                this.copyInto(T_ARR, 0);
                return T_ARR;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        protected long capacity() {
            long l = this.spineIndex == 0 ? (long)this.arrayLength(this.curChunk) : this.priorElementCount[this.spineIndex] + (long)this.arrayLength(this.spine[this.spineIndex]);
            return l;
        }

        protected int chunkFor(long l) {
            if (this.spineIndex == 0) {
                if (l < (long)this.elementIndex) {
                    return 0;
                }
                throw new IndexOutOfBoundsException(Long.toString(l));
            }
            if (l < this.count()) {
                for (int i = 0; i <= this.spineIndex; ++i) {
                    if (l >= this.priorElementCount[i] + (long)this.arrayLength(this.spine[i])) continue;
                    return i;
                }
                throw new IndexOutOfBoundsException(Long.toString(l));
            }
            throw new IndexOutOfBoundsException(Long.toString(l));
        }

        @Override
        public void clear() {
            T_ARR[] arrT_ARR = this.spine;
            if (arrT_ARR != null) {
                this.curChunk = arrT_ARR[0];
                this.spine = null;
                this.priorElementCount = null;
            }
            this.elementIndex = 0;
            this.spineIndex = 0;
        }

        public void copyInto(T_ARR T_ARR, int n) {
            long l = (long)n + this.count();
            if (l <= (long)this.arrayLength(T_ARR) && l >= (long)n) {
                if (this.spineIndex == 0) {
                    System.arraycopy(this.curChunk, 0, T_ARR, n, this.elementIndex);
                } else {
                    for (int i = 0; i < this.spineIndex; ++i) {
                        T_ARR[] arrT_ARR = this.spine;
                        System.arraycopy(arrT_ARR[i], 0, T_ARR, n, this.arrayLength(arrT_ARR[i]));
                        n += this.arrayLength(this.spine[i]);
                    }
                    if (this.elementIndex > 0) {
                        System.arraycopy(this.curChunk, 0, T_ARR, n, this.elementIndex);
                    }
                }
                return;
            }
            throw new IndexOutOfBoundsException("does not fit");
        }

        protected final void ensureCapacity(long l) {
            long l2 = this.capacity();
            if (l > l2) {
                this.inflateSpine();
                int n = this.spineIndex + 1;
                while (l > l2) {
                    int n2;
                    T_ARR[] arrT_ARR = this.spine;
                    if (n >= arrT_ARR.length) {
                        n2 = arrT_ARR.length * 2;
                        this.spine = Arrays.copyOf(arrT_ARR, n2);
                        this.priorElementCount = Arrays.copyOf(this.priorElementCount, n2);
                    }
                    n2 = this.chunkSize(n);
                    this.spine[n] = this.newArray(n2);
                    this.priorElementCount[n] = this.priorElementCount[n - 1] + (long)this.arrayLength(this.spine[n - 1]);
                    l2 += (long)n2;
                    ++n;
                }
            }
        }

        public void forEach(T_CONS T_CONS) {
            for (int i = 0; i < this.spineIndex; ++i) {
                T_ARR[] arrT_ARR = this.spine;
                this.arrayForEach(arrT_ARR[i], 0, this.arrayLength(arrT_ARR[i]), T_CONS);
            }
            this.arrayForEach(this.curChunk, 0, this.elementIndex, T_CONS);
        }

        @Override
        public abstract void forEach(Consumer<? super E> var1);

        protected void increaseCapacity() {
            this.ensureCapacity(this.capacity() + 1L);
        }

        @Override
        public abstract Iterator<E> iterator();

        public abstract T_ARR newArray(int var1);

        protected abstract T_ARR[] newArrayArray(int var1);

        protected void preAccept() {
            if (this.elementIndex == this.arrayLength(this.curChunk)) {
                this.inflateSpine();
                int n = this.spineIndex;
                T_ARR[] arrT_ARR = this.spine;
                if (n + 1 >= arrT_ARR.length || arrT_ARR[this.spineIndex + 1] == null) {
                    this.increaseCapacity();
                }
                this.elementIndex = 0;
                ++this.spineIndex;
                this.curChunk = this.spine[this.spineIndex];
            }
        }

        abstract class BaseSpliterator<T_SPLITR extends Spliterator.OfPrimitive<E, T_CONS, T_SPLITR>>
        implements Spliterator.OfPrimitive<E, T_CONS, T_SPLITR> {
            static final /* synthetic */ boolean $assertionsDisabled = false;
            final int lastSpineElementFence;
            final int lastSpineIndex;
            T_ARR splChunk;
            int splElementIndex;
            int splSpineIndex;

            BaseSpliterator(int n, int n2, int n3, int n4) {
                this.splSpineIndex = n;
                this.lastSpineIndex = n2;
                this.splElementIndex = n3;
                this.lastSpineElementFence = n4;
                OfPrimitive.this = OfPrimitive.this.spine == null ? OfPrimitive.this.curChunk : OfPrimitive.this.spine[n];
                this.splChunk = OfPrimitive.this;
            }

            abstract void arrayForOne(T_ARR var1, int var2, T_CONS var3);

            abstract T_SPLITR arraySpliterator(T_ARR var1, int var2, int var3);

            @Override
            public int characteristics() {
                return 16464;
            }

            @Override
            public long estimateSize() {
                long l = this.splSpineIndex == this.lastSpineIndex ? (long)this.lastSpineElementFence - (long)this.splElementIndex : OfPrimitive.this.priorElementCount[this.lastSpineIndex] + (long)this.lastSpineElementFence - OfPrimitive.this.priorElementCount[this.splSpineIndex] - (long)this.splElementIndex;
                return l;
            }

            @Override
            public void forEachRemaining(T_CONS T_CONS) {
                Objects.requireNonNull(T_CONS);
                int n = this.splSpineIndex;
                int n2 = this.lastSpineIndex;
                if (n < n2 || n == n2 && this.splElementIndex < this.lastSpineElementFence) {
                    int n3;
                    Object object;
                    n = this.splElementIndex;
                    for (n2 = this.splSpineIndex; n2 < (n3 = this.lastSpineIndex); ++n2) {
                        T_ARR T_ARR = OfPrimitive.this.spine[n2];
                        object = OfPrimitive.this;
                        ((OfPrimitive)object).arrayForEach(T_ARR, n, ((OfPrimitive)object).arrayLength(T_ARR), T_CONS);
                        n = 0;
                    }
                    object = this.splSpineIndex == n3 ? this.splChunk : OfPrimitive.this.spine[this.lastSpineIndex];
                    OfPrimitive.this.arrayForEach(object, n, this.lastSpineElementFence, T_CONS);
                    this.splSpineIndex = this.lastSpineIndex;
                    this.splElementIndex = this.lastSpineElementFence;
                }
            }

            abstract T_SPLITR newSpliterator(int var1, int var2, int var3, int var4);

            @Override
            public boolean tryAdvance(T_CONS T_CONS) {
                Objects.requireNonNull(T_CONS);
                int n = this.splSpineIndex;
                int n2 = this.lastSpineIndex;
                if (n >= n2 && (n != n2 || this.splElementIndex >= this.lastSpineElementFence)) {
                    return false;
                }
                T_ARR T_ARR = this.splChunk;
                n = this.splElementIndex;
                this.splElementIndex = n + 1;
                this.arrayForOne(T_ARR, n, T_CONS);
                if (this.splElementIndex == OfPrimitive.this.arrayLength(this.splChunk)) {
                    this.splElementIndex = 0;
                    ++this.splSpineIndex;
                    if (OfPrimitive.this.spine != null && this.splSpineIndex <= this.lastSpineIndex) {
                        this.splChunk = OfPrimitive.this.spine[this.splSpineIndex];
                    }
                }
                return true;
            }

            @Override
            public T_SPLITR trySplit() {
                int n = this.splSpineIndex;
                int n2 = this.lastSpineIndex;
                if (n < n2) {
                    int n3 = this.splElementIndex;
                    OfPrimitive<E, T_ARR, T_CONS> ofPrimitive = OfPrimitive.this;
                    ofPrimitive = this.newSpliterator(n, n2 - 1, n3, ofPrimitive.arrayLength(ofPrimitive.spine[this.lastSpineIndex - 1]));
                    this.splSpineIndex = this.lastSpineIndex;
                    this.splElementIndex = 0;
                    this.splChunk = OfPrimitive.this.spine[this.splSpineIndex];
                    return (T_SPLITR)ofPrimitive;
                }
                if (n == n2) {
                    int n4 = this.lastSpineElementFence;
                    n = this.splElementIndex;
                    if ((n4 = (n4 - n) / 2) == 0) {
                        return null;
                    }
                    T_SPLITR T_SPLITR = this.arraySpliterator(this.splChunk, n, n4);
                    this.splElementIndex += n4;
                    return T_SPLITR;
                }
                return null;
            }
        }

    }

}

