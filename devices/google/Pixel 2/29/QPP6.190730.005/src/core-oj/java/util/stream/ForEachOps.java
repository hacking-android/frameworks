/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$ForEachOps
 *  java.util.stream.-$$Lambda$ForEachOps$ForEachOrderedTask
 *  java.util.stream.-$$Lambda$ForEachOps$ForEachOrderedTask$XLqga2XPr4V7tlS8H12fiz-In-o
 */
package java.util.stream;

import java.util.Objects;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import java.util.stream.-$;
import java.util.stream.AbstractTask;
import java.util.stream.Node;
import java.util.stream.PipelineHelper;
import java.util.stream.Sink;
import java.util.stream.StreamOpFlag;
import java.util.stream.StreamShape;
import java.util.stream.TerminalOp;
import java.util.stream.TerminalSink;
import java.util.stream._$$Lambda$ForEachOps$ForEachOrderedTask$XLqga2XPr4V7tlS8H12fiz_In_o;

final class ForEachOps {
    private ForEachOps() {
    }

    public static TerminalOp<Double, Void> makeDouble(DoubleConsumer doubleConsumer, boolean bl) {
        Objects.requireNonNull(doubleConsumer);
        return new ForEachOp.OfDouble(doubleConsumer, bl);
    }

    public static TerminalOp<Integer, Void> makeInt(IntConsumer intConsumer, boolean bl) {
        Objects.requireNonNull(intConsumer);
        return new ForEachOp.OfInt(intConsumer, bl);
    }

    public static TerminalOp<Long, Void> makeLong(LongConsumer longConsumer, boolean bl) {
        Objects.requireNonNull(longConsumer);
        return new ForEachOp.OfLong(longConsumer, bl);
    }

    public static <T> TerminalOp<T, Void> makeRef(Consumer<? super T> consumer, boolean bl) {
        Objects.requireNonNull(consumer);
        return new ForEachOp.OfRef<T>(consumer, bl);
    }

    static abstract class ForEachOp<T>
    implements TerminalOp<T, Void>,
    TerminalSink<T, Void> {
        private final boolean ordered;

        protected ForEachOp(boolean bl) {
            this.ordered = bl;
        }

        @Override
        public <S> Void evaluateParallel(PipelineHelper<T> pipelineHelper, Spliterator<S> spliterator) {
            if (this.ordered) {
                new ForEachOrderedTask<S, T>(pipelineHelper, spliterator, this).invoke();
            } else {
                new ForEachTask<S, T>(pipelineHelper, spliterator, pipelineHelper.wrapSink(this)).invoke();
            }
            return null;
        }

        @Override
        public <S> Void evaluateSequential(PipelineHelper<T> pipelineHelper, Spliterator<S> spliterator) {
            return pipelineHelper.wrapAndCopyInto(this, spliterator).get();
        }

        @Override
        public Void get() {
            return null;
        }

        @Override
        public int getOpFlags() {
            int n = this.ordered ? 0 : StreamOpFlag.NOT_ORDERED;
            return n;
        }

        static final class OfDouble
        extends ForEachOp<Double>
        implements Sink.OfDouble {
            final DoubleConsumer consumer;

            OfDouble(DoubleConsumer doubleConsumer, boolean bl) {
                super(bl);
                this.consumer = doubleConsumer;
            }

            @Override
            public void accept(double d) {
                this.consumer.accept(d);
            }

            @Override
            public StreamShape inputShape() {
                return StreamShape.DOUBLE_VALUE;
            }
        }

        static final class OfInt
        extends ForEachOp<Integer>
        implements Sink.OfInt {
            final IntConsumer consumer;

            OfInt(IntConsumer intConsumer, boolean bl) {
                super(bl);
                this.consumer = intConsumer;
            }

            @Override
            public void accept(int n) {
                this.consumer.accept(n);
            }

            @Override
            public StreamShape inputShape() {
                return StreamShape.INT_VALUE;
            }
        }

        static final class OfLong
        extends ForEachOp<Long>
        implements Sink.OfLong {
            final LongConsumer consumer;

            OfLong(LongConsumer longConsumer, boolean bl) {
                super(bl);
                this.consumer = longConsumer;
            }

            @Override
            public void accept(long l) {
                this.consumer.accept(l);
            }

            @Override
            public StreamShape inputShape() {
                return StreamShape.LONG_VALUE;
            }
        }

        static final class OfRef<T>
        extends ForEachOp<T> {
            final Consumer<? super T> consumer;

            OfRef(Consumer<? super T> consumer, boolean bl) {
                super(bl);
                this.consumer = consumer;
            }

            @Override
            public void accept(T t) {
                this.consumer.accept(t);
            }
        }

    }

