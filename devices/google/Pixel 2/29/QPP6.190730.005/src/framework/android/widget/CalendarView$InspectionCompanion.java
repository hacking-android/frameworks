/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.graphics.drawable.Drawable;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.CalendarView;

public final class CalendarView$InspectionCompanion
implements InspectionCompanion<CalendarView> {
    private int mDateTextAppearanceId;
    private int mFirstDayOfWeekId;
    private int mFocusedMonthDateColorId;
    private int mMaxDateId;
    private int mMinDateId;
    private boolean mPropertiesMapped = false;
    private int mSelectedDateVerticalBarId;
    private int mSelectedWeekBackgroundColorId;
    private int mShowWeekNumberId;
    private int mShownWeekCountId;
    private int mUnfocusedMonthDateColorId;
    private int mWeekDayTextAppearanceId;
    private int mWeekNumberColorId;
    private int mWeekSeparatorLineColorId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mDateTextAppearanceId = propertyMapper.mapResourceId("dateTextAppearance", 16843593);
        this.mFirstDayOfWeekId = propertyMapper.mapInt("firstDayOfWeek", 16843581);
        this.mFocusedMonthDateColorId = propertyMapper.mapColor("focusedMonthDateColor", 16843587);
        this.mMaxDateId = propertyMapper.mapLong("maxDate", 16843584);
        this.mMinDateId = propertyMapper.mapLong("minDate", 16843583);
        this.mSelectedDateVerticalBarId = propertyMapper.mapObject("selectedDateVerticalBar", 16843591);
        this.mSelectedWeekBackgroundColorId = propertyMapper.mapColor("selectedWeekBackgroundColor", 16843586);
        this.mShowWeekNumberId = propertyMapper.mapBoolean("showWeekNumber", 16843582);
        this.mShownWeekCountId = propertyMapper.mapInt("shownWeekCount", 16843585);
        this.mUnfocusedMonthDateColorId = propertyMapper.mapColor("unfocusedMonthDateColor", 16843588);
        this.mWeekDayTextAppearanceId = propertyMapper.mapResourceId("weekDayTextAppearance", 16843592);
        this.mWeekNumberColorId = propertyMapper.mapColor("weekNumberColor", 16843589);
        this.mWeekSeparatorLineColorId = propertyMapper.mapColor("weekSeparatorLineColor", 16843590);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(CalendarView calendarView, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readResourceId(this.mDateTextAppearanceId, calendarView.getDateTextAppearance());
            propertyReader.readInt(this.mFirstDayOfWeekId, calendarView.getFirstDayOfWeek());
            propertyReader.readColor(this.mFocusedMonthDateColorId, calendarView.getFocusedMonthDateColor());
            propertyReader.readLong(this.mMaxDateId, calendarView.getMaxDate());
            propertyReader.readLong(this.mMinDateId, calendarView.getMinDate());
            propertyReader.readObject(this.mSelectedDateVerticalBarId, calendarView.getSelectedDateVerticalBar());
            propertyReader.readColor(this.mSelectedWeekBackgroundColorId, calendarView.getSelectedWeekBackgroundColor());
            propertyReader.readBoolean(this.mShowWeekNumberId, calendarView.getShowWeekNumber());
            propertyReader.readInt(this.mShownWeekCountId, calendarView.getShownWeekCount());
            propertyReader.readColor(this.mUnfocusedMonthDateColorId, calendarView.getUnfocusedMonthDateColor());
            propertyReader.readResourceId(this.mWeekDayTextAppearanceId, calendarView.getWeekDayTextAppearance());
            propertyReader.readColor(this.mWeekNumberColorId, calendarView.getWeekNumberColor());
            propertyReader.readColor(this.mWeekSeparatorLineColorId, calendarView.getWeekSeparatorLineColor());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

