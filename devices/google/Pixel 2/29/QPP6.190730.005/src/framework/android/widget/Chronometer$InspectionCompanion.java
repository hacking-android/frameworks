/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.Chronometer;

public final class Chronometer$InspectionCompanion
implements InspectionCompanion<Chronometer> {
    private int mCountDownId;
    private int mFormatId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mCountDownId = propertyMapper.mapBoolean("countDown", 16844059);
        this.mFormatId = propertyMapper.mapObject("format", 16843013);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(Chronometer chronometer, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readBoolean(this.mCountDownId, chronometer.isCountDown());
            propertyReader.readObject(this.mFormatId, chronometer.getFormat());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

