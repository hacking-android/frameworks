/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.FilterContext;
import android.filterfw.core.FilterGraph;
import android.filterfw.core.GraphRunner;
import android.filterfw.core.SimpleScheduler;
import android.filterfw.core.SyncRunner;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncRunner
extends GraphRunner {
    private static final String TAG = "AsyncRunner";
    private boolean isProcessing;
    private GraphRunner.OnRunnerDoneListener mDoneListener;
    private Exception mException;
    private boolean mLogVerbose;
    private AsyncRunnerTask mRunTask;
    private SyncRunner mRunner;
    private Class mSchedulerClass;

    public AsyncRunner(FilterContext filterContext) {
        super(filterContext);
        this.mSchedulerClass = SimpleScheduler.class;
        this.mLogVerbose = Log.isLoggable(TAG, 2);
    }

    public AsyncRunner(FilterContext filterContext, Class class_) {
        super(filterContext);
        this.mSchedulerClass = class_;
        this.mLogVerbose = Log.isLoggable(TAG, 2);
    }

    private void setException(Exception exception) {
        synchronized (this) {
            this.mException = exception;
            return;
        }
    }

    private void setRunning(boolean bl) {
        synchronized (this) {
            this.isProcessing = bl;
            return;
        }
    }

    @Override
    public void close() {
        synchronized (this) {
            if (!this.isRunning()) {
                if (this.mLogVerbose) {
                    Log.v(TAG, "Closing filters.");
                }
                this.mRunner.close();
                return;
            }
            RuntimeException runtimeException = new RuntimeException("Cannot close graph while it is running!");
            throw runtimeException;
        }
    }

    @Override
    public Exception getError() {
        synchronized (this) {
            Exception exception = this.mException;
            return exception;
        }
    }

    @Override
    public FilterGraph getGraph() {
        Object object = this.mRunner;
        object = object != null ? ((SyncRunner)object).getGraph() : null;
        return object;
    }

    @Override
    public boolean isRunning() {
        synchronized (this) {
            boolean bl = this.isProcessing;
            return bl;
        }
    }

    @Override
    public void run() {
        synchronized (this) {
            if (this.mLogVerbose) {
                Log.v(TAG, "Running graph.");
            }
            this.setException(null);
            if (!this.isRunning()) {
                if (this.mRunner != null) {
                    AsyncRunnerTask asyncRunnerTask;
                    this.mRunTask = asyncRunnerTask = new AsyncRunnerTask();
                    this.setRunning(true);
                    this.mRunTask.execute(this.mRunner);
                    return;
                }
                RuntimeException runtimeException = new RuntimeException("Cannot run before a graph is set!");
                throw runtimeException;
            }
            RuntimeException runtimeException = new RuntimeException("Graph is already running!");
            throw runtimeException;
        }
    }

    @Override
    public void setDoneCallback(GraphRunner.OnRunnerDoneListener onRunnerDoneListener) {
        this.mDoneListener = onRunnerDoneListener;
    }

    public void setGraph(FilterGraph object) {
        synchronized (this) {
            if (!this.isRunning()) {
                SyncRunner syncRunner;
                this.mRunner = syncRunner = new SyncRunner(this.mFilterContext, (FilterGraph)object, this.mSchedulerClass);
                return;
            }
            object = new RuntimeException("Graph is already running!");
            throw object;
        }
    }

    @Override
    public void stop() {
        synchronized (this) {
            if (this.mRunTask != null && !this.mRunTask.isCancelled()) {
                if (this.mLogVerbose) {
                    Log.v(TAG, "Stopping graph.");
                }
                this.mRunTask.cancel(false);
            }
            return;
        }
    }

    private class AsyncRunnerTask
    extends AsyncTask<SyncRunner, Void, RunnerResult> {
        private static final String TAG = "AsyncRunnerTask";

        private AsyncRunnerTask() {
        }

        protected RunnerResult doInBackground(SyncRunner ... object) {
            RunnerResult runnerResult;
            block11 : {
                runnerResult = new RunnerResult();
                try {
                    if (((SyncRunner[])object).length <= 1) {
                        object[0].assertReadyToStep();
                        if (AsyncRunner.this.mLogVerbose) {
                            Log.v(TAG, "Starting background graph processing.");
                        }
                        AsyncRunner.this.activateGlContext();
                        if (AsyncRunner.this.mLogVerbose) {
                            Log.v(TAG, "Preparing filter graph for processing.");
                        }
                        object[0].beginProcessing();
                        if (AsyncRunner.this.mLogVerbose) {
                            Log.v(TAG, "Running graph.");
                        }
                        runnerResult.status = 1;
                        while (!this.isCancelled() && runnerResult.status == 1) {
                            if (object[0].performStep()) continue;
                            runnerResult.status = object[0].determinePostRunState();
                            if (runnerResult.status != 3) continue;
                            object[0].waitUntilWake();
                            runnerResult.status = 1;
                        }
                        if (this.isCancelled()) {
                            runnerResult.status = 5;
                        }
                        break block11;
                    }
                    object = new RuntimeException("More than one runner received!");
                    throw object;
                }
                catch (Exception exception) {
                    runnerResult.exception = exception;
                    runnerResult.status = 6;
                }
            }
            try {
                AsyncRunner.this.deactivateGlContext();
            }
            catch (Exception exception) {
                runnerResult.exception = exception;
                runnerResult.status = 6;
            }
            if (AsyncRunner.this.mLogVerbose) {
                Log.v(TAG, "Done with background graph processing.");
            }
            return runnerResult;
        }

        @Override
        protected void onCancelled(RunnerResult runnerResult) {
            this.onPostExecute(runnerResult);
        }

        @Override
        protected void onPostExecute(RunnerResult runnerResult) {
            if (AsyncRunner.this.mLogVerbose) {
                Log.v(TAG, "Starting post-execute.");
            }
            AsyncRunner.this.setRunning(false);
            RunnerResult runnerResult2 = runnerResult;
            if (runnerResult == null) {
                runnerResult2 = new RunnerResult();
                runnerResult2.status = 5;
            }
            AsyncRunner.this.setException(runnerResult2.exception);
            if (runnerResult2.status == 5 || runnerResult2.status == 6) {
                if (AsyncRunner.this.mLogVerbose) {
                    Log.v(TAG, "Closing filters.");
                }
                try {
                    AsyncRunner.this.mRunner.close();
                }
                catch (Exception exception) {
                    runnerResult2.status = 6;
                    AsyncRunner.this.setException(exception);
                }
            }
            if (AsyncRunner.this.mDoneListener != null) {
                if (AsyncRunner.this.mLogVerbose) {
                    Log.v(TAG, "Calling graph done callback.");
                }
                AsyncRunner.this.mDoneListener.onRunnerDone(runnerResult2.status);
            }
            if (AsyncRunner.this.mLogVerbose) {
                Log.v(TAG, "Completed post-execute.");
            }
        }
    }

    private class RunnerResult {
        public Exception exception;
        public int status = 0;

        private RunnerResult() {
        }
    }

}

