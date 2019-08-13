/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.TableRow;

public final class TableRow$LayoutParams$InspectionCompanion
implements InspectionCompanion<TableRow.LayoutParams> {
    private int mLayout_columnId;
    private int mLayout_spanId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mLayout_columnId = propertyMapper.mapInt("layout_column", 16843084);
        this.mLayout_spanId = propertyMapper.mapInt("layout_span", 16843085);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(TableRow.LayoutParams layoutParams, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readInt(this.mLayout_columnId, layoutParams.column);
            propertyReader.readInt(this.mLayout_spanId, layoutParams.span);
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

