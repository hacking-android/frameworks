/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.RelativeLayout;

public final class RelativeLayout$InspectionCompanion
implements InspectionCompanion<RelativeLayout> {
    private int mGravityId;
    private int mIgnoreGravityId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mGravityId = propertyMapper.mapGravity("gravity", 16842927);
        this.mIgnoreGravityId = propertyMapper.mapInt("ignoreGravity", 16843263);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(RelativeLayout relativeLayout, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readGravity(this.mGravityId, relativeLayout.getGravity());
            propertyReader.readInt(this.mIgnoreGravityId, relativeLayout.getIgnoreGravity());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

