/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.widget.OnDateChangedListener;
import java.util.Calendar;

interface DatePickerController {
    public Calendar getSelectedDay();

    public void onYearSelected(int var1);

    public void registerOnDateChangedListener(OnDateChangedListener var1);

    public void tryVibrate();
}

