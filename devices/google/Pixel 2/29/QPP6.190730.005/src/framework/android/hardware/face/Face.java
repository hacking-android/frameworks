/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.face;

import android.hardware.biometrics.BiometricAuthenticator;
import android.os.Parcel;
import android.os.Parcelable;

public final class Face
extends BiometricAuthenticator.Identifier {
    public static final Parcelable.Creator<Face> CREATOR = new Parcelable.Creator<Face>(){

        @Override
        public Face createFromParcel(Parcel parcel) {
            return new Face(parcel);
        }

        public Face[] newArray(int n) {
            return new Face[n];
        }
    };

    private Face(Parcel parcel) {
        super(parcel.readString(), parcel.readInt(), parcel.readLong());
    }

    public Face(CharSequence charSequence, int n, long l) {
        super(charSequence, n, l);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.getName().toString());
        parcel.writeInt(this.getBiometricId());
        parcel.writeLong(this.getDeviceId());
    }

}

