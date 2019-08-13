/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$XcCQq8gYss3OrVBeBIbyvBZpOz8
 *  java.util.stream.-$$Lambda$YpedFjT304pmSbvYSkjP1adjrAo
 *  java.util.stream.-$$Lambda$bjSXRjZ5UYwAzkW-XPKwqbJ9BRQ
 *  java.util.stream.-$$Lambda$l1vHMFuOMPAI8WfDQT6zNBh_B7U
 *  java.util.stream.-$$Lambda$mpgi0fNdNmnu9LkjGowG335UgGc
 *  java.util.stream.-$$Lambda$opQ7JxjVCJzqzgTxGU3LVtqC7is
 *  java.util.stream.-$$Lambda$timJ2_RnT5GwsTSax4Q0EMpi4pc
 *  java.util.stream.-$$Lambda$yrGzfUbU_IPNM4mz8V8FlMUHCw4
 */
package java.util.stream;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.-$;
import java.util.stream.AbstractShortCircuitTask;
import java.util.stream.AbstractTask;
import java.util.stream.PipelineHelper;
import java.util.stream.Sink;
import java.util.stream.StreamOpFlag;
import java.util.stream.StreamShape;
import java.util.stream.TerminalOp;
import java.util.stream.TerminalSink;
import java.util.stream._$$Lambda$XcCQq8gYss3OrVBeBIbyvBZpOz8;
import java.util.stream._$$Lambda$YpedFjT304pmSbvYSkjP1adjrAo;
import java.util.stream._$$Lambda$bjSXRjZ5UYwAzkW_XPKwqbJ9BRQ;
import java.util.stream._$$Lambda$l1vHMFuOMPAI8WfDQT6zNBh_B7U;
import java.util.stream._$$Lambda$mpgi0fNdNmnu9LkjGowG335UgGc;
import java.util.stream._$$Lambda$opQ7JxjVCJzqzgTxGU3LVtqC7is;
import java.util.stream._$$Lambda$timJ2_RnT5GwsTSax4Q0EMpi4pc;
import java.util.stream._$$Lambda$yrGzfUbU_IPNM4mz8V8FlMUHCw4;

final class FindOps {
    private FindOps() {
    }

    public static TerminalOp<Double, OptionalDouble> makeDouble(boolean bl) {
        return new FindOp<Double, OptionalDouble>(bl, StreamShape.DOUBLE_VALUE, OptionalDouble.empty(), (Predicate<OptionalDouble>)_$$Lambda$yrGzfUbU_IPNM4mz8V8FlMUHCw4.INSTANCE, (Supplier<TerminalSink<Double, OptionalDouble>>)_$$Lambda$l1vHMFuOMPAI8WfDQT6zNBh_B7U.INSTANCE);
    }

    public static TerminalOp<Integer, OptionalInt> makeInt(boolean bl) {
        return new FindOp<Integer, OptionalInt>(bl, StreamShape.INT_VALUE, OptionalInt.empty(), (Predicate<OptionalInt>)_$$Lambda$timJ2_RnT5GwsTSax4Q0EMpi4pc.INSTANCE, (Supplier<TerminalSink<Integer, OptionalInt>>)_$$Lambda$mpgi0fNdNmnu9LkjGowG335UgGc.INSTANCE);
    }

    public static TerminalOp<Long, OptionalLong> makeLong(boolean bl) {
        return new FindOp<Long, OptionalLong>(bl, StreamShape.LONG_VALUE, OptionalLong.empty(), (Predicate<OptionalLong>)_$$Lambda$XcCQq8gYss3OrVBeBIbyvBZpOz8.INSTANCE, (Supplier<TerminalSink<Long, OptionalLong>>)_$$Lambda$YpedFjT304pmSbvYSkjP1adjrAo.INSTANCE);
    }

    public static <T> TerminalOp<T, Optional<T>> makeRef(boolean bl) {
        return new FindOp(bl, StreamShape.REFERENCE, Optional.empty(), _$$Lambda$bjSXRjZ5UYwAzkW_XPKwqbJ9BRQ.INSTANCE, _$$Lambda$opQ7JxjVCJzqzgTxGU3LVtqC7is.INSTANCE);
    }

