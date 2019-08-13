/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.AbstractShortCircuitTask;
import java.util.stream.AbstractTask;
import java.util.stream.PipelineHelper;
import java.util.stream.Sink;
import java.util.stream.StreamOpFlag;
import java.util.stream.StreamShape;
import java.util.stream.TerminalOp;
import java.util.stream._$$Lambda$MatchOps$VXR1J72V6WzQCN_3NkesXDVJ1uc;
import java.util.stream._$$Lambda$MatchOps$_LtFSpSMfVwoPv_8p_1cMGGcaHA;
import java.util.stream._$$Lambda$MatchOps$emK14UX33I4_nqH2o5l7hLEVAy8;
import java.util.stream._$$Lambda$MatchOps$kCrOdGmndcbZklBaJ6Z4blQ1F5M;

final class MatchOps {
    private MatchOps() {
    }

    static /* synthetic */ BooleanTerminalSink lambda$makeDouble$3(MatchKind matchKind, DoublePredicate doublePredicate) {
        return new 4MatchSink(matchKind, doublePredicate);
    }

    static /* synthetic */ BooleanTerminalSink lambda$makeInt$1(MatchKind matchKind, IntPredicate intPredicate) {
        return new 2MatchSink(matchKind, intPredicate);
    }

    static /* synthetic */ BooleanTerminalSink lambda$makeLong$2(MatchKind matchKind, LongPredicate longPredicate) {
        return new 3MatchSink(matchKind, longPredicate);
    }

    static /* synthetic */ BooleanTerminalSink lambda$makeRef$0(MatchKind matchKind, Predicate predicate) {
        return new 1MatchSink(matchKind, predicate);
    }

    public static TerminalOp<Double, Boolean> makeDouble(DoublePredicate doublePredicate, MatchKind matchKind) {
        Objects.requireNonNull(doublePredicate);
        Objects.requireNonNull(matchKind);
        return new MatchOp<Double>(StreamShape.DOUBLE_VALUE, matchKind, new _$$Lambda$MatchOps$VXR1J72V6WzQCN_3NkesXDVJ1uc(matchKind, doublePredicate));
    }

    public static TerminalOp<Integer, Boolean> makeInt(IntPredicate intPredicate, MatchKind matchKind) {
        Objects.requireNonNull(intPredicate);
        Objects.requireNonNull(matchKind);
        return new MatchOp<Integer>(StreamShape.INT_VALUE, matchKind, new _$$Lambda$MatchOps$emK14UX33I4_nqH2o5l7hLEVAy8(matchKind, intPredicate));
    }

    public static TerminalOp<Long, Boolean> makeLong(LongPredicate longPredicate, MatchKind matchKind) {
        Objects.requireNonNull(longPredicate);
        Objects.requireNonNull(matchKind);
        return new MatchOp<Long>(StreamShape.LONG_VALUE, matchKind, new _$$Lambda$MatchOps$kCrOdGmndcbZklBaJ6Z4blQ1F5M(matchKind, longPredicate));
    }

