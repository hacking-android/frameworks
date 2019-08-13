/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$StreamSpliterators
 *  java.util.stream.-$$Lambda$StreamSpliterators$SliceSpliterator
 *  java.util.stream.-$$Lambda$StreamSpliterators$SliceSpliterator$OfDouble
 *  java.util.stream.-$$Lambda$StreamSpliterators$SliceSpliterator$OfDouble$F1bBlpqcoM_HwaVPMQ3Q9zUwTCw
 *  java.util.stream.-$$Lambda$StreamSpliterators$SliceSpliterator$OfInt
 *  java.util.stream.-$$Lambda$StreamSpliterators$SliceSpliterator$OfInt$GDCU9wlqIN8f-np3lkzlBdIGmvc
 *  java.util.stream.-$$Lambda$StreamSpliterators$SliceSpliterator$OfLong
 *  java.util.stream.-$$Lambda$StreamSpliterators$SliceSpliterator$OfLong$gbTno_el7bKUjUiBqsBq7RYjcY8
 *  java.util.stream.-$$Lambda$StreamSpliterators$SliceSpliterator$OfRef
 *  java.util.stream.-$$Lambda$StreamSpliterators$SliceSpliterator$OfRef$NUGTWbZg9cfpPm623I8CORYtfns
 *  java.util.stream.-$$Lambda$StreamSpliterators$SliceSpliterator$OfRef$WQsOrB6TN5sHvsPJU2O20DZGElU
 */
package java.util.stream;

import java.util.Comparator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.LongConsumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.-$;
import java.util.stream.AbstractSpinedBuffer;
import java.util.stream.PipelineHelper;
import java.util.stream.Sink;
import java.util.stream.SpinedBuffer;
import java.util.stream.StreamOpFlag;
import java.util.stream._$$Lambda$6BdNjvJJOqgXMfHsEogzyrab_60;
import java.util.stream._$$Lambda$C9lt_0Cg_SARhdNFJsMyHSsCsGA;
import java.util.stream._$$Lambda$G3FiaNZPcIIAnGkHVY7Mdu42X5g;
import java.util.stream._$$Lambda$GF_s38TgrG6hfxe__ZFdhGp_wPw;
import java.util.stream._$$Lambda$StreamSpliterators$DistinctSpliterator$ojM_Hxa6O4_MX3G2cGvIRG3GI58;
import java.util.stream._$$Lambda$StreamSpliterators$DoubleWrappingSpliterator$vGvekEV3XchaSAEI93tmYCeVG9A;
import java.util.stream._$$Lambda$StreamSpliterators$IntWrappingSpliterator$js67IRBzuEwtfp5Z3OTF_GfmUTw;
import java.util.stream._$$Lambda$StreamSpliterators$LongWrappingSpliterator$sXmxiR9mZHUX9mr52PfuVCxTtPw;
import java.util.stream._$$Lambda$StreamSpliterators$SliceSpliterator$OfDouble$F1bBlpqcoM_HwaVPMQ3Q9zUwTCw;
import java.util.stream._$$Lambda$StreamSpliterators$SliceSpliterator$OfInt$GDCU9wlqIN8f_np3lkzlBdIGmvc;
import java.util.stream._$$Lambda$StreamSpliterators$SliceSpliterator$OfLong$gbTno_el7bKUjUiBqsBq7RYjcY8;
import java.util.stream._$$Lambda$StreamSpliterators$SliceSpliterator$OfRef$NUGTWbZg9cfpPm623I8CORYtfns;
import java.util.stream._$$Lambda$StreamSpliterators$SliceSpliterator$OfRef$WQsOrB6TN5sHvsPJU2O20DZGElU;
import java.util.stream._$$Lambda$StreamSpliterators$WrappingSpliterator$Ky6g3CKkCccuRWAvbAL1cAsdkNk;
import java.util.stream._$$Lambda$ZgCkHA78fnu8poGzKYmvya_ev3U;
import java.util.stream._$$Lambda$btpzqYSQDsLykCcQbI2_g5D3_zs;
import java.util.stream._$$Lambda$fgFAI1gk0hw2h3IP9CmHWlY3YkM;
import java.util.stream._$$Lambda$xWqUKn_t_aBWo9sD9bohYsGFiXg;

class StreamSpliterators {
    StreamSpliterators() {
    }

