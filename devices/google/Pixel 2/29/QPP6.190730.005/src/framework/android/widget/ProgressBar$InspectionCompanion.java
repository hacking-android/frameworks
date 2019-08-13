/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.animation.Interpolator;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.ProgressBar;

public final class ProgressBar$InspectionCompanion
implements InspectionCompanion<ProgressBar> {
    private int mIndeterminateDrawableId;
    private int mIndeterminateId;
    private int mIndeterminateTintBlendModeId;
    private int mIndeterminateTintId;
    private int mIndeterminateTintModeId;
    private int mInterpolatorId;
    private int mMaxId;
    private int mMinId;
    private int mMirrorForRtlId;
    private int mProgressBackgroundTintBlendModeId;
    private int mProgressBackgroundTintId;
    private int mProgressBackgroundTintModeId;
    private int mProgressDrawableId;
    private int mProgressId;
    private int mProgressTintBlendModeId;
    private int mProgressTintId;
    private int mProgressTintModeId;
    private boolean mPropertiesMapped = false;
    private int mSecondaryProgressId;
    private int mSecondaryProgressTintBlendModeId;
    private int mSecondaryProgressTintId;
    private int mSecondaryProgressTintModeId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mIndeterminateId = propertyMapper.mapBoolean("indeterminate", 16843065);
        this.mIndeterminateDrawableId = propertyMapper.mapObject("indeterminateDrawable", 16843067);
        this.mIndeterminateTintId = propertyMapper.mapObject("indeterminateTint", 16843881);
        this.mIndeterminateTintBlendModeId = propertyMapper.mapObject("indeterminateTintBlendMode", 23);
        this.mIndeterminateTintModeId = propertyMapper.mapObject("indeterminateTintMode", 16843882);
        this.mInterpolatorId = propertyMapper.mapObject("interpolator", 16843073);
        this.mMaxId = propertyMapper.mapInt("max", 16843062);
        this.mMinId = propertyMapper.mapInt("min", 16844089);
        this.mMirrorForRtlId = propertyMapper.mapBoolean("mirrorForRtl", 16843726);
        this.mProgressId = propertyMapper.mapInt("progress", 16843063);
        this.mProgressBackgroundTintId = propertyMapper.mapObject("progressBackgroundTint", 16843877);
        this.mProgressBackgroundTintBlendModeId = propertyMapper.mapObject("progressBackgroundTintBlendMode", 19);
        this.mProgressBackgroundTintModeId = propertyMapper.mapObject("progressBackgroundTintMode", 16843878);
        this.mProgressDrawableId = propertyMapper.mapObject("progressDrawable", 16843068);
        this.mProgressTintId = propertyMapper.mapObject("progressTint", 16843875);
        this.mProgressTintBlendModeId = propertyMapper.mapObject("progressTintBlendMode", 17);
        this.mProgressTintModeId = propertyMapper.mapObject("progressTintMode", 16843876);
        this.mSecondaryProgressId = propertyMapper.mapInt("secondaryProgress", 16843064);
        this.mSecondaryProgressTintId = propertyMapper.mapObject("secondaryProgressTint", 16843879);
        this.mSecondaryProgressTintBlendModeId = propertyMapper.mapObject("secondaryProgressTintBlendMode", 21);
        this.mSecondaryProgressTintModeId = propertyMapper.mapObject("secondaryProgressTintMode", 16843880);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(ProgressBar progressBar, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readBoolean(this.mIndeterminateId, progressBar.isIndeterminate());
            propertyReader.readObject(this.mIndeterminateDrawableId, progressBar.getIndeterminateDrawable());
            propertyReader.readObject(this.mIndeterminateTintId, progressBar.getIndeterminateTintList());
            propertyReader.readObject(this.mIndeterminateTintBlendModeId, (Object)progressBar.getIndeterminateTintBlendMode());
            propertyReader.readObject(this.mIndeterminateTintModeId, (Object)progressBar.getIndeterminateTintMode());
            propertyReader.readObject(this.mInterpolatorId, progressBar.getInterpolator());
            propertyReader.readInt(this.mMaxId, progressBar.getMax());
            propertyReader.readInt(this.mMinId, progressBar.getMin());
            propertyReader.readBoolean(this.mMirrorForRtlId, progressBar.getMirrorForRtl());
            propertyReader.readInt(this.mProgressId, progressBar.getProgress());
            propertyReader.readObject(this.mProgressBackgroundTintId, progressBar.getProgressBackgroundTintList());
            propertyReader.readObject(this.mProgressBackgroundTintBlendModeId, (Object)progressBar.getProgressBackgroundTintBlendMode());
            propertyReader.readObject(this.mProgressBackgroundTintModeId, (Object)progressBar.getProgressBackgroundTintMode());
            propertyReader.readObject(this.mProgressDrawableId, progressBar.getProgressDrawable());
            propertyReader.readObject(this.mProgressTintId, progressBar.getProgressTintList());
            propertyReader.readObject(this.mProgressTintBlendModeId, (Object)progressBar.getProgressTintBlendMode());
            propertyReader.readObject(this.mProgressTintModeId, (Object)progressBar.getProgressTintMode());
            propertyReader.readInt(this.mSecondaryProgressId, progressBar.getSecondaryProgress());
            propertyReader.readObject(this.mSecondaryProgressTintId, progressBar.getSecondaryProgressTintList());
            propertyReader.readObject(this.mSecondaryProgressTintBlendModeId, (Object)progressBar.getSecondaryProgressTintBlendMode());
            propertyReader.readObject(this.mSecondaryProgressTintModeId, (Object)progressBar.getSecondaryProgressTintMode());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

