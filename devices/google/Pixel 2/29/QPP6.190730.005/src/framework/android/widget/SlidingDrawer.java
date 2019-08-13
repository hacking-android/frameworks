/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.R;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

@Deprecated
public class SlidingDrawer
extends ViewGroup {
    private static final int ANIMATION_FRAME_DURATION = 16;
    private static final int COLLAPSED_FULL_CLOSED = -10002;
    private static final int EXPANDED_FULL_OPEN = -10001;
    private static final float MAXIMUM_ACCELERATION = 2000.0f;
    private static final float MAXIMUM_MAJOR_VELOCITY = 200.0f;
    private static final float MAXIMUM_MINOR_VELOCITY = 150.0f;
    private static final float MAXIMUM_TAP_VELOCITY = 100.0f;
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;
    private static final int TAP_THRESHOLD = 6;
    private static final int VELOCITY_UNITS = 1000;
    private boolean mAllowSingleTap;
    private boolean mAnimateOnClick;
    private float mAnimatedAcceleration;
    private float mAnimatedVelocity;
    private boolean mAnimating;
    private long mAnimationLastTime;
    private float mAnimationPosition;
    private int mBottomOffset;
    private View mContent;
    private final int mContentId;
    private long mCurrentAnimationTime;
    private boolean mExpanded;
    private final Rect mFrame = new Rect();
    private View mHandle;
    private int mHandleHeight;
    private final int mHandleId;
    private int mHandleWidth;
    private final Rect mInvalidate = new Rect();
    private boolean mLocked;
    private final int mMaximumAcceleration;
    private final int mMaximumMajorVelocity;
    private final int mMaximumMinorVelocity;
    private final int mMaximumTapVelocity;
    private OnDrawerCloseListener mOnDrawerCloseListener;
    private OnDrawerOpenListener mOnDrawerOpenListener;
    private OnDrawerScrollListener mOnDrawerScrollListener;
    private final Runnable mSlidingRunnable = new Runnable(){

        @Override
        public void run() {
            SlidingDrawer.this.doAnimation();
        }
    };
    private final int mTapThreshold;
    @UnsupportedAppUsage
    private int mTopOffset;
    @UnsupportedAppUsage
    private int mTouchDelta;
    @UnsupportedAppUsage
    private boolean mTracking;
    @UnsupportedAppUsage
    private VelocityTracker mVelocityTracker;
    private final int mVelocityUnits;
    private boolean mVertical;

    public SlidingDrawer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingDrawer(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public SlidingDrawer(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.SlidingDrawer, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.SlidingDrawer, attributeSet, typedArray, n, n2);
        boolean bl = typedArray.getInt(0, 1) == 1;
        this.mVertical = bl;
        this.mBottomOffset = (int)typedArray.getDimension(1, 0.0f);
        this.mTopOffset = (int)typedArray.getDimension(2, 0.0f);
        this.mAllowSingleTap = typedArray.getBoolean(3, true);
        this.mAnimateOnClick = typedArray.getBoolean(6, true);
        n2 = typedArray.getResourceId(4, 0);
        if (n2 != 0) {
            n = typedArray.getResourceId(5, 0);
            if (n != 0) {
                if (n2 != n) {
                    this.mHandleId = n2;
                    this.mContentId = n;
                    float f = this.getResources().getDisplayMetrics().density;
                    this.mTapThreshold = (int)(6.0f * f + 0.5f);
                    this.mMaximumTapVelocity = (int)(100.0f * f + 0.5f);
                    this.mMaximumMinorVelocity = (int)(150.0f * f + 0.5f);
                    this.mMaximumMajorVelocity = (int)(200.0f * f + 0.5f);
                    this.mMaximumAcceleration = (int)(2000.0f * f + 0.5f);
                    this.mVelocityUnits = (int)(1000.0f * f + 0.5f);
                    typedArray.recycle();
                    this.setAlwaysDrawnWithCacheEnabled(false);
                    return;
                }
                throw new IllegalArgumentException("The content and handle attributes must refer to different children.");
            }
            throw new IllegalArgumentException("The content attribute is required and must refer to a valid child.");
        }
        throw new IllegalArgumentException("The handle attribute is required and must refer to a valid child.");
    }

    private void animateClose(int n, boolean bl) {
        this.prepareTracking(n);
        this.performFling(n, this.mMaximumAcceleration, true, bl);
    }

    private void animateOpen(int n, boolean bl) {
        this.prepareTracking(n);
        this.performFling(n, -this.mMaximumAcceleration, true, bl);
    }

    private void closeDrawer() {
        this.moveHandle(-10002);
        this.mContent.setVisibility(8);
        this.mContent.destroyDrawingCache();
        if (!this.mExpanded) {
            return;
        }
        this.mExpanded = false;
        OnDrawerCloseListener onDrawerCloseListener = this.mOnDrawerCloseListener;
        if (onDrawerCloseListener != null) {
            onDrawerCloseListener.onDrawerClosed();
        }
    }

    private void doAnimation() {
        if (this.mAnimating) {
            this.incrementAnimation();
            float f = this.mAnimationPosition;
            int n = this.mBottomOffset;
            int n2 = this.mVertical ? this.getHeight() : this.getWidth();
            if (f >= (float)(n + n2 - 1)) {
                this.mAnimating = false;
                this.closeDrawer();
            } else {
                f = this.mAnimationPosition;
                if (f < (float)this.mTopOffset) {
                    this.mAnimating = false;
                    this.openDrawer();
                } else {
                    this.moveHandle((int)f);
                    this.mCurrentAnimationTime += 16L;
                    this.postDelayed(this.mSlidingRunnable, 16L);
                }
            }
        }
    }

    private void incrementAnimation() {
        long l = SystemClock.uptimeMillis();
        float f = (float)(l - this.mAnimationLastTime) / 1000.0f;
        float f2 = this.mAnimationPosition;
        float f3 = this.mAnimatedVelocity;
        float f4 = this.mAnimatedAcceleration;
        this.mAnimationPosition = f3 * f + f2 + 0.5f * f4 * f * f;
        this.mAnimatedVelocity = f4 * f + f3;
        this.mAnimationLastTime = l;
    }

    private void moveHandle(int n) {
        View view = this.mHandle;
        if (this.mVertical) {
            if (n == -10001) {
                view.offsetTopAndBottom(this.mTopOffset - view.getTop());
                this.invalidate();
            } else if (n == -10002) {
                view.offsetTopAndBottom(this.mBottomOffset + this.mBottom - this.mTop - this.mHandleHeight - view.getTop());
                this.invalidate();
            } else {
                int n2 = view.getTop();
                int n3 = n - n2;
                int n4 = this.mTopOffset;
                if (n < n4) {
                    n = n4 - n2;
                } else {
                    n = n3;
                    if (n3 > this.mBottomOffset + this.mBottom - this.mTop - this.mHandleHeight - n2) {
                        n = this.mBottomOffset + this.mBottom - this.mTop - this.mHandleHeight - n2;
                    }
                }
                view.offsetTopAndBottom(n);
                Rect rect = this.mFrame;
                Rect rect2 = this.mInvalidate;
                view.getHitRect(rect);
                rect2.set(rect);
                rect2.union(rect.left, rect.top - n, rect.right, rect.bottom - n);
                rect2.union(0, rect.bottom - n, this.getWidth(), rect.bottom - n + this.mContent.getHeight());
                this.invalidate(rect2);
            }
        } else if (n == -10001) {
            view.offsetLeftAndRight(this.mTopOffset - view.getLeft());
            this.invalidate();
        } else if (n == -10002) {
            view.offsetLeftAndRight(this.mBottomOffset + this.mRight - this.mLeft - this.mHandleWidth - view.getLeft());
            this.invalidate();
        } else {
            int n5 = view.getLeft();
            int n6 = n - n5;
            int n7 = this.mTopOffset;
            if (n < n7) {
                n = n7 - n5;
            } else {
                n = n6;
                if (n6 > this.mBottomOffset + this.mRight - this.mLeft - this.mHandleWidth - n5) {
                    n = this.mBottomOffset + this.mRight - this.mLeft - this.mHandleWidth - n5;
                }
            }
            view.offsetLeftAndRight(n);
            Rect rect = this.mFrame;
            Rect rect3 = this.mInvalidate;
            view.getHitRect(rect);
            rect3.set(rect);
            rect3.union(rect.left - n, rect.top, rect.right - n, rect.bottom);
            rect3.union(rect.right - n, 0, rect.right - n + this.mContent.getWidth(), this.getHeight());
            this.invalidate(rect3);
        }
    }

    private void openDrawer() {
        this.moveHandle(-10001);
        this.mContent.setVisibility(0);
        if (this.mExpanded) {
            return;
        }
        this.mExpanded = true;
        OnDrawerOpenListener onDrawerOpenListener = this.mOnDrawerOpenListener;
        if (onDrawerOpenListener != null) {
            onDrawerOpenListener.onDrawerOpened();
        }
    }

    private void performFling(int n, float f, boolean bl, boolean bl2) {
        long l;
        int n2;
        this.mAnimationPosition = n;
        this.mAnimatedVelocity = f;
        if (this.mExpanded) {
            int n3;
            int n4;
            if (!(bl || f > (float)this.mMaximumMajorVelocity || n > (n3 = this.mTopOffset) + (n4 = this.mVertical ? this.mHandleHeight : this.mHandleWidth) && f > (float)(-this.mMaximumMajorVelocity))) {
                this.mAnimatedAcceleration = -this.mMaximumAcceleration;
                if (f > 0.0f) {
                    this.mAnimatedVelocity = 0.0f;
                }
            } else {
                this.mAnimatedAcceleration = this.mMaximumAcceleration;
                if (f < 0.0f) {
                    this.mAnimatedVelocity = 0.0f;
                }
            }
        } else if (!bl && (f > (float)this.mMaximumMajorVelocity || n > (n2 = this.mVertical ? this.getHeight() : this.getWidth()) / 2 && f > (float)(-this.mMaximumMajorVelocity))) {
            this.mAnimatedAcceleration = this.mMaximumAcceleration;
            if (f < 0.0f) {
                this.mAnimatedVelocity = 0.0f;
            }
        } else {
            this.mAnimatedAcceleration = -this.mMaximumAcceleration;
            if (f > 0.0f) {
                this.mAnimatedVelocity = 0.0f;
            }
        }
        this.mAnimationLastTime = l = SystemClock.uptimeMillis();
        this.mCurrentAnimationTime = l + 16L;
        this.mAnimating = true;
        this.removeCallbacks(this.mSlidingRunnable);
        this.postDelayed(this.mSlidingRunnable, 16L);
        this.stopTracking(bl2);
    }

    @UnsupportedAppUsage
    private void prepareContent() {
        if (this.mAnimating) {
            return;
        }
        View view = this.mContent;
        if (view.isLayoutRequested()) {
            if (this.mVertical) {
                int n = this.mHandleHeight;
                int n2 = this.mBottom;
                int n3 = this.mTop;
                int n4 = this.mTopOffset;
                view.measure(View.MeasureSpec.makeMeasureSpec(this.mRight - this.mLeft, 1073741824), View.MeasureSpec.makeMeasureSpec(n2 - n3 - n - n4, 1073741824));
                view.layout(0, this.mTopOffset + n, view.getMeasuredWidth(), this.mTopOffset + n + view.getMeasuredHeight());
            } else {
                int n = this.mHandle.getWidth();
                view.measure(View.MeasureSpec.makeMeasureSpec(this.mRight - this.mLeft - n - this.mTopOffset, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mBottom - this.mTop, 1073741824));
                int n5 = this.mTopOffset;
                view.layout(n + n5, 0, n5 + n + view.getMeasuredWidth(), view.getMeasuredHeight());
            }
        }
        view.getViewTreeObserver().dispatchOnPreDraw();
        if (!view.isHardwareAccelerated()) {
            view.buildDrawingCache();
        }
        view.setVisibility(8);
    }

    @UnsupportedAppUsage
    private void prepareTracking(int n) {
        this.mTracking = true;
        this.mVelocityTracker = VelocityTracker.obtain();
        if (this.mExpanded ^ true) {
            int n2;
            long l;
            this.mAnimatedAcceleration = this.mMaximumAcceleration;
            this.mAnimatedVelocity = this.mMaximumMajorVelocity;
            int n3 = this.mBottomOffset;
            if (this.mVertical) {
                n = this.getHeight();
                n2 = this.mHandleHeight;
            } else {
                n = this.getWidth();
                n2 = this.mHandleWidth;
            }
            this.mAnimationPosition = n3 + (n - n2);
            this.moveHandle((int)this.mAnimationPosition);
            this.mAnimating = true;
            this.removeCallbacks(this.mSlidingRunnable);
            this.mAnimationLastTime = l = SystemClock.uptimeMillis();
            this.mCurrentAnimationTime = 16L + l;
            this.mAnimating = true;
        } else {
            if (this.mAnimating) {
                this.mAnimating = false;
                this.removeCallbacks(this.mSlidingRunnable);
            }
            this.moveHandle(n);
        }
    }

    private void stopTracking(boolean bl) {
        Object object;
        this.mHandle.setPressed(false);
        this.mTracking = false;
        if (bl && (object = this.mOnDrawerScrollListener) != null) {
            object.onScrollEnded();
        }
        if ((object = this.mVelocityTracker) != null) {
            ((VelocityTracker)object).recycle();
            this.mVelocityTracker = null;
        }
    }

    public void animateClose() {
        this.prepareContent();
        OnDrawerScrollListener onDrawerScrollListener = this.mOnDrawerScrollListener;
        if (onDrawerScrollListener != null) {
            onDrawerScrollListener.onScrollStarted();
        }
        int n = this.mVertical ? this.mHandle.getTop() : this.mHandle.getLeft();
        this.animateClose(n, false);
        if (onDrawerScrollListener != null) {
            onDrawerScrollListener.onScrollEnded();
        }
    }

    public void animateOpen() {
        this.prepareContent();
        OnDrawerScrollListener onDrawerScrollListener = this.mOnDrawerScrollListener;
        if (onDrawerScrollListener != null) {
            onDrawerScrollListener.onScrollStarted();
        }
        int n = this.mVertical ? this.mHandle.getTop() : this.mHandle.getLeft();
        this.animateOpen(n, false);
        this.sendAccessibilityEvent(32);
        if (onDrawerScrollListener != null) {
            onDrawerScrollListener.onScrollEnded();
        }
    }

    public void animateToggle() {
        if (!this.mExpanded) {
            this.animateOpen();
        } else {
            this.animateClose();
        }
    }

    public void close() {
        this.closeDrawer();
        this.invalidate();
        this.requestLayout();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        block6 : {
            boolean bl;
            View view;
            long l;
            block5 : {
                l = this.getDrawingTime();
                view = this.mHandle;
                bl = this.mVertical;
                this.drawChild(canvas, view, l);
                if (this.mTracking || this.mAnimating) break block5;
                if (!this.mExpanded) break block6;
                this.drawChild(canvas, this.mContent, l);
                break block6;
            }
            Bitmap bitmap = this.mContent.getDrawingCache();
            float f = 0.0f;
            if (bitmap != null) {
                if (bl) {
                    canvas.drawBitmap(bitmap, 0.0f, view.getBottom(), null);
                } else {
                    canvas.drawBitmap(bitmap, view.getRight(), 0.0f, null);
                }
            } else {
                canvas.save();
                float f2 = bl ? 0.0f : (float)(view.getLeft() - this.mTopOffset);
                if (bl) {
                    f = view.getTop() - this.mTopOffset;
                }
                canvas.translate(f2, f);
                this.drawChild(canvas, this.mContent, l);
                canvas.restore();
            }
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return SlidingDrawer.class.getName();
    }

    public View getContent() {
        return this.mContent;
    }

    public View getHandle() {
        return this.mHandle;
    }

    public boolean isMoving() {
        boolean bl = this.mTracking || this.mAnimating;
        return bl;
    }

    public boolean isOpened() {
        return this.mExpanded;
    }

    public void lock() {
        this.mLocked = true;
    }

    @Override
    protected void onFinishInflate() {
        this.mHandle = this.findViewById(this.mHandleId);
        View view = this.mHandle;
        if (view != null) {
            view.setOnClickListener(new DrawerToggler());
            view = this.mContent = this.findViewById(this.mContentId);
            if (view != null) {
                view.setVisibility(8);
                return;
            }
            throw new IllegalArgumentException("The content attribute is must refer to an existing child.");
        }
        throw new IllegalArgumentException("The handle attribute is must refer to an existing child.");
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mLocked) {
            return false;
        }
        int n = motionEvent.getAction();
        float f = motionEvent.getX();
        float f2 = motionEvent.getY();
        Object object = this.mFrame;
        View view = this.mHandle;
        view.getHitRect((Rect)object);
        if (!this.mTracking && !((Rect)object).contains((int)f, (int)f2)) {
            return false;
        }
        if (n == 0) {
            this.mTracking = true;
            view.setPressed(true);
            this.prepareContent();
            object = this.mOnDrawerScrollListener;
            if (object != null) {
                object.onScrollStarted();
            }
            if (this.mVertical) {
                n = this.mHandle.getTop();
                this.mTouchDelta = (int)f2 - n;
                this.prepareTracking(n);
            } else {
                n = this.mHandle.getLeft();
                this.mTouchDelta = (int)f - n;
                this.prepareTracking(n);
            }
            this.mVelocityTracker.addMovement(motionEvent);
        }
        return true;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        if (this.mTracking) {
            return;
        }
        n = n3 - n;
        n2 = n4 - n2;
        View view = this.mHandle;
        n4 = view.getMeasuredWidth();
        int n5 = view.getMeasuredHeight();
        View view2 = this.mContent;
        if (this.mVertical) {
            n3 = (n - n4) / 2;
            n = this.mExpanded ? this.mTopOffset : n2 - n5 + this.mBottomOffset;
            view2.layout(0, this.mTopOffset + n5, view2.getMeasuredWidth(), this.mTopOffset + n5 + view2.getMeasuredHeight());
            n2 = n;
        } else {
            n = this.mExpanded ? this.mTopOffset : n - n4 + this.mBottomOffset;
            n2 = (n2 - n5) / 2;
            n3 = this.mTopOffset;
            view2.layout(n3 + n4, 0, n3 + n4 + view2.getMeasuredWidth(), view2.getMeasuredHeight());
            n3 = n;
        }
        view.layout(n3, n2, n3 + n4, n2 + n5);
        this.mHandleHeight = view.getHeight();
        this.mHandleWidth = view.getWidth();
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getMode(n);
        int n4 = View.MeasureSpec.getSize(n);
        int n5 = View.MeasureSpec.getMode(n2);
        int n6 = View.MeasureSpec.getSize(n2);
        if (n3 != 0 && n5 != 0) {
            View view = this.mHandle;
            this.measureChild(view, n, n2);
            if (this.mVertical) {
                n2 = view.getMeasuredHeight();
                n = this.mTopOffset;
                this.mContent.measure(View.MeasureSpec.makeMeasureSpec(n4, 1073741824), View.MeasureSpec.makeMeasureSpec(n6 - n2 - n, 1073741824));
            } else {
                n = view.getMeasuredWidth();
                n2 = this.mTopOffset;
                this.mContent.measure(View.MeasureSpec.makeMeasureSpec(n4 - n - n2, 1073741824), View.MeasureSpec.makeMeasureSpec(n6, 1073741824));
            }
            this.setMeasuredDimension(n4, n6);
            return;
        }
        throw new RuntimeException("SlidingDrawer cannot have UNSPECIFIED dimensions");
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean bl;
        boolean bl2;
        block23 : {
            int n;
            float f;
            float f2;
            int n2;
            block24 : {
                block25 : {
                    bl2 = this.mLocked;
                    bl = true;
                    if (bl2) {
                        return true;
                    }
                    if (!this.mTracking) break block23;
                    this.mVelocityTracker.addMovement(motionEvent);
                    n2 = motionEvent.getAction();
                    if (n2 == 1) break block24;
                    if (n2 == 2) break block25;
                    if (n2 == 3) break block24;
                    break block23;
                }
                float f3 = this.mVertical ? motionEvent.getY() : motionEvent.getX();
                this.moveHandle((int)f3 - this.mTouchDelta);
                break block23;
            }
            VelocityTracker velocityTracker = this.mVelocityTracker;
            velocityTracker.computeCurrentVelocity(this.mVelocityUnits);
            float f4 = velocityTracker.getYVelocity();
            float f5 = velocityTracker.getXVelocity();
            bl2 = this.mVertical;
            if (bl2) {
                n2 = f4 < 0.0f ? 1 : 0;
                float f6 = f5;
                if (f5 < 0.0f) {
                    f6 = -f5;
                }
                int n3 = this.mMaximumMinorVelocity;
                f2 = f4;
                f = f6;
                n = n2;
                if (f6 > (float)n3) {
                    f = n3;
                    f2 = f4;
                    n = n2;
                }
            } else {
                n2 = f5 < 0.0f ? 1 : 0;
                float f7 = f4;
                if (f4 < 0.0f) {
                    f7 = -f4;
                }
                int n4 = this.mMaximumMinorVelocity;
                f2 = f7;
                f = f5;
                n = n2;
                if (f7 > (float)n4) {
                    f2 = n4;
                    n = n2;
                    f = f5;
                }
            }
            f = f2 = (float)Math.hypot(f, f2);
            if (n != 0) {
                f = -f2;
            }
            n = this.mHandle.getTop();
            n2 = this.mHandle.getLeft();
            if (Math.abs(f) < (float)this.mMaximumTapVelocity) {
                boolean bl3 = this.mExpanded;
                if (bl2 ? bl3 && n < this.mTapThreshold + this.mTopOffset || !this.mExpanded && n > this.mBottomOffset + this.mBottom - this.mTop - this.mHandleHeight - this.mTapThreshold : bl3 && n2 < this.mTapThreshold + this.mTopOffset || !this.mExpanded && n2 > this.mBottomOffset + this.mRight - this.mLeft - this.mHandleWidth - this.mTapThreshold) {
                    if (this.mAllowSingleTap) {
                        this.playSoundEffect(0);
                        if (this.mExpanded) {
                            if (bl2) {
                                n2 = n;
                            }
                            this.animateClose(n2, true);
                        } else {
                            if (bl2) {
                                n2 = n;
                            }
                            this.animateOpen(n2, true);
                        }
                    } else {
                        if (bl2) {
                            n2 = n;
                        }
                        this.performFling(n2, f, false, true);
                    }
                } else {
                    if (bl2) {
                        n2 = n;
                    }
                    this.performFling(n2, f, false, true);
                }
            } else {
                if (bl2) {
                    n2 = n;
                }
                this.performFling(n2, f, false, true);
            }
        }
        bl2 = bl;
        if (!this.mTracking) {
            bl2 = bl;
            if (!this.mAnimating) {
                bl2 = super.onTouchEvent(motionEvent) ? bl : false;
            }
        }
        return bl2;
    }

    public void open() {
        this.openDrawer();
        this.invalidate();
        this.requestLayout();
        this.sendAccessibilityEvent(32);
    }

    public void setOnDrawerCloseListener(OnDrawerCloseListener onDrawerCloseListener) {
        this.mOnDrawerCloseListener = onDrawerCloseListener;
    }

    public void setOnDrawerOpenListener(OnDrawerOpenListener onDrawerOpenListener) {
        this.mOnDrawerOpenListener = onDrawerOpenListener;
    }

    public void setOnDrawerScrollListener(OnDrawerScrollListener onDrawerScrollListener) {
        this.mOnDrawerScrollListener = onDrawerScrollListener;
    }

    public void toggle() {
        if (!this.mExpanded) {
            this.openDrawer();
        } else {
            this.closeDrawer();
        }
        this.invalidate();
        this.requestLayout();
    }

    public void unlock() {
        this.mLocked = false;
    }

    private class DrawerToggler
    implements View.OnClickListener {
        private DrawerToggler() {
        }

        @Override
        public void onClick(View view) {
            if (SlidingDrawer.this.mLocked) {
                return;
            }
            if (SlidingDrawer.this.mAnimateOnClick) {
                SlidingDrawer.this.animateToggle();
            } else {
                SlidingDrawer.this.toggle();
            }
        }
    }

    public static interface OnDrawerCloseListener {
        public void onDrawerClosed();
    }

    public static interface OnDrawerOpenListener {
        public void onDrawerOpened();
    }

    public static interface OnDrawerScrollListener {
        public void onScrollEnded();

        public void onScrollStarted();
    }

}

