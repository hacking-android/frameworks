/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterGraph;
import android.filterfw.core.RoundRobinScheduler;
import android.util.Log;
import java.util.HashMap;

public class OneShotScheduler
extends RoundRobinScheduler {
    private static final String TAG = "OneShotScheduler";
    private final boolean mLogVerbose = Log.isLoggable("OneShotScheduler", 2);
    private HashMap<String, Integer> scheduled = new HashMap();

    public OneShotScheduler(FilterGraph filterGraph) {
        super(filterGraph);
    }

    @Override
    public void reset() {
        super.reset();
        this.scheduled.clear();
    }

    @Override
    public Filter scheduleNextNode() {
        Object object = null;
        do {
            Filter filter;
            if ((filter = super.scheduleNextNode()) == null) {
                if (this.mLogVerbose) {
                    Log.v(TAG, "No filters available to run.");
                }
                return null;
            }
            if (!this.scheduled.containsKey(filter.getName())) {
                if (filter.getNumberOfConnectedInputs() == 0) {
                    this.scheduled.put(filter.getName(), 1);
                }
                if (this.mLogVerbose) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Scheduling filter \"");
                    ((StringBuilder)object).append(filter.getName());
                    ((StringBuilder)object).append("\" of type ");
                    ((StringBuilder)object).append(filter.getFilterClassName());
                    Log.v(TAG, ((StringBuilder)object).toString());
                }
                return filter;
            }
            if (object == filter) {
                if (this.mLogVerbose) {
                    Log.v(TAG, "One pass through graph completed.");
                }
                return null;
            }
            Filter filter2 = object;
            if (object == null) {
                filter2 = filter;
            }
            object = filter2;
        } while (true);
    }
}

