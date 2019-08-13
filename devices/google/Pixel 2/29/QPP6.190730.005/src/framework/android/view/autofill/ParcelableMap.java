/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class ParcelableMap
extends HashMap<AutofillId, AutofillValue>
implements Parcelable {
    public static final Parcelable.Creator<ParcelableMap> CREATOR = new Parcelable.Creator<ParcelableMap>(){

        @Override
        public ParcelableMap createFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            ParcelableMap parcelableMap = new ParcelableMap(n);
            for (int i = 0; i < n; ++i) {
                parcelableMap.put((AutofillId)parcel.readParcelable(null), (AutofillValue)parcel.readParcelable(null));
            }
            return parcelableMap;
        }

        public ParcelableMap[] newArray(int n) {
            return new ParcelableMap[n];
        }
    };

    ParcelableMap(int n) {
        super(n);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.size());
        for (Map.Entry entry : this.entrySet()) {
            parcel.writeParcelable((Parcelable)entry.getKey(), 0);
            parcel.writeParcelable((Parcelable)entry.getValue(), 0);
        }
    }

}

