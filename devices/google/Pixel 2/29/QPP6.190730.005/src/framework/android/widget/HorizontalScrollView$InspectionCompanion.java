/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.HorizontalScrollView;

public final class HorizontalScrollView$InspectionCompanion
implements InspectionCompanion<HorizontalScrollView> {
    private int mFillViewportId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mFillViewportId = propertyMapper.mapBoolean("fillViewport", 16843130);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(HorizontalScrollView horizontalScrollView, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readBoolean(this.mFillViewportId, horizontalScrollView.isFillViewport());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

