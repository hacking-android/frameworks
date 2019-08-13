/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.R;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.TextView;

public class DialogTitle
extends TextView {
    public DialogTitle(Context context) {
        super(context);
    }

    @UnsupportedAppUsage
    public DialogTitle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DialogTitle(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public DialogTitle(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        super.onMeasure(n, n2);
        Object object = this.getLayout();
        if (object != null && (n3 = ((Layout)object).getLineCount()) > 0 && ((Layout)object).getEllipsisCount(n3 - 1) > 0) {
            this.setSingleLine(false);
            this.setMaxLines(2);
            object = this.mContext.obtainStyledAttributes(null, R.styleable.TextAppearance, 16842817, 16973892);
            n3 = ((TypedArray)object).getDimensionPixelSize(0, 0);
            if (n3 != 0) {
                this.setTextSize(0, n3);
            }
            ((TypedArray)object).recycle();
            super.onMeasure(n, n2);
        }
    }
}

