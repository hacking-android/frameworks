/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.ActionMode;
import android.view.FrameMetrics;
import android.view.InputEvent;
import android.view.InputQueue;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.WindowManagerImpl;
import android.view.accessibility.AccessibilityEvent;
import com.android.internal.R;
import java.util.Collections;
import java.util.List;

public abstract class Window {
    public static final int DECOR_CAPTION_SHADE_AUTO = 0;
    public static final int DECOR_CAPTION_SHADE_DARK = 2;
    public static final int DECOR_CAPTION_SHADE_LIGHT = 1;
    @Deprecated
    protected static final int DEFAULT_FEATURES = 65;
    public static final int FEATURE_ACTION_BAR = 8;
    public static final int FEATURE_ACTION_BAR_OVERLAY = 9;
    public static final int FEATURE_ACTION_MODE_OVERLAY = 10;
    public static final int FEATURE_ACTIVITY_TRANSITIONS = 13;
    public static final int FEATURE_CONTENT_TRANSITIONS = 12;
    public static final int FEATURE_CONTEXT_MENU = 6;
    public static final int FEATURE_CUSTOM_TITLE = 7;
    @Deprecated
    public static final int FEATURE_INDETERMINATE_PROGRESS = 5;
    public static final int FEATURE_LEFT_ICON = 3;
    @UnsupportedAppUsage
    public static final int FEATURE_MAX = 13;
    public static final int FEATURE_NO_TITLE = 1;
    public static final int FEATURE_OPTIONS_PANEL = 0;
    @Deprecated
    public static final int FEATURE_PROGRESS = 2;
    public static final int FEATURE_RIGHT_ICON = 4;
    public static final int FEATURE_SWIPE_TO_DISMISS = 11;
    public static final int ID_ANDROID_CONTENT = 16908290;
    public static final String NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME = "android:navigation:background";
    @Deprecated
    public static final int PROGRESS_END = 10000;
    @Deprecated
    public static final int PROGRESS_INDETERMINATE_OFF = -4;
    @Deprecated
    public static final int PROGRESS_INDETERMINATE_ON = -3;
    @Deprecated
    public static final int PROGRESS_SECONDARY_END = 30000;
    @Deprecated
    public static final int PROGRESS_SECONDARY_START = 20000;
    @Deprecated
    public static final int PROGRESS_START = 0;
    @Deprecated
    public static final int PROGRESS_VISIBILITY_OFF = -2;
    @Deprecated
    public static final int PROGRESS_VISIBILITY_ON = -1;
    public static final String STATUS_BAR_BACKGROUND_TRANSITION_NAME = "android:status:background";
    private Window mActiveChild;
    @UnsupportedAppUsage
    private String mAppName;
    @UnsupportedAppUsage
    private IBinder mAppToken;
    @UnsupportedAppUsage
    private Callback mCallback;
    private boolean mCloseOnSwipeEnabled = false;
    private boolean mCloseOnTouchOutside = false;
    private Window mContainer;
    @UnsupportedAppUsage
    private final Context mContext;
    private int mDefaultWindowFormat = -1;
    @UnsupportedAppUsage
    private boolean mDestroyed;
    @UnsupportedAppUsage
    private int mFeatures;
    private int mForcedWindowFlags = 0;
    @UnsupportedAppUsage
    private boolean mHardwareAccelerated;
    private boolean mHasChildren = false;
    private boolean mHasSoftInputMode = false;
    private boolean mHaveDimAmount = false;
    private boolean mHaveWindowFormat = false;
    private boolean mIsActive = false;
    @UnsupportedAppUsage
    private int mLocalFeatures;
    private OnRestrictedCaptionAreaChangedListener mOnRestrictedCaptionAreaChangedListener;
    private OnWindowDismissedCallback mOnWindowDismissedCallback;
    private OnWindowSwipeDismissedCallback mOnWindowSwipeDismissedCallback;
    private boolean mOverlayWithDecorCaptionEnabled = false;
    private Rect mRestrictedCaptionAreaRect;
    private boolean mSetCloseOnTouchOutside = false;
    @UnsupportedAppUsage
    private final WindowManager.LayoutParams mWindowAttributes = new WindowManager.LayoutParams();
    private WindowControllerCallback mWindowControllerCallback;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private WindowManager mWindowManager;
    @UnsupportedAppUsage
    private TypedArray mWindowStyle;

