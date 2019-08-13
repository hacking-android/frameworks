/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import com.android.internal.policy.PhoneWindow;
import java.util.ArrayList;

public class DecorCaptionView
extends ViewGroup
implements View.OnTouchListener,
GestureDetector.OnGestureListener {
    private static final String TAG = "DecorCaptionView";
    private View mCaption;
    private boolean mCheckForDragging;
    private View mClickTarget;
    private View mClose;
    private final Rect mCloseRect = new Rect();
    private View mContent;
    private int mDragSlop;
    private boolean mDragging = false;
    private GestureDetector mGestureDetector;
    private View mMaximize;
    private final Rect mMaximizeRect = new Rect();
    private boolean mOverlayWithAppContent = false;
    private PhoneWindow mOwner = null;
    private int mRootScrollY;
    private boolean mShow = false;
    private ArrayList<View> mTouchDispatchList = new ArrayList(2);
    private int mTouchDownX;
    private int mTouchDownY;

    public DecorCaptionView(Context context) {
        super(context);
        this.init(context);
    }

    public DecorCaptionView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context);
    }

    public DecorCaptionView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context);
    }

    private void init(Context context) {
        this.mDragSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mGestureDetector = new GestureDetector(context, this);
    }

    private boolean passedSlop(int n, int n2) {
        boolean bl = Math.abs(n - this.mTouchDownX) > this.mDragSlop || Math.abs(n2 - this.mTouchDownY) > this.mDragSlop;
        return bl;
    }

    private void toggleFreeformWindowingMode() {
        Window.WindowControllerCallback windowControllerCallback = this.mOwner.getWindowControllerCallback();
        if (windowControllerCallback != null) {
            try {
                windowControllerCallback.toggleFreeformWindowingMode();
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Cannot change task workspace.");
            }
        }
    }

    private void updateCaptionVisibility() {
        View view = this.mCaption;
        int n = this.mShow ? 0 : 8;
        view.setVisibility(n);
        this.mCaption.setOnTouchListener(this);
    }

    @Override
    public void addView(View object, int n, ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            if (n < 2 && this.getChildCount() < 2) {
                super.addView((View)object, 0, layoutParams);
                this.mContent = object;
                return;
            }
            throw new IllegalStateException("DecorCaptionView can only handle 1 client view");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("params ");
        ((StringBuilder)object).append(layoutParams);
        ((StringBuilder)object).append(" must subclass MarginLayoutParams");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @Override
    public ArrayList<View> buildTouchDispatchChildList() {
        this.mTouchDispatchList.ensureCapacity(3);
        View view = this.mCaption;
        if (view != null) {
            this.mTouchDispatchList.add(view);
        }
        if ((view = this.mContent) != null) {
            this.mTouchDispatchList.add(view);
        }
        return this.mTouchDispatchList;
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-1, -1);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(this.getContext(), attributeSet);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new ViewGroup.MarginLayoutParams(layoutParams);
    }

    public View getCaption() {
        return this.mCaption;
    }

    public int getCaptionHeight() {
        View view = this.mCaption;
        int n = view != null ? view.getHeight() : 0;
        return n;
    }

    public boolean isCaptionShowing() {
        return this.mShow;
    }

    public void onConfigurationChanged(boolean bl) {
        this.mShow = bl;
        this.updateCaptionVisibility();
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mCaption = this.getChildAt(0);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            int n;
            int n2 = (int)motionEvent.getX();
            if (this.mMaximizeRect.contains(n2, (n = (int)motionEvent.getY()) - this.mRootScrollY)) {
                this.mClickTarget = this.mMaximize;
            }
            if (this.mCloseRect.contains(n2, n - this.mRootScrollY)) {
                this.mClickTarget = this.mClose;
            }
        }
        boolean bl = this.mClickTarget != null;
        return bl;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        View view;
        if (this.mCaption.getVisibility() != 8) {
            view = this.mCaption;
            view.layout(0, 0, view.getMeasuredWidth(), this.mCaption.getMeasuredHeight());
            n = this.mCaption.getBottom() - this.mCaption.getTop();
            this.mMaximize.getHitRect(this.mMaximizeRect);
            this.mClose.getHitRect(this.mCloseRect);
        } else {
            n = 0;
            this.mMaximizeRect.setEmpty();
            this.mCloseRect.setEmpty();
        }
        view = this.mContent;
        if (view != null) {
            if (this.mOverlayWithAppContent) {
                view.layout(0, 0, view.getMeasuredWidth(), this.mContent.getMeasuredHeight());
            } else {
                view.layout(0, n, view.getMeasuredWidth(), this.mContent.getMeasuredHeight() + n);
            }
        }
        this.mOwner.notifyRestrictedCaptionAreaCallback(this.mMaximize.getLeft(), this.mMaximize.getTop(), this.mClose.getRight(), this.mClose.getBottom());
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        if (this.mCaption.getVisibility() != 8) {
            this.measureChildWithMargins(this.mCaption, n, 0, n2, 0);
            n3 = this.mCaption.getMeasuredHeight();
        } else {
            n3 = 0;
        }
        View view = this.mContent;
        if (view != null) {
            if (this.mOverlayWithAppContent) {
                this.measureChildWithMargins(view, n, 0, n2, 0);
            } else {
                this.measureChildWithMargins(view, n, 0, n2, n3);
            }
        }
        this.setMeasuredDimension(View.MeasureSpec.getSize(n), View.MeasureSpec.getSize(n2));
    }

    public void onRootViewScrollYChanged(int n) {
        View view = this.mCaption;
        if (view != null) {
            this.mRootScrollY = n;
            view.setTranslationY(n);
        }
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent object) {
        object = this.mClickTarget;
        if (object == this.mMaximize) {
            this.toggleFreeformWindowingMode();
        } else if (object == this.mClose) {
            this.mOwner.dispatchOnWindowDismissed(true, false);
        }
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean bl;
        block9 : {
            boolean bl2;
            int n;
            int n2;
            int n3;
            block6 : {
                int n4;
                block7 : {
                    block8 : {
                        n2 = (int)motionEvent.getX();
                        n3 = (int)motionEvent.getY();
                        n = motionEvent.getToolType(motionEvent.getActionIndex());
                        bl = false;
                        n = n == 3 ? 1 : 0;
                        bl2 = (motionEvent.getButtonState() & 1) != 0;
                        n4 = motionEvent.getActionMasked();
                        if (n4 == 0) break block6;
                        if (n4 == 1) break block7;
                        if (n4 == 2) break block8;
                        if (n4 == 3) break block7;
                        break block9;
                    }
                    if (!this.mDragging && this.mCheckForDragging && (n != 0 || this.passedSlop(n2, n3))) {
                        this.mCheckForDragging = false;
                        this.mDragging = true;
                        this.startMovingTask(motionEvent.getRawX(), motionEvent.getRawY());
                    }
                    break block9;
                }
                if (this.mDragging) {
                    if (n4 == 1) {
                        this.finishMovingTask();
                    }
                    this.mDragging = false;
                    return this.mCheckForDragging ^ true;
                }
                break block9;
            }
            if (!this.mShow) {
                return false;
            }
            if (n == 0 || bl2) {
                this.mCheckForDragging = true;
                this.mTouchDownX = n2;
                this.mTouchDownY = n3;
            }
        }
        if (this.mDragging || this.mCheckForDragging) {
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mClickTarget != null) {
            this.mGestureDetector.onTouchEvent(motionEvent);
            int n = motionEvent.getAction();
            if (n == 1 || n == 3) {
                this.mClickTarget = null;
            }
            return true;
        }
        return false;
    }

    public void removeContentView() {
        View view = this.mContent;
        if (view != null) {
            this.removeView(view);
            this.mContent = null;
        }
    }

    public void setPhoneWindow(PhoneWindow phoneWindow, boolean bl) {
        this.mOwner = phoneWindow;
        this.mShow = bl;
        this.mOverlayWithAppContent = phoneWindow.isOverlayWithDecorCaptionEnabled();
        if (this.mOverlayWithAppContent) {
            this.mCaption.setBackgroundColor(0);
        }
        this.updateCaptionVisibility();
        this.mOwner.getDecorView().setOutlineProvider(ViewOutlineProvider.BOUNDS);
        this.mMaximize = this.findViewById(16909091);
        this.mClose = this.findViewById(16908818);
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }
}