    private static abstract class AbstractWrappingSpliterator<P_IN, P_OUT, T_BUFFER extends AbstractSpinedBuffer>
    implements Spliterator<P_OUT> {
        T_BUFFER buffer;
        Sink<P_IN> bufferSink;
        boolean finished;
        final boolean isParallel;
        long nextToConsume;
        final PipelineHelper<P_OUT> ph;
        BooleanSupplier pusher;
        Spliterator<P_IN> spliterator;
        private Supplier<Spliterator<P_IN>> spliteratorSupplier;

        AbstractWrappingSpliterator(PipelineHelper<P_OUT> pipelineHelper, Spliterator<P_IN> spliterator, boolean bl) {
            this.ph = pipelineHelper;
            this.spliteratorSupplier = null;
            this.spliterator = spliterator;
            this.isParallel = bl;
        }

        AbstractWrappingSpliterator(PipelineHelper<P_OUT> pipelineHelper, Supplier<Spliterator<P_IN>> supplier, boolean bl) {
            this.ph = pipelineHelper;
            this.spliteratorSupplier = supplier;
            this.spliterator = null;
            this.isParallel = bl;
        }

        private boolean fillBuffer() {
            while (((AbstractSpinedBuffer)this.buffer).count() == 0L) {
                if (!this.bufferSink.cancellationRequested() && this.pusher.getAsBoolean()) continue;
                if (this.finished) {
                    return false;
                }
                this.bufferSink.end();
                this.finished = true;
            }
            return true;
        }

        @Override
        public final int characteristics() {
            int n;
            this.init();
            int n2 = n = StreamOpFlag.toCharacteristics(StreamOpFlag.toStreamFlags(this.ph.getStreamAndOpFlags()));
            if ((n & 64) != 0) {
                n2 = n & -16449 | this.spliterator.characteristics() & 16448;
            }
            return n2;
        }

        final boolean doAdvance() {
            boolean bl;
            T_BUFFER T_BUFFER = this.buffer;
            boolean bl2 = false;
            if (T_BUFFER == null) {
                if (this.finished) {
                    return false;
                }
                this.init();
                this.initPartialTraversalState();
                this.nextToConsume = 0L;
                this.bufferSink.begin(this.spliterator.getExactSizeIfKnown());
                return this.fillBuffer();
            }
            ++this.nextToConsume;
            if (this.nextToConsume < ((AbstractSpinedBuffer)T_BUFFER).count()) {
                bl2 = true;
            }
            bl2 = bl = bl2;
            if (!bl) {
                this.nextToConsume = 0L;
                ((AbstractSpinedBuffer)this.buffer).clear();
                bl2 = this.fillBuffer();
            }
            return bl2;
        }

        @Override
        public final long estimateSize() {
            this.init();
            return this.spliterator.estimateSize();
        }

        @Override
        public Comparator<? super P_OUT> getComparator() {
            if (this.hasCharacteristics(4)) {
                return null;
            }
            throw new IllegalStateException();
        }

        @Override
        public final long getExactSizeIfKnown() {
            this.init();
            long l = StreamOpFlag.SIZED.isKnown(this.ph.getStreamAndOpFlags()) ? this.spliterator.getExactSizeIfKnown() : -1L;
            return l;
        }

        final void init() {
            if (this.spliterator == null) {
                this.spliterator = this.spliteratorSupplier.get();
                this.spliteratorSupplier = null;
            }
        }

        abstract void initPartialTraversalState();

        public final String toString() {
            return String.format("%s[%s]", this.getClass().getName(), this.spliterator);
        }

        @Override
        public Spliterator<P_OUT> trySplit() {
            boolean bl = this.isParallel;
            AbstractWrappingSpliterator<P_IN, P_OUT, ?> abstractWrappingSpliterator = null;
            if (bl && !this.finished) {
                this.init();
                Spliterator<P_IN> spliterator = this.spliterator.trySplit();
                if (spliterator != null) {
                    abstractWrappingSpliterator = this.wrap(spliterator);
                }
                return abstractWrappingSpliterator;
            }
            return null;
        }

        abstract AbstractWrappingSpliterator<P_IN, P_OUT, ?> wrap(Spliterator<P_IN> var1);
    }

    static abstract class ArrayBuffer {
        int index;

        ArrayBuffer() {
        }

        void reset() {
            this.index = 0;
        }

        static final class OfDouble
        extends OfPrimitive<DoubleConsumer>
        implements DoubleConsumer {
            final double[] array;

            OfDouble(int n) {
                this.array = new double[n];
            }

            @Override
            public void accept(double d) {
                double[] arrd = this.array;
                int n = this.index;
                this.index = n + 1;
                arrd[n] = d;
            }

            @Override
            void forEach(DoubleConsumer doubleConsumer, long l) {
                int n = 0;
                while ((long)n < l) {
                    doubleConsumer.accept(this.array[n]);
                    ++n;
                }
            }
        }

        static final class OfInt
        extends OfPrimitive<IntConsumer>
        implements IntConsumer {
            final int[] array;

            OfInt(int n) {
                this.array = new int[n];
            }

            @Override
            public void accept(int n) {
                int[] arrn = this.array;
                int n2 = this.index;
                this.index = n2 + 1;
                arrn[n2] = n;
            }

            @Override
            public void forEach(IntConsumer intConsumer, long l) {
                int n = 0;
                while ((long)n < l) {
                    intConsumer.accept(this.array[n]);
                    ++n;
                }
            }
        }

        static final class OfLong
        extends OfPrimitive<LongConsumer>
        implements LongConsumer {
            final long[] array;

            OfLong(int n) {
                this.array = new long[n];
            }

            @Override
            public void accept(long l) {
                long[] arrl = this.array;
                int n = this.index;
                this.index = n + 1;
                arrl[n] = l;
            }

            @Override
            public void forEach(LongConsumer longConsumer, long l) {
                int n = 0;
                while ((long)n < l) {
                    longConsumer.accept(this.array[n]);
                    ++n;
                }
            }
        }

        static abstract class OfPrimitive<T_CONS>
        extends ArrayBuffer {
            int index;

            OfPrimitive() {
            }

            abstract void forEach(T_CONS var1, long var2);

            @Override
            void reset() {
                this.index = 0;
            }
        }

        static final class OfRef<T>
        extends ArrayBuffer
        implements Consumer<T> {
            final Object[] array;

            OfRef(int n) {
                this.array = new Object[n];
            }

            @Override
            public void accept(T t) {
                Object[] arrobject = this.array;
                int n = this.index;
                this.index = n + 1;
                arrobject[n] = t;
            }

            public void forEach(Consumer<? super T> consumer, long l) {
                int n = 0;
                while ((long)n < l) {
                    consumer.accept(this.array[n]);
                    ++n;
                }
            }
        }

    }

    static class DelegatingSpliterator<T, T_SPLITR extends Spliterator<T>>
    implements Spliterator<T> {
        private T_SPLITR s;
        private final Supplier<? extends T_SPLITR> supplier;

        DelegatingSpliterator(Supplier<? extends T_SPLITR> supplier) {
            this.supplier = supplier;
        }

        @Override
        public int characteristics() {
            return this.get().characteristics();
        }

        @Override
        public long estimateSize() {
            return this.get().estimateSize();
        }

        @Override
        public void forEachRemaining(Consumer<? super T> consumer) {
            this.get().forEachRemaining(consumer);
        }

        T_SPLITR get() {
            if (this.s == null) {
                this.s = (Spliterator)this.supplier.get();
            }
            return this.s;
        }

        @Override
        public Comparator<? super T> getComparator() {
            return this.get().getComparator();
        }

        @Override
        public long getExactSizeIfKnown() {
            return this.get().getExactSizeIfKnown();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getClass().getName());
            stringBuilder.append("[");
            stringBuilder.append(this.get());
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> consumer) {
            return this.get().tryAdvance(consumer);
        }

        public T_SPLITR trySplit() {
            return (T_SPLITR)this.get().trySplit();
        }

