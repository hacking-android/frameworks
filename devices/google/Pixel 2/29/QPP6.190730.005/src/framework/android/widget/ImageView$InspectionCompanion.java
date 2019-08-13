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
import android.widget.ImageView;

public final class ImageView$InspectionCompanion
implements InspectionCompanion<ImageView> {
    private int mAdjustViewBoundsId;
    private int mBaselineAlignBottomId;
    private int mBaselineId;
    private int mBlendModeId;
    private int mCropToPaddingId;
    private int mMaxHeightId;
    private int mMaxWidthId;
    private boolean mPropertiesMapped = false;
    private int mScaleTypeId;
    private int mSrcId;
    private int mTintId;
    private int mTintModeId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mAdjustViewBoundsId = propertyMapper.mapBoolean("adjustViewBounds", 16843038);
        this.mBaselineId = propertyMapper.mapInt("baseline", 16843548);
        this.mBaselineAlignBottomId = propertyMapper.mapBoolean("baselineAlignBottom", 16843042);
        this.mBlendModeId = propertyMapper.mapObject("blendMode", 9);
        this.mCropToPaddingId = propertyMapper.mapBoolean("cropToPadding", 16843043);
        this.mMaxHeightId = propertyMapper.mapInt("maxHeight", 16843040);
        this.mMaxWidthId = propertyMapper.mapInt("maxWidth", 16843039);
        this.mScaleTypeId = propertyMapper.mapObject("scaleType", 16843037);
        this.mSrcId = propertyMapper.mapObject("src", 16843033);
        this.mTintId = propertyMapper.mapObject("tint", 16843041);
        this.mTintModeId = propertyMapper.mapObject("tintMode", 16843771);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(ImageView imageView, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readBoolean(this.mAdjustViewBoundsId, imageView.getAdjustViewBounds());
            propertyReader.readInt(this.mBaselineId, imageView.getBaseline());
            propertyReader.readBoolean(this.mBaselineAlignBottomId, imageView.getBaselineAlignBottom());
            propertyReader.readObject(this.mBlendModeId, (Object)imageView.getImageTintBlendMode());
            propertyReader.readBoolean(this.mCropToPaddingId, imageView.getCropToPadding());
            propertyReader.readInt(this.mMaxHeightId, imageView.getMaxHeight());
            propertyReader.readInt(this.mMaxWidthId, imageView.getMaxWidth());
            propertyReader.readObject(this.mScaleTypeId, (Object)imageView.getScaleType());
            propertyReader.readObject(this.mSrcId, imageView.getDrawable());
            propertyReader.readObject(this.mTintId, imageView.getImageTintList());
            propertyReader.readObject(this.mTintModeId, (Object)imageView.getImageTintMode());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

