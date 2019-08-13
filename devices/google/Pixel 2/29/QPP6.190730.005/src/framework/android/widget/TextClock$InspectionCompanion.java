/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.TextClock;

public final class TextClock$InspectionCompanion
implements InspectionCompanion<TextClock> {
    private int mFormat12HourId;
    private int mFormat24HourId;
    private int mIs24HourModeEnabledId;
    private boolean mPropertiesMapped = false;
    private int mTimeZoneId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mFormat12HourId = propertyMapper.mapObject("format12Hour", 16843722);
        this.mFormat24HourId = propertyMapper.mapObject("format24Hour", 16843723);
        this.mIs24HourModeEnabledId = propertyMapper.mapBoolean("is24HourModeEnabled", 0);
        this.mTimeZoneId = propertyMapper.mapObject("timeZone", 16843724);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(TextClock textClock, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readObject(this.mFormat12HourId, textClock.getFormat12Hour());
            propertyReader.readObject(this.mFormat24HourId, textClock.getFormat24Hour());
            propertyReader.readBoolean(this.mIs24HourModeEnabledId, textClock.is24HourModeEnabled());
            propertyReader.readObject(this.mTimeZoneId, textClock.getTimeZone());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

