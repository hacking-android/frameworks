/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.Calendar
 *  libcore.icu.ICU
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.icu.util.Calendar;
import android.os.IBinder;
import android.os.Parcelable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.android.internal.R;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import libcore.icu.ICU;

class DatePickerSpinnerDelegate
extends DatePicker.AbstractDatePickerDelegate {
    private static final String DATE_FORMAT = "MM/dd/yyyy";
    private static final boolean DEFAULT_CALENDAR_VIEW_SHOWN = true;
    private static final boolean DEFAULT_ENABLED_STATE = true;
    private static final int DEFAULT_END_YEAR = 2100;
    private static final boolean DEFAULT_SPINNERS_SHOWN = true;
    private static final int DEFAULT_START_YEAR = 1900;
    private final CalendarView mCalendarView;
    private final java.text.DateFormat mDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private final NumberPicker mDaySpinner;
    private final EditText mDaySpinnerInput;
    private boolean mIsEnabled = true;
    private Calendar mMaxDate;
    private Calendar mMinDate;
    private final NumberPicker mMonthSpinner;
    private final EditText mMonthSpinnerInput;
    private int mNumberOfMonths;
    private String[] mShortMonths;
    private final LinearLayout mSpinners;
    private Calendar mTempDate;
    private final NumberPicker mYearSpinner;
    private final EditText mYearSpinnerInput;

    DatePickerSpinnerDelegate(DatePicker object, Context object2, AttributeSet object3, int n, int n2) {
        super((DatePicker)object, (Context)object2);
        this.mDelegator = object;
        this.mContext = object2;
        this.setCurrentLocale(Locale.getDefault());
        TypedArray typedArray = ((Context)object2).obtainStyledAttributes((AttributeSet)object3, R.styleable.DatePicker, n, n2);
        boolean bl = typedArray.getBoolean(6, true);
        boolean bl2 = typedArray.getBoolean(7, true);
        int n3 = typedArray.getInt(1, 1900);
        n2 = typedArray.getInt(2, 2100);
        object3 = typedArray.getString(4);
        object = typedArray.getString(5);
        n = typedArray.getResourceId(20, 17367131);
        typedArray.recycle();
        ((LayoutInflater)((Context)object2).getSystemService("layout_inflater")).inflate(n, (ViewGroup)this.mDelegator, true).setSaveFromParentEnabled(false);
        object2 = new NumberPicker.OnValueChangeListener(){

            @Override
            public void onValueChange(NumberPicker object, int n, int n2) {
                block14 : {
                    block12 : {
                        block13 : {
                            block11 : {
                                DatePickerSpinnerDelegate.this.updateInputState();
                                DatePickerSpinnerDelegate.this.mTempDate.setTimeInMillis(DatePickerSpinnerDelegate.this.mCurrentDate.getTimeInMillis());
                                if (object != DatePickerSpinnerDelegate.this.mDaySpinner) break block11;
                                int n3 = DatePickerSpinnerDelegate.this.mTempDate.getActualMaximum(5);
                                if (n == n3 && n2 == 1) {
                                    DatePickerSpinnerDelegate.this.mTempDate.add(5, 1);
                                } else if (n == 1 && n2 == n3) {
                                    DatePickerSpinnerDelegate.this.mTempDate.add(5, -1);
                                } else {
                                    DatePickerSpinnerDelegate.this.mTempDate.add(5, n2 - n);
                                }
                                break block12;
                            }
                            if (object != DatePickerSpinnerDelegate.this.mMonthSpinner) break block13;
                            if (n == 11 && n2 == 0) {
                                DatePickerSpinnerDelegate.this.mTempDate.add(2, 1);
                            } else if (n == 0 && n2 == 11) {
                                DatePickerSpinnerDelegate.this.mTempDate.add(2, -1);
                            } else {
                                DatePickerSpinnerDelegate.this.mTempDate.add(2, n2 - n);
                            }
                            break block12;
                        }
                        if (object != DatePickerSpinnerDelegate.this.mYearSpinner) break block14;
                        DatePickerSpinnerDelegate.this.mTempDate.set(1, n2);
                    }
                    object = DatePickerSpinnerDelegate.this;
                    ((DatePickerSpinnerDelegate)object).setDate(((DatePickerSpinnerDelegate)object).mTempDate.get(1), DatePickerSpinnerDelegate.this.mTempDate.get(2), DatePickerSpinnerDelegate.this.mTempDate.get(5));
                    DatePickerSpinnerDelegate.this.updateSpinners();
                    DatePickerSpinnerDelegate.this.updateCalendarView();
                    DatePickerSpinnerDelegate.this.notifyDateChanged();
                    return;
                }
                throw new IllegalArgumentException();
            }
        };
        this.mSpinners = (LinearLayout)this.mDelegator.findViewById(16909234);
        this.mCalendarView = (CalendarView)this.mDelegator.findViewById(16908790);
        this.mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){

            @Override
            public void onSelectedDayChange(CalendarView calendarView, int n, int n2, int n3) {
                DatePickerSpinnerDelegate.this.setDate(n, n2, n3);
                DatePickerSpinnerDelegate.this.updateSpinners();
                DatePickerSpinnerDelegate.this.notifyDateChanged();
            }
        });
        this.mDaySpinner = (NumberPicker)this.mDelegator.findViewById(16908865);
        this.mDaySpinner.setFormatter(NumberPicker.getTwoDigitFormatter());
        this.mDaySpinner.setOnLongPressUpdateInterval(100L);
        this.mDaySpinner.setOnValueChangedListener((NumberPicker.OnValueChangeListener)object2);
        this.mDaySpinnerInput = (EditText)this.mDaySpinner.findViewById(16909185);
        this.mMonthSpinner = (NumberPicker)this.mDelegator.findViewById(16909127);
        this.mMonthSpinner.setMinValue(0);
        this.mMonthSpinner.setMaxValue(this.mNumberOfMonths - 1);
        this.mMonthSpinner.setDisplayedValues(this.mShortMonths);
        this.mMonthSpinner.setOnLongPressUpdateInterval(200L);
        this.mMonthSpinner.setOnValueChangedListener((NumberPicker.OnValueChangeListener)object2);
        this.mMonthSpinnerInput = (EditText)this.mMonthSpinner.findViewById(16909185);
        this.mYearSpinner = (NumberPicker)this.mDelegator.findViewById(16909571);
        this.mYearSpinner.setOnLongPressUpdateInterval(100L);
        this.mYearSpinner.setOnValueChangedListener((NumberPicker.OnValueChangeListener)object2);
        this.mYearSpinnerInput = (EditText)this.mYearSpinner.findViewById(16909185);
        if (!bl && !bl2) {
            this.setSpinnersShown(true);
        } else {
            this.setSpinnersShown(bl);
            this.setCalendarViewShown(bl2);
        }
        this.mTempDate.clear();
        if (!TextUtils.isEmpty((CharSequence)object3)) {
            if (!this.parseDate((String)object3, this.mTempDate)) {
                this.mTempDate.set(n3, 0, 1);
            }
        } else {
            this.mTempDate.set(n3, 0, 1);
        }
        this.setMinDate(this.mTempDate.getTimeInMillis());
        this.mTempDate.clear();
        if (!TextUtils.isEmpty((CharSequence)object)) {
            if (!this.parseDate((String)object, this.mTempDate)) {
                this.mTempDate.set(n2, 11, 31);
            }
        } else {
            this.mTempDate.set(n2, 11, 31);
        }
        this.setMaxDate(this.mTempDate.getTimeInMillis());
        this.mCurrentDate.setTimeInMillis(System.currentTimeMillis());
        this.init(this.mCurrentDate.get(1), this.mCurrentDate.get(2), this.mCurrentDate.get(5), null);
        this.reorderSpinners();
        this.setContentDescriptions();
        if (this.mDelegator.getImportantForAccessibility() == 0) {
            this.mDelegator.setImportantForAccessibility(1);
        }
    }

    private Calendar getCalendarForLocale(Calendar calendar, Locale locale) {
        if (calendar == null) {
            return Calendar.getInstance((Locale)locale);
        }
        long l = calendar.getTimeInMillis();
        calendar = Calendar.getInstance((Locale)locale);
        calendar.setTimeInMillis(l);
        return calendar;
    }

    private boolean isNewDate(int n, int n2, int n3) {
        boolean bl;
        block0 : {
            Calendar calendar = this.mCurrentDate;
            bl = true;
            if (calendar.get(1) != n || this.mCurrentDate.get(2) != n2 || this.mCurrentDate.get(5) != n3) break block0;
            bl = false;
        }
        return bl;
    }

    @UnsupportedAppUsage
    private void notifyDateChanged() {
        this.mDelegator.sendAccessibilityEvent(4);
        if (this.mOnDateChangedListener != null) {
            this.mOnDateChangedListener.onDateChanged(this.mDelegator, this.getYear(), this.getMonth(), this.getDayOfMonth());
        }
        if (this.mAutoFillChangeListener != null) {
            this.mAutoFillChangeListener.onDateChanged(this.mDelegator, this.getYear(), this.getMonth(), this.getDayOfMonth());
        }
    }

    private boolean parseDate(String string2, Calendar calendar) {
        try {
            calendar.setTime(this.mDateFormat.parse(string2));
            return true;
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
            return false;
        }
    }

    private void reorderSpinners() {
        this.mSpinners.removeAllViews();
        char[] arrc = ICU.getDateFormatOrder((String)DateFormat.getBestDateTimePattern(Locale.getDefault(), "yyyyMMMdd"));
        int n = arrc.length;
        for (int i = 0; i < n; ++i) {
            char c = arrc[i];
            if (c != 'M') {
                if (c != 'd') {
                    if (c == 'y') {
                        this.mSpinners.addView(this.mYearSpinner);
                        this.setImeOptions(this.mYearSpinner, n, i);
                        continue;
                    }
                    throw new IllegalArgumentException(Arrays.toString(arrc));
                }
                this.mSpinners.addView(this.mDaySpinner);
                this.setImeOptions(this.mDaySpinner, n, i);
                continue;
            }
            this.mSpinners.addView(this.mMonthSpinner);
            this.setImeOptions(this.mMonthSpinner, n, i);
        }
    }

    private void setContentDescriptions() {
        this.trySetContentDescription(this.mDaySpinner, 16909015, 17039822);
        this.trySetContentDescription(this.mDaySpinner, 16908870, 17039818);
        this.trySetContentDescription(this.mMonthSpinner, 16909015, 17039823);
        this.trySetContentDescription(this.mMonthSpinner, 16908870, 17039819);
        this.trySetContentDescription(this.mYearSpinner, 16909015, 17039824);
        this.trySetContentDescription(this.mYearSpinner, 16908870, 17039820);
    }

    @UnsupportedAppUsage
    private void setDate(int n, int n2, int n3) {
        this.mCurrentDate.set(n, n2, n3);
        this.resetAutofilledValue();
        if (this.mCurrentDate.before((Object)this.mMinDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
        } else if (this.mCurrentDate.after((Object)this.mMaxDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
        }
    }

    private void setImeOptions(NumberPicker numberPicker, int n, int n2) {
        n = n2 < n - 1 ? 5 : 6;
        ((TextView)numberPicker.findViewById(16909185)).setImeOptions(n);
    }

    private void trySetContentDescription(View view, int n, int n2) {
        if ((view = view.findViewById(n)) != null) {
            view.setContentDescription(this.mContext.getString(n2));
        }
    }

    @UnsupportedAppUsage
    private void updateCalendarView() {
        this.mCalendarView.setDate(this.mCurrentDate.getTimeInMillis(), false, false);
    }

    @UnsupportedAppUsage
    private void updateInputState() {
        InputMethodManager inputMethodManager = this.mContext.getSystemService(InputMethodManager.class);
        if (inputMethodManager != null) {
            if (inputMethodManager.isActive(this.mYearSpinnerInput)) {
                this.mYearSpinnerInput.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
            } else if (inputMethodManager.isActive(this.mMonthSpinnerInput)) {
                this.mMonthSpinnerInput.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
            } else if (inputMethodManager.isActive(this.mDaySpinnerInput)) {
                this.mDaySpinnerInput.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
            }
        }
    }

    @UnsupportedAppUsage
    private void updateSpinners() {
        if (this.mCurrentDate.equals((Object)this.mMinDate)) {
            this.mDaySpinner.setMinValue(this.mCurrentDate.get(5));
            this.mDaySpinner.setMaxValue(this.mCurrentDate.getActualMaximum(5));
            this.mDaySpinner.setWrapSelectorWheel(false);
            this.mMonthSpinner.setDisplayedValues(null);
            this.mMonthSpinner.setMinValue(this.mCurrentDate.get(2));
            this.mMonthSpinner.setMaxValue(this.mCurrentDate.getActualMaximum(2));
            this.mMonthSpinner.setWrapSelectorWheel(false);
        } else if (this.mCurrentDate.equals((Object)this.mMaxDate)) {
            this.mDaySpinner.setMinValue(this.mCurrentDate.getActualMinimum(5));
            this.mDaySpinner.setMaxValue(this.mCurrentDate.get(5));
            this.mDaySpinner.setWrapSelectorWheel(false);
            this.mMonthSpinner.setDisplayedValues(null);
            this.mMonthSpinner.setMinValue(this.mCurrentDate.getActualMinimum(2));
            this.mMonthSpinner.setMaxValue(this.mCurrentDate.get(2));
            this.mMonthSpinner.setWrapSelectorWheel(false);
        } else {
            this.mDaySpinner.setMinValue(1);
            this.mDaySpinner.setMaxValue(this.mCurrentDate.getActualMaximum(5));
            this.mDaySpinner.setWrapSelectorWheel(true);
            this.mMonthSpinner.setDisplayedValues(null);
            this.mMonthSpinner.setMinValue(0);
            this.mMonthSpinner.setMaxValue(11);
            this.mMonthSpinner.setWrapSelectorWheel(true);
        }
        String[] arrstring = Arrays.copyOfRange(this.mShortMonths, this.mMonthSpinner.getMinValue(), this.mMonthSpinner.getMaxValue() + 1);
        this.mMonthSpinner.setDisplayedValues(arrstring);
        this.mYearSpinner.setMinValue(this.mMinDate.get(1));
        this.mYearSpinner.setMaxValue(this.mMaxDate.get(1));
        this.mYearSpinner.setWrapSelectorWheel(false);
        this.mYearSpinner.setValue(this.mCurrentDate.get(1));
        this.mMonthSpinner.setValue(this.mCurrentDate.get(2));
        this.mDaySpinner.setValue(this.mCurrentDate.get(5));
        if (this.usingNumericMonths()) {
            this.mMonthSpinnerInput.setRawInputType(2);
        }
    }

    private boolean usingNumericMonths() {
        return Character.isDigit(this.mShortMonths[0].charAt(0));
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        this.onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override
    public CalendarView getCalendarView() {
        return this.mCalendarView;
    }

    @Override
    public boolean getCalendarViewShown() {
        boolean bl = this.mCalendarView.getVisibility() == 0;
        return bl;
    }

    @Override
    public int getDayOfMonth() {
        return this.mCurrentDate.get(5);
    }

    @Override
    public int getFirstDayOfWeek() {
        return this.mCalendarView.getFirstDayOfWeek();
    }

    @Override
    public Calendar getMaxDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.mCalendarView.getMaxDate());
        return calendar;
    }

    @Override
    public Calendar getMinDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.mCalendarView.getMinDate());
        return calendar;
    }

    @Override
    public int getMonth() {
        return this.mCurrentDate.get(2);
    }

    @Override
    public boolean getSpinnersShown() {
        return this.mSpinners.isShown();
    }

    @Override
    public int getYear() {
        return this.mCurrentDate.get(1);
    }

    @Override
    public void init(int n, int n2, int n3, DatePicker.OnDateChangedListener onDateChangedListener) {
        this.setDate(n, n2, n3);
        this.updateSpinners();
        this.updateCalendarView();
        this.mOnDateChangedListener = onDateChangedListener;
    }

    @Override
    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        this.setCurrentLocale(configuration.locale);
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof DatePicker.AbstractDatePickerDelegate.SavedState) {
            parcelable = (DatePicker.AbstractDatePickerDelegate.SavedState)parcelable;
            this.setDate(((DatePicker.AbstractDatePickerDelegate.SavedState)parcelable).getSelectedYear(), ((DatePicker.AbstractDatePickerDelegate.SavedState)parcelable).getSelectedMonth(), ((DatePicker.AbstractDatePickerDelegate.SavedState)parcelable).getSelectedDay());
            this.updateSpinners();
            this.updateCalendarView();
        }
    }

    @Override
    public Parcelable onSaveInstanceState(Parcelable parcelable) {
        return new DatePicker.AbstractDatePickerDelegate.SavedState(parcelable, this.getYear(), this.getMonth(), this.getDayOfMonth(), this.getMinDate().getTimeInMillis(), this.getMaxDate().getTimeInMillis());
    }

    @Override
    public void setCalendarViewShown(boolean bl) {
        CalendarView calendarView = this.mCalendarView;
        int n = bl ? 0 : 8;
        calendarView.setVisibility(n);
    }

    @Override
    protected void setCurrentLocale(Locale locale) {
        super.setCurrentLocale(locale);
        this.mTempDate = this.getCalendarForLocale(this.mTempDate, locale);
        this.mMinDate = this.getCalendarForLocale(this.mMinDate, locale);
        this.mMaxDate = this.getCalendarForLocale(this.mMaxDate, locale);
        this.mCurrentDate = this.getCalendarForLocale(this.mCurrentDate, locale);
        this.mNumberOfMonths = this.mTempDate.getActualMaximum(2) + 1;
        this.mShortMonths = new DateFormatSymbols().getShortMonths();
        if (this.usingNumericMonths()) {
            this.mShortMonths = new String[this.mNumberOfMonths];
            for (int i = 0; i < this.mNumberOfMonths; ++i) {
                this.mShortMonths[i] = String.format("%d", i + 1);
            }
        }
    }

    @Override
    public void setEnabled(boolean bl) {
        this.mDaySpinner.setEnabled(bl);
        this.mMonthSpinner.setEnabled(bl);
        this.mYearSpinner.setEnabled(bl);
        this.mCalendarView.setEnabled(bl);
        this.mIsEnabled = bl;
    }

    @Override
    public void setFirstDayOfWeek(int n) {
        this.mCalendarView.setFirstDayOfWeek(n);
    }

    @Override
    public void setMaxDate(long l) {
        this.mTempDate.setTimeInMillis(l);
        if (this.mTempDate.get(1) == this.mMaxDate.get(1) && this.mTempDate.get(6) == this.mMaxDate.get(6)) {
            return;
        }
        this.mMaxDate.setTimeInMillis(l);
        this.mCalendarView.setMaxDate(l);
        if (this.mCurrentDate.after((Object)this.mMaxDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
            this.updateCalendarView();
        }
        this.updateSpinners();
    }

    @Override
    public void setMinDate(long l) {
        this.mTempDate.setTimeInMillis(l);
        if (this.mTempDate.get(1) == this.mMinDate.get(1) && this.mTempDate.get(6) == this.mMinDate.get(6)) {
            return;
        }
        this.mMinDate.setTimeInMillis(l);
        this.mCalendarView.setMinDate(l);
        if (this.mCurrentDate.before((Object)this.mMinDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
            this.updateCalendarView();
        }
        this.updateSpinners();
    }

    @Override
    public void setSpinnersShown(boolean bl) {
        LinearLayout linearLayout = this.mSpinners;
        int n = bl ? 0 : 8;
        linearLayout.setVisibility(n);
    }

    @Override
    public void updateDate(int n, int n2, int n3) {
        if (!this.isNewDate(n, n2, n3)) {
            return;
        }
        this.setDate(n, n2, n3);
        this.updateSpinners();
        this.updateCalendarView();
        this.notifyDateChanged();
    }

}

