/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.LinearLayout;
import android.widget._$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI;
import java.util.Objects;
import java.util.function.IntFunction;

public final class LinearLayout$InspectionCompanion
implements InspectionCompanion<LinearLayout> {
    private int mBaselineAlignedChildIndexId;
    private int mBaselineAlignedId;
    private int mDividerId;
    private int mGravityId;
    private int mMeasureWithLargestChildId;
    private int mOrientationId;
    private boolean mPropertiesMapped = false;
    private int mWeightSumId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mBaselineAlignedId = propertyMapper.mapBoolean("baselineAligned", 16843046);
        this.mBaselineAlignedChildIndexId = propertyMapper.mapInt("baselineAlignedChildIndex", 16843047);
        this.mDividerId = propertyMapper.mapObject("divider", 16843049);
        this.mGravityId = propertyMapper.mapGravity("gravity", 16842927);
        this.mMeasureWithLargestChildId = propertyMapper.mapBoolean("measureWithLargestChild", 16843476);
        SparseArray<String> sparseArray = new SparseArray<String>();
        sparseArray.put(0, "horizontal");
        sparseArray.put(1, "vertical");
        Objects.requireNonNull(sparseArray);
        this.mOrientationId = propertyMapper.mapIntEnum("orientation", 16842948, new _$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(sparseArray));
        this.mWeightSumId = propertyMapper.mapFloat("weightSum", 16843048);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(LinearLayout linearLayout, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readBoolean(this.mBaselineAlignedId, linearLayout.isBaselineAligned());
            propertyReader.readInt(this.mBaselineAlignedChildIndexId, linearLayout.getBaselineAlignedChildIndex());
            propertyReader.readObject(this.mDividerId, linearLayout.getDividerDrawable());
            propertyReader.readGravity(this.mGravityId, linearLayout.getGravity());
            propertyReader.readBoolean(this.mMeasureWithLargestChildId, linearLayout.isMeasureWithLargestChildEnabled());
            propertyReader.readIntEnum(this.mOrientationId, linearLayout.getOrientation());
            propertyReader.readFloat(this.mWeightSumId, linearLayout.getWeightSum());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

