/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.concurrent.ForkJoinTask;

public abstract class RecursiveTask<V>
extends ForkJoinTask<V> {
    private static final long serialVersionUID = 5232453952276485270L;
    V result;

    protected abstract V compute();

    @Override
    protected final boolean exec() {
        this.result = this.compute();
        return true;
    }

    @Override
    public final V getRawResult() {
        return this.result;
    }

    @Override
    protected final void setRawResult(V v) {
        this.result = v;
    }
}

