/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ViewAnimator;
import java.util.ArrayList;

public class DialogViewAnimator
extends ViewAnimator {
    private final ArrayList<View> mMatchParentChildren = new ArrayList(1);

    public DialogViewAnimator(Context context) {
        super(context);
    }

    public DialogViewAnimator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        Object object;
        int n3;
        int n4;
        Object object2;
        int n5 = View.MeasureSpec.getMode(n) == 1073741824 && View.MeasureSpec.getMode(n2) == 1073741824 ? 0 : 1;
        int n6 = this.getChildCount();
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        for (int i = 0; i < n6; ++i) {
            int n10;
            block11 : {
                int n11;
                block10 : {
                    object2 = this.getChildAt(i);
                    if (this.getMeasureAllChildren()) break block10;
                    n10 = n9;
                    n4 = n7;
                    n3 = n8;
                    if (((View)object2).getVisibility() == 8) break block11;
                }
                object = (FrameLayout.LayoutParams)((View)object2).getLayoutParams();
                n10 = ((FrameLayout.LayoutParams)object).width == -1 ? 1 : 0;
                boolean bl = ((FrameLayout.LayoutParams)object).height == -1;
                if (n5 != 0 && (n10 != 0 || bl)) {
                    this.mMatchParentChildren.add((View)object2);
                }
                this.measureChildWithMargins((View)object2, n, 0, n2, 0);
                n4 = n11 = 0;
                n3 = n8;
                if (n5 != 0) {
                    n4 = n11;
                    n3 = n8;
                    if (n10 == 0) {
                        n3 = Math.max(n8, ((View)object2).getMeasuredWidth() + ((FrameLayout.LayoutParams)object).leftMargin + ((FrameLayout.LayoutParams)object).rightMargin);
                        n4 = 0 | ((View)object2).getMeasuredWidthAndState() & -16777216;
                    }
                }
                if (n5 != 0 && !bl) {
                    n7 = Math.max(n7, ((View)object2).getMeasuredHeight() + ((FrameLayout.LayoutParams)object).topMargin + ((FrameLayout.LayoutParams)object).bottomMargin);
                    n4 |= ((View)object2).getMeasuredHeightAndState() >> 16 & -256;
                }
                n10 = DialogViewAnimator.combineMeasuredStates(n9, n4);
                n4 = n7;
            }
            n9 = n10;
            n7 = n4;
            n8 = n3;
        }
        n5 = this.getPaddingLeft();
        n4 = this.getPaddingRight();
        n3 = Math.max(n7 + (this.getPaddingTop() + this.getPaddingBottom()), this.getSuggestedMinimumHeight());
        n4 = Math.max(n8 + (n5 + n4), this.getSuggestedMinimumWidth());
        object2 = this.getForeground();
        n8 = n3;
        n7 = n4;
        if (object2 != null) {
            n8 = Math.max(n3, ((Drawable)object2).getMinimumHeight());
            n7 = Math.max(n4, ((Drawable)object2).getMinimumWidth());
        }
        this.setMeasuredDimension(DialogViewAnimator.resolveSizeAndState(n7, n, n9), DialogViewAnimator.resolveSizeAndState(n8, n2, n9 << 16));
        n4 = this.mMatchParentChildren.size();
        for (n7 = 0; n7 < n4; ++n7) {
            object = this.mMatchParentChildren.get(n7);
            object2 = (ViewGroup.MarginLayoutParams)((View)object).getLayoutParams();
            n8 = ((ViewGroup.MarginLayoutParams)object2).width == -1 ? View.MeasureSpec.makeMeasureSpec(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight() - ((ViewGroup.MarginLayoutParams)object2).leftMargin - ((ViewGroup.MarginLayoutParams)object2).rightMargin, 1073741824) : DialogViewAnimator.getChildMeasureSpec(n, this.getPaddingLeft() + this.getPaddingRight() + ((ViewGroup.MarginLayoutParams)object2).leftMargin + ((ViewGroup.MarginLayoutParams)object2).rightMargin, ((ViewGroup.MarginLayoutParams)object2).width);
            n3 = ((ViewGroup.MarginLayoutParams)object2).height == -1 ? View.MeasureSpec.makeMeasureSpec(this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom() - ((ViewGroup.MarginLayoutParams)object2).topMargin - ((ViewGroup.MarginLayoutParams)object2).bottomMargin, 1073741824) : DialogViewAnimator.getChildMeasureSpec(n2, this.getPaddingTop() + this.getPaddingBottom() + ((ViewGroup.MarginLayoutParams)object2).topMargin + ((ViewGroup.MarginLayoutParams)object2).bottomMargin, ((ViewGroup.MarginLayoutParams)object2).height);
            ((View)object).measure(n8, n3);
        }
        this.mMatchParentChildren.clear();
    }
}

