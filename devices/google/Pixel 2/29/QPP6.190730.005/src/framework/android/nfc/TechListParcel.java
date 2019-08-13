/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.os.Parcel;
import android.os.Parcelable;

public class TechListParcel
implements Parcelable {
    public static final Parcelable.Creator<TechListParcel> CREATOR = new Parcelable.Creator<TechListParcel>(){

        @Override
        public TechListParcel createFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            String[][] arrstring = new String[n][];
            for (int i = 0; i < n; ++i) {
                arrstring[i] = parcel.readStringArray();
            }
            return new TechListParcel(arrstring);
        }

        public TechListParcel[] newArray(int n) {
            return new TechListParcel[n];
        }
    };
    private String[][] mTechLists;

    public TechListParcel(String[] ... arrstring) {
        this.mTechLists = arrstring;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String[][] getTechLists() {
        return this.mTechLists;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        int n2 = this.mTechLists.length;
        parcel.writeInt(n2);
        for (n = 0; n < n2; ++n) {
            parcel.writeStringArray(this.mTechLists[n]);
        }
    }

}

