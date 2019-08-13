/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePickerClockDelegate;

public class TextInputTimePickerView
extends RelativeLayout {
    private static final int AM = 0;
    public static final int AMPM = 2;
    public static final int HOURS = 0;
    public static final int MINUTES = 1;
    private static final int PM = 1;
    private final Spinner mAmPmSpinner;
    private final TextView mErrorLabel;
    private boolean mErrorShowing;
    private final EditText mHourEditText;
    private boolean mHourFormatStartsAtZero;
    private final TextView mHourLabel;
    private final TextView mInputSeparatorView;
    private boolean mIs24Hour;
    private OnValueTypedListener mListener;
    private final EditText mMinuteEditText;
    private final TextView mMinuteLabel;
    private boolean mTimeSet;

    public TextInputTimePickerView(Context context) {
        this(context, null);
    }

    public TextInputTimePickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextInputTimePickerView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public TextInputTimePickerView(Context object, AttributeSet arrstring, int n, int n2) {
        super((Context)object, (AttributeSet)arrstring, n, n2);
        TextInputTimePickerView.inflate((Context)object, 17367325, this);
        this.mHourEditText = (EditText)this.findViewById(16909024);
        this.mMinuteEditText = (EditText)this.findViewById(16909025);
        this.mInputSeparatorView = (TextView)this.findViewById(16909027);
        this.mErrorLabel = (TextView)this.findViewById(16909053);
        this.mHourLabel = (TextView)this.findViewById(16909054);
        this.mMinuteLabel = (TextView)this.findViewById(16909055);
        this.mHourEditText.addTextChangedListener(new TextWatcher((Context)object){
            final /* synthetic */ Context val$context;
            {
                this.val$context = context;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextInputTimePickerView.this.parseAndSetHourInternal(editable.toString()) && editable.length() > 1 && !((AccessibilityManager)this.val$context.getSystemService("accessibility")).isEnabled()) {
                    TextInputTimePickerView.this.mMinuteEditText.requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.mMinuteEditText.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable editable) {
                TextInputTimePickerView.this.parseAndSetMinuteInternal(editable.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.mAmPmSpinner = (Spinner)this.findViewById(16908725);
        arrstring = TimePicker.getAmPmStrings((Context)object);
        object = new ArrayAdapter((Context)object, 17367049);
        ((ArrayAdapter)object).add(TimePickerClockDelegate.obtainVerbatim(arrstring[0]));
        ((ArrayAdapter)object).add(TimePickerClockDelegate.obtainVerbatim(arrstring[1]));
        this.mAmPmSpinner.setAdapter((SpinnerAdapter)object);
        this.mAmPmSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                if (n == 0) {
                    TextInputTimePickerView.this.mListener.onValueChanged(2, 0);
                } else {
                    TextInputTimePickerView.this.mListener.onValueChanged(2, 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private int getHourOfDayFromLocalizedHour(int n) {
        int n2;
        int n3 = n;
        if (this.mIs24Hour) {
            n2 = n3;
            if (!this.mHourFormatStartsAtZero) {
                n2 = n3;
                if (n == 24) {
                    n2 = 0;
                }
            }
        } else {
            int n4 = n3;
            if (!this.mHourFormatStartsAtZero) {
                n4 = n3;
                if (n == 12) {
                    n4 = 0;
                }
            }
            n2 = n4;
            if (this.mAmPmSpinner.getSelectedItemPosition() == 1) {
                n2 = n4 + 12;
            }
        }
        return n2;
    }

    private boolean isTimeSet() {
        return this.mTimeSet;
    }

    private boolean isValidLocalizedHour(int n) {
        boolean bl = this.mHourFormatStartsAtZero;
        boolean bl2 = true;
        int n2 = bl ^ true;
        int n3 = this.mIs24Hour ? 23 : 11;
        if (n < n2 || n > n3 + n2) {
            bl2 = false;
        }
        return bl2;
    }

    private boolean parseAndSetHourInternal(String string2) {
        int n;
        block6 : {
            int n2;
            block7 : {
                try {
                    n = Integer.parseInt(string2);
                    boolean bl = this.isValidLocalizedHour(n);
                    n2 = 1;
                    if (bl) break block6;
                }
                catch (NumberFormatException numberFormatException) {
                    return false;
                }
                if (!this.mHourFormatStartsAtZero) break block7;
                n2 = 0;
            }
            int n3 = this.mIs24Hour ? 23 : n2 + 11;
            this.mListener.onValueChanged(0, this.getHourOfDayFromLocalizedHour(MathUtils.constrain(n, n2, n3)));
            return false;
        }
        this.mListener.onValueChanged(0, this.getHourOfDayFromLocalizedHour(n));
        this.setTimeSet(true);
        return true;
    }

    private boolean parseAndSetMinuteInternal(String string2) {
        int n;
        block4 : {
            try {
                n = Integer.parseInt(string2);
                if (n < 0 || n > 59) break block4;
            }
            catch (NumberFormatException numberFormatException) {
                return false;
            }
            this.mListener.onValueChanged(1, n);
            this.setTimeSet(true);
            return true;
        }
        this.mListener.onValueChanged(1, MathUtils.constrain(n, 0, 59));
        return false;
    }

    private void setError(boolean bl) {
        this.mErrorShowing = bl;
        TextView textView = this.mErrorLabel;
        int n = 0;
        int n2 = bl ? 0 : 4;
        textView.setVisibility(n2);
        textView = this.mHourLabel;
        n2 = bl ? 4 : 0;
        textView.setVisibility(n2);
        textView = this.mMinuteLabel;
        n2 = n;
        if (bl) {
            n2 = 4;
        }
        textView.setVisibility(n2);
    }

    private void setTimeSet(boolean bl) {
        bl = this.mTimeSet || bl;
        this.mTimeSet = bl;
    }

    void setHourFormat(int n) {
        this.mHourEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(n)});
        this.mMinuteEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(n)});
        LocaleList localeList = this.mContext.getResources().getConfiguration().getLocales();
        this.mHourEditText.setImeHintLocales(localeList);
        this.mMinuteEditText.setImeHintLocales(localeList);
    }

    void setListener(OnValueTypedListener onValueTypedListener) {
        this.mListener = onValueTypedListener;
    }

    void updateSeparator(String string2) {
        this.mInputSeparatorView.setText(string2);
    }

    void updateTextInputValues(int n, int n2, int n3, boolean bl, boolean bl2) {
        this.mIs24Hour = bl;
        this.mHourFormatStartsAtZero = bl2;
        Spinner spinner = this.mAmPmSpinner;
        int n4 = bl ? 4 : 0;
        spinner.setVisibility(n4);
        if (n3 == 0) {
            this.mAmPmSpinner.setSelection(0);
        } else {
            this.mAmPmSpinner.setSelection(1);
        }
        if (this.isTimeSet()) {
            this.mHourEditText.setText(String.format("%d", n));
            this.mMinuteEditText.setText(String.format("%02d", n2));
        } else {
            this.mHourEditText.setHint(String.format("%d", n));
            this.mMinuteEditText.setHint(String.format("%02d", n2));
        }
        if (this.mErrorShowing) {
            this.validateInput();
        }
    }

    boolean validateInput() {
        String string2 = TextUtils.isEmpty(this.mHourEditText.getText()) ? this.mHourEditText.getHint().toString() : this.mHourEditText.getText().toString();
        String string3 = TextUtils.isEmpty(this.mMinuteEditText.getText()) ? this.mMinuteEditText.getHint().toString() : this.mMinuteEditText.getText().toString();
        boolean bl = this.parseAndSetHourInternal(string2);
        boolean bl2 = true;
        if (bl = bl && this.parseAndSetMinuteInternal(string3)) {
            bl2 = false;
        }
        this.setError(bl2);
        return bl;
    }

    static interface OnValueTypedListener {
        public void onValueChanged(int var1, int var2);
    }

}

