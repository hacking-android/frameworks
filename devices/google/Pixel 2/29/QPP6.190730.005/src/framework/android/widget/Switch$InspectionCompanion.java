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
import android.widget.Switch;

public final class Switch$InspectionCompanion
implements InspectionCompanion<Switch> {
    private boolean mPropertiesMapped = false;
    private int mShowTextId;
    private int mSplitTrackId;
    private int mSwitchMinWidthId;
    private int mSwitchPaddingId;
    private int mTextOffId;
    private int mTextOnId;
    private int mThumbId;
    private int mThumbTextPaddingId;
    private int mThumbTintBlendModeId;
    private int mThumbTintId;
    private int mThumbTintModeId;
    private int mTrackId;
    private int mTrackTintBlendModeId;
    private int mTrackTintId;
    private int mTrackTintModeId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mShowTextId = propertyMapper.mapBoolean("showText", 16843949);
        this.mSplitTrackId = propertyMapper.mapBoolean("splitTrack", 16843852);
        this.mSwitchMinWidthId = propertyMapper.mapInt("switchMinWidth", 16843632);
        this.mSwitchPaddingId = propertyMapper.mapInt("switchPadding", 16843633);
        this.mTextOffId = propertyMapper.mapObject("textOff", 16843045);
        this.mTextOnId = propertyMapper.mapObject("textOn", 16843044);
        this.mThumbId = propertyMapper.mapObject("thumb", 16843074);
        this.mThumbTextPaddingId = propertyMapper.mapInt("thumbTextPadding", 16843634);
        this.mThumbTintId = propertyMapper.mapObject("thumbTint", 16843889);
        this.mThumbTintBlendModeId = propertyMapper.mapObject("thumbTintBlendMode", 10);
        this.mThumbTintModeId = propertyMapper.mapObject("thumbTintMode", 16843890);
        this.mTrackId = propertyMapper.mapObject("track", 16843631);
        this.mTrackTintId = propertyMapper.mapObject("trackTint", 16843993);
        this.mTrackTintBlendModeId = propertyMapper.mapObject("trackTintBlendMode", 13);
        this.mTrackTintModeId = propertyMapper.mapObject("trackTintMode", 16843994);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(Switch switch_, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readBoolean(this.mShowTextId, switch_.getShowText());
            propertyReader.readBoolean(this.mSplitTrackId, switch_.getSplitTrack());
            propertyReader.readInt(this.mSwitchMinWidthId, switch_.getSwitchMinWidth());
            propertyReader.readInt(this.mSwitchPaddingId, switch_.getSwitchPadding());
            propertyReader.readObject(this.mTextOffId, switch_.getTextOff());
            propertyReader.readObject(this.mTextOnId, switch_.getTextOn());
            propertyReader.readObject(this.mThumbId, switch_.getThumbDrawable());
            propertyReader.readInt(this.mThumbTextPaddingId, switch_.getThumbTextPadding());
            propertyReader.readObject(this.mThumbTintId, switch_.getThumbTintList());
            propertyReader.readObject(this.mThumbTintBlendModeId, (Object)switch_.getThumbTintBlendMode());
            propertyReader.readObject(this.mThumbTintModeId, (Object)switch_.getThumbTintMode());
            propertyReader.readObject(this.mTrackId, switch_.getTrackDrawable());
            propertyReader.readObject(this.mTrackTintId, switch_.getTrackTintList());
            propertyReader.readObject(this.mTrackTintBlendModeId, (Object)switch_.getTrackTintBlendMode());
            propertyReader.readObject(this.mTrackTintModeId, (Object)switch_.getTrackTintMode());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

