/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.annotation.SystemApi;
import android.content.Context;
import java.util.Locale;

@SystemApi
public abstract class SearchIndexableData {
    public String className;
    public Context context;
    public boolean enabled = true;
    public int iconResId;
    public String intentAction;
    public String intentTargetClass;
    public String intentTargetPackage;
    public String key;
    public Locale locale = Locale.getDefault();
    public String packageName;
    public int rank;
    public int userId = -1;

    public SearchIndexableData() {
    }

    public SearchIndexableData(Context context) {
        this();
        this.context = context;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SearchIndexableData[context: ");
        stringBuilder.append(this.context);
        stringBuilder.append(", ");
        stringBuilder.append("locale: ");
        stringBuilder.append(this.locale);
        stringBuilder.append(", ");
        stringBuilder.append("enabled: ");
        stringBuilder.append(this.enabled);
        stringBuilder.append(", ");
        stringBuilder.append("rank: ");
        stringBuilder.append(this.rank);
        stringBuilder.append(", ");
        stringBuilder.append("key: ");
        stringBuilder.append(this.key);
        stringBuilder.append(", ");
        stringBuilder.append("userId: ");
        stringBuilder.append(this.userId);
        stringBuilder.append(", ");
        stringBuilder.append("className: ");
        stringBuilder.append(this.className);
        stringBuilder.append(", ");
        stringBuilder.append("packageName: ");
        stringBuilder.append(this.packageName);
        stringBuilder.append(", ");
        stringBuilder.append("iconResId: ");
        stringBuilder.append(this.iconResId);
        stringBuilder.append(", ");
        stringBuilder.append("intentAction: ");
        stringBuilder.append(this.intentAction);
        stringBuilder.append(", ");
        stringBuilder.append("intentTargetPackage: ");
        stringBuilder.append(this.intentTargetPackage);
        stringBuilder.append(", ");
        stringBuilder.append("intentTargetClass: ");
        stringBuilder.append(this.intentTargetClass);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