    public static <T> TerminalOp<T, Boolean> makeRef(Predicate<? super T> predicate, MatchKind matchKind) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(matchKind);
        return new MatchOp(StreamShape.REFERENCE, matchKind, new _$$Lambda$MatchOps$_LtFSpSMfVwoPv_8p_1cMGGcaHA(matchKind, predicate));
    }

    class 1MatchSink
    extends BooleanTerminalSink<T> {
        final /* synthetic */ Predicate val$predicate;

        1MatchSink() {
            this.val$predicate = var2_2;
            super(val$matchKind);
        }

        @Override
        public void accept(T t) {
            if (!this.stop && this.val$predicate.test(t) == val$matchKind.stopOnPredicateMatches) {
                this.stop = true;
                this.value = val$matchKind.shortCircuitResult;
            }
        }
    }

    class 2MatchSink
    extends BooleanTerminalSink<Integer>
    implements Sink.OfInt {
        final /* synthetic */ IntPredicate val$predicate;

        2MatchSink() {
            this.val$predicate = var2_2;
            super(val$matchKind);
        }

        @Override
        public void accept(int n) {
            if (!this.stop && this.val$predicate.test(n) == val$matchKind.stopOnPredicateMatches) {
                this.stop = true;
                this.value = val$matchKind.shortCircuitResult;
            }
        }
    }

    class 3MatchSink
    extends BooleanTerminalSink<Long>
    implements Sink.OfLong {
        final /* synthetic */ LongPredicate val$predicate;

        3MatchSink() {
            this.val$predicate = var2_2;
            super(val$matchKind);
        }

        @Override
        public void accept(long l) {
            if (!this.stop && this.val$predicate.test(l) == val$matchKind.stopOnPredicateMatches) {
                this.stop = true;
                this.value = val$matchKind.shortCircuitResult;
            }
        }
    }

    class 4MatchSink
    extends BooleanTerminalSink<Double>
    implements Sink.OfDouble {
        final /* synthetic */ DoublePredicate val$predicate;

        4MatchSink() {
            this.val$predicate = var2_2;
            super(val$matchKind);
        }

        @Override
        public void accept(double d) {
            if (!this.stop && this.val$predicate.test(d) == val$matchKind.stopOnPredicateMatches) {
                this.stop = true;
                this.value = val$matchKind.shortCircuitResult;
            }
        }
    }

    private static abstract class BooleanTerminalSink<T>
    implements Sink<T> {
        boolean stop;
        boolean value;

        BooleanTerminalSink(MatchKind matchKind) {
            this.value = matchKind.shortCircuitResult ^ true;
        }

        @Override
        public boolean cancellationRequested() {
            return this.stop;
        }

        public boolean getAndClearState() {
            return this.value;
        }
    }

    static enum MatchKind {
        ANY(true, true),
        ALL(false, false),
        NONE(true, false);
        
        private final boolean shortCircuitResult;
        private final boolean stopOnPredicateMatches;

        private MatchKind(boolean bl, boolean bl2) {
            this.stopOnPredicateMatches = bl;
            this.shortCircuitResult = bl2;
        }
    }

    private static final class MatchOp<T>
    implements TerminalOp<T, Boolean> {
        private final StreamShape inputShape;
        final MatchKind matchKind;
        final Supplier<BooleanTerminalSink<T>> sinkSupplier;

        MatchOp(StreamShape streamShape, MatchKind matchKind, Supplier<BooleanTerminalSink<T>> supplier) {
            this.inputShape = streamShape;
            this.matchKind = matchKind;
            this.sinkSupplier = supplier;
        }

        @Override
        public <S> Boolean evaluateParallel(PipelineHelper<T> pipelineHelper, Spliterator<S> spliterator) {
            return (Boolean)new MatchTask<S, T>(this, pipelineHelper, spliterator).invoke();
        }

        @Override
        public <S> Boolean evaluateSequential(PipelineHelper<T> pipelineHelper, Spliterator<S> spliterator) {
            return pipelineHelper.wrapAndCopyInto(this.sinkSupplier.get(), spliterator).getAndClearState();
        }

        @Override
        public int getOpFlags() {
            return StreamOpFlag.IS_SHORT_CIRCUIT | StreamOpFlag.NOT_ORDERED;
        }

        @Override
        public StreamShape inputShape() {
            return this.inputShape;
        }
    }

    private static final class MatchTask<P_IN, P_OUT>
    extends AbstractShortCircuitTask<P_IN, P_OUT, Boolean, MatchTask<P_IN, P_OUT>> {
        private final MatchOp<P_OUT> op;

        MatchTask(MatchOp<P_OUT> matchOp, PipelineHelper<P_OUT> pipelineHelper, Spliterator<P_IN> spliterator) {
            super(pipelineHelper, spliterator);
            this.op = matchOp;
        }

        MatchTask(MatchTask<P_IN, P_OUT> matchTask, Spliterator<P_IN> spliterator) {
            super(matchTask, spliterator);
            this.op = matchTask.op;
        }

        @Override
        protected Boolean doLeaf() {
            boolean bl = this.helper.wrapAndCopyInto(this.op.sinkSupplier.get(), this.spliterator).getAndClearState();
            if (bl == this.op.matchKind.shortCircuitResult) {
                this.shortCircuit(bl);
            }
            return null;
        }

        @Override
        protected Boolean getEmptyResult() {
            return this.op.matchKind.shortCircuitResult ^ true;
        }

        @Override
        protected MatchTask<P_IN, P_OUT> makeChild(Spliterator<P_IN> spliterator) {
            return new MatchTask<P_IN, P_OUT>(this, spliterator);
        }
    }

}

