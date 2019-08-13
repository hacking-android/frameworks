/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.ScrollView;

public final class ScrollView$InspectionCompanion
implements InspectionCompanion<ScrollView> {
    private int mFillViewportId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mFillViewportId = propertyMapper.mapBoolean("fillViewport", 16843130);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(ScrollView scrollView, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readBoolean(this.mFillViewportId, scrollView.isFillViewport());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

