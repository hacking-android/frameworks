/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.fingerprint;

import android.hardware.biometrics.BiometricAuthenticator;
import android.os.Parcel;
import android.os.Parcelable;

public final class Fingerprint
extends BiometricAuthenticator.Identifier {
    public static final Parcelable.Creator<Fingerprint> CREATOR = new Parcelable.Creator<Fingerprint>(){

        @Override
        public Fingerprint createFromParcel(Parcel parcel) {
            return new Fingerprint(parcel);
        }

        public Fingerprint[] newArray(int n) {
            return new Fingerprint[n];
        }
    };
    private int mGroupId;

    private Fingerprint(Parcel parcel) {
        super(parcel.readString(), parcel.readInt(), parcel.readLong());
        this.mGroupId = parcel.readInt();
    }

    public Fingerprint(CharSequence charSequence, int n, int n2, long l) {
        super(charSequence, n2, l);
        this.mGroupId = n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getGroupId() {
        return this.mGroupId;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.getName().toString());
        parcel.writeInt(this.getBiometricId());
        parcel.writeLong(this.getDeviceId());
        parcel.writeInt(this.mGroupId);
    }

}

