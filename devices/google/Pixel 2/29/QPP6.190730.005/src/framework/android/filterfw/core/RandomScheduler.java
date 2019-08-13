/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterGraph;
import android.filterfw.core.Scheduler;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

public class RandomScheduler
extends Scheduler {
    private Random mRand = new Random();

    public RandomScheduler(FilterGraph filterGraph) {
        super(filterGraph);
    }

    @Override
    public void reset() {
    }

    @Override
    public Filter scheduleNextNode() {
        Vector<Filter> vector = new Vector<Filter>();
        for (Filter filter : this.getGraph().getFilters()) {
            if (!filter.canProcess()) continue;
            vector.add(filter);
        }
        if (vector.size() > 0) {
            return (Filter)vector.elementAt(this.mRand.nextInt(vector.size()));
        }
        return null;
    }
}

