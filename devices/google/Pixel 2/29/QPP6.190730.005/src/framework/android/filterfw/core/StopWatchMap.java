/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.StopWatch;
import java.util.HashMap;

public class StopWatchMap {
    public boolean LOG_MFF_RUNNING_TIMES = false;
    private HashMap<String, StopWatch> mStopWatches = new HashMap();

    public void start(String string2) {
        if (!this.LOG_MFF_RUNNING_TIMES) {
            return;
        }
        if (!this.mStopWatches.containsKey(string2)) {
            this.mStopWatches.put(string2, new StopWatch(string2));
        }
        this.mStopWatches.get(string2).start();
    }

    public void stop(String string2) {
        if (!this.LOG_MFF_RUNNING_TIMES) {
            return;
        }
        if (this.mStopWatches.containsKey(string2)) {
            this.mStopWatches.get(string2).stop();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Calling stop with unknown stopWatchName: ");
        stringBuilder.append(string2);
        throw new RuntimeException(stringBuilder.toString());
    }
}

