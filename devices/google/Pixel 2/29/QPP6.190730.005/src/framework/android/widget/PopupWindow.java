/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.IBinder;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.widget.FrameLayout;
import android.widget._$$Lambda$PopupWindow$8Gc2stI5cSJZbuKX7X4Qr_vU2nI;
import android.widget._$$Lambda$PopupWindow$PopupDecorView$T99WKEnQefOCXbbKvW95WY38p_I;
import android.widget._$$Lambda$PopupWindow$nV1HS3Nc6Ck5JRIbIHe3mkyHWzc;
import com.android.internal.R;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

public class PopupWindow {
    private static final int[] ABOVE_ANCHOR_STATE_SET = new int[]{16842922};
    private static final int ANIMATION_STYLE_DEFAULT = -1;
    private static final int DEFAULT_ANCHORED_GRAVITY = 8388659;
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    @UnsupportedAppUsage
    private boolean mAboveAnchor;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private Drawable mAboveAnchorBackgroundDrawable;
    private boolean mAllowScrollingAnchorParent = true;
    @UnsupportedAppUsage
    private WeakReference<View> mAnchor;
    private WeakReference<View> mAnchorRoot;
    private int mAnchorXoff;
    private int mAnchorYoff;
    private int mAnchoredGravity;
    @UnsupportedAppUsage
    private int mAnimationStyle = -1;
    private boolean mAttachedInDecor = true;
    private boolean mAttachedInDecorSet = false;
    private Drawable mBackground;
    @UnsupportedAppUsage
    private View mBackgroundView;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private Drawable mBelowAnchorBackgroundDrawable;
    private boolean mClipToScreen;
    private boolean mClippingEnabled = true;
    @UnsupportedAppUsage
    private View mContentView;
    @UnsupportedAppUsage
    private Context mContext;
    @UnsupportedAppUsage
    private PopupDecorView mDecorView;
    private float mElevation;
    private Transition mEnterTransition;
    private Rect mEpicenterBounds;
    private Transition mExitTransition;
    private boolean mFocusable;
    private int mGravity = 0;
    private int mHeight = -2;
    @UnsupportedAppUsage
    private int mHeightMode;
    private boolean mIgnoreCheekPress = false;
    private int mInputMethodMode = 0;
    private boolean mIsAnchorRootAttached;
    @UnsupportedAppUsage
    private boolean mIsDropdown;
    @UnsupportedAppUsage
    private boolean mIsShowing;
    private boolean mIsTransitioningToDismiss;
    @UnsupportedAppUsage
    private int mLastHeight;
    @UnsupportedAppUsage
    private int mLastWidth;
    @UnsupportedAppUsage
    private boolean mLayoutInScreen;
    private boolean mLayoutInsetDecor = false;
    @UnsupportedAppUsage
    private boolean mNotTouchModal;
    private final View.OnAttachStateChangeListener mOnAnchorDetachedListener = new View.OnAttachStateChangeListener(){

        @Override
        public void onViewAttachedToWindow(View view) {
            PopupWindow.this.alignToAnchor();
        }

        @Override
        public void onViewDetachedFromWindow(View view) {
        }
    };
    private final View.OnAttachStateChangeListener mOnAnchorRootDetachedListener = new View.OnAttachStateChangeListener(){

        @Override
        public void onViewAttachedToWindow(View view) {
        }

        @Override
        public void onViewDetachedFromWindow(View view) {
            PopupWindow.this.mIsAnchorRootAttached = false;
        }
    };
    @UnsupportedAppUsage
    private OnDismissListener mOnDismissListener;
    private final View.OnLayoutChangeListener mOnLayoutChangeListener = new _$$Lambda$PopupWindow$8Gc2stI5cSJZbuKX7X4Qr_vU2nI(this);
    @UnsupportedAppUsage(maxTargetSdk=28)
    private final ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener = new _$$Lambda$PopupWindow$nV1HS3Nc6Ck5JRIbIHe3mkyHWzc(this);
    private boolean mOutsideTouchable = false;
    @UnsupportedAppUsage(maxTargetSdk=28)
    private boolean mOverlapAnchor;
    private WeakReference<View> mParentRootView;
    private boolean mPopupViewInitialLayoutDirectionInherited;
    private int mSoftInputMode = 1;
    private int mSplitTouchEnabled = -1;
    private final Rect mTempRect = new Rect();
    private final int[] mTmpAppLocation = new int[2];
    private final int[] mTmpDrawingLocation = new int[2];
    private final int[] mTmpScreenLocation = new int[2];
    @UnsupportedAppUsage
    private View.OnTouchListener mTouchInterceptor;
    private boolean mTouchable = true;
    private int mWidth = -2;
    @UnsupportedAppUsage
    private int mWidthMode;
    @UnsupportedAppUsage
    private int mWindowLayoutType = 1000;
    @UnsupportedAppUsage
    private WindowManager mWindowManager;

    public PopupWindow() {
        this(null, 0, 0);
    }

    public PopupWindow(int n, int n2) {
        this(null, n, n2);
    }

    public PopupWindow(Context context) {
        this(context, null);
    }

