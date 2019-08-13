/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.RatingBar;

public final class RatingBar$InspectionCompanion
implements InspectionCompanion<RatingBar> {
    private int mIsIndicatorId;
    private int mNumStarsId;
    private boolean mPropertiesMapped = false;
    private int mRatingId;
    private int mStepSizeId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mIsIndicatorId = propertyMapper.mapBoolean("isIndicator", 16843079);
        this.mNumStarsId = propertyMapper.mapInt("numStars", 16843076);
        this.mRatingId = propertyMapper.mapFloat("rating", 16843077);
        this.mStepSizeId = propertyMapper.mapFloat("stepSize", 16843078);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(RatingBar ratingBar, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readBoolean(this.mIsIndicatorId, ratingBar.isIndicator());
            propertyReader.readInt(this.mNumStarsId, ratingBar.getNumStars());
            propertyReader.readFloat(this.mRatingId, ratingBar.getRating());
            propertyReader.readFloat(this.mStepSizeId, ratingBar.getStepSize());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

