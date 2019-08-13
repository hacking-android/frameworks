/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.IActivityTaskManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Singleton;
import java.util.Iterator;
import java.util.List;

public class ActivityTaskManager {
    public static final String EXTRA_IGNORE_TARGET_SECURITY = "android.app.extra.EXTRA_IGNORE_TARGET_SECURITY";
    public static final String EXTRA_OPTIONS = "android.app.extra.OPTIONS";
    public static final String EXTRA_PERMISSION_TOKEN = "android.app.extra.PERMISSION_TOKEN";
    @UnsupportedAppUsage(trackingBug=129726065L)
    private static final Singleton<IActivityTaskManager> IActivityTaskManagerSingleton;
    public static final int INVALID_STACK_ID = -1;
    public static final int INVALID_TASK_ID = -1;
    public static final int RESIZE_MODE_FORCED = 2;
    public static final int RESIZE_MODE_PRESERVE_WINDOW = 1;
    public static final int RESIZE_MODE_SYSTEM = 0;
    public static final int RESIZE_MODE_SYSTEM_SCREEN_ROTATION = 1;
    public static final int RESIZE_MODE_USER = 1;
    public static final int RESIZE_MODE_USER_FORCED = 3;
    public static final int SPLIT_SCREEN_CREATE_MODE_BOTTOM_OR_RIGHT = 1;
    public static final int SPLIT_SCREEN_CREATE_MODE_TOP_OR_LEFT = 0;
    private static int sMaxRecentTasks;

    static {
        sMaxRecentTasks = -1;
        IActivityTaskManagerSingleton = new Singleton<IActivityTaskManager>(){

            @Override
            protected IActivityTaskManager create() {
                return IActivityTaskManager.Stub.asInterface(ServiceManager.getService("activity_task"));
            }
        };
    }

    ActivityTaskManager(Context context, Handler handler) {
    }

    public static int getDefaultAppRecentsLimitStatic() {
        return ActivityTaskManager.getMaxRecentTasksStatic() / 6;
    }

    public static int getMaxAppRecentsLimitStatic() {
        return ActivityTaskManager.getMaxRecentTasksStatic() / 2;
    }

    public static int getMaxRecentTasksStatic() {
        int n = sMaxRecentTasks;
        if (n < 0) {
            n = ActivityManager.isLowRamDeviceStatic() ? 36 : 48;
            sMaxRecentTasks = n;
            return n;
        }
        return n;
    }

    public static IActivityTaskManager getService() {
        return IActivityTaskManagerSingleton.get();
    }

    public static boolean supportsMultiWindow(Context context) {
        boolean bl = context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
        bl = (!ActivityManager.isLowRamDeviceStatic() || bl) && Resources.getSystem().getBoolean(17891539);
        return bl;
    }

    public static boolean supportsSplitScreenMultiWindow(Context context) {
        boolean bl = ActivityTaskManager.supportsMultiWindow(context) && Resources.getSystem().getBoolean(17891541);
        return bl;
    }

    public void clearLaunchParamsForPackages(List<String> list) {
        try {
            ActivityTaskManager.getService().clearLaunchParamsForPackages(list);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    public String listAllStacks() {
        StringBuilder stringBuilder;
        block3 : {
            Object object;
            try {
                object = ActivityTaskManager.getService().getAllStackInfos();
                stringBuilder = new StringBuilder();
                if (object == null) break block3;
                object = object.iterator();
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            while (object.hasNext()) {
                stringBuilder.append((ActivityManager.StackInfo)object.next());
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public void moveTaskToStack(int n, int n2, boolean bl) {
        try {
            ActivityTaskManager.getService().moveTaskToStack(n, n2, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean moveTopActivityToPinnedStack(int n, Rect rect) {
        try {
            boolean bl = ActivityTaskManager.getService().moveTopActivityToPinnedStack(n, rect);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void removeAllVisibleRecentTasks() {
        try {
            ActivityTaskManager.getService().removeAllVisibleRecentTasks();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void removeStacksInWindowingModes(int[] arrn) throws SecurityException {
        try {
            ActivityTaskManager.getService().removeStacksInWindowingModes(arrn);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void removeStacksWithActivityTypes(int[] arrn) throws SecurityException {
        try {
            ActivityTaskManager.getService().removeStacksWithActivityTypes(arrn);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void resizeDockedStack(Rect rect, Rect rect2) {
        try {
            ActivityTaskManager.getService().resizeDockedStack(rect, rect2, null, null, null);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void resizeStack(int n, Rect rect) throws SecurityException {
        try {
            ActivityTaskManager.getService().resizeStack(n, rect, false, false, false, -1);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void resizeStack(int n, Rect rect, boolean bl) {
        try {
            ActivityTaskManager.getService().resizeStack(n, rect, false, false, bl, -1);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void resizeTask(int n, Rect rect) {
        try {
            ActivityTaskManager.getService().resizeTask(n, rect, 0);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setDisplayToSingleTaskInstance(int n) {
        try {
            ActivityTaskManager.getService().setDisplayToSingleTaskInstance(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setTaskWindowingMode(int n, int n2, boolean bl) throws SecurityException {
        try {
            ActivityTaskManager.getService().setTaskWindowingMode(n, n2, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setTaskWindowingModeSplitScreenPrimary(int n, int n2, boolean bl, boolean bl2, Rect rect, boolean bl3) throws SecurityException {
        try {
            ActivityTaskManager.getService().setTaskWindowingModeSplitScreenPrimary(n, n2, bl, bl2, rect, bl3);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void startSystemLockTaskMode(int n) {
        try {
            ActivityTaskManager.getService().startSystemLockTaskMode(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void stopSystemLockTaskMode() {
        try {
            ActivityTaskManager.getService().stopSystemLockTaskMode();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

}

