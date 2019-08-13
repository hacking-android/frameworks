/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$FZ2W1z3RReutoY2tFnI_NsF0lTk
 *  java.util.stream.-$$Lambda$HJTpjoyUrBGPZyR69XwKllqU1YY
 *  java.util.stream.-$$Lambda$IntPipeline
 *  java.util.stream.-$$Lambda$IntPipeline$0s_rkIyKzlnj_M-bqfCTpum_W2c
 *  java.util.stream.-$$Lambda$IntPipeline$MrivqBp4YhHB_ix11jxmkPQ1lbE
 *  java.util.stream.-$$Lambda$IntPipeline$Q_Wb7uDnZZMCasMbsGNAwSlprMo
 *  java.util.stream.-$$Lambda$IntPipeline$R-E7oGjPWog3HR9X-8MdhU1ZGRE
 *  java.util.stream.-$$Lambda$IntPipeline$hMFCZ84F0UujzJhdWtPfESTkN2A
 *  java.util.stream.-$$Lambda$IntPipeline$ozedusDMANE_B8aDthWCd1L-na4
 *  java.util.stream.-$$Lambda$UowTf7vzuMsu4sv1-eMs5iEeNh0
 *  java.util.stream.-$$Lambda$YcgMAuDDScc4HC6CSMDq1R0qa40
 *  java.util.stream.-$$Lambda$_Ea_sNpqZAwihIOCRBaP7hHgWWI
 *  java.util.stream.-$$Lambda$ono9Bp0lMrKbIRfAAYdycY0_qag
 *  java.util.stream.-$$Lambda$wFoiz-RiPqYBPe0X4aSzbj2iL3g
 */
package java.util.stream;

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
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.-$;
import java.util.stream.AbstractPipeline;
import java.util.stream.BaseStream;
import java.util.stream.DoublePipeline;
import java.util.stream.DoubleStream;
import java.util.stream.FindOps;
import java.util.stream.ForEachOps;
import java.util.stream.IntStream;
import java.util.stream.LongPipeline;
import java.util.stream.LongStream;
import java.util.stream.MatchOps;
import java.util.stream.Node;
import java.util.stream.Nodes;
import java.util.stream.PipelineHelper;
import java.util.stream.ReduceOps;
import java.util.stream.ReferencePipeline;
import java.util.stream.Sink;
import java.util.stream.SliceOps;
import java.util.stream.SortedOps;
import java.util.stream.Stream;
import java.util.stream.StreamOpFlag;
import java.util.stream.StreamShape;
import java.util.stream.StreamSpliterators;
import java.util.stream.TerminalOp;
import java.util.stream.Tripwire;
import java.util.stream._$$Lambda$FZ2W1z3RReutoY2tFnI_NsF0lTk;
import java.util.stream._$$Lambda$HJTpjoyUrBGPZyR69XwKllqU1YY;
import java.util.stream._$$Lambda$IntPipeline$0s_rkIyKzlnj_M_bqfCTpum_W2c;
import java.util.stream._$$Lambda$IntPipeline$7$1$E2wwNE1UnVxs0E9_n47lRWmnJGM;
import java.util.stream._$$Lambda$IntPipeline$MrivqBp4YhHB_ix11jxmkPQ1lbE;
import java.util.stream._$$Lambda$IntPipeline$Q_Wb7uDnZZMCasMbsGNAwSlprMo;
import java.util.stream._$$Lambda$IntPipeline$R_E7oGjPWog3HR9X_8MdhU1ZGRE;
import java.util.stream._$$Lambda$IntPipeline$gTDhYg7hsRI2br4NmAxtQnW5i6Y;
import java.util.stream._$$Lambda$IntPipeline$hMFCZ84F0UujzJhdWtPfESTkN2A;
import java.util.stream._$$Lambda$IntPipeline$ozedusDMANE_B8aDthWCd1L_na4;
import java.util.stream._$$Lambda$UowTf7vzuMsu4sv1_eMs5iEeNh0;
import java.util.stream._$$Lambda$YcgMAuDDScc4HC6CSMDq1R0qa40;
import java.util.stream._$$Lambda$_Ea_sNpqZAwihIOCRBaP7hHgWWI;
import java.util.stream._$$Lambda$ono9Bp0lMrKbIRfAAYdycY0_qag;
import java.util.stream._$$Lambda$wDsxx48ovPSGeNEb3P6H9u7YX0k;
import java.util.stream._$$Lambda$wFoiz_RiPqYBPe0X4aSzbj2iL3g;

