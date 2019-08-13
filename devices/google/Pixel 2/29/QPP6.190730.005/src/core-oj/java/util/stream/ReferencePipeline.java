/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$ReferencePipeline
 *  java.util.stream.-$$Lambda$ReferencePipeline$mk6xSsLZAKvG89IyN8pzBoM6otw
 *  java.util.stream.-$$Lambda$ReferencePipeline$n3O_UMTjTSOeDSKD1yhh_2N2rRU
 */
package java.util.stream;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.-$;
import java.util.stream.AbstractPipeline;
import java.util.stream.BaseStream;
import java.util.stream.Collector;
import java.util.stream.DistinctOps;
import java.util.stream.DoublePipeline;
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
import java.util.stream.Sink;
import java.util.stream.SliceOps;
import java.util.stream.SortedOps;
import java.util.stream.Stream;
import java.util.stream.StreamOpFlag;
import java.util.stream.StreamShape;
import java.util.stream.StreamSpliterators;
import java.util.stream.TerminalOp;
import java.util.stream._$$Lambda$G0LLxk8pWitjFgsOx2bYtRO_rGg;
import java.util.stream._$$Lambda$ReferencePipeline$i_WMOWw_bzoH_v_eRIvtIHIFvko;
import java.util.stream._$$Lambda$ReferencePipeline$mk6xSsLZAKvG89IyN8pzBoM6otw;
import java.util.stream._$$Lambda$ReferencePipeline$n3O_UMTjTSOeDSKD1yhh_2N2rRU;
import java.util.stream._$$Lambda$wDsxx48ovPSGeNEb3P6H9u7YX0k;
import java.util.stream._$$Lambda$zQ_9PoG_PFOA3MjNNbaERnRB6ik;