        static final class OfDouble
        extends OfPrimitive<Double, DoubleConsumer, Spliterator.OfDouble>
        implements Spliterator.OfDouble {
            OfDouble(Supplier<Spliterator.OfDouble> supplier) {
                super(supplier);
            }
        }

        static final class OfInt
        extends OfPrimitive<Integer, IntConsumer, Spliterator.OfInt>
        implements Spliterator.OfInt {
            OfInt(Supplier<Spliterator.OfInt> supplier) {
                super(supplier);
            }
        }

        static final class OfLong
        extends OfPrimitive<Long, LongConsumer, Spliterator.OfLong>
        implements Spliterator.OfLong {
            OfLong(Supplier<Spliterator.OfLong> supplier) {
                super(supplier);
            }
        }

        static class OfPrimitive<T, T_CONS, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>>
        extends DelegatingSpliterator<T, T_SPLITR>
        implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR> {
            OfPrimitive(Supplier<? extends T_SPLITR> supplier) {
                super(supplier);
            }

            @Override
            public void forEachRemaining(T_CONS T_CONS) {
                ((Spliterator.OfPrimitive)this.get()).forEachRemaining(T_CONS);
            }

            @Override
            public boolean tryAdvance(T_CONS T_CONS) {
                return ((Spliterator.OfPrimitive)this.get()).tryAdvance(T_CONS);
            }
        }

    }

    static final class DistinctSpliterator<T>
    implements Spliterator<T>,
    Consumer<T> {
        private static final Object NULL_VALUE = new Object();
        private final Spliterator<T> s;
        private final ConcurrentHashMap<T, Boolean> seen;
        private T tmpSlot;

        DistinctSpliterator(Spliterator<T> spliterator) {
            this(spliterator, new ConcurrentHashMap());
        }

        private DistinctSpliterator(Spliterator<T> spliterator, ConcurrentHashMap<T, Boolean> concurrentHashMap) {
            this.s = spliterator;
            this.seen = concurrentHashMap;
        }

        private T mapNull(T object) {
            if (object == null) {
                object = NULL_VALUE;
            }
            return object;
        }

        @Override
        public void accept(T t) {
            this.tmpSlot = t;
        }

        @Override
        public int characteristics() {
            return this.s.characteristics() & -16469 | 1;
        }

        @Override
        public long estimateSize() {
            return this.s.estimateSize();
        }

        @Override
        public void forEachRemaining(Consumer<? super T> consumer) {
            this.s.forEachRemaining(new _$$Lambda$StreamSpliterators$DistinctSpliterator$ojM_Hxa6O4_MX3G2cGvIRG3GI58(this, consumer));
        }

        @Override
        public Comparator<? super T> getComparator() {
            return this.s.getComparator();
        }

        public /* synthetic */ void lambda$forEachRemaining$0$StreamSpliterators$DistinctSpliterator(Consumer consumer, Object object) {
            if (this.seen.putIfAbsent(this.mapNull(object), Boolean.TRUE) == null) {
                consumer.accept(object);
            }
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> consumer) {
            while (this.s.tryAdvance(this)) {
                if (this.seen.putIfAbsent(this.mapNull(this.tmpSlot), Boolean.TRUE) != null) continue;
                consumer.accept(this.tmpSlot);
                this.tmpSlot = null;
                return true;
            }
            return false;
        }

        @Override
        public Spliterator<T> trySplit() {
            Spliterator<T> spliterator = this.s.trySplit();
            spliterator = spliterator != null ? new DistinctSpliterator<T>(spliterator, this.seen) : null;
            return spliterator;
        }
    }

    static final class DoubleWrappingSpliterator<P_IN>
    extends AbstractWrappingSpliterator<P_IN, Double, SpinedBuffer.OfDouble>
    implements Spliterator.OfDouble {
        DoubleWrappingSpliterator(PipelineHelper<Double> pipelineHelper, Spliterator<P_IN> spliterator, boolean bl) {
            super(pipelineHelper, spliterator, bl);
        }

        DoubleWrappingSpliterator(PipelineHelper<Double> pipelineHelper, Supplier<Spliterator<P_IN>> supplier, boolean bl) {
            super(pipelineHelper, supplier, bl);
        }

        @Override
        public void forEachRemaining(DoubleConsumer doubleConsumer) {
            if (this.buffer == null && !this.finished) {
                Objects.requireNonNull(doubleConsumer);
                this.init();
                PipelineHelper pipelineHelper = this.ph;
                Objects.requireNonNull(doubleConsumer);
                pipelineHelper.wrapAndCopyInto(new _$$Lambda$fgFAI1gk0hw2h3IP9CmHWlY3YkM(doubleConsumer), this.spliterator);
                this.finished = true;
            } else {
                while (this.tryAdvance(doubleConsumer)) {
                }
            }
        }

        @Override
        void initPartialTraversalState() {
            SpinedBuffer.OfDouble ofDouble = new SpinedBuffer.OfDouble();
            this.buffer = ofDouble;
            PipelineHelper pipelineHelper = this.ph;
            Objects.requireNonNull(ofDouble);
            this.bufferSink = pipelineHelper.wrapSink(new _$$Lambda$xWqUKn_t_aBWo9sD9bohYsGFiXg(ofDouble));
            this.pusher = new _$$Lambda$StreamSpliterators$DoubleWrappingSpliterator$vGvekEV3XchaSAEI93tmYCeVG9A(this);
        }

        public /* synthetic */ boolean lambda$initPartialTraversalState$0$StreamSpliterators$DoubleWrappingSpliterator() {
            return this.spliterator.tryAdvance(this.bufferSink);
        }

        @Override
        public boolean tryAdvance(DoubleConsumer doubleConsumer) {
            Objects.requireNonNull(doubleConsumer);
            boolean bl = this.doAdvance();
            if (bl) {
                doubleConsumer.accept(((SpinedBuffer.OfDouble)this.buffer).get(this.nextToConsume));
            }
            return bl;
        }

        @Override
        public Spliterator.OfDouble trySplit() {
            return (Spliterator.OfDouble)super.trySplit();
        }

        @Override
        AbstractWrappingSpliterator<P_IN, Double, ?> wrap(Spliterator<P_IN> spliterator) {
            return new DoubleWrappingSpliterator<P_IN>((PipelineHelper<Double>)this.ph, spliterator, this.isParallel);
        }
    }

    static abstract class InfiniteSupplyingSpliterator<T>
    implements Spliterator<T> {
        long estimate;

        protected InfiniteSupplyingSpliterator(long l) {
            this.estimate = l;
        }

        @Override
        public int characteristics() {
            return 1024;
        }

        @Override
        public long estimateSize() {
            return this.estimate;
        }

        static final class OfDouble
        extends InfiniteSupplyingSpliterator<Double>
        implements Spliterator.OfDouble {
            final DoubleSupplier s;

            OfDouble(long l, DoubleSupplier doubleSupplier) {
                super(l);
                this.s = doubleSupplier;
            }

            @Override
            public boolean tryAdvance(DoubleConsumer doubleConsumer) {
                Objects.requireNonNull(doubleConsumer);
                doubleConsumer.accept(this.s.getAsDouble());
                return true;
            }

            @Override
            public Spliterator.OfDouble trySplit() {
                long l;
                if (this.estimate == 0L) {
                    return null;
                }
                this.estimate = l = this.estimate >>> 1;
                return new OfDouble(l, this.s);
            }
        }

        static final class OfInt
        extends InfiniteSupplyingSpliterator<Integer>
        implements Spliterator.OfInt {
            final IntSupplier s;

            OfInt(long l, IntSupplier intSupplier) {
                super(l);
                this.s = intSupplier;
            }

            @Override
            public boolean tryAdvance(IntConsumer intConsumer) {
                Objects.requireNonNull(intConsumer);
                intConsumer.accept(this.s.getAsInt());
                return true;
            }

            @Override
            public Spliterator.OfInt trySplit() {
                long l;
                if (this.estimate == 0L) {
                    return null;
                }
                this.estimate = l = this.estimate >>> 1;
                return new OfInt(l, this.s);
            }
        }

        static final class OfLong
        extends InfiniteSupplyingSpliterator<Long>
        implements Spliterator.OfLong {
            final LongSupplier s;

            OfLong(long l, LongSupplier longSupplier) {
                super(l);
                this.s = longSupplier;
            }

            @Override
            public boolean tryAdvance(LongConsumer longConsumer) {
                Objects.requireNonNull(longConsumer);
                longConsumer.accept(this.s.getAsLong());
                return true;
            }

            @Override
            public Spliterator.OfLong trySplit() {
                long l;
                if (this.estimate == 0L) {
                    return null;
                }
                this.estimate = l = this.estimate >>> 1;
                return new OfLong(l, this.s);
            }
        }

        static final class OfRef<T>
        extends InfiniteSupplyingSpliterator<T> {
            final Supplier<T> s;

            OfRef(long l, Supplier<T> supplier) {
                super(l);
                this.s = supplier;
            }

            @Override
            public boolean tryAdvance(Consumer<? super T> consumer) {
                Objects.requireNonNull(consumer);
                consumer.accept(this.s.get());
                return true;
            }

            @Override
            public Spliterator<T> trySplit() {
                long l;
                if (this.estimate == 0L) {
                    return null;
                }
                this.estimate = l = this.estimate >>> 1;
                return new OfRef<T>(l, this.s);
            }
        }

    }

    static final class IntWrappingSpliterator<P_IN>
    extends AbstractWrappingSpliterator<P_IN, Integer, SpinedBuffer.OfInt>
    implements Spliterator.OfInt {
        IntWrappingSpliterator(PipelineHelper<Integer> pipelineHelper, Spliterator<P_IN> spliterator, boolean bl) {
            super(pipelineHelper, spliterator, bl);
        }

        IntWrappingSpliterator(PipelineHelper<Integer> pipelineHelper, Supplier<Spliterator<P_IN>> supplier, boolean bl) {
            super(pipelineHelper, supplier, bl);
        }

        @Override
        public void forEachRemaining(IntConsumer intConsumer) {
            if (this.buffer == null && !this.finished) {
                Objects.requireNonNull(intConsumer);
                this.init();
                PipelineHelper pipelineHelper = this.ph;
                Objects.requireNonNull(intConsumer);
                pipelineHelper.wrapAndCopyInto(new _$$Lambda$C9lt_0Cg_SARhdNFJsMyHSsCsGA(intConsumer), this.spliterator);
                this.finished = true;
            } else {
                while (this.tryAdvance(intConsumer)) {
                }
            }
        }

        @Override
        void initPartialTraversalState() {
            SpinedBuffer.OfInt ofInt = new SpinedBuffer.OfInt();
            this.buffer = ofInt;
            PipelineHelper pipelineHelper = this.ph;
            Objects.requireNonNull(ofInt);
            this.bufferSink = pipelineHelper.wrapSink(new _$$Lambda$ZgCkHA78fnu8poGzKYmvya_ev3U(ofInt));
            this.pusher = new _$$Lambda$StreamSpliterators$IntWrappingSpliterator$js67IRBzuEwtfp5Z3OTF_GfmUTw(this);
        }

        public /* synthetic */ boolean lambda$initPartialTraversalState$0$StreamSpliterators$IntWrappingSpliterator() {
            return this.spliterator.tryAdvance(this.bufferSink);
        }

        @Override
        public boolean tryAdvance(IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            boolean bl = this.doAdvance();
            if (bl) {
                intConsumer.accept(((SpinedBuffer.OfInt)this.buffer).get(this.nextToConsume));
            }
            return bl;
        }

        @Override
        public Spliterator.OfInt trySplit() {
            return (Spliterator.OfInt)super.trySplit();
        }

        @Override
        AbstractWrappingSpliterator<P_IN, Integer, ?> wrap(Spliterator<P_IN> spliterator) {
            return new IntWrappingSpliterator<P_IN>((PipelineHelper<Integer>)this.ph, spliterator, this.isParallel);
        }
    }

    static final class LongWrappingSpliterator<P_IN>
    extends AbstractWrappingSpliterator<P_IN, Long, SpinedBuffer.OfLong>
    implements Spliterator.OfLong {
        LongWrappingSpliterator(PipelineHelper<Long> pipelineHelper, Spliterator<P_IN> spliterator, boolean bl) {
            super(pipelineHelper, spliterator, bl);
        }

        LongWrappingSpliterator(PipelineHelper<Long> pipelineHelper, Supplier<Spliterator<P_IN>> supplier, boolean bl) {
            super(pipelineHelper, supplier, bl);
        }

        @Override
        public void forEachRemaining(LongConsumer longConsumer) {
            if (this.buffer == null && !this.finished) {
                Objects.requireNonNull(longConsumer);
                this.init();
                PipelineHelper pipelineHelper = this.ph;
                Objects.requireNonNull(longConsumer);
                pipelineHelper.wrapAndCopyInto(new _$$Lambda$G3FiaNZPcIIAnGkHVY7Mdu42X5g(longConsumer), this.spliterator);
                this.finished = true;
            } else {
                while (this.tryAdvance(longConsumer)) {
                }
            }
        }

        @Override
        void initPartialTraversalState() {
            SpinedBuffer.OfLong ofLong = new SpinedBuffer.OfLong();
            this.buffer = ofLong;
            PipelineHelper pipelineHelper = this.ph;
            Objects.requireNonNull(ofLong);
            this.bufferSink = pipelineHelper.wrapSink(new _$$Lambda$6BdNjvJJOqgXMfHsEogzyrab_60(ofLong));
            this.pusher = new _$$Lambda$StreamSpliterators$LongWrappingSpliterator$sXmxiR9mZHUX9mr52PfuVCxTtPw(this);
        }

        public /* synthetic */ boolean lambda$initPartialTraversalState$0$StreamSpliterators$LongWrappingSpliterator() {
            return this.spliterator.tryAdvance(this.bufferSink);
        }

        @Override
        public boolean tryAdvance(LongConsumer longConsumer) {
            Objects.requireNonNull(longConsumer);
            boolean bl = this.doAdvance();
            if (bl) {
                longConsumer.accept(((SpinedBuffer.OfLong)this.buffer).get(this.nextToConsume));
            }
            return bl;
        }

        @Override
        public Spliterator.OfLong trySplit() {
            return (Spliterator.OfLong)super.trySplit();
        }

        @Override
        AbstractWrappingSpliterator<P_IN, Long, ?> wrap(Spliterator<P_IN> spliterator) {
            return new LongWrappingSpliterator<P_IN>((PipelineHelper<Long>)this.ph, spliterator, this.isParallel);
        }
    }

    static abstract class SliceSpliterator<T, T_SPLITR extends Spliterator<T>> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        long fence;
        long index;
        T_SPLITR s;
        final long sliceFence;
        final long sliceOrigin;

        SliceSpliterator(T_SPLITR T_SPLITR, long l, long l2, long l3, long l4) {
            this.s = T_SPLITR;
            this.sliceOrigin = l;
            this.sliceFence = l2;
            this.index = l3;
            this.fence = l4;
        }

        public int characteristics() {
            return this.s.characteristics();
        }

        public long estimateSize() {
            long l = this.sliceOrigin;
            long l2 = this.fence;
            l2 = l < l2 ? (l2 -= Math.max(l, this.index)) : 0L;
            return l2;
        }

        protected abstract T_SPLITR makeSpliterator(T_SPLITR var1, long var2, long var4, long var6, long var8);

        public T_SPLITR trySplit() {
            long l;
            long l2;
            Spliterator<T> spliterator;
            long l3 = this.sliceOrigin;
            long l4 = this.fence;
            if (l3 >= l4) {
                return null;
            }
            if (this.index >= l4) {
                return null;
            }
            do {
                if ((spliterator = this.s.trySplit()) == null) {
                    return null;
                }
                l2 = this.sliceOrigin;
                l = this.index + spliterator.estimateSize();
                l4 = Math.min(l, this.sliceFence);
                if (l2 >= l4) {
                    this.index = l4;
                    continue;
                }
                l3 = this.sliceFence;
                if (l4 < l3) break;
                this.s = spliterator;
                this.fence = l4;
            } while (true);
            if (this.index >= l2 && l <= l3) {
                this.index = l4;
                return (T_SPLITR)spliterator;
            }
            l2 = this.sliceOrigin;
            l3 = this.sliceFence;
            l = this.index;
            this.index = l4;
            return (T_SPLITR)this.makeSpliterator(spliterator, l2, l3, l, l4);
        }

        static final class OfDouble
        extends OfPrimitive<Double, Spliterator.OfDouble, DoubleConsumer>
        implements Spliterator.OfDouble {
            OfDouble(Spliterator.OfDouble ofDouble, long l, long l2) {
                super(ofDouble, l, l2);
            }

            OfDouble(Spliterator.OfDouble ofDouble, long l, long l2, long l3, long l4) {
                super(ofDouble, l, l2, l3, l4, null);
            }

            static /* synthetic */ void lambda$emptyConsumer$0(double d) {
            }

            @Override
            protected DoubleConsumer emptyConsumer() {
                return _$$Lambda$StreamSpliterators$SliceSpliterator$OfDouble$F1bBlpqcoM_HwaVPMQ3Q9zUwTCw.INSTANCE;
            }

            @Override
            protected Spliterator.OfDouble makeSpliterator(Spliterator.OfDouble ofDouble, long l, long l2, long l3, long l4) {
                return new OfDouble(ofDouble, l, l2, l3, l4);
            }
        }

        static final class OfInt
        extends OfPrimitive<Integer, Spliterator.OfInt, IntConsumer>
        implements Spliterator.OfInt {
            OfInt(Spliterator.OfInt ofInt, long l, long l2) {
                super(ofInt, l, l2);
            }

            OfInt(Spliterator.OfInt ofInt, long l, long l2, long l3, long l4) {
                super(ofInt, l, l2, l3, l4, null);
            }

            static /* synthetic */ void lambda$emptyConsumer$0(int n) {
            }

            @Override
            protected IntConsumer emptyConsumer() {
                return _$$Lambda$StreamSpliterators$SliceSpliterator$OfInt$GDCU9wlqIN8f_np3lkzlBdIGmvc.INSTANCE;
            }

            @Override
            protected Spliterator.OfInt makeSpliterator(Spliterator.OfInt ofInt, long l, long l2, long l3, long l4) {
                return new OfInt(ofInt, l, l2, l3, l4);
            }
        }

        static final class OfLong
        extends OfPrimitive<Long, Spliterator.OfLong, LongConsumer>
        implements Spliterator.OfLong {
            OfLong(Spliterator.OfLong ofLong, long l, long l2) {
                super(ofLong, l, l2);
            }

            OfLong(Spliterator.OfLong ofLong, long l, long l2, long l3, long l4) {
                super(ofLong, l, l2, l3, l4, null);
            }

            static /* synthetic */ void lambda$emptyConsumer$0(long l) {
            }

            @Override
            protected LongConsumer emptyConsumer() {
                return _$$Lambda$StreamSpliterators$SliceSpliterator$OfLong$gbTno_el7bKUjUiBqsBq7RYjcY8.INSTANCE;
            }

            @Override
            protected Spliterator.OfLong makeSpliterator(Spliterator.OfLong ofLong, long l, long l2, long l3, long l4) {
                return new OfLong(ofLong, l, l2, l3, l4);
            }
        }

        static abstract class OfPrimitive<T, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>, T_CONS>
        extends SliceSpliterator<T, T_SPLITR>
        implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR> {
            OfPrimitive(T_SPLITR T_SPLITR, long l, long l2) {
                this(T_SPLITR, l, l2, 0L, Math.min(T_SPLITR.estimateSize(), l2));
            }

            private OfPrimitive(T_SPLITR T_SPLITR, long l, long l2, long l3, long l4) {
                super(T_SPLITR, l, l2, l3, l4);
            }

            /* synthetic */ OfPrimitive(Spliterator.OfPrimitive ofPrimitive, long l, long l2, long l3, long l4, 1 var10_6) {
                this(ofPrimitive, l, l2, l3, l4);
            }

            protected abstract T_CONS emptyConsumer();

            @Override
            public void forEachRemaining(T_CONS T_CONS) {
                Objects.requireNonNull(T_CONS);
                if (this.sliceOrigin >= this.fence) {
                    return;
                }
                if (this.index >= this.fence) {
                    return;
                }
                if (this.index >= this.sliceOrigin && this.index + ((Spliterator.OfPrimitive)this.s).estimateSize() <= this.sliceFence) {
                    ((Spliterator.OfPrimitive)this.s).forEachRemaining(T_CONS);
                    this.index = this.fence;
                } else {
                    while (this.sliceOrigin > this.index) {
                        ((Spliterator.OfPrimitive)this.s).tryAdvance(this.emptyConsumer());
                        ++this.index;
                    }
                    while (this.index < this.fence) {
                        ((Spliterator.OfPrimitive)this.s).tryAdvance(T_CONS);
                        ++this.index;
                    }
                }
            }

            @Override
            public boolean tryAdvance(T_CONS T_CONS) {
                Objects.requireNonNull(T_CONS);
                if (this.sliceOrigin >= this.fence) {
                    return false;
                }
                while (this.sliceOrigin > this.index) {
                    ((Spliterator.OfPrimitive)this.s).tryAdvance(this.emptyConsumer());
                    ++this.index;
                }
                if (this.index >= this.fence) {
                    return false;
                }
                ++this.index;
                return ((Spliterator.OfPrimitive)this.s).tryAdvance(T_CONS);
            }
        }

        static final class OfRef<T>
        extends SliceSpliterator<T, Spliterator<T>>
        implements Spliterator<T> {
            OfRef(Spliterator<T> spliterator, long l, long l2) {
                this(spliterator, l, l2, 0L, Math.min(spliterator.estimateSize(), l2));
            }

            private OfRef(Spliterator<T> spliterator, long l, long l2, long l3, long l4) {
                super(spliterator, l, l2, l3, l4);
            }

            static /* synthetic */ void lambda$forEachRemaining$1(Object object) {
            }

            static /* synthetic */ void lambda$tryAdvance$0(Object object) {
            }

            @Override
            public void forEachRemaining(Consumer<? super T> consumer) {
                Objects.requireNonNull(consumer);
                if (this.sliceOrigin >= this.fence) {
                    return;
                }
                if (this.index >= this.fence) {
                    return;
                }
                if (this.index >= this.sliceOrigin && this.index + this.s.estimateSize() <= this.sliceFence) {
                    this.s.forEachRemaining(consumer);
                    this.index = this.fence;
                } else {
                    while (this.sliceOrigin > this.index) {
                        this.s.tryAdvance(_$$Lambda$StreamSpliterators$SliceSpliterator$OfRef$NUGTWbZg9cfpPm623I8CORYtfns.INSTANCE);
                        ++this.index;
                    }
                    while (this.index < this.fence) {
                        this.s.tryAdvance(consumer);
                        ++this.index;
                    }
                }
            }

            @Override
            protected Spliterator<T> makeSpliterator(Spliterator<T> spliterator, long l, long l2, long l3, long l4) {
                return new OfRef<T>(spliterator, l, l2, l3, l4);
            }

            @Override
            public boolean tryAdvance(Consumer<? super T> consumer) {
                Objects.requireNonNull(consumer);
                if (this.sliceOrigin >= this.fence) {
                    return false;
                }
                while (this.sliceOrigin > this.index) {
                    this.s.tryAdvance(_$$Lambda$StreamSpliterators$SliceSpliterator$OfRef$WQsOrB6TN5sHvsPJU2O20DZGElU.INSTANCE);
                    ++this.index;
                }
                if (this.index >= this.fence) {
                    return false;
                }
                ++this.index;
                return this.s.tryAdvance(consumer);
            }
        }

    }

    static abstract class UnorderedSliceSpliterator<T, T_SPLITR extends Spliterator<T>> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        static final int CHUNK_SIZE = 128;
        private final AtomicLong permits;
        protected final T_SPLITR s;
        private final long skipThreshold;
        protected final boolean unlimited;

        UnorderedSliceSpliterator(T_SPLITR T_SPLITR, long l, long l2) {
            this.s = T_SPLITR;
            boolean bl = l2 < 0L;
            this.unlimited = bl;
            long l3 = l2 >= 0L ? l2 : 0L;
            this.skipThreshold = l3;
            if (l2 >= 0L) {
                l += l2;
            }
            this.permits = new AtomicLong(l);
        }

        UnorderedSliceSpliterator(T_SPLITR T_SPLITR, UnorderedSliceSpliterator<T, T_SPLITR> unorderedSliceSpliterator) {
            this.s = T_SPLITR;
            this.unlimited = unorderedSliceSpliterator.unlimited;
            this.permits = unorderedSliceSpliterator.permits;
            this.skipThreshold = unorderedSliceSpliterator.skipThreshold;
        }

        protected final long acquirePermits(long l) {
            long l2;
            long l3;
            do {
                l2 = this.permits.get();
                l3 = 0L;
                if (l2 != 0L) continue;
                if (this.unlimited) {
                    l3 = l;
                }
                return l3;
            } while ((l3 = Math.min(l2, l)) > 0L && !this.permits.compareAndSet(l2, l2 - l3));
            if (this.unlimited) {
                return Math.max(l - l3, 0L);
            }
            l = this.skipThreshold;
            if (l2 > l) {
                return Math.max(l3 - (l2 - l), 0L);
            }
            return l3;
        }

        public final int characteristics() {
            return this.s.characteristics() & -16465;
        }

        public final long estimateSize() {
            return this.s.estimateSize();
        }

        protected abstract T_SPLITR makeSpliterator(T_SPLITR var1);

        protected final PermitStatus permitStatus() {
            if (this.permits.get() > 0L) {
                return PermitStatus.MAYBE_MORE;
            }
            PermitStatus permitStatus = this.unlimited ? PermitStatus.UNLIMITED : PermitStatus.NO_MORE;
            return permitStatus;
        }

        public final T_SPLITR trySplit() {
            long l = this.permits.get();
            Spliterator<T> spliterator = null;
            if (l == 0L) {
                return null;
            }
            Spliterator<T> spliterator2 = this.s.trySplit();
            if (spliterator2 != null) {
                spliterator = this.makeSpliterator(spliterator2);
            }
            return (T_SPLITR)spliterator;
        }

        static final class OfDouble
        extends OfPrimitive<Double, DoubleConsumer, ArrayBuffer.OfDouble, Spliterator.OfDouble>
        implements Spliterator.OfDouble,
        DoubleConsumer {
            double tmpValue;

            OfDouble(Spliterator.OfDouble ofDouble, long l, long l2) {
                super(ofDouble, l, l2);
            }

            OfDouble(Spliterator.OfDouble ofDouble, OfDouble ofDouble2) {
                super(ofDouble, ofDouble2);
            }

            @Override
            public void accept(double d) {
                this.tmpValue = d;
            }

            @Override
            protected void acceptConsumed(DoubleConsumer doubleConsumer) {
                doubleConsumer.accept(this.tmpValue);
            }

            @Override
            protected ArrayBuffer.OfDouble bufferCreate(int n) {
                return new ArrayBuffer.OfDouble(n);
            }

            @Override
            protected Spliterator.OfDouble makeSpliterator(Spliterator.OfDouble ofDouble) {
                return new OfDouble(ofDouble, this);
            }
        }

        static final class OfInt
        extends OfPrimitive<Integer, IntConsumer, ArrayBuffer.OfInt, Spliterator.OfInt>
        implements Spliterator.OfInt,
        IntConsumer {
            int tmpValue;

            OfInt(Spliterator.OfInt ofInt, long l, long l2) {
                super(ofInt, l, l2);
            }

            OfInt(Spliterator.OfInt ofInt, OfInt ofInt2) {
                super(ofInt, ofInt2);
            }

            @Override
            public void accept(int n) {
                this.tmpValue = n;
            }

            @Override
            protected void acceptConsumed(IntConsumer intConsumer) {
                intConsumer.accept(this.tmpValue);
            }

            @Override
            protected ArrayBuffer.OfInt bufferCreate(int n) {
                return new ArrayBuffer.OfInt(n);
            }

            @Override
            protected Spliterator.OfInt makeSpliterator(Spliterator.OfInt ofInt) {
                return new OfInt(ofInt, this);
            }
        }

        static final class OfLong
        extends OfPrimitive<Long, LongConsumer, ArrayBuffer.OfLong, Spliterator.OfLong>
        implements Spliterator.OfLong,
        LongConsumer {
            long tmpValue;

            OfLong(Spliterator.OfLong ofLong, long l, long l2) {
                super(ofLong, l, l2);
            }

            OfLong(Spliterator.OfLong ofLong, OfLong ofLong2) {
                super(ofLong, ofLong2);
            }

            @Override
            public void accept(long l) {
                this.tmpValue = l;
            }

            @Override
            protected void acceptConsumed(LongConsumer longConsumer) {
                longConsumer.accept(this.tmpValue);
            }

            @Override
            protected ArrayBuffer.OfLong bufferCreate(int n) {
                return new ArrayBuffer.OfLong(n);
            }

            @Override
            protected Spliterator.OfLong makeSpliterator(Spliterator.OfLong ofLong) {
                return new OfLong(ofLong, this);
            }
        }

        static abstract class OfPrimitive<T, T_CONS, T_BUFF extends ArrayBuffer.OfPrimitive<T_CONS>, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>>
        extends UnorderedSliceSpliterator<T, T_SPLITR>
        implements Spliterator.OfPrimitive<T, T_CONS, T_SPLITR> {
            OfPrimitive(T_SPLITR T_SPLITR, long l, long l2) {
                super(T_SPLITR, l, l2);
            }

            OfPrimitive(T_SPLITR T_SPLITR, OfPrimitive<T, T_CONS, T_BUFF, T_SPLITR> ofPrimitive) {
                super(T_SPLITR, ofPrimitive);
            }

            protected abstract void acceptConsumed(T_CONS var1);

            protected abstract T_BUFF bufferCreate(int var1);

            @Override
            public void forEachRemaining(T_CONS T_CONS) {
                PermitStatus permitStatus;
                Objects.requireNonNull(T_CONS);
                ArrayBuffer.OfPrimitive<Object> ofPrimitive = null;
                while ((permitStatus = this.permitStatus()) != PermitStatus.NO_MORE) {
                    if (permitStatus == PermitStatus.MAYBE_MORE) {
                        long l;
                        long l2;
                        if (ofPrimitive == null) {
                            ofPrimitive = this.bufferCreate(128);
                        } else {
                            ofPrimitive.reset();
                        }
                        long l3 = 0L;
                        do {
                            l2 = l3;
                            if (!((Spliterator.OfPrimitive)this.s).tryAdvance(ofPrimitive)) break;
                            l3 = l2 = (l = 1L + l3);
                        } while (l < 128L);
                        if (l2 == 0L) {
                            return;
                        }
                        ofPrimitive.forEach(T_CONS, this.acquirePermits(l2));
                        continue;
                    }
                    ((Spliterator.OfPrimitive)this.s).forEachRemaining(T_CONS);
                    return;
                }
            }

            @Override
            public boolean tryAdvance(T_CONS T_CONS) {
                Objects.requireNonNull(T_CONS);
                while (this.permitStatus() != PermitStatus.NO_MORE) {
                    if (!((Spliterator.OfPrimitive)this.s).tryAdvance(this)) {
                        return false;
                    }
                    if (this.acquirePermits(1L) != 1L) continue;
                    this.acceptConsumed(T_CONS);
                    return true;
                }
                return false;
            }
        }

        static final class OfRef<T>
        extends UnorderedSliceSpliterator<T, Spliterator<T>>
        implements Spliterator<T>,
        Consumer<T> {
            T tmpSlot;

            OfRef(Spliterator<T> spliterator, long l, long l2) {
                super(spliterator, l, l2);
            }

            OfRef(Spliterator<T> spliterator, OfRef<T> ofRef) {
                super(spliterator, ofRef);
            }

            @Override
            public final void accept(T t) {
                this.tmpSlot = t;
            }

            @Override
            public void forEachRemaining(Consumer<? super T> consumer) {
                PermitStatus permitStatus;
                Objects.requireNonNull(consumer);
                ArrayBuffer.OfRef<T> ofRef = null;
                while ((permitStatus = this.permitStatus()) != PermitStatus.NO_MORE) {
                    if (permitStatus == PermitStatus.MAYBE_MORE) {
                        long l;
                        long l2;
                        if (ofRef == null) {
                            ofRef = new ArrayBuffer.OfRef<T>(128);
                        } else {
                            ofRef.reset();
                        }
                        long l3 = 0L;
                        do {
                            l2 = l3;
                            if (!this.s.tryAdvance(ofRef)) break;
                            l3 = l2 = (l = 1L + l3);
                        } while (l < 128L);
                        if (l2 == 0L) {
                            return;
                        }
                        ofRef.forEach(consumer, this.acquirePermits(l2));
                        continue;
                    }
                    this.s.forEachRemaining(consumer);
                    return;
                }
            }

            @Override
            protected Spliterator<T> makeSpliterator(Spliterator<T> spliterator) {
                return new OfRef<T>(spliterator, this);
            }

            @Override
            public boolean tryAdvance(Consumer<? super T> consumer) {
                Objects.requireNonNull(consumer);
                while (this.permitStatus() != PermitStatus.NO_MORE) {
                    if (!this.s.tryAdvance(this)) {
                        return false;
                    }
                    if (this.acquirePermits(1L) != 1L) continue;
                    consumer.accept(this.tmpSlot);
                    this.tmpSlot = null;
                    return true;
                }
                return false;
            }
        }

        static enum PermitStatus {
            NO_MORE,
            MAYBE_MORE,
            UNLIMITED;
            
        }

    }

    static final class WrappingSpliterator<P_IN, P_OUT>
    extends AbstractWrappingSpliterator<P_IN, P_OUT, SpinedBuffer<P_OUT>> {
        WrappingSpliterator(PipelineHelper<P_OUT> pipelineHelper, Spliterator<P_IN> spliterator, boolean bl) {
            super(pipelineHelper, spliterator, bl);
        }

        WrappingSpliterator(PipelineHelper<P_OUT> pipelineHelper, Supplier<Spliterator<P_IN>> supplier, boolean bl) {
            super(pipelineHelper, supplier, bl);
        }

        @Override
        public void forEachRemaining(Consumer<? super P_OUT> consumer) {
            if (this.buffer == null && !this.finished) {
                Objects.requireNonNull(consumer);
                this.init();
                PipelineHelper pipelineHelper = this.ph;
                Objects.requireNonNull(consumer);
                pipelineHelper.wrapAndCopyInto(new _$$Lambda$btpzqYSQDsLykCcQbI2_g5D3_zs(consumer), this.spliterator);
                this.finished = true;
            } else {
                while (this.tryAdvance(consumer)) {
                }
            }
        }

        @Override
        void initPartialTraversalState() {
            SpinedBuffer spinedBuffer;
            this.buffer = spinedBuffer = new SpinedBuffer();
            PipelineHelper pipelineHelper = this.ph;
            Objects.requireNonNull(spinedBuffer);
            this.bufferSink = pipelineHelper.wrapSink(new _$$Lambda$GF_s38TgrG6hfxe__ZFdhGp_wPw(spinedBuffer));
            this.pusher = new _$$Lambda$StreamSpliterators$WrappingSpliterator$Ky6g3CKkCccuRWAvbAL1cAsdkNk(this);
        }

        public /* synthetic */ boolean lambda$initPartialTraversalState$0$StreamSpliterators$WrappingSpliterator() {
            return this.spliterator.tryAdvance(this.bufferSink);
        }

        @Override
        public boolean tryAdvance(Consumer<? super P_OUT> consumer) {
            Objects.requireNonNull(consumer);
            boolean bl = this.doAdvance();
            if (bl) {
                consumer.accept(((SpinedBuffer)this.buffer).get(this.nextToConsume));
            }
            return bl;
        }

        WrappingSpliterator<P_IN, P_OUT> wrap(Spliterator<P_IN> spliterator) {
            return new WrappingSpliterator<P_IN, P_OUT>(this.ph, spliterator, this.isParallel);
        }
    }

}