public abstract class IntPipeline<E_IN>
extends AbstractPipeline<E_IN, Integer, IntStream>
implements IntStream {
    IntPipeline(Spliterator<Integer> spliterator, int n, boolean bl) {
        super(spliterator, n, bl);
    }

    IntPipeline(Supplier<? extends Spliterator<Integer>> supplier, int n, boolean bl) {
        super(supplier, n, bl);
    }

    IntPipeline(AbstractPipeline<?, E_IN, ?> abstractPipeline, int n) {
        super(abstractPipeline, n);
    }

    private static Spliterator.OfInt adapt(Spliterator<Integer> spliterator) {
        if (spliterator instanceof Spliterator.OfInt) {
            return (Spliterator.OfInt)spliterator;
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using IntStream.adapt(Spliterator<Integer> s)");
        }
        throw new UnsupportedOperationException("IntStream.adapt(Spliterator<Integer> s)");
    }

    private static IntConsumer adapt(Sink<Integer> sink) {
        if (sink instanceof IntConsumer) {
            return (IntConsumer)((Object)sink);
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using IntStream.adapt(Sink<Integer> s)");
        }
        Objects.requireNonNull(sink);
        return new _$$Lambda$wDsxx48ovPSGeNEb3P6H9u7YX0k(sink);
    }

    static /* synthetic */ long[] lambda$average$2() {
        return new long[2];
    }

    static /* synthetic */ void lambda$average$3(long[] arrl, int n) {
        arrl[0] = arrl[0] + 1L;
        arrl[1] = arrl[1] + (long)n;
    }

    static /* synthetic */ void lambda$average$4(long[] arrl, long[] arrl2) {
        arrl[0] = arrl[0] + arrl2[0];
        arrl[1] = arrl[1] + arrl2[1];
    }

    static /* synthetic */ Object lambda$collect$5(BiConsumer biConsumer, Object object, Object object2) {
        biConsumer.accept(object, object2);
        return object;
    }

    static /* synthetic */ long lambda$count$1(int n) {
        return 1L;
    }

    static /* synthetic */ int lambda$distinct$0(Integer n) {
        return n;
    }

    static /* synthetic */ Integer[] lambda$toArray$6(int n) {
        return new Integer[n];
    }

    @Override
    public final boolean allMatch(IntPredicate intPredicate) {
        return this.evaluate(MatchOps.makeInt(intPredicate, MatchOps.MatchKind.ALL));
    }

    @Override
    public final boolean anyMatch(IntPredicate intPredicate) {
        return this.evaluate(MatchOps.makeInt(intPredicate, MatchOps.MatchKind.ANY));
    }

    @Override
    public final DoubleStream asDoubleStream() {
        return new DoublePipeline.StatelessOp<Integer>((AbstractPipeline)this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<Integer> opWrapSink(int n, Sink<Double> sink) {
                return new Sink.ChainedInt<Double>(sink){

                    @Override
                    public void accept(int n) {
                        this.downstream.accept((double)n);
                    }
                };
            }

        };
    }

    @Override
    public final LongStream asLongStream() {
        return new LongPipeline.StatelessOp<Integer>((AbstractPipeline)this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<Integer> opWrapSink(int n, Sink<Long> sink) {
                return new Sink.ChainedInt<Long>(sink){

                    @Override
                    public void accept(int n) {
                        this.downstream.accept((long)n);
                    }
                };
            }

        };
    }

    @Override
    public final OptionalDouble average() {
        Object object = (long[])this.collect((Supplier<R>)_$$Lambda$IntPipeline$MrivqBp4YhHB_ix11jxmkPQ1lbE.INSTANCE, (ObjIntConsumer<R>)_$$Lambda$IntPipeline$0s_rkIyKzlnj_M_bqfCTpum_W2c.INSTANCE, (BiConsumer<R, R>)_$$Lambda$IntPipeline$hMFCZ84F0UujzJhdWtPfESTkN2A.INSTANCE);
        object = object[0] > 0L ? OptionalDouble.of((double)object[1] / (double)object[0]) : OptionalDouble.empty();
        return object;
    }

    @Override
    public final Stream<Integer> boxed() {
        return this.mapToObj((IntFunction<? extends U>)_$$Lambda$wFoiz_RiPqYBPe0X4aSzbj2iL3g.INSTANCE);
    }

    @Override
    public final <R> R collect(Supplier<R> supplier, ObjIntConsumer<R> objIntConsumer, BiConsumer<R, R> biConsumer) {
        return this.evaluate(ReduceOps.makeInt(supplier, objIntConsumer, new _$$Lambda$IntPipeline$gTDhYg7hsRI2br4NmAxtQnW5i6Y(biConsumer)));
    }

    @Override
    public final long count() {
        return this.mapToLong((IntToLongFunction)_$$Lambda$IntPipeline$Q_Wb7uDnZZMCasMbsGNAwSlprMo.INSTANCE).sum();
    }

    @Override
    public final IntStream distinct() {
        return this.boxed().distinct().mapToInt((ToIntFunction<Integer>)_$$Lambda$IntPipeline$R_E7oGjPWog3HR9X_8MdhU1ZGRE.INSTANCE);
    }

    @Override
    public final <P_IN> Node<Integer> evaluateToNode(PipelineHelper<Integer> pipelineHelper, Spliterator<P_IN> spliterator, boolean bl, IntFunction<Integer[]> intFunction) {
        return Nodes.collectInt(pipelineHelper, spliterator, bl);
    }

    @Override
    public final IntStream filter(final IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return new StatelessOp<Integer>((AbstractPipeline)this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SIZED){

            @Override
            public Sink<Integer> opWrapSink(int n, Sink<Integer> sink) {
                return new Sink.ChainedInt<Integer>(sink){

                    @Override
                    public void accept(int n) {
                        if (intPredicate.test(n)) {
                            this.downstream.accept(n);
                        }
                    }

                    @Override
                    public void begin(long l) {
                        this.downstream.begin(-1L);
                    }
                };
            }

        };
    }

    @Override
    public final OptionalInt findAny() {
        return this.evaluate(FindOps.makeInt(false));
    }

    @Override
    public final OptionalInt findFirst() {
        return this.evaluate(FindOps.makeInt(true));
    }

    @Override
    public final IntStream flatMap(final IntFunction<? extends IntStream> intFunction) {
        return new StatelessOp<Integer>((AbstractPipeline)this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED){

            @Override
            public Sink<Integer> opWrapSink(int n, Sink<Integer> sink) {
                return new Sink.ChainedInt<Integer>(sink){

                    @Override
                    public void accept(int n) {
                        IntStream intStream = (IntStream)intFunction.apply(n);
                        if (intStream != null) {
                            try {
                                BaseStream baseStream = intStream.sequential();
                                _$$Lambda$IntPipeline$7$1$E2wwNE1UnVxs0E9_n47lRWmnJGM _$$Lambda$IntPipeline$7$1$E2wwNE1UnVxs0E9_n47lRWmnJGM = new _$$Lambda$IntPipeline$7$1$E2wwNE1UnVxs0E9_n47lRWmnJGM(this);
                                baseStream.forEach(_$$Lambda$IntPipeline$7$1$E2wwNE1UnVxs0E9_n47lRWmnJGM);
                            }
                            catch (Throwable throwable) {
                                try {
                                    throw throwable;
                                }
                                catch (Throwable throwable2) {
                                    try {
                                        intStream.close();
                                    }
                                    catch (Throwable throwable3) {
                                        throwable.addSuppressed(throwable3);
                                    }
                                    throw throwable2;
                                }
                            }
                        }
                        if (intStream != null) {
                            intStream.close();
                        }
                    }

                    @Override
                    public void begin(long l) {
                        this.downstream.begin(-1L);
                    }

                    public /* synthetic */ void lambda$accept$0$IntPipeline$7$1(int n) {
                        this.downstream.accept(n);
                    }
                };
            }

        };
    }

    @Override
    public void forEach(IntConsumer intConsumer) {
        this.evaluate(ForEachOps.makeInt(intConsumer, false));
    }

    @Override
    public void forEachOrdered(IntConsumer intConsumer) {
        this.evaluate(ForEachOps.makeInt(intConsumer, true));
    }

    @Override
    public final void forEachWithCancel(Spliterator<Integer> ofInt, Sink<Integer> sink) {
        ofInt = IntPipeline.adapt(ofInt);
        IntConsumer intConsumer = IntPipeline.adapt(sink);
        while (!sink.cancellationRequested() && ofInt.tryAdvance(intConsumer)) {
        }
    }

    @Override
    public final StreamShape getOutputShape() {
        return StreamShape.INT_VALUE;
    }

    @Override
    public final PrimitiveIterator.OfInt iterator() {
        return Spliterators.iterator(this.spliterator());
    }

    public final Spliterator.OfInt lazySpliterator(Supplier<? extends Spliterator<Integer>> supplier) {
        return new StreamSpliterators.DelegatingSpliterator.OfInt(supplier);
    }

    @Override
    public final IntStream limit(long l) {
        if (l >= 0L) {
            return SliceOps.makeInt(this, 0L, l);
        }
        throw new IllegalArgumentException(Long.toString(l));
    }

    @Override
    public final Node.Builder<Integer> makeNodeBuilder(long l, IntFunction<Integer[]> intFunction) {
        return Nodes.intBuilder(l);
    }

    @Override
    public final IntStream map(final IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        return new StatelessOp<Integer>((AbstractPipeline)this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<Integer> opWrapSink(int n, Sink<Integer> sink) {
                return new Sink.ChainedInt<Integer>(sink){

                    @Override
                    public void accept(int n) {
                        this.downstream.accept(intUnaryOperator.applyAsInt(n));
                    }
                };
            }

        };
    }

    @Override
    public final DoubleStream mapToDouble(final IntToDoubleFunction intToDoubleFunction) {
        Objects.requireNonNull(intToDoubleFunction);
        return new DoublePipeline.StatelessOp<Integer>((AbstractPipeline)this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<Integer> opWrapSink(int n, Sink<Double> sink) {
                return new Sink.ChainedInt<Double>(sink){

                    @Override
                    public void accept(int n) {
                        this.downstream.accept(intToDoubleFunction.applyAsDouble(n));
                    }
                };
            }

        };
    }

    @Override
    public final LongStream mapToLong(final IntToLongFunction intToLongFunction) {
        Objects.requireNonNull(intToLongFunction);
        return new LongPipeline.StatelessOp<Integer>((AbstractPipeline)this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<Integer> opWrapSink(int n, Sink<Long> sink) {
                return new Sink.ChainedInt<Long>(sink){

                    @Override
                    public void accept(int n) {
                        this.downstream.accept(intToLongFunction.applyAsLong(n));
                    }
                };
            }

        };
    }

    @Override
    public final <U> Stream<U> mapToObj(final IntFunction<? extends U> intFunction) {
        Objects.requireNonNull(intFunction);
        return new ReferencePipeline.StatelessOp<Integer, U>(this, StreamShape.INT_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<Integer> opWrapSink(int n, Sink<U> sink) {
                return new Sink.ChainedInt<U>(sink){

                    @Override
                    public void accept(int n) {
                        this.downstream.accept(intFunction.apply(n));
                    }
                };
            }

        };
    }

    @Override
    public final OptionalInt max() {
        return this.reduce((IntBinaryOperator)_$$Lambda$HJTpjoyUrBGPZyR69XwKllqU1YY.INSTANCE);
    }

    @Override
    public final OptionalInt min() {
        return this.reduce((IntBinaryOperator)_$$Lambda$FZ2W1z3RReutoY2tFnI_NsF0lTk.INSTANCE);
    }

    @Override
    public final boolean noneMatch(IntPredicate intPredicate) {
        return this.evaluate(MatchOps.makeInt(intPredicate, MatchOps.MatchKind.NONE));
    }

    @Override
    public final IntStream peek(final IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        return new StatelessOp<Integer>((AbstractPipeline)this, StreamShape.INT_VALUE, 0){

            @Override
            public Sink<Integer> opWrapSink(int n, Sink<Integer> sink) {
                return new Sink.ChainedInt<Integer>(sink){

                    @Override
                    public void accept(int n) {
                        intConsumer.accept(n);
                        this.downstream.accept(n);
                    }
                };
            }

        };
    }

    @Override
    public final int reduce(int n, IntBinaryOperator intBinaryOperator) {
        return this.evaluate(ReduceOps.makeInt(n, intBinaryOperator));
    }

    @Override
    public final OptionalInt reduce(IntBinaryOperator intBinaryOperator) {
        return this.evaluate(ReduceOps.makeInt(intBinaryOperator));
    }

    @Override
    public final IntStream skip(long l) {
        if (l >= 0L) {
            if (l == 0L) {
                return this;
            }
            return SliceOps.makeInt(this, l, -1L);
        }
        throw new IllegalArgumentException(Long.toString(l));
    }

    @Override
    public final IntStream sorted() {
        return SortedOps.makeInt(this);
    }

    @Override
    public final Spliterator.OfInt spliterator() {
        return IntPipeline.adapt(super.spliterator());
    }

    @Override
    public final int sum() {
        return this.reduce(0, (IntBinaryOperator)_$$Lambda$ono9Bp0lMrKbIRfAAYdycY0_qag.INSTANCE);
    }

    @Override
    public final IntSummaryStatistics summaryStatistics() {
        return (IntSummaryStatistics)this.collect((Supplier<R>)_$$Lambda$_Ea_sNpqZAwihIOCRBaP7hHgWWI.INSTANCE, (ObjIntConsumer<R>)_$$Lambda$UowTf7vzuMsu4sv1_eMs5iEeNh0.INSTANCE, (BiConsumer<R, R>)_$$Lambda$YcgMAuDDScc4HC6CSMDq1R0qa40.INSTANCE);
    }

    @Override
    public final int[] toArray() {
        return (int[])Nodes.flattenInt((Node.OfInt)this.evaluateToArrayNode((IntFunction<E_OUT[]>)_$$Lambda$IntPipeline$ozedusDMANE_B8aDthWCd1L_na4.INSTANCE)).asPrimitiveArray();
    }

    @Override
    public IntStream unordered() {
        if (!this.isOrdered()) {
            return this;
        }
        return new StatelessOp<Integer>((AbstractPipeline)this, StreamShape.INT_VALUE, StreamOpFlag.NOT_ORDERED){

            @Override
            public Sink<Integer> opWrapSink(int n, Sink<Integer> sink) {
                return sink;
            }
        };
    }

    @Override
    public final <P_IN> Spliterator<Integer> wrap(PipelineHelper<Integer> pipelineHelper, Supplier<Spliterator<P_IN>> supplier, boolean bl) {
        return new StreamSpliterators.IntWrappingSpliterator<P_IN>(pipelineHelper, supplier, bl);
    }

    public static class Head<E_IN>
    extends IntPipeline<E_IN> {
        public Head(Spliterator<Integer> spliterator, int n, boolean bl) {
            super(spliterator, n, bl);
        }

        public Head(Supplier<? extends Spliterator<Integer>> supplier, int n, boolean bl) {
            super(supplier, n, bl);
        }

        @Override
        public void forEach(IntConsumer intConsumer) {
            if (!this.isParallel()) {
                IntPipeline.adapt(this.sourceStageSpliterator()).forEachRemaining(intConsumer);
            } else {
                super.forEach(intConsumer);
            }
        }

        @Override
        public void forEachOrdered(IntConsumer intConsumer) {
            if (!this.isParallel()) {
                IntPipeline.adapt(this.sourceStageSpliterator()).forEachRemaining(intConsumer);
            } else {
                super.forEachOrdered(intConsumer);
            }
        }

        @Override
        public final boolean opIsStateful() {
            throw new UnsupportedOperationException();
        }

        @Override
        public final Sink<E_IN> opWrapSink(int n, Sink<Integer> sink) {
            throw new UnsupportedOperationException();
        }
    }

    public static abstract class StatefulOp<E_IN>
    extends IntPipeline<E_IN> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        public StatefulOp(AbstractPipeline<?, E_IN, ?> abstractPipeline, StreamShape streamShape, int n) {
            super(abstractPipeline, n);
        }

        @Override
        public abstract <P_IN> Node<Integer> opEvaluateParallel(PipelineHelper<Integer> var1, Spliterator<P_IN> var2, IntFunction<Integer[]> var3);

        @Override
        public final boolean opIsStateful() {
            return true;
        }
    }

    public static abstract class StatelessOp<E_IN>
    extends IntPipeline<E_IN> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        public StatelessOp(AbstractPipeline<?, E_IN, ?> abstractPipeline, StreamShape streamShape, int n) {
            super(abstractPipeline, n);
        }

        @Override
        public final boolean opIsStateful() {
            return false;
        }
    }

}

