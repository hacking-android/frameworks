/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$SliceOps
 *  java.util.stream.-$$Lambda$SliceOps$2
 *  java.util.stream.-$$Lambda$SliceOps$2$pJKvYyBs7HGPiOPTm_fxpciSsG8
 *  java.util.stream.-$$Lambda$SliceOps$3
 *  java.util.stream.-$$Lambda$SliceOps$3$iKJ8R9VMhJpW3rzcr1q-11o2TH4
 *  java.util.stream.-$$Lambda$SliceOps$4
 *  java.util.stream.-$$Lambda$SliceOps$4$JdMLhF4N5dBS3gGxMct4lK2SQ04
 *  java.util.stream.-$$Lambda$SliceOps$T0eS2B9nWeCpmA7G2QlMnW3G2UA
 */
package java.util.stream;

import java.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.function.IntFunction;
import java.util.stream.-$;
import java.util.stream.AbstractPipeline;
import java.util.stream.AbstractShortCircuitTask;
import java.util.stream.AbstractTask;
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
import java.util.stream.Stream;
import java.util.stream.StreamOpFlag;
import java.util.stream.StreamShape;
import java.util.stream.StreamSpliterators;
import java.util.stream._$$Lambda$SliceOps$2$pJKvYyBs7HGPiOPTm_fxpciSsG8;
import java.util.stream._$$Lambda$SliceOps$3$iKJ8R9VMhJpW3rzcr1q_11o2TH4;
import java.util.stream._$$Lambda$SliceOps$4$JdMLhF4N5dBS3gGxMct4lK2SQ04;
import java.util.stream._$$Lambda$SliceOps$T0eS2B9nWeCpmA7G2QlMnW3G2UA;

final class SliceOps {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private SliceOps() {
    }

    private static long calcSize(long l, long l2, long l3) {
        long l4 = -1L;
        if (l >= 0L) {
            l4 = Math.max(-1L, Math.min(l - l2, l3));
        }
        return l4;
    }

    private static long calcSliceFence(long l, long l2) {
        long l3 = Long.MAX_VALUE;
        l = l2 >= 0L ? (l += l2) : Long.MAX_VALUE;
        l2 = l3;
        if (l >= 0L) {
            l2 = l;
        }
        return l2;
    }

    private static <T> IntFunction<T[]> castingArray() {
        return _$$Lambda$SliceOps$T0eS2B9nWeCpmA7G2QlMnW3G2UA.INSTANCE;
    }

    private static int flags(long l) {
        int n = StreamOpFlag.NOT_SIZED;
        int n2 = l != -1L ? StreamOpFlag.IS_SHORT_CIRCUIT : 0;
        return n | n2;
    }

    static /* synthetic */ Object[] lambda$castingArray$0(int n) {
        return new Object[n];
    }

