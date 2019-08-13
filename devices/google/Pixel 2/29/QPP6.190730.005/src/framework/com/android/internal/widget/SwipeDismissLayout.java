/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ReceiverCallNotAllowedException;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import com.android.internal.R;
import com.android.internal.widget._$$Lambda$SwipeDismissLayout$1$NDXsqpv65OVP2OutTHt_hDxJywg;

public class SwipeDismissLayout
extends FrameLayout {
    private static final float MAX_DIST_THRESHOLD = 0.33f;
    private static final float MIN_DIST_THRESHOLD = 0.1f;
    private static final String TAG = "SwipeDismissLayout";
    private int mActiveTouchId;
    private boolean mActivityTranslucencyConverted = false;
    private boolean mBlockGesture = false;
    private boolean mDiscardIntercept;
    private final DismissAnimator mDismissAnimator = new DismissAnimator();
    private boolean mDismissable = true;
    private boolean mDismissed;
    private OnDismissedListener mDismissedListener;
    private float mDownX;
    private float mDownY;
    private boolean mIsWindowNativelyTranslucent;
    private float mLastX;
    private int mMinFlingVelocity;
    private OnSwipeProgressChangedListener mProgressListener;
    private IntentFilter mScreenOffFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
    private BroadcastReceiver mScreenOffReceiver;
    private int mSlop;
    private boolean mSwiping;
    private VelocityTracker mVelocityTracker;

    public SwipeDismissLayout(Context context) {
        super(context);
        this.init(context);
    }

    public SwipeDismissLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context);
    }

    public SwipeDismissLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context);
    }

    private void checkGesture(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            this.mBlockGesture = this.mDismissAnimator.isAnimating();
        }
    }

    private void dismiss() {
        OnDismissedListener onDismissedListener = this.mDismissedListener;
        if (onDismissedListener != null) {
            onDismissedListener.onDismissed(this);
        }
    }

    private Activity findActivity() {
        Context context = this.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    private void init(Context object) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)object);
        this.mSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        object = ((Context)object).getTheme().obtainStyledAttributes(R.styleable.Theme);
        this.mIsWindowNativelyTranslucent = ((TypedArray)object).getBoolean(5, false);
        ((TypedArray)object).recycle();
    }

    private float progressToAlpha(float f) {
        return 1.0f - f * f * f;
    }

    private void resetMembers() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
        }
        this.mVelocityTracker = null;
        this.mDownX = 0.0f;
        this.mLastX = -2.14748365E9f;
        this.mDownY = 0.0f;
        this.mSwiping = false;
        this.mDismissed = false;
        this.mDiscardIntercept = false;
    }

    private void setProgress(float f) {
        OnSwipeProgressChangedListener onSwipeProgressChangedListener = this.mProgressListener;
        if (onSwipeProgressChangedListener != null && f >= 0.0f) {
            onSwipeProgressChangedListener.onSwipeProgressChanged(this, this.progressToAlpha(f / (float)this.getWidth()), f);
        }
    }

    private void updateDismiss(MotionEvent motionEvent) {
        float f = motionEvent.getRawX() - this.mDownX;
        this.mVelocityTracker.computeCurrentVelocity(1000);
        float f2 = this.mVelocityTracker.getXVelocity();
        if (this.mLastX == -2.14748365E9f) {
            f2 = f / (float)((motionEvent.getEventTime() - motionEvent.getDownTime()) / 1000L);
        }
        if (!this.mDismissed && (f > (float)this.getWidth() * Math.max(Math.min(-0.23000002f * f2 / (float)this.mMinFlingVelocity + 0.33f, 0.33f), 0.1f) && motionEvent.getRawX() >= this.mLastX || f2 >= (float)this.mMinFlingVelocity)) {
            this.mDismissed = true;
        }
        if (this.mDismissed && this.mSwiping && f2 < (float)(-this.mMinFlingVelocity)) {
            this.mDismissed = false;
        }
    }

    private void updateSwiping(MotionEvent object) {
        boolean bl = this.mSwiping;
        if (!this.mSwiping) {
            float f = ((MotionEvent)object).getRawX() - this.mDownX;
            float f2 = ((MotionEvent)object).getRawY() - this.mDownY;
            int n = this.mSlop;
            float f3 = n * n;
            boolean bl2 = false;
            if (f * f + f2 * f2 > f3) {
                boolean bl3 = bl2;
                if (f > (float)(n * 2)) {
                    bl3 = bl2;
                    if (Math.abs(f2) < Math.abs(f)) {
                        bl3 = true;
                    }
                }
                this.mSwiping = bl3;
            } else {
                this.mSwiping = false;
            }
        }
        if (this.mSwiping && !bl && !this.mIsWindowNativelyTranslucent && (object = this.findActivity()) != null) {
            this.mActivityTranslucencyConverted = ((Activity)object).convertToTranslucent(null, null);
        }
    }

    protected boolean canScroll(View view, boolean bl, float f, float f2, float f3) {
        boolean bl2 = view instanceof ViewGroup;
        boolean bl3 = true;
        if (bl2) {
            ViewGroup viewGroup = (ViewGroup)view;
            int n = view.getScrollX();
            int n2 = view.getScrollY();
            for (int i = viewGroup.getChildCount() - 1; i >= 0; --i) {
                View view2 = viewGroup.getChildAt(i);
                if (!(f2 + (float)n >= (float)view2.getLeft()) || !(f2 + (float)n < (float)view2.getRight()) || !(f3 + (float)n2 >= (float)view2.getTop()) || !(f3 + (float)n2 < (float)view2.getBottom()) || !this.canScroll(view2, true, f, f2 + (float)n - (float)view2.getLeft(), f3 + (float)n2 - (float)view2.getTop())) continue;
                return true;
            }
        }
        bl = bl && view.canScrollHorizontally((int)(-f)) ? bl3 : false;
        return bl;
    }

    protected void cancel() {
        Object object;
        if (!this.mIsWindowNativelyTranslucent && (object = this.findActivity()) != null && this.mActivityTranslucencyConverted) {
            ((Activity)object).convertFromTranslucent();
            this.mActivityTranslucencyConverted = false;
        }
        if ((object = this.mProgressListener) != null) {
            object.onSwipeCancelled(this);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            BroadcastReceiver broadcastReceiver;
            this.mScreenOffReceiver = broadcastReceiver = new BroadcastReceiver(){

                public /* synthetic */ void lambda$onReceive$0$SwipeDismissLayout$1() {
                    if (SwipeDismissLayout.this.mDismissed) {
                        SwipeDismissLayout.this.dismiss();
                    } else {
                        SwipeDismissLayout.this.cancel();
                    }
                    SwipeDismissLayout.this.resetMembers();
                }

                @Override
                public void onReceive(Context context, Intent intent) {
                    SwipeDismissLayout.this.post(new _$$Lambda$SwipeDismissLayout$1$NDXsqpv65OVP2OutTHt_hDxJywg(this));
                }
            };
            this.getContext().registerReceiver(this.mScreenOffReceiver, this.mScreenOffFilter);
        }
        catch (ReceiverCallNotAllowedException receiverCallNotAllowedException) {
            this.mScreenOffReceiver = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (this.mScreenOffReceiver != null) {
            this.getContext().unregisterReceiver(this.mScreenOffReceiver);
            this.mScreenOffReceiver = null;
        }
        super.onDetachedFromWindow();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean bl;
        block14 : {
            block11 : {
                block12 : {
                    int n;
                    block13 : {
                        this.checkGesture(motionEvent);
                        boolean bl2 = this.mBlockGesture;
                        bl = true;
                        if (bl2) {
                            return true;
                        }
                        if (!this.mDismissable) {
                            return super.onInterceptTouchEvent(motionEvent);
                        }
                        motionEvent.offsetLocation(motionEvent.getRawX() - motionEvent.getX(), 0.0f);
                        n = motionEvent.getActionMasked();
                        if (n == 0) break block11;
                        if (n == 1) break block12;
                        if (n == 2) break block13;
                        if (n == 3) break block12;
                        if (n != 5) {
                            if (n == 6 && motionEvent.getPointerId(n = motionEvent.getActionIndex()) == this.mActiveTouchId) {
                                n = n == 0 ? 1 : 0;
                                this.mActiveTouchId = motionEvent.getPointerId(n);
                            }
                        } else {
                            this.mActiveTouchId = motionEvent.getPointerId(motionEvent.getActionIndex());
                        }
                        break block14;
                    }
                    if (this.mVelocityTracker != null && !this.mDiscardIntercept) {
                        n = motionEvent.findPointerIndex(this.mActiveTouchId);
                        if (n == -1) {
                            Log.e(TAG, "Invalid pointer index: ignoring.");
                            this.mDiscardIntercept = true;
                        } else {
                            float f = motionEvent.getRawX() - this.mDownX;
                            float f2 = motionEvent.getX(n);
                            float f3 = motionEvent.getY(n);
                            if (f != 0.0f && this.canScroll(this, false, f, f2, f3)) {
                                this.mDiscardIntercept = true;
                            } else {
                                this.updateSwiping(motionEvent);
                            }
                        }
                    }
                    break block14;
                }
                this.resetMembers();
                break block14;
            }
            this.resetMembers();
            this.mDownX = motionEvent.getRawX();
            this.mDownY = motionEvent.getRawY();
            this.mActiveTouchId = motionEvent.getPointerId(0);
            this.mVelocityTracker = VelocityTracker.obtain("int1");
            this.mVelocityTracker.addMovement(motionEvent);
        }
        if (this.mDiscardIntercept || !this.mSwiping) {
            bl = false;
        }
        return bl;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.checkGesture(motionEvent);
        if (this.mBlockGesture) {
            return true;
        }
        if (this.mVelocityTracker != null && this.mDismissable) {
            motionEvent.offsetLocation(motionEvent.getRawX() - motionEvent.getX(), 0.0f);
            int n = motionEvent.getActionMasked();
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        this.cancel();
                        this.resetMembers();
                    }
                } else {
                    this.mVelocityTracker.addMovement(motionEvent);
                    this.mLastX = motionEvent.getRawX();
                    this.updateSwiping(motionEvent);
                    if (this.mSwiping) {
                        this.setProgress(motionEvent.getRawX() - this.mDownX);
                    }
                }
            } else {
                this.updateDismiss(motionEvent);
                if (this.mDismissed) {
                    this.mDismissAnimator.animateDismissal(motionEvent.getRawX() - this.mDownX);
                } else if (this.mSwiping && this.mLastX != -2.14748365E9f) {
                    this.mDismissAnimator.animateRecovery(motionEvent.getRawX() - this.mDownX);
                }
                this.resetMembers();
            }
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setDismissable(boolean bl) {
        if (!bl && this.mDismissable) {
            this.cancel();
            this.resetMembers();
        }
        this.mDismissable = bl;
    }

    public void setOnDismissedListener(OnDismissedListener onDismissedListener) {
        this.mDismissedListener = onDismissedListener;
    }

    public void setOnSwipeProgressChangedListener(OnSwipeProgressChangedListener onSwipeProgressChangedListener) {
        this.mProgressListener = onSwipeProgressChangedListener;
    }

    private class DismissAnimator
    implements ValueAnimator.AnimatorUpdateListener,
    Animator.AnimatorListener {
        private final long DISMISS_DURATION;
        private final TimeInterpolator DISMISS_INTERPOLATOR = new DecelerateInterpolator(1.5f);
        private final ValueAnimator mDismissAnimator = new ValueAnimator();
        private boolean mDismissOnComplete = false;
        private boolean mWasCanceled = false;

        DismissAnimator() {
            this.DISMISS_DURATION = 250L;
            this.mDismissAnimator.addUpdateListener(this);
            this.mDismissAnimator.addListener(this);
        }

        private void animate(float f, float f2, long l, TimeInterpolator timeInterpolator, boolean bl) {
            this.mDismissAnimator.cancel();
            this.mDismissOnComplete = bl;
            this.mDismissAnimator.setFloatValues(f, f2);
            this.mDismissAnimator.setDuration(l);
            this.mDismissAnimator.setInterpolator(timeInterpolator);
            this.mDismissAnimator.start();
        }

        void animateDismissal(float f) {
            this.animate(f / (float)SwipeDismissLayout.this.getWidth(), 1.0f, 250L, this.DISMISS_INTERPOLATOR, true);
        }

        void animateRecovery(float f) {
            this.animate(f / (float)SwipeDismissLayout.this.getWidth(), 0.0f, 250L, this.DISMISS_INTERPOLATOR, false);
        }

        boolean isAnimating() {
            return this.mDismissAnimator.isStarted();
        }

        @Override
        public void onAnimationCancel(Animator animator2) {
            this.mWasCanceled = true;
        }

        @Override
        public void onAnimationEnd(Animator animator2) {
            if (!this.mWasCanceled) {
                if (this.mDismissOnComplete) {
                    SwipeDismissLayout.this.dismiss();
                } else {
                    SwipeDismissLayout.this.cancel();
                }
            }
        }

        @Override
        public void onAnimationRepeat(Animator animator2) {
        }

        @Override
        public void onAnimationStart(Animator animator2) {
            this.mWasCanceled = false;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator object) {
            float f = ((Float)((ValueAnimator)object).getAnimatedValue()).floatValue();
            object = SwipeDismissLayout.this;
            ((SwipeDismissLayout)object).setProgress((float)((View)object).getWidth() * f);
        }
    }

    public static interface OnDismissedListener {
        public void onDismissed(SwipeDismissLayout var1);
    }

    public static interface OnSwipeProgressChangedListener {
        public void onSwipeCancelled(SwipeDismissLayout var1);

        public void onSwipeProgressChanged(SwipeDismissLayout var1, float var2, float var3);
    }

}

