/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.CompoundButton;

public final class CompoundButton$InspectionCompanion
implements InspectionCompanion<CompoundButton> {
    private int mButtonBlendModeId;
    private int mButtonId;
    private int mButtonTintId;
    private int mButtonTintModeId;
    private int mCheckedId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mButtonId = propertyMapper.mapObject("button", 16843015);
        this.mButtonBlendModeId = propertyMapper.mapObject("buttonBlendMode", 3);
        this.mButtonTintId = propertyMapper.mapObject("buttonTint", 16843887);
        this.mButtonTintModeId = propertyMapper.mapObject("buttonTintMode", 16843888);
        this.mCheckedId = propertyMapper.mapBoolean("checked", 16843014);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(CompoundButton compoundButton, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readObject(this.mButtonId, compoundButton.getButtonDrawable());
            propertyReader.readObject(this.mButtonBlendModeId, (Object)compoundButton.getButtonTintBlendMode());
            propertyReader.readObject(this.mButtonTintId, compoundButton.getButtonTintList());
            propertyReader.readObject(this.mButtonTintModeId, (Object)compoundButton.getButtonTintMode());
            propertyReader.readBoolean(this.mCheckedId, compoundButton.isChecked());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

