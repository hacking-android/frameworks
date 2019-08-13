/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import com.android.internal.R;

@RemoteViews.RemoteView
@Deprecated
public class AbsoluteLayout
extends ViewGroup {
    public AbsoluteLayout(Context context) {
        this(context, null);
    }

    public AbsoluteLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AbsoluteLayout(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public AbsoluteLayout(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2, 0, 0);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        n2 = this.getChildCount();
        for (n = 0; n < n2; ++n) {
            View view = this.getChildAt(n);
            if (view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            n3 = this.mPaddingLeft + layoutParams.x;
            n4 = this.mPaddingTop + layoutParams.y;
            view.layout(n3, n4, view.getMeasuredWidth() + n3, view.getMeasuredHeight() + n4);
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        int n5 = this.getChildCount();
        int n6 = 0;
        int n7 = 0;
        this.measureChildren(n, n2);
        for (n4 = 0; n4 < n5; ++n4) {
            View view = this.getChildAt(n4);
            int n8 = n6;
            n3 = n7;
            if (view.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                n3 = layoutParams.x;
                int n9 = view.getMeasuredWidth();
                int n10 = layoutParams.y;
                n8 = view.getMeasuredHeight();
                n3 = Math.max(n7, n3 + n9);
                n8 = Math.max(n6, n10 + n8);
            }
            n6 = n8;
            n7 = n3;
        }
        n4 = this.mPaddingLeft;
        n3 = this.mPaddingRight;
        n6 = Math.max(n6 + (this.mPaddingTop + this.mPaddingBottom), this.getSuggestedMinimumHeight());
        this.setMeasuredDimension(AbsoluteLayout.resolveSizeAndState(Math.max(n7 + (n4 + n3), this.getSuggestedMinimumWidth()), n, 0), AbsoluteLayout.resolveSizeAndState(n6, n2, 0));
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public static class LayoutParams
    extends ViewGroup.LayoutParams {
        public int x;
        public int y;

        public LayoutParams(int n, int n2, int n3, int n4) {
            super(n, n2);
            this.x = n3;
            this.y = n4;
        }

        public LayoutParams(Context object, AttributeSet attributeSet) {
            super((Context)object, attributeSet);
            object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.AbsoluteLayout_Layout);
            this.x = ((TypedArray)object).getDimensionPixelOffset(0, 0);
            this.y = ((TypedArray)object).getDimensionPixelOffset(1, 0);
            ((TypedArray)object).recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        @Override
        public String debug(String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("Absolute.LayoutParams={width=");
            stringBuilder.append(LayoutParams.sizeToString(this.width));
            stringBuilder.append(", height=");
            stringBuilder.append(LayoutParams.sizeToString(this.height));
            stringBuilder.append(" x=");
            stringBuilder.append(this.x);
            stringBuilder.append(" y=");
            stringBuilder.append(this.y);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

}

