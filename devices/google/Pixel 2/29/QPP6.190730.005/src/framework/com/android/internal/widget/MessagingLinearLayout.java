/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import com.android.internal.R;
import com.android.internal.widget.MessagingLayout;

@RemoteViews.RemoteView
public class MessagingLinearLayout
extends ViewGroup {
    private int mMaxDisplayedLines = Integer.MAX_VALUE;
    private MessagingLayout mMessagingLayout;
    private int mSpacing;

    public MessagingLinearLayout(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.MessagingLinearLayout, 0, 0);
        int n = ((TypedArray)object).getIndexCount();
        for (int i = 0; i < n; ++i) {
            if (((TypedArray)object).getIndex(i) != 0) continue;
            this.mSpacing = ((TypedArray)object).getDimensionPixelSize(i, 0);
        }
        ((TypedArray)object).recycle();
    }

    public static boolean isGone(View object) {
        if (((View)object).getVisibility() == 8) {
            return true;
        }
        return (object = ((View)object).getLayoutParams()) instanceof LayoutParams && ((LayoutParams)object).hide;
    }

    @Override
    protected boolean drawChild(Canvas canvas, View view, long l) {
        if (((LayoutParams)view.getLayoutParams()).hide && !((MessagingChild)((Object)view)).isHidingAnimated()) {
            return true;
        }
        return super.drawChild(canvas, view, l);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.mContext, attributeSet);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        LayoutParams layoutParams2 = new LayoutParams(layoutParams.width, layoutParams.height);
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            layoutParams2.copyMarginsFrom((ViewGroup.MarginLayoutParams)layoutParams);
        }
        return layoutParams2;
    }

    public MessagingLayout getMessagingLayout() {
        return this.mMessagingLayout;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        n4 = this.mPaddingLeft;
        int n5 = n3 - n;
        int n6 = this.mPaddingRight;
        int n7 = this.getLayoutDirection();
        int n8 = this.getChildCount();
        n = this.mPaddingTop;
        boolean bl2 = true;
        bl = this.isShown();
        n2 = n5;
        for (n3 = 0; n3 < n8; ++n3) {
            View view = this.getChildAt(n3);
            if (view.getVisibility() == 8) continue;
            LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            MessagingChild messagingChild = (MessagingChild)((Object)view);
            int n9 = view.getMeasuredWidth();
            int n10 = view.getMeasuredHeight();
            int n11 = n7 == 1 ? n5 - n6 - n9 - layoutParams.rightMargin : n4 + layoutParams.leftMargin;
            if (layoutParams.hide) {
                if (bl && layoutParams.visibleBefore) {
                    view.layout(n11, n, n11 + n9, layoutParams.lastVisibleHeight + n);
                    messagingChild.hideAnimated();
                }
                layoutParams.visibleBefore = false;
                continue;
            }
            layoutParams.visibleBefore = true;
            layoutParams.lastVisibleHeight = n10;
            int n12 = n;
            if (!bl2) {
                n12 = n + this.mSpacing;
            }
            n = n12 + layoutParams.topMargin;
            view.layout(n11, n, n11 + n9, n + n10);
            n += layoutParams.bottomMargin + n10;
            bl2 = false;
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        int n5 = View.MeasureSpec.getSize(n2);
        if (View.MeasureSpec.getMode(n2) == 0) {
            n5 = Integer.MAX_VALUE;
        }
        int n6 = this.mPaddingLeft;
        int n7 = this.mPaddingRight;
        int n8 = this.getChildCount();
        for (n4 = 0; n4 < n8; ++n4) {
            ((LayoutParams)this.getChildAt((int)n4).getLayoutParams()).hide = true;
        }
        int n9 = this.mPaddingTop;
        int n10 = this.mPaddingBottom;
        int n11 = this.mMaxDisplayedLines;
        n4 = n6 + n7;
        n6 = n9 + n10;
        n9 = 1;
        n7 = n8 - 1;
        do {
            n3 = n6;
            n10 = n4;
            if (n7 < 0) break;
            n3 = n6;
            n10 = n4;
            if (n6 >= n5) break;
            if (this.getChildAt(n7).getVisibility() != 8) {
                MessagingChild messagingChild;
                View view = this.getChildAt(n7);
                LayoutParams layoutParams = (LayoutParams)this.getChildAt(n7).getLayoutParams();
                n8 = this.mSpacing;
                if (view instanceof MessagingChild) {
                    messagingChild = (MessagingChild)((Object)view);
                    messagingChild.setMaxDisplayedLines(n11);
                    n8 += messagingChild.getExtraSpacing();
                } else {
                    messagingChild = null;
                }
                if (n9 != 0) {
                    n8 = 0;
                }
                this.measureChildWithMargins(view, n, 0, n2, n6 - this.mPaddingTop - this.mPaddingBottom + n8);
                int n12 = Math.max(n6, n6 + view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin + n8);
                n10 = 0;
                n8 = n11;
                if (messagingChild != null) {
                    n10 = messagingChild.getMeasuredType();
                    n8 = n11 - messagingChild.getConsumedLines();
                }
                n11 = n10 == 2 && n9 == 0 ? 1 : 0;
                n9 = n10 != 1 && (n10 != 2 || n9 == 0) ? 0 : 1;
                n3 = n6;
                n10 = n4;
                if (n12 > n5) break;
                n3 = n6;
                n10 = n4;
                if (n11 != 0) break;
                n6 = n12;
                n4 = Math.max(n4, view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin + this.mPaddingLeft + this.mPaddingRight);
                layoutParams.hide = false;
                n3 = n6;
                n10 = n4;
                if (n9 != 0) break;
                if (n8 <= 0) {
                    n3 = n6;
                    n10 = n4;
                    break;
                }
                n9 = 0;
                n11 = n8;
            }
            --n7;
        } while (true);
        this.setMeasuredDimension(MessagingLinearLayout.resolveSize(Math.max(this.getSuggestedMinimumWidth(), n10), n), Math.max(this.getSuggestedMinimumHeight(), n3));
    }

    @RemotableViewMethod
    public void setMaxDisplayedLines(int n) {
        this.mMaxDisplayedLines = n;
    }

    public void setMessagingLayout(MessagingLayout messagingLayout) {
        this.mMessagingLayout = messagingLayout;
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public boolean hide = false;
        public int lastVisibleHeight;
        public boolean visibleBefore = false;

        public LayoutParams(int n, int n2) {
            super(n, n2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    public static interface MessagingChild {
        public static final int MEASURED_NORMAL = 0;
        public static final int MEASURED_SHORTENED = 1;
        public static final int MEASURED_TOO_SMALL = 2;

        public int getConsumedLines();

        default public int getExtraSpacing() {
            return 0;
        }

        public int getMeasuredType();

        public void hideAnimated();

        public boolean isHidingAnimated();

        public void setMaxDisplayedLines(int var1);
    }

}

