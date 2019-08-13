/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterGraph;
import android.filterfw.core.Scheduler;
import java.util.Set;

public class SimpleScheduler
extends Scheduler {
    public SimpleScheduler(FilterGraph filterGraph) {
        super(filterGraph);
    }

    @Override
    public void reset() {
    }

    @Override
    public Filter scheduleNextNode() {
        for (Filter filter : this.getGraph().getFilters()) {
            if (!filter.canProcess()) continue;
            return filter;
        }
        return null;
    }
}

