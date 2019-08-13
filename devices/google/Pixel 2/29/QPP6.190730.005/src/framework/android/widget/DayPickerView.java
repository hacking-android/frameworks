/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.Calendar
 *  libcore.icu.LocaleData
 */
package android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.icu.util.Calendar;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.CalendarView;
import android.widget.DayPickerPagerAdapter;
import android.widget.ImageButton;
import android.widget.SimpleMonthView;
import com.android.internal.R;
import com.android.internal.widget.PagerAdapter;
import com.android.internal.widget.ViewPager;
import java.util.Locale;
import libcore.icu.LocaleData;

class DayPickerView
extends ViewGroup {
    private static final int[] ATTRS_TEXT_COLOR = new int[]{16842904};
    private static final int DEFAULT_END_YEAR = 2100;
    private static final int DEFAULT_LAYOUT = 17367136;
    private static final int DEFAULT_START_YEAR = 1900;
    private final AccessibilityManager mAccessibilityManager;
    private final DayPickerPagerAdapter mAdapter;
    private final Calendar mMaxDate = Calendar.getInstance();
    private final Calendar mMinDate = Calendar.getInstance();
    private final ImageButton mNextButton;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            block4 : {
                int n;
                block3 : {
                    block2 : {
                        if (view != DayPickerView.this.mPrevButton) break block2;
                        n = -1;
                        break block3;
                    }
                    if (view != DayPickerView.this.mNextButton) break block4;
                    n = 1;
                }
                boolean bl = DayPickerView.this.mAccessibilityManager.isEnabled();
                int n2 = DayPickerView.this.mViewPager.getCurrentItem();
                DayPickerView.this.mViewPager.setCurrentItem(n2 + n, bl ^ true);
                return;
            }
        }
    };
    private OnDaySelectedListener mOnDaySelectedListener;
    private final ViewPager.OnPageChangeListener mOnPageChangedListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrollStateChanged(int n) {
        }

        @Override
        public void onPageScrolled(int n, float f, int n2) {
            f = Math.abs(0.5f - f) * 2.0f;
            DayPickerView.this.mPrevButton.setAlpha(f);
            DayPickerView.this.mNextButton.setAlpha(f);
        }

        @Override
        public void onPageSelected(int n) {
            DayPickerView.this.updateButtonVisibility(n);
        }
    };
    private final ImageButton mPrevButton;
    private final Calendar mSelectedDay = Calendar.getInstance();
    private Calendar mTempCalendar;
    private final ViewPager mViewPager;

    public DayPickerView(Context context) {
        this(context, null);
    }

    public DayPickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843613);
    }

    public DayPickerView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public DayPickerView(Context object, AttributeSet object2, int n, int n2) {
        super((Context)object, (AttributeSet)object2, n, n2);
        long l;
        this.mAccessibilityManager = (AccessibilityManager)((Context)object).getSystemService("accessibility");
        Object object3 = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.CalendarView, n, n2);
        this.saveAttributeDataForStyleable((Context)object, R.styleable.CalendarView, (AttributeSet)object2, (TypedArray)object3, n, n2);
        int n3 = ((TypedArray)object3).getInt(0, LocaleData.get((Locale)Locale.getDefault()).firstDayOfWeek);
        object2 = ((TypedArray)object3).getString(2);
        String string2 = ((TypedArray)object3).getString(3);
        int n4 = ((TypedArray)object3).getResourceId(16, 16974788);
        n = ((TypedArray)object3).getResourceId(11, 16974787);
        n2 = ((TypedArray)object3).getResourceId(12, 16974786);
        Object object4 = ((TypedArray)object3).getColorStateList(15);
        ((TypedArray)object3).recycle();
        this.mAdapter = new DayPickerPagerAdapter((Context)object, 17367134, 16909129);
        this.mAdapter.setMonthTextAppearance(n4);
        this.mAdapter.setDayOfWeekTextAppearance(n);
        this.mAdapter.setDayTextAppearance(n2);
        this.mAdapter.setDaySelectorColor((ColorStateList)object4);
        object = LayoutInflater.from((Context)object);
        object3 = (ViewGroup)((LayoutInflater)object).inflate(17367136, (ViewGroup)this, false);
        while (((ViewGroup)object3).getChildCount() > 0) {
            object4 = ((ViewGroup)object3).getChildAt(0);
            ((ViewGroup)object3).removeViewAt(0);
            this.addView((View)object4);
        }
        this.mPrevButton = (ImageButton)this.findViewById(16909254);
        this.mPrevButton.setOnClickListener(this.mOnClickListener);
        this.mNextButton = (ImageButton)this.findViewById(16909144);
        this.mNextButton.setOnClickListener(this.mOnClickListener);
        this.mViewPager = (ViewPager)this.findViewById(16908867);
        this.mViewPager.setAdapter(this.mAdapter);
        this.mViewPager.setOnPageChangeListener(this.mOnPageChangedListener);
        if (n4 != 0) {
            object3 = this.mContext.obtainStyledAttributes(null, ATTRS_TEXT_COLOR, 0, n4);
            object = ((TypedArray)object3).getColorStateList(0);
            if (object != null) {
                this.mPrevButton.setImageTintList((ColorStateList)object);
                this.mNextButton.setImageTintList((ColorStateList)object);
            }
            ((TypedArray)object3).recycle();
        }
        if (!CalendarView.parseDate((String)object2, (Calendar)(object = Calendar.getInstance()))) {
            object.set(1900, 0, 1);
        }
        long l2 = object.getTimeInMillis();
        if (!CalendarView.parseDate(string2, (Calendar)object)) {
            object.set(2100, 11, 31);
        }
        if ((l = object.getTimeInMillis()) >= l2) {
            long l3 = MathUtils.constrain(System.currentTimeMillis(), l2, l);
            this.setFirstDayOfWeek(n3);
            this.setMinDate(l2);
            this.setMaxDate(l);
            this.setDate(l3, false);
            this.mAdapter.setOnDaySelectedListener(new DayPickerPagerAdapter.OnDaySelectedListener(){

                @Override
                public void onDaySelected(DayPickerPagerAdapter dayPickerPagerAdapter, Calendar calendar) {
                    if (DayPickerView.this.mOnDaySelectedListener != null) {
                        DayPickerView.this.mOnDaySelectedListener.onDaySelected(DayPickerView.this, calendar);
                    }
                }
            });
            return;
        }
        throw new IllegalArgumentException("maxDate must be >= minDate");
    }

    private int getDiffMonths(Calendar calendar, Calendar calendar2) {
        int n = calendar2.get(1);
        int n2 = calendar.get(1);
        return calendar2.get(2) - calendar.get(2) + (n - n2) * 12;
    }

    private int getPositionFromDay(long l) {
        int n = this.getDiffMonths(this.mMinDate, this.mMaxDate);
        return MathUtils.constrain(this.getDiffMonths(this.mMinDate, this.getTempCalendarForTime(l)), 0, n);
    }

    private Calendar getTempCalendarForTime(long l) {
        if (this.mTempCalendar == null) {
            this.mTempCalendar = Calendar.getInstance();
        }
        this.mTempCalendar.setTimeInMillis(l);
        return this.mTempCalendar;
    }

    private void setDate(long l, boolean bl, boolean bl2) {
        long l2;
        int n = 0;
        if (l < this.mMinDate.getTimeInMillis()) {
            l2 = this.mMinDate.getTimeInMillis();
            n = 1;
        } else {
            l2 = l;
            if (l > this.mMaxDate.getTimeInMillis()) {
                l2 = this.mMaxDate.getTimeInMillis();
                n = 1;
            }
        }
        this.getTempCalendarForTime(l2);
        if (bl2 || n != 0) {
            this.mSelectedDay.setTimeInMillis(l2);
        }
        if ((n = this.getPositionFromDay(l2)) != this.mViewPager.getCurrentItem()) {
            this.mViewPager.setCurrentItem(n, bl);
        }
        this.mAdapter.setSelectedDay(this.mTempCalendar);
    }

    private void updateButtonVisibility(int n) {
        int n2 = 1;
        int n3 = 0;
        int n4 = n > 0 ? 1 : 0;
        n = n < this.mAdapter.getCount() - 1 ? n2 : 0;
        ImageButton imageButton = this.mPrevButton;
        n4 = n4 != 0 ? 0 : 4;
        imageButton.setVisibility(n4);
        imageButton = this.mNextButton;
        n = n != 0 ? n3 : 4;
        imageButton.setVisibility(n);
    }

    public boolean getBoundsForDate(long l, Rect rect) {
        if (this.getPositionFromDay(l) != this.mViewPager.getCurrentItem()) {
            return false;
        }
        this.mTempCalendar.setTimeInMillis(l);
        return this.mAdapter.getBoundsForDate(this.mTempCalendar, rect);
    }

    public long getDate() {
        return this.mSelectedDay.getTimeInMillis();
    }

    public int getDayOfWeekTextAppearance() {
        return this.mAdapter.getDayOfWeekTextAppearance();
    }

    public int getDayTextAppearance() {
        return this.mAdapter.getDayTextAppearance();
    }

    public int getFirstDayOfWeek() {
        return this.mAdapter.getFirstDayOfWeek();
    }

    public long getMaxDate() {
        return this.mMaxDate.getTimeInMillis();
    }

    public long getMinDate() {
        return this.mMinDate.getTimeInMillis();
    }

    public int getMostVisiblePosition() {
        return this.mViewPager.getCurrentItem();
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        ImageButton imageButton;
        ImageButton imageButton2;
        if (this.isLayoutRtl()) {
            imageButton = this.mNextButton;
            imageButton2 = this.mPrevButton;
        } else {
            imageButton = this.mPrevButton;
            imageButton2 = this.mNextButton;
        }
        n = n3 - n;
        this.mViewPager.layout(0, 0, n, n4 - n2);
        SimpleMonthView simpleMonthView = (SimpleMonthView)this.mViewPager.getChildAt(0);
        n3 = simpleMonthView.getMonthHeight();
        n2 = simpleMonthView.getCellWidth();
        int n5 = imageButton.getMeasuredWidth();
        n4 = imageButton.getMeasuredHeight();
        int n6 = simpleMonthView.getPaddingTop() + (n3 - n4) / 2;
        int n7 = simpleMonthView.getPaddingLeft() + (n2 - n5) / 2;
        imageButton.layout(n7, n6, n7 + n5, n6 + n4);
        n5 = imageButton2.getMeasuredWidth();
        n4 = imageButton2.getMeasuredHeight();
        n3 = simpleMonthView.getPaddingTop() + (n3 - n4) / 2;
        n = n - simpleMonthView.getPaddingRight() - (n2 - n5) / 2;
        imageButton2.layout(n - n5, n3, n, n3 + n4);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        ViewPager viewPager = this.mViewPager;
        this.measureChild(viewPager, n, n2);
        this.setMeasuredDimension(viewPager.getMeasuredWidthAndState(), viewPager.getMeasuredHeightAndState());
        n = viewPager.getMeasuredWidth();
        n2 = viewPager.getMeasuredHeight();
        n = View.MeasureSpec.makeMeasureSpec(n, Integer.MIN_VALUE);
        n2 = View.MeasureSpec.makeMeasureSpec(n2, Integer.MIN_VALUE);
        this.mPrevButton.measure(n, n2);
        this.mNextButton.measure(n, n2);
    }

    public void onRangeChanged() {
        this.mAdapter.setRange(this.mMinDate, this.mMaxDate);
        this.setDate(this.mSelectedDay.getTimeInMillis(), false, false);
        this.updateButtonVisibility(this.mViewPager.getCurrentItem());
    }

    @Override
    public void onRtlPropertiesChanged(int n) {
        super.onRtlPropertiesChanged(n);
        this.requestLayout();
    }

    public void setDate(long l) {
        this.setDate(l, false);
    }

    public void setDate(long l, boolean bl) {
        this.setDate(l, bl, true);
    }

    public void setDayOfWeekTextAppearance(int n) {
        this.mAdapter.setDayOfWeekTextAppearance(n);
    }

    public void setDayTextAppearance(int n) {
        this.mAdapter.setDayTextAppearance(n);
    }

    public void setFirstDayOfWeek(int n) {
        this.mAdapter.setFirstDayOfWeek(n);
    }

    public void setMaxDate(long l) {
        this.mMaxDate.setTimeInMillis(l);
        this.onRangeChanged();
    }

    public void setMinDate(long l) {
        this.mMinDate.setTimeInMillis(l);
        this.onRangeChanged();
    }

    public void setOnDaySelectedListener(OnDaySelectedListener onDaySelectedListener) {
        this.mOnDaySelectedListener = onDaySelectedListener;
    }

    public void setPosition(int n) {
        this.mViewPager.setCurrentItem(n, false);
    }

    public static interface OnDaySelectedListener {
        public void onDaySelected(DayPickerView var1, Calendar var2);
    }

}

