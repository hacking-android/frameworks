/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Transformation;
import android.widget.AbsSpinner;
import android.widget.AdapterView;
import android.widget.Scroller;
import android.widget.SpinnerAdapter;
import com.android.internal.R;

@Deprecated
public class Gallery
extends AbsSpinner
implements GestureDetector.OnGestureListener {
    private static final int SCROLL_TO_FLING_UNCERTAINTY_TIMEOUT = 250;
    private static final String TAG = "Gallery";
    private static final boolean localLOGV = false;
    private int mAnimationDuration = 400;
    private AdapterView.AdapterContextMenuInfo mContextMenuInfo;
    private Runnable mDisableSuppressSelectionChangedRunnable = new Runnable(){

        @Override
        public void run() {
            Gallery.this.mSuppressSelectionChanged = false;
            Gallery.this.selectionChanged();
        }
    };
    @UnsupportedAppUsage
    private int mDownTouchPosition;
    @UnsupportedAppUsage
    private View mDownTouchView;
    @UnsupportedAppUsage
    private FlingRunnable mFlingRunnable = new FlingRunnable();
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private GestureDetector mGestureDetector;
    private int mGravity;
    private boolean mIsFirstScroll;
    private boolean mIsRtl = true;
    private int mLeftMost;
    private boolean mReceivedInvokeKeyDown;
    private int mRightMost;
    private int mSelectedCenterOffset;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private View mSelectedChild;
    private boolean mShouldCallbackDuringFling = true;
    private boolean mShouldCallbackOnUnselectedItemClick = true;
    private boolean mShouldStopFling;
    @UnsupportedAppUsage
    private int mSpacing = 0;
    private boolean mSuppressSelectionChanged;
    private float mUnselectedAlpha;

    public Gallery(Context context) {
        this(context, null);
    }

    public Gallery(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842864);
    }

    public Gallery(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public Gallery(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.Gallery, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.Gallery, attributeSet, typedArray, n, n2);
        n = typedArray.getInt(0, -1);
        if (n >= 0) {
            this.setGravity(n);
        }
        if ((n = typedArray.getInt(1, -1)) > 0) {
            this.setAnimationDuration(n);
        }
        this.setSpacing(typedArray.getDimensionPixelOffset(2, 0));
        this.setUnselectedAlpha(typedArray.getFloat(3, 0.5f));
        typedArray.recycle();
        this.mGroupFlags |= 1024;
        this.mGroupFlags |= 2048;
    }

    private int calculateTop(View view, boolean bl) {
        int n = bl ? this.getMeasuredHeight() : this.getHeight();
        int n2 = bl ? view.getMeasuredHeight() : view.getHeight();
        int n3 = 0;
        int n4 = this.mGravity;
        if (n4 != 16) {
            n = n4 != 48 ? (n4 != 80 ? n3 : n - this.mSpinnerPadding.bottom - n2) : this.mSpinnerPadding.top;
        } else {
            n3 = this.mSpinnerPadding.bottom;
            n4 = this.mSpinnerPadding.top;
            n = this.mSpinnerPadding.top + (n - n3 - n4 - n2) / 2;
        }
        return n;
    }

    private void detachOffScreenChildren(boolean bl) {
        int n;
        int n2 = this.getChildCount();
        int n3 = this.mFirstPosition;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        if (bl) {
            View view;
            int n8 = this.mPaddingLeft;
            n6 = n7;
            n4 = n5;
            for (n = 0; n < n2 && (view = this.getChildAt(n5 = this.mIsRtl ? n2 - 1 - n : n)).getRight() < n8; ++n) {
                n4 = n5;
                ++n6;
                this.mRecycler.put(n3 + n5, view);
            }
            if (!this.mIsRtl) {
                n4 = 0;
            }
            n = n6;
        } else {
            View view;
            n7 = this.getWidth();
            int n9 = this.mPaddingRight;
            for (n = n2 - 1; n >= 0 && (view = this.getChildAt(n5 = this.mIsRtl ? n2 - 1 - n : n)).getLeft() > n7 - n9; --n) {
                n4 = n5;
                ++n6;
                this.mRecycler.put(n3 + n5, view);
            }
            n = n6;
            if (this.mIsRtl) {
                n4 = 0;
                n = n6;
            }
        }
        this.detachViewsFromParent(n4, n);
        if (bl != this.mIsRtl) {
            this.mFirstPosition += n;
        }
    }

    private boolean dispatchLongPress(View view, int n, long l, float f, float f2, boolean bl) {
        boolean bl2 = false;
        if (this.mOnItemLongClickListener != null) {
            bl2 = this.mOnItemLongClickListener.onItemLongClick(this, this.mDownTouchView, this.mDownTouchPosition, l);
        }
        boolean bl3 = bl2;
        if (!bl2) {
            this.mContextMenuInfo = new AdapterView.AdapterContextMenuInfo(view, n, l);
            bl3 = bl ? super.showContextMenuForChild(view, f, f2) : super.showContextMenuForChild(this);
        }
        if (bl3) {
            this.performHapticFeedback(0);
        }
        return bl3;
    }

    private void dispatchPress(View view) {
        if (view != null) {
            view.setPressed(true);
        }
        this.setPressed(true);
    }

    private void dispatchUnpress() {
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            this.getChildAt(i).setPressed(false);
        }
        this.setPressed(false);
    }

    @UnsupportedAppUsage
    private void fillToGalleryLeft() {
        if (this.mIsRtl) {
            this.fillToGalleryLeftRtl();
        } else {
            this.fillToGalleryLeftLtr();
        }
    }

    private void fillToGalleryLeftLtr() {
        int n;
        int n2;
        int n3 = this.mSpacing;
        int n4 = this.mPaddingLeft;
        View view = this.getChildAt(0);
        if (view != null) {
            n2 = this.mFirstPosition - 1;
            n = view.getLeft() - n3;
        } else {
            n2 = 0;
            n = this.mRight;
            int n5 = this.mLeft;
            int n6 = this.mPaddingRight;
            this.mShouldStopFling = true;
            n = n - n5 - n6;
        }
        while (n > n4 && n2 >= 0) {
            view = this.makeAndAddView(n2, n2 - this.mSelectedPosition, n, false);
            this.mFirstPosition = n2--;
            n = view.getLeft() - n3;
        }
    }

    private void fillToGalleryLeftRtl() {
        int n = this.mSpacing;
        int n2 = this.mPaddingLeft;
        int n3 = this.getChildCount();
        int n4 = this.mItemCount;
        View view = this.getChildAt(n3 - 1);
        if (view != null) {
            int n5 = this.mFirstPosition;
            n4 = view.getLeft();
            n3 = n5 + n3;
            n4 -= n;
        } else {
            n3 = n4 = this.mItemCount - 1;
            this.mFirstPosition = n4;
            n4 = this.mRight - this.mLeft - this.mPaddingRight;
            this.mShouldStopFling = true;
        }
        while (n4 > n2 && n3 < this.mItemCount) {
            n4 = this.makeAndAddView(n3, n3 - this.mSelectedPosition, n4, false).getLeft() - n;
            ++n3;
        }
    }

    @UnsupportedAppUsage
    private void fillToGalleryRight() {
        if (this.mIsRtl) {
            this.fillToGalleryRightRtl();
        } else {
            this.fillToGalleryRightLtr();
        }
    }

    private void fillToGalleryRightLtr() {
        int n;
        int n2;
        int n3 = this.mSpacing;
        int n4 = this.mRight;
        int n5 = this.mLeft;
        int n6 = this.mPaddingRight;
        int n7 = this.getChildCount();
        int n8 = this.mItemCount;
        View view = this.getChildAt(n7 - 1);
        if (view != null) {
            n2 = this.mFirstPosition;
            n = view.getRight();
            n2 += n7;
            n += n3;
        } else {
            n2 = n = this.mItemCount - 1;
            this.mFirstPosition = n;
            n = this.mPaddingLeft;
            this.mShouldStopFling = true;
        }
        while (n < n4 - n5 - n6 && n2 < n8) {
            n = this.makeAndAddView(n2, n2 - this.mSelectedPosition, n, true).getRight() + n3;
            ++n2;
        }
    }

    private void fillToGalleryRightRtl() {
        int n;
        int n2;
        int n3 = this.mSpacing;
        int n4 = this.mRight;
        int n5 = this.mLeft;
        int n6 = this.mPaddingRight;
        View view = this.getChildAt(0);
        if (view != null) {
            n2 = this.mFirstPosition - 1;
            n = view.getRight() + n3;
        } else {
            n2 = 0;
            n = this.mPaddingLeft;
            this.mShouldStopFling = true;
        }
        while (n < n4 - n5 - n6 && n2 >= 0) {
            view = this.makeAndAddView(n2, n2 - this.mSelectedPosition, n, true);
            this.mFirstPosition = n2--;
            n = view.getRight() + n3;
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int getCenterOfGallery() {
        return (this.getWidth() - this.mPaddingLeft - this.mPaddingRight) / 2 + this.mPaddingLeft;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static int getCenterOfView(View view) {
        return view.getLeft() + view.getWidth() / 2;
    }

    @UnsupportedAppUsage
    private View makeAndAddView(int n, int n2, int n3, boolean bl) {
        View view;
        if (!this.mDataChanged && (view = this.mRecycler.get(n)) != null) {
            n = view.getLeft();
            this.mRightMost = Math.max(this.mRightMost, view.getMeasuredWidth() + n);
            this.mLeftMost = Math.min(this.mLeftMost, n);
            this.setUpChild(view, n2, n3, bl);
            return view;
        }
        view = this.mAdapter.getView(n, null, this);
        this.setUpChild(view, n2, n3, bl);
        return view;
    }

    private void offsetChildrenLeftAndRight(int n) {
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            this.getChildAt(i).offsetLeftAndRight(n);
        }
    }

    private void onFinishedMovement() {
        if (this.mSuppressSelectionChanged) {
            this.mSuppressSelectionChanged = false;
            super.selectionChanged();
        }
        this.mSelectedCenterOffset = 0;
        this.invalidate();
    }

    private void scrollIntoSlots() {
        View view;
        if (this.getChildCount() != 0 && (view = this.mSelectedChild) != null) {
            int n = Gallery.getCenterOfView(view);
            n = this.getCenterOfGallery() - n;
            if (n != 0) {
                this.mFlingRunnable.startUsingDistance(n);
            } else {
                this.onFinishedMovement();
            }
            return;
        }
    }

    private boolean scrollToChild(int n) {
        View view = this.getChildAt(n);
        if (view != null) {
            int n2 = this.getCenterOfGallery();
            n = Gallery.getCenterOfView(view);
            this.mFlingRunnable.startUsingDistance(n2 - n);
            return true;
        }
        return false;
    }

    private void setSelectionToCenterChild() {
        int n;
        View view = this.mSelectedChild;
        if (this.mSelectedChild == null) {
            return;
        }
        int n2 = this.getCenterOfGallery();
        if (view.getLeft() <= n2 && view.getRight() >= n2) {
            return;
        }
        int n3 = Integer.MAX_VALUE;
        int n4 = 0;
        int n5 = this.getChildCount() - 1;
        do {
            n = n4;
            if (n5 < 0) break;
            view = this.getChildAt(n5);
            if (view.getLeft() <= n2 && view.getRight() >= n2) {
                n = n5;
                break;
            }
            int n6 = Math.min(Math.abs(view.getLeft() - n2), Math.abs(view.getRight() - n2));
            n = n3;
            if (n6 < n3) {
                n = n6;
                n4 = n5;
            }
            --n5;
            n3 = n;
        } while (true);
        if ((n5 = this.mFirstPosition + n) != this.mSelectedPosition) {
            this.setSelectedPositionInt(n5);
            this.setNextSelectedPositionInt(n5);
            this.checkSelectionChanged();
        }
    }

    private void setUpChild(View view, int n, int n2, boolean bl) {
        LayoutParams layoutParams;
        LayoutParams layoutParams2 = layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams2 = (LayoutParams)this.generateDefaultLayoutParams();
        }
        boolean bl2 = this.mIsRtl;
        boolean bl3 = false;
        int n3 = bl != bl2 ? -1 : 0;
        this.addViewInLayout(view, n3, layoutParams2, true);
        if (n == 0) {
            bl3 = true;
        }
        view.setSelected(bl3);
        n = ViewGroup.getChildMeasureSpec(this.mHeightMeasureSpec, this.mSpinnerPadding.top + this.mSpinnerPadding.bottom, layoutParams2.height);
        view.measure(ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mSpinnerPadding.left + this.mSpinnerPadding.right, layoutParams2.width), n);
        int n4 = this.calculateTop(view, true);
        int n5 = view.getMeasuredHeight();
        n = view.getMeasuredWidth();
        if (bl) {
            n = n2 + n;
        } else {
            n3 = n2 - n;
            n = n2;
            n2 = n3;
        }
        view.layout(n2, n4, n, n5 + n4);
    }

    private boolean showContextMenuForChildInternal(View view, float f, float f2, boolean bl) {
        int n = this.getPositionForView(view);
        if (n < 0) {
            return false;
        }
        return this.dispatchLongPress(view, n, this.mAdapter.getItemId(n), f, f2, bl);
    }

    private boolean showContextMenuInternal(float f, float f2, boolean bl) {
        if (this.isPressed() && this.mSelectedPosition >= 0) {
            return this.dispatchLongPress(this.getChildAt(this.mSelectedPosition - this.mFirstPosition), this.mSelectedPosition, this.mSelectedRowId, f, f2, bl);
        }
        return false;
    }

    private void updateSelectedItemMetadata() {
        View view;
        View view2 = this.mSelectedChild;
        this.mSelectedChild = view = this.getChildAt(this.mSelectedPosition - this.mFirstPosition);
        if (view == null) {
            return;
        }
        view.setSelected(true);
        view.setFocusable(true);
        if (this.hasFocus()) {
            view.requestFocus();
        }
        if (view2 != null && view2 != view) {
            view2.setSelected(false);
            view2.setFocusable(false);
        }
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override
    protected int computeHorizontalScrollExtent() {
        return 1;
    }

    @Override
    protected int computeHorizontalScrollOffset() {
        return this.mSelectedPosition;
    }

    @Override
    protected int computeHorizontalScrollRange() {
        return this.mItemCount;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return keyEvent.dispatch(this, null, null);
    }

    @Override
    protected void dispatchSetPressed(boolean bl) {
        View view = this.mSelectedChild;
        if (view != null) {
            view.setPressed(bl);
        }
    }

    @Override
    public void dispatchSetSelected(boolean bl) {
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return Gallery.class.getName();
    }

    @Override
    protected int getChildDrawingOrder(int n, int n2) {
        int n3 = this.mSelectedPosition - this.mFirstPosition;
        if (n3 < 0) {
            return n2;
        }
        if (n2 == n - 1) {
            return n3;
        }
        if (n2 >= n3) {
            return n2 + 1;
        }
        return n2;
    }

    @Override
    int getChildHeight(View view) {
        return view.getMeasuredHeight();
    }

    @Override
    protected boolean getChildStaticTransformation(View view, Transformation transformation) {
        transformation.clear();
        float f = view == this.mSelectedChild ? 1.0f : this.mUnselectedAlpha;
        transformation.setAlpha(f);
        return true;
    }

    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return this.mContextMenuInfo;
    }

    int getLimitedMotionScrollAmount(boolean bl, int n) {
        int n2 = bl != this.mIsRtl ? this.mItemCount - 1 : 0;
        View view = this.getChildAt(n2 - this.mFirstPosition);
        if (view == null) {
            return n;
        }
        int n3 = Gallery.getCenterOfView(view);
        n2 = this.getCenterOfGallery();
        if (bl ? n3 <= n2 : n3 >= n2) {
            return 0;
        }
        n = bl ? Math.max(n2, n) : Math.min(n2 -= n3, n);
        return n;
    }

    @Override
    void layout(int n, boolean bl) {
        this.mIsRtl = this.isLayoutRtl();
        int n2 = this.mSpinnerPadding.left;
        int n3 = this.mRight;
        n = this.mLeft;
        int n4 = this.mSpinnerPadding.left;
        int n5 = this.mSpinnerPadding.right;
        if (this.mDataChanged) {
            this.handleDataChanged();
        }
        if (this.mItemCount == 0) {
            this.resetList();
            return;
        }
        if (this.mNextSelectedPosition >= 0) {
            this.setSelectedPositionInt(this.mNextSelectedPosition);
        }
        this.recycleAllViews();
        this.detachAllViewsFromParent();
        this.mRightMost = 0;
        this.mLeftMost = 0;
        this.mFirstPosition = this.mSelectedPosition;
        View view = this.makeAndAddView(this.mSelectedPosition, 0, 0, true);
        view.offsetLeftAndRight((n3 - n - n4 - n5) / 2 + n2 - view.getWidth() / 2 + this.mSelectedCenterOffset);
        this.fillToGalleryRight();
        this.fillToGalleryLeft();
        this.mRecycler.clear();
        this.invalidate();
        this.checkSelectionChanged();
        this.mDataChanged = false;
        this.mNeedSync = false;
        this.setNextSelectedPositionInt(this.mSelectedPosition);
        this.updateSelectedItemMetadata();
    }

    @UnsupportedAppUsage
    boolean moveDirection(int n) {
        if (this.isLayoutRtl()) {
            n = -n;
        }
        n = this.mSelectedPosition + n;
        if (this.mItemCount > 0 && n >= 0 && n < this.mItemCount) {
            this.scrollToChild(n - this.mFirstPosition);
            return true;
        }
        return false;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mGestureDetector == null) {
            this.mGestureDetector = new GestureDetector(this.getContext(), this);
            this.mGestureDetector.setIsLongpressEnabled(true);
        }
    }

    void onCancel() {
        this.onUp();
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        this.mFlingRunnable.stop(false);
        int n = this.mDownTouchPosition = this.pointToPosition((int)motionEvent.getX(), (int)motionEvent.getY());
        if (n >= 0) {
            this.mDownTouchView = this.getChildAt(n - this.mFirstPosition);
            this.mDownTouchView.setPressed(true);
        }
        this.mIsFirstScroll = true;
        return true;
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (!this.mShouldCallbackDuringFling) {
            this.removeCallbacks(this.mDisableSuppressSelectionChangedRunnable);
            if (!this.mSuppressSelectionChanged) {
                this.mSuppressSelectionChanged = true;
            }
        }
        this.mFlingRunnable.startUsingVelocity((int)(-f));
        return true;
    }

    @Override
    protected void onFocusChanged(boolean bl, int n, Rect object) {
        super.onFocusChanged(bl, n, (Rect)object);
        if (bl && (object = this.mSelectedChild) != null) {
            ((View)object).requestFocus(n);
            this.mSelectedChild.setSelected(true);
        }
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        boolean bl = this.mItemCount > 1;
        accessibilityNodeInfo.setScrollable(bl);
        if (this.isEnabled()) {
            if (this.mItemCount > 0 && this.mSelectedPosition < this.mItemCount - 1) {
                accessibilityNodeInfo.addAction(4096);
            }
            if (this.isEnabled() && this.mItemCount > 0 && this.mSelectedPosition > 0) {
                accessibilityNodeInfo.addAction(8192);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (n != 66) {
            switch (n) {
                default: {
                    return super.onKeyDown(n, keyEvent);
                }
                case 22: {
                    if (!this.moveDirection(1)) return super.onKeyDown(n, keyEvent);
                    this.playSoundEffect(3);
                    return true;
                }
                case 21: {
                    if (!this.moveDirection(-1)) return super.onKeyDown(n, keyEvent);
                    this.playSoundEffect(1);
                    return true;
                }
                case 23: 
            }
        }
        this.mReceivedInvokeKeyDown = true;
        return super.onKeyDown(n, keyEvent);
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        if (KeyEvent.isConfirmKey(n)) {
            if (this.mReceivedInvokeKeyDown && this.mItemCount > 0) {
                this.dispatchPress(this.mSelectedChild);
                this.postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        Gallery.this.dispatchUnpress();
                    }
                }, ViewConfiguration.getPressedStateDuration());
                this.performItemClick(this.getChildAt(this.mSelectedPosition - this.mFirstPosition), this.mSelectedPosition, this.mAdapter.getItemId(this.mSelectedPosition));
            }
            this.mReceivedInvokeKeyDown = false;
            return true;
        }
        return super.onKeyUp(n, keyEvent);
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        this.mInLayout = true;
        this.layout(0, false);
        this.mInLayout = false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        if (this.mDownTouchPosition < 0) {
            return;
        }
        this.performHapticFeedback(0);
        long l = this.getItemIdAtPosition(this.mDownTouchPosition);
        this.dispatchLongPress(this.mDownTouchView, this.mDownTouchPosition, l, motionEvent.getX(), motionEvent.getY(), true);
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        this.mParent.requestDisallowInterceptTouchEvent(true);
        if (!this.mShouldCallbackDuringFling) {
            if (this.mIsFirstScroll) {
                if (!this.mSuppressSelectionChanged) {
                    this.mSuppressSelectionChanged = true;
                }
                this.postDelayed(this.mDisableSuppressSelectionChangedRunnable, 250L);
            }
        } else if (this.mSuppressSelectionChanged) {
            this.mSuppressSelectionChanged = false;
        }
        this.trackMotionScroll((int)f * -1);
        this.mIsFirstScroll = false;
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        int n = this.mDownTouchPosition;
        if (n >= 0) {
            this.scrollToChild(n - this.mFirstPosition);
            if (this.mShouldCallbackOnUnselectedItemClick || this.mDownTouchPosition == this.mSelectedPosition) {
                this.performItemClick(this.mDownTouchView, this.mDownTouchPosition, this.mAdapter.getItemId(this.mDownTouchPosition));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean bl = this.mGestureDetector.onTouchEvent(motionEvent);
        int n = motionEvent.getAction();
        if (n == 1) {
            this.onUp();
        } else if (n == 3) {
            this.onCancel();
        }
        return bl;
    }

    void onUp() {
        if (this.mFlingRunnable.mScroller.isFinished()) {
            this.scrollIntoSlots();
        }
        this.dispatchUnpress();
    }

    @Override
    public boolean performAccessibilityActionInternal(int n, Bundle bundle) {
        if (super.performAccessibilityActionInternal(n, bundle)) {
            return true;
        }
        if (n != 4096) {
            if (n != 8192) {
                return false;
            }
            if (this.isEnabled() && this.mItemCount > 0 && this.mSelectedPosition > 0) {
                return this.scrollToChild(this.mSelectedPosition - this.mFirstPosition - 1);
            }
            return false;
        }
        if (this.isEnabled() && this.mItemCount > 0 && this.mSelectedPosition < this.mItemCount - 1) {
            return this.scrollToChild(this.mSelectedPosition - this.mFirstPosition + 1);
        }
        return false;
    }

    @Override
    void selectionChanged() {
        if (!this.mSuppressSelectionChanged) {
            super.selectionChanged();
        }
    }

    public void setAnimationDuration(int n) {
        this.mAnimationDuration = n;
    }

    public void setCallbackDuringFling(boolean bl) {
        this.mShouldCallbackDuringFling = bl;
    }

    public void setCallbackOnUnselectedItemClick(boolean bl) {
        this.mShouldCallbackOnUnselectedItemClick = bl;
    }

    public void setGravity(int n) {
        if (this.mGravity != n) {
            this.mGravity = n;
            this.requestLayout();
        }
    }

    @Override
    void setSelectedPositionInt(int n) {
        super.setSelectedPositionInt(n);
        this.updateSelectedItemMetadata();
    }

    public void setSpacing(int n) {
        this.mSpacing = n;
    }

    public void setUnselectedAlpha(float f) {
        this.mUnselectedAlpha = f;
    }

    @Override
    public boolean showContextMenu() {
        return this.showContextMenuInternal(0.0f, 0.0f, false);
    }

    @Override
    public boolean showContextMenu(float f, float f2) {
        return this.showContextMenuInternal(f, f2, true);
    }

    @Override
    public boolean showContextMenuForChild(View view) {
        if (this.isShowingContextMenuWithCoords()) {
            return false;
        }
        return this.showContextMenuForChildInternal(view, 0.0f, 0.0f, false);
    }

    @Override
    public boolean showContextMenuForChild(View view, float f, float f2) {
        return this.showContextMenuForChildInternal(view, f, f2, true);
    }

    @UnsupportedAppUsage
    void trackMotionScroll(int n) {
        if (this.getChildCount() == 0) {
            return;
        }
        boolean bl = n < 0;
        int n2 = this.getLimitedMotionScrollAmount(bl, n);
        if (n2 != n) {
            this.mFlingRunnable.endFling(false);
            this.onFinishedMovement();
        }
        this.offsetChildrenLeftAndRight(n2);
        this.detachOffScreenChildren(bl);
        if (bl) {
            this.fillToGalleryRight();
        } else {
            this.fillToGalleryLeft();
        }
        this.mRecycler.clear();
        this.setSelectionToCenterChild();
        View view = this.mSelectedChild;
        if (view != null) {
            this.mSelectedCenterOffset = view.getLeft() + view.getWidth() / 2 - this.getWidth() / 2;
        }
        this.onScrollChanged(0, 0, 0, 0);
        this.invalidate();
    }

    private class FlingRunnable
    implements Runnable {
        private int mLastFlingX;
        private Scroller mScroller;

        public FlingRunnable() {
            this.mScroller = new Scroller(Gallery.this.getContext());
        }

        private void endFling(boolean bl) {
            this.mScroller.forceFinished(true);
            if (bl) {
                Gallery.this.scrollIntoSlots();
            }
        }

        private void startCommon() {
            Gallery.this.removeCallbacks(this);
        }

        @Override
        public void run() {
            int n;
            if (Gallery.this.mItemCount == 0) {
                this.endFling(true);
                return;
            }
            Gallery.this.mShouldStopFling = false;
            Object object = this.mScroller;
            boolean bl = ((Scroller)object).computeScrollOffset();
            int n2 = ((Scroller)object).getCurrX();
            int n3 = this.mLastFlingX - n2;
            if (n3 > 0) {
                object = Gallery.this;
                n = ((Gallery)object).mIsRtl ? Gallery.this.mFirstPosition + Gallery.this.getChildCount() - 1 : Gallery.this.mFirstPosition;
                ((Gallery)object).mDownTouchPosition = n;
                n = Math.min(Gallery.this.getWidth() - Gallery.this.mPaddingLeft - Gallery.this.mPaddingRight - 1, n3);
            } else {
                Gallery.this.getChildCount();
                object = Gallery.this;
                n = ((Gallery)object).mIsRtl ? Gallery.this.mFirstPosition : Gallery.this.mFirstPosition + Gallery.this.getChildCount() - 1;
                ((Gallery)object).mDownTouchPosition = n;
                n = Math.max(-(Gallery.this.getWidth() - Gallery.this.mPaddingRight - Gallery.this.mPaddingLeft - 1), n3);
            }
            Gallery.this.trackMotionScroll(n);
            if (bl && !Gallery.this.mShouldStopFling) {
                this.mLastFlingX = n2;
                Gallery.this.post(this);
            } else {
                this.endFling(true);
            }
        }

        public void startUsingDistance(int n) {
            if (n == 0) {
                return;
            }
            this.startCommon();
            this.mLastFlingX = 0;
            this.mScroller.startScroll(0, 0, -n, 0, Gallery.this.mAnimationDuration);
            Gallery.this.post(this);
        }

        @UnsupportedAppUsage
        public void startUsingVelocity(int n) {
            if (n == 0) {
                return;
            }
            this.startCommon();
            int n2 = n < 0 ? Integer.MAX_VALUE : 0;
            this.mLastFlingX = n2;
            this.mScroller.fling(n2, 0, n, 0, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
            Gallery.this.post(this);
        }

        public void stop(boolean bl) {
            Gallery.this.removeCallbacks(this);
            this.endFling(bl);
        }
    }

    public static class LayoutParams
    extends ViewGroup.LayoutParams {
        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

}

