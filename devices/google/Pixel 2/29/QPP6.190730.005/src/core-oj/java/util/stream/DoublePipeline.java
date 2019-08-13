/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$0HimmAYr5h1pFdNckEhxJ9y9Zqk
 *  java.util.stream.-$$Lambda$745FUy7cYwYu7KrMQTYh2DNqh1I
 *  java.util.stream.-$$Lambda$9-clh6DyAY2rGfAxuH1sO9aEBuU
 *  java.util.stream.-$$Lambda$BZcmU4lh1MU8ke57orLk6ELdvT4
 *  java.util.stream.-$$Lambda$DoublePipeline
 *  java.util.stream.-$$Lambda$DoublePipeline$8lpXAdS4oGMq6Yo_dNhNdoP-gg0
 *  java.util.stream.-$$Lambda$DoublePipeline$KYIKJiRuFnKlAv02sN6Y0G5US7E
 *  java.util.stream.-$$Lambda$DoublePipeline$O7F4ENrC3oYj9E0vblCKW9Dec60
 *  java.util.stream.-$$Lambda$DoublePipeline$V2mM4_kocaa0EZ7g04Qc6_Yd13E
 *  java.util.stream.-$$Lambda$DoublePipeline$VwL6T93St4bY9lzEXgl24N_DcA4
 *  java.util.stream.-$$Lambda$DoublePipeline$btJQIF5a5bk658mbj9AIl0UV19Q
 *  java.util.stream.-$$Lambda$DoublePipeline$gq0fD9NZ938fl5Zgm1Lwm9G2tpI
 *  java.util.stream.-$$Lambda$DoublePipeline$jsM76ecD5K_oP4TaArM1RdmdjOw
 *  java.util.stream.-$$Lambda$DoublePipeline$lWQTyY6EPN0Xvhyjp5Lr5ZKBDCA
 *  java.util.stream.-$$Lambda$Xsl4nKeYydTETtdRjTtEXmjJItE
 *  java.util.stream.-$$Lambda$xi7ZBZfKmkbt5CSsaL8qlNeHupc
 */
package java.util.stream;

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
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.-$;
import java.util.stream.AbstractPipeline;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.FindOps;
import java.util.stream.ForEachOps;
import java.util.stream.IntPipeline;
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
import java.util.stream._$$Lambda$0HimmAYr5h1pFdNckEhxJ9y9Zqk;
import java.util.stream._$$Lambda$745FUy7cYwYu7KrMQTYh2DNqh1I;
import java.util.stream._$$Lambda$9_clh6DyAY2rGfAxuH1sO9aEBuU;
import java.util.stream._$$Lambda$BZcmU4lh1MU8ke57orLk6ELdvT4;
import java.util.stream._$$Lambda$DoublePipeline$5$1$kqJiVK7sQB3kKvPk9DB9gInHJq4;
import java.util.stream._$$Lambda$DoublePipeline$8lpXAdS4oGMq6Yo_dNhNdoP_gg0;
import java.util.stream._$$Lambda$DoublePipeline$IBZGhEgRy1ddKsqLtAJ_JIbQPE8;
import java.util.stream._$$Lambda$DoublePipeline$KYIKJiRuFnKlAv02sN6Y0G5US7E;
import java.util.stream._$$Lambda$DoublePipeline$O7F4ENrC3oYj9E0vblCKW9Dec60;
import java.util.stream._$$Lambda$DoublePipeline$V2mM4_kocaa0EZ7g04Qc6_Yd13E;
import java.util.stream._$$Lambda$DoublePipeline$VwL6T93St4bY9lzEXgl24N_DcA4;
import java.util.stream._$$Lambda$DoublePipeline$btJQIF5a5bk658mbj9AIl0UV19Q;
import java.util.stream._$$Lambda$DoublePipeline$gq0fD9NZ938fl5Zgm1Lwm9G2tpI;
import java.util.stream._$$Lambda$DoublePipeline$jsM76ecD5K_oP4TaArM1RdmdjOw;
import java.util.stream._$$Lambda$DoublePipeline$lWQTyY6EPN0Xvhyjp5Lr5ZKBDCA;
import java.util.stream._$$Lambda$G0LLxk8pWitjFgsOx2bYtRO_rGg;
import java.util.stream._$$Lambda$Xsl4nKeYydTETtdRjTtEXmjJItE;
import java.util.stream._$$Lambda$xi7ZBZfKmkbt5CSsaL8qlNeHupc;

