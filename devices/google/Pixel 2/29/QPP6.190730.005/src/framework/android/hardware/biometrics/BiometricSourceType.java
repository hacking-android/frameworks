/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.biometrics;

import android.os.Parcel;
import android.os.Parcelable;

public enum BiometricSourceType implements Parcelable
{
    FINGERPRINT,
    FACE,
    IRIS;
    
    public static final Parcelable.Creator<BiometricSourceType> CREATOR = new Parcelable.Creator<BiometricSourceType>(){

        @Override
        public BiometricSourceType createFromParcel(Parcel parcel) {
            return BiometricSourceType.valueOf(parcel.readString());
        }

        public BiometricSourceType[] newArray(int n) {
            return new BiometricSourceType[n];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.name());
    }

}

