/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.FrameLayout;

public final class FrameLayout$InspectionCompanion
implements InspectionCompanion<FrameLayout> {
    private int mMeasureAllChildrenId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mMeasureAllChildrenId = propertyMapper.mapBoolean("measureAllChildren", 16843018);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(FrameLayout frameLayout, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readBoolean(this.mMeasureAllChildrenId, frameLayout.getMeasureAllChildren());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

