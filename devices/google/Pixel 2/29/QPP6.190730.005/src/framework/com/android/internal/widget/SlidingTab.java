/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.R;

public class SlidingTab
extends ViewGroup {
    private static final int ANIM_DURATION = 250;
    private static final int ANIM_TARGET_TIME = 500;
    private static final boolean DBG = false;
    private static final int HORIZONTAL = 0;
    private static final String LOG_TAG = "SlidingTab";
    private static final float THRESHOLD = 0.6666667f;
    private static final int TRACKING_MARGIN = 50;
    private static final int VERTICAL = 1;
    private static final long VIBRATE_LONG = 40L;
    private static final long VIBRATE_SHORT = 30L;
    private static final AudioAttributes VIBRATION_ATTRIBUTES = new AudioAttributes.Builder().setContentType(4).setUsage(13).build();
    private boolean mAnimating;
    @UnsupportedAppUsage
    private final Animation.AnimationListener mAnimationDoneListener = new Animation.AnimationListener(){

        @Override
        public void onAnimationEnd(Animation animation) {
            SlidingTab.this.onAnimationDone();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }
    };
    private Slider mCurrentSlider;
    private final float mDensity;
    private int mGrabbedState = 0;
    private boolean mHoldLeftOnTransition = true;
    private boolean mHoldRightOnTransition = true;
    @UnsupportedAppUsage
    private final Slider mLeftSlider;
    private OnTriggerListener mOnTriggerListener;
    private final int mOrientation;
    private Slider mOtherSlider;
    @UnsupportedAppUsage
    private final Slider mRightSlider;
    private float mThreshold;
    private final Rect mTmpRect = new Rect();
    private boolean mTracking;
    private boolean mTriggered = false;
    private Vibrator mVibrator;

    public SlidingTab(Context context) {
        this(context, null);
    }

    public SlidingTab(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.SlidingTab);
        this.mOrientation = ((TypedArray)object).getInt(0, 0);
        ((TypedArray)object).recycle();
        this.mDensity = this.getResources().getDisplayMetrics().density;
        this.mLeftSlider = new Slider(this, 17302910, 17302893, 17302924);
        this.mRightSlider = new Slider(this, 17302919, 17302902, 17302924);
    }

    private void cancelGrab() {
        this.mTracking = false;
        this.mTriggered = false;
        this.mOtherSlider.show(true);
        this.mCurrentSlider.reset(false);
        this.mCurrentSlider.hideTarget();
        this.mCurrentSlider = null;
        this.mOtherSlider = null;
        this.setGrabbedState(0);
    }

    private void dispatchTriggerEvent(int n) {
        this.vibrate(40L);
        OnTriggerListener onTriggerListener = this.mOnTriggerListener;
        if (onTriggerListener != null) {
            onTriggerListener.onTrigger(this, n);
        }
    }

    private boolean isHorizontal() {
        boolean bl = this.mOrientation == 0;
        return bl;
    }

    private void log(String string2) {
        Log.d(LOG_TAG, string2);
    }

    private void moveHandle(float f, float f2) {
        ImageView imageView = this.mCurrentSlider.tab;
        TextView textView = this.mCurrentSlider.text;
        if (this.isHorizontal()) {
            int n = (int)f - imageView.getLeft() - imageView.getWidth() / 2;
            imageView.offsetLeftAndRight(n);
            textView.offsetLeftAndRight(n);
        } else {
            int n = (int)f2 - imageView.getTop() - imageView.getHeight() / 2;
            imageView.offsetTopAndBottom(n);
            textView.offsetTopAndBottom(n);
        }
        this.invalidate();
    }

    @UnsupportedAppUsage
    private void onAnimationDone() {
        this.resetView();
        this.mAnimating = false;
    }

    @UnsupportedAppUsage
    private void resetView() {
        this.mLeftSlider.reset(false);
        this.mRightSlider.reset(false);
    }

    private void setGrabbedState(int n) {
        if (n != this.mGrabbedState) {
            this.mGrabbedState = n;
            OnTriggerListener onTriggerListener = this.mOnTriggerListener;
            if (onTriggerListener != null) {
                onTriggerListener.onGrabbedStateChange(this, this.mGrabbedState);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void vibrate(long l) {
        synchronized (this) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            boolean bl = true;
            if (Settings.System.getIntForUser(contentResolver, "haptic_feedback_enabled", 1, -2) == 0) return;
            if (!bl) return;
            if (this.mVibrator == null) {
                this.mVibrator = (Vibrator)this.getContext().getSystemService("vibrator");
            }
            this.mVibrator.vibrate(l, VIBRATION_ATTRIBUTES);
            return;
        }
    }

    private boolean withinView(float f, float f2, View view) {
        boolean bl = this.isHorizontal() && f2 > -50.0f && f2 < (float)(view.getHeight() + 50) || !this.isHorizontal() && f > -50.0f && f < (float)(view.getWidth() + 50);
        return bl;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int n = motionEvent.getAction();
        float f = motionEvent.getX();
        float f2 = motionEvent.getY();
        if (this.mAnimating) {
            return false;
        }
        this.mLeftSlider.tab.getHitRect(this.mTmpRect);
        boolean bl = this.mTmpRect.contains((int)f, (int)f2);
        this.mRightSlider.tab.getHitRect(this.mTmpRect);
        boolean bl2 = this.mTmpRect.contains((int)f, (int)f2);
        if (!(this.mTracking || bl || bl2)) {
            return false;
        }
        if (n == 0) {
            this.mTracking = true;
            this.mTriggered = false;
            this.vibrate(30L);
            f = 0.3333333f;
            if (bl) {
                this.mCurrentSlider = this.mLeftSlider;
                this.mOtherSlider = this.mRightSlider;
                if (this.isHorizontal()) {
                    f = 0.6666667f;
                }
                this.mThreshold = f;
                this.setGrabbedState(1);
            } else {
                this.mCurrentSlider = this.mRightSlider;
                this.mOtherSlider = this.mLeftSlider;
                if (!this.isHorizontal()) {
                    f = 0.6666667f;
                }
                this.mThreshold = f;
                this.setGrabbedState(2);
            }
            this.mCurrentSlider.setState(1);
            this.mCurrentSlider.showTarget();
            this.mOtherSlider.hide();
        }
        return true;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        if (!bl) {
            return;
        }
        Slider slider = this.mLeftSlider;
        int n5 = this.isHorizontal() ? 0 : 3;
        slider.layout(n, n2, n3, n4, n5);
        slider = this.mRightSlider;
        n5 = this.isHorizontal() ? 1 : 2;
        slider.layout(n, n2, n3, n4, n5);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        View.MeasureSpec.getMode(n);
        int n3 = View.MeasureSpec.getSize(n);
        View.MeasureSpec.getMode(n2);
        int n4 = View.MeasureSpec.getSize(n2);
        this.mLeftSlider.measure(n, n2);
        this.mRightSlider.measure(n, n2);
        int n5 = this.mLeftSlider.getTabWidth();
        n2 = this.mRightSlider.getTabWidth();
        n = this.mLeftSlider.getTabHeight();
        int n6 = this.mRightSlider.getTabHeight();
        if (this.isHorizontal()) {
            n2 = Math.max(n3, n5 + n2);
            n = Math.max(n, n6);
        } else {
            n2 = Math.max(n5, n6);
            n = Math.max(n4, n + n6);
        }
        this.setMeasuredDimension(n2, n);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean onTouchEvent(MotionEvent var1_1) {
        block4 : {
            block5 : {
                block6 : {
                    var2_2 = this.mTracking;
                    var3_3 = false;
                    if (!var2_2) break block4;
                    var4_4 = var1_1.getAction();
                    var5_5 = var1_1.getX();
                    var6_6 = var1_1.getY();
                    if (var4_4 == 1) break block5;
                    var7_7 = 2;
                    if (var4_4 == 2) break block6;
                    if (var4_4 == 3) break block5;
                    break block4;
                }
                if (this.withinView(var5_5, var6_6, this)) {
                    this.moveHandle(var5_5, var6_6);
                    if (this.isHorizontal()) {
                        var6_6 = var5_5;
                    }
                    var5_5 = this.mThreshold;
                    var4_4 = this.isHorizontal() != false ? this.getWidth() : this.getHeight();
                    var5_5 *= (float)var4_4;
                    var4_4 = this.isHorizontal() ? ((this.mCurrentSlider == this.mLeftSlider ? var6_6 > var5_5 : var6_6 < var5_5) ? 1 : 0) : ((this.mCurrentSlider == this.mLeftSlider ? var6_6 < var5_5 : var6_6 > var5_5) ? 1 : 0);
                    if (!this.mTriggered && var4_4 != 0) {
                        this.mTriggered = true;
                        this.mTracking = false;
                        this.mCurrentSlider.setState(2);
                        var4_4 = this.mCurrentSlider == this.mLeftSlider ? 1 : 0;
                        if (var4_4 != 0) {
                            var7_7 = 1;
                        }
                        this.dispatchTriggerEvent(var7_7);
                        var2_2 = var4_4 != 0 ? this.mHoldLeftOnTransition : this.mHoldRightOnTransition;
                        this.startAnimating(var2_2);
                        this.setGrabbedState(0);
                        ** GOTO lbl37
                    } else {
                        ** GOTO lbl34
                    }
                }
                break block5;
lbl34: // 2 sources:
                break block4;
            }
            this.cancelGrab();
        }
        if (this.mTracking != false) return true;
        var2_2 = var3_3;
        if (super.onTouchEvent(var1_1) == false) return var2_2;
        return true;
    }

    @Override
    protected void onVisibilityChanged(View view, int n) {
        super.onVisibilityChanged(view, n);
        if (view == this && n != 0 && this.mGrabbedState != 0) {
            this.cancelGrab();
        }
    }

    public void reset(boolean bl) {
        this.mLeftSlider.reset(bl);
        this.mRightSlider.reset(bl);
        if (!bl) {
            this.mAnimating = false;
        }
    }

    @UnsupportedAppUsage
    public void setHoldAfterTrigger(boolean bl, boolean bl2) {
        this.mHoldLeftOnTransition = bl;
        this.mHoldRightOnTransition = bl2;
    }

    @UnsupportedAppUsage
    public void setLeftHintText(int n) {
        if (this.isHorizontal()) {
            this.mLeftSlider.setHintText(n);
        }
    }

    @UnsupportedAppUsage
    public void setLeftTabResources(int n, int n2, int n3, int n4) {
        this.mLeftSlider.setIcon(n);
        this.mLeftSlider.setTarget(n2);
        this.mLeftSlider.setBarBackgroundResource(n3);
        this.mLeftSlider.setTabBackgroundResource(n4);
        this.mLeftSlider.updateDrawableStates();
    }

    @UnsupportedAppUsage
    public void setOnTriggerListener(OnTriggerListener onTriggerListener) {
        this.mOnTriggerListener = onTriggerListener;
    }

    @UnsupportedAppUsage
    public void setRightHintText(int n) {
        if (this.isHorizontal()) {
            this.mRightSlider.setHintText(n);
        }
    }

    @UnsupportedAppUsage
    public void setRightTabResources(int n, int n2, int n3, int n4) {
        this.mRightSlider.setIcon(n);
        this.mRightSlider.setTarget(n2);
        this.mRightSlider.setBarBackgroundResource(n3);
        this.mRightSlider.setTabBackgroundResource(n4);
        this.mRightSlider.updateDrawableStates();
    }

    @Override
    public void setVisibility(int n) {
        if (n != this.getVisibility() && n == 4) {
            this.reset(false);
        }
        super.setVisibility(n);
    }

    void startAnimating(final boolean bl) {
        int n;
        this.mAnimating = true;
        Slider slider = this.mCurrentSlider;
        Object object = this.mOtherSlider;
        boolean bl2 = this.isHorizontal();
        final int n2 = 0;
        int n3 = 0;
        if (bl2) {
            n = slider.tab.getRight();
            n2 = slider.tab.getWidth();
            int n4 = slider.tab.getLeft();
            int n5 = this.getWidth();
            if (!bl) {
                n3 = n2;
            }
            n3 = slider == this.mRightSlider ? -(n + n5 - n3) : n5 - n4 + n5 - n3;
            n = 0;
            n2 = n3;
        } else {
            int n6 = slider.tab.getTop();
            int n7 = slider.tab.getBottom();
            n3 = slider.tab.getHeight();
            n = this.getHeight();
            if (bl) {
                n3 = n2;
            }
            n2 = 0;
            n3 = slider == this.mRightSlider ? n6 + n - n3 : -(n - n7 + n - n3);
            n = n3;
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, n2, 0.0f, n);
        translateAnimation.setDuration(250L);
        translateAnimation.setInterpolator(new LinearInterpolator());
        translateAnimation.setFillAfter(true);
        object = new TranslateAnimation(0.0f, n2, 0.0f, n);
        ((Animation)object).setDuration(250L);
        ((Animation)object).setInterpolator(new LinearInterpolator());
        ((Animation)object).setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationEnd(Animation animation) {
                if (bl) {
                    int n3 = n2;
                    float f = n3;
                    float f2 = n3;
                    n3 = n;
                    animation = new TranslateAnimation(f, f2, n3, n3);
                    animation.setDuration(1000L);
                    SlidingTab.this.mAnimating = false;
                } else {
                    animation = new AlphaAnimation(0.5f, 1.0f);
                    animation.setDuration(250L);
                    SlidingTab.this.resetView();
                }
                animation.setAnimationListener(SlidingTab.this.mAnimationDoneListener);
                SlidingTab.this.mLeftSlider.startAnimation(animation, animation);
                SlidingTab.this.mRightSlider.startAnimation(animation, animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
        slider.hideTarget();
        slider.startAnimation(translateAnimation, (Animation)object);
    }

    public static interface OnTriggerListener {
        public static final int LEFT_HANDLE = 1;
        public static final int NO_HANDLE = 0;
        public static final int RIGHT_HANDLE = 2;

        public void onGrabbedStateChange(View var1, int var2);

        public void onTrigger(View var1, int var2);
    }

    private static class Slider {
        public static final int ALIGN_BOTTOM = 3;
        public static final int ALIGN_LEFT = 0;
        public static final int ALIGN_RIGHT = 1;
        public static final int ALIGN_TOP = 2;
        public static final int ALIGN_UNKNOWN = 4;
        private static final int STATE_ACTIVE = 2;
        private static final int STATE_NORMAL = 0;
        private static final int STATE_PRESSED = 1;
        private int alignment = 4;
        private int alignment_value;
        private int currentState = 0;
        @UnsupportedAppUsage
        private final ImageView tab;
        private final ImageView target;
        @UnsupportedAppUsage
        private final TextView text;

        Slider(ViewGroup viewGroup, int n, int n2, int n3) {
            this.tab = new ImageView(viewGroup.getContext());
            this.tab.setBackgroundResource(n);
            this.tab.setScaleType(ImageView.ScaleType.CENTER);
            this.tab.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            this.text = new TextView(viewGroup.getContext());
            this.text.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
            this.text.setBackgroundResource(n2);
            this.text.setTextAppearance(viewGroup.getContext(), 16974794);
            this.target = new ImageView(viewGroup.getContext());
            this.target.setImageResource(n3);
            this.target.setScaleType(ImageView.ScaleType.CENTER);
            this.target.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            this.target.setVisibility(4);
            viewGroup.addView(this.target);
            viewGroup.addView(this.tab);
            viewGroup.addView(this.text);
        }

        public int getTabHeight() {
            return this.tab.getMeasuredHeight();
        }

        public int getTabWidth() {
            return this.tab.getMeasuredWidth();
        }

        void hide() {
            int n = this.alignment;
            int n2 = 0;
            int n3 = n != 0 && n != 1 ? 0 : 1;
            n = n3 != 0 ? (this.alignment == 0 ? this.alignment_value - this.tab.getRight() : this.alignment_value - this.tab.getLeft()) : 0;
            n3 = n3 != 0 ? n2 : (this.alignment == 2 ? this.alignment_value - this.tab.getBottom() : this.alignment_value - this.tab.getTop());
            TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, n, 0.0f, n3);
            translateAnimation.setDuration(250L);
            translateAnimation.setFillAfter(true);
            this.tab.startAnimation(translateAnimation);
            this.text.startAnimation(translateAnimation);
            this.target.setVisibility(4);
        }

        public void hideTarget() {
            this.target.clearAnimation();
            this.target.setVisibility(4);
        }

        void layout(int n, int n2, int n3, int n4, int n5) {
            this.alignment = n5;
            Drawable drawable2 = this.tab.getBackground();
            int n6 = drawable2.getIntrinsicWidth();
            int n7 = drawable2.getIntrinsicHeight();
            drawable2 = this.target.getDrawable();
            int n8 = drawable2.getIntrinsicWidth();
            int n9 = drawable2.getIntrinsicHeight();
            int n10 = n3 - n;
            int n11 = n4 - n2;
            int n12 = (int)((float)n10 * 0.6666667f) - n8 + n6 / 2;
            int n13 = (int)((float)n10 * 0.3333333f) - n6 / 2;
            int n14 = (n10 - n6) / 2;
            int n15 = n14 + n6;
            if (n5 != 0 && n5 != 1) {
                n = (n10 - n8) / 2;
                n3 = (n10 + n8) / 2;
                n8 = (int)((float)n11 * 0.6666667f) + n7 / 2 - n9;
                n10 = (int)((float)n11 * 0.3333333f) - n7 / 2;
                if (n5 == 2) {
                    this.tab.layout(n14, 0, n15, n7);
                    this.text.layout(n14, 0 - n11, n15, 0);
                    this.target.layout(n, n8, n3, n8 + n9);
                    this.alignment_value = n2;
                } else {
                    this.tab.layout(n14, n11 - n7, n15, n11);
                    this.text.layout(n14, n11, n15, n11 + n11);
                    this.target.layout(n, n10, n3, n10 + n9);
                    this.alignment_value = n4;
                }
            } else {
                n2 = n6;
                n4 = n13;
                n13 = (n11 - n9) / 2;
                n9 = n13 + n9;
                n6 = (n11 - n7) / 2;
                n7 = (n11 + n7) / 2;
                if (n5 == 0) {
                    this.tab.layout(0, n6, n2, n7);
                    this.text.layout(0 - n10, n6, 0, n7);
                    this.text.setGravity(5);
                    this.target.layout(n12, n13, n12 + n8, n9);
                    this.alignment_value = n;
                } else {
                    this.tab.layout(n10 - n2, n6, n10, n7);
                    this.text.layout(n10, n6, n10 + n10, n7);
                    this.target.layout(n4, n13, n4 + n8, n9);
                    this.text.setGravity(48);
                    this.alignment_value = n3;
                }
            }
        }

        public void measure(int n, int n2) {
            n = View.MeasureSpec.getSize(n);
            n2 = View.MeasureSpec.getSize(n2);
            this.tab.measure(View.MeasureSpec.makeSafeMeasureSpec(n, 0), View.MeasureSpec.makeSafeMeasureSpec(n2, 0));
            this.text.measure(View.MeasureSpec.makeSafeMeasureSpec(n, 0), View.MeasureSpec.makeSafeMeasureSpec(n2, 0));
        }

        void reset(boolean bl) {
            int n;
            this.setState(0);
            this.text.setVisibility(0);
            Object object = this.text;
            ((TextView)object).setTextAppearance(((View)object).getContext(), 16974794);
            this.tab.setVisibility(0);
            this.target.setVisibility(4);
            int n2 = this.alignment;
            int n3 = n = 1;
            if (n2 != 0) {
                n3 = n2 == 1 ? n : 0;
            }
            n = n3 != 0 ? (this.alignment == 0 ? this.alignment_value - this.tab.getLeft() : this.alignment_value - this.tab.getRight()) : 0;
            n2 = n3 != 0 ? 0 : (this.alignment == 2 ? this.alignment_value - this.tab.getTop() : this.alignment_value - this.tab.getBottom());
            if (bl) {
                object = new TranslateAnimation(0.0f, n, 0.0f, n2);
                ((Animation)object).setDuration(250L);
                ((Animation)object).setFillAfter(false);
                this.text.startAnimation((Animation)object);
                this.tab.startAnimation((Animation)object);
            } else {
                if (n3 != 0) {
                    this.text.offsetLeftAndRight(n);
                    this.tab.offsetLeftAndRight(n);
                } else {
                    this.text.offsetTopAndBottom(n2);
                    this.tab.offsetTopAndBottom(n2);
                }
                this.text.clearAnimation();
                this.tab.clearAnimation();
                this.target.clearAnimation();
            }
        }

        void setBarBackgroundResource(int n) {
            this.text.setBackgroundResource(n);
        }

        void setHintText(int n) {
            this.text.setText(n);
        }

        void setIcon(int n) {
            this.tab.setImageResource(n);
        }

        void setState(int n) {
            View view = this.text;
            boolean bl = n == 1;
            view.setPressed(bl);
            view = this.tab;
            bl = n == 1;
            view.setPressed(bl);
            if (n == 2) {
                view = new int[]{(View)16842914};
                if (this.text.getBackground().isStateful()) {
                    this.text.getBackground().setState((int[])view);
                }
                if (this.tab.getBackground().isStateful()) {
                    this.tab.getBackground().setState((int[])view);
                }
                view = this.text;
                ((TextView)view).setTextAppearance(view.getContext(), 16974793);
            } else {
                view = this.text;
                ((TextView)view).setTextAppearance(view.getContext(), 16974794);
            }
            this.currentState = n;
        }

        void setTabBackgroundResource(int n) {
            this.tab.setBackgroundResource(n);
        }

        void setTarget(int n) {
            this.target.setImageResource(n);
        }

        void show(boolean bl) {
            Object object = this.text;
            int n = 0;
            ((View)object).setVisibility(0);
            this.tab.setVisibility(0);
            if (bl) {
                int n2;
                int n3 = this.alignment;
                int n4 = n2 = 1;
                if (n3 != 0) {
                    n4 = n3 == 1 ? n2 : 0;
                }
                n2 = n4 != 0 ? (this.alignment == 0 ? this.tab.getWidth() : -this.tab.getWidth()) : 0;
                n4 = n4 != 0 ? n : (this.alignment == 2 ? this.tab.getHeight() : -this.tab.getHeight());
                object = new TranslateAnimation(-n2, 0.0f, -n4, 0.0f);
                ((Animation)object).setDuration(250L);
                this.tab.startAnimation((Animation)object);
                this.text.startAnimation((Animation)object);
            }
        }

        void showTarget() {
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(500L);
            this.target.startAnimation(alphaAnimation);
            this.target.setVisibility(0);
        }

        public void startAnimation(Animation animation, Animation animation2) {
            this.tab.startAnimation(animation);
            this.text.startAnimation(animation2);
        }

        public void updateDrawableStates() {
            this.setState(this.currentState);
        }
    }

}

