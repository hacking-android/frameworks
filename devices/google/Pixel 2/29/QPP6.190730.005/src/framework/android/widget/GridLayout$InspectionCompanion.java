/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.util.SparseArray;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.GridLayout;
import android.widget._$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI;
import java.util.Objects;
import java.util.function.IntFunction;

public final class GridLayout$InspectionCompanion
implements InspectionCompanion<GridLayout> {
    private int mAlignmentModeId;
    private int mColumnCountId;
    private int mColumnOrderPreservedId;
    private int mOrientationId;
    private boolean mPropertiesMapped = false;
    private int mRowCountId;
    private int mRowOrderPreservedId;
    private int mUseDefaultMarginsId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        SparseArray<String> sparseArray = new SparseArray<String>();
        sparseArray.put(0, "alignBounds");
        sparseArray.put(1, "alignMargins");
        Objects.requireNonNull(sparseArray);
        this.mAlignmentModeId = propertyMapper.mapIntEnum("alignmentMode", 16843642, new _$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(sparseArray));
        this.mColumnCountId = propertyMapper.mapInt("columnCount", 16843639);
        this.mColumnOrderPreservedId = propertyMapper.mapBoolean("columnOrderPreserved", 16843640);
        sparseArray = new SparseArray();
        sparseArray.put(0, "horizontal");
        sparseArray.put(1, "vertical");
        Objects.requireNonNull(sparseArray);
        this.mOrientationId = propertyMapper.mapIntEnum("orientation", 16842948, new _$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(sparseArray));
        this.mRowCountId = propertyMapper.mapInt("rowCount", 16843637);
        this.mRowOrderPreservedId = propertyMapper.mapBoolean("rowOrderPreserved", 16843638);
        this.mUseDefaultMarginsId = propertyMapper.mapBoolean("useDefaultMargins", 16843641);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(GridLayout gridLayout, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readIntEnum(this.mAlignmentModeId, gridLayout.getAlignmentMode());
            propertyReader.readInt(this.mColumnCountId, gridLayout.getColumnCount());
            propertyReader.readBoolean(this.mColumnOrderPreservedId, gridLayout.isColumnOrderPreserved());
            propertyReader.readIntEnum(this.mOrientationId, gridLayout.getOrientation());
            propertyReader.readInt(this.mRowCountId, gridLayout.getRowCount());
            propertyReader.readBoolean(this.mRowOrderPreservedId, gridLayout.isRowOrderPreserved());
            propertyReader.readBoolean(this.mUseDefaultMarginsId, gridLayout.getUseDefaultMargins());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

