/*
 * Decompiled with CFR 0.145.
 */
package android.security.keymaster;

import android.os.Parcel;
import android.os.Parcelable;
import android.security.keymaster.KeyAttestationPackageInfo;

public class KeyAttestationApplicationId
implements Parcelable {
    public static final Parcelable.Creator<KeyAttestationApplicationId> CREATOR = new Parcelable.Creator<KeyAttestationApplicationId>(){

        @Override
        public KeyAttestationApplicationId createFromParcel(Parcel parcel) {
            return new KeyAttestationApplicationId(parcel);
        }

        public KeyAttestationApplicationId[] newArray(int n) {
            return new KeyAttestationApplicationId[n];
        }
    };
    private final KeyAttestationPackageInfo[] mAttestationPackageInfos;

    KeyAttestationApplicationId(Parcel parcel) {
        this.mAttestationPackageInfos = parcel.createTypedArray(KeyAttestationPackageInfo.CREATOR);
    }

    public KeyAttestationApplicationId(KeyAttestationPackageInfo[] arrkeyAttestationPackageInfo) {
        this.mAttestationPackageInfos = arrkeyAttestationPackageInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public KeyAttestationPackageInfo[] getAttestationPackageInfos() {
        return this.mAttestationPackageInfos;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedArray((Parcelable[])this.mAttestationPackageInfos, n);
    }

}