    static final class ForEachOrderedTask<S, T>
    extends CountedCompleter<Void> {
        private final Sink<T> action;
        private final ConcurrentHashMap<ForEachOrderedTask<S, T>, ForEachOrderedTask<S, T>> completionMap;
        private final PipelineHelper<T> helper;
        private final ForEachOrderedTask<S, T> leftPredecessor;
        private Node<T> node;
        private Spliterator<S> spliterator;
        private final long targetSize;

        ForEachOrderedTask(ForEachOrderedTask<S, T> forEachOrderedTask, Spliterator<S> spliterator, ForEachOrderedTask<S, T> forEachOrderedTask2) {
            super(forEachOrderedTask);
            this.helper = forEachOrderedTask.helper;
            this.spliterator = spliterator;
            this.targetSize = forEachOrderedTask.targetSize;
            this.completionMap = forEachOrderedTask.completionMap;
            this.action = forEachOrderedTask.action;
            this.leftPredecessor = forEachOrderedTask2;
        }

        protected ForEachOrderedTask(PipelineHelper<T> pipelineHelper, Spliterator<S> spliterator, Sink<T> sink) {
            super(null);
            this.helper = pipelineHelper;
            this.spliterator = spliterator;
            this.targetSize = AbstractTask.suggestTargetSize(spliterator.estimateSize());
            this.completionMap = new ConcurrentHashMap(Math.max(16, AbstractTask.LEAF_TARGET << 1));
            this.action = sink;
            this.leftPredecessor = null;
        }

        private static <S, T> void doCompute(ForEachOrderedTask<S, T> object) {
            Spliterator<S> spliterator;
            Object object2;
            Object object3;
            Spliterator<S> spliterator2 = ((ForEachOrderedTask)object).spliterator;
            long l = ((ForEachOrderedTask)object).targetSize;
            boolean bl = false;
            while (spliterator2.estimateSize() > l && (spliterator = spliterator2.trySplit()) != null) {
                object3 = new ForEachOrderedTask<S, T>((ForEachOrderedTask<S, T>)object, spliterator, ((ForEachOrderedTask)object).leftPredecessor);
                object2 = new ForEachOrderedTask<S, T>((ForEachOrderedTask<S, T>)object, spliterator2, (ForEachOrderedTask<S, T>)object3);
                ((CountedCompleter)object).addToPendingCount(1);
                ((CountedCompleter)object2).addToPendingCount(1);
                ((ForEachOrderedTask)object).completionMap.put((ForEachOrderedTask<S, T>)object3, (ForEachOrderedTask<S, T>)object2);
                if (((ForEachOrderedTask)object).leftPredecessor != null) {
                    ((CountedCompleter)object3).addToPendingCount(1);
                    if (((ForEachOrderedTask)object).completionMap.replace(((ForEachOrderedTask)object).leftPredecessor, (ForEachOrderedTask<S, T>)object, (ForEachOrderedTask<S, T>)object3)) {
                        ((CountedCompleter)object).addToPendingCount(-1);
                    } else {
                        ((CountedCompleter)object3).addToPendingCount(-1);
                    }
                }
                if (bl) {
                    bl = false;
                    spliterator2 = spliterator;
                    object = object3;
                } else {
                    bl = true;
                    object = object2;
                    object2 = object3;
                }
                ((ForkJoinTask)object2).fork();
            }
            if (((CountedCompleter)object).getPendingCount() > 0) {
                object2 = _$$Lambda$ForEachOps$ForEachOrderedTask$XLqga2XPr4V7tlS8H12fiz_In_o.INSTANCE;
                object3 = ((ForEachOrderedTask)object).helper;
                object2 = ((PipelineHelper)object3).makeNodeBuilder(((PipelineHelper)object3).exactOutputSizeIfKnown(spliterator2), (IntFunction<P_OUT[]>)object2);
                ((ForEachOrderedTask)object).node = ((Node.Builder)((ForEachOrderedTask)object).helper.wrapAndCopyInto(object2, spliterator2)).build();
                ((ForEachOrderedTask)object).spliterator = null;
            }
            ((CountedCompleter)object).tryComplete();
        }

        static /* synthetic */ Object[] lambda$doCompute$0(int n) {
            return new Object[n];
        }

        @Override
        public final void compute() {
            ForEachOrderedTask.doCompute(this);
        }

        @Override
        public void onCompletion(CountedCompleter<?> object) {
            object = this.node;
            if (object != null) {
                object.forEach(this.action);
                this.node = null;
            } else {
                object = this.spliterator;
                if (object != null) {
                    this.helper.wrapAndCopyInto(this.action, object);
                    this.spliterator = null;
                }
            }
            object = this.completionMap.remove(this);
            if (object != null) {
                ((CountedCompleter)object).tryComplete();
            }
        }
    }

