/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.policy;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.annotation.UnsupportedAppUsage;
import android.app.WindowConfiguration;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.util.Property;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.InputQueue;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.view.ViewRootImpl;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowCallbacks;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import com.android.internal.policy.BackdropFrameRenderer;
import com.android.internal.policy.DecorContext;
import com.android.internal.policy.PhoneWindow;
import com.android.internal.view.FloatingActionMode;
import com.android.internal.view.RootViewSurfaceTaker;
import com.android.internal.view.StandaloneActionMode;
import com.android.internal.view.menu.ContextMenuBuilder;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.view.menu.MenuDialogHelper;
import com.android.internal.view.menu.MenuHelper;
import com.android.internal.view.menu.MenuPopupHelper;
import com.android.internal.view.menu.MenuPresenter;
import com.android.internal.widget.ActionBarContextView;
import com.android.internal.widget.BackgroundFallback;
import com.android.internal.widget.DecorCaptionView;
import com.android.internal.widget.DecorContentParent;
import com.android.internal.widget.FloatingToolbar;
import java.util.List;

public class DecorView
extends FrameLayout
implements RootViewSurfaceTaker,
WindowCallbacks {
    private static final boolean DEBUG_MEASURE = false;
    private static final int DECOR_SHADOW_FOCUSED_HEIGHT_IN_DIP = 20;
    private static final int DECOR_SHADOW_UNFOCUSED_HEIGHT_IN_DIP = 5;
    public static final ColorViewAttributes NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES;
    private static final ViewOutlineProvider PIP_OUTLINE_PROVIDER;
    private static final int SCRIM_LIGHT = -419430401;
    public static final ColorViewAttributes STATUS_BAR_COLOR_VIEW_ATTRIBUTES;
    private static final boolean SWEEP_OPEN_MENU = false;
    private static final String TAG = "DecorView";
    private boolean mAllowUpdateElevation;
    private boolean mApplyFloatingHorizontalInsets;
    private boolean mApplyFloatingVerticalInsets;
    private float mAvailableWidth;
    private BackdropFrameRenderer mBackdropFrameRenderer;
    private final BackgroundFallback mBackgroundFallback;
    private Insets mBackgroundInsets;
    private final Rect mBackgroundPadding;
    private final int mBarEnterExitDuration;
    private Drawable mCaptionBackgroundDrawable;
    private boolean mChanging;
    ViewGroup mContentRoot;
    private DecorCaptionView mDecorCaptionView;
    int mDefaultOpacity;
    private int mDownY;
    private boolean mDrawLegacyNavigationBarBackground;
    private final Rect mDrawingBounds;
    private boolean mElevationAdjustedForStack;
    private ObjectAnimator mFadeAnim;
    private final int mFeatureId;
    private ActionMode mFloatingActionMode;
    private View mFloatingActionModeOriginatingView;
    private final Rect mFloatingInsets;
    private FloatingToolbar mFloatingToolbar;
    private ViewTreeObserver.OnPreDrawListener mFloatingToolbarPreDrawListener;
    final boolean mForceWindowDrawsBarBackgrounds;
    private final Rect mFrameOffsets;
    private final Rect mFramePadding;
    private boolean mHasCaption;
    private final Interpolator mHideInterpolator;
    private final Paint mHorizontalResizeShadowPaint;
    private boolean mIsInPictureInPictureMode;
    private Drawable.Callback mLastBackgroundDrawableCb;
    private Insets mLastBackgroundInsets;
    @UnsupportedAppUsage
    private int mLastBottomInset;
    private boolean mLastHasBottomStableInset;
    private boolean mLastHasLeftStableInset;
    private boolean mLastHasRightStableInset;
    private boolean mLastHasTopStableInset;
    @UnsupportedAppUsage
    private int mLastLeftInset;
    private Drawable mLastOriginalBackgroundDrawable;
    private ViewOutlineProvider mLastOutlineProvider;
    @UnsupportedAppUsage
    private int mLastRightInset;
    private boolean mLastShouldAlwaysConsumeSystemBars;
    private int mLastTopInset;
    private int mLastWindowFlags;
    private final Paint mLegacyNavigationBarBackgroundPaint;
    String mLogTag;
    private Drawable mMenuBackground;
    private final ColorViewState mNavigationColorViewState;
    private Drawable mOriginalBackgroundDrawable;
    private Rect mOutsets;
    ActionMode mPrimaryActionMode;
    private PopupWindow mPrimaryActionModePopup;
    private ActionBarContextView mPrimaryActionModeView;
    private int mResizeMode;
    private final int mResizeShadowSize;
    private Drawable mResizingBackgroundDrawable;
    private int mRootScrollY;
    private final int mSemiTransparentBarColor;
    private final Interpolator mShowInterpolator;
    private Runnable mShowPrimaryActionModePopup;
    private final ColorViewState mStatusColorViewState;
    private View mStatusGuard;
    private Rect mTempRect;
    private Drawable mUserCaptionBackgroundDrawable;
    private final Paint mVerticalResizeShadowPaint;
    private boolean mWatchingForMenu;
    @UnsupportedAppUsage
    private PhoneWindow mWindow;
    private boolean mWindowResizeCallbacksAdded;

    static {
        STATUS_BAR_COLOR_VIEW_ATTRIBUTES = new ColorViewAttributes(4, 67108864, 48, 3, 5, "android:status:background", 16908335, 1024);
        NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES = new ColorViewAttributes(2, 134217728, 80, 5, 3, "android:navigation:background", 16908336, 0);
        PIP_OUTLINE_PROVIDER = new ViewOutlineProvider(){

            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(0, 0, view.getWidth(), view.getHeight());
                outline.setAlpha(1.0f);
            }
        };
    }

    DecorView(Context context, int n, PhoneWindow phoneWindow, WindowManager.LayoutParams layoutParams) {
        super(context);
        boolean bl = false;
        this.mAllowUpdateElevation = false;
        this.mElevationAdjustedForStack = false;
        this.mDefaultOpacity = -1;
        this.mDrawingBounds = new Rect();
        this.mBackgroundPadding = new Rect();
        this.mFramePadding = new Rect();
        this.mFrameOffsets = new Rect();
        this.mHasCaption = false;
        this.mStatusColorViewState = new ColorViewState(STATUS_BAR_COLOR_VIEW_ATTRIBUTES);
        this.mNavigationColorViewState = new ColorViewState(NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES);
        this.mBackgroundFallback = new BackgroundFallback();
        this.mLastTopInset = 0;
        this.mLastBottomInset = 0;
        this.mLastRightInset = 0;
        this.mLastLeftInset = 0;
        this.mLastHasTopStableInset = false;
        this.mLastHasBottomStableInset = false;
        this.mLastHasRightStableInset = false;
        this.mLastHasLeftStableInset = false;
        this.mLastWindowFlags = 0;
        this.mLastShouldAlwaysConsumeSystemBars = false;
        this.mRootScrollY = 0;
        this.mOutsets = new Rect();
        this.mWindowResizeCallbacksAdded = false;
        this.mLastBackgroundDrawableCb = null;
        this.mBackdropFrameRenderer = null;
        this.mLogTag = TAG;
        this.mFloatingInsets = new Rect();
        this.mApplyFloatingVerticalInsets = false;
        this.mApplyFloatingHorizontalInsets = false;
        this.mResizeMode = -1;
        this.mVerticalResizeShadowPaint = new Paint();
        this.mHorizontalResizeShadowPaint = new Paint();
        this.mLegacyNavigationBarBackgroundPaint = new Paint();
        this.mBackgroundInsets = Insets.NONE;
        this.mLastBackgroundInsets = Insets.NONE;
        this.mFeatureId = n;
        this.mShowInterpolator = AnimationUtils.loadInterpolator(context, 17563662);
        this.mHideInterpolator = AnimationUtils.loadInterpolator(context, 17563663);
        this.mBarEnterExitDuration = context.getResources().getInteger(17694969);
        if (context.getResources().getBoolean(17891458) && context.getApplicationInfo().targetSdkVersion >= 24) {
            bl = true;
        }
        this.mForceWindowDrawsBarBackgrounds = bl;
        this.mSemiTransparentBarColor = context.getResources().getColor(17170985, null);
        this.updateAvailableWidth();
        this.setWindow(phoneWindow);
        this.updateLogTag(layoutParams);
        this.mResizeShadowSize = context.getResources().getDimensionPixelSize(17105394);
        this.initResizingPaints();
        this.mLegacyNavigationBarBackgroundPaint.setColor(-16777216);
    }

    static /* synthetic */ Context access$500(DecorView decorView) {
        return decorView.mContext;
    }

    public static int calculateBarColor(int n, int n2, int n3, int n4, int n5, int n6, boolean bl) {
        if ((n & n2) != 0) {
            return n3;
        }
        if ((Integer.MIN_VALUE & n) == 0) {
            return -16777216;
        }
        if (bl && Color.alpha(n4) == 0) {
            n = (n5 & n6) != 0 ? 1 : 0;
            if (n != 0) {
                n3 = -419430401;
            }
            return n3;
        }
        return n4;
    }

    private int calculateNavigationBarColor() {
        int n = this.mWindow.getAttributes().flags;
        int n2 = this.mSemiTransparentBarColor;
        int n3 = this.mWindow.mNavigationBarColor;
        int n4 = this.getWindowSystemUiVisibility();
        boolean bl = this.mWindow.mEnsureNavigationBarContrastWhenTransparent && this.getContext().getResources().getBoolean(17891483);
        return DecorView.calculateBarColor(n, 134217728, n2, n3, n4, 16, bl);
    }

    private int calculateStatusBarColor() {
        return DecorView.calculateBarColor(this.mWindow.getAttributes().flags, 67108864, this.mSemiTransparentBarColor, this.mWindow.mStatusBarColor, this.getWindowSystemUiVisibility(), 8192, this.mWindow.mEnsureStatusBarContrastWhenTransparent);
    }

    private void cleanupFloatingActionModeViews() {
        Object object = this.mFloatingToolbar;
        if (object != null) {
            ((FloatingToolbar)object).dismiss();
            this.mFloatingToolbar = null;
        }
        if ((object = this.mFloatingActionModeOriginatingView) != null) {
            if (this.mFloatingToolbarPreDrawListener != null) {
                ((View)object).getViewTreeObserver().removeOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
                this.mFloatingToolbarPreDrawListener = null;
            }
            this.mFloatingActionModeOriginatingView = null;
        }
    }

    private void cleanupPrimaryActionMode() {
        Object object = this.mPrimaryActionMode;
        if (object != null) {
            ((ActionMode)object).finish();
            this.mPrimaryActionMode = null;
        }
        if ((object = this.mPrimaryActionModeView) != null) {
            ((ActionBarContextView)object).killMode();
        }
    }

    private ActionMode createActionMode(int n, ActionMode.Callback2 callback2, View view) {
        if (n != 1) {
            return this.createStandaloneActionMode(callback2);
        }
        return this.createFloatingActionMode(view, callback2);
    }

    private DecorCaptionView createDecorCaptionView(LayoutInflater layoutInflater) {
        Object object;
        DecorCaptionView decorCaptionView = null;
        int n = this.getChildCount();
        boolean bl = true;
        --n;
        while (n >= 0 && decorCaptionView == null) {
            object = this.getChildAt(n);
            if (object instanceof DecorCaptionView) {
                decorCaptionView = (DecorCaptionView)object;
                this.removeViewAt(n);
            }
            --n;
        }
        object = this.mWindow.getAttributes();
        n = ((WindowManager.LayoutParams)object).type != 1 && ((WindowManager.LayoutParams)object).type != 2 && ((WindowManager.LayoutParams)object).type != 4 ? 0 : 1;
        object = this.getResources().getConfiguration().windowConfiguration;
        if (!this.mWindow.isFloating() && n != 0 && ((WindowConfiguration)object).hasWindowDecorCaption()) {
            object = decorCaptionView;
            if (decorCaptionView == null) {
                object = this.inflateDecorCaptionView(layoutInflater);
            }
            ((DecorCaptionView)object).setPhoneWindow(this.mWindow, true);
        } else {
            object = null;
        }
        if (object == null) {
            bl = false;
        }
        this.enableCaption(bl);
        return object;
    }

    private ActionMode createFloatingActionMode(View view, ActionMode.Callback2 object) {
        ActionMode actionMode = this.mFloatingActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
        this.cleanupFloatingActionModeViews();
        this.mFloatingToolbar = new FloatingToolbar(this.mWindow);
        object = new FloatingActionMode(this.mContext, (ActionMode.Callback2)object, view, this.mFloatingToolbar);
        this.mFloatingActionModeOriginatingView = view;
        this.mFloatingToolbarPreDrawListener = new ViewTreeObserver.OnPreDrawListener((FloatingActionMode)object){
            final /* synthetic */ FloatingActionMode val$mode;
            {
                this.val$mode = floatingActionMode;
            }

            @Override
            public boolean onPreDraw() {
                this.val$mode.updateViewLocationInWindow();
                return true;
            }
        };
        return object;
    }

    private ActionMode createStandaloneActionMode(ActionMode.Callback callback) {
        Object object;
        this.endOnGoingFadeAnimation();
        this.cleanupPrimaryActionMode();
        Object object2 = this.mPrimaryActionModeView;
        boolean bl = false;
        if (object2 == null || !((View)object2).isAttachedToWindow()) {
            if (this.mWindow.isFloating()) {
                object = new TypedValue();
                object2 = this.mContext.getTheme();
                ((Resources.Theme)object2).resolveAttribute(16843825, (TypedValue)object, true);
                if (((TypedValue)object).resourceId != 0) {
                    Resources.Theme theme = this.mContext.getResources().newTheme();
                    theme.setTo((Resources.Theme)object2);
                    theme.applyStyle(((TypedValue)object).resourceId, true);
                    object2 = new ContextThemeWrapper(this.mContext, 0);
                    ((Context)object2).getTheme().setTo(theme);
                } else {
                    object2 = this.mContext;
                }
                this.mPrimaryActionModeView = new ActionBarContextView((Context)object2);
                this.mPrimaryActionModePopup = new PopupWindow((Context)object2, null, 17956872);
                this.mPrimaryActionModePopup.setWindowLayoutType(2);
                this.mPrimaryActionModePopup.setContentView(this.mPrimaryActionModeView);
                this.mPrimaryActionModePopup.setWidth(-1);
                ((Context)object2).getTheme().resolveAttribute(16843499, (TypedValue)object, true);
                int n = TypedValue.complexToDimensionPixelSize(((TypedValue)object).data, ((Context)object2).getResources().getDisplayMetrics());
                this.mPrimaryActionModeView.setContentHeight(n);
                this.mPrimaryActionModePopup.setHeight(-2);
                this.mShowPrimaryActionModePopup = new Runnable(){

                    @Override
                    public void run() {
                        DecorView.this.mPrimaryActionModePopup.showAtLocation(DecorView.this.mPrimaryActionModeView.getApplicationWindowToken(), 55, 0, 0);
                        DecorView.this.endOnGoingFadeAnimation();
                        if (DecorView.this.shouldAnimatePrimaryActionModeView()) {
                            DecorView decorView = DecorView.this;
                            decorView.mFadeAnim = ObjectAnimator.ofFloat(decorView.mPrimaryActionModeView, View.ALPHA, 0.0f, 1.0f);
                            DecorView.this.mFadeAnim.addListener(new AnimatorListenerAdapter(){

                                @Override
                                public void onAnimationEnd(Animator animator2) {
                                    DecorView.this.mPrimaryActionModeView.setAlpha(1.0f);
                                    DecorView.this.mFadeAnim = null;
                                }

                                @Override
                                public void onAnimationStart(Animator animator2) {
                                    DecorView.this.mPrimaryActionModeView.setVisibility(0);
                                }
                            });
                            DecorView.this.mFadeAnim.start();
                        } else {
                            DecorView.this.mPrimaryActionModeView.setAlpha(1.0f);
                            DecorView.this.mPrimaryActionModeView.setVisibility(0);
                        }
                    }

                };
            } else {
                object2 = (ViewStub)this.findViewById(16908692);
                if (object2 != null) {
                    this.mPrimaryActionModeView = (ActionBarContextView)((ViewStub)object2).inflate();
                    this.mPrimaryActionModePopup = null;
                }
            }
        }
        if ((object2 = this.mPrimaryActionModeView) != null) {
            ((ActionBarContextView)object2).killMode();
            object2 = this.mPrimaryActionModeView.getContext();
            object = this.mPrimaryActionModeView;
            if (this.mPrimaryActionModePopup == null) {
                bl = true;
            }
            return new StandaloneActionMode((Context)object2, (ActionBarContextView)object, callback, bl);
        }
        return null;
    }

    private float dipToPx(float f) {
        return TypedValue.applyDimension(1, f, this.getResources().getDisplayMetrics());
    }

    private void drawLegacyNavigationBarBackground(RecordingCanvas recordingCanvas) {
        if (!this.mDrawLegacyNavigationBarBackground) {
            return;
        }
        View view = this.mNavigationColorViewState.view;
        if (view == null) {
            return;
        }
        recordingCanvas.drawRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), this.mLegacyNavigationBarBackgroundPaint);
    }

    private void drawResizingShadowIfNeeded(RecordingCanvas recordingCanvas) {
        if (!(this.mResizeMode != 1 || this.mWindow.mIsFloating || this.mWindow.isTranslucent() || this.mWindow.isShowingWallpaper())) {
            recordingCanvas.save();
            recordingCanvas.translate(0.0f, this.getHeight() - this.mFrameOffsets.bottom);
            recordingCanvas.drawRect(0.0f, 0.0f, this.getWidth(), this.mResizeShadowSize, this.mHorizontalResizeShadowPaint);
            recordingCanvas.restore();
            recordingCanvas.save();
            recordingCanvas.translate(this.getWidth() - this.mFrameOffsets.right, 0.0f);
            recordingCanvas.drawRect(0.0f, 0.0f, this.mResizeShadowSize, this.getHeight(), this.mVerticalResizeShadowPaint);
            recordingCanvas.restore();
            return;
        }
    }

    private void drawableChanged() {
        if (this.mChanging) {
            return;
        }
        Rect rect = this.mFramePadding;
        if (rect == null) {
            rect = new Rect();
        }
        Object object = this.mBackgroundPadding;
        if (object == null) {
            object = new Rect();
        }
        this.setPadding(rect.left + ((Rect)object).left, rect.top + ((Rect)object).top, rect.right + ((Rect)object).right, rect.bottom + ((Rect)object).bottom);
        this.requestLayout();
        this.invalidate();
        int n = -1;
        if (this.getResources().getConfiguration().windowConfiguration.hasWindowShadow()) {
            n = -3;
        } else {
            Drawable drawable2 = this.getBackground();
            object = this.getForeground();
            if (drawable2 != null) {
                if (object == null) {
                    n = drawable2.getOpacity();
                } else if (rect.left <= 0 && rect.top <= 0 && rect.right <= 0 && rect.bottom <= 0) {
                    int n2 = ((Drawable)object).getOpacity();
                    n = drawable2.getOpacity();
                    if (n2 != -1 && n != -1) {
                        if (n2 != 0) {
                            n = n == 0 ? n2 : Drawable.resolveOpacity(n2, n);
                        }
                    } else {
                        n = -1;
                    }
                } else {
                    n = -3;
                }
            }
        }
        this.mDefaultOpacity = n;
        if (this.mFeatureId < 0) {
            this.mWindow.setDefaultWindowFormat(n);
        }
    }

    private void endOnGoingFadeAnimation() {
        ObjectAnimator objectAnimator = this.mFadeAnim;
        if (objectAnimator != null) {
            objectAnimator.end();
        }
    }

    private static Drawable enforceNonTranslucentBackground(Drawable drawable2, boolean bl) {
        ColorDrawable colorDrawable;
        int n;
        if (!bl && drawable2 instanceof ColorDrawable && Color.alpha(n = (colorDrawable = (ColorDrawable)drawable2).getColor()) != 255) {
            drawable2 = (ColorDrawable)colorDrawable.getConstantState().newDrawable().mutate();
            ((ColorDrawable)drawable2).setColor(Color.argb(255, Color.red(n), Color.green(n), Color.blue(n)));
            return drawable2;
        }
        return drawable2;
    }

    public static int getColorViewBottomInset(int n, int n2) {
        return Math.min(n, n2);
    }

    public static int getColorViewLeftInset(int n, int n2) {
        return Math.min(n, n2);
    }

    public static int getColorViewRightInset(int n, int n2) {
        return Math.min(n, n2);
    }

    public static int getColorViewTopInset(int n, int n2) {
        return Math.min(n, n2);
    }

    private int getCurrentColor(ColorViewState colorViewState) {
        if (colorViewState.visible) {
            return colorViewState.color;
        }
        return 0;
    }

    public static int getNavBarSize(int n, int n2, int n3) {
        block1 : {
            block0 : {
                if (!DecorView.isNavBarToRightEdge(n, n2)) break block0;
                n = n2;
                break block1;
            }
            if (!DecorView.isNavBarToLeftEdge(n, n3)) break block1;
            n = n3;
        }
        return n;
    }

    public static void getNavigationBarRect(int n, int n2, Rect rect, Rect rect2, Rect rect3, float f) {
        int n3 = (int)((float)DecorView.getColorViewBottomInset(rect.bottom, rect2.bottom) * f);
        int n4 = (int)((float)DecorView.getColorViewLeftInset(rect.left, rect2.left) * f);
        int n5 = (int)((float)DecorView.getColorViewLeftInset(rect.right, rect2.right) * f);
        int n6 = DecorView.getNavBarSize(n3, n5, n4);
        if (DecorView.isNavBarToRightEdge(n3, n5)) {
            rect3.set(n - n6, 0, n, n2);
        } else if (DecorView.isNavBarToLeftEdge(n3, n4)) {
            rect3.set(0, 0, n6, n2);
        } else {
            rect3.set(0, n2 - n6, n, n2);
        }
    }

    public static Drawable getResizingBackgroundDrawable(Drawable drawable2, Drawable drawable3, boolean bl) {
        if (drawable2 != null) {
            return DecorView.enforceNonTranslucentBackground(drawable2, bl);
        }
        if (drawable3 != null) {
            return DecorView.enforceNonTranslucentBackground(drawable3, bl);
        }
        return new ColorDrawable(-16777216);
    }

    private static String getTitleSuffix(WindowManager.LayoutParams arrstring) {
        if (arrstring == null) {
            return "";
        }
        if ((arrstring = arrstring.getTitle().toString().split("\\.")).length > 0) {
            return arrstring[arrstring.length - 1];
        }
        return "";
    }

    private DecorCaptionView inflateDecorCaptionView(LayoutInflater object) {
        object = this.getContext();
        DecorCaptionView decorCaptionView = (DecorCaptionView)LayoutInflater.from((Context)object).inflate(17367137, null);
        this.setDecorCaptionShade((Context)object, decorCaptionView);
        return decorCaptionView;
    }

    private void initResizingPaints() {
        int n = this.mContext.getResources().getColor(17170942, null);
        int n2 = this.mContext.getResources().getColor(17170941, null);
        int n3 = (n + n2) / 2;
        Paint paint = this.mHorizontalResizeShadowPaint;
        float f = this.mResizeShadowSize;
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        paint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, f, new int[]{n, n3, n2}, new float[]{0.0f, 0.3f, 1.0f}, tileMode));
        paint = this.mVerticalResizeShadowPaint;
        f = this.mResizeShadowSize;
        tileMode = Shader.TileMode.CLAMP;
        paint.setShader(new LinearGradient(0.0f, 0.0f, f, 0.0f, new int[]{n, n3, n2}, new float[]{0.0f, 0.3f, 1.0f}, tileMode));
    }

    private void initializeElevation() {
        this.mAllowUpdateElevation = false;
        this.updateElevation();
    }

    private boolean isFillingScreen(Configuration configuration) {
        int n = configuration.windowConfiguration.getWindowingMode();
        boolean bl = false;
        n = n == 1 ? 1 : 0;
        boolean bl2 = bl;
        if (n != 0) {
            bl2 = bl;
            if (((this.getWindowSystemUiVisibility() | this.getSystemUiVisibility()) & 4) != 0) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public static boolean isNavBarToLeftEdge(int n, int n2) {
        boolean bl = n == 0 && n2 > 0;
        return bl;
    }

    public static boolean isNavBarToRightEdge(int n, int n2) {
        boolean bl = n == 0 && n2 > 0;
        return bl;
    }

    private boolean isOutOfBounds(int n, int n2) {
        boolean bl = n < -5 || n2 < -5 || n > this.getWidth() + 5 || n2 > this.getHeight() + 5;
        return bl;
    }

    private boolean isOutOfInnerBounds(int n, int n2) {
        boolean bl = n < 0 || n2 < 0 || n > this.getWidth() || n2 > this.getHeight();
        return bl;
    }

    private boolean isResizing() {
        boolean bl = this.mBackdropFrameRenderer != null;
        return bl;
    }

    private void loadBackgroundDrawablesIfNeeded() {
        Object object;
        if (this.mResizingBackgroundDrawable == null) {
            object = this.mWindow.mBackgroundDrawable;
            Object object2 = this.mWindow.mBackgroundFallbackDrawable;
            boolean bl = this.mWindow.isTranslucent() || this.mWindow.isShowingWallpaper();
            this.mResizingBackgroundDrawable = DecorView.getResizingBackgroundDrawable((Drawable)object, (Drawable)object2, bl);
            if (this.mResizingBackgroundDrawable == null) {
                object2 = this.mLogTag;
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to find background drawable for PhoneWindow=");
                ((StringBuilder)object).append(this.mWindow);
                Log.w((String)object2, ((StringBuilder)object).toString());
            }
        }
        if (this.mCaptionBackgroundDrawable == null) {
            this.mCaptionBackgroundDrawable = this.getContext().getDrawable(17302131);
        }
        if ((object = this.mResizingBackgroundDrawable) != null) {
            this.mLastBackgroundDrawableCb = ((Drawable)object).getCallback();
            this.mResizingBackgroundDrawable.setCallback(null);
        }
    }

    private void releaseThreadedRenderer() {
        Drawable.Callback callback;
        Object object = this.mResizingBackgroundDrawable;
        if (object != null && (callback = this.mLastBackgroundDrawableCb) != null) {
            ((Drawable)object).setCallback(callback);
            this.mLastBackgroundDrawableCb = null;
        }
        if ((object = this.mBackdropFrameRenderer) != null) {
            ((BackdropFrameRenderer)object).releaseRenderer();
            this.mBackdropFrameRenderer = null;
            this.updateElevation();
        }
    }

    private static void setColor(View callback, int n, int n2, boolean bl, boolean bl2) {
        if (n2 != 0) {
            Object object = (Pair)((View)callback).getTag();
            if (object != null && (Boolean)((Pair)object).first == bl && (Boolean)((Pair)object).second == bl2) {
                callback = (LayerDrawable)((View)callback).getBackground();
                ((ColorDrawable)((InsetDrawable)((LayerDrawable)callback).getDrawable(1)).getDrawable()).setColor(n);
                ((ColorDrawable)((LayerDrawable)callback).getDrawable(0)).setColor(n2);
            } else {
                int n3 = Math.round(TypedValue.applyDimension(1, 1.0f, ((View)callback).getContext().getResources().getDisplayMetrics()));
                object = new ColorDrawable(n);
                n = bl && !bl2 ? n3 : 0;
                int n4 = !bl ? n3 : 0;
                if (!bl || !bl2) {
                    n3 = 0;
                }
                object = new InsetDrawable((Drawable)object, n, n4, n3, 0);
                ((View)callback).setBackground(new LayerDrawable(new Drawable[]{new ColorDrawable(n2), object}));
                ((View)callback).setTag(new Pair<Boolean, Boolean>(bl, bl2));
            }
        } else {
            ((View)callback).setTag(null);
            ((View)callback).setBackgroundColor(n);
        }
    }

    private void setDarkDecorCaptionShade(DecorCaptionView decorCaptionView) {
        ((View)decorCaptionView.findViewById(16909091)).setBackgroundResource(17302135);
        ((View)decorCaptionView.findViewById(16908818)).setBackgroundResource(17302133);
    }

    private void setDecorCaptionShade(Context context, DecorCaptionView decorCaptionView) {
        int n = this.mWindow.getDecorCaptionShade();
        if (n != 1) {
            if (n != 2) {
                TypedValue typedValue = new TypedValue();
                context.getTheme().resolveAttribute(16843827, typedValue, true);
                if ((double)Color.luminance(typedValue.data) < 0.5) {
                    this.setLightDecorCaptionShade(decorCaptionView);
                } else {
                    this.setDarkDecorCaptionShade(decorCaptionView);
                }
            } else {
                this.setDarkDecorCaptionShade(decorCaptionView);
            }
        } else {
            this.setLightDecorCaptionShade(decorCaptionView);
        }
    }

    private void setHandledActionMode(ActionMode actionMode) {
        if (actionMode.getType() == 0) {
            this.setHandledPrimaryActionMode(actionMode);
        } else if (actionMode.getType() == 1) {
            this.setHandledFloatingActionMode(actionMode);
        }
    }

    private void setHandledFloatingActionMode(ActionMode actionMode) {
        this.mFloatingActionMode = actionMode;
        this.mFloatingActionMode.invalidate();
        this.mFloatingActionModeOriginatingView.getViewTreeObserver().addOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
    }

    private void setHandledPrimaryActionMode(ActionMode actionMode) {
        this.endOnGoingFadeAnimation();
        this.mPrimaryActionMode = actionMode;
        this.mPrimaryActionMode.invalidate();
        this.mPrimaryActionModeView.initForMode(this.mPrimaryActionMode);
        if (this.mPrimaryActionModePopup != null) {
            this.post(this.mShowPrimaryActionModePopup);
        } else if (this.shouldAnimatePrimaryActionModeView()) {
            this.mFadeAnim = ObjectAnimator.ofFloat(this.mPrimaryActionModeView, View.ALPHA, 0.0f, 1.0f);
            this.mFadeAnim.addListener(new AnimatorListenerAdapter(){

                @Override
                public void onAnimationEnd(Animator animator2) {
                    DecorView.this.mPrimaryActionModeView.setAlpha(1.0f);
                    DecorView.this.mFadeAnim = null;
                }

                @Override
                public void onAnimationStart(Animator animator2) {
                    DecorView.this.mPrimaryActionModeView.setVisibility(0);
                }
            });
            this.mFadeAnim.start();
        } else {
            this.mPrimaryActionModeView.setAlpha(1.0f);
            this.mPrimaryActionModeView.setVisibility(0);
        }
        this.mPrimaryActionModeView.sendAccessibilityEvent(32);
    }

    private void setLightDecorCaptionShade(DecorCaptionView decorCaptionView) {
        ((View)decorCaptionView.findViewById(16909091)).setBackgroundResource(17302136);
        ((View)decorCaptionView.findViewById(16908818)).setBackgroundResource(17302134);
    }

    private boolean showContextMenuForChildInternal(View object, float f, float f2) {
        if (this.mWindow.mContextMenuHelper != null) {
            this.mWindow.mContextMenuHelper.dismiss();
            this.mWindow.mContextMenuHelper = null;
        }
        PhoneWindow.PhoneWindowMenuCallback phoneWindowMenuCallback = this.mWindow.mContextMenuCallback;
        if (this.mWindow.mContextMenu == null) {
            this.mWindow.mContextMenu = new ContextMenuBuilder(this.getContext());
            this.mWindow.mContextMenu.setCallback(phoneWindowMenuCallback);
        } else {
            this.mWindow.mContextMenu.clearAll();
        }
        boolean bl = Float.isNaN(f);
        boolean bl2 = true;
        boolean bl3 = !bl && !Float.isNaN(f2);
        object = bl3 ? this.mWindow.mContextMenu.showPopup(this.getContext(), (View)object, f, f2) : this.mWindow.mContextMenu.showDialog((View)object, ((View)object).getWindowToken());
        if (object != null) {
            bl = !bl3;
            phoneWindowMenuCallback.setShowDialogForSubmenu(bl);
            object.setPresenterCallback(phoneWindowMenuCallback);
        }
        this.mWindow.mContextMenuHelper = object;
        bl = object != null ? bl2 : false;
        return bl;
    }

    private ActionMode startActionMode(View object, ActionMode.Callback object2, int n) {
        ActionModeCallback2Wrapper actionModeCallback2Wrapper;
        block17 : {
            actionModeCallback2Wrapper = new ActionModeCallback2Wrapper((ActionMode.Callback)object2);
            Object var5_8 = null;
            object2 = var5_8;
            if (this.mWindow.getCallback() != null) {
                object2 = var5_8;
                if (!this.mWindow.isDestroyed()) {
                    try {
                        object2 = this.mWindow.getCallback().onWindowStartingActionMode(actionModeCallback2Wrapper, n);
                    }
                    catch (AbstractMethodError abstractMethodError) {
                        object2 = var5_8;
                        if (n != 0) break block17;
                        try {
                            object2 = this.mWindow.getCallback().onWindowStartingActionMode(actionModeCallback2Wrapper);
                        }
                        catch (AbstractMethodError abstractMethodError2) {
                            object2 = var5_8;
                        }
                    }
                }
            }
        }
        if (object2 != null) {
            if (((ActionMode)object2).getType() == 0) {
                this.cleanupPrimaryActionMode();
                this.mPrimaryActionMode = object2;
                object = object2;
            } else {
                object = object2;
                if (((ActionMode)object2).getType() == 1) {
                    object = this.mFloatingActionMode;
                    if (object != null) {
                        ((ActionMode)object).finish();
                    }
                    this.mFloatingActionMode = object2;
                    object = object2;
                }
            }
        } else if ((object = this.createActionMode(n, actionModeCallback2Wrapper, (View)object)) != null && actionModeCallback2Wrapper.onCreateActionMode((ActionMode)object, ((ActionMode)object).getMenu())) {
            this.setHandledActionMode((ActionMode)object);
        } else {
            object = null;
        }
        if (object != null && this.mWindow.getCallback() != null && !this.mWindow.isDestroyed()) {
            try {
                this.mWindow.getCallback().onActionModeStarted((ActionMode)object);
            }
            catch (AbstractMethodError abstractMethodError) {
                // empty catch block
            }
        }
        return object;
    }

    private void updateAvailableWidth() {
        Resources resources = this.getResources();
        this.mAvailableWidth = TypedValue.applyDimension(1, resources.getConfiguration().screenWidthDp, resources.getDisplayMetrics());
    }

    private void updateBackgroundDrawable() {
        if (this.mBackgroundInsets == null) {
            this.mBackgroundInsets = Insets.NONE;
        }
        if (this.mBackgroundInsets.equals(this.mLastBackgroundInsets) && this.mLastOriginalBackgroundDrawable == this.mOriginalBackgroundDrawable) {
            return;
        }
        if (this.mOriginalBackgroundDrawable != null && !this.mBackgroundInsets.equals(Insets.NONE)) {
            super.setBackgroundDrawable(new InsetDrawable(this.mOriginalBackgroundDrawable, this.mBackgroundInsets.left, this.mBackgroundInsets.top, this.mBackgroundInsets.right, this.mBackgroundInsets.bottom){

                @Override
                public boolean getPadding(Rect rect) {
                    return this.getDrawable().getPadding(rect);
                }
            });
        } else {
            super.setBackgroundDrawable(this.mOriginalBackgroundDrawable);
        }
        this.mLastBackgroundInsets = this.mBackgroundInsets;
        this.mLastOriginalBackgroundDrawable = this.mOriginalBackgroundDrawable;
    }

    private void updateColorViewInt(final ColorViewState colorViewState, int n, int n2, int n3, int n4, boolean bl, boolean bl2, int n5, boolean bl3, boolean bl4) {
        Object object;
        colorViewState.present = colorViewState.attributes.isPresent(n, this.mWindow.getAttributes().flags, bl4);
        boolean bl5 = (bl4 = colorViewState.attributes.isVisible(colorViewState.present, n2, this.mWindow.getAttributes().flags, bl4)) && !this.isResizing() && n4 > 0;
        int n6 = 0;
        Object object2 = colorViewState.view;
        int n7 = -1;
        int n8 = bl ? -1 : n4;
        if (bl) {
            n7 = n4;
        }
        if (bl) {
            object = colorViewState.attributes;
            n = bl2 ? ((ColorViewAttributes)object).seascapeGravity : ((ColorViewAttributes)object).horizontalGravity;
        } else {
            n = colorViewState.attributes.verticalGravity;
        }
        if (object2 == null) {
            if (bl5) {
                object2 = object = new View(this.mContext);
                colorViewState.view = object;
                DecorView.setColor((View)object2, n2, n3, bl, bl2);
                ((View)object2).setTransitionName(colorViewState.attributes.transitionName);
                ((View)object2).setId(colorViewState.attributes.id);
                n3 = 1;
                ((View)object2).setVisibility(4);
                colorViewState.targetVisibility = 0;
                object = new FrameLayout.LayoutParams(n7, n8, n);
                if (bl2) {
                    ((FrameLayout.LayoutParams)object).leftMargin = n5;
                } else {
                    ((FrameLayout.LayoutParams)object).rightMargin = n5;
                }
                this.addView((View)object2, (ViewGroup.LayoutParams)object);
                this.updateColorViewTranslations();
                n = n3;
            } else {
                n = n6;
            }
        } else {
            n6 = bl5 ? 0 : 4;
            n4 = colorViewState.targetVisibility != n6 ? 1 : 0;
            colorViewState.targetVisibility = n6;
            object = (FrameLayout.LayoutParams)((View)object2).getLayoutParams();
            n6 = bl2 ? 0 : n5;
            if (!bl2) {
                n5 = 0;
            }
            if (((FrameLayout.LayoutParams)object).height != n8 || ((FrameLayout.LayoutParams)object).width != n7 || ((FrameLayout.LayoutParams)object).gravity != n || ((FrameLayout.LayoutParams)object).rightMargin != n6 || ((FrameLayout.LayoutParams)object).leftMargin != n5) {
                ((FrameLayout.LayoutParams)object).height = n8;
                ((FrameLayout.LayoutParams)object).width = n7;
                ((FrameLayout.LayoutParams)object).gravity = n;
                ((FrameLayout.LayoutParams)object).rightMargin = n6;
                ((FrameLayout.LayoutParams)object).leftMargin = n5;
                ((View)object2).setLayoutParams((ViewGroup.LayoutParams)object);
            }
            if (bl5) {
                DecorView.setColor((View)object2, n2, n3, bl, bl2);
            }
            n = n4;
        }
        if (n != 0) {
            ((View)object2).animate().cancel();
            if (bl3 && !this.isResizing()) {
                if (bl5) {
                    if (((View)object2).getVisibility() != 0) {
                        ((View)object2).setVisibility(0);
                        ((View)object2).setAlpha(0.0f);
                    }
                    ((View)object2).animate().alpha(1.0f).setInterpolator(this.mShowInterpolator).setDuration(this.mBarEnterExitDuration);
                } else {
                    ((View)object2).animate().alpha(0.0f).setInterpolator(this.mHideInterpolator).setDuration(this.mBarEnterExitDuration).withEndAction(new Runnable(){

                        @Override
                        public void run() {
                            colorViewState.view.setAlpha(1.0f);
                            colorViewState.view.setVisibility(4);
                        }
                    });
                }
            } else {
                n = 0;
                ((View)object2).setAlpha(1.0f);
                if (!bl5) {
                    n = 4;
                }
                ((View)object2).setVisibility(n);
            }
        }
        colorViewState.visible = bl4;
        colorViewState.color = n2;
    }

    private void updateColorViewTranslations() {
        float f;
        int n = this.mRootScrollY;
        View view = this.mStatusColorViewState.view;
        float f2 = 0.0f;
        if (view != null) {
            view = this.mStatusColorViewState.view;
            f = n > 0 ? (float)n : 0.0f;
            view.setTranslationY(f);
        }
        if (this.mNavigationColorViewState.view != null) {
            view = this.mNavigationColorViewState.view;
            f = f2;
            if (n < 0) {
                f = n;
            }
            view.setTranslationY(f);
        }
    }

    private void updateDecorCaptionStatus(Configuration object) {
        block2 : {
            boolean bl;
            block1 : {
                bl = ((Configuration)object).windowConfiguration.hasWindowDecorCaption() && !this.isFillingScreen((Configuration)object);
                if (this.mDecorCaptionView != null || !bl) break block1;
                this.mDecorCaptionView = this.createDecorCaptionView(this.mWindow.getLayoutInflater());
                object = this.mDecorCaptionView;
                if (object == null) break block2;
                if (((View)object).getParent() == null) {
                    this.addView((View)this.mDecorCaptionView, 0, new ViewGroup.LayoutParams(-1, -1));
                }
                this.removeView(this.mContentRoot);
                this.mDecorCaptionView.addView((View)this.mContentRoot, new ViewGroup.MarginLayoutParams(-1, -1));
                break block2;
            }
            object = this.mDecorCaptionView;
            if (object == null) break block2;
            ((DecorCaptionView)object).onConfigurationChanged(bl);
            this.enableCaption(bl);
        }
    }

    private void updateElevation() {
        float f = 0.0f;
        boolean bl = this.mElevationAdjustedForStack;
        int n = this.getResources().getConfiguration().windowConfiguration.getWindowingMode();
        float f2 = 5.0f;
        if (n == 5 && !this.isResizing()) {
            f = this.hasWindowFocus() ? 20.0f : f2;
            if (!this.mAllowUpdateElevation) {
                f = 20.0f;
            }
            f = this.dipToPx(f);
            this.mElevationAdjustedForStack = true;
        } else if (n == 2) {
            f = this.dipToPx(5.0f);
            this.mElevationAdjustedForStack = true;
        } else {
            this.mElevationAdjustedForStack = false;
        }
        if ((bl || this.mElevationAdjustedForStack) && this.getElevation() != f) {
            if (!this.isResizing()) {
                this.mWindow.setElevation(f);
            } else {
                this.setElevation(f);
            }
        }
    }

    private WindowInsets updateStatusGuard(WindowInsets object) {
        int n;
        int n2;
        Object object2 = object;
        Object object3 = this.mPrimaryActionModeView;
        if (object3 != null) {
            if (((View)object3).getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                int n3;
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)this.mPrimaryActionModeView.getLayoutParams();
                n = 0;
                n2 = 0;
                if (this.mPrimaryActionModeView.isShown()) {
                    if (this.mTempRect == null) {
                        this.mTempRect = new Rect();
                    }
                    object3 = this.mTempRect;
                    object3 = this.mWindow.mContentParent.computeSystemWindowInsets((WindowInsets)object2, (Rect)object3);
                    int n4 = ((WindowInsets)object3).getSystemWindowInsetTop();
                    int n5 = ((WindowInsets)object3).getSystemWindowInsetLeft();
                    int n6 = ((WindowInsets)object3).getSystemWindowInsetRight();
                    object3 = this.getRootWindowInsets();
                    n3 = ((WindowInsets)object3).getSystemWindowInsetLeft();
                    n = ((WindowInsets)object3).getSystemWindowInsetRight();
                    if (marginLayoutParams.topMargin != n4 || marginLayoutParams.leftMargin != n5 || marginLayoutParams.rightMargin != n6) {
                        n2 = 1;
                        marginLayoutParams.topMargin = n4;
                        marginLayoutParams.leftMargin = n5;
                        marginLayoutParams.rightMargin = n6;
                    }
                    if (n4 > 0 && this.mStatusGuard == null) {
                        this.mStatusGuard = new View(this.mContext);
                        this.mStatusGuard.setVisibility(8);
                        object3 = new FrameLayout.LayoutParams(-1, marginLayoutParams.topMargin, 51);
                        ((FrameLayout.LayoutParams)object3).leftMargin = n3;
                        ((FrameLayout.LayoutParams)object3).rightMargin = n;
                        this.addView(this.mStatusGuard, this.indexOfChild(this.mStatusColorViewState.view), (ViewGroup.LayoutParams)object3);
                    } else {
                        object3 = this.mStatusGuard;
                        if (object3 != null) {
                            object3 = (FrameLayout.LayoutParams)((View)object3).getLayoutParams();
                            if (((FrameLayout.LayoutParams)object3).height != marginLayoutParams.topMargin || ((FrameLayout.LayoutParams)object3).leftMargin != n3 || ((FrameLayout.LayoutParams)object3).rightMargin != n) {
                                ((FrameLayout.LayoutParams)object3).height = marginLayoutParams.topMargin;
                                ((FrameLayout.LayoutParams)object3).leftMargin = n3;
                                ((FrameLayout.LayoutParams)object3).rightMargin = n;
                                this.mStatusGuard.setLayoutParams((ViewGroup.LayoutParams)object3);
                            }
                        }
                    }
                    object3 = this.mStatusGuard;
                    n = 1;
                    n3 = object3 != null ? 1 : 0;
                    if (n3 != 0 && this.mStatusGuard.getVisibility() != 0) {
                        this.updateStatusGuardColor();
                    }
                    if ((this.mWindow.getLocalFeaturesPrivate() & 1024) != 0) {
                        n = 0;
                    }
                    object3 = object2;
                    if (n != 0) {
                        object3 = object2;
                        if (n3 != 0) {
                            object3 = ((WindowInsets)object2).inset(0, ((WindowInsets)object).getSystemWindowInsetTop(), 0, 0);
                        }
                    }
                    object2 = object3;
                } else {
                    n3 = 0;
                    if (marginLayoutParams.topMargin == 0 && marginLayoutParams.leftMargin == 0 && marginLayoutParams.rightMargin == 0) {
                        n2 = n;
                    } else {
                        n2 = 1;
                        marginLayoutParams.topMargin = 0;
                    }
                }
                object = object2;
                n = n3;
                if (n2 != 0) {
                    this.mPrimaryActionModeView.setLayoutParams(marginLayoutParams);
                    object = object2;
                    n = n3;
                }
            } else {
                n = 0;
                object = object2;
            }
        } else {
            n = 0;
            object = object2;
        }
        n2 = 0;
        object2 = this.mStatusGuard;
        if (object2 != null) {
            if (n == 0) {
                n2 = 8;
            }
            ((View)object2).setVisibility(n2);
        }
        return object;
    }

    private void updateStatusGuardColor() {
        int n = (this.getWindowSystemUiVisibility() & 8192) != 0 ? 1 : 0;
        View view = this.mStatusGuard;
        n = n != 0 ? this.mContext.getColor(17170749) : this.mContext.getColor(17170748);
        view.setBackgroundColor(n);
    }

    void clearContentView() {
        View view = this.mDecorCaptionView;
        if (view != null) {
            view.removeContentView();
        } else {
            for (int i = this.getChildCount() - 1; i >= 0; --i) {
                view = this.getChildAt(i);
                if (view == this.mStatusColorViewState.view || view == this.mNavigationColorViewState.view || view == this.mStatusGuard) continue;
                this.removeViewAt(i);
            }
        }
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        Window.Callback callback = this.mWindow.getCallback();
        boolean bl = callback != null && !this.mWindow.isDestroyed() && this.mFeatureId < 0 ? callback.dispatchGenericMotionEvent(motionEvent) : super.dispatchGenericMotionEvent(motionEvent);
        return bl;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        Object object;
        boolean bl;
        int n = keyEvent.getKeyCode();
        boolean bl2 = keyEvent.getAction() == 0;
        if (bl2 && keyEvent.getRepeatCount() == 0) {
            if (this.mWindow.mPanelChordingKey > 0 && this.mWindow.mPanelChordingKey != n && this.dispatchKeyShortcutEvent(keyEvent)) {
                return true;
            }
            if (this.mWindow.mPreparedPanel != null && this.mWindow.mPreparedPanel.isOpen && ((PhoneWindow)(object = this.mWindow)).performPanelShortcut(((PhoneWindow)object).mPreparedPanel, n, keyEvent, 0)) {
                return true;
            }
        }
        if (!this.mWindow.isDestroyed() && (bl = (object = this.mWindow.getCallback()) != null && this.mFeatureId < 0 ? object.dispatchKeyEvent(keyEvent) : super.dispatchKeyEvent(keyEvent))) {
            return true;
        }
        bl = bl2 ? this.mWindow.onKeyDown(this.mFeatureId, keyEvent.getKeyCode(), keyEvent) : this.mWindow.onKeyUp(this.mFeatureId, keyEvent.getKeyCode(), keyEvent);
        return bl;
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        Object object;
        if (this.mWindow.mPreparedPanel != null && ((PhoneWindow)(object = this.mWindow)).performPanelShortcut(((PhoneWindow)object).mPreparedPanel, keyEvent.getKeyCode(), keyEvent, 1)) {
            if (this.mWindow.mPreparedPanel != null) {
                this.mWindow.mPreparedPanel.isHandled = true;
            }
            return true;
        }
        object = this.mWindow.getCallback();
        boolean bl = object != null && !this.mWindow.isDestroyed() && this.mFeatureId < 0 ? object.dispatchKeyShortcutEvent(keyEvent) : super.dispatchKeyShortcutEvent(keyEvent);
        if (bl) {
            return true;
        }
        object = this.mWindow.getPanelState(0, false);
        if (object != null && this.mWindow.mPreparedPanel == null) {
            this.mWindow.preparePanel((PhoneWindow.PanelFeatureState)object, keyEvent);
            bl = this.mWindow.performPanelShortcut((PhoneWindow.PanelFeatureState)object, keyEvent.getKeyCode(), keyEvent, 1);
            ((PhoneWindow.PanelFeatureState)object).isPrepared = false;
            if (bl) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void dispatchPointerCaptureChanged(boolean bl) {
        super.dispatchPointerCaptureChanged(bl);
        if (!this.mWindow.isDestroyed() && this.mWindow.getCallback() != null) {
            this.mWindow.getCallback().onPointerCaptureChanged(bl);
        }
    }

    @Override
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        Window.Callback callback = this.mWindow.getCallback();
        if (callback != null && !this.mWindow.isDestroyed() && callback.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
            return true;
        }
        return super.dispatchPopulateAccessibilityEventInternal(accessibilityEvent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        Window.Callback callback = this.mWindow.getCallback();
        boolean bl = callback != null && !this.mWindow.isDestroyed() && this.mFeatureId < 0 ? callback.dispatchTouchEvent(motionEvent) : super.dispatchTouchEvent(motionEvent);
        return bl;
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        Window.Callback callback = this.mWindow.getCallback();
        boolean bl = callback != null && !this.mWindow.isDestroyed() && this.mFeatureId < 0 ? callback.dispatchTrackballEvent(motionEvent) : super.dispatchTrackballEvent(motionEvent);
        return bl;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Drawable drawable2 = this.mMenuBackground;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
    }

    void enableCaption(boolean bl) {
        if (this.mHasCaption != bl) {
            this.mHasCaption = bl;
            if (this.getForeground() != null) {
                this.drawableChanged();
            }
        }
    }

    void finishChanging() {
        this.mChanging = false;
        this.drawableChanged();
    }

    @Override
    public boolean gatherTransparentRegion(Region region) {
        boolean bl = this.gatherTransparentRegion(this.mStatusColorViewState, region);
        boolean bl2 = this.gatherTransparentRegion(this.mNavigationColorViewState, region);
        boolean bl3 = super.gatherTransparentRegion(region);
        bl = bl || bl2 || bl3;
        return bl;
    }

    boolean gatherTransparentRegion(ColorViewState colorViewState, Region region) {
        if (colorViewState.view != null && colorViewState.visible && this.isResizing()) {
            return colorViewState.view.gatherTransparentRegion(region);
        }
        return false;
    }

    @Override
    public int getAccessibilityViewId() {
        return 2147483646;
    }

    @Override
    public Drawable getBackground() {
        return this.mOriginalBackgroundDrawable;
    }

    public Drawable getBackgroundFallback() {
        return this.mBackgroundFallback.getDrawable();
    }

    int getCaptionHeight() {
        int n = this.isShowingCaption() ? this.mDecorCaptionView.getCaptionHeight() : 0;
        return n;
    }

    @Override
    public Resources getResources() {
        return this.getContext().getResources();
    }

    boolean isShowingCaption() {
        DecorCaptionView decorCaptionView = this.mDecorCaptionView;
        boolean bl = decorCaptionView != null && decorCaptionView.isCaptionShowing();
        return bl;
    }

    @Override
    public boolean isTransitionGroup() {
        return false;
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        WindowManager.LayoutParams layoutParams = this.mWindow.getAttributes();
        this.mFloatingInsets.setEmpty();
        WindowInsets windowInsets2 = windowInsets;
        if ((layoutParams.flags & 256) == 0) {
            WindowInsets windowInsets3 = windowInsets;
            if (layoutParams.height == -2) {
                this.mFloatingInsets.top = windowInsets.getSystemWindowInsetTop();
                this.mFloatingInsets.bottom = windowInsets.getSystemWindowInsetBottom();
                windowInsets3 = windowInsets.inset(0, windowInsets.getSystemWindowInsetTop(), 0, windowInsets.getSystemWindowInsetBottom());
            }
            windowInsets2 = windowInsets3;
            if (this.mWindow.getAttributes().width == -2) {
                this.mFloatingInsets.left = windowInsets3.getSystemWindowInsetTop();
                this.mFloatingInsets.right = windowInsets3.getSystemWindowInsetBottom();
                windowInsets2 = windowInsets3.inset(windowInsets3.getSystemWindowInsetLeft(), 0, windowInsets3.getSystemWindowInsetRight(), 0);
            }
        }
        this.mFrameOffsets.set(windowInsets2.getSystemWindowInsetsAsRect());
        windowInsets = this.updateStatusGuard(this.updateColorViews(windowInsets2, true));
        if (this.getForeground() != null) {
            this.drawableChanged();
        }
        return windowInsets;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Object object = this.mWindow.getCallback();
        if (object != null && !this.mWindow.isDestroyed() && this.mFeatureId < 0) {
            object.onAttachedToWindow();
        }
        if (this.mFeatureId == -1) {
            this.mWindow.openPanelsAfterRestore();
        }
        if (!this.mWindowResizeCallbacksAdded) {
            this.getViewRootImpl().addWindowCallbacks(this);
            this.mWindowResizeCallbacksAdded = true;
        } else {
            object = this.mBackdropFrameRenderer;
            if (object != null) {
                ((BackdropFrameRenderer)object).onConfigurationChange();
            }
        }
        this.mWindow.onViewRootImplSet(this.getViewRootImpl());
    }

    @Override
    public void onCloseSystemDialogs(String string2) {
        if (this.mFeatureId >= 0) {
            this.mWindow.closeAllPanels();
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.updateDecorCaptionStatus(configuration);
        this.updateAvailableWidth();
        this.initializeElevation();
    }

    @Override
    public boolean onContentDrawn(int n, int n2, int n3, int n4) {
        BackdropFrameRenderer backdropFrameRenderer = this.mBackdropFrameRenderer;
        if (backdropFrameRenderer == null) {
            return false;
        }
        return backdropFrameRenderer.onContentDrawn(n, n2, n3, n4);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Object object = this.mWindow.getCallback();
        if (object != null && this.mFeatureId < 0) {
            object.onDetachedFromWindow();
        }
        if (this.mWindow.mDecorContentParent != null) {
            this.mWindow.mDecorContentParent.dismissPopups();
        }
        if (this.mPrimaryActionModePopup != null) {
            this.removeCallbacks(this.mShowPrimaryActionModePopup);
            if (this.mPrimaryActionModePopup.isShowing()) {
                this.mPrimaryActionModePopup.dismiss();
            }
            this.mPrimaryActionModePopup = null;
        }
        if ((object = this.mFloatingToolbar) != null) {
            ((FloatingToolbar)object).dismiss();
            this.mFloatingToolbar = null;
        }
        if ((object = this.mWindow.getPanelState(0, false)) != null && ((PhoneWindow.PanelFeatureState)object).menu != null && this.mFeatureId < 0) {
            ((PhoneWindow.PanelFeatureState)object).menu.close();
        }
        this.releaseThreadedRenderer();
        if (this.mWindowResizeCallbacksAdded) {
            this.getViewRootImpl().removeWindowCallbacks(this);
            this.mWindowResizeCallbacksAdded = false;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mBackgroundFallback.draw(this, this.mContentRoot, canvas, this.mWindow.mContentParent, this.mStatusColorViewState.view, this.mNavigationColorViewState.view);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getAction();
        if (this.mHasCaption && this.isShowingCaption() && n == 0 && this.isOutOfInnerBounds((int)motionEvent.getX(), (int)motionEvent.getY())) {
            return true;
        }
        if (this.mFeatureId >= 0 && n == 0 && this.isOutOfBounds((int)motionEvent.getX(), (int)motionEvent.getY())) {
            this.mWindow.closePanel(this.mFeatureId);
            return true;
        }
        return false;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        this.getOutsets(this.mOutsets);
        if (this.mOutsets.left > 0) {
            this.offsetLeftAndRight(-this.mOutsets.left);
        }
        if (this.mOutsets.top > 0) {
            this.offsetTopAndBottom(-this.mOutsets.top);
        }
        if (this.mApplyFloatingVerticalInsets) {
            this.offsetTopAndBottom(this.mFloatingInsets.top);
        }
        if (this.mApplyFloatingHorizontalInsets) {
            this.offsetLeftAndRight(this.mFloatingInsets.left);
        }
        this.updateElevation();
        this.mAllowUpdateElevation = true;
        if (bl && this.mResizeMode == 1) {
            this.getViewRootImpl().requestInvalidateRootRenderNode();
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        boolean bl;
        int n3;
        DisplayMetrics displayMetrics;
        boolean bl2;
        int n4;
        int n5;
        Object object;
        block28 : {
            block27 : {
                block26 : {
                    block25 : {
                        displayMetrics = this.getContext().getResources().getDisplayMetrics();
                        bl = this.getResources().getConfiguration().orientation == 1;
                        n4 = View.MeasureSpec.getMode(n);
                        n5 = View.MeasureSpec.getMode(n2);
                        bl2 = false;
                        this.mApplyFloatingHorizontalInsets = false;
                        if (n4 == Integer.MIN_VALUE) {
                            object = this.mWindow;
                            object = bl ? ((PhoneWindow)object).mFixedWidthMinor : ((PhoneWindow)object).mFixedWidthMajor;
                            if (object != null && ((TypedValue)object).type != 0) {
                                n3 = ((TypedValue)object).type == 5 ? (int)((TypedValue)object).getDimension(displayMetrics) : (((TypedValue)object).type == 6 ? (int)((TypedValue)object).getFraction(displayMetrics.widthPixels, displayMetrics.widthPixels) : 0);
                                n = View.MeasureSpec.getSize(n);
                                if (n3 > 0) {
                                    n = View.MeasureSpec.makeMeasureSpec(Math.min(n3, n), 1073741824);
                                    bl2 = true;
                                } else {
                                    n = View.MeasureSpec.makeMeasureSpec(n - this.mFloatingInsets.left - this.mFloatingInsets.right, Integer.MIN_VALUE);
                                    this.mApplyFloatingHorizontalInsets = true;
                                }
                            }
                        }
                        this.mApplyFloatingVerticalInsets = false;
                        if (n5 == Integer.MIN_VALUE && (object = bl ? this.mWindow.mFixedHeightMajor : this.mWindow.mFixedHeightMinor) != null && ((TypedValue)object).type != 0) {
                            n3 = ((TypedValue)object).type == 5 ? (int)((TypedValue)object).getDimension(displayMetrics) : (((TypedValue)object).type == 6 ? (int)((TypedValue)object).getFraction(displayMetrics.heightPixels, displayMetrics.heightPixels) : 0);
                            n5 = View.MeasureSpec.getSize(n2);
                            if (n3 > 0) {
                                n2 = View.MeasureSpec.makeMeasureSpec(Math.min(n3, n5), 1073741824);
                            } else if ((this.mWindow.getAttributes().flags & 256) == 0) {
                                n2 = View.MeasureSpec.makeMeasureSpec(n5 - this.mFloatingInsets.top - this.mFloatingInsets.bottom, Integer.MIN_VALUE);
                                this.mApplyFloatingVerticalInsets = true;
                            }
                        }
                        this.getOutsets(this.mOutsets);
                        if (this.mOutsets.top > 0) break block25;
                        n3 = n2;
                        if (this.mOutsets.bottom <= 0) break block26;
                    }
                    n5 = View.MeasureSpec.getMode(n2);
                    n3 = n2;
                    if (n5 != 0) {
                        n2 = View.MeasureSpec.getSize(n2);
                        n3 = View.MeasureSpec.makeMeasureSpec(this.mOutsets.top + n2 + this.mOutsets.bottom, n5);
                    }
                }
                if (this.mOutsets.left > 0) break block27;
                n2 = n;
                if (this.mOutsets.right <= 0) break block28;
            }
            n5 = View.MeasureSpec.getMode(n);
            n2 = n;
            if (n5 != 0) {
                n = View.MeasureSpec.getSize(n);
                n2 = View.MeasureSpec.makeMeasureSpec(this.mOutsets.left + n + this.mOutsets.right, n5);
            }
        }
        super.onMeasure(n2, n3);
        int n6 = this.getMeasuredWidth();
        int n7 = 0;
        int n8 = View.MeasureSpec.makeMeasureSpec(n6, 1073741824);
        n5 = n7;
        n2 = n8;
        if (!bl2) {
            n5 = n7;
            n2 = n8;
            if (n4 == Integer.MIN_VALUE) {
                object = this.mWindow;
                object = bl ? ((PhoneWindow)object).mMinWidthMinor : ((PhoneWindow)object).mMinWidthMajor;
                n5 = n7;
                n2 = n8;
                if (((TypedValue)object).type != 0) {
                    if (((TypedValue)object).type == 5) {
                        n = (int)((TypedValue)object).getDimension(displayMetrics);
                    } else if (((TypedValue)object).type == 6) {
                        float f = this.mAvailableWidth;
                        n = (int)((TypedValue)object).getFraction(f, f);
                    } else {
                        n = 0;
                    }
                    n5 = n7;
                    n2 = n8;
                    if (n6 < n) {
                        n2 = View.MeasureSpec.makeMeasureSpec(n, 1073741824);
                        n5 = 1;
                    }
                }
            }
        }
        if (n5 != 0) {
            super.onMeasure(n2, n3);
        }
    }

    @Override
    public void onMovedToDisplay(int n, Configuration configuration) {
        super.onMovedToDisplay(n, configuration);
        this.getContext().updateDisplay(n);
    }

    @Override
    public void onPostDraw(RecordingCanvas recordingCanvas) {
        this.drawResizingShadowIfNeeded(recordingCanvas);
        this.drawLegacyNavigationBarBackground(recordingCanvas);
    }

    @Override
    public void onRequestDraw(boolean bl) {
        BackdropFrameRenderer backdropFrameRenderer = this.mBackdropFrameRenderer;
        if (backdropFrameRenderer != null) {
            backdropFrameRenderer.onRequestDraw(bl);
        } else if (bl && this.isAttachedToWindow()) {
            this.getViewRootImpl().reportDrawFinish();
        }
    }

    void onResourcesLoaded(LayoutInflater object, int n) {
        if (this.mBackdropFrameRenderer != null) {
            this.loadBackgroundDrawablesIfNeeded();
            this.mBackdropFrameRenderer.onResourcesLoaded(this, this.mResizingBackgroundDrawable, this.mCaptionBackgroundDrawable, this.mUserCaptionBackgroundDrawable, this.getCurrentColor(this.mStatusColorViewState), this.getCurrentColor(this.mNavigationColorViewState));
        }
        this.mDecorCaptionView = this.createDecorCaptionView((LayoutInflater)object);
        object = ((LayoutInflater)object).inflate(n, null);
        DecorCaptionView decorCaptionView = this.mDecorCaptionView;
        if (decorCaptionView != null) {
            if (decorCaptionView.getParent() == null) {
                this.addView((View)this.mDecorCaptionView, new ViewGroup.LayoutParams(-1, -1));
            }
            this.mDecorCaptionView.addView((View)object, new ViewGroup.MarginLayoutParams(-1, -1));
        } else {
            this.addView((View)object, 0, new ViewGroup.LayoutParams(-1, -1));
        }
        this.mContentRoot = (ViewGroup)object;
        this.initializeElevation();
    }

    @Override
    public void onRootViewScrollYChanged(int n) {
        this.mRootScrollY = n;
        DecorCaptionView decorCaptionView = this.mDecorCaptionView;
        if (decorCaptionView != null) {
            decorCaptionView.onRootViewScrollYChanged(n);
        }
        this.updateColorViewTranslations();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.onInterceptTouchEvent(motionEvent);
    }

    @Override
    public void onWindowDragResizeEnd() {
        this.releaseThreadedRenderer();
        this.updateColorViews(null, false);
        this.mResizeMode = -1;
        this.getViewRootImpl().requestInvalidateRootRenderNode();
    }

    @Override
    public void onWindowDragResizeStart(Rect rect, boolean bl, Rect rect2, Rect rect3, int n) {
        if (this.mWindow.isDestroyed()) {
            this.releaseThreadedRenderer();
            return;
        }
        if (this.mBackdropFrameRenderer != null) {
            return;
        }
        ThreadedRenderer threadedRenderer = this.getThreadedRenderer();
        if (threadedRenderer != null) {
            this.loadBackgroundDrawablesIfNeeded();
            this.mBackdropFrameRenderer = new BackdropFrameRenderer(this, threadedRenderer, rect, this.mResizingBackgroundDrawable, this.mCaptionBackgroundDrawable, this.mUserCaptionBackgroundDrawable, this.getCurrentColor(this.mStatusColorViewState), this.getCurrentColor(this.mNavigationColorViewState), bl, rect2, rect3);
            this.updateElevation();
            this.updateColorViews(null, false);
        }
        this.mResizeMode = n;
        this.getViewRootImpl().requestInvalidateRootRenderNode();
    }

    @Override
    public void onWindowFocusChanged(boolean bl) {
        Object object;
        super.onWindowFocusChanged(bl);
        if (this.mWindow.hasFeature(0) && !bl && this.mWindow.mPanelChordingKey != 0) {
            this.mWindow.closePanel(0);
        }
        if ((object = this.mWindow.getCallback()) != null && !this.mWindow.isDestroyed() && this.mFeatureId < 0) {
            object.onWindowFocusChanged(bl);
        }
        if ((object = this.mPrimaryActionMode) != null) {
            ((ActionMode)object).onWindowFocusChanged(bl);
        }
        if ((object = this.mFloatingActionMode) != null) {
            ((ActionMode)object).onWindowFocusChanged(bl);
        }
        this.updateElevation();
    }

    @Override
    public void onWindowSizeIsChanging(Rect rect, boolean bl, Rect rect2, Rect rect3) {
        BackdropFrameRenderer backdropFrameRenderer = this.mBackdropFrameRenderer;
        if (backdropFrameRenderer != null) {
            backdropFrameRenderer.setTargetRect(rect, bl, rect2, rect3);
        }
    }

    @Override
    public void onWindowSystemUiVisibilityChanged(int n) {
        this.updateColorViews(null, true);
        this.updateDecorCaptionStatus(this.getResources().getConfiguration());
        View view = this.mStatusGuard;
        if (view != null && view.getVisibility() == 0) {
            this.updateStatusGuardColor();
        }
    }

    @Override
    public void requestKeyboardShortcuts(List<KeyboardShortcutGroup> list, int n) {
        Object object = this.mWindow.getPanelState(0, false);
        object = object != null ? ((PhoneWindow.PanelFeatureState)object).menu : null;
        if (!this.mWindow.isDestroyed() && this.mWindow.getCallback() != null) {
            this.mWindow.getCallback().onProvideKeyboardShortcuts(list, (Menu)object, n);
        }
    }

    @Override
    public void sendAccessibilityEvent(int n) {
        if (!AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            return;
        }
        int n2 = this.mFeatureId;
        if ((n2 == 0 || n2 == 6 || n2 == 2 || n2 == 5) && this.getChildCount() == 1) {
            this.getChildAt(0).sendAccessibilityEvent(n);
        } else {
            super.sendAccessibilityEvent(n);
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable drawable2) {
        if (this.mOriginalBackgroundDrawable != drawable2) {
            this.mOriginalBackgroundDrawable = drawable2;
            this.updateBackgroundDrawable();
            if (!View.sBrokenWindowBackground) {
                this.drawableChanged();
            }
        }
    }

    void setBackgroundFallback(Drawable drawable2) {
        this.mBackgroundFallback.setDrawable(drawable2);
        boolean bl = this.getBackground() == null && !this.mBackgroundFallback.hasFallback();
        this.setWillNotDraw(bl);
    }

    @Override
    protected boolean setFrame(int n, int n2, int n3, int n4) {
        boolean bl = super.setFrame(n, n2, n3, n4);
        if (bl) {
            Object object;
            Rect rect = this.mDrawingBounds;
            this.getDrawingRect(rect);
            Object object2 = this.getForeground();
            if (object2 != null) {
                object = this.mFrameOffsets;
                rect.left += ((Rect)object).left;
                rect.top += ((Rect)object).top;
                rect.right -= ((Rect)object).right;
                rect.bottom -= ((Rect)object).bottom;
                ((Drawable)object2).setBounds(rect);
                object2 = this.mFramePadding;
                rect.left += ((Rect)object2).left - ((Rect)object).left;
                rect.top += ((Rect)object2).top - ((Rect)object).top;
                rect.right -= ((Rect)object2).right - ((Rect)object).right;
                rect.bottom -= ((Rect)object2).bottom - ((Rect)object).bottom;
            }
            if ((object = super.getBackground()) != null) {
                ((Drawable)object).setBounds(rect);
            }
        }
        return bl;
    }

    @Override
    public void setOutlineProvider(ViewOutlineProvider viewOutlineProvider) {
        super.setOutlineProvider(viewOutlineProvider);
        this.mLastOutlineProvider = viewOutlineProvider;
    }

    @Override
    public void setSurfaceFormat(int n) {
        this.mWindow.setFormat(n);
    }

    @Override
    public void setSurfaceKeepScreenOn(boolean bl) {
        if (bl) {
            this.mWindow.addFlags(128);
        } else {
            this.mWindow.clearFlags(128);
        }
    }

    @Override
    public void setSurfaceType(int n) {
        this.mWindow.setType(n);
    }

    void setUserCaptionBackgroundDrawable(Drawable drawable2) {
        this.mUserCaptionBackgroundDrawable = drawable2;
        BackdropFrameRenderer backdropFrameRenderer = this.mBackdropFrameRenderer;
        if (backdropFrameRenderer != null) {
            backdropFrameRenderer.setUserCaptionBackgroundDrawable(drawable2);
        }
    }

    void setWindow(PhoneWindow object) {
        this.mWindow = object;
        object = this.getContext();
        if (object instanceof DecorContext) {
            ((DecorContext)object).setPhoneWindow(this.mWindow);
        }
    }

    public void setWindowBackground(Drawable drawable2) {
        block6 : {
            block8 : {
                boolean bl;
                Drawable drawable3;
                block10 : {
                    block9 : {
                        boolean bl2;
                        block7 : {
                            if (this.mOriginalBackgroundDrawable == drawable2) break block6;
                            this.mOriginalBackgroundDrawable = drawable2;
                            this.updateBackgroundDrawable();
                            bl2 = false;
                            bl = false;
                            if (drawable2 == null) break block7;
                            if (this.mWindow.isTranslucent() || this.mWindow.isShowingWallpaper()) {
                                bl = true;
                            }
                            this.mResizingBackgroundDrawable = DecorView.enforceNonTranslucentBackground(drawable2, bl);
                            break block8;
                        }
                        drawable2 = this.mWindow.mBackgroundDrawable;
                        drawable3 = this.mWindow.mBackgroundFallbackDrawable;
                        if (this.mWindow.isTranslucent()) break block9;
                        bl = bl2;
                        if (!this.mWindow.isShowingWallpaper()) break block10;
                    }
                    bl = true;
                }
                this.mResizingBackgroundDrawable = DecorView.getResizingBackgroundDrawable(drawable2, drawable3, bl);
            }
            drawable2 = this.mResizingBackgroundDrawable;
            if (drawable2 != null) {
                drawable2.getPadding(this.mBackgroundPadding);
            } else {
                this.mBackgroundPadding.setEmpty();
            }
            this.drawableChanged();
        }
    }

    public void setWindowFrame(Drawable drawable2) {
        if (this.getForeground() != drawable2) {
            this.setForeground(drawable2);
            if (drawable2 != null) {
                drawable2.getPadding(this.mFramePadding);
            } else {
                this.mFramePadding.setEmpty();
            }
            this.drawableChanged();
        }
    }

    boolean shouldAnimatePrimaryActionModeView() {
        return this.isLaidOut();
    }

    @Override
    public boolean showContextMenuForChild(View view) {
        return this.showContextMenuForChildInternal(view, Float.NaN, Float.NaN);
    }

    @Override
    public boolean showContextMenuForChild(View view, float f, float f2) {
        return this.showContextMenuForChildInternal(view, f, f2);
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback) {
        return this.startActionMode(callback, 0);
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback, int n) {
        return this.startActionMode(this, callback, n);
    }

    @Override
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback) {
        return this.startActionModeForChild(view, callback, 0);
    }

    @Override
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int n) {
        return this.startActionMode(view, callback, n);
    }

    void startChanging() {
        this.mChanging = true;
    }

    public boolean superDispatchGenericMotionEvent(MotionEvent motionEvent) {
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    public boolean superDispatchKeyEvent(KeyEvent keyEvent) {
        int n = keyEvent.getKeyCode();
        boolean bl = true;
        if (n == 4) {
            n = keyEvent.getAction();
            ActionMode actionMode = this.mPrimaryActionMode;
            if (actionMode != null) {
                if (n == 1) {
                    actionMode.finish();
                }
                return true;
            }
        }
        if (super.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        if (this.getViewRootImpl() == null || !this.getViewRootImpl().dispatchUnhandledKeyEvent(keyEvent)) {
            bl = false;
        }
        return bl;
    }

    public boolean superDispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return super.dispatchKeyShortcutEvent(keyEvent);
    }

    public boolean superDispatchTouchEvent(MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean superDispatchTrackballEvent(MotionEvent motionEvent) {
        return super.dispatchTrackballEvent(motionEvent);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DecorView@");
        stringBuilder.append(Integer.toHexString(this.hashCode()));
        stringBuilder.append("[");
        stringBuilder.append(DecorView.getTitleSuffix(this.mWindow.getAttributes()));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    WindowInsets updateColorViews(WindowInsets windowInsets, boolean bl) {
        int n;
        int n2;
        int n3;
        int n4;
        Object object = this.mWindow.getAttributes();
        int n5 = ((WindowManager.LayoutParams)object).systemUiVisibility | this.getWindowSystemUiVisibility();
        int n6 = this.mWindow.getAttributes().type == 2011 ? 1 : 0;
        if (!this.mWindow.mIsFloating || n6 != 0) {
            boolean bl2 = this.isLaidOut();
            n6 = ((this.mLastWindowFlags ^ ((WindowManager.LayoutParams)object).flags) & Integer.MIN_VALUE) != 0 ? 1 : 0;
            n3 = bl2 ^ true | n6;
            this.mLastWindowFlags = ((WindowManager.LayoutParams)object).flags;
            if (windowInsets != null) {
                this.mLastTopInset = DecorView.getColorViewTopInset(windowInsets.getStableInsetTop(), windowInsets.getSystemWindowInsetTop());
                this.mLastBottomInset = DecorView.getColorViewBottomInset(windowInsets.getStableInsetBottom(), windowInsets.getSystemWindowInsetBottom());
                this.mLastRightInset = DecorView.getColorViewRightInset(windowInsets.getStableInsetRight(), windowInsets.getSystemWindowInsetRight());
                this.mLastLeftInset = DecorView.getColorViewRightInset(windowInsets.getStableInsetLeft(), windowInsets.getSystemWindowInsetLeft());
                bl2 = windowInsets.getStableInsetTop() != 0;
                n6 = bl2 != this.mLastHasTopStableInset ? 1 : 0;
                this.mLastHasTopStableInset = bl2;
                bl2 = windowInsets.getStableInsetBottom() != 0;
                n = bl2 != this.mLastHasBottomStableInset ? 1 : 0;
                this.mLastHasBottomStableInset = bl2;
                bl2 = windowInsets.getStableInsetRight() != 0;
                n2 = bl2 != this.mLastHasRightStableInset ? 1 : 0;
                this.mLastHasRightStableInset = bl2;
                bl2 = windowInsets.getStableInsetLeft() != 0;
                n4 = bl2 != this.mLastHasLeftStableInset ? 1 : 0;
                this.mLastHasLeftStableInset = bl2;
                this.mLastShouldAlwaysConsumeSystemBars = windowInsets.shouldAlwaysConsumeSystemBars();
                n2 = n3 | n6 | n | n2 | n4;
            } else {
                n2 = n3;
            }
            boolean bl3 = DecorView.isNavBarToRightEdge(this.mLastBottomInset, this.mLastRightInset);
            boolean bl4 = DecorView.isNavBarToLeftEdge(this.mLastBottomInset, this.mLastLeftInset);
            n = DecorView.getNavBarSize(this.mLastBottomInset, this.mLastRightInset, this.mLastLeftInset);
            Object object2 = this.mNavigationColorViewState;
            n4 = this.calculateNavigationBarColor();
            n3 = this.mWindow.mNavigationBarDividerColor;
            bl2 = bl3 || bl4;
            boolean bl5 = bl && n2 == 0;
            boolean bl6 = this.mForceWindowDrawsBarBackgrounds;
            n6 = 0;
            this.updateColorViewInt((ColorViewState)object2, n5, n4, n3, n, bl2, bl4, 0, bl5, bl6);
            bl2 = this.mDrawLegacyNavigationBarBackground;
            n = this.mNavigationColorViewState.visible && (this.mWindow.getAttributes().flags & Integer.MIN_VALUE) == 0 ? 1 : n6;
            this.mDrawLegacyNavigationBarBackground = n;
            if (bl2 != this.mDrawLegacyNavigationBarBackground && (object2 = this.getViewRootImpl()) != null) {
                ((ViewRootImpl)object2).requestInvalidateRootRenderNode();
            }
            n = bl3 && this.mNavigationColorViewState.present ? 1 : n6;
            n4 = bl4 && this.mNavigationColorViewState.present ? 1 : n6;
            n = n != 0 ? this.mLastRightInset : (n4 != 0 ? this.mLastLeftInset : n6);
            object2 = this.mStatusColorViewState;
            n3 = this.calculateStatusBarColor();
            int n7 = this.mLastTopInset;
            if (bl && n2 == 0) {
                n6 = 1;
            }
            this.updateColorViewInt((ColorViewState)object2, n5, n3, 0, n7, false, n4 != 0, n, n6 != 0, this.mForceWindowDrawsBarBackgrounds);
        }
        n = (n5 & 2) != 0 ? 1 : 0;
        n6 = this.mForceWindowDrawsBarBackgrounds && (((WindowManager.LayoutParams)object).flags & Integer.MIN_VALUE) == 0 && (n5 & 512) == 0 && n == 0 || this.mLastShouldAlwaysConsumeSystemBars && n != 0 ? 1 : 0;
        n = (((WindowManager.LayoutParams)object).flags & Integer.MIN_VALUE) != 0 && (n5 & 512) == 0 && n == 0 || n6 != 0 ? 1 : 0;
        n2 = (n5 & 4) == 0 && (((WindowManager.LayoutParams)object).flags & 1024) == 0 ? 0 : 1;
        n2 = (n5 & 1024) == 0 && (((WindowManager.LayoutParams)object).flags & 256) == 0 && (((WindowManager.LayoutParams)object).flags & 65536) == 0 && this.mForceWindowDrawsBarBackgrounds && this.mLastTopInset != 0 || this.mLastShouldAlwaysConsumeSystemBars && n2 != 0 ? 1 : 0;
        n2 = n2 != 0 ? this.mLastTopInset : 0;
        n4 = n != 0 ? this.mLastRightInset : 0;
        n3 = n != 0 ? this.mLastBottomInset : 0;
        n = n != 0 ? this.mLastLeftInset : 0;
        object = this.mContentRoot;
        if (object != null && ((View)object).getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            object = (ViewGroup.MarginLayoutParams)this.mContentRoot.getLayoutParams();
            if (((ViewGroup.MarginLayoutParams)object).topMargin != n2 || ((ViewGroup.MarginLayoutParams)object).rightMargin != n4 || ((ViewGroup.MarginLayoutParams)object).bottomMargin != n3 || ((ViewGroup.MarginLayoutParams)object).leftMargin != n) {
                ((ViewGroup.MarginLayoutParams)object).topMargin = n2;
                ((ViewGroup.MarginLayoutParams)object).rightMargin = n4;
                ((ViewGroup.MarginLayoutParams)object).bottomMargin = n3;
                ((ViewGroup.MarginLayoutParams)object).leftMargin = n;
                this.mContentRoot.setLayoutParams((ViewGroup.LayoutParams)object);
                if (windowInsets == null) {
                    this.requestApplyInsets();
                }
            }
            if (windowInsets != null) {
                windowInsets = windowInsets.inset(n, n2, n4, n3);
            }
        }
        this.mBackgroundInsets = n6 != 0 ? Insets.of(this.mLastLeftInset, 0, this.mLastRightInset, this.mLastBottomInset) : Insets.NONE;
        this.updateBackgroundDrawable();
        object = windowInsets;
        if (windowInsets != null) {
            object = windowInsets.consumeStableInsets();
        }
        return object;
    }

    void updateDecorCaptionShade() {
        if (this.mDecorCaptionView != null) {
            this.setDecorCaptionShade(this.getContext(), this.mDecorCaptionView);
        }
    }

    void updateLogTag(WindowManager.LayoutParams layoutParams) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DecorView[");
        stringBuilder.append(DecorView.getTitleSuffix(layoutParams));
        stringBuilder.append("]");
        this.mLogTag = stringBuilder.toString();
    }

    public void updatePictureInPictureOutlineProvider(boolean bl) {
        if (this.mIsInPictureInPictureMode == bl) {
            return;
        }
        if (bl) {
            Window.WindowControllerCallback windowControllerCallback = this.mWindow.getWindowControllerCallback();
            if (windowControllerCallback != null && windowControllerCallback.isTaskRoot()) {
                super.setOutlineProvider(PIP_OUTLINE_PROVIDER);
            }
        } else {
            ViewOutlineProvider viewOutlineProvider;
            ViewOutlineProvider viewOutlineProvider2 = this.getOutlineProvider();
            if (viewOutlineProvider2 != (viewOutlineProvider = this.mLastOutlineProvider)) {
                this.setOutlineProvider(viewOutlineProvider);
            }
        }
        this.mIsInPictureInPictureMode = bl;
    }

    @Override
    public InputQueue.Callback willYouTakeTheInputQueue() {
        InputQueue.Callback callback = this.mFeatureId < 0 ? this.mWindow.mTakeInputQueueCallback : null;
        return callback;
    }

    @Override
    public SurfaceHolder.Callback2 willYouTakeTheSurface() {
        SurfaceHolder.Callback2 callback2 = this.mFeatureId < 0 ? this.mWindow.mTakeSurfaceCallback : null;
        return callback2;
    }

    private class ActionModeCallback2Wrapper
    extends ActionMode.Callback2 {
        private final ActionMode.Callback mWrapped;

        public ActionModeCallback2Wrapper(ActionMode.Callback callback) {
            this.mWrapped = callback;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu2) {
            return this.mWrapped.onCreateActionMode(actionMode, menu2);
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            int n;
            Object object;
            Object object2;
            this.mWrapped.onDestroyActionMode(actionMode);
            int n2 = DecorView.access$500((DecorView)DecorView.this).getApplicationInfo().targetSdkVersion;
            boolean bl = false;
            boolean bl2 = false;
            n2 = n2 >= 23 ? 1 : 0;
            if (n2 != 0) {
                n2 = actionMode == DecorView.this.mPrimaryActionMode ? 1 : 0;
                if (actionMode == DecorView.this.mFloatingActionMode) {
                    bl2 = true;
                }
                if (n2 == 0 && actionMode.getType() == 0) {
                    object2 = DecorView.this.mLogTag;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Destroying unexpected ActionMode instance of TYPE_PRIMARY; ");
                    ((StringBuilder)object).append(actionMode);
                    ((StringBuilder)object).append(" was not the current primary action mode! Expected ");
                    ((StringBuilder)object).append(DecorView.this.mPrimaryActionMode);
                    Log.e((String)object2, ((StringBuilder)object).toString());
                }
                bl = bl2;
                n = n2;
                if (!bl2) {
                    bl = bl2;
                    n = n2;
                    if (actionMode.getType() == 1) {
                        object2 = DecorView.this.mLogTag;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Destroying unexpected ActionMode instance of TYPE_FLOATING; ");
                        ((StringBuilder)object).append(actionMode);
                        ((StringBuilder)object).append(" was not the current floating action mode! Expected ");
                        ((StringBuilder)object).append(DecorView.this.mFloatingActionMode);
                        Log.e((String)object2, ((StringBuilder)object).toString());
                        bl = bl2;
                        n = n2;
                    }
                }
            } else {
                n2 = actionMode.getType() == 0 ? 1 : 0;
                n = n2;
                if (actionMode.getType() == 1) {
                    bl = true;
                    n = n2;
                }
            }
            if (n != 0) {
                if (DecorView.this.mPrimaryActionModePopup != null) {
                    object2 = DecorView.this;
                    ((View)object2).removeCallbacks(((DecorView)object2).mShowPrimaryActionModePopup);
                }
                if (DecorView.this.mPrimaryActionModeView != null) {
                    DecorView.this.endOnGoingFadeAnimation();
                    object = DecorView.this.mPrimaryActionModeView;
                    object2 = DecorView.this;
                    ((DecorView)object2).mFadeAnim = ObjectAnimator.ofFloat(((DecorView)object2).mPrimaryActionModeView, View.ALPHA, 1.0f, 0.0f);
                    DecorView.this.mFadeAnim.addListener(new Animator.AnimatorListener((ActionBarContextView)object){
                        final /* synthetic */ ActionBarContextView val$lastActionModeView;
                        {
                            this.val$lastActionModeView = actionBarContextView;
                        }

                        @Override
                        public void onAnimationCancel(Animator animator2) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator2) {
                            if (this.val$lastActionModeView == DecorView.this.mPrimaryActionModeView) {
                                this.val$lastActionModeView.setVisibility(8);
                                if (DecorView.this.mPrimaryActionModePopup != null) {
                                    DecorView.this.mPrimaryActionModePopup.dismiss();
                                }
                                this.val$lastActionModeView.killMode();
                                DecorView.this.mFadeAnim = null;
                                DecorView.this.requestApplyInsets();
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animator animator2) {
                        }

                        @Override
                        public void onAnimationStart(Animator animator2) {
                        }
                    });
                    DecorView.this.mFadeAnim.start();
                }
                DecorView.this.mPrimaryActionMode = null;
            } else if (bl) {
                DecorView.this.cleanupFloatingActionModeViews();
                DecorView.this.mFloatingActionMode = null;
            }
            if (DecorView.this.mWindow.getCallback() != null && !DecorView.this.mWindow.isDestroyed()) {
                try {
                    DecorView.this.mWindow.getCallback().onActionModeFinished(actionMode);
                }
                catch (AbstractMethodError abstractMethodError) {
                    // empty catch block
                }
            }
            DecorView.this.requestFitSystemWindows();
        }

        @Override
        public void onGetContentRect(ActionMode actionMode, View view, Rect rect) {
            ActionMode.Callback callback = this.mWrapped;
            if (callback instanceof ActionMode.Callback2) {
                ((ActionMode.Callback2)callback).onGetContentRect(actionMode, view, rect);
            } else {
                super.onGetContentRect(actionMode, view, rect);
            }
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu2) {
            DecorView.this.requestFitSystemWindows();
            return this.mWrapped.onPrepareActionMode(actionMode, menu2);
        }

    }

    public static class ColorViewAttributes {
        final int hideWindowFlag;
        final int horizontalGravity;
        final int id;
        final int seascapeGravity;
        final int systemUiHideFlag;
        final String transitionName;
        final int translucentFlag;
        final int verticalGravity;

        private ColorViewAttributes(int n, int n2, int n3, int n4, int n5, String string2, int n6, int n7) {
            this.id = n6;
            this.systemUiHideFlag = n;
            this.translucentFlag = n2;
            this.verticalGravity = n3;
            this.horizontalGravity = n4;
            this.seascapeGravity = n5;
            this.transitionName = string2;
            this.hideWindowFlag = n7;
        }

        public boolean isPresent(int n, int n2, boolean bl) {
            bl = (this.systemUiHideFlag & n) == 0 && (this.hideWindowFlag & n2) == 0 && ((Integer.MIN_VALUE & n2) != 0 || bl);
            return bl;
        }

        public boolean isVisible(int n, int n2, int n3, boolean bl) {
            return this.isVisible(this.isPresent(n, n3, bl), n2, n3, bl);
        }

        public boolean isVisible(boolean bl, int n, int n2, boolean bl2) {
            bl = bl && (-16777216 & n) != 0 && ((this.translucentFlag & n2) == 0 || bl2);
            return bl;
        }
    }

    private static class ColorViewState {
        final ColorViewAttributes attributes;
        int color;
        boolean present = false;
        int targetVisibility = 4;
        View view = null;
        boolean visible;

        ColorViewState(ColorViewAttributes colorViewAttributes) {
            this.attributes = colorViewAttributes;
        }
    }

}

