/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.Calendar
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.icu.util.Calendar;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.AbsSavedState;
import android.view.View;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.widget.CalendarView;
import android.widget.DatePickerCalendarDelegate;
import android.widget.DatePickerSpinnerDelegate;
import android.widget.FrameLayout;
import android.widget._$$Lambda$DatePicker$AnJPL5BrPXPJa_Oc_WUAB_HJq84;
import com.android.internal.R;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Locale;

public class DatePicker
extends FrameLayout {
    private static final String LOG_TAG = DatePicker.class.getSimpleName();
    public static final int MODE_CALENDAR = 2;
    public static final int MODE_SPINNER = 1;
    @UnsupportedAppUsage
    private final DatePickerDelegate mDelegate;
    private final int mMode;

    public DatePicker(Context context) {
        this(context, null);
    }

    public DatePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843612);
    }

    public DatePicker(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public DatePicker(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        if (this.getImportantForAutofill() == 0) {
            this.setImportantForAutofill(1);
        }
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.DatePicker, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.DatePicker, attributeSet, typedArray, n, n2);
        boolean bl = typedArray.getBoolean(17, false);
        int n3 = typedArray.getInt(16, 1);
        int n4 = typedArray.getInt(3, 0);
        typedArray.recycle();
        this.mMode = n3 == 2 && bl ? context.getResources().getInteger(17694960) : n3;
        this.mDelegate = this.mMode != 2 ? this.createSpinnerUIDelegate(context, attributeSet, n, n2) : this.createCalendarUIDelegate(context, attributeSet, n, n2);
        if (n4 != 0) {
            this.setFirstDayOfWeek(n4);
        }
        this.mDelegate.setAutoFillChangeListener(new _$$Lambda$DatePicker$AnJPL5BrPXPJa_Oc_WUAB_HJq84(this, context));
    }

    private DatePickerDelegate createCalendarUIDelegate(Context context, AttributeSet attributeSet, int n, int n2) {
        return new DatePickerCalendarDelegate(this, context, attributeSet, n, n2);
    }

    private DatePickerDelegate createSpinnerUIDelegate(Context context, AttributeSet attributeSet, int n, int n2) {
        return new DatePickerSpinnerDelegate(this, context, attributeSet, n, n2);
    }

    @Override
    public void autofill(AutofillValue autofillValue) {
        if (!this.isEnabled()) {
            return;
        }
        this.mDelegate.autofill(autofillValue);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        return this.mDelegate.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Override
    public void dispatchProvideAutofillStructure(ViewStructure viewStructure, int n) {
        viewStructure.setAutofillId(this.getAutofillId());
        this.onProvideAutofillStructure(viewStructure, n);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.dispatchThawSelfOnly(sparseArray);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return DatePicker.class.getName();
    }

    @Override
    public int getAutofillType() {
        int n = this.isEnabled() ? 4 : 0;
        return n;
    }

    @Override
    public AutofillValue getAutofillValue() {
        AutofillValue autofillValue = this.isEnabled() ? this.mDelegate.getAutofillValue() : null;
        return autofillValue;
    }

    @Deprecated
    public CalendarView getCalendarView() {
        return this.mDelegate.getCalendarView();
    }

    @Deprecated
    public boolean getCalendarViewShown() {
        return this.mDelegate.getCalendarViewShown();
    }

    public int getDayOfMonth() {
        return this.mDelegate.getDayOfMonth();
    }

    public int getFirstDayOfWeek() {
        return this.mDelegate.getFirstDayOfWeek();
    }

    public long getMaxDate() {
        return this.mDelegate.getMaxDate().getTimeInMillis();
    }

    public long getMinDate() {
        return this.mDelegate.getMinDate().getTimeInMillis();
    }

    public int getMode() {
        return this.mMode;
    }

    public int getMonth() {
        return this.mDelegate.getMonth();
    }

    @Deprecated
    public boolean getSpinnersShown() {
        return this.mDelegate.getSpinnersShown();
    }

    public int getYear() {
        return this.mDelegate.getYear();
    }

    public void init(int n, int n2, int n3, OnDateChangedListener onDateChangedListener) {
        this.mDelegate.init(n, n2, n3, onDateChangedListener);
    }

    @Override
    public boolean isEnabled() {
        return this.mDelegate.isEnabled();
    }

    public /* synthetic */ void lambda$new$0$DatePicker(Context object, DatePicker datePicker, int n, int n2, int n3) {
        if ((object = ((Context)object).getSystemService(AutofillManager.class)) != null) {
            ((AutofillManager)object).notifyValueChanged(this);
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDelegate.onConfigurationChanged(configuration);
    }

    @Override
    public void onPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEventInternal(accessibilityEvent);
        this.mDelegate.onPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable parcelable) {
        parcelable = (View.BaseSavedState)parcelable;
        super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
        this.mDelegate.onRestoreInstanceState(parcelable);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        return this.mDelegate.onSaveInstanceState(parcelable);
    }

    @Deprecated
    public void setCalendarViewShown(boolean bl) {
        this.mDelegate.setCalendarViewShown(bl);
    }

    @Override
    public void setEnabled(boolean bl) {
        if (this.mDelegate.isEnabled() == bl) {
            return;
        }
        super.setEnabled(bl);
        this.mDelegate.setEnabled(bl);
    }

    public void setFirstDayOfWeek(int n) {
        if (n >= 1 && n <= 7) {
            this.mDelegate.setFirstDayOfWeek(n);
            return;
        }
        throw new IllegalArgumentException("firstDayOfWeek must be between 1 and 7");
    }

    public void setMaxDate(long l) {
        this.mDelegate.setMaxDate(l);
    }

    public void setMinDate(long l) {
        this.mDelegate.setMinDate(l);
    }

    public void setOnDateChangedListener(OnDateChangedListener onDateChangedListener) {
        this.mDelegate.setOnDateChangedListener(onDateChangedListener);
    }

    @Deprecated
    public void setSpinnersShown(boolean bl) {
        this.mDelegate.setSpinnersShown(bl);
    }

    @UnsupportedAppUsage
    public void setValidationCallback(ValidationCallback validationCallback) {
        this.mDelegate.setValidationCallback(validationCallback);
    }

    public void updateDate(int n, int n2, int n3) {
        this.mDelegate.updateDate(n, n2, n3);
    }

    static abstract class AbstractDatePickerDelegate
    implements DatePickerDelegate {
        protected OnDateChangedListener mAutoFillChangeListener;
        private long mAutofilledValue;
        protected Context mContext;
        protected Calendar mCurrentDate;
        protected Locale mCurrentLocale;
        protected DatePicker mDelegator;
        protected OnDateChangedListener mOnDateChangedListener;
        protected ValidationCallback mValidationCallback;

        public AbstractDatePickerDelegate(DatePicker datePicker, Context context) {
            this.mDelegator = datePicker;
            this.mContext = context;
            this.setCurrentLocale(Locale.getDefault());
        }

        @Override
        public final void autofill(AutofillValue autofillValue) {
            if (autofillValue != null && autofillValue.isDate()) {
                long l = autofillValue.getDateValue();
                autofillValue = Calendar.getInstance((Locale)this.mCurrentLocale);
                autofillValue.setTimeInMillis(l);
                this.updateDate(autofillValue.get(1), autofillValue.get(2), autofillValue.get(5));
                this.mAutofilledValue = l;
                return;
            }
            String string2 = LOG_TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(autofillValue);
            stringBuilder.append(" could not be autofilled into ");
            stringBuilder.append(this);
            Log.w(string2, stringBuilder.toString());
        }

        @Override
        public final AutofillValue getAutofillValue() {
            long l = this.mAutofilledValue;
            if (l == 0L) {
                l = this.mCurrentDate.getTimeInMillis();
            }
            return AutofillValue.forDate(l);
        }

        protected String getFormattedCurrentDate() {
            return DateUtils.formatDateTime(this.mContext, this.mCurrentDate.getTimeInMillis(), 22);
        }

        protected void onLocaleChanged(Locale locale) {
        }

        @Override
        public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.getText().add(this.getFormattedCurrentDate());
        }

        protected void onValidationChanged(boolean bl) {
            ValidationCallback validationCallback = this.mValidationCallback;
            if (validationCallback != null) {
                validationCallback.onValidationChanged(bl);
            }
        }

        protected void resetAutofilledValue() {
            this.mAutofilledValue = 0L;
        }

        @Override
        public void setAutoFillChangeListener(OnDateChangedListener onDateChangedListener) {
            this.mAutoFillChangeListener = onDateChangedListener;
        }

        protected void setCurrentLocale(Locale locale) {
            if (!locale.equals(this.mCurrentLocale)) {
                this.mCurrentLocale = locale;
                this.onLocaleChanged(locale);
            }
        }

        @Override
        public void setOnDateChangedListener(OnDateChangedListener onDateChangedListener) {
            this.mOnDateChangedListener = onDateChangedListener;
        }

        @Override
        public void setValidationCallback(ValidationCallback validationCallback) {
            this.mValidationCallback = validationCallback;
        }

        static class SavedState
        extends View.BaseSavedState {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

                @Override
                public SavedState createFromParcel(Parcel parcel) {
                    return new SavedState(parcel);
                }

                public SavedState[] newArray(int n) {
                    return new SavedState[n];
                }
            };
            private final int mCurrentView;
            private final int mListPosition;
            private final int mListPositionOffset;
            private final long mMaxDate;
            private final long mMinDate;
            private final int mSelectedDay;
            private final int mSelectedMonth;
            private final int mSelectedYear;

            private SavedState(Parcel parcel) {
                super(parcel);
                this.mSelectedYear = parcel.readInt();
                this.mSelectedMonth = parcel.readInt();
                this.mSelectedDay = parcel.readInt();
                this.mMinDate = parcel.readLong();
                this.mMaxDate = parcel.readLong();
                this.mCurrentView = parcel.readInt();
                this.mListPosition = parcel.readInt();
                this.mListPositionOffset = parcel.readInt();
            }

            public SavedState(Parcelable parcelable, int n, int n2, int n3, long l, long l2) {
                this(parcelable, n, n2, n3, l, l2, 0, 0, 0);
            }

            public SavedState(Parcelable parcelable, int n, int n2, int n3, long l, long l2, int n4, int n5, int n6) {
                super(parcelable);
                this.mSelectedYear = n;
                this.mSelectedMonth = n2;
                this.mSelectedDay = n3;
                this.mMinDate = l;
                this.mMaxDate = l2;
                this.mCurrentView = n4;
                this.mListPosition = n5;
                this.mListPositionOffset = n6;
            }

            public int getCurrentView() {
                return this.mCurrentView;
            }

            public int getListPosition() {
                return this.mListPosition;
            }

            public int getListPositionOffset() {
                return this.mListPositionOffset;
            }

            public long getMaxDate() {
                return this.mMaxDate;
            }

            public long getMinDate() {
                return this.mMinDate;
            }

            public int getSelectedDay() {
                return this.mSelectedDay;
            }

            public int getSelectedMonth() {
                return this.mSelectedMonth;
            }

            public int getSelectedYear() {
                return this.mSelectedYear;
            }

            @Override
            public void writeToParcel(Parcel parcel, int n) {
                super.writeToParcel(parcel, n);
                parcel.writeInt(this.mSelectedYear);
                parcel.writeInt(this.mSelectedMonth);
                parcel.writeInt(this.mSelectedDay);
                parcel.writeLong(this.mMinDate);
                parcel.writeLong(this.mMaxDate);
                parcel.writeInt(this.mCurrentView);
                parcel.writeInt(this.mListPosition);
                parcel.writeInt(this.mListPositionOffset);
            }

        }

    }

    static interface DatePickerDelegate {
        public void autofill(AutofillValue var1);

        public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent var1);

        public AutofillValue getAutofillValue();

        public CalendarView getCalendarView();

        public boolean getCalendarViewShown();

        public int getDayOfMonth();

        public int getFirstDayOfWeek();

        public Calendar getMaxDate();

        public Calendar getMinDate();

        public int getMonth();

        public boolean getSpinnersShown();

        public int getYear();

        public void init(int var1, int var2, int var3, OnDateChangedListener var4);

        public boolean isEnabled();

        public void onConfigurationChanged(Configuration var1);

        public void onPopulateAccessibilityEvent(AccessibilityEvent var1);

        public void onRestoreInstanceState(Parcelable var1);

        public Parcelable onSaveInstanceState(Parcelable var1);

        public void setAutoFillChangeListener(OnDateChangedListener var1);

        public void setCalendarViewShown(boolean var1);

        public void setEnabled(boolean var1);

        public void setFirstDayOfWeek(int var1);

        public void setMaxDate(long var1);

        public void setMinDate(long var1);

        public void setOnDateChangedListener(OnDateChangedListener var1);

        public void setSpinnersShown(boolean var1);

        public void setValidationCallback(ValidationCallback var1);

        public void updateDate(int var1, int var2, int var3);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DatePickerMode {
    }

    public static interface OnDateChangedListener {
        public void onDateChanged(DatePicker var1, int var2, int var3, int var4);
    }

    public static interface ValidationCallback {
        public void onValidationChanged(boolean var1);
    }

}

