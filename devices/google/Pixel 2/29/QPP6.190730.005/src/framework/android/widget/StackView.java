/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.MaskFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.TableMaskFilter;
import android.graphics.Xfermode;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.RemotableViewMethod;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.LinearInterpolator;
import android.widget.Adapter;
import android.widget.AdapterViewAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.lang.ref.WeakReference;
import java.util.HashMap;

@RemoteViews.RemoteView
public class StackView
extends AdapterViewAnimator {
    private static final int DEFAULT_ANIMATION_DURATION = 400;
    private static final int FRAME_PADDING = 4;
    private static final int GESTURE_NONE = 0;
    private static final int GESTURE_SLIDE_DOWN = 2;
    private static final int GESTURE_SLIDE_UP = 1;
    private static final int INVALID_POINTER = -1;
    private static final int ITEMS_SLIDE_DOWN = 1;
    private static final int ITEMS_SLIDE_UP = 0;
    private static final int MINIMUM_ANIMATION_DURATION = 50;
    private static final int MIN_TIME_BETWEEN_INTERACTION_AND_AUTOADVANCE = 5000;
    private static final long MIN_TIME_BETWEEN_SCROLLS = 100L;
    private static final int NUM_ACTIVE_VIEWS = 5;
    private static final float PERSPECTIVE_SCALE_FACTOR = 0.0f;
    private static final float PERSPECTIVE_SHIFT_FACTOR_X = 0.1f;
    private static final float PERSPECTIVE_SHIFT_FACTOR_Y = 0.1f;
    private static final float SLIDE_UP_RATIO = 0.7f;
    private static final int STACK_RELAYOUT_DURATION = 100;
    private static final float SWIPE_THRESHOLD_RATIO = 0.2f;
    private static HolographicHelper sHolographicHelper;
    private final String TAG;
    private int mActivePointerId;
    private int mClickColor;
    private ImageView mClickFeedback;
    private boolean mClickFeedbackIsValid = false;
    private boolean mFirstLayoutHappened = false;
    private int mFramePadding;
    private ImageView mHighlight;
    private float mInitialX;
    private float mInitialY;
    private long mLastInteractionTime = 0L;
    private long mLastScrollTime;
    private int mMaximumVelocity;
    private float mNewPerspectiveShiftX;
    private float mNewPerspectiveShiftY;
    private float mPerspectiveShiftX;
    private float mPerspectiveShiftY;
    private int mResOutColor;
    private int mSlideAmount;
    private int mStackMode;
    private StackSlider mStackSlider;
    private int mSwipeGestureType = 0;
    private int mSwipeThreshold;
    private final Rect mTouchRect = new Rect();
    private int mTouchSlop;
    private boolean mTransitionIsSetup = false;
    private VelocityTracker mVelocityTracker;
    private int mYVelocity = 0;
    private final Rect stackInvalidateRect = new Rect();

    public StackView(Context context) {
        this(context, null);
    }

    public StackView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843838);
    }

    public StackView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public StackView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.TAG = "StackView";
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.StackView, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.StackView, attributeSet, typedArray, n, n2);
        this.mResOutColor = typedArray.getColor(1, 0);
        this.mClickColor = typedArray.getColor(0, 0);
        typedArray.recycle();
        this.initStackView();
    }

    private void beginGestureIfNeeded(float f) {
        if ((int)Math.abs(f) > this.mTouchSlop && this.mSwipeGestureType == 0) {
            boolean bl = true;
            int n = f < 0.0f ? 1 : 2;
            this.cancelLongPress();
            this.requestDisallowInterceptTouchEvent(true);
            if (this.mAdapter == null) {
                return;
            }
            int n2 = this.getCount();
            int n3 = this.mStackMode == 0 ? (n == 2 ? 0 : 1) : (n == 2 ? 1 : 0);
            int n4 = this.mLoopViews && n2 == 1 && (this.mStackMode == 0 && n == 1 || this.mStackMode == 1 && n == 2) ? 1 : 0;
            int n5 = this.mLoopViews && n2 == 1 && (this.mStackMode == 1 && n == 1 || this.mStackMode == 0 && n == 2) ? 1 : 0;
            if (this.mLoopViews && n5 == 0 && n4 == 0) {
                n5 = 0;
                n4 = n3;
                n3 = n5;
            } else if (this.mCurrentWindowStartUnbounded + n3 != -1 && n5 == 0) {
                if (this.mCurrentWindowStartUnbounded + n3 != n2 - 1 && n4 == 0) {
                    n5 = 0;
                    n4 = n3;
                    n3 = n5;
                } else {
                    n5 = 2;
                    n4 = n3;
                    n3 = n5;
                }
            } else {
                n4 = n3 + 1;
                n3 = 1;
            }
            if (n3 != 0) {
                bl = false;
            }
            this.mTransitionIsSetup = bl;
            View view = this.getViewAtRelativeIndex(n4);
            if (view == null) {
                return;
            }
            this.setupStackSlider(view, n3);
            this.mSwipeGestureType = n;
            this.cancelHandleClick();
        }
    }

    private void handlePointerUp(MotionEvent object) {
        int n = (int)(((MotionEvent)object).getY(((MotionEvent)object).findPointerIndex(this.mActivePointerId)) - this.mInitialY);
        this.mLastInteractionTime = System.currentTimeMillis();
        object = this.mVelocityTracker;
        if (object != null) {
            ((VelocityTracker)object).computeCurrentVelocity(1000, this.mMaximumVelocity);
            this.mYVelocity = (int)this.mVelocityTracker.getYVelocity(this.mActivePointerId);
        }
        if ((object = this.mVelocityTracker) != null) {
            ((VelocityTracker)object).recycle();
            this.mVelocityTracker = null;
        }
        if (n > this.mSwipeThreshold && this.mSwipeGestureType == 2 && this.mStackSlider.mMode == 0) {
            this.mSwipeGestureType = 0;
            if (this.mStackMode == 0) {
                this.showPrevious();
            } else {
                this.showNext();
            }
            this.mHighlight.bringToFront();
        } else if (n < -this.mSwipeThreshold && this.mSwipeGestureType == 1 && this.mStackSlider.mMode == 0) {
            this.mSwipeGestureType = 0;
            if (this.mStackMode == 0) {
                this.showNext();
            } else {
                this.showPrevious();
            }
            this.mHighlight.bringToFront();
        } else {
            n = this.mSwipeGestureType;
            float f = 1.0f;
            if (n == 1) {
                if (this.mStackMode != 1) {
                    f = 0.0f;
                }
                n = this.mStackMode != 0 && this.mStackSlider.mMode == 0 ? Math.round(this.mStackSlider.getDurationForOffscreenPosition()) : Math.round(this.mStackSlider.getDurationForNeutralPosition());
                object = new StackSlider(this.mStackSlider);
                PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat("YProgress", f);
                object = ObjectAnimator.ofPropertyValuesHolder(object, PropertyValuesHolder.ofFloat("XProgress", 0.0f), propertyValuesHolder);
                ((ObjectAnimator)object).setDuration(n);
                ((ValueAnimator)object).setInterpolator(new LinearInterpolator());
                ((ObjectAnimator)object).start();
            } else if (n == 2) {
                if (this.mStackMode == 1) {
                    f = 0.0f;
                }
                n = this.mStackMode != 1 && this.mStackSlider.mMode == 0 ? Math.round(this.mStackSlider.getDurationForOffscreenPosition()) : Math.round(this.mStackSlider.getDurationForNeutralPosition());
                StackSlider stackSlider = new StackSlider(this.mStackSlider);
                object = PropertyValuesHolder.ofFloat("YProgress", f);
                object = ObjectAnimator.ofPropertyValuesHolder(stackSlider, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat("XProgress", 0.0f), object});
                ((ObjectAnimator)object).setDuration(n);
                ((ObjectAnimator)object).start();
            }
        }
        this.mActivePointerId = -1;
        this.mSwipeGestureType = 0;
    }

    private void initStackView() {
        this.configureViewAnimator(5, 1);
        this.setStaticTransformationsEnabled(true);
        Object object = ViewConfiguration.get(this.getContext());
        this.mTouchSlop = ((ViewConfiguration)object).getScaledTouchSlop();
        this.mMaximumVelocity = ((ViewConfiguration)object).getScaledMaximumFlingVelocity();
        this.mActivePointerId = -1;
        this.mHighlight = new ImageView(this.getContext());
        object = this.mHighlight;
        ((View)object).setLayoutParams(new LayoutParams((View)object));
        object = this.mHighlight;
        this.addViewInLayout((View)object, -1, new LayoutParams((View)object));
        this.mClickFeedback = new ImageView(this.getContext());
        object = this.mClickFeedback;
        ((View)object).setLayoutParams(new LayoutParams((View)object));
        object = this.mClickFeedback;
        this.addViewInLayout((View)object, -1, new LayoutParams((View)object));
        this.mClickFeedback.setVisibility(4);
        this.mStackSlider = new StackSlider();
        if (sHolographicHelper == null) {
            sHolographicHelper = new HolographicHelper(this.mContext);
        }
        this.setClipChildren(false);
        this.setClipToPadding(false);
        this.mStackMode = 1;
        this.mWhichChild = -1;
        this.mFramePadding = (int)Math.ceil(4.0f * this.mContext.getResources().getDisplayMetrics().density);
    }

    private void measureChildren() {
        int n = this.getChildCount();
        int n2 = this.getMeasuredWidth();
        int n3 = this.getMeasuredHeight();
        int n4 = Math.round((float)n2 * 0.9f) - this.mPaddingLeft - this.mPaddingRight;
        int n5 = Math.round((float)n3 * 0.9f) - this.mPaddingTop - this.mPaddingBottom;
        int n6 = 0;
        int n7 = 0;
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            view.measure(View.MeasureSpec.makeMeasureSpec(n4, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(n5, Integer.MIN_VALUE));
            int n8 = n6;
            int n9 = n7;
            if (view != this.mHighlight) {
                n8 = n6;
                n9 = n7;
                if (view != this.mClickFeedback) {
                    n8 = view.getMeasuredWidth();
                    int n10 = view.getMeasuredHeight();
                    int n11 = n6;
                    if (n8 > n6) {
                        n11 = n8;
                    }
                    n8 = n11;
                    n9 = n7;
                    if (n10 > n7) {
                        n9 = n10;
                        n8 = n11;
                    }
                }
            }
            n6 = n8;
            n7 = n9;
        }
        this.mNewPerspectiveShiftX = (float)n2 * 0.1f;
        this.mNewPerspectiveShiftY = (float)n3 * 0.1f;
        if (n6 > 0 && n > 0 && n6 < n4) {
            this.mNewPerspectiveShiftX = n2 - n6;
        }
        if (n7 > 0 && n > 0 && n7 < n5) {
            this.mNewPerspectiveShiftY = n3 - n7;
        }
    }

    private void onLayout() {
        int n;
        if (!this.mFirstLayoutHappened) {
            this.mFirstLayoutHappened = true;
            this.updateChildTransforms();
        }
        if (this.mSlideAmount != (n = Math.round((float)this.getMeasuredHeight() * 0.7f))) {
            this.mSlideAmount = n;
            this.mSwipeThreshold = Math.round((float)n * 0.2f);
        }
        if (Float.compare(this.mPerspectiveShiftY, this.mNewPerspectiveShiftY) != 0 || Float.compare(this.mPerspectiveShiftX, this.mNewPerspectiveShiftX) != 0) {
            this.mPerspectiveShiftY = this.mNewPerspectiveShiftY;
            this.mPerspectiveShiftX = this.mNewPerspectiveShiftX;
            this.updateChildTransforms();
        }
    }

    private void onSecondaryPointerUp(MotionEvent object) {
        int n = ((MotionEvent)object).getActionIndex();
        if (((MotionEvent)object).getPointerId(n) == this.mActivePointerId) {
            int n2 = this.mSwipeGestureType == 2 ? 0 : 1;
            View view = this.getViewAtRelativeIndex(n2);
            if (view == null) {
                return;
            }
            for (n2 = 0; n2 < ((MotionEvent)object).getPointerCount(); ++n2) {
                if (n2 == n) continue;
                float f = ((MotionEvent)object).getX(n2);
                float f2 = ((MotionEvent)object).getY(n2);
                this.mTouchRect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                if (!this.mTouchRect.contains(Math.round(f), Math.round(f2))) continue;
                float f3 = ((MotionEvent)object).getX(n);
                float f4 = ((MotionEvent)object).getY(n);
                this.mInitialY += f2 - f4;
                this.mInitialX += f - f3;
                this.mActivePointerId = ((MotionEvent)object).getPointerId(n2);
                object = this.mVelocityTracker;
                if (object != null) {
                    ((VelocityTracker)object).clear();
                }
                return;
            }
            this.handlePointerUp((MotionEvent)object);
        }
    }

    private void pacedScroll(boolean bl) {
        if (System.currentTimeMillis() - this.mLastScrollTime > 100L) {
            if (bl) {
                this.showPrevious();
            } else {
                this.showNext();
            }
            this.mLastScrollTime = System.currentTimeMillis();
        }
    }

    private void setupStackSlider(View view, int n) {
        this.mStackSlider.setMode(n);
        if (view != null) {
            this.mHighlight.setImageBitmap(sHolographicHelper.createResOutline(view, this.mResOutColor));
            this.mHighlight.setRotation(view.getRotation());
            this.mHighlight.setTranslationY(view.getTranslationY());
            this.mHighlight.setTranslationX(view.getTranslationX());
            this.mHighlight.bringToFront();
            view.bringToFront();
            this.mStackSlider.setView(view);
            view.setVisibility(0);
        }
    }

    private void transformViewAtIndex(int n, View view, boolean bl) {
        float f = this.mPerspectiveShiftY;
        float f2 = this.mPerspectiveShiftX;
        if (this.mStackMode == 1) {
            int n2;
            n = n2 = this.mMaxNumActiveViews - n - 1;
            if (n2 == this.mMaxNumActiveViews - 1) {
                n = n2 - 1;
            }
        } else {
            int n3;
            n = n3 = n - 1;
            if (n3 < 0) {
                n = n3 + 1;
            }
        }
        float f3 = (float)n * 1.0f / (float)(this.mMaxNumActiveViews - 2);
        float f4 = 1.0f - (1.0f - f3) * 0.0f;
        f = f3 * f + (f4 - 1.0f) * ((float)this.getMeasuredHeight() * 0.9f / 2.0f);
        f2 = (1.0f - f3) * f2 + (1.0f - f4) * ((float)this.getMeasuredWidth() * 0.9f / 2.0f);
        if (view instanceof StackFrame) {
            ((StackFrame)view).cancelTransformAnimator();
        }
        if (bl) {
            PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat("translationX", f2);
            Cloneable cloneable = PropertyValuesHolder.ofFloat("translationY", f);
            cloneable = ObjectAnimator.ofPropertyValuesHolder(view, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat("scaleX", f4), PropertyValuesHolder.ofFloat("scaleY", f4), cloneable, propertyValuesHolder});
            ((ObjectAnimator)cloneable).setDuration(100L);
            if (view instanceof StackFrame) {
                ((StackFrame)view).setTransformAnimator((ObjectAnimator)cloneable);
            }
            ((ObjectAnimator)cloneable).start();
        } else {
            view.setTranslationX(f2);
            view.setTranslationY(f);
            view.setScaleX(f4);
            view.setScaleY(f4);
        }
    }

    private void updateChildTransforms() {
        for (int i = 0; i < this.getNumActiveViews(); ++i) {
            View view = this.getViewAtRelativeIndex(i);
            if (view == null) continue;
            this.transformViewAtIndex(i, view, false);
        }
    }

    @Override
    public void advance() {
        long l = System.currentTimeMillis();
        long l2 = this.mLastInteractionTime;
        if (this.mAdapter == null) {
            return;
        }
        if (this.getCount() == 1 && this.mLoopViews) {
            return;
        }
        if (this.mSwipeGestureType == 0 && l - l2 > 5000L) {
            this.showNext();
        }
    }

    @Override
    void applyTransformForChildAtIndex(View view, int n) {
    }

    @Override
    LayoutParams createOrReuseLayoutParams(View object) {
        ViewGroup.LayoutParams layoutParams = ((View)object).getLayoutParams();
        if (layoutParams instanceof LayoutParams) {
            object = (LayoutParams)layoutParams;
            ((LayoutParams)object).setHorizontalOffset(0);
            ((LayoutParams)object).setVerticalOffset(0);
            ((LayoutParams)object).width = 0;
            ((LayoutParams)object).width = 0;
            return object;
        }
        return new LayoutParams((View)object);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        boolean bl = false;
        canvas.getClipBounds(this.stackInvalidateRect);
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            Object object = (LayoutParams)view.getLayoutParams();
            if (((LayoutParams)object).horizontalOffset == 0 && ((LayoutParams)object).verticalOffset == 0 || view.getAlpha() == 0.0f || view.getVisibility() != 0) {
                ((LayoutParams)object).resetInvalidateRect();
            }
            if (((Rect)(object = ((LayoutParams)object).getInvalidateRect())).isEmpty()) continue;
            bl = true;
            this.stackInvalidateRect.union((Rect)object);
        }
        if (bl) {
            canvas.save();
            canvas.clipRectUnion(this.stackInvalidateRect);
            super.dispatchDraw(canvas);
            canvas.restore();
        } else {
            super.dispatchDraw(canvas);
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return StackView.class.getName();
    }

    @Override
    FrameLayout getFrameForChild() {
        StackFrame stackFrame = new StackFrame(this.mContext);
        int n = this.mFramePadding;
        stackFrame.setPadding(n, n, n, n);
        return stackFrame;
    }

    @Override
    void hideTapFeedback(View view) {
        this.mClickFeedback.setVisibility(4);
        this.invalidate();
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) != 0 && motionEvent.getAction() == 8) {
            float f = motionEvent.getAxisValue(9);
            if (f < 0.0f) {
                this.pacedScroll(false);
                return true;
            }
            if (f > 0.0f) {
                this.pacedScroll(true);
                return true;
            }
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        boolean bl = this.getChildCount() > 1;
        accessibilityNodeInfo.setScrollable(bl);
        if (this.isEnabled()) {
            if (this.getDisplayedChild() < this.getChildCount() - 1) {
                accessibilityNodeInfo.addAction(4096);
            }
            if (this.getDisplayedChild() > 0) {
                accessibilityNodeInfo.addAction(8192);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean bl;
        block7 : {
            block4 : {
                block5 : {
                    int n;
                    block6 : {
                        n = motionEvent.getAction() & 255;
                        bl = true;
                        if (n == 0) break block4;
                        if (n == 1) break block5;
                        if (n == 2) break block6;
                        if (n == 3) break block5;
                        if (n == 6) {
                            this.onSecondaryPointerUp(motionEvent);
                        }
                        break block7;
                    }
                    n = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (n == -1) {
                        Log.d("StackView", "Error: No data for our primary pointer.");
                        return false;
                    }
                    this.beginGestureIfNeeded(motionEvent.getY(n) - this.mInitialY);
                    break block7;
                }
                this.mActivePointerId = -1;
                this.mSwipeGestureType = 0;
                break block7;
            }
            if (this.mActivePointerId == -1) {
                this.mInitialX = motionEvent.getX();
                this.mInitialY = motionEvent.getY();
                this.mActivePointerId = motionEvent.getPointerId(0);
            }
        }
        if (this.mSwipeGestureType == 0) {
            bl = false;
        }
        return bl;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        this.checkForAndHandleDataChanged();
        n2 = this.getChildCount();
        for (n = 0; n < n2; ++n) {
            View view = this.getChildAt(n);
            n4 = this.mPaddingLeft;
            int n5 = view.getMeasuredWidth();
            int n6 = this.mPaddingTop;
            n3 = view.getMeasuredHeight();
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            view.layout(this.mPaddingLeft + layoutParams.horizontalOffset, this.mPaddingTop + layoutParams.verticalOffset, layoutParams.horizontalOffset + (n4 + n5), layoutParams.verticalOffset + (n6 + n3));
        }
        this.onLayout();
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getSize(n);
        int n4 = View.MeasureSpec.getSize(n2);
        int n5 = View.MeasureSpec.getMode(n);
        int n6 = View.MeasureSpec.getMode(n2);
        n = this.mReferenceChildWidth;
        n2 = 0;
        boolean bl = n != -1 && this.mReferenceChildHeight != -1;
        if (n6 == 0) {
            n = bl ? Math.round((float)this.mReferenceChildHeight * (1.1111112f + 1.0f)) + this.mPaddingTop + this.mPaddingBottom : 0;
        } else {
            n = n4;
            if (n6 == Integer.MIN_VALUE) {
                if (bl) {
                    n = Math.round((float)this.mReferenceChildHeight * (1.1111112f + 1.0f)) + this.mPaddingTop + this.mPaddingBottom;
                    if (n > n4) {
                        n = n4 | 16777216;
                    }
                } else {
                    n = 0;
                }
            }
        }
        if (n5 == 0) {
            if (bl) {
                n2 = Math.round((float)this.mReferenceChildWidth * (1.0f + 1.1111112f)) + this.mPaddingLeft + this.mPaddingRight;
            }
        } else {
            n2 = n3;
            if (n6 == Integer.MIN_VALUE) {
                if (bl) {
                    n2 = this.mReferenceChildWidth + this.mPaddingLeft + this.mPaddingRight;
                    if (n2 > n3) {
                        n2 = n3 | 16777216;
                    }
                } else {
                    n2 = 0;
                }
            }
        }
        this.setMeasuredDimension(n2, n);
        this.measureChildren();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        int n = motionEvent.getAction();
        int n2 = motionEvent.findPointerIndex(this.mActivePointerId);
        if (n2 == -1) {
            Log.d("StackView", "Error: No data for our primary pointer.");
            return false;
        }
        float f = motionEvent.getY(n2);
        float f2 = motionEvent.getX(n2);
        f -= this.mInitialY;
        float f3 = this.mInitialX;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        n2 = n & 255;
        if (n2 != 1) {
            if (n2 != 2) {
                if (n2 != 3) {
                    if (n2 == 6) {
                        this.onSecondaryPointerUp(motionEvent);
                    }
                } else {
                    this.mActivePointerId = -1;
                    this.mSwipeGestureType = 0;
                }
            } else {
                this.beginGestureIfNeeded(f);
                n2 = this.mSlideAmount;
                f3 = (f2 - f3) / ((float)n2 * 1.0f);
                n = this.mSwipeGestureType;
                if (n == 2) {
                    f2 = f = (f - (float)this.mTouchSlop * 1.0f) / (float)n2 * 1.0f;
                    if (this.mStackMode == 1) {
                        f2 = 1.0f - f;
                    }
                    this.mStackSlider.setYProgress(1.0f - f2);
                    this.mStackSlider.setXProgress(f3);
                    return true;
                }
                if (n == 1) {
                    f2 = f = -((float)this.mTouchSlop * 1.0f + f) / (float)n2 * 1.0f;
                    if (this.mStackMode == 1) {
                        f2 = 1.0f - f;
                    }
                    this.mStackSlider.setYProgress(f2);
                    this.mStackSlider.setXProgress(f3);
                    return true;
                }
            }
        } else {
            this.handlePointerUp(motionEvent);
        }
        return true;
    }

    @Override
    public boolean performAccessibilityActionInternal(int n, Bundle bundle) {
        if (super.performAccessibilityActionInternal(n, bundle)) {
            return true;
        }
        if (!this.isEnabled()) {
            return false;
        }
        if (n != 4096) {
            if (n != 8192) {
                return false;
            }
            if (this.getDisplayedChild() > 0) {
                this.showPrevious();
                return true;
            }
            return false;
        }
        if (this.getDisplayedChild() < this.getChildCount() - 1) {
            this.showNext();
            return true;
        }
        return false;
    }

    @RemotableViewMethod
    @Override
    public void showNext() {
        View view;
        if (this.mSwipeGestureType != 0) {
            return;
        }
        if (!this.mTransitionIsSetup && (view = this.getViewAtRelativeIndex(1)) != null) {
            this.setupStackSlider(view, 0);
            this.mStackSlider.setYProgress(0.0f);
            this.mStackSlider.setXProgress(0.0f);
        }
        super.showNext();
    }

    @Override
    void showOnly(int n, boolean bl) {
        View view;
        super.showOnly(n, bl);
        for (n = this.mCurrentWindowEnd; n >= this.mCurrentWindowStart; --n) {
            int n2 = this.modulo(n, this.getWindowSize());
            if ((AdapterViewAnimator.ViewAndMetaData)this.mViewsMap.get(n2) == null || (view = ((AdapterViewAnimator.ViewAndMetaData)this.mViewsMap.get((Object)Integer.valueOf((int)n2))).view) == null) continue;
            view.bringToFront();
        }
        view = this.mHighlight;
        if (view != null) {
            view.bringToFront();
        }
        this.mTransitionIsSetup = false;
        this.mClickFeedbackIsValid = false;
    }

    @RemotableViewMethod
    @Override
    public void showPrevious() {
        View view;
        if (this.mSwipeGestureType != 0) {
            return;
        }
        if (!this.mTransitionIsSetup && (view = this.getViewAtRelativeIndex(0)) != null) {
            this.setupStackSlider(view, 0);
            this.mStackSlider.setYProgress(1.0f);
            this.mStackSlider.setXProgress(0.0f);
        }
        super.showPrevious();
    }

    @Override
    void showTapFeedback(View view) {
        this.updateClickFeedback();
        this.mClickFeedback.setVisibility(0);
        this.mClickFeedback.bringToFront();
        this.invalidate();
    }

    @Override
    void transformViewForTransition(int n, int n2, final View view, boolean bl) {
        Object object;
        if (!bl) {
            ((StackFrame)view).cancelSliderAnimator();
            view.setRotationX(0.0f);
            object = (LayoutParams)view.getLayoutParams();
            ((LayoutParams)object).setVerticalOffset(0);
            ((LayoutParams)object).setHorizontalOffset(0);
        }
        if (n == -1 && n2 == this.getNumActiveViews() - 1) {
            this.transformViewAtIndex(n2, view, false);
            view.setVisibility(0);
            view.setAlpha(1.0f);
        } else if (n == 0 && n2 == 1) {
            ((StackFrame)view).cancelSliderAnimator();
            view.setVisibility(0);
            n = Math.round(this.mStackSlider.getDurationForNeutralPosition(this.mYVelocity));
            object = new StackSlider(this.mStackSlider);
            ((StackSlider)object).setView(view);
            if (bl) {
                PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat("YProgress", 0.0f);
                object = ObjectAnimator.ofPropertyValuesHolder(object, PropertyValuesHolder.ofFloat("XProgress", 0.0f), propertyValuesHolder);
                ((ObjectAnimator)object).setDuration(n);
                ((ValueAnimator)object).setInterpolator(new LinearInterpolator());
                ((StackFrame)view).setSliderAnimator((ObjectAnimator)object);
                ((ObjectAnimator)object).start();
            } else {
                ((StackSlider)object).setYProgress(0.0f);
                ((StackSlider)object).setXProgress(0.0f);
            }
        } else if (n == 1 && n2 == 0) {
            ((StackFrame)view).cancelSliderAnimator();
            n = Math.round(this.mStackSlider.getDurationForOffscreenPosition(this.mYVelocity));
            object = new StackSlider(this.mStackSlider);
            ((StackSlider)object).setView(view);
            if (bl) {
                PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat("YProgress", 1.0f);
                object = ObjectAnimator.ofPropertyValuesHolder(object, PropertyValuesHolder.ofFloat("XProgress", 0.0f), propertyValuesHolder);
                ((ObjectAnimator)object).setDuration(n);
                ((ValueAnimator)object).setInterpolator(new LinearInterpolator());
                ((StackFrame)view).setSliderAnimator((ObjectAnimator)object);
                ((ObjectAnimator)object).start();
            } else {
                ((StackSlider)object).setYProgress(1.0f);
                ((StackSlider)object).setXProgress(0.0f);
            }
        } else if (n2 == 0) {
            view.setAlpha(0.0f);
            view.setVisibility(4);
        } else if ((n == 0 || n == 1) && n2 > 1) {
            view.setVisibility(0);
            view.setAlpha(1.0f);
            view.setRotationX(0.0f);
            object = (LayoutParams)view.getLayoutParams();
            ((LayoutParams)object).setVerticalOffset(0);
            ((LayoutParams)object).setHorizontalOffset(0);
        } else if (n == -1) {
            view.setAlpha(1.0f);
            view.setVisibility(0);
        } else if (n2 == -1) {
            if (bl) {
                this.postDelayed(new Runnable(){

                    @Override
                    public void run() {
                        view.setAlpha(0.0f);
                    }
                }, 100L);
            } else {
                view.setAlpha(0.0f);
            }
        }
        if (n2 != -1) {
            this.transformViewAtIndex(n2, view, bl);
        }
    }

    void updateClickFeedback() {
        if (!this.mClickFeedbackIsValid) {
            View view = this.getViewAtRelativeIndex(1);
            if (view != null) {
                this.mClickFeedback.setImageBitmap(sHolographicHelper.createClickOutline(view, this.mClickColor));
                this.mClickFeedback.setTranslationX(view.getTranslationX());
                this.mClickFeedback.setTranslationY(view.getTranslationY());
            }
            this.mClickFeedbackIsValid = true;
        }
    }

    private static class HolographicHelper {
        private static final int CLICK_FEEDBACK = 1;
        private static final int RES_OUT = 0;
        private final Paint mBlurPaint = new Paint();
        private final Canvas mCanvas = new Canvas();
        private float mDensity;
        private final Paint mErasePaint = new Paint();
        private final Paint mHolographicPaint = new Paint();
        private final Matrix mIdentityMatrix = new Matrix();
        private BlurMaskFilter mLargeBlurMaskFilter;
        private final Canvas mMaskCanvas = new Canvas();
        private BlurMaskFilter mSmallBlurMaskFilter;
        private final int[] mTmpXY = new int[2];

        HolographicHelper(Context context) {
            this.mDensity = context.getResources().getDisplayMetrics().density;
            this.mHolographicPaint.setFilterBitmap(true);
            this.mHolographicPaint.setMaskFilter(TableMaskFilter.CreateClipTable(0, 30));
            this.mErasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            this.mErasePaint.setFilterBitmap(true);
            this.mSmallBlurMaskFilter = new BlurMaskFilter(this.mDensity * 2.0f, BlurMaskFilter.Blur.NORMAL);
            this.mLargeBlurMaskFilter = new BlurMaskFilter(this.mDensity * 4.0f, BlurMaskFilter.Blur.NORMAL);
        }

        Bitmap createClickOutline(View view, int n) {
            return this.createOutline(view, 1, n);
        }

        Bitmap createOutline(View view, int n, int n2) {
            this.mHolographicPaint.setColor(n2);
            if (n == 0) {
                this.mBlurPaint.setMaskFilter(this.mSmallBlurMaskFilter);
            } else if (n == 1) {
                this.mBlurPaint.setMaskFilter(this.mLargeBlurMaskFilter);
            }
            if (view.getMeasuredWidth() != 0 && view.getMeasuredHeight() != 0) {
                Bitmap bitmap = Bitmap.createBitmap(view.getResources().getDisplayMetrics(), view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                this.mCanvas.setBitmap(bitmap);
                float f = view.getRotationX();
                float f2 = view.getRotation();
                float f3 = view.getTranslationY();
                float f4 = view.getTranslationX();
                view.setRotationX(0.0f);
                view.setRotation(0.0f);
                view.setTranslationY(0.0f);
                view.setTranslationX(0.0f);
                view.draw(this.mCanvas);
                view.setRotationX(f);
                view.setRotation(f2);
                view.setTranslationY(f3);
                view.setTranslationX(f4);
                this.drawOutline(this.mCanvas, bitmap);
                this.mCanvas.setBitmap(null);
                return bitmap;
            }
            return null;
        }

        Bitmap createResOutline(View view, int n) {
            return this.createOutline(view, 0, n);
        }

        void drawOutline(Canvas canvas, Bitmap bitmap) {
            int[] arrn = this.mTmpXY;
            Bitmap bitmap2 = bitmap.extractAlpha(this.mBlurPaint, arrn);
            this.mMaskCanvas.setBitmap(bitmap2);
            this.mMaskCanvas.drawBitmap(bitmap, -arrn[0], -arrn[1], this.mErasePaint);
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            canvas.setMatrix(this.mIdentityMatrix);
            canvas.drawBitmap(bitmap2, arrn[0], arrn[1], this.mHolographicPaint);
            this.mMaskCanvas.setBitmap(null);
            bitmap2.recycle();
        }
    }

    class LayoutParams
    extends ViewGroup.LayoutParams {
        private final Rect globalInvalidateRect;
        int horizontalOffset;
        private final Rect invalidateRect;
        private final RectF invalidateRectf;
        View mView;
        private final Rect parentRect;
        int verticalOffset;

        LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.parentRect = new Rect();
            this.invalidateRect = new Rect();
            this.invalidateRectf = new RectF();
            this.globalInvalidateRect = new Rect();
            this.horizontalOffset = 0;
            this.verticalOffset = 0;
            this.width = 0;
            this.height = 0;
        }

        LayoutParams(View view) {
            super(0, 0);
            this.parentRect = new Rect();
            this.invalidateRect = new Rect();
            this.invalidateRectf = new RectF();
            this.globalInvalidateRect = new Rect();
            this.width = 0;
            this.height = 0;
            this.horizontalOffset = 0;
            this.verticalOffset = 0;
            this.mView = view;
        }

        Rect getInvalidateRect() {
            return this.invalidateRect;
        }

        void invalidateGlobalRegion(View view, Rect object) {
            this.globalInvalidateRect.set((Rect)object);
            this.globalInvalidateRect.union(0, 0, StackView.this.getWidth(), StackView.this.getHeight());
            object = view;
            if (view.getParent() != null && view.getParent() instanceof View) {
                boolean bl = true;
                this.parentRect.set(0, 0, 0, 0);
                while (((View)object).getParent() != null && ((View)object).getParent() instanceof View && !this.parentRect.contains(this.globalInvalidateRect)) {
                    if (!bl) {
                        this.globalInvalidateRect.offset(((View)object).getLeft() - ((View)object).getScrollX(), ((View)object).getTop() - ((View)object).getScrollY());
                    }
                    bl = false;
                    object = (View)((Object)((View)object).getParent());
                    this.parentRect.set(((View)object).getScrollX(), ((View)object).getScrollY(), ((View)object).getWidth() + ((View)object).getScrollX(), ((View)object).getHeight() + ((View)object).getScrollY());
                    ((View)object).invalidate(this.globalInvalidateRect.left, this.globalInvalidateRect.top, this.globalInvalidateRect.right, this.globalInvalidateRect.bottom);
                }
                ((View)object).invalidate(this.globalInvalidateRect.left, this.globalInvalidateRect.top, this.globalInvalidateRect.right, this.globalInvalidateRect.bottom);
                return;
            }
        }

        void resetInvalidateRect() {
            this.invalidateRect.set(0, 0, 0, 0);
        }

        public void setHorizontalOffset(int n) {
            this.setOffsets(n, this.verticalOffset);
        }

        public void setOffsets(int n, int n2) {
            int n3 = n - this.horizontalOffset;
            this.horizontalOffset = n;
            n = n2 - this.verticalOffset;
            this.verticalOffset = n2;
            View view = this.mView;
            if (view != null) {
                view.requestLayout();
                n2 = Math.min(this.mView.getLeft() + n3, this.mView.getLeft());
                n3 = Math.max(this.mView.getRight() + n3, this.mView.getRight());
                int n4 = Math.min(this.mView.getTop() + n, this.mView.getTop());
                n = Math.max(this.mView.getBottom() + n, this.mView.getBottom());
                this.invalidateRectf.set(n2, n4, n3, n);
                float f = -this.invalidateRectf.left;
                float f2 = -this.invalidateRectf.top;
                this.invalidateRectf.offset(f, f2);
                this.mView.getMatrix().mapRect(this.invalidateRectf);
                this.invalidateRectf.offset(-f, -f2);
                this.invalidateRect.set((int)Math.floor(this.invalidateRectf.left), (int)Math.floor(this.invalidateRectf.top), (int)Math.ceil(this.invalidateRectf.right), (int)Math.ceil(this.invalidateRectf.bottom));
                this.invalidateGlobalRegion(this.mView, this.invalidateRect);
            }
        }

        public void setVerticalOffset(int n) {
            this.setOffsets(this.horizontalOffset, n);
        }
    }

    private static class StackFrame
    extends FrameLayout {
        WeakReference<ObjectAnimator> sliderAnimator;
        WeakReference<ObjectAnimator> transformAnimator;

        public StackFrame(Context context) {
            super(context);
        }

        boolean cancelSliderAnimator() {
            WeakReference<ObjectAnimator> weakReference = this.sliderAnimator;
            if (weakReference != null && (weakReference = (ObjectAnimator)weakReference.get()) != null) {
                ((ValueAnimator)((Object)weakReference)).cancel();
                return true;
            }
            return false;
        }

        boolean cancelTransformAnimator() {
            WeakReference<ObjectAnimator> weakReference = this.transformAnimator;
            if (weakReference != null && (weakReference = (ObjectAnimator)weakReference.get()) != null) {
                ((ValueAnimator)((Object)weakReference)).cancel();
                return true;
            }
            return false;
        }

        void setSliderAnimator(ObjectAnimator objectAnimator) {
            this.sliderAnimator = new WeakReference<ObjectAnimator>(objectAnimator);
        }

        void setTransformAnimator(ObjectAnimator objectAnimator) {
            this.transformAnimator = new WeakReference<ObjectAnimator>(objectAnimator);
        }
    }

    private class StackSlider {
        static final int BEGINNING_OF_STACK_MODE = 1;
        static final int END_OF_STACK_MODE = 2;
        static final int NORMAL_MODE = 0;
        int mMode = 0;
        View mView;
        float mXProgress;
        float mYProgress;

        public StackSlider() {
        }

        public StackSlider(StackSlider stackSlider) {
            this.mView = stackSlider.mView;
            this.mYProgress = stackSlider.mYProgress;
            this.mXProgress = stackSlider.mXProgress;
            this.mMode = stackSlider.mMode;
        }

        private float cubic(float f) {
            return (float)(Math.pow(f * 2.0f - 1.0f, 3.0) + 1.0) / 2.0f;
        }

        private float getDuration(boolean bl, float f) {
            Object object = this.mView;
            if (object != null) {
                object = (LayoutParams)((View)object).getLayoutParams();
                float f2 = (float)Math.hypot(((LayoutParams)object).horizontalOffset, ((LayoutParams)object).verticalOffset);
                float f3 = (float)Math.hypot(StackView.this.mSlideAmount, (float)StackView.this.mSlideAmount * 0.4f);
                float f4 = f2;
                if (f2 > f3) {
                    f4 = f3;
                }
                if (f == 0.0f) {
                    f = bl ? 1.0f - f4 / f3 : f4 / f3;
                    return f * 400.0f;
                }
                f = bl ? f4 / Math.abs(f) : (f3 - f4) / Math.abs(f);
                if (!(f < 50.0f) && !(f > 400.0f)) {
                    return f;
                }
                return this.getDuration(bl, 0.0f);
            }
            return 0.0f;
        }

        private float highlightAlphaInterpolator(float f) {
            if (f < 0.4f) {
                return this.cubic(f / 0.4f) * 0.85f;
            }
            return this.cubic(1.0f - (f - 0.4f) / (1.0f - 0.4f)) * 0.85f;
        }

        private float rotationInterpolator(float f) {
            if (f < 0.2f) {
                return 0.0f;
            }
            return (f - 0.2f) / (1.0f - 0.2f);
        }

        private float viewAlphaInterpolator(float f) {
            if (f > 0.3f) {
                return (f - 0.3f) / (1.0f - 0.3f);
            }
            return 0.0f;
        }

        float getDurationForNeutralPosition() {
            return this.getDuration(false, 0.0f);
        }

        float getDurationForNeutralPosition(float f) {
            return this.getDuration(false, f);
        }

        float getDurationForOffscreenPosition() {
            return this.getDuration(true, 0.0f);
        }

        float getDurationForOffscreenPosition(float f) {
            return this.getDuration(true, f);
        }

        public float getXProgress() {
            return this.mXProgress;
        }

        public float getYProgress() {
            return this.mYProgress;
        }

        void setMode(int n) {
            this.mMode = n;
        }

        void setView(View view) {
            this.mView = view;
        }

        public void setXProgress(float f) {
            this.mXProgress = f = Math.max(-2.0f, Math.min(2.0f, f));
            Object object = this.mView;
            if (object == null) {
                return;
            }
            object = (LayoutParams)((View)object).getLayoutParams();
            LayoutParams layoutParams = (LayoutParams)StackView.this.mHighlight.getLayoutParams();
            ((LayoutParams)object).setHorizontalOffset(Math.round((float)StackView.this.mSlideAmount * (f *= 0.2f)));
            layoutParams.setHorizontalOffset(Math.round((float)StackView.this.mSlideAmount * f));
        }

        public void setYProgress(float f) {
            int n;
            this.mYProgress = f = Math.max(0.0f, Math.min(1.0f, f));
            Object object = this.mView;
            if (object == null) {
                return;
            }
            LayoutParams layoutParams = (LayoutParams)((View)object).getLayoutParams();
            object = (LayoutParams)StackView.this.mHighlight.getLayoutParams();
            int n2 = StackView.this.mStackMode == 0 ? 1 : -1;
            if (Float.compare(0.0f, this.mYProgress) != 0 && Float.compare(1.0f, this.mYProgress) != 0) {
                if (this.mView.getLayerType() == 0) {
                    this.mView.setLayerType(2, null);
                }
            } else if (this.mView.getLayerType() != 0) {
                this.mView.setLayerType(0, null);
            }
            if ((n = this.mMode) != 0) {
                if (n != 1) {
                    if (n == 2) {
                        layoutParams.setVerticalOffset(Math.round((float)(-n2) * (f *= 0.2f) * (float)StackView.this.mSlideAmount));
                        ((LayoutParams)object).setVerticalOffset(Math.round((float)(-n2) * f * (float)StackView.this.mSlideAmount));
                        StackView.this.mHighlight.setAlpha(this.highlightAlphaInterpolator(f));
                    }
                } else {
                    f = (1.0f - f) * 0.2f;
                    layoutParams.setVerticalOffset(Math.round((float)n2 * f * (float)StackView.this.mSlideAmount));
                    ((LayoutParams)object).setVerticalOffset(Math.round((float)n2 * f * (float)StackView.this.mSlideAmount));
                    StackView.this.mHighlight.setAlpha(this.highlightAlphaInterpolator(f));
                }
            } else {
                layoutParams.setVerticalOffset(Math.round(-f * (float)n2 * (float)StackView.this.mSlideAmount));
                ((LayoutParams)object).setVerticalOffset(Math.round(-f * (float)n2 * (float)StackView.this.mSlideAmount));
                StackView.this.mHighlight.setAlpha(this.highlightAlphaInterpolator(f));
                float f2 = this.viewAlphaInterpolator(1.0f - f);
                if (this.mView.getAlpha() == 0.0f && f2 != 0.0f && this.mView.getVisibility() != 0) {
                    this.mView.setVisibility(0);
                } else if (f2 == 0.0f && this.mView.getAlpha() != 0.0f && this.mView.getVisibility() == 0) {
                    this.mView.setVisibility(4);
                }
                this.mView.setAlpha(f2);
                this.mView.setRotationX((float)n2 * 90.0f * this.rotationInterpolator(f));
                StackView.this.mHighlight.setRotationX((float)n2 * 90.0f * this.rotationInterpolator(f));
            }
        }
    }

}

