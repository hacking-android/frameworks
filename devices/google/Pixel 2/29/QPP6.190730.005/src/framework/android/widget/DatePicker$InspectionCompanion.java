/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.util.SparseArray;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.DatePicker;
import android.widget._$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI;
import java.util.Objects;
import java.util.function.IntFunction;

public final class DatePicker$InspectionCompanion
implements InspectionCompanion<DatePicker> {
    private int mCalendarViewShownId;
    private int mDatePickerModeId;
    private int mDayOfMonthId;
    private int mFirstDayOfWeekId;
    private int mMaxDateId;
    private int mMinDateId;
    private int mMonthId;
    private boolean mPropertiesMapped = false;
    private int mSpinnersShownId;
    private int mYearId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mCalendarViewShownId = propertyMapper.mapBoolean("calendarViewShown", 16843596);
        SparseArray<String> sparseArray = new SparseArray<String>();
        sparseArray.put(1, "spinner");
        sparseArray.put(2, "calendar");
        Objects.requireNonNull(sparseArray);
        this.mDatePickerModeId = propertyMapper.mapIntEnum("datePickerMode", 16843955, new _$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(sparseArray));
        this.mDayOfMonthId = propertyMapper.mapInt("dayOfMonth", 0);
        this.mFirstDayOfWeekId = propertyMapper.mapInt("firstDayOfWeek", 16843581);
        this.mMaxDateId = propertyMapper.mapLong("maxDate", 16843584);
        this.mMinDateId = propertyMapper.mapLong("minDate", 16843583);
        this.mMonthId = propertyMapper.mapInt("month", 0);
        this.mSpinnersShownId = propertyMapper.mapBoolean("spinnersShown", 16843595);
        this.mYearId = propertyMapper.mapInt("year", 0);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(DatePicker datePicker, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readBoolean(this.mCalendarViewShownId, datePicker.getCalendarViewShown());
            propertyReader.readIntEnum(this.mDatePickerModeId, datePicker.getMode());
            propertyReader.readInt(this.mDayOfMonthId, datePicker.getDayOfMonth());
            propertyReader.readInt(this.mFirstDayOfWeekId, datePicker.getFirstDayOfWeek());
            propertyReader.readLong(this.mMaxDateId, datePicker.getMaxDate());
            propertyReader.readLong(this.mMinDateId, datePicker.getMinDate());
            propertyReader.readInt(this.mMonthId, datePicker.getMonth());
            propertyReader.readBoolean(this.mSpinnersShownId, datePicker.getSpinnersShown());
            propertyReader.readInt(this.mYearId, datePicker.getYear());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

