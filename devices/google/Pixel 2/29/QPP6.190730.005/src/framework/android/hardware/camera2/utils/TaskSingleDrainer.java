/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.utils;

import android.hardware.camera2.utils.TaskDrainer;
import java.util.concurrent.Executor;

public class TaskSingleDrainer {
    private final Object mSingleTask = new Object();
    private final TaskDrainer<Object> mTaskDrainer;

    public TaskSingleDrainer(Executor executor, TaskDrainer.DrainListener drainListener) {
        this.mTaskDrainer = new TaskDrainer(executor, drainListener);
    }

    public TaskSingleDrainer(Executor executor, TaskDrainer.DrainListener drainListener, String string2) {
        this.mTaskDrainer = new TaskDrainer(executor, drainListener, string2);
    }

    public void beginDrain() {
        this.mTaskDrainer.beginDrain();
    }

    public void taskFinished() {
        this.mTaskDrainer.taskFinished(this.mSingleTask);
    }

    public void taskStarted() {
        this.mTaskDrainer.taskStarted(this.mSingleTask);
    }
}

