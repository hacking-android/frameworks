/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.Calendar
 */
package android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.icu.util.Calendar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleMonthView;
import com.android.internal.widget.PagerAdapter;

class DayPickerPagerAdapter
extends PagerAdapter {
    private static final int MONTHS_IN_YEAR = 12;
    private ColorStateList mCalendarTextColor;
    private final int mCalendarViewId;
    private int mCount;
    private ColorStateList mDayHighlightColor;
    private int mDayOfWeekTextAppearance;
    private ColorStateList mDaySelectorColor;
    private int mDayTextAppearance;
    private int mFirstDayOfWeek;
    private final LayoutInflater mInflater;
    private final SparseArray<ViewHolder> mItems = new SparseArray();
    private final int mLayoutResId;
    private final Calendar mMaxDate = Calendar.getInstance();
    private final Calendar mMinDate = Calendar.getInstance();
    private int mMonthTextAppearance;
    private final SimpleMonthView.OnDayClickListener mOnDayClickListener = new SimpleMonthView.OnDayClickListener(){

        @Override
        public void onDayClick(SimpleMonthView simpleMonthView, Calendar calendar) {
            if (calendar != null) {
                DayPickerPagerAdapter.this.setSelectedDay(calendar);
                if (DayPickerPagerAdapter.this.mOnDaySelectedListener != null) {
                    DayPickerPagerAdapter.this.mOnDaySelectedListener.onDaySelected(DayPickerPagerAdapter.this, calendar);
                }
            }
        }
    };
    private OnDaySelectedListener mOnDaySelectedListener;
    private Calendar mSelectedDay = null;

    public DayPickerPagerAdapter(Context object, int n, int n2) {
        this.mInflater = LayoutInflater.from((Context)object);
        this.mLayoutResId = n;
        this.mCalendarViewId = n2;
        object = ((Context)object).obtainStyledAttributes(new int[]{16843820});
        this.mDayHighlightColor = ((TypedArray)object).getColorStateList(0);
        ((TypedArray)object).recycle();
    }

    private int getMonthForPosition(int n) {
        return (this.mMinDate.get(2) + n) % 12;
    }

    private int getPositionForDay(Calendar calendar) {
        if (calendar == null) {
            return -1;
        }
        return (calendar.get(1) - this.mMinDate.get(1)) * 12 + (calendar.get(2) - this.mMinDate.get(2));
    }

    private int getYearForPosition(int n) {
        n = (this.mMinDate.get(2) + n) / 12;
        return this.mMinDate.get(1) + n;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int n, Object object) {
        viewGroup.removeView(((ViewHolder)object).container);
        this.mItems.remove(n);
    }

    public boolean getBoundsForDate(Calendar calendar, Rect rect) {
        int n = this.getPositionForDay(calendar);
        ViewHolder viewHolder = this.mItems.get(n, null);
        if (viewHolder == null) {
            return false;
        }
        n = calendar.get(5);
        return viewHolder.calendar.getBoundsForDay(n, rect);
    }

    @Override
    public int getCount() {
        return this.mCount;
    }

    int getDayOfWeekTextAppearance() {
        return this.mDayOfWeekTextAppearance;
    }

    int getDayTextAppearance() {
        return this.mDayTextAppearance;
    }

    public int getFirstDayOfWeek() {
        return this.mFirstDayOfWeek;
    }

    @Override
    public int getItemPosition(Object object) {
        return ((ViewHolder)object).position;
    }

    @Override
    public CharSequence getPageTitle(int n) {
        SimpleMonthView simpleMonthView = this.mItems.get((int)n).calendar;
        if (simpleMonthView != null) {
            return simpleMonthView.getMonthYearLabel();
        }
        return null;
    }

    SimpleMonthView getView(Object object) {
        if (object == null) {
            return null;
        }
        return ((ViewHolder)object).calendar;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int n) {
        View view = this.mInflater.inflate(this.mLayoutResId, viewGroup, false);
        Object object = (SimpleMonthView)view.findViewById(this.mCalendarViewId);
        ((SimpleMonthView)object).setOnDayClickListener(this.mOnDayClickListener);
        ((SimpleMonthView)object).setMonthTextAppearance(this.mMonthTextAppearance);
        ((SimpleMonthView)object).setDayOfWeekTextAppearance(this.mDayOfWeekTextAppearance);
        ((SimpleMonthView)object).setDayTextAppearance(this.mDayTextAppearance);
        ColorStateList colorStateList = this.mDaySelectorColor;
        if (colorStateList != null) {
            ((SimpleMonthView)object).setDaySelectorColor(colorStateList);
        }
        if ((colorStateList = this.mDayHighlightColor) != null) {
            ((SimpleMonthView)object).setDayHighlightColor(colorStateList);
        }
        if ((colorStateList = this.mCalendarTextColor) != null) {
            ((SimpleMonthView)object).setMonthTextColor(colorStateList);
            ((SimpleMonthView)object).setDayOfWeekTextColor(this.mCalendarTextColor);
            ((SimpleMonthView)object).setDayTextColor(this.mCalendarTextColor);
        }
        int n2 = this.getMonthForPosition(n);
        int n3 = this.getYearForPosition(n);
        colorStateList = this.mSelectedDay;
        int n4 = colorStateList != null && colorStateList.get(2) == n2 && this.mSelectedDay.get(1) == n3 ? this.mSelectedDay.get(5) : -1;
        int n5 = this.mMinDate.get(2) == n2 && this.mMinDate.get(1) == n3 ? this.mMinDate.get(5) : 1;
        int n6 = this.mMaxDate.get(2) == n2 && this.mMaxDate.get(1) == n3 ? this.mMaxDate.get(5) : 31;
        ((SimpleMonthView)object).setMonthParams(n4, n2, n3, this.mFirstDayOfWeek, n5, n6);
        object = new ViewHolder(n, view, (SimpleMonthView)object);
        this.mItems.put(n, (ViewHolder)object);
        viewGroup.addView(view);
        return object;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        boolean bl = view == ((ViewHolder)object).container;
        return bl;
    }

    void setCalendarTextColor(ColorStateList colorStateList) {
        this.mCalendarTextColor = colorStateList;
        this.notifyDataSetChanged();
    }

    void setDayOfWeekTextAppearance(int n) {
        this.mDayOfWeekTextAppearance = n;
        this.notifyDataSetChanged();
    }

    void setDaySelectorColor(ColorStateList colorStateList) {
        this.mDaySelectorColor = colorStateList;
        this.notifyDataSetChanged();
    }

    void setDayTextAppearance(int n) {
        this.mDayTextAppearance = n;
        this.notifyDataSetChanged();
    }

    public void setFirstDayOfWeek(int n) {
        this.mFirstDayOfWeek = n;
        int n2 = this.mItems.size();
        for (int i = 0; i < n2; ++i) {
            this.mItems.valueAt((int)i).calendar.setFirstDayOfWeek(n);
        }
    }

    void setMonthTextAppearance(int n) {
        this.mMonthTextAppearance = n;
        this.notifyDataSetChanged();
    }

    public void setOnDaySelectedListener(OnDaySelectedListener onDaySelectedListener) {
        this.mOnDaySelectedListener = onDaySelectedListener;
    }

    public void setRange(Calendar calendar, Calendar calendar2) {
        this.mMinDate.setTimeInMillis(calendar.getTimeInMillis());
        this.mMaxDate.setTimeInMillis(calendar2.getTimeInMillis());
        this.mCount = (this.mMaxDate.get(1) - this.mMinDate.get(1)) * 12 + (this.mMaxDate.get(2) - this.mMinDate.get(2)) + 1;
        this.notifyDataSetChanged();
    }

    public void setSelectedDay(Calendar calendar) {
        ViewHolder viewHolder;
        int n;
        int n2 = this.getPositionForDay(this.mSelectedDay);
        if (n2 != (n = this.getPositionForDay(calendar)) && n2 >= 0 && (viewHolder = (ViewHolder)this.mItems.get(n2, null)) != null) {
            viewHolder.calendar.setSelectedDay(-1);
        }
        if (n >= 0 && (viewHolder = (ViewHolder)this.mItems.get(n, null)) != null) {
            n = calendar.get(5);
            viewHolder.calendar.setSelectedDay(n);
        }
        this.mSelectedDay = calendar;
    }

    public static interface OnDaySelectedListener {
        public void onDaySelected(DayPickerPagerAdapter var1, Calendar var2);
    }

    private static class ViewHolder {
        public final SimpleMonthView calendar;
        public final View container;
        public final int position;

        public ViewHolder(int n, View view, SimpleMonthView simpleMonthView) {
            this.position = n;
            this.container = view;
            this.calendar = simpleMonthView;
        }
    }

}

