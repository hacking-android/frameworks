/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.AbstractTask;
import java.util.stream.Collector;
import java.util.stream.PipelineHelper;
import java.util.stream.Sink;
import java.util.stream.StreamOpFlag;
import java.util.stream.StreamShape;
import java.util.stream.TerminalOp;
import java.util.stream.TerminalSink;

final class ReduceOps {
    private ReduceOps() {
    }

    public static TerminalOp<Double, Double> makeDouble(final double d, final DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        return new ReduceOp<Double, Double, 11ReducingSink>(StreamShape.DOUBLE_VALUE){

            @Override
            public 11ReducingSink makeSink() {
                return new 11ReducingSink(d, doubleBinaryOperator);
            }
        };
    }

    public static TerminalOp<Double, OptionalDouble> makeDouble(final DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        return new ReduceOp<Double, OptionalDouble, 12ReducingSink>(StreamShape.DOUBLE_VALUE){

            @Override
            public 12ReducingSink makeSink() {
                return new 12ReducingSink();
            }
        };
    }

    public static <R> TerminalOp<Double, R> makeDouble(final Supplier<R> supplier, final ObjDoubleConsumer<R> objDoubleConsumer, final BinaryOperator<R> binaryOperator) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objDoubleConsumer);
        Objects.requireNonNull(binaryOperator);
        return new ReduceOp<Double, R, 13ReducingSink>(StreamShape.DOUBLE_VALUE){

            @Override
            public 13ReducingSink makeSink() {
                return new 13ReducingSink(supplier, objDoubleConsumer, binaryOperator);
            }
        };
    }

    public static TerminalOp<Integer, Integer> makeInt(final int n, final IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        return new ReduceOp<Integer, Integer, 5ReducingSink>(StreamShape.INT_VALUE){

            @Override
            public 5ReducingSink makeSink() {
                return new 5ReducingSink(n, intBinaryOperator);
            }
        };
    }

    public static TerminalOp<Integer, OptionalInt> makeInt(final IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        return new ReduceOp<Integer, OptionalInt, 6ReducingSink>(StreamShape.INT_VALUE){

            @Override
            public 6ReducingSink makeSink() {
                return new 6ReducingSink();
            }
        };
    }

    public static <R> TerminalOp<Integer, R> makeInt(final Supplier<R> supplier, final ObjIntConsumer<R> objIntConsumer, final BinaryOperator<R> binaryOperator) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objIntConsumer);
        Objects.requireNonNull(binaryOperator);
        return new ReduceOp<Integer, R, 7ReducingSink>(StreamShape.INT_VALUE){

            @Override
            public 7ReducingSink makeSink() {
                return new 7ReducingSink(supplier, objIntConsumer, binaryOperator);
            }
        };
    }

    public static TerminalOp<Long, Long> makeLong(final long l, final LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        return new ReduceOp<Long, Long, 8ReducingSink>(StreamShape.LONG_VALUE){

            @Override
            public 8ReducingSink makeSink() {
                return new 8ReducingSink(l, longBinaryOperator);
            }
        };
    }

    public static TerminalOp<Long, OptionalLong> makeLong(final LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        return new ReduceOp<Long, OptionalLong, 9ReducingSink>(StreamShape.LONG_VALUE){

            @Override
            public 9ReducingSink makeSink() {
                return new 9ReducingSink();
            }
        };
    }

    public static <R> TerminalOp<Long, R> makeLong(final Supplier<R> supplier, final ObjLongConsumer<R> objLongConsumer, final BinaryOperator<R> binaryOperator) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objLongConsumer);
        Objects.requireNonNull(binaryOperator);
        return new ReduceOp<Long, R, 10ReducingSink>(StreamShape.LONG_VALUE){

            @Override
            public 10ReducingSink makeSink() {
                return new 10ReducingSink(supplier, objLongConsumer, binaryOperator);
            }
        };
    }

    public static <T, U> TerminalOp<T, U> makeRef(final U u, final BiFunction<U, ? super T, U> biFunction, final BinaryOperator<U> binaryOperator) {
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(binaryOperator);
        return new ReduceOp<T, U, 1ReducingSink>(StreamShape.REFERENCE){

            @Override
            public 1ReducingSink makeSink() {
                return new 1ReducingSink(u, biFunction, binaryOperator);
            }
        };
    }

    public static <T> TerminalOp<T, Optional<T>> makeRef(final BinaryOperator<T> binaryOperator) {
        Objects.requireNonNull(binaryOperator);
        return new ReduceOp<T, Optional<T>, 2ReducingSink>(StreamShape.REFERENCE){

            @Override
            public 2ReducingSink makeSink() {
                return new 2ReducingSink();
            }
        };
    }

    public static <T, R> TerminalOp<T, R> makeRef(final Supplier<R> supplier, final BiConsumer<R, ? super T> biConsumer, final BiConsumer<R, R> biConsumer2) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(biConsumer);
        Objects.requireNonNull(biConsumer2);
        return new ReduceOp<T, R, 4ReducingSink>(StreamShape.REFERENCE){

            @Override
            public 4ReducingSink makeSink() {
                return new 4ReducingSink(supplier, biConsumer, biConsumer2);
            }
        };
    }

    public static <T, I> TerminalOp<T, I> makeRef(final Collector<? super T, I, ?> collector) {
        final Supplier<I> supplier = Objects.requireNonNull(collector).supplier();
        final BiConsumer<I, ? super T> biConsumer = collector.accumulator();
        final BinaryOperator<I> binaryOperator = collector.combiner();
        return new ReduceOp<T, I, 3ReducingSink>(StreamShape.REFERENCE){

            @Override
            public int getOpFlags() {
                int n = collector.characteristics().contains((Object)Collector.Characteristics.UNORDERED) ? StreamOpFlag.NOT_ORDERED : 0;
                return n;
            }

            @Override
            public 3ReducingSink makeSink() {
                return new 3ReducingSink(supplier, biConsumer, binaryOperator);
            }
        };
    }

    class 10ReducingSink
    extends Box<R>
    implements AccumulatingSink<Long, R, 10ReducingSink>,
    Sink.OfLong {
        final /* synthetic */ ObjLongConsumer val$accumulator;
        final /* synthetic */ BinaryOperator val$combiner;

        10ReducingSink() {
            this.val$accumulator = var2_2;
            this.val$combiner = var3_3;
        }

        @Override
        public void accept(long l) {
            this.val$accumulator.accept(this.state, l);
        }

        @Override
        public void begin(long l) {
            this.state = val$supplier.get();
        }

        @Override
        public void combine(10ReducingSink reducingSink) {
            this.state = this.val$combiner.apply(this.state, reducingSink.state);
        }
    }

    class 11ReducingSink
    implements AccumulatingSink<Double, Double, 11ReducingSink>,
    Sink.OfDouble {
        private double state;
        final /* synthetic */ DoubleBinaryOperator val$operator;

        11ReducingSink() {
            this.val$operator = var3_2;
        }

        @Override
        public void accept(double d) {
            this.state = this.val$operator.applyAsDouble(this.state, d);
        }

        @Override
        public void begin(long l) {
            this.state = val$identity;
        }

        @Override
        public void combine(11ReducingSink reducingSink) {
            this.accept(reducingSink.state);
        }

        @Override
        public Double get() {
            return this.state;
        }
    }

    class 12ReducingSink
    implements AccumulatingSink<Double, OptionalDouble, 12ReducingSink>,
    Sink.OfDouble {
        private boolean empty;
        private double state;

        12ReducingSink() {
        }

        @Override
        public void accept(double d) {
            if (this.empty) {
                this.empty = false;
                this.state = d;
            } else {
                this.state = DoubleBinaryOperator.this.applyAsDouble(this.state, d);
            }
        }

        @Override
        public void begin(long l) {
            this.empty = true;
            this.state = 0.0;
        }

        @Override
        public void combine(12ReducingSink reducingSink) {
            if (!reducingSink.empty) {
                this.accept(reducingSink.state);
            }
        }

        @Override
        public OptionalDouble get() {
            OptionalDouble optionalDouble = this.empty ? OptionalDouble.empty() : OptionalDouble.of(this.state);
            return optionalDouble;
        }
    }

    class 13ReducingSink
    extends Box<R>
    implements AccumulatingSink<Double, R, 13ReducingSink>,
    Sink.OfDouble {
        final /* synthetic */ ObjDoubleConsumer val$accumulator;
        final /* synthetic */ BinaryOperator val$combiner;

        13ReducingSink() {
            this.val$accumulator = var2_2;
            this.val$combiner = var3_3;
        }

        @Override
        public void accept(double d) {
            this.val$accumulator.accept(this.state, d);
        }

        @Override
        public void begin(long l) {
            this.state = val$supplier.get();
        }

        @Override
        public void combine(13ReducingSink reducingSink) {
            this.state = this.val$combiner.apply(this.state, reducingSink.state);
        }
    }

    class 1ReducingSink
    extends Box<U>
    implements AccumulatingSink<T, U, 1ReducingSink> {
        final /* synthetic */ BinaryOperator val$combiner;
        final /* synthetic */ BiFunction val$reducer;

        1ReducingSink() {
            this.val$reducer = var2_2;
            this.val$combiner = var3_3;
        }

        @Override
        public void accept(T t) {
            this.state = this.val$reducer.apply(this.state, t);
        }

        @Override
        public void begin(long l) {
            this.state = val$seed;
        }

        @Override
        public void combine(1ReducingSink reducingSink) {
            this.state = this.val$combiner.apply(this.state, reducingSink.state);
        }
    }

    class 2ReducingSink
    implements AccumulatingSink<T, Optional<T>, 2ReducingSink> {
        private boolean empty;
        private T state;

        2ReducingSink() {
        }

        @Override
        public void accept(T t) {
            if (this.empty) {
                this.empty = false;
                this.state = t;
            } else {
                this.state = BinaryOperator.this.apply(this.state, t);
            }
        }

        @Override
        public void begin(long l) {
            this.empty = true;
            this.state = null;
        }

        @Override
        public void combine(2ReducingSink reducingSink) {
            if (!reducingSink.empty) {
                this.accept(reducingSink.state);
            }
        }

        @Override
        public Optional<T> get() {
            Optional optional = this.empty ? Optional.empty() : Optional.of(this.state);
            return optional;
        }
    }

    class 3ReducingSink
    extends Box<I>
    implements AccumulatingSink<T, I, 3ReducingSink> {
        final /* synthetic */ BiConsumer val$accumulator;
        final /* synthetic */ BinaryOperator val$combiner;

        3ReducingSink() {
            this.val$accumulator = var2_2;
            this.val$combiner = var3_3;
        }

        @Override
        public void accept(T t) {
            this.val$accumulator.accept(this.state, t);
        }

        @Override
        public void begin(long l) {
            this.state = val$supplier.get();
        }

        @Override
        public void combine(3ReducingSink reducingSink) {
            this.state = this.val$combiner.apply(this.state, reducingSink.state);
        }
    }

    class 4ReducingSink
    extends Box<R>
    implements AccumulatingSink<T, R, 4ReducingSink> {
        final /* synthetic */ BiConsumer val$accumulator;
        final /* synthetic */ BiConsumer val$reducer;

        4ReducingSink() {
            this.val$accumulator = var2_2;
            this.val$reducer = var3_3;
        }

        @Override
        public void accept(T t) {
            this.val$accumulator.accept(this.state, t);
        }

        @Override
        public void begin(long l) {
            this.state = val$seedFactory.get();
        }

        @Override
        public void combine(4ReducingSink reducingSink) {
            this.val$reducer.accept(this.state, reducingSink.state);
        }
    }

    class 5ReducingSink
    implements AccumulatingSink<Integer, Integer, 5ReducingSink>,
    Sink.OfInt {
        private int state;
        final /* synthetic */ IntBinaryOperator val$operator;

        5ReducingSink() {
            this.val$operator = var2_2;
        }

        @Override
        public void accept(int n) {
            this.state = this.val$operator.applyAsInt(this.state, n);
        }

        @Override
        public void begin(long l) {
            this.state = val$identity;
        }

        @Override
        public void combine(5ReducingSink reducingSink) {
            this.accept(reducingSink.state);
        }

        @Override
        public Integer get() {
            return this.state;
        }
    }

    class 6ReducingSink
    implements AccumulatingSink<Integer, OptionalInt, 6ReducingSink>,
    Sink.OfInt {
        private boolean empty;
        private int state;

        6ReducingSink() {
        }

        @Override
        public void accept(int n) {
            if (this.empty) {
                this.empty = false;
                this.state = n;
            } else {
                this.state = IntBinaryOperator.this.applyAsInt(this.state, n);
            }
        }

        @Override
        public void begin(long l) {
            this.empty = true;
            this.state = 0;
        }

        @Override
        public void combine(6ReducingSink reducingSink) {
            if (!reducingSink.empty) {
                this.accept(reducingSink.state);
            }
        }

        @Override
        public OptionalInt get() {
            OptionalInt optionalInt = this.empty ? OptionalInt.empty() : OptionalInt.of(this.state);
            return optionalInt;
        }
    }

    class 7ReducingSink
    extends Box<R>
    implements AccumulatingSink<Integer, R, 7ReducingSink>,
    Sink.OfInt {
        final /* synthetic */ ObjIntConsumer val$accumulator;
        final /* synthetic */ BinaryOperator val$combiner;

        7ReducingSink() {
            this.val$accumulator = var2_2;
            this.val$combiner = var3_3;
        }

        @Override
        public void accept(int n) {
            this.val$accumulator.accept(this.state, n);
        }

        @Override
        public void begin(long l) {
            this.state = val$supplier.get();
        }

        @Override
        public void combine(7ReducingSink reducingSink) {
            this.state = this.val$combiner.apply(this.state, reducingSink.state);
        }
    }

    class 8ReducingSink
    implements AccumulatingSink<Long, Long, 8ReducingSink>,
    Sink.OfLong {
        private long state;
        final /* synthetic */ LongBinaryOperator val$operator;

        8ReducingSink() {
            this.val$operator = var3_2;
        }

        @Override
        public void accept(long l) {
            this.state = this.val$operator.applyAsLong(this.state, l);
        }

        @Override
        public void begin(long l) {
            this.state = val$identity;
        }

        @Override
        public void combine(8ReducingSink reducingSink) {
            this.accept(reducingSink.state);
        }

        @Override
        public Long get() {
            return this.state;
        }
    }

    class 9ReducingSink
    implements AccumulatingSink<Long, OptionalLong, 9ReducingSink>,
    Sink.OfLong {
        private boolean empty;
        private long state;

        9ReducingSink() {
        }

        @Override
        public void accept(long l) {
            if (this.empty) {
                this.empty = false;
                this.state = l;
            } else {
                this.state = LongBinaryOperator.this.applyAsLong(this.state, l);
            }
        }

        @Override
        public void begin(long l) {
            this.empty = true;
            this.state = 0L;
        }

        @Override
        public void combine(9ReducingSink reducingSink) {
            if (!reducingSink.empty) {
                this.accept(reducingSink.state);
            }
        }

        @Override
        public OptionalLong get() {
            OptionalLong optionalLong = this.empty ? OptionalLong.empty() : OptionalLong.of(this.state);
            return optionalLong;
        }
    }

    private static interface AccumulatingSink<T, R, K extends AccumulatingSink<T, R, K>>
    extends TerminalSink<T, R> {
        public void combine(K var1);
    }

    private static abstract class Box<U> {
        U state;

        Box() {
        }

        public U get() {
            return this.state;
        }
    }

    private static abstract class ReduceOp<T, R, S extends AccumulatingSink<T, R, S>>
    implements TerminalOp<T, R> {
        private final StreamShape inputShape;

        ReduceOp(StreamShape streamShape) {
            this.inputShape = streamShape;
        }

        @Override
        public <P_IN> R evaluateParallel(PipelineHelper<T> pipelineHelper, Spliterator<P_IN> spliterator) {
            return (R)((AccumulatingSink)new ReduceTask(this, pipelineHelper, spliterator).invoke()).get();
        }

        @Override
        public <P_IN> R evaluateSequential(PipelineHelper<T> pipelineHelper, Spliterator<P_IN> spliterator) {
            return (R)((AccumulatingSink)pipelineHelper.wrapAndCopyInto(this.makeSink(), spliterator)).get();
        }

        @Override
        public StreamShape inputShape() {
            return this.inputShape;
        }

        public abstract S makeSink();
    }

    private static final class ReduceTask<P_IN, P_OUT, R, S extends AccumulatingSink<P_OUT, R, S>>
    extends AbstractTask<P_IN, P_OUT, S, ReduceTask<P_IN, P_OUT, R, S>> {
        private final ReduceOp<P_OUT, R, S> op;

        ReduceTask(ReduceOp<P_OUT, R, S> reduceOp, PipelineHelper<P_OUT> pipelineHelper, Spliterator<P_IN> spliterator) {
            super(pipelineHelper, spliterator);
            this.op = reduceOp;
        }

        ReduceTask(ReduceTask<P_IN, P_OUT, R, S> reduceTask, Spliterator<P_IN> spliterator) {
            super(reduceTask, spliterator);
            this.op = reduceTask.op;
        }

        @Override
        protected S doLeaf() {
            return (S)((AccumulatingSink)this.helper.wrapAndCopyInto(this.op.makeSink(), this.spliterator));
        }

        @Override
        protected ReduceTask<P_IN, P_OUT, R, S> makeChild(Spliterator<P_IN> spliterator) {
            return new ReduceTask<P_IN, P_OUT, R, S>(this, spliterator);
        }

        @Override
        public void onCompletion(CountedCompleter<?> countedCompleter) {
            if (!this.isLeaf()) {
                AccumulatingSink accumulatingSink = (AccumulatingSink)((ReduceTask)this.leftChild).getLocalResult();
                accumulatingSink.combine((AccumulatingSink)((ReduceTask)this.rightChild).getLocalResult());
                this.setLocalResult(accumulatingSink);
            }
            super.onCompletion(countedCompleter);
        }
    }

}

