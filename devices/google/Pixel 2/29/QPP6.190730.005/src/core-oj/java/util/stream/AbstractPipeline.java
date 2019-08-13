/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  java.util.stream.-$
 *  java.util.stream.-$$Lambda
 *  java.util.stream.-$$Lambda$AbstractPipeline
 *  java.util.stream.-$$Lambda$AbstractPipeline$wEsmW74nQaCA9FYTjN7e9qkJaXE
 */
package java.util.stream;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.-$;
import java.util.stream.BaseStream;
import java.util.stream.Node;
import java.util.stream.PipelineHelper;
import java.util.stream.Sink;
import java.util.stream.StreamOpFlag;
import java.util.stream.StreamShape;
import java.util.stream.Streams;
import java.util.stream.TerminalOp;
import java.util.stream._$$Lambda$AbstractPipeline$ImXhRLJT29W8lJFXpTT_PieAotg;
import java.util.stream._$$Lambda$AbstractPipeline$i18ybop3uEZwLQyKh7zmnuTXiFw;
import java.util.stream._$$Lambda$AbstractPipeline$wEsmW74nQaCA9FYTjN7e9qkJaXE;

public abstract class AbstractPipeline<E_IN, E_OUT, S extends BaseStream<E_OUT, S>>
extends PipelineHelper<E_OUT>
implements BaseStream<E_OUT, S> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String MSG_CONSUMED = "source already consumed or closed";
    private static final String MSG_STREAM_LINKED = "stream has already been operated upon or closed";
    private int combinedFlags;
    private int depth;
    private boolean linkedOrConsumed;
    private AbstractPipeline nextStage;
    private boolean parallel;
    private final AbstractPipeline previousStage;
    private boolean sourceAnyStateful;
    private Runnable sourceCloseAction;
    protected final int sourceOrOpFlags;
    private Spliterator<?> sourceSpliterator;
    private final AbstractPipeline sourceStage;
    private Supplier<? extends Spliterator<?>> sourceSupplier;

    AbstractPipeline(Spliterator<?> spliterator, int n, boolean bl) {
        this.previousStage = null;
        this.sourceSpliterator = spliterator;
        this.sourceStage = this;
        this.sourceOrOpFlags = StreamOpFlag.STREAM_MASK & n;
        this.combinedFlags = this.sourceOrOpFlags << 1 & StreamOpFlag.INITIAL_OPS_VALUE;
        this.depth = 0;
        this.parallel = bl;
    }

    AbstractPipeline(Supplier<? extends Spliterator<?>> supplier, int n, boolean bl) {
        this.previousStage = null;
        this.sourceSupplier = supplier;
        this.sourceStage = this;
        this.sourceOrOpFlags = StreamOpFlag.STREAM_MASK & n;
        this.combinedFlags = this.sourceOrOpFlags << 1 & StreamOpFlag.INITIAL_OPS_VALUE;
        this.depth = 0;
        this.parallel = bl;
    }

    AbstractPipeline(AbstractPipeline<?, E_IN, ?> abstractPipeline, int n) {
        if (!abstractPipeline.linkedOrConsumed) {
            abstractPipeline.linkedOrConsumed = true;
            abstractPipeline.nextStage = this;
            this.previousStage = abstractPipeline;
            this.sourceOrOpFlags = StreamOpFlag.OP_MASK & n;
            this.combinedFlags = StreamOpFlag.combineOpFlags(n, abstractPipeline.combinedFlags);
            this.sourceStage = abstractPipeline.sourceStage;
            if (this.opIsStateful()) {
                this.sourceStage.sourceAnyStateful = true;
            }
            this.depth = abstractPipeline.depth + 1;
            return;
        }
        throw new IllegalStateException(MSG_STREAM_LINKED);
    }

    static /* synthetic */ Object[] lambda$opEvaluateParallelLazy$2(int n) {
        return new Object[n];
    }

    static /* synthetic */ Spliterator lambda$wrapSpliterator$1(Spliterator spliterator) {
        return spliterator;
    }

    private Spliterator<?> sourceSpliterator(int n) {
        block11 : {
            AbstractPipeline abstractPipeline;
            Object object;
            block10 : {
                block9 : {
                    abstractPipeline = this.sourceStage;
                    if (abstractPipeline.sourceSpliterator == null) break block9;
                    object = abstractPipeline.sourceSpliterator;
                    abstractPipeline.sourceSpliterator = null;
                    break block10;
                }
                object = abstractPipeline.sourceSupplier;
                if (object == null) break block11;
                object = (Spliterator)object.get();
                this.sourceStage.sourceSupplier = null;
            }
            Spliterator<E_OUT> spliterator = object;
            if (this.isParallel()) {
                abstractPipeline = this.sourceStage;
                spliterator = object;
                if (abstractPipeline.sourceAnyStateful) {
                    int n2 = 1;
                    AbstractPipeline abstractPipeline2 = this.sourceStage;
                    abstractPipeline = abstractPipeline.nextStage;
                    do {
                        spliterator = object;
                        if (abstractPipeline2 == this) break;
                        int n3 = abstractPipeline.sourceOrOpFlags;
                        spliterator = object;
                        int n4 = n2;
                        n2 = n3;
                        if (abstractPipeline.opIsStateful()) {
                            n4 = 0;
                            n2 = n3;
                            if (StreamOpFlag.SHORT_CIRCUIT.isKnown(n3)) {
                                n2 = n3 & StreamOpFlag.IS_SHORT_CIRCUIT;
                            }
                            n2 = (spliterator = abstractPipeline.opEvaluateParallelLazy(abstractPipeline2, (Spliterator<P_IN>)object)).hasCharacteristics(64) ? StreamOpFlag.NOT_SIZED & n2 | StreamOpFlag.IS_SIZED : StreamOpFlag.IS_SIZED & n2 | StreamOpFlag.NOT_SIZED;
                        }
                        abstractPipeline.depth = n4;
                        abstractPipeline.combinedFlags = StreamOpFlag.combineOpFlags(n2, abstractPipeline2.combinedFlags);
                        abstractPipeline2 = abstractPipeline;
                        abstractPipeline = abstractPipeline.nextStage;
                        n2 = n4 + 1;
                        object = spliterator;
                    } while (true);
                }
            }
            if (n != 0) {
                this.combinedFlags = StreamOpFlag.combineOpFlags(n, this.combinedFlags);
            }
            return spliterator;
        }
        throw new IllegalStateException(MSG_CONSUMED);
    }

    @Override
    public void close() {
        this.linkedOrConsumed = true;
        this.sourceSupplier = null;
        this.sourceSpliterator = null;
        AbstractPipeline abstractPipeline = this.sourceStage;
        if (abstractPipeline.sourceCloseAction != null) {
            Runnable runnable = abstractPipeline.sourceCloseAction;
            abstractPipeline.sourceCloseAction = null;
            runnable.run();
        }
    }

    @Override
    final <P_IN> void copyInto(Sink<P_IN> sink, Spliterator<P_IN> spliterator) {
        Objects.requireNonNull(sink);
        if (!StreamOpFlag.SHORT_CIRCUIT.isKnown(this.getStreamAndOpFlags())) {
            sink.begin(spliterator.getExactSizeIfKnown());
            spliterator.forEachRemaining(sink);
            sink.end();
        } else {
            this.copyIntoWithCancel(sink, spliterator);
        }
    }

    @Override
    final <P_IN> void copyIntoWithCancel(Sink<P_IN> sink, Spliterator<P_IN> spliterator) {
        AbstractPipeline abstractPipeline = this;
        while (abstractPipeline.depth > 0) {
            abstractPipeline = abstractPipeline.previousStage;
        }
        sink.begin(spliterator.getExactSizeIfKnown());
        abstractPipeline.forEachWithCancel(spliterator, sink);
        sink.end();
    }

    final <R> R evaluate(TerminalOp<E_OUT, R> terminalOp) {
        if (!this.linkedOrConsumed) {
            this.linkedOrConsumed = true;
            terminalOp = this.isParallel() ? terminalOp.evaluateParallel(this, this.sourceSpliterator(terminalOp.getOpFlags())) : terminalOp.evaluateSequential(this, this.sourceSpliterator(terminalOp.getOpFlags()));
            return (R)terminalOp;
        }
        throw new IllegalStateException(MSG_STREAM_LINKED);
    }

    @Override
    public final <P_IN> Node<E_OUT> evaluate(Spliterator<P_IN> spliterator, boolean bl, IntFunction<E_OUT[]> intFunction) {
        if (this.isParallel()) {
            return this.evaluateToNode(this, spliterator, bl, intFunction);
        }
        return this.wrapAndCopyInto(this.makeNodeBuilder(this.exactOutputSizeIfKnown(spliterator), intFunction), spliterator).build();
    }

    public final Node<E_OUT> evaluateToArrayNode(IntFunction<E_OUT[]> intFunction) {
        if (!this.linkedOrConsumed) {
            this.linkedOrConsumed = true;
            if (this.isParallel() && this.previousStage != null && this.opIsStateful()) {
                this.depth = 0;
                AbstractPipeline abstractPipeline = this.previousStage;
                return this.opEvaluateParallel(abstractPipeline, abstractPipeline.sourceSpliterator(0), intFunction);
            }
            return this.evaluate(this.sourceSpliterator(0), true, intFunction);
        }
        throw new IllegalStateException(MSG_STREAM_LINKED);
    }

    public abstract <P_IN> Node<E_OUT> evaluateToNode(PipelineHelper<E_OUT> var1, Spliterator<P_IN> var2, boolean var3, IntFunction<E_OUT[]> var4);

    @Override
    final <P_IN> long exactOutputSizeIfKnown(Spliterator<P_IN> spliterator) {
        long l = StreamOpFlag.SIZED.isKnown(this.getStreamAndOpFlags()) ? spliterator.getExactSizeIfKnown() : -1L;
        return l;
    }

    public abstract void forEachWithCancel(Spliterator<E_OUT> var1, Sink<E_OUT> var2);

    public abstract StreamShape getOutputShape();

    @Override
    final StreamShape getSourceShape() {
        AbstractPipeline abstractPipeline = this;
        while (abstractPipeline.depth > 0) {
            abstractPipeline = abstractPipeline.previousStage;
        }
        return abstractPipeline.getOutputShape();
    }

    @Override
    public final int getStreamAndOpFlags() {
        return this.combinedFlags;
    }

    public final int getStreamFlags() {
        return StreamOpFlag.toStreamFlags(this.combinedFlags);
    }

    final boolean isOrdered() {
        return StreamOpFlag.ORDERED.isKnown(this.combinedFlags);
    }

    @Override
    public final boolean isParallel() {
        return this.sourceStage.parallel;
    }

    public /* synthetic */ Spliterator lambda$spliterator$0$AbstractPipeline() {
        return this.sourceSpliterator(0);
    }

    public abstract Spliterator<E_OUT> lazySpliterator(Supplier<? extends Spliterator<E_OUT>> var1);

    @Override
    public abstract Node.Builder<E_OUT> makeNodeBuilder(long var1, IntFunction<E_OUT[]> var3);

    @Override
    public S onClose(Runnable runnable) {
        AbstractPipeline abstractPipeline = this.sourceStage;
        Runnable runnable2 = abstractPipeline.sourceCloseAction;
        if (runnable2 != null) {
            runnable = Streams.composeWithExceptions(runnable2, runnable);
        }
        abstractPipeline.sourceCloseAction = runnable;
        return (S)this;
    }

    public <P_IN> Node<E_OUT> opEvaluateParallel(PipelineHelper<E_OUT> pipelineHelper, Spliterator<P_IN> spliterator, IntFunction<E_OUT[]> intFunction) {
        throw new UnsupportedOperationException("Parallel evaluation is not supported");
    }

    public <P_IN> Spliterator<E_OUT> opEvaluateParallelLazy(PipelineHelper<E_OUT> pipelineHelper, Spliterator<P_IN> spliterator) {
        return this.opEvaluateParallel(pipelineHelper, spliterator, (IntFunction<E_OUT[]>)_$$Lambda$AbstractPipeline$wEsmW74nQaCA9FYTjN7e9qkJaXE.INSTANCE).spliterator();
    }

    public abstract boolean opIsStateful();

    public abstract Sink<E_IN> opWrapSink(int var1, Sink<E_OUT> var2);

    @Override
    public final S parallel() {
        this.sourceStage.parallel = true;
        return (S)this;
    }

    @Override
    public final S sequential() {
        this.sourceStage.parallel = false;
        return (S)this;
    }

    final Spliterator<E_OUT> sourceStageSpliterator() {
        AbstractPipeline abstractPipeline = this.sourceStage;
        if (this == abstractPipeline) {
            if (!this.linkedOrConsumed) {
                this.linkedOrConsumed = true;
                if (abstractPipeline.sourceSpliterator != null) {
                    Spliterator<?> spliterator = abstractPipeline.sourceSpliterator;
                    abstractPipeline.sourceSpliterator = null;
                    return spliterator;
                }
                Supplier<Spliterator<?>> supplier = abstractPipeline.sourceSupplier;
                if (supplier != null) {
                    supplier = supplier.get();
                    this.sourceStage.sourceSupplier = null;
                    return supplier;
                }
                throw new IllegalStateException("source already consumed or closed");
            }
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        throw new IllegalStateException();
    }

    @Override
    public Spliterator<E_OUT> spliterator() {
        if (!this.linkedOrConsumed) {
            this.linkedOrConsumed = true;
            AbstractPipeline abstractPipeline = this.sourceStage;
            if (this == abstractPipeline) {
                if (abstractPipeline.sourceSpliterator != null) {
                    Spliterator<?> spliterator = abstractPipeline.sourceSpliterator;
                    abstractPipeline.sourceSpliterator = null;
                    return spliterator;
                }
                if (abstractPipeline.sourceSupplier != null) {
                    Supplier<? extends Spliterator<?>> supplier = abstractPipeline.sourceSupplier;
                    abstractPipeline.sourceSupplier = null;
                    return this.lazySpliterator(supplier);
                }
                throw new IllegalStateException("source already consumed or closed");
            }
            return this.wrap(this, new _$$Lambda$AbstractPipeline$ImXhRLJT29W8lJFXpTT_PieAotg(this), this.isParallel());
        }
        throw new IllegalStateException("stream has already been operated upon or closed");
    }

    public abstract <P_IN> Spliterator<E_OUT> wrap(PipelineHelper<E_OUT> var1, Supplier<Spliterator<P_IN>> var2, boolean var3);

    @Override
    final <P_IN, S extends Sink<E_OUT>> S wrapAndCopyInto(S s, Spliterator<P_IN> spliterator) {
        this.copyInto(this.wrapSink(Objects.requireNonNull(s)), spliterator);
        return s;
    }

    @Override
    public final <P_IN> Sink<P_IN> wrapSink(Sink<E_OUT> sink) {
        Objects.requireNonNull(sink);
        AbstractPipeline abstractPipeline = this;
        while (abstractPipeline.depth > 0) {
            sink = abstractPipeline.opWrapSink(abstractPipeline.previousStage.combinedFlags, sink);
            abstractPipeline = abstractPipeline.previousStage;
        }
        return sink;
    }

    @Override
    final <P_IN> Spliterator<E_OUT> wrapSpliterator(Spliterator<P_IN> spliterator) {
        if (this.depth == 0) {
            return spliterator;
        }
        return this.wrap(this, new _$$Lambda$AbstractPipeline$i18ybop3uEZwLQyKh7zmnuTXiFw(spliterator), this.isParallel());
    }
}