    private static final class FindOp<T, O>
    implements TerminalOp<T, O> {
        final O emptyValue;
        final boolean mustFindFirst;
        final Predicate<O> presentPredicate;
        private final StreamShape shape;
        final Supplier<TerminalSink<T, O>> sinkSupplier;

        FindOp(boolean bl, StreamShape streamShape, O o, Predicate<O> predicate, Supplier<TerminalSink<T, O>> supplier) {
            this.mustFindFirst = bl;
            this.shape = streamShape;
            this.emptyValue = o;
            this.presentPredicate = predicate;
            this.sinkSupplier = supplier;
        }

        @Override
        public <P_IN> O evaluateParallel(PipelineHelper<T> pipelineHelper, Spliterator<P_IN> spliterator) {
            return (O)new FindTask(this, pipelineHelper, spliterator).invoke();
        }

        @Override
        public <S> O evaluateSequential(PipelineHelper<T> pipelineHelper, Spliterator<S> spliterator) {
            if ((pipelineHelper = pipelineHelper.wrapAndCopyInto(this.sinkSupplier.get(), spliterator).get()) == null) {
                pipelineHelper = this.emptyValue;
            }
            return (O)pipelineHelper;
        }

        @Override
        public int getOpFlags() {
            int n = StreamOpFlag.IS_SHORT_CIRCUIT;
            int n2 = this.mustFindFirst ? 0 : StreamOpFlag.NOT_ORDERED;
            return n | n2;
        }

        @Override
        public StreamShape inputShape() {
            return this.shape;
        }
    }

    private static abstract class FindSink<T, O>
    implements TerminalSink<T, O> {
        boolean hasValue;
        T value;

        FindSink() {
        }

        @Override
        public void accept(T t) {
            if (!this.hasValue) {
                this.hasValue = true;
                this.value = t;
            }
        }

        @Override
        public boolean cancellationRequested() {
            return this.hasValue;
        }

        static final class OfDouble
        extends FindSink<Double, OptionalDouble>
        implements Sink.OfDouble {
            OfDouble() {
            }

            @Override
            public void accept(double d) {
                this.accept(d);
            }

            @Override
            public OptionalDouble get() {
                OptionalDouble optionalDouble = this.hasValue ? OptionalDouble.of((Double)this.value) : null;
                return optionalDouble;
            }
        }

        static final class OfInt
        extends FindSink<Integer, OptionalInt>
        implements Sink.OfInt {
            OfInt() {
            }

            @Override
            public void accept(int n) {
                this.accept(n);
            }

            @Override
            public OptionalInt get() {
                OptionalInt optionalInt = this.hasValue ? OptionalInt.of((Integer)this.value) : null;
                return optionalInt;
            }
        }

        static final class OfLong
        extends FindSink<Long, OptionalLong>
        implements Sink.OfLong {
            OfLong() {
            }

            @Override
            public void accept(long l) {
                this.accept(l);
            }

            @Override
            public OptionalLong get() {
                OptionalLong optionalLong = this.hasValue ? OptionalLong.of((Long)this.value) : null;
                return optionalLong;
            }
        }

        static final class OfRef<T>
        extends FindSink<T, Optional<T>> {
            OfRef() {
            }

            @Override
            public Optional<T> get() {
                Optional<Object> optional = this.hasValue ? Optional.of(this.value) : null;
                return optional;
            }
        }

    }

    private static final class FindTask<P_IN, P_OUT, O>
    extends AbstractShortCircuitTask<P_IN, P_OUT, O, FindTask<P_IN, P_OUT, O>> {
        private final FindOp<P_OUT, O> op;

        FindTask(FindOp<P_OUT, O> findOp, PipelineHelper<P_OUT> pipelineHelper, Spliterator<P_IN> spliterator) {
            super(pipelineHelper, spliterator);
            this.op = findOp;
        }

        FindTask(FindTask<P_IN, P_OUT, O> findTask, Spliterator<P_IN> spliterator) {
            super(findTask, spliterator);
            this.op = findTask.op;
        }

        private void foundResult(O o) {
            if (this.isLeftmostNode()) {
                this.shortCircuit(o);
            } else {
                this.cancelLaterNodes();
            }
        }

        @Override
        protected O doLeaf() {
            Object t = this.helper.wrapAndCopyInto(this.op.sinkSupplier.get(), this.spliterator).get();
            if (!this.op.mustFindFirst) {
                if (t != null) {
                    this.shortCircuit(t);
                }
                return null;
            }
            if (t != null) {
                this.foundResult(t);
                return (O)t;
            }
            return null;
        }

        @Override
        protected O getEmptyResult() {
            return this.op.emptyValue;
        }

        @Override
        protected FindTask<P_IN, P_OUT, O> makeChild(Spliterator<P_IN> spliterator) {
            return new FindTask<P_IN, P_OUT, O>(this, spliterator);
        }

        @Override
        public void onCompletion(CountedCompleter<?> countedCompleter) {
            if (this.op.mustFindFirst) {
                FindTask findTask = (FindTask)this.leftChild;
                FindTask findTask2 = null;
                while (findTask != findTask2) {
                    findTask2 = (FindTask)findTask.getLocalResult();
                    if (findTask2 != null && this.op.presentPredicate.test(findTask2)) {
                        this.setLocalResult(findTask2);
                        this.foundResult(findTask2);
                        break;
                    }
                    findTask2 = findTask;
                    findTask = (FindTask)this.rightChild;
                }
            }
            super.onCompletion(countedCompleter);
        }
    }

}

