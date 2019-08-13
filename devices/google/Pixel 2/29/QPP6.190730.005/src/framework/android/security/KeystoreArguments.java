/*
 * Decompiled with CFR 0.145.
 */
package android.security;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;

public class KeystoreArguments
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<KeystoreArguments> CREATOR = new Parcelable.Creator<KeystoreArguments>(){

        @Override
        public KeystoreArguments createFromParcel(Parcel parcel) {
            return new KeystoreArguments(parcel);
        }

        public KeystoreArguments[] newArray(int n) {
            return new KeystoreArguments[n];
        }
    };
    public byte[][] args;

    public KeystoreArguments() {
        this.args = null;
    }

    private KeystoreArguments(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    @UnsupportedAppUsage
    public KeystoreArguments(byte[][] arrby) {
        this.args = arrby;
    }

    private void readFromParcel(Parcel parcel) {
        int n = parcel.readInt();
        this.args = new byte[n][];
        for (int i = 0; i < n; ++i) {
            this.args[i] = parcel.createByteArray();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        byte[][] arrby = this.args;
        if (arrby == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(arrby.length);
            arrby = this.args;
            int n2 = arrby.length;
            for (n = 0; n < n2; ++n) {
                parcel.writeByteArray(arrby[n]);
            }
        }
    }

}

