/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.util.SparseArray;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.GridView;
import android.widget._$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI;
import java.util.Objects;
import java.util.function.IntFunction;

public final class GridView$InspectionCompanion
implements InspectionCompanion<GridView> {
    private int mColumnWidthId;
    private int mGravityId;
    private int mHorizontalSpacingId;
    private int mNumColumnsId;
    private boolean mPropertiesMapped = false;
    private int mStretchModeId;
    private int mVerticalSpacingId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mColumnWidthId = propertyMapper.mapInt("columnWidth", 16843031);
        this.mGravityId = propertyMapper.mapGravity("gravity", 16842927);
        this.mHorizontalSpacingId = propertyMapper.mapInt("horizontalSpacing", 16843028);
        this.mNumColumnsId = propertyMapper.mapInt("numColumns", 16843032);
        SparseArray<String> sparseArray = new SparseArray<String>();
        sparseArray.put(0, "none");
        sparseArray.put(1, "spacingWidth");
        sparseArray.put(2, "columnWidth");
        sparseArray.put(3, "spacingWidthUniform");
        Objects.requireNonNull(sparseArray);
        this.mStretchModeId = propertyMapper.mapIntEnum("stretchMode", 16843030, new _$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(sparseArray));
        this.mVerticalSpacingId = propertyMapper.mapInt("verticalSpacing", 16843029);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(GridView gridView, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readInt(this.mColumnWidthId, gridView.getColumnWidth());
            propertyReader.readGravity(this.mGravityId, gridView.getGravity());
            propertyReader.readInt(this.mHorizontalSpacingId, gridView.getHorizontalSpacing());
            propertyReader.readInt(this.mNumColumnsId, gridView.getNumColumns());
            propertyReader.readIntEnum(this.mStretchModeId, gridView.getStretchMode());
            propertyReader.readInt(this.mVerticalSpacingId, gridView.getVerticalSpacing());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

