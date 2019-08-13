/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.graphics.drawable.Drawable;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.AutoCompleteTextView;

public final class AutoCompleteTextView$InspectionCompanion
implements InspectionCompanion<AutoCompleteTextView> {
    private int mCompletionHintId;
    private int mCompletionThresholdId;
    private int mDropDownHeightId;
    private int mDropDownHorizontalOffsetId;
    private int mDropDownVerticalOffsetId;
    private int mDropDownWidthId;
    private int mPopupBackgroundId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mCompletionHintId = propertyMapper.mapObject("completionHint", 16843122);
        this.mCompletionThresholdId = propertyMapper.mapInt("completionThreshold", 16843124);
        this.mDropDownHeightId = propertyMapper.mapInt("dropDownHeight", 16843395);
        this.mDropDownHorizontalOffsetId = propertyMapper.mapInt("dropDownHorizontalOffset", 16843436);
        this.mDropDownVerticalOffsetId = propertyMapper.mapInt("dropDownVerticalOffset", 16843437);
        this.mDropDownWidthId = propertyMapper.mapInt("dropDownWidth", 16843362);
        this.mPopupBackgroundId = propertyMapper.mapObject("popupBackground", 16843126);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(AutoCompleteTextView autoCompleteTextView, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readObject(this.mCompletionHintId, autoCompleteTextView.getCompletionHint());
            propertyReader.readInt(this.mCompletionThresholdId, autoCompleteTextView.getThreshold());
            propertyReader.readInt(this.mDropDownHeightId, autoCompleteTextView.getDropDownHeight());
            propertyReader.readInt(this.mDropDownHorizontalOffsetId, autoCompleteTextView.getDropDownHorizontalOffset());
            propertyReader.readInt(this.mDropDownVerticalOffsetId, autoCompleteTextView.getDropDownVerticalOffset());
            propertyReader.readInt(this.mDropDownWidthId, autoCompleteTextView.getDropDownWidth());
            propertyReader.readObject(this.mPopupBackgroundId, autoCompleteTextView.getDropDownBackground());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

