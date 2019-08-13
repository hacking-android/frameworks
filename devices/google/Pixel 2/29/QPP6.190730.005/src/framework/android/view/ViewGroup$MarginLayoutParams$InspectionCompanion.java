/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.view.ViewGroup;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;

public final class ViewGroup$MarginLayoutParams$InspectionCompanion
implements InspectionCompanion<ViewGroup.MarginLayoutParams> {
    private int mLayout_marginBottomId;
    private int mLayout_marginLeftId;
    private int mLayout_marginRightId;
    private int mLayout_marginTopId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mLayout_marginBottomId = propertyMapper.mapInt("layout_marginBottom", 16843002);
        this.mLayout_marginLeftId = propertyMapper.mapInt("layout_marginLeft", 16842999);
        this.mLayout_marginRightId = propertyMapper.mapInt("layout_marginRight", 16843001);
        this.mLayout_marginTopId = propertyMapper.mapInt("layout_marginTop", 16843000);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(ViewGroup.MarginLayoutParams marginLayoutParams, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readInt(this.mLayout_marginBottomId, marginLayoutParams.bottomMargin);
            propertyReader.readInt(this.mLayout_marginLeftId, marginLayoutParams.leftMargin);
            propertyReader.readInt(this.mLayout_marginRightId, marginLayoutParams.rightMargin);
            propertyReader.readInt(this.mLayout_marginTopId, marginLayoutParams.topMargin);
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

