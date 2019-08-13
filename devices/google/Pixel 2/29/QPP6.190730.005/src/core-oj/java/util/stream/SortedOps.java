/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.AbstractPipeline;
import java.util.stream.DoublePipeline;
import java.util.stream.DoubleStream;
import java.util.stream.IntPipeline;
import java.util.stream.IntStream;
import java.util.stream.LongPipeline;
import java.util.stream.LongStream;
import java.util.stream.Node;
import java.util.stream.Nodes;
import java.util.stream.PipelineHelper;
import java.util.stream.ReferencePipeline;
import java.util.stream.Sink;
import java.util.stream.SpinedBuffer;
import java.util.stream.Stream;
import java.util.stream.StreamOpFlag;
import java.util.stream.StreamShape;
import java.util.stream._$$Lambda$Abl7XfE0Z4AgkViLas9vhsO9mjw;

final class SortedOps {
    private SortedOps() {
    }

    static <T> DoubleStream makeDouble(AbstractPipeline<?, Double, ?> abstractPipeline) {
        return new OfDouble(abstractPipeline);
    }

    static <T> IntStream makeInt(AbstractPipeline<?, Integer, ?> abstractPipeline) {
        return new OfInt(abstractPipeline);
    }

    static <T> LongStream makeLong(AbstractPipeline<?, Long, ?> abstractPipeline) {
        return new OfLong(abstractPipeline);
    }

    static <T> Stream<T> makeRef(AbstractPipeline<?, T, ?> abstractPipeline) {
        return new OfRef<T>(abstractPipeline);
    }

    static <T> Stream<T> makeRef(AbstractPipeline<?, T, ?> abstractPipeline, Comparator<? super T> comparator) {
        return new OfRef<T>(abstractPipeline, comparator);
    }

    private static abstract class AbstractDoubleSortingSink
    extends Sink.ChainedDouble<Double> {
        protected boolean cancellationWasRequested;

        AbstractDoubleSortingSink(Sink<? super Double> sink) {
            super(sink);
        }

        @Override
        public final boolean cancellationRequested() {
            this.cancellationWasRequested = true;
            return false;
        }
    }

    private static abstract class AbstractIntSortingSink
    extends Sink.ChainedInt<Integer> {
        protected boolean cancellationWasRequested;

        AbstractIntSortingSink(Sink<? super Integer> sink) {
            super(sink);
        }

        @Override
        public final boolean cancellationRequested() {
            this.cancellationWasRequested = true;
            return false;
        }
    }

    private static abstract class AbstractLongSortingSink
    extends Sink.ChainedLong<Long> {
        protected boolean cancellationWasRequested;

        AbstractLongSortingSink(Sink<? super Long> sink) {
            super(sink);
        }

        @Override
        public final boolean cancellationRequested() {
            this.cancellationWasRequested = true;
            return false;
        }
    }

    private static abstract class AbstractRefSortingSink<T>
    extends Sink.ChainedReference<T, T> {
        protected boolean cancellationWasRequested;
        protected final Comparator<? super T> comparator;

        AbstractRefSortingSink(Sink<? super T> sink, Comparator<? super T> comparator) {
            super(sink);
            this.comparator = comparator;
        }

        @Override
        public final boolean cancellationRequested() {
            this.cancellationWasRequested = true;
            return false;
        }
    }

