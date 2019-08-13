/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.ToggleButton;

public final class ToggleButton$InspectionCompanion
implements InspectionCompanion<ToggleButton> {
    private int mDisabledAlphaId;
    private boolean mPropertiesMapped = false;
    private int mTextOffId;
    private int mTextOnId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mDisabledAlphaId = propertyMapper.mapFloat("disabledAlpha", 16842803);
        this.mTextOffId = propertyMapper.mapObject("textOff", 16843045);
        this.mTextOnId = propertyMapper.mapObject("textOn", 16843044);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(ToggleButton toggleButton, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readFloat(this.mDisabledAlphaId, toggleButton.getDisabledAlpha());
            propertyReader.readObject(this.mTextOffId, toggleButton.getTextOff());
            propertyReader.readObject(this.mTextOnId, toggleButton.getTextOn());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

