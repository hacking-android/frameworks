/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.util.SparseArray;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.TimePicker;
import android.widget._$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI;
import java.util.Objects;
import java.util.function.IntFunction;

public final class TimePicker$InspectionCompanion
implements InspectionCompanion<TimePicker> {
    private int m24HourId;
    private int mHourId;
    private int mMinuteId;
    private boolean mPropertiesMapped = false;
    private int mTimePickerModeId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.m24HourId = propertyMapper.mapBoolean("24Hour", 0);
        this.mHourId = propertyMapper.mapInt("hour", 0);
        this.mMinuteId = propertyMapper.mapInt("minute", 0);
        SparseArray<String> sparseArray = new SparseArray<String>();
        sparseArray.put(1, "spinner");
        sparseArray.put(2, "clock");
        Objects.requireNonNull(sparseArray);
        this.mTimePickerModeId = propertyMapper.mapIntEnum("timePickerMode", 16843956, new _$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(sparseArray));
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(TimePicker timePicker, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readBoolean(this.m24HourId, timePicker.is24HourView());
            propertyReader.readInt(this.mHourId, timePicker.getHour());
            propertyReader.readInt(this.mMinuteId, timePicker.getMinute());
            propertyReader.readIntEnum(this.mTimePickerModeId, timePicker.getMode());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

