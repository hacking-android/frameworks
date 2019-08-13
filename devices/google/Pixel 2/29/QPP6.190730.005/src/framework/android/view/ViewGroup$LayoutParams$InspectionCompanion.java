/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.util.SparseArray;
import android.view.ViewGroup;
import android.view._$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import java.util.Objects;
import java.util.function.IntFunction;

public final class ViewGroup$LayoutParams$InspectionCompanion
implements InspectionCompanion<ViewGroup.LayoutParams> {
    private int mLayout_heightId;
    private int mLayout_widthId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        SparseArray<String> sparseArray = new SparseArray<String>();
        sparseArray.put(-2, "wrap_content");
        sparseArray.put(-1, "match_parent");
        Objects.requireNonNull(sparseArray);
        this.mLayout_heightId = propertyMapper.mapIntEnum("layout_height", 16842997, new _$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(sparseArray));
        sparseArray = new SparseArray();
        sparseArray.put(-2, "wrap_content");
        sparseArray.put(-1, "match_parent");
        Objects.requireNonNull(sparseArray);
        this.mLayout_widthId = propertyMapper.mapIntEnum("layout_width", 16842996, new _$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(sparseArray));
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(ViewGroup.LayoutParams layoutParams, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readIntEnum(this.mLayout_heightId, layoutParams.height);
            propertyReader.readIntEnum(this.mLayout_widthId, layoutParams.width);
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

