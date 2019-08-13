/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.internal.R;

public class ButtonBarLayout
extends LinearLayout {
    private static final int PEEK_BUTTON_DP = 16;
    private boolean mAllowStacking;
    private int mLastWidthSize = -1;
    private int mMinimumHeight = 0;

    @UnsupportedAppUsage
    public ButtonBarLayout(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.ButtonBarLayout);
        this.mAllowStacking = ((TypedArray)object).getBoolean(0, true);
        ((TypedArray)object).recycle();
    }

    private int getNextVisibleChildIndex(int n) {
        int n2 = this.getChildCount();
        while (n < n2) {
            if (this.getChildAt(n).getVisibility() == 0) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    private boolean isStacked() {
        int n = this.getOrientation();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    private void setStacked(boolean n) {
        this.setOrientation(n);
        int n2 = n != 0 ? 8388613 : 80;
        this.setGravity(n2);
        Object t = this.findViewById(16909384);
        if (t != null) {
            n = n != 0 ? 8 : 4;
            ((View)t).setVisibility(n);
        }
        for (n = this.getChildCount() - 2; n >= 0; --n) {
            this.bringChildToFront(this.getChildAt(n));
        }
    }

    @Override
    public int getMinimumHeight() {
        return Math.max(this.mMinimumHeight, super.getMinimumHeight());
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getSize(n);
        if (this.mAllowStacking) {
            if (n3 > this.mLastWidthSize && this.isStacked()) {
                this.setStacked(false);
            }
            this.mLastWidthSize = n3;
        }
        int n4 = 0;
        if (!this.isStacked() && View.MeasureSpec.getMode(n) == 1073741824) {
            n3 = View.MeasureSpec.makeMeasureSpec(n3, Integer.MIN_VALUE);
            n4 = 1;
        } else {
            n3 = n;
        }
        super.onMeasure(n3, n2);
        n3 = n4;
        if (this.mAllowStacking) {
            n3 = n4;
            if (!this.isStacked()) {
                n3 = n4;
                if ((-16777216 & this.getMeasuredWidthAndState()) == 16777216) {
                    this.setStacked(true);
                    n3 = 1;
                }
            }
        }
        if (n3 != 0) {
            super.onMeasure(n, n2);
        }
        n = 0;
        n4 = this.getNextVisibleChildIndex(0);
        if (n4 >= 0) {
            View view = this.getChildAt(n4);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
            n2 = 0 + (this.getPaddingTop() + view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
            if (this.isStacked()) {
                n4 = this.getNextVisibleChildIndex(n4 + 1);
                n = n2;
                if (n4 >= 0) {
                    n = (int)((float)n2 + ((float)this.getChildAt(n4).getPaddingTop() + this.getResources().getDisplayMetrics().density * 16.0f));
                }
            } else {
                n = n2 + this.getPaddingBottom();
            }
        }
        if (this.getMinimumHeight() != n) {
            this.setMinimumHeight(n);
        }
    }

    public void setAllowStacking(boolean bl) {
        if (this.mAllowStacking != bl) {
            this.mAllowStacking = bl;
            if (!this.mAllowStacking && this.getOrientation() == 1) {
                this.setStacked(false);
            }
            this.requestLayout();
        }
    }
}

