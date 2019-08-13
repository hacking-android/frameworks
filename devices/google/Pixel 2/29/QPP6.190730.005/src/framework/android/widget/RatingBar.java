/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AbsSeekBar;
import com.android.internal.R;

public class RatingBar
extends AbsSeekBar {
    private int mNumStars = 5;
    @UnsupportedAppUsage
    private OnRatingBarChangeListener mOnRatingBarChangeListener;
    private int mProgressOnStartTracking;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842876);
    }

    public RatingBar(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public RatingBar(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.RatingBar, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.RatingBar, attributeSet, typedArray, n, n2);
        n = typedArray.getInt(0, this.mNumStars);
        this.setIsIndicator(typedArray.getBoolean(3, this.mIsUserSeekable ^ true));
        float f = typedArray.getFloat(1, -1.0f);
        float f2 = typedArray.getFloat(2, -1.0f);
        typedArray.recycle();
        if (n > 0 && n != this.mNumStars) {
            this.setNumStars(n);
        }
        if (f2 >= 0.0f) {
            this.setStepSize(f2);
        } else {
            this.setStepSize(0.5f);
        }
        if (f >= 0.0f) {
            this.setRating(f);
        }
        this.mTouchProgressOffset = 0.6f;
    }

    private float getProgressPerStar() {
        if (this.mNumStars > 0) {
            return (float)this.getMax() * 1.0f / (float)this.mNumStars;
        }
        return 1.0f;
    }

    private void updateSecondaryProgress(int n) {
        float f = this.getProgressPerStar();
        if (f > 0.0f) {
            this.setSecondaryProgress((int)(Math.ceil((float)n / f) * (double)f));
        }
    }

    @Override
    boolean canUserSetProgress() {
        boolean bl = super.canUserSetProgress() && !this.isIndicator();
        return bl;
    }

    void dispatchRatingChange(boolean bl) {
        OnRatingBarChangeListener onRatingBarChangeListener = this.mOnRatingBarChangeListener;
        if (onRatingBarChangeListener != null) {
            onRatingBarChangeListener.onRatingChanged(this, this.getRating(), bl);
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return RatingBar.class.getName();
    }

    @Override
    Shape getDrawableShape() {
        return new RectShape();
    }

    public int getNumStars() {
        return this.mNumStars;
    }

    public OnRatingBarChangeListener getOnRatingBarChangeListener() {
        return this.mOnRatingBarChangeListener;
    }

    public float getRating() {
        return (float)this.getProgress() / this.getProgressPerStar();
    }

    public float getStepSize() {
        return (float)this.getNumStars() / (float)this.getMax();
    }

    public boolean isIndicator() {
        return this.mIsUserSeekable ^ true;
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.canUserSetProgress()) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS);
        }
    }

    @Override
    void onKeyChange() {
        super.onKeyChange();
        this.dispatchRatingChange(true);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        synchronized (this) {
            super.onMeasure(n, n2);
            if (this.mSampleWidth > 0) {
                this.setMeasuredDimension(RatingBar.resolveSizeAndState(this.mSampleWidth * this.mNumStars, n, 0), this.getMeasuredHeight());
            }
            return;
        }
    }

    @Override
    void onProgressRefresh(float f, boolean bl, int n) {
        super.onProgressRefresh(f, bl, n);
        this.updateSecondaryProgress(n);
        if (!bl) {
            this.dispatchRatingChange(false);
        }
    }

    @Override
    void onStartTrackingTouch() {
        this.mProgressOnStartTracking = this.getProgress();
        super.onStartTrackingTouch();
    }

    @Override
    void onStopTrackingTouch() {
        super.onStopTrackingTouch();
        if (this.getProgress() != this.mProgressOnStartTracking) {
            this.dispatchRatingChange(true);
        }
    }

    public void setIsIndicator(boolean bl) {
        this.mIsUserSeekable = bl ^ true;
        if (bl) {
            this.setFocusable(16);
        } else {
            this.setFocusable(1);
        }
    }

    @Override
    public void setMax(int n) {
        synchronized (this) {
            if (n <= 0) {
                return;
            }
            super.setMax(n);
            return;
        }
    }

    public void setNumStars(int n) {
        if (n <= 0) {
            return;
        }
        this.mNumStars = n;
        this.requestLayout();
    }

    public void setOnRatingBarChangeListener(OnRatingBarChangeListener onRatingBarChangeListener) {
        this.mOnRatingBarChangeListener = onRatingBarChangeListener;
    }

    public void setRating(float f) {
        this.setProgress(Math.round(this.getProgressPerStar() * f));
    }

    public void setStepSize(float f) {
        if (f <= 0.0f) {
            return;
        }
        f = (float)this.mNumStars / f;
        int n = (int)(f / (float)this.getMax() * (float)this.getProgress());
        this.setMax((int)f);
        this.setProgress(n);
    }

    public static interface OnRatingBarChangeListener {
        public void onRatingChanged(RatingBar var1, float var2, boolean var3);
    }

}

