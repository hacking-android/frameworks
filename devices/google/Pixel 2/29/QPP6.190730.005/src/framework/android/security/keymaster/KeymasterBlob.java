/*
 * Decompiled with CFR 0.145.
 */
package android.security.keymaster;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public class KeymasterBlob
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<KeymasterBlob> CREATOR = new Parcelable.Creator<KeymasterBlob>(){

        @Override
        public KeymasterBlob createFromParcel(Parcel parcel) {
            return new KeymasterBlob(parcel);
        }

        public KeymasterBlob[] newArray(int n) {
            return new KeymasterBlob[n];
        }
    };
    public byte[] blob;

    protected KeymasterBlob(Parcel parcel) {
        this.blob = parcel.createByteArray();
    }

    public KeymasterBlob(byte[] arrby) {
        this.blob = arrby;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByteArray(this.blob);
    }

}

