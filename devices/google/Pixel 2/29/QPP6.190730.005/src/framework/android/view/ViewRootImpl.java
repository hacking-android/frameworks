/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.animation.LayoutTransition;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.ResourcesManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.FrameInfo;
import android.graphics.HardwareRenderer;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RenderNode;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.sysprop.DisplayProperties;
import android.util.AndroidRuntimeException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LongArray;
import android.util.MergedConfiguration;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.AccessibilityInteractionController;
import android.view.ActionMode;
import android.view.Choreographer;
import android.view.ContextMenu;
import android.view.Display;
import android.view.DisplayAdjustments;
import android.view.DisplayCutout;
import android.view.DragEvent;
import android.view.FallbackEventHandler;
import android.view.FocusFinder;
import android.view.GestureExclusionTracker;
import android.view.HandlerActionQueue;
import android.view.IWindow;
import android.view.IWindowSession;
import android.view.InputChannel;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.InputEventCompatProcessor;
import android.view.InputEventConsistencyVerifier;
import android.view.InputEventReceiver;
import android.view.InputQueue;
import android.view.InsetsController;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.MagnificationSpec;
import android.view.MotionEvent;
import android.view.NativeVectorDrawableAnimator;
import android.view.PointerIcon;
import android.view.SoundEffectConstants;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceSession;
import android.view.ThreadedRenderer;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowCallbacks;
import android.view.WindowInsets;
import android.view.WindowLeaked;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view._$$Lambda$ViewRootImpl$7A_3tkr_Kw4TZAeIUGVlOoTcZhg;
import android.view._$$Lambda$ViewRootImpl$IReiNMSbDakZSGbIZuL_ifaFWn8;
import android.view._$$Lambda$ViewRootImpl$YBiqAhbCbXVPSKdbE3K4rH2gpxI;
import android.view._$$Lambda$ViewRootImpl$_dgEKMWLAJVMlaVy41safRlNQBo;
import android.view._$$Lambda$ViewRootImpl$dznxCZGM2R1fsBljsJKomLjBRoM;
import android.view._$$Lambda$ViewRootImpl$zlBUjCwDtoAWMNaHI62DIq_eKFY;
import android.view._$$Lambda$dj1hfDQd0iEp_uBDBPEUMMYJJwk;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeIdManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.accessibility.AccessibilityRecord;
import android.view.accessibility.IAccessibilityInteractionConnection;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.autofill.AutofillManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Scroller;
import com.android.internal.R;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.os.IResultReceiver;
import com.android.internal.os.SomeArgs;
import com.android.internal.policy.PhoneFallbackEventHandler;
import com.android.internal.util.Preconditions;
import com.android.internal.view.BaseSurfaceHolder;
import com.android.internal.view.RootViewSurfaceTaker;
import com.android.internal.view.SurfaceCallbackHelper;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public final class ViewRootImpl
implements ViewParent,
View.AttachInfo.Callbacks,
ThreadedRenderer.DrawCallbacks {
    private static final boolean DBG = false;
    private static final boolean DEBUG_CONFIGURATION = false;
    private static final boolean DEBUG_CONTENT_CAPTURE = false;
    private static final boolean DEBUG_DIALOG = false;
    private static final boolean DEBUG_DRAW = false;
    private static final boolean DEBUG_FPS = false;
    private static final boolean DEBUG_IMF = false;
    private static final boolean DEBUG_INPUT_RESIZE = false;
    private static final boolean DEBUG_INPUT_STAGES = false;
    private static final boolean DEBUG_KEEP_SCREEN_ON = false;
    private static final boolean DEBUG_LAYOUT = false;
    private static final boolean DEBUG_ORIENTATION = false;
    private static final boolean DEBUG_TRACKBALL = false;
    private static final boolean LOCAL_LOGV = false;
    private static final int MAX_QUEUED_INPUT_EVENT_POOL_SIZE = 10;
    static final int MAX_TRACKBALL_DELAY = 250;
    private static final int MSG_CHECK_FOCUS = 13;
    private static final int MSG_CLEAR_ACCESSIBILITY_FOCUS_HOST = 21;
    private static final int MSG_CLOSE_SYSTEM_DIALOGS = 14;
    private static final int MSG_DIE = 3;
    private static final int MSG_DISPATCH_APP_VISIBILITY = 8;
    private static final int MSG_DISPATCH_DRAG_EVENT = 15;
    private static final int MSG_DISPATCH_DRAG_LOCATION_EVENT = 16;
    private static final int MSG_DISPATCH_GET_NEW_SURFACE = 9;
    private static final int MSG_DISPATCH_INPUT_EVENT = 7;
    private static final int MSG_DISPATCH_KEY_FROM_AUTOFILL = 12;
    private static final int MSG_DISPATCH_KEY_FROM_IME = 11;
    private static final int MSG_DISPATCH_SYSTEM_UI_VISIBILITY = 17;
    private static final int MSG_DISPATCH_WINDOW_SHOWN = 25;
    private static final int MSG_DRAW_FINISHED = 29;
    private static final int MSG_INSETS_CHANGED = 30;
    private static final int MSG_INSETS_CONTROL_CHANGED = 31;
    private static final int MSG_INVALIDATE = 1;
    private static final int MSG_INVALIDATE_RECT = 2;
    private static final int MSG_INVALIDATE_WORLD = 22;
    private static final int MSG_POINTER_CAPTURE_CHANGED = 28;
    private static final int MSG_PROCESS_INPUT_EVENTS = 19;
    private static final int MSG_REQUEST_KEYBOARD_SHORTCUTS = 26;
    private static final int MSG_RESIZED = 4;
    private static final int MSG_RESIZED_REPORT = 5;
    private static final int MSG_SYNTHESIZE_INPUT_EVENT = 24;
    private static final int MSG_SYSTEM_GESTURE_EXCLUSION_CHANGED = 32;
    private static final int MSG_UPDATE_CONFIGURATION = 18;
    private static final int MSG_UPDATE_POINTER_ICON = 27;
    private static final int MSG_WINDOW_FOCUS_CHANGED = 6;
    private static final int MSG_WINDOW_MOVED = 23;
    private static final boolean MT_RENDERER_AVAILABLE = true;
    public static final int NEW_INSETS_MODE_FULL = 2;
    public static final int NEW_INSETS_MODE_IME = 1;
    public static final int NEW_INSETS_MODE_NONE = 0;
    public static final String PROPERTY_EMULATOR_WIN_OUTSET_BOTTOM_PX = "ro.emu.win_outset_bottom_px";
    private static final String PROPERTY_PROFILE_RENDERING = "viewroot.profile_rendering";
    private static final String TAG = "ViewRootImpl";
    private static final String USE_NEW_INSETS_PROPERTY = "persist.wm.new_insets";
    static final Interpolator mResizeInterpolator;
    private static boolean sAlwaysAssignFocus;
    private static boolean sCompatibilityDone;
    private static final ArrayList<ConfigChangedCallback> sConfigCallbacks;
    static boolean sFirstDrawComplete;
    static final ArrayList<Runnable> sFirstDrawHandlers;
    public static int sNewInsetsMode;
    @UnsupportedAppUsage
    static final ThreadLocal<HandlerActionQueue> sRunQueues;
    View mAccessibilityFocusedHost;
    AccessibilityNodeInfo mAccessibilityFocusedVirtualView;
    final AccessibilityInteractionConnectionManager mAccessibilityInteractionConnectionManager;
    AccessibilityInteractionController mAccessibilityInteractionController;
    final AccessibilityManager mAccessibilityManager;
    private ActivityConfigCallback mActivityConfigCallback;
    private boolean mActivityRelaunched;
    @UnsupportedAppUsage
    boolean mAdded;
    boolean mAddedTouchMode;
    private boolean mAppVisibilityChanged;
    boolean mAppVisible;
    boolean mApplyInsetsRequested;
    @UnsupportedAppUsage
    final View.AttachInfo mAttachInfo;
    AudioManager mAudioManager;
    final String mBasePackageName;
    public final Surface mBoundsSurface;
    private SurfaceControl mBoundsSurfaceControl;
    private int mCanvasOffsetX;
    private int mCanvasOffsetY;
    Choreographer mChoreographer;
    int mClientWindowLayoutFlags;
    final ConsumeBatchedInputImmediatelyRunnable mConsumeBatchedInputImmediatelyRunnable;
    boolean mConsumeBatchedInputImmediatelyScheduled;
    boolean mConsumeBatchedInputScheduled;
    final ConsumeBatchedInputRunnable mConsumedBatchedInputRunnable;
    @UnsupportedAppUsage
    public final Context mContext;
    int mCurScrollY;
    View mCurrentDragView;
    private PointerIcon mCustomPointerIcon;
    private final int mDensity;
    @UnsupportedAppUsage
    Rect mDirty;
    final Rect mDispatchContentInsets;
    DisplayCutout mDispatchDisplayCutout;
    final Rect mDispatchStableInsets;
    Display mDisplay;
    private final DisplayManager.DisplayListener mDisplayListener;
    final DisplayManager mDisplayManager;
    ClipDescription mDragDescription;
    final PointF mDragPoint;
    private boolean mDragResizing;
    boolean mDrawingAllowed;
    int mDrawsNeededToReport;
    @UnsupportedAppUsage
    FallbackEventHandler mFallbackEventHandler;
    boolean mFirst;
    InputStage mFirstInputStage;
    InputStage mFirstPostImeInputStage;
    private boolean mForceDecorViewVisibility;
    private boolean mForceNextConfigUpdate;
    boolean mForceNextWindowRelayout;
    private int mFpsNumFrames;
    private long mFpsPrevTime;
    private long mFpsStartTime;
    boolean mFullRedrawNeeded;
    private final GestureExclusionTracker mGestureExclusionTracker;
    boolean mHadWindowFocus;
    final ViewRootHandler mHandler;
    boolean mHandlingLayoutInLayoutRequest;
    int mHardwareXOffset;
    int mHardwareYOffset;
    boolean mHasHadWindowFocus;
    @UnsupportedAppUsage
    int mHeight;
    final HighContrastTextManager mHighContrastTextManager;
    private boolean mInLayout;
    InputChannel mInputChannel;
    private final InputEventCompatProcessor mInputCompatProcessor;
    protected final InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    WindowInputEventReceiver mInputEventReceiver;
    InputQueue mInputQueue;
    InputQueue.Callback mInputQueueCallback;
    private final InsetsController mInsetsController;
    final InvalidateOnAnimationRunnable mInvalidateOnAnimationRunnable;
    private boolean mInvalidateRootRequested;
    boolean mIsAmbientMode;
    public boolean mIsAnimating;
    boolean mIsCreating;
    boolean mIsDrawing;
    boolean mIsInTraversal;
    private final Configuration mLastConfigurationFromResources;
    final ViewTreeObserver.InternalInsetsInfo mLastGivenInsets;
    boolean mLastInCompatMode;
    boolean mLastOverscanRequested;
    private final MergedConfiguration mLastReportedMergedConfiguration;
    @UnsupportedAppUsage
    WeakReference<View> mLastScrolledFocus;
    int mLastSystemUiVisibility;
    final PointF mLastTouchPoint;
    int mLastTouchSource;
    boolean mLastWasImTarget;
    private WindowInsets mLastWindowInsets;
    boolean mLayoutRequested;
    ArrayList<View> mLayoutRequesters;
    volatile Object mLocalDragState;
    final WindowLeaked mLocation;
    boolean mLostWindowFocus;
    private boolean mNeedsRendererSetup;
    boolean mNewSurfaceNeeded;
    private final int mNoncompatDensity;
    int mOrigWindowType;
    boolean mPausedForTransition;
    boolean mPendingAlwaysConsumeSystemBars;
    final Rect mPendingBackDropFrame;
    final Rect mPendingContentInsets;
    final DisplayCutout.ParcelableWrapper mPendingDisplayCutout;
    int mPendingInputEventCount;
    QueuedInputEvent mPendingInputEventHead;
    String mPendingInputEventQueueLengthCounterName;
    QueuedInputEvent mPendingInputEventTail;
    private final MergedConfiguration mPendingMergedConfiguration;
    final Rect mPendingOutsets;
    final Rect mPendingOverscanInsets;
    final Rect mPendingStableInsets;
    private ArrayList<LayoutTransition> mPendingTransitions;
    final Rect mPendingVisibleInsets;
    boolean mPointerCapture;
    private int mPointerIconType;
    final Region mPreviousTransparentRegion;
    boolean mProcessInputEventsScheduled;
    private boolean mProfile;
    private boolean mProfileRendering;
    private QueuedInputEvent mQueuedInputEventPool;
    private int mQueuedInputEventPoolSize;
    private boolean mRemoved;
    private Choreographer.FrameCallback mRenderProfiler;
    private boolean mRenderProfilingEnabled;
    boolean mReportNextDraw;
    private int mResizeMode;
    boolean mScrollMayChange;
    int mScrollY;
    Scroller mScroller;
    SendWindowContentChangedAccessibilityEvent mSendWindowContentChangedAccessibilityEvent;
    int mSeq;
    int mSoftInputMode;
    @UnsupportedAppUsage
    boolean mStopped;
    @UnsupportedAppUsage
    public final Surface mSurface;
    private final SurfaceControl mSurfaceControl;
    BaseSurfaceHolder mSurfaceHolder;
    SurfaceHolder.Callback2 mSurfaceHolderCallback;
    private SurfaceSession mSurfaceSession;
    InputStage mSyntheticInputStage;
    private String mTag;
    final int mTargetSdkVersion;
    private final Rect mTempBoundsRect;
    HashSet<View> mTempHashSet;
    private InsetsState mTempInsets;
    final Rect mTempRect;
    final Thread mThread;
    final Rect mTmpFrame;
    final int[] mTmpLocation;
    final TypedValue mTmpValue;
    private final SurfaceControl.Transaction mTransaction;
    CompatibilityInfo.Translator mTranslator;
    final Region mTransparentRegion;
    int mTraversalBarrier;
    final TraversalRunnable mTraversalRunnable;
    public boolean mTraversalScheduled;
    boolean mUnbufferedInputDispatch;
    private final UnhandledKeyManager mUnhandledKeyManager;
    @GuardedBy(value={"this"})
    boolean mUpcomingInTouchMode;
    @GuardedBy(value={"this"})
    boolean mUpcomingWindowFocus;
    private boolean mUseMTRenderer;
    @UnsupportedAppUsage
    View mView;
    final ViewConfiguration mViewConfiguration;
    private int mViewLayoutDirectionInitial;
    int mViewVisibility;
    final Rect mVisRect;
    @UnsupportedAppUsage
    int mWidth;
    boolean mWillDrawSoon;
    final Rect mWinFrame;
    final W mWindow;
    public final WindowManager.LayoutParams mWindowAttributes;
    boolean mWindowAttributesChanged;
    int mWindowAttributesChangesFlag;
    @GuardedBy(value={"mWindowCallbacks"})
    final ArrayList<WindowCallbacks> mWindowCallbacks;
    CountDownLatch mWindowDrawCountDown;
    @GuardedBy(value={"this"})
    boolean mWindowFocusChanged;
    @UnsupportedAppUsage
    final IWindowSession mWindowSession;
    private final ArrayList<WindowStoppedCallback> mWindowStoppedCallbacks;

    static {
        sNewInsetsMode = SystemProperties.getInt(USE_NEW_INSETS_PROPERTY, 0);
        sRunQueues = new ThreadLocal();
        sFirstDrawHandlers = new ArrayList();
        sFirstDrawComplete = false;
        sConfigCallbacks = new ArrayList();
        sCompatibilityDone = false;
        mResizeInterpolator = new AccelerateDecelerateInterpolator();
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public ViewRootImpl(Context object, Display object2) {
        Throwable throwable2222;
        block8 : {
            this.mWindowCallbacks = new ArrayList();
            this.mTmpLocation = new int[2];
            this.mTmpValue = new TypedValue();
            this.mWindowAttributes = new WindowManager.LayoutParams();
            this.mAppVisible = true;
            boolean bl = false;
            this.mForceDecorViewVisibility = false;
            this.mOrigWindowType = -1;
            this.mStopped = false;
            this.mIsAmbientMode = false;
            this.mPausedForTransition = false;
            this.mLastInCompatMode = false;
            this.mTempBoundsRect = new Rect();
            this.mPendingInputEventQueueLengthCounterName = "pq";
            this.mUnhandledKeyManager = new UnhandledKeyManager();
            this.mWindowAttributesChanged = false;
            this.mWindowAttributesChangesFlag = 0;
            this.mSurface = new Surface();
            this.mSurfaceControl = new SurfaceControl();
            this.mBoundsSurface = new Surface();
            this.mTransaction = new SurfaceControl.Transaction();
            this.mTmpFrame = new Rect();
            this.mPendingOverscanInsets = new Rect();
            this.mPendingVisibleInsets = new Rect();
            this.mPendingStableInsets = new Rect();
            this.mPendingContentInsets = new Rect();
            this.mPendingOutsets = new Rect();
            this.mPendingBackDropFrame = new Rect();
            this.mPendingDisplayCutout = new DisplayCutout.ParcelableWrapper(DisplayCutout.NO_CUTOUT);
            this.mTempInsets = new InsetsState();
            this.mLastGivenInsets = new ViewTreeObserver.InternalInsetsInfo();
            this.mDispatchContentInsets = new Rect();
            this.mDispatchStableInsets = new Rect();
            this.mDispatchDisplayCutout = DisplayCutout.NO_CUTOUT;
            this.mLastConfigurationFromResources = new Configuration();
            this.mLastReportedMergedConfiguration = new MergedConfiguration();
            this.mPendingMergedConfiguration = new MergedConfiguration();
            this.mDragPoint = new PointF();
            this.mLastTouchPoint = new PointF();
            this.mFpsStartTime = -1L;
            this.mFpsPrevTime = -1L;
            this.mPointerIconType = 1;
            this.mCustomPointerIcon = null;
            this.mAccessibilityInteractionConnectionManager = new AccessibilityInteractionConnectionManager();
            this.mInLayout = false;
            this.mLayoutRequesters = new ArrayList();
            this.mHandlingLayoutInLayoutRequest = false;
            InputEventConsistencyVerifier inputEventConsistencyVerifier = InputEventConsistencyVerifier.isInstrumentationEnabled() ? new InputEventConsistencyVerifier(this, 0) : null;
            this.mInputEventConsistencyVerifier = inputEventConsistencyVerifier;
            this.mInsetsController = new InsetsController(this);
            this.mGestureExclusionTracker = new GestureExclusionTracker();
            this.mTag = TAG;
            this.mProfile = false;
            this.mDisplayListener = new DisplayManager.DisplayListener(){

                private int toViewScreenState(int n) {
                    int n2 = 1;
                    n = n == 1 ? 0 : n2;
                    return n;
                }

                @Override
                public void onDisplayAdded(int n) {
                }

                @Override
                public void onDisplayChanged(int n) {
                    int n2;
                    int n3;
                    if (ViewRootImpl.this.mView != null && ViewRootImpl.this.mDisplay.getDisplayId() == n && (n2 = ViewRootImpl.this.mAttachInfo.mDisplayState) != (n3 = ViewRootImpl.this.mDisplay.getState())) {
                        ViewRootImpl.this.mAttachInfo.mDisplayState = n3;
                        ViewRootImpl.this.pokeDrawLockIfNeeded();
                        if (n2 != 0) {
                            n = this.toViewScreenState(n2);
                            if (n != (n3 = this.toViewScreenState(n3))) {
                                ViewRootImpl.this.mView.dispatchScreenStateChanged(n3);
                            }
                            if (n2 == 1) {
                                ViewRootImpl viewRootImpl = ViewRootImpl.this;
                                viewRootImpl.mFullRedrawNeeded = true;
                                viewRootImpl.scheduleTraversals();
                            }
                        }
                    }
                }

                @Override
                public void onDisplayRemoved(int n) {
                }
            };
            this.mWindowStoppedCallbacks = new ArrayList();
            this.mDrawsNeededToReport = 0;
            this.mHandler = new ViewRootHandler();
            this.mTraversalRunnable = new TraversalRunnable();
            this.mConsumedBatchedInputRunnable = new ConsumeBatchedInputRunnable();
            this.mConsumeBatchedInputImmediatelyRunnable = new ConsumeBatchedInputImmediatelyRunnable();
            this.mInvalidateOnAnimationRunnable = new InvalidateOnAnimationRunnable();
            this.mContext = object;
            this.mWindowSession = WindowManagerGlobal.getWindowSession();
            this.mDisplay = object2;
            this.mBasePackageName = ((Context)object).getBasePackageName();
            this.mThread = Thread.currentThread();
            this.mLocation = new WindowLeaked(null);
            this.mLocation.fillInStackTrace();
            this.mWidth = -1;
            this.mHeight = -1;
            this.mDirty = new Rect();
            this.mTempRect = new Rect();
            this.mVisRect = new Rect();
            this.mWinFrame = new Rect();
            this.mWindow = new W(this);
            this.mTargetSdkVersion = object.getApplicationInfo().targetSdkVersion;
            this.mViewVisibility = 8;
            this.mTransparentRegion = new Region();
            this.mPreviousTransparentRegion = new Region();
            this.mFirst = true;
            this.mAdded = false;
            this.mAttachInfo = new View.AttachInfo(this.mWindowSession, this.mWindow, (Display)object2, this, this.mHandler, this, (Context)object);
            this.mAccessibilityManager = AccessibilityManager.getInstance((Context)object);
            this.mAccessibilityManager.addAccessibilityStateChangeListener(this.mAccessibilityInteractionConnectionManager, this.mHandler);
            this.mHighContrastTextManager = new HighContrastTextManager();
            this.mAccessibilityManager.addHighTextContrastStateChangeListener(this.mHighContrastTextManager, this.mHandler);
            this.mViewConfiguration = ViewConfiguration.get((Context)object);
            this.mDensity = object.getResources().getDisplayMetrics().densityDpi;
            this.mNoncompatDensity = object.getResources().getDisplayMetrics().noncompatDensityDpi;
            this.mFallbackEventHandler = new PhoneFallbackEventHandler((Context)object);
            this.mChoreographer = Choreographer.getInstance();
            this.mDisplayManager = (DisplayManager)((Context)object).getSystemService("display");
            object2 = ((Context)object).getResources().getString(17039745);
            if (((String)object2).isEmpty()) {
                this.mInputCompatProcessor = new InputEventCompatProcessor((Context)object);
            } else {
                this.mInputCompatProcessor = object = (InputEventCompatProcessor)Class.forName((String)object2).getConstructor(Context.class).newInstance(object);
                {
                    catch (Throwable throwable2222) {
                        break block8;
                    }
                    catch (Exception exception) {}
                    {
                        Log.e(TAG, "Unable to create the InputEventCompatProcessor. ", exception);
                        this.mInputCompatProcessor = null;
                    }
                }
            }
            if (!sCompatibilityDone) {
                if (this.mTargetSdkVersion < 28) {
                    bl = true;
                }
                sAlwaysAssignFocus = bl;
                sCompatibilityDone = true;
            }
            this.loadSystemProperties();
            return;
        }
        this.mInputCompatProcessor = null;
        throw throwable2222;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static void addConfigCallback(ConfigChangedCallback configChangedCallback) {
        ArrayList<ConfigChangedCallback> arrayList = sConfigCallbacks;
        synchronized (arrayList) {
            sConfigCallbacks.add(configChangedCallback);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void addFirstDrawHandler(Runnable runnable) {
        ArrayList<Runnable> arrayList = sFirstDrawHandlers;
        synchronized (arrayList) {
            if (!sFirstDrawComplete) {
                sFirstDrawHandlers.add(runnable);
            }
            return;
        }
    }

    private void applyKeepScreenOnFlag(WindowManager.LayoutParams layoutParams) {
        layoutParams.flags = this.mAttachInfo.mKeepScreenOn ? (layoutParams.flags |= 128) : layoutParams.flags & -129 | this.mClientWindowLayoutFlags & 128;
    }

    private boolean checkForLeavingTouchModeAndConsume(KeyEvent keyEvent) {
        if (!this.mAttachInfo.mInTouchMode) {
            return false;
        }
        int n = keyEvent.getAction();
        if (n != 0 && n != 2) {
            return false;
        }
        if ((keyEvent.getFlags() & 4) != 0) {
            return false;
        }
        if (ViewRootImpl.isNavigationKey(keyEvent)) {
            return this.ensureTouchMode(false);
        }
        if (ViewRootImpl.isTypingKey(keyEvent)) {
            this.ensureTouchMode(false);
            return false;
        }
        return false;
    }

    private boolean collectViewAttributes() {
        if (this.mAttachInfo.mRecomputeGlobalAttributes) {
            Object object = this.mAttachInfo;
            ((View.AttachInfo)object).mRecomputeGlobalAttributes = false;
            boolean bl = ((View.AttachInfo)object).mKeepScreenOn;
            object = this.mAttachInfo;
            ((View.AttachInfo)object).mKeepScreenOn = false;
            ((View.AttachInfo)object).mSystemUiVisibility = 0;
            ((View.AttachInfo)object).mHasSystemUiListeners = false;
            this.mView.dispatchCollectViewAttributes((View.AttachInfo)object, 0);
            object = this.mAttachInfo;
            ((View.AttachInfo)object).mSystemUiVisibility &= this.mAttachInfo.mDisabledSystemUiVisibility;
            object = this.mWindowAttributes;
            View.AttachInfo attachInfo = this.mAttachInfo;
            attachInfo.mSystemUiVisibility |= this.getImpliedSystemUiVisibility((WindowManager.LayoutParams)object);
            if (this.mAttachInfo.mKeepScreenOn != bl || this.mAttachInfo.mSystemUiVisibility != ((WindowManager.LayoutParams)object).subtreeSystemUiVisibility || this.mAttachInfo.mHasSystemUiListeners != ((WindowManager.LayoutParams)object).hasSystemUiListeners) {
                this.applyKeepScreenOnFlag((WindowManager.LayoutParams)object);
                ((WindowManager.LayoutParams)object).subtreeSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                ((WindowManager.LayoutParams)object).hasSystemUiListeners = this.mAttachInfo.mHasSystemUiListeners;
                this.mView.dispatchWindowSystemUiVisiblityChanged(this.mAttachInfo.mSystemUiVisibility);
                return true;
            }
        }
        return false;
    }

    private void deliverInputEvent(QueuedInputEvent queuedInputEvent) {
        Trace.asyncTraceBegin(8L, "deliverInputEvent", queuedInputEvent.mEvent.getSequenceNumber());
        Object object = this.mInputEventConsistencyVerifier;
        if (object != null) {
            ((InputEventConsistencyVerifier)object).onInputEvent(queuedInputEvent.mEvent, 0);
        }
        object = queuedInputEvent.shouldSendToSynthesizer() ? this.mSyntheticInputStage : (queuedInputEvent.shouldSkipIme() ? this.mFirstPostImeInputStage : this.mFirstInputStage);
        if (queuedInputEvent.mEvent instanceof KeyEvent) {
            this.mUnhandledKeyManager.preDispatch((KeyEvent)queuedInputEvent.mEvent);
        }
        if (object != null) {
            this.handleWindowFocusChanged();
            ((InputStage)object).deliver(queuedInputEvent);
        } else {
            this.finishInputEvent(queuedInputEvent);
        }
    }

    private void destroyHardwareRenderer() {
        Object object = this.mAttachInfo.mThreadedRenderer;
        if (object != null) {
            View view = this.mView;
            if (view != null) {
                ((ThreadedRenderer)object).destroyHardwareResources(view);
            }
            ((ThreadedRenderer)object).destroy();
            ((ThreadedRenderer)object).setRequested(false);
            object = this.mAttachInfo;
            ((View.AttachInfo)object).mThreadedRenderer = null;
            ((View.AttachInfo)object).mHardwareAccelerated = false;
        }
    }

    private void destroySurface() {
        this.mSurface.release();
        this.mSurfaceControl.release();
        this.mSurfaceSession = null;
        SurfaceControl surfaceControl = this.mBoundsSurfaceControl;
        if (surfaceControl != null) {
            surfaceControl.remove();
            this.mBoundsSurface.release();
            this.mBoundsSurfaceControl = null;
        }
    }

    private int dipToPx(int n) {
        return (int)(this.mContext.getResources().getDisplayMetrics().density * (float)n + 0.5f);
    }

    private void dispatchInsetsChanged(InsetsState insetsState) {
        this.mHandler.obtainMessage(30, insetsState).sendToTarget();
    }

    private void dispatchInsetsControlChanged(InsetsState insetsState, InsetsSourceControl[] arrinsetsSourceControl) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = insetsState;
        someArgs.arg2 = arrinsetsSourceControl;
        this.mHandler.obtainMessage(31, someArgs).sendToTarget();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private void dispatchResized(Rect parcelable, Rect rect, Rect rect2, Rect rect3, Rect rect4, Rect rect5, boolean bl, MergedConfiguration mergedConfiguration, Rect rect6, boolean bl2, boolean bl3, int n, DisplayCutout.ParcelableWrapper parcelableWrapper) {
        int n2;
        Object object;
        boolean bl4 = this.mDragResizing;
        int n3 = 1;
        if (bl4 && this.mUseMTRenderer) {
            bl4 = parcelable.equals(rect6);
            object = this.mWindowCallbacks;
            synchronized (object) {
                for (n2 = this.mWindowCallbacks.size() - 1; n2 >= 0; --n2) {
                    this.mWindowCallbacks.get(n2).onWindowSizeIsChanging(rect6, bl4, rect3, rect4);
                }
            }
        }
        object = this.mHandler;
        n2 = bl ? 5 : 4;
        object = ((Handler)object).obtainMessage(n2);
        Object object2 = this.mTranslator;
        if (object2 != null) {
            ((CompatibilityInfo.Translator)object2).translateRectInScreenToAppWindow((Rect)parcelable);
            this.mTranslator.translateRectInScreenToAppWindow(rect);
            this.mTranslator.translateRectInScreenToAppWindow(rect2);
            this.mTranslator.translateRectInScreenToAppWindow(rect3);
        }
        object2 = SomeArgs.obtain();
        n2 = Binder.getCallingPid() == Process.myPid() ? n3 : 0;
        if (n2 != 0) {
            parcelable = new Rect((Rect)parcelable);
        }
        ((SomeArgs)object2).arg1 = parcelable;
        if (n2 != 0) {
            rect2 = new Rect(rect2);
        }
        ((SomeArgs)object2).arg2 = rect2;
        if (n2 != 0) {
            rect3 = new Rect(rect3);
        }
        ((SomeArgs)object2).arg3 = rect3;
        parcelable = n2 != 0 && mergedConfiguration != null ? new MergedConfiguration(mergedConfiguration) : mergedConfiguration;
        ((SomeArgs)object2).arg4 = parcelable;
        parcelable = n2 != 0 ? new Rect(rect) : rect;
        ((SomeArgs)object2).arg5 = parcelable;
        if (n2 != 0) {
            rect4 = new Rect(rect4);
        }
        ((SomeArgs)object2).arg6 = rect4;
        parcelable = n2 != 0 ? new Rect(rect5) : rect5;
        ((SomeArgs)object2).arg7 = parcelable;
        parcelable = n2 != 0 ? new Rect(rect6) : rect6;
        ((SomeArgs)object2).arg8 = parcelable;
        ((SomeArgs)object2).arg9 = parcelableWrapper.get();
        ((SomeArgs)object2).argi1 = bl2 ? 1 : 0;
        ((SomeArgs)object2).argi2 = bl3 ? 1 : 0;
        ((SomeArgs)object2).argi3 = n;
        ((Message)object).obj = object2;
        this.mHandler.sendMessage((Message)object);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private boolean draw(boolean var1_1) {
        block25 : {
            block27 : {
                block28 : {
                    block26 : {
                        var2_2 = this.mSurface;
                        if (!var2_2.isValid()) {
                            return false;
                        }
                        if (!ViewRootImpl.sFirstDrawComplete) {
                            var3_3 = ViewRootImpl.sFirstDrawHandlers;
                            // MONITORENTER : var3_3
                            ViewRootImpl.sFirstDrawComplete = true;
                            var4_7 = ViewRootImpl.sFirstDrawHandlers.size();
                            for (var5_8 = 0; var5_8 < var4_7; ++var5_8) {
                                this.mHandler.post(ViewRootImpl.sFirstDrawHandlers.get(var5_8));
                            }
                            // MONITOREXIT : var3_3
                        }
                        var3_3 = null;
                        this.scrollToRectOrFocus(null, false);
                        if (this.mAttachInfo.mViewScrollChanged) {
                            var6_9 = this.mAttachInfo;
                            var6_9.mViewScrollChanged = false;
                            var6_9.mTreeObserver.dispatchOnScrollChanged();
                        }
                        if (this.mCurScrollY != (var5_8 = (var4_7 = (var6_9 = this.mScroller) != null && var6_9.computeScrollOffset() != false ? 1 : 0) != 0 ? this.mScroller.getCurrY() : this.mScrollY)) {
                            this.mCurScrollY = var5_8;
                            var6_9 = this.mView;
                            if (var6_9 instanceof RootViewSurfaceTaker) {
                                ((RootViewSurfaceTaker)var6_9).onRootViewScrollYChanged(this.mCurScrollY);
                            }
                            var1_1 = true;
                        }
                        var7_10 = this.mAttachInfo.mApplicationScale;
                        var8_11 = this.mAttachInfo.mScalingRequired;
                        var6_9 = this.mDirty;
                        if (this.mSurfaceHolder != null) {
                            var6_9.setEmpty();
                            if (var4_7 == 0) return false;
                            var3_3 = this.mScroller;
                            if (var3_3 == null) return false;
                            var3_3.abortAnimation();
                            return false;
                        }
                        if (var1_1) {
                            var6_9.set(0, 0, (int)((float)this.mWidth * var7_10 + 0.5f), (int)((float)this.mHeight * var7_10 + 0.5f));
                        }
                        this.mAttachInfo.mTreeObserver.dispatchOnDraw();
                        var9_12 = -this.mCanvasOffsetX;
                        var10_13 = -this.mCanvasOffsetY + var5_8;
                        var11_14 = this.mWindowAttributes;
                        if (var11_14 != null) {
                            var3_3 = var11_14.surfaceInsets;
                        }
                        if (var3_3 != null) {
                            var5_8 = var3_3.left;
                            var12_15 = var3_3.top;
                            var6_9.offset(var3_3.left, var3_3.right);
                            var10_13 -= var12_15;
                            var9_12 -= var5_8;
                        }
                        if ((var13_16 = this.mAttachInfo.mAccessibilityFocusDrawable) == null) ** GOTO lbl-1000
                        var11_14 = this.mAttachInfo.mTmpInvalRect;
                        if (!this.getAccessibilityFocusedRect((Rect)var11_14)) {
                            var11_14.setEmpty();
                        }
                        if (!var11_14.equals(var13_16.getBounds())) {
                            var5_8 = 1;
                        } else lbl-1000: // 2 sources:
                        {
                            var5_8 = 0;
                        }
                        this.mAttachInfo.mDrawingTime = this.mChoreographer.getFrameTimeNanos() / 1000000L;
                        var1_1 = false;
                        if (var6_9.isEmpty() && !this.mIsAnimating && var5_8 == 0) break block25;
                        if (this.mAttachInfo.mThreadedRenderer == null || !this.mAttachInfo.mThreadedRenderer.isEnabled()) break block26;
                        var5_8 = var5_8 == 0 && !this.mInvalidateRootRequested ? 0 : 1;
                        this.mInvalidateRootRequested = false;
                        this.mIsAnimating = false;
                        if (this.mHardwareYOffset != var10_13 || this.mHardwareXOffset != var9_12) {
                            this.mHardwareYOffset = var10_13;
                            this.mHardwareXOffset = var9_12;
                            var5_8 = 1;
                        }
                        if (var5_8 != 0) {
                            this.mAttachInfo.mThreadedRenderer.invalidateRoot();
                        }
                        var6_9.setEmpty();
                        var1_1 = this.updateContentDrawBounds();
                        if (this.mReportNextDraw) {
                            this.mAttachInfo.mThreadedRenderer.setStopped(false);
                        }
                        if (var1_1) {
                            this.requestDrawWindow();
                        }
                        var1_1 = true;
                        this.mAttachInfo.mThreadedRenderer.draw(this.mView, this.mAttachInfo, this);
                        break block25;
                    }
                    if (this.mAttachInfo.mThreadedRenderer == null || this.mAttachInfo.mThreadedRenderer.isEnabled() || !this.mAttachInfo.mThreadedRenderer.isRequested() || !this.mSurface.isValid()) break block27;
                    var2_2 = this.mAttachInfo.mThreadedRenderer;
                    var4_7 = this.mWidth;
                    var5_8 = this.mHeight;
                    var6_9 = this.mAttachInfo;
                    try {
                        var2_2.initializeIfNeeded(var4_7, var5_8, (View.AttachInfo)var6_9, this.mSurface, (Rect)var3_3);
                    }
                    catch (Surface.OutOfResourcesException var3_4) {}
                    this.mFullRedrawNeeded = true;
                    this.scheduleTraversals();
                    return false;
                    break block28;
                    catch (Surface.OutOfResourcesException var3_5) {
                        // empty catch block
                    }
                }
                this.handleOutOfResourcesException((Surface.OutOfResourcesException)var3_6);
                return false;
            }
            if (!this.drawSoftware((Surface)var2_2, this.mAttachInfo, var9_12, var10_13, var8_11, (Rect)var6_9, (Rect)var3_3)) {
                return false;
            }
        }
        if (var4_7 == 0) return var1_1;
        this.mFullRedrawNeeded = true;
        this.scheduleTraversals();
        return var1_1;
    }

    private void drawAccessibilityFocusedDrawableIfNeeded(Canvas canvas) {
        block1 : {
            block0 : {
                Rect rect = this.mAttachInfo.mTmpInvalRect;
                if (!this.getAccessibilityFocusedRect(rect)) break block0;
                Drawable drawable2 = this.getAccessibilityFocusedDrawable();
                if (drawable2 == null) break block1;
                drawable2.setBounds(rect);
                drawable2.draw(canvas);
                break block1;
            }
            if (this.mAttachInfo.mAccessibilityFocusDrawable == null) break block1;
            this.mAttachInfo.mAccessibilityFocusDrawable.setBounds(0, 0, 0, 0);
        }
    }

    /*
     * Exception decompiling
     */
    private boolean drawSoftware(Surface var1_1, View.AttachInfo var2_7, int var3_8, int var4_9, boolean var5_10, Rect var6_11, Rect var7_13) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private void dumpViewHierarchy(String string2, PrintWriter printWriter, View view) {
        printWriter.print(string2);
        if (view == null) {
            printWriter.println("null");
            return;
        }
        printWriter.println(view.toString());
        if (!(view instanceof ViewGroup)) {
            return;
        }
        int n = ((ViewGroup)(view = (ViewGroup)view)).getChildCount();
        if (n <= 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("  ");
        string2 = stringBuilder.toString();
        for (int i = 0; i < n; ++i) {
            this.dumpViewHierarchy(string2, printWriter, ((ViewGroup)view).getChildAt(i));
        }
    }

    @UnsupportedAppUsage
    private void enableHardwareAcceleration(WindowManager.LayoutParams object) {
        Object object2 = this.mAttachInfo;
        boolean bl = false;
        ((View.AttachInfo)object2).mHardwareAccelerated = false;
        ((View.AttachInfo)object2).mHardwareAccelerationRequested = false;
        if (this.mTranslator != null) {
            return;
        }
        boolean bl2 = (((WindowManager.LayoutParams)object).flags & 16777216) != 0;
        if (bl2) {
            if (!ThreadedRenderer.isAvailable()) {
                return;
            }
            bl2 = (((WindowManager.LayoutParams)object).privateFlags & 1) != 0;
            boolean bl3 = (((WindowManager.LayoutParams)object).privateFlags & 2) != 0;
            if (bl2) {
                this.mAttachInfo.mHardwareAccelerationRequested = true;
            } else if (!ThreadedRenderer.sRendererDisabled || ThreadedRenderer.sSystemRendererDisabled && bl3) {
                if (this.mAttachInfo.mThreadedRenderer != null) {
                    this.mAttachInfo.mThreadedRenderer.destroy();
                }
                object2 = ((WindowManager.LayoutParams)object).surfaceInsets;
                bl2 = ((Rect)object2).left != 0 || ((Rect)object2).right != 0 || ((Rect)object2).top != 0 || ((Rect)object2).bottom != 0;
                boolean bl4 = ((WindowManager.LayoutParams)object).format != -1 || bl2;
                if (this.mContext.getResources().getConfiguration().isScreenWideColorGamut() && ((WindowManager.LayoutParams)object).getColorMode() == 1) {
                    bl = true;
                }
                this.mAttachInfo.mThreadedRenderer = ThreadedRenderer.create(this.mContext, bl4, ((WindowManager.LayoutParams)object).getTitle().toString());
                this.mAttachInfo.mThreadedRenderer.setWideGamut(bl);
                this.updateForceDarkMode();
                if (this.mAttachInfo.mThreadedRenderer != null) {
                    object = this.mAttachInfo;
                    ((View.AttachInfo)object).mHardwareAccelerationRequested = true;
                    ((View.AttachInfo)object).mHardwareAccelerated = true;
                }
            }
        }
    }

    private void endDragResizing() {
        if (this.mDragResizing) {
            this.mDragResizing = false;
            if (this.mUseMTRenderer) {
                for (int i = this.mWindowCallbacks.size() - 1; i >= 0; --i) {
                    this.mWindowCallbacks.get(i).onWindowDragResizeEnd();
                }
            }
            this.mFullRedrawNeeded = true;
        }
    }

    private Rect ensureInsetsNonNegative(Rect rect, String string2) {
        if (rect.left >= 0 && rect.top >= 0 && rect.right >= 0 && rect.bottom >= 0) {
            return rect;
        }
        String string3 = this.mTag;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Negative ");
        stringBuilder.append(string2);
        stringBuilder.append("Insets: ");
        stringBuilder.append(rect);
        stringBuilder.append(", mFirst=");
        stringBuilder.append(this.mFirst);
        Log.wtf(string3, stringBuilder.toString());
        return new Rect(Math.max(0, rect.left), Math.max(0, rect.top), Math.max(0, rect.right), Math.max(0, rect.bottom));
    }

    private boolean ensureTouchModeLocally(boolean bl) {
        if (this.mAttachInfo.mInTouchMode == bl) {
            return false;
        }
        View.AttachInfo attachInfo = this.mAttachInfo;
        attachInfo.mInTouchMode = bl;
        attachInfo.mTreeObserver.dispatchOnTouchModeChanged(bl);
        bl = bl ? this.enterTouchMode() : this.leaveTouchMode();
        return bl;
    }

    private boolean enterTouchMode() {
        View view;
        View view2 = this.mView;
        if (view2 != null && view2.hasFocus() && (view = this.mView.findFocus()) != null && !view.isFocusableInTouchMode()) {
            view2 = ViewRootImpl.findAncestorToTakeFocusInTouchMode(view);
            if (view2 != null) {
                return view2.requestFocus();
            }
            view.clearFocusInternal(null, true, false);
            return true;
        }
        return false;
    }

    private static ViewGroup findAncestorToTakeFocusInTouchMode(View object) {
        object = ((View)object).getParent();
        while (object instanceof ViewGroup) {
            if (((ViewGroup)(object = (ViewGroup)object)).getDescendantFocusability() == 262144 && ((View)object).isFocusableInTouchMode()) {
                return object;
            }
            if (((View)object).isRootNamespace()) {
                return null;
            }
            object = ((View)object).getParent();
        }
        return null;
    }

    private AccessibilityNodeInfo findFocusedVirtualNode(AccessibilityNodeProvider accessibilityNodeProvider) {
        Object object = accessibilityNodeProvider.findFocus(1);
        if (object != null) {
            return object;
        }
        if (!this.mContext.isAutofillCompatibilityEnabled()) {
            return null;
        }
        Object object2 = accessibilityNodeProvider.createAccessibilityNodeInfo(-1);
        if (((AccessibilityNodeInfo)object2).isFocused()) {
            return object2;
        }
        object = new LinkedList();
        object.offer(object2);
        while (!object.isEmpty()) {
            AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo)object.poll();
            object2 = accessibilityNodeInfo.getChildNodeIds();
            if (object2 == null || ((LongArray)object2).size() <= 0) continue;
            int n = ((LongArray)object2).size();
            for (int i = 0; i < n; ++i) {
                AccessibilityNodeInfo accessibilityNodeInfo2 = accessibilityNodeProvider.createAccessibilityNodeInfo(AccessibilityNodeInfo.getVirtualDescendantId(((LongArray)object2).get(i)));
                if (accessibilityNodeInfo2 == null) continue;
                if (accessibilityNodeInfo2.isFocused()) {
                    return accessibilityNodeInfo2;
                }
                object.offer(accessibilityNodeInfo2);
            }
            accessibilityNodeInfo.recycle();
        }
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void finishInputEvent(QueuedInputEvent var1_1) {
        Trace.asyncTraceEnd(8L, "deliverInputEvent", var1_1.mEvent.getSequenceNumber());
        if (var1_1.mReceiver != null) {
            var2_3 = var1_1.mFlags;
            var3_4 = true;
            var4_5 = (var2_3 & 8) != 0;
            if ((var1_1.mFlags & 64) == 0) {
                var3_4 = false;
            }
            if (var3_4) {
                Trace.traceBegin(8L, "processInputEventBeforeFinish");
                try {
                    var5_6 = this.mInputCompatProcessor.processInputEventBeforeFinish(var1_1.mEvent);
                    if (var5_6 == null) ** GOTO lbl21
                    var1_1.mReceiver.finishInputEvent(var5_6, var4_5);
                }
                finally {
                    Trace.traceEnd(8L);
                }
            } else {
                var1_1.mReceiver.finishInputEvent(var1_1.mEvent, var4_5);
            }
        } else {
            var1_1.mEvent.recycleIfNeededAfterDispatch();
        }
lbl21: // 4 sources:
        this.recycleQueuedInputEvent(var1_1);
    }

    private void fireAccessibilityFocusEventIfHasFocusedNode() {
        if (!AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            return;
        }
        View view = this.mView.findFocus();
        if (view == null) {
            return;
        }
        Object object = view.getAccessibilityNodeProvider();
        if (object == null) {
            view.sendAccessibilityEvent(8);
        } else if ((object = this.findFocusedVirtualNode((AccessibilityNodeProvider)object)) != null) {
            int n = AccessibilityNodeInfo.getVirtualDescendantId(((AccessibilityNodeInfo)object).getSourceNodeId());
            AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(8);
            accessibilityEvent.setSource(view, n);
            accessibilityEvent.setPackageName(((AccessibilityNodeInfo)object).getPackageName());
            accessibilityEvent.setChecked(((AccessibilityNodeInfo)object).isChecked());
            accessibilityEvent.setContentDescription(((AccessibilityNodeInfo)object).getContentDescription());
            accessibilityEvent.setPassword(((AccessibilityNodeInfo)object).isPassword());
            accessibilityEvent.getText().add(((AccessibilityNodeInfo)object).getText());
            accessibilityEvent.setEnabled(((AccessibilityNodeInfo)object).isEnabled());
            view.getParent().requestSendAccessibilityEvent(view, accessibilityEvent);
            ((AccessibilityNodeInfo)object).recycle();
        }
    }

    private static void forceLayout(View view) {
        view.forceLayout();
        if (view instanceof ViewGroup) {
            view = (ViewGroup)view;
            int n = ((ViewGroup)view).getChildCount();
            for (int i = 0; i < n; ++i) {
                ViewRootImpl.forceLayout(((ViewGroup)view).getChildAt(i));
            }
        }
    }

    private Drawable getAccessibilityFocusedDrawable() {
        if (this.mAttachInfo.mAccessibilityFocusDrawable == null) {
            TypedValue typedValue = new TypedValue();
            if (this.mView.mContext.getTheme().resolveAttribute(17956871, typedValue, true)) {
                this.mAttachInfo.mAccessibilityFocusDrawable = this.mView.mContext.getDrawable(typedValue.resourceId);
            }
        }
        return this.mAttachInfo.mAccessibilityFocusDrawable;
    }

    private boolean getAccessibilityFocusedRect(Rect rect) {
        block4 : {
            block5 : {
                block8 : {
                    Object object;
                    block7 : {
                        block6 : {
                            object = AccessibilityManager.getInstance(this.mView.mContext);
                            if (!((AccessibilityManager)object).isEnabled() || !((AccessibilityManager)object).isTouchExplorationEnabled()) break block4;
                            object = this.mAccessibilityFocusedHost;
                            if (object == null || ((View)object).mAttachInfo == null) break block5;
                            if (((View)object).getAccessibilityNodeProvider() != null) break block6;
                            ((View)object).getBoundsOnScreen(rect, true);
                            break block7;
                        }
                        object = this.mAccessibilityFocusedVirtualView;
                        if (object == null) break block8;
                        ((AccessibilityNodeInfo)object).getBoundsInScreen(rect);
                    }
                    object = this.mAttachInfo;
                    rect.offset(0, object.mViewRootImpl.mScrollY);
                    rect.offset(-((View.AttachInfo)object).mWindowLeft, -((View.AttachInfo)object).mWindowTop);
                    if (!rect.intersect(0, 0, object.mViewRootImpl.mWidth, object.mViewRootImpl.mHeight)) {
                        rect.setEmpty();
                    }
                    return rect.isEmpty() ^ true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private AudioManager getAudioManager() {
        View view = this.mView;
        if (view != null) {
            if (this.mAudioManager == null) {
                this.mAudioManager = (AudioManager)view.getContext().getSystemService("audio");
            }
            return this.mAudioManager;
        }
        throw new IllegalStateException("getAudioManager called when there is no mView");
    }

    private AutofillManager getAutofillManager() {
        View view = this.mView;
        if (view instanceof ViewGroup && ((ViewGroup)(view = (ViewGroup)view)).getChildCount() > 0) {
            return ((ViewGroup)view).getChildAt(0).getContext().getSystemService(AutofillManager.class);
        }
        return null;
    }

    private View getCommonPredecessor(View object, View view) {
        if (this.mTempHashSet == null) {
            this.mTempHashSet = new HashSet();
        }
        HashSet<View> hashSet = this.mTempHashSet;
        hashSet.clear();
        while (object != null) {
            hashSet.add((View)object);
            object = ((View)object).mParent;
            if (object instanceof View) {
                object = (View)object;
                continue;
            }
            object = null;
        }
        object = view;
        while (object != null) {
            if (hashSet.contains(object)) {
                hashSet.clear();
                return object;
            }
            object = ((View)object).mParent;
            if (object instanceof View) {
                object = (View)object;
                continue;
            }
            object = null;
        }
        hashSet.clear();
        return null;
    }

    private static void getGfxInfo(View view, int[] arrn) {
        RenderNode renderNode = view.mRenderNode;
        arrn[0] = arrn[0] + 1;
        if (renderNode != null) {
            arrn[1] = arrn[1] + (int)renderNode.computeApproximateMemoryUsage();
        }
        if (view instanceof ViewGroup) {
            view = (ViewGroup)view;
            int n = ((ViewGroup)view).getChildCount();
            for (int i = 0; i < n; ++i) {
                ViewRootImpl.getGfxInfo(((ViewGroup)view).getChildAt(i), arrn);
            }
        }
    }

    private int getImpliedSystemUiVisibility(WindowManager.LayoutParams layoutParams) {
        int n = 0;
        if ((layoutParams.flags & 67108864) != 0) {
            n = 0 | 1280;
        }
        int n2 = n;
        if ((layoutParams.flags & 134217728) != 0) {
            n2 = n | 768;
        }
        return n2;
    }

    private int getNightMode() {
        return this.mContext.getResources().getConfiguration().uiMode & 48;
    }

    private static int getRootMeasureSpec(int n, int n2) {
        n = n2 != -2 ? (n2 != -1 ? View.MeasureSpec.makeMeasureSpec(n2, 1073741824) : View.MeasureSpec.makeMeasureSpec(n, 1073741824)) : View.MeasureSpec.makeMeasureSpec(n, Integer.MIN_VALUE);
        return n;
    }

    static HandlerActionQueue getRunQueue() {
        HandlerActionQueue handlerActionQueue = sRunQueues.get();
        if (handlerActionQueue != null) {
            return handlerActionQueue;
        }
        handlerActionQueue = new HandlerActionQueue();
        sRunQueues.set(handlerActionQueue);
        return handlerActionQueue;
    }

    private View getSourceForAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int n = AccessibilityNodeInfo.getAccessibilityViewId(accessibilityEvent.getSourceNodeId());
        return AccessibilityNodeIdManager.getInstance().findView(n);
    }

    private ArrayList<View> getValidLayoutRequesters(ArrayList<View> arrayList, boolean bl) {
        Object object;
        int n;
        int n2 = arrayList.size();
        View view = null;
        for (n = 0; n < n2; ++n) {
            block13 : {
                View view2;
                boolean bl2;
                block14 : {
                    view2 = arrayList.get(n);
                    object = view;
                    if (view2 == null) break block13;
                    object = view;
                    if (view2.mAttachInfo == null) break block13;
                    object = view;
                    if (view2.mParent == null) break block13;
                    if (bl) break block14;
                    object = view;
                    if ((view2.mPrivateFlags & 4096) != 4096) break block13;
                }
                boolean bl3 = false;
                object = view2;
                do {
                    bl2 = bl3;
                    if (object == null) break;
                    if ((((View)object).mViewFlags & 12) == 8) {
                        bl2 = true;
                        break;
                    }
                    if (((View)object).mParent instanceof View) {
                        object = (View)((Object)((View)object).mParent);
                        continue;
                    }
                    object = null;
                } while (true);
                object = view;
                if (!bl2) {
                    object = view;
                    if (view == null) {
                        object = new ArrayList();
                    }
                    ((ArrayList)object).add(view2);
                }
            }
            view = object;
        }
        if (!bl) {
            for (n = 0; n < n2; ++n) {
                object = arrayList.get(n);
                while (object != null && (((View)object).mPrivateFlags & 4096) != 0) {
                    ((View)object).mPrivateFlags &= -4097;
                    if (((View)object).mParent instanceof View) {
                        object = (View)((Object)((View)object).mParent);
                        continue;
                    }
                    object = null;
                }
            }
        }
        arrayList.clear();
        return view;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void handleDragEvent(DragEvent var1_1) {
        block17 : {
            block19 : {
                block18 : {
                    if (this.mView == null || !this.mAdded) break block17;
                    var2_2 = var1_1.mAction;
                    if (var2_2 == 1) {
                        this.mCurrentDragView = null;
                        this.mDragDescription = var1_1.mClipDescription;
                    } else {
                        if (var2_2 == 4) {
                            this.mDragDescription = null;
                        }
                        var1_1.mClipDescription = this.mDragDescription;
                    }
                    if (var2_2 != 6) break block18;
                    if (View.sCascadedDragDrop) {
                        this.mView.dispatchDragEnterExitInPreN(var1_1);
                    }
                    this.setDragFocus(null, var1_1);
                    break block17;
                }
                if (var2_2 == 2 || var2_2 == 3) {
                    this.mDragPoint.set(var1_1.mX, var1_1.mY);
                    var3_3 = this.mTranslator;
                    if (var3_3 != null) {
                        var3_3.translatePointInScreenToAppWindow(this.mDragPoint);
                    }
                    if ((var4_6 = this.mCurScrollY) != 0) {
                        this.mDragPoint.offset(0.0f, var4_6);
                    }
                    var1_1.mX = this.mDragPoint.x;
                    var1_1.mY = this.mDragPoint.y;
                }
                var3_3 = this.mCurrentDragView;
                if (var2_2 == 3 && var1_1.mClipData != null) {
                    var1_1.mClipData.prepareToEnterProcess();
                }
                var5_7 = this.mView.dispatchDragEvent(var1_1);
                if (var2_2 == 2 && !var1_1.mEventHandlerWasCalled) {
                    this.setDragFocus(null, var1_1);
                }
                if (var3_3 == this.mCurrentDragView) break block19;
                if (var3_3 == null) ** GOTO lbl36
                try {
                    this.mWindowSession.dragRecipientExited(this.mWindow);
lbl36: // 2 sources:
                    if (this.mCurrentDragView != null) {
                        this.mWindowSession.dragRecipientEntered(this.mWindow);
                    }
                }
                catch (RemoteException var3_4) {
                    Slog.e(this.mTag, "Unable to note drag target change");
                }
            }
            if (var2_2 == 3) {
                try {
                    var6_8 = this.mTag;
                    var3_3 = new StringBuilder();
                    var3_3.append("Reporting drop result: ");
                    var3_3.append(var5_7);
                    Log.i(var6_8, var3_3.toString());
                    this.mWindowSession.reportDropResult(this.mWindow, var5_7);
                }
                catch (RemoteException var3_5) {
                    Log.e(this.mTag, "Unable to report drop result");
                }
            }
            if (var2_2 == 4) {
                this.mCurrentDragView = null;
                this.setLocalDragState(null);
                var3_3 = this.mAttachInfo;
                var3_3.mDragToken = null;
                if (var3_3.mDragSurface != null) {
                    this.mAttachInfo.mDragSurface.release();
                    this.mAttachInfo.mDragSurface = null;
                }
            }
        }
        var1_1.recycle();
    }

    private void handleOutOfResourcesException(Surface.OutOfResourcesException outOfResourcesException) {
        Log.e(this.mTag, "OutOfResourcesException initializing HW surface", outOfResourcesException);
        try {
            if (!this.mWindowSession.outOfMemory(this.mWindow) && Process.myUid() != 1000) {
                Slog.w(this.mTag, "No processes killed for memory; killing self");
                Process.killProcess(Process.myPid());
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        this.mLayoutRequested = true;
    }

    private void handlePointerCaptureChanged(boolean bl) {
        if (this.mPointerCapture == bl) {
            return;
        }
        this.mPointerCapture = bl;
        View view = this.mView;
        if (view != null) {
            view.dispatchPointerCaptureChanged(bl);
        }
    }

    private void handleWindowContentChangedEvent(AccessibilityEvent object) {
        View view = this.mAccessibilityFocusedHost;
        if (view != null && this.mAccessibilityFocusedVirtualView != null) {
            Object object2 = view.getAccessibilityNodeProvider();
            if (object2 == null) {
                this.mAccessibilityFocusedHost = null;
                this.mAccessibilityFocusedVirtualView = null;
                view.clearAccessibilityFocusNoCallbacks(0);
                return;
            }
            int n = ((AccessibilityEvent)object).getContentChangeTypes();
            if ((n & 1) == 0 && n != 0) {
                return;
            }
            int n2 = AccessibilityNodeInfo.getAccessibilityViewId(((AccessibilityRecord)object).getSourceNodeId());
            n = 0;
            object = this.mAccessibilityFocusedHost;
            while (object != null && n == 0) {
                if (n2 == ((View)object).getAccessibilityViewId()) {
                    n = 1;
                    continue;
                }
                if ((object = ((View)object).getParent()) instanceof View) {
                    object = (View)object;
                    continue;
                }
                object = null;
            }
            if (n == 0) {
                return;
            }
            n = AccessibilityNodeInfo.getVirtualDescendantId(this.mAccessibilityFocusedVirtualView.getSourceNodeId());
            object = this.mTempRect;
            this.mAccessibilityFocusedVirtualView.getBoundsInScreen((Rect)object);
            AccessibilityNodeInfo accessibilityNodeInfo = this.mAccessibilityFocusedVirtualView = ((AccessibilityNodeProvider)object2).createAccessibilityNodeInfo(n);
            if (accessibilityNodeInfo == null) {
                this.mAccessibilityFocusedHost = null;
                view.clearAccessibilityFocusNoCallbacks(0);
                ((AccessibilityNodeProvider)object2).performAction(n, AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS.getId(), null);
                this.invalidateRectOnScreen((Rect)object);
            } else {
                object2 = accessibilityNodeInfo.getBoundsInScreen();
                if (!((Rect)object).equals(object2)) {
                    ((Rect)object).union((Rect)object2);
                    this.invalidateRectOnScreen((Rect)object);
                }
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
    private void handleWindowFocusChanged() {
        // MONITORENTER : this
        if (!this.mWindowFocusChanged) {
            // MONITOREXIT : this
            return;
        }
        this.mWindowFocusChanged = false;
        boolean bl = this.mUpcomingWindowFocus;
        boolean bl2 = this.mUpcomingInTouchMode;
        // MONITOREXIT : this
        if (sNewInsetsMode != 0) {
            if (bl) {
                this.mInsetsController.onWindowFocusGained();
            } else {
                this.mInsetsController.onWindowFocusLost();
            }
        }
        if (this.mAdded) {
            Object object;
            this.profileRendering(bl);
            if (bl) {
                this.ensureTouchModeLocally(bl2);
                if (this.mAttachInfo.mThreadedRenderer != null && this.mSurface.isValid()) {
                    this.mFullRedrawNeeded = true;
                    try {
                        object = this.mWindowAttributes;
                        object = object != null ? ((WindowManager.LayoutParams)object).surfaceInsets : null;
                        this.mAttachInfo.mThreadedRenderer.initializeIfNeeded(this.mWidth, this.mHeight, this.mAttachInfo, this.mSurface, (Rect)object);
                    }
                    catch (Surface.OutOfResourcesException outOfResourcesException) {
                        Log.e(this.mTag, "OutOfResourcesException locking surface", outOfResourcesException);
                        try {
                            if (!this.mWindowSession.outOfMemory(this.mWindow)) {
                                Slog.w(this.mTag, "No processes killed for memory; killing self");
                                Process.killProcess(Process.myPid());
                            }
                        }
                        catch (RemoteException remoteException) {
                            // empty catch block
                        }
                        ViewRootHandler viewRootHandler = this.mHandler;
                        viewRootHandler.sendMessageDelayed(viewRootHandler.obtainMessage(6), 500L);
                        return;
                    }
                }
            }
            this.mAttachInfo.mHasWindowFocus = bl;
            this.mLastWasImTarget = WindowManager.LayoutParams.mayUseInputMethod(this.mWindowAttributes.flags);
            InputMethodManager inputMethodManager = this.mContext.getSystemService(InputMethodManager.class);
            if (inputMethodManager != null && this.mLastWasImTarget && !this.isInLocalFocusMode()) {
                inputMethodManager.onPreWindowFocus(this.mView, bl);
            }
            if (this.mView != null) {
                this.mAttachInfo.mKeyDispatchState.reset();
                this.mView.dispatchWindowFocusChanged(bl);
                this.mAttachInfo.mTreeObserver.dispatchOnWindowFocusChange(bl);
                if (this.mAttachInfo.mTooltipHost != null) {
                    this.mAttachInfo.mTooltipHost.hideTooltip();
                }
            }
            if (bl) {
                if (inputMethodManager != null && this.mLastWasImTarget && !this.isInLocalFocusMode()) {
                    object = this.mView;
                    inputMethodManager.onPostWindowFocus((View)object, ((View)object).findFocus(), this.mWindowAttributes.softInputMode, this.mHasHadWindowFocus ^ true, this.mWindowAttributes.flags);
                }
                object = this.mWindowAttributes;
                ((WindowManager.LayoutParams)object).softInputMode &= -257;
                object = (WindowManager.LayoutParams)this.mView.getLayoutParams();
                ((WindowManager.LayoutParams)object).softInputMode &= -257;
                this.mHasHadWindowFocus = true;
                this.fireAccessibilityFocusEventIfHasFocusedNode();
            } else if (this.mPointerCapture) {
                this.handlePointerCaptureChanged(false);
            }
        }
        this.mFirstInputStage.onWindowFocusChanged(bl);
    }

    private boolean hasColorModeChanged(int n) {
        if (this.mAttachInfo.mThreadedRenderer == null) {
            return false;
        }
        boolean bl = n == 1;
        if (this.mAttachInfo.mThreadedRenderer.isWideGamut() == bl) {
            return false;
        }
        return !bl || this.mContext.getResources().getConfiguration().isScreenWideColorGamut();
    }

    private void invalidateRectOnScreen(Rect rect) {
        Rect rect2 = this.mDirty;
        rect2.union(rect.left, rect.top, rect.right, rect.bottom);
        float f = this.mAttachInfo.mApplicationScale;
        boolean bl = rect2.intersect(0, 0, (int)((float)this.mWidth * f + 0.5f), (int)((float)this.mHeight * f + 0.5f));
        if (!bl) {
            rect2.setEmpty();
        }
        if (!this.mWillDrawSoon && (bl || this.mIsAnimating)) {
            this.scheduleTraversals();
        }
    }

    @UnsupportedAppUsage
    public static void invokeFunctor(long l, boolean bl) {
        ThreadedRenderer.invokeFunctor(l, bl);
    }

    private boolean isAutofillUiShowing() {
        AutofillManager autofillManager = this.getAutofillManager();
        if (autofillManager == null) {
            return false;
        }
        return autofillManager.isAutofillUiShowing();
    }

    private boolean isInLocalFocusMode() {
        boolean bl = (this.mWindowAttributes.flags & 268435456) != 0;
        return bl;
    }

    static boolean isInTouchMode() {
        IWindowSession iWindowSession = WindowManagerGlobal.peekWindowSession();
        if (iWindowSession != null) {
            try {
                boolean bl = iWindowSession.getInTouchMode();
                return bl;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return false;
    }

    private static boolean isNavigationKey(KeyEvent keyEvent) {
        int n = keyEvent.getKeyCode();
        if (n != 61 && n != 62 && n != 66 && n != 92 && n != 93 && n != 122 && n != 123) {
            switch (n) {
                default: {
                    return false;
                }
                case 19: 
                case 20: 
                case 21: 
                case 22: 
                case 23: 
            }
        }
        return true;
    }

    static boolean isTerminalInputEvent(InputEvent inputEvent) {
        boolean bl;
        block6 : {
            block5 : {
                boolean bl2 = inputEvent instanceof KeyEvent;
                boolean bl3 = false;
                bl = false;
                if (bl2) {
                    if (((KeyEvent)inputEvent).getAction() == 1) {
                        bl = true;
                    }
                    return bl;
                }
                int n = ((MotionEvent)inputEvent).getAction();
                if (n == 1 || n == 3) break block5;
                bl = bl3;
                if (n != 10) break block6;
            }
            bl = true;
        }
        return bl;
    }

    private static boolean isTypingKey(KeyEvent keyEvent) {
        boolean bl = keyEvent.getUnicodeChar() > 0;
        return bl;
    }

    public static boolean isViewDescendantOf(View object, View view) {
        boolean bl = true;
        if (object == view) {
            return true;
        }
        if (!((object = ((View)object).getParent()) instanceof ViewGroup) || !ViewRootImpl.isViewDescendantOf((View)object, view)) {
            bl = false;
        }
        return bl;
    }

    public static /* synthetic */ void lambda$dznxCZGM2R1fsBljsJKomLjBRoM(ViewRootImpl viewRootImpl) {
        viewRootImpl.postDrawFinished();
    }

    static /* synthetic */ void lambda$performDraw$3(ArrayList arrayList) {
        for (int i = 0; i < arrayList.size(); ++i) {
            ((Runnable)arrayList.get(i)).run();
        }
    }

    static /* synthetic */ void lambda$performDraw$4(Handler handler, ArrayList arrayList, long l) {
        handler.postAtFrontOfQueue(new _$$Lambda$ViewRootImpl$_dgEKMWLAJVMlaVy41safRlNQBo(arrayList));
    }

    static /* synthetic */ void lambda$registerRtFrameCallback$0(HardwareRenderer.FrameDrawingCallback frameDrawingCallback, long l) {
        try {
            frameDrawingCallback.onFrameDraw(l);
        }
        catch (Exception exception) {
            Log.e(TAG, "Exception while executing onFrameDraw", exception);
        }
    }

    private boolean leaveTouchMode() {
        View view = this.mView;
        if (view != null) {
            if (view.hasFocus()) {
                view = this.mView.findFocus();
                if (!(view instanceof ViewGroup)) {
                    return false;
                }
                if (((ViewGroup)view).getDescendantFocusability() != 262144) {
                    return false;
                }
            }
            return this.mView.restoreDefaultFocus();
        }
        return false;
    }

    private void maybeHandleWindowMove(Rect rect) {
        boolean bl = this.mAttachInfo.mWindowLeft != rect.left || this.mAttachInfo.mWindowTop != rect.top;
        if (bl) {
            CompatibilityInfo.Translator translator = this.mTranslator;
            if (translator != null) {
                translator.translateRectInScreenToAppWinFrame(rect);
            }
            this.mAttachInfo.mWindowLeft = rect.left;
            this.mAttachInfo.mWindowTop = rect.top;
        }
        if (bl || this.mAttachInfo.mNeedsUpdateLightCenter) {
            if (this.mAttachInfo.mThreadedRenderer != null) {
                this.mAttachInfo.mThreadedRenderer.setLightCenter(this.mAttachInfo);
            }
            this.mAttachInfo.mNeedsUpdateLightCenter = false;
        }
    }

    private void maybeUpdateTooltip(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() != 1) {
            return;
        }
        int n = motionEvent.getActionMasked();
        if (n != 9 && n != 7 && n != 10) {
            return;
        }
        Object object = AccessibilityManager.getInstance(this.mContext);
        if (((AccessibilityManager)object).isEnabled() && ((AccessibilityManager)object).isTouchExplorationEnabled()) {
            return;
        }
        object = this.mView;
        if (object == null) {
            Slog.d(this.mTag, "maybeUpdateTooltip called after view was removed");
            return;
        }
        ((View)object).dispatchTooltipHoverEvent(motionEvent);
    }

    private boolean measureHierarchy(View view, WindowManager.LayoutParams layoutParams, Resources resources, int n, int n2) {
        boolean bl;
        block10 : {
            block11 : {
                int n3;
                boolean bl2 = false;
                int n4 = n3 = 0;
                if (layoutParams.width == -2) {
                    DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                    resources.getValue(17105070, this.mTmpValue, true);
                    int n5 = 0;
                    if (this.mTmpValue.type == 5) {
                        n5 = (int)this.mTmpValue.getDimension(displayMetrics);
                    }
                    n4 = n3;
                    if (n5 != 0) {
                        n4 = n3;
                        if (n > n5) {
                            int n6 = ViewRootImpl.getRootMeasureSpec(n5, layoutParams.width);
                            n4 = ViewRootImpl.getRootMeasureSpec(n2, layoutParams.height);
                            this.performMeasure(n6, n4);
                            if ((view.getMeasuredWidthAndState() & 16777216) == 0) {
                                n4 = 1;
                            } else {
                                this.performMeasure(ViewRootImpl.getRootMeasureSpec((n5 + n) / 2, layoutParams.width), n4);
                                n4 = n3;
                                if ((view.getMeasuredWidthAndState() & 16777216) == 0) {
                                    n4 = 1;
                                }
                            }
                        }
                    }
                }
                bl = bl2;
                if (n4 != 0) break block10;
                this.performMeasure(ViewRootImpl.getRootMeasureSpec(n, layoutParams.width), ViewRootImpl.getRootMeasureSpec(n2, layoutParams.height));
                if (this.mWidth != view.getMeasuredWidth()) break block11;
                bl = bl2;
                if (this.mHeight == view.getMeasuredHeight()) break block10;
            }
            bl = true;
        }
        return bl;
    }

    private void notifySurfaceDestroyed() {
        this.mSurfaceHolder.ungetCallbacks();
        SurfaceHolder.Callback[] arrcallback = this.mSurfaceHolder.getCallbacks();
        if (arrcallback != null) {
            int n = arrcallback.length;
            for (int i = 0; i < n; ++i) {
                arrcallback[i].surfaceDestroyed(this.mSurfaceHolder);
            }
        }
    }

    private QueuedInputEvent obtainQueuedInputEvent(InputEvent inputEvent, InputEventReceiver inputEventReceiver, int n) {
        QueuedInputEvent queuedInputEvent = this.mQueuedInputEventPool;
        if (queuedInputEvent != null) {
            --this.mQueuedInputEventPoolSize;
            this.mQueuedInputEventPool = queuedInputEvent.mNext;
            queuedInputEvent.mNext = null;
        } else {
            queuedInputEvent = new QueuedInputEvent();
        }
        queuedInputEvent.mEvent = inputEvent;
        queuedInputEvent.mReceiver = inputEventReceiver;
        queuedInputEvent.mFlags = n;
        return queuedInputEvent;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void performConfigurationChange(MergedConfiguration object, boolean bl, int n) {
        if (object == null) {
            throw new IllegalArgumentException("No merged config provided.");
        }
        Object object2 = ((MergedConfiguration)object).getGlobalConfiguration();
        Configuration configuration = ((MergedConfiguration)object).getOverrideConfiguration();
        CompatibilityInfo compatibilityInfo = this.mDisplay.getDisplayAdjustments().getCompatibilityInfo();
        object = object2;
        if (!compatibilityInfo.equals(CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO)) {
            object = new Configuration((Configuration)object2);
            compatibilityInfo.applyToConfiguration(this.mNoncompatDensity, (Configuration)object);
        }
        object2 = sConfigCallbacks;
        synchronized (object2) {
            for (int i = ViewRootImpl.sConfigCallbacks.size() - 1; i >= 0; --i) {
                sConfigCallbacks.get(i).onConfigurationChanged((Configuration)object);
            }
        }
        this.mLastReportedMergedConfiguration.setConfiguration((Configuration)object, configuration);
        this.mForceNextConfigUpdate = bl;
        object = this.mActivityConfigCallback;
        if (object != null) {
            object.onConfigurationChanged(configuration, n);
        } else {
            this.updateConfiguration(n);
        }
        this.mForceNextConfigUpdate = false;
    }

    private void performDraw() {
        SurfaceHolder.Callback[] arrcallback;
        int n;
        int n2;
        Object object;
        int n3;
        block22 : {
            if (this.mAttachInfo.mDisplayState == 1 && !this.mReportNextDraw) {
                return;
            }
            if (this.mView == null) {
                return;
            }
            boolean bl = this.mFullRedrawNeeded || this.mReportNextDraw;
            this.mFullRedrawNeeded = false;
            this.mIsDrawing = true;
            Trace.traceBegin(8L, "draw");
            n = 0;
            n2 = 0;
            n3 = n;
            if (this.mAttachInfo.mThreadedRenderer != null) {
                n3 = n;
                if (this.mAttachInfo.mThreadedRenderer.isEnabled()) {
                    object = this.mAttachInfo.mTreeObserver.captureFrameCommitCallbacks();
                    if (this.mReportNextDraw) {
                        n3 = 1;
                        arrcallback = this.mAttachInfo.mHandler;
                        this.mAttachInfo.mThreadedRenderer.setFrameCompleteCallback(new _$$Lambda$ViewRootImpl$YBiqAhbCbXVPSKdbE3K4rH2gpxI(this, (Handler)arrcallback, (ArrayList)object));
                    } else {
                        n3 = n2;
                        if (object != null) {
                            n3 = n2;
                            if (((ArrayList)object).size() > 0) {
                                arrcallback = this.mAttachInfo.mHandler;
                                this.mAttachInfo.mThreadedRenderer.setFrameCompleteCallback(new _$$Lambda$ViewRootImpl$zlBUjCwDtoAWMNaHI62DIq_eKFY((Handler)arrcallback, (ArrayList)object));
                                n3 = n;
                            }
                        }
                    }
                }
            }
            bl = this.draw(bl);
            n2 = n3;
            if (n3 == 0) break block22;
            n2 = n3;
            if (bl) break block22;
            try {
                this.mAttachInfo.mThreadedRenderer.setFrameCompleteCallback(null);
                n2 = 0;
            }
            catch (Throwable throwable) {
                this.mIsDrawing = false;
                Trace.traceEnd(8L);
                throw throwable;
            }
        }
        this.mIsDrawing = false;
        Trace.traceEnd(8L);
        if (this.mAttachInfo.mPendingAnimatingRenderNodes != null) {
            n = this.mAttachInfo.mPendingAnimatingRenderNodes.size();
            for (n3 = 0; n3 < n; ++n3) {
                this.mAttachInfo.mPendingAnimatingRenderNodes.get(n3).endAllAnimators();
            }
            this.mAttachInfo.mPendingAnimatingRenderNodes.clear();
        }
        if (this.mReportNextDraw) {
            this.mReportNextDraw = false;
            object = this.mWindowDrawCountDown;
            if (object != null) {
                try {
                    ((CountDownLatch)object).await();
                }
                catch (InterruptedException interruptedException) {
                    Log.e(this.mTag, "Window redraw count down interrupted!");
                }
                this.mWindowDrawCountDown = null;
            }
            if (this.mAttachInfo.mThreadedRenderer != null) {
                this.mAttachInfo.mThreadedRenderer.setStopped(this.mStopped);
            }
            if (this.mSurfaceHolder != null && this.mSurface.isValid()) {
                object = new SurfaceCallbackHelper(new _$$Lambda$ViewRootImpl$dznxCZGM2R1fsBljsJKomLjBRoM(this));
                arrcallback = this.mSurfaceHolder.getCallbacks();
                ((SurfaceCallbackHelper)object).dispatchSurfaceRedrawNeededAsync(this.mSurfaceHolder, arrcallback);
            } else if (n2 == 0) {
                if (this.mAttachInfo.mThreadedRenderer != null) {
                    this.mAttachInfo.mThreadedRenderer.fence();
                }
                this.pendingDrawFinished();
            }
        }
        return;
    }

    private void performLayout(WindowManager.LayoutParams object, int n, int n2) {
        block9 : {
            Runnable runnable;
            ArrayList<View> arrayList;
            this.mLayoutRequested = false;
            this.mScrollMayChange = true;
            this.mInLayout = true;
            Object object2 = this.mView;
            if (object2 == null) {
                return;
            }
            Trace.traceBegin(8L, "layout");
            ((View)object2).layout(0, 0, ((View)object2).getMeasuredWidth(), ((View)object2).getMeasuredHeight());
            this.mInLayout = false;
            if (this.mLayoutRequesters.size() <= 0 || (arrayList = this.getValidLayoutRequesters(this.mLayoutRequesters, false)) == null) break block9;
            this.mHandlingLayoutInLayoutRequest = true;
            int n3 = arrayList.size();
            for (int i = 0; i < n3; ++i) {
                View view = arrayList.get(i);
                runnable = new StringBuilder();
                ((StringBuilder)((Object)runnable)).append("requestLayout() improperly called by ");
                ((StringBuilder)((Object)runnable)).append(view);
                ((StringBuilder)((Object)runnable)).append(" during layout: running second layout pass");
                Log.w("View", ((StringBuilder)((Object)runnable)).toString());
                view.requestLayout();
            }
            this.measureHierarchy((View)object2, (WindowManager.LayoutParams)object, this.mView.getContext().getResources(), n, n2);
            this.mInLayout = true;
            ((View)object2).layout(0, 0, ((View)object2).getMeasuredWidth(), ((View)object2).getMeasuredHeight());
            this.mHandlingLayoutInLayoutRequest = false;
            object2 = this.getValidLayoutRequesters(this.mLayoutRequesters, true);
            if (object2 == null) break block9;
            object = ViewRootImpl.getRunQueue();
            runnable = new Runnable((ArrayList)object2){
                final /* synthetic */ ArrayList val$finalRequesters;
                {
                    this.val$finalRequesters = arrayList;
                }

                @Override
                public void run() {
                    int n = this.val$finalRequesters.size();
                    for (int i = 0; i < n; ++i) {
                        View view = (View)this.val$finalRequesters.get(i);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("requestLayout() improperly called by ");
                        stringBuilder.append(view);
                        stringBuilder.append(" during second layout pass: posting in next frame");
                        Log.w("View", stringBuilder.toString());
                        view.requestLayout();
                    }
                }
            };
            ((HandlerActionQueue)object).post(runnable);
        }
        this.mInLayout = false;
        return;
        finally {
            Trace.traceEnd(8L);
        }
    }

    private void performMeasure(int n, int n2) {
        if (this.mView == null) {
            return;
        }
        Trace.traceBegin(8L, "measure");
        try {
            this.mView.measure(n, n2);
            return;
        }
        finally {
            Trace.traceEnd(8L);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void performTraversals() {
        block177 : {
            block182 : {
                block175 : {
                    block166 : {
                        block174 : {
                            block173 : {
                                block170 : {
                                    block172 : {
                                        block169 : {
                                            block171 : {
                                                block168 : {
                                                    block167 : {
                                                        block165 : {
                                                            block181 : {
                                                                block179 : {
                                                                    block180 : {
                                                                        block178 : {
                                                                            var1_1 = this.mView;
                                                                            if (var1_1 == null) return;
                                                                            if (!this.mAdded) {
                                                                                return;
                                                                            }
                                                                            this.mIsInTraversal = true;
                                                                            this.mWillDrawSoon = true;
                                                                            var2_2 = 0;
                                                                            var3_3 = this.mWindowAttributes;
                                                                            var4_8 = this.getHostVisibility();
                                                                            var5_9 = this.mFirst == false && (this.mViewVisibility != var4_8 || this.mNewSurfaceNeeded != false || this.mAppVisibilityChanged != false);
                                                                            this.mAppVisibilityChanged = false;
                                                                            var8_12 = this.mFirst == false && (var6_10 = this.mViewVisibility == 0 ? 1 : 0) != (var7_11 = var4_8 == 0 ? 1 : 0) ? 1 : 0;
                                                                            var9_13 = null;
                                                                            if (this.mWindowAttributesChanged) {
                                                                                this.mWindowAttributesChanged = false;
                                                                                var9_13 = var3_3;
                                                                                var10_14 = 1;
                                                                            } else {
                                                                                var10_14 = 0;
                                                                            }
                                                                            var11_15 = this.mDisplay.getDisplayAdjustments().getCompatibilityInfo().supportsScreen();
                                                                            var12_16 = this.mLastInCompatMode;
                                                                            if (var11_15 == var12_16) {
                                                                                this.mFullRedrawNeeded = true;
                                                                                this.mLayoutRequested = true;
                                                                                if (var12_16) {
                                                                                    var3_3.privateFlags &= -129;
                                                                                    this.mLastInCompatMode = false;
                                                                                } else {
                                                                                    var3_3.privateFlags |= 128;
                                                                                    this.mLastInCompatMode = true;
                                                                                }
                                                                                var9_13 = var3_3;
                                                                            }
                                                                            this.mWindowAttributesChangesFlag = 0;
                                                                            var13_17 = this.mWinFrame;
                                                                            if (!this.mFirst) break block178;
                                                                            this.mFullRedrawNeeded = true;
                                                                            this.mLayoutRequested = true;
                                                                            var14_32 = this.mContext.getResources().getConfiguration();
                                                                            if (ViewRootImpl.shouldUseDisplaySize((WindowManager.LayoutParams)var3_3)) {
                                                                                var15_33 = new Point();
                                                                                this.mDisplay.getRealSize((Point)var15_33);
                                                                                var6_10 = var15_33.x;
                                                                                var7_11 = var15_33.y;
                                                                            } else {
                                                                                var6_10 = this.mWinFrame.width();
                                                                                var7_11 = this.mWinFrame.height();
                                                                            }
                                                                            var15_33 = this.mAttachInfo;
                                                                            var15_33.mUse32BitDrawingCache = true;
                                                                            var15_33.mWindowVisibility = var4_8;
                                                                            var15_33.mRecomputeGlobalAttributes = false;
                                                                            this.mLastConfigurationFromResources.setTo((Configuration)var14_32);
                                                                            this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                                                                            if (this.mViewLayoutDirectionInitial == 2) {
                                                                                var1_1.setLayoutDirection(var14_32.getLayoutDirection());
                                                                            }
                                                                            var1_1.dispatchAttachedToWindow(this.mAttachInfo, 0);
                                                                            this.mAttachInfo.mTreeObserver.dispatchOnWindowAttachedChange(true);
                                                                            this.dispatchApplyInsets(var1_1);
                                                                            break block179;
                                                                        }
                                                                        var16_34 = var13_17.width();
                                                                        var17_35 = var13_17.height();
                                                                        if (var16_34 != this.mWidth) break block180;
                                                                        var7_11 = var17_35;
                                                                        var6_10 = var16_34;
                                                                        if (var17_35 == this.mHeight) break block179;
                                                                    }
                                                                    this.mFullRedrawNeeded = true;
                                                                    this.mLayoutRequested = true;
                                                                    var2_2 = 1;
                                                                    var6_10 = var16_34;
                                                                    var7_11 = var17_35;
                                                                }
                                                                if (var5_9) {
                                                                    this.mAttachInfo.mWindowVisibility = var4_8;
                                                                    var1_1.dispatchWindowVisibilityChanged(var4_8);
                                                                    if (var8_12 != 0) {
                                                                        var11_15 = var4_8 == 0;
                                                                        var1_1.dispatchVisibilityAggregated(var11_15);
                                                                    }
                                                                    if (var4_8 != 0 || this.mNewSurfaceNeeded) {
                                                                        this.endDragResizing();
                                                                        this.destroyHardwareResources();
                                                                    }
                                                                    if (var4_8 == 8) {
                                                                        this.mHasHadWindowFocus = false;
                                                                    }
                                                                }
                                                                if (this.mAttachInfo.mWindowVisibility != 0) {
                                                                    var1_1.clearAccessibilityFocus();
                                                                }
                                                                ViewRootImpl.getRunQueue().executeActions(this.mAttachInfo.mHandler);
                                                                var8_12 = 0;
                                                                var18_36 = this.mLayoutRequested != false && (this.mStopped == false || this.mReportNextDraw != false) ? 1 : 0;
                                                                if (var18_36 != 0) {
                                                                    var14_32 = this.mView.getContext().getResources();
                                                                    if (this.mFirst) {
                                                                        var15_33 = this.mAttachInfo;
                                                                        var11_15 = this.mAddedTouchMode;
                                                                        var15_33.mInTouchMode = var11_15 ^ true;
                                                                        this.ensureTouchModeLocally(var11_15);
                                                                        var8_12 = 0;
                                                                    } else {
                                                                        if (!this.mPendingOverscanInsets.equals(this.mAttachInfo.mOverscanInsets)) {
                                                                            var8_12 = 1;
                                                                        }
                                                                        if (!this.mPendingContentInsets.equals(this.mAttachInfo.mContentInsets)) {
                                                                            var8_12 = 1;
                                                                        }
                                                                        if (!this.mPendingStableInsets.equals(this.mAttachInfo.mStableInsets)) {
                                                                            var8_12 = 1;
                                                                        }
                                                                        if (!this.mPendingDisplayCutout.equals(this.mAttachInfo.mDisplayCutout)) {
                                                                            var8_12 = 1;
                                                                        }
                                                                        if (!this.mPendingVisibleInsets.equals(this.mAttachInfo.mVisibleInsets)) {
                                                                            this.mAttachInfo.mVisibleInsets.set(this.mPendingVisibleInsets);
                                                                        }
                                                                        if (!this.mPendingOutsets.equals(this.mAttachInfo.mOutsets)) {
                                                                            var8_12 = 1;
                                                                        }
                                                                        if (this.mPendingAlwaysConsumeSystemBars != this.mAttachInfo.mAlwaysConsumeSystemBars) {
                                                                            var8_12 = 1;
                                                                        }
                                                                        if (var3_3.width == -2 || var3_3.height == -2) {
                                                                            var2_2 = 1;
                                                                            if (ViewRootImpl.shouldUseDisplaySize((WindowManager.LayoutParams)var3_3)) {
                                                                                var15_33 = new Point();
                                                                                this.mDisplay.getRealSize((Point)var15_33);
                                                                                var6_10 = var15_33.x;
                                                                                var7_11 = var15_33.y;
                                                                            } else {
                                                                                var15_33 = var14_32.getConfiguration();
                                                                                var6_10 = this.dipToPx(var15_33.screenWidthDp);
                                                                                var7_11 = this.dipToPx(var15_33.screenHeightDp);
                                                                            }
                                                                        }
                                                                    }
                                                                    var2_2 |= this.measureHierarchy(var1_1, (WindowManager.LayoutParams)var3_3, (Resources)var14_32, var6_10, var7_11);
                                                                    var16_34 = var6_10;
                                                                } else {
                                                                    var8_12 = 0;
                                                                    var16_34 = var6_10;
                                                                }
                                                                if (this.collectViewAttributes()) {
                                                                    var9_13 = var3_3;
                                                                }
                                                                if (this.mAttachInfo.mForceReportNewAttributes) {
                                                                    this.mAttachInfo.mForceReportNewAttributes = false;
                                                                    var9_13 = var3_3;
                                                                }
                                                                if (this.mFirst || this.mAttachInfo.mViewVisibilityChanged) {
                                                                    var14_32 = this.mAttachInfo;
                                                                    var14_32.mViewVisibilityChanged = false;
                                                                    var6_10 = this.mSoftInputMode & 240;
                                                                    if (var6_10 == 0) {
                                                                        var19_37 = var14_32.mScrollContainers.size();
                                                                        for (var17_35 = 0; var17_35 < var19_37; ++var17_35) {
                                                                            if (!this.mAttachInfo.mScrollContainers.get(var17_35).isShown()) continue;
                                                                            var6_10 = 16;
                                                                        }
                                                                        var17_35 = var6_10;
                                                                        if (var6_10 == 0) {
                                                                            var17_35 = 32;
                                                                        }
                                                                        if ((var3_3.softInputMode & 240) != var17_35) {
                                                                            var3_3.softInputMode = var3_3.softInputMode & -241 | var17_35;
                                                                            var9_13 = var3_3;
                                                                        }
                                                                    }
                                                                }
                                                                if (var9_13 != null) {
                                                                    if ((var1_1.mPrivateFlags & 512) != 0 && !PixelFormat.formatHasAlpha(var9_13.format)) {
                                                                        var9_13.format = -3;
                                                                    }
                                                                    var14_32 = this.mAttachInfo;
                                                                    var11_15 = (var9_13.flags & 33554432) != 0;
                                                                    var14_32.mOverscanRequested = var11_15;
                                                                }
                                                                if (this.mApplyInsetsRequested) {
                                                                    this.mApplyInsetsRequested = false;
                                                                    this.mLastOverscanRequested = this.mAttachInfo.mOverscanRequested;
                                                                    this.dispatchApplyInsets(var1_1);
                                                                    if (this.mLayoutRequested) {
                                                                        var2_2 |= this.measureHierarchy(var1_1, (WindowManager.LayoutParams)var3_3, this.mView.getContext().getResources(), var16_34, var7_11);
                                                                    }
                                                                }
                                                                var14_32 = var9_13;
                                                                if (var18_36 != 0) {
                                                                    this.mLayoutRequested = false;
                                                                }
                                                                var6_10 = var18_36 != 0 && var2_2 != 0 && (this.mWidth != var1_1.getMeasuredWidth() || this.mHeight != var1_1.getMeasuredHeight() || var3_3.width == -2 && var13_17.width() < var16_34 && var13_17.width() != this.mWidth || var3_3.height == -2 && var13_17.height() < var7_11 && var13_17.height() != this.mHeight) ? 1 : 0;
                                                                var7_11 = this.mDragResizing != false && this.mResizeMode == 0 ? 1 : 0;
                                                                var11_15 = this.mActivityRelaunched;
                                                                var20_38 = this.mAttachInfo.mTreeObserver.hasComputeInternalInsetsListeners() || this.mAttachInfo.mHasNonEmptyGivenInternalInsets;
                                                                var16_34 = 0;
                                                                var2_2 = 0;
                                                                var21_39 = this.mSurface.getGenerationId();
                                                                var22_40 = var4_8 == 0;
                                                                var23_41 = this.mForceNextWindowRelayout;
                                                                var17_35 = 0;
                                                                if (this.mFirst || (var6_10 | var7_11 | var11_15) != 0 || var8_12 != 0 || var5_9 || var14_32 != null) break block181;
                                                                var11_15 = false;
                                                                if (this.mForceNextWindowRelayout) break block181;
                                                                this.maybeHandleWindowMove((Rect)var13_17);
                                                                var7_11 = var16_34;
                                                                var10_14 = var2_2;
                                                                var6_10 = var17_35;
                                                                break block182;
                                                            }
                                                            var9_13 = var13_17;
                                                            this.mForceNextWindowRelayout = false;
                                                            if (var22_40) {
                                                                var11_15 = var20_38 != false && (this.mFirst != false || var5_9 != false);
                                                                var12_16 = var11_15;
                                                            } else {
                                                                var12_16 = false;
                                                            }
                                                            var13_17 = this.mSurfaceHolder;
                                                            if (var13_17 != null) {
                                                                var13_17.mSurfaceLock.lock();
                                                                this.mDrawingAllowed = true;
                                                            }
                                                            var24_42 = 0;
                                                            var25_43 = 0;
                                                            var17_35 = 0;
                                                            var26_44 = 0;
                                                            var16_34 = 0;
                                                            var8_12 = 0;
                                                            var27_45 = 0;
                                                            var28_46 = this.mSurface.isValid();
                                                            var13_17 = this.mAttachInfo.mThreadedRenderer;
                                                            if (var13_17 == null) break block165;
                                                            if (!this.mAttachInfo.mThreadedRenderer.pause()) ** GOTO lbl227
                                                            var13_17 = this.mDirty;
                                                            var11_15 = false;
                                                            var6_10 = this.mWidth;
                                                            var7_11 = 0;
                                                            var2_2 = this.mHeight;
                                                            {
                                                                catch (RemoteException var13_18) {
                                                                    var6_10 = 0;
                                                                    var16_34 = var10_14;
                                                                    break block166;
                                                                }
                                                            }
                                                            try {
                                                                block183 : {
                                                                    var13_17.set(0, 0, var6_10, var2_2);
                                                                    break block183;
                                                                    catch (RemoteException var13_19) {
                                                                        var7_11 = 0;
                                                                        var6_10 = 0;
                                                                        var16_34 = var10_14;
                                                                    }
                                                                    break block166;
                                                                }
                                                                this.mChoreographer.mFrameInfo.addFlags(1L);
                                                                break block165;
                                                            }
                                                            catch (RemoteException v0) {
                                                                var7_11 = 0;
                                                                var11_15 = false;
                                                                var13_17 = v0;
                                                                var6_10 = 0;
                                                                var16_34 = var10_14;
                                                                break block166;
                                                            }
                                                            catch (RemoteException var13_20) {
                                                                var11_15 = false;
                                                                var7_11 = 0;
                                                                var6_10 = 0;
                                                                var16_34 = var10_14;
                                                            }
                                                            break block166;
                                                        }
                                                        var2_2 = 0;
                                                        var6_10 = 0;
                                                        var29_47 = 0;
                                                        var30_48 = false;
                                                        var31_49 = false;
                                                        var11_15 = false;
                                                        var32_50 = false;
                                                        var7_11 = this.relayoutWindow((WindowManager.LayoutParams)var14_32, var4_8, var12_16);
                                                        var33_51 = var10_14;
                                                        var6_10 = var16_34;
                                                        var34_52 = var31_49;
                                                        var19_37 = var2_2;
                                                        var11_15 = this.mPendingMergedConfiguration.equals(this.mLastReportedMergedConfiguration);
                                                        var8_12 = var24_42;
                                                        if (var11_15) break block167;
                                                        var25_43 = var10_14;
                                                        var19_37 = var26_44;
                                                        var6_10 = var27_45;
                                                        var34_52 = var32_50;
                                                        var33_51 = var29_47;
                                                        var13_17 = this.mPendingMergedConfiguration;
                                                        var25_43 = var10_14;
                                                        var19_37 = var26_44;
                                                        var6_10 = var27_45;
                                                        var34_52 = var32_50;
                                                        var33_51 = var29_47;
                                                        var11_15 = this.mFirst == false;
                                                        var25_43 = var10_14;
                                                        var19_37 = var26_44;
                                                        var6_10 = var27_45;
                                                        var34_52 = var32_50;
                                                        var33_51 = var29_47;
                                                        this.performConfigurationChange((MergedConfiguration)var13_17, var11_15, -1);
                                                        var8_12 = 1;
                                                    }
                                                    var33_51 = var10_14;
                                                    var25_43 = var8_12;
                                                    var6_10 = var16_34;
                                                    var34_52 = var31_49;
                                                    var19_37 = var2_2;
                                                    var26_44 = this.mPendingOverscanInsets.equals(this.mAttachInfo.mOverscanInsets) == false ? 1 : 0;
                                                    var33_51 = var10_14;
                                                    var25_43 = var8_12;
                                                    var6_10 = var16_34;
                                                    var34_52 = var31_49;
                                                    var19_37 = var2_2;
                                                    var16_34 = this.mPendingContentInsets.equals(this.mAttachInfo.mContentInsets) == false ? 1 : 0;
                                                    var33_51 = var10_14;
                                                    var25_43 = var8_12;
                                                    var6_10 = var16_34;
                                                    var34_52 = var31_49;
                                                    var19_37 = var2_2;
                                                    var27_45 = this.mPendingVisibleInsets.equals(this.mAttachInfo.mVisibleInsets) == false ? 1 : 0;
                                                    var33_51 = var10_14;
                                                    var25_43 = var8_12;
                                                    var6_10 = var16_34;
                                                    var34_52 = var31_49;
                                                    var19_37 = var2_2;
                                                    var24_42 = this.mPendingStableInsets.equals(this.mAttachInfo.mStableInsets) == false ? 1 : 0;
                                                    var33_51 = var10_14;
                                                    var25_43 = var8_12;
                                                    var6_10 = var16_34;
                                                    var34_52 = var31_49;
                                                    var19_37 = var2_2;
                                                    var29_47 = this.mPendingDisplayCutout.equals(this.mAttachInfo.mDisplayCutout) == false ? 1 : 0;
                                                    var33_51 = var10_14;
                                                    var25_43 = var8_12;
                                                    var6_10 = var16_34;
                                                    var34_52 = var31_49;
                                                    var19_37 = var2_2;
                                                    var11_15 = this.mPendingOutsets.equals(this.mAttachInfo.mOutsets);
                                                    var2_2 = (var7_11 & 32) != 0 ? 1 : 0;
                                                    var33_51 = var17_35 = var10_14 | var2_2;
                                                    var25_43 = var8_12;
                                                    var6_10 = var16_34;
                                                    var34_52 = var31_49;
                                                    var19_37 = var2_2;
                                                    var35_53 = this.mPendingAlwaysConsumeSystemBars != this.mAttachInfo.mAlwaysConsumeSystemBars;
                                                    var33_51 = var17_35;
                                                    var25_43 = var8_12;
                                                    var6_10 = var16_34;
                                                    var34_52 = var31_49;
                                                    var19_37 = var2_2;
                                                    var36_54 = this.hasColorModeChanged(var3_3.getColorMode());
                                                    if (var16_34 == 0) ** GOTO lbl340
                                                    var25_43 = var17_35;
                                                    var19_37 = var8_12;
                                                    var6_10 = var16_34;
                                                    var34_52 = var32_50;
                                                    var33_51 = var2_2;
                                                    this.mAttachInfo.mContentInsets.set(this.mPendingContentInsets);
lbl340: // 2 sources:
                                                    var6_10 = var16_34;
                                                    if (var26_44 != 0) {
                                                        var25_43 = var17_35;
                                                        var19_37 = var8_12;
                                                        var6_10 = var16_34;
                                                        var34_52 = var32_50;
                                                        var33_51 = var2_2;
                                                        this.mAttachInfo.mOverscanInsets.set(this.mPendingOverscanInsets);
                                                        var6_10 = 1;
                                                    }
                                                    var10_14 = var6_10;
                                                    if (var24_42 != 0) {
                                                        var25_43 = var17_35;
                                                        var19_37 = var8_12;
                                                        var34_52 = var32_50;
                                                        var33_51 = var2_2;
                                                        this.mAttachInfo.mStableInsets.set(this.mPendingStableInsets);
                                                        var10_14 = 1;
                                                    }
                                                    var6_10 = var10_14;
                                                    if (var29_47 != 0) {
                                                        var25_43 = var17_35;
                                                        var19_37 = var8_12;
                                                        var6_10 = var10_14;
                                                        var34_52 = var32_50;
                                                        var33_51 = var2_2;
                                                        this.mAttachInfo.mDisplayCutout.set(this.mPendingDisplayCutout);
                                                        var6_10 = 1;
                                                    }
                                                    var16_34 = var6_10;
                                                    if (var35_53) {
                                                        var25_43 = var17_35;
                                                        var19_37 = var8_12;
                                                        var34_52 = var32_50;
                                                        var33_51 = var2_2;
                                                        this.mAttachInfo.mAlwaysConsumeSystemBars = this.mPendingAlwaysConsumeSystemBars;
                                                        var16_34 = 1;
                                                    }
                                                    if (var16_34 == 0) {
                                                        var25_43 = var17_35;
                                                        var19_37 = var8_12;
                                                        var6_10 = var16_34;
                                                        var34_52 = var32_50;
                                                        var33_51 = var2_2;
                                                        if (this.mLastSystemUiVisibility == this.mAttachInfo.mSystemUiVisibility) {
                                                            var25_43 = var17_35;
                                                            var19_37 = var8_12;
                                                            var6_10 = var16_34;
                                                            var34_52 = var32_50;
                                                            var33_51 = var2_2;
                                                            if (!this.mApplyInsetsRequested) {
                                                                var25_43 = var17_35;
                                                                var19_37 = var8_12;
                                                                var6_10 = var16_34;
                                                                var34_52 = var32_50;
                                                                var33_51 = var2_2;
                                                                var37_55 = this.mLastOverscanRequested;
                                                                var25_43 = var17_35;
                                                                var19_37 = var8_12;
                                                                var6_10 = var16_34;
                                                                var34_52 = var32_50;
                                                                var33_51 = var2_2;
                                                                var38_56 = this.mAttachInfo.mOverscanRequested;
                                                                if (var37_55 == var38_56) {
                                                                    var10_14 = var16_34;
                                                                    if (!(var11_15 ^ true)) break block168;
                                                                }
                                                            }
                                                        }
                                                    }
                                                    var33_51 = var17_35;
                                                    var25_43 = var8_12;
                                                    var6_10 = var16_34;
                                                    var34_52 = var31_49;
                                                    var19_37 = var2_2;
                                                    this.mLastSystemUiVisibility = this.mAttachInfo.mSystemUiVisibility;
                                                    var33_51 = var17_35;
                                                    var25_43 = var8_12;
                                                    var6_10 = var16_34;
                                                    var34_52 = var31_49;
                                                    var19_37 = var2_2;
                                                    this.mLastOverscanRequested = this.mAttachInfo.mOverscanRequested;
                                                    var33_51 = var17_35;
                                                    var25_43 = var8_12;
                                                    var6_10 = var16_34;
                                                    var34_52 = var31_49;
                                                    var19_37 = var2_2;
                                                    this.mAttachInfo.mOutsets.set(this.mPendingOutsets);
                                                    var33_51 = var17_35;
                                                    var25_43 = var8_12;
                                                    var6_10 = var16_34;
                                                    var34_52 = var31_49;
                                                    var19_37 = var2_2;
                                                    this.mApplyInsetsRequested = false;
                                                    var33_51 = var17_35;
                                                    var25_43 = var8_12;
                                                    var6_10 = var16_34;
                                                    var34_52 = var31_49;
                                                    var19_37 = var2_2;
                                                    this.dispatchApplyInsets(var1_1);
                                                    var10_14 = 1;
                                                }
                                                if (var27_45 == 0) ** GOTO lbl443
                                                var25_43 = var17_35;
                                                var19_37 = var8_12;
                                                var6_10 = var10_14;
                                                var34_52 = var32_50;
                                                var33_51 = var2_2;
                                                this.mAttachInfo.mVisibleInsets.set(this.mPendingVisibleInsets);
lbl443: // 2 sources:
                                                if (var36_54) {
                                                    var25_43 = var17_35;
                                                    var19_37 = var8_12;
                                                    var6_10 = var10_14;
                                                    var34_52 = var32_50;
                                                    var33_51 = var2_2;
                                                    if (this.mAttachInfo.mThreadedRenderer != null) {
                                                        var25_43 = var17_35;
                                                        var19_37 = var8_12;
                                                        var6_10 = var10_14;
                                                        var34_52 = var32_50;
                                                        var33_51 = var2_2;
                                                        var13_17 = this.mAttachInfo.mThreadedRenderer;
                                                        var25_43 = var17_35;
                                                        var19_37 = var8_12;
                                                        var6_10 = var10_14;
                                                        var34_52 = var32_50;
                                                        var33_51 = var2_2;
                                                        var11_15 = var3_3.getColorMode() == 1;
                                                        var25_43 = var17_35;
                                                        var19_37 = var8_12;
                                                        var6_10 = var10_14;
                                                        var34_52 = var32_50;
                                                        var33_51 = var2_2;
                                                        var13_17.setWideGamut(var11_15);
                                                    }
                                                }
                                                if (var28_46) break block169;
                                                var25_43 = var17_35;
                                                var19_37 = var8_12;
                                                var6_10 = var10_14;
                                                var34_52 = var32_50;
                                                var33_51 = var2_2;
                                                var11_15 = var30_48;
                                                if (!this.mSurface.isValid()) break block170;
                                                var25_43 = var17_35;
                                                var19_37 = var8_12;
                                                var6_10 = var10_14;
                                                var34_52 = var32_50;
                                                var33_51 = var2_2;
                                                this.mFullRedrawNeeded = true;
                                                var25_43 = var17_35;
                                                var19_37 = var8_12;
                                                var6_10 = var10_14;
                                                var34_52 = var32_50;
                                                var33_51 = var2_2;
                                                this.mPreviousTransparentRegion.setEmpty();
                                                var25_43 = var17_35;
                                                var19_37 = var8_12;
                                                var6_10 = var10_14;
                                                var34_52 = var32_50;
                                                var33_51 = var2_2;
                                                var13_17 = this.mAttachInfo.mThreadedRenderer;
                                                var11_15 = var30_48;
                                                if (var13_17 == null) break block170;
                                                var25_43 = var17_35;
                                                var19_37 = var8_12;
                                                var6_10 = var10_14;
                                                var34_52 = var32_50;
                                                var33_51 = var2_2;
                                                var11_15 = this.mAttachInfo.mThreadedRenderer.initialize(this.mSurface);
                                                if (!var11_15) break block170;
                                                var34_52 = var11_15;
                                                try {
                                                    if ((var1_1.mPrivateFlags & 512) == 0) {
                                                        var34_52 = var11_15;
                                                        this.mAttachInfo.mThreadedRenderer.allocateBuffers();
                                                    }
                                                    break block170;
                                                }
                                                catch (Surface.OutOfResourcesException var13_22) {
                                                    break block171;
                                                }
                                                catch (Surface.OutOfResourcesException var13_23) {
                                                    var11_15 = false;
                                                }
                                            }
                                            var34_52 = var11_15;
                                            this.handleOutOfResourcesException((Surface.OutOfResourcesException)var13_24);
                                            return;
                                            catch (RemoteException var13_25) {
                                                var11_15 = var34_52;
                                                var6_10 = var7_11;
                                                var16_34 = var17_35;
                                                var17_35 = var8_12;
                                                var8_12 = var10_14;
                                                var7_11 = var2_2;
                                                break block166;
                                            }
                                        }
                                        var33_51 = var17_35;
                                        var25_43 = var8_12;
                                        var6_10 = var10_14;
                                        var34_52 = var31_49;
                                        var19_37 = var2_2;
                                        var11_15 = this.mSurface.isValid();
                                        if (var11_15) break block172;
                                        var25_43 = var17_35;
                                        var19_37 = var8_12;
                                        var6_10 = var10_14;
                                        var34_52 = var32_50;
                                        var33_51 = var2_2;
                                        if (this.mLastScrolledFocus != null) {
                                            var25_43 = var17_35;
                                            var19_37 = var8_12;
                                            var6_10 = var10_14;
                                            var34_52 = var32_50;
                                            var33_51 = var2_2;
                                            this.mLastScrolledFocus.clear();
                                        }
                                        var25_43 = var17_35;
                                        var19_37 = var8_12;
                                        var6_10 = var10_14;
                                        var34_52 = var32_50;
                                        var33_51 = var2_2;
                                        this.mCurScrollY = 0;
                                        var25_43 = var17_35;
                                        var19_37 = var8_12;
                                        var6_10 = var10_14;
                                        var34_52 = var32_50;
                                        var33_51 = var2_2;
                                        this.mScrollY = 0;
                                        var25_43 = var17_35;
                                        var19_37 = var8_12;
                                        var6_10 = var10_14;
                                        var34_52 = var32_50;
                                        var33_51 = var2_2;
                                        if (this.mView instanceof RootViewSurfaceTaker) {
                                            var25_43 = var17_35;
                                            var19_37 = var8_12;
                                            var6_10 = var10_14;
                                            var34_52 = var32_50;
                                            var33_51 = var2_2;
                                            ((RootViewSurfaceTaker)this.mView).onRootViewScrollYChanged(this.mCurScrollY);
                                        }
                                        var25_43 = var17_35;
                                        var19_37 = var8_12;
                                        var6_10 = var10_14;
                                        var34_52 = var32_50;
                                        var33_51 = var2_2;
                                        if (this.mScroller != null) {
                                            var25_43 = var17_35;
                                            var19_37 = var8_12;
                                            var6_10 = var10_14;
                                            var34_52 = var32_50;
                                            var33_51 = var2_2;
                                            this.mScroller.abortAnimation();
                                        }
                                        var25_43 = var17_35;
                                        var19_37 = var8_12;
                                        var6_10 = var10_14;
                                        var34_52 = var32_50;
                                        var33_51 = var2_2;
                                        var11_15 = var30_48;
                                        if (this.mAttachInfo.mThreadedRenderer != null) {
                                            var25_43 = var17_35;
                                            var19_37 = var8_12;
                                            var6_10 = var10_14;
                                            var34_52 = var32_50;
                                            var33_51 = var2_2;
                                            var11_15 = var30_48;
                                            if (this.mAttachInfo.mThreadedRenderer.isEnabled()) {
                                                var25_43 = var17_35;
                                                var19_37 = var8_12;
                                                var6_10 = var10_14;
                                                var34_52 = var32_50;
                                                var33_51 = var2_2;
                                                this.mAttachInfo.mThreadedRenderer.destroy();
                                                var11_15 = var30_48;
                                            }
                                        }
                                        break block170;
                                    }
                                    var33_51 = var17_35;
                                    var25_43 = var8_12;
                                    var6_10 = var10_14;
                                    var34_52 = var31_49;
                                    var19_37 = var2_2;
                                    if (var21_39 == this.mSurface.getGenerationId() && var2_2 == 0 && !var23_41) {
                                        var11_15 = var30_48;
                                        if (!var36_54) break block170;
                                    }
                                    var33_51 = var17_35;
                                    var25_43 = var8_12;
                                    var6_10 = var10_14;
                                    var34_52 = var31_49;
                                    var19_37 = var2_2;
                                    var13_17 = this.mSurfaceHolder;
                                    var11_15 = var30_48;
                                    if (var13_17 != null) break block170;
                                    var25_43 = var17_35;
                                    var19_37 = var8_12;
                                    var6_10 = var10_14;
                                    var34_52 = var32_50;
                                    var33_51 = var2_2;
                                    var11_15 = var30_48;
                                    if (this.mAttachInfo.mThreadedRenderer == null) break block170;
                                    var25_43 = var17_35;
                                    var19_37 = var8_12;
                                    var6_10 = var10_14;
                                    var34_52 = var32_50;
                                    var33_51 = var2_2;
                                    this.mFullRedrawNeeded = true;
                                    var25_43 = var17_35;
                                    var19_37 = var8_12;
                                    var6_10 = var10_14;
                                    var34_52 = var32_50;
                                    var33_51 = var2_2;
                                    try {
                                        this.mAttachInfo.mThreadedRenderer.updateSurface(this.mSurface);
                                        var11_15 = var30_48;
                                    }
                                    catch (Surface.OutOfResourcesException var13_26) {
                                        var25_43 = var17_35;
                                        var19_37 = var8_12;
                                        var6_10 = var10_14;
                                        var34_52 = var32_50;
                                        var33_51 = var2_2;
                                        this.handleOutOfResourcesException(var13_26);
                                        return;
                                    }
                                }
                                var16_34 = (var7_11 & 16) != 0 ? 1 : 0;
                                var6_10 = (var7_11 & 8) != 0 ? 1 : 0;
                                var32_50 = var16_34 != 0 || var6_10 != 0;
                                var33_51 = var17_35;
                                var25_43 = var8_12;
                                var6_10 = var10_14;
                                var34_52 = var11_15;
                                var19_37 = var2_2;
                                if (this.mDragResizing == var32_50) ** GOTO lbl752
                                if (!var32_50) ** GOTO lbl751
                                var16_34 = var16_34 != 0 ? 0 : 1;
                                var33_51 = var17_35;
                                var25_43 = var8_12;
                                var6_10 = var10_14;
                                var34_52 = var11_15;
                                var19_37 = var2_2;
                                this.mResizeMode = var16_34;
                                var33_51 = var17_35;
                                var25_43 = var8_12;
                                var6_10 = var10_14;
                                var34_52 = var11_15;
                                var19_37 = var2_2;
                                var27_45 = this.mWinFrame.width();
                                var33_51 = var17_35;
                                var25_43 = var8_12;
                                var6_10 = var10_14;
                                var34_52 = var11_15;
                                var19_37 = var2_2;
                                var16_34 = this.mPendingBackDropFrame.width();
                                if (var27_45 != var16_34) break block173;
                                var25_43 = var17_35;
                                var19_37 = var8_12;
                                var6_10 = var10_14;
                                var34_52 = var11_15;
                                var33_51 = var2_2;
                                try {
                                    var27_45 = this.mWinFrame.height();
                                    var25_43 = var17_35;
                                    var19_37 = var8_12;
                                    var6_10 = var10_14;
                                    var34_52 = var11_15;
                                    var33_51 = var2_2;
                                    var16_34 = this.mPendingBackDropFrame.height();
                                    if (var27_45 != var16_34) break block173;
                                    var16_34 = 1;
                                    break block174;
                                }
                                catch (RemoteException var13_21) {
                                    var10_14 = var7_11;
                                    var16_34 = var25_43;
                                    var17_35 = var19_37;
                                    var8_12 = var6_10;
                                    var11_15 = var34_52;
                                    var7_11 = var33_51;
                                    var6_10 = var10_14;
                                }
                                break block166;
                            }
                            var16_34 = 0;
                        }
                        var33_51 = var17_35;
                        var25_43 = var8_12;
                        var6_10 = var10_14;
                        var34_52 = var11_15;
                        var19_37 = var2_2;
                        var14_32 = this.mPendingBackDropFrame;
                        var30_48 = var16_34 == 0;
                        var33_51 = var17_35;
                        var25_43 = var8_12;
                        var6_10 = var10_14;
                        var34_52 = var11_15;
                        var19_37 = var2_2;
                        var13_17 = this.mPendingVisibleInsets;
                        var15_33 = this.mPendingStableInsets;
                        {
                            catch (RemoteException var13_28) {
                                var6_10 = var7_11;
                                var16_34 = var17_35;
                                var17_35 = var8_12;
                                var8_12 = var10_14;
                                var7_11 = var2_2;
                                break block166;
                            }
                        }
                        var6_10 = this.mResizeMode;
                        {
                            catch (RemoteException var13_27) {
                                var6_10 = var7_11;
                                var16_34 = var17_35;
                                var17_35 = var8_12;
                                var8_12 = var10_14;
                                var7_11 = var2_2;
                                break block166;
                            }
                        }
                        try {
                            block184 : {
                                this.startDragResizing((Rect)var14_32, var30_48, (Rect)var13_17, (Rect)var15_33, var6_10);
                                break block184;
lbl751: // 1 sources:
                                this.endDragResizing();
                            }
                            if (!this.mUseMTRenderer) {
                                if (var32_50) {
                                    this.mCanvasOffsetX = this.mWinFrame.left;
                                    this.mCanvasOffsetY = this.mWinFrame.top;
                                } else {
                                    this.mCanvasOffsetY = 0;
                                    this.mCanvasOffsetX = 0;
                                }
                            }
                            var16_34 = var8_12;
                            var6_10 = var2_2;
                            var8_12 = var7_11;
                            var7_11 = var16_34;
                            var16_34 = var17_35;
                            break block175;
                        }
                        catch (RemoteException v1) {
                            var6_10 = var7_11;
                            var13_17 = v1;
                            var16_34 = var17_35;
                            var17_35 = var8_12;
                            var8_12 = var10_14;
                            var7_11 = var2_2;
                            break block166;
                        }
                        catch (RemoteException var13_29) {
                            var10_14 = var7_11;
                            var16_34 = var33_51;
                            var17_35 = var25_43;
                            var8_12 = var6_10;
                            var11_15 = var34_52;
                            var7_11 = var19_37;
                            var6_10 = var10_14;
                        }
                        break block166;
                        catch (RemoteException var13_30) {
                            var2_2 = 0;
                            var16_34 = var10_14;
                            var7_11 = var6_10;
                            var6_10 = var2_2;
                        }
                        break block166;
                        catch (RemoteException var13_31) {
                            var11_15 = false;
                            var7_11 = 0;
                            var6_10 = 0;
                            var16_34 = var10_14;
                        }
                    }
                    var2_2 = var6_10;
                    var10_14 = var8_12;
                    var6_10 = var7_11;
                    var7_11 = var17_35;
                    var8_12 = var2_2;
                }
                this.mAttachInfo.mWindowLeft = var9_13.left;
                this.mAttachInfo.mWindowTop = var9_13.top;
                if (this.mWidth != var9_13.width() || this.mHeight != var9_13.height()) {
                    this.mWidth = var9_13.width();
                    this.mHeight = var9_13.height();
                }
                if (this.mSurfaceHolder != null) {
                    if (this.mSurface.isValid()) {
                        this.mSurfaceHolder.mSurface = this.mSurface;
                    }
                    this.mSurfaceHolder.setSurfaceFrameSize(this.mWidth, this.mHeight);
                    this.mSurfaceHolder.mSurfaceLock.unlock();
                    if (this.mSurface.isValid()) {
                        if (!var28_46) {
                            this.mSurfaceHolder.ungetCallbacks();
                            this.mIsCreating = true;
                            var13_17 = this.mSurfaceHolder.getCallbacks();
                            if (var13_17 != null) {
                                var16_34 = ((SurfaceHolder.Callback[])var13_17).length;
                                for (var2_2 = 0; var2_2 < var16_34; ++var2_2) {
                                    var13_17[var2_2].surfaceCreated(this.mSurfaceHolder);
                                }
                            }
                            var16_34 = 1;
                        }
                        if ((var16_34 != 0 || var21_39 != this.mSurface.getGenerationId()) && (var13_17 = this.mSurfaceHolder.getCallbacks()) != null) {
                            var2_2 = ((SurfaceHolder.Callback[])var13_17).length;
                            for (var16_34 = 0; var16_34 < var2_2; ++var16_34) {
                                var13_17[var16_34].surfaceChanged(this.mSurfaceHolder, var3_3.format, this.mWidth, this.mHeight);
                            }
                        }
                        this.mIsCreating = false;
                    } else {
                        var6_10 = var2_2 = var6_10;
                        if (var28_46) {
                            this.notifySurfaceDestroyed();
                            this.mSurfaceHolder.mSurfaceLock.lock();
                            try {
                                var9_13 = this.mSurfaceHolder;
                                var13_17 = new Surface();
                                var9_13.mSurface = var13_17;
                                var6_10 = var2_2;
                            }
                            finally {
                                this.mSurfaceHolder.mSurfaceLock.unlock();
                            }
                        }
                    }
                }
                if ((var9_13 = this.mAttachInfo.mThreadedRenderer) != null && var9_13.isEnabled() && (var11_15 || this.mWidth != var9_13.getWidth() || this.mHeight != var9_13.getHeight() || this.mNeedsRendererSetup)) {
                    var9_13.setup(this.mWidth, this.mHeight, this.mAttachInfo, this.mWindowAttributes.surfaceInsets);
                    this.mNeedsRendererSetup = false;
                }
                if (!(this.mStopped && !this.mReportNextDraw || !this.ensureTouchModeLocally(var11_15 = (var8_12 & 1) != 0) && this.mWidth == var1_1.getMeasuredWidth() && this.mHeight == var1_1.getMeasuredHeight() && var10_14 == 0 && var7_11 == 0)) {
                    var2_2 = ViewRootImpl.getRootMeasureSpec(this.mWidth, var3_3.width);
                    var16_34 = ViewRootImpl.getRootMeasureSpec(this.mHeight, var3_3.height);
                    this.performMeasure(var2_2, var16_34);
                    var18_36 = var1_1.getMeasuredWidth();
                    var17_35 = var1_1.getMeasuredHeight();
                    var10_14 = 0;
                    if (var3_3.horizontalWeight > 0.0f) {
                        var2_2 = View.MeasureSpec.makeMeasureSpec(var18_36 + (int)((float)(this.mWidth - var18_36) * var3_3.horizontalWeight), 1073741824);
                        var10_14 = 1;
                    }
                    if (var3_3.verticalWeight > 0.0f) {
                        var16_34 = View.MeasureSpec.makeMeasureSpec(var17_35 + (int)((float)(this.mHeight - var17_35) * var3_3.verticalWeight), 1073741824);
                        var10_14 = 1;
                    }
                    if (var10_14 != 0) {
                        this.performMeasure(var2_2, var16_34);
                    }
                    var18_36 = 1;
                }
                var11_15 = var12_16;
                var10_14 = var7_11;
                var7_11 = var8_12;
            }
            if (var6_10 != 0) {
                this.updateBoundsSurface();
            }
            var8_12 = (var6_10 = var18_36 != 0 && (this.mStopped == false || this.mReportNextDraw != false) ? 1 : 0) == 0 && !this.mAttachInfo.mRecomputeGlobalAttributes ? 0 : 1;
            if (var6_10 != 0) {
                this.performLayout((WindowManager.LayoutParams)var3_3, this.mWidth, this.mHeight);
                if ((var1_1.mPrivateFlags & 512) != 0) {
                    var1_1.getLocationInWindow(this.mTmpLocation);
                    var3_3 = this.mTransparentRegion;
                    var9_13 = this.mTmpLocation;
                    var3_3.set(var9_13[0], var9_13[1], var9_13[0] + var1_1.mRight - var1_1.mLeft, this.mTmpLocation[1] + var1_1.mBottom - var1_1.mTop);
                    var1_1.gatherTransparentRegion(this.mTransparentRegion);
                    var3_3 = this.mTranslator;
                    if (var3_3 != null) {
                        var3_3.translateRegionInWindowToScreen(this.mTransparentRegion);
                    }
                    if (!this.mTransparentRegion.equals(this.mPreviousTransparentRegion)) {
                        this.mPreviousTransparentRegion.set(this.mTransparentRegion);
                        this.mFullRedrawNeeded = true;
                        try {
                            this.mWindowSession.setTransparentRegion(this.mWindow, this.mTransparentRegion);
                        }
                        catch (RemoteException var3_5) {
                            // empty catch block
                        }
                    }
                }
            }
            if (var8_12 != 0) {
                var3_3 = this.mAttachInfo;
                var3_3.mRecomputeGlobalAttributes = false;
                var3_3.mTreeObserver.dispatchOnGlobalLayout();
            }
            if (var20_38) {
                var14_32 = this.mAttachInfo.mGivenInternalInsets;
                var14_32.reset();
                this.mAttachInfo.mTreeObserver.dispatchOnComputeInternalInsets((ViewTreeObserver.InternalInsetsInfo)var14_32);
                this.mAttachInfo.mHasNonEmptyGivenInternalInsets = var14_32.isEmpty() ^ true;
                if (var11_15 || !this.mLastGivenInsets.equals(var14_32)) {
                    this.mLastGivenInsets.set((ViewTreeObserver.InternalInsetsInfo)var14_32);
                    var3_3 = this.mTranslator;
                    if (var3_3 != null) {
                        var13_17 = var3_3.getTranslatedContentInsets(var14_32.contentInsets);
                        var3_3 = this.mTranslator.getTranslatedVisibleInsets(var14_32.visibleInsets);
                        var9_13 = this.mTranslator.getTranslatedTouchableArea(var14_32.touchableRegion);
                    } else {
                        var13_17 = var14_32.contentInsets;
                        var3_3 = var14_32.visibleInsets;
                        var9_13 = var14_32.touchableRegion;
                    }
                    var39_57 = this.mWindowSession;
                    var15_33 = this.mWindow;
                    try {
                        var39_57.setInsets((IWindow)var15_33, var14_32.mTouchableInsets, (Rect)var13_17, (Rect)var3_3, (Region)var9_13);
                    }
                    catch (RemoteException var3_6) {}
                    break block177;
                    catch (RemoteException var3_7) {
                        // empty catch block
                    }
                }
            }
        }
        if (this.mFirst) {
            if (!ViewRootImpl.sAlwaysAssignFocus && ViewRootImpl.isInTouchMode()) {
                var3_3 = this.mView.findFocus();
                if (var3_3 instanceof ViewGroup && ((ViewGroup)var3_3).getDescendantFocusability() == 262144) {
                    var3_3.restoreDefaultFocus();
                }
            } else {
                var3_3 = this.mView;
                if (var3_3 != null && !var3_3.hasFocus()) {
                    this.mView.restoreDefaultFocus();
                }
            }
        }
        var6_10 = (var5_9 != false || this.mFirst != false) && var22_40 != false ? 1 : 0;
        var11_15 = this.mAttachInfo.mHasWindowFocus != false && var22_40 != false;
        var8_12 = var11_15 != false && this.mLostWindowFocus != false ? 1 : 0;
        if (var8_12 != 0) {
            this.mLostWindowFocus = false;
        } else if (!var11_15 && this.mHadWindowFocus) {
            this.mLostWindowFocus = true;
        }
        if ((var6_10 != 0 || var8_12 != 0) && (var6_10 = (var3_3 = this.mWindowAttributes) == null ? 0 : (var3_3.type == 2005 ? 1 : 0)) == 0) {
            var1_1.sendAccessibilityEvent(32);
        }
        this.mFirst = false;
        this.mWillDrawSoon = false;
        this.mNewSurfaceNeeded = false;
        this.mActivityRelaunched = false;
        this.mViewVisibility = var4_8;
        this.mHadWindowFocus = var11_15;
        if (var11_15 && !this.isInLocalFocusMode() && (var12_16 = WindowManager.LayoutParams.mayUseInputMethod(this.mWindowAttributes.flags)) != this.mLastWasImTarget) {
            this.mLastWasImTarget = var12_16;
            var3_3 = this.mContext.getSystemService(InputMethodManager.class);
            if (var3_3 != null && var12_16) {
                var3_3.onPreWindowFocus(this.mView, var11_15);
                var9_13 = this.mView;
                var3_3.onPostWindowFocus((View)var9_13, var9_13.findFocus(), this.mWindowAttributes.softInputMode, this.mHasHadWindowFocus ^ true, this.mWindowAttributes.flags);
            }
        }
        var8_12 = 1;
        if ((var7_11 & 2) != 0) {
            this.reportNextDraw();
        }
        var6_10 = var8_12;
        if (!this.mAttachInfo.mTreeObserver.dispatchOnPreDraw()) {
            var6_10 = var22_40 == false ? var8_12 : 0;
        }
        if (var6_10 == 0) {
            var3_3 = this.mPendingTransitions;
            if (var3_3 != null && var3_3.size() > 0) {
                for (var6_10 = 0; var6_10 < this.mPendingTransitions.size(); ++var6_10) {
                    this.mPendingTransitions.get(var6_10).startChangingAnimations();
                }
                this.mPendingTransitions.clear();
            }
            this.performDraw();
        } else if (var22_40) {
            this.scheduleTraversals();
        } else {
            var3_3 = this.mPendingTransitions;
            if (var3_3 != null && var3_3.size() > 0) {
                for (var6_10 = 0; var6_10 < this.mPendingTransitions.size(); ++var6_10) {
                    this.mPendingTransitions.get(var6_10).endChangingAnimations();
                }
                this.mPendingTransitions.clear();
            }
        }
        this.mIsInTraversal = false;
    }

    private void postDrawFinished() {
        this.mHandler.sendEmptyMessage(29);
    }

    private void postSendWindowContentChangedCallback(View view, int n) {
        if (this.mSendWindowContentChangedAccessibilityEvent == null) {
            this.mSendWindowContentChangedAccessibilityEvent = new SendWindowContentChangedAccessibilityEvent();
        }
        this.mSendWindowContentChangedAccessibilityEvent.runOrPost(view, n);
    }

    private void profileRendering(boolean bl) {
        if (this.mProfileRendering) {
            this.mRenderProfilingEnabled = bl;
            Choreographer.FrameCallback frameCallback = this.mRenderProfiler;
            if (frameCallback != null) {
                this.mChoreographer.removeFrameCallback(frameCallback);
            }
            if (this.mRenderProfilingEnabled) {
                if (this.mRenderProfiler == null) {
                    this.mRenderProfiler = new Choreographer.FrameCallback(){

                        @Override
                        public void doFrame(long l) {
                            ViewRootImpl.this.mDirty.set(0, 0, ViewRootImpl.this.mWidth, ViewRootImpl.this.mHeight);
                            ViewRootImpl.this.scheduleTraversals();
                            if (ViewRootImpl.this.mRenderProfilingEnabled) {
                                ViewRootImpl.this.mChoreographer.postFrameCallback(ViewRootImpl.this.mRenderProfiler);
                            }
                        }
                    };
                }
                this.mChoreographer.postFrameCallback(this.mRenderProfiler);
            } else {
                this.mRenderProfiler = null;
            }
        }
    }

    private void recycleQueuedInputEvent(QueuedInputEvent queuedInputEvent) {
        queuedInputEvent.mEvent = null;
        queuedInputEvent.mReceiver = null;
        int n = this.mQueuedInputEventPoolSize;
        if (n < 10) {
            this.mQueuedInputEventPoolSize = n + 1;
            queuedInputEvent.mNext = this.mQueuedInputEventPool;
            this.mQueuedInputEventPool = queuedInputEvent;
        }
    }

    private int relayoutWindow(WindowManager.LayoutParams object, int n, boolean bl) throws RemoteException {
        boolean bl2;
        float f = this.mAttachInfo.mApplicationScale;
        if (object != null && this.mTranslator != null) {
            ((WindowManager.LayoutParams)object).backup();
            this.mTranslator.translateWindowLayout((WindowManager.LayoutParams)object);
            bl2 = true;
        } else {
            bl2 = false;
        }
        if (object != null && this.mOrigWindowType != ((WindowManager.LayoutParams)object).type && this.mTargetSdkVersion < 14) {
            String string2 = this.mTag;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Window type can not be changed after the window is added; ignoring change of ");
            stringBuilder.append(this.mView);
            Slog.w(string2, stringBuilder.toString());
            ((WindowManager.LayoutParams)object).type = this.mOrigWindowType;
        }
        long l = this.mSurface.isValid() ? this.mSurface.getNextFrameNumber() : -1L;
        n = this.mWindowSession.relayout(this.mWindow, this.mSeq, (WindowManager.LayoutParams)object, (int)((float)this.mView.getMeasuredWidth() * f + 0.5f), (int)((float)this.mView.getMeasuredHeight() * f + 0.5f), n, (int)bl, l, this.mTmpFrame, this.mPendingOverscanInsets, this.mPendingContentInsets, this.mPendingVisibleInsets, this.mPendingStableInsets, this.mPendingOutsets, this.mPendingBackDropFrame, this.mPendingDisplayCutout, this.mPendingMergedConfiguration, this.mSurfaceControl, this.mTempInsets);
        if (this.mSurfaceControl.isValid()) {
            this.mSurface.copyFrom(this.mSurfaceControl);
        } else {
            this.destroySurface();
        }
        boolean bl3 = (n & 64) != 0;
        this.mPendingAlwaysConsumeSystemBars = bl3;
        if (bl2) {
            ((WindowManager.LayoutParams)object).restore();
        }
        if ((object = this.mTranslator) != null) {
            ((CompatibilityInfo.Translator)object).translateRectInScreenToAppWinFrame(this.mTmpFrame);
            this.mTranslator.translateRectInScreenToAppWindow(this.mPendingOverscanInsets);
            this.mTranslator.translateRectInScreenToAppWindow(this.mPendingContentInsets);
            this.mTranslator.translateRectInScreenToAppWindow(this.mPendingVisibleInsets);
            this.mTranslator.translateRectInScreenToAppWindow(this.mPendingStableInsets);
        }
        this.setFrame(this.mTmpFrame);
        this.mInsetsController.onStateChanged(this.mTempInsets);
        return n;
    }

    private void removeSendWindowContentChangedCallback() {
        SendWindowContentChangedAccessibilityEvent sendWindowContentChangedAccessibilityEvent = this.mSendWindowContentChangedAccessibilityEvent;
        if (sendWindowContentChangedAccessibilityEvent != null) {
            this.mHandler.removeCallbacks(sendWindowContentChangedAccessibilityEvent);
        }
    }

    private void reportDrawFinished() {
        try {
            this.mDrawsNeededToReport = 0;
            this.mWindowSession.finishDrawing(this.mWindow);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    private void reportNextDraw() {
        if (!this.mReportNextDraw) {
            this.drawPending();
        }
        this.mReportNextDraw = true;
    }

    private void requestDrawWindow() {
        if (!this.mUseMTRenderer) {
            return;
        }
        this.mWindowDrawCountDown = new CountDownLatch(this.mWindowCallbacks.size());
        for (int i = this.mWindowCallbacks.size() - 1; i >= 0; --i) {
            this.mWindowCallbacks.get(i).onRequestDraw(this.mReportNextDraw);
        }
    }

    private void resetPointerIcon(MotionEvent motionEvent) {
        this.mPointerIconType = 1;
        this.updatePointerIcon(motionEvent);
    }

    private void scheduleProcessInputEvents() {
        if (!this.mProcessInputEventsScheduled) {
            this.mProcessInputEventsScheduled = true;
            Message message = this.mHandler.obtainMessage(19);
            message.setAsynchronous(true);
            this.mHandler.sendMessage(message);
        }
    }

    private void setBoundsSurfaceCrop() {
        this.mTempBoundsRect.set(this.mWinFrame);
        this.mTempBoundsRect.offsetTo(this.mWindowAttributes.surfaceInsets.left, this.mWindowAttributes.surfaceInsets.top);
        this.mTransaction.setWindowCrop(this.mBoundsSurfaceControl, this.mTempBoundsRect);
    }

    private void setFrame(Rect rect) {
        this.mWinFrame.set(rect);
        this.mInsetsController.onFrameChanged(rect);
    }

    private void setTag() {
        String[] arrstring = this.mWindowAttributes.getTitle().toString().split("\\.");
        if (arrstring.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ViewRootImpl[");
            stringBuilder.append(arrstring[arrstring.length - 1]);
            stringBuilder.append("]");
            this.mTag = stringBuilder.toString();
        }
    }

    private static boolean shouldUseDisplaySize(WindowManager.LayoutParams layoutParams) {
        boolean bl = layoutParams.type == 2014 || layoutParams.type == 2011 || layoutParams.type == 2020;
        return bl;
    }

    private void startDragResizing(Rect rect, boolean bl, Rect rect2, Rect rect3, int n) {
        if (!this.mDragResizing) {
            this.mDragResizing = true;
            if (this.mUseMTRenderer) {
                for (int i = this.mWindowCallbacks.size() - 1; i >= 0; --i) {
                    this.mWindowCallbacks.get(i).onWindowDragResizeStart(rect, bl, rect2, rect3, n);
                }
            }
            this.mFullRedrawNeeded = true;
        }
    }

    private void trackFPS() {
        long l = System.currentTimeMillis();
        if (this.mFpsStartTime < 0L) {
            this.mFpsPrevTime = l;
            this.mFpsStartTime = l;
            this.mFpsNumFrames = 0;
        } else {
            ++this.mFpsNumFrames;
            String string2 = Integer.toHexString(System.identityHashCode(this));
            long l2 = this.mFpsPrevTime;
            long l3 = l - this.mFpsStartTime;
            String string3 = this.mTag;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0x");
            stringBuilder.append(string2);
            stringBuilder.append("\tFrame time:\t");
            stringBuilder.append(l - l2);
            Log.v(string3, stringBuilder.toString());
            this.mFpsPrevTime = l;
            if (l3 > 1000L) {
                float f = (float)this.mFpsNumFrames * 1000.0f / (float)l3;
                string3 = this.mTag;
                stringBuilder = new StringBuilder();
                stringBuilder.append("0x");
                stringBuilder.append(string2);
                stringBuilder.append("\tFPS:\t");
                stringBuilder.append(f);
                Log.v(string3, stringBuilder.toString());
                this.mFpsStartTime = l;
                this.mFpsNumFrames = 0;
            }
        }
    }

    private void updateBoundsSurface() {
        if (this.mBoundsSurfaceControl != null && this.mSurface.isValid()) {
            this.setBoundsSurfaceCrop();
            SurfaceControl.Transaction transaction = this.mTransaction;
            SurfaceControl surfaceControl = this.mBoundsSurfaceControl;
            Surface surface = this.mSurface;
            transaction.deferTransactionUntilSurface(surfaceControl, surface, surface.getNextFrameNumber()).apply();
        }
    }

    private boolean updateContentDrawBounds() {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = this.mUseMTRenderer;
        boolean bl4 = true;
        if (bl3) {
            int n = this.mWindowCallbacks.size() - 1;
            do {
                bl = bl2;
                if (n < 0) break;
                bl2 |= this.mWindowCallbacks.get(n).onContentDrawn(this.mWindowAttributes.surfaceInsets.left, this.mWindowAttributes.surfaceInsets.top, this.mWidth, this.mHeight);
                --n;
            } while (true);
        }
        bl2 = this.mDragResizing && this.mReportNextDraw ? bl4 : false;
        return bl | bl2;
    }

    private void updateForceDarkMode() {
        if (this.mAttachInfo.mThreadedRenderer == null) {
            return;
        }
        int n = this.getNightMode();
        boolean bl = true;
        boolean bl2 = n == 32;
        boolean bl3 = bl2;
        if (bl2) {
            bl2 = SystemProperties.getBoolean("debug.hwui.force_dark", false);
            TypedArray typedArray = this.mContext.obtainStyledAttributes(R.styleable.Theme);
            bl2 = typedArray.getBoolean(279, true) && typedArray.getBoolean(278, bl2) ? bl : false;
            typedArray.recycle();
            bl3 = bl2;
        }
        if (this.mAttachInfo.mThreadedRenderer.setForceDark(bl3)) {
            this.invalidateWorld(this.mView);
        }
    }

    private void updateInternalDisplay(int n, Resources resources) {
        Object object = ResourcesManager.getInstance().getAdjustedDisplay(n, resources);
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot get desired display with Id: ");
            ((StringBuilder)object).append(n);
            Slog.w(TAG, ((StringBuilder)object).toString());
            this.mDisplay = ResourcesManager.getInstance().getAdjustedDisplay(0, resources);
        } else {
            this.mDisplay = object;
        }
        this.mContext.updateDisplay(this.mDisplay.getDisplayId());
    }

    private boolean updatePointerIcon(MotionEvent parcelable) {
        float f = ((MotionEvent)parcelable).getX(0);
        float f2 = ((MotionEvent)parcelable).getY(0);
        View view = this.mView;
        if (view == null) {
            Slog.d(this.mTag, "updatePointerIcon called after view was removed");
            return false;
        }
        if (!(f < 0.0f || f >= (float)view.getWidth() || f2 < 0.0f || f2 >= (float)this.mView.getHeight())) {
            int n = (parcelable = this.mView.onResolvePointerIcon((MotionEvent)parcelable, 0)) != null ? ((PointerIcon)parcelable).getType() : 1000;
            if (this.mPointerIconType != n) {
                this.mPointerIconType = n;
                this.mCustomPointerIcon = null;
                if (this.mPointerIconType != -1) {
                    InputManager.getInstance().setPointerIconType(n);
                    return true;
                }
            }
            if (this.mPointerIconType == -1 && !((PointerIcon)parcelable).equals(this.mCustomPointerIcon)) {
                this.mCustomPointerIcon = parcelable;
                InputManager.getInstance().setCustomPointerIcon(this.mCustomPointerIcon);
            }
            return true;
        }
        Slog.d(this.mTag, "updatePointerIcon called with position out of bounds");
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addWindowCallbacks(WindowCallbacks windowCallbacks) {
        ArrayList<WindowCallbacks> arrayList = this.mWindowCallbacks;
        synchronized (arrayList) {
            this.mWindowCallbacks.add(windowCallbacks);
            return;
        }
    }

    void addWindowStoppedCallback(WindowStoppedCallback windowStoppedCallback) {
        this.mWindowStoppedCallbacks.add(windowStoppedCallback);
    }

    @Override
    public void bringChildToFront(View view) {
    }

    @Override
    public boolean canResolveLayoutDirection() {
        return true;
    }

    @Override
    public boolean canResolveTextAlignment() {
        return true;
    }

    @Override
    public boolean canResolveTextDirection() {
        return true;
    }

    @UnsupportedAppUsage
    public void cancelInvalidate(View view) {
        this.mHandler.removeMessages(1, view);
        this.mHandler.removeMessages(2, view);
        this.mInvalidateOnAnimationRunnable.removeView(view);
    }

    void changeCanvasOpacity(boolean bl) {
        String string2 = this.mTag;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("changeCanvasOpacity: opaque=");
        stringBuilder.append(bl);
        Log.d(string2, stringBuilder.toString());
        boolean bl2 = (this.mView.mPrivateFlags & 512) == 0;
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.setOpaque(bl & bl2);
        }
    }

    void checkThread() {
        if (this.mThread == Thread.currentThread()) {
            return;
        }
        throw new CalledFromWrongThreadException("Only the original thread that created a view hierarchy can touch its views.");
    }

    @Override
    public void childDrawableStateChanged(View view) {
    }

    @Override
    public void childHasTransientStateChanged(View view, boolean bl) {
    }

    @Override
    public void clearChildFocus(View view) {
        this.checkThread();
        this.scheduleTraversals();
    }

    public void createBoundsSurface(int n) {
        if (this.mSurfaceSession == null) {
            this.mSurfaceSession = new SurfaceSession();
        }
        if (this.mBoundsSurfaceControl != null && this.mBoundsSurface.isValid()) {
            return;
        }
        SurfaceControl.Builder builder = new SurfaceControl.Builder(this.mSurfaceSession);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bounds for - ");
        stringBuilder.append(this.getTitle().toString());
        this.mBoundsSurfaceControl = builder.setName(stringBuilder.toString()).setParent(this.mSurfaceControl).build();
        this.setBoundsSurfaceCrop();
        this.mTransaction.setLayer(this.mBoundsSurfaceControl, n).show(this.mBoundsSurfaceControl).apply();
        this.mBoundsSurface.copyFrom(this.mBoundsSurfaceControl);
    }

    @Override
    public void createContextMenu(ContextMenu contextMenu) {
    }

    public void debug() {
        this.mView.debug();
    }

    void destroyHardwareResources() {
        ThreadedRenderer threadedRenderer = this.mAttachInfo.mThreadedRenderer;
        if (threadedRenderer != null) {
            if (Looper.myLooper() != this.mAttachInfo.mHandler.getLooper()) {
                this.mAttachInfo.mHandler.postAtFrontOfQueue(new _$$Lambda$dj1hfDQd0iEp_uBDBPEUMMYJJwk(this));
                return;
            }
            threadedRenderer.destroyHardwareResources(this.mView);
            threadedRenderer.destroy();
        }
    }

    @UnsupportedAppUsage
    public void detachFunctor(long l) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.stopDrawing();
        }
    }

    boolean die(boolean bl) {
        if (bl && !this.mIsInTraversal) {
            this.doDie();
            return false;
        }
        if (!this.mIsDrawing) {
            this.destroyHardwareRenderer();
        } else {
            String string2 = this.mTag;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Attempting to destroy the window while drawing!\n  window=");
            stringBuilder.append(this);
            stringBuilder.append(", title=");
            stringBuilder.append((Object)this.mWindowAttributes.getTitle());
            Log.e(string2, stringBuilder.toString());
        }
        this.mHandler.sendEmptyMessage(3);
        return true;
    }

    public void dispatchAppVisibility(boolean bl) {
        Message message = this.mHandler.obtainMessage(8);
        message.arg1 = bl ? 1 : 0;
        this.mHandler.sendMessage(message);
    }

    void dispatchApplyInsets(View view) {
        Trace.traceBegin(8L, "dispatchApplyInsets");
        boolean bl = true;
        WindowInsets windowInsets = this.getWindowInsets(true);
        if (this.mWindowAttributes.layoutInDisplayCutoutMode != 1) {
            bl = false;
        }
        WindowInsets windowInsets2 = windowInsets;
        if (!bl) {
            windowInsets2 = windowInsets.consumeDisplayCutout();
        }
        view.dispatchApplyWindowInsets(windowInsets2);
        Trace.traceEnd(8L);
    }

    public void dispatchCheckFocus() {
        if (!this.mHandler.hasMessages(13)) {
            this.mHandler.sendEmptyMessage(13);
        }
    }

    public void dispatchCloseSystemDialogs(String string2) {
        Message message = Message.obtain();
        message.what = 14;
        message.obj = string2;
        this.mHandler.sendMessage(message);
    }

    void dispatchDetachedFromWindow() {
        this.mFirstInputStage.onDetachedFromWindow();
        Object object = this.mView;
        if (object != null && ((View)object).mAttachInfo != null) {
            this.mAttachInfo.mTreeObserver.dispatchOnWindowAttachedChange(false);
            this.mView.dispatchDetachedFromWindow();
        }
        this.mAccessibilityInteractionConnectionManager.ensureNoConnection();
        this.mAccessibilityManager.removeAccessibilityStateChangeListener(this.mAccessibilityInteractionConnectionManager);
        this.mAccessibilityManager.removeHighTextContrastStateChangeListener(this.mHighContrastTextManager);
        this.removeSendWindowContentChangedCallback();
        this.destroyHardwareRenderer();
        this.setAccessibilityFocus(null, null);
        this.mView.assignParent(null);
        this.mView = null;
        this.mAttachInfo.mRootView = null;
        this.destroySurface();
        InputQueue.Callback callback = this.mInputQueueCallback;
        if (callback != null && (object = this.mInputQueue) != null) {
            callback.onInputQueueDestroyed((InputQueue)object);
            this.mInputQueue.dispose();
            this.mInputQueueCallback = null;
            this.mInputQueue = null;
        }
        if ((object = this.mInputEventReceiver) != null) {
            ((WindowInputEventReceiver)object).dispose();
            this.mInputEventReceiver = null;
        }
        try {
            this.mWindowSession.remove(this.mWindow);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        object = this.mInputChannel;
        if (object != null) {
            ((InputChannel)object).dispose();
            this.mInputChannel = null;
        }
        this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
        this.unscheduleTraversals();
    }

    public void dispatchDragEvent(DragEvent parcelable) {
        int n;
        if (parcelable.getAction() == 2) {
            n = 16;
            this.mHandler.removeMessages(16);
        } else {
            n = 15;
        }
        parcelable = this.mHandler.obtainMessage(n, parcelable);
        this.mHandler.sendMessage((Message)parcelable);
    }

    public void dispatchGetNewSurface() {
        Message message = this.mHandler.obtainMessage(9);
        this.mHandler.sendMessage(message);
    }

    @UnsupportedAppUsage
    public void dispatchInputEvent(InputEvent inputEvent) {
        this.dispatchInputEvent(inputEvent, null);
    }

    @UnsupportedAppUsage
    public void dispatchInputEvent(InputEvent parcelable, InputEventReceiver inputEventReceiver) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = parcelable;
        someArgs.arg2 = inputEventReceiver;
        parcelable = this.mHandler.obtainMessage(7, someArgs);
        ((Message)parcelable).setAsynchronous(true);
        this.mHandler.sendMessage((Message)parcelable);
    }

    public void dispatchInvalidateDelayed(View object, long l) {
        object = this.mHandler.obtainMessage(1, object);
        this.mHandler.sendMessageDelayed((Message)object, l);
    }

    public void dispatchInvalidateOnAnimation(View view) {
        this.mInvalidateOnAnimationRunnable.addView(view);
    }

    public void dispatchInvalidateRectDelayed(View.AttachInfo.InvalidateInfo object, long l) {
        object = this.mHandler.obtainMessage(2, object);
        this.mHandler.sendMessageDelayed((Message)object, l);
    }

    public void dispatchInvalidateRectOnAnimation(View.AttachInfo.InvalidateInfo invalidateInfo) {
        this.mInvalidateOnAnimationRunnable.addViewRect(invalidateInfo);
    }

    public void dispatchKeyFromAutofill(KeyEvent parcelable) {
        parcelable = this.mHandler.obtainMessage(12, parcelable);
        ((Message)parcelable).setAsynchronous(true);
        this.mHandler.sendMessage((Message)parcelable);
    }

    @UnsupportedAppUsage
    public void dispatchKeyFromIme(KeyEvent parcelable) {
        parcelable = this.mHandler.obtainMessage(11, parcelable);
        ((Message)parcelable).setAsynchronous(true);
        this.mHandler.sendMessage((Message)parcelable);
    }

    public void dispatchMoved(int n, int n2) {
        Parcelable parcelable;
        int n3 = n;
        int n4 = n2;
        if (this.mTranslator != null) {
            parcelable = new PointF(n, n2);
            this.mTranslator.translatePointInScreenToAppWindow((PointF)parcelable);
            n3 = (int)((double)((PointF)parcelable).x + 0.5);
            n4 = (int)((double)((PointF)parcelable).y + 0.5);
        }
        parcelable = this.mHandler.obtainMessage(23, n3, n4);
        this.mHandler.sendMessage((Message)parcelable);
    }

    public void dispatchPointerCaptureChanged(boolean bl) {
        this.mHandler.removeMessages(28);
        Message message = this.mHandler.obtainMessage(28);
        message.arg1 = bl ? 1 : 0;
        this.mHandler.sendMessage(message);
    }

    public void dispatchRequestKeyboardShortcuts(IResultReceiver iResultReceiver, int n) {
        this.mHandler.obtainMessage(26, n, 0, iResultReceiver).sendToTarget();
    }

    public void dispatchSystemUiVisibilityChanged(int n, int n2, int n3, int n4) {
        SystemUiVisibilityInfo systemUiVisibilityInfo = new SystemUiVisibilityInfo();
        systemUiVisibilityInfo.seq = n;
        systemUiVisibilityInfo.globalVisibility = n2;
        systemUiVisibilityInfo.localValue = n3;
        systemUiVisibilityInfo.localChanges = n4;
        ViewRootHandler viewRootHandler = this.mHandler;
        viewRootHandler.sendMessage(viewRootHandler.obtainMessage(17, systemUiVisibilityInfo));
    }

    @UnsupportedAppUsage
    public void dispatchUnhandledInputEvent(InputEvent inputEvent) {
        InputEvent inputEvent2 = inputEvent;
        if (inputEvent instanceof MotionEvent) {
            inputEvent2 = MotionEvent.obtain((MotionEvent)inputEvent);
        }
        this.synthesizeInputEvent(inputEvent2);
    }

    public boolean dispatchUnhandledKeyEvent(KeyEvent keyEvent) {
        return this.mUnhandledKeyManager.dispatch(this.mView, keyEvent);
    }

    public void dispatchWindowShown() {
        this.mHandler.sendEmptyMessage(25);
    }

    void doConsumeBatchedInput(long l) {
        if (this.mConsumeBatchedInputScheduled) {
            this.mConsumeBatchedInputScheduled = false;
            WindowInputEventReceiver windowInputEventReceiver = this.mInputEventReceiver;
            if (windowInputEventReceiver != null && windowInputEventReceiver.consumeBatchedInputEvents(l) && l != -1L) {
                this.scheduleConsumeBatchedInput();
            }
            this.doProcessInputEvents();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void doDie() {
        this.checkThread();
        synchronized (this) {
            if (this.mRemoved) {
                return;
            }
            boolean bl = true;
            this.mRemoved = true;
            if (this.mAdded) {
                this.dispatchDetachedFromWindow();
            }
            if (this.mAdded && !this.mFirst) {
                this.destroyHardwareRenderer();
                if (this.mView != null) {
                    int n = this.mView.getVisibility();
                    if (this.mViewVisibility == n) {
                        bl = false;
                    }
                    boolean bl2 = this.mWindowAttributesChanged;
                    if (bl2 || bl) {
                        try {
                            if ((this.relayoutWindow(this.mWindowAttributes, n, false) & 2) != 0) {
                                this.mWindowSession.finishDrawing(this.mWindow);
                            }
                        }
                        catch (RemoteException remoteException) {
                            // empty catch block
                        }
                    }
                    this.destroySurface();
                }
            }
            this.mAdded = false;
        }
        WindowManagerGlobal.getInstance().doRemoveView(this);
    }

    void doProcessInputEvents() {
        while (this.mPendingInputEventHead != null) {
            long l;
            long l2;
            QueuedInputEvent queuedInputEvent = this.mPendingInputEventHead;
            this.mPendingInputEventHead = queuedInputEvent.mNext;
            if (this.mPendingInputEventHead == null) {
                this.mPendingInputEventTail = null;
            }
            queuedInputEvent.mNext = null;
            --this.mPendingInputEventCount;
            Trace.traceCounter(4L, this.mPendingInputEventQueueLengthCounterName, this.mPendingInputEventCount);
            long l3 = l2 = (l = queuedInputEvent.mEvent.getEventTimeNano());
            if (queuedInputEvent.mEvent instanceof MotionEvent) {
                MotionEvent motionEvent = (MotionEvent)queuedInputEvent.mEvent;
                l3 = l2;
                if (motionEvent.getHistorySize() > 0) {
                    l3 = motionEvent.getHistoricalEventTimeNano(0);
                }
            }
            this.mChoreographer.mFrameInfo.updateInputEventTime(l, l3);
            this.deliverInputEvent(queuedInputEvent);
        }
        if (this.mProcessInputEventsScheduled) {
            this.mProcessInputEventsScheduled = false;
            this.mHandler.removeMessages(19);
        }
    }

    void doTraversal() {
        if (this.mTraversalScheduled) {
            this.mTraversalScheduled = false;
            this.mHandler.getLooper().getQueue().removeSyncBarrier(this.mTraversalBarrier);
            if (this.mProfile) {
                Debug.startMethodTracing("ViewAncestor");
            }
            this.performTraversals();
            if (this.mProfile) {
                Debug.stopMethodTracing();
                this.mProfile = false;
            }
        }
    }

    void drawPending() {
        ++this.mDrawsNeededToReport;
    }

    public void dump(String string2, FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("  ");
        object = ((StringBuilder)object).toString();
        printWriter.print(string2);
        printWriter.println("ViewRoot:");
        printWriter.print((String)object);
        printWriter.print("mAdded=");
        printWriter.print(this.mAdded);
        printWriter.print(" mRemoved=");
        printWriter.println(this.mRemoved);
        printWriter.print((String)object);
        printWriter.print("mConsumeBatchedInputScheduled=");
        printWriter.println(this.mConsumeBatchedInputScheduled);
        printWriter.print((String)object);
        printWriter.print("mConsumeBatchedInputImmediatelyScheduled=");
        printWriter.println(this.mConsumeBatchedInputImmediatelyScheduled);
        printWriter.print((String)object);
        printWriter.print("mPendingInputEventCount=");
        printWriter.println(this.mPendingInputEventCount);
        printWriter.print((String)object);
        printWriter.print("mProcessInputEventsScheduled=");
        printWriter.println(this.mProcessInputEventsScheduled);
        printWriter.print((String)object);
        printWriter.print("mTraversalScheduled=");
        printWriter.print(this.mTraversalScheduled);
        printWriter.print((String)object);
        printWriter.print("mIsAmbientMode=");
        printWriter.print(this.mIsAmbientMode);
        if (this.mTraversalScheduled) {
            printWriter.print(" (barrier=");
            printWriter.print(this.mTraversalBarrier);
            printWriter.println(")");
        } else {
            printWriter.println();
        }
        this.mFirstInputStage.dump((String)object, printWriter);
        this.mChoreographer.dump(string2, printWriter);
        this.mInsetsController.dump(string2, printWriter);
        printWriter.print(string2);
        printWriter.println("View Hierarchy:");
        this.dumpViewHierarchy((String)object, printWriter, this.mView);
    }

    public void dumpGfxInfo(int[] arrn) {
        arrn[1] = 0;
        arrn[0] = 0;
        View view = this.mView;
        if (view != null) {
            ViewRootImpl.getGfxInfo(view, arrn);
        }
    }

    @UnsupportedAppUsage
    void enqueueInputEvent(InputEvent inputEvent) {
        this.enqueueInputEvent(inputEvent, null, 0, false);
    }

    @UnsupportedAppUsage
    void enqueueInputEvent(InputEvent object, InputEventReceiver object2, int n, boolean bl) {
        object = this.obtainQueuedInputEvent((InputEvent)object, (InputEventReceiver)object2, n);
        object2 = this.mPendingInputEventTail;
        if (object2 == null) {
            this.mPendingInputEventHead = object;
            this.mPendingInputEventTail = object;
        } else {
            ((QueuedInputEvent)object2).mNext = object;
            this.mPendingInputEventTail = object;
        }
        ++this.mPendingInputEventCount;
        Trace.traceCounter(4L, this.mPendingInputEventQueueLengthCounterName, this.mPendingInputEventCount);
        if (bl) {
            this.doProcessInputEvents();
        } else {
            this.scheduleProcessInputEvents();
        }
    }

    @UnsupportedAppUsage
    boolean ensureTouchMode(boolean bl) {
        if (this.mAttachInfo.mInTouchMode == bl) {
            return false;
        }
        try {
            this.mWindowSession.setInTouchMode(bl);
            return this.ensureTouchModeLocally(bl);
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    @Override
    public View focusSearch(View view, int n) {
        this.checkThread();
        if (!(this.mView instanceof ViewGroup)) {
            return null;
        }
        return FocusFinder.getInstance().findNextFocus((ViewGroup)this.mView, view, n);
    }

    @Override
    public void focusableViewAvailable(View view) {
        this.checkThread();
        View view2 = this.mView;
        if (view2 != null) {
            if (!view2.hasFocus()) {
                if (sAlwaysAssignFocus || !this.mAttachInfo.mInTouchMode) {
                    view.requestFocus();
                }
            } else {
                view2 = this.mView.findFocus();
                if (view2 instanceof ViewGroup && ((ViewGroup)view2).getDescendantFocusability() == 262144 && ViewRootImpl.isViewDescendantOf(view, view2)) {
                    view.requestFocus();
                }
            }
        }
    }

    @UnsupportedAppUsage
    public View getAccessibilityFocusedHost() {
        return this.mAccessibilityFocusedHost;
    }

    @UnsupportedAppUsage
    public AccessibilityNodeInfo getAccessibilityFocusedVirtualView() {
        return this.mAccessibilityFocusedVirtualView;
    }

    public AccessibilityInteractionController getAccessibilityInteractionController() {
        if (this.mView != null) {
            if (this.mAccessibilityInteractionController == null) {
                this.mAccessibilityInteractionController = new AccessibilityInteractionController(this);
            }
            return this.mAccessibilityInteractionController;
        }
        throw new IllegalStateException("getAccessibilityInteractionController called when there is no mView");
    }

    @Override
    public boolean getChildVisibleRect(View view, Rect rect, Point point) {
        if (view == this.mView) {
            return rect.intersect(0, 0, this.mWidth, this.mHeight);
        }
        throw new RuntimeException("child is not mine, honest!");
    }

    public int getDisplayId() {
        return this.mDisplay.getDisplayId();
    }

    public int getHeight() {
        return this.mHeight;
    }

    int getHostVisibility() {
        int n = !this.mAppVisible && !this.mForceDecorViewVisibility ? 8 : this.mView.getVisibility();
        return n;
    }

    InsetsController getInsetsController() {
        return this.mInsetsController;
    }

    @UnsupportedAppUsage
    public void getLastTouchPoint(Point point) {
        point.x = (int)this.mLastTouchPoint.x;
        point.y = (int)this.mLastTouchPoint.y;
    }

    public int getLastTouchSource() {
        return this.mLastTouchSource;
    }

    @Override
    public int getLayoutDirection() {
        return 0;
    }

    final WindowLeaked getLocation() {
        return this.mLocation;
    }

    @Override
    public ViewParent getParent() {
        return null;
    }

    @Override
    public ViewParent getParentForAccessibility() {
        return null;
    }

    public List<Rect> getRootSystemGestureExclusionRects() {
        return this.mGestureExclusionTracker.getRootSystemGestureExclusionRects();
    }

    public SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    @Override
    public int getTextAlignment() {
        return 1;
    }

    @Override
    public int getTextDirection() {
        return 1;
    }

    public CharSequence getTitle() {
        return this.mWindowAttributes.getTitle();
    }

    @UnsupportedAppUsage
    public View getView() {
        return this.mView;
    }

    public int getWidth() {
        return this.mWidth;
    }

    @UnsupportedAppUsage
    public int getWindowFlags() {
        return this.mWindowAttributes.flags;
    }

    WindowInsets getWindowInsets(boolean bl) {
        block4 : {
            Rect rect;
            Rect rect2;
            DisplayCutout displayCutout;
            Rect rect3;
            block6 : {
                Rect rect4;
                block5 : {
                    if (this.mLastWindowInsets != null && !bl) break block4;
                    this.mDispatchContentInsets.set(this.mAttachInfo.mContentInsets);
                    this.mDispatchStableInsets.set(this.mAttachInfo.mStableInsets);
                    this.mDispatchDisplayCutout = this.mAttachInfo.mDisplayCutout.get();
                    rect2 = this.mDispatchContentInsets;
                    rect = this.mDispatchStableInsets;
                    displayCutout = this.mDispatchDisplayCutout;
                    if (!(bl || this.mPendingContentInsets.equals(rect2) && this.mPendingStableInsets.equals(rect) && this.mPendingDisplayCutout.get().equals(displayCutout))) {
                        rect2 = this.mPendingContentInsets;
                        rect = this.mPendingStableInsets;
                        displayCutout = this.mPendingDisplayCutout.get();
                    }
                    rect4 = this.mAttachInfo.mOutsets;
                    if (rect4.left > 0 || rect4.top > 0 || rect4.right > 0) break block5;
                    rect3 = rect2;
                    if (rect4.bottom <= 0) break block6;
                }
                rect3 = new Rect(rect2.left + rect4.left, rect2.top + rect4.top, rect2.right + rect4.right, rect2.bottom + rect4.bottom);
            }
            rect2 = this.ensureInsetsNonNegative(rect3, "content");
            rect = this.ensureInsetsNonNegative(rect, "stable");
            this.mLastWindowInsets = this.mInsetsController.calculateInsets(this.mContext.getResources().getConfiguration().isScreenRound(), this.mAttachInfo.mAlwaysConsumeSystemBars, displayCutout, rect2, rect, this.mWindowAttributes.softInputMode);
        }
        return this.mLastWindowInsets;
    }

    void handleAppVisibility(boolean bl) {
        if (this.mAppVisible != bl) {
            this.mAppVisible = bl;
            this.mAppVisibilityChanged = true;
            this.scheduleTraversals();
            if (!this.mAppVisible) {
                WindowManagerGlobal.trimForeground();
            }
        }
    }

    public void handleDispatchSystemUiVisibilityChanged(SystemUiVisibilityInfo systemUiVisibilityInfo) {
        int n;
        if (this.mSeq != systemUiVisibilityInfo.seq) {
            this.mSeq = systemUiVisibilityInfo.seq;
            this.mAttachInfo.mForceReportNewAttributes = true;
            this.scheduleTraversals();
        }
        if (this.mView == null) {
            return;
        }
        if (systemUiVisibilityInfo.localChanges != 0) {
            this.mView.updateLocalSystemUiVisibility(systemUiVisibilityInfo.localValue, systemUiVisibilityInfo.localChanges);
        }
        if ((n = systemUiVisibilityInfo.globalVisibility & 7) != this.mAttachInfo.mGlobalSystemUiVisibility) {
            this.mAttachInfo.mGlobalSystemUiVisibility = n;
            this.mView.dispatchSystemUiVisibilityChanged(n);
        }
    }

    public void handleDispatchWindowShown() {
        this.mAttachInfo.mTreeObserver.dispatchOnWindowShown();
    }

    void handleGetNewSurface() {
        this.mNewSurfaceNeeded = true;
        this.mFullRedrawNeeded = true;
        this.scheduleTraversals();
    }

    public void handleRequestKeyboardShortcuts(IResultReceiver iResultReceiver, int n) {
        Bundle bundle = new Bundle();
        ArrayList<KeyboardShortcutGroup> arrayList = new ArrayList<KeyboardShortcutGroup>();
        View view = this.mView;
        if (view != null) {
            view.requestKeyboardShortcuts(arrayList, n);
        }
        bundle.putParcelableArrayList("shortcuts_array", arrayList);
        try {
            iResultReceiver.send(0, bundle);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    boolean hasPointerCapture() {
        return this.mPointerCapture;
    }

    @UnsupportedAppUsage
    void invalidate() {
        this.mDirty.set(0, 0, this.mWidth, this.mHeight);
        if (!this.mWillDrawSoon) {
            this.scheduleTraversals();
        }
    }

    @Override
    public void invalidateChild(View view, Rect rect) {
        this.invalidateChildInParent(null, rect);
    }

    @Override
    public ViewParent invalidateChildInParent(int[] object, Rect rect) {
        block9 : {
            block8 : {
                this.checkThread();
                if (rect == null) {
                    this.invalidate();
                    return null;
                }
                if (rect.isEmpty() && !this.mIsAnimating) {
                    return null;
                }
                if (this.mCurScrollY != 0) break block8;
                object = rect;
                if (this.mTranslator == null) break block9;
            }
            this.mTempRect.set(rect);
            rect = this.mTempRect;
            int n = this.mCurScrollY;
            if (n != 0) {
                rect.offset(0, -n);
            }
            if ((object = this.mTranslator) != null) {
                object.translateRectInAppWindowToScreen(rect);
            }
            object = rect;
            if (this.mAttachInfo.mScalingRequired) {
                rect.inset(-1, -1);
                object = rect;
            }
        }
        this.invalidateRectOnScreen((Rect)object);
        return null;
    }

    void invalidateWorld(View view) {
        view.invalidate();
        if (view instanceof ViewGroup) {
            view = (ViewGroup)view;
            for (int i = 0; i < ((ViewGroup)view).getChildCount(); ++i) {
                this.invalidateWorld(((ViewGroup)view).getChildAt(i));
            }
        }
    }

    boolean isInLayout() {
        return this.mInLayout;
    }

    @Override
    public boolean isLayoutDirectionResolved() {
        return true;
    }

    @Override
    public boolean isLayoutRequested() {
        return this.mLayoutRequested;
    }

    @Override
    public boolean isTextAlignmentResolved() {
        return true;
    }

    @Override
    public boolean isTextDirectionResolved() {
        return true;
    }

    @Override
    public View keyboardNavigationClusterSearch(View view, int n) {
        this.checkThread();
        return FocusFinder.getInstance().findNextKeyboardNavigationCluster(this.mView, view, n);
    }

    public /* synthetic */ void lambda$performDraw$1$ViewRootImpl(ArrayList arrayList) {
        this.pendingDrawFinished();
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); ++i) {
                ((Runnable)arrayList.get(i)).run();
            }
        }
    }

    public /* synthetic */ void lambda$performDraw$2$ViewRootImpl(Handler handler, ArrayList arrayList, long l) {
        handler.postAtFrontOfQueue(new _$$Lambda$ViewRootImpl$7A_3tkr_Kw4TZAeIUGVlOoTcZhg(this, arrayList));
    }

    public void loadSystemProperties() {
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                boolean bl;
                ViewRootImpl.this.mProfileRendering = SystemProperties.getBoolean(ViewRootImpl.PROPERTY_PROFILE_RENDERING, false);
                ViewRootImpl viewRootImpl = ViewRootImpl.this;
                viewRootImpl.profileRendering(viewRootImpl.mAttachInfo.mHasWindowFocus);
                if (ViewRootImpl.this.mAttachInfo.mThreadedRenderer != null && ViewRootImpl.this.mAttachInfo.mThreadedRenderer.loadSystemProperties()) {
                    ViewRootImpl.this.invalidate();
                }
                if ((bl = DisplayProperties.debug_layout().orElse(false).booleanValue()) != ViewRootImpl.this.mAttachInfo.mDebugLayout) {
                    ViewRootImpl.this.mAttachInfo.mDebugLayout = bl;
                    if (!ViewRootImpl.this.mHandler.hasMessages(22)) {
                        ViewRootImpl.this.mHandler.sendEmptyMessageDelayed(22, 200L);
                    }
                }
            }
        });
    }

    public void notifyChildRebuilt() {
        if (this.mView instanceof RootViewSurfaceTaker) {
            Object object = this.mSurfaceHolderCallback;
            if (object != null) {
                this.mSurfaceHolder.removeCallback((SurfaceHolder.Callback)object);
            }
            this.mSurfaceHolderCallback = ((RootViewSurfaceTaker)((Object)this.mView)).willYouTakeTheSurface();
            if (this.mSurfaceHolderCallback != null) {
                this.mSurfaceHolder = new TakenSurfaceHolder();
                this.mSurfaceHolder.setFormat(0);
                this.mSurfaceHolder.addCallback(this.mSurfaceHolderCallback);
            } else {
                this.mSurfaceHolder = null;
            }
            this.mInputQueueCallback = ((RootViewSurfaceTaker)((Object)this.mView)).willYouTakeTheInputQueue();
            object = this.mInputQueueCallback;
            if (object != null) {
                object.onInputQueueCreated(this.mInputQueue);
            }
        }
    }

    void notifyInsetsChanged() {
        if (sNewInsetsMode == 0) {
            return;
        }
        this.mApplyInsetsRequested = true;
        if (!this.mIsInTraversal) {
            this.scheduleTraversals();
        }
    }

    void notifyRendererOfFramePending() {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.notifyFramePending();
        }
    }

    @Override
    public void notifySubtreeAccessibilityStateChanged(View view, View view2, int n) {
        this.postSendWindowContentChangedCallback(Preconditions.checkNotNull(view2), n);
    }

    @Override
    public void onDescendantInvalidated(View view, View view2) {
        if ((view2.mPrivateFlags & 64) != 0) {
            this.mIsAnimating = true;
        }
        this.invalidate();
    }

    public void onMovedToDisplay(int n, Configuration configuration) {
        if (this.mDisplay.getDisplayId() == n) {
            return;
        }
        this.updateInternalDisplay(n, this.mView.getResources());
        this.mAttachInfo.mDisplayState = this.mDisplay.getState();
        this.mView.dispatchMovedToDisplay(this.mDisplay, configuration);
    }

    @Override
    public boolean onNestedFling(View view, float f, float f2, boolean bl) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View view, float f, float f2) {
        return false;
    }

    @Override
    public boolean onNestedPrePerformAccessibilityAction(View view, int n, Bundle bundle) {
        return false;
    }

    @Override
    public void onNestedPreScroll(View view, int n, int n2, int[] arrn) {
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4) {
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n) {
    }

    @Override
    public void onPostDraw(RecordingCanvas recordingCanvas) {
        this.drawAccessibilityFocusedDrawableIfNeeded(recordingCanvas);
        if (this.mUseMTRenderer) {
            for (int i = this.mWindowCallbacks.size() - 1; i >= 0; --i) {
                this.mWindowCallbacks.get(i).onPostDraw(recordingCanvas);
            }
        }
    }

    @Override
    public void onPreDraw(RecordingCanvas recordingCanvas) {
        if (this.mCurScrollY != 0 && this.mHardwareYOffset != 0 && this.mAttachInfo.mThreadedRenderer.isOpaque()) {
            recordingCanvas.drawColor(-16777216);
        }
        recordingCanvas.translate(-this.mHardwareXOffset, -this.mHardwareYOffset);
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n) {
        return false;
    }

    @Override
    public void onStopNestedScroll(View view) {
    }

    public void onWindowTitleChanged() {
        this.mAttachInfo.mForceReportNewAttributes = true;
    }

    void outputDisplayList(View view) {
        view.mRenderNode.output();
    }

    void pendingDrawFinished() {
        int n = this.mDrawsNeededToReport;
        if (n != 0) {
            this.mDrawsNeededToReport = n - 1;
            if (this.mDrawsNeededToReport == 0) {
                this.reportDrawFinished();
            }
            return;
        }
        throw new RuntimeException("Unbalanced drawPending/pendingDrawFinished calls");
    }

    @Override
    public boolean performHapticFeedback(int n, boolean bl) {
        try {
            bl = this.mWindowSession.performHapticFeedback(n, bl);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void playSoundEffect(int n) {
        this.checkThread();
        try {
            Object object = this.getAudioManager();
            if (n == 0) {
                ((AudioManager)object).playSoundEffect(0);
                return;
            }
            if (n == 1) {
                ((AudioManager)object).playSoundEffect(3);
                return;
            }
            if (n == 2) {
                ((AudioManager)object).playSoundEffect(1);
                return;
            }
            if (n == 3) {
                ((AudioManager)object).playSoundEffect(4);
                return;
            }
            if (n == 4) {
                ((AudioManager)object).playSoundEffect(2);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("unknown effect id ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" not defined in ");
            ((StringBuilder)object).append(SoundEffectConstants.class.getCanonicalName());
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(((StringBuilder)object).toString());
            throw illegalArgumentException;
        }
        catch (IllegalStateException illegalStateException) {
            String string2 = this.mTag;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FATAL EXCEPTION when attempting to play sound effect: ");
            stringBuilder.append(illegalStateException);
            Log.e(string2, stringBuilder.toString());
            illegalStateException.printStackTrace();
            return;
        }
    }

    void pokeDrawLockIfNeeded() {
        int n = this.mAttachInfo.mDisplayState;
        if (this.mView != null && this.mAdded && this.mTraversalScheduled && (n == 3 || n == 4)) {
            try {
                this.mWindowSession.pokeDrawLock(this.mWindow);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    public void profile() {
        this.mProfile = true;
    }

    @Override
    public void recomputeViewAttributes(View view) {
        this.checkThread();
        if (this.mView == view) {
            this.mAttachInfo.mRecomputeGlobalAttributes = true;
            if (!this.mWillDrawSoon) {
                this.scheduleTraversals();
            }
        }
    }

    public void registerAnimatingRenderNode(RenderNode renderNode) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.registerAnimatingRenderNode(renderNode);
        } else {
            if (this.mAttachInfo.mPendingAnimatingRenderNodes == null) {
                this.mAttachInfo.mPendingAnimatingRenderNodes = new ArrayList<RenderNode>();
            }
            this.mAttachInfo.mPendingAnimatingRenderNodes.add(renderNode);
        }
    }

    public void registerRtFrameCallback(HardwareRenderer.FrameDrawingCallback frameDrawingCallback) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.registerRtFrameCallback(new _$$Lambda$ViewRootImpl$IReiNMSbDakZSGbIZuL_ifaFWn8(frameDrawingCallback));
        }
    }

    public void registerVectorDrawableAnimator(NativeVectorDrawableAnimator nativeVectorDrawableAnimator) {
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.registerVectorDrawableAnimator(nativeVectorDrawableAnimator);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeWindowCallbacks(WindowCallbacks windowCallbacks) {
        ArrayList<WindowCallbacks> arrayList = this.mWindowCallbacks;
        synchronized (arrayList) {
            this.mWindowCallbacks.remove(windowCallbacks);
            return;
        }
    }

    void removeWindowStoppedCallback(WindowStoppedCallback windowStoppedCallback) {
        this.mWindowStoppedCallbacks.remove(windowStoppedCallback);
    }

    public void reportActivityRelaunched() {
        this.mActivityRelaunched = true;
    }

    public void reportDrawFinish() {
        CountDownLatch countDownLatch = this.mWindowDrawCountDown;
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    @Override
    public void requestChildFocus(View view, View view2) {
        this.checkThread();
        this.scheduleTraversals();
    }

    @Override
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        if (rect == null) {
            return this.scrollToRectOrFocus(null, bl);
        }
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        bl = this.scrollToRectOrFocus(rect, bl);
        this.mTempRect.set(rect);
        this.mTempRect.offset(0, -this.mCurScrollY);
        this.mTempRect.offset(this.mAttachInfo.mWindowLeft, this.mAttachInfo.mWindowTop);
        try {
            this.mWindowSession.onRectangleOnScreenRequested(this.mWindow, this.mTempRect);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        return bl;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean bl) {
    }

    @Override
    public void requestFitSystemWindows() {
        this.checkThread();
        this.mApplyInsetsRequested = true;
        this.scheduleTraversals();
    }

    public void requestInvalidateRootRenderNode() {
        this.mInvalidateRootRequested = true;
    }

    @Override
    public void requestLayout() {
        if (!this.mHandlingLayoutInLayoutRequest) {
            this.checkThread();
            this.mLayoutRequested = true;
            this.scheduleTraversals();
        }
    }

    boolean requestLayoutDuringLayout(View view) {
        if (view.mParent != null && view.mAttachInfo != null) {
            if (!this.mLayoutRequesters.contains(view)) {
                this.mLayoutRequesters.add(view);
            }
            return !this.mHandlingLayoutInLayoutRequest;
        }
        return true;
    }

    void requestPointerCapture(boolean bl) {
        if (this.mPointerCapture == bl) {
            return;
        }
        InputManager.getInstance().requestPointerCapture(this.mAttachInfo.mWindowToken, bl);
    }

    @Override
    public boolean requestSendAccessibilityEvent(View object, AccessibilityEvent accessibilityEvent) {
        if (this.mView != null && !this.mStopped && !this.mPausedForTransition) {
            if (accessibilityEvent.getEventType() != 2048 && (object = this.mSendWindowContentChangedAccessibilityEvent) != null && ((SendWindowContentChangedAccessibilityEvent)object).mSource != null) {
                this.mSendWindowContentChangedAccessibilityEvent.removeCallbacksAndRun();
            }
            int n = accessibilityEvent.getEventType();
            View view = this.getSourceForAccessibilityEvent(accessibilityEvent);
            if (n != 2048) {
                if (n != 32768) {
                    if (n == 65536 && view != null && view.getAccessibilityNodeProvider() != null) {
                        this.setAccessibilityFocus(null, null);
                    }
                } else if (view != null && (object = view.getAccessibilityNodeProvider()) != null) {
                    this.setAccessibilityFocus(view, ((AccessibilityNodeProvider)object).createAccessibilityNodeInfo(AccessibilityNodeInfo.getVirtualDescendantId(accessibilityEvent.getSourceNodeId())));
                }
            } else {
                this.handleWindowContentChangedEvent(accessibilityEvent);
            }
            this.mAccessibilityManager.sendAccessibilityEvent(accessibilityEvent);
            return true;
        }
        return false;
    }

    public void requestTransitionStart(LayoutTransition layoutTransition) {
        ArrayList<LayoutTransition> arrayList = this.mPendingTransitions;
        if (arrayList == null || !arrayList.contains(layoutTransition)) {
            if (this.mPendingTransitions == null) {
                this.mPendingTransitions = new ArrayList();
            }
            this.mPendingTransitions.add(layoutTransition);
        }
    }

    @Override
    public void requestTransparentRegion(View view) {
        this.checkThread();
        View view2 = this.mView;
        if (view2 == view) {
            view2.mPrivateFlags |= 512;
            this.mWindowAttributesChanged = true;
            this.mWindowAttributesChangesFlag = 0;
            this.requestLayout();
        }
    }

    public void requestUpdateConfiguration(Configuration parcelable) {
        parcelable = this.mHandler.obtainMessage(18, parcelable);
        this.mHandler.sendMessage((Message)parcelable);
    }

    void scheduleConsumeBatchedInput() {
        if (!this.mConsumeBatchedInputScheduled) {
            this.mConsumeBatchedInputScheduled = true;
            this.mChoreographer.postCallback(0, this.mConsumedBatchedInputRunnable, null);
        }
    }

    void scheduleConsumeBatchedInputImmediately() {
        if (!this.mConsumeBatchedInputImmediatelyScheduled) {
            this.unscheduleConsumeBatchedInput();
            this.mConsumeBatchedInputImmediatelyScheduled = true;
            this.mHandler.post(this.mConsumeBatchedInputImmediatelyRunnable);
        }
    }

    @UnsupportedAppUsage
    void scheduleTraversals() {
        if (!this.mTraversalScheduled) {
            this.mTraversalScheduled = true;
            this.mTraversalBarrier = this.mHandler.getLooper().getQueue().postSyncBarrier();
            this.mChoreographer.postCallback(3, this.mTraversalRunnable, null);
            if (!this.mUnbufferedInputDispatch) {
                this.scheduleConsumeBatchedInput();
            }
            this.notifyRendererOfFramePending();
            this.pokeDrawLockIfNeeded();
        }
    }

    boolean scrollToRectOrFocus(Rect object, boolean bl) {
        int n;
        boolean bl2;
        int n2;
        block18 : {
            boolean bl3;
            Rect rect;
            WeakReference<View> weakReference;
            block17 : {
                weakReference = this.mAttachInfo.mContentInsets;
                rect = this.mAttachInfo.mVisibleInsets;
                n2 = 0;
                bl3 = false;
                if (rect.left > ((Rect)weakReference).left || rect.top > ((Rect)weakReference).top || rect.right > ((Rect)weakReference).right) break block17;
                bl2 = bl3;
                if (rect.bottom <= ((Rect)weakReference).bottom) break block18;
            }
            n = this.mScrollY;
            View view = this.mView.findFocus();
            if (view == null) {
                return false;
            }
            weakReference = this.mLastScrolledFocus;
            weakReference = weakReference != null ? (View)weakReference.get() : null;
            if (view != weakReference) {
                object = null;
            }
            if (view == weakReference && !this.mScrollMayChange && object == null) {
                n2 = n;
                bl2 = bl3;
            } else {
                this.mLastScrolledFocus = new WeakReference<View>(view);
                this.mScrollMayChange = false;
                n2 = n;
                bl2 = bl3;
                if (view.getGlobalVisibleRect(this.mVisRect, null)) {
                    if (object == null) {
                        view.getFocusedRect(this.mTempRect);
                        object = this.mView;
                        if (object instanceof ViewGroup) {
                            ((ViewGroup)object).offsetDescendantRectToMyCoords(view, this.mTempRect);
                        }
                    } else {
                        this.mTempRect.set((Rect)object);
                    }
                    n2 = n;
                    bl2 = bl3;
                    if (this.mTempRect.intersect(this.mVisRect)) {
                        n2 = this.mTempRect.height() > this.mView.getHeight() - rect.top - rect.bottom ? n : (this.mTempRect.top < rect.top ? this.mTempRect.top - rect.top : (this.mTempRect.bottom > this.mView.getHeight() - rect.bottom ? this.mTempRect.bottom - (this.mView.getHeight() - rect.bottom) : 0));
                        bl2 = true;
                    }
                }
            }
        }
        if (n2 != this.mScrollY) {
            if (!bl) {
                if (this.mScroller == null) {
                    this.mScroller = new Scroller(this.mView.getContext());
                }
                object = this.mScroller;
                n = this.mScrollY;
                ((Scroller)object).startScroll(0, n, 0, n2 - n);
            } else {
                object = this.mScroller;
                if (object != null) {
                    ((Scroller)object).abortAnimation();
                }
            }
            this.mScrollY = n2;
        }
        return bl2;
    }

    void setAccessibilityFocus(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
        Object object;
        if (this.mAccessibilityFocusedVirtualView != null) {
            object = this.mAccessibilityFocusedVirtualView;
            View view2 = this.mAccessibilityFocusedHost;
            this.mAccessibilityFocusedHost = null;
            this.mAccessibilityFocusedVirtualView = null;
            view2.clearAccessibilityFocusNoCallbacks(64);
            AccessibilityNodeProvider accessibilityNodeProvider = view2.getAccessibilityNodeProvider();
            if (accessibilityNodeProvider != null) {
                ((AccessibilityNodeInfo)object).getBoundsInParent(this.mTempRect);
                view2.invalidate(this.mTempRect);
                accessibilityNodeProvider.performAction(AccessibilityNodeInfo.getVirtualDescendantId(((AccessibilityNodeInfo)object).getSourceNodeId()), 128, null);
            }
            ((AccessibilityNodeInfo)object).recycle();
        }
        if ((object = this.mAccessibilityFocusedHost) != null && object != view) {
            ((View)object).clearAccessibilityFocusNoCallbacks(64);
        }
        this.mAccessibilityFocusedHost = view;
        this.mAccessibilityFocusedVirtualView = accessibilityNodeInfo;
        if (this.mAttachInfo.mThreadedRenderer != null) {
            this.mAttachInfo.mThreadedRenderer.invalidateRoot();
        }
    }

    public void setActivityConfigCallback(ActivityConfigCallback activityConfigCallback) {
        this.mActivityConfigCallback = activityConfigCallback;
    }

    public void setDragFocus(View view, DragEvent dragEvent) {
        if (this.mCurrentDragView != view && !View.sCascadedDragDrop) {
            float f = dragEvent.mX;
            float f2 = dragEvent.mY;
            int n = dragEvent.mAction;
            ClipData clipData = dragEvent.mClipData;
            dragEvent.mX = 0.0f;
            dragEvent.mY = 0.0f;
            dragEvent.mClipData = null;
            View view2 = this.mCurrentDragView;
            if (view2 != null) {
                dragEvent.mAction = 6;
                view2.callDragEventHandler(dragEvent);
            }
            if (view != null) {
                dragEvent.mAction = 5;
                view.callDragEventHandler(dragEvent);
            }
            dragEvent.mAction = n;
            dragEvent.mX = f;
            dragEvent.mY = f2;
            dragEvent.mClipData = clipData;
        }
        this.mCurrentDragView = view;
    }

    public void setIsAmbientMode(boolean bl) {
        this.mIsAmbientMode = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void setLayoutParams(WindowManager.LayoutParams layoutParams, boolean bl) {
        synchronized (this) {
            int n = this.mWindowAttributes.surfaceInsets.left;
            int n2 = this.mWindowAttributes.surfaceInsets.top;
            int n3 = this.mWindowAttributes.surfaceInsets.right;
            int n4 = this.mWindowAttributes.surfaceInsets.bottom;
            int n5 = this.mWindowAttributes.softInputMode;
            boolean bl2 = this.mWindowAttributes.hasManualSurfaceInsets;
            this.mClientWindowLayoutFlags = layoutParams.flags;
            int n6 = this.mWindowAttributes.privateFlags;
            layoutParams.systemUiVisibility = this.mWindowAttributes.systemUiVisibility;
            layoutParams.subtreeSystemUiVisibility = this.mWindowAttributes.subtreeSystemUiVisibility;
            this.mWindowAttributesChangesFlag = this.mWindowAttributes.copyFrom(layoutParams);
            if ((this.mWindowAttributesChangesFlag & 524288) != 0) {
                this.mAttachInfo.mRecomputeGlobalAttributes = true;
            }
            if ((this.mWindowAttributesChangesFlag & 1) != 0) {
                this.mAttachInfo.mNeedsUpdateLightCenter = true;
            }
            if (this.mWindowAttributes.packageName == null) {
                this.mWindowAttributes.packageName = this.mBasePackageName;
            }
            WindowManager.LayoutParams layoutParams2 = this.mWindowAttributes;
            layoutParams2.privateFlags |= n6 & 128;
            if (this.mWindowAttributes.preservePreviousSurfaceInsets) {
                this.mWindowAttributes.surfaceInsets.set(n, n2, n3, n4);
                this.mWindowAttributes.hasManualSurfaceInsets = bl2;
            } else if (this.mWindowAttributes.surfaceInsets.left != n || this.mWindowAttributes.surfaceInsets.top != n2 || this.mWindowAttributes.surfaceInsets.right != n3 || this.mWindowAttributes.surfaceInsets.bottom != n4) {
                this.mNeedsRendererSetup = true;
            }
            this.applyKeepScreenOnFlag(this.mWindowAttributes);
            if (bl) {
                this.mSoftInputMode = layoutParams.softInputMode;
                this.requestLayout();
            }
            if ((layoutParams.softInputMode & 240) == 0) {
                this.mWindowAttributes.softInputMode = this.mWindowAttributes.softInputMode & -241 | n5 & 240;
            }
            this.mWindowAttributesChanged = true;
            this.scheduleTraversals();
            return;
        }
    }

    @UnsupportedAppUsage
    void setLocalDragState(Object object) {
        this.mLocalDragState = object;
    }

    public void setPausedForTransition(boolean bl) {
        this.mPausedForTransition = bl;
    }

    public void setReportNextDraw() {
        this.reportNextDraw();
        this.invalidate();
    }

    public void setRootSystemGestureExclusionRects(List<Rect> list) {
        this.mGestureExclusionTracker.setRootSystemGestureExclusionRects(list);
        this.mHandler.sendEmptyMessage(32);
    }

    /*
     * Exception decompiling
     */
    public void setView(View var1_1, WindowManager.LayoutParams var2_12, View var3_13) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [13[CASE]], but top level block is 7[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    void setWindowStopped(boolean bl) {
        this.checkThread();
        if (this.mStopped != bl) {
            this.mStopped = bl;
            ThreadedRenderer threadedRenderer = this.mAttachInfo.mThreadedRenderer;
            if (threadedRenderer != null) {
                threadedRenderer.setStopped(this.mStopped);
            }
            if (!this.mStopped) {
                this.mNewSurfaceNeeded = true;
                this.scheduleTraversals();
            } else if (threadedRenderer != null) {
                threadedRenderer.destroyHardwareResources(this.mView);
            }
            for (int i = 0; i < this.mWindowStoppedCallbacks.size(); ++i) {
                this.mWindowStoppedCallbacks.get(i).windowStopped(bl);
            }
            if (this.mStopped) {
                if (this.mSurfaceHolder != null && this.mSurface.isValid()) {
                    this.notifySurfaceDestroyed();
                }
                this.destroySurface();
            }
        }
    }

    @Override
    public boolean showContextMenuForChild(View view) {
        return false;
    }

    @Override
    public boolean showContextMenuForChild(View view, float f, float f2) {
        return false;
    }

    @Override
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback) {
        return null;
    }

    @Override
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int n) {
        return null;
    }

    public void synthesizeInputEvent(InputEvent parcelable) {
        parcelable = this.mHandler.obtainMessage(24, parcelable);
        ((Message)parcelable).setAsynchronous(true);
        this.mHandler.sendMessage((Message)parcelable);
    }

    void systemGestureExclusionChanged() {
        List<Rect> list = this.mGestureExclusionTracker.computeChangedRects();
        if (list != null && this.mView != null) {
            try {
                this.mWindowSession.reportSystemGestureExclusionChanged(this.mWindow, list);
                this.mAttachInfo.mTreeObserver.dispatchOnSystemGestureExclusionRectsChanged(list);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    void transformMatrixToGlobal(Matrix matrix) {
        matrix.preTranslate(this.mAttachInfo.mWindowLeft, this.mAttachInfo.mWindowTop);
    }

    void transformMatrixToLocal(Matrix matrix) {
        matrix.postTranslate(-this.mAttachInfo.mWindowLeft, -this.mAttachInfo.mWindowTop);
    }

    void unscheduleConsumeBatchedInput() {
        if (this.mConsumeBatchedInputScheduled) {
            this.mConsumeBatchedInputScheduled = false;
            this.mChoreographer.removeCallbacks(0, this.mConsumedBatchedInputRunnable, null);
        }
    }

    void unscheduleTraversals() {
        if (this.mTraversalScheduled) {
            this.mTraversalScheduled = false;
            this.mHandler.getLooper().getQueue().removeSyncBarrier(this.mTraversalBarrier);
            this.mChoreographer.removeCallbacks(3, this.mTraversalRunnable, null);
        }
    }

    public void updateConfiguration(int n) {
        Object object = this.mView;
        if (object == null) {
            return;
        }
        object = ((View)object).getResources();
        Configuration configuration = ((Resources)object).getConfiguration();
        if (n != -1) {
            this.onMovedToDisplay(n, configuration);
        }
        if (this.mForceNextConfigUpdate || this.mLastConfigurationFromResources.diff(configuration) != 0) {
            this.updateInternalDisplay(this.mDisplay.getDisplayId(), (Resources)object);
            n = this.mLastConfigurationFromResources.getLayoutDirection();
            int n2 = configuration.getLayoutDirection();
            this.mLastConfigurationFromResources.setTo(configuration);
            if (n != n2 && this.mViewLayoutDirectionInitial == 2) {
                this.mView.setLayoutDirection(n2);
            }
            this.mView.dispatchConfigurationChanged(configuration);
            this.mForceNextWindowRelayout = true;
            this.requestLayout();
        }
        this.updateForceDarkMode();
    }

    public void updatePointerIcon(float f, float f2) {
        this.mHandler.removeMessages(27);
        Parcelable parcelable = MotionEvent.obtain(0L, SystemClock.uptimeMillis(), 7, f, f2, 0);
        parcelable = this.mHandler.obtainMessage(27, parcelable);
        this.mHandler.sendMessage((Message)parcelable);
    }

    void updateSystemGestureExclusionRectsForView(View view) {
        this.mGestureExclusionTracker.updateRectsForView(view);
        this.mHandler.sendEmptyMessage(32);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void windowFocusChanged(boolean bl, boolean bl2) {
        synchronized (this) {
            this.mWindowFocusChanged = true;
            this.mUpcomingWindowFocus = bl;
            this.mUpcomingInTouchMode = bl2;
        }
        Message message = Message.obtain();
        message.what = 6;
        this.mHandler.sendMessage(message);
    }

    static final class AccessibilityInteractionConnection
    extends IAccessibilityInteractionConnection.Stub {
        private final WeakReference<ViewRootImpl> mViewRootImpl;

        AccessibilityInteractionConnection(ViewRootImpl viewRootImpl) {
            this.mViewRootImpl = new WeakReference<ViewRootImpl>(viewRootImpl);
        }

        @Override
        public void clearAccessibilityFocus() {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().clearAccessibilityFocusClientThread();
            }
        }

        @Override
        public void findAccessibilityNodeInfoByAccessibilityId(long l, Region region, int n, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n2, int n3, long l2, MagnificationSpec magnificationSpec, Bundle bundle) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfoByAccessibilityIdClientThread(l, region, n, iAccessibilityInteractionConnectionCallback, n2, n3, l2, magnificationSpec, bundle);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfosResult(null, n);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
        }

        @Override
        public void findAccessibilityNodeInfosByText(long l, String string2, Region region, int n, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n2, int n3, long l2, MagnificationSpec magnificationSpec) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfosByTextClientThread(l, string2, region, n, iAccessibilityInteractionConnectionCallback, n2, n3, l2, magnificationSpec);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfosResult(null, n);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
        }

        @Override
        public void findAccessibilityNodeInfosByViewId(long l, String string2, Region region, int n, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n2, int n3, long l2, MagnificationSpec magnificationSpec) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findAccessibilityNodeInfosByViewIdClientThread(l, string2, region, n, iAccessibilityInteractionConnectionCallback, n2, n3, l2, magnificationSpec);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfoResult(null, n);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
        }

        @Override
        public void findFocus(long l, int n, Region region, int n2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n3, int n4, long l2, MagnificationSpec magnificationSpec) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().findFocusClientThread(l, n, region, n2, iAccessibilityInteractionConnectionCallback, n3, n4, l2, magnificationSpec);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfoResult(null, n2);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
        }

        @Override
        public void focusSearch(long l, int n, Region region, int n2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n3, int n4, long l2, MagnificationSpec magnificationSpec) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().focusSearchClientThread(l, n, region, n2, iAccessibilityInteractionConnectionCallback, n3, n4, l2, magnificationSpec);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfoResult(null, n2);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
        }

        @Override
        public void notifyOutsideTouch() {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().notifyOutsideTouchClientThread();
            }
        }

        @Override
        public void performAccessibilityAction(long l, int n, Bundle bundle, int n2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n3, int n4, long l2) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewRootImpl.get();
            if (viewRootImpl != null && viewRootImpl.mView != null) {
                viewRootImpl.getAccessibilityInteractionController().performAccessibilityActionClientThread(l, n, bundle, n2, iAccessibilityInteractionConnectionCallback, n3, n4, l2);
            } else {
                try {
                    iAccessibilityInteractionConnectionCallback.setPerformAccessibilityActionResult(false, n2);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
        }
    }

    final class AccessibilityInteractionConnectionManager
    implements AccessibilityManager.AccessibilityStateChangeListener {
        AccessibilityInteractionConnectionManager() {
        }

        public void ensureConnection() {
            boolean bl = ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId != -1;
            if (!bl) {
                ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId = ViewRootImpl.this.mAccessibilityManager.addAccessibilityInteractionConnection(ViewRootImpl.this.mWindow, ViewRootImpl.this.mContext.getPackageName(), new AccessibilityInteractionConnection(ViewRootImpl.this));
            }
        }

        public void ensureNoConnection() {
            boolean bl = ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId != -1;
            if (bl) {
                ViewRootImpl.this.mAttachInfo.mAccessibilityWindowId = -1;
                ViewRootImpl.this.mAccessibilityManager.removeAccessibilityInteractionConnection(ViewRootImpl.this.mWindow);
            }
        }

        @Override
        public void onAccessibilityStateChanged(boolean bl) {
            if (bl) {
                this.ensureConnection();
                if (ViewRootImpl.this.mAttachInfo.mHasWindowFocus && ViewRootImpl.this.mView != null) {
                    ViewRootImpl.this.mView.sendAccessibilityEvent(32);
                    View view = ViewRootImpl.this.mView.findFocus();
                    if (view != null && view != ViewRootImpl.this.mView) {
                        view.sendAccessibilityEvent(8);
                    }
                }
            } else {
                this.ensureNoConnection();
                ViewRootImpl.this.mHandler.obtainMessage(21).sendToTarget();
            }
        }
    }

    public static interface ActivityConfigCallback {
        public void onConfigurationChanged(Configuration var1, int var2);
    }

    abstract class AsyncInputStage
    extends InputStage {
        protected static final int DEFER = 3;
        private QueuedInputEvent mQueueHead;
        private int mQueueLength;
        private QueuedInputEvent mQueueTail;
        private final String mTraceCounter;

        public AsyncInputStage(InputStage inputStage, String string2) {
            super(inputStage);
            this.mTraceCounter = string2;
        }

        private void dequeue(QueuedInputEvent queuedInputEvent, QueuedInputEvent queuedInputEvent2) {
            if (queuedInputEvent2 == null) {
                this.mQueueHead = queuedInputEvent.mNext;
            } else {
                queuedInputEvent2.mNext = queuedInputEvent.mNext;
            }
            if (this.mQueueTail == queuedInputEvent) {
                this.mQueueTail = queuedInputEvent2;
            }
            queuedInputEvent.mNext = null;
            --this.mQueueLength;
            Trace.traceCounter(4L, this.mTraceCounter, this.mQueueLength);
        }

        private void enqueue(QueuedInputEvent queuedInputEvent) {
            QueuedInputEvent queuedInputEvent2 = this.mQueueTail;
            if (queuedInputEvent2 == null) {
                this.mQueueHead = queuedInputEvent;
                this.mQueueTail = queuedInputEvent;
            } else {
                queuedInputEvent2.mNext = queuedInputEvent;
                this.mQueueTail = queuedInputEvent;
            }
            ++this.mQueueLength;
            Trace.traceCounter(4L, this.mTraceCounter, this.mQueueLength);
        }

        @Override
        protected void apply(QueuedInputEvent queuedInputEvent, int n) {
            if (n == 3) {
                this.defer(queuedInputEvent);
            } else {
                super.apply(queuedInputEvent, n);
            }
        }

        protected void defer(QueuedInputEvent queuedInputEvent) {
            queuedInputEvent.mFlags |= 2;
            this.enqueue(queuedInputEvent);
        }

        @Override
        void dump(String string2, PrintWriter printWriter) {
            printWriter.print(string2);
            printWriter.print(this.getClass().getName());
            printWriter.print(": mQueueLength=");
            printWriter.println(this.mQueueLength);
            super.dump(string2, printWriter);
        }

        @Override
        protected void forward(QueuedInputEvent queuedInputEvent) {
            queuedInputEvent.mFlags &= -3;
            QueuedInputEvent queuedInputEvent2 = this.mQueueHead;
            if (queuedInputEvent2 == null) {
                super.forward(queuedInputEvent);
                return;
            }
            int n = queuedInputEvent.mEvent.getDeviceId();
            QueuedInputEvent queuedInputEvent3 = null;
            boolean bl = false;
            while (queuedInputEvent2 != null && queuedInputEvent2 != queuedInputEvent) {
                boolean bl2 = bl;
                if (!bl) {
                    bl2 = bl;
                    if (n == queuedInputEvent2.mEvent.getDeviceId()) {
                        bl2 = true;
                    }
                }
                queuedInputEvent3 = queuedInputEvent2;
                queuedInputEvent2 = queuedInputEvent2.mNext;
                bl = bl2;
            }
            if (bl) {
                if (queuedInputEvent2 == null) {
                    this.enqueue(queuedInputEvent);
                }
                return;
            }
            QueuedInputEvent queuedInputEvent4 = queuedInputEvent2;
            if (queuedInputEvent2 != null) {
                queuedInputEvent4 = queuedInputEvent2.mNext;
                this.dequeue(queuedInputEvent, queuedInputEvent3);
            }
            super.forward(queuedInputEvent);
            queuedInputEvent = queuedInputEvent4;
            while (queuedInputEvent != null) {
                if (n == queuedInputEvent.mEvent.getDeviceId()) {
                    if ((queuedInputEvent.mFlags & 2) != 0) break;
                    queuedInputEvent2 = queuedInputEvent.mNext;
                    this.dequeue(queuedInputEvent, queuedInputEvent3);
                    super.forward(queuedInputEvent);
                    queuedInputEvent = queuedInputEvent2;
                    continue;
                }
                queuedInputEvent3 = queuedInputEvent;
                queuedInputEvent = queuedInputEvent.mNext;
            }
        }
    }

    public static final class CalledFromWrongThreadException
    extends AndroidRuntimeException {
        @UnsupportedAppUsage
        public CalledFromWrongThreadException(String string2) {
            super(string2);
        }
    }

    public static interface ConfigChangedCallback {
        public void onConfigurationChanged(Configuration var1);
    }

    final class ConsumeBatchedInputImmediatelyRunnable
    implements Runnable {
        ConsumeBatchedInputImmediatelyRunnable() {
        }

        @Override
        public void run() {
            ViewRootImpl.this.doConsumeBatchedInput(-1L);
        }
    }

    final class ConsumeBatchedInputRunnable
    implements Runnable {
        ConsumeBatchedInputRunnable() {
        }

        @Override
        public void run() {
            ViewRootImpl viewRootImpl = ViewRootImpl.this;
            viewRootImpl.doConsumeBatchedInput(viewRootImpl.mChoreographer.getFrameTimeNanos());
        }
    }

    final class EarlyPostImeInputStage
    extends InputStage {
        public EarlyPostImeInputStage(InputStage inputStage) {
            super(inputStage);
        }

        private int processKeyEvent(QueuedInputEvent object) {
            object = (KeyEvent)((QueuedInputEvent)object).mEvent;
            if (ViewRootImpl.this.mAttachInfo.mTooltipHost != null) {
                ViewRootImpl.this.mAttachInfo.mTooltipHost.handleTooltipKey((KeyEvent)object);
            }
            if (ViewRootImpl.this.checkForLeavingTouchModeAndConsume((KeyEvent)object)) {
                return 1;
            }
            ViewRootImpl.this.mFallbackEventHandler.preDispatchKeyEvent((KeyEvent)object);
            return 0;
        }

        private int processMotionEvent(QueuedInputEvent queuedInputEvent) {
            MotionEvent motionEvent = (MotionEvent)queuedInputEvent.mEvent;
            if (motionEvent.isFromSource(2)) {
                return this.processPointerEvent(queuedInputEvent);
            }
            int n = motionEvent.getActionMasked();
            if ((n == 0 || n == 8) && motionEvent.isFromSource(8)) {
                ViewRootImpl.this.ensureTouchMode(false);
            }
            return 0;
        }

        private int processPointerEvent(QueuedInputEvent object) {
            int n;
            MotionEvent motionEvent = (MotionEvent)((QueuedInputEvent)object).mEvent;
            if (ViewRootImpl.this.mTranslator != null) {
                ViewRootImpl.this.mTranslator.translateEventInScreenToAppWindow(motionEvent);
            }
            if ((n = motionEvent.getAction()) == 0 || n == 8) {
                ViewRootImpl.this.ensureTouchMode(motionEvent.isFromSource(4098));
            }
            if (n == 0 && (object = ViewRootImpl.this.getAutofillManager()) != null) {
                ((AutofillManager)object).requestHideFillUi();
            }
            if (n == 0 && ViewRootImpl.this.mAttachInfo.mTooltipHost != null) {
                ViewRootImpl.this.mAttachInfo.mTooltipHost.hideTooltip();
            }
            if (ViewRootImpl.this.mCurScrollY != 0) {
                motionEvent.offsetLocation(0.0f, ViewRootImpl.this.mCurScrollY);
            }
            if (motionEvent.isTouchEvent()) {
                ViewRootImpl.this.mLastTouchPoint.x = motionEvent.getRawX();
                ViewRootImpl.this.mLastTouchPoint.y = motionEvent.getRawY();
                ViewRootImpl.this.mLastTouchSource = motionEvent.getSource();
            }
            return 0;
        }

        @Override
        protected int onProcess(QueuedInputEvent queuedInputEvent) {
            if (queuedInputEvent.mEvent instanceof KeyEvent) {
                return this.processKeyEvent(queuedInputEvent);
            }
            if (queuedInputEvent.mEvent instanceof MotionEvent) {
                return this.processMotionEvent(queuedInputEvent);
            }
            return 0;
        }
    }

    final class HighContrastTextManager
    implements AccessibilityManager.HighTextContrastChangeListener {
        HighContrastTextManager() {
            ThreadedRenderer.setHighContrastText(ViewRootImpl.this.mAccessibilityManager.isHighTextContrastEnabled());
        }

        @Override
        public void onHighTextContrastStateChanged(boolean bl) {
            ThreadedRenderer.setHighContrastText(bl);
            ViewRootImpl.this.destroyHardwareResources();
            ViewRootImpl.this.invalidate();
        }
    }

    final class ImeInputStage
    extends AsyncInputStage
    implements InputMethodManager.FinishedInputEventCallback {
        public ImeInputStage(InputStage inputStage, String string2) {
            super(inputStage, string2);
        }

        @Override
        public void onFinishedInputEvent(Object object, boolean bl) {
            object = (QueuedInputEvent)object;
            if (bl) {
                this.finish((QueuedInputEvent)object, true);
                return;
            }
            this.forward((QueuedInputEvent)object);
        }

        @Override
        protected int onProcess(QueuedInputEvent queuedInputEvent) {
            InputMethodManager inputMethodManager;
            if (ViewRootImpl.this.mLastWasImTarget && !ViewRootImpl.this.isInLocalFocusMode() && (inputMethodManager = ViewRootImpl.this.mContext.getSystemService(InputMethodManager.class)) != null) {
                int n = inputMethodManager.dispatchInputEvent(queuedInputEvent.mEvent, queuedInputEvent, this, ViewRootImpl.this.mHandler);
                if (n == 1) {
                    return 1;
                }
                if (n == 0) {
                    return 0;
                }
                return 3;
            }
            return 0;
        }
    }

    abstract class InputStage {
        protected static final int FINISH_HANDLED = 1;
        protected static final int FINISH_NOT_HANDLED = 2;
        protected static final int FORWARD = 0;
        private final InputStage mNext;

        public InputStage(InputStage inputStage) {
            this.mNext = inputStage;
        }

        private boolean isBack(InputEvent inputEvent) {
            boolean bl = inputEvent instanceof KeyEvent;
            boolean bl2 = false;
            if (bl) {
                if (((KeyEvent)inputEvent).getKeyCode() == 4) {
                    bl2 = true;
                }
                return bl2;
            }
            return false;
        }

        protected void apply(QueuedInputEvent object, int n) {
            block5 : {
                block3 : {
                    block4 : {
                        block2 : {
                            if (n != 0) break block2;
                            this.forward((QueuedInputEvent)object);
                            break block3;
                        }
                        if (n != 1) break block4;
                        this.finish((QueuedInputEvent)object, true);
                        break block3;
                    }
                    if (n != 2) break block5;
                    this.finish((QueuedInputEvent)object, false);
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid result: ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public final void deliver(QueuedInputEvent queuedInputEvent) {
            if ((queuedInputEvent.mFlags & 4) != 0) {
                this.forward(queuedInputEvent);
            } else if (this.shouldDropInputEvent(queuedInputEvent)) {
                this.finish(queuedInputEvent, false);
            } else {
                this.apply(queuedInputEvent, this.onProcess(queuedInputEvent));
            }
        }

        void dump(String string2, PrintWriter printWriter) {
            InputStage inputStage = this.mNext;
            if (inputStage != null) {
                inputStage.dump(string2, printWriter);
            }
        }

        protected void finish(QueuedInputEvent queuedInputEvent, boolean bl) {
            queuedInputEvent.mFlags |= 4;
            if (bl) {
                queuedInputEvent.mFlags |= 8;
            }
            this.forward(queuedInputEvent);
        }

        protected void forward(QueuedInputEvent queuedInputEvent) {
            this.onDeliverToNext(queuedInputEvent);
        }

        protected void onDeliverToNext(QueuedInputEvent queuedInputEvent) {
            InputStage inputStage = this.mNext;
            if (inputStage != null) {
                inputStage.deliver(queuedInputEvent);
            } else {
                ViewRootImpl.this.finishInputEvent(queuedInputEvent);
            }
        }

        protected void onDetachedFromWindow() {
            InputStage inputStage = this.mNext;
            if (inputStage != null) {
                inputStage.onDetachedFromWindow();
            }
        }

        protected int onProcess(QueuedInputEvent queuedInputEvent) {
            return 0;
        }

        protected void onWindowFocusChanged(boolean bl) {
            InputStage inputStage = this.mNext;
            if (inputStage != null) {
                inputStage.onWindowFocusChanged(bl);
            }
        }

        protected boolean shouldDropInputEvent(QueuedInputEvent queuedInputEvent) {
            if (ViewRootImpl.this.mView != null && ViewRootImpl.this.mAdded) {
                if (!(!ViewRootImpl.this.mAttachInfo.mHasWindowFocus && !queuedInputEvent.mEvent.isFromSource(2) && !ViewRootImpl.this.isAutofillUiShowing() || ViewRootImpl.this.mStopped || ViewRootImpl.this.mIsAmbientMode && !queuedInputEvent.mEvent.isFromSource(1) || ViewRootImpl.this.mPausedForTransition && !this.isBack(queuedInputEvent.mEvent))) {
                    return false;
                }
                if (ViewRootImpl.isTerminalInputEvent(queuedInputEvent.mEvent)) {
                    queuedInputEvent.mEvent.cancel();
                    String string2 = ViewRootImpl.this.mTag;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Cancelling event due to no window focus: ");
                    stringBuilder.append(queuedInputEvent.mEvent);
                    Slog.w(string2, stringBuilder.toString());
                    return false;
                }
                String string3 = ViewRootImpl.this.mTag;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Dropping event due to no window focus: ");
                stringBuilder.append(queuedInputEvent.mEvent);
                Slog.w(string3, stringBuilder.toString());
                return true;
            }
            String string4 = ViewRootImpl.this.mTag;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Dropping event due to root view being removed: ");
            stringBuilder.append(queuedInputEvent.mEvent);
            Slog.w(string4, stringBuilder.toString());
            return true;
        }
    }

    final class InvalidateOnAnimationRunnable
    implements Runnable {
        private boolean mPosted;
        private View.AttachInfo.InvalidateInfo[] mTempViewRects;
        private View[] mTempViews;
        private final ArrayList<View.AttachInfo.InvalidateInfo> mViewRects = new ArrayList();
        private final ArrayList<View> mViews = new ArrayList();

        InvalidateOnAnimationRunnable() {
        }

        private void postIfNeededLocked() {
            if (!this.mPosted) {
                ViewRootImpl.this.mChoreographer.postCallback(1, this, null);
                this.mPosted = true;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void addView(View view) {
            synchronized (this) {
                this.mViews.add(view);
                this.postIfNeededLocked();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void addViewRect(View.AttachInfo.InvalidateInfo invalidateInfo) {
            synchronized (this) {
                this.mViewRects.add(invalidateInfo);
                this.postIfNeededLocked();
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void removeView(View view) {
            synchronized (this) {
                this.mViews.remove(view);
                int n = this.mViewRects.size();
                do {
                    int n2 = n - 1;
                    if (n <= 0) break;
                    View.AttachInfo.InvalidateInfo invalidateInfo = this.mViewRects.get(n2);
                    if (invalidateInfo.target == view) {
                        this.mViewRects.remove(n2);
                        invalidateInfo.recycle();
                    }
                    n = n2;
                } while (true);
                if (this.mPosted && this.mViews.isEmpty() && this.mViewRects.isEmpty()) {
                    ViewRootImpl.this.mChoreographer.removeCallbacks(1, this, null);
                    this.mPosted = false;
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            int n;
            Object object;
            int n2;
            int n3;
            synchronized (this) {
                ArrayList<Object> arrayList;
                this.mPosted = false;
                n3 = this.mViews.size();
                if (n3 != 0) {
                    arrayList = this.mViews;
                    object = this.mTempViews != null ? this.mTempViews : new View[n3];
                    this.mTempViews = arrayList.toArray((T[])object);
                    this.mViews.clear();
                }
                if ((n2 = this.mViewRects.size()) != 0) {
                    arrayList = this.mViewRects;
                    object = this.mTempViewRects != null ? this.mTempViewRects : new View.AttachInfo.InvalidateInfo[n2];
                    this.mTempViewRects = (View.AttachInfo.InvalidateInfo[])arrayList.toArray((T[])object);
                    this.mViewRects.clear();
                }
            }
            for (n = 0; n < n3; ++n) {
                this.mTempViews[n].invalidate();
                this.mTempViews[n] = null;
            }
            n = 0;
            while (n < n2) {
                object = this.mTempViewRects[n];
                ((View.AttachInfo.InvalidateInfo)object).target.invalidate(((View.AttachInfo.InvalidateInfo)object).left, ((View.AttachInfo.InvalidateInfo)object).top, ((View.AttachInfo.InvalidateInfo)object).right, ((View.AttachInfo.InvalidateInfo)object).bottom);
                ((View.AttachInfo.InvalidateInfo)object).recycle();
                ++n;
            }
            return;
        }
    }

    final class NativePostImeInputStage
    extends AsyncInputStage
    implements InputQueue.FinishedInputEventCallback {
        public NativePostImeInputStage(InputStage inputStage, String string2) {
            super(inputStage, string2);
        }

        @Override
        public void onFinishedInputEvent(Object object, boolean bl) {
            object = (QueuedInputEvent)object;
            if (bl) {
                this.finish((QueuedInputEvent)object, true);
                return;
            }
            this.forward((QueuedInputEvent)object);
        }

        @Override
        protected int onProcess(QueuedInputEvent queuedInputEvent) {
            if (ViewRootImpl.this.mInputQueue != null) {
                ViewRootImpl.this.mInputQueue.sendInputEvent(queuedInputEvent.mEvent, queuedInputEvent, false, this);
                return 3;
            }
            return 0;
        }
    }

    final class NativePreImeInputStage
    extends AsyncInputStage
    implements InputQueue.FinishedInputEventCallback {
        public NativePreImeInputStage(InputStage inputStage, String string2) {
            super(inputStage, string2);
        }

        @Override
        public void onFinishedInputEvent(Object object, boolean bl) {
            object = (QueuedInputEvent)object;
            if (bl) {
                this.finish((QueuedInputEvent)object, true);
                return;
            }
            this.forward((QueuedInputEvent)object);
        }

        @Override
        protected int onProcess(QueuedInputEvent queuedInputEvent) {
            if (ViewRootImpl.this.mInputQueue != null && queuedInputEvent.mEvent instanceof KeyEvent) {
                ViewRootImpl.this.mInputQueue.sendInputEvent(queuedInputEvent.mEvent, queuedInputEvent, true, this);
                return 3;
            }
            return 0;
        }
    }

    private static final class QueuedInputEvent {
        public static final int FLAG_DEFERRED = 2;
        public static final int FLAG_DELIVER_POST_IME = 1;
        public static final int FLAG_FINISHED = 4;
        public static final int FLAG_FINISHED_HANDLED = 8;
        public static final int FLAG_MODIFIED_FOR_COMPATIBILITY = 64;
        public static final int FLAG_RESYNTHESIZED = 16;
        public static final int FLAG_UNHANDLED = 32;
        public InputEvent mEvent;
        public int mFlags;
        public QueuedInputEvent mNext;
        public InputEventReceiver mReceiver;

        private QueuedInputEvent() {
        }

        private boolean flagToString(String string2, int n, boolean bl, StringBuilder stringBuilder) {
            if ((this.mFlags & n) != 0) {
                if (bl) {
                    stringBuilder.append("|");
                }
                stringBuilder.append(string2);
                return true;
            }
            return bl;
        }

        public boolean shouldSendToSynthesizer() {
            return (this.mFlags & 32) != 0;
        }

        public boolean shouldSkipIme() {
            int n = this.mFlags;
            boolean bl = true;
            if ((n & 1) != 0) {
                return true;
            }
            InputEvent inputEvent = this.mEvent;
            if (!(inputEvent instanceof MotionEvent) || !inputEvent.isFromSource(2) && !this.mEvent.isFromSource(4194304)) {
                bl = false;
            }
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("QueuedInputEvent{flags=");
            if (!this.flagToString("UNHANDLED", 32, this.flagToString("RESYNTHESIZED", 16, this.flagToString("FINISHED_HANDLED", 8, this.flagToString("FINISHED", 4, this.flagToString("DEFERRED", 2, this.flagToString("DELIVER_POST_IME", 1, false, stringBuilder), stringBuilder), stringBuilder), stringBuilder), stringBuilder), stringBuilder)) {
                stringBuilder.append("0");
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", hasNextQueuedEvent=");
            Object object = this.mEvent;
            String string2 = "true";
            object = object != null ? "true" : "false";
            stringBuilder2.append((String)object);
            stringBuilder.append(stringBuilder2.toString());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(", hasInputEventReceiver=");
            object = this.mReceiver != null ? string2 : "false";
            stringBuilder2.append((String)object);
            stringBuilder.append(stringBuilder2.toString());
            object = new StringBuilder();
            ((StringBuilder)object).append(", mEvent=");
            ((StringBuilder)object).append(this.mEvent);
            ((StringBuilder)object).append("}");
            stringBuilder.append(((StringBuilder)object).toString());
            return stringBuilder.toString();
        }
    }

    private class SendWindowContentChangedAccessibilityEvent
    implements Runnable {
        private int mChangeTypes = 0;
        public long mLastEventTimeMillis;
        public StackTraceElement[] mOrigin;
        public View mSource;

        private SendWindowContentChangedAccessibilityEvent() {
        }

        public void removeCallbacksAndRun() {
            ViewRootImpl.this.mHandler.removeCallbacks(this);
            this.run();
        }

        @Override
        public void run() {
            View view = this.mSource;
            this.mSource = null;
            if (view == null) {
                Log.e(ViewRootImpl.TAG, "Accessibility content change has no source");
                return;
            }
            if (AccessibilityManager.getInstance(ViewRootImpl.this.mContext).isEnabled()) {
                this.mLastEventTimeMillis = SystemClock.uptimeMillis();
                AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain();
                accessibilityEvent.setEventType(2048);
                accessibilityEvent.setContentChangeTypes(this.mChangeTypes);
                view.sendAccessibilityEventUnchecked(accessibilityEvent);
            } else {
                this.mLastEventTimeMillis = 0L;
            }
            view.resetSubtreeAccessibilityStateChanged();
            this.mChangeTypes = 0;
        }

        public void runOrPost(View view, int n) {
            long l;
            View view2;
            if (ViewRootImpl.this.mHandler.getLooper() != Looper.myLooper()) {
                Log.e(ViewRootImpl.TAG, "Accessibility content change on non-UI thread. Future Android versions will throw an exception.", new CalledFromWrongThreadException("Only the original thread that created a view hierarchy can touch its views."));
                ViewRootImpl.this.mHandler.removeCallbacks(this);
                if (this.mSource != null) {
                    this.run();
                }
            }
            if ((view2 = this.mSource) != null) {
                View view3;
                view2 = view3 = ViewRootImpl.this.getCommonPredecessor(view2, view);
                if (view3 != null) {
                    view2 = view3.getSelfOrParentImportantForA11y();
                }
                if (view2 == null) {
                    view2 = view;
                }
                this.mSource = view2;
                this.mChangeTypes |= n;
                return;
            }
            this.mSource = view;
            this.mChangeTypes = n;
            long l2 = SystemClock.uptimeMillis() - this.mLastEventTimeMillis;
            if (l2 >= (l = ViewConfiguration.getSendRecurringAccessibilityEventsInterval())) {
                this.removeCallbacksAndRun();
            } else {
                ViewRootImpl.this.mHandler.postDelayed(this, l - l2);
            }
        }
    }

    final class SyntheticInputStage
    extends InputStage {
        private final SyntheticJoystickHandler mJoystick;
        private final SyntheticKeyboardHandler mKeyboard;
        private final SyntheticTouchNavigationHandler mTouchNavigation;
        private final SyntheticTrackballHandler mTrackball;

        public SyntheticInputStage() {
            super(null);
            this.mTrackball = new SyntheticTrackballHandler();
            this.mJoystick = new SyntheticJoystickHandler();
            this.mTouchNavigation = new SyntheticTouchNavigationHandler();
            this.mKeyboard = new SyntheticKeyboardHandler();
        }

        @Override
        protected void onDeliverToNext(QueuedInputEvent queuedInputEvent) {
            if ((queuedInputEvent.mFlags & 16) == 0 && queuedInputEvent.mEvent instanceof MotionEvent) {
                MotionEvent motionEvent = (MotionEvent)queuedInputEvent.mEvent;
                int n = motionEvent.getSource();
                if ((n & 4) != 0) {
                    this.mTrackball.cancel();
                } else if ((n & 16) != 0) {
                    this.mJoystick.cancel();
                } else if ((n & 2097152) == 2097152) {
                    this.mTouchNavigation.cancel(motionEvent);
                }
            }
            super.onDeliverToNext(queuedInputEvent);
        }

        @Override
        protected void onDetachedFromWindow() {
            this.mJoystick.cancel();
        }

        @Override
        protected int onProcess(QueuedInputEvent object) {
            ((QueuedInputEvent)object).mFlags |= 16;
            if (((QueuedInputEvent)object).mEvent instanceof MotionEvent) {
                object = (MotionEvent)((QueuedInputEvent)object).mEvent;
                int n = ((MotionEvent)object).getSource();
                if ((n & 4) != 0) {
                    this.mTrackball.process((MotionEvent)object);
                    return 1;
                }
                if ((n & 16) != 0) {
                    this.mJoystick.process((MotionEvent)object);
                    return 1;
                }
                if ((n & 2097152) == 2097152) {
                    this.mTouchNavigation.process((MotionEvent)object);
                    return 1;
                }
            } else if ((((QueuedInputEvent)object).mFlags & 32) != 0) {
                this.mKeyboard.process((KeyEvent)((QueuedInputEvent)object).mEvent);
                return 1;
            }
            return 0;
        }

        @Override
        protected void onWindowFocusChanged(boolean bl) {
            if (!bl) {
                this.mJoystick.cancel();
            }
        }
    }

    final class SyntheticJoystickHandler
    extends Handler {
        private static final int MSG_ENQUEUE_X_AXIS_KEY_REPEAT = 1;
        private static final int MSG_ENQUEUE_Y_AXIS_KEY_REPEAT = 2;
        private final SparseArray<KeyEvent> mDeviceKeyEvents;
        private final JoystickAxesState mJoystickAxesState;

        public SyntheticJoystickHandler() {
            super(true);
            this.mJoystickAxesState = new JoystickAxesState();
            this.mDeviceKeyEvents = new SparseArray();
        }

        private void cancel() {
            this.removeMessages(1);
            this.removeMessages(2);
            for (int i = 0; i < this.mDeviceKeyEvents.size(); ++i) {
                KeyEvent keyEvent = this.mDeviceKeyEvents.valueAt(i);
                if (keyEvent == null) continue;
                ViewRootImpl.this.enqueueInputEvent(KeyEvent.changeTimeRepeat(keyEvent, SystemClock.uptimeMillis(), 0));
            }
            this.mDeviceKeyEvents.clear();
            this.mJoystickAxesState.resetState();
        }

        private void update(MotionEvent motionEvent) {
            long l;
            int n = motionEvent.getHistorySize();
            for (int i = 0; i < n; ++i) {
                l = motionEvent.getHistoricalEventTime(i);
                this.mJoystickAxesState.updateStateForAxis(motionEvent, l, 0, motionEvent.getHistoricalAxisValue(0, 0, i));
                this.mJoystickAxesState.updateStateForAxis(motionEvent, l, 1, motionEvent.getHistoricalAxisValue(1, 0, i));
                this.mJoystickAxesState.updateStateForAxis(motionEvent, l, 15, motionEvent.getHistoricalAxisValue(15, 0, i));
                this.mJoystickAxesState.updateStateForAxis(motionEvent, l, 16, motionEvent.getHistoricalAxisValue(16, 0, i));
            }
            l = motionEvent.getEventTime();
            this.mJoystickAxesState.updateStateForAxis(motionEvent, l, 0, motionEvent.getAxisValue(0));
            this.mJoystickAxesState.updateStateForAxis(motionEvent, l, 1, motionEvent.getAxisValue(1));
            this.mJoystickAxesState.updateStateForAxis(motionEvent, l, 15, motionEvent.getAxisValue(15));
            this.mJoystickAxesState.updateStateForAxis(motionEvent, l, 16, motionEvent.getAxisValue(16));
        }

        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            if ((n == 1 || n == 2) && ViewRootImpl.this.mAttachInfo.mHasWindowFocus) {
                KeyEvent keyEvent = (KeyEvent)message.obj;
                keyEvent = KeyEvent.changeTimeRepeat(keyEvent, SystemClock.uptimeMillis(), keyEvent.getRepeatCount() + 1);
                ViewRootImpl.this.enqueueInputEvent(keyEvent);
                message = this.obtainMessage(message.what, keyEvent);
                message.setAsynchronous(true);
                this.sendMessageDelayed(message, ViewConfiguration.getKeyRepeatDelay());
            }
        }

        public void process(MotionEvent motionEvent) {
            int n = motionEvent.getActionMasked();
            if (n != 2) {
                if (n != 3) {
                    String string2 = ViewRootImpl.this.mTag;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unexpected action: ");
                    stringBuilder.append(motionEvent.getActionMasked());
                    Log.w(string2, stringBuilder.toString());
                } else {
                    this.cancel();
                }
            } else {
                this.update(motionEvent);
            }
        }

        final class JoystickAxesState {
            private static final int STATE_DOWN_OR_RIGHT = 1;
            private static final int STATE_NEUTRAL = 0;
            private static final int STATE_UP_OR_LEFT = -1;
            final int[] mAxisStatesHat = new int[]{0, 0};
            final int[] mAxisStatesStick = new int[]{0, 0};

            JoystickAxesState() {
            }

            private boolean isXAxis(int n) {
                boolean bl = n == 0 || n == 15;
                return bl;
            }

            private boolean isYAxis(int n) {
                boolean bl;
                boolean bl2 = bl = true;
                if (n != 1) {
                    bl2 = n == 16 ? bl : false;
                }
                return bl2;
            }

            private int joystickAxisAndStateToKeycode(int n, int n2) {
                if (this.isXAxis(n) && n2 == -1) {
                    return 21;
                }
                if (this.isXAxis(n) && n2 == 1) {
                    return 22;
                }
                if (this.isYAxis(n) && n2 == -1) {
                    return 19;
                }
                if (this.isYAxis(n) && n2 == 1) {
                    return 20;
                }
                String string2 = ViewRootImpl.this.mTag;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown axis ");
                stringBuilder.append(n);
                stringBuilder.append(" or direction ");
                stringBuilder.append(n2);
                Log.e(string2, stringBuilder.toString());
                return 0;
            }

            private int joystickAxisValueToState(float f) {
                if (f >= 0.5f) {
                    return 1;
                }
                if (f <= -0.5f) {
                    return -1;
                }
                return 0;
            }

            void resetState() {
                int[] arrn = this.mAxisStatesHat;
                arrn[0] = 0;
                arrn[1] = 0;
                arrn = this.mAxisStatesStick;
                arrn[0] = 0;
                arrn[1] = 0;
            }

            void updateStateForAxis(MotionEvent object, long l, int n, float f) {
                block12 : {
                    int n2;
                    int n3;
                    int n4;
                    block11 : {
                        block10 : {
                            if (!this.isXAxis(n)) break block10;
                            n4 = 0;
                            n3 = 1;
                            break block11;
                        }
                        if (!this.isYAxis(n)) break block12;
                        n4 = 1;
                        n3 = 2;
                    }
                    int n5 = this.joystickAxisValueToState(f);
                    int n6 = n != 0 && n != 1 ? this.mAxisStatesHat[n4] : this.mAxisStatesStick[n4];
                    if (n6 == n5) {
                        return;
                    }
                    int n7 = ((MotionEvent)object).getMetaState();
                    int n8 = n2 = ((MotionEvent)object).getDeviceId();
                    int n9 = ((MotionEvent)object).getSource();
                    if (n6 == 1 || n6 == -1) {
                        if ((n6 = this.joystickAxisAndStateToKeycode(n, n6)) != 0) {
                            ViewRootImpl.this.enqueueInputEvent(new KeyEvent(l, l, 1, n6, 0, n7, n8, 0, 1024, n9));
                            SyntheticJoystickHandler.this.mDeviceKeyEvents.put(n8, null);
                        }
                        SyntheticJoystickHandler.this.removeMessages(n3);
                    }
                    if (n5 == 1 || n5 == -1) {
                        n6 = this.joystickAxisAndStateToKeycode(n, n5);
                        if (n6 != 0) {
                            object = new KeyEvent(l, l, 0, n6, 0, n7, n2, 0, 1024, n9);
                            ViewRootImpl.this.enqueueInputEvent((InputEvent)object);
                            object = SyntheticJoystickHandler.this.obtainMessage(n3, object);
                            ((Message)object).setAsynchronous(true);
                            SyntheticJoystickHandler.this.sendMessageDelayed((Message)object, ViewConfiguration.getKeyRepeatTimeout());
                            SyntheticJoystickHandler.this.mDeviceKeyEvents.put(n2, new KeyEvent(l, l, 1, n6, 0, n7, n2, 0, 1056, n9));
                        }
                    }
                    if (n != 0 && n != 1) {
                        this.mAxisStatesHat[n4] = n5;
                    } else {
                        this.mAxisStatesStick[n4] = n5;
                    }
                    return;
                }
                String string2 = ViewRootImpl.this.mTag;
                object = new StringBuilder();
                ((StringBuilder)object).append("Unexpected axis ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" in updateStateForAxis!");
                Log.e(string2, ((StringBuilder)object).toString());
            }
        }

    }

    final class SyntheticKeyboardHandler {
        SyntheticKeyboardHandler() {
        }

        public void process(KeyEvent keyEvent) {
            block1 : {
                int n;
                if ((keyEvent.getFlags() & 1024) != 0) {
                    return;
                }
                Object object = keyEvent.getKeyCharacterMap();
                int n2 = keyEvent.getKeyCode();
                if ((object = ((KeyCharacterMap)object).getFallbackAction(n2, n = keyEvent.getMetaState())) == null) break block1;
                n = keyEvent.getFlags();
                keyEvent = KeyEvent.obtain(keyEvent.getDownTime(), keyEvent.getEventTime(), keyEvent.getAction(), ((KeyCharacterMap.FallbackAction)object).keyCode, keyEvent.getRepeatCount(), ((KeyCharacterMap.FallbackAction)object).metaState, keyEvent.getDeviceId(), keyEvent.getScanCode(), n | 1024, keyEvent.getSource(), null);
                ((KeyCharacterMap.FallbackAction)object).recycle();
                ViewRootImpl.this.enqueueInputEvent(keyEvent);
            }
        }
    }

    final class SyntheticTouchNavigationHandler
    extends Handler {
        private static final float DEFAULT_HEIGHT_MILLIMETERS = 48.0f;
        private static final float DEFAULT_WIDTH_MILLIMETERS = 48.0f;
        private static final float FLING_TICK_DECAY = 0.8f;
        private static final boolean LOCAL_DEBUG = false;
        private static final String LOCAL_TAG = "SyntheticTouchNavigationHandler";
        private static final float MAX_FLING_VELOCITY_TICKS_PER_SECOND = 20.0f;
        private static final float MIN_FLING_VELOCITY_TICKS_PER_SECOND = 6.0f;
        private static final int TICK_DISTANCE_MILLIMETERS = 12;
        private float mAccumulatedX;
        private float mAccumulatedY;
        private int mActivePointerId;
        private float mConfigMaxFlingVelocity;
        private float mConfigMinFlingVelocity;
        private float mConfigTickDistance;
        private boolean mConsumedMovement;
        private int mCurrentDeviceId;
        private boolean mCurrentDeviceSupported;
        private int mCurrentSource;
        private final Runnable mFlingRunnable;
        private float mFlingVelocity;
        private boolean mFlinging;
        private float mLastX;
        private float mLastY;
        private int mPendingKeyCode;
        private long mPendingKeyDownTime;
        private int mPendingKeyMetaState;
        private int mPendingKeyRepeatCount;
        private float mStartX;
        private float mStartY;
        private VelocityTracker mVelocityTracker;

        public SyntheticTouchNavigationHandler() {
            super(true);
            this.mCurrentDeviceId = -1;
            this.mActivePointerId = -1;
            this.mPendingKeyCode = 0;
            this.mFlingRunnable = new Runnable(){

                @Override
                public void run() {
                    long l = SystemClock.uptimeMillis();
                    SyntheticTouchNavigationHandler syntheticTouchNavigationHandler = SyntheticTouchNavigationHandler.this;
                    syntheticTouchNavigationHandler.sendKeyDownOrRepeat(l, syntheticTouchNavigationHandler.mPendingKeyCode, SyntheticTouchNavigationHandler.this.mPendingKeyMetaState);
                    SyntheticTouchNavigationHandler.access$3132(SyntheticTouchNavigationHandler.this, 0.8f);
                    if (!SyntheticTouchNavigationHandler.this.postFling(l)) {
                        SyntheticTouchNavigationHandler.this.mFlinging = false;
                        SyntheticTouchNavigationHandler.this.finishKeys(l);
                    }
                }
            };
        }

        static /* synthetic */ float access$3132(SyntheticTouchNavigationHandler syntheticTouchNavigationHandler, float f) {
            syntheticTouchNavigationHandler.mFlingVelocity = f = syntheticTouchNavigationHandler.mFlingVelocity * f;
            return f;
        }

        private void cancelFling() {
            if (this.mFlinging) {
                this.removeCallbacks(this.mFlingRunnable);
                this.mFlinging = false;
            }
        }

        private float consumeAccumulatedMovement(long l, int n, float f, int n2, int n3) {
            float f2;
            do {
                if (!(f <= -this.mConfigTickDistance)) break;
                this.sendKeyDownOrRepeat(l, n2, n);
                f += this.mConfigTickDistance;
            } while (true);
            for (f2 = f; f2 >= this.mConfigTickDistance; f2 -= this.mConfigTickDistance) {
                this.sendKeyDownOrRepeat(l, n3, n);
            }
            return f2;
        }

        private void consumeAccumulatedMovement(long l, int n) {
            float f;
            float f2 = Math.abs(this.mAccumulatedX);
            if (f2 >= (f = Math.abs(this.mAccumulatedY))) {
                if (f2 >= this.mConfigTickDistance) {
                    this.mAccumulatedX = this.consumeAccumulatedMovement(l, n, this.mAccumulatedX, 21, 22);
                    this.mAccumulatedY = 0.0f;
                    this.mConsumedMovement = true;
                }
            } else if (f >= this.mConfigTickDistance) {
                this.mAccumulatedY = this.consumeAccumulatedMovement(l, n, this.mAccumulatedY, 19, 20);
                this.mAccumulatedX = 0.0f;
                this.mConsumedMovement = true;
            }
        }

        private void finishKeys(long l) {
            this.cancelFling();
            this.sendKeyUp(l);
        }

        private void finishTracking(long l) {
            if (this.mActivePointerId >= 0) {
                this.mActivePointerId = -1;
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
        }

        private boolean postFling(long l) {
            float f = this.mFlingVelocity;
            if (f >= this.mConfigMinFlingVelocity) {
                long l2 = (long)(this.mConfigTickDistance / f * 1000.0f);
                this.postAtTime(this.mFlingRunnable, l + l2);
                return true;
            }
            return false;
        }

        private void sendKeyDownOrRepeat(long l, int n, int n2) {
            if (this.mPendingKeyCode != n) {
                this.sendKeyUp(l);
                this.mPendingKeyDownTime = l;
                this.mPendingKeyCode = n;
                this.mPendingKeyRepeatCount = 0;
            } else {
                ++this.mPendingKeyRepeatCount;
            }
            this.mPendingKeyMetaState = n2;
            ViewRootImpl.this.enqueueInputEvent(new KeyEvent(this.mPendingKeyDownTime, l, 0, this.mPendingKeyCode, this.mPendingKeyRepeatCount, this.mPendingKeyMetaState, this.mCurrentDeviceId, 1024, this.mCurrentSource));
        }

        private void sendKeyUp(long l) {
            int n = this.mPendingKeyCode;
            if (n != 0) {
                ViewRootImpl.this.enqueueInputEvent(new KeyEvent(this.mPendingKeyDownTime, l, 1, n, 0, this.mPendingKeyMetaState, this.mCurrentDeviceId, 0, 1024, this.mCurrentSource));
                this.mPendingKeyCode = 0;
            }
        }

        private boolean startFling(long l, float f, float f2) {
            switch (this.mPendingKeyCode) {
                default: {
                    break;
                }
                case 22: {
                    if (f >= this.mConfigMinFlingVelocity && Math.abs(f2) < this.mConfigMinFlingVelocity) {
                        this.mFlingVelocity = f;
                        break;
                    }
                    return false;
                }
                case 21: {
                    if (-f >= this.mConfigMinFlingVelocity && Math.abs(f2) < this.mConfigMinFlingVelocity) {
                        this.mFlingVelocity = -f;
                        break;
                    }
                    return false;
                }
                case 20: {
                    if (f2 >= this.mConfigMinFlingVelocity && Math.abs(f) < this.mConfigMinFlingVelocity) {
                        this.mFlingVelocity = f2;
                        break;
                    }
                    return false;
                }
                case 19: {
                    if (-f2 >= this.mConfigMinFlingVelocity && Math.abs(f) < this.mConfigMinFlingVelocity) {
                        this.mFlingVelocity = -f2;
                        break;
                    }
                    return false;
                }
            }
            this.mFlinging = this.postFling(l);
            return this.mFlinging;
        }

        public void cancel(MotionEvent motionEvent) {
            if (this.mCurrentDeviceId == motionEvent.getDeviceId() && this.mCurrentSource == motionEvent.getSource()) {
                long l = motionEvent.getEventTime();
                this.finishKeys(l);
                this.finishTracking(l);
            }
        }

        public void process(MotionEvent motionEvent) {
            float f;
            float f2;
            long l = motionEvent.getEventTime();
            int n = motionEvent.getDeviceId();
            int n2 = motionEvent.getSource();
            if (this.mCurrentDeviceId != n || this.mCurrentSource != n2) {
                this.finishKeys(l);
                this.finishTracking(l);
                this.mCurrentDeviceId = n;
                this.mCurrentSource = n2;
                this.mCurrentDeviceSupported = false;
                Object object = motionEvent.getDevice();
                if (object != null) {
                    InputDevice.MotionRange motionRange = ((InputDevice)object).getMotionRange(0);
                    object = ((InputDevice)object).getMotionRange(1);
                    if (motionRange != null && object != null) {
                        float f3;
                        this.mCurrentDeviceSupported = true;
                        f = f2 = motionRange.getResolution();
                        if (f2 <= 0.0f) {
                            f = motionRange.getRange() / 48.0f;
                        }
                        f2 = f3 = ((InputDevice.MotionRange)object).getResolution();
                        if (f3 <= 0.0f) {
                            f2 = ((InputDevice.MotionRange)object).getRange() / 48.0f;
                        }
                        f = this.mConfigTickDistance = 12.0f * ((f + f2) * 0.5f);
                        this.mConfigMinFlingVelocity = 6.0f * f;
                        this.mConfigMaxFlingVelocity = f * 20.0f;
                    }
                }
            }
            if (!this.mCurrentDeviceSupported) {
                return;
            }
            n = motionEvent.getActionMasked();
            if (n != 0) {
                if (n != 1 && n != 2) {
                    if (n == 3) {
                        this.finishKeys(l);
                        this.finishTracking(l);
                    }
                } else {
                    n2 = this.mActivePointerId;
                    if (n2 >= 0) {
                        if ((n2 = motionEvent.findPointerIndex(n2)) < 0) {
                            this.finishKeys(l);
                            this.finishTracking(l);
                        } else {
                            this.mVelocityTracker.addMovement(motionEvent);
                            f2 = motionEvent.getX(n2);
                            f = motionEvent.getY(n2);
                            this.mAccumulatedX += f2 - this.mLastX;
                            this.mAccumulatedY += f - this.mLastY;
                            this.mLastX = f2;
                            this.mLastY = f;
                            this.consumeAccumulatedMovement(l, motionEvent.getMetaState());
                            if (n == 1) {
                                if (this.mConsumedMovement && this.mPendingKeyCode != 0) {
                                    this.mVelocityTracker.computeCurrentVelocity(1000, this.mConfigMaxFlingVelocity);
                                    if (!this.startFling(l, this.mVelocityTracker.getXVelocity(this.mActivePointerId), this.mVelocityTracker.getYVelocity(this.mActivePointerId))) {
                                        this.finishKeys(l);
                                    }
                                }
                                this.finishTracking(l);
                            }
                        }
                    }
                }
            } else {
                boolean bl = this.mFlinging;
                this.finishKeys(l);
                this.finishTracking(l);
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.mVelocityTracker = VelocityTracker.obtain();
                this.mVelocityTracker.addMovement(motionEvent);
                this.mStartX = motionEvent.getX();
                this.mStartY = motionEvent.getY();
                this.mLastX = this.mStartX;
                this.mLastY = this.mStartY;
                this.mAccumulatedX = 0.0f;
                this.mAccumulatedY = 0.0f;
                this.mConsumedMovement = bl;
            }
        }

    }

    final class SyntheticTrackballHandler {
        private long mLastTime;
        private final TrackballAxis mX = new TrackballAxis();
        private final TrackballAxis mY = new TrackballAxis();

        SyntheticTrackballHandler() {
        }

        public void cancel() {
            this.mLastTime = Integer.MIN_VALUE;
            if (ViewRootImpl.this.mView != null && ViewRootImpl.this.mAdded) {
                ViewRootImpl.this.ensureTouchMode(false);
            }
        }

        public void process(MotionEvent motionEvent) {
            long l = SystemClock.uptimeMillis();
            if (this.mLastTime + 250L < l) {
                this.mX.reset(0);
                this.mY.reset(0);
                this.mLastTime = l;
            }
            int n = motionEvent.getAction();
            int n2 = motionEvent.getMetaState();
            if (n != 0) {
                if (n == 1) {
                    this.mX.reset(2);
                    this.mY.reset(2);
                    ViewRootImpl.this.enqueueInputEvent(new KeyEvent(l, l, 1, 23, 0, n2, -1, 0, 1024, 257));
                }
            } else {
                this.mX.reset(2);
                this.mY.reset(2);
                ViewRootImpl.this.enqueueInputEvent(new KeyEvent(l, l, 0, 23, 0, n2, -1, 0, 1024, 257));
            }
            float f = this.mX.collect(motionEvent.getX(), motionEvent.getEventTime(), "X");
            float f2 = this.mY.collect(motionEvent.getY(), motionEvent.getEventTime(), "Y");
            int n3 = 0;
            if (f > f2) {
                n3 = this.mX.generate();
                if (n3 != 0) {
                    n = n3 > 0 ? 22 : 21;
                    f2 = this.mX.acceleration;
                    this.mY.reset(2);
                } else {
                    n = 0;
                    f2 = 1.0f;
                }
            } else if (f2 > 0.0f) {
                n3 = this.mY.generate();
                if (n3 != 0) {
                    n = n3 > 0 ? 20 : 19;
                    f2 = this.mY.acceleration;
                    this.mX.reset(2);
                } else {
                    n = 0;
                    f2 = 1.0f;
                }
            } else {
                n = 0;
                f2 = 1.0f;
            }
            if (n != 0) {
                int n4 = n3;
                if (n3 < 0) {
                    n4 = -n3;
                }
                if ((n3 = (int)((float)n4 * f2)) > n4) {
                    ViewRootImpl.this.enqueueInputEvent(new KeyEvent(l, l, 2, n, n3 - --n4, n2, -1, 0, 1024, 257));
                }
                while (n4 > 0) {
                    --n4;
                    l = SystemClock.uptimeMillis();
                    ViewRootImpl.this.enqueueInputEvent(new KeyEvent(l, l, 0, n, 0, n2, -1, 0, 1024, 257));
                    ViewRootImpl.this.enqueueInputEvent(new KeyEvent(l, l, 1, n, 0, n2, -1, 0, 1024, 257));
                }
                this.mLastTime = l;
            }
        }
    }

    static final class SystemUiVisibilityInfo {
        int globalVisibility;
        int localChanges;
        int localValue;
        int seq;

        SystemUiVisibilityInfo() {
        }
    }

    class TakenSurfaceHolder
    extends BaseSurfaceHolder {
        TakenSurfaceHolder() {
        }

        @Override
        public boolean isCreating() {
            return ViewRootImpl.this.mIsCreating;
        }

        @Override
        public boolean onAllowLockCanvas() {
            return ViewRootImpl.this.mDrawingAllowed;
        }

        @Override
        public void onRelayoutContainer() {
        }

        @Override
        public void onUpdateSurface() {
            throw new IllegalStateException("Shouldn't be here");
        }

        @Override
        public void setFixedSize(int n, int n2) {
            throw new UnsupportedOperationException("Currently only support sizing from layout");
        }

        @Override
        public void setFormat(int n) {
            ((RootViewSurfaceTaker)((Object)ViewRootImpl.this.mView)).setSurfaceFormat(n);
        }

        @Override
        public void setKeepScreenOn(boolean bl) {
            ((RootViewSurfaceTaker)((Object)ViewRootImpl.this.mView)).setSurfaceKeepScreenOn(bl);
        }

        @Override
        public void setType(int n) {
            ((RootViewSurfaceTaker)((Object)ViewRootImpl.this.mView)).setSurfaceType(n);
        }
    }

    static final class TrackballAxis {
        static final float ACCEL_MOVE_SCALING_FACTOR = 0.025f;
        static final long FAST_MOVE_TIME = 150L;
        static final float FIRST_MOVEMENT_THRESHOLD = 0.5f;
        static final float MAX_ACCELERATION = 20.0f;
        static final float SECOND_CUMULATIVE_MOVEMENT_THRESHOLD = 2.0f;
        static final float SUBSEQUENT_INCREMENTAL_MOVEMENT_THRESHOLD = 1.0f;
        float acceleration = 1.0f;
        int dir;
        long lastMoveTime = 0L;
        int nonAccelMovement;
        float position;
        int step;

        TrackballAxis() {
        }

        float collect(float f, long l, String string2) {
            long l2;
            float f2 = 1.0f;
            if (f > 0.0f) {
                l2 = (long)(150.0f * f);
                if (this.dir < 0) {
                    this.position = 0.0f;
                    this.step = 0;
                    this.acceleration = 1.0f;
                    this.lastMoveTime = 0L;
                }
                this.dir = 1;
            } else if (f < 0.0f) {
                l2 = (long)(-f * 150.0f);
                if (this.dir > 0) {
                    this.position = 0.0f;
                    this.step = 0;
                    this.acceleration = 1.0f;
                    this.lastMoveTime = 0L;
                }
                this.dir = -1;
            } else {
                l2 = 0L;
            }
            if (l2 > 0L) {
                long l3 = l - this.lastMoveTime;
                this.lastMoveTime = l;
                float f3 = this.acceleration;
                if (l3 < l2) {
                    f2 = (float)(l2 - l3) * 0.025f;
                    float f4 = f3;
                    if (f2 > 1.0f) {
                        f4 = f3 * f2;
                    }
                    f3 = 20.0f;
                    if (f4 < 20.0f) {
                        f3 = f4;
                    }
                    this.acceleration = f3;
                } else {
                    float f5 = (float)(l3 - l2) * 0.025f;
                    float f6 = f3;
                    if (f5 > 1.0f) {
                        f6 = f3 / f5;
                    }
                    f3 = f2;
                    if (f6 > 1.0f) {
                        f3 = f6;
                    }
                    this.acceleration = f3;
                }
            }
            this.position += f;
            return Math.abs(this.position);
        }

        int generate() {
            int n = 0;
            this.nonAccelMovement = 0;
            do {
                int n2 = this.position >= 0.0f ? 1 : -1;
                int n3 = this.step;
                if (n3 != 0) {
                    if (n3 != 1) {
                        if (Math.abs(this.position) < 1.0f) {
                            return n;
                        }
                        n += n2;
                        this.position -= (float)n2 * 1.0f;
                        float f = this.acceleration * 1.1f;
                        if (!(f < 20.0f)) {
                            f = this.acceleration;
                        }
                        this.acceleration = f;
                        n2 = n;
                    } else {
                        if (Math.abs(this.position) < 2.0f) {
                            return n;
                        }
                        n += n2;
                        this.nonAccelMovement += n2;
                        this.position -= (float)n2 * 2.0f;
                        this.step = 2;
                        n2 = n;
                    }
                } else {
                    if (Math.abs(this.position) < 0.5f) {
                        return n;
                    }
                    n += n2;
                    this.nonAccelMovement += n2;
                    this.step = 1;
                    n2 = n;
                }
                n = n2;
            } while (true);
        }

        void reset(int n) {
            this.position = 0.0f;
            this.acceleration = 1.0f;
            this.lastMoveTime = 0L;
            this.step = n;
            this.dir = 0;
        }
    }

    final class TraversalRunnable
    implements Runnable {
        TraversalRunnable() {
        }

        @Override
        public void run() {
            ViewRootImpl.this.doTraversal();
        }
    }

    private static class UnhandledKeyManager {
        private final SparseArray<WeakReference<View>> mCapturedKeys = new SparseArray();
        private WeakReference<View> mCurrentReceiver = null;
        private boolean mDispatched = true;

        private UnhandledKeyManager() {
        }

        boolean dispatch(View view, KeyEvent object) {
            boolean bl;
            block7 : {
                if (this.mDispatched) {
                    return false;
                }
                Trace.traceBegin(8L, "UnhandledKeyEvent dispatch");
                bl = true;
                this.mDispatched = true;
                view = view.dispatchUnhandledKeyEvent((KeyEvent)object);
                if (((KeyEvent)object).getAction() != 0) break block7;
                int n = ((KeyEvent)object).getKeyCode();
                if (view == null) break block7;
                if (KeyEvent.isModifierKey(n)) break block7;
                object = this.mCapturedKeys;
                WeakReference<View> weakReference = new WeakReference<View>(view);
                ((SparseArray)object).put(n, weakReference);
            }
            if (view == null) {
                bl = false;
            }
            return bl;
            finally {
                Trace.traceEnd(8L);
            }
        }

        void preDispatch(KeyEvent keyEvent) {
            int n;
            this.mCurrentReceiver = null;
            if (keyEvent.getAction() == 1 && (n = this.mCapturedKeys.indexOfKey(keyEvent.getKeyCode())) >= 0) {
                this.mCurrentReceiver = this.mCapturedKeys.valueAt(n);
                this.mCapturedKeys.removeAt(n);
            }
        }

        boolean preViewDispatch(KeyEvent keyEvent) {
            Object object;
            this.mDispatched = false;
            if (this.mCurrentReceiver == null) {
                this.mCurrentReceiver = this.mCapturedKeys.get(keyEvent.getKeyCode());
            }
            if ((object = this.mCurrentReceiver) != null) {
                object = (View)((Reference)object).get();
                if (keyEvent.getAction() == 1) {
                    this.mCurrentReceiver = null;
                }
                if (object != null && ((View)object).isAttachedToWindow()) {
                    ((View)object).onUnhandledKeyEvent(keyEvent);
                }
                return true;
            }
            return false;
        }
    }

    final class ViewPostImeInputStage
    extends InputStage {
        public ViewPostImeInputStage(InputStage inputStage) {
            super(inputStage);
        }

        private void maybeUpdatePointerIcon(MotionEvent motionEvent) {
            if (motionEvent.getPointerCount() == 1 && motionEvent.isFromSource(8194)) {
                if (motionEvent.getActionMasked() == 9 || motionEvent.getActionMasked() == 10) {
                    ViewRootImpl.this.mPointerIconType = 1;
                }
                if (motionEvent.getActionMasked() != 10 && !ViewRootImpl.this.updatePointerIcon(motionEvent) && motionEvent.getActionMasked() == 7) {
                    ViewRootImpl.this.mPointerIconType = 1;
                }
            }
        }

        private boolean performFocusNavigation(KeyEvent object) {
            int n;
            block22 : {
                block21 : {
                    n = 0;
                    int n2 = ((KeyEvent)object).getKeyCode();
                    if (n2 == 61) break block21;
                    switch (n2) {
                        default: {
                            break;
                        }
                        case 22: {
                            if (((KeyEvent)object).hasNoModifiers()) {
                                n = 66;
                                break;
                            }
                            break block22;
                        }
                        case 21: {
                            if (((KeyEvent)object).hasNoModifiers()) {
                                n = 17;
                                break;
                            }
                            break block22;
                        }
                        case 20: {
                            if (((KeyEvent)object).hasNoModifiers()) {
                                n = 130;
                                break;
                            }
                            break block22;
                        }
                        case 19: {
                            if (((KeyEvent)object).hasNoModifiers()) {
                                n = 33;
                                break;
                            }
                            break block22;
                        }
                    }
                    break block22;
                }
                if (((KeyEvent)object).hasNoModifiers()) {
                    n = 2;
                } else if (((KeyEvent)object).hasModifiers(1)) {
                    n = 1;
                }
            }
            if (n != 0) {
                object = ViewRootImpl.this.mView.findFocus();
                if (object != null) {
                    View view = ((View)object).focusSearch(n);
                    if (view != null && view != object) {
                        ((View)object).getFocusedRect(ViewRootImpl.this.mTempRect);
                        if (ViewRootImpl.this.mView instanceof ViewGroup) {
                            ((ViewGroup)ViewRootImpl.this.mView).offsetDescendantRectToMyCoords((View)object, ViewRootImpl.this.mTempRect);
                            ((ViewGroup)ViewRootImpl.this.mView).offsetRectIntoDescendantCoords(view, ViewRootImpl.this.mTempRect);
                        }
                        if (view.requestFocus(n, ViewRootImpl.this.mTempRect)) {
                            ViewRootImpl.this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(n));
                            return true;
                        }
                    }
                    if (ViewRootImpl.this.mView.dispatchUnhandledMove((View)object, n)) {
                        return true;
                    }
                } else if (ViewRootImpl.this.mView.restoreDefaultFocus()) {
                    return true;
                }
            }
            return false;
        }

        private boolean performKeyboardGroupNavigation(int n) {
            View view = ViewRootImpl.this.mView.findFocus();
            if (view == null && ViewRootImpl.this.mView.restoreDefaultFocus()) {
                return true;
            }
            view = view == null ? ViewRootImpl.this.keyboardNavigationClusterSearch(null, n) : view.keyboardNavigationClusterSearch(null, n);
            int n2 = n;
            if (n == 2 || n == 1) {
                n2 = 130;
            }
            View view2 = view;
            if (view != null) {
                view2 = view;
                if (view.isRootNamespace()) {
                    if (view.restoreFocusNotInCluster()) {
                        ViewRootImpl.this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(n));
                        return true;
                    }
                    view2 = ViewRootImpl.this.keyboardNavigationClusterSearch(null, n);
                }
            }
            if (view2 != null && view2.restoreFocusInCluster(n2)) {
                ViewRootImpl.this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(n));
                return true;
            }
            return false;
        }

        private int processGenericMotionEvent(QueuedInputEvent object) {
            object = (MotionEvent)((QueuedInputEvent)object).mEvent;
            if (((InputEvent)object).isFromSource(1048584) && ViewRootImpl.this.hasPointerCapture() && ViewRootImpl.this.mView.dispatchCapturedPointerEvent((MotionEvent)object)) {
                return 1;
            }
            return ViewRootImpl.this.mView.dispatchGenericMotionEvent((MotionEvent)object);
        }

        private int processKeyEvent(QueuedInputEvent queuedInputEvent) {
            int n;
            KeyEvent keyEvent = (KeyEvent)queuedInputEvent.mEvent;
            if (ViewRootImpl.this.mUnhandledKeyManager.preViewDispatch(keyEvent)) {
                return 1;
            }
            if (ViewRootImpl.this.mView.dispatchKeyEvent(keyEvent)) {
                return 1;
            }
            if (this.shouldDropInputEvent(queuedInputEvent)) {
                return 2;
            }
            if (ViewRootImpl.this.mUnhandledKeyManager.dispatch(ViewRootImpl.this.mView, keyEvent)) {
                return 1;
            }
            int n2 = n = 0;
            if (keyEvent.getAction() == 0) {
                n2 = n;
                if (keyEvent.getKeyCode() == 61) {
                    if (KeyEvent.metaStateHasModifiers(keyEvent.getMetaState(), 65536)) {
                        n2 = 2;
                    } else {
                        n2 = n;
                        if (KeyEvent.metaStateHasModifiers(keyEvent.getMetaState(), 65537)) {
                            n2 = 1;
                        }
                    }
                }
            }
            if (keyEvent.getAction() == 0 && !KeyEvent.metaStateHasNoModifiers(keyEvent.getMetaState()) && keyEvent.getRepeatCount() == 0 && !KeyEvent.isModifierKey(keyEvent.getKeyCode()) && n2 == 0) {
                if (ViewRootImpl.this.mView.dispatchKeyShortcutEvent(keyEvent)) {
                    return 1;
                }
                if (this.shouldDropInputEvent(queuedInputEvent)) {
                    return 2;
                }
            }
            if (ViewRootImpl.this.mFallbackEventHandler.dispatchKeyEvent(keyEvent)) {
                return 1;
            }
            if (this.shouldDropInputEvent(queuedInputEvent)) {
                return 2;
            }
            return keyEvent.getAction() == 0 && (n2 != 0 ? this.performKeyboardGroupNavigation(n2) : this.performFocusNavigation(keyEvent));
        }

        private int processPointerEvent(QueuedInputEvent object) {
            object = (MotionEvent)((QueuedInputEvent)object).mEvent;
            ViewRootImpl.this.mAttachInfo.mUnbufferedDispatchRequested = false;
            ViewRootImpl.this.mAttachInfo.mHandlingPointerEvent = true;
            int n = ViewRootImpl.this.mView.dispatchPointerEvent((MotionEvent)object);
            this.maybeUpdatePointerIcon((MotionEvent)object);
            ViewRootImpl.this.maybeUpdateTooltip((MotionEvent)object);
            ViewRootImpl.this.mAttachInfo.mHandlingPointerEvent = false;
            if (ViewRootImpl.this.mAttachInfo.mUnbufferedDispatchRequested && !ViewRootImpl.this.mUnbufferedInputDispatch) {
                object = ViewRootImpl.this;
                ((ViewRootImpl)object).mUnbufferedInputDispatch = true;
                if (((ViewRootImpl)object).mConsumeBatchedInputScheduled) {
                    ViewRootImpl.this.scheduleConsumeBatchedInputImmediately();
                }
            }
            return n;
        }

        private int processTrackballEvent(QueuedInputEvent object) {
            object = (MotionEvent)((QueuedInputEvent)object).mEvent;
            if (((InputEvent)object).isFromSource(131076) && (!ViewRootImpl.this.hasPointerCapture() || ViewRootImpl.this.mView.dispatchCapturedPointerEvent((MotionEvent)object))) {
                return 1;
            }
            return ViewRootImpl.this.mView.dispatchTrackballEvent((MotionEvent)object);
        }

        @Override
        protected void onDeliverToNext(QueuedInputEvent queuedInputEvent) {
            if (ViewRootImpl.this.mUnbufferedInputDispatch && queuedInputEvent.mEvent instanceof MotionEvent && ((MotionEvent)queuedInputEvent.mEvent).isTouchEvent() && ViewRootImpl.isTerminalInputEvent(queuedInputEvent.mEvent)) {
                ViewRootImpl viewRootImpl = ViewRootImpl.this;
                viewRootImpl.mUnbufferedInputDispatch = false;
                viewRootImpl.scheduleConsumeBatchedInput();
            }
            super.onDeliverToNext(queuedInputEvent);
        }

        @Override
        protected int onProcess(QueuedInputEvent queuedInputEvent) {
            if (queuedInputEvent.mEvent instanceof KeyEvent) {
                return this.processKeyEvent(queuedInputEvent);
            }
            int n = queuedInputEvent.mEvent.getSource();
            if ((n & 2) != 0) {
                return this.processPointerEvent(queuedInputEvent);
            }
            if ((n & 4) != 0) {
                return this.processTrackballEvent(queuedInputEvent);
            }
            return this.processGenericMotionEvent(queuedInputEvent);
        }
    }

    final class ViewPreImeInputStage
    extends InputStage {
        public ViewPreImeInputStage(InputStage inputStage) {
            super(inputStage);
        }

        private int processKeyEvent(QueuedInputEvent object) {
            object = (KeyEvent)((QueuedInputEvent)object).mEvent;
            return ViewRootImpl.this.mView.dispatchKeyEventPreIme((KeyEvent)object);
        }

        @Override
        protected int onProcess(QueuedInputEvent queuedInputEvent) {
            if (queuedInputEvent.mEvent instanceof KeyEvent) {
                return this.processKeyEvent(queuedInputEvent);
            }
            return 0;
        }
    }

    final class ViewRootHandler
    extends Handler {
        ViewRootHandler() {
        }

        @Override
        public String getMessageName(Message message) {
            switch (message.what) {
                default: {
                    return super.getMessageName(message);
                }
                case 32: {
                    return "MSG_SYSTEM_GESTURE_EXCLUSION_CHANGED";
                }
                case 31: {
                    return "MSG_INSETS_CONTROL_CHANGED";
                }
                case 30: {
                    return "MSG_INSETS_CHANGED";
                }
                case 29: {
                    return "MSG_DRAW_FINISHED";
                }
                case 28: {
                    return "MSG_POINTER_CAPTURE_CHANGED";
                }
                case 27: {
                    return "MSG_UPDATE_POINTER_ICON";
                }
                case 25: {
                    return "MSG_DISPATCH_WINDOW_SHOWN";
                }
                case 24: {
                    return "MSG_SYNTHESIZE_INPUT_EVENT";
                }
                case 23: {
                    return "MSG_WINDOW_MOVED";
                }
                case 21: {
                    return "MSG_CLEAR_ACCESSIBILITY_FOCUS_HOST";
                }
                case 19: {
                    return "MSG_PROCESS_INPUT_EVENTS";
                }
                case 18: {
                    return "MSG_UPDATE_CONFIGURATION";
                }
                case 17: {
                    return "MSG_DISPATCH_SYSTEM_UI_VISIBILITY";
                }
                case 16: {
                    return "MSG_DISPATCH_DRAG_LOCATION_EVENT";
                }
                case 15: {
                    return "MSG_DISPATCH_DRAG_EVENT";
                }
                case 14: {
                    return "MSG_CLOSE_SYSTEM_DIALOGS";
                }
                case 13: {
                    return "MSG_CHECK_FOCUS";
                }
                case 12: {
                    return "MSG_DISPATCH_KEY_FROM_AUTOFILL";
                }
                case 11: {
                    return "MSG_DISPATCH_KEY_FROM_IME";
                }
                case 9: {
                    return "MSG_DISPATCH_GET_NEW_SURFACE";
                }
                case 8: {
                    return "MSG_DISPATCH_APP_VISIBILITY";
                }
                case 7: {
                    return "MSG_DISPATCH_INPUT_EVENT";
                }
                case 6: {
                    return "MSG_WINDOW_FOCUS_CHANGED";
                }
                case 5: {
                    return "MSG_RESIZED_REPORT";
                }
                case 4: {
                    return "MSG_RESIZED";
                }
                case 3: {
                    return "MSG_DIE";
                }
                case 2: {
                    return "MSG_INVALIDATE_RECT";
                }
                case 1: 
            }
            return "MSG_INVALIDATE";
        }

        @Override
        public void handleMessage(Message object) {
            int n = ((Message)object).what;
            int n2 = -1;
            boolean bl = true;
            boolean bl2 = true;
            boolean bl3 = true;
            switch (n) {
                default: {
                    break;
                }
                case 32: {
                    ViewRootImpl.this.systemGestureExclusionChanged();
                    break;
                }
                case 31: {
                    object = (SomeArgs)((Message)object).obj;
                    ViewRootImpl.this.mInsetsController.onControlsChanged((InsetsSourceControl[])((SomeArgs)object).arg2);
                    ViewRootImpl.this.mInsetsController.onStateChanged((InsetsState)((SomeArgs)object).arg1);
                    break;
                }
                case 30: {
                    ViewRootImpl.this.mInsetsController.onStateChanged((InsetsState)((Message)object).obj);
                    break;
                }
                case 29: {
                    ViewRootImpl.this.pendingDrawFinished();
                    break;
                }
                case 28: {
                    if (((Message)object).arg1 == 0) {
                        bl3 = false;
                    }
                    ViewRootImpl.this.handlePointerCaptureChanged(bl3);
                    break;
                }
                case 27: {
                    object = (MotionEvent)((Message)object).obj;
                    ViewRootImpl.this.resetPointerIcon((MotionEvent)object);
                    break;
                }
                case 26: {
                    IResultReceiver iResultReceiver = (IResultReceiver)((Message)object).obj;
                    n2 = ((Message)object).arg1;
                    ViewRootImpl.this.handleRequestKeyboardShortcuts(iResultReceiver, n2);
                    break;
                }
                case 25: {
                    ViewRootImpl.this.handleDispatchWindowShown();
                    break;
                }
                case 24: {
                    object = (InputEvent)((Message)object).obj;
                    ViewRootImpl.this.enqueueInputEvent((InputEvent)object, null, 32, true);
                    break;
                }
                case 23: {
                    if (!ViewRootImpl.this.mAdded) break;
                    int n3 = ViewRootImpl.this.mWinFrame.width();
                    n = ViewRootImpl.this.mWinFrame.height();
                    int n4 = ((Message)object).arg1;
                    n2 = ((Message)object).arg2;
                    ViewRootImpl.this.mTmpFrame.left = n4;
                    ViewRootImpl.this.mTmpFrame.right = n4 + n3;
                    ViewRootImpl.this.mTmpFrame.top = n2;
                    ViewRootImpl.this.mTmpFrame.bottom = n2 + n;
                    object = ViewRootImpl.this;
                    ((ViewRootImpl)object).setFrame(((ViewRootImpl)object).mTmpFrame);
                    ViewRootImpl.this.mPendingBackDropFrame.set(ViewRootImpl.this.mWinFrame);
                    object = ViewRootImpl.this;
                    ((ViewRootImpl)object).maybeHandleWindowMove(((ViewRootImpl)object).mWinFrame);
                    break;
                }
                case 22: {
                    if (ViewRootImpl.this.mView == null) break;
                    object = ViewRootImpl.this;
                    ((ViewRootImpl)object).invalidateWorld(((ViewRootImpl)object).mView);
                    break;
                }
                case 21: {
                    ViewRootImpl.this.setAccessibilityFocus(null, null);
                    break;
                }
                case 19: {
                    object = ViewRootImpl.this;
                    ((ViewRootImpl)object).mProcessInputEventsScheduled = false;
                    ((ViewRootImpl)object).doProcessInputEvents();
                    break;
                }
                case 18: {
                    Configuration configuration = (Configuration)((Message)object).obj;
                    object = configuration;
                    if (configuration.isOtherSeqNewer(ViewRootImpl.this.mLastReportedMergedConfiguration.getMergedConfiguration())) {
                        object = ViewRootImpl.this.mLastReportedMergedConfiguration.getGlobalConfiguration();
                    }
                    ViewRootImpl.this.mPendingMergedConfiguration.setConfiguration((Configuration)object, ViewRootImpl.this.mLastReportedMergedConfiguration.getOverrideConfiguration());
                    object = ViewRootImpl.this;
                    ((ViewRootImpl)object).performConfigurationChange(((ViewRootImpl)object).mPendingMergedConfiguration, false, -1);
                    break;
                }
                case 17: {
                    ViewRootImpl.this.handleDispatchSystemUiVisibilityChanged((SystemUiVisibilityInfo)((Message)object).obj);
                    break;
                }
                case 15: 
                case 16: {
                    object = (DragEvent)((Message)object).obj;
                    ((DragEvent)object).mLocalState = ViewRootImpl.this.mLocalDragState;
                    ViewRootImpl.this.handleDragEvent((DragEvent)object);
                    break;
                }
                case 14: {
                    if (ViewRootImpl.this.mView == null) break;
                    ViewRootImpl.this.mView.onCloseSystemDialogs((String)((Message)object).obj);
                    break;
                }
                case 13: {
                    object = ViewRootImpl.this.mContext.getSystemService(InputMethodManager.class);
                    if (object == null) break;
                    ((InputMethodManager)object).checkFocus();
                    break;
                }
                case 12: {
                    object = (KeyEvent)((Message)object).obj;
                    ViewRootImpl.this.enqueueInputEvent((InputEvent)object, null, 0, true);
                    break;
                }
                case 11: {
                    KeyEvent keyEvent = (KeyEvent)((Message)object).obj;
                    object = keyEvent;
                    if ((keyEvent.getFlags() & 8) != 0) {
                        object = KeyEvent.changeFlags(keyEvent, keyEvent.getFlags() & -9);
                    }
                    ViewRootImpl.this.enqueueInputEvent((InputEvent)object, null, 1, true);
                    break;
                }
                case 9: {
                    ViewRootImpl.this.handleGetNewSurface();
                    break;
                }
                case 8: {
                    ViewRootImpl viewRootImpl = ViewRootImpl.this;
                    bl3 = ((Message)object).arg1 != 0 ? bl : false;
                    viewRootImpl.handleAppVisibility(bl3);
                    break;
                }
                case 7: {
                    object = (SomeArgs)((Message)object).obj;
                    InputEvent inputEvent = (InputEvent)((SomeArgs)object).arg1;
                    InputEventReceiver inputEventReceiver = (InputEventReceiver)((SomeArgs)object).arg2;
                    ViewRootImpl.this.enqueueInputEvent(inputEvent, inputEventReceiver, 0, true);
                    ((SomeArgs)object).recycle();
                    break;
                }
                case 6: {
                    ViewRootImpl.this.handleWindowFocusChanged();
                    break;
                }
                case 4: {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    if (ViewRootImpl.this.mWinFrame.equals(someArgs.arg1) && ViewRootImpl.this.mPendingOverscanInsets.equals(someArgs.arg5) && ViewRootImpl.this.mPendingContentInsets.equals(someArgs.arg2) && ViewRootImpl.this.mPendingStableInsets.equals(someArgs.arg6) && ViewRootImpl.this.mPendingDisplayCutout.get().equals(someArgs.arg9) && ViewRootImpl.this.mPendingVisibleInsets.equals(someArgs.arg3) && ViewRootImpl.this.mPendingOutsets.equals(someArgs.arg7) && ViewRootImpl.this.mPendingBackDropFrame.equals(someArgs.arg8) && someArgs.arg4 == null && someArgs.argi1 == 0 && ViewRootImpl.this.mDisplay.getDisplayId() == someArgs.argi3) break;
                }
                case 5: {
                    if (!ViewRootImpl.this.mAdded) break;
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    int n5 = someArgs.argi3;
                    Object object2 = (MergedConfiguration)someArgs.arg4;
                    n = ViewRootImpl.this.mDisplay.getDisplayId() != n5 ? 1 : 0;
                    int n6 = 0;
                    if (!ViewRootImpl.this.mLastReportedMergedConfiguration.equals(object2)) {
                        ViewRootImpl viewRootImpl = ViewRootImpl.this;
                        if (n != 0) {
                            n2 = n5;
                        }
                        viewRootImpl.performConfigurationChange((MergedConfiguration)object2, false, n2);
                        n2 = 1;
                    } else {
                        n2 = n6;
                        if (n != 0) {
                            object2 = ViewRootImpl.this;
                            ((ViewRootImpl)object2).onMovedToDisplay(n5, ((ViewRootImpl)object2).mLastConfigurationFromResources);
                            n2 = n6;
                        }
                    }
                    n = ViewRootImpl.this.mWinFrame.equals(someArgs.arg1) && ViewRootImpl.this.mPendingOverscanInsets.equals(someArgs.arg5) && ViewRootImpl.this.mPendingContentInsets.equals(someArgs.arg2) && ViewRootImpl.this.mPendingStableInsets.equals(someArgs.arg6) && ViewRootImpl.this.mPendingDisplayCutout.get().equals(someArgs.arg9) && ViewRootImpl.this.mPendingVisibleInsets.equals(someArgs.arg3) && ViewRootImpl.this.mPendingOutsets.equals(someArgs.arg7) ? 0 : 1;
                    ViewRootImpl.this.setFrame((Rect)someArgs.arg1);
                    ViewRootImpl.this.mPendingOverscanInsets.set((Rect)someArgs.arg5);
                    ViewRootImpl.this.mPendingContentInsets.set((Rect)someArgs.arg2);
                    ViewRootImpl.this.mPendingStableInsets.set((Rect)someArgs.arg6);
                    ViewRootImpl.this.mPendingDisplayCutout.set((DisplayCutout)someArgs.arg9);
                    ViewRootImpl.this.mPendingVisibleInsets.set((Rect)someArgs.arg3);
                    ViewRootImpl.this.mPendingOutsets.set((Rect)someArgs.arg7);
                    ViewRootImpl.this.mPendingBackDropFrame.set((Rect)someArgs.arg8);
                    object2 = ViewRootImpl.this;
                    bl3 = someArgs.argi1 != 0;
                    ((ViewRootImpl)object2).mForceNextWindowRelayout = bl3;
                    object2 = ViewRootImpl.this;
                    bl3 = someArgs.argi2 != 0 ? bl2 : false;
                    ((ViewRootImpl)object2).mPendingAlwaysConsumeSystemBars = bl3;
                    someArgs.recycle();
                    if (((Message)object).what == 5) {
                        ViewRootImpl.this.reportNextDraw();
                    }
                    if (ViewRootImpl.this.mView != null && (n != 0 || n2 != 0)) {
                        ViewRootImpl.forceLayout(ViewRootImpl.this.mView);
                    }
                    ViewRootImpl.this.requestLayout();
                    break;
                }
                case 3: {
                    ViewRootImpl.this.doDie();
                    break;
                }
                case 2: {
                    object = (View.AttachInfo.InvalidateInfo)((Message)object).obj;
                    ((View.AttachInfo.InvalidateInfo)object).target.invalidate(((View.AttachInfo.InvalidateInfo)object).left, ((View.AttachInfo.InvalidateInfo)object).top, ((View.AttachInfo.InvalidateInfo)object).right, ((View.AttachInfo.InvalidateInfo)object).bottom);
                    ((View.AttachInfo.InvalidateInfo)object).recycle();
                    break;
                }
                case 1: {
                    ((View)((Message)object).obj).invalidate();
                }
            }
        }

        @Override
        public boolean sendMessageAtTime(Message message, long l) {
            if (message.what == 26 && message.obj == null) {
                throw new NullPointerException("Attempted to call MSG_REQUEST_KEYBOARD_SHORTCUTS with null receiver:");
            }
            return super.sendMessageAtTime(message, l);
        }
    }

    static class W
    extends IWindow.Stub {
        private final WeakReference<ViewRootImpl> mViewAncestor;
        private final IWindowSession mWindowSession;

        W(ViewRootImpl viewRootImpl) {
            this.mViewAncestor = new WeakReference<ViewRootImpl>(viewRootImpl);
            this.mWindowSession = viewRootImpl.mWindowSession;
        }

        private static int checkCallingPermission(String string2) {
            try {
                int n = ActivityManager.getService().checkPermission(string2, Binder.getCallingPid(), Binder.getCallingUid());
                return n;
            }
            catch (RemoteException remoteException) {
                return -1;
            }
        }

        @Override
        public void closeSystemDialogs(String string2) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchCloseSystemDialogs(string2);
            }
        }

        @Override
        public void dispatchAppVisibility(boolean bl) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchAppVisibility(bl);
            }
        }

        @Override
        public void dispatchDragEvent(DragEvent dragEvent) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchDragEvent(dragEvent);
            }
        }

        @Override
        public void dispatchGetNewSurface() {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchGetNewSurface();
            }
        }

        @Override
        public void dispatchPointerCaptureChanged(boolean bl) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchPointerCaptureChanged(bl);
            }
        }

        @Override
        public void dispatchSystemUiVisibilityChanged(int n, int n2, int n3, int n4) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchSystemUiVisibilityChanged(n, n2, n3, n4);
            }
        }

        @Override
        public void dispatchWallpaperCommand(String string2, int n, int n2, int n3, Bundle bundle, boolean bl) {
            if (bl) {
                try {
                    this.mWindowSession.wallpaperCommandComplete(this.asBinder(), null);
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
        }

        @Override
        public void dispatchWallpaperOffsets(float f, float f2, float f3, float f4, boolean bl) {
            if (bl) {
                try {
                    this.mWindowSession.wallpaperOffsetsComplete(this.asBinder());
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
            }
        }

        @Override
        public void dispatchWindowShown() {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchWindowShown();
            }
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void executeCommand(String charSequence, String string2, ParcelFileDescriptor closeable) {
            Throwable throwable2222;
            Object object = (ViewRootImpl)this.mViewAncestor.get();
            if (object == null) return;
            View view = ((ViewRootImpl)object).mView;
            if (view == null) return;
            if (W.checkCallingPermission("android.permission.DUMP") != 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Insufficient permissions to invoke executeCommand() from pid=");
                ((StringBuilder)charSequence).append(Binder.getCallingPid());
                ((StringBuilder)charSequence).append(", uid=");
                ((StringBuilder)charSequence).append(Binder.getCallingUid());
                throw new SecurityException(((StringBuilder)charSequence).toString());
            }
            OutputStream outputStream = null;
            Object var7_11 = null;
            object = var7_11;
            Closeable closeable2 = outputStream;
            object = var7_11;
            closeable2 = outputStream;
            ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream((ParcelFileDescriptor)closeable);
            closeable = autoCloseOutputStream;
            object = closeable;
            closeable2 = closeable;
            ViewDebug.dispatchCommand(view, (String)charSequence, string2, (OutputStream)closeable);
            ((OutputStream)closeable).close();
            return;
            {
                catch (IOException iOException) {
                    iOException.printStackTrace();
                    return;
                }
                catch (Throwable throwable2222) {
                }
                catch (IOException iOException) {}
                object = closeable2;
                {
                    iOException.printStackTrace();
                    if (closeable2 == null) return;
                }
                closeable2.close();
                return;
            }
            if (object == null) throw throwable2222;
            try {
                ((OutputStream)object).close();
                throw throwable2222;
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
            throw throwable2222;
        }

        @Override
        public void insetsChanged(InsetsState insetsState) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchInsetsChanged(insetsState);
            }
        }

        @Override
        public void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] arrinsetsSourceControl) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchInsetsControlChanged(insetsState, arrinsetsSourceControl);
            }
        }

        @Override
        public void moved(int n, int n2) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchMoved(n, n2);
            }
        }

        @Override
        public void requestAppKeyboardShortcuts(IResultReceiver iResultReceiver, int n) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchRequestKeyboardShortcuts(iResultReceiver, n);
            }
        }

        @Override
        public void resized(Rect rect, Rect rect2, Rect rect3, Rect rect4, Rect rect5, Rect rect6, boolean bl, MergedConfiguration mergedConfiguration, Rect rect7, boolean bl2, boolean bl3, int n, DisplayCutout.ParcelableWrapper parcelableWrapper) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchResized(rect, rect2, rect3, rect4, rect5, rect6, bl, mergedConfiguration, rect7, bl2, bl3, n, parcelableWrapper);
            }
        }

        @Override
        public void updatePointerIcon(float f, float f2) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.updatePointerIcon(f, f2);
            }
        }

        @Override
        public void windowFocusChanged(boolean bl, boolean bl2) {
            ViewRootImpl viewRootImpl = (ViewRootImpl)this.mViewAncestor.get();
            if (viewRootImpl != null) {
                viewRootImpl.windowFocusChanged(bl, bl2);
            }
        }
    }

    final class WindowInputEventReceiver
    extends InputEventReceiver {
        public WindowInputEventReceiver(InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
        }

        @Override
        public void dispose() {
            ViewRootImpl.this.unscheduleConsumeBatchedInput();
            super.dispose();
        }

        @Override
        public void onBatchedInputEventPending() {
            if (ViewRootImpl.this.mUnbufferedInputDispatch) {
                super.onBatchedInputEventPending();
            } else {
                ViewRootImpl.this.scheduleConsumeBatchedInput();
            }
        }

        @Override
        public void onInputEvent(InputEvent inputEvent) {
            block6 : {
                block4 : {
                    List<InputEvent> list;
                    block5 : {
                        Trace.traceBegin(8L, "processInputEventForCompatibility");
                        list = ViewRootImpl.this.mInputCompatProcessor.processInputEventForCompatibility(inputEvent);
                        if (list == null) break block4;
                        if (!list.isEmpty()) break block5;
                        this.finishInputEvent(inputEvent, true);
                        break block6;
                    }
                    for (int i = 0; i < list.size(); ++i) {
                        ViewRootImpl.this.enqueueInputEvent(list.get(i), this, 64, true);
                    }
                    break block6;
                }
                ViewRootImpl.this.enqueueInputEvent(inputEvent, this, 0, true);
            }
            return;
            finally {
                Trace.traceEnd(8L);
            }
        }
    }

    static interface WindowStoppedCallback {
        public void windowStopped(boolean var1);
    }

}

