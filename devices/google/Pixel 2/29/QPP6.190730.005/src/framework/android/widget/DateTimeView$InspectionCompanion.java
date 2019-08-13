/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.DateTimeView;

public final class DateTimeView$InspectionCompanion
implements InspectionCompanion<DateTimeView> {
    private boolean mPropertiesMapped = false;
    private int mShowReleativeId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mShowReleativeId = propertyMapper.mapBoolean("showReleative", 0);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(DateTimeView dateTimeView, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readBoolean(this.mShowReleativeId, dateTimeView.isShowRelativeTime());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

