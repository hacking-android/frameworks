/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

@RemoteViews.RemoteView
public class RemeasuringLinearLayout
extends LinearLayout {
    public RemeasuringLinearLayout(Context context) {
        super(context);
    }

    public RemeasuringLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RemeasuringLinearLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public RemeasuringLinearLayout(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        int n3 = this.getChildCount();
        n2 = 0;
        for (n = 0; n < n3; ++n) {
            View view = this.getChildAt(n);
            int n4 = n2;
            if (view != null) {
                if (view.getVisibility() == 8) {
                    n4 = n2;
                } else {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
                    n4 = Math.max(n2, view.getMeasuredHeight() + n2 + layoutParams.topMargin + layoutParams.bottomMargin);
                }
            }
            n2 = n4;
        }
        this.setMeasuredDimension(this.getMeasuredWidth(), n2);
    }
}

