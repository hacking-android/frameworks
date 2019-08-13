/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.view.textclassifier._$$Lambda$EntityConfidence$YPh8hwgSYYK8OyQ1kFlQngc71Q0;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class EntityConfidence
implements Parcelable {
    public static final Parcelable.Creator<EntityConfidence> CREATOR = new Parcelable.Creator<EntityConfidence>(){

        @Override
        public EntityConfidence createFromParcel(Parcel parcel) {
            return new EntityConfidence(parcel);
        }

        public EntityConfidence[] newArray(int n) {
            return new EntityConfidence[n];
        }
    };
    private final ArrayMap<String, Float> mEntityConfidence = new ArrayMap();
    private final ArrayList<String> mSortedEntities = new ArrayList();

    EntityConfidence() {
    }

    private EntityConfidence(Parcel parcel) {
        int n = parcel.readInt();
        this.mEntityConfidence.ensureCapacity(n);
        for (int i = 0; i < n; ++i) {
            this.mEntityConfidence.put(parcel.readString(), Float.valueOf(parcel.readFloat()));
        }
        this.resetSortedEntitiesFromMap();
    }

    EntityConfidence(EntityConfidence entityConfidence) {
        Preconditions.checkNotNull(entityConfidence);
        this.mEntityConfidence.putAll(entityConfidence.mEntityConfidence);
        this.mSortedEntities.addAll(entityConfidence.mSortedEntities);
    }

    EntityConfidence(Map<String, Float> object) {
        Preconditions.checkNotNull(object);
        this.mEntityConfidence.ensureCapacity(object.size());
        for (Map.Entry entry : object.entrySet()) {
            if (((Float)entry.getValue()).floatValue() <= 0.0f) continue;
            this.mEntityConfidence.put((String)entry.getKey(), Float.valueOf(Math.min(1.0f, ((Float)entry.getValue()).floatValue())));
        }
        this.resetSortedEntitiesFromMap();
    }

    private void resetSortedEntitiesFromMap() {
        this.mSortedEntities.clear();
        this.mSortedEntities.ensureCapacity(this.mEntityConfidence.size());
        this.mSortedEntities.addAll(this.mEntityConfidence.keySet());
        this.mSortedEntities.sort(new _$$Lambda$EntityConfidence$YPh8hwgSYYK8OyQ1kFlQngc71Q0(this));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public float getConfidenceScore(String string2) {
        if (this.mEntityConfidence.containsKey(string2)) {
            return this.mEntityConfidence.get(string2).floatValue();
        }
        return 0.0f;
    }

    public List<String> getEntities() {
        return Collections.unmodifiableList(this.mSortedEntities);
    }

    public /* synthetic */ int lambda$resetSortedEntitiesFromMap$0$EntityConfidence(String string2, String string3) {
        float f = this.mEntityConfidence.get(string2).floatValue();
        return Float.compare(this.mEntityConfidence.get(string3).floatValue(), f);
    }

    public String toString() {
        return this.mEntityConfidence.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mEntityConfidence.size());
        for (Map.Entry<String, Float> entry : this.mEntityConfidence.entrySet()) {
            parcel.writeString(entry.getKey());
            parcel.writeFloat(entry.getValue().floatValue());
        }
    }

}

