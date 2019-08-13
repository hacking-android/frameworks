/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.app;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.ITaskStackListener;
import android.app.PendingIntent;
import android.app.TaskStackListener;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Region;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.IWindow;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceSession;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.inputmethod.InputMethodManager;
import dalvik.system.CloseGuard;
import java.util.List;

public class ActivityView
extends ViewGroup {
    private static final String DISPLAY_NAME = "ActivityViewVirtualDisplay";
    private static final String TAG = "ActivityView";
    private IActivityTaskManager mActivityTaskManager;
    private StateCallback mActivityViewCallback;
    private Insets mForwardedInsets;
    private final CloseGuard mGuard = CloseGuard.get();
    private final int[] mLocationInWindow = new int[2];
    private boolean mOpened;
    private SurfaceControl mRootSurfaceControl;
    private final boolean mSingleTaskInstance;
    private final SurfaceCallback mSurfaceCallback;
    private final SurfaceView mSurfaceView;
    private final Region mTapExcludeRegion = new Region();
    private TaskStackListener mTaskStackListener;
    private final SurfaceControl.Transaction mTmpTransaction = new SurfaceControl.Transaction();
    private VirtualDisplay mVirtualDisplay;

    public ActivityView(Context context) {
        this(context, null);
    }

    public ActivityView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActivityView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, false);
    }

    public ActivityView(Context context, AttributeSet attributeSet, int n, boolean bl) {
        super(context, attributeSet, n);
        this.mSingleTaskInstance = bl;
        this.mActivityTaskManager = ActivityTaskManager.getService();
        this.mSurfaceView = new SurfaceView(context);
        this.mSurfaceCallback = new SurfaceCallback();
        this.mSurfaceView.getHolder().addCallback(this.mSurfaceCallback);
        this.addView(this.mSurfaceView);
        this.mOpened = true;
        this.mGuard.open("release");
    }

    private void cleanTapExcludeRegion() {
        if (this.isAttachedToWindow() && !this.mTapExcludeRegion.isEmpty()) {
            try {
                WindowManagerGlobal.getWindowSession().updateTapExcludeRegion(this.getWindow(), this.hashCode(), null);
                this.mTapExcludeRegion.setEmpty();
            }
            catch (RemoteException remoteException) {
                remoteException.rethrowAsRuntimeException();
            }
            return;
        }
    }

    private void clearActivityViewGeometryForIme() {
        VirtualDisplay virtualDisplay = this.mVirtualDisplay;
        if (virtualDisplay == null) {
            return;
        }
        int n = virtualDisplay.getDisplay().getDisplayId();
        this.mContext.getSystemService(InputMethodManager.class).reportActivityView(n, null);
    }

    private static KeyEvent createKeyEvent(int n, int n2, int n3) {
        long l = SystemClock.uptimeMillis();
        KeyEvent keyEvent = new KeyEvent(l, l, n, n2, 0, 0, -1, 0, 72, 257);
        keyEvent.setDisplayId(n3);
        return keyEvent;
    }

    private int getBaseDisplayDensity() {
        WindowManager windowManager = this.mContext.getSystemService(WindowManager.class);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.densityDpi;
    }

    private void initVirtualDisplay(SurfaceSession surfaceSession) {
        if (this.mVirtualDisplay == null) {
            int n = this.mSurfaceView.getWidth();
            int n2 = this.mSurfaceView.getHeight();
            DisplayManager displayManager = this.mContext.getSystemService(DisplayManager.class);
            Object object = new StringBuilder();
            ((StringBuilder)object).append("ActivityViewVirtualDisplay@");
            ((StringBuilder)object).append(System.identityHashCode(this));
            this.mVirtualDisplay = displayManager.createVirtualDisplay(((StringBuilder)object).toString(), n, n2, this.getBaseDisplayDensity(), null, 265);
            object = this.mVirtualDisplay;
            if (object == null) {
                Log.e(TAG, "Failed to initialize ActivityView");
                return;
            }
            n2 = ((VirtualDisplay)object).getDisplay().getDisplayId();
            object = WindowManagerGlobal.getWindowManagerService();
            this.mRootSurfaceControl = new SurfaceControl.Builder(surfaceSession).setContainerLayer().setParent(this.mSurfaceView.getSurfaceControl()).setName(DISPLAY_NAME).build();
            try {
                WindowManagerGlobal.getWindowSession().reparentDisplayContent(this.getWindow(), this.mRootSurfaceControl, n2);
                object.dontOverrideDisplayInfo(n2);
                if (this.mSingleTaskInstance) {
                    this.mActivityTaskManager.setDisplayToSingleTaskInstance(n2);
                }
                object.setForwardedInsets(n2, this.mForwardedInsets);
            }
            catch (RemoteException remoteException) {
                remoteException.rethrowAsRuntimeException();
            }
            this.mTmpTransaction.show(this.mRootSurfaceControl).apply();
            this.mTaskStackListener = new TaskStackListenerImpl();
            try {
                this.mActivityTaskManager.registerTaskStackListener(this.mTaskStackListener);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Failed to register task stack listener", remoteException);
            }
            return;
        }
        throw new IllegalStateException("Trying to initialize for the second time.");
    }

    private void performRelease() {
        boolean bl;
        if (!this.mOpened) {
            return;
        }
        this.mSurfaceView.getHolder().removeCallback(this.mSurfaceCallback);
        this.cleanTapExcludeRegion();
        Object object = this.mTaskStackListener;
        if (object != null) {
            try {
                this.mActivityTaskManager.unregisterTaskStackListener((ITaskStackListener)object);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Failed to unregister task stack listener", remoteException);
            }
            this.mTaskStackListener = null;
        }
        if ((object = this.mVirtualDisplay) != null) {
            ((VirtualDisplay)object).release();
            this.mVirtualDisplay = null;
            bl = true;
        } else {
            bl = false;
        }
        if (bl && (object = this.mActivityViewCallback) != null) {
            ((StateCallback)object).onActivityViewDestroyed(this);
        }
        this.mGuard.close();
        this.mOpened = false;
    }

    private ActivityOptions prepareActivityOptions() {
        if (this.mVirtualDisplay != null) {
            ActivityOptions activityOptions = ActivityOptions.makeBasic();
            activityOptions.setLaunchDisplayId(this.mVirtualDisplay.getDisplay().getDisplayId());
            return activityOptions;
        }
        throw new IllegalStateException("Trying to start activity before ActivityView is ready.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateLocationAndTapExcludeRegion() {
        if (this.mVirtualDisplay == null) return;
        if (!this.isAttachedToWindow()) {
            return;
        }
        try {
            int n;
            int n2;
            block7 : {
                int n3;
                block6 : {
                    n2 = this.mLocationInWindow[0];
                    n3 = this.mLocationInWindow[1];
                    this.getLocationInWindow(this.mLocationInWindow);
                    if (n2 != this.mLocationInWindow[0]) break block6;
                    n = n3;
                    if (n3 == this.mLocationInWindow[1]) break block7;
                }
                n2 = this.mLocationInWindow[0];
                n = this.mLocationInWindow[1];
                n3 = this.mVirtualDisplay.getDisplay().getDisplayId();
                WindowManagerGlobal.getWindowSession().updateDisplayContentLocation(this.getWindow(), n2, n, n3);
                Matrix matrix = new Matrix();
                matrix.set(this.getMatrix());
                matrix.postTranslate(n2, n);
                this.mContext.getSystemService(InputMethodManager.class).reportActivityView(n3, matrix);
            }
            this.updateTapExcludeRegion(n2, n);
            return;
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowAsRuntimeException();
        }
    }

    private void updateTapExcludeRegion(int n, int n2) throws RemoteException {
        if (!this.canReceivePointerEvents()) {
            this.cleanTapExcludeRegion();
            return;
        }
        this.mTapExcludeRegion.set(n, n2, this.getWidth() + n, this.getHeight() + n2);
        ViewParent viewParent = this.getParent();
        if (viewParent != null) {
            viewParent.subtractObscuredTouchableRegion(this.mTapExcludeRegion, this);
        }
        WindowManagerGlobal.getWindowSession().updateTapExcludeRegion(this.getWindow(), this.hashCode(), this.mTapExcludeRegion);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mGuard != null) {
                this.mGuard.warnIfOpen();
                this.performRelease();
            }
            return;
        }
        finally {
            Object.super.finalize();
        }
    }

    @Override
    public boolean gatherTransparentRegion(Region region) {
        this.updateLocationAndTapExcludeRegion();
        return super.gatherTransparentRegion(region);
    }

    public int getVirtualDisplayId() {
        VirtualDisplay virtualDisplay = this.mVirtualDisplay;
        if (virtualDisplay != null) {
            return virtualDisplay.getDisplay().getDisplayId();
        }
        return -1;
    }

    @Override
    public void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        this.mSurfaceView.layout(0, 0, n3 - n, n4 - n2);
    }

    public void onLocationChanged() {
        this.updateLocationAndTapExcludeRegion();
    }

    @Override
    protected void onVisibilityChanged(View view, int n) {
        super.onVisibilityChanged(view, n);
        this.mSurfaceView.setVisibility(n);
    }

    public void performBackPress() {
        Object object = this.mVirtualDisplay;
        if (object == null) {
            return;
        }
        int n = ((VirtualDisplay)object).getDisplay().getDisplayId();
        object = InputManager.getInstance();
        ((InputManager)object).injectInputEvent(ActivityView.createKeyEvent(0, 4, n), 0);
        ((InputManager)object).injectInputEvent(ActivityView.createKeyEvent(1, 4, n), 0);
    }

    public void release() {
        if (this.mVirtualDisplay != null) {
            this.performRelease();
            return;
        }
        throw new IllegalStateException("Trying to release container that is not initialized.");
    }

    public void setCallback(StateCallback stateCallback) {
        this.mActivityViewCallback = stateCallback;
        if (this.mVirtualDisplay != null && (stateCallback = this.mActivityViewCallback) != null) {
            stateCallback.onActivityViewReady(this);
        }
    }

    public void setCornerRadius(float f) {
        this.mSurfaceView.setCornerRadius(f);
    }

    public void setForwardedInsets(Insets insets) {
        this.mForwardedInsets = insets;
        if (this.mVirtualDisplay == null) {
            return;
        }
        try {
            WindowManagerGlobal.getWindowManagerService().setForwardedInsets(this.mVirtualDisplay.getDisplay().getDisplayId(), this.mForwardedInsets);
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowAsRuntimeException();
        }
    }

    public void startActivity(PendingIntent pendingIntent) {
        ActivityOptions activityOptions = this.prepareActivityOptions();
        try {
            pendingIntent.send(null, 0, null, null, null, null, activityOptions.toBundle());
            return;
        }
        catch (PendingIntent.CanceledException canceledException) {
            throw new RuntimeException(canceledException);
        }
    }

    public void startActivity(PendingIntent pendingIntent, ActivityOptions activityOptions) {
        activityOptions.setLaunchDisplayId(this.mVirtualDisplay.getDisplay().getDisplayId());
        try {
            pendingIntent.send(null, 0, null, null, null, null, activityOptions.toBundle());
            return;
        }
        catch (PendingIntent.CanceledException canceledException) {
            throw new RuntimeException(canceledException);
        }
    }

    public void startActivity(Intent intent) {
        ActivityOptions activityOptions = this.prepareActivityOptions();
        this.getContext().startActivity(intent, activityOptions.toBundle());
    }

    public void startActivity(Intent intent, UserHandle userHandle) {
        ActivityOptions activityOptions = this.prepareActivityOptions();
        this.getContext().startActivityAsUser(intent, activityOptions.toBundle(), userHandle);
    }

    public static abstract class StateCallback {
        public abstract void onActivityViewDestroyed(ActivityView var1);

        public abstract void onActivityViewReady(ActivityView var1);

        public void onTaskCreated(int n, ComponentName componentName) {
        }

        public void onTaskMovedToFront(int n) {
        }

        public void onTaskRemovalStarted(int n) {
        }
    }

    private class SurfaceCallback
    implements SurfaceHolder.Callback {
        private SurfaceCallback() {
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int n, int n2, int n3) {
            if (ActivityView.this.mVirtualDisplay != null) {
                ActivityView.this.mVirtualDisplay.resize(n2, n3, ActivityView.this.getBaseDisplayDensity());
            }
            ActivityView.this.updateLocationAndTapExcludeRegion();
        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            if (ActivityView.this.mVirtualDisplay == null) {
                ActivityView.this.initVirtualDisplay(new SurfaceSession());
                if (ActivityView.this.mVirtualDisplay != null && ActivityView.this.mActivityViewCallback != null) {
                    ActivityView.this.mActivityViewCallback.onActivityViewReady(ActivityView.this);
                }
            } else {
                ActivityView.this.mTmpTransaction.reparent(ActivityView.this.mRootSurfaceControl, ActivityView.this.mSurfaceView.getSurfaceControl()).apply();
            }
            if (ActivityView.this.mVirtualDisplay != null) {
                ActivityView.this.mVirtualDisplay.setDisplayState(true);
            }
            ActivityView.this.updateLocationAndTapExcludeRegion();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (ActivityView.this.mVirtualDisplay != null) {
                ActivityView.this.mVirtualDisplay.setDisplayState(false);
            }
            ActivityView.this.clearActivityViewGeometryForIme();
            ActivityView.this.cleanTapExcludeRegion();
        }
    }

    private class TaskStackListenerImpl
    extends TaskStackListener {
        private TaskStackListenerImpl() {
        }

        private ActivityManager.StackInfo getTopMostStackInfo() throws RemoteException {
            int n = ActivityView.this.mVirtualDisplay.getDisplay().getDisplayId();
            List<ActivityManager.StackInfo> list = ActivityView.this.mActivityTaskManager.getAllStackInfos();
            int n2 = list.size();
            for (int i = 0; i < n2; ++i) {
                ActivityManager.StackInfo stackInfo = list.get(i);
                if (stackInfo.displayId != n) {
                    continue;
                }
                return stackInfo;
            }
            return null;
        }

        @Override
        public void onTaskCreated(int n, ComponentName componentName) throws RemoteException {
            if (ActivityView.this.mActivityViewCallback != null && ActivityView.this.mVirtualDisplay != null) {
                ActivityManager.StackInfo stackInfo = this.getTopMostStackInfo();
                if (stackInfo != null && n == stackInfo.taskIds[stackInfo.taskIds.length - 1]) {
                    ActivityView.this.mActivityViewCallback.onTaskCreated(n, componentName);
                }
                return;
            }
        }

        @Override
        public void onTaskDescriptionChanged(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
            if (ActivityView.this.mVirtualDisplay != null && runningTaskInfo.displayId == ActivityView.this.mVirtualDisplay.getDisplay().getDisplayId()) {
                ActivityManager.StackInfo stackInfo = this.getTopMostStackInfo();
                if (stackInfo == null) {
                    return;
                }
                if (runningTaskInfo.taskId == stackInfo.taskIds[stackInfo.taskIds.length - 1]) {
                    ActivityView.this.mSurfaceView.setResizeBackgroundColor(runningTaskInfo.taskDescription.getBackgroundColor());
                }
                return;
            }
        }

        @Override
        public void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
            if (ActivityView.this.mActivityViewCallback != null && ActivityView.this.mVirtualDisplay != null && runningTaskInfo.displayId == ActivityView.this.mVirtualDisplay.getDisplay().getDisplayId()) {
                ActivityManager.StackInfo stackInfo = this.getTopMostStackInfo();
                if (stackInfo != null && runningTaskInfo.taskId == stackInfo.taskIds[stackInfo.taskIds.length - 1]) {
                    ActivityView.this.mActivityViewCallback.onTaskMovedToFront(runningTaskInfo.taskId);
                }
                return;
            }
        }

        @Override
        public void onTaskRemovalStarted(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
            if (ActivityView.this.mActivityViewCallback != null && ActivityView.this.mVirtualDisplay != null && runningTaskInfo.displayId == ActivityView.this.mVirtualDisplay.getDisplay().getDisplayId()) {
                ActivityView.this.mActivityViewCallback.onTaskRemovalStarted(runningTaskInfo.taskId);
                return;
            }
        }
    }

}

