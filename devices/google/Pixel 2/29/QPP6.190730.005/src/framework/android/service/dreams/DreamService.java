/*
 * Decompiled with CFR 0.145.
 */
package android.service.dreams;

import android.annotation.UnsupportedAppUsage;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.dreams.IDreamManager;
import android.service.dreams.IDreamService;
import android.util.MathUtils;
import android.util.Slog;
import android.view.ActionMode;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityEvent;
import com.android.internal.policy.PhoneWindow;
import com.android.internal.util.DumpUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class DreamService
extends Service
implements Window.Callback {
    public static final String DREAM_META_DATA = "android.service.dream";
    public static final String DREAM_SERVICE = "dreams";
    public static final String SERVICE_INTERFACE = "android.service.dreams.DreamService";
    private final String TAG;
    private boolean mCanDoze;
    private boolean mDebug;
    private int mDozeScreenBrightness;
    private int mDozeScreenState;
    private boolean mDozing;
    private boolean mFinished;
    private boolean mFullscreen;
    private final Handler mHandler;
    private boolean mInteractive;
    private boolean mLowProfile;
    private final IDreamManager mSandman;
    private boolean mScreenBright;
    private boolean mStarted;
    private boolean mWaking;
    private Window mWindow;
    private IBinder mWindowToken;
    private boolean mWindowless;

    public DreamService() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DreamService.class.getSimpleName());
        stringBuilder.append("[");
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append("]");
        this.TAG = stringBuilder.toString();
        this.mHandler = new Handler();
        this.mLowProfile = true;
        this.mScreenBright = true;
        this.mDozeScreenState = 0;
        this.mDozeScreenBrightness = -1;
        this.mDebug = false;
        this.mSandman = IDreamManager.Stub.asInterface(ServiceManager.getService(DREAM_SERVICE));
    }

    private int applyFlags(int n, int n2, int n3) {
        return n3 & n | n2 & n3;
    }

    private void applySystemUiVisibilityFlags(int n, int n2) {
        Object object = this.mWindow;
        object = object == null ? null : ((Window)object).getDecorView();
        if (object != null) {
            ((View)object).setSystemUiVisibility(this.applyFlags(((View)object).getSystemUiVisibility(), n, n2));
        }
    }

    private void applyWindowFlags(int n, int n2) {
        Object object = this.mWindow;
        if (object != null) {
            object = ((Window)object).getAttributes();
            ((WindowManager.LayoutParams)object).flags = this.applyFlags(((WindowManager.LayoutParams)object).flags, n, n2);
            this.mWindow.setAttributes((WindowManager.LayoutParams)object);
            this.mWindow.getWindowManager().updateViewLayout(this.mWindow.getDecorView(), (ViewGroup.LayoutParams)object);
        }
    }

    private final void attach(IBinder object, boolean bl, IRemoteCallback object2) {
        if (this.mWindowToken != null) {
            object2 = this.TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("attach() called when already attached with token=");
            ((StringBuilder)object).append(this.mWindowToken);
            Slog.e((String)object2, ((StringBuilder)object).toString());
            return;
        }
        if (!this.mFinished && !this.mWaking) {
            this.mWindowToken = object;
            this.mCanDoze = bl;
            if (this.mWindowless && !this.mCanDoze) {
                throw new IllegalStateException("Only doze dreams can be windowless");
            }
            if (!this.mWindowless) {
                this.mWindow = new PhoneWindow(this);
                this.mWindow.setCallback(this);
                this.mWindow.requestFeature(1);
                this.mWindow.setBackgroundDrawable(new ColorDrawable(-16777216));
                this.mWindow.setFormat(-1);
                bl = this.mDebug;
                int n = 0;
                if (bl) {
                    Slog.v(this.TAG, String.format("Attaching window token: %s to window of type %s", object, 2023));
                }
                WindowManager.LayoutParams layoutParams = this.mWindow.getAttributes();
                layoutParams.type = 2023;
                layoutParams.token = object;
                layoutParams.windowAnimations = 16974580;
                int n2 = layoutParams.flags;
                int n3 = this.mFullscreen ? 1024 : 0;
                if (this.mScreenBright) {
                    n = 128;
                }
                layoutParams.flags = n2 | (n | (4784385 | n3));
                this.mWindow.setAttributes(layoutParams);
                this.mWindow.clearFlags(Integer.MIN_VALUE);
                this.mWindow.setWindowManager(null, (IBinder)object, "dream", true);
                this.applySystemUiVisibilityFlags((int)this.mLowProfile, 1);
                try {
                    this.getWindowManager().addView(this.mWindow.getDecorView(), this.mWindow.getAttributes());
                }
                catch (WindowManager.BadTokenException badTokenException) {
                    Slog.i(this.TAG, "attach() called after window token already removed, dream will finish soon");
                    this.mWindow = null;
                    return;
                }
            }
            this.mHandler.post(new Runnable((IRemoteCallback)object2){
                final /* synthetic */ IRemoteCallback val$started;
                {
                    this.val$started = iRemoteCallback;
                }

                /*
                 * Enabled force condition propagation
                 * Lifted jumps to return sites
                 */
                @Override
                public void run() {
                    if (DreamService.this.mWindow == null && !DreamService.this.mWindowless) return;
                    if (DreamService.this.mDebug) {
                        Slog.v(DreamService.this.TAG, "Calling onDreamingStarted()");
                    }
                    DreamService.this.mStarted = true;
                    try {
                        DreamService.this.onDreamingStarted();
                    }
                    catch (Throwable throwable) {
                        try {
                            this.val$started.sendResult(null);
                        }
                        catch (RemoteException remoteException) {
                            throw remoteException.rethrowFromSystemServer();
                        }
                        throw throwable;
                    }
                    try {
                        this.val$started.sendResult(null);
                        return;
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                }
            });
            return;
        }
        Slog.w(this.TAG, "attach() called after dream already finished");
        try {
            this.mSandman.finishSelf((IBinder)object, true);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    private static int clampAbsoluteBrightness(int n) {
        return MathUtils.constrain(n, 0, 255);
    }

    private final void detach() {
        if (this.mStarted) {
            if (this.mDebug) {
                Slog.v(this.TAG, "detach(): Calling onDreamingStopped()");
            }
            this.mStarted = false;
            this.onDreamingStopped();
        }
        if (this.mWindow != null) {
            if (this.mDebug) {
                Slog.v(this.TAG, "detach(): Removing window from window manager");
            }
            this.mWindow.getWindowManager().removeViewImmediate(this.mWindow.getDecorView());
            this.mWindow = null;
        }
        if (this.mWindowToken != null) {
            WindowManagerGlobal.getInstance().closeAll(this.mWindowToken, this.getClass().getName(), "Dream");
            this.mWindowToken = null;
            this.mCanDoze = false;
        }
    }

    private boolean getSystemUiVisibilityFlagValue(int n, boolean bl) {
        Object object = this.mWindow;
        object = object == null ? null : ((Window)object).getDecorView();
        if (object != null) {
            bl = (((View)object).getSystemUiVisibility() & n) != 0;
        }
        return bl;
    }

    private boolean getWindowFlagValue(int n, boolean bl) {
        Window window = this.mWindow;
        if (window != null) {
            bl = (window.getAttributes().flags & n) != 0;
        }
        return bl;
    }

    private void updateDoze() {
        IBinder iBinder = this.mWindowToken;
        if (iBinder == null) {
            Slog.w(this.TAG, "Updating doze without a window token.");
            return;
        }
        if (this.mDozing) {
            try {
                this.mSandman.startDozing(iBinder, this.mDozeScreenState, this.mDozeScreenBrightness);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    private void wakeUp(boolean bl) {
        Object object;
        if (this.mDebug) {
            object = this.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("wakeUp(): fromSystem=");
            stringBuilder.append(bl);
            stringBuilder.append(", mWaking=");
            stringBuilder.append(this.mWaking);
            stringBuilder.append(", mFinished=");
            stringBuilder.append(this.mFinished);
            Slog.v((String)object, stringBuilder.toString());
        }
        if (!this.mWaking && !this.mFinished) {
            this.mWaking = true;
            this.onWakeUp();
            if (!bl && !this.mFinished) {
                object = this.mWindowToken;
                if (object == null) {
                    Slog.w(this.TAG, "WakeUp was called before the dream was attached.");
                } else {
                    try {
                        this.mSandman.finishSelf((IBinder)object, false);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
            }
        }
    }

    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.getWindow().addContentView(view, layoutParams);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public boolean canDoze() {
        return this.mCanDoze;
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if (!this.mInteractive) {
            if (this.mDebug) {
                Slog.v(this.TAG, "Waking up on genericMotionEvent");
            }
            this.wakeUp();
            return true;
        }
        return this.mWindow.superDispatchGenericMotionEvent(motionEvent);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!this.mInteractive) {
            if (this.mDebug) {
                Slog.v(this.TAG, "Waking up on keyEvent");
            }
            this.wakeUp();
            return true;
        }
        if (keyEvent.getKeyCode() == 4) {
            if (this.mDebug) {
                Slog.v(this.TAG, "Waking up on back key");
            }
            this.wakeUp();
            return true;
        }
        return this.mWindow.superDispatchKeyEvent(keyEvent);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        if (!this.mInteractive) {
            if (this.mDebug) {
                Slog.v(this.TAG, "Waking up on keyShortcutEvent");
            }
            this.wakeUp();
            return true;
        }
        return this.mWindow.superDispatchKeyShortcutEvent(keyEvent);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (!this.mInteractive) {
            if (this.mDebug) {
                Slog.v(this.TAG, "Waking up on touchEvent");
            }
            this.wakeUp();
            return true;
        }
        return this.mWindow.superDispatchTouchEvent(motionEvent);
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        if (!this.mInteractive) {
            if (this.mDebug) {
                Slog.v(this.TAG, "Waking up on trackballEvent");
            }
            this.wakeUp();
            return true;
        }
        return this.mWindow.superDispatchTrackballEvent(motionEvent);
    }

    @Override
    protected void dump(final FileDescriptor fileDescriptor, PrintWriter printWriter, final String[] arrstring) {
        DumpUtils.dumpAsync(this.mHandler, new DumpUtils.Dump(){

            @Override
            public void dump(PrintWriter printWriter, String string2) {
                DreamService.this.dumpOnHandler(fileDescriptor, printWriter, arrstring);
            }
        }, printWriter, "", 1000L);
    }

    protected void dumpOnHandler(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        object = new StringBuilder();
        ((StringBuilder)object).append(this.TAG);
        ((StringBuilder)object).append(": ");
        printWriter.print(((StringBuilder)object).toString());
        if (this.mWindowToken == null) {
            printWriter.println("stopped");
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("running (token=");
            ((StringBuilder)object).append(this.mWindowToken);
            ((StringBuilder)object).append(")");
            printWriter.println(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("  window: ");
        ((StringBuilder)object).append(this.mWindow);
        printWriter.println(((StringBuilder)object).toString());
        printWriter.print("  flags:");
        if (this.isInteractive()) {
            printWriter.print(" interactive");
        }
        if (this.isLowProfile()) {
            printWriter.print(" lowprofile");
        }
        if (this.isFullscreen()) {
            printWriter.print(" fullscreen");
        }
        if (this.isScreenBright()) {
            printWriter.print(" bright");
        }
        if (this.isWindowless()) {
            printWriter.print(" windowless");
        }
        if (this.isDozing()) {
            printWriter.print(" dozing");
        } else if (this.canDoze()) {
            printWriter.print(" candoze");
        }
        printWriter.println();
        if (this.canDoze()) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  doze screen state: ");
            ((StringBuilder)object).append(Display.stateToString(this.mDozeScreenState));
            printWriter.println(((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append("  doze screen brightness: ");
            ((StringBuilder)object).append(this.mDozeScreenBrightness);
            printWriter.println(((StringBuilder)object).toString());
        }
    }

    public <T extends View> T findViewById(int n) {
        return this.getWindow().findViewById(n);
    }

    public final void finish() {
        Object object;
        if (this.mDebug) {
            object = this.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("finish(): mFinished=");
            stringBuilder.append(this.mFinished);
            Slog.v((String)object, stringBuilder.toString());
        }
        if (!this.mFinished) {
            this.mFinished = true;
            object = this.mWindowToken;
            if (object == null) {
                Slog.w(this.TAG, "Finish was called before the dream was attached.");
            } else {
                try {
                    this.mSandman.finishSelf((IBinder)object, true);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
            this.stopSelf();
        }
    }

    @UnsupportedAppUsage
    public int getDozeScreenBrightness() {
        return this.mDozeScreenBrightness;
    }

    public int getDozeScreenState() {
        return this.mDozeScreenState;
    }

    public Window getWindow() {
        return this.mWindow;
    }

    public WindowManager getWindowManager() {
        Object object = this.mWindow;
        object = object != null ? ((Window)object).getWindowManager() : null;
        return object;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public boolean isDozing() {
        return this.mDozing;
    }

    public boolean isFullscreen() {
        return this.mFullscreen;
    }

    public boolean isInteractive() {
        return this.mInteractive;
    }

    public boolean isLowProfile() {
        return this.getSystemUiVisibilityFlagValue(1, this.mLowProfile);
    }

    public boolean isScreenBright() {
        return this.getWindowFlagValue(128, this.mScreenBright);
    }

    public boolean isWindowless() {
        return this.mWindowless;
    }

    @Override
    public void onActionModeFinished(ActionMode actionMode) {
    }

    @Override
    public void onActionModeStarted(ActionMode actionMode) {
    }

    @Override
    public void onAttachedToWindow() {
    }

    @Override
    public final IBinder onBind(Intent intent) {
        if (this.mDebug) {
            String string2 = this.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onBind() intent = ");
            stringBuilder.append(intent);
            Slog.v(string2, stringBuilder.toString());
        }
        return new DreamServiceWrapper();
    }

    @Override
    public void onContentChanged() {
    }

    @Override
    public void onCreate() {
        if (this.mDebug) {
            Slog.v(this.TAG, "onCreate()");
        }
        super.onCreate();
    }

    @Override
    public boolean onCreatePanelMenu(int n, Menu menu2) {
        return false;
    }

    @Override
    public View onCreatePanelView(int n) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (this.mDebug) {
            Slog.v(this.TAG, "onDestroy()");
        }
        this.detach();
        super.onDestroy();
    }

    @Override
    public void onDetachedFromWindow() {
    }

    public void onDreamingStarted() {
        if (this.mDebug) {
            Slog.v(this.TAG, "onDreamingStarted()");
        }
    }

    public void onDreamingStopped() {
        if (this.mDebug) {
            Slog.v(this.TAG, "onDreamingStopped()");
        }
    }

    @Override
    public boolean onMenuItemSelected(int n, MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onMenuOpened(int n, Menu menu2) {
        return false;
    }

    @Override
    public void onPanelClosed(int n, Menu menu2) {
    }

    @Override
    public boolean onPreparePanel(int n, View view, Menu menu2) {
        return false;
    }

    @Override
    public boolean onSearchRequested() {
        return false;
    }

    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return this.onSearchRequested();
    }

    public void onWakeUp() {
        this.finish();
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
    }

    @Override
    public void onWindowFocusChanged(boolean bl) {
    }

    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }

    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int n) {
        return null;
    }

    public final <T extends View> T requireViewById(int n) {
        T t = this.findViewById(n);
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this DreamService");
    }

    public void setContentView(int n) {
        this.getWindow().setContentView(n);
    }

    public void setContentView(View view) {
        this.getWindow().setContentView(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        this.getWindow().setContentView(view, layoutParams);
    }

    public void setDebug(boolean bl) {
        this.mDebug = bl;
    }

    @UnsupportedAppUsage
    public void setDozeScreenBrightness(int n) {
        int n2 = n;
        if (n != -1) {
            n2 = DreamService.clampAbsoluteBrightness(n);
        }
        if (this.mDozeScreenBrightness != n2) {
            this.mDozeScreenBrightness = n2;
            this.updateDoze();
        }
    }

    @UnsupportedAppUsage
    public void setDozeScreenState(int n) {
        if (this.mDozeScreenState != n) {
            this.mDozeScreenState = n;
            this.updateDoze();
        }
    }

    public void setFullscreen(boolean bl) {
        if (this.mFullscreen != bl) {
            this.mFullscreen = bl;
            int n = this.mFullscreen ? 1024 : 0;
            this.applyWindowFlags(n, 1024);
        }
    }

    public void setInteractive(boolean bl) {
        this.mInteractive = bl;
    }

    public void setLowProfile(boolean bl) {
        if (this.mLowProfile != bl) {
            this.mLowProfile = bl;
            int n = this.mLowProfile ? 1 : 0;
            this.applySystemUiVisibilityFlags(n, 1);
        }
    }

    public void setScreenBright(boolean bl) {
        if (this.mScreenBright != bl) {
            this.mScreenBright = bl;
            int n = this.mScreenBright ? 128 : 0;
            this.applyWindowFlags(n, 128);
        }
    }

    @UnsupportedAppUsage
    public void setWindowless(boolean bl) {
        this.mWindowless = bl;
    }

    @UnsupportedAppUsage
    public void startDozing() {
        if (this.mCanDoze && !this.mDozing) {
            this.mDozing = true;
            this.updateDoze();
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void stopDozing() {
        if (this.mDozing) {
            this.mDozing = false;
            try {
                this.mSandman.stopDozing(this.mWindowToken);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public final void wakeUp() {
        this.wakeUp(false);
    }

    private final class DreamServiceWrapper
    extends IDreamService.Stub {
        private DreamServiceWrapper() {
        }

        @Override
        public void attach(final IBinder iBinder, final boolean bl, final IRemoteCallback iRemoteCallback) {
            DreamService.this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    DreamService.this.attach(iBinder, bl, iRemoteCallback);
                }
            });
        }

        @Override
        public void detach() {
            DreamService.this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    DreamService.this.detach();
                }
            });
        }

        @Override
        public void wakeUp() {
            DreamService.this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    DreamService.this.wakeUp(true);
                }
            });
        }

    }

}

