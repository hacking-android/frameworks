/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.SearchView;

public final class SearchView$InspectionCompanion
implements InspectionCompanion<SearchView> {
    private int mIconifiedByDefaultId;
    private int mIconifiedId;
    private int mMaxWidthId;
    private boolean mPropertiesMapped = false;
    private int mQueryHintId;
    private int mQueryId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mIconifiedId = propertyMapper.mapBoolean("iconified", 0);
        this.mIconifiedByDefaultId = propertyMapper.mapBoolean("iconifiedByDefault", 16843514);
        this.mMaxWidthId = propertyMapper.mapInt("maxWidth", 16843039);
        this.mQueryId = propertyMapper.mapObject("query", 0);
        this.mQueryHintId = propertyMapper.mapObject("queryHint", 16843608);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(SearchView searchView, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readBoolean(this.mIconifiedId, searchView.isIconified());
            propertyReader.readBoolean(this.mIconifiedByDefaultId, searchView.isIconifiedByDefault());
            propertyReader.readInt(this.mMaxWidthId, searchView.getMaxWidth());
            propertyReader.readObject(this.mQueryId, searchView.getQuery());
            propertyReader.readObject(this.mQueryHintId, searchView.getQueryHint());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