    public Window(Context context) {
        int n;
        this.mContext = context;
        this.mLocalFeatures = n = Window.getDefaultFeatures(context);
        this.mFeatures = n;
    }

    public static int getDefaultFeatures(Context object) {
        int n = 0;
        if (((Resources)(object = ((Context)object).getResources())).getBoolean(17891400)) {
            n = false | true;
        }
        int n2 = n;
        if (((Resources)object).getBoolean(17891399)) {
            n2 = n | 64;
        }
        return n2;
    }

    private boolean isOutOfBounds(Context object, MotionEvent motionEvent) {
        int n = (int)motionEvent.getX();
        int n2 = (int)motionEvent.getY();
        int n3 = ViewConfiguration.get((Context)object).getScaledWindowTouchSlop();
        object = this.getDecorView();
        boolean bl = n < -n3 || n2 < -n3 || n > ((View)object).getWidth() + n3 || n2 > ((View)object).getHeight() + n3;
        return bl;
    }

    private void setPrivateFlags(int n, int n2) {
        WindowManager.LayoutParams layoutParams = this.getAttributes();
        layoutParams.privateFlags = layoutParams.privateFlags & n2 | n & n2;
        this.dispatchWindowAttributesChanged(layoutParams);
    }

    public abstract void addContentView(View var1, ViewGroup.LayoutParams var2);

    public void addFlags(int n) {
        this.setFlags(n, n);
    }

    public final void addOnFrameMetricsAvailableListener(OnFrameMetricsAvailableListener onFrameMetricsAvailableListener, Handler handler) {
        View view = this.getDecorView();
        if (view != null) {
            if (onFrameMetricsAvailableListener != null) {
                view.addFrameMetricsListener(this, onFrameMetricsAvailableListener, handler);
                return;
            }
            throw new NullPointerException("listener cannot be null");
        }
        throw new IllegalStateException("can't observe a Window without an attached view");
    }

    @UnsupportedAppUsage
    public void addPrivateFlags(int n) {
        this.setPrivateFlags(n, n);
    }

    @SystemApi
    public void addSystemFlags(int n) {
        this.addPrivateFlags(n);
    }

    void adjustLayoutParamsForSubWindow(WindowManager.LayoutParams layoutParams) {
        CharSequence charSequence = layoutParams.getTitle();
        if (layoutParams.type >= 1000 && layoutParams.type <= 1999) {
            Object object;
            if (layoutParams.token == null && (object = this.peekDecorView()) != null) {
                layoutParams.token = ((View)object).getWindowToken();
            }
            if (charSequence == null || charSequence.length() == 0) {
                object = new StringBuilder(32);
                if (layoutParams.type == 1001) {
                    ((StringBuilder)object).append("Media");
                } else if (layoutParams.type == 1004) {
                    ((StringBuilder)object).append("MediaOvr");
                } else if (layoutParams.type == 1000) {
                    ((StringBuilder)object).append("Panel");
                } else if (layoutParams.type == 1002) {
                    ((StringBuilder)object).append("SubPanel");
                } else if (layoutParams.type == 1005) {
                    ((StringBuilder)object).append("AboveSubPanel");
                } else if (layoutParams.type == 1003) {
                    ((StringBuilder)object).append("AtchDlg");
                } else {
                    ((StringBuilder)object).append(layoutParams.type);
                }
                if (this.mAppName != null) {
                    ((StringBuilder)object).append(":");
                    ((StringBuilder)object).append(this.mAppName);
                }
                layoutParams.setTitle((CharSequence)object);
            }
        } else if (layoutParams.type >= 2000 && layoutParams.type <= 2999) {
            if (charSequence == null || charSequence.length() == 0) {
                StringBuilder stringBuilder = new StringBuilder(32);
                stringBuilder.append("Sys");
                stringBuilder.append(layoutParams.type);
                if (this.mAppName != null) {
                    stringBuilder.append(":");
                    stringBuilder.append(this.mAppName);
                }
                layoutParams.setTitle(stringBuilder);
            }
        } else {
            Object object;
            if (layoutParams.token == null) {
                object = this.mContainer;
                object = object == null ? this.mAppToken : ((Window)object).mAppToken;
                layoutParams.token = object;
            }
            if ((charSequence == null || charSequence.length() == 0) && (object = this.mAppName) != null) {
                layoutParams.setTitle((CharSequence)object);
            }
        }
        if (layoutParams.packageName == null) {
            layoutParams.packageName = this.mContext.getPackageName();
        }
        if (this.mHardwareAccelerated || (this.mWindowAttributes.flags & 16777216) != 0) {
            layoutParams.flags |= 16777216;
        }
    }