public abstract class ReferencePipeline<P_IN, P_OUT>
extends AbstractPipeline<P_IN, P_OUT, Stream<P_OUT>>
implements Stream<P_OUT> {
    ReferencePipeline(Spliterator<?> spliterator, int n, boolean bl) {
        super(spliterator, n, bl);
    }

    ReferencePipeline(Supplier<? extends Spliterator<?>> supplier, int n, boolean bl) {
        super(supplier, n, bl);
    }

    ReferencePipeline(AbstractPipeline<?, P_IN, ?> abstractPipeline, int n) {
        super(abstractPipeline, n);
    }

    static /* synthetic */ void lambda$collect$1(BiConsumer biConsumer, Object object, Object object2) {
        biConsumer.accept(object, object2);
    }

    static /* synthetic */ long lambda$count$2(Object object) {
        return 1L;
    }

    static /* synthetic */ Object[] lambda$toArray$0(int n) {
        return new Object[n];
    }

    @Override
    public final boolean allMatch(Predicate<? super P_OUT> predicate) {
        return this.evaluate(MatchOps.makeRef(predicate, MatchOps.MatchKind.ALL));
    }

    @Override
    public final boolean anyMatch(Predicate<? super P_OUT> predicate) {
        return this.evaluate(MatchOps.makeRef(predicate, MatchOps.MatchKind.ANY));
    }

    @Override
    public final <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super P_OUT> biConsumer, BiConsumer<R, R> biConsumer2) {
        return this.evaluate(ReduceOps.makeRef(supplier, biConsumer, biConsumer2));
    }

    @Override
    public final <R, A> R collect(Collector<? super P_OUT, A, R> collector) {
        A a;
        if (this.isParallel() && collector.characteristics().contains((Object)Collector.Characteristics.CONCURRENT) && (!this.isOrdered() || collector.characteristics().contains((Object)Collector.Characteristics.UNORDERED))) {
            a = collector.supplier().get();
            this.forEach(new _$$Lambda$ReferencePipeline$i_WMOWw_bzoH_v_eRIvtIHIFvko(collector.accumulator(), a));
        } else {
            a = this.evaluate(ReduceOps.makeRef(collector));
        }
        collector = collector.characteristics().contains((Object)Collector.Characteristics.IDENTITY_FINISH) ? a : collector.finisher().apply(a);
        return (R)collector;
    }

    @Override
    public final long count() {
        return this.mapToLong((ToLongFunction<? super P_OUT>)_$$Lambda$ReferencePipeline$mk6xSsLZAKvG89IyN8pzBoM6otw.INSTANCE).sum();
    }

    @Override
    public final Stream<P_OUT> distinct() {
        return DistinctOps.makeRef(this);
    }

    @Override
    public final <P_IN> Node<P_OUT> evaluateToNode(PipelineHelper<P_OUT> pipelineHelper, Spliterator<P_IN> spliterator, boolean bl, IntFunction<P_OUT[]> intFunction) {
        return Nodes.collect(pipelineHelper, spliterator, bl, intFunction);
    }

    @Override
    public final Stream<P_OUT> filter(final Predicate<? super P_OUT> predicate) {
        Objects.requireNonNull(predicate);
        return new StatelessOp<P_OUT, P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SIZED){

            @Override
            public Sink<P_OUT> opWrapSink(int n, Sink<P_OUT> sink) {
                return new Sink.ChainedReference<P_OUT, P_OUT>(sink){

                    @Override
                    public void accept(P_OUT P_OUT) {
                        if (predicate.test(P_OUT)) {
                            this.downstream.accept(P_OUT);
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
    public final Optional<P_OUT> findAny() {
        return this.evaluate(FindOps.makeRef(false));
    }

    @Override
    public final Optional<P_OUT> findFirst() {
        return this.evaluate(FindOps.makeRef(true));
    }

    @Override
    public final <R> Stream<R> flatMap(final Function<? super P_OUT, ? extends Stream<? extends R>> function) {
        Objects.requireNonNull(function);
        return new StatelessOp<P_OUT, R>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED){

            @Override
            public Sink<P_OUT> opWrapSink(int n, Sink<R> sink) {
                return new Sink.ChainedReference<P_OUT, R>(sink){

                    @Override
                    public void accept(P_OUT P_OUT) {
                        Stream stream = (Stream)function.apply(P_OUT);
                        if (stream != null) {
                            try {
                                ((Stream)stream.sequential()).forEach(this.downstream);
                            }
                            catch (Throwable throwable) {
                                try {
                                    throw throwable;
                                }
                                catch (Throwable throwable2) {
                                    try {
                                        stream.close();
                                    }
                                    catch (Throwable throwable3) {
                                        throwable.addSuppressed(throwable3);
                                    }
                                    throw throwable2;
                                }
                            }
                        }
                        if (stream != null) {
                            stream.close();
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
    public final DoubleStream flatMapToDouble(final Function<? super P_OUT, ? extends DoubleStream> function) {
        Objects.requireNonNull(function);
        return new DoublePipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED){

            @Override
            public Sink<P_OUT> opWrapSink(int n, Sink<Double> sink) {
                return new Sink.ChainedReference<P_OUT, Double>(sink){
                    DoubleConsumer downstreamAsDouble;
                    {
                        this = this.downstream;
                        Objects.requireNonNull(this);
                        this.downstreamAsDouble = new _$$Lambda$G0LLxk8pWitjFgsOx2bYtRO_rGg((Sink)this);
                    }

                    @Override
                    public void accept(P_OUT P_OUT) {
                        DoubleStream doubleStream = (DoubleStream)function.apply(P_OUT);
                        if (doubleStream != null) {
                            try {
                                doubleStream.sequential().forEach(this.downstreamAsDouble);
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
                };
            }

        };
    }

    @Override
    public final IntStream flatMapToInt(final Function<? super P_OUT, ? extends IntStream> function) {
        Objects.requireNonNull(function);
        return new IntPipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED){

            @Override
            public Sink<P_OUT> opWrapSink(int n, Sink<Integer> sink) {
                return new Sink.ChainedReference<P_OUT, Integer>(sink){
                    IntConsumer downstreamAsInt;
                    {
                        this = this.downstream;
                        Objects.requireNonNull(this);
                        this.downstreamAsInt = new _$$Lambda$wDsxx48ovPSGeNEb3P6H9u7YX0k((Sink)this);
                    }

                    @Override
                    public void accept(P_OUT P_OUT) {
                        IntStream intStream = (IntStream)function.apply(P_OUT);
                        if (intStream != null) {
                            try {
                                intStream.sequential().forEach(this.downstreamAsInt);
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
                };
            }

        };
    }

    @Override
    public final LongStream flatMapToLong(final Function<? super P_OUT, ? extends LongStream> function) {
        Objects.requireNonNull(function);
        return new LongPipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED){

            @Override
            public Sink<P_OUT> opWrapSink(int n, Sink<Long> sink) {
                return new Sink.ChainedReference<P_OUT, Long>(sink){
                    LongConsumer downstreamAsLong;
                    {
                        this = this.downstream;
                        Objects.requireNonNull(this);
                        this.downstreamAsLong = new _$$Lambda$zQ_9PoG_PFOA3MjNNbaERnRB6ik((Sink)this);
                    }

                    @Override
                    public void accept(P_OUT P_OUT) {
                        LongStream longStream = (LongStream)function.apply(P_OUT);
                        if (longStream != null) {
                            try {
                                longStream.sequential().forEach(this.downstreamAsLong);
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
                };
            }

        };
    }

    @Override
    public void forEach(Consumer<? super P_OUT> consumer) {
        this.evaluate(ForEachOps.makeRef(consumer, false));
    }

    @Override
    public void forEachOrdered(Consumer<? super P_OUT> consumer) {
        this.evaluate(ForEachOps.makeRef(consumer, true));
    }

    @Override
    public final void forEachWithCancel(Spliterator<P_OUT> spliterator, Sink<P_OUT> sink) {
        while (!sink.cancellationRequested() && spliterator.tryAdvance(sink)) {
        }
    }

    @Override
    public final StreamShape getOutputShape() {
        return StreamShape.REFERENCE;
    }

    @Override
    public final Iterator<P_OUT> iterator() {
        return Spliterators.iterator(this.spliterator());
    }

    @Override
    public final Spliterator<P_OUT> lazySpliterator(Supplier<? extends Spliterator<P_OUT>> supplier) {
        return new StreamSpliterators.DelegatingSpliterator(supplier);
    }

    @Override
    public final Stream<P_OUT> limit(long l) {
        if (l >= 0L) {
            return SliceOps.makeRef(this, 0L, l);
        }
        throw new IllegalArgumentException(Long.toString(l));
    }

    @Override
    public final Node.Builder<P_OUT> makeNodeBuilder(long l, IntFunction<P_OUT[]> intFunction) {
        return Nodes.builder(l, intFunction);
    }

    @Override
    public final <R> Stream<R> map(final Function<? super P_OUT, ? extends R> function) {
        Objects.requireNonNull(function);
        return new StatelessOp<P_OUT, R>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<P_OUT> opWrapSink(int n, Sink<R> sink) {
                return new Sink.ChainedReference<P_OUT, R>(sink){

                    @Override
                    public void accept(P_OUT P_OUT) {
                        this.downstream.accept(function.apply(P_OUT));
                    }
                };
            }

        };
    }

    @Override
    public final DoubleStream mapToDouble(final ToDoubleFunction<? super P_OUT> toDoubleFunction) {
        Objects.requireNonNull(toDoubleFunction);
        return new DoublePipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<P_OUT> opWrapSink(int n, Sink<Double> sink) {
                return new Sink.ChainedReference<P_OUT, Double>(sink){

                    @Override
                    public void accept(P_OUT P_OUT) {
                        this.downstream.accept(toDoubleFunction.applyAsDouble(P_OUT));
                    }
                };
            }

        };
    }

    @Override
    public final IntStream mapToInt(final ToIntFunction<? super P_OUT> toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        return new IntPipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<P_OUT> opWrapSink(int n, Sink<Integer> sink) {
                return new Sink.ChainedReference<P_OUT, Integer>(sink){

                    @Override
                    public void accept(P_OUT P_OUT) {
                        this.downstream.accept(toIntFunction.applyAsInt(P_OUT));
                    }
                };
            }

        };
    }

    @Override
    public final LongStream mapToLong(final ToLongFunction<? super P_OUT> toLongFunction) {
        Objects.requireNonNull(toLongFunction);
        return new LongPipeline.StatelessOp<P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT){

            @Override
            public Sink<P_OUT> opWrapSink(int n, Sink<Long> sink) {
                return new Sink.ChainedReference<P_OUT, Long>(sink){

                    @Override
                    public void accept(P_OUT P_OUT) {
                        this.downstream.accept(toLongFunction.applyAsLong(P_OUT));
                    }
                };
            }

        };
    }

    @Override
    public final Optional<P_OUT> max(Comparator<? super P_OUT> comparator) {
        return this.reduce(BinaryOperator.maxBy(comparator));
    }

    @Override
    public final Optional<P_OUT> min(Comparator<? super P_OUT> comparator) {
        return this.reduce(BinaryOperator.minBy(comparator));
    }

    @Override
    public final boolean noneMatch(Predicate<? super P_OUT> predicate) {
        return this.evaluate(MatchOps.makeRef(predicate, MatchOps.MatchKind.NONE));
    }

    @Override
    public final Stream<P_OUT> peek(final Consumer<? super P_OUT> consumer) {
        Objects.requireNonNull(consumer);
        return new StatelessOp<P_OUT, P_OUT>(this, StreamShape.REFERENCE, 0){

            @Override
            public Sink<P_OUT> opWrapSink(int n, Sink<P_OUT> sink) {
                return new Sink.ChainedReference<P_OUT, P_OUT>(sink){

                    @Override
                    public void accept(P_OUT P_OUT) {
                        consumer.accept(P_OUT);
                        this.downstream.accept(P_OUT);
                    }
                };
            }

        };
    }

    @Override
    public final <R> R reduce(R r, BiFunction<R, ? super P_OUT, R> biFunction, BinaryOperator<R> binaryOperator) {
        return this.evaluate(ReduceOps.makeRef(r, biFunction, binaryOperator));
    }

    @Override
    public final P_OUT reduce(P_OUT P_OUT, BinaryOperator<P_OUT> binaryOperator) {
        return this.evaluate(ReduceOps.makeRef(P_OUT, binaryOperator, binaryOperator));
    }

    @Override
    public final Optional<P_OUT> reduce(BinaryOperator<P_OUT> binaryOperator) {
        return this.evaluate(ReduceOps.makeRef(binaryOperator));
    }

    @Override
    public final Stream<P_OUT> skip(long l) {
        if (l >= 0L) {
            if (l == 0L) {
                return this;
            }
            return SliceOps.makeRef(this, l, -1L);
        }
        throw new IllegalArgumentException(Long.toString(l));
    }

    @Override
    public final Stream<P_OUT> sorted() {
        return SortedOps.makeRef(this);
    }

    @Override
    public final Stream<P_OUT> sorted(Comparator<? super P_OUT> comparator) {
        return SortedOps.makeRef(this, comparator);
    }

    @Override
    public final Object[] toArray() {
        return this.toArray((IntFunction<A[]>)_$$Lambda$ReferencePipeline$n3O_UMTjTSOeDSKD1yhh_2N2rRU.INSTANCE);
    }

    @Override
    public final <A> A[] toArray(IntFunction<A[]> intFunction) {
        return Nodes.flatten(this.evaluateToArrayNode(intFunction), intFunction).asArray(intFunction);
    }

    @Override
    public Stream<P_OUT> unordered() {
        if (!this.isOrdered()) {
            return this;
        }
        return new StatelessOp<P_OUT, P_OUT>(this, StreamShape.REFERENCE, StreamOpFlag.NOT_ORDERED){

            @Override
            public Sink<P_OUT> opWrapSink(int n, Sink<P_OUT> sink) {
                return sink;
            }
        };
    }

    @Override
    public final <P_IN> Spliterator<P_OUT> wrap(PipelineHelper<P_OUT> pipelineHelper, Supplier<Spliterator<P_IN>> supplier, boolean bl) {
        return new StreamSpliterators.WrappingSpliterator<P_IN, P_OUT>(pipelineHelper, supplier, bl);
    }

    public static class Head<E_IN, E_OUT>
    extends ReferencePipeline<E_IN, E_OUT> {
        public Head(Spliterator<?> spliterator, int n, boolean bl) {
            super(spliterator, n, bl);
        }

        public Head(Supplier<? extends Spliterator<?>> supplier, int n, boolean bl) {
            super(supplier, n, bl);
        }

        @Override
        public void forEach(Consumer<? super E_OUT> consumer) {
            if (!this.isParallel()) {
                this.sourceStageSpliterator().forEachRemaining(consumer);
            } else {
                super.forEach(consumer);
            }
        }

        @Override
        public void forEachOrdered(Consumer<? super E_OUT> consumer) {
            if (!this.isParallel()) {
                this.sourceStageSpliterator().forEachRemaining(consumer);
            } else {
                super.forEachOrdered(consumer);
            }
        }

        @Override
        public final boolean opIsStateful() {
            throw new UnsupportedOperationException();
        }

        @Override
        public final Sink<E_IN> opWrapSink(int n, Sink<E_OUT> sink) {
            throw new UnsupportedOperationException();
        }
    }

    public static abstract class StatefulOp<E_IN, E_OUT>
    extends ReferencePipeline<E_IN, E_OUT> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        public StatefulOp(AbstractPipeline<?, E_IN, ?> abstractPipeline, StreamShape streamShape, int n) {
            super(abstractPipeline, n);
        }

        @Override
        public abstract <P_IN> Node<E_OUT> opEvaluateParallel(PipelineHelper<E_OUT> var1, Spliterator<P_IN> var2, IntFunction<E_OUT[]> var3);

        @Override
        public final boolean opIsStateful() {
            return true;
        }
    }

    public static abstract class StatelessOp<E_IN, E_OUT>
    extends ReferencePipeline<E_IN, E_OUT> {
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

