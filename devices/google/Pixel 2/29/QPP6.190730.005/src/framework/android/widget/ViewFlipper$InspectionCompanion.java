/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.ViewFlipper;

public final class ViewFlipper$InspectionCompanion
implements InspectionCompanion<ViewFlipper> {
    private int mAutoStartId;
    private int mFlipIntervalId;
    private int mFlippingId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mAutoStartId = propertyMapper.mapBoolean("autoStart", 16843445);
        this.mFlipIntervalId = propertyMapper.mapInt("flipInterval", 16843129);
        this.mFlippingId = propertyMapper.mapBoolean("flipping", 0);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(ViewFlipper viewFlipper, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readBoolean(this.mAutoStartId, viewFlipper.isAutoStart());
            propertyReader.readInt(this.mFlipIntervalId, viewFlipper.getFlipInterval());
            propertyReader.readBoolean(this.mFlippingId, viewFlipper.isFlipping());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