    public static DoubleStream makeDouble(AbstractPipeline<?, Double, ?> object, final long l, final long l2) {
        if (l >= 0L) {
            return new DoublePipeline.StatefulOp<Double>(object, StreamShape.DOUBLE_VALUE, SliceOps.flags(l2)){

                static /* synthetic */ Double[] lambda$opEvaluateParallelLazy$0(int n) {
                    return new Double[n];
                }

                @Override
                public <P_IN> Node<Double> opEvaluateParallel(PipelineHelper<Double> pipelineHelper, Spliterator<P_IN> spliterator, IntFunction<Double[]> intFunction) {
                    long l3 = pipelineHelper.exactOutputSizeIfKnown(spliterator);
                    if (l3 > 0L && spliterator.hasCharacteristics(16384)) {
                        return Nodes.collectDouble(pipelineHelper, SliceOps.sliceSpliterator(pipelineHelper.getSourceShape(), spliterator, l, l2), true);
                    }
                    if (!StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                        return Nodes.collectDouble(this, this.unorderedSkipLimitSpliterator((Spliterator.OfDouble)pipelineHelper.wrapSpliterator(spliterator), l, l2, l3), true);
                    }
                    return (Node)new SliceTask<P_IN, Double>(this, pipelineHelper, spliterator, intFunction, l, l2).invoke();
                }

                @Override
                public <P_IN> Spliterator<Double> opEvaluateParallelLazy(PipelineHelper<Double> object, Spliterator<P_IN> spliterator) {
                    long l3 = ((PipelineHelper)object).exactOutputSizeIfKnown(spliterator);
                    if (l3 > 0L && spliterator.hasCharacteristics(16384)) {
                        object = (Spliterator.OfDouble)((PipelineHelper)object).wrapSpliterator(spliterator);
                        l3 = l;
                        return new StreamSpliterators.SliceSpliterator.OfDouble((Spliterator.OfDouble)object, l3, SliceOps.calcSliceFence(l3, l2));
                    }
                    if (!StreamOpFlag.ORDERED.isKnown(((PipelineHelper)object).getStreamAndOpFlags())) {
                        return this.unorderedSkipLimitSpliterator((Spliterator.OfDouble)((PipelineHelper)object).wrapSpliterator(spliterator), l, l2, l3);
                    }
                    return ((Node)new SliceTask<P_IN, Double>(this, (PipelineHelper<Double>)object, spliterator, (IntFunction<P_OUT[]>)_$$Lambda$SliceOps$4$JdMLhF4N5dBS3gGxMct4lK2SQ04.INSTANCE, l, l2).invoke()).spliterator();
                }

                @Override
                public Sink<Double> opWrapSink(int n, Sink<Double> sink) {
                    return new Sink.ChainedDouble<Double>(sink){
                        long m;
                        long n;
                        {
                            super(sink);
                            this.n = l;
                            long l = l2 >= 0L ? l2 : Long.MAX_VALUE;
                            this.m = l;
                        }

                        @Override
                        public void accept(double d) {
                            long l = this.n;
                            if (l == 0L) {
                                l = this.m;
                                if (l > 0L) {
                                    this.m = l - 1L;
                                    this.downstream.accept(d);
                                }
                            } else {
                                this.n = l - 1L;
                            }
                        }

                        @Override
                        public void begin(long l) {
                            this.downstream.begin(SliceOps.calcSize(l, l, this.m));
                        }

                        @Override
                        public boolean cancellationRequested() {
                            boolean bl = this.m == 0L || this.downstream.cancellationRequested();
                            return bl;
                        }
                    };
                }

                Spliterator.OfDouble unorderedSkipLimitSpliterator(Spliterator.OfDouble ofDouble, long l6, long l22, long l3) {
                    long l4 = l6;
                    long l5 = l22;
                    if (l6 <= l3) {
                        l6 = l22 >= 0L ? Math.min(l22, l3 - l6) : l3 - l6;
                        l4 = 0L;
                        l5 = l6;
                    }
                    return new StreamSpliterators.UnorderedSliceSpliterator.OfDouble(ofDouble, l4, l5);
                }

            };
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Skip must be non-negative: ");
        ((StringBuilder)object).append(l);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static IntStream makeInt(AbstractPipeline<?, Integer, ?> object, final long l, final long l2) {
        if (l >= 0L) {
            return new IntPipeline.StatefulOp<Integer>(object, StreamShape.INT_VALUE, SliceOps.flags(l2)){

                static /* synthetic */ Integer[] lambda$opEvaluateParallelLazy$0(int n) {
                    return new Integer[n];
                }

                @Override
                public <P_IN> Node<Integer> opEvaluateParallel(PipelineHelper<Integer> pipelineHelper, Spliterator<P_IN> spliterator, IntFunction<Integer[]> intFunction) {
                    long l3 = pipelineHelper.exactOutputSizeIfKnown(spliterator);
                    if (l3 > 0L && spliterator.hasCharacteristics(16384)) {
                        return Nodes.collectInt(pipelineHelper, SliceOps.sliceSpliterator(pipelineHelper.getSourceShape(), spliterator, l, l2), true);
                    }
                    if (!StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                        return Nodes.collectInt(this, this.unorderedSkipLimitSpliterator((Spliterator.OfInt)pipelineHelper.wrapSpliterator(spliterator), l, l2, l3), true);
                    }
                    return (Node)new SliceTask<P_IN, Integer>(this, pipelineHelper, spliterator, intFunction, l, l2).invoke();
                }

                @Override
                public <P_IN> Spliterator<Integer> opEvaluateParallelLazy(PipelineHelper<Integer> object, Spliterator<P_IN> spliterator) {
                    long l3 = ((PipelineHelper)object).exactOutputSizeIfKnown(spliterator);
                    if (l3 > 0L && spliterator.hasCharacteristics(16384)) {
                        object = (Spliterator.OfInt)((PipelineHelper)object).wrapSpliterator(spliterator);
                        l3 = l;
                        return new StreamSpliterators.SliceSpliterator.OfInt((Spliterator.OfInt)object, l3, SliceOps.calcSliceFence(l3, l2));
                    }
                    if (!StreamOpFlag.ORDERED.isKnown(((PipelineHelper)object).getStreamAndOpFlags())) {
                        return this.unorderedSkipLimitSpliterator((Spliterator.OfInt)((PipelineHelper)object).wrapSpliterator(spliterator), l, l2, l3);
                    }
                    return ((Node)new SliceTask<P_IN, Integer>(this, (PipelineHelper<Integer>)object, spliterator, (IntFunction<P_OUT[]>)_$$Lambda$SliceOps$2$pJKvYyBs7HGPiOPTm_fxpciSsG8.INSTANCE, l, l2).invoke()).spliterator();
                }

                @Override
                public Sink<Integer> opWrapSink(int n, Sink<Integer> sink) {
                    return new Sink.ChainedInt<Integer>(sink){
                        long m;
                        long n;
                        {
                            super(sink);
                            this.n = l;
                            long l = l2 >= 0L ? l2 : Long.MAX_VALUE;
                            this.m = l;
                        }

                        @Override
                        public void accept(int n) {
                            long l = this.n;
                            if (l == 0L) {
                                l = this.m;
                                if (l > 0L) {
                                    this.m = l - 1L;
                                    this.downstream.accept(n);
                                }
                            } else {
                                this.n = l - 1L;
                            }
                        }

                        @Override
                        public void begin(long l) {
                            this.downstream.begin(SliceOps.calcSize(l, l, this.m));
                        }

                        @Override
                        public boolean cancellationRequested() {
                            boolean bl = this.m == 0L || this.downstream.cancellationRequested();
                            return bl;
                        }
                    };
                }

                Spliterator.OfInt unorderedSkipLimitSpliterator(Spliterator.OfInt ofInt, long l6, long l22, long l3) {
                    long l4 = l6;
                    long l5 = l22;
                    if (l6 <= l3) {
                        l6 = l22 >= 0L ? Math.min(l22, l3 - l6) : l3 - l6;
                        l4 = 0L;
                        l5 = l6;
                    }
                    return new StreamSpliterators.UnorderedSliceSpliterator.OfInt(ofInt, l4, l5);
                }

            };
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Skip must be non-negative: ");
        ((StringBuilder)object).append(l);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static LongStream makeLong(AbstractPipeline<?, Long, ?> object, final long l, final long l2) {
        if (l >= 0L) {
            return new LongPipeline.StatefulOp<Long>(object, StreamShape.LONG_VALUE, SliceOps.flags(l2)){

                static /* synthetic */ Long[] lambda$opEvaluateParallelLazy$0(int n) {
                    return new Long[n];
                }

                @Override
                public <P_IN> Node<Long> opEvaluateParallel(PipelineHelper<Long> pipelineHelper, Spliterator<P_IN> spliterator, IntFunction<Long[]> intFunction) {
                    long l3 = pipelineHelper.exactOutputSizeIfKnown(spliterator);
                    if (l3 > 0L && spliterator.hasCharacteristics(16384)) {
                        return Nodes.collectLong(pipelineHelper, SliceOps.sliceSpliterator(pipelineHelper.getSourceShape(), spliterator, l, l2), true);
                    }
                    if (!StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                        return Nodes.collectLong(this, this.unorderedSkipLimitSpliterator((Spliterator.OfLong)pipelineHelper.wrapSpliterator(spliterator), l, l2, l3), true);
                    }
                    return (Node)new SliceTask<P_IN, Long>(this, pipelineHelper, spliterator, intFunction, l, l2).invoke();
                }

                @Override
                public <P_IN> Spliterator<Long> opEvaluateParallelLazy(PipelineHelper<Long> object, Spliterator<P_IN> spliterator) {
                    long l3 = ((PipelineHelper)object).exactOutputSizeIfKnown(spliterator);
                    if (l3 > 0L && spliterator.hasCharacteristics(16384)) {
                        object = (Spliterator.OfLong)((PipelineHelper)object).wrapSpliterator(spliterator);
                        l3 = l;
                        return new StreamSpliterators.SliceSpliterator.OfLong((Spliterator.OfLong)object, l3, SliceOps.calcSliceFence(l3, l2));
                    }
                    if (!StreamOpFlag.ORDERED.isKnown(((PipelineHelper)object).getStreamAndOpFlags())) {
                        return this.unorderedSkipLimitSpliterator((Spliterator.OfLong)((PipelineHelper)object).wrapSpliterator(spliterator), l, l2, l3);
                    }
                    return ((Node)new SliceTask<P_IN, Long>(this, (PipelineHelper<Long>)object, spliterator, (IntFunction<P_OUT[]>)_$$Lambda$SliceOps$3$iKJ8R9VMhJpW3rzcr1q_11o2TH4.INSTANCE, l, l2).invoke()).spliterator();
                }

                @Override
                public Sink<Long> opWrapSink(int n, Sink<Long> sink) {
                    return new Sink.ChainedLong<Long>(sink){
                        long m;
                        long n;
                        {
                            super(sink);
                            this.n = l;
                            long l = l2 >= 0L ? l2 : Long.MAX_VALUE;
                            this.m = l;
                        }

                        @Override
                        public void accept(long l) {
                            long l2 = this.n;
                            if (l2 == 0L) {
                                l2 = this.m;
                                if (l2 > 0L) {
                                    this.m = l2 - 1L;
                                    this.downstream.accept(l);
                                }
                            } else {
                                this.n = l2 - 1L;
                            }
                        }

                        @Override
                        public void begin(long l) {
                            this.downstream.begin(SliceOps.calcSize(l, l, this.m));
                        }

                        @Override
                        public boolean cancellationRequested() {
                            boolean bl = this.m == 0L || this.downstream.cancellationRequested();
                            return bl;
                        }
                    };
                }

                Spliterator.OfLong unorderedSkipLimitSpliterator(Spliterator.OfLong ofLong, long l6, long l22, long l3) {
                    long l4 = l6;
                    long l5 = l22;
                    if (l6 <= l3) {
                        l6 = l22 >= 0L ? Math.min(l22, l3 - l6) : l3 - l6;
                        l4 = 0L;
                        l5 = l6;
                    }
                    return new StreamSpliterators.UnorderedSliceSpliterator.OfLong(ofLong, l4, l5);
                }

            };
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Skip must be non-negative: ");
        ((StringBuilder)object).append(l);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static <T> Stream<T> makeRef(AbstractPipeline<?, T, ?> object, final long l, final long l2) {
        if (l >= 0L) {
            return new ReferencePipeline.StatefulOp<T, T>((AbstractPipeline)object, StreamShape.REFERENCE, SliceOps.flags(l2)){

                @Override
                public <P_IN> Node<T> opEvaluateParallel(PipelineHelper<T> pipelineHelper, Spliterator<P_IN> spliterator, IntFunction<T[]> intFunction) {
                    long l3 = pipelineHelper.exactOutputSizeIfKnown(spliterator);
                    if (l3 > 0L && spliterator.hasCharacteristics(16384)) {
                        return Nodes.collect(pipelineHelper, SliceOps.sliceSpliterator(pipelineHelper.getSourceShape(), spliterator, l, l2), true, intFunction);
                    }
                    if (!StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags())) {
                        return Nodes.collect(this, this.unorderedSkipLimitSpliterator(pipelineHelper.wrapSpliterator(spliterator), l, l2, l3), true, intFunction);
                    }
                    return (Node)new SliceTask<P_IN, T>(this, pipelineHelper, spliterator, intFunction, l, l2).invoke();
                }

                @Override
                public <P_IN> Spliterator<T> opEvaluateParallelLazy(PipelineHelper<T> object, Spliterator<P_IN> spliterator) {
                    long l3 = ((PipelineHelper)object).exactOutputSizeIfKnown(spliterator);
                    if (l3 > 0L && spliterator.hasCharacteristics(16384)) {
                        object = ((PipelineHelper)object).wrapSpliterator(spliterator);
                        l3 = l;
                        return new StreamSpliterators.SliceSpliterator.OfRef(object, l3, SliceOps.calcSliceFence(l3, l2));
                    }
                    if (!StreamOpFlag.ORDERED.isKnown(((PipelineHelper)object).getStreamAndOpFlags())) {
                        return this.unorderedSkipLimitSpliterator(((PipelineHelper)object).wrapSpliterator(spliterator), l, l2, l3);
                    }
                    return ((Node)new SliceTask(this, object, spliterator, SliceOps.castingArray(), l, l2).invoke()).spliterator();
                }

                @Override
                public Sink<T> opWrapSink(int n, Sink<T> sink) {
                    return new Sink.ChainedReference<T, T>(sink){
                        long m;
                        long n;
                        {
                            super(sink);
                            this.n = l;
                            long l = l2 >= 0L ? l2 : Long.MAX_VALUE;
                            this.m = l;
                        }

                        @Override
                        public void accept(T t) {
                            long l = this.n;
                            if (l == 0L) {
                                l = this.m;
                                if (l > 0L) {
                                    this.m = l - 1L;
                                    this.downstream.accept(t);
                                }
                            } else {
                                this.n = l - 1L;
                            }
                        }

                        @Override
                        public void begin(long l) {
                            this.downstream.begin(SliceOps.calcSize(l, l, this.m));
                        }

                        @Override
                        public boolean cancellationRequested() {
                            boolean bl = this.m == 0L || this.downstream.cancellationRequested();
                            return bl;
                        }
                    };
                }

                Spliterator<T> unorderedSkipLimitSpliterator(Spliterator<T> spliterator, long l6, long l22, long l3) {
                    long l4 = l6;
                    long l5 = l22;
                    if (l6 <= l3) {
                        l6 = l22 >= 0L ? Math.min(l22, l3 - l6) : l3 - l6;
                        l4 = 0L;
                        l5 = l6;
                    }
                    return new StreamSpliterators.UnorderedSliceSpliterator.OfRef<T>(spliterator, l4, l5);
                }

            };
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Skip must be non-negative: ");
        ((StringBuilder)object).append(l);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private static <P_IN> Spliterator<P_IN> sliceSpliterator(StreamShape streamShape, Spliterator<P_IN> object, long l, long l2) {
        l2 = SliceOps.calcSliceFence(l, l2);
        int n = 5.$SwitchMap$java$util$stream$StreamShape[streamShape.ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n == 4) {
                        return new StreamSpliterators.SliceSpliterator.OfDouble((Spliterator.OfDouble)object, l, l2);
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown shape ");
                    ((StringBuilder)object).append((Object)streamShape);
                    throw new IllegalStateException(((StringBuilder)object).toString());
                }
                return new StreamSpliterators.SliceSpliterator.OfLong((Spliterator.OfLong)object, l, l2);
            }
            return new StreamSpliterators.SliceSpliterator.OfInt((Spliterator.OfInt)object, l, l2);
        }
        return new StreamSpliterators.SliceSpliterator.OfRef<P_IN>((Spliterator<P_IN>)object, l, l2);
    }

    private static final class SliceTask<P_IN, P_OUT>
    extends AbstractShortCircuitTask<P_IN, P_OUT, Node<P_OUT>, SliceTask<P_IN, P_OUT>> {
        private volatile boolean completed;
        private final IntFunction<P_OUT[]> generator;
        private final AbstractPipeline<P_OUT, P_OUT, ?> op;
        private final long targetOffset;
        private final long targetSize;
        private long thisNodeSize;

        SliceTask(AbstractPipeline<P_OUT, P_OUT, ?> abstractPipeline, PipelineHelper<P_OUT> pipelineHelper, Spliterator<P_IN> spliterator, IntFunction<P_OUT[]> intFunction, long l, long l2) {
            super(pipelineHelper, spliterator);
            this.op = abstractPipeline;
            this.generator = intFunction;
            this.targetOffset = l;
            this.targetSize = l2;
        }

        SliceTask(SliceTask<P_IN, P_OUT> sliceTask, Spliterator<P_IN> spliterator) {
            super(sliceTask, spliterator);
            this.op = sliceTask.op;
            this.generator = sliceTask.generator;
            this.targetOffset = sliceTask.targetOffset;
            this.targetSize = sliceTask.targetSize;
        }

        private long completedSize(long l) {
            if (this.completed) {
                return this.thisNodeSize;
            }
            SliceTask sliceTask = (SliceTask)this.leftChild;
            SliceTask sliceTask2 = (SliceTask)this.rightChild;
            if (sliceTask != null && sliceTask2 != null) {
                long l2 = sliceTask.completedSize(l);
                l = l2 >= l ? l2 : sliceTask2.completedSize(l) + l2;
                return l;
            }
            return this.thisNodeSize;
        }

        private Node<P_OUT> doTruncate(Node<P_OUT> node) {
            long l = this.targetSize >= 0L ? Math.min(node.count(), this.targetOffset + this.targetSize) : this.thisNodeSize;
            return node.truncate(this.targetOffset, l, this.generator);
        }

        private boolean isLeftCompleted(long l) {
            long l2 = this.completed ? this.thisNodeSize : this.completedSize(l);
            boolean bl = true;
            if (l2 >= l) {
                return true;
            }
            SliceTask sliceTask = this;
            long l3 = l2;
            for (SliceTask sliceTask2 = (SliceTask)this.getParent(); sliceTask2 != null; sliceTask2 = (SliceTask)sliceTask2.getParent()) {
                l2 = l3;
                if (sliceTask == sliceTask2.rightChild) {
                    sliceTask = (SliceTask)sliceTask2.leftChild;
                    l2 = l3;
                    if (sliceTask != null) {
                        l2 = l3 += sliceTask.completedSize(l);
                        if (l3 >= l) {
                            return true;
                        }
                    }
                }
                sliceTask = sliceTask2;
                l3 = l2;
            }
            if (l3 < l) {
                bl = false;
            }
            return bl;
        }

        @Override
        protected void cancel() {
            super.cancel();
            if (this.completed) {
                this.setLocalResult(this.getEmptyResult());
            }
        }

        @Override
        protected final Node<P_OUT> doLeaf() {
            boolean bl = this.isRoot();
            long l = -1L;
            if (bl) {
                if (StreamOpFlag.SIZED.isPreserved(this.op.sourceOrOpFlags)) {
                    l = this.op.exactOutputSizeIfKnown(this.spliterator);
                }
                Node.Builder<P_OUT> builder = this.op.makeNodeBuilder(l, this.generator);
                Sink<P_OUT> sink = this.op.opWrapSink(this.helper.getStreamAndOpFlags(), builder);
                this.helper.copyIntoWithCancel(this.helper.wrapSink(sink), this.spliterator);
                return builder.build();
            }
            Node node = this.helper.wrapAndCopyInto(this.helper.makeNodeBuilder(-1L, this.generator), this.spliterator).build();
            this.thisNodeSize = node.count();
            this.completed = true;
            this.spliterator = null;
            return node;
        }

        @Override
        protected final Node<P_OUT> getEmptyResult() {
            return Nodes.emptyNode(this.op.getOutputShape());
        }

        @Override
        protected SliceTask<P_IN, P_OUT> makeChild(Spliterator<P_IN> spliterator) {
            return new SliceTask<P_IN, P_OUT>(this, spliterator);
        }

        @Override
        public final void onCompletion(CountedCompleter<?> countedCompleter) {
            if (!this.isLeaf()) {
                Object object;
                this.thisNodeSize = ((SliceTask)this.leftChild).thisNodeSize + ((SliceTask)this.rightChild).thisNodeSize;
                if (this.canceled) {
                    this.thisNodeSize = 0L;
                    object = this.getEmptyResult();
                } else {
                    object = this.thisNodeSize == 0L ? this.getEmptyResult() : (((SliceTask)this.leftChild).thisNodeSize == 0L ? (Node)((SliceTask)this.rightChild).getLocalResult() : Nodes.conc(this.op.getOutputShape(), (Node)((SliceTask)this.leftChild).getLocalResult(), (Node)((SliceTask)this.rightChild).getLocalResult()));
                }
                if (this.isRoot()) {
                    object = this.doTruncate((Node<P_OUT>)object);
                }
                this.setLocalResult(object);
                this.completed = true;
            }
            if (this.targetSize >= 0L && !this.isRoot() && this.isLeftCompleted(this.targetOffset + this.targetSize)) {
                this.cancelLaterNodes();
            }
            super.onCompletion(countedCompleter);
        }
    }

}

