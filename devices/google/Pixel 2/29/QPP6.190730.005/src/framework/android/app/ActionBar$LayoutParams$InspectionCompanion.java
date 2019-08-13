/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.ActionBar;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;

public final class ActionBar$LayoutParams$InspectionCompanion
implements InspectionCompanion<ActionBar.LayoutParams> {
    private int mLayout_gravityId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mLayout_gravityId = propertyMapper.mapGravity("layout_gravity", 16842931);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(ActionBar.LayoutParams layoutParams, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readGravity(this.mLayout_gravityId, layoutParams.gravity);
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

