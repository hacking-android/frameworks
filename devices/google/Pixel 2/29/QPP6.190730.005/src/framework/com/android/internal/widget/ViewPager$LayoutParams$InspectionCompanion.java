/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import com.android.internal.widget.ViewPager;

public final class ViewPager$LayoutParams$InspectionCompanion
implements InspectionCompanion<ViewPager.LayoutParams> {
    private int mLayout_gravityId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mLayout_gravityId = propertyMapper.mapGravity("layout_gravity", 16842931);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(ViewPager.LayoutParams layoutParams, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readGravity(this.mLayout_gravityId, layoutParams.gravity);
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

