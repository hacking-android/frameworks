/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore.recovery;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

@SystemApi
public final class WrappedApplicationKey
implements Parcelable {
    public static final Parcelable.Creator<WrappedApplicationKey> CREATOR = new Parcelable.Creator<WrappedApplicationKey>(){

        @Override
        public WrappedApplicationKey createFromParcel(Parcel parcel) {
            return new WrappedApplicationKey(parcel);
        }

        public WrappedApplicationKey[] newArray(int n) {
            return new WrappedApplicationKey[n];
        }
    };
    private String mAlias;
    private byte[] mEncryptedKeyMaterial;
    private byte[] mMetadata;

    private WrappedApplicationKey() {
    }

    protected WrappedApplicationKey(Parcel parcel) {
        this.mAlias = parcel.readString();
        this.mEncryptedKeyMaterial = parcel.createByteArray();
        if (parcel.dataAvail() > 0) {
            this.mMetadata = parcel.createByteArray();
        }
    }

    @Deprecated
    public WrappedApplicationKey(String string2, byte[] arrby) {
        this.mAlias = Preconditions.checkNotNull(string2);
        this.mEncryptedKeyMaterial = Preconditions.checkNotNull(arrby);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getAlias() {
        return this.mAlias;
    }

    public byte[] getEncryptedKeyMaterial() {
        return this.mEncryptedKeyMaterial;
    }

    public byte[] getMetadata() {
        return this.mMetadata;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mAlias);
        parcel.writeByteArray(this.mEncryptedKeyMaterial);
        parcel.writeByteArray(this.mMetadata);
    }

    public static class Builder {
        private WrappedApplicationKey mInstance = new WrappedApplicationKey();

        public WrappedApplicationKey build() {
            Preconditions.checkNotNull(this.mInstance.mAlias);
            Preconditions.checkNotNull(this.mInstance.mEncryptedKeyMaterial);
            return this.mInstance;
        }

        public Builder setAlias(String string2) {
            this.mInstance.mAlias = string2;
            return this;
        }

        public Builder setEncryptedKeyMaterial(byte[] arrby) {
            this.mInstance.mEncryptedKeyMaterial = arrby;
            return this;
        }

        public Builder setMetadata(byte[] arrby) {
            this.mInstance.mMetadata = arrby;
            return this;
        }
    }

}

