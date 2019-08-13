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
import android.widget.CheckedTextView;

public final class CheckedTextView$InspectionCompanion
implements InspectionCompanion<CheckedTextView> {
    private int mCheckMarkId;
    private int mCheckMarkTintBlendModeId;
    private int mCheckMarkTintId;
    private int mCheckMarkTintModeId;
    private int mCheckedId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mCheckMarkId = propertyMapper.mapObject("checkMark", 16843016);
        this.mCheckMarkTintId = propertyMapper.mapObject("checkMarkTint", 16843943);
        this.mCheckMarkTintBlendModeId = propertyMapper.mapObject("checkMarkTintBlendMode", 3);
        this.mCheckMarkTintModeId = propertyMapper.mapObject("checkMarkTintMode", 16843944);
        this.mCheckedId = propertyMapper.mapBoolean("checked", 16843014);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(CheckedTextView checkedTextView, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readObject(this.mCheckMarkId, checkedTextView.getCheckMarkDrawable());
            propertyReader.readObject(this.mCheckMarkTintId, checkedTextView.getCheckMarkTintList());
            propertyReader.readObject(this.mCheckMarkTintBlendModeId, (Object)checkedTextView.getCheckMarkTintBlendMode());
            propertyReader.readObject(this.mCheckMarkTintModeId, (Object)checkedTextView.getCheckMarkTintMode());
            propertyReader.readBoolean(this.mCheckedId, checkedTextView.isChecked());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

