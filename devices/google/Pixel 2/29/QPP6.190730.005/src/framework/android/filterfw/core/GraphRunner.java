/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.annotation.UnsupportedAppUsage;
import android.filterfw.core.FilterContext;
import android.filterfw.core.FilterGraph;
import android.filterfw.core.GLEnvironment;

public abstract class GraphRunner {
    public static final int RESULT_BLOCKED = 4;
    public static final int RESULT_ERROR = 6;
    public static final int RESULT_FINISHED = 2;
    public static final int RESULT_RUNNING = 1;
    public static final int RESULT_SLEEPING = 3;
    public static final int RESULT_STOPPED = 5;
    public static final int RESULT_UNKNOWN = 0;
    protected FilterContext mFilterContext = null;

    public GraphRunner(FilterContext filterContext) {
        this.mFilterContext = filterContext;
    }

    protected boolean activateGlContext() {
        GLEnvironment gLEnvironment = this.mFilterContext.getGLEnvironment();
        if (gLEnvironment != null && !gLEnvironment.isActive()) {
            gLEnvironment.activate();
            return true;
        }
        return false;
    }

    public abstract void close();

    protected void deactivateGlContext() {
        GLEnvironment gLEnvironment = this.mFilterContext.getGLEnvironment();
        if (gLEnvironment != null) {
            gLEnvironment.deactivate();
        }
    }

    public FilterContext getContext() {
        return this.mFilterContext;
    }

    @UnsupportedAppUsage
    public abstract Exception getError();

    @UnsupportedAppUsage
    public abstract FilterGraph getGraph();

    public abstract boolean isRunning();

    @UnsupportedAppUsage
    public abstract void run();

    @UnsupportedAppUsage
    public abstract void setDoneCallback(OnRunnerDoneListener var1);

    @UnsupportedAppUsage
    public abstract void stop();

    public static interface OnRunnerDoneListener {
        public void onRunnerDone(int var1);
    }

}

