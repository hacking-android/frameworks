/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.animation.ValueAnimator;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.ArraySet;
import android.util.Log;
import android.view.Display;
import android.view.IWindowManager;
import android.view.IWindowSession;
import android.view.IWindowSessionCallback;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowLeaked;
import android.view.WindowManager;
import android.view._$$Lambda$WindowManagerGlobal$2bR3FsEm4EdRwuXfttH0wA2xOW4;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.util.FastPrintWriter;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

public final class WindowManagerGlobal {
    public static final int ADD_APP_EXITING = -4;
    public static final int ADD_BAD_APP_TOKEN = -1;
    public static final int ADD_BAD_SUBWINDOW_TOKEN = -2;
    public static final int ADD_DUPLICATE_ADD = -5;
    public static final int ADD_FLAG_ALWAYS_CONSUME_SYSTEM_BARS = 4;
    public static final int ADD_FLAG_APP_VISIBLE = 2;
    public static final int ADD_FLAG_IN_TOUCH_MODE = 1;
    public static final int ADD_INVALID_DISPLAY = -9;
    public static final int ADD_INVALID_TYPE = -10;
    public static final int ADD_MULTIPLE_SINGLETON = -7;
    public static final int ADD_NOT_APP_TOKEN = -3;
    public static final int ADD_OKAY = 0;
    public static final int ADD_PERMISSION_DENIED = -8;
    public static final int ADD_STARTING_NOT_NEEDED = -6;
    public static final int RELAYOUT_DEFER_SURFACE_DESTROY = 2;
    public static final int RELAYOUT_INSETS_PENDING = 1;
    public static final int RELAYOUT_RES_CONSUME_ALWAYS_SYSTEM_BARS = 64;
    public static final int RELAYOUT_RES_DRAG_RESIZING_DOCKED = 8;
    public static final int RELAYOUT_RES_DRAG_RESIZING_FREEFORM = 16;
    public static final int RELAYOUT_RES_FIRST_TIME = 2;
    public static final int RELAYOUT_RES_IN_TOUCH_MODE = 1;
    public static final int RELAYOUT_RES_SURFACE_CHANGED = 4;
    public static final int RELAYOUT_RES_SURFACE_RESIZED = 32;
    private static final String TAG = "WindowManager";
    @UnsupportedAppUsage
    private static WindowManagerGlobal sDefaultWindowManager;
    @UnsupportedAppUsage
    private static IWindowManager sWindowManagerService;
    @UnsupportedAppUsage
    private static IWindowSession sWindowSession;
    private final ArraySet<View> mDyingViews = new ArraySet();
    @UnsupportedAppUsage
    private final Object mLock = new Object();
    @UnsupportedAppUsage
    private final ArrayList<WindowManager.LayoutParams> mParams = new ArrayList();
    @UnsupportedAppUsage
    private final ArrayList<ViewRootImpl> mRoots = new ArrayList();
    private Runnable mSystemPropertyUpdater;
    @UnsupportedAppUsage
    private final ArrayList<View> mViews = new ArrayList();

    private WindowManagerGlobal() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void doTrimForeground() {
        boolean bl = false;
        Object object = this.mLock;
        // MONITORENTER : object
        int n = this.mRoots.size() - 1;
        do {
            if (n < 0) {
                // MONITOREXIT : object
                if (bl) return;
                ThreadedRenderer.trimMemory(80);
                return;
            }
            ViewRootImpl viewRootImpl = this.mRoots.get(n);
            if (viewRootImpl.mView != null && viewRootImpl.getHostVisibility() == 0 && viewRootImpl.mAttachInfo.mThreadedRenderer != null) {
                bl = true;
            } else {
                viewRootImpl.destroyHardwareResources();
            }
            --n;
        } while (true);
    }

