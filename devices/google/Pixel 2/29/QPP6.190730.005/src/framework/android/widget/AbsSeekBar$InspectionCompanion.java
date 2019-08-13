/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.PorterDuff;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.AbsSeekBar;

public final class AbsSeekBar$InspectionCompanion
implements InspectionCompanion<AbsSeekBar> {
    private boolean mPropertiesMapped = false;
    private int mThumbTintId;
    private int mThumbTintModeId;
    private int mTickMarkTintBlendModeId;
    private int mTickMarkTintId;
    private int mTickMarkTintModeId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mThumbTintId = propertyMapper.mapObject("thumbTint", 16843889);
        this.mThumbTintModeId = propertyMapper.mapObject("thumbTintMode", 16843890);
        this.mTickMarkTintId = propertyMapper.mapObject("tickMarkTint", 16844043);
        this.mTickMarkTintBlendModeId = propertyMapper.mapObject("tickMarkTintBlendMode", 7);
        this.mTickMarkTintModeId = propertyMapper.mapObject("tickMarkTintMode", 16844044);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(AbsSeekBar absSeekBar, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readObject(this.mThumbTintId, absSeekBar.getThumbTintList());
            propertyReader.readObject(this.mThumbTintModeId, (Object)absSeekBar.getThumbTintMode());
            propertyReader.readObject(this.mTickMarkTintId, absSeekBar.getTickMarkTintList());
            propertyReader.readObject(this.mTickMarkTintBlendModeId, (Object)absSeekBar.getTickMarkTintBlendMode());
            propertyReader.readObject(this.mTickMarkTintModeId, (Object)absSeekBar.getTickMarkTintMode());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

