/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentValues;
import android.net.Uri;
import java.util.ArrayList;

public final class Entity {
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final ArrayList<NamedContentValues> mSubValues;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final ContentValues mValues;

    public Entity(ContentValues contentValues) {
        this.mValues = contentValues;
        this.mSubValues = new ArrayList();
    }

    public void addSubValue(Uri uri, ContentValues contentValues) {
        this.mSubValues.add(new NamedContentValues(uri, contentValues));
    }

    public ContentValues getEntityValues() {
        return this.mValues;
    }

    public ArrayList<NamedContentValues> getSubValues() {
        return this.mSubValues;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Entity: ");
        stringBuilder.append(this.getEntityValues());
        for (NamedContentValues namedContentValues : this.getSubValues()) {
            stringBuilder.append("\n  ");
            stringBuilder.append(namedContentValues.uri);
            stringBuilder.append("\n  -> ");
            stringBuilder.append(namedContentValues.values);
        }
        return stringBuilder.toString();
    }

    public static class NamedContentValues {
        public final Uri uri;
        public final ContentValues values;

        public NamedContentValues(Uri uri, ContentValues contentValues) {
            this.uri = uri;
            this.values = contentValues;
        }
    }

}

