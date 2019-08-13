/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Spliterator;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.PipelineHelper;

abstract class AbstractTask<P_IN, P_OUT, R, K extends AbstractTask<P_IN, P_OUT, R, K>>
extends CountedCompleter<R> {
    static final int LEAF_TARGET = ForkJoinPool.getCommonPoolParallelism() << 2;
    protected final PipelineHelper<P_OUT> helper;
    protected K leftChild;
    private R localResult;
    protected K rightChild;
    protected Spliterator<P_IN> spliterator;
    protected long targetSize;

    protected AbstractTask(K k, Spliterator<P_IN> spliterator) {
        super((CountedCompleter<?>)k);
        this.spliterator = spliterator;
        this.helper = ((AbstractTask)k).helper;
        this.targetSize = ((AbstractTask)k).targetSize;
    }

    protected AbstractTask(PipelineHelper<P_OUT> pipelineHelper, Spliterator<P_IN> spliterator) {
        super(null);
        this.helper = pipelineHelper;
        this.spliterator = spliterator;
        this.targetSize = 0L;
    }

    public static long suggestTargetSize(long l) {
        if ((l /= (long)LEAF_TARGET) <= 0L) {
            l = 1L;
        }
        return l;
    }

    @Override
    public void compute() {
        Spliterator<P_IN> spliterator;
        Spliterator<P_IN> spliterator2 = this.spliterator;
        long l = spliterator2.estimateSize();
        long l2 = this.getTargetSize(l);
        boolean bl = false;
        AbstractTask<P_IN, P_OUT, R, K> abstractTask = this;
        while (l > l2 && (spliterator = spliterator2.trySplit()) != null) {
            K k = abstractTask.makeChild(spliterator);
            abstractTask.leftChild = k;
            K k2 = abstractTask.makeChild(spliterator2);
            abstractTask.rightChild = k2;
            abstractTask.setPendingCount(1);
            if (bl) {
                bl = false;
                spliterator2 = spliterator;
                abstractTask = k2;
            } else {
                bl = true;
                abstractTask = k;
                k = k2;
            }
            abstractTask.fork();
            l = spliterator2.estimateSize();
            abstractTask = k;
        }
        abstractTask.setLocalResult(abstractTask.doLeaf());
        abstractTask.tryComplete();
    }

    protected abstract R doLeaf();

    protected R getLocalResult() {
        return this.localResult;
    }

    protected K getParent() {
        return (K)((AbstractTask)this.getCompleter());
    }

    @Override
    public R getRawResult() {
        return this.localResult;
    }

    protected final long getTargetSize(long l) {
        long l2 = this.targetSize;
        if (l2 != 0L) {
            l = l2;
        } else {
            this.targetSize = l = AbstractTask.suggestTargetSize(l);
        }
        return l;
    }

    protected boolean isLeaf() {
        boolean bl = this.leftChild == null;
        return bl;
    }

    protected boolean isLeftmostNode() {
        AbstractTask<P_IN, P_OUT, R, K> abstractTask = this;
        while (abstractTask != null) {
            K k = abstractTask.getParent();
            if (k != null && ((AbstractTask)k).leftChild != abstractTask) {
                return false;
            }
            abstractTask = k;
        }
        return true;
    }

    protected boolean isRoot() {
        boolean bl = this.getParent() == null;
        return bl;
    }

    protected abstract K makeChild(Spliterator<P_IN> var1);

    @Override
    public void onCompletion(CountedCompleter<?> countedCompleter) {
        this.spliterator = null;
        this.rightChild = null;
        this.leftChild = null;
    }

    protected void setLocalResult(R r) {
        this.localResult = r;
    }

    @Override
    protected void setRawResult(R r) {
        if (r == null) {
            return;
        }
        throw new IllegalStateException();
    }
}