public abstract class DoublePipeline<E_IN>
extends AbstractPipeline<E_IN, Double, DoubleStream>
implements DoubleStream {
    DoublePipeline(Spliterator<Double> spliterator, int n, boolean bl) {
        super(spliterator, n, bl);
    }

    DoublePipeline(Supplier<? extends Spliterator<Double>> supplier, int n, boolean bl) {
        super(supplier, n, bl);
    }

    DoublePipeline(AbstractPipeline<?, E_IN, ?> abstractPipeline, int n) {
        super(abstractPipeline, n);
    }

    private static Spliterator.OfDouble adapt(Spliterator<Double> spliterator) {
        if (spliterator instanceof Spliterator.OfDouble) {
            return (Spliterator.OfDouble)spliterator;
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using DoubleStream.adapt(Spliterator<Double> s)");
        }
        throw new UnsupportedOperationException("DoubleStream.adapt(Spliterator<Double> s)");
    }

    private static DoubleConsumer adapt(Sink<Double> sink) {
        if (sink instanceof DoubleConsumer) {
            return (DoubleConsumer)((Object)sink);
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using DoubleStream.adapt(Sink<Double> s)");
        }
        Objects.requireNonNull(sink);
        return new _$$Lambda$G0LLxk8pWitjFgsOx2bYtRO_rGg(sink);
    }

    static /* synthetic */ double[] lambda$average$4() {
        return new double[4];
    }

    static /* synthetic */ void lambda$average$5(double[] arrd, double d) {
        arrd[2] = arrd[2] + 1.0;
        Collectors.sumWithCompensation(arrd, d);
        arrd[3] = arrd[3] + d;
    }

    static /* synthetic */ void lambda$average$6(double[] arrd, double[] arrd2) {
        Collectors.sumWithCompensation(arrd, arrd2[0]);
        Collectors.sumWithCompensation(arrd, arrd2[1]);
        arrd[2] = arrd[2] + arrd2[2];
        arrd[3] = arrd[3] + arrd2[3];
    }

    static /* synthetic */ Object lambda$collect$8(BiConsumer biConsumer, Object object, Object object2) {
        biConsumer.accept(object, object2);
        return object;
    }

    static /* synthetic */ long lambda$count$7(double d) {
        return 1L;
    }

    static /* synthetic */ double lambda$distinct$0(Double d) {
        return d;
    }

    static /* synthetic */ double[] lambda$sum$1() {
        return new double[3];
    }

    static /* synthetic */ void lambda$sum$2(double[] arrd, double d) {
        Collectors.sumWithCompensation(arrd, d);
        arrd[2] = arrd[2] + d;
    }

    static /* synthetic */ void lambda$sum$3(double[] arrd, double[] arrd2) {
        Collectors.sumWithCompensation(arrd, arrd2[0]);
        Collectors.sumWithCompensation(arrd, arrd2[1]);
        arrd[2] = arrd[2] + arrd2[2];
    }

    static /* synthetic */ Double[] lambda$toArray$9(int n) {
        return new Double[n];
    }

    @Override
    public final boolean allMatch(DoublePredicate doublePredicate) {
        return this.evaluate(MatchOps.makeDouble(doublePredicate, MatchOps.MatchKind.ALL));
    }

    @Override
    public final boolean anyMatch(DoublePredicate doublePredicate) {
        return this.evaluate(MatchOps.makeDouble(doublePredicate, MatchOps.MatchKind.ANY));
    }

    @Override
    public final OptionalDouble average() {
        Object object = (double[])this.collect((Supplier<R>)_$$Lambda$DoublePipeline$O7F4ENrC3oYj9E0vblCKW9Dec60.INSTANCE, (ObjDoubleConsumer<R>)_$$Lambda$DoublePipeline$lWQTyY6EPN0Xvhyjp5Lr5ZKBDCA.INSTANCE, (BiConsumer<R, R>)_$$Lambda$DoublePipeline$8lpXAdS4oGMq6Yo_dNhNdoP_gg0.INSTANCE);
        object = object[2] > 0.0 ? OptionalDouble.of(Collectors.computeFinalSum(object) / object[2]) : OptionalDouble.empty();
        return object;
    }

    @Override
    public final Stream<Double> boxed() {
        return this.mapToObj((DoubleFunction<? extends U>)_$$Lambda$0HimmAYr5h1pFdNckEhxJ9y9Zqk.INSTANCE);
    }

    @Override
    public final <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> objDoubleConsumer, BiConsumer<R, R> biConsumer) {
        return this.evaluate(ReduceOps.makeDouble(supplier, objDoubleConsumer, new _$$Lambda$DoublePipeline$IBZGhEgRy1ddKsqLtAJ_JIbQPE8(biConsumer)));
    }

    @Override
    public final long count() {
        return this.mapToLong((DoubleToLongFunction)_$$Lambda$DoublePipeline$V2mM4_kocaa0EZ7g04Qc6_Yd13E.INSTANCE).sum();
    }

    @Override
    public final DoubleStream distinct() {
        return this.boxed().distinct().mapToDouble((ToDoubleFunction<Double>)_$$Lambda$DoublePipeline$gq0fD9NZ938fl5Zgm1Lwm9G2tpI.INSTANCE);
    }

    @Override
    public final <P_IN> Node<Double> evaluateToNode(PipelineHelper<Double> pipelineHelper, Spliterator<P_IN> spliterator, boolean bl, IntFunction<Double[]> intFunction) {
        return Nodes.collectDouble(pipelineHelper, spliterator, bl);
    }

    @Override
    public final DoubleStream filter(final DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        return new StatelessOp<Double>((AbstractPipeline)this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SIZED){

            @Override
            public Sink<Double> opWrapSink(int n, Sink<Double> sink) {
                return new Sink.ChainedDouble<Double>(sink){

                    @Override
                    public void accept(double d) {
                        if (doublePredicate.test(d)) {
                            this.downstream.accept(d);
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
    public final OptionalDouble findAny() {
        return this.evaluate(FindOps.makeDouble(false));
    }

    @Override
    public final OptionalDouble findFirst() {
        return this.evaluate(FindOps.makeDouble(true));
    }

    @Override
    public final DoubleStream flatMap(final DoubleFunction<? extends DoubleStream> doubleFunction) {
        return new StatelessOp<Double>((AbstractPipeline)this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED){

            @Override
            public Sink<Double> opWrapSink(int n, Sink<Double> sink) {
                return new Sink.ChainedDouble<Double>(sink){

                    @Override
                    public void accept(double d) {
                        DoubleStream doubleStream = (DoubleStream)doubleFunction.apply(d);
                        if (doubleStream != null) {
                            try {
                                BaseStream baseStream = doubleStream.sequential();
                                _$$Lambda$DoublePipeline$5$1$kqJiVK7sQB3kKvPk9DB9gInHJq4 _$$Lambda$DoublePipeline$5$1$kqJiVK7sQB3kKvPk9DB9gInHJq4 = new _$$Lambda$DoublePipeline$5$1$kqJiVK7sQB3kKvPk9DB9gInHJq4(this);
                                baseStream.forEach(_$$Lambda$DoublePipeline$5$1$kqJiVK7sQB3kKvPk9DB9gInHJq4);
                            }
                            catch (Throwable throwable) {
                                try {
                                    throw throwable;
                                }
                                catch (Throwable throwable2) {
                                    try {
                                        doubleStream.close();
                                    }
                                    catch (Throwable throwable3) {
                                        throwable.addSuppressed(throwable3);
                                    }
                                    throw throwable2;
                                }
                            }
                        }
                        if (doubleStream != null) {
                            doubleStream.close();
                        }
                    }

                    @Override
                    public void begin(long l) {
                        this.downstream.begin(-1L);
                    }

                    public /* synthetic */ void lambda$accept$0$DoublePipeline$5$1(double d) {
                        this.downstream.accept(d);
                    }
                };
            }

        };
    }

    @Override
    public void forEach(DoubleConsumer doubleConsumer) {
        this.evaluate(ForEachOps.makeDouble(doubleConsumer, false));
    }

    @Override
    public void forEachOrdered(DoubleConsumer doubleConsumer) {
        this.evaluate(ForEachOps.makeDouble(doubleConsumer, true));
    }

    @Override
    public final void forEachWithCancel(Spliterator<Double> ofDouble, Sink<Double> sink) {
        ofDouble = DoublePipeline.adapt(ofDouble);
        DoubleConsumer doubleConsumer = DoublePipeline.adapt(sink);
        while (!sink.cancellationRequested() && ofDouble.tryAdvance(doubleConsumer)) {
        }
    }

    @Override
    public final StreamShape getOutputShape() {
        return StreamShape.DOUBLE_VALUE;
    }

    @Override
    public final PrimitiveIterator.OfDouble iterator() {
        return Spliterators.iterator(this.spliterator());
    }

    public final Spliterator.OfDouble lazySpliterator(Supplier<? extends Spliterator<Double>> supplier) {
        return new StreamSpliterators.DelegatingSpliterator.OfDouble(supplier);
    }

    @Override
    public final DoubleStream limit(long l) {
        if (l >= 0L) {
            return SliceOps.makeDouble(this, 0L, l);
        }
        throw new IllegalArgumentException(Long.toString(l));
    }

    @Override
    public final Node.Builder<Double> makeNodeBuilder(long l, IntFunction<Double[]> intFunction) {
        return Nodes.doubleBuilder(l);
    }

    @Override
    public final DoubleStream map(final DoubleUnaryOperator doubleUnaryOperator) {
        Objects.requireNonNull(doubleUnaryOperator);
        return new StatelessOp<Double>((AbstractPipeline)this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<Double> opWrapSink(int n, Sink<Double> sink) {
                return new Sink.ChainedDouble<Double>(sink){

                    @Override
                    public void accept(double d) {
                        this.downstream.accept(doubleUnaryOperator.applyAsDouble(d));
                    }
                };
            }

        };
    }

    @Override
    public final IntStream mapToInt(final DoubleToIntFunction doubleToIntFunction) {
        Objects.requireNonNull(doubleToIntFunction);
        return new IntPipeline.StatelessOp<Double>((AbstractPipeline)this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<Double> opWrapSink(int n, Sink<Integer> sink) {
                return new Sink.ChainedDouble<Integer>(sink){

                    @Override
                    public void accept(double d) {
                        this.downstream.accept(doubleToIntFunction.applyAsInt(d));
                    }
                };
            }

        };
    }

    @Override
    public final LongStream mapToLong(final DoubleToLongFunction doubleToLongFunction) {
        Objects.requireNonNull(doubleToLongFunction);
        return new LongPipeline.StatelessOp<Double>((AbstractPipeline)this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<Double> opWrapSink(int n, Sink<Long> sink) {
                return new Sink.ChainedDouble<Long>(sink){

                    @Override
                    public void accept(double d) {
                        this.downstream.accept(doubleToLongFunction.applyAsLong(d));
                    }
                };
            }

        };
    }

    @Override
    public final <U> Stream<U> mapToObj(final DoubleFunction<? extends U> doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        return new ReferencePipeline.StatelessOp<Double, U>(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<Double> opWrapSink(int n, Sink<U> sink) {
                return new Sink.ChainedDouble<U>(sink){

                    @Override
                    public void accept(double d) {
                        this.downstream.accept(doubleFunction.apply(d));
                    }
                };
            }

        };
    }

    @Override
    public final OptionalDouble max() {
        return this.reduce((DoubleBinaryOperator)_$$Lambda$xi7ZBZfKmkbt5CSsaL8qlNeHupc.INSTANCE);
    }

    @Override
    public final OptionalDouble min() {
        return this.reduce((DoubleBinaryOperator)_$$Lambda$Xsl4nKeYydTETtdRjTtEXmjJItE.INSTANCE);
    }

    @Override
    public final boolean noneMatch(DoublePredicate doublePredicate) {
        return this.evaluate(MatchOps.makeDouble(doublePredicate, MatchOps.MatchKind.NONE));
    }

    @Override
    public final DoubleStream peek(final DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        return new StatelessOp<Double>((AbstractPipeline)this, StreamShape.DOUBLE_VALUE, 0){

            @Override
            public Sink<Double> opWrapSink(int n, Sink<Double> sink) {
                return new Sink.ChainedDouble<Double>(sink){

                    @Override
                    public void accept(double d) {
                        doubleConsumer.accept(d);
                        this.downstream.accept(d);
                    }
                };
            }

        };
    }

    @Override
    public final double reduce(double d, DoubleBinaryOperator doubleBinaryOperator) {
        return this.evaluate(ReduceOps.makeDouble(d, doubleBinaryOperator));
    }

    @Override
    public final OptionalDouble reduce(DoubleBinaryOperator doubleBinaryOperator) {
        return this.evaluate(ReduceOps.makeDouble(doubleBinaryOperator));
    }

    @Override
    public final DoubleStream skip(long l) {
        if (l >= 0L) {
            if (l == 0L) {
                return this;
            }
            return SliceOps.makeDouble(this, l, -1L);
        }
        throw new IllegalArgumentException(Long.toString(l));
    }

    @Override
    public final DoubleStream sorted() {
        return SortedOps.makeDouble(this);
    }

    @Override
    public final Spliterator.OfDouble spliterator() {
        return DoublePipeline.adapt(super.spliterator());
    }

    @Override
    public final double sum() {
        return Collectors.computeFinalSum((double[])this.collect((Supplier<R>)_$$Lambda$DoublePipeline$jsM76ecD5K_oP4TaArM1RdmdjOw.INSTANCE, (ObjDoubleConsumer<R>)_$$Lambda$DoublePipeline$btJQIF5a5bk658mbj9AIl0UV19Q.INSTANCE, (BiConsumer<R, R>)_$$Lambda$DoublePipeline$KYIKJiRuFnKlAv02sN6Y0G5US7E.INSTANCE));
    }

    @Override
    public final DoubleSummaryStatistics summaryStatistics() {
        return (DoubleSummaryStatistics)this.collect((Supplier<R>)_$$Lambda$745FUy7cYwYu7KrMQTYh2DNqh1I.INSTANCE, (ObjDoubleConsumer<R>)_$$Lambda$9_clh6DyAY2rGfAxuH1sO9aEBuU.INSTANCE, (BiConsumer<R, R>)_$$Lambda$BZcmU4lh1MU8ke57orLk6ELdvT4.INSTANCE);
    }

    @Override
    public final double[] toArray() {
        return (double[])Nodes.flattenDouble((Node.OfDouble)this.evaluateToArrayNode((IntFunction<E_OUT[]>)_$$Lambda$DoublePipeline$VwL6T93St4bY9lzEXgl24N_DcA4.INSTANCE)).asPrimitiveArray();
    }

    @Override
    public DoubleStream unordered() {
        if (!this.isOrdered()) {
            return this;
        }
        return new StatelessOp<Double>((AbstractPipeline)this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_ORDERED){

            @Override
            public Sink<Double> opWrapSink(int n, Sink<Double> sink) {
                return sink;
            }
        };
    }

    @Override
    public final <P_IN> Spliterator<Double> wrap(PipelineHelper<Double> pipelineHelper, Supplier<Spliterator<P_IN>> supplier, boolean bl) {
        return new StreamSpliterators.DoubleWrappingSpliterator<P_IN>(pipelineHelper, supplier, bl);
    }

    public static class Head<E_IN>
    extends DoublePipeline<E_IN> {
        public Head(Spliterator<Double> spliterator, int n, boolean bl) {
            super(spliterator, n, bl);
        }

        public Head(Supplier<? extends Spliterator<Double>> supplier, int n, boolean bl) {
            super(supplier, n, bl);
        }

        @Override
        public void forEach(DoubleConsumer doubleConsumer) {
            if (!this.isParallel()) {
                DoublePipeline.adapt(this.sourceStageSpliterator()).forEachRemaining(doubleConsumer);
            } else {
                super.forEach(doubleConsumer);
            }
        }

        @Override
        public void forEachOrdered(DoubleConsumer doubleConsumer) {
            if (!this.isParallel()) {
                DoublePipeline.adapt(this.sourceStageSpliterator()).forEachRemaining(doubleConsumer);
            } else {
                super.forEachOrdered(doubleConsumer);
            }
        }

        @Override
        public final boolean opIsStateful() {
            throw new UnsupportedOperationException();
        }

        @Override
        public final Sink<E_IN> opWrapSink(int n, Sink<Double> sink) {
            throw new UnsupportedOperationException();
        }
    }

    public static abstract class StatefulOp<E_IN>
    extends DoublePipeline<E_IN> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        public StatefulOp(AbstractPipeline<?, E_IN, ?> abstractPipeline, StreamShape streamShape, int n) {
            super(abstractPipeline, n);
        }

        @Override
        public abstract <P_IN> Node<Double> opEvaluateParallel(PipelineHelper<Double> var1, Spliterator<P_IN> var2, IntFunction<Double[]> var3);

        @Override
        public final boolean opIsStateful() {
            return true;
        }
    }

    public static abstract class StatelessOp<E_IN>
    extends DoublePipeline<E_IN> {
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

