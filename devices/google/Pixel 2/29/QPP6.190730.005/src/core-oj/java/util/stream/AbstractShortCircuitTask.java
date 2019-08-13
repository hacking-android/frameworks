/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Spliterator;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.AbstractTask;
import java.util.stream.PipelineHelper;

abstract class AbstractShortCircuitTask<P_IN, P_OUT, R, K extends AbstractShortCircuitTask<P_IN, P_OUT, R, K>>
extends AbstractTask<P_IN, P_OUT, R, K> {
    protected volatile boolean canceled;
    protected final AtomicReference<R> sharedResult;

    protected AbstractShortCircuitTask(K k, Spliterator<P_IN> spliterator) {
        super(k, spliterator);
        this.sharedResult = ((AbstractShortCircuitTask)k).sharedResult;
    }

    protected AbstractShortCircuitTask(PipelineHelper<P_OUT> pipelineHelper, Spliterator<P_IN> spliterator) {
        super(pipelineHelper, spliterator);
        this.sharedResult = new AtomicReference<Object>(null);
    }

    protected void cancel() {
        this.canceled = true;
    }

    protected void cancelLaterNodes() {
        AbstractShortCircuitTask abstractShortCircuitTask = this;
        for (AbstractShortCircuitTask abstractShortCircuitTask2 = (AbstractShortCircuitTask)this.getParent(); abstractShortCircuitTask2 != null; abstractShortCircuitTask2 = (AbstractShortCircuitTask)abstractShortCircuitTask2.getParent()) {
            if (abstractShortCircuitTask2.leftChild == abstractShortCircuitTask) {
                abstractShortCircuitTask = (AbstractShortCircuitTask)abstractShortCircuitTask2.rightChild;
                if (!abstractShortCircuitTask.canceled) {
                    abstractShortCircuitTask.cancel();
                }
            }
            abstractShortCircuitTask = abstractShortCircuitTask2;
        }
    }

    @Override
    public void compute() {
        AbstractShortCircuitTask<P_IN, P_OUT, R, K> abstractShortCircuitTask;
        Object object;
        block4 : {
            Spliterator spliterator = this.spliterator;
            long l = spliterator.estimateSize();
            long l2 = this.getTargetSize(l);
            boolean bl = false;
            abstractShortCircuitTask = this;
            AtomicReference<R> atomicReference = this.sharedResult;
            do {
                Spliterator spliterator2;
                Object object2 = atomicReference.get();
                object = object2;
                if (object2 != null) break block4;
                if (abstractShortCircuitTask.taskCanceled()) {
                    object = abstractShortCircuitTask.getEmptyResult();
                    break block4;
                }
                if (l <= l2 || (spliterator2 = spliterator.trySplit()) == null) break;
                object = (AbstractShortCircuitTask)abstractShortCircuitTask.makeChild(spliterator2);
                abstractShortCircuitTask.leftChild = object;
                object2 = (AbstractShortCircuitTask)abstractShortCircuitTask.makeChild(spliterator);
                abstractShortCircuitTask.rightChild = object2;
                abstractShortCircuitTask.setPendingCount(1);
                if (bl) {
                    bl = false;
                    spliterator = spliterator2;
                } else {
                    bl = true;
                    abstractShortCircuitTask = object2;
                    object2 = object;
                    object = abstractShortCircuitTask;
                }
                ((ForkJoinTask)object2).fork();
                l = spliterator.estimateSize();
                abstractShortCircuitTask = object;
            } while (true);
            object = abstractShortCircuitTask.doLeaf();
        }
        abstractShortCircuitTask.setLocalResult(object);
        abstractShortCircuitTask.tryComplete();
    }

    protected abstract R getEmptyResult();

    @Override
    public R getLocalResult() {
        if (this.isRoot()) {
            R r = this.sharedResult.get();
            if (r == null) {
                r = this.getEmptyResult();
            }
            return r;
        }
        return super.getLocalResult();
    }

    @Override
    public R getRawResult() {
        return this.getLocalResult();
    }

    @Override
    protected void setLocalResult(R r) {
        if (this.isRoot()) {
            if (r != null) {
                this.sharedResult.compareAndSet(null, r);
            }
        } else {
            super.setLocalResult(r);
        }
    }

    protected void shortCircuit(R r) {
        if (r != null) {
            this.sharedResult.compareAndSet(null, r);
        }
    }

    protected boolean taskCanceled() {
        boolean bl;
        boolean bl2 = bl = this.canceled;
        if (!bl) {
            AbstractShortCircuitTask abstractShortCircuitTask = (AbstractShortCircuitTask)this.getParent();
            do {
                bl2 = bl;
                if (bl) break;
                bl2 = bl;
                if (abstractShortCircuitTask == null) break;
                bl = abstractShortCircuitTask.canceled;
                abstractShortCircuitTask = (AbstractShortCircuitTask)abstractShortCircuitTask.getParent();
            } while (true);
        }
        return bl2;
    }
}

