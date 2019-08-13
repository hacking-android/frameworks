/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.wallpaper.-$
 *  android.service.wallpaper.-$$Lambda
 *  android.service.wallpaper.-$$Lambda$87Do-TfJA3qVM7QF6F_6BpQlQTA
 */
package android.service.wallpaper;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.Service;
import android.app.WallpaperColors;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.wallpaper.-$;
import android.service.wallpaper.IWallpaperConnection;
import android.service.wallpaper.IWallpaperEngine;
import android.service.wallpaper.IWallpaperService;
import android.service.wallpaper._$$Lambda$87Do_TfJA3qVM7QF6F_6BpQlQTA;
import android.service.wallpaper._$$Lambda$vsWBQpiXExY07tlrSzTqh4pNQAQ;
import android.util.Log;
import android.util.MergedConfiguration;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.IWindow;
import android.view.IWindowSession;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InsetsState;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import com.android.internal.R;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.HandlerCaller;
import com.android.internal.view.BaseIWindow;
import com.android.internal.view.BaseSurfaceHolder;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public abstract class WallpaperService
extends Service {
    static final boolean DEBUG = false;
    private static final int DO_ATTACH = 10;
    private static final int DO_DETACH = 20;
    private static final int DO_IN_AMBIENT_MODE = 50;
    private static final int DO_SET_DESIRED_SIZE = 30;
    private static final int DO_SET_DISPLAY_PADDING = 40;
    private static final int MSG_REQUEST_WALLPAPER_COLORS = 10050;
    private static final int MSG_TOUCH_EVENT = 10040;
    private static final int MSG_UPDATE_SURFACE = 10000;
    private static final int MSG_VISIBILITY_CHANGED = 10010;
    private static final int MSG_WALLPAPER_COMMAND = 10025;
    private static final int MSG_WALLPAPER_OFFSETS = 10020;
    private static final int MSG_WINDOW_MOVED = 10035;
    @UnsupportedAppUsage
    private static final int MSG_WINDOW_RESIZED = 10030;
    private static final int NOTIFY_COLORS_RATE_LIMIT_MS = 1000;
    public static final String SERVICE_INTERFACE = "android.service.wallpaper.WallpaperService";
    public static final String SERVICE_META_DATA = "android.service.wallpaper";
    static final String TAG = "WallpaperService";
    private final ArrayList<Engine> mActiveEngines = new ArrayList();

    @Override
    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.print("State of wallpaper ");
        printWriter.print(this);
        printWriter.println(":");
        for (int i = 0; i < this.mActiveEngines.size(); ++i) {
            Engine engine = this.mActiveEngines.get(i);
            printWriter.print("  Engine ");
            printWriter.print(engine);
            printWriter.println(":");
            engine.dump("    ", fileDescriptor, printWriter, arrstring);
        }
    }

    @Override
    public final IBinder onBind(Intent intent) {
        return new IWallpaperServiceWrapper(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public abstract Engine onCreateEngine();

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < this.mActiveEngines.size(); ++i) {
            this.mActiveEngines.get(i).detach();
        }
        this.mActiveEngines.clear();
    }

    public class Engine {
        final Rect mBackdropFrame = new Rect();
        HandlerCaller mCaller;
        private final Supplier<Long> mClockFunction;
        IWallpaperConnection mConnection;
        final Rect mContentInsets = new Rect();
        boolean mCreated;
        int mCurHeight;
        int mCurWidth;
        int mCurWindowFlags = this.mWindowFlags;
        int mCurWindowPrivateFlags = this.mWindowPrivateFlags;
        boolean mDestroyed;
        final Rect mDispatchedContentInsets = new Rect();
        DisplayCutout mDispatchedDisplayCutout = DisplayCutout.NO_CUTOUT;
        final Rect mDispatchedOutsets = new Rect();
        final Rect mDispatchedOverscanInsets = new Rect();
        final Rect mDispatchedStableInsets = new Rect();
        private Display mDisplay;
        private Context mDisplayContext;
        final DisplayCutout.ParcelableWrapper mDisplayCutout = new DisplayCutout.ParcelableWrapper();
        private final DisplayManager.DisplayListener mDisplayListener = new DisplayManager.DisplayListener(){

            @Override
            public void onDisplayAdded(int n) {
            }

            @Override
            public void onDisplayChanged(int n) {
                if (Engine.this.mDisplay.getDisplayId() == n) {
                    Engine.this.reportVisibility();
                }
            }

            @Override
            public void onDisplayRemoved(int n) {
            }
        };
        private int mDisplayState;
        boolean mDrawingAllowed;
        final Rect mFinalStableInsets = new Rect();
        final Rect mFinalSystemInsets = new Rect();
        boolean mFixedSizeAllowed;
        int mFormat;
        private final Handler mHandler;
        int mHeight;
        IWallpaperEngineWrapper mIWallpaperEngine;
        boolean mInitializing = true;
        InputChannel mInputChannel;
        WallpaperInputEventReceiver mInputEventReceiver;
        final InsetsState mInsetsState = new InsetsState();
        boolean mIsCreating;
        boolean mIsInAmbientMode;
        private long mLastColorInvalidation;
        final WindowManager.LayoutParams mLayout = new WindowManager.LayoutParams();
        final Object mLock = new Object();
        final MergedConfiguration mMergedConfiguration = new MergedConfiguration();
        private final Runnable mNotifyColorsChanged = new _$$Lambda$vsWBQpiXExY07tlrSzTqh4pNQAQ(this);
        boolean mOffsetMessageEnqueued;
        boolean mOffsetsChanged;
        final Rect mOutsets = new Rect();
        final Rect mOverscanInsets = new Rect();
        MotionEvent mPendingMove;
        boolean mPendingSync;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        float mPendingXOffset;
        float mPendingXOffsetStep;
        float mPendingYOffset;
        float mPendingYOffsetStep;
        boolean mReportedVisible;
        IWindowSession mSession;
        final Rect mStableInsets = new Rect();
        SurfaceControl mSurfaceControl = new SurfaceControl();
        boolean mSurfaceCreated;
        final BaseSurfaceHolder mSurfaceHolder = new BaseSurfaceHolder(){
            {
                this.mRequestedFormat = 2;
            }

            private void prepareToDraw() {
                if (Engine.this.mDisplayState == 3 || Engine.this.mDisplayState == 4) {
                    try {
                        Engine.this.mSession.pokeDrawLock(Engine.this.mWindow);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
            }

            @Override
            public boolean isCreating() {
                return Engine.this.mIsCreating;
            }

            @Override
            public Canvas lockCanvas() {
                this.prepareToDraw();
                return super.lockCanvas();
            }

            @Override
            public Canvas lockCanvas(Rect rect) {
                this.prepareToDraw();
                return super.lockCanvas(rect);
            }

            @Override
            public Canvas lockHardwareCanvas() {
                this.prepareToDraw();
                return super.lockHardwareCanvas();
            }

            @Override
            public boolean onAllowLockCanvas() {
                return Engine.this.mDrawingAllowed;
            }

            @Override
            public void onRelayoutContainer() {
                Message message = Engine.this.mCaller.obtainMessage(10000);
                Engine.this.mCaller.sendMessage(message);
            }

            @Override
            public void onUpdateSurface() {
                Message message = Engine.this.mCaller.obtainMessage(10000);
                Engine.this.mCaller.sendMessage(message);
            }

            @Override
            public void setFixedSize(int n, int n2) {
                if (Engine.this.mFixedSizeAllowed) {
                    super.setFixedSize(n, n2);
                    return;
                }
                throw new UnsupportedOperationException("Wallpapers currently only support sizing from layout");
            }

            @Override
            public void setKeepScreenOn(boolean bl) {
                throw new UnsupportedOperationException("Wallpapers do not support keep screen on");
            }
        };
        int mType;
        boolean mVisible;
        final Rect mVisibleInsets = new Rect();
        int mWidth;
        final Rect mWinFrame = new Rect();
        final BaseIWindow mWindow = new BaseIWindow(){

            @Override
            public void dispatchAppVisibility(boolean bl) {
                if (!Engine.this.mIWallpaperEngine.mIsPreview) {
                    Object object = Engine.this.mCaller;
                    object = ((HandlerCaller)object).obtainMessageI(10010, (int)bl);
                    Engine.this.mCaller.sendMessage((Message)object);
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void dispatchWallpaperCommand(String object, int n, int n2, int n3, Bundle bundle, boolean bl) {
                Object object2 = Engine.this.mLock;
                synchronized (object2) {
                    WallpaperCommand wallpaperCommand = new WallpaperCommand();
                    wallpaperCommand.action = object;
                    wallpaperCommand.x = n;
                    wallpaperCommand.y = n2;
                    wallpaperCommand.z = n3;
                    wallpaperCommand.extras = bundle;
                    wallpaperCommand.sync = bl;
                    object = Engine.this.mCaller.obtainMessage(10025);
                    ((Message)object).obj = wallpaperCommand;
                    Engine.this.mCaller.sendMessage((Message)object);
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, boolean bl) {
                Object object = Engine.this.mLock;
                synchronized (object) {
                    Engine.this.mPendingXOffset = f;
                    Engine.this.mPendingYOffset = f2;
                    Engine.this.mPendingXOffsetStep = f3;
                    Engine.this.mPendingYOffsetStep = f4;
                    if (bl) {
                        Engine.this.mPendingSync = true;
                    }
                    if (!Engine.this.mOffsetMessageEnqueued) {
                        Engine.this.mOffsetMessageEnqueued = true;
                        Message message = Engine.this.mCaller.obtainMessage(10020);
                        Engine.this.mCaller.sendMessage(message);
                    }
                    return;
                }
            }

            @Override
            public void moved(int n, int n2) {
                Message message = Engine.this.mCaller.obtainMessageII(10035, n, n2);
                Engine.this.mCaller.sendMessage(message);
            }

            @Override
            public void resized(Rect object, Rect rect, Rect rect2, Rect rect3, Rect rect4, Rect rect5, boolean bl, MergedConfiguration mergedConfiguration, Rect rect6, boolean bl2, boolean bl3, int n, DisplayCutout.ParcelableWrapper parcelableWrapper) {
                object = Engine.this.mCaller;
                object = ((HandlerCaller)object).obtainMessageIO(10030, (int)bl, rect5);
                Engine.this.mCaller.sendMessage((Message)object);
            }
        };
        int mWindowFlags = 16;
        int mWindowPrivateFlags = 4;
        IBinder mWindowToken;

        public Engine() {
            this((Supplier<Long>)_$$Lambda$87Do_TfJA3qVM7QF6F_6BpQlQTA.INSTANCE, Handler.getMain());
        }

        @VisibleForTesting
        public Engine(Supplier<Long> supplier, Handler handler) {
            this.mClockFunction = supplier;
            this.mHandler = handler;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void dispatchPointer(MotionEvent parcelable) {
            if (!parcelable.isTouchEvent()) {
                parcelable.recycle();
                return;
            }
            Object object = this.mLock;
            synchronized (object) {
                this.mPendingMove = parcelable.getAction() == 2 ? parcelable : null;
            }
            parcelable = this.mCaller.obtainMessageO(10040, parcelable);
            this.mCaller.sendMessage((Message)parcelable);
        }

        void attach(IWallpaperEngineWrapper iWallpaperEngineWrapper) {
            if (this.mDestroyed) {
                return;
            }
            this.mIWallpaperEngine = iWallpaperEngineWrapper;
            this.mCaller = iWallpaperEngineWrapper.mCaller;
            this.mConnection = iWallpaperEngineWrapper.mConnection;
            this.mWindowToken = iWallpaperEngineWrapper.mWindowToken;
            this.mSurfaceHolder.setSizeFromLayout();
            this.mInitializing = true;
            this.mSession = WindowManagerGlobal.getWindowSession();
            this.mWindow.setSession(this.mSession);
            this.mLayout.packageName = WallpaperService.this.getPackageName();
            this.mIWallpaperEngine.mDisplayManager.registerDisplayListener(this.mDisplayListener, this.mCaller.getHandler());
            this.mDisplay = this.mIWallpaperEngine.mDisplay;
            this.mDisplayContext = WallpaperService.this.createDisplayContext(this.mDisplay);
            this.mDisplayState = this.mDisplay.getState();
            this.onCreate(this.mSurfaceHolder);
            this.mInitializing = false;
            this.mReportedVisible = false;
            this.updateSurface(false, false, false);
        }

        void detach() {
            if (this.mDestroyed) {
                return;
            }
            this.mDestroyed = true;
            if (this.mIWallpaperEngine.mDisplayManager != null) {
                this.mIWallpaperEngine.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
            }
            if (this.mVisible) {
                this.mVisible = false;
                this.onVisibilityChanged(false);
            }
            this.reportSurfaceDestroyed();
            this.onDestroy();
            if (this.mCreated) {
                try {
                    if (this.mInputEventReceiver != null) {
                        this.mInputEventReceiver.dispose();
                        this.mInputEventReceiver = null;
                    }
                    this.mSession.remove(this.mWindow);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
                this.mSurfaceHolder.mSurface.release();
                this.mCreated = false;
                InputChannel inputChannel = this.mInputChannel;
                if (inputChannel != null) {
                    inputChannel.dispose();
                    this.mInputChannel = null;
                }
            }
        }

        @VisibleForTesting
        public void doAmbientModeChanged(boolean bl, long l) {
            if (!this.mDestroyed) {
                this.mIsInAmbientMode = bl;
                if (this.mCreated) {
                    this.onAmbientModeChanged(bl, l);
                }
            }
        }

        void doCommand(WallpaperCommand wallpaperCommand) {
            Bundle bundle = !this.mDestroyed ? this.onCommand(wallpaperCommand.action, wallpaperCommand.x, wallpaperCommand.y, wallpaperCommand.z, wallpaperCommand.extras, wallpaperCommand.sync) : null;
            if (wallpaperCommand.sync) {
                try {
                    this.mSession.wallpaperCommandComplete(this.mWindow.asBinder(), bundle);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
        }

        void doDesiredSizeChanged(int n, int n2) {
            if (!this.mDestroyed) {
                IWallpaperEngineWrapper iWallpaperEngineWrapper = this.mIWallpaperEngine;
                iWallpaperEngineWrapper.mReqWidth = n;
                iWallpaperEngineWrapper.mReqHeight = n2;
                this.onDesiredSizeChanged(n, n2);
                this.doOffsetsChanged(true);
            }
        }

        void doDisplayPaddingChanged(Rect rect) {
            if (!this.mDestroyed && !this.mIWallpaperEngine.mDisplayPadding.equals(rect)) {
                this.mIWallpaperEngine.mDisplayPadding.set(rect);
                this.updateSurface(true, false, false);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        void doOffsetsChanged(boolean bl) {
            if (this.mDestroyed) {
                return;
            }
            if (!bl && !this.mOffsetsChanged) {
                return;
            }
            Object object = this.mLock;
            // MONITORENTER : object
            float f = this.mPendingXOffset;
            float f2 = this.mPendingYOffset;
            float f3 = this.mPendingXOffsetStep;
            float f4 = this.mPendingYOffsetStep;
            bl = this.mPendingSync;
            int n = 0;
            this.mPendingSync = false;
            this.mOffsetMessageEnqueued = false;
            // MONITOREXIT : object
            if (this.mSurfaceCreated) {
                if (this.mReportedVisible) {
                    int n2 = this.mIWallpaperEngine.mReqWidth - this.mCurWidth;
                    n2 = n2 > 0 ? -((int)((float)n2 * f + 0.5f)) : 0;
                    int n3 = this.mIWallpaperEngine.mReqHeight - this.mCurHeight;
                    if (n3 > 0) {
                        n = -((int)((float)n3 * f2 + 0.5f));
                    }
                    this.onOffsetsChanged(f, f2, f3, f4, n2, n);
                } else {
                    this.mOffsetsChanged = true;
                }
            }
            if (!bl) return;
            try {
                this.mSession.wallpaperOffsetsComplete(this.mWindow.asBinder());
                return;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        void doVisibilityChanged(boolean bl) {
            if (!this.mDestroyed) {
                this.mVisible = bl;
                this.reportVisibility();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected void dump(String string2, FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
            printWriter.print(string2);
            printWriter.print("mInitializing=");
            printWriter.print(this.mInitializing);
            printWriter.print(" mDestroyed=");
            printWriter.println(this.mDestroyed);
            printWriter.print(string2);
            printWriter.print("mVisible=");
            printWriter.print(this.mVisible);
            printWriter.print(" mReportedVisible=");
            printWriter.println(this.mReportedVisible);
            printWriter.print(string2);
            printWriter.print("mDisplay=");
            printWriter.println(this.mDisplay);
            printWriter.print(string2);
            printWriter.print("mCreated=");
            printWriter.print(this.mCreated);
            printWriter.print(" mSurfaceCreated=");
            printWriter.print(this.mSurfaceCreated);
            printWriter.print(" mIsCreating=");
            printWriter.print(this.mIsCreating);
            printWriter.print(" mDrawingAllowed=");
            printWriter.println(this.mDrawingAllowed);
            printWriter.print(string2);
            printWriter.print("mWidth=");
            printWriter.print(this.mWidth);
            printWriter.print(" mCurWidth=");
            printWriter.print(this.mCurWidth);
            printWriter.print(" mHeight=");
            printWriter.print(this.mHeight);
            printWriter.print(" mCurHeight=");
            printWriter.println(this.mCurHeight);
            printWriter.print(string2);
            printWriter.print("mType=");
            printWriter.print(this.mType);
            printWriter.print(" mWindowFlags=");
            printWriter.print(this.mWindowFlags);
            printWriter.print(" mCurWindowFlags=");
            printWriter.println(this.mCurWindowFlags);
            printWriter.print(string2);
            printWriter.print("mWindowPrivateFlags=");
            printWriter.print(this.mWindowPrivateFlags);
            printWriter.print(" mCurWindowPrivateFlags=");
            printWriter.println(this.mCurWindowPrivateFlags);
            printWriter.print(string2);
            printWriter.print("mVisibleInsets=");
            printWriter.print(this.mVisibleInsets.toShortString());
            printWriter.print(" mWinFrame=");
            printWriter.print(this.mWinFrame.toShortString());
            printWriter.print(" mContentInsets=");
            printWriter.println(this.mContentInsets.toShortString());
            printWriter.print(string2);
            printWriter.print("mConfiguration=");
            printWriter.println(this.mMergedConfiguration.getMergedConfiguration());
            printWriter.print(string2);
            printWriter.print("mLayout=");
            printWriter.println(this.mLayout);
            object = this.mLock;
            synchronized (object) {
                printWriter.print(string2);
                printWriter.print("mPendingXOffset=");
                printWriter.print(this.mPendingXOffset);
                printWriter.print(" mPendingXOffset=");
                printWriter.println(this.mPendingXOffset);
                printWriter.print(string2);
                printWriter.print("mPendingXOffsetStep=");
                printWriter.print(this.mPendingXOffsetStep);
                printWriter.print(" mPendingXOffsetStep=");
                printWriter.println(this.mPendingXOffsetStep);
                printWriter.print(string2);
                printWriter.print("mOffsetMessageEnqueued=");
                printWriter.print(this.mOffsetMessageEnqueued);
                printWriter.print(" mPendingSync=");
                printWriter.println(this.mPendingSync);
                if (this.mPendingMove != null) {
                    printWriter.print(string2);
                    printWriter.print("mPendingMove=");
                    printWriter.println(this.mPendingMove);
                }
                return;
            }
        }

        public int getDesiredMinimumHeight() {
            return this.mIWallpaperEngine.mReqHeight;
        }

        public int getDesiredMinimumWidth() {
            return this.mIWallpaperEngine.mReqWidth;
        }

        public Context getDisplayContext() {
            return this.mDisplayContext;
        }

        public SurfaceHolder getSurfaceHolder() {
            return this.mSurfaceHolder;
        }

        @SystemApi
        public boolean isInAmbientMode() {
            return this.mIsInAmbientMode;
        }

        public boolean isPreview() {
            return this.mIWallpaperEngine.mIsPreview;
        }

        public boolean isVisible() {
            return this.mReportedVisible;
        }

        public void notifyColorsChanged() {
            long l = this.mClockFunction.get();
            if (l - this.mLastColorInvalidation < 1000L) {
                Log.w(WallpaperService.TAG, "This call has been deferred. You should only call notifyColorsChanged() once every 1.0 seconds.");
                if (!this.mHandler.hasCallbacks(this.mNotifyColorsChanged)) {
                    this.mHandler.postDelayed(this.mNotifyColorsChanged, 1000L);
                }
                return;
            }
            this.mLastColorInvalidation = l;
            this.mHandler.removeCallbacks(this.mNotifyColorsChanged);
            try {
                WallpaperColors wallpaperColors = this.onComputeColors();
                if (this.mConnection != null) {
                    this.mConnection.onWallpaperColorsChanged(wallpaperColors, this.mDisplay.getDisplayId());
                } else {
                    Log.w(WallpaperService.TAG, "Can't notify system because wallpaper connection was not established.");
                }
            }
            catch (RemoteException remoteException) {
                Log.w(WallpaperService.TAG, "Can't notify system because wallpaper connection was lost.", remoteException);
            }
        }

        @SystemApi
        public void onAmbientModeChanged(boolean bl, long l) {
        }

        public void onApplyWindowInsets(WindowInsets windowInsets) {
        }

        public Bundle onCommand(String string2, int n, int n2, int n3, Bundle bundle, boolean bl) {
            return null;
        }

        public WallpaperColors onComputeColors() {
            return null;
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
        }

        public void onDesiredSizeChanged(int n, int n2) {
        }

        public void onDestroy() {
        }

        public void onOffsetsChanged(float f, float f2, float f3, float f4, int n, int n2) {
        }

        public void onSurfaceChanged(SurfaceHolder surfaceHolder, int n, int n2, int n3) {
        }

        public void onSurfaceCreated(SurfaceHolder surfaceHolder) {
        }

        public void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
        }

        public void onSurfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
        }

        public void onTouchEvent(MotionEvent motionEvent) {
        }

        public void onVisibilityChanged(boolean bl) {
        }

        void reportSurfaceDestroyed() {
            if (this.mSurfaceCreated) {
                this.mSurfaceCreated = false;
                this.mSurfaceHolder.ungetCallbacks();
                SurfaceHolder.Callback[] arrcallback = this.mSurfaceHolder.getCallbacks();
                if (arrcallback != null) {
                    int n = arrcallback.length;
                    for (int i = 0; i < n; ++i) {
                        arrcallback[i].surfaceDestroyed(this.mSurfaceHolder);
                    }
                }
                this.onSurfaceDestroyed(this.mSurfaceHolder);
            }
        }

        void reportVisibility() {
            if (!this.mDestroyed) {
                Display display = this.mDisplay;
                int n = display == null ? 0 : display.getState();
                this.mDisplayState = n;
                boolean bl = this.mVisible;
                boolean bl2 = true;
                if (!bl || this.mDisplayState == 1) {
                    bl2 = false;
                }
                if (this.mReportedVisible != bl2) {
                    this.mReportedVisible = bl2;
                    if (bl2) {
                        this.doOffsetsChanged(false);
                        this.updateSurface(false, false, false);
                    }
                    this.onVisibilityChanged(bl2);
                }
            }
        }

        @VisibleForTesting
        public void setCreated(boolean bl) {
            this.mCreated = bl;
        }

        @UnsupportedAppUsage
        public void setFixedSizeAllowed(boolean bl) {
            this.mFixedSizeAllowed = bl;
        }

        public void setOffsetNotificationsEnabled(boolean bl) {
            int n = bl ? this.mWindowPrivateFlags | 4 : this.mWindowPrivateFlags & -5;
            this.mWindowPrivateFlags = n;
            if (this.mCreated) {
                this.updateSurface(false, false, false);
            }
        }

        public void setTouchEventsEnabled(boolean bl) {
            int n = bl ? this.mWindowFlags & -17 : this.mWindowFlags | 16;
            this.mWindowFlags = n;
            if (this.mCreated) {
                this.updateSurface(false, false, false);
            }
        }

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        void updateSurface(boolean var1_1, boolean var2_2, boolean var3_3) {
            block73 : {
                block72 : {
                    block71 : {
                        block70 : {
                            block69 : {
                                if (this.mDestroyed) {
                                    Log.w("WallpaperService", "Ignoring updateSurface: destroyed");
                                }
                                var4_4 = 0;
                                var5_5 = this.mSurfaceHolder.getRequestedWidth();
                                if (var5_5 <= 0) {
                                    var5_5 = -1;
                                } else {
                                    var4_4 = 1;
                                }
                                var6_6 = this.mSurfaceHolder.getRequestedHeight();
                                if (var6_6 <= 0) {
                                    var6_6 = -1;
                                    var7_7 = var4_4;
                                    var4_4 = var6_6;
                                } else {
                                    var7_7 = 1;
                                    var4_4 = var6_6;
                                }
                                var8_8 = this.mCreated ^ true;
                                var9_9 = this.mSurfaceCreated ^ true;
                                var10_10 = this.mFormat != this.mSurfaceHolder.getRequestedFormat() ? 1 : 0;
                                var6_6 = this.mWidth == var5_5 && this.mHeight == var4_4 ? 0 : 1;
                                var11_11 = this.mCreated ^ true;
                                var12_12 = this.mType != this.mSurfaceHolder.getRequestedType() ? 1 : 0;
                                var13_13 = this.mCurWindowFlags == this.mWindowFlags && this.mCurWindowPrivateFlags == this.mWindowPrivateFlags ? 0 : 1;
                                if (!(var1_1 || var8_8 || var9_9 || var10_10 != 0 || var6_6 != 0 || var12_12 != 0 || var13_13 != 0 || var3_3)) {
                                    if (this.mIWallpaperEngine.mShownReported != false) return;
                                }
                                this.mWidth = var5_5;
                                this.mHeight = var4_4;
                                this.mFormat = this.mSurfaceHolder.getRequestedFormat();
                                this.mType = this.mSurfaceHolder.getRequestedType();
                                this.mLayout.x = 0;
                                this.mLayout.y = 0;
                                if (var7_7 != 0) break block69;
                                try {
                                    this.mLayout.width = var5_5;
                                    this.mLayout.height = var4_4;
                                    ** GOTO lbl50
                                }
                                catch (RemoteException var14_14) {
                                    return;
                                }
                            }
                            var14_15 /* !! */  = new DisplayInfo();
                            this.mDisplay.getDisplayInfo((DisplayInfo)var14_15 /* !! */ );
                            var15_37 = Math.max((float)var14_15 /* !! */ .logicalHeight / (float)var4_4, (float)var14_15 /* !! */ .logicalWidth / (float)var5_5);
                            this.mLayout.height = (int)((float)var4_4 * var15_37);
                            this.mLayout.width = (int)((float)var5_5 * var15_37);
                            this.mWindowFlags |= 16384;
lbl50: // 2 sources:
                            this.mLayout.format = this.mFormat;
                            this.mCurWindowFlags = this.mWindowFlags;
                            this.mLayout.flags = this.mWindowFlags | 512 | 65536 | 256 | 8;
                            this.mCurWindowPrivateFlags = this.mWindowPrivateFlags;
                            this.mLayout.privateFlags = this.mWindowPrivateFlags;
                            this.mLayout.memoryType = this.mType;
                            this.mLayout.token = this.mWindowToken;
                            var1_1 = this.mCreated;
                            if (var1_1) break block70;
                            WallpaperService.this.obtainStyledAttributes(R.styleable.Window).recycle();
                            this.mLayout.type = this.mIWallpaperEngine.mWindowType;
                            this.mLayout.gravity = 8388659;
                            this.mLayout.setTitle(WallpaperService.this.getClass().getName());
                            this.mLayout.windowAnimations = 16974606;
                            var14_15 /* !! */  = new InputChannel();
                            this.mInputChannel = var14_15 /* !! */ ;
                            var16_38 = this.mSession;
                            var17_39 = this.mWindow;
                            var13_13 = this.mWindow.mSeq;
                            var18_40 /* !! */  = this.mLayout;
                            var12_12 = this.mDisplay.getDisplayId();
                            var19_41 = this.mWinFrame;
                            var14_15 /* !! */  = this.mContentInsets;
                            var20_42 = this.mStableInsets;
                            var21_43 = this.mOutsets;
                            var22_44 /* !! */  = this.mDisplayCutout;
                            var23_45 = this.mInputChannel;
                            try {
                                if (var16_38.addToDisplay((IWindow)var17_39, var13_13, var18_40 /* !! */ , 0, var12_12, var19_41, (Rect)var14_15 /* !! */ , var20_42, var21_43, (DisplayCutout.ParcelableWrapper)var22_44 /* !! */ , (InputChannel)var23_45, this.mInsetsState) < 0) {
                                    Log.w("WallpaperService", "Failed to add window while updating wallpaper surface.");
                                    return;
                                }
                                this.mCreated = true;
                                var14_15 /* !! */  = new WallpaperInputEventReceiver(this.mInputChannel, Looper.myLooper());
                                this.mInputEventReceiver = var14_15 /* !! */ ;
                            }
                            catch (RemoteException var14_23) {
                                return;
                            }
                            catch (RemoteException var14_16) {
                                return;
                            }
                            catch (RemoteException var14_17) {
                                return;
                            }
                            catch (RemoteException var14_18) {
                                return;
                            }
                            catch (RemoteException var14_19) {
                                return;
                            }
                            catch (RemoteException var14_20) {
                                return;
                            }
                            catch (RemoteException var14_21) {
                                return;
                            }
                            catch (RemoteException var14_22) {
                                return;
                            }
                        }
                        this.mSurfaceHolder.mSurfaceLock.lock();
                        this.mDrawingAllowed = true;
                        if (var7_7 != 0) break block71;
                        this.mLayout.surfaceInsets.set(this.mIWallpaperEngine.mDisplayPadding);
                        var14_15 /* !! */  = this.mLayout.surfaceInsets;
                        var14_15 /* !! */ .left += this.mOutsets.left;
                        var14_15 /* !! */  = this.mLayout.surfaceInsets;
                        var14_15 /* !! */ .top += this.mOutsets.top;
                        var14_15 /* !! */  = this.mLayout.surfaceInsets;
                        var14_15 /* !! */ .right += this.mOutsets.right;
                        var14_15 /* !! */  = this.mLayout.surfaceInsets;
                        var14_15 /* !! */ .bottom += this.mOutsets.bottom;
                        ** GOTO lbl128
                    }
                    this.mLayout.surfaceInsets.set(0, 0, 0, 0);
lbl128: // 2 sources:
                    var23_45 = this.mSession;
                    var17_39 = this.mWindow;
                    var24_46 = this.mWindow.mSeq;
                    var14_15 /* !! */  = this.mLayout;
                    var12_12 = this.mWidth;
                    var25_47 = this.mHeight;
                    var22_44 /* !! */  = this.mWinFrame;
                    var20_42 = this.mOverscanInsets;
                    var18_40 /* !! */  = this.mContentInsets;
                    var19_41 = this.mVisibleInsets;
                    var26_48 = this.mStableInsets;
                    var21_43 = this.mOutsets;
                    var27_49 = this.mBackdropFrame;
                    var16_38 = this.mDisplayCutout;
                    var13_13 = var6_6;
                    var25_47 = var23_45.relayout((IWindow)var17_39, var24_46, (WindowManager.LayoutParams)var14_15 /* !! */ , var12_12, var25_47, 0, 0, -1L, var22_44 /* !! */ , var20_42, (Rect)var18_40 /* !! */ , var19_41, var26_48, var21_43, var27_49, (DisplayCutout.ParcelableWrapper)var16_38, this.mMergedConfiguration, this.mSurfaceControl, this.mInsetsState);
                    var13_13 = var6_6;
                    if (this.mSurfaceControl.isValid()) {
                        var13_13 = var6_6;
                        this.mSurfaceHolder.mSurface.copyFrom(this.mSurfaceControl);
                        var13_13 = var6_6;
                        this.mSurfaceControl.release();
                    }
                    var13_13 = var6_6;
                    var24_46 = this.mWinFrame.width();
                    var13_13 = var6_6;
                    var12_12 = this.mWinFrame.height();
                    if (var7_7 == 0) {
                        var13_13 = var6_6;
                        var14_15 /* !! */  = this.mIWallpaperEngine.mDisplayPadding;
                        var13_13 = var6_6;
                        var4_4 = var14_15 /* !! */ .left;
                        var13_13 = var6_6;
                        var5_5 = var14_15 /* !! */ .right;
                        var13_13 = var6_6;
                        var7_7 = this.mOutsets.left;
                        var13_13 = var6_6;
                        var28_50 = this.mOutsets.right;
                        var13_13 = var6_6;
                        var29_51 = var14_15 /* !! */ .top;
                        var13_13 = var6_6;
                        var30_52 = var14_15 /* !! */ .bottom;
                        var13_13 = var6_6;
                        var31_53 = this.mOutsets.top;
                        var13_13 = var6_6;
                        var32_54 = this.mOutsets.bottom;
                        var13_13 = var6_6;
                        var17_39 = this.mOverscanInsets;
                        var13_13 = var6_6;
                        var17_39.left += var14_15 /* !! */ .left;
                        var13_13 = var6_6;
                        var17_39 = this.mOverscanInsets;
                        var13_13 = var6_6;
                        var17_39.top += var14_15 /* !! */ .top;
                        var13_13 = var6_6;
                        var17_39 = this.mOverscanInsets;
                        var13_13 = var6_6;
                        var17_39.right += var14_15 /* !! */ .right;
                        var13_13 = var6_6;
                        var17_39 = this.mOverscanInsets;
                        var13_13 = var6_6;
                        var17_39.bottom += var14_15 /* !! */ .bottom;
                        var13_13 = var6_6;
                        var17_39 = this.mContentInsets;
                        var13_13 = var6_6;
                        var17_39.left += var14_15 /* !! */ .left;
                        var13_13 = var6_6;
                        var17_39 = this.mContentInsets;
                        var13_13 = var6_6;
                        var17_39.top += var14_15 /* !! */ .top;
                        var13_13 = var6_6;
                        var17_39 = this.mContentInsets;
                        var13_13 = var6_6;
                        var17_39.right += var14_15 /* !! */ .right;
                        var13_13 = var6_6;
                        var17_39 = this.mContentInsets;
                        var13_13 = var6_6;
                        var17_39.bottom += var14_15 /* !! */ .bottom;
                        var13_13 = var6_6;
                        var17_39 = this.mStableInsets;
                        var13_13 = var6_6;
                        var17_39.left += var14_15 /* !! */ .left;
                        var13_13 = var6_6;
                        var17_39 = this.mStableInsets;
                        var13_13 = var6_6;
                        var17_39.top += var14_15 /* !! */ .top;
                        var13_13 = var6_6;
                        var17_39 = this.mStableInsets;
                        var13_13 = var6_6;
                        var17_39.right += var14_15 /* !! */ .right;
                        var13_13 = var6_6;
                        var17_39 = this.mStableInsets;
                        var13_13 = var6_6;
                        var17_39.bottom += var14_15 /* !! */ .bottom;
                        var13_13 = var6_6;
                        this.mDisplayCutout.set(this.mDisplayCutout.get().inset(-var14_15 /* !! */ .left, -var14_15 /* !! */ .top, -var14_15 /* !! */ .right, -var14_15 /* !! */ .bottom));
                        var12_12 += var29_51 + var30_52 + var31_53 + var32_54;
                        var7_7 = var24_46 + (var4_4 + var5_5 + var7_7 + var28_50);
                    } else {
                        var12_12 = var4_4;
                        var7_7 = var5_5;
                    }
                    var13_13 = var6_6;
                    var4_4 = this.mCurWidth;
                    var5_5 = var6_6;
                    if (var4_4 == var7_7) break block72;
                    try {
                        this.mCurWidth = var7_7;
                        var5_5 = 1;
                    }
                    catch (RemoteException var14_24) {
                        return;
                    }
                }
                var13_13 = var5_5;
                var4_4 = this.mCurHeight;
                if (var4_4 == var12_12) break block73;
                {
                    catch (RemoteException var14_32) {
                        return;
                    }
                }
                try {
                    this.mCurHeight = var12_12;
                    var4_4 = 1;
                }
                catch (RemoteException var14_25) {
                    return;
                }
            }
            var4_4 = var5_5;
            var1_1 = this.mDispatchedOverscanInsets.equals(this.mOverscanInsets);
            var5_5 = var1_1 == false ? 1 : 0;
            var5_5 = var6_6 = var11_11 | var5_5;
            var5_5 = this.mDispatchedContentInsets.equals(this.mContentInsets) == false ? 1 : 0;
            var6_6 |= var5_5;
            var5_5 = var6_6;
            var5_5 = this.mDispatchedStableInsets.equals(this.mStableInsets) == false ? 1 : 0;
            var6_6 |= var5_5;
            var5_5 = var6_6;
            var5_5 = this.mDispatchedOutsets.equals(this.mOutsets) == false ? 1 : 0;
            var5_5 = var13_13 = var6_6 | var5_5;
            var1_1 = this.mDispatchedDisplayCutout.equals(this.mDisplayCutout.get());
            var6_6 = var1_1 == false ? 1 : 0;
            {
                catch (RemoteException var14_31) {
                    return;
                }
            }
            this.mSurfaceHolder.setSurfaceFrameSize(var7_7, var12_12);
            this.mSurfaceHolder.mSurfaceLock.unlock();
            if (!this.mSurfaceHolder.mSurface.isValid()) {
                this.reportSurfaceDestroyed();
                return;
            }
            var5_5 = 0;
            {
                catch (RemoteException var14_30) {
                    return;
                }
            }
            this.mSurfaceHolder.ungetCallbacks();
            if (!var9_9) ** GOTO lbl311
            this.mIsCreating = true;
            var12_12 = 1;
            this.onSurfaceCreated(this.mSurfaceHolder);
            var14_15 /* !! */  = this.mSurfaceHolder.getCallbacks();
            var5_5 = var12_12;
            if (var14_15 /* !! */  == null) ** GOTO lbl311
            var11_11 = var14_15 /* !! */ .length;
            var7_7 = 0;
            ** GOTO lbl305
            {
                block74 : {
                    block76 : {
                        block75 : {
                            catch (Throwable var14_27) {
                                var1_1 = var3_3;
                                break block74;
                            }
                            catch (RemoteException var14_33) {
                                return;
                            }
                            catch (RemoteException var14_34) {
                                return;
                            }
                            catch (RemoteException var14_35) {
                                return;
                            }
                            catch (RemoteException var14_36) {
                                // empty catch block
                            }
                            return;
lbl305: // 1 sources:
                            do {
                                var5_5 = var12_12;
                                if (var7_7 >= var11_11) break;
                                var14_15 /* !! */ [var7_7].surfaceCreated(this.mSurfaceHolder);
                                ++var7_7;
                            } while (true);
lbl311: // 3 sources:
                            var7_7 = !var8_8 && (var25_47 & 2) == 0 ? 0 : 1;
                            var1_1 = var3_3 | var7_7;
                            if (var2_2 || var8_8 || var9_9 || var10_10 != 0 || var4_4 != 0) {
                                var10_10 = 1;
                                this.onSurfaceChanged(this.mSurfaceHolder, this.mFormat, this.mCurWidth, this.mCurHeight);
                                var14_15 /* !! */  = this.mSurfaceHolder.getCallbacks();
                                var5_5 = var10_10;
                                if (var14_15 /* !! */  == null) break block75;
                                var7_7 = var14_15 /* !! */ .length;
                                var4_4 = 0;
                                do {
                                    var5_5 = var10_10;
                                    if (var4_4 >= var7_7) break;
                                    var14_15 /* !! */ [var4_4].surfaceChanged(this.mSurfaceHolder, this.mFormat, this.mCurWidth, this.mCurHeight);
                                    ++var4_4;
                                } while (true);
                            }
                        }
                        if ((var13_13 | var6_6) == 0) ** GOTO lbl348
                        try {
                            this.mDispatchedOverscanInsets.set(this.mOverscanInsets);
                            var14_15 /* !! */  = this.mDispatchedOverscanInsets;
                            var14_15 /* !! */ .left += this.mOutsets.left;
                            var14_15 /* !! */  = this.mDispatchedOverscanInsets;
                            var14_15 /* !! */ .top += this.mOutsets.top;
                            var14_15 /* !! */  = this.mDispatchedOverscanInsets;
                            var14_15 /* !! */ .right += this.mOutsets.right;
                            var14_15 /* !! */  = this.mDispatchedOverscanInsets;
                            var14_15 /* !! */ .bottom += this.mOutsets.bottom;
                            this.mDispatchedContentInsets.set(this.mContentInsets);
                            this.mDispatchedStableInsets.set(this.mStableInsets);
                            this.mDispatchedOutsets.set(this.mOutsets);
                            this.mDispatchedDisplayCutout = this.mDisplayCutout.get();
                            this.mFinalSystemInsets.set(this.mDispatchedOverscanInsets);
                            this.mFinalStableInsets.set(this.mDispatchedStableInsets);
                            var14_15 /* !! */  = new WindowInsets(this.mFinalSystemInsets, this.mFinalStableInsets, WallpaperService.this.getResources().getConfiguration().isScreenRound(), false, this.mDispatchedDisplayCutout);
                            this.onApplyWindowInsets((WindowInsets)var14_15 /* !! */ );
lbl348: // 2 sources:
                            if (var1_1) {
                                this.onSurfaceRedrawNeeded(this.mSurfaceHolder);
                                var14_15 /* !! */  = this.mSurfaceHolder.getCallbacks();
                                if (var14_15 /* !! */  != null) {
                                    for (Object var17_39 : var14_15 /* !! */ ) {
                                        if (!(var17_39 instanceof SurfaceHolder.Callback2)) continue;
                                        ((SurfaceHolder.Callback2)var17_39).surfaceRedrawNeeded(this.mSurfaceHolder);
                                    }
                                }
                            }
                            if (var5_5 == 0) break block76;
                        }
                        catch (Throwable var14_26) {}
                        if (this.mReportedVisible) break block76;
                        if (this.mIsCreating) {
                            this.onVisibilityChanged(true);
                        }
                        this.onVisibilityChanged(false);
                    }
                    ** try [egrp 27[TRYBLOCK] [83 : 2575->2658)] { 
lbl365: // 1 sources:
                    this.mIsCreating = false;
                    this.mSurfaceCreated = true;
                    if (var1_1) {
                        this.mSession.finishDrawing(this.mWindow);
                    }
                    this.mIWallpaperEngine.reportShown();
                    return;
                }
                this.mIsCreating = false;
                this.mSurfaceCreated = true;
                if (var1_1) {
                    this.mSession.finishDrawing(this.mWindow);
                }
                this.mIWallpaperEngine.reportShown();
                throw var14_28;
            }
lbl378: // 1 sources:
            catch (RemoteException var14_29) {
                return;
            }
        }

        final class WallpaperInputEventReceiver
        extends InputEventReceiver {
            public WallpaperInputEventReceiver(InputChannel inputChannel, Looper looper) {
                super(inputChannel, looper);
            }

            @Override
            public void onInputEvent(InputEvent inputEvent) {
                boolean bl;
                block3 : {
                    boolean bl2;
                    bl = bl2 = false;
                    try {
                        if (!(inputEvent instanceof MotionEvent)) break block3;
                        bl = bl2;
                    }
                    catch (Throwable throwable) {
                        this.finishInputEvent(inputEvent, false);
                        throw throwable;
                    }
                    if ((inputEvent.getSource() & 2) == 0) break block3;
                    MotionEvent motionEvent = MotionEvent.obtainNoHistory((MotionEvent)inputEvent);
                    Engine.this.dispatchPointer(motionEvent);
                    bl = true;
                }
                this.finishInputEvent(inputEvent, bl);
            }
        }

    }

    class IWallpaperEngineWrapper
    extends IWallpaperEngine.Stub
    implements HandlerCaller.Callback {
        private final HandlerCaller mCaller;
        final IWallpaperConnection mConnection;
        private final AtomicBoolean mDetached = new AtomicBoolean();
        final Display mDisplay;
        final int mDisplayId;
        final DisplayManager mDisplayManager;
        final Rect mDisplayPadding = new Rect();
        Engine mEngine;
        final boolean mIsPreview;
        int mReqHeight;
        int mReqWidth;
        boolean mShownReported;
        final IBinder mWindowToken;
        final int mWindowType;

        IWallpaperEngineWrapper(WallpaperService wallpaperService, IWallpaperConnection iWallpaperConnection, IBinder iBinder, int n, boolean bl, int n2, int n3, Rect rect, int n4) {
            this.mCaller = new HandlerCaller(wallpaperService, wallpaperService.getMainLooper(), this, true);
            this.mConnection = iWallpaperConnection;
            this.mWindowToken = iBinder;
            this.mWindowType = n;
            this.mIsPreview = bl;
            this.mReqWidth = n2;
            this.mReqHeight = n3;
            this.mDisplayPadding.set(rect);
            this.mDisplayId = n4;
            this.mDisplayManager = ((Context)WallpaperService.this).getSystemService(DisplayManager.class);
            this.mDisplay = this.mDisplayManager.getDisplay(this.mDisplayId);
            if (this.mDisplay != null) {
                WallpaperService.this = this.mCaller.obtainMessage(10);
                this.mCaller.sendMessage((Message)WallpaperService.this);
                return;
            }
            WallpaperService.this = new StringBuilder();
            ((StringBuilder)WallpaperService.this).append("Cannot find display with id");
            ((StringBuilder)WallpaperService.this).append(this.mDisplayId);
            throw new IllegalArgumentException(((StringBuilder)WallpaperService.this).toString());
        }

        private void doDetachEngine() {
            WallpaperService.this.mActiveEngines.remove(this.mEngine);
            this.mEngine.detach();
        }

        @Override
        public void destroy() {
            Message message = this.mCaller.obtainMessage(20);
            this.mCaller.sendMessage(message);
        }

        public void detach() {
            this.mDetached.set(true);
        }

        @Override
        public void dispatchPointer(MotionEvent motionEvent) {
            Engine engine = this.mEngine;
            if (engine != null) {
                engine.dispatchPointer(motionEvent);
            } else {
                motionEvent.recycle();
            }
        }

        @Override
        public void dispatchWallpaperCommand(String string2, int n, int n2, int n3, Bundle bundle) {
            Engine engine = this.mEngine;
            if (engine != null) {
                engine.mWindow.dispatchWallpaperCommand(string2, n, n2, n3, bundle, false);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void executeMessage(Message object) {
            if (this.mDetached.get()) {
                if (!WallpaperService.this.mActiveEngines.contains(this.mEngine)) return;
                this.doDetachEngine();
                return;
            }
            int n = ((Message)object).what;
            boolean bl = false;
            boolean bl2 = false;
            switch (n) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown message type ");
                    stringBuilder.append(((Message)object).what);
                    Log.w(WallpaperService.TAG, stringBuilder.toString());
                    return;
                }
                case 10050: {
                    object = this.mConnection;
                    if (object == null) {
                        return;
                    }
                    try {
                        object.onWallpaperColorsChanged(this.mEngine.onComputeColors(), this.mDisplayId);
                        return;
                    }
                    catch (RemoteException remoteException) {
                        return;
                    }
                }
                case 10040: {
                    n = 0;
                    int n2 = 0;
                    MotionEvent motionEvent = (MotionEvent)((Message)object).obj;
                    if (motionEvent.getAction() == 2) {
                        object = this.mEngine.mLock;
                        synchronized (object) {
                            if (this.mEngine.mPendingMove == motionEvent) {
                                this.mEngine.mPendingMove = null;
                                n = n2;
                            } else {
                                n = 1;
                            }
                        }
                    }
                    if (n == 0) {
                        this.mEngine.onTouchEvent(motionEvent);
                    }
                    motionEvent.recycle();
                    return;
                }
                case 10035: {
                    return;
                }
                case 10030: {
                    bl2 = ((Message)object).arg1 != 0;
                    this.mEngine.mOutsets.set((Rect)((Message)object).obj);
                    this.mEngine.updateSurface(true, false, bl2);
                    this.mEngine.doOffsetsChanged(true);
                    return;
                }
                case 10025: {
                    object = (WallpaperCommand)((Message)object).obj;
                    this.mEngine.doCommand((WallpaperCommand)object);
                    return;
                }
                case 10020: {
                    this.mEngine.doOffsetsChanged(true);
                    return;
                }
                case 10010: {
                    Engine engine = this.mEngine;
                    if (((Message)object).arg1 != 0) {
                        bl2 = true;
                    }
                    engine.doVisibilityChanged(bl2);
                    return;
                }
                case 10000: {
                    this.mEngine.updateSurface(true, false, false);
                    return;
                }
                case 50: {
                    Engine engine = this.mEngine;
                    bl2 = bl;
                    if (((Message)object).arg1 != 0) {
                        bl2 = true;
                    }
                    engine.doAmbientModeChanged(bl2, (Long)((Message)object).obj);
                    return;
                }
                case 40: {
                    this.mEngine.doDisplayPaddingChanged((Rect)((Message)object).obj);
                    return;
                }
                case 30: {
                    this.mEngine.doDesiredSizeChanged(((Message)object).arg1, ((Message)object).arg2);
                    return;
                }
                case 20: {
                    this.doDetachEngine();
                    return;
                }
                case 10: 
            }
            try {
                this.mConnection.attachEngine(this, this.mDisplayId);
                this.mEngine = object = WallpaperService.this.onCreateEngine();
                WallpaperService.this.mActiveEngines.add(object);
            }
            catch (RemoteException remoteException) {
                Log.w(WallpaperService.TAG, "Wallpaper host disappeared", remoteException);
                return;
            }
            ((Engine)object).attach(this);
        }

        public void reportShown() {
            if (!this.mShownReported) {
                this.mShownReported = true;
                try {
                    this.mConnection.engineShown(this);
                }
                catch (RemoteException remoteException) {
                    Log.w(WallpaperService.TAG, "Wallpaper host disappeared", remoteException);
                    return;
                }
            }
        }

        @Override
        public void requestWallpaperColors() {
            Message message = this.mCaller.obtainMessage(10050);
            this.mCaller.sendMessage(message);
        }

        @Override
        public void setDesiredSize(int n, int n2) {
            Message message = this.mCaller.obtainMessageII(30, n, n2);
            this.mCaller.sendMessage(message);
        }

        @Override
        public void setDisplayPadding(Rect parcelable) {
            parcelable = this.mCaller.obtainMessageO(40, parcelable);
            this.mCaller.sendMessage((Message)parcelable);
        }

        @Override
        public void setInAmbientMode(boolean bl, long l) throws RemoteException {
            Message message = this.mCaller.obtainMessageIO(50, (int)bl, l);
            this.mCaller.sendMessage(message);
        }

        @Override
        public void setVisibility(boolean bl) {
            Object object = this.mCaller;
            object = ((HandlerCaller)object).obtainMessageI(10010, (int)bl);
            this.mCaller.sendMessage((Message)object);
        }
    }

    class IWallpaperServiceWrapper
    extends IWallpaperService.Stub {
        private IWallpaperEngineWrapper mEngineWrapper;
        private final WallpaperService mTarget;

        public IWallpaperServiceWrapper(WallpaperService wallpaperService2) {
            this.mTarget = wallpaperService2;
        }

        @Override
        public void attach(IWallpaperConnection iWallpaperConnection, IBinder iBinder, int n, boolean bl, int n2, int n3, Rect rect, int n4) {
            this.mEngineWrapper = new IWallpaperEngineWrapper(this.mTarget, iWallpaperConnection, iBinder, n, bl, n2, n3, rect, n4);
        }

        @Override
        public void detach() {
            this.mEngineWrapper.detach();
        }
    }

    static final class WallpaperCommand {
        String action;
        Bundle extras;
        boolean sync;
        int x;
        int y;
        int z;

        WallpaperCommand() {
        }
    }

}

