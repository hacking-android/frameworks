/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.DateFormat
 *  android.icu.text.DisplayContext
 *  android.icu.util.Calendar
 */
package android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.icu.util.Calendar;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.DayPickerView;
import android.widget.TextView;
import android.widget.ViewAnimator;
import android.widget.YearPickerView;
import android.widget._$$Lambda$DatePickerCalendarDelegate$GuCiuXPsIV2EU6oKGRXrsGY_DHM;
import android.widget._$$Lambda$DatePickerCalendarDelegate$_6rynvAYPe1gU9xVgvSm4VMsr2M;
import com.android.internal.R;
import java.util.Date;
import java.util.Locale;

class DatePickerCalendarDelegate
extends DatePicker.AbstractDatePickerDelegate {
    private static final int ANIMATION_DURATION = 300;
    private static final int[] ATTRS_DISABLED_ALPHA;
    private static final int[] ATTRS_TEXT_COLOR;
    private static final int DEFAULT_END_YEAR = 2100;
    private static final int DEFAULT_START_YEAR = 1900;
    private static final int UNINITIALIZED = -1;
    private static final int USE_LOCALE = 0;
    private static final int VIEW_MONTH_DAY = 0;
    private static final int VIEW_YEAR = 1;
    private ViewAnimator mAnimator;
    private ViewGroup mContainer;
    private int mCurrentView = -1;
    private DayPickerView mDayPickerView;
    private int mFirstDayOfWeek = 0;
    private TextView mHeaderMonthDay;
    private TextView mHeaderYear;
    private final Calendar mMaxDate;
    private final Calendar mMinDate;
    private DateFormat mMonthDayFormat;
    private final DayPickerView.OnDaySelectedListener mOnDaySelectedListener = new DayPickerView.OnDaySelectedListener(){

        @Override
        public void onDaySelected(DayPickerView dayPickerView, Calendar calendar) {
            DatePickerCalendarDelegate.this.mCurrentDate.setTimeInMillis(calendar.getTimeInMillis());
            DatePickerCalendarDelegate.this.onDateChanged(true, true);
        }
    };
    private final View.OnClickListener mOnHeaderClickListener = new _$$Lambda$DatePickerCalendarDelegate$GuCiuXPsIV2EU6oKGRXrsGY_DHM(this);
    private final YearPickerView.OnYearSelectedListener mOnYearSelectedListener = new YearPickerView.OnYearSelectedListener(){

        @Override
        public void onYearChanged(YearPickerView yearPickerView, int n) {
            int n2;
            int n3 = DatePickerCalendarDelegate.this.mCurrentDate.get(5);
            if (n3 > (n2 = DatePickerCalendarDelegate.getDaysInMonth(DatePickerCalendarDelegate.this.mCurrentDate.get(2), n))) {
                DatePickerCalendarDelegate.this.mCurrentDate.set(5, n2);
            }
            DatePickerCalendarDelegate.this.mCurrentDate.set(1, n);
            DatePickerCalendarDelegate.this.onDateChanged(true, true);
            DatePickerCalendarDelegate.this.setCurrentView(0);
            DatePickerCalendarDelegate.this.mHeaderYear.requestFocus();
        }
    };
    private String mSelectDay;
    private String mSelectYear;
    private final Calendar mTempDate;
    private DateFormat mYearFormat;
    private YearPickerView mYearPickerView;

    static {
        ATTRS_TEXT_COLOR = new int[]{16842904};
        ATTRS_DISABLED_ALPHA = new int[]{16842803};
    }

    public DatePickerCalendarDelegate(DatePicker object, Context object2, AttributeSet object3, int n, int n2) {
        super((DatePicker)object, (Context)object2);
        object = this.mCurrentLocale;
        this.mCurrentDate = Calendar.getInstance((Locale)object);
        this.mTempDate = Calendar.getInstance((Locale)object);
        this.mMinDate = Calendar.getInstance((Locale)object);
        this.mMaxDate = Calendar.getInstance((Locale)object);
        this.mMinDate.set(1900, 0, 1);
        this.mMaxDate.set(2100, 11, 31);
        Resources resources = this.mDelegator.getResources();
        TypedArray typedArray = this.mContext.obtainStyledAttributes((AttributeSet)object3, R.styleable.DatePicker, n, n2);
        this.mContainer = (ViewGroup)((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(typedArray.getResourceId(19, 17367133), (ViewGroup)this.mDelegator, false);
        this.mContainer.setSaveFromParentEnabled(false);
        this.mDelegator.addView(this.mContainer);
        object3 = (ViewGroup)this.mContainer.findViewById(16908860);
        this.mHeaderYear = (TextView)((View)object3).findViewById(16908862);
        this.mHeaderYear.setOnClickListener(this.mOnHeaderClickListener);
        this.mHeaderMonthDay = (TextView)((View)object3).findViewById(16908861);
        this.mHeaderMonthDay.setOnClickListener(this.mOnHeaderClickListener);
        object = null;
        n = typedArray.getResourceId(10, 0);
        if (n != 0) {
            object2 = this.mContext.obtainStyledAttributes(null, ATTRS_TEXT_COLOR, 0, n);
            object = this.applyLegacyColorFixes(((TypedArray)object2).getColorStateList(0));
            ((TypedArray)object2).recycle();
        }
        object2 = object;
        if (object == null) {
            object2 = typedArray.getColorStateList(18);
        }
        if (object2 != null) {
            this.mHeaderYear.setTextColor((ColorStateList)object2);
            this.mHeaderMonthDay.setTextColor((ColorStateList)object2);
        }
        if (typedArray.hasValueOrEmpty(0)) {
            ((View)object3).setBackground(typedArray.getDrawable(0));
        }
        typedArray.recycle();
        this.mAnimator = (ViewAnimator)this.mContainer.findViewById(16908728);
        this.mDayPickerView = (DayPickerView)this.mAnimator.findViewById(16908859);
        this.mDayPickerView.setFirstDayOfWeek(this.mFirstDayOfWeek);
        this.mDayPickerView.setMinDate(this.mMinDate.getTimeInMillis());
        this.mDayPickerView.setMaxDate(this.mMaxDate.getTimeInMillis());
        this.mDayPickerView.setDate(this.mCurrentDate.getTimeInMillis());
        this.mDayPickerView.setOnDaySelectedListener(this.mOnDaySelectedListener);
        this.mYearPickerView = (YearPickerView)this.mAnimator.findViewById(16908863);
        this.mYearPickerView.setRange(this.mMinDate, this.mMaxDate);
        this.mYearPickerView.setYear(this.mCurrentDate.get(1));
        this.mYearPickerView.setOnYearSelectedListener(this.mOnYearSelectedListener);
        this.mSelectDay = resources.getString(17040979);
        this.mSelectYear = resources.getString(17040985);
        this.onLocaleChanged(this.mCurrentLocale);
        this.setCurrentView(0);
    }

    private ColorStateList applyLegacyColorFixes(ColorStateList colorStateList) {
        if (colorStateList != null && !colorStateList.hasState(16843518)) {
            int n;
            int n2;
            if (colorStateList.hasState(16842913)) {
                n = colorStateList.getColorForState(StateSet.get(10), 0);
                n2 = colorStateList.getColorForState(StateSet.get(8), 0);
            } else {
                n = colorStateList.getDefaultColor();
                n2 = this.multiplyAlphaComponent(n, this.mContext.obtainStyledAttributes(ATTRS_DISABLED_ALPHA).getFloat(0, 0.3f));
            }
            if (n != 0 && n2 != 0) {
                return new ColorStateList(new int[][]{{16843518}, new int[0]}, new int[]{n, n2});
            }
            return null;
        }
        return colorStateList;
    }

    public static int getDaysInMonth(int n, int n2) {
        switch (n) {
            default: {
                throw new IllegalArgumentException("Invalid Month");
            }
            case 3: 
            case 5: 
            case 8: 
            case 10: {
                return 30;
            }
            case 1: {
                n = n2 % 4 == 0 ? 29 : 28;
                return n;
            }
            case 0: 
            case 2: 
            case 4: 
            case 6: 
            case 7: 
            case 9: 
            case 11: 
        }
        return 31;
    }

    private int multiplyAlphaComponent(int n, float f) {
        return (int)((float)(n >> 24 & 255) * f + 0.5f) << 24 | 16777215 & n;
    }

    private void onCurrentDateChanged(boolean bl) {
        if (this.mHeaderYear == null) {
            return;
        }
        String string2 = this.mYearFormat.format(this.mCurrentDate.getTime());
        this.mHeaderYear.setText(string2);
        string2 = this.mMonthDayFormat.format(this.mCurrentDate.getTime());
        this.mHeaderMonthDay.setText(string2);
        if (bl) {
            this.mAnimator.announceForAccessibility(this.getFormattedCurrentDate());
        }
    }

    private void onDateChanged(boolean bl, boolean bl2) {
        int n = this.mCurrentDate.get(1);
        if (bl2 && (this.mOnDateChangedListener != null || this.mAutoFillChangeListener != null)) {
            int n2 = this.mCurrentDate.get(2);
            int n3 = this.mCurrentDate.get(5);
            if (this.mOnDateChangedListener != null) {
                this.mOnDateChangedListener.onDateChanged(this.mDelegator, n, n2, n3);
            }
            if (this.mAutoFillChangeListener != null) {
                this.mAutoFillChangeListener.onDateChanged(this.mDelegator, n, n2, n3);
            }
        }
        this.mDayPickerView.setDate(this.mCurrentDate.getTimeInMillis());
        this.mYearPickerView.setYear(n);
        this.onCurrentDateChanged(bl);
        if (bl) {
            this.tryVibrate();
        }
    }

    private void setCurrentView(int n) {
        if (n != 0) {
            if (n == 1) {
                int n2 = this.mCurrentDate.get(1);
                this.mYearPickerView.setYear(n2);
                this.mYearPickerView.post(new _$$Lambda$DatePickerCalendarDelegate$_6rynvAYPe1gU9xVgvSm4VMsr2M(this));
                if (this.mCurrentView != n) {
                    this.mHeaderMonthDay.setActivated(false);
                    this.mHeaderYear.setActivated(true);
                    this.mAnimator.setDisplayedChild(1);
                    this.mCurrentView = n;
                }
                this.mAnimator.announceForAccessibility(this.mSelectYear);
            }
        } else {
            this.mDayPickerView.setDate(this.mCurrentDate.getTimeInMillis());
            if (this.mCurrentView != n) {
                this.mHeaderMonthDay.setActivated(true);
                this.mHeaderYear.setActivated(false);
                this.mAnimator.setDisplayedChild(0);
                this.mCurrentView = n;
            }
            this.mAnimator.announceForAccessibility(this.mSelectDay);
        }
    }

    private void setDate(int n, int n2, int n3) {
        this.mCurrentDate.set(1, n);
        this.mCurrentDate.set(2, n2);
        this.mCurrentDate.set(5, n3);
        this.resetAutofilledValue();
    }

    private void tryVibrate() {
        this.mDelegator.performHapticFeedback(5);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        this.onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    public CharSequence getAccessibilityClassName() {
        return DatePicker.class.getName();
    }

    @Override
    public CalendarView getCalendarView() {
        throw new UnsupportedOperationException("Not supported by calendar-mode DatePicker");
    }

    @Override
    public boolean getCalendarViewShown() {
        return false;
    }

    @Override
    public int getDayOfMonth() {
        return this.mCurrentDate.get(5);
    }

    @Override
    public int getFirstDayOfWeek() {
        int n = this.mFirstDayOfWeek;
        if (n != 0) {
            return n;
        }
        return this.mCurrentDate.getFirstDayOfWeek();
    }

    @Override
    public Calendar getMaxDate() {
        return this.mMaxDate;
    }

    @Override
    public Calendar getMinDate() {
        return this.mMinDate;
    }

    @Override
    public int getMonth() {
        return this.mCurrentDate.get(2);
    }

    @Override
    public boolean getSpinnersShown() {
        return false;
    }

    @Override
    public int getYear() {
        return this.mCurrentDate.get(1);
    }

    @Override
    public void init(int n, int n2, int n3, DatePicker.OnDateChangedListener onDateChangedListener) {
        this.setDate(n, n2, n3);
        this.onDateChanged(false, false);
        this.mOnDateChangedListener = onDateChangedListener;
    }

    @Override
    public boolean isEnabled() {
        return this.mContainer.isEnabled();
    }

    public /* synthetic */ void lambda$new$0$DatePickerCalendarDelegate(View view) {
        this.tryVibrate();
        switch (view.getId()) {
            default: {
                break;
            }
            case 16908862: {
                this.setCurrentView(1);
                break;
            }
            case 16908861: {
                this.setCurrentView(0);
            }
        }
    }

    public /* synthetic */ void lambda$setCurrentView$1$DatePickerCalendarDelegate() {
        this.mYearPickerView.requestFocus();
        View view = this.mYearPickerView.getSelectedView();
        if (view != null) {
            view.requestFocus();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        this.setCurrentLocale(configuration.locale);
    }

    @Override
    protected void onLocaleChanged(Locale locale) {
        if (this.mHeaderYear == null) {
            return;
        }
        this.mMonthDayFormat = DateFormat.getInstanceForSkeleton((String)"EMMMd", (Locale)locale);
        this.mMonthDayFormat.setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE);
        this.mYearFormat = DateFormat.getInstanceForSkeleton((String)"y", (Locale)locale);
        this.onCurrentDateChanged(false);
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof DatePicker.AbstractDatePickerDelegate.SavedState) {
            parcelable = (DatePicker.AbstractDatePickerDelegate.SavedState)parcelable;
            this.mCurrentDate.set(((DatePicker.AbstractDatePickerDelegate.SavedState)parcelable).getSelectedYear(), ((DatePicker.AbstractDatePickerDelegate.SavedState)parcelable).getSelectedMonth(), ((DatePicker.AbstractDatePickerDelegate.SavedState)parcelable).getSelectedDay());
            this.mMinDate.setTimeInMillis(((DatePicker.AbstractDatePickerDelegate.SavedState)parcelable).getMinDate());
            this.mMaxDate.setTimeInMillis(((DatePicker.AbstractDatePickerDelegate.SavedState)parcelable).getMaxDate());
            this.onCurrentDateChanged(false);
            int n = ((DatePicker.AbstractDatePickerDelegate.SavedState)parcelable).getCurrentView();
            this.setCurrentView(n);
            int n2 = ((DatePicker.AbstractDatePickerDelegate.SavedState)parcelable).getListPosition();
            if (n2 != -1) {
                if (n == 0) {
                    this.mDayPickerView.setPosition(n2);
                } else if (n == 1) {
                    n = ((DatePicker.AbstractDatePickerDelegate.SavedState)parcelable).getListPositionOffset();
                    this.mYearPickerView.setSelectionFromTop(n2, n);
                }
            }
        }
    }

    @Override
    public Parcelable onSaveInstanceState(Parcelable parcelable) {
        int n;
        int n2 = this.mCurrentDate.get(1);
        int n3 = this.mCurrentDate.get(2);
        int n4 = this.mCurrentDate.get(5);
        int n5 = this.mCurrentView;
        if (n5 == 0) {
            n5 = this.mDayPickerView.getMostVisiblePosition();
            n = -1;
        } else if (n5 == 1) {
            n5 = this.mYearPickerView.getFirstVisiblePosition();
            n = this.mYearPickerView.getFirstPositionOffset();
        } else {
            n5 = -1;
            n = -1;
        }
        return new DatePicker.AbstractDatePickerDelegate.SavedState(parcelable, n2, n3, n4, this.mMinDate.getTimeInMillis(), this.mMaxDate.getTimeInMillis(), this.mCurrentView, n5, n);
    }

    @Override
    public void setCalendarViewShown(boolean bl) {
    }

    @Override
    public void setEnabled(boolean bl) {
        this.mContainer.setEnabled(bl);
        this.mDayPickerView.setEnabled(bl);
        this.mYearPickerView.setEnabled(bl);
        this.mHeaderYear.setEnabled(bl);
        this.mHeaderMonthDay.setEnabled(bl);
    }

    @Override
    public void setFirstDayOfWeek(int n) {
        this.mFirstDayOfWeek = n;
        this.mDayPickerView.setFirstDayOfWeek(n);
    }

    @Override
    public void setMaxDate(long l) {
        this.mTempDate.setTimeInMillis(l);
        if (this.mTempDate.get(1) == this.mMaxDate.get(1) && this.mTempDate.get(6) == this.mMaxDate.get(6)) {
            return;
        }
        if (this.mCurrentDate.after((Object)this.mTempDate)) {
            this.mCurrentDate.setTimeInMillis(l);
            this.onDateChanged(false, true);
        }
        this.mMaxDate.setTimeInMillis(l);
        this.mDayPickerView.setMaxDate(l);
        this.mYearPickerView.setRange(this.mMinDate, this.mMaxDate);
    }

    @Override
    public void setMinDate(long l) {
        this.mTempDate.setTimeInMillis(l);
        if (this.mTempDate.get(1) == this.mMinDate.get(1) && this.mTempDate.get(6) == this.mMinDate.get(6)) {
            return;
        }
        if (this.mCurrentDate.before((Object)this.mTempDate)) {
            this.mCurrentDate.setTimeInMillis(l);
            this.onDateChanged(false, true);
        }
        this.mMinDate.setTimeInMillis(l);
        this.mDayPickerView.setMinDate(l);
        this.mYearPickerView.setRange(this.mMinDate, this.mMaxDate);
    }

    @Override
    public void setSpinnersShown(boolean bl) {
    }

    @Override
    public void updateDate(int n, int n2, int n3) {
        this.setDate(n, n2, n3);
        this.onDateChanged(false, true);
    }

}