    public PopupWindow(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842870);
    }

    public PopupWindow(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public PopupWindow(Context object, AttributeSet object2, int n, int n2) {
        this.mContext = object;
        this.mWindowManager = (WindowManager)((Context)object).getSystemService("window");
        TypedArray typedArray = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.PopupWindow, n, n2);
        object2 = typedArray.getDrawable(0);
        this.mElevation = typedArray.getDimension(3, 0.0f);
        this.mOverlapAnchor = typedArray.getBoolean(2, false);
        this.mAnimationStyle = typedArray.hasValueOrEmpty(1) ? ((n = typedArray.getResourceId(1, 0)) == 16974594 ? -1 : n) : -1;
        Transition transition2 = this.getTransition(typedArray.getResourceId(4, 0));
        object = typedArray.hasValueOrEmpty(5) ? this.getTransition(typedArray.getResourceId(5, 0)) : (transition2 == null ? null : transition2.clone());
        typedArray.recycle();
        this.setEnterTransition(transition2);
        this.setExitTransition((Transition)object);
        this.setBackgroundDrawable((Drawable)object2);
    }

    public PopupWindow(View view) {
        this(view, 0, 0);
    }

    public PopupWindow(View view, int n, int n2) {
        this(view, n, n2, false);
    }

    public PopupWindow(View view, int n, int n2, boolean bl) {
        if (view != null) {
            this.mContext = view.getContext();
            this.mWindowManager = (WindowManager)this.mContext.getSystemService("window");
        }
        this.setContentView(view);
        this.setWidth(n);
        this.setHeight(n2);
        this.setFocusable(bl);
    }

    private void alignToAnchor() {
        WeakReference<View> weakReference = this.mAnchor;
        weakReference = weakReference != null ? (View)weakReference.get() : null;
        if (weakReference != null && ((View)((Object)weakReference)).isAttachedToWindow() && this.hasDecorView()) {
            WindowManager.LayoutParams layoutParams = this.getDecorViewLayoutParams();
            this.updateAboveAnchor(this.findDropDownPosition((View)((Object)weakReference), layoutParams, this.mAnchorXoff, this.mAnchorYoff, layoutParams.width, layoutParams.height, this.mAnchoredGravity, false));
            this.update(layoutParams.x, layoutParams.y, -1, -1, true);
        }
    }

    @UnsupportedAppUsage
    private int computeAnimationResource() {
        int n = this.mAnimationStyle;
        if (n == -1) {
            if (this.mIsDropdown) {
                n = this.mAboveAnchor ? 16974582 : 16974581;
                return n;
            }
            return 0;
        }
        return n;
    }

    private int computeFlags(int n) {
        int n2;
        block17 : {
            int n3;
            block16 : {
                n2 = n &= -8815129;
                if (this.mIgnoreCheekPress) {
                    n2 = n | 32768;
                }
                if (!this.mFocusable) {
                    n = n2 |= 8;
                    if (this.mInputMethodMode == 1) {
                        n = n2 | 131072;
                    }
                } else {
                    n = n2;
                    if (this.mInputMethodMode == 2) {
                        n = n2 | 131072;
                    }
                }
                n2 = n;
                if (!this.mTouchable) {
                    n2 = n | 16;
                }
                n3 = n2;
                if (this.mOutsideTouchable) {
                    n3 = n2 | 262144;
                }
                if (!this.mClippingEnabled) break block16;
                n = n3;
                if (!this.mClipToScreen) break block17;
            }
            n = n3 | 512;
        }
        n2 = n;
        if (this.isSplitTouchEnabled()) {
            n2 = n | 8388608;
        }
        n = n2;
        if (this.mLayoutInScreen) {
            n = n2 | 256;
        }
        n2 = n;
        if (this.mLayoutInsetDecor) {
            n2 = n | 65536;
        }
        n = n2;
        if (this.mNotTouchModal) {
            n = n2 | 32;
        }
        n2 = n;
        if (this.mAttachedInDecor) {
            n2 = n | 1073741824;
        }
        return n2;
    }

    private int computeGravity() {
        int n;
        block4 : {
            int n2;
            block5 : {
                n2 = n = this.mGravity;
                if (n == 0) {
                    n2 = 8388659;
                }
                n = n2;
                if (!this.mIsDropdown) break block4;
                if (this.mClipToScreen) break block5;
                n = n2;
                if (!this.mClippingEnabled) break block4;
            }
            n = n2 | 268435456;
        }
        return n;
    }

    private PopupBackgroundView createBackgroundView(View view) {
        Object object = this.mContentView.getLayoutParams();
        int n = object != null && ((ViewGroup.LayoutParams)object).height == -2 ? -2 : -1;
        object = new PopupBackgroundView(this.mContext);
        ((ViewGroup)object).addView(view, new FrameLayout.LayoutParams(-1, n));
        return object;
    }

    private PopupDecorView createDecorView(View view) {
        Object object = this.mContentView.getLayoutParams();
        int n = object != null && ((ViewGroup.LayoutParams)object).height == -2 ? -2 : -1;
        object = new PopupDecorView(this.mContext);
        ((ViewGroup)object).addView(view, -1, n);
        ((ViewGroup)object).setClipChildren(false);
        ((ViewGroup)object).setClipToPadding(false);
        return object;
    }

    private void dismissImmediate(View view, ViewGroup viewGroup, View view2) {
        if (view.getParent() != null) {
            this.mWindowManager.removeViewImmediate(view);
        }
        if (viewGroup != null) {
            viewGroup.removeView(view2);
        }
        this.mDecorView = null;
        this.mBackgroundView = null;
        this.mIsTransitioningToDismiss = false;
    }

    private View getAppRootView(View view) {
        View view2 = WindowManagerGlobal.getInstance().getWindowView(view.getApplicationWindowToken());
        if (view2 != null) {
            return view2;
        }
        return view.getRootView();
    }

    private Transition getTransition(int n) {
        Transition transition2;
        if (n != 0 && n != 17760256 && (transition2 = TransitionInflater.from(this.mContext).inflateTransition(n)) != null && (n = transition2 instanceof TransitionSet && ((TransitionSet)transition2).getTransitionCount() == 0 ? 1 : 0) == 0) {
            return transition2;
        }
        return null;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    private void invokePopup(WindowManager.LayoutParams object) {
        Object object2 = this.mContext;
        if (object2 != null) {
            ((WindowManager.LayoutParams)object).packageName = ((Context)object2).getPackageName();
        }
        object2 = this.mDecorView;
        ((View)object2).setFitsSystemWindows(this.mLayoutInsetDecor);
        this.setLayoutDirectionFromAnchor();
        this.mWindowManager.addView((View)object2, (ViewGroup.LayoutParams)object);
        object = this.mEnterTransition;
        if (object != null) {
            ((PopupDecorView)object2).requestEnterTransition((Transition)object);
        }
    }

    public static /* synthetic */ void lambda$nV1HS3Nc6Ck5JRIbIHe3mkyHWzc(PopupWindow popupWindow) {
        popupWindow.alignToAnchor();
    }

    private boolean positionInDisplayHorizontal(WindowManager.LayoutParams layoutParams, int n, int n2, int n3, int n4, int n5, boolean bl) {
        boolean bl2 = true;
        n2 = n3 - n2;
        layoutParams.x += n2;
        n3 = layoutParams.x + n;
        if (n3 > n5) {
            layoutParams.x -= n3 - n5;
        }
        boolean bl3 = bl2;
        if (layoutParams.x < n4) {
            layoutParams.x = n4;
            n3 = n5 - n4;
            if (bl && n > n3) {
                layoutParams.width = n3;
                bl3 = bl2;
            } else {
                bl3 = false;
            }
        }
        layoutParams.x -= n2;
        return bl3;
    }

    private boolean positionInDisplayVertical(WindowManager.LayoutParams layoutParams, int n, int n2, int n3, int n4, int n5, boolean bl) {
        boolean bl2 = true;
        n2 = n3 - n2;
        layoutParams.y += n2;
        layoutParams.height = n;
        n3 = layoutParams.y + n;
        if (n3 > n5) {
            layoutParams.y -= n3 - n5;
        }
        boolean bl3 = bl2;
        if (layoutParams.y < n4) {
            layoutParams.y = n4;
            n3 = n5 - n4;
            if (bl && n > n3) {
                layoutParams.height = n3;
                bl3 = bl2;
            } else {
                bl3 = false;
            }
        }
        layoutParams.y -= n2;
        return bl3;
    }

    @UnsupportedAppUsage
    private void preparePopup(WindowManager.LayoutParams layoutParams) {
        if (this.mContentView != null && this.mContext != null && this.mWindowManager != null) {
            PopupDecorView popupDecorView;
            if (layoutParams.accessibilityTitle == null) {
                layoutParams.accessibilityTitle = this.mContext.getString(17040871);
            }
            if ((popupDecorView = this.mDecorView) != null) {
                popupDecorView.cancelTransitions();
            }
            if (this.mBackground != null) {
                this.mBackgroundView = this.createBackgroundView(this.mContentView);
                this.mBackgroundView.setBackground(this.mBackground);
            } else {
                this.mBackgroundView = this.mContentView;
            }
            popupDecorView = this.mDecorView = this.createDecorView(this.mBackgroundView);
            boolean bl = true;
            popupDecorView.setIsRootNamespace(true);
            this.mBackgroundView.setElevation(this.mElevation);
            layoutParams.setSurfaceInsets(this.mBackgroundView, true, true);
            if (this.mContentView.getRawLayoutDirection() != 2) {
                bl = false;
            }
            this.mPopupViewInitialLayoutDirectionInherited = bl;
            return;
        }
        throw new IllegalStateException("You must specify a valid content view by calling setContentView() before attempting to show the popup.");
    }

    private void setLayoutDirectionFromAnchor() {
        WeakReference<View> weakReference = this.mAnchor;
        if (weakReference != null && (weakReference = (View)weakReference.get()) != null && this.mPopupViewInitialLayoutDirectionInherited) {
            this.mDecorView.setLayoutDirection(((View)((Object)weakReference)).getLayoutDirection());
        }
    }

    private boolean tryFitHorizontal(WindowManager.LayoutParams layoutParams, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl) {
        n = layoutParams.x + (n5 - n4);
        if (n >= 0 && n2 <= n7 - n) {
            return true;
        }
        return this.positionInDisplayHorizontal(layoutParams, n2, n4, n5, n6, n7, bl);
    }

    private boolean tryFitVertical(WindowManager.LayoutParams layoutParams, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl) {
        int n8 = layoutParams.y + (n5 - n4);
        if (n8 >= 0 && n2 <= n7 - n8) {
            return true;
        }
        if (n2 <= n8 - n3 - n6) {
            if (this.mOverlapAnchor) {
                n += n3;
            }
            layoutParams.y = n4 - n2 + n;
            return true;
        }
        return this.positionInDisplayVertical(layoutParams, n2, n4, n5, n6, n7, bl);
    }

    private void update(View view, boolean bl, int n, int n2, int n3, int n4) {
        if (this.isShowing() && this.hasContentView()) {
            Object object = this.mAnchor;
            int n5 = this.mAnchoredGravity;
            int n6 = bl && (this.mAnchorXoff != n || this.mAnchorYoff != n2) ? 1 : 0;
            if (object != null && ((Reference)object).get() == view && (n6 == 0 || this.mIsDropdown)) {
                if (n6 != 0) {
                    this.mAnchorXoff = n;
                    this.mAnchorYoff = n2;
                }
            } else {
                this.attachToAnchor(view, n, n2, n5);
            }
            object = this.getDecorViewLayoutParams();
            n6 = ((WindowManager.LayoutParams)object).gravity;
            int n7 = ((WindowManager.LayoutParams)object).width;
            int n8 = ((WindowManager.LayoutParams)object).height;
            int n9 = ((WindowManager.LayoutParams)object).x;
            int n10 = ((WindowManager.LayoutParams)object).y;
            n = n3 < 0 ? this.mWidth : n3;
            n2 = n4 < 0 ? this.mHeight : n4;
            this.updateAboveAnchor(this.findDropDownPosition(view, (WindowManager.LayoutParams)object, this.mAnchorXoff, this.mAnchorYoff, n, n2, n5, this.mAllowScrollingAnchorParent));
            bl = n6 != ((WindowManager.LayoutParams)object).gravity || n9 != ((WindowManager.LayoutParams)object).x || n10 != ((WindowManager.LayoutParams)object).y || n7 != ((WindowManager.LayoutParams)object).width || n8 != ((WindowManager.LayoutParams)object).height;
            if (n >= 0) {
                n = ((WindowManager.LayoutParams)object).width;
            }
            if (n2 >= 0) {
                n2 = ((WindowManager.LayoutParams)object).height;
            }
            this.update(((WindowManager.LayoutParams)object).x, ((WindowManager.LayoutParams)object).y, n, n2, bl);
            return;
        }
    }

    protected void attachToAnchor(View view, int n, int n2, int n3) {
        this.detachFromAnchor();
        Object object = view.getViewTreeObserver();
        if (object != null) {
            ((ViewTreeObserver)object).addOnScrollChangedListener(this.mOnScrollChangedListener);
        }
        view.addOnAttachStateChangeListener(this.mOnAnchorDetachedListener);
        object = view.getRootView();
        ((View)object).addOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
        ((View)object).addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        this.mAnchor = new WeakReference<View>(view);
        this.mAnchorRoot = new WeakReference<Object>(object);
        this.mIsAnchorRootAttached = ((View)object).isAttachedToWindow();
        this.mParentRootView = this.mAnchorRoot;
        this.mAnchorXoff = n;
        this.mAnchorYoff = n2;
        this.mAnchoredGravity = n3;
    }

    @UnsupportedAppUsage
    protected final WindowManager.LayoutParams createPopupLayoutParams(IBinder object) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = this.computeGravity();
        layoutParams.flags = this.computeFlags(layoutParams.flags);
        layoutParams.type = this.mWindowLayoutType;
        layoutParams.token = object;
        layoutParams.softInputMode = this.mSoftInputMode;
        layoutParams.windowAnimations = this.computeAnimationResource();
        object = this.mBackground;
        layoutParams.format = object != null ? ((Drawable)object).getOpacity() : -3;
        int n = this.mHeightMode;
        if (n < 0) {
            this.mLastHeight = n;
            layoutParams.height = n;
        } else {
            this.mLastHeight = n = this.mHeight;
            layoutParams.height = n;
        }
        n = this.mWidthMode;
        if (n < 0) {
            this.mLastWidth = n;
            layoutParams.width = n;
        } else {
            this.mLastWidth = n = this.mWidth;
            layoutParams.width = n;
        }
        layoutParams.privateFlags = 98304;
        object = new StringBuilder();
        ((StringBuilder)object).append("PopupWindow:");
        ((StringBuilder)object).append(Integer.toHexString(this.hashCode()));
        layoutParams.setTitle(((StringBuilder)object).toString());
        return layoutParams;
    }

    protected void detachFromAnchor() {
        Object object = this.getAnchor();
        if (object != null) {
            ((View)object).getViewTreeObserver().removeOnScrollChangedListener(this.mOnScrollChangedListener);
            ((View)object).removeOnAttachStateChangeListener(this.mOnAnchorDetachedListener);
        }
        if ((object = (object = this.mAnchorRoot) != null ? (View)((Reference)object).get() : null) != null) {
            ((View)object).removeOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
            ((View)object).removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
        }
        this.mAnchor = null;
        this.mAnchorRoot = null;
        this.mIsAnchorRootAttached = false;
    }

    public void dismiss() {
        if (this.isShowing() && !this.isTransitioningToDismiss()) {
            final PopupDecorView popupDecorView = this.mDecorView;
            View view = this.mContentView;
            Object object = view.getParent();
            object = object instanceof ViewGroup ? (ViewGroup)object : null;
            popupDecorView.cancelTransitions();
            this.mIsShowing = false;
            this.mIsTransitioningToDismiss = true;
            Transition transition2 = this.mExitTransition;
            if (transition2 != null && popupDecorView.isLaidOut() && (this.mIsAnchorRootAttached || this.mAnchorRoot == null)) {
                Object object2 = (WindowManager.LayoutParams)popupDecorView.getLayoutParams();
                ((WindowManager.LayoutParams)object2).flags |= 16;
                ((WindowManager.LayoutParams)object2).flags |= 8;
                ((WindowManager.LayoutParams)object2).flags &= -131073;
                this.mWindowManager.updateViewLayout(popupDecorView, (ViewGroup.LayoutParams)object2);
                object2 = this.mAnchorRoot;
                object2 = object2 != null ? (View)((Reference)object2).get() : null;
                popupDecorView.startExitTransition(transition2, (View)object2, this.getTransitionEpicenter(), new TransitionListenerAdapter((ViewGroup)object, view){
                    final /* synthetic */ ViewGroup val$contentHolder;
                    final /* synthetic */ View val$contentView;
                    {
                        this.val$contentHolder = viewGroup;
                        this.val$contentView = view;
                    }

                    @Override
                    public void onTransitionEnd(Transition transition2) {
                        PopupWindow.this.dismissImmediate(popupDecorView, this.val$contentHolder, this.val$contentView);
                    }
                });
            } else {
                this.dismissImmediate(popupDecorView, (ViewGroup)object, view);
            }
            this.detachFromAnchor();
            object = this.mOnDismissListener;
            if (object != null) {
                object.onDismiss();
            }
            return;
        }
    }

    protected boolean findDropDownPosition(View object, WindowManager.LayoutParams layoutParams, int n, int n2, int n3, int n4, int n5, boolean bl) {
        int n6 = ((View)object).getHeight();
        int n7 = ((View)object).getWidth();
        if (this.mOverlapAnchor) {
            n2 -= n6;
        }
        int[] arrn = this.mTmpAppLocation;
        Object object2 = this.getAppRootView((View)object);
        ((View)object2).getLocationOnScreen(arrn);
        int[] arrn2 = this.mTmpScreenLocation;
        ((View)object).getLocationOnScreen(arrn2);
        int[] arrn3 = this.mTmpDrawingLocation;
        boolean bl2 = false;
        arrn3[0] = arrn2[0] - arrn[0];
        arrn3[1] = arrn2[1] - arrn[1];
        layoutParams.x = arrn3[0] + n;
        layoutParams.y = arrn3[1] + n6 + n2;
        Rect rect = new Rect();
        ((View)object2).getWindowVisibleDisplayFrame(rect);
        if (n3 == -1) {
            n3 = rect.right - rect.left;
        }
        if (n4 == -1) {
            n4 = rect.bottom - rect.top;
        }
        layoutParams.gravity = this.computeGravity();
        layoutParams.width = n3;
        layoutParams.height = n4;
        if ((n5 = Gravity.getAbsoluteGravity(n5, ((View)object).getLayoutDirection()) & 7) == 5) {
            layoutParams.x -= n3 - n7;
        }
        boolean bl3 = this.tryFitVertical(layoutParams, n2, n4, n6, arrn3[1], arrn2[1], rect.top, rect.bottom, false);
        boolean bl4 = this.tryFitHorizontal(layoutParams, n, n3, n7, arrn3[0], arrn2[0], rect.left, rect.right, false);
        if (bl3 && bl4) {
            object = layoutParams;
        } else {
            int n8 = ((View)object).getScrollX();
            int n9 = ((View)object).getScrollY();
            object2 = new Rect(n8, n9, n8 + n3 + n, n9 + n4 + n6 + n2);
            if (bl && ((View)object).requestRectangleOnScreen((Rect)object2, true)) {
                ((View)object).getLocationOnScreen(arrn2);
                arrn3[0] = arrn2[0] - arrn[0];
                arrn3[1] = arrn2[1] - arrn[1];
                n8 = arrn3[0];
                object = layoutParams;
                ((WindowManager.LayoutParams)object).x = n8 + n;
                ((WindowManager.LayoutParams)object).y = arrn3[1] + n6 + n2;
                if (n5 == 5) {
                    ((WindowManager.LayoutParams)object).x -= n3 - n7;
                }
            }
            int n10 = arrn3[1];
            n5 = arrn2[1];
            n9 = rect.top;
            n8 = rect.bottom;
            bl = this.mClipToScreen;
            object = layoutParams;
            this.tryFitVertical(layoutParams, n2, n4, n6, n10, n5, n9, n8, bl);
            this.tryFitHorizontal(layoutParams, n, n3, n7, arrn3[0], arrn2[0], rect.left, rect.right, this.mClipToScreen);
        }
        bl = bl2;
        if (((WindowManager.LayoutParams)object).y < arrn3[1]) {
            bl = true;
        }
        return bl;
    }

    protected final boolean getAllowScrollingAnchorParent() {
        return this.mAllowScrollingAnchorParent;
    }

    protected View getAnchor() {
        WeakReference<View> weakReference = this.mAnchor;
        weakReference = weakReference != null ? (View)weakReference.get() : null;
        return weakReference;
    }

    public int getAnimationStyle() {
        return this.mAnimationStyle;
    }

    public Drawable getBackground() {
        return this.mBackground;
    }

    public View getContentView() {
        return this.mContentView;
    }

    protected WindowManager.LayoutParams getDecorViewLayoutParams() {
        return (WindowManager.LayoutParams)this.mDecorView.getLayoutParams();
    }

    public float getElevation() {
        return this.mElevation;
    }

    public Transition getEnterTransition() {
        return this.mEnterTransition;
    }

    public Rect getEpicenterBounds() {
        Rect rect = this.mEpicenterBounds;
        rect = rect != null ? new Rect(rect) : null;
        return rect;
    }

    public Transition getExitTransition() {
        return this.mExitTransition;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getInputMethodMode() {
        return this.mInputMethodMode;
    }

    public int getMaxAvailableHeight(View view) {
        return this.getMaxAvailableHeight(view, 0);
    }

    public int getMaxAvailableHeight(View view, int n) {
        return this.getMaxAvailableHeight(view, n, false);
    }

    public int getMaxAvailableHeight(View object, int n, boolean bl) {
        int[] arrn;
        int[] arrn2 = new Rect();
        this.getAppRootView((View)object).getWindowVisibleDisplayFrame((Rect)arrn2);
        if (bl) {
            arrn = new Rect();
            ((View)object).getWindowDisplayFrame((Rect)arrn);
            arrn.top = arrn2.top;
            arrn.right = arrn2.right;
            arrn.left = arrn2.left;
        } else {
            arrn = arrn2;
        }
        arrn2 = this.mTmpDrawingLocation;
        ((View)object).getLocationOnScreen(arrn2);
        int n2 = arrn.bottom;
        n2 = this.mOverlapAnchor ? n2 - arrn2[1] - n : n2 - (arrn2[1] + ((View)object).getHeight()) - n;
        n2 = Math.max(n2, arrn2[1] - arrn.top + n);
        object = this.mBackground;
        n = n2;
        if (object != null) {
            ((Drawable)object).getPadding(this.mTempRect);
            n = n2 - (this.mTempRect.top + this.mTempRect.bottom);
        }
        return n;
    }

    protected final OnDismissListener getOnDismissListener() {
        return this.mOnDismissListener;
    }

    public boolean getOverlapAnchor() {
        return this.mOverlapAnchor;
    }

    public int getSoftInputMode() {
        return this.mSoftInputMode;
    }

    protected final Rect getTransitionEpicenter() {
        WeakReference<View> weakReference = this.mAnchor;
        weakReference = weakReference != null ? (View)weakReference.get() : null;
        int[] arrn = this.mDecorView;
        if (weakReference != null && arrn != null) {
            arrn = ((View)((Object)weakReference)).getLocationOnScreen();
            int[] arrn2 = this.mDecorView.getLocationOnScreen();
            weakReference = new Rect(0, 0, ((View)((Object)weakReference)).getWidth(), ((View)((Object)weakReference)).getHeight());
            ((Rect)((Object)weakReference)).offset(arrn[0] - arrn2[0], arrn[1] - arrn2[1]);
            if (this.mEpicenterBounds != null) {
                int n = ((Rect)weakReference).left;
                int n2 = ((Rect)weakReference).top;
                ((Rect)((Object)weakReference)).set(this.mEpicenterBounds);
                ((Rect)((Object)weakReference)).offset(n, n2);
            }
            return weakReference;
        }
        return null;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getWindowLayoutType() {
        return this.mWindowLayoutType;
    }

    protected boolean hasContentView() {
        boolean bl = this.mContentView != null;
        return bl;
    }

    protected boolean hasDecorView() {
        boolean bl = this.mDecorView != null;
        return bl;
    }

    public boolean isAboveAnchor() {
        return this.mAboveAnchor;
    }

    public boolean isAttachedInDecor() {
        return this.mAttachedInDecor;
    }

    @Deprecated
    public boolean isClipToScreenEnabled() {
        return this.mClipToScreen;
    }

    public boolean isClippedToScreen() {
        return this.mClipToScreen;
    }

    public boolean isClippingEnabled() {
        return this.mClippingEnabled;
    }

    public boolean isFocusable() {
        return this.mFocusable;
    }

    public boolean isLaidOutInScreen() {
        return this.mLayoutInScreen;
    }

    @Deprecated
    public boolean isLayoutInScreenEnabled() {
        return this.mLayoutInScreen;
    }

    protected final boolean isLayoutInsetDecor() {
        return this.mLayoutInsetDecor;
    }

    public boolean isOutsideTouchable() {
        return this.mOutsideTouchable;
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }

    public boolean isSplitTouchEnabled() {
        Context context;
        int n = this.mSplitTouchEnabled;
        boolean bl = false;
        boolean bl2 = false;
        if (n < 0 && (context = this.mContext) != null) {
            if (context.getApplicationInfo().targetSdkVersion >= 11) {
                bl2 = true;
            }
            return bl2;
        }
        bl2 = bl;
        if (this.mSplitTouchEnabled == 1) {
            bl2 = true;
        }
        return bl2;
    }

    public boolean isTouchModal() {
        return this.mNotTouchModal ^ true;
    }

    public boolean isTouchable() {
        return this.mTouchable;
    }

    protected final boolean isTransitioningToDismiss() {
        return this.mIsTransitioningToDismiss;
    }

    public /* synthetic */ void lambda$new$0$PopupWindow(View view, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        this.alignToAnchor();
    }

    @UnsupportedAppUsage
    void setAllowScrollingAnchorParent(boolean bl) {
        this.mAllowScrollingAnchorParent = bl;
    }

    public void setAnimationStyle(int n) {
        this.mAnimationStyle = n;
    }

    public void setAttachedInDecor(boolean bl) {
        this.mAttachedInDecor = bl;
        this.mAttachedInDecorSet = true;
    }

    public void setBackgroundDrawable(Drawable drawable2) {
        this.mBackground = drawable2;
        if ((drawable2 = this.mBackground) instanceof StateListDrawable) {
            int n;
            drawable2 = (StateListDrawable)drawable2;
            int n2 = ((StateListDrawable)drawable2).findStateDrawableIndex(ABOVE_ANCHOR_STATE_SET);
            int n3 = ((StateListDrawable)drawable2).getStateCount();
            int n4 = -1;
            int n5 = 0;
            do {
                n = n4;
                if (n5 >= n3) break;
                if (n5 != n2) {
                    n = n5;
                    break;
                }
                ++n5;
            } while (true);
            if (n2 != -1 && n != -1) {
                this.mAboveAnchorBackgroundDrawable = ((StateListDrawable)drawable2).getStateDrawable(n2);
                this.mBelowAnchorBackgroundDrawable = ((StateListDrawable)drawable2).getStateDrawable(n);
            } else {
                this.mBelowAnchorBackgroundDrawable = null;
                this.mAboveAnchorBackgroundDrawable = null;
            }
        }
    }

    @Deprecated
    public void setClipToScreenEnabled(boolean bl) {
        this.mClipToScreen = bl;
    }

    public void setClippingEnabled(boolean bl) {
        this.mClippingEnabled = bl;
    }

    public void setContentView(View object) {
        if (this.isShowing()) {
            return;
        }
        this.mContentView = object;
        if (this.mContext == null && (object = this.mContentView) != null) {
            this.mContext = ((View)object).getContext();
        }
        if (this.mWindowManager == null && this.mContentView != null) {
            this.mWindowManager = (WindowManager)this.mContext.getSystemService("window");
        }
        if ((object = this.mContext) != null && !this.mAttachedInDecorSet) {
            boolean bl = object.getApplicationInfo().targetSdkVersion >= 22;
            this.setAttachedInDecor(bl);
        }
    }

    protected final void setDropDown(boolean bl) {
        this.mIsDropdown = bl;
    }

    public void setElevation(float f) {
        this.mElevation = f;
    }

    public void setEnterTransition(Transition transition2) {
        this.mEnterTransition = transition2;
    }

    public void setEpicenterBounds(Rect rect) {
        rect = rect != null ? new Rect(rect) : null;
        this.mEpicenterBounds = rect;
    }

    public void setExitTransition(Transition transition2) {
        this.mExitTransition = transition2;
    }

    public void setFocusable(boolean bl) {
        this.mFocusable = bl;
    }

    public void setHeight(int n) {
        this.mHeight = n;
    }

    public void setIgnoreCheekPress() {
        this.mIgnoreCheekPress = true;
    }

    public void setInputMethodMode(int n) {
        this.mInputMethodMode = n;
    }

    public void setIsClippedToScreen(boolean bl) {
        this.mClipToScreen = bl;
    }

    public void setIsLaidOutInScreen(boolean bl) {
        this.mLayoutInScreen = bl;
    }

    @Deprecated
    public void setLayoutInScreenEnabled(boolean bl) {
        this.mLayoutInScreen = bl;
    }

    @UnsupportedAppUsage
    public void setLayoutInsetDecor(boolean bl) {
        this.mLayoutInsetDecor = bl;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public void setOutsideTouchable(boolean bl) {
        this.mOutsideTouchable = bl;
    }

    public void setOverlapAnchor(boolean bl) {
        this.mOverlapAnchor = bl;
    }

    protected final void setShowing(boolean bl) {
        this.mIsShowing = bl;
    }

    public void setSoftInputMode(int n) {
        this.mSoftInputMode = n;
    }

    public void setSplitTouchEnabled(boolean bl) {
        this.mSplitTouchEnabled = bl ? 1 : 0;
    }

    public void setTouchInterceptor(View.OnTouchListener onTouchListener) {
        this.mTouchInterceptor = onTouchListener;
    }

    public void setTouchModal(boolean bl) {
        this.mNotTouchModal = bl ^ true;
    }

    public void setTouchable(boolean bl) {
        this.mTouchable = bl;
    }

    protected final void setTransitioningToDismiss(boolean bl) {
        this.mIsTransitioningToDismiss = bl;
    }

    public void setWidth(int n) {
        this.mWidth = n;
    }

    @Deprecated
    public void setWindowLayoutMode(int n, int n2) {
        this.mWidthMode = n;
        this.mHeightMode = n2;
    }

    public void setWindowLayoutType(int n) {
        this.mWindowLayoutType = n;
    }

    public void showAsDropDown(View view) {
        this.showAsDropDown(view, 0, 0);
    }

    public void showAsDropDown(View view, int n, int n2) {
        this.showAsDropDown(view, n, n2, 8388659);
    }

    public void showAsDropDown(View view, int n, int n2, int n3) {
        if (!this.isShowing() && this.hasContentView()) {
            TransitionManager.endTransitions(this.mDecorView);
            this.attachToAnchor(view, n, n2, n3);
            this.mIsShowing = true;
            this.mIsDropdown = true;
            WindowManager.LayoutParams layoutParams = this.createPopupLayoutParams(view.getApplicationWindowToken());
            this.preparePopup(layoutParams);
            this.updateAboveAnchor(this.findDropDownPosition(view, layoutParams, n, n2, layoutParams.width, layoutParams.height, n3, this.mAllowScrollingAnchorParent));
            layoutParams.accessibilityIdOfAnchor = view.getAccessibilityViewId();
            this.invokePopup(layoutParams);
            return;
        }
    }

    @UnsupportedAppUsage
    public void showAtLocation(IBinder object, int n, int n2, int n3) {
        if (!this.isShowing() && this.mContentView != null) {
            TransitionManager.endTransitions(this.mDecorView);
            this.detachFromAnchor();
            this.mIsShowing = true;
            this.mIsDropdown = false;
            this.mGravity = n;
            object = this.createPopupLayoutParams((IBinder)object);
            this.preparePopup((WindowManager.LayoutParams)object);
            ((WindowManager.LayoutParams)object).x = n2;
            ((WindowManager.LayoutParams)object).y = n3;
            this.invokePopup((WindowManager.LayoutParams)object);
            return;
        }
    }

    public void showAtLocation(View view, int n, int n2, int n3) {
        this.mParentRootView = new WeakReference<View>(view.getRootView());
        this.showAtLocation(view.getWindowToken(), n, n2, n3);
    }

    public void update() {
        if (this.isShowing() && this.hasContentView()) {
            WindowManager.LayoutParams layoutParams = this.getDecorViewLayoutParams();
            boolean bl = false;
            int n = this.computeAnimationResource();
            if (n != layoutParams.windowAnimations) {
                layoutParams.windowAnimations = n;
                bl = true;
            }
            if ((n = this.computeFlags(layoutParams.flags)) != layoutParams.flags) {
                layoutParams.flags = n;
                bl = true;
            }
            if ((n = this.computeGravity()) != layoutParams.gravity) {
                layoutParams.gravity = n;
                bl = true;
            }
            if (bl) {
                WeakReference<View> weakReference = this.mAnchor;
                weakReference = weakReference != null ? (View)weakReference.get() : null;
                this.update((View)((Object)weakReference), layoutParams);
            }
            return;
        }
    }

    public void update(int n, int n2) {
        WindowManager.LayoutParams layoutParams = this.getDecorViewLayoutParams();
        this.update(layoutParams.x, layoutParams.y, n, n2, false);
    }

    public void update(int n, int n2, int n3, int n4) {
        this.update(n, n2, n3, n4, false);
    }

    public void update(int n, int n2, int n3, int n4, boolean bl) {
        if (n3 >= 0) {
            this.mLastWidth = n3;
            this.setWidth(n3);
        }
        if (n4 >= 0) {
            this.mLastHeight = n4;
            this.setHeight(n4);
        }
        if (this.isShowing() && this.hasContentView()) {
            WindowManager.LayoutParams layoutParams = this.getDecorViewLayoutParams();
            boolean bl2 = bl;
            int n5 = this.mWidthMode;
            if (n5 >= 0) {
                n5 = this.mLastWidth;
            }
            bl = bl2;
            if (n3 != -1) {
                bl = bl2;
                if (layoutParams.width != n5) {
                    this.mLastWidth = n5;
                    layoutParams.width = n5;
                    bl = true;
                }
            }
            if ((n3 = this.mHeightMode) >= 0) {
                n3 = this.mLastHeight;
            }
            bl2 = bl;
            if (n4 != -1) {
                bl2 = bl;
                if (layoutParams.height != n3) {
                    this.mLastHeight = n3;
                    layoutParams.height = n3;
                    bl2 = true;
                }
            }
            if (layoutParams.x != n) {
                layoutParams.x = n;
                bl2 = true;
            }
            bl = bl2;
            if (layoutParams.y != n2) {
                layoutParams.y = n2;
                bl = true;
            }
            if ((n = this.computeAnimationResource()) != layoutParams.windowAnimations) {
                layoutParams.windowAnimations = n;
                bl = true;
            }
            if ((n = this.computeFlags(layoutParams.flags)) != layoutParams.flags) {
                layoutParams.flags = n;
                bl = true;
            }
            if ((n = this.computeGravity()) != layoutParams.gravity) {
                layoutParams.gravity = n;
                bl = true;
            }
            View view = null;
            n2 = -1;
            WeakReference<View> weakReference = this.mAnchor;
            View view2 = view;
            n = n2;
            if (weakReference != null) {
                view2 = view;
                n = n2;
                if (weakReference.get() != null) {
                    view2 = (View)this.mAnchor.get();
                    n = view2.getAccessibilityViewId();
                }
            }
            if ((long)n != layoutParams.accessibilityIdOfAnchor) {
                layoutParams.accessibilityIdOfAnchor = n;
                bl = true;
            }
            if (bl) {
                this.update(view2, layoutParams);
            }
            return;
        }
    }

    public void update(View view, int n, int n2) {
        this.update(view, false, 0, 0, n, n2);
    }

    public void update(View view, int n, int n2, int n3, int n4) {
        this.update(view, true, n, n2, n3, n4);
    }

    protected void update(View view, WindowManager.LayoutParams layoutParams) {
        this.setLayoutDirectionFromAnchor();
        this.mWindowManager.updateViewLayout(this.mDecorView, layoutParams);
    }

    @UnsupportedAppUsage
    protected final void updateAboveAnchor(boolean bl) {
        if (bl != this.mAboveAnchor) {
            View view;
            this.mAboveAnchor = bl;
            if (this.mBackground != null && (view = this.mBackgroundView) != null) {
                Drawable drawable2 = this.mAboveAnchorBackgroundDrawable;
                if (drawable2 != null) {
                    if (this.mAboveAnchor) {
                        view.setBackground(drawable2);
                    } else {
                        view.setBackground(this.mBelowAnchorBackgroundDrawable);
                    }
                } else {
                    view.refreshDrawableState();
                }
            }
        }
    }

    public static interface OnDismissListener {
        public void onDismiss();
    }

    private class PopupBackgroundView
    extends FrameLayout {
        public PopupBackgroundView(Context context) {
            super(context);
        }

        @Override
        protected int[] onCreateDrawableState(int n) {
            if (PopupWindow.this.mAboveAnchor) {
                int[] arrn = super.onCreateDrawableState(n + 1);
                View.mergeDrawableStates(arrn, ABOVE_ANCHOR_STATE_SET);
                return arrn;
            }
            return super.onCreateDrawableState(n);
        }
    }

    private class PopupDecorView
    extends FrameLayout {
        private Runnable mCleanupAfterExit;
        private final View.OnAttachStateChangeListener mOnAnchorRootDetachedListener;

        public PopupDecorView(Context context) {
            super(context);
            this.mOnAnchorRootDetachedListener = new View.OnAttachStateChangeListener(){

                @Override
                public void onViewAttachedToWindow(View view) {
                }

                @Override
                public void onViewDetachedFromWindow(View view) {
                    view.removeOnAttachStateChangeListener(this);
                    if (PopupDecorView.this.isAttachedToWindow()) {
                        TransitionManager.endTransitions(PopupDecorView.this);
                    }
                }
            };
        }

        private void startEnterTransition(Transition transition2) {
            int n;
            int n2 = this.getChildCount();
            for (n = 0; n < n2; ++n) {
                View view = this.getChildAt(n);
                transition2.addTarget(view);
                view.setTransitionVisibility(4);
            }
            TransitionManager.beginDelayedTransition(this, transition2);
            for (n = 0; n < n2; ++n) {
                this.getChildAt(n).setTransitionVisibility(0);
            }
        }

        public void cancelTransitions() {
            TransitionManager.endTransitions(this);
            Runnable runnable = this.mCleanupAfterExit;
            if (runnable != null) {
                runnable.run();
            }
        }

        @Override
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == 4) {
                KeyEvent.DispatcherState dispatcherState;
                if (this.getKeyDispatcherState() == null) {
                    return super.dispatchKeyEvent(keyEvent);
                }
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    KeyEvent.DispatcherState dispatcherState2 = this.getKeyDispatcherState();
                    if (dispatcherState2 != null) {
                        dispatcherState2.startTracking(keyEvent, this);
                    }
                    return true;
                }
                if (keyEvent.getAction() == 1 && (dispatcherState = this.getKeyDispatcherState()) != null && dispatcherState.isTracking(keyEvent) && !keyEvent.isCanceled()) {
                    PopupWindow.this.dismiss();
                    return true;
                }
                return super.dispatchKeyEvent(keyEvent);
            }
            return super.dispatchKeyEvent(keyEvent);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            if (PopupWindow.this.mTouchInterceptor != null && PopupWindow.this.mTouchInterceptor.onTouch(this, motionEvent)) {
                return true;
            }
            return super.dispatchTouchEvent(motionEvent);
        }

        public /* synthetic */ void lambda$startExitTransition$0$PopupWindow$PopupDecorView(Transition.TransitionListener transitionListener, Transition transition2, View view) {
            transitionListener.onTransitionEnd(transition2);
            if (view != null) {
                view.removeOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
            }
            this.mCleanupAfterExit = null;
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            int n = (int)motionEvent.getX();
            int n2 = (int)motionEvent.getY();
            if (motionEvent.getAction() == 0 && (n < 0 || n >= this.getWidth() || n2 < 0 || n2 >= this.getHeight())) {
                PopupWindow.this.dismiss();
                return true;
            }
            if (motionEvent.getAction() == 4) {
                PopupWindow.this.dismiss();
                return true;
            }
            return super.onTouchEvent(motionEvent);
        }

        public void requestEnterTransition(Transition transition2) {
            ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
            if (viewTreeObserver != null && transition2 != null) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(transition2.clone()){
                    final /* synthetic */ Transition val$enterTransition;
                    {
                        this.val$enterTransition = transition2;
                    }

                    @Override
                    public void onGlobalLayout() {
                        Object object = PopupDecorView.this.getViewTreeObserver();
                        if (object != null) {
                            ((ViewTreeObserver)object).removeOnGlobalLayoutListener(this);
                        }
                        object = PopupWindow.this.getTransitionEpicenter();
                        this.val$enterTransition.setEpicenterCallback(new Transition.EpicenterCallback((Rect)object){
                            final /* synthetic */ Rect val$epicenter;
                            {
                                this.val$epicenter = rect;
                            }

                            @Override
                            public Rect onGetEpicenter(Transition transition2) {
                                return this.val$epicenter;
                            }
                        });
                        PopupDecorView.this.startEnterTransition(this.val$enterTransition);
                    }

                });
            }
        }

        @Override
        public void requestKeyboardShortcuts(List<KeyboardShortcutGroup> list, int n) {
            View view;
            if (PopupWindow.this.mParentRootView != null && (view = (View)PopupWindow.this.mParentRootView.get()) != null) {
                view.requestKeyboardShortcuts(list, n);
            }
        }

        public void startExitTransition(Transition transition2, View view, final Rect rect, Transition.TransitionListener transitionListener) {
            int n;
            if (transition2 == null) {
                return;
            }
            if (view != null) {
                view.addOnAttachStateChangeListener(this.mOnAnchorRootDetachedListener);
            }
            this.mCleanupAfterExit = new _$$Lambda$PopupWindow$PopupDecorView$T99WKEnQefOCXbbKvW95WY38p_I(this, transitionListener, transition2, view);
            transition2 = transition2.clone();
            transition2.addListener(new TransitionListenerAdapter(){

                @Override
                public void onTransitionEnd(Transition transition2) {
                    transition2.removeListener(this);
                    if (PopupDecorView.this.mCleanupAfterExit != null) {
                        PopupDecorView.this.mCleanupAfterExit.run();
                    }
                }
            });
            transition2.setEpicenterCallback(new Transition.EpicenterCallback(){

                @Override
                public Rect onGetEpicenter(Transition transition2) {
                    return rect;
                }
            });
            int n2 = this.getChildCount();
            for (n = 0; n < n2; ++n) {
                transition2.addTarget(this.getChildAt(n));
            }
            TransitionManager.beginDelayedTransition(this, transition2);
            for (n = 0; n < n2; ++n) {
                this.getChildAt(n).setVisibility(4);
            }
        }

    }

}

