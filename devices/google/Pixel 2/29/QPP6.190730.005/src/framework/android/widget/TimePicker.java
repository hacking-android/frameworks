/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.util.Calendar
 *  libcore.icu.LocaleData
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
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.view.AbsSavedState;
import android.view.View;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.widget.FrameLayout;
import android.widget.TimePickerClockDelegate;
import android.widget.TimePickerSpinnerDelegate;
import android.widget._$$Lambda$TimePicker$2FhAB9WgnLgn4zn4f9rRT7DNfjw;
import com.android.internal.R;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;
import libcore.icu.LocaleData;

public class TimePicker
extends FrameLayout {
    private static final String LOG_TAG = TimePicker.class.getSimpleName();
    public static final int MODE_CLOCK = 2;
    public static final int MODE_SPINNER = 1;
    @UnsupportedAppUsage
    private final TimePickerDelegate mDelegate;
    private final int mMode;

    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843933);
    }

    public TimePicker(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public TimePicker(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        if (this.getImportantForAutofill() == 0) {
            this.setImportantForAutofill(1);
        }
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TimePicker, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.TimePicker, attributeSet, typedArray, n, n2);
        boolean bl = typedArray.getBoolean(10, false);
        int n3 = typedArray.getInt(8, 1);
        typedArray.recycle();
        this.mMode = n3 == 2 && bl ? context.getResources().getInteger(17694999) : n3;
        this.mDelegate = this.mMode != 2 ? new TimePickerSpinnerDelegate(this, context, attributeSet, n, n2) : new TimePickerClockDelegate(this, context, attributeSet, n, n2);
        this.mDelegate.setAutoFillChangeListener(new _$$Lambda$TimePicker$2FhAB9WgnLgn4zn4f9rRT7DNfjw(this, context));
    }

    static String[] getAmPmStrings(Context object) {
        Object object2 = LocaleData.get((Locale)object.getResources().getConfiguration().locale);
        object = object2.amPm[0].length() > 4 ? object2.narrowAm : object2.amPm[0];
        object2 = object2.amPm[1].length() > 4 ? object2.narrowPm : object2.amPm[1];
        return new String[]{object, object2};
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
    public CharSequence getAccessibilityClassName() {
        return TimePicker.class.getName();
    }

    public View getAmView() {
        return this.mDelegate.getAmView();
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

    @Override
    public int getBaseline() {
        return this.mDelegate.getBaseline();
    }

    @Deprecated
    public Integer getCurrentHour() {
        return this.getHour();
    }

    @Deprecated
    public Integer getCurrentMinute() {
        return this.getMinute();
    }

    public int getHour() {
        return this.mDelegate.getHour();
    }

    public View getHourView() {
        return this.mDelegate.getHourView();
    }

    public int getMinute() {
        return this.mDelegate.getMinute();
    }

    public View getMinuteView() {
        return this.mDelegate.getMinuteView();
    }

    public int getMode() {
        return this.mMode;
    }

    public View getPmView() {
        return this.mDelegate.getPmView();
    }

    public boolean is24HourView() {
        return this.mDelegate.is24Hour();
    }

    @Override
    public boolean isEnabled() {
        return this.mDelegate.isEnabled();
    }

    public /* synthetic */ void lambda$new$0$TimePicker(Context object, TimePicker timePicker, int n, int n2) {
        if ((object = ((Context)object).getSystemService(AutofillManager.class)) != null) {
            ((AutofillManager)object).notifyValueChanged(this);
        }
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
    public void setCurrentHour(Integer n) {
        this.setHour(n);
    }

    @Deprecated
    public void setCurrentMinute(Integer n) {
        this.setMinute(n);
    }

    @Override
    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        this.mDelegate.setEnabled(bl);
    }

    public void setHour(int n) {
        this.mDelegate.setHour(MathUtils.constrain(n, 0, 23));
    }

    public void setIs24HourView(Boolean bl) {
        if (bl == null) {
            return;
        }
        this.mDelegate.setIs24Hour(bl);
    }

    public void setMinute(int n) {
        this.mDelegate.setMinute(MathUtils.constrain(n, 0, 59));
    }

    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        this.mDelegate.setOnTimeChangedListener(onTimeChangedListener);
    }

    public boolean validateInput() {
        return this.mDelegate.validateInput();
    }

    static abstract class AbstractTimePickerDelegate
    implements TimePickerDelegate {
        protected OnTimeChangedListener mAutoFillChangeListener;
        private long mAutofilledValue;
        protected final Context mContext;
        protected final TimePicker mDelegator;
        protected final Locale mLocale;
        protected OnTimeChangedListener mOnTimeChangedListener;

        public AbstractTimePickerDelegate(TimePicker timePicker, Context context) {
            this.mDelegator = timePicker;
            this.mContext = context;
            this.mLocale = context.getResources().getConfiguration().locale;
        }

        @Override
        public final void autofill(AutofillValue autofillValue) {
            if (autofillValue != null && autofillValue.isDate()) {
                long l = autofillValue.getDateValue();
                autofillValue = Calendar.getInstance((Locale)this.mLocale);
                autofillValue.setTimeInMillis(l);
                this.setDate(autofillValue.get(11), autofillValue.get(12));
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
            if (l != 0L) {
                return AutofillValue.forDate(l);
            }
            Calendar calendar = Calendar.getInstance((Locale)this.mLocale);
            calendar.set(11, this.getHour());
            calendar.set(12, this.getMinute());
            return AutofillValue.forDate(calendar.getTimeInMillis());
        }

        protected void resetAutofilledValue() {
            this.mAutofilledValue = 0L;
        }

        @Override
        public void setAutoFillChangeListener(OnTimeChangedListener onTimeChangedListener) {
            this.mAutoFillChangeListener = onTimeChangedListener;
        }

        @Override
        public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
            this.mOnTimeChangedListener = onTimeChangedListener;
        }

        protected static class SavedState
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
            private final int mCurrentItemShowing;
            private final int mHour;
            private final boolean mIs24HourMode;
            private final int mMinute;

            private SavedState(Parcel parcel) {
                super(parcel);
                this.mHour = parcel.readInt();
                this.mMinute = parcel.readInt();
                int n = parcel.readInt();
                boolean bl = true;
                if (n != 1) {
                    bl = false;
                }
                this.mIs24HourMode = bl;
                this.mCurrentItemShowing = parcel.readInt();
            }

            public SavedState(Parcelable parcelable, int n, int n2, boolean bl) {
                this(parcelable, n, n2, bl, 0);
            }

            public SavedState(Parcelable parcelable, int n, int n2, boolean bl, int n3) {
                super(parcelable);
                this.mHour = n;
                this.mMinute = n2;
                this.mIs24HourMode = bl;
                this.mCurrentItemShowing = n3;
            }

            public int getCurrentItemShowing() {
                return this.mCurrentItemShowing;
            }

            public int getHour() {
                return this.mHour;
            }

            public int getMinute() {
                return this.mMinute;
            }

            public boolean is24HourMode() {
                return this.mIs24HourMode;
            }

            @Override
            public void writeToParcel(Parcel parcel, int n) {
                super.writeToParcel(parcel, n);
                parcel.writeInt(this.mHour);
                parcel.writeInt(this.mMinute);
                parcel.writeInt((int)this.mIs24HourMode);
                parcel.writeInt(this.mCurrentItemShowing);
            }

        }

    }

    public static interface OnTimeChangedListener {
        public void onTimeChanged(TimePicker var1, int var2, int var3);
    }

    static interface TimePickerDelegate {
        public void autofill(AutofillValue var1);

        public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent var1);

        public View getAmView();

        public AutofillValue getAutofillValue();

        public int getBaseline();

        public int getHour();

        public View getHourView();

        public int getMinute();

        public View getMinuteView();

        public View getPmView();

        public boolean is24Hour();

        public boolean isEnabled();

        public void onPopulateAccessibilityEvent(AccessibilityEvent var1);

        public void onRestoreInstanceState(Parcelable var1);

        public Parcelable onSaveInstanceState(Parcelable var1);

        public void setAutoFillChangeListener(OnTimeChangedListener var1);

        public void setDate(int var1, int var2);

        public void setEnabled(boolean var1);

        public void setHour(int var1);

        public void setIs24Hour(boolean var1);

        public void setMinute(int var1);

        public void setOnTimeChangedListener(OnTimeChangedListener var1);

        public boolean validateInput();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TimePickerMode {
    }

}

