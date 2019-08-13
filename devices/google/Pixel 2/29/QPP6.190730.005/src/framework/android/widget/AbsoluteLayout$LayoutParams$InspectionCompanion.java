/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.AbsoluteLayout;

public final class AbsoluteLayout$LayoutParams$InspectionCompanion
implements InspectionCompanion<AbsoluteLayout.LayoutParams> {
    private int mLayout_xId;
    private int mLayout_yId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mLayout_xId = propertyMapper.mapInt("layout_x", 16843135);
        this.mLayout_yId = propertyMapper.mapInt("layout_y", 16843136);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(AbsoluteLayout.LayoutParams layoutParams, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readInt(this.mLayout_xId, layoutParams.x);
            propertyReader.readInt(this.mLayout_yId, layoutParams.y);
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

