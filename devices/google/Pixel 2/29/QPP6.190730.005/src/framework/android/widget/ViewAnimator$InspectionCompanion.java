/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.view.animation.Animation;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.ViewAnimator;

public final class ViewAnimator$InspectionCompanion
implements InspectionCompanion<ViewAnimator> {
    private int mAnimateFirstViewId;
    private int mInAnimationId;
    private int mOutAnimationId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mAnimateFirstViewId = propertyMapper.mapBoolean("animateFirstView", 16843477);
        this.mInAnimationId = propertyMapper.mapObject("inAnimation", 16843127);
        this.mOutAnimationId = propertyMapper.mapObject("outAnimation", 16843128);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(ViewAnimator viewAnimator, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readBoolean(this.mAnimateFirstViewId, viewAnimator.getAnimateFirstView());
            propertyReader.readObject(this.mInAnimationId, viewAnimator.getInAnimation());
            propertyReader.readObject(this.mOutAnimationId, viewAnimator.getOutAnimation());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