    private static final class DoubleSortingSink
    extends AbstractDoubleSortingSink {
        private SpinedBuffer.OfDouble b;

        DoubleSortingSink(Sink<? super Double> sink) {
            super(sink);
        }

        @Override
        public void accept(double d) {
            this.b.accept(d);
        }

        @Override
        public void begin(long l) {
            if (l < 0x7FFFFFF7L) {
                SpinedBuffer.OfDouble ofDouble = l > 0L ? new SpinedBuffer.OfDouble((int)l) : new SpinedBuffer.OfDouble();
                this.b = ofDouble;
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override
        public void end() {
            double[] arrd = (double[])this.b.asPrimitiveArray();
            Arrays.sort(arrd);
            this.downstream.begin(arrd.length);
            boolean bl = this.cancellationWasRequested;
            int n = 0;
            if (!bl) {
                for (double d : arrd) {
                    this.downstream.accept(d);
                }
            } else {
                int n2 = arrd.length;
                for (int i = n; i < n2; ++i) {
                    double d = arrd[i];
                    if (!this.downstream.cancellationRequested()) {
                        this.downstream.accept(d);
                        continue;
                    }
                    break;
                }
            }
            this.downstream.end();
        }
    }

    private static final class IntSortingSink
    extends AbstractIntSortingSink {
        private SpinedBuffer.OfInt b;

        IntSortingSink(Sink<? super Integer> sink) {
            super(sink);
        }

        @Override
        public void accept(int n) {
            this.b.accept(n);
        }

        @Override
        public void begin(long l) {
            if (l < 0x7FFFFFF7L) {
                SpinedBuffer.OfInt ofInt = l > 0L ? new SpinedBuffer.OfInt((int)l) : new SpinedBuffer.OfInt();
                this.b = ofInt;
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override
        public void end() {
            int[] arrn = (int[])this.b.asPrimitiveArray();
            Arrays.sort(arrn);
            this.downstream.begin(arrn.length);
            boolean bl = this.cancellationWasRequested;
            int n = 0;
            if (!bl) {
                for (int n2 : arrn) {
                    this.downstream.accept(n2);
                }
            } else {
                int n3 = arrn.length;
                for (int i = n; i < n3; ++i) {
                    n = arrn[i];
                    if (!this.downstream.cancellationRequested()) {
                        this.downstream.accept(n);
                        continue;
                    }
                    break;
                }
            }
            this.downstream.end();
        }
    }

    private static final class LongSortingSink
    extends AbstractLongSortingSink {
        private SpinedBuffer.OfLong b;

        LongSortingSink(Sink<? super Long> sink) {
            super(sink);
        }

        @Override
        public void accept(long l) {
            this.b.accept(l);
        }

        @Override
        public void begin(long l) {
            if (l < 0x7FFFFFF7L) {
                SpinedBuffer.OfLong ofLong = l > 0L ? new SpinedBuffer.OfLong((int)l) : new SpinedBuffer.OfLong();
                this.b = ofLong;
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override
        public void end() {
            long[] arrl = (long[])this.b.asPrimitiveArray();
            Arrays.sort(arrl);
            this.downstream.begin(arrl.length);
            boolean bl = this.cancellationWasRequested;
            int n = 0;
            if (!bl) {
                for (long l : arrl) {
                    this.downstream.accept(l);
                }
            } else {
                int n2 = arrl.length;
                for (int i = n; i < n2; ++i) {
                    long l = arrl[i];
                    if (!this.downstream.cancellationRequested()) {
                        this.downstream.accept(l);
                        continue;
                    }
                    break;
                }
            }
            this.downstream.end();
        }
    }

    private static final class OfDouble
    extends DoublePipeline.StatefulOp<Double> {
        OfDouble(AbstractPipeline<?, Double, ?> abstractPipeline) {
            super(abstractPipeline, StreamShape.DOUBLE_VALUE, StreamOpFlag.IS_ORDERED | StreamOpFlag.IS_SORTED);
        }

        @Override
        public <P_IN> Node<Double> opEvaluateParallel(PipelineHelper<Double> arrd, Spliterator<P_IN> spliterator, IntFunction<Double[]> intFunction) {
            if (StreamOpFlag.SORTED.isKnown(arrd.getStreamAndOpFlags())) {
                return arrd.evaluate(spliterator, false, intFunction);
            }
            arrd = (double[])((Node.OfDouble)arrd.evaluate(spliterator, true, intFunction)).asPrimitiveArray();
            Arrays.parallelSort(arrd);
            return Nodes.node(arrd);
        }

        @Override
        public Sink<Double> opWrapSink(int n, Sink<Double> sink) {
            Objects.requireNonNull(sink);
            if (StreamOpFlag.SORTED.isKnown(n)) {
                return sink;
            }
            if (StreamOpFlag.SIZED.isKnown(n)) {
                return new SizedDoubleSortingSink(sink);
            }
            return new DoubleSortingSink(sink);
        }
    }

    private static final class OfInt
    extends IntPipeline.StatefulOp<Integer> {
        OfInt(AbstractPipeline<?, Integer, ?> abstractPipeline) {
            super(abstractPipeline, StreamShape.INT_VALUE, StreamOpFlag.IS_ORDERED | StreamOpFlag.IS_SORTED);
        }

        @Override
        public <P_IN> Node<Integer> opEvaluateParallel(PipelineHelper<Integer> arrn, Spliterator<P_IN> spliterator, IntFunction<Integer[]> intFunction) {
            if (StreamOpFlag.SORTED.isKnown(arrn.getStreamAndOpFlags())) {
                return arrn.evaluate(spliterator, false, intFunction);
            }
            arrn = (int[])((Node.OfInt)arrn.evaluate(spliterator, true, intFunction)).asPrimitiveArray();
            Arrays.parallelSort(arrn);
            return Nodes.node(arrn);
        }

        @Override
        public Sink<Integer> opWrapSink(int n, Sink<Integer> sink) {
            Objects.requireNonNull(sink);
            if (StreamOpFlag.SORTED.isKnown(n)) {
                return sink;
            }
            if (StreamOpFlag.SIZED.isKnown(n)) {
                return new SizedIntSortingSink(sink);
            }
            return new IntSortingSink(sink);
        }
    }

    private static final class OfLong
    extends LongPipeline.StatefulOp<Long> {
        OfLong(AbstractPipeline<?, Long, ?> abstractPipeline) {
            super(abstractPipeline, StreamShape.LONG_VALUE, StreamOpFlag.IS_ORDERED | StreamOpFlag.IS_SORTED);
        }

        @Override
        public <P_IN> Node<Long> opEvaluateParallel(PipelineHelper<Long> arrl, Spliterator<P_IN> spliterator, IntFunction<Long[]> intFunction) {
            if (StreamOpFlag.SORTED.isKnown(arrl.getStreamAndOpFlags())) {
                return arrl.evaluate(spliterator, false, intFunction);
            }
            arrl = (long[])((Node.OfLong)arrl.evaluate(spliterator, true, intFunction)).asPrimitiveArray();
            Arrays.parallelSort(arrl);
            return Nodes.node(arrl);
        }

        @Override
        public Sink<Long> opWrapSink(int n, Sink<Long> sink) {
            Objects.requireNonNull(sink);
            if (StreamOpFlag.SORTED.isKnown(n)) {
                return sink;
            }
            if (StreamOpFlag.SIZED.isKnown(n)) {
                return new SizedLongSortingSink(sink);
            }
            return new LongSortingSink(sink);
        }
    }

    private static final class OfRef<T>
    extends ReferencePipeline.StatefulOp<T, T> {
        private final Comparator<? super T> comparator;
        private final boolean isNaturalSort;

        OfRef(AbstractPipeline<?, T, ?> abstractPipeline) {
            super(abstractPipeline, StreamShape.REFERENCE, StreamOpFlag.IS_ORDERED | StreamOpFlag.IS_SORTED);
            this.isNaturalSort = true;
            this.comparator = Comparator.naturalOrder();
        }

        OfRef(AbstractPipeline<?, T, ?> abstractPipeline, Comparator<? super T> comparator) {
            super(abstractPipeline, StreamShape.REFERENCE, StreamOpFlag.IS_ORDERED | StreamOpFlag.NOT_SORTED);
            this.isNaturalSort = false;
            this.comparator = Objects.requireNonNull(comparator);
        }

        @Override
        public <P_IN> Node<T> opEvaluateParallel(PipelineHelper<T> arrT, Spliterator<P_IN> spliterator, IntFunction<T[]> intFunction) {
            if (StreamOpFlag.SORTED.isKnown(arrT.getStreamAndOpFlags()) && this.isNaturalSort) {
                return arrT.evaluate(spliterator, false, intFunction);
            }
            arrT = arrT.evaluate(spliterator, true, intFunction).asArray(intFunction);
            Arrays.parallelSort(arrT, this.comparator);
            return Nodes.node(arrT);
        }

        @Override
        public Sink<T> opWrapSink(int n, Sink<T> sink) {
            Objects.requireNonNull(sink);
            if (StreamOpFlag.SORTED.isKnown(n) && this.isNaturalSort) {
                return sink;
            }
            if (StreamOpFlag.SIZED.isKnown(n)) {
                return new SizedRefSortingSink<T>(sink, this.comparator);
            }
            return new RefSortingSink<T>(sink, this.comparator);
        }
    }

    private static final class RefSortingSink<T>
    extends AbstractRefSortingSink<T> {
        private ArrayList<T> list;

        RefSortingSink(Sink<? super T> sink, Comparator<? super T> comparator) {
            super(sink, comparator);
        }

        @Override
        public void accept(T t) {
            this.list.add(t);
        }

        @Override
        public void begin(long l) {
            if (l < 0x7FFFFFF7L) {
                ArrayList arrayList = l >= 0L ? new ArrayList((int)l) : new ArrayList();
                this.list = arrayList;
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override
        public void end() {
            this.list.sort(this.comparator);
            this.downstream.begin(this.list.size());
            if (!this.cancellationWasRequested) {
                ArrayList<T> arrayList = this.list;
                Sink sink = this.downstream;
                Objects.requireNonNull(sink);
                arrayList.forEach(new _$$Lambda$Abl7XfE0Z4AgkViLas9vhsO9mjw(sink));
            } else {
                for (T t : this.list) {
                    if (this.downstream.cancellationRequested()) break;
                    this.downstream.accept(t);
                }
            }
            this.downstream.end();
            this.list = null;
        }
    }

    private static final class SizedDoubleSortingSink
    extends AbstractDoubleSortingSink {
        private double[] array;
        private int offset;

        SizedDoubleSortingSink(Sink<? super Double> sink) {
            super(sink);
        }

        @Override
        public void accept(double d) {
            double[] arrd = this.array;
            int n = this.offset;
            this.offset = n + 1;
            arrd[n] = d;
        }

        @Override
        public void begin(long l) {
            if (l < 0x7FFFFFF7L) {
                this.array = new double[(int)l];
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override
        public void end() {
            Arrays.sort(this.array, 0, this.offset);
            this.downstream.begin(this.offset);
            if (!this.cancellationWasRequested) {
                for (int i = 0; i < this.offset; ++i) {
                    this.downstream.accept(this.array[i]);
                }
            } else {
                for (int i = 0; i < this.offset && !this.downstream.cancellationRequested(); ++i) {
                    this.downstream.accept(this.array[i]);
                }
            }
            this.downstream.end();
            this.array = null;
        }
    }

    private static final class SizedIntSortingSink
    extends AbstractIntSortingSink {
        private int[] array;
        private int offset;

        SizedIntSortingSink(Sink<? super Integer> sink) {
            super(sink);
        }

        @Override
        public void accept(int n) {
            int[] arrn = this.array;
            int n2 = this.offset;
            this.offset = n2 + 1;
            arrn[n2] = n;
        }

        @Override
        public void begin(long l) {
            if (l < 0x7FFFFFF7L) {
                this.array = new int[(int)l];
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override
        public void end() {
            Arrays.sort(this.array, 0, this.offset);
            this.downstream.begin(this.offset);
            if (!this.cancellationWasRequested) {
                for (int i = 0; i < this.offset; ++i) {
                    this.downstream.accept(this.array[i]);
                }
            } else {
                for (int i = 0; i < this.offset && !this.downstream.cancellationRequested(); ++i) {
                    this.downstream.accept(this.array[i]);
                }
            }
            this.downstream.end();
            this.array = null;
        }
    }

    private static final class SizedLongSortingSink
    extends AbstractLongSortingSink {
        private long[] array;
        private int offset;

        SizedLongSortingSink(Sink<? super Long> sink) {
            super(sink);
        }

        @Override
        public void accept(long l) {
            long[] arrl = this.array;
            int n = this.offset;
            this.offset = n + 1;
            arrl[n] = l;
        }

        @Override
        public void begin(long l) {
            if (l < 0x7FFFFFF7L) {
                this.array = new long[(int)l];
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override
        public void end() {
            Arrays.sort(this.array, 0, this.offset);
            this.downstream.begin(this.offset);
            if (!this.cancellationWasRequested) {
                for (int i = 0; i < this.offset; ++i) {
                    this.downstream.accept(this.array[i]);
                }
            } else {
                for (int i = 0; i < this.offset && !this.downstream.cancellationRequested(); ++i) {
                    this.downstream.accept(this.array[i]);
                }
            }
            this.downstream.end();
            this.array = null;
        }
    }

    private static final class SizedRefSortingSink<T>
    extends AbstractRefSortingSink<T> {
        private T[] array;
        private int offset;

        SizedRefSortingSink(Sink<? super T> sink, Comparator<? super T> comparator) {
            super(sink, comparator);
        }

        @Override
        public void accept(T t) {
            T[] arrT = this.array;
            int n = this.offset;
            this.offset = n + 1;
            arrT[n] = t;
        }

        @Override
        public void begin(long l) {
            if (l < 0x7FFFFFF7L) {
                this.array = new Object[(int)l];
                return;
            }
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }

        @Override
        public void end() {
            Arrays.sort(this.array, 0, this.offset, this.comparator);
            this.downstream.begin(this.offset);
            if (!this.cancellationWasRequested) {
                for (int i = 0; i < this.offset; ++i) {
                    this.downstream.accept(this.array[i]);
                }
            } else {
                for (int i = 0; i < this.offset && !this.downstream.cancellationRequested(); ++i) {
                    this.downstream.accept(this.array[i]);
                }
            }
            this.downstream.end();
            this.array = null;
        }
    }

}

