/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

import java.util.concurrent.ForkJoinTask;

public abstract class RecursiveAction
extends ForkJoinTask<Void> {
    private static final long serialVersionUID = 5232453952276485070L;

    protected abstract void compute();

    @Override
    protected final boolean exec() {
        this.compute();
        return true;
    }

    @Override
    public final Void getRawResult() {
        return null;
    }

    @Override
    protected final void setRawResult(Void void_) {
    }
}