    static final class ForEachTask<S, T>
    extends CountedCompleter<Void> {
        private final PipelineHelper<T> helper;
        private final Sink<S> sink;
        private Spliterator<S> spliterator;
        private long targetSize;

        ForEachTask(ForEachTask<S, T> forEachTask, Spliterator<S> spliterator) {
            super(forEachTask);
            this.spliterator = spliterator;
            this.sink = forEachTask.sink;
            this.targetSize = forEachTask.targetSize;
            this.helper = forEachTask.helper;
        }

        ForEachTask(PipelineHelper<T> pipelineHelper, Spliterator<S> spliterator, Sink<S> sink) {
            super(null);
            this.sink = sink;
            this.helper = pipelineHelper;
            this.spliterator = spliterator;
            this.targetSize = 0L;
        }

        @Override
        public void compute() {
            long l;
            Spliterator<S> spliterator = this.spliterator;
            long l2 = spliterator.estimateSize();
            long l3 = l = this.targetSize;
            if (l == 0L) {
                l3 = l = AbstractTask.suggestTargetSize(l2);
                this.targetSize = l;
            }
            boolean bl = StreamOpFlag.SHORT_CIRCUIT.isKnown(this.helper.getStreamAndOpFlags());
            boolean bl2 = false;
            Sink<S> sink = this.sink;
            ForEachTask forEachTask = this;
            while (!bl || !sink.cancellationRequested()) {
                Object object;
                if (l2 > l3 && (object = spliterator.trySplit()) != null) {
                    ForEachTask forEachTask2 = new ForEachTask(forEachTask, (Spliterator<S>)object);
                    forEachTask.addToPendingCount(1);
                    if (bl2) {
                        bl2 = false;
                        spliterator = object;
                        object = forEachTask;
                    } else {
                        bl2 = true;
                        object = forEachTask2;
                        forEachTask2 = forEachTask;
                    }
                    ((ForkJoinTask)object).fork();
                    l2 = spliterator.estimateSize();
                    forEachTask = forEachTask2;
                    continue;
                }
                forEachTask.helper.copyInto(sink, spliterator);
                break;
            }
            forEachTask.spliterator = null;
            forEachTask.propagateCompletion();
        }
    }

}

