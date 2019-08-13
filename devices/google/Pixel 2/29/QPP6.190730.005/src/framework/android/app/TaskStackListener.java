/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.ITaskStackListener;
import android.content.ComponentName;
import android.graphics.GraphicBuffer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;

public abstract class TaskStackListener
extends ITaskStackListener.Stub {
    @UnsupportedAppUsage
    @Override
    public void onActivityDismissingDockedStack() throws RemoteException {
    }

    @UnsupportedAppUsage
    @Override
    public void onActivityForcedResizable(String string2, int n, int n2) throws RemoteException {
    }

    @Deprecated
    @UnsupportedAppUsage
    public void onActivityLaunchOnSecondaryDisplayFailed() throws RemoteException {
    }

    @Override
    public void onActivityLaunchOnSecondaryDisplayFailed(ActivityManager.RunningTaskInfo runningTaskInfo, int n) throws RemoteException {
        this.onActivityLaunchOnSecondaryDisplayFailed();
    }

    @UnsupportedAppUsage
    @Override
    public void onActivityLaunchOnSecondaryDisplayRerouted(ActivityManager.RunningTaskInfo runningTaskInfo, int n) throws RemoteException {
    }

    @UnsupportedAppUsage
    @Override
    public void onActivityPinned(String string2, int n, int n2, int n3) throws RemoteException {
    }

    @UnsupportedAppUsage
    @Override
    public void onActivityRequestedOrientationChanged(int n, int n2) throws RemoteException {
    }

    @UnsupportedAppUsage
    @Override
    public void onActivityUnpinned() throws RemoteException {
    }

    @Override
    public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
    }

    @UnsupportedAppUsage
    @Override
    public void onPinnedActivityRestartAttempt(boolean bl) throws RemoteException {
    }

    @UnsupportedAppUsage
    @Override
    public void onPinnedStackAnimationEnded() throws RemoteException {
    }

    @UnsupportedAppUsage
    @Override
    public void onPinnedStackAnimationStarted() throws RemoteException {
    }

    @UnsupportedAppUsage
    @Override
    public void onSizeCompatModeActivityChanged(int n, IBinder iBinder) throws RemoteException {
    }

    @Override
    public void onTaskCreated(int n, ComponentName componentName) throws RemoteException {
    }

    @Deprecated
    public void onTaskDescriptionChanged(int n, ActivityManager.TaskDescription taskDescription) throws RemoteException {
    }

    @Override
    public void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        this.onTaskDescriptionChanged(runningTaskInfo.taskId, runningTaskInfo.taskDescription);
    }

    @Override
    public void onTaskDisplayChanged(int n, int n2) throws RemoteException {
    }

    @Deprecated
    @UnsupportedAppUsage
    public void onTaskMovedToFront(int n) throws RemoteException {
    }

    @Override
    public void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        this.onTaskMovedToFront(runningTaskInfo.taskId);
    }

    @UnsupportedAppUsage
    @Override
    public void onTaskProfileLocked(int n, int n2) throws RemoteException {
    }

    @Deprecated
    public void onTaskRemovalStarted(int n) throws RemoteException {
    }

    @Override
    public void onTaskRemovalStarted(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        this.onTaskRemovalStarted(runningTaskInfo.taskId);
    }

    @UnsupportedAppUsage
    @Override
    public void onTaskRemoved(int n) throws RemoteException {
    }

    @UnsupportedAppUsage
    @Override
    public void onTaskSnapshotChanged(int n, ActivityManager.TaskSnapshot taskSnapshot) throws RemoteException {
        if (Binder.getCallingPid() != Process.myPid() && taskSnapshot != null && taskSnapshot.getSnapshot() != null) {
            taskSnapshot.getSnapshot().destroy();
        }
    }

    @UnsupportedAppUsage
    @Override
    public void onTaskStackChanged() throws RemoteException {
    }
}

