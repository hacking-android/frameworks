/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DayPickerPagerAdapter;
import android.widget.SimpleMonthView;
import com.android.internal.widget.PagerAdapter;
import com.android.internal.widget.ViewPager;
import java.util.ArrayList;
import java.util.function.Predicate;

class DayPickerViewPager
extends ViewPager {
    private final ArrayList<View> mMatchParentChildren = new ArrayList(1);

    public DayPickerViewPager(Context context) {
        this(context, null);
    }

    public DayPickerViewPager(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DayPickerViewPager(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public DayPickerViewPager(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    @Override
    protected <T extends View> T findViewByPredicateTraversal(Predicate<View> predicate, View view) {
        Object object;
        if (predicate.test(this)) {
            return (T)this;
        }
        SimpleMonthView simpleMonthView = ((DayPickerPagerAdapter)this.getAdapter()).getView(this.getCurrent());
        if (simpleMonthView != view && simpleMonthView != null && (object = simpleMonthView.findViewByPredicate(predicate)) != null) {
            return object;
        }
        int n = this.getChildCount();
        for (int i = 0; i < n; ++i) {
            object = this.getChildAt(i);
            if (object == view || object == simpleMonthView || (object = ((View)object).findViewByPredicate(predicate)) == null) continue;
            return object;
        }
        return null;
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        View view;
        Object object;
        int n4;
        this.populate();
        int n5 = this.getChildCount();
        int n6 = View.MeasureSpec.getMode(n) == 1073741824 && View.MeasureSpec.getMode(n2) == 1073741824 ? 0 : 1;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        for (n3 = 0; n3 < n5; ++n3) {
            int n10;
            int n11;
            block7 : {
                block8 : {
                    view = this.getChildAt(n3);
                    n11 = n7;
                    n10 = n8;
                    n4 = n9;
                    if (view.getVisibility() == 8) break block7;
                    this.measureChild(view, n, n2);
                    object = (ViewPager.LayoutParams)view.getLayoutParams();
                    n8 = Math.max(n8, view.getMeasuredWidth());
                    n7 = Math.max(n7, view.getMeasuredHeight());
                    n9 = DayPickerViewPager.combineMeasuredStates(n9, view.getMeasuredState());
                    n11 = n7;
                    n10 = n8;
                    n4 = n9;
                    if (n6 == 0) break block7;
                    if (((ViewPager.LayoutParams)object).width == -1) break block8;
                    n11 = n7;
                    n10 = n8;
                    n4 = n9;
                    if (((ViewPager.LayoutParams)object).height != -1) break block7;
                }
                this.mMatchParentChildren.add(view);
                n4 = n9;
                n10 = n8;
                n11 = n7;
            }
            n7 = n11;
            n8 = n10;
            n9 = n4;
        }
        n3 = this.getPaddingLeft();
        n6 = this.getPaddingRight();
        n4 = Math.max(n7 + (this.getPaddingTop() + this.getPaddingBottom()), this.getSuggestedMinimumHeight());
        n8 = Math.max(n8 + (n3 + n6), this.getSuggestedMinimumWidth());
        object = this.getForeground();
        n3 = n4;
        n6 = n8;
        if (object != null) {
            n3 = Math.max(n4, ((Drawable)object).getMinimumHeight());
            n6 = Math.max(n8, ((Drawable)object).getMinimumWidth());
        }
        this.setMeasuredDimension(DayPickerViewPager.resolveSizeAndState(n6, n, n9), DayPickerViewPager.resolveSizeAndState(n3, n2, n9 << 16));
        n8 = this.mMatchParentChildren.size();
        if (n8 > 1) {
            for (n9 = 0; n9 < n8; ++n9) {
                view = this.mMatchParentChildren.get(n9);
                object = (ViewPager.LayoutParams)view.getLayoutParams();
                n6 = ((ViewPager.LayoutParams)object).width == -1 ? View.MeasureSpec.makeMeasureSpec(this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight(), 1073741824) : DayPickerViewPager.getChildMeasureSpec(n, this.getPaddingLeft() + this.getPaddingRight(), ((ViewPager.LayoutParams)object).width);
                n3 = ((ViewPager.LayoutParams)object).height == -1 ? View.MeasureSpec.makeMeasureSpec(this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom(), 1073741824) : DayPickerViewPager.getChildMeasureSpec(n2, this.getPaddingTop() + this.getPaddingBottom(), ((ViewPager.LayoutParams)object).height);
                view.measure(n6, n3);
            }
        }
        this.mMatchParentChildren.clear();
    }
}

