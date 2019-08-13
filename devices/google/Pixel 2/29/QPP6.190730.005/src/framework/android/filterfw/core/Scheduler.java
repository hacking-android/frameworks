/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterGraph;

public abstract class Scheduler {
    private FilterGraph mGraph;

    Scheduler(FilterGraph filterGraph) {
        this.mGraph = filterGraph;
    }

    boolean finished() {
        return true;
    }

    FilterGraph getGraph() {
        return this.mGraph;
    }

    abstract void reset();

    abstract Filter scheduleNextNode();
}

