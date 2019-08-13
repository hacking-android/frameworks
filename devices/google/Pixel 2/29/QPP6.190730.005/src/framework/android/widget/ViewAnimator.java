/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import com.android.internal.R;

public class ViewAnimator
extends FrameLayout {
    boolean mAnimateFirstTime = true;
    @UnsupportedAppUsage
    boolean mFirstTime = true;
    Animation mInAnimation;
    Animation mOutAnimation;
    @UnsupportedAppUsage
    int mWhichChild = 0;

    public ViewAnimator(Context context) {
        super(context);
        this.initViewAnimator(context, null);
    }

    public ViewAnimator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ViewAnimator);
        int n = typedArray.getResourceId(0, 0);
        if (n > 0) {
            this.setInAnimation(context, n);
        }
        if ((n = typedArray.getResourceId(1, 0)) > 0) {
            this.setOutAnimation(context, n);
        }
        this.setAnimateFirstView(typedArray.getBoolean(2, true));
        typedArray.recycle();
        this.initViewAnimator(context, attributeSet);
    }

    private void initViewAnimator(Context object, AttributeSet attributeSet) {
        if (attributeSet == null) {
            this.mMeasureAllChildren = true;
            return;
        }
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.FrameLayout);
        this.setMeasureAllChildren(((TypedArray)object).getBoolean(0, true));
        ((TypedArray)object).recycle();
    }

    @Override
    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        int n2;
        super.addView(view, n, layoutParams);
        if (this.getChildCount() == 1) {
            view.setVisibility(0);
        } else {
            view.setVisibility(8);
        }
        if (n >= 0 && (n2 = this.mWhichChild) >= n) {
            this.setDisplayedChild(n2 + 1);
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ViewAnimator.class.getName();
    }

    public boolean getAnimateFirstView() {
        return this.mAnimateFirstTime;
    }

    @Override
    public int getBaseline() {
        int n = this.getCurrentView() != null ? this.getCurrentView().getBaseline() : super.getBaseline();
        return n;
    }

    public View getCurrentView() {
        return this.getChildAt(this.mWhichChild);
    }

    public int getDisplayedChild() {
        return this.mWhichChild;
    }

    public Animation getInAnimation() {
        return this.mInAnimation;
    }

    public Animation getOutAnimation() {
        return this.mOutAnimation;
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        this.mWhichChild = 0;
        this.mFirstTime = true;
    }

    @Override
    public void removeView(View view) {
        int n = this.indexOfChild(view);
        if (n >= 0) {
            this.removeViewAt(n);
        }
    }

    @Override
    public void removeViewAt(int n) {
        super.removeViewAt(n);
        int n2 = this.getChildCount();
        if (n2 == 0) {
            this.mWhichChild = 0;
            this.mFirstTime = true;
        } else {
            int n3 = this.mWhichChild;
            if (n3 >= n2) {
                this.setDisplayedChild(n2 - 1);
            } else if (n3 == n) {
                this.setDisplayedChild(n3);
            }
        }
    }

    @Override
    public void removeViewInLayout(View view) {
        this.removeView(view);
    }

    @Override
    public void removeViews(int n, int n2) {
        super.removeViews(n, n2);
        if (this.getChildCount() == 0) {
            this.mWhichChild = 0;
            this.mFirstTime = true;
        } else {
            int n3 = this.mWhichChild;
            if (n3 >= n && n3 < n + n2) {
                this.setDisplayedChild(n3);
            }
        }
    }

    @Override
    public void removeViewsInLayout(int n, int n2) {
        this.removeViews(n, n2);
    }

    public void setAnimateFirstView(boolean bl) {
        this.mAnimateFirstTime = bl;
    }

    @RemotableViewMethod
    public void setDisplayedChild(int n) {
        this.mWhichChild = n;
        int n2 = this.getChildCount();
        int n3 = 1;
        if (n >= n2) {
            this.mWhichChild = 0;
        } else if (n < 0) {
            this.mWhichChild = this.getChildCount() - 1;
        }
        n = this.getFocusedChild() != null ? n3 : 0;
        this.showOnly(this.mWhichChild);
        if (n != 0) {
            this.requestFocus(2);
        }
    }

    public void setInAnimation(Context context, int n) {
        this.setInAnimation(AnimationUtils.loadAnimation(context, n));
    }

    public void setInAnimation(Animation animation) {
        this.mInAnimation = animation;
    }

    public void setOutAnimation(Context context, int n) {
        this.setOutAnimation(AnimationUtils.loadAnimation(context, n));
    }

    public void setOutAnimation(Animation animation) {
        this.mOutAnimation = animation;
    }

    @RemotableViewMethod
    public void showNext() {
        this.setDisplayedChild(this.mWhichChild + 1);
    }

    void showOnly(int n) {
        boolean bl = !this.mFirstTime || this.mAnimateFirstTime;
        this.showOnly(n, bl);
    }

    @UnsupportedAppUsage
    void showOnly(int n, boolean bl) {
        int n2 = this.getChildCount();
        for (int i = 0; i < n2; ++i) {
            View view = this.getChildAt(i);
            if (i == n) {
                Animation animation;
                if (bl && (animation = this.mInAnimation) != null) {
                    view.startAnimation(animation);
                }
                view.setVisibility(0);
                this.mFirstTime = false;
                continue;
            }
            if (bl && this.mOutAnimation != null && view.getVisibility() == 0) {
                view.startAnimation(this.mOutAnimation);
            } else if (view.getAnimation() == this.mInAnimation) {
                view.clearAnimation();
            }
            view.setVisibility(8);
        }
    }

    @RemotableViewMethod
    public void showPrevious() {
        this.setDisplayedChild(this.mWhichChild - 1);
    }
}

