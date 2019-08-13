/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.FilterGraph;
import android.filterfw.core.GraphRunner;
import android.filterfw.core.Scheduler;
import android.filterfw.core.StopWatchMap;
import android.os.ConditionVariable;
import android.util.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SyncRunner
extends GraphRunner {
    private static final String TAG = "SyncRunner";
    private GraphRunner.OnRunnerDoneListener mDoneListener = null;
    private final boolean mLogVerbose = Log.isLoggable("SyncRunner", 2);
    private Scheduler mScheduler = null;
    private StopWatchMap mTimer = null;
    private ConditionVariable mWakeCondition = new ConditionVariable();
    private ScheduledThreadPoolExecutor mWakeExecutor = new ScheduledThreadPoolExecutor(1);

    public SyncRunner(FilterContext filterContext, FilterGraph filterGraph, Class class_) {
        super(filterContext);
        if (this.mLogVerbose) {
            Log.v(TAG, "Initializing SyncRunner");
        }
        if (Scheduler.class.isAssignableFrom(class_)) {
            try {
                this.mScheduler = (Scheduler)class_.getConstructor(FilterGraph.class).newInstance(filterGraph);
                this.mFilterContext = filterContext;
                this.mFilterContext.addGraph(filterGraph);
            }
            catch (Exception exception) {
                throw new RuntimeException("Could not instantiate Scheduler", exception);
            }
            catch (InvocationTargetException invocationTargetException) {
                throw new RuntimeException("Scheduler constructor threw an exception", invocationTargetException);
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new RuntimeException("Cannot access Scheduler constructor!", illegalAccessException);
            }
            catch (InstantiationException instantiationException) {
                throw new RuntimeException("Could not instantiate the Scheduler instance!", instantiationException);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                throw new RuntimeException("Scheduler does not have constructor <init>(FilterGraph)!", noSuchMethodException);
            }
            this.mTimer = new StopWatchMap();
            if (this.mLogVerbose) {
                Log.v(TAG, "Setting up filters");
            }
            filterGraph.setupFilters();
            return;
        }
        throw new IllegalArgumentException("Class provided is not a Scheduler subclass!");
    }

    void assertReadyToStep() {
        if (this.mScheduler != null) {
            if (this.getGraph() != null) {
                return;
            }
            throw new RuntimeException("Calling step on scheduler with no graph in place!");
        }
        throw new RuntimeException("Attempting to run schedule with no scheduler in place!");
    }

    public void beginProcessing() {
        this.mScheduler.reset();
        this.getGraph().beginProcessing();
    }

    @Override
    public void close() {
        if (this.mLogVerbose) {
            Log.v(TAG, "Closing graph.");
        }
        this.getGraph().closeFilters(this.mFilterContext);
        this.mScheduler.reset();
    }

    protected int determinePostRunState() {
        for (Filter filter : this.mScheduler.getGraph().getFilters()) {
            if (!filter.isOpen()) continue;
            if (filter.getStatus() == 4) {
                return 3;
            }
            return 4;
        }
        return 2;
    }

    /*
     * Enabled aggressive block sorting
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public Exception getError() {
        // MONITORENTER : this
        // MONITOREXIT : this
        return null;
    }

    @Override
    public FilterGraph getGraph() {
        Object object = this.mScheduler;
        object = object != null ? ((Scheduler)object).getGraph() : null;
        return object;
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    boolean performStep() {
        Filter filter;
        if (this.mLogVerbose) {
            Log.v(TAG, "Performing one step.");
        }
        if ((filter = this.mScheduler.scheduleNextNode()) != null) {
            this.mTimer.start(filter.getName());
            this.processFilterNode(filter);
            this.mTimer.stop(filter.getName());
            return true;
        }
        return false;
    }

    protected void processFilterNode(Filter filter) {
        if (this.mLogVerbose) {
            Log.v(TAG, "Processing filter node");
        }
        filter.performProcess(this.mFilterContext);
        if (filter.getStatus() != 6) {
            if (filter.getStatus() == 4) {
                if (this.mLogVerbose) {
                    Log.v(TAG, "Scheduling filter wakeup");
                }
                this.scheduleFilterWake(filter, filter.getSleepDelay());
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("There was an error executing ");
        stringBuilder.append(filter);
        stringBuilder.append("!");
        throw new RuntimeException(stringBuilder.toString());
    }

    @Override
    public void run() {
        if (this.mLogVerbose) {
            Log.v(TAG, "Beginning run.");
        }
        this.assertReadyToStep();
        this.beginProcessing();
        boolean bl = this.activateGlContext();
        boolean bl2 = true;
        while (bl2) {
            bl2 = this.performStep();
        }
        if (bl) {
            this.deactivateGlContext();
        }
        if (this.mDoneListener != null) {
            if (this.mLogVerbose) {
                Log.v(TAG, "Calling completion listener.");
            }
            this.mDoneListener.onRunnerDone(this.determinePostRunState());
        }
        if (this.mLogVerbose) {
            Log.v(TAG, "Run complete");
        }
    }

    protected void scheduleFilterWake(final Filter filter, int n) {
        this.mWakeCondition.close();
        final ConditionVariable conditionVariable = this.mWakeCondition;
        this.mWakeExecutor.schedule(new Runnable(){

            @Override
            public void run() {
                filter.unsetStatus(4);
                conditionVariable.open();
            }
        }, (long)n, TimeUnit.MILLISECONDS);
    }

    @Override
    public void setDoneCallback(GraphRunner.OnRunnerDoneListener onRunnerDoneListener) {
        this.mDoneListener = onRunnerDoneListener;
    }

    public int step() {
        this.assertReadyToStep();
        if (this.getGraph().isReady()) {
            int n = this.performStep() ? 1 : this.determinePostRunState();
            return n;
        }
        throw new RuntimeException("Trying to process graph that is not open!");
    }

    @Override
    public void stop() {
        throw new RuntimeException("SyncRunner does not support stopping a graph!");
    }

    protected void waitUntilWake() {
        this.mWakeCondition.block();
    }

}

