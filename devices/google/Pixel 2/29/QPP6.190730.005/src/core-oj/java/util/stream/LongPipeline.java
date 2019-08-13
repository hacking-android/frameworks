/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$6eeAyFpmvaed9kw3uuEs0ErN7sg
 *  java.util.stream.-$$Lambda$JNjUhnscc8mcsjlQNaAi4qIfRDQ
 *  java.util.stream.-$$Lambda$LongPipeline
 *  java.util.stream.-$$Lambda$LongPipeline$C2qxkG-7ctBwIL2ufjYSA46AbOM
 *  java.util.stream.-$$Lambda$LongPipeline$HjmjwoQcQfPYnTF2E4GrQONBjyM
 *  java.util.stream.-$$Lambda$LongPipeline$LTFlNC6dzl63DE63FJGC-sG7H_c
 *  java.util.stream.-$$Lambda$LongPipeline$doop4YO9hzEFGaLnLB3xKA404M4
 *  java.util.stream.-$$Lambda$LongPipeline$sfTgyfHS4klE7h4z5M-NXsSIFcQ
 *  java.util.stream.-$$Lambda$LongPipeline$unkecqyY0oPqnMvfYdq_wAGb9pY
 *  java.util.stream.-$$Lambda$OExyAlU04fvFLvnsXWOUeFS6K6Y
 *  java.util.stream.-$$Lambda$Y_fORtDI6zkwP_Z_VGSwO2GcnS0
 *  java.util.stream.-$$Lambda$dplkPhACWDPIy18ogwdupEQaN40
 *  java.util.stream.-$$Lambda$kZuTETptiPwvB1J27Na7j760aLU
 *  java.util.stream.-$$Lambda$w4zz3RuWVbX94KiVllUNB6u_ygA
 */
package java.util.stream;

import java.util.Iterator;
import java.util.LongSummaryStatistics;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;
import java.util.stream.-$;
import java.util.stream.AbstractPipeline;
import java.util.stream.BaseStream;
import java.util.stream.DoublePipeline;
import java.util.stream.DoubleStream;
import java.util.stream.FindOps;
import java.util.stream.ForEachOps;
import java.util.stream.IntPipeline;
import java.util.stream.IntStream;
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
import java.util.stream._$$Lambda$6eeAyFpmvaed9kw3uuEs0ErN7sg;
import java.util.stream._$$Lambda$JNjUhnscc8mcsjlQNaAi4qIfRDQ;
import java.util.stream._$$Lambda$LongPipeline$6$1$fLvJH_Wq0Kv_MEJSFU3IOaEtvxk;
import java.util.stream._$$Lambda$LongPipeline$C2qxkG_7ctBwIL2ufjYSA46AbOM;
import java.util.stream._$$Lambda$LongPipeline$HjmjwoQcQfPYnTF2E4GrQONBjyM;
import java.util.stream._$$Lambda$LongPipeline$LTFlNC6dzl63DE63FJGC_sG7H_c;
import java.util.stream._$$Lambda$LongPipeline$_BxZA1c1Y79VaVw54W8s5K5ji_0;
import java.util.stream._$$Lambda$LongPipeline$doop4YO9hzEFGaLnLB3xKA404M4;
import java.util.stream._$$Lambda$LongPipeline$sfTgyfHS4klE7h4z5M_NXsSIFcQ;
import java.util.stream._$$Lambda$LongPipeline$unkecqyY0oPqnMvfYdq_wAGb9pY;
import java.util.stream._$$Lambda$OExyAlU04fvFLvnsXWOUeFS6K6Y;
import java.util.stream._$$Lambda$Y_fORtDI6zkwP_Z_VGSwO2GcnS0;
import java.util.stream._$$Lambda$dplkPhACWDPIy18ogwdupEQaN40;
import java.util.stream._$$Lambda$kZuTETptiPwvB1J27Na7j760aLU;
import java.util.stream._$$Lambda$w4zz3RuWVbX94KiVllUNB6u_ygA;
import java.util.stream._$$Lambda$zQ_9PoG_PFOA3MjNNbaERnRB6ik;

