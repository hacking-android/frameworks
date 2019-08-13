/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.Calendar
 */
package android.widget;

import android.content.Context;
import android.graphics.Rect;
import android.icu.util.Calendar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DayPickerView;

class CalendarViewMaterialDelegate
extends CalendarView.AbstractCalendarViewDelegate {
    private final DayPickerView mDayPickerView;
    private CalendarView.OnDateChangeListener mOnDateChangeListener;
    private final DayPickerView.OnDaySelectedListener mOnDaySelectedListener = new DayPickerView.OnDaySelectedListener(){

        @Override
        public void onDaySelected(DayPickerView dayPickerView, Calendar calendar) {
            if (CalendarViewMaterialDelegate.this.mOnDateChangeListener != null) {
                int n = calendar.get(1);
                int n2 = calendar.get(2);
                int n3 = calendar.get(5);
                CalendarViewMaterialDelegate.this.mOnDateChangeListener.onSelectedDayChange(CalendarViewMaterialDelegate.this.mDelegator, n, n2, n3);
            }
        }
    };

    public CalendarViewMaterialDelegate(CalendarView calendarView, Context context, AttributeSet attributeSet, int n, int n2) {
        super(calendarView, context);
        this.mDayPickerView = new DayPickerView(context, attributeSet, n, n2);
        this.mDayPickerView.setOnDaySelectedListener(this.mOnDaySelectedListener);
        calendarView.addView(this.mDayPickerView);
    }

    @Override
    public boolean getBoundsForDate(long l, Rect rect) {
        if (this.mDayPickerView.getBoundsForDate(l, rect)) {
            int[] arrn = new int[2];
            int[] arrn2 = new int[2];
            this.mDayPickerView.getLocationOnScreen(arrn);
            this.mDelegator.getLocationOnScreen(arrn2);
            int n = arrn[1] - arrn2[1];
            rect.top += n;
            rect.bottom += n;
            return true;
        }
        return false;
    }

    @Override
    public long getDate() {
        return this.mDayPickerView.getDate();
    }

    @Override
    public int getDateTextAppearance() {
        return this.mDayPickerView.getDayTextAppearance();
    }

    @Override
    public int getFirstDayOfWeek() {
        return this.mDayPickerView.getFirstDayOfWeek();
    }

    @Override
    public long getMaxDate() {
        return this.mDayPickerView.getMaxDate();
    }

    @Override
    public long getMinDate() {
        return this.mDayPickerView.getMinDate();
    }

    @Override
    public int getWeekDayTextAppearance() {
        return this.mDayPickerView.getDayOfWeekTextAppearance();
    }

    @Override
    public void setDate(long l) {
        this.mDayPickerView.setDate(l, true);
    }

    @Override
    public void setDate(long l, boolean bl, boolean bl2) {
        this.mDayPickerView.setDate(l, bl);
    }

    @Override
    public void setDateTextAppearance(int n) {
        this.mDayPickerView.setDayTextAppearance(n);
    }

    @Override
    public void setFirstDayOfWeek(int n) {
        this.mDayPickerView.setFirstDayOfWeek(n);
    }

    @Override
    public void setMaxDate(long l) {
        this.mDayPickerView.setMaxDate(l);
    }

    @Override
    public void setMinDate(long l) {
        this.mDayPickerView.setMinDate(l);
    }

    @Override
    public void setOnDateChangeListener(CalendarView.OnDateChangeListener onDateChangeListener) {
        this.mOnDateChangeListener = onDateChangeListener;
    }

    @Override
    public void setWeekDayTextAppearance(int n) {
        this.mDayPickerView.setDayOfWeekTextAppearance(n);
    }

}

