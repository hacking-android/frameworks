/*
 * Decompiled with CFR 0.145.
 */
package android.security.keymaster;

import android.content.pm.Signature;
import android.os.Parcel;
import android.os.Parcelable;

public class KeyAttestationPackageInfo
implements Parcelable {
    public static final Parcelable.Creator<KeyAttestationPackageInfo> CREATOR = new Parcelable.Creator<KeyAttestationPackageInfo>(){

        @Override
        public KeyAttestationPackageInfo createFromParcel(Parcel parcel) {
            return new KeyAttestationPackageInfo(parcel);
        }

        public KeyAttestationPackageInfo[] newArray(int n) {
            return new KeyAttestationPackageInfo[n];
        }
    };
    private final String mPackageName;
    private final Signature[] mPackageSignatures;
    private final long mPackageVersionCode;

    private KeyAttestationPackageInfo(Parcel parcel) {
        this.mPackageName = parcel.readString();
        this.mPackageVersionCode = parcel.readLong();
        this.mPackageSignatures = parcel.createTypedArray(Signature.CREATOR);
    }

    public KeyAttestationPackageInfo(String string2, long l, Signature[] arrsignature) {
        this.mPackageName = string2;
        this.mPackageVersionCode = l;
        this.mPackageSignatures = arrsignature;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public Signature[] getPackageSignatures() {
        return this.mPackageSignatures;
    }

    public long getPackageVersionCode() {
        return this.mPackageVersionCode;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mPackageName);
        parcel.writeLong(this.mPackageVersionCode);
        parcel.writeTypedArray((Parcelable[])this.mPackageSignatures, n);
    }

}

