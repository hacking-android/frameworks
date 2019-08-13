/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.graphics.drawable.Drawable;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.ListView;

public final class ListView$InspectionCompanion
implements InspectionCompanion<ListView> {
    private int mDividerHeightId;
    private int mDividerId;
    private int mFooterDividersEnabledId;
    private int mHeaderDividersEnabledId;
    private boolean mPropertiesMapped = false;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mDividerId = propertyMapper.mapObject("divider", 16843049);
        this.mDividerHeightId = propertyMapper.mapInt("dividerHeight", 16843050);
        this.mFooterDividersEnabledId = propertyMapper.mapBoolean("footerDividersEnabled", 16843311);
        this.mHeaderDividersEnabledId = propertyMapper.mapBoolean("headerDividersEnabled", 16843310);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(ListView listView, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readObject(this.mDividerId, listView.getDivider());
            propertyReader.readInt(this.mDividerHeightId, listView.getDividerHeight());
            propertyReader.readBoolean(this.mFooterDividersEnabledId, listView.areFooterDividersEnabled());
            propertyReader.readBoolean(this.mHeaderDividersEnabledId, listView.areHeaderDividersEnabled());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

