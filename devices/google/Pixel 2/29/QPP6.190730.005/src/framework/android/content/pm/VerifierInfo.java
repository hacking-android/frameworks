/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import java.security.PublicKey;

public class VerifierInfo
implements Parcelable {
    public static final Parcelable.Creator<VerifierInfo> CREATOR = new Parcelable.Creator<VerifierInfo>(){

        @Override
        public VerifierInfo createFromParcel(Parcel parcel) {
            return new VerifierInfo(parcel);
        }

        public VerifierInfo[] newArray(int n) {
            return new VerifierInfo[n];
        }
    };
    public final String packageName;
    public final PublicKey publicKey;

    private VerifierInfo(Parcel parcel) {
        this.packageName = parcel.readString();
        this.publicKey = (PublicKey)parcel.readSerializable();
    }

    @UnsupportedAppUsage
    public VerifierInfo(String string2, PublicKey publicKey) {
        if (string2 != null && string2.length() != 0) {
            if (publicKey != null) {
                this.packageName = string2;
                this.publicKey = publicKey;
                return;
            }
            throw new IllegalArgumentException("publicKey must not be null");
        }
        throw new IllegalArgumentException("packageName must not be null or empty");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.packageName);
        parcel.writeSerializable(this.publicKey);
    }

}

