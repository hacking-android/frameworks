/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

public class KeySet
implements Parcelable {
    public static final Parcelable.Creator<KeySet> CREATOR = new Parcelable.Creator<KeySet>(){

        @Override
        public KeySet createFromParcel(Parcel parcel) {
            return KeySet.readFromParcel(parcel);
        }

        public KeySet[] newArray(int n) {
            return new KeySet[n];
        }
    };
    private IBinder token;

    public KeySet(IBinder iBinder) {
        if (iBinder != null) {
            this.token = iBinder;
            return;
        }
        throw new NullPointerException("null value for KeySet IBinder token");
    }

    private static KeySet readFromParcel(Parcel parcel) {
        return new KeySet(parcel.readStrongBinder());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof KeySet;
        boolean bl2 = false;
        if (bl) {
            object = (KeySet)object;
            if (this.token == ((KeySet)object).token) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public IBinder getToken() {
        return this.token;
    }

    public int hashCode() {
        return this.token.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongBinder(this.token);
    }

}

