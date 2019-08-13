/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.graphics.drawable.Drawable;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.Spinner;

public final class Spinner$InspectionCompanion
implements InspectionCompanion<Spinner> {
    private int mDropDownHorizontalOffsetId;
    private int mDropDownVerticalOffsetId;
    private int mDropDownWidthId;
    private int mGravityId;
    private int mPopupBackgroundId;
    private int mPromptId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mDropDownHorizontalOffsetId = propertyMapper.mapInt("dropDownHorizontalOffset", 16843436);
        this.mDropDownVerticalOffsetId = propertyMapper.mapInt("dropDownVerticalOffset", 16843437);
        this.mDropDownWidthId = propertyMapper.mapInt("dropDownWidth", 16843362);
        this.mGravityId = propertyMapper.mapGravity("gravity", 16842927);
        this.mPopupBackgroundId = propertyMapper.mapObject("popupBackground", 16843126);
        this.mPromptId = propertyMapper.mapObject("prompt", 16843131);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(Spinner spinner, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readInt(this.mDropDownHorizontalOffsetId, spinner.getDropDownHorizontalOffset());
            propertyReader.readInt(this.mDropDownVerticalOffsetId, spinner.getDropDownVerticalOffset());
            propertyReader.readInt(this.mDropDownWidthId, spinner.getDropDownWidth());
            propertyReader.readGravity(this.mGravityId, spinner.getGravity());
            propertyReader.readObject(this.mPopupBackgroundId, spinner.getPopupBackground());
            propertyReader.readObject(this.mPromptId, spinner.getPrompt());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