public abstract class LongPipeline<E_IN>
extends AbstractPipeline<E_IN, Long, LongStream>
implements LongStream {
    LongPipeline(Spliterator<Long> spliterator, int n, boolean bl) {
        super(spliterator, n, bl);
    }

    LongPipeline(Supplier<? extends Spliterator<Long>> supplier, int n, boolean bl) {
        super(supplier, n, bl);
    }

    LongPipeline(AbstractPipeline<?, E_IN, ?> abstractPipeline, int n) {
        super(abstractPipeline, n);
    }

    private static Spliterator.OfLong adapt(Spliterator<Long> spliterator) {
        if (spliterator instanceof Spliterator.OfLong) {
            return (Spliterator.OfLong)spliterator;
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using LongStream.adapt(Spliterator<Long> s)");
        }
        throw new UnsupportedOperationException("LongStream.adapt(Spliterator<Long> s)");
    }

    private static LongConsumer adapt(Sink<Long> sink) {
        if (sink instanceof LongConsumer) {
            return (LongConsumer)((Object)sink);
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using LongStream.adapt(Sink<Long> s)");
        }
        Objects.requireNonNull(sink);
        return new _$$Lambda$zQ_9PoG_PFOA3MjNNbaERnRB6ik(sink);
    }

    static /* synthetic */ long[] lambda$average$1() {
        return new long[2];
    }

    static /* synthetic */ void lambda$average$2(long[] arrl, long l) {
        arrl[0] = arrl[0] + 1L;
        arrl[1] = arrl[1] + l;
    }

    static /* synthetic */ void lambda$average$3(long[] arrl, long[] arrl2) {
        arrl[0] = arrl[0] + arrl2[0];
        arrl[1] = arrl[1] + arrl2[1];
    }

    static /* synthetic */ Object lambda$collect$5(BiConsumer biConsumer, Object object, Object object2) {
        biConsumer.accept(object, object2);
        return object;
    }

    static /* synthetic */ long lambda$count$4(long l) {
        return 1L;
    }

    static /* synthetic */ long lambda$distinct$0(Long l) {
        return l;
    }

    static /* synthetic */ Long[] lambda$toArray$6(int n) {
        return new Long[n];
    }

    @Override
    public final boolean allMatch(LongPredicate longPredicate) {
        return this.evaluate(MatchOps.makeLong(longPredicate, MatchOps.MatchKind.ALL));
    }

    @Override
    public final boolean anyMatch(LongPredicate longPredicate) {
        return this.evaluate(MatchOps.makeLong(longPredicate, MatchOps.MatchKind.ANY));
    }

    @Override
    public final DoubleStream asDoubleStream() {
        return new DoublePipeline.StatelessOp<Long>((AbstractPipeline)this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<Long> opWrapSink(int n, Sink<Double> sink) {
                return new Sink.ChainedLong<Double>(sink){

                    @Override
                    public void accept(long l) {
                        this.downstream.accept((double)l);
                    }
                };
            }

        };
    }

    @Override
    public final OptionalDouble average() {
        Object object = (long[])this.collect((Supplier<R>)_$$Lambda$LongPipeline$C2qxkG_7ctBwIL2ufjYSA46AbOM.INSTANCE, (ObjLongConsumer<R>)_$$Lambda$LongPipeline$sfTgyfHS4klE7h4z5M_NXsSIFcQ.INSTANCE, (BiConsumer<R, R>)_$$Lambda$LongPipeline$unkecqyY0oPqnMvfYdq_wAGb9pY.INSTANCE);
        object = object[0] > 0L ? OptionalDouble.of((double)object[1] / (double)object[0]) : OptionalDouble.empty();
        return object;
    }

    @Override
    public final Stream<Long> boxed() {
        return this.mapToObj((LongFunction<? extends U>)_$$Lambda$w4zz3RuWVbX94KiVllUNB6u_ygA.INSTANCE);
    }

    @Override
    public final <R> R collect(Supplier<R> supplier, ObjLongConsumer<R> objLongConsumer, BiConsumer<R, R> biConsumer) {
        return this.evaluate(ReduceOps.makeLong(supplier, objLongConsumer, new _$$Lambda$LongPipeline$_BxZA1c1Y79VaVw54W8s5K5ji_0(biConsumer)));
    }

    @Override
    public final long count() {
        return this.map((LongUnaryOperator)_$$Lambda$LongPipeline$HjmjwoQcQfPYnTF2E4GrQONBjyM.INSTANCE).sum();
    }

    @Override
    public final LongStream distinct() {
        return this.boxed().distinct().mapToLong((ToLongFunction<Long>)_$$Lambda$LongPipeline$doop4YO9hzEFGaLnLB3xKA404M4.INSTANCE);
    }

    @Override
    public final <P_IN> Node<Long> evaluateToNode(PipelineHelper<Long> pipelineHelper, Spliterator<P_IN> spliterator, boolean bl, IntFunction<Long[]> intFunction) {
        return Nodes.collectLong(pipelineHelper, spliterator, bl);
    }

    @Override
    public final LongStream filter(final LongPredicate longPredicate) {
        Objects.requireNonNull(longPredicate);
        return new StatelessOp<Long>((AbstractPipeline)this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SIZED){

            @Override
            public Sink<Long> opWrapSink(int n, Sink<Long> sink) {
                return new Sink.ChainedLong<Long>(sink){

                    @Override
                    public void accept(long l) {
                        if (longPredicate.test(l)) {
                            this.downstream.accept(l);
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
    public final OptionalLong findAny() {
        return this.evaluate(FindOps.makeLong(false));
    }

    @Override
    public final OptionalLong findFirst() {
        return this.evaluate(FindOps.makeLong(true));
    }

    @Override
    public final LongStream flatMap(final LongFunction<? extends LongStream> longFunction) {
        return new StatelessOp<Long>((AbstractPipeline)this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED){

            @Override
            public Sink<Long> opWrapSink(int n, Sink<Long> sink) {
                return new Sink.ChainedLong<Long>(sink){

                    @Override
                    public void accept(long l) {
                        LongStream longStream = (LongStream)longFunction.apply(l);
                        if (longStream != null) {
                            try {
                                BaseStream baseStream = longStream.sequential();
                                _$$Lambda$LongPipeline$6$1$fLvJH_Wq0Kv_MEJSFU3IOaEtvxk _$$Lambda$LongPipeline$6$1$fLvJH_Wq0Kv_MEJSFU3IOaEtvxk = new _$$Lambda$LongPipeline$6$1$fLvJH_Wq0Kv_MEJSFU3IOaEtvxk(this);
                                baseStream.forEach(_$$Lambda$LongPipeline$6$1$fLvJH_Wq0Kv_MEJSFU3IOaEtvxk);
                            }
                            catch (Throwable throwable) {
                                try {
                                    throw throwable;
                                }
                                catch (Throwable throwable2) {
                                    try {
                                        longStream.close();
                                    }
                                    catch (Throwable throwable3) {
                                        throwable.addSuppressed(throwable3);
                                    }
                                    throw throwable2;
                                }
                            }
                        }
                        if (longStream != null) {
                            longStream.close();
                        }
                    }

                    @Override
                    public void begin(long l) {
                        this.downstream.begin(-1L);
                    }

                    public /* synthetic */ void lambda$accept$0$LongPipeline$6$1(long l) {
                        this.downstream.accept(l);
                    }
                };
            }

        };
    }

    @Override
    public void forEach(LongConsumer longConsumer) {
        this.evaluate(ForEachOps.makeLong(longConsumer, false));
    }

    @Override
    public void forEachOrdered(LongConsumer longConsumer) {
        this.evaluate(ForEachOps.makeLong(longConsumer, true));
    }

    @Override
    public final void forEachWithCancel(Spliterator<Long> object, Sink<Long> sink) {
        Spliterator.OfLong ofLong = LongPipeline.adapt(object);
        object = LongPipeline.adapt(sink);
        while (!sink.cancellationRequested() && ofLong.tryAdvance((LongConsumer)object)) {
        }
    }

    @Override
    public final StreamShape getOutputShape() {
        return StreamShape.LONG_VALUE;
    }

    @Override
    public final PrimitiveIterator.OfLong iterator() {
        return Spliterators.iterator(this.spliterator());
    }

    public final Spliterator.OfLong lazySpliterator(Supplier<? extends Spliterator<Long>> supplier) {
        return new StreamSpliterators.DelegatingSpliterator.OfLong(supplier);
    }

    @Override
    public final LongStream limit(long l) {
        if (l >= 0L) {
            return SliceOps.makeLong(this, 0L, l);
        }
        throw new IllegalArgumentException(Long.toString(l));
    }

    @Override
    public final Node.Builder<Long> makeNodeBuilder(long l, IntFunction<Long[]> intFunction) {
        return Nodes.longBuilder(l);
    }

    @Override
    public final LongStream map(final LongUnaryOperator longUnaryOperator) {
        Objects.requireNonNull(longUnaryOperator);
        return new StatelessOp<Long>((AbstractPipeline)this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<Long> opWrapSink(int n, Sink<Long> sink) {
                return new Sink.ChainedLong<Long>(sink){

                    @Override
                    public void accept(long l) {
                        this.downstream.accept(longUnaryOperator.applyAsLong(l));
                    }
                };
            }

        };
    }

    @Override
    public final DoubleStream mapToDouble(final LongToDoubleFunction longToDoubleFunction) {
        Objects.requireNonNull(longToDoubleFunction);
        return new DoublePipeline.StatelessOp<Long>((AbstractPipeline)this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<Long> opWrapSink(int n, Sink<Double> sink) {
                return new Sink.ChainedLong<Double>(sink){

                    @Override
                    public void accept(long l) {
                        this.downstream.accept(longToDoubleFunction.applyAsDouble(l));
                    }
                };
            }

        };
    }

    @Override
    public final IntStream mapToInt(final LongToIntFunction longToIntFunction) {
        Objects.requireNonNull(longToIntFunction);
        return new IntPipeline.StatelessOp<Long>((AbstractPipeline)this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<Long> opWrapSink(int n, Sink<Integer> sink) {
                return new Sink.ChainedLong<Integer>(sink){

                    @Override
                    public void accept(long l) {
                        this.downstream.accept(longToIntFunction.applyAsInt(l));
                    }
                };
            }

        };
    }

    @Override
    public final <U> Stream<U> mapToObj(final LongFunction<? extends U> longFunction) {
        Objects.requireNonNull(longFunction);
        return new ReferencePipeline.StatelessOp<Long, U>(this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<Long> opWrapSink(int n, Sink<U> sink) {
                return new Sink.ChainedLong<U>(sink){

                    @Override
                    public void accept(long l) {
                        this.downstream.accept(longFunction.apply(l));
                    }
                };
            }

        };
    }

    @Override
    public final OptionalLong max() {
        return this.reduce((LongBinaryOperator)_$$Lambda$6eeAyFpmvaed9kw3uuEs0ErN7sg.INSTANCE);
    }

    @Override
    public final OptionalLong min() {
        return this.reduce((LongBinaryOperator)_$$Lambda$OExyAlU04fvFLvnsXWOUeFS6K6Y.INSTANCE);
    }

    @Override
    public final boolean noneMatch(LongPredicate longPredicate) {
        return this.evaluate(MatchOps.makeLong(longPredicate, MatchOps.MatchKind.NONE));
    }

    @Override
    public final LongStream peek(final LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        return new StatelessOp<Long>((AbstractPipeline)this, StreamShape.LONG_VALUE, 0){

            @Override
            public Sink<Long> opWrapSink(int n, Sink<Long> sink) {
                return new Sink.ChainedLong<Long>(sink){

                    @Override
                    public void accept(long l) {
                        longConsumer.accept(l);
                        this.downstream.accept(l);
                    }
                };
            }

        };
    }

    @Override
    public final long reduce(long l, LongBinaryOperator longBinaryOperator) {
        return this.evaluate(ReduceOps.makeLong(l, longBinaryOperator));
    }

    @Override
    public final OptionalLong reduce(LongBinaryOperator longBinaryOperator) {
        return this.evaluate(ReduceOps.makeLong(longBinaryOperator));
    }

    @Override
    public final LongStream skip(long l) {
        if (l >= 0L) {
            if (l == 0L) {
                return this;
            }
            return SliceOps.makeLong(this, l, -1L);
        }
        throw new IllegalArgumentException(Long.toString(l));
    }

    @Override
    public final LongStream sorted() {
        return SortedOps.makeLong(this);
    }

    @Override
    public final Spliterator.OfLong spliterator() {
        return LongPipeline.adapt(super.spliterator());
    }

    @Override
    public final long sum() {
        return this.reduce(0L, (LongBinaryOperator)_$$Lambda$dplkPhACWDPIy18ogwdupEQaN40.INSTANCE);
    }

    @Override
    public final LongSummaryStatistics summaryStatistics() {
        return (LongSummaryStatistics)this.collect((Supplier<R>)_$$Lambda$kZuTETptiPwvB1J27Na7j760aLU.INSTANCE, (ObjLongConsumer<R>)_$$Lambda$Y_fORtDI6zkwP_Z_VGSwO2GcnS0.INSTANCE, (BiConsumer<R, R>)_$$Lambda$JNjUhnscc8mcsjlQNaAi4qIfRDQ.INSTANCE);
    }

    @Override
    public final long[] toArray() {
        return (long[])Nodes.flattenLong((Node.OfLong)this.evaluateToArrayNode((IntFunction<E_OUT[]>)_$$Lambda$LongPipeline$LTFlNC6dzl63DE63FJGC_sG7H_c.INSTANCE)).asPrimitiveArray();
    }

    @Override
    public LongStream unordered() {
        if (!this.isOrdered()) {
            return this;
        }
        return new StatelessOp<Long>((AbstractPipeline)this, StreamShape.LONG_VALUE, StreamOpFlag.NOT_ORDERED){

            @Override
            public Sink<Long> opWrapSink(int n, Sink<Long> sink) {
                return sink;
            }
        };
    }

    @Override
    public final <P_IN> Spliterator<Long> wrap(PipelineHelper<Long> pipelineHelper, Supplier<Spliterator<P_IN>> supplier, boolean bl) {
        return new StreamSpliterators.LongWrappingSpliterator<P_IN>(pipelineHelper, supplier, bl);
    }

    public static class Head<E_IN>
    extends LongPipeline<E_IN> {
        public Head(Spliterator<Long> spliterator, int n, boolean bl) {
            super(spliterator, n, bl);
        }

        public Head(Supplier<? extends Spliterator<Long>> supplier, int n, boolean bl) {
            super(supplier, n, bl);
        }

        @Override
        public void forEach(LongConsumer longConsumer) {
            if (!this.isParallel()) {
                LongPipeline.adapt(this.sourceStageSpliterator()).forEachRemaining(longConsumer);
            } else {
                super.forEach(longConsumer);
            }
        }

        @Override
        public void forEachOrdered(LongConsumer longConsumer) {
            if (!this.isParallel()) {
                LongPipeline.adapt(this.sourceStageSpliterator()).forEachRemaining(longConsumer);
            } else {
                super.forEachOrdered(longConsumer);
            }
        }

        @Override
        public final boolean opIsStateful() {
            throw new UnsupportedOperationException();
        }

        @Override
        public final Sink<E_IN> opWrapSink(int n, Sink<Long> sink) {
            throw new UnsupportedOperationException();
        }
    }

    public static abstract class StatefulOp<E_IN>
    extends LongPipeline<E_IN> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        public StatefulOp(AbstractPipeline<?, E_IN, ?> abstractPipeline, StreamShape streamShape, int n) {
            super(abstractPipeline, n);
        }

        @Override
        public abstract <P_IN> Node<Long> opEvaluateParallel(PipelineHelper<Long> var1, Spliterator<P_IN> var2, IntFunction<Long[]> var3);

        @Override
        public final boolean opIsStateful() {
            return true;
        }
    }

    public static abstract class StatelessOp<E_IN>
    extends LongPipeline<E_IN> {
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

