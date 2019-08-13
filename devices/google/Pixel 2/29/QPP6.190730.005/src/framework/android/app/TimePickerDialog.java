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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

public class TimePickerDialog
extends AlertDialog
implements DialogInterface.OnClickListener,
TimePicker.OnTimeChangedListener {
    private static final String HOUR = "hour";
    private static final String IS_24_HOUR = "is24hour";
    private static final String MINUTE = "minute";
    private final int mInitialHourOfDay;
    private final int mInitialMinute;
    private final boolean mIs24HourView;
    @UnsupportedAppUsage
    private final TimePicker mTimePicker;
    private final OnTimeSetListener mTimeSetListener;

    public TimePickerDialog(Context context, int n, OnTimeSetListener object, int n2, int n3, boolean bl) {
        super(context, TimePickerDialog.resolveDialogTheme(context, n));
        this.mTimeSetListener = object;
        this.mInitialHourOfDay = n2;
        this.mInitialMinute = n3;
        this.mIs24HourView = bl;
        context = this.getContext();
        object = LayoutInflater.from(context).inflate(17367320, null);
        this.setView((View)object);
        this.setButton(-1, (CharSequence)context.getString(17039370), this);
        this.setButton(-2, (CharSequence)context.getString(17039360), this);
        this.setButtonPanelLayoutHint(1);
        this.mTimePicker = (TimePicker)((View)object).findViewById(16909461);
        this.mTimePicker.setIs24HourView(this.mIs24HourView);
        this.mTimePicker.setCurrentHour(this.mInitialHourOfDay);
        this.mTimePicker.setCurrentMinute(this.mInitialMinute);
        this.mTimePicker.setOnTimeChangedListener(this);
    }

    public TimePickerDialog(Context context, OnTimeSetListener onTimeSetListener, int n, int n2, boolean bl) {
        this(context, 0, onTimeSetListener, n, n2, bl);
    }

    static int resolveDialogTheme(Context context, int n) {
        if (n == 0) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(16843934, typedValue, true);
            return typedValue.resourceId;
        }
        return n;
    }

    public TimePicker getTimePicker() {
        return this.mTimePicker;
    }

    @Override
    public void onClick(DialogInterface object, int n) {
        if (n != -2) {
            OnTimeSetListener onTimeSetListener;
            if (n == -1 && (onTimeSetListener = this.mTimeSetListener) != null) {
                object = this.mTimePicker;
                onTimeSetListener.onTimeSet((TimePicker)object, ((TimePicker)object).getCurrentHour(), this.mTimePicker.getCurrentMinute());
            }
        } else {
            this.cancel();
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        int n = bundle.getInt(HOUR);
        int n2 = bundle.getInt(MINUTE);
        this.mTimePicker.setIs24HourView(bundle.getBoolean(IS_24_HOUR));
        this.mTimePicker.setCurrentHour(n);
        this.mTimePicker.setCurrentMinute(n2);
    }

    @Override
    public Bundle onSaveInstanceState() {
        Bundle bundle = super.onSaveInstanceState();
        bundle.putInt(HOUR, this.mTimePicker.getCurrentHour());
        bundle.putInt(MINUTE, this.mTimePicker.getCurrentMinute());
        bundle.putBoolean(IS_24_HOUR, this.mTimePicker.is24HourView());
        return bundle;
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int n, int n2) {
    }

    @Override
    public void show() {
        super.show();
        this.getButton(-1).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View callback) {
                if (TimePickerDialog.this.mTimePicker.validateInput()) {
                    callback = TimePickerDialog.this;
                    ((TimePickerDialog)callback).onClick((DialogInterface)((Object)callback), -1);
                    TimePickerDialog.this.mTimePicker.clearFocus();
                    TimePickerDialog.this.dismiss();
                }
            }
        });
    }

    public void updateTime(int n, int n2) {
        this.mTimePicker.setCurrentHour(n);
        this.mTimePicker.setCurrentMinute(n2);
    }

    public static interface OnTimeSetListener {
        public void onTimeSet(TimePicker var1, int var2, int var3);
    }

}

