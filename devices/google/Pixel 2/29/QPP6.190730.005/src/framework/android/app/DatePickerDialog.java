/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import java.util.Calendar;

public class DatePickerDialog
extends AlertDialog
implements DialogInterface.OnClickListener,
DatePicker.OnDateChangedListener {
    private static final String DAY = "day";
    private static final String MONTH = "month";
    private static final String YEAR = "year";
    @UnsupportedAppUsage
    private final DatePicker mDatePicker;
    private OnDateSetListener mDateSetListener;
    private final DatePicker.ValidationCallback mValidationCallback = new DatePicker.ValidationCallback(){

        @Override
        public void onValidationChanged(boolean bl) {
            Button button = DatePickerDialog.this.getButton(-1);
            if (button != null) {
                button.setEnabled(bl);
            }
        }
    };

    public DatePickerDialog(Context context) {
        this(context, 0, null, Calendar.getInstance(), -1, -1, -1);
    }

    public DatePickerDialog(Context context, int n) {
        this(context, n, null, Calendar.getInstance(), -1, -1, -1);
    }

    public DatePickerDialog(Context context, int n, OnDateSetListener onDateSetListener, int n2, int n3, int n4) {
        this(context, n, onDateSetListener, null, n2, n3, n4);
    }

    private DatePickerDialog(Context context, int n, OnDateSetListener onDateSetListener, Calendar calendar, int n2, int n3, int n4) {
        super(context, DatePickerDialog.resolveDialogTheme(context, n));
        context = this.getContext();
        View view = LayoutInflater.from(context).inflate(17367129, null);
        this.setView(view);
        this.setButton(-1, (CharSequence)context.getString(17039370), this);
        this.setButton(-2, (CharSequence)context.getString(17039360), this);
        this.setButtonPanelLayoutHint(1);
        if (calendar != null) {
            n2 = calendar.get(1);
            n3 = calendar.get(2);
            n4 = calendar.get(5);
        }
        this.mDatePicker = (DatePicker)view.findViewById(16908858);
        this.mDatePicker.init(n2, n3, n4, this);
        this.mDatePicker.setValidationCallback(this.mValidationCallback);
        this.mDateSetListener = onDateSetListener;
    }

    public DatePickerDialog(Context context, OnDateSetListener onDateSetListener, int n, int n2, int n3) {
        this(context, 0, onDateSetListener, null, n, n2, n3);
    }

    static int resolveDialogTheme(Context context, int n) {
        if (n == 0) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(16843948, typedValue, true);
            return typedValue.resourceId;
        }
        return n;
    }

    public DatePicker getDatePicker() {
        return this.mDatePicker;
    }

    @Override
    public void onClick(DialogInterface object, int n) {
        if (n != -2) {
            if (n == -1 && this.mDateSetListener != null) {
                this.mDatePicker.clearFocus();
                OnDateSetListener onDateSetListener = this.mDateSetListener;
                object = this.mDatePicker;
                onDateSetListener.onDateSet((DatePicker)object, ((DatePicker)object).getYear(), this.mDatePicker.getMonth(), this.mDatePicker.getDayOfMonth());
            }
        } else {
            this.cancel();
        }
    }

    @Override
    public void onDateChanged(DatePicker datePicker, int n, int n2, int n3) {
        this.mDatePicker.init(n, n2, n3, this);
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        int n = bundle.getInt(YEAR);
        int n2 = bundle.getInt(MONTH);
        int n3 = bundle.getInt(DAY);
        this.mDatePicker.init(n, n2, n3, this);
    }

    @Override
    public Bundle onSaveInstanceState() {
        Bundle bundle = super.onSaveInstanceState();
        bundle.putInt(YEAR, this.mDatePicker.getYear());
        bundle.putInt(MONTH, this.mDatePicker.getMonth());
        bundle.putInt(DAY, this.mDatePicker.getDayOfMonth());
        return bundle;
    }

    public void setOnDateSetListener(OnDateSetListener onDateSetListener) {
        this.mDateSetListener = onDateSetListener;
    }

    public void updateDate(int n, int n2, int n3) {
        this.mDatePicker.updateDate(n, n2, n3);
    }

    public static interface OnDateSetListener {
        public void onDateSet(DatePicker var1, int var2, int var3, int var4);
    }

}

