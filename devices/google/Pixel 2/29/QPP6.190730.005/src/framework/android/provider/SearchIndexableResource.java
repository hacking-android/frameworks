/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.annotation.SystemApi;
import android.content.Context;
import android.provider.SearchIndexableData;

@SystemApi
public class SearchIndexableResource
extends SearchIndexableData {
    public int xmlResId;

    public SearchIndexableResource(int n, int n2, String string2, int n3) {
        this.rank = n;
        this.xmlResId = n2;
        this.className = string2;
        this.iconResId = n3;
    }

    public SearchIndexableResource(Context context) {
        super(context);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SearchIndexableResource[");
        stringBuilder.append(super.toString());
        stringBuilder.append(", ");
        stringBuilder.append("xmlResId: ");
        stringBuilder.append(this.xmlResId);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

