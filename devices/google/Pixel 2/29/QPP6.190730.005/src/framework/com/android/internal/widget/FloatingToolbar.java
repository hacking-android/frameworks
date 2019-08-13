/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.widget.-$
 *  com.android.internal.widget.-$$Lambda
 *  com.android.internal.widget.-$$Lambda$FloatingToolbar
 *  com.android.internal.widget.-$$Lambda$FloatingToolbar$7-enOzxeypZYfdFYr1HzBLfj47k
 *  com.android.internal.widget.-$$Lambda$FloatingToolbar$LutnsyBKrZiroTBekgIjhIyrl40
 */
package com.android.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Property;
import android.util.Size;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.-$;
import com.android.internal.widget._$$Lambda$FloatingToolbar$7_enOzxeypZYfdFYr1HzBLfj47k;
import com.android.internal.widget._$$Lambda$FloatingToolbar$FloatingToolbarPopup$13$7WTSUuAWkzil48e0QxuKTn0YOXI;
import com.android.internal.widget._$$Lambda$FloatingToolbar$FloatingToolbarPopup$77YZy6kisO5OnjlgtKp0Zi1V8EY;
import com.android.internal.widget._$$Lambda$FloatingToolbar$FloatingToolbarPopup$E8FwnPCl7gZpcTlX_UaRPIBRnT0;
import com.android.internal.widget._$$Lambda$FloatingToolbar$FloatingToolbarPopup$_uEfRwR__1oHxMvRVdmbNRdukDM;
import com.android.internal.widget._$$Lambda$FloatingToolbar$LutnsyBKrZiroTBekgIjhIyrl40;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class FloatingToolbar {
    public static final String FLOATING_TOOLBAR_TAG = "floating_toolbar";
    private static final MenuItem.OnMenuItemClickListener NO_OP_MENUITEM_CLICK_LISTENER = _$$Lambda$FloatingToolbar$7_enOzxeypZYfdFYr1HzBLfj47k.INSTANCE;
    private final Rect mContentRect = new Rect();
    private final Context mContext;
    private Menu mMenu;
    private MenuItem.OnMenuItemClickListener mMenuItemClickListener = NO_OP_MENUITEM_CLICK_LISTENER;
    private final Comparator<MenuItem> mMenuItemComparator = _$$Lambda$FloatingToolbar$LutnsyBKrZiroTBekgIjhIyrl40.INSTANCE;
    private final View.OnLayoutChangeListener mOrientationChangeHandler = new View.OnLayoutChangeListener(){
        private final Rect mNewRect = new Rect();
        private final Rect mOldRect = new Rect();

        @Override
        public void onLayoutChange(View view, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
            this.mNewRect.set(n, n2, n3, n4);
            this.mOldRect.set(n5, n6, n7, n8);
            if (FloatingToolbar.this.mPopup.isShowing() && !this.mNewRect.equals(this.mOldRect)) {
                FloatingToolbar.this.mWidthChanged = true;
                FloatingToolbar.this.updateLayout();
            }
        }
    };
    private final FloatingToolbarPopup mPopup;
    private final Rect mPreviousContentRect = new Rect();
    private List<MenuItem> mShowingMenuItems = new ArrayList<MenuItem>();
    private int mSuggestedWidth;
    private boolean mWidthChanged = true;
    private final Window mWindow;

    public FloatingToolbar(Window window) {
        this.mContext = FloatingToolbar.applyDefaultTheme(window.getContext());
        this.mWindow = Preconditions.checkNotNull(window);
        this.mPopup = new FloatingToolbarPopup(this.mContext, window.getDecorView());
    }

    private static Context applyDefaultTheme(Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{16844176});
        int n = typedArray.getBoolean(0, true) ? 16974123 : 16974120;
        typedArray.recycle();
        return new ContextThemeWrapper(context, n);
    }

    private static ViewGroup createContentContainer(Context object) {
        object = (ViewGroup)LayoutInflater.from((Context)object).inflate(17367150, null);
        ((View)object).setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        ((View)object).setTag(FLOATING_TOOLBAR_TAG);
        ((View)object).setClipToOutline(true);
        return object;
    }

    private static AnimatorSet createEnterAnimation(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f).setDuration(150L));
        return animatorSet;
    }

    private static AnimatorSet createExitAnimation(View view, int n, Animator.AnimatorListener animatorListener) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(view, View.ALPHA, 1.0f, 0.0f).setDuration(100L));
        animatorSet.setStartDelay(n);
        animatorSet.addListener(animatorListener);
        return animatorSet;
    }

    private static View createMenuItemButton(Context object, MenuItem menuItem, int n, boolean bl) {
        object = LayoutInflater.from((Context)object).inflate(17367151, null);
        if (menuItem != null) {
            FloatingToolbar.updateMenuItemButton((View)object, menuItem, n, bl);
        }
        return object;
    }

    private static PopupWindow createPopupWindow(ViewGroup viewGroup) {
        LinearLayout linearLayout = new LinearLayout(viewGroup.getContext());
        PopupWindow popupWindow = new PopupWindow(linearLayout);
        popupWindow.setClippingEnabled(false);
        popupWindow.setWindowLayoutType(1005);
        popupWindow.setAnimationStyle(0);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        viewGroup.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        linearLayout.addView(viewGroup);
        return popupWindow;
    }

    private void doShow() {
        List<MenuItem> list = this.getVisibleAndEnabledMenuItems(this.mMenu);
        list.sort(this.mMenuItemComparator);
        if (!this.isCurrentlyShowing(list) || this.mWidthChanged) {
            this.mPopup.dismiss();
            this.mPopup.layoutMenuItems(list, this.mMenuItemClickListener, this.mSuggestedWidth);
            this.mShowingMenuItems = list;
        }
        if (!this.mPopup.isShowing()) {
            this.mPopup.show(this.mContentRect);
        } else if (!this.mPreviousContentRect.equals(this.mContentRect)) {
            this.mPopup.updateCoordinates(this.mContentRect);
        }
        this.mWidthChanged = false;
        this.mPreviousContentRect.set(this.mContentRect);
    }

    private List<MenuItem> getVisibleAndEnabledMenuItems(Menu menu2) {
        ArrayList<MenuItem> arrayList = new ArrayList<MenuItem>();
        for (int i = 0; menu2 != null && i < menu2.size(); ++i) {
            MenuItem menuItem = menu2.getItem(i);
            if (!menuItem.isVisible() || !menuItem.isEnabled()) continue;
            SubMenu subMenu = menuItem.getSubMenu();
            if (subMenu != null) {
                arrayList.addAll(this.getVisibleAndEnabledMenuItems(subMenu));
                continue;
            }
            arrayList.add(menuItem);
        }
        return arrayList;
    }

    private boolean isCurrentlyShowing(List<MenuItem> list) {
        if (this.mShowingMenuItems != null && list.size() == this.mShowingMenuItems.size()) {
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                MenuItem menuItem = list.get(i);
                MenuItem menuItem2 = this.mShowingMenuItems.get(i);
                if (menuItem.getItemId() == menuItem2.getItemId() && TextUtils.equals(menuItem.getTitle(), menuItem2.getTitle()) && Objects.equals(menuItem.getIcon(), menuItem2.getIcon()) && menuItem.getGroupId() == menuItem2.getGroupId()) {
                    continue;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    static /* synthetic */ int lambda$new$1(MenuItem menuItem, MenuItem menuItem2) {
        int n = menuItem.getItemId();
        int n2 = 0;
        int n3 = 0;
        if (n == 16908353) {
            if (menuItem2.getItemId() != 16908353) {
                n3 = -1;
            }
            return n3;
        }
        if (menuItem2.getItemId() == 16908353) {
            return 1;
        }
        if (menuItem.requiresActionButton()) {
            n3 = menuItem2.requiresActionButton() ? n2 : -1;
            return n3;
        }
        if (menuItem2.requiresActionButton()) {
            return 1;
        }
        if (menuItem.requiresOverflow()) {
            return menuItem2.requiresOverflow() ^ true;
        }
        if (menuItem2.requiresOverflow()) {
            return -1;
        }
        return menuItem.getOrder() - menuItem2.getOrder();
    }

    static /* synthetic */ boolean lambda$static$0(MenuItem menuItem) {
        return false;
    }

    private void registerOrientationHandler() {
        this.unregisterOrientationHandler();
        this.mWindow.getDecorView().addOnLayoutChangeListener(this.mOrientationChangeHandler);
    }

    private void unregisterOrientationHandler() {
        this.mWindow.getDecorView().removeOnLayoutChangeListener(this.mOrientationChangeHandler);
    }

    private static void updateMenuItemButton(View view, MenuItem menuItem, int n, boolean bl) {
        TextView textView = (TextView)view.findViewById(16908954);
        textView.setEllipsize(null);
        if (TextUtils.isEmpty(menuItem.getTitle())) {
            textView.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView.setText(menuItem.getTitle());
        }
        Object object = (ImageView)view.findViewById(16908952);
        if (menuItem.getIcon() != null && bl) {
            ((ImageView)object).setVisibility(0);
            ((ImageView)object).setImageDrawable(menuItem.getIcon());
            textView.setPaddingRelative(n, 0, 0, 0);
        } else {
            ((ImageView)object).setVisibility(8);
            textView.setPaddingRelative(0, 0, 0, 0);
        }
        object = menuItem.getContentDescription();
        if (TextUtils.isEmpty((CharSequence)object)) {
            view.setContentDescription(menuItem.getTitle());
        } else {
            view.setContentDescription((CharSequence)object);
        }
    }

    public void dismiss() {
        this.unregisterOrientationHandler();
        this.mPopup.dismiss();
    }

    public void hide() {
        this.mPopup.hide();
    }

    public boolean isHidden() {
        return this.mPopup.isHidden();
    }

    public boolean isShowing() {
        return this.mPopup.isShowing();
    }

    public FloatingToolbar setContentRect(Rect rect) {
        this.mContentRect.set(Preconditions.checkNotNull(rect));
        return this;
    }

    public FloatingToolbar setMenu(Menu menu2) {
        this.mMenu = Preconditions.checkNotNull(menu2);
        return this;
    }

    public FloatingToolbar setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.mMenuItemClickListener = onMenuItemClickListener != null ? onMenuItemClickListener : NO_OP_MENUITEM_CLICK_LISTENER;
        return this;
    }

    public void setOutsideTouchable(boolean bl, PopupWindow.OnDismissListener onDismissListener) {
        if (this.mPopup.setOutsideTouchable(bl, onDismissListener) && this.isShowing()) {
            this.dismiss();
            this.doShow();
        }
    }

    public FloatingToolbar setSuggestedWidth(int n) {
        boolean bl = (double)Math.abs(n - this.mSuggestedWidth) > (double)this.mSuggestedWidth * 0.2;
        this.mWidthChanged = bl;
        this.mSuggestedWidth = n;
        return this;
    }

    public FloatingToolbar show() {
        this.registerOrientationHandler();
        this.doShow();
        return this;
    }

    public FloatingToolbar updateLayout() {
        if (this.mPopup.isShowing()) {
            this.doShow();
        }
        return this;
    }

    private static final class FloatingToolbarPopup {
        private static final int MAX_OVERFLOW_SIZE = 4;
        private static final int MIN_OVERFLOW_SIZE = 2;
        private final Drawable mArrow;
        private final AnimationSet mCloseOverflowAnimation;
        private final ViewGroup mContentContainer;
        private final Context mContext;
        private final Point mCoordsOnWindow = new Point();
        private final AnimatorSet mDismissAnimation;
        private boolean mDismissed = true;
        private final Interpolator mFastOutLinearInInterpolator;
        private final Interpolator mFastOutSlowInInterpolator;
        private boolean mHidden;
        private final AnimatorSet mHideAnimation;
        private final int mIconTextSpacing;
        private final ViewTreeObserver.OnComputeInternalInsetsListener mInsetsComputer = new _$$Lambda$FloatingToolbar$FloatingToolbarPopup$77YZy6kisO5OnjlgtKp0Zi1V8EY(this);
        private boolean mIsOverflowOpen;
        private final int mLineHeight;
        private final Interpolator mLinearOutSlowInInterpolator;
        private final Interpolator mLogAccelerateInterpolator;
        private final ViewGroup mMainPanel;
        private Size mMainPanelSize;
        private final int mMarginHorizontal;
        private final int mMarginVertical;
        private final View.OnClickListener mMenuItemButtonOnClickListener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (view.getTag() instanceof MenuItem && FloatingToolbarPopup.this.mOnMenuItemClickListener != null) {
                    FloatingToolbarPopup.this.mOnMenuItemClickListener.onMenuItemClick((MenuItem)view.getTag());
                }
            }
        };
        private MenuItem.OnMenuItemClickListener mOnMenuItemClickListener;
        private final AnimationSet mOpenOverflowAnimation;
        private boolean mOpenOverflowUpwards;
        private final Drawable mOverflow;
        private final Animation.AnimationListener mOverflowAnimationListener;
        private final ImageButton mOverflowButton;
        private final Size mOverflowButtonSize;
        private final OverflowPanel mOverflowPanel;
        private Size mOverflowPanelSize;
        private final OverflowPanelViewHelper mOverflowPanelViewHelper;
        private final View mParent;
        private final PopupWindow mPopupWindow;
        private final Runnable mPreparePopupContentRTLHelper = new Runnable(){

            @Override
            public void run() {
                FloatingToolbarPopup.this.setPanelsStatesAtRestingPosition();
                FloatingToolbarPopup.this.setContentAreaAsTouchableSurface();
                FloatingToolbarPopup.this.mContentContainer.setAlpha(1.0f);
            }
        };
        private final AnimatorSet mShowAnimation;
        private final int[] mTmpCoords = new int[2];
        private final AnimatedVectorDrawable mToArrow;
        private final AnimatedVectorDrawable mToOverflow;
        private final Region mTouchableRegion = new Region();
        private int mTransitionDurationScale;
        private final Rect mViewPortOnScreen = new Rect();

        public FloatingToolbarPopup(Context context, View view) {
            this.mParent = Preconditions.checkNotNull(view);
            this.mContext = Preconditions.checkNotNull(context);
            this.mContentContainer = FloatingToolbar.createContentContainer(context);
            this.mPopupWindow = FloatingToolbar.createPopupWindow(this.mContentContainer);
            this.mMarginHorizontal = view.getResources().getDimensionPixelSize(17105154);
            this.mMarginVertical = view.getResources().getDimensionPixelSize(17105167);
            this.mLineHeight = context.getResources().getDimensionPixelSize(17105153);
            this.mIconTextSpacing = context.getResources().getDimensionPixelSize(17105155);
            this.mLogAccelerateInterpolator = new LogAccelerateInterpolator();
            this.mFastOutSlowInInterpolator = AnimationUtils.loadInterpolator(this.mContext, 17563661);
            this.mLinearOutSlowInInterpolator = AnimationUtils.loadInterpolator(this.mContext, 17563662);
            this.mFastOutLinearInInterpolator = AnimationUtils.loadInterpolator(this.mContext, 17563663);
            this.mArrow = this.mContext.getResources().getDrawable(17302256, this.mContext.getTheme());
            this.mArrow.setAutoMirrored(true);
            this.mOverflow = this.mContext.getResources().getDrawable(17302254, this.mContext.getTheme());
            this.mOverflow.setAutoMirrored(true);
            this.mToArrow = (AnimatedVectorDrawable)this.mContext.getResources().getDrawable(17302255, this.mContext.getTheme());
            this.mToArrow.setAutoMirrored(true);
            this.mToOverflow = (AnimatedVectorDrawable)this.mContext.getResources().getDrawable(17302257, this.mContext.getTheme());
            this.mToOverflow.setAutoMirrored(true);
            this.mOverflowButton = this.createOverflowButton();
            this.mOverflowButtonSize = FloatingToolbarPopup.measure(this.mOverflowButton);
            this.mMainPanel = this.createMainPanel();
            this.mOverflowPanelViewHelper = new OverflowPanelViewHelper(this.mContext, this.mIconTextSpacing);
            this.mOverflowPanel = this.createOverflowPanel();
            this.mOverflowAnimationListener = this.createOverflowAnimationListener();
            this.mOpenOverflowAnimation = new AnimationSet(true);
            this.mOpenOverflowAnimation.setAnimationListener(this.mOverflowAnimationListener);
            this.mCloseOverflowAnimation = new AnimationSet(true);
            this.mCloseOverflowAnimation.setAnimationListener(this.mOverflowAnimationListener);
            this.mShowAnimation = FloatingToolbar.createEnterAnimation(this.mContentContainer);
            this.mDismissAnimation = FloatingToolbar.createExitAnimation(this.mContentContainer, 150, new AnimatorListenerAdapter(){

                @Override
                public void onAnimationEnd(Animator animator2) {
                    FloatingToolbarPopup.this.mPopupWindow.dismiss();
                    FloatingToolbarPopup.this.mContentContainer.removeAllViews();
                }
            });
            this.mHideAnimation = FloatingToolbar.createExitAnimation(this.mContentContainer, 0, new AnimatorListenerAdapter(){

                @Override
                public void onAnimationEnd(Animator animator2) {
                    FloatingToolbarPopup.this.mPopupWindow.dismiss();
                }
            });
        }

        private int calculateOverflowHeight(int n) {
            int n2 = Math.min(4, Math.min(Math.max(2, n), this.mOverflowPanel.getCount()));
            n = 0;
            if (n2 < this.mOverflowPanel.getCount()) {
                n = (int)((float)this.mLineHeight * 0.5f);
            }
            return this.mLineHeight * n2 + this.mOverflowButtonSize.getHeight() + n;
        }

        private void cancelDismissAndHideAnimations() {
            this.mDismissAnimation.cancel();
            this.mHideAnimation.cancel();
        }

        private void cancelOverflowAnimations() {
            this.mContentContainer.clearAnimation();
            this.mMainPanel.animate().cancel();
            this.mOverflowPanel.animate().cancel();
            this.mToArrow.stop();
            this.mToOverflow.stop();
        }

        private void clearPanels() {
            this.mOverflowPanelSize = null;
            this.mMainPanelSize = null;
            this.mIsOverflowOpen = false;
            this.mMainPanel.removeAllViews();
            ArrayAdapter arrayAdapter = (ArrayAdapter)this.mOverflowPanel.getAdapter();
            arrayAdapter.clear();
            this.mOverflowPanel.setAdapter(arrayAdapter);
            this.mContentContainer.removeAllViews();
        }

        private void closeOverflow() {
            final int n = this.mMainPanelSize.getWidth();
            final int n2 = this.mContentContainer.getWidth();
            final float f = this.mContentContainer.getX();
            Animation animation = new Animation(f + (float)this.mContentContainer.getWidth()){
                final /* synthetic */ float val$right;
                {
                    this.val$right = f2;
                }

                @Override
                protected void applyTransformation(float f2, Transformation transformation) {
                    int n3 = (int)((float)(n - n2) * f2);
                    FloatingToolbarPopup.setWidth(FloatingToolbarPopup.this.mContentContainer, n2 + n3);
                    if (FloatingToolbarPopup.this.isInRTLMode()) {
                        FloatingToolbarPopup.this.mContentContainer.setX(f);
                        FloatingToolbarPopup.this.mMainPanel.setX(0.0f);
                        FloatingToolbarPopup.this.mOverflowPanel.setX(0.0f);
                    } else {
                        FloatingToolbarPopup.this.mContentContainer.setX(this.val$right - (float)FloatingToolbarPopup.this.mContentContainer.getWidth());
                        FloatingToolbarPopup.this.mMainPanel.setX(FloatingToolbarPopup.this.mContentContainer.getWidth() - n);
                        FloatingToolbarPopup.this.mOverflowPanel.setX(FloatingToolbarPopup.this.mContentContainer.getWidth() - n2);
                    }
                }
            };
            Animation animation2 = new Animation(this.mMainPanelSize.getHeight(), this.mContentContainer.getHeight(), this.mContentContainer.getY() + (float)this.mContentContainer.getHeight()){
                final /* synthetic */ float val$bottom;
                final /* synthetic */ int val$startHeight;
                final /* synthetic */ int val$targetHeight;
                {
                    this.val$targetHeight = n;
                    this.val$startHeight = n2;
                    this.val$bottom = f;
                }

                @Override
                protected void applyTransformation(float f, Transformation transformation) {
                    int n = (int)((float)(this.val$targetHeight - this.val$startHeight) * f);
                    FloatingToolbarPopup.setHeight(FloatingToolbarPopup.this.mContentContainer, this.val$startHeight + n);
                    if (FloatingToolbarPopup.this.mOpenOverflowUpwards) {
                        FloatingToolbarPopup.this.mContentContainer.setY(this.val$bottom - (float)FloatingToolbarPopup.this.mContentContainer.getHeight());
                        FloatingToolbarPopup.this.positionContentYCoordinatesIfOpeningOverflowUpwards();
                    }
                }
            };
            final float f2 = this.mOverflowButton.getX();
            f = this.isInRTLMode() ? f2 - (float)n2 + (float)this.mOverflowButton.getWidth() : (float)n2 + f2 - (float)this.mOverflowButton.getWidth();
            Animation animation3 = new Animation(){

                @Override
                protected void applyTransformation(float f5, Transformation transformation) {
                    float f22 = f2;
                    float f3 = f;
                    float f4 = FloatingToolbarPopup.this.isInRTLMode() ? 0.0f : (float)(FloatingToolbarPopup.this.mContentContainer.getWidth() - n2);
                    FloatingToolbarPopup.this.mOverflowButton.setX(f22 + (f3 - f22) * f5 + f4);
                }
            };
            animation.setInterpolator(this.mFastOutSlowInInterpolator);
            animation.setDuration(this.getAdjustedDuration(250));
            animation2.setInterpolator(this.mLogAccelerateInterpolator);
            animation2.setDuration(this.getAdjustedDuration(250));
            animation3.setInterpolator(this.mFastOutSlowInInterpolator);
            animation3.setDuration(this.getAdjustedDuration(250));
            this.mCloseOverflowAnimation.getAnimations().clear();
            this.mCloseOverflowAnimation.addAnimation(animation);
            this.mCloseOverflowAnimation.addAnimation(animation2);
            this.mCloseOverflowAnimation.addAnimation(animation3);
            this.mContentContainer.startAnimation(this.mCloseOverflowAnimation);
            this.mIsOverflowOpen = false;
            this.mMainPanel.animate().alpha(1.0f).withLayer().setInterpolator(this.mFastOutLinearInInterpolator).setDuration(100L).start();
            this.mOverflowPanel.animate().alpha(0.0f).withLayer().setInterpolator(this.mLinearOutSlowInInterpolator).setDuration(150L).start();
        }

        private ViewGroup createMainPanel() {
            return new LinearLayout(this.mContext){

                @Override
                public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                    return FloatingToolbarPopup.this.isOverflowAnimating();
                }

                @Override
                protected void onMeasure(int n, int n2) {
                    if (FloatingToolbarPopup.this.isOverflowAnimating()) {
                        n = View.MeasureSpec.makeMeasureSpec(FloatingToolbarPopup.this.mMainPanelSize.getWidth(), 1073741824);
                    }
                    super.onMeasure(n, n2);
                }
            };
        }

        private Animation.AnimationListener createOverflowAnimationListener() {
            return new Animation.AnimationListener(){

                public /* synthetic */ void lambda$onAnimationEnd$0$FloatingToolbar$FloatingToolbarPopup$13() {
                    FloatingToolbarPopup.this.setPanelsStatesAtRestingPosition();
                    FloatingToolbarPopup.this.setContentAreaAsTouchableSurface();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    FloatingToolbarPopup.this.mContentContainer.post(new _$$Lambda$FloatingToolbar$FloatingToolbarPopup$13$7WTSUuAWkzil48e0QxuKTn0YOXI(this));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationStart(Animation animation) {
                    FloatingToolbarPopup.this.mOverflowButton.setEnabled(false);
                    FloatingToolbarPopup.this.mMainPanel.setVisibility(0);
                    FloatingToolbarPopup.this.mOverflowPanel.setVisibility(0);
                }
            };
        }

        private ImageButton createOverflowButton() {
            ImageButton imageButton = (ImageButton)LayoutInflater.from(this.mContext).inflate(17367153, null);
            imageButton.setImageDrawable(this.mOverflow);
            imageButton.setOnClickListener(new _$$Lambda$FloatingToolbar$FloatingToolbarPopup$_uEfRwR__1oHxMvRVdmbNRdukDM(this, imageButton));
            return imageButton;
        }

        private OverflowPanel createOverflowPanel() {
            OverflowPanel overflowPanel = new OverflowPanel(this);
            overflowPanel.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            overflowPanel.setDivider(null);
            overflowPanel.setDividerHeight(0);
            overflowPanel.setAdapter(new ArrayAdapter<MenuItem>(this.mContext, 0){

                @Override
                public View getView(int n, View view, ViewGroup viewGroup) {
                    return FloatingToolbarPopup.this.mOverflowPanelViewHelper.getView((MenuItem)this.getItem(n), FloatingToolbarPopup.this.mOverflowPanelSize.getWidth(), view);
                }
            });
            overflowPanel.setOnItemClickListener(new _$$Lambda$FloatingToolbar$FloatingToolbarPopup$E8FwnPCl7gZpcTlX_UaRPIBRnT0(this, overflowPanel));
            return overflowPanel;
        }

        private int getAdjustedDuration(int n) {
            int n2 = this.mTransitionDurationScale;
            if (n2 < 150) {
                return Math.max(n - 50, 0);
            }
            if (n2 > 300) {
                return n + 50;
            }
            return (int)((float)n * ValueAnimator.getDurationScale());
        }

        private int getAdjustedToolbarWidth(int n) {
            this.refreshViewPort();
            int n2 = this.mViewPortOnScreen.width();
            int n3 = this.mParent.getResources().getDimensionPixelSize(17105154);
            int n4 = n;
            if (n <= 0) {
                n4 = this.mParent.getResources().getDimensionPixelSize(17105165);
            }
            return Math.min(n4, n2 - n3 * 2);
        }

        private int getOverflowWidth() {
            int n = 0;
            int n2 = this.mOverflowPanel.getAdapter().getCount();
            for (int i = 0; i < n2; ++i) {
                MenuItem menuItem = (MenuItem)this.mOverflowPanel.getAdapter().getItem(i);
                n = Math.max(this.mOverflowPanelViewHelper.calculateWidth(menuItem), n);
            }
            return n;
        }

        private boolean hasOverflow() {
            boolean bl = this.mOverflowPanelSize != null;
            return bl;
        }

        private boolean isInRTLMode() {
            boolean bl = this.mContext.getApplicationInfo().hasRtlSupport();
            boolean bl2 = true;
            if (!bl || this.mContext.getResources().getConfiguration().getLayoutDirection() != 1) {
                bl2 = false;
            }
            return bl2;
        }

        private boolean isOverflowAnimating() {
            boolean bl = this.mOpenOverflowAnimation.hasStarted();
            boolean bl2 = true;
            boolean bl3 = bl && !this.mOpenOverflowAnimation.hasEnded();
            boolean bl4 = this.mCloseOverflowAnimation.hasStarted() && !this.mCloseOverflowAnimation.hasEnded();
            bl = bl2;
            if (!bl3) {
                bl = bl4 ? bl2 : false;
            }
            return bl;
        }

        private void layoutOverflowPanelItems(List<MenuItem> list) {
            ArrayAdapter arrayAdapter = (ArrayAdapter)this.mOverflowPanel.getAdapter();
            arrayAdapter.clear();
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                arrayAdapter.add(list.get(i));
            }
            this.mOverflowPanel.setAdapter(arrayAdapter);
            if (this.mOpenOverflowUpwards) {
                this.mOverflowPanel.setY(0.0f);
            } else {
                this.mOverflowPanel.setY(this.mOverflowButtonSize.getHeight());
            }
            this.mOverflowPanelSize = new Size(Math.max(this.getOverflowWidth(), this.mOverflowButtonSize.getWidth()), this.calculateOverflowHeight(4));
            FloatingToolbarPopup.setSize(this.mOverflowPanel, this.mOverflowPanelSize);
        }

        private void maybeComputeTransitionDurationScale() {
            Size size = this.mMainPanelSize;
            if (size != null && this.mOverflowPanelSize != null) {
                int n = size.getWidth() - this.mOverflowPanelSize.getWidth();
                int n2 = this.mOverflowPanelSize.getHeight() - this.mMainPanelSize.getHeight();
                this.mTransitionDurationScale = (int)(Math.sqrt(n * n + n2 * n2) / (double)this.mContentContainer.getContext().getResources().getDisplayMetrics().density);
            }
        }

        private static Size measure(View view) {
            boolean bl = view.getParent() == null;
            Preconditions.checkState(bl);
            view.measure(0, 0);
            return new Size(view.getMeasuredWidth(), view.getMeasuredHeight());
        }

        private void openOverflow() {
            final int n = this.mOverflowPanelSize.getWidth();
            final int n2 = this.mOverflowPanelSize.getHeight();
            final int n3 = this.mContentContainer.getWidth();
            final int n4 = this.mContentContainer.getHeight();
            final float f = this.mContentContainer.getY();
            final float f2 = this.mContentContainer.getX();
            Animation animation = new Animation(f2 + (float)this.mContentContainer.getWidth()){
                final /* synthetic */ float val$right;
                {
                    this.val$right = f22;
                }

                @Override
                protected void applyTransformation(float f, Transformation transformation) {
                    int n2 = (int)((float)(n - n3) * f);
                    FloatingToolbarPopup.setWidth(FloatingToolbarPopup.this.mContentContainer, n3 + n2);
                    if (FloatingToolbarPopup.this.isInRTLMode()) {
                        FloatingToolbarPopup.this.mContentContainer.setX(f2);
                        FloatingToolbarPopup.this.mMainPanel.setX(0.0f);
                        FloatingToolbarPopup.this.mOverflowPanel.setX(0.0f);
                    } else {
                        FloatingToolbarPopup.this.mContentContainer.setX(this.val$right - (float)FloatingToolbarPopup.this.mContentContainer.getWidth());
                        FloatingToolbarPopup.this.mMainPanel.setX(FloatingToolbarPopup.this.mContentContainer.getWidth() - n3);
                        FloatingToolbarPopup.this.mOverflowPanel.setX(FloatingToolbarPopup.this.mContentContainer.getWidth() - n);
                    }
                }
            };
            Animation animation2 = new Animation(){

                @Override
                protected void applyTransformation(float f2, Transformation transformation) {
                    int n = (int)((float)(n2 - n4) * f2);
                    FloatingToolbarPopup.setHeight(FloatingToolbarPopup.this.mContentContainer, n4 + n);
                    if (FloatingToolbarPopup.this.mOpenOverflowUpwards) {
                        FloatingToolbarPopup.this.mContentContainer.setY(f - (float)(FloatingToolbarPopup.this.mContentContainer.getHeight() - n4));
                        FloatingToolbarPopup.this.positionContentYCoordinatesIfOpeningOverflowUpwards();
                    }
                }
            };
            f = this.mOverflowButton.getX();
            f2 = this.isInRTLMode() ? (float)n + f - (float)this.mOverflowButton.getWidth() : f - (float)n + (float)this.mOverflowButton.getWidth();
            Animation animation3 = new Animation(){

                @Override
                protected void applyTransformation(float f5, Transformation transformation) {
                    float f22 = f;
                    float f3 = f2;
                    float f4 = FloatingToolbarPopup.this.isInRTLMode() ? 0.0f : (float)(FloatingToolbarPopup.this.mContentContainer.getWidth() - n3);
                    FloatingToolbarPopup.this.mOverflowButton.setX(f22 + (f3 - f22) * f5 + f4);
                }
            };
            animation.setInterpolator(this.mLogAccelerateInterpolator);
            animation.setDuration(this.getAdjustedDuration(250));
            animation2.setInterpolator(this.mFastOutSlowInInterpolator);
            animation2.setDuration(this.getAdjustedDuration(250));
            animation3.setInterpolator(this.mFastOutSlowInInterpolator);
            animation3.setDuration(this.getAdjustedDuration(250));
            this.mOpenOverflowAnimation.getAnimations().clear();
            this.mOpenOverflowAnimation.getAnimations().clear();
            this.mOpenOverflowAnimation.addAnimation(animation);
            this.mOpenOverflowAnimation.addAnimation(animation2);
            this.mOpenOverflowAnimation.addAnimation(animation3);
            this.mContentContainer.startAnimation(this.mOpenOverflowAnimation);
            this.mIsOverflowOpen = true;
            this.mMainPanel.animate().alpha(0.0f).withLayer().setInterpolator(this.mLinearOutSlowInInterpolator).setDuration(250L).start();
            this.mOverflowPanel.setAlpha(1.0f);
        }

        private void positionContentYCoordinatesIfOpeningOverflowUpwards() {
            if (this.mOpenOverflowUpwards) {
                this.mMainPanel.setY(this.mContentContainer.getHeight() - this.mMainPanelSize.getHeight());
                this.mOverflowButton.setY(this.mContentContainer.getHeight() - this.mOverflowButton.getHeight());
                this.mOverflowPanel.setY(this.mContentContainer.getHeight() - this.mOverflowPanelSize.getHeight());
            }
        }

        private void preparePopupContent() {
            this.mContentContainer.removeAllViews();
            if (this.hasOverflow()) {
                this.mContentContainer.addView(this.mOverflowPanel);
            }
            this.mContentContainer.addView(this.mMainPanel);
            if (this.hasOverflow()) {
                this.mContentContainer.addView(this.mOverflowButton);
            }
            this.setPanelsStatesAtRestingPosition();
            this.setContentAreaAsTouchableSurface();
            if (this.isInRTLMode()) {
                this.mContentContainer.setAlpha(0.0f);
                this.mContentContainer.post(this.mPreparePopupContentRTLHelper);
            }
        }

        private void refreshCoordinatesAndOverflowDirection(Rect arrn) {
            int n;
            int n2;
            int n3;
            this.refreshViewPort();
            int n4 = Math.min(arrn.centerX() - this.mPopupWindow.getWidth() / 2, this.mViewPortOnScreen.right - this.mPopupWindow.getWidth());
            int n5 = arrn.top - this.mViewPortOnScreen.top;
            int n6 = this.mViewPortOnScreen.bottom - arrn.bottom;
            int n7 = this.mMarginVertical * 2;
            int n8 = this.mLineHeight + n7;
            if (!this.hasOverflow()) {
                n3 = n5 >= n8 ? arrn.top - n8 : (n6 >= n8 ? arrn.bottom : (n6 >= this.mLineHeight ? arrn.bottom - this.mMarginVertical : Math.max(this.mViewPortOnScreen.top, arrn.top - n8)));
            } else {
                n2 = this.calculateOverflowHeight(2) + n7;
                n3 = this.mViewPortOnScreen.bottom - arrn.top + n8;
                n = arrn.bottom;
                int n9 = this.mViewPortOnScreen.top;
                if (n5 >= n2) {
                    this.updateOverflowHeight(n5 - n7);
                    n3 = arrn.top - this.mPopupWindow.getHeight();
                    this.mOpenOverflowUpwards = true;
                } else if (n5 >= n8 && n3 >= n2) {
                    this.updateOverflowHeight(n3 - n7);
                    n3 = arrn.top - n8;
                    this.mOpenOverflowUpwards = false;
                } else if (n6 >= n2) {
                    this.updateOverflowHeight(n6 - n7);
                    n3 = arrn.bottom;
                    this.mOpenOverflowUpwards = false;
                } else if (n6 >= n8 && this.mViewPortOnScreen.height() >= n2) {
                    this.updateOverflowHeight(n - n9 + n8 - n7);
                    n3 = arrn.bottom + n8 - this.mPopupWindow.getHeight();
                    this.mOpenOverflowUpwards = true;
                } else {
                    this.updateOverflowHeight(this.mViewPortOnScreen.height() - n7);
                    n3 = this.mViewPortOnScreen.top;
                    this.mOpenOverflowUpwards = false;
                }
            }
            this.mParent.getRootView().getLocationOnScreen(this.mTmpCoords);
            arrn = this.mTmpCoords;
            n = arrn[0];
            n7 = arrn[1];
            this.mParent.getRootView().getLocationInWindow(this.mTmpCoords);
            arrn = this.mTmpCoords;
            n2 = arrn[0];
            n8 = arrn[1];
            this.mCoordsOnWindow.set(Math.max(0, n4 - (n - n2)), Math.max(0, n3 - (n7 - n8)));
        }

        private void refreshViewPort() {
            this.mParent.getWindowVisibleDisplayFrame(this.mViewPortOnScreen);
        }

        private void runDismissAnimation() {
            this.mDismissAnimation.start();
        }

        private void runHideAnimation() {
            this.mHideAnimation.start();
        }

        private void runShowAnimation() {
            this.mShowAnimation.start();
        }

        private void setButtonTagAndClickListener(View view, MenuItem menuItem) {
            view.setTag(menuItem);
            view.setOnClickListener(this.mMenuItemButtonOnClickListener);
        }

        private void setContentAreaAsTouchableSurface() {
            int n;
            int n2;
            Preconditions.checkNotNull(this.mMainPanelSize);
            if (this.mIsOverflowOpen) {
                Preconditions.checkNotNull(this.mOverflowPanelSize);
                n2 = this.mOverflowPanelSize.getWidth();
                n = this.mOverflowPanelSize.getHeight();
            } else {
                n2 = this.mMainPanelSize.getWidth();
                n = this.mMainPanelSize.getHeight();
            }
            this.mTouchableRegion.set((int)this.mContentContainer.getX(), (int)this.mContentContainer.getY(), (int)this.mContentContainer.getX() + n2, (int)this.mContentContainer.getY() + n);
        }

        private static void setHeight(View view, int n) {
            FloatingToolbarPopup.setSize(view, view.getLayoutParams().width, n);
        }

        private void setPanelsStatesAtRestingPosition() {
            this.mOverflowButton.setEnabled(true);
            this.mOverflowPanel.awakenScrollBars();
            if (this.mIsOverflowOpen) {
                Size size = this.mOverflowPanelSize;
                FloatingToolbarPopup.setSize(this.mContentContainer, size);
                this.mMainPanel.setAlpha(0.0f);
                this.mMainPanel.setVisibility(4);
                this.mOverflowPanel.setAlpha(1.0f);
                this.mOverflowPanel.setVisibility(0);
                this.mOverflowButton.setImageDrawable(this.mArrow);
                this.mOverflowButton.setContentDescription(this.mContext.getString(17040026));
                if (this.isInRTLMode()) {
                    this.mContentContainer.setX(this.mMarginHorizontal);
                    this.mMainPanel.setX(0.0f);
                    this.mOverflowButton.setX(size.getWidth() - this.mOverflowButtonSize.getWidth());
                    this.mOverflowPanel.setX(0.0f);
                } else {
                    this.mContentContainer.setX(this.mPopupWindow.getWidth() - size.getWidth() - this.mMarginHorizontal);
                    this.mMainPanel.setX(-this.mContentContainer.getX());
                    this.mOverflowButton.setX(0.0f);
                    this.mOverflowPanel.setX(0.0f);
                }
                if (this.mOpenOverflowUpwards) {
                    this.mContentContainer.setY(this.mMarginVertical);
                    this.mMainPanel.setY(size.getHeight() - this.mContentContainer.getHeight());
                    this.mOverflowButton.setY(size.getHeight() - this.mOverflowButtonSize.getHeight());
                    this.mOverflowPanel.setY(0.0f);
                } else {
                    this.mContentContainer.setY(this.mMarginVertical);
                    this.mMainPanel.setY(0.0f);
                    this.mOverflowButton.setY(0.0f);
                    this.mOverflowPanel.setY(this.mOverflowButtonSize.getHeight());
                }
            } else {
                Size size = this.mMainPanelSize;
                FloatingToolbarPopup.setSize(this.mContentContainer, size);
                this.mMainPanel.setAlpha(1.0f);
                this.mMainPanel.setVisibility(0);
                this.mOverflowPanel.setAlpha(0.0f);
                this.mOverflowPanel.setVisibility(4);
                this.mOverflowButton.setImageDrawable(this.mOverflow);
                this.mOverflowButton.setContentDescription(this.mContext.getString(17040027));
                if (this.hasOverflow()) {
                    if (this.isInRTLMode()) {
                        this.mContentContainer.setX(this.mMarginHorizontal);
                        this.mMainPanel.setX(0.0f);
                        this.mOverflowButton.setX(0.0f);
                        this.mOverflowPanel.setX(0.0f);
                    } else {
                        this.mContentContainer.setX(this.mPopupWindow.getWidth() - size.getWidth() - this.mMarginHorizontal);
                        this.mMainPanel.setX(0.0f);
                        this.mOverflowButton.setX(size.getWidth() - this.mOverflowButtonSize.getWidth());
                        this.mOverflowPanel.setX(size.getWidth() - this.mOverflowPanelSize.getWidth());
                    }
                    if (this.mOpenOverflowUpwards) {
                        this.mContentContainer.setY(this.mMarginVertical + this.mOverflowPanelSize.getHeight() - size.getHeight());
                        this.mMainPanel.setY(0.0f);
                        this.mOverflowButton.setY(0.0f);
                        this.mOverflowPanel.setY(size.getHeight() - this.mOverflowPanelSize.getHeight());
                    } else {
                        this.mContentContainer.setY(this.mMarginVertical);
                        this.mMainPanel.setY(0.0f);
                        this.mOverflowButton.setY(0.0f);
                        this.mOverflowPanel.setY(this.mOverflowButtonSize.getHeight());
                    }
                } else {
                    this.mContentContainer.setX(this.mMarginHorizontal);
                    this.mContentContainer.setY(this.mMarginVertical);
                    this.mMainPanel.setX(0.0f);
                    this.mMainPanel.setY(0.0f);
                }
            }
        }

        private static void setSize(View view, int n, int n2) {
            view.setMinimumWidth(n);
            view.setMinimumHeight(n2);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(0, 0);
            }
            layoutParams.width = n;
            layoutParams.height = n2;
            view.setLayoutParams(layoutParams);
        }

        private static void setSize(View view, Size size) {
            FloatingToolbarPopup.setSize(view, size.getWidth(), size.getHeight());
        }

        private void setTouchableSurfaceInsetsComputer() {
            ViewTreeObserver viewTreeObserver = this.mPopupWindow.getContentView().getRootView().getViewTreeObserver();
            viewTreeObserver.removeOnComputeInternalInsetsListener(this.mInsetsComputer);
            viewTreeObserver.addOnComputeInternalInsetsListener(this.mInsetsComputer);
        }

        private static void setWidth(View view, int n) {
            FloatingToolbarPopup.setSize(view, n, view.getLayoutParams().height);
        }

        private void setZeroTouchableSurface() {
            this.mTouchableRegion.setEmpty();
        }

        private void updateOverflowHeight(int n) {
            if (this.hasOverflow()) {
                n = this.calculateOverflowHeight((n - this.mOverflowButtonSize.getHeight()) / this.mLineHeight);
                if (this.mOverflowPanelSize.getHeight() != n) {
                    this.mOverflowPanelSize = new Size(this.mOverflowPanelSize.getWidth(), n);
                }
                FloatingToolbarPopup.setSize(this.mOverflowPanel, this.mOverflowPanelSize);
                if (this.mIsOverflowOpen) {
                    FloatingToolbarPopup.setSize(this.mContentContainer, this.mOverflowPanelSize);
                    if (this.mOpenOverflowUpwards) {
                        n = this.mOverflowPanelSize.getHeight() - n;
                        View view = this.mContentContainer;
                        view.setY(view.getY() + (float)n);
                        view = this.mOverflowButton;
                        view.setY(view.getY() - (float)n);
                    }
                } else {
                    FloatingToolbarPopup.setSize(this.mContentContainer, this.mMainPanelSize);
                }
                this.updatePopupSize();
            }
        }

        private void updatePopupSize() {
            int n = 0;
            int n2 = 0;
            Size size = this.mMainPanelSize;
            if (size != null) {
                n = Math.max(0, size.getWidth());
                n2 = Math.max(0, this.mMainPanelSize.getHeight());
            }
            size = this.mOverflowPanelSize;
            int n3 = n;
            int n4 = n2;
            if (size != null) {
                n3 = Math.max(n, size.getWidth());
                n4 = Math.max(n2, this.mOverflowPanelSize.getHeight());
            }
            this.mPopupWindow.setWidth(this.mMarginHorizontal * 2 + n3);
            this.mPopupWindow.setHeight(this.mMarginVertical * 2 + n4);
            this.maybeComputeTransitionDurationScale();
        }

        public void dismiss() {
            if (this.mDismissed) {
                return;
            }
            this.mHidden = false;
            this.mDismissed = true;
            this.mHideAnimation.cancel();
            this.runDismissAnimation();
            this.setZeroTouchableSurface();
        }

        public void hide() {
            if (!this.isShowing()) {
                return;
            }
            this.mHidden = true;
            this.runHideAnimation();
            this.setZeroTouchableSurface();
        }

        public boolean isHidden() {
            return this.mHidden;
        }

        public boolean isShowing() {
            boolean bl = !this.mDismissed && !this.mHidden;
            return bl;
        }

        public /* synthetic */ void lambda$createOverflowButton$1$FloatingToolbar$FloatingToolbarPopup(ImageButton imageButton, View view) {
            if (this.mIsOverflowOpen) {
                imageButton.setImageDrawable(this.mToOverflow);
                this.mToOverflow.start();
                this.closeOverflow();
            } else {
                imageButton.setImageDrawable(this.mToArrow);
                this.mToArrow.start();
                this.openOverflow();
            }
        }

        public /* synthetic */ void lambda$createOverflowPanel$2$FloatingToolbar$FloatingToolbarPopup(OverflowPanel object, AdapterView object2, View view, int n, long l) {
            object2 = (MenuItem)((ListView)object).getAdapter().getItem(n);
            object = this.mOnMenuItemClickListener;
            if (object != null) {
                object.onMenuItemClick((MenuItem)object2);
            }
        }

        public /* synthetic */ void lambda$new$0$FloatingToolbar$FloatingToolbarPopup(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
            internalInsetsInfo.contentInsets.setEmpty();
            internalInsetsInfo.visibleInsets.setEmpty();
            internalInsetsInfo.touchableRegion.set(this.mTouchableRegion);
            internalInsetsInfo.setTouchableInsets(3);
        }

        public List<MenuItem> layoutMainPanelItems(List<MenuItem> object, int n) {
            Preconditions.checkNotNull(object);
            int n2 = n;
            LinkedList<MenuItem> linkedList = new LinkedList<MenuItem>();
            Object object2 = new LinkedList<Object>();
            Object object3 = object.iterator();
            while (object3.hasNext()) {
                object = object3.next();
                if (object.getItemId() != 16908353 && object.requiresOverflow()) {
                    ((LinkedList)object2).add(object);
                    continue;
                }
                linkedList.add((MenuItem)object);
            }
            linkedList.addAll((Collection<MenuItem>)object2);
            this.mMainPanel.removeAllViews();
            this.mMainPanel.setPaddingRelative(0, 0, 0, 0);
            boolean bl = true;
            while (!linkedList.isEmpty()) {
                boolean bl2;
                object = (MenuItem)linkedList.peek();
                if (!bl && object.requiresOverflow()) break;
                boolean bl3 = bl && object.getItemId() == 16908353;
                object2 = FloatingToolbar.createMenuItemButton(this.mContext, (MenuItem)object, this.mIconTextSpacing, bl3);
                if (!bl3 && object2 instanceof LinearLayout) {
                    ((LinearLayout)object2).setGravity(17);
                }
                if (bl) {
                    ((View)object2).setPaddingRelative((int)((double)((View)object2).getPaddingStart() * 1.5), ((View)object2).getPaddingTop(), ((View)object2).getPaddingEnd(), ((View)object2).getPaddingBottom());
                }
                if (bl2 = linkedList.size() == 1) {
                    ((View)object2).setPaddingRelative(((View)object2).getPaddingStart(), ((View)object2).getPaddingTop(), (int)((double)((View)object2).getPaddingEnd() * 1.5), ((View)object2).getPaddingBottom());
                }
                ((View)object2).measure(0, 0);
                int n3 = Math.min(((View)object2).getMeasuredWidth(), n);
                bl = n3 <= n2 - this.mOverflowButtonSize.getWidth();
                bl2 = bl2 && n3 <= n2;
                if (!bl && !bl2) break;
                this.setButtonTagAndClickListener((View)object2, (MenuItem)object);
                ((View)object2).setTooltipText(object.getTooltipText());
                this.mMainPanel.addView((View)object2);
                object3 = ((View)object2).getLayoutParams();
                ((ViewGroup.LayoutParams)object3).width = n3;
                ((View)object2).setLayoutParams((ViewGroup.LayoutParams)object3);
                n2 -= n3;
                linkedList.pop();
                object.getGroupId();
                bl = false;
            }
            if (!linkedList.isEmpty()) {
                this.mMainPanel.setPaddingRelative(0, 0, this.mOverflowButtonSize.getWidth(), 0);
            }
            this.mMainPanelSize = FloatingToolbarPopup.measure(this.mMainPanel);
            return linkedList;
        }

        public void layoutMenuItems(List<MenuItem> list, MenuItem.OnMenuItemClickListener onMenuItemClickListener, int n) {
            this.mOnMenuItemClickListener = onMenuItemClickListener;
            this.cancelOverflowAnimations();
            this.clearPanels();
            list = this.layoutMainPanelItems(list, this.getAdjustedToolbarWidth(n));
            if (!list.isEmpty()) {
                this.layoutOverflowPanelItems(list);
            }
            this.updatePopupSize();
        }

        public boolean setOutsideTouchable(boolean bl, PopupWindow.OnDismissListener onDismissListener) {
            boolean bl2 = false;
            if (this.mPopupWindow.isOutsideTouchable() ^ bl) {
                this.mPopupWindow.setOutsideTouchable(bl);
                this.mPopupWindow.setFocusable(bl ^ true);
                bl2 = true;
            }
            this.mPopupWindow.setOnDismissListener(onDismissListener);
            return bl2;
        }

        public void show(Rect rect) {
            Preconditions.checkNotNull(rect);
            if (this.isShowing()) {
                return;
            }
            this.mHidden = false;
            this.mDismissed = false;
            this.cancelDismissAndHideAnimations();
            this.cancelOverflowAnimations();
            this.refreshCoordinatesAndOverflowDirection(rect);
            this.preparePopupContent();
            this.mPopupWindow.showAtLocation(this.mParent, 0, this.mCoordsOnWindow.x, this.mCoordsOnWindow.y);
            this.setTouchableSurfaceInsetsComputer();
            this.runShowAnimation();
        }

        public void updateCoordinates(Rect rect) {
            Preconditions.checkNotNull(rect);
            if (this.isShowing() && this.mPopupWindow.isShowing()) {
                this.cancelOverflowAnimations();
                this.refreshCoordinatesAndOverflowDirection(rect);
                this.preparePopupContent();
                this.mPopupWindow.update(this.mCoordsOnWindow.x, this.mCoordsOnWindow.y, this.mPopupWindow.getWidth(), this.mPopupWindow.getHeight());
                return;
            }
        }

        private static final class LogAccelerateInterpolator
        implements Interpolator {
            private static final int BASE = 100;
            private static final float LOGS_SCALE = 1.0f / LogAccelerateInterpolator.computeLog(1.0f, 100);

            private LogAccelerateInterpolator() {
            }

            private static float computeLog(float f, int n) {
                return (float)(1.0 - Math.pow(n, -f));
            }

            @Override
            public float getInterpolation(float f) {
                return 1.0f - LogAccelerateInterpolator.computeLog(1.0f - f, 100) * LOGS_SCALE;
            }
        }

        private static final class OverflowPanel
        extends ListView {
            private final FloatingToolbarPopup mPopup;

            OverflowPanel(FloatingToolbarPopup floatingToolbarPopup) {
                super(Preconditions.checkNotNull(floatingToolbarPopup).mContext);
                this.mPopup = floatingToolbarPopup;
                this.setScrollBarDefaultDelayBeforeFade(ViewConfiguration.getScrollDefaultDelay() * 3);
                this.setScrollIndicators(3);
            }

            @Override
            protected boolean awakenScrollBars() {
                return super.awakenScrollBars();
            }

            @Override
            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                if (this.mPopup.isOverflowAnimating()) {
                    return true;
                }
                return super.dispatchTouchEvent(motionEvent);
            }

            @Override
            protected void onMeasure(int n, int n2) {
                super.onMeasure(n, View.MeasureSpec.makeMeasureSpec(this.mPopup.mOverflowPanelSize.getHeight() - this.mPopup.mOverflowButtonSize.getHeight(), 1073741824));
            }
        }

        private static final class OverflowPanelViewHelper {
            private final View mCalculator;
            private final Context mContext;
            private final int mIconTextSpacing;
            private final int mSidePadding;

            public OverflowPanelViewHelper(Context context, int n) {
                this.mContext = Preconditions.checkNotNull(context);
                this.mIconTextSpacing = n;
                this.mSidePadding = context.getResources().getDimensionPixelSize(17105164);
                this.mCalculator = this.createMenuButton(null);
            }

            private View createMenuButton(MenuItem object) {
                object = FloatingToolbar.createMenuItemButton(this.mContext, (MenuItem)object, this.mIconTextSpacing, this.shouldShowIcon((MenuItem)object));
                int n = this.mSidePadding;
                ((View)object).setPadding(n, 0, n, 0);
                return object;
            }

            private boolean shouldShowIcon(MenuItem menuItem) {
                boolean bl = false;
                if (menuItem != null) {
                    if (menuItem.getGroupId() == 16908353) {
                        bl = true;
                    }
                    return bl;
                }
                return false;
            }

            public int calculateWidth(MenuItem menuItem) {
                FloatingToolbar.updateMenuItemButton(this.mCalculator, menuItem, this.mIconTextSpacing, this.shouldShowIcon(menuItem));
                this.mCalculator.measure(0, 0);
                return this.mCalculator.getMeasuredWidth();
            }

            public View getView(MenuItem menuItem, int n, View view) {
                Preconditions.checkNotNull(menuItem);
                if (view != null) {
                    FloatingToolbar.updateMenuItemButton(view, menuItem, this.mIconTextSpacing, this.shouldShowIcon(menuItem));
                } else {
                    view = this.createMenuButton(menuItem);
                }
                view.setMinimumWidth(n);
                return view;
            }
        }

    }

}

