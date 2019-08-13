/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.widget.-$
 *  com.android.internal.widget.-$$Lambda
 *  com.android.internal.widget.-$$Lambda$NotificationActionListLayout
 *  com.android.internal.widget.-$$Lambda$NotificationActionListLayout$uFZFEmIEBpI3kn6c3tNvvgmMSv8
 */
package com.android.internal.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.Gravity;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.android.internal.widget.-$;
import com.android.internal.widget._$$Lambda$NotificationActionListLayout$uFZFEmIEBpI3kn6c3tNvvgmMSv8;
import java.util.ArrayList;
import java.util.Comparator;

@RemoteViews.RemoteView
public class NotificationActionListLayout
extends LinearLayout {
    public static final Comparator<Pair<Integer, TextView>> MEASURE_ORDER_COMPARATOR = _$$Lambda$NotificationActionListLayout$uFZFEmIEBpI3kn6c3tNvvgmMSv8.INSTANCE;
    private int mDefaultPaddingBottom;
    private int mDefaultPaddingTop;
    private int mEmphasizedHeight;
    private boolean mEmphasizedMode;
    private final int mGravity;
    private ArrayList<View> mMeasureOrderOther = new ArrayList();
    private ArrayList<Pair<Integer, TextView>> mMeasureOrderTextViews = new ArrayList();
    private int mRegularHeight;
    private int mTotalWidth = 0;

    public NotificationActionListLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NotificationActionListLayout(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public NotificationActionListLayout(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        object = ((Context)object).obtainStyledAttributes(attributeSet, new int[]{16842927}, n, n2);
        this.mGravity = ((TypedArray)object).getInt(0, 0);
        ((TypedArray)object).recycle();
    }

    private void clearMeasureOrder() {
        this.mMeasureOrderOther.clear();
        this.mMeasureOrderTextViews.clear();
    }

    static /* synthetic */ int lambda$static$0(Pair pair, Pair pair2) {
        return ((Integer)pair.first).compareTo((Integer)pair2.first);
    }

    private void rebuildMeasureOrder(int n, int n2) {
        this.clearMeasureOrder();
        this.mMeasureOrderTextViews.ensureCapacity(n);
        this.mMeasureOrderOther.ensureCapacity(n2);
        n2 = this.getChildCount();
        for (n = 0; n < n2; ++n) {
            View view = this.getChildAt(n);
            if (view instanceof TextView && ((TextView)view).getText().length() > 0) {
                this.mMeasureOrderTextViews.add(Pair.create(((TextView)view).getText().length(), (TextView)view));
                continue;
            }
            this.mMeasureOrderOther.add(view);
        }
        this.mMeasureOrderTextViews.sort(MEASURE_ORDER_COMPARATOR);
    }

    private void updateHeights() {
        int n = this.getResources().getDimensionPixelSize(17105306);
        this.mEmphasizedHeight = this.getResources().getDimensionPixelSize(17105307) + n + this.getResources().getDimensionPixelSize(17105296);
        this.mRegularHeight = this.getResources().getDimensionPixelSize(17105297);
    }

    public int getExtraMeasureHeight() {
        if (this.mEmphasizedMode) {
            return this.mEmphasizedHeight - this.mRegularHeight;
        }
        return 0;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mDefaultPaddingBottom = this.getPaddingBottom();
        this.mDefaultPaddingTop = this.getPaddingTop();
        this.updateHeights();
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        View view = this;
        if (((NotificationActionListLayout)view).mEmphasizedMode) {
            super.onLayout(bl, n, n2, n3, n4);
            return;
        }
        bl = this.isLayoutRtl();
        int n5 = ((NotificationActionListLayout)view).mPaddingTop;
        int n6 = ((NotificationActionListLayout)view).mGravity;
        int n7 = 1;
        if ((n6 & 1) == 0) {
            n7 = 0;
        }
        if (n7 != 0) {
            n7 = ((NotificationActionListLayout)view).mPaddingLeft + n + (n3 - n) / 2 - ((NotificationActionListLayout)view).mTotalWidth / 2;
        } else {
            n7 = n6 = ((NotificationActionListLayout)view).mPaddingLeft;
            if (Gravity.getAbsoluteGravity(8388611, this.getLayoutDirection()) == 5) {
                n7 = n6 + (n3 - n - ((NotificationActionListLayout)view).mTotalWidth);
            }
        }
        int n8 = ((NotificationActionListLayout)view).mPaddingBottom;
        int n9 = this.getChildCount();
        n6 = 0;
        n3 = 1;
        if (bl) {
            n6 = n9 - 1;
            n3 = -1;
        }
        n = n5;
        for (int i = 0; i < n9; ++i) {
            view = this.getChildAt(n3 * i + n6);
            if (view.getVisibility() == 8) continue;
            int n10 = view.getMeasuredWidth();
            int n11 = view.getMeasuredHeight();
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
            int n12 = n + (n4 - n2 - n5 - n8 - n11) / 2 + marginLayoutParams.topMargin - marginLayoutParams.bottomMargin;
            view.layout(n7 += marginLayoutParams.leftMargin, n12, n7 + n10, n12 + n11);
            n7 += marginLayoutParams.rightMargin + n10;
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        int n4;
        int n5;
        Object object;
        if (this.mEmphasizedMode) {
            super.onMeasure(n, n2);
            return;
        }
        int n6 = this.getChildCount();
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        for (n4 = 0; n4 < n6; ++n4) {
            object = this.getChildAt(n4);
            if (object instanceof TextView) {
                ++n7;
            } else {
                ++n8;
            }
            n3 = n9;
            if (((View)object).getVisibility() != 8) {
                n3 = n9 + 1;
            }
            n9 = n3;
        }
        n4 = 0;
        if (n7 != this.mMeasureOrderTextViews.size() || n8 != this.mMeasureOrderOther.size()) {
            n4 = 1;
        }
        if (n4 == 0) {
            n5 = this.mMeasureOrderTextViews.size();
            for (n3 = 0; n3 < n5; ++n3) {
                object = this.mMeasureOrderTextViews.get(n3);
                if (((Integer)((Pair)object).first).intValue() == ((TextView)((Pair)object).second).getText().length()) continue;
                n4 = 1;
            }
        }
        if (n4 != 0) {
            this.rebuildMeasureOrder(n7, n8);
        }
        n8 = View.MeasureSpec.getMode(n) != 0 ? 1 : 0;
        int n10 = View.MeasureSpec.getSize(n) - this.mPaddingLeft - this.mPaddingRight;
        int n11 = this.mMeasureOrderOther.size();
        n4 = 0;
        n7 = 0;
        for (n3 = 0; n3 < n6; ++n3) {
            object = n3 < n11 ? this.mMeasureOrderOther.get(n3) : (View)this.mMeasureOrderTextViews.get((int)(n3 - n11)).second;
            if (((View)object).getVisibility() == 8) continue;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)((View)object).getLayoutParams();
            n5 = n8 != 0 ? n10 - (n10 - n4) / (n9 - n7) : n4;
            this.measureChildWithMargins((View)object, n, n5, n2, 0);
            n4 += ((View)object).getMeasuredWidth() + marginLayoutParams.rightMargin + marginLayoutParams.leftMargin;
            ++n7;
        }
        this.mTotalWidth = n4 + this.mPaddingRight + this.mPaddingLeft;
        this.setMeasuredDimension(NotificationActionListLayout.resolveSize(this.getSuggestedMinimumWidth(), n), NotificationActionListLayout.resolveSize(this.getSuggestedMinimumHeight(), n2));
    }

    @Override
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        this.clearMeasureOrder();
        if (view.getBackground() instanceof RippleDrawable) {
            ((RippleDrawable)view.getBackground()).setForceSoftware(true);
        }
    }

    @Override
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        this.clearMeasureOrder();
    }

    @RemotableViewMethod
    public void setEmphasizedMode(boolean bl) {
        int n;
        this.mEmphasizedMode = bl;
        if (bl) {
            int n2 = this.getResources().getDimensionPixelSize(17105306);
            int n3 = this.getResources().getDimensionPixelSize(17105307);
            n = this.mEmphasizedHeight;
            int n4 = this.getResources().getDimensionPixelSize(17104961);
            this.setPaddingRelative(this.getPaddingStart(), n2 - n4, this.getPaddingEnd(), n3 - n4);
        } else {
            this.setPaddingRelative(this.getPaddingStart(), this.mDefaultPaddingTop, this.getPaddingEnd(), this.mDefaultPaddingBottom);
            n = this.mRegularHeight;
        }
        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
        layoutParams.height = n;
        this.setLayoutParams(layoutParams);
    }
}

