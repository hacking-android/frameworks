/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class AlertDialogLayout
extends LinearLayout {
    public AlertDialogLayout(Context context) {
        super(context);
    }

    @UnsupportedAppUsage
    public AlertDialogLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AlertDialogLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public AlertDialogLayout(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    private void forceUniformWidth(int n, int n2) {
        int n3 = View.MeasureSpec.makeMeasureSpec(this.getMeasuredWidth(), 1073741824);
        for (int i = 0; i < n; ++i) {
            View view = this.getChildAt(i);
            if (view.getVisibility() == 8) continue;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
            if (layoutParams.width != -1) continue;
            int n4 = layoutParams.height;
            layoutParams.height = view.getMeasuredHeight();
            this.measureChildWithMargins(view, n3, 0, n2, 0);
            layoutParams.height = n4;
        }
    }

    private int resolveMinimumHeight(View view) {
        int n = view.getMinimumHeight();
        if (n > 0) {
            return n;
        }
        if (view instanceof ViewGroup && ((ViewGroup)(view = (ViewGroup)view)).getChildCount() == 1) {
            return this.resolveMinimumHeight(((ViewGroup)view).getChildAt(0));
        }
        return 0;
    }

    private void setChildFrame(View view, int n, int n2, int n3, int n4) {
        view.layout(n, n2, n + n3, n2 + n4);
    }

    private boolean tryOnMeasure(int n, int n2) {
        int n3;
        View view;
        int n4;
        View view2 = null;
        View view3 = null;
        View view4 = null;
        int n5 = this.getChildCount();
        block5 : for (n4 = 0; n4 < n5; ++n4) {
            view = this.getChildAt(n4);
            if (view.getVisibility() == 8) continue;
            switch (view.getId()) {
                default: {
                    return false;
                }
                case 16909482: {
                    view2 = view;
                    continue block5;
                }
                case 16908829: 
                case 16908853: {
                    if (view4 != null) {
                        return false;
                    }
                    view4 = view;
                    continue block5;
                }
                case 16908778: {
                    view3 = view;
                }
            }
        }
        int n6 = View.MeasureSpec.getMode(n2);
        int n7 = View.MeasureSpec.getSize(n2);
        int n8 = View.MeasureSpec.getMode(n);
        int n9 = 0;
        int n10 = n4 = this.getPaddingTop() + this.getPaddingBottom();
        if (view2 != null) {
            view2.measure(n, 0);
            n10 = n4 + view2.getMeasuredHeight();
            n9 = AlertDialogLayout.combineMeasuredStates(0, view2.getMeasuredState());
        }
        n4 = 0;
        int n11 = 0;
        int n12 = n9;
        int n13 = n10;
        if (view3 != null) {
            view3.measure(n, 0);
            n4 = this.resolveMinimumHeight(view3);
            n11 = view3.getMeasuredHeight() - n4;
            n13 = n10 + n4;
            n12 = AlertDialogLayout.combineMeasuredStates(n9, view3.getMeasuredState());
        }
        int n14 = 0;
        if (view4 != null) {
            n9 = n6 == 0 ? 0 : View.MeasureSpec.makeMeasureSpec(Math.max(0, n7 - n13), n6);
            view4.measure(n, n9);
            n14 = view4.getMeasuredHeight();
            n13 += n14;
            n12 = AlertDialogLayout.combineMeasuredStates(n12, view4.getMeasuredState());
        }
        n7 = n3 = n7 - n13;
        n9 = n12;
        n10 = n13;
        if (view3 != null) {
            n7 = Math.min(n3, n11);
            n9 = n3;
            n10 = n4;
            if (n7 > 0) {
                n9 = n3 - n7;
                n10 = n4 + n7;
            }
            view3.measure(n, View.MeasureSpec.makeMeasureSpec(n10, 1073741824));
            n10 = n13 - n4 + view3.getMeasuredHeight();
            n4 = AlertDialogLayout.combineMeasuredStates(n12, view3.getMeasuredState());
            n7 = n9;
            n9 = n4;
        }
        n13 = n7;
        n12 = n9;
        n4 = n10;
        if (view4 != null) {
            n13 = n7;
            n12 = n9;
            n4 = n10;
            if (n7 > 0) {
                view4.measure(n, View.MeasureSpec.makeMeasureSpec(n14 + n7, n6));
                n4 = n10 - n14 + view4.getMeasuredHeight();
                n12 = AlertDialogLayout.combineMeasuredStates(n9, view4.getMeasuredState());
                n13 = n7 - n7;
            }
        }
        n10 = 0;
        for (n13 = 0; n13 < n5; ++n13) {
            view = this.getChildAt(n13);
            n9 = n10;
            if (view.getVisibility() != 8) {
                n9 = Math.max(n10, view.getMeasuredWidth());
            }
            n10 = n9;
        }
        this.setMeasuredDimension(AlertDialogLayout.resolveSizeAndState(n10 + (this.getPaddingLeft() + this.getPaddingRight()), n, n12), AlertDialogLayout.resolveSizeAndState(n4, n2, 0));
        if (n8 != 1073741824) {
            this.forceUniformWidth(n5, n2);
        }
        return true;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        Object object = this;
        int n5 = ((AlertDialogLayout)object).mPaddingLeft;
        int n6 = n3 - n;
        int n7 = ((AlertDialogLayout)object).mPaddingRight;
        int n8 = ((AlertDialogLayout)object).mPaddingRight;
        n3 = this.getMeasuredHeight();
        int n9 = this.getChildCount();
        int n10 = this.getGravity();
        n = n10 & 112;
        n = n != 16 ? (n != 80 ? ((AlertDialogLayout)object).mPaddingTop : ((AlertDialogLayout)object).mPaddingTop + n4 - n2 - n3) : ((AlertDialogLayout)object).mPaddingTop + (n4 - n2 - n3) / 2;
        object = this.getDividerDrawable();
        n3 = object == null ? 0 : ((Drawable)object).getIntrinsicHeight();
        n4 = 0;
        do {
            object = this;
            if (n4 >= n9) break;
            View view = ((ViewGroup)object).getChildAt(n4);
            if (view != null && view.getVisibility() != 8) {
                int n11 = view.getMeasuredWidth();
                int n12 = view.getMeasuredHeight();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
                n2 = layoutParams.gravity;
                if (n2 < 0) {
                    n2 = n10 & 8388615;
                }
                n2 = (n2 = Gravity.getAbsoluteGravity(n2, this.getLayoutDirection()) & 7) != 1 ? (n2 != 5 ? layoutParams.leftMargin + n5 : n6 - n7 - n11 - layoutParams.rightMargin) : (n6 - n5 - n8 - n11) / 2 + n5 + layoutParams.leftMargin - layoutParams.rightMargin;
                int n13 = n;
                if (((LinearLayout)object).hasDividerBeforeChildAt(n4)) {
                    n13 = n + n3;
                }
                n = n13 + layoutParams.topMargin;
                this.setChildFrame(view, n2, n, n11, n12);
                n += n12 + layoutParams.bottomMargin;
            }
            ++n4;
        } while (true);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        if (!this.tryOnMeasure(n, n2)) {
            super.onMeasure(n, n2);
        }
    }
}

