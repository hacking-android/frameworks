/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.LinearLayout;

public final class LinearLayout$LayoutParams$InspectionCompanion
implements InspectionCompanion<LinearLayout.LayoutParams> {
    private int mLayout_gravityId;
    private int mLayout_weightId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mLayout_gravityId = propertyMapper.mapGravity("layout_gravity", 16842931);
        this.mLayout_weightId = propertyMapper.mapFloat("layout_weight", 16843137);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(LinearLayout.LayoutParams layoutParams, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readGravity(this.mLayout_gravityId, layoutParams.gravity);
            propertyReader.readFloat(this.mLayout_weightId, layoutParams.weight);
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