    @UnsupportedAppUsage
    public abstract void alwaysReadCloseOnTouchAttr();

    public abstract void clearContentView();

    public void clearFlags(int n) {
        this.setFlags(0, n);
    }

    public abstract void closeAllPanels();

    public abstract void closePanel(int var1);

    public final void destroy() {
        this.mDestroyed = true;
    }

    public final void dispatchOnWindowDismissed(boolean bl, boolean bl2) {
        OnWindowDismissedCallback onWindowDismissedCallback = this.mOnWindowDismissedCallback;
        if (onWindowDismissedCallback != null) {
            onWindowDismissedCallback.onWindowDismissed(bl, bl2);
        }
    }

    public final void dispatchOnWindowSwipeDismissed() {
        OnWindowSwipeDismissedCallback onWindowSwipeDismissedCallback = this.mOnWindowSwipeDismissedCallback;
        if (onWindowSwipeDismissedCallback != null) {
            onWindowSwipeDismissedCallback.onWindowSwipeDismissed();
        }
    }

    protected void dispatchWindowAttributesChanged(WindowManager.LayoutParams layoutParams) {
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onWindowAttributesChanged(layoutParams);
        }
    }

    public <T extends View> T findViewById(int n) {
        return this.getDecorView().findViewById(n);
    }

    public boolean getAllowEnterTransitionOverlap() {
        return true;
    }

    public boolean getAllowReturnTransitionOverlap() {
        return true;
    }

    public final WindowManager.LayoutParams getAttributes() {
        return this.mWindowAttributes;
    }

    public final Callback getCallback() {
        return this.mCallback;
    }

    public int getColorMode() {
        return this.getAttributes().getColorMode();
    }

    public final Window getContainer() {
        return this.mContainer;
    }

    public Scene getContentScene() {
        return null;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public abstract View getCurrentFocus();

    public abstract View getDecorView();

    public float getElevation() {
        return 0.0f;
    }

    public Transition getEnterTransition() {
        return null;
    }

    public Transition getExitTransition() {
        return null;
    }

    protected final int getFeatures() {
        return this.mFeatures;
    }

    protected final int getForcedWindowFlags() {
        return this.mForcedWindowFlags;
    }

    public abstract WindowInsetsController getInsetsController();

    public abstract LayoutInflater getLayoutInflater();

    protected final int getLocalFeatures() {
        return this.mLocalFeatures;
    }

    public MediaController getMediaController() {
        return null;
    }

    public abstract int getNavigationBarColor();

    public int getNavigationBarDividerColor() {
        return 0;
    }

    public Transition getReenterTransition() {
        return null;
    }

    public Transition getReturnTransition() {
        return null;
    }

    public Transition getSharedElementEnterTransition() {
        return null;
    }

    public Transition getSharedElementExitTransition() {
        return null;
    }

    public Transition getSharedElementReenterTransition() {
        return null;
    }

    public Transition getSharedElementReturnTransition() {
        return null;
    }

    public boolean getSharedElementsUseOverlay() {
        return true;
    }

    public abstract int getStatusBarColor();

    public List<Rect> getSystemGestureExclusionRects() {
        return Collections.emptyList();
    }

    public long getTransitionBackgroundFadeDuration() {
        return 0L;
    }

    public TransitionManager getTransitionManager() {
        return null;
    }

    public abstract int getVolumeControlStream();

    public final WindowControllerCallback getWindowControllerCallback() {
        return this.mWindowControllerCallback;
    }

    public WindowManager getWindowManager() {
        return this.mWindowManager;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final TypedArray getWindowStyle() {
        synchronized (this) {
            if (this.mWindowStyle != null) return this.mWindowStyle;
            this.mWindowStyle = this.mContext.obtainStyledAttributes(R.styleable.Window);
            return this.mWindowStyle;
        }
    }

    public final boolean hasChildren() {
        return this.mHasChildren;
    }

    public boolean hasFeature(int n) {
        int n2 = this.getFeatures();
        boolean bl = true;
        if ((n2 & 1 << n) == 0) {
            bl = false;
        }
        return bl;
    }

    protected final boolean hasSoftInputMode() {
        return this.mHasSoftInputMode;
    }

    protected boolean haveDimAmount() {
        return this.mHaveDimAmount;
    }

    public void injectInputEvent(InputEvent inputEvent) {
    }

    public abstract void invalidatePanelMenu(int var1);

    public final boolean isActive() {
        return this.mIsActive;
    }

    public boolean isCloseOnSwipeEnabled() {
        return this.mCloseOnSwipeEnabled;
    }

    @UnsupportedAppUsage
    public final boolean isDestroyed() {
        return this.mDestroyed;
    }

    public abstract boolean isFloating();

    public boolean isNavigationBarContrastEnforced() {
        return false;
    }

    public boolean isOverlayWithDecorCaptionEnabled() {
        return this.mOverlayWithDecorCaptionEnabled;
    }

    public abstract boolean isShortcutKey(int var1, KeyEvent var2);

    public boolean isStatusBarContrastEnforced() {
        return false;
    }

    public boolean isWideColorGamut() {
        int n = this.getColorMode();
        boolean bl = true;
        if (n != 1 || !this.getContext().getResources().getConfiguration().isScreenWideColorGamut()) {
            bl = false;
        }
        return bl;
    }

    public final void makeActive() {
        Window window = this.mContainer;
        if (window != null) {
            window = window.mActiveChild;
            if (window != null) {
                window.mIsActive = false;
            }
            this.mContainer.mActiveChild = this;
        }
        this.mIsActive = true;
        this.onActive();
    }

    public void notifyRestrictedCaptionAreaCallback(int n, int n2, int n3, int n4) {
        if (this.mOnRestrictedCaptionAreaChangedListener != null) {
            this.mRestrictedCaptionAreaRect.set(n, n2, n3, n4);
            this.mOnRestrictedCaptionAreaChangedListener.onRestrictedCaptionAreaChanged(this.mRestrictedCaptionAreaRect);
        }
    }

    protected abstract void onActive();

    public abstract void onConfigurationChanged(Configuration var1);

    public abstract void onMultiWindowModeChanged();

    public abstract void onPictureInPictureModeChanged(boolean var1);

    public abstract void openPanel(int var1, KeyEvent var2);

    public abstract View peekDecorView();

    public abstract boolean performContextMenuIdentifierAction(int var1, int var2);

    public abstract boolean performPanelIdentifierAction(int var1, int var2, int var3);

    public abstract boolean performPanelShortcut(int var1, int var2, KeyEvent var3, int var4);

    protected void removeFeature(int n) {
        n = 1 << n;
        this.mFeatures &= n;
        int n2 = this.mLocalFeatures;
        Window window = this.mContainer;
        if (window != null) {
            n = window.mFeatures & n;
        }
        this.mLocalFeatures = n2 & n;
    }

    public final void removeOnFrameMetricsAvailableListener(OnFrameMetricsAvailableListener onFrameMetricsAvailableListener) {
        if (this.getDecorView() != null) {
            this.getDecorView().removeFrameMetricsListener(onFrameMetricsAvailableListener);
        }
    }

    public abstract void reportActivityRelaunched();

    public boolean requestFeature(int n) {
        boolean bl = true;
        int n2 = 1 << n;
        this.mFeatures |= n2;
        int n3 = this.mLocalFeatures;
        Window window = this.mContainer;
        n = window != null ? window.mFeatures & n2 : n2;
        this.mLocalFeatures = n3 | n;
        if ((this.mFeatures & n2) == 0) {
            bl = false;
        }
        return bl;
    }

    public final <T extends View> T requireViewById(int n) {
        T t = this.findViewById(n);
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this Window");
    }

    public abstract void restoreHierarchyState(Bundle var1);

    public abstract Bundle saveHierarchyState();

    public void setAllowEnterTransitionOverlap(boolean bl) {
    }

    public void setAllowReturnTransitionOverlap(boolean bl) {
    }

    public void setAttributes(WindowManager.LayoutParams layoutParams) {
        this.mWindowAttributes.copyFrom(layoutParams);
        this.dispatchWindowAttributesChanged(this.mWindowAttributes);
    }

    public abstract void setBackgroundDrawable(Drawable var1);

    public void setBackgroundDrawableResource(int n) {
        this.setBackgroundDrawable(this.mContext.getDrawable(n));
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public abstract void setChildDrawable(int var1, Drawable var2);

    public abstract void setChildInt(int var1, int var2);

    public void setClipToOutline(boolean bl) {
    }

    public void setCloseOnSwipeEnabled(boolean bl) {
        this.mCloseOnSwipeEnabled = bl;
    }

    @UnsupportedAppUsage
    public void setCloseOnTouchOutside(boolean bl) {
        this.mCloseOnTouchOutside = bl;
        this.mSetCloseOnTouchOutside = true;
    }

    @UnsupportedAppUsage
    public void setCloseOnTouchOutsideIfNotSet(boolean bl) {
        if (!this.mSetCloseOnTouchOutside) {
            this.mCloseOnTouchOutside = bl;
            this.mSetCloseOnTouchOutside = true;
        }
    }

    public void setColorMode(int n) {
        WindowManager.LayoutParams layoutParams = this.getAttributes();
        layoutParams.setColorMode(n);
        this.dispatchWindowAttributesChanged(layoutParams);
    }

    public void setContainer(Window window) {
        this.mContainer = window;
        if (window != null) {
            this.mFeatures |= 2;
            this.mLocalFeatures |= 2;
            window.mHasChildren = true;
        }
    }

    public abstract void setContentView(int var1);

    public abstract void setContentView(View var1);

    public abstract void setContentView(View var1, ViewGroup.LayoutParams var2);

    public abstract void setDecorCaptionShade(int var1);

    public void setDefaultIcon(int n) {
    }

    public void setDefaultLogo(int n) {
    }

    protected void setDefaultWindowFormat(int n) {
        this.mDefaultWindowFormat = n;
        if (!this.mHaveWindowFormat) {
            WindowManager.LayoutParams layoutParams = this.getAttributes();
            layoutParams.format = n;
            this.dispatchWindowAttributesChanged(layoutParams);
        }
    }

    public void setDimAmount(float f) {
        WindowManager.LayoutParams layoutParams = this.getAttributes();
        layoutParams.dimAmount = f;
        this.mHaveDimAmount = true;
        this.dispatchWindowAttributesChanged(layoutParams);
    }

    public void setElevation(float f) {
    }

    public void setEnterTransition(Transition transition2) {
    }

    public void setExitTransition(Transition transition2) {
    }

    public abstract void setFeatureDrawable(int var1, Drawable var2);

    public abstract void setFeatureDrawableAlpha(int var1, int var2);

    public abstract void setFeatureDrawableResource(int var1, int var2);

    public abstract void setFeatureDrawableUri(int var1, Uri var2);

    public abstract void setFeatureInt(int var1, int var2);

    public void setFlags(int n, int n2) {
        WindowManager.LayoutParams layoutParams = this.getAttributes();
        layoutParams.flags = layoutParams.flags & n2 | n & n2;
        this.mForcedWindowFlags |= n2;
        this.dispatchWindowAttributesChanged(layoutParams);
    }

    public void setFormat(int n) {
        WindowManager.LayoutParams layoutParams = this.getAttributes();
        if (n != 0) {
            layoutParams.format = n;
            this.mHaveWindowFormat = true;
        } else {
            layoutParams.format = this.mDefaultWindowFormat;
            this.mHaveWindowFormat = false;
        }
        this.dispatchWindowAttributesChanged(layoutParams);
    }

    public void setGravity(int n) {
        WindowManager.LayoutParams layoutParams = this.getAttributes();
        layoutParams.gravity = n;
        this.dispatchWindowAttributesChanged(layoutParams);
    }

    public void setIcon(int n) {
    }

    public void setLayout(int n, int n2) {
        WindowManager.LayoutParams layoutParams = this.getAttributes();
        layoutParams.width = n;
        layoutParams.height = n2;
        this.dispatchWindowAttributesChanged(layoutParams);
    }

    public void setLocalFocus(boolean bl, boolean bl2) {
    }

    public void setLogo(int n) {
    }

    public void setMediaController(MediaController mediaController) {
    }

    public abstract void setNavigationBarColor(int var1);

    public void setNavigationBarContrastEnforced(boolean bl) {
    }

    public void setNavigationBarDividerColor(int n) {
    }

    @UnsupportedAppUsage
    protected void setNeedsMenuKey(int n) {
        WindowManager.LayoutParams layoutParams = this.getAttributes();
        layoutParams.needsMenuKey = n;
        this.dispatchWindowAttributesChanged(layoutParams);
    }

    public final void setOnWindowDismissedCallback(OnWindowDismissedCallback onWindowDismissedCallback) {
        this.mOnWindowDismissedCallback = onWindowDismissedCallback;
    }

    public final void setOnWindowSwipeDismissedCallback(OnWindowSwipeDismissedCallback onWindowSwipeDismissedCallback) {
        this.mOnWindowSwipeDismissedCallback = onWindowSwipeDismissedCallback;
    }

    public void setOverlayWithDecorCaptionEnabled(boolean bl) {
        this.mOverlayWithDecorCaptionEnabled = bl;
    }

    public void setReenterTransition(Transition transition2) {
    }

    public abstract void setResizingCaptionDrawable(Drawable var1);

    public final void setRestrictedCaptionAreaListener(OnRestrictedCaptionAreaChangedListener object) {
        this.mOnRestrictedCaptionAreaChangedListener = object;
        object = object != null ? new Rect() : null;
        this.mRestrictedCaptionAreaRect = object;
    }

    public void setReturnTransition(Transition transition2) {
    }

    public void setSharedElementEnterTransition(Transition transition2) {
    }

    public void setSharedElementExitTransition(Transition transition2) {
    }

    public void setSharedElementReenterTransition(Transition transition2) {
    }

    public void setSharedElementReturnTransition(Transition transition2) {
    }

    public void setSharedElementsUseOverlay(boolean bl) {
    }

    public void setSoftInputMode(int n) {
        WindowManager.LayoutParams layoutParams = this.getAttributes();
        if (n != 0) {
            layoutParams.softInputMode = n;
            this.mHasSoftInputMode = true;
        } else {
            this.mHasSoftInputMode = false;
        }
        this.dispatchWindowAttributesChanged(layoutParams);
    }

    public abstract void setStatusBarColor(int var1);

    public void setStatusBarContrastEnforced(boolean bl) {
    }

    public void setSustainedPerformanceMode(boolean bl) {
        int n = bl ? 262144 : 0;
        this.setPrivateFlags(n, 262144);
    }

    public void setSystemGestureExclusionRects(List<Rect> list) {
        throw new UnsupportedOperationException("window does not support gesture exclusion rects");
    }

    public void setTheme(int n) {
    }

    public abstract void setTitle(CharSequence var1);

    @Deprecated
    public abstract void setTitleColor(int var1);

    public void setTransitionBackgroundFadeDuration(long l) {
    }

    public void setTransitionManager(TransitionManager transitionManager) {
        throw new UnsupportedOperationException();
    }

    public void setType(int n) {
        WindowManager.LayoutParams layoutParams = this.getAttributes();
        layoutParams.type = n;
        this.dispatchWindowAttributesChanged(layoutParams);
    }

    public void setUiOptions(int n) {
    }

    public void setUiOptions(int n, int n2) {
    }

    public abstract void setVolumeControlStream(int var1);

    public void setWindowAnimations(int n) {
        WindowManager.LayoutParams layoutParams = this.getAttributes();
        layoutParams.windowAnimations = n;
        this.dispatchWindowAttributesChanged(layoutParams);
    }

    public final void setWindowControllerCallback(WindowControllerCallback windowControllerCallback) {
        this.mWindowControllerCallback = windowControllerCallback;
    }

    public void setWindowManager(WindowManager windowManager, IBinder iBinder, String string2) {
        this.setWindowManager(windowManager, iBinder, string2, false);
    }

    public void setWindowManager(WindowManager windowManager, IBinder object, String string2, boolean bl) {
        this.mAppToken = object;
        this.mAppName = string2;
        this.mHardwareAccelerated = bl;
        object = windowManager;
        if (windowManager == null) {
            object = (WindowManager)this.mContext.getSystemService("window");
        }
        this.mWindowManager = ((WindowManagerImpl)object).createLocalWindowManager(this);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public boolean shouldCloseOnTouch(Context context, MotionEvent motionEvent) {
        boolean bl = motionEvent.getAction() == 1 && this.isOutOfBounds(context, motionEvent) || motionEvent.getAction() == 4;
        return this.mCloseOnTouchOutside && this.peekDecorView() != null && bl;
    }

    public abstract boolean superDispatchGenericMotionEvent(MotionEvent var1);

    public abstract boolean superDispatchKeyEvent(KeyEvent var1);

    public abstract boolean superDispatchKeyShortcutEvent(KeyEvent var1);

    public abstract boolean superDispatchTouchEvent(MotionEvent var1);

    public abstract boolean superDispatchTrackballEvent(MotionEvent var1);

    public abstract void takeInputQueue(InputQueue.Callback var1);

    public abstract void takeKeyEvents(boolean var1);

    public abstract void takeSurface(SurfaceHolder.Callback2 var1);

    public abstract void togglePanel(int var1, KeyEvent var2);

    public static interface Callback {
        public boolean dispatchGenericMotionEvent(MotionEvent var1);

        public boolean dispatchKeyEvent(KeyEvent var1);

        public boolean dispatchKeyShortcutEvent(KeyEvent var1);

        public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent var1);

        public boolean dispatchTouchEvent(MotionEvent var1);

        public boolean dispatchTrackballEvent(MotionEvent var1);

        public void onActionModeFinished(ActionMode var1);

        public void onActionModeStarted(ActionMode var1);

        public void onAttachedToWindow();

        public void onContentChanged();

        public boolean onCreatePanelMenu(int var1, Menu var2);

        public View onCreatePanelView(int var1);

        public void onDetachedFromWindow();

        public boolean onMenuItemSelected(int var1, MenuItem var2);

        public boolean onMenuOpened(int var1, Menu var2);

        public void onPanelClosed(int var1, Menu var2);

        default public void onPointerCaptureChanged(boolean bl) {
        }

        public boolean onPreparePanel(int var1, View var2, Menu var3);

        default public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu2, int n) {
        }

        public boolean onSearchRequested();

        public boolean onSearchRequested(SearchEvent var1);

        public void onWindowAttributesChanged(WindowManager.LayoutParams var1);

        public void onWindowFocusChanged(boolean var1);

        public ActionMode onWindowStartingActionMode(ActionMode.Callback var1);

        public ActionMode onWindowStartingActionMode(ActionMode.Callback var1, int var2);
    }

    public static interface OnFrameMetricsAvailableListener {
        public void onFrameMetricsAvailable(Window var1, FrameMetrics var2, int var3);
    }

    public static interface OnRestrictedCaptionAreaChangedListener {
        public void onRestrictedCaptionAreaChanged(Rect var1);
    }

    public static interface OnWindowDismissedCallback {
        public void onWindowDismissed(boolean var1, boolean var2);
    }

    public static interface OnWindowSwipeDismissedCallback {
        public void onWindowSwipeDismissed();
    }

    public static interface WindowControllerCallback {
        public void enterPictureInPictureModeIfPossible();

        public boolean isTaskRoot();

        public void toggleFreeformWindowingMode() throws RemoteException;
    }

}

