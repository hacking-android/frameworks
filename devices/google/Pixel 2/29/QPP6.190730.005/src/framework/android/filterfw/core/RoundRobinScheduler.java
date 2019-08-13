/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterGraph;
import android.filterfw.core.Scheduler;
import java.util.Set;

public class RoundRobinScheduler
extends Scheduler {
    private int mLastPos = -1;

    public RoundRobinScheduler(FilterGraph filterGraph) {
        super(filterGraph);
    }

    @Override
    public void reset() {
        this.mLastPos = -1;
    }

    @Override
    public Filter scheduleNextNode() {
        Set<Filter> set = this.getGraph().getFilters();
        if (this.mLastPos >= set.size()) {
            this.mLastPos = -1;
        }
        int n = 0;
        Set<Filter> set2 = null;
        int n2 = -1;
        for (Filter filter : set) {
            set = set2;
            int n3 = n2;
            if (filter.canProcess()) {
                if (n <= this.mLastPos) {
                    set = set2;
                    n3 = n2;
                    if (set2 == null) {
                        set = filter;
                        n3 = n;
                    }
                } else {
                    this.mLastPos = n;
                    return filter;
                }
            }
            ++n;
            set2 = set;
            n2 = n3;
        }
        if (set2 != null) {
            this.mLastPos = n2;
            return set2;
        }
        return null;
    }
}

