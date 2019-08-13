/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.FrameLayout;

public final class FrameLayout$LayoutParams$InspectionCompanion
implements InspectionCompanion<FrameLayout.LayoutParams> {
    private int mLayout_gravityId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mLayout_gravityId = propertyMapper.mapGravity("layout_gravity", 16842931);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(FrameLayout.LayoutParams layoutParams, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readGravity(this.mLayout_gravityId, layoutParams.gravity);
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

