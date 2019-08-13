/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import com.android.internal.R;

public class WeightedLinearLayout
extends LinearLayout {
    private float mMajorWeightMax;
    private float mMajorWeightMin;
    private float mMinorWeightMax;
    private float mMinorWeightMin;

    public WeightedLinearLayout(Context context) {
        super(context);
    }

    public WeightedLinearLayout(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.WeightedLinearLayout);
        this.mMajorWeightMin = ((TypedArray)object).getFloat(1, 0.0f);
        this.mMinorWeightMin = ((TypedArray)object).getFloat(3, 0.0f);
        this.mMajorWeightMax = ((TypedArray)object).getFloat(0, 0.0f);
        this.mMinorWeightMax = ((TypedArray)object).getFloat(2, 0.0f);
        ((TypedArray)object).recycle();
    }

    @Override
    protected void onMeasure(int n, int n2) {
        DisplayMetrics displayMetrics = this.getContext().getResources().getDisplayMetrics();
        int n3 = displayMetrics.widthPixels;
        int n4 = n3 < displayMetrics.heightPixels ? 1 : 0;
        int n5 = View.MeasureSpec.getMode(n);
        super.onMeasure(n, n2);
        int n6 = this.getMeasuredWidth();
        int n7 = 0;
        int n8 = View.MeasureSpec.makeMeasureSpec(n6, 1073741824);
        float f = n4 != 0 ? this.mMinorWeightMin : this.mMajorWeightMin;
        float f2 = n4 != 0 ? this.mMinorWeightMax : this.mMajorWeightMax;
        n = n7;
        n4 = n8;
        if (n5 == Integer.MIN_VALUE) {
            n = (int)((float)n3 * f);
            n3 = (int)((float)n3 * f);
            if (f > 0.0f && n6 < n) {
                n4 = View.MeasureSpec.makeMeasureSpec(n, 1073741824);
                n = 1;
            } else {
                n = n7;
                n4 = n8;
                if (f2 > 0.0f) {
                    n = n7;
                    n4 = n8;
                    if (n6 > n3) {
                        n4 = View.MeasureSpec.makeMeasureSpec(n3, 1073741824);
                        n = 1;
                    }
                }
            }
        }
        if (n != 0) {
            super.onMeasure(n4, n2);
        }
    }
}