    private int findViewLocked(View view, boolean bl) {
        int n = this.mViews.indexOf(view);
        if (bl && n < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("View=");
            stringBuilder.append(view);
            stringBuilder.append(" not attached to window manager");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static WindowManagerGlobal getInstance() {
        synchronized (WindowManagerGlobal.class) {
            WindowManagerGlobal windowManagerGlobal;
            if (sDefaultWindowManager != null) return sDefaultWindowManager;
            sDefaultWindowManager = windowManagerGlobal = new WindowManagerGlobal();
            return sDefaultWindowManager;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static IWindowManager getWindowManagerService() {
        synchronized (WindowManagerGlobal.class) {
            if (sWindowManagerService != null) return sWindowManagerService;
            sWindowManagerService = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
            try {
                if (sWindowManagerService == null) return sWindowManagerService;
                ValueAnimator.setDurationScale(sWindowManagerService.getCurrentAnimatorScale());
                return sWindowManagerService;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    private static String getWindowName(ViewRootImpl viewRootImpl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((Object)viewRootImpl.mWindowAttributes.getTitle());
        stringBuilder.append("/");
        stringBuilder.append(viewRootImpl.getClass().getName());
        stringBuilder.append('@');
        stringBuilder.append(Integer.toHexString(viewRootImpl.hashCode()));
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static IWindowSession getWindowSession() {
        synchronized (WindowManagerGlobal.class) {
            IInterface iInterface = sWindowSession;
            if (iInterface != null) return sWindowSession;
            try {
                InputMethodManager.ensureDefaultInstanceForDefaultDisplayIfNecessary();
                IWindowManager iWindowManager = WindowManagerGlobal.getWindowManagerService();
                iInterface = new IWindowSessionCallback.Stub(){

                    @Override
                    public void onAnimatorScaleChanged(float f) {
                        ValueAnimator.setDurationScale(f);
                    }
                };
                sWindowSession = iWindowManager.openSession((IWindowSessionCallback)iInterface);
                return sWindowSession;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @UnsupportedAppUsage
    public static void initialize() {
        WindowManagerGlobal.getWindowManagerService();
    }

    static /* synthetic */ void lambda$setStoppedState$0(ViewRootImpl viewRootImpl, boolean bl) {
        viewRootImpl.setWindowStopped(bl);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static IWindowSession peekWindowSession() {
        synchronized (WindowManagerGlobal.class) {
            return sWindowSession;
        }
    }

    private void removeViewLocked(int n, boolean bl) {
        InputMethodManager inputMethodManager;
        ViewRootImpl viewRootImpl = this.mRoots.get(n);
        View view = viewRootImpl.getView();
        if (view != null && (inputMethodManager = view.getContext().getSystemService(InputMethodManager.class)) != null) {
            inputMethodManager.windowDismissed(this.mViews.get(n).getWindowToken());
        }
        bl = viewRootImpl.die(bl);
        if (view != null) {
            view.assignParent(null);
            if (bl) {
                this.mDyingViews.add(view);
            }
        }
    }

    public static boolean shouldDestroyEglContext(int n) {
        if (n >= 80) {
            return true;
        }
        return n >= 60 && !ActivityManager.isHighEndGfx();
    }

    public static void trimForeground() {
        if (ThreadedRenderer.sTrimForeground && ThreadedRenderer.isAvailable()) {
            WindowManagerGlobal.getInstance().doTrimForeground();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addView(View view, ViewGroup.LayoutParams object, Display object2, Window runnable) {
        if (view == null) {
            throw new IllegalArgumentException("view must not be null");
        }
        if (object2 == null) {
            throw new IllegalArgumentException("display must not be null");
        }
        if (!(object instanceof WindowManager.LayoutParams)) {
            throw new IllegalArgumentException("Params must be WindowManager.LayoutParams");
        }
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams)object;
        if (runnable != null) {
            ((Window)((Object)runnable)).adjustLayoutParamsForSubWindow(layoutParams);
        } else {
            object = view.getContext();
            if (object != null && (object.getApplicationInfo().flags & 536870912) != 0) {
                layoutParams.flags |= 16777216;
            }
        }
        Object var6_7 = null;
        object = null;
        Object object3 = this.mLock;
        synchronized (object3) {
            int n;
            if (this.mSystemPropertyUpdater == null) {
                runnable = new Runnable(){

                    /*
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    @Override
                    public void run() {
                        Object object = WindowManagerGlobal.this.mLock;
                        synchronized (object) {
                            int n = WindowManagerGlobal.this.mRoots.size() - 1;
                            while (n >= 0) {
                                ((ViewRootImpl)WindowManagerGlobal.this.mRoots.get(n)).loadSystemProperties();
                                --n;
                            }
                            return;
                        }
                    }
                };
                this.mSystemPropertyUpdater = runnable;
                SystemProperties.addChangeCallback(this.mSystemPropertyUpdater);
            }
            if ((n = this.findViewLocked(view, false)) >= 0) {
                if (!this.mDyingViews.contains(view)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("View ");
                    ((StringBuilder)object).append(view);
                    ((StringBuilder)object).append(" has already been added to the window manager.");
                    object2 = new IllegalStateException(((StringBuilder)object).toString());
                    throw object2;
                }
                this.mRoots.get(n).doDie();
            }
            runnable = var6_7;
            if (layoutParams.type >= 1000) {
                runnable = var6_7;
                if (layoutParams.type <= 1999) {
                    int n2 = this.mViews.size();
                    int n3 = 0;
                    do {
                        runnable = object;
                        if (n3 >= n2) break;
                        if (this.mRoots.get((int)n3).mWindow.asBinder() == layoutParams.token) {
                            object = this.mViews.get(n3);
                        }
                        ++n3;
                    } while (true);
                }
            }
            object = new ViewRootImpl(view.getContext(), (Display)object2);
            view.setLayoutParams(layoutParams);
            this.mViews.add(view);
            this.mRoots.add((ViewRootImpl)object);
            this.mParams.add(layoutParams);
            try {
                ((ViewRootImpl)object).setView(view, layoutParams, (View)((Object)runnable));
                return;
            }
            catch (RuntimeException runtimeException) {
                if (n >= 0) {
                    this.removeViewLocked(n, true);
                }
                throw runtimeException;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void changeCanvasOpacity(IBinder iBinder, boolean bl) {
        if (iBinder == null) {
            return;
        }
        Object object = this.mLock;
        synchronized (object) {
            int n = this.mParams.size() - 1;
            while (n >= 0) {
                if (this.mParams.get((int)n).token == iBinder) {
                    this.mRoots.get(n).changeCanvasOpacity(bl);
                    return;
                }
                --n;
            }
            return;
        }
    }

    public void closeAll(IBinder iBinder, String string2, String string3) {
        this.closeAllExceptView(iBinder, null, string2, string3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void closeAllExceptView(IBinder iBinder, View view, String string2, String string3) {
        Object object = this.mLock;
        synchronized (object) {
            int n = this.mViews.size();
            int n2 = 0;
            while (n2 < n) {
                if (!(view != null && this.mViews.get(n2) == view || iBinder != null && this.mParams.get((int)n2).token != iBinder)) {
                    ViewRootImpl viewRootImpl = this.mRoots.get(n2);
                    if (string2 != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(string3);
                        stringBuilder.append(" ");
                        stringBuilder.append(string2);
                        stringBuilder.append(" has leaked window ");
                        stringBuilder.append(viewRootImpl.getView());
                        stringBuilder.append(" that was originally added here");
                        WindowLeaked windowLeaked = new WindowLeaked(stringBuilder.toString());
                        windowLeaked.setStackTrace(viewRootImpl.getLocation().getStackTrace());
                        Log.e(TAG, "", windowLeaked);
                    }
                    this.removeViewLocked(n2, false);
                }
                ++n2;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void doRemoveView(ViewRootImpl object) {
        Object object2 = this.mLock;
        // MONITORENTER : object2
        int n = this.mRoots.indexOf(object);
        if (n >= 0) {
            this.mRoots.remove(n);
            this.mParams.remove(n);
            object = this.mViews.remove(n);
            this.mDyingViews.remove(object);
        }
        // MONITOREXIT : object2
        if (!ThreadedRenderer.sTrimForeground) return;
        if (!ThreadedRenderer.isAvailable()) return;
        this.doTrimForeground();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void dumpGfxInfo(FileDescriptor object, String[] arrstring) {
        void var1_5;
        FastPrintWriter fastPrintWriter;
        block13 : {
            int n4;
            int n3;
            int n2;
            int[] arrn;
            int n;
            fastPrintWriter = new FastPrintWriter(new FileOutputStream((FileDescriptor)object));
            try {
                Object object2 = this.mLock;
                // MONITORENTER : object2
                n = this.mViews.size();
                fastPrintWriter.println("Profile data in ms:");
            }
            catch (Throwable throwable) {
                // empty catch block
                break block13;
            }
            for (n2 = 0; n2 < n; ++n2) {
                Object object3 = this.mRoots.get(n2);
                fastPrintWriter.printf("\n\t%s (visibility=%d)", WindowManagerGlobal.getWindowName((ViewRootImpl)object3), ((ViewRootImpl)object3).getHostVisibility());
                object3 = object3.getView().mAttachInfo.mThreadedRenderer;
                if (object3 == null) continue;
                ((ThreadedRenderer)object3).dumpGfxInfo(fastPrintWriter, (FileDescriptor)object, (String[])arrn);
            }
            try {
                fastPrintWriter.println("\nView hierarchy:\n");
                n3 = 0;
                n4 = 0;
                arrn = new int[2];
                for (n2 = 0; n2 < n; n3 += arrn[0], n4 += arrn[1], ++n2) {
                    object = this.mRoots.get(n2);
                    ((ViewRootImpl)object).dumpGfxInfo(arrn);
                    fastPrintWriter.printf("  %s\n  %d views, %.2f kB of display lists", WindowManagerGlobal.getWindowName((ViewRootImpl)object), arrn[0], Float.valueOf((float)arrn[1] / 1024.0f));
                    fastPrintWriter.printf("\n\n", new Object[0]);
                }
            }
            catch (Throwable throwable) {}
            fastPrintWriter.printf("\nTotal ViewRootImpl: %d\n", n);
            fastPrintWriter.printf("Total Views:        %d\n", n3);
            fastPrintWriter.printf("Total DisplayList:  %.2f kB\n\n", Float.valueOf((float)n4 / 1024.0f));
            // MONITOREXIT : object2
            ((PrintWriter)fastPrintWriter).flush();
            return;
            try {
                throw throwable;
            }
            catch (Throwable throwable) {}
        }
        ((PrintWriter)fastPrintWriter).flush();
        throw var1_5;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public View getRootView(String object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            int n = this.mRoots.size() - 1;
            while (n >= 0) {
                ViewRootImpl viewRootImpl = this.mRoots.get(n);
                if (((String)object).equals(WindowManagerGlobal.getWindowName(viewRootImpl))) {
                    return viewRootImpl.getView();
                }
                --n;
            }
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public ArrayList<ViewRootImpl> getRootViews(IBinder iBinder) {
        ArrayList<ViewRootImpl> arrayList = new ArrayList<ViewRootImpl>();
        Object object = this.mLock;
        synchronized (object) {
            int n = this.mRoots.size();
            int n2 = 0;
            do {
                block13 : {
                    block14 : {
                        boolean bl;
                        if (n2 >= n) {
                            return arrayList;
                        }
                        WindowManager.LayoutParams layoutParams = this.mParams.get(n2);
                        if (layoutParams.token == null) break block13;
                        if (layoutParams.token == iBinder) break block14;
                        boolean bl2 = bl = false;
                        if (layoutParams.type >= 1000) {
                            bl2 = bl;
                            if (layoutParams.type <= 1999) {
                                int n3 = 0;
                                do {
                                    bl2 = bl;
                                    if (n3 >= n) break;
                                    View view = this.mViews.get(n3);
                                    WindowManager.LayoutParams layoutParams2 = this.mParams.get(n3);
                                    if (layoutParams.token == view.getWindowToken() && layoutParams2.token == iBinder) {
                                        bl2 = true;
                                        break;
                                    }
                                    ++n3;
                                } while (true);
                            }
                        }
                        if (!bl2) break block13;
                    }
                    arrayList.add(this.mRoots.get(n2));
                }
                ++n2;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public String[] getViewRootNames() {
        Object object = this.mLock;
        synchronized (object) {
            int n = this.mRoots.size();
            String[] arrstring = new String[n];
            int n2 = 0;
            while (n2 < n) {
                arrstring[n2] = WindowManagerGlobal.getWindowName(this.mRoots.get(n2));
                ++n2;
            }
            return arrstring;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public View getWindowView(IBinder iBinder) {
        Object object = this.mLock;
        synchronized (object) {
            int n = this.mViews.size();
            int n2 = 0;
            while (n2 < n) {
                View view = this.mViews.get(n2);
                if (view.getWindowToken() == iBinder) {
                    return view;
                }
                ++n2;
            }
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ArrayList<View> getWindowViews() {
        Object object = this.mLock;
        synchronized (object) {
            return new ArrayList<View>(this.mViews);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void removeView(View view, boolean bl) {
        if (view == null) {
            throw new IllegalArgumentException("view must not be null");
        }
        Object object = this.mLock;
        synchronized (object) {
            int n = this.findViewLocked(view, true);
            View view2 = this.mRoots.get(n).getView();
            this.removeViewLocked(n, bl);
            if (view2 == view) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Calling with view ");
            stringBuilder.append(view);
            stringBuilder.append(" but the ViewAncestor is attached to ");
            stringBuilder.append(view2);
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void reportNewConfiguration(Configuration configuration) {
        Object object = this.mLock;
        synchronized (object) {
            int n = this.mViews.size();
            Configuration configuration2 = new Configuration(configuration);
            int n2 = 0;
            while (n2 < n) {
                this.mRoots.get(n2).requestUpdateConfiguration(configuration2);
                ++n2;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void setStoppedState(IBinder object, boolean bl) {
        int n;
        ArrayList<ViewRootImpl> arrayList = null;
        Object object2 = this.mLock;
        // MONITORENTER : object2
        for (n = this.mViews.size() - 1; n >= 0; --n) {
            ArrayList<ViewRootImpl> arrayList2;
            block13 : {
                block12 : {
                    if (object == null) break block12;
                    arrayList2 = arrayList;
                    if (this.mParams.get((int)n).token != object) break block13;
                }
                ViewRootImpl viewRootImpl = this.mRoots.get(n);
                if (viewRootImpl.mThread == Thread.currentThread()) {
                    viewRootImpl.setWindowStopped(bl);
                } else {
                    arrayList2 = arrayList;
                    if (arrayList == null) {
                        arrayList2 = new ArrayList<ViewRootImpl>();
                    }
                    arrayList2.add(viewRootImpl);
                    arrayList = arrayList2;
                }
                this.setStoppedState(viewRootImpl.mAttachInfo.mWindowToken, bl);
                arrayList2 = arrayList;
            }
            arrayList = arrayList2;
        }
        // MONITOREXIT : object2
        if (arrayList == null) return;
        n = arrayList.size() - 1;
        while (n >= 0) {
            object = (ViewRootImpl)arrayList.get(n);
            ((ViewRootImpl)object).mHandler.runWithScissors(new _$$Lambda$WindowManagerGlobal$2bR3FsEm4EdRwuXfttH0wA2xOW4((ViewRootImpl)object, bl), 0L);
            --n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage(maxTargetSdk=28)
    public void trimMemory(int n) {
        if (ThreadedRenderer.isAvailable()) {
            int n2 = n;
            if (WindowManagerGlobal.shouldDestroyEglContext(n)) {
                Object object = this.mLock;
                synchronized (object) {
                    for (n = this.mRoots.size() - 1; n >= 0; --n) {
                        this.mRoots.get(n).destroyHardwareResources();
                    }
                }
                n2 = 80;
            }
            ThreadedRenderer.trimMemory(n2);
            if (ThreadedRenderer.sTrimForeground) {
                this.doTrimForeground();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateViewLayout(View object, ViewGroup.LayoutParams object2) {
        if (object == null) {
            throw new IllegalArgumentException("view must not be null");
        }
        if (object2 instanceof WindowManager.LayoutParams) {
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams)object2;
            ((View)object).setLayoutParams(layoutParams);
            object2 = this.mLock;
            synchronized (object2) {
                int n = this.findViewLocked((View)object, true);
                object = this.mRoots.get(n);
                this.mParams.remove(n);
                this.mParams.add(n, layoutParams);
                ((ViewRootImpl)object).setLayoutParams(layoutParams, false);
                return;
            }
        }
        throw new IllegalArgumentException("Params must be WindowManager.LayoutParams");
    }

}

