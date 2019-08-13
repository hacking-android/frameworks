/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.os.Parcel;
import android.os.Parcelable;

public class KeystoreResponse
implements Parcelable {
    public static final Parcelable.Creator<KeystoreResponse> CREATOR = new Parcelable.Creator<KeystoreResponse>(){

        @Override
        public KeystoreResponse createFromParcel(Parcel parcel) {
            return new KeystoreResponse(parcel.readInt(), parcel.readString());
        }

        public KeystoreResponse[] newArray(int n) {
            return new KeystoreResponse[n];
        }
    };
    public final int error_code_;
    public final String error_msg_;

    protected KeystoreResponse(int n, String string2) {
        this.error_code_ = n;
        this.error_msg_ = string2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public final int getErrorCode() {
        return this.error_code_;
    }

    public final String getErrorMessage() {
        return this.error_msg_;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.error_code_);
        parcel.writeString(this.error_msg_);
    }

}

