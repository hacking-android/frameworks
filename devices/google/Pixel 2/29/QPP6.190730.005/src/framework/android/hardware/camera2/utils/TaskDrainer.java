/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.utils;

import android.hardware.camera2.utils._$$Lambda$TaskDrainer$Jb53sDskEXp_qIjiikQeCRx0wJs;
import com.android.internal.util.Preconditions;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;

public class TaskDrainer<T> {
    private static final String TAG = "TaskDrainer";
    private final boolean DEBUG;
    private boolean mDrainFinished = false;
    private boolean mDraining = false;
    private final Set<T> mEarlyFinishedTaskSet = new HashSet<T>();
    private final Executor mExecutor;
    private final DrainListener mListener;
    private final Object mLock = new Object();
    private final String mName;
    private final Set<T> mTaskSet = new HashSet<T>();

    public TaskDrainer(Executor executor, DrainListener drainListener) {
        this.DEBUG = false;
        this.mExecutor = Preconditions.checkNotNull(executor, "executor must not be null");
        this.mListener = Preconditions.checkNotNull(drainListener, "listener must not be null");
        this.mName = null;
    }

    public TaskDrainer(Executor executor, DrainListener drainListener, String string2) {
        this.DEBUG = false;
        this.mExecutor = Preconditions.checkNotNull(executor, "executor must not be null");
        this.mListener = Preconditions.checkNotNull(drainListener, "listener must not be null");
        this.mName = string2;
    }

    private void checkIfDrainFinished() {
        if (this.mTaskSet.isEmpty() && this.mDraining && !this.mDrainFinished) {
            this.mDrainFinished = true;
            this.postDrained();
        }
    }

    private void postDrained() {
        this.mExecutor.execute(new _$$Lambda$TaskDrainer$Jb53sDskEXp_qIjiikQeCRx0wJs(this));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void beginDrain() {
        Object object = this.mLock;
        synchronized (object) {
            if (!this.mDraining) {
                this.mDraining = true;
                this.checkIfDrainFinished();
            }
            return;
        }
    }

    public /* synthetic */ void lambda$postDrained$0$TaskDrainer() {
        this.mListener.onDrained();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void taskFinished(T t) {
        Object object = this.mLock;
        synchronized (object) {
            if (!this.mTaskSet.remove(t) && !this.mEarlyFinishedTaskSet.add(t)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Task ");
                stringBuilder.append(t);
                stringBuilder.append(" was already finished");
                IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
                throw illegalStateException;
            }
            this.checkIfDrainFinished();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void taskStarted(T object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (this.mDraining) {
                object = new Object("Can't start more tasks after draining has begun");
                throw object;
            }
            if (!this.mEarlyFinishedTaskSet.remove(object) && !this.mTaskSet.add(object)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Task ");
                stringBuilder.append(object);
                stringBuilder.append(" was already started");
                IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
                throw illegalStateException;
            }
            return;
        }
    }

    public static interface DrainListener {
        public void onDrained();
    }

}

