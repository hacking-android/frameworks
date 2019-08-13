/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.icu.LocaleData
 */
package android.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.IBinder;
import android.os.Parcelable;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import com.android.internal.R;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import libcore.icu.LocaleData;

class TimePickerSpinnerDelegate
extends TimePicker.AbstractTimePickerDelegate {
    private static final boolean DEFAULT_ENABLED_STATE = true;
    private static final int HOURS_IN_HALF_DAY = 12;
    private final Button mAmPmButton;
    private final NumberPicker mAmPmSpinner;
    private final EditText mAmPmSpinnerInput;
    private final String[] mAmPmStrings;
    private final TextView mDivider;
    private char mHourFormat;
    private final NumberPicker mHourSpinner;
    private final EditText mHourSpinnerInput;
    private boolean mHourWithTwoDigit;
    private boolean mIs24HourView;
    private boolean mIsAm;
    private boolean mIsEnabled = true;
    private final NumberPicker mMinuteSpinner;
    private final EditText mMinuteSpinnerInput;
    private final Calendar mTempCalendar;

    public TimePickerSpinnerDelegate(TimePicker object, Context context, AttributeSet object2, int n, int n2) {
        super((TimePicker)object, context);
        object2 = this.mContext.obtainStyledAttributes((AttributeSet)object2, R.styleable.TimePicker, n, n2);
        n = ((TypedArray)object2).getResourceId(13, 17367322);
        ((TypedArray)object2).recycle();
        LayoutInflater.from(this.mContext).inflate(n, (ViewGroup)this.mDelegator, true).setSaveFromParentEnabled(false);
        this.mHourSpinner = (NumberPicker)((View)object).findViewById(16908992);
        this.mHourSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){

            @Override
            public void onValueChange(NumberPicker object, int n, int n2) {
                TimePickerSpinnerDelegate.this.updateInputState();
                if (!TimePickerSpinnerDelegate.this.is24Hour() && (n == 11 && n2 == 12 || n == 12 && n2 == 11)) {
                    object = TimePickerSpinnerDelegate.this;
                    ((TimePickerSpinnerDelegate)object).mIsAm = ((TimePickerSpinnerDelegate)object).mIsAm ^ true;
                    TimePickerSpinnerDelegate.this.updateAmPmControl();
                }
                TimePickerSpinnerDelegate.this.onTimeChanged();
            }
        });
        this.mHourSpinnerInput = (EditText)this.mHourSpinner.findViewById(16909185);
        this.mHourSpinnerInput.setImeOptions(5);
        this.mDivider = (TextView)this.mDelegator.findViewById(16908883);
        if (this.mDivider != null) {
            this.setDividerText();
        }
        this.mMinuteSpinner = (NumberPicker)this.mDelegator.findViewById(16909115);
        this.mMinuteSpinner.setMinValue(0);
        this.mMinuteSpinner.setMaxValue(59);
        this.mMinuteSpinner.setOnLongPressUpdateInterval(100L);
        this.mMinuteSpinner.setFormatter(NumberPicker.getTwoDigitFormatter());
        this.mMinuteSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){

            @Override
            public void onValueChange(NumberPicker object, int n, int n2) {
                TimePickerSpinnerDelegate.this.updateInputState();
                int n3 = TimePickerSpinnerDelegate.this.mMinuteSpinner.getMinValue();
                int n4 = TimePickerSpinnerDelegate.this.mMinuteSpinner.getMaxValue();
                if (n == n4 && n2 == n3) {
                    n = TimePickerSpinnerDelegate.this.mHourSpinner.getValue() + 1;
                    if (!TimePickerSpinnerDelegate.this.is24Hour() && n == 12) {
                        object = TimePickerSpinnerDelegate.this;
                        ((TimePickerSpinnerDelegate)object).mIsAm = ((TimePickerSpinnerDelegate)object).mIsAm ^ true;
                        TimePickerSpinnerDelegate.this.updateAmPmControl();
                    }
                    TimePickerSpinnerDelegate.this.mHourSpinner.setValue(n);
                } else if (n == n3 && n2 == n4) {
                    n = TimePickerSpinnerDelegate.this.mHourSpinner.getValue() - 1;
                    if (!TimePickerSpinnerDelegate.this.is24Hour() && n == 11) {
                        object = TimePickerSpinnerDelegate.this;
                        ((TimePickerSpinnerDelegate)object).mIsAm = ((TimePickerSpinnerDelegate)object).mIsAm ^ true;
                        TimePickerSpinnerDelegate.this.updateAmPmControl();
                    }
                    TimePickerSpinnerDelegate.this.mHourSpinner.setValue(n);
                }
                TimePickerSpinnerDelegate.this.onTimeChanged();
            }
        });
        this.mMinuteSpinnerInput = (EditText)this.mMinuteSpinner.findViewById(16909185);
        this.mMinuteSpinnerInput.setImeOptions(5);
        this.mAmPmStrings = TimePickerSpinnerDelegate.getAmPmStrings(context);
        context = this.mDelegator.findViewById(16908723);
        if (context instanceof Button) {
            this.mAmPmSpinner = null;
            this.mAmPmSpinnerInput = null;
            this.mAmPmButton = (Button)((Object)context);
            this.mAmPmButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View object) {
                    ((View)object).requestFocus();
                    object = TimePickerSpinnerDelegate.this;
                    ((TimePickerSpinnerDelegate)object).mIsAm = ((TimePickerSpinnerDelegate)object).mIsAm ^ true;
                    TimePickerSpinnerDelegate.this.updateAmPmControl();
                    TimePickerSpinnerDelegate.this.onTimeChanged();
                }
            });
        } else {
            this.mAmPmButton = null;
            this.mAmPmSpinner = (NumberPicker)((Object)context);
            this.mAmPmSpinner.setMinValue(0);
            this.mAmPmSpinner.setMaxValue(1);
            this.mAmPmSpinner.setDisplayedValues(this.mAmPmStrings);
            this.mAmPmSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){

                @Override
                public void onValueChange(NumberPicker object, int n, int n2) {
                    TimePickerSpinnerDelegate.this.updateInputState();
                    ((View)object).requestFocus();
                    object = TimePickerSpinnerDelegate.this;
                    ((TimePickerSpinnerDelegate)object).mIsAm = ((TimePickerSpinnerDelegate)object).mIsAm ^ true;
                    TimePickerSpinnerDelegate.this.updateAmPmControl();
                    TimePickerSpinnerDelegate.this.onTimeChanged();
                }
            });
            this.mAmPmSpinnerInput = (EditText)this.mAmPmSpinner.findViewById(16909185);
            this.mAmPmSpinnerInput.setImeOptions(6);
        }
        if (this.isAmPmAtStart()) {
            object = (ViewGroup)((View)object).findViewById(16909462);
            ((ViewGroup)object).removeView((View)((Object)context));
            ((ViewGroup)object).addView((View)((Object)context), 0);
            object = (ViewGroup.MarginLayoutParams)((View)((Object)context)).getLayoutParams();
            n2 = ((ViewGroup.MarginLayoutParams)object).getMarginStart();
            n = ((ViewGroup.MarginLayoutParams)object).getMarginEnd();
            if (n2 != n) {
                ((ViewGroup.MarginLayoutParams)object).setMarginStart(n);
                ((ViewGroup.MarginLayoutParams)object).setMarginEnd(n2);
            }
        }
        this.getHourFormatData();
        this.updateHourControl();
        this.updateMinuteControl();
        this.updateAmPmControl();
        this.mTempCalendar = Calendar.getInstance(this.mLocale);
        this.setHour(this.mTempCalendar.get(11));
        this.setMinute(this.mTempCalendar.get(12));
        if (!this.isEnabled()) {
            this.setEnabled(false);
        }
        this.setContentDescriptions();
        if (this.mDelegator.getImportantForAccessibility() == 0) {
            this.mDelegator.setImportantForAccessibility(1);
        }
    }

    public static String[] getAmPmStrings(Context object) {
        Object object2 = LocaleData.get((Locale)object.getResources().getConfiguration().locale);
        object = object2.amPm[0].length() > 4 ? object2.narrowAm : object2.amPm[0];
        object2 = object2.amPm[1].length() > 4 ? object2.narrowPm : object2.amPm[1];
        return new String[]{object, object2};
    }

    private void getHourFormatData() {
        Locale locale = this.mLocale;
        String string2 = this.mIs24HourView ? "Hm" : "hm";
        string2 = DateFormat.getBestDateTimePattern(locale, string2);
        int n = string2.length();
        this.mHourWithTwoDigit = false;
        for (int i = 0; i < n; ++i) {
            char c = string2.charAt(i);
            if (c != 'H' && c != 'h' && c != 'K' && c != 'k') {
                continue;
            }
            this.mHourFormat = c;
            if (i + 1 >= n || c != string2.charAt(i + 1)) break;
            this.mHourWithTwoDigit = true;
            break;
        }
    }

    private boolean isAmPmAtStart() {
        return DateFormat.getBestDateTimePattern(this.mLocale, "hm").startsWith("a");
    }

    private void onTimeChanged() {
        this.mDelegator.sendAccessibilityEvent(4);
        if (this.mOnTimeChangedListener != null) {
            this.mOnTimeChangedListener.onTimeChanged(this.mDelegator, this.getHour(), this.getMinute());
        }
        if (this.mAutoFillChangeListener != null) {
            this.mAutoFillChangeListener.onTimeChanged(this.mDelegator, this.getHour(), this.getMinute());
        }
    }

    private void setContentDescriptions() {
        this.trySetContentDescription(this.mMinuteSpinner, 16909015, 17041136);
        this.trySetContentDescription(this.mMinuteSpinner, 16908870, 17041130);
        this.trySetContentDescription(this.mHourSpinner, 16909015, 17041135);
        this.trySetContentDescription(this.mHourSpinner, 16908870, 17041129);
        NumberPicker numberPicker = this.mAmPmSpinner;
        if (numberPicker != null) {
            this.trySetContentDescription(numberPicker, 16909015, 17041137);
            this.trySetContentDescription(this.mAmPmSpinner, 16908870, 17041131);
        }
    }

    private void setCurrentHour(int n, boolean bl) {
        if (n == this.getHour()) {
            return;
        }
        this.resetAutofilledValue();
        int n2 = n;
        if (!this.is24Hour()) {
            if (n >= 12) {
                this.mIsAm = false;
                n2 = n;
                if (n > 12) {
                    n2 = n - 12;
                }
            } else {
                this.mIsAm = true;
                n2 = n;
                if (n == 0) {
                    n2 = 12;
                }
            }
            this.updateAmPmControl();
        }
        this.mHourSpinner.setValue(n2);
        if (bl) {
            this.onTimeChanged();
        }
    }

    private void setCurrentMinute(int n, boolean bl) {
        if (n == this.getMinute()) {
            return;
        }
        this.resetAutofilledValue();
        this.mMinuteSpinner.setValue(n);
        if (bl) {
            this.onTimeChanged();
        }
    }

    private void setDividerText() {
        int n;
        String string2 = this.mIs24HourView ? "Hm" : "hm";
        string2 = DateFormat.getBestDateTimePattern(this.mLocale, string2);
        int n2 = n = string2.lastIndexOf(72);
        if (n == -1) {
            n2 = string2.lastIndexOf(104);
        }
        string2 = n2 == -1 ? ":" : ((n = string2.indexOf(109, n2 + 1)) == -1 ? Character.toString(string2.charAt(n2 + 1)) : string2.substring(n2 + 1, n));
        this.mDivider.setText(string2);
    }

    private void trySetContentDescription(View view, int n, int n2) {
        if ((view = view.findViewById(n)) != null) {
            view.setContentDescription(this.mContext.getString(n2));
        }
    }

    private void updateAmPmControl() {
        if (this.is24Hour()) {
            NumberPicker numberPicker = this.mAmPmSpinner;
            if (numberPicker != null) {
                numberPicker.setVisibility(8);
            } else {
                this.mAmPmButton.setVisibility(8);
            }
        } else {
            int n = this.mIsAm ^ true;
            NumberPicker numberPicker = this.mAmPmSpinner;
            if (numberPicker != null) {
                numberPicker.setValue(n);
                this.mAmPmSpinner.setVisibility(0);
            } else {
                this.mAmPmButton.setText(this.mAmPmStrings[n]);
                this.mAmPmButton.setVisibility(0);
            }
        }
        this.mDelegator.sendAccessibilityEvent(4);
    }

    private void updateHourControl() {
        if (this.is24Hour()) {
            if (this.mHourFormat == 'k') {
                this.mHourSpinner.setMinValue(1);
                this.mHourSpinner.setMaxValue(24);
            } else {
                this.mHourSpinner.setMinValue(0);
                this.mHourSpinner.setMaxValue(23);
            }
        } else if (this.mHourFormat == 'K') {
            this.mHourSpinner.setMinValue(0);
            this.mHourSpinner.setMaxValue(11);
        } else {
            this.mHourSpinner.setMinValue(1);
            this.mHourSpinner.setMaxValue(12);
        }
        NumberPicker numberPicker = this.mHourSpinner;
        NumberPicker.Formatter formatter = this.mHourWithTwoDigit ? NumberPicker.getTwoDigitFormatter() : null;
        numberPicker.setFormatter(formatter);
    }

    private void updateInputState() {
        InputMethodManager inputMethodManager = this.mContext.getSystemService(InputMethodManager.class);
        if (inputMethodManager != null) {
            if (inputMethodManager.isActive(this.mHourSpinnerInput)) {
                this.mHourSpinnerInput.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
            } else if (inputMethodManager.isActive(this.mMinuteSpinnerInput)) {
                this.mMinuteSpinnerInput.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
            } else if (inputMethodManager.isActive(this.mAmPmSpinnerInput)) {
                this.mAmPmSpinnerInput.clearFocus();
                inputMethodManager.hideSoftInputFromWindow(this.mDelegator.getWindowToken(), 0);
            }
        }
    }

    private void updateMinuteControl() {
        if (this.is24Hour()) {
            this.mMinuteSpinnerInput.setImeOptions(6);
        } else {
            this.mMinuteSpinnerInput.setImeOptions(5);
        }
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        this.onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override
    public View getAmView() {
        return this.mAmPmSpinnerInput;
    }

    @Override
    public int getBaseline() {
        return this.mHourSpinner.getBaseline();
    }

    @Override
    public int getHour() {
        int n = this.mHourSpinner.getValue();
        if (this.is24Hour()) {
            return n;
        }
        if (this.mIsAm) {
            return n % 12;
        }
        return n % 12 + 12;
    }

    @Override
    public View getHourView() {
        return this.mHourSpinnerInput;
    }

    @Override
    public int getMinute() {
        return this.mMinuteSpinner.getValue();
    }

    @Override
    public View getMinuteView() {
        return this.mMinuteSpinnerInput;
    }

    @Override
    public View getPmView() {
        return this.mAmPmSpinnerInput;
    }

    @Override
    public boolean is24Hour() {
        return this.mIs24HourView;
    }

    @Override
    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override
    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int n = this.mIs24HourView ? 1 | 128 : 1 | 64;
        this.mTempCalendar.set(11, this.getHour());
        this.mTempCalendar.set(12, this.getMinute());
        String string2 = DateUtils.formatDateTime(this.mContext, this.mTempCalendar.getTimeInMillis(), n);
        accessibilityEvent.getText().add(string2);
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof TimePicker.AbstractTimePickerDelegate.SavedState) {
            parcelable = (TimePicker.AbstractTimePickerDelegate.SavedState)parcelable;
            this.setHour(((TimePicker.AbstractTimePickerDelegate.SavedState)parcelable).getHour());
            this.setMinute(((TimePicker.AbstractTimePickerDelegate.SavedState)parcelable).getMinute());
        }
    }

    @Override
    public Parcelable onSaveInstanceState(Parcelable parcelable) {
        return new TimePicker.AbstractTimePickerDelegate.SavedState(parcelable, this.getHour(), this.getMinute(), this.is24Hour());
    }

    @Override
    public void setDate(int n, int n2) {
        this.setCurrentHour(n, false);
        this.setCurrentMinute(n2, false);
        this.onTimeChanged();
    }

    @Override
    public void setEnabled(boolean bl) {
        this.mMinuteSpinner.setEnabled(bl);
        View view = this.mDivider;
        if (view != null) {
            ((TextView)view).setEnabled(bl);
        }
        this.mHourSpinner.setEnabled(bl);
        view = this.mAmPmSpinner;
        if (view != null) {
            ((NumberPicker)view).setEnabled(bl);
        } else {
            this.mAmPmButton.setEnabled(bl);
        }
        this.mIsEnabled = bl;
    }

    @Override
    public void setHour(int n) {
        this.setCurrentHour(n, true);
    }

    @Override
    public void setIs24Hour(boolean bl) {
        if (this.mIs24HourView == bl) {
            return;
        }
        int n = this.getHour();
        this.mIs24HourView = bl;
        this.getHourFormatData();
        this.updateHourControl();
        this.setCurrentHour(n, false);
        this.updateMinuteControl();
        this.updateAmPmControl();
    }

    @Override
    public void setMinute(int n) {
        this.setCurrentMinute(n, true);
    }

    @Override
    public boolean validateInput() {
        return true;
    }

}

