/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore.recovery;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.recovery.KeyDerivationParams;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

@SystemApi
public final class KeyChainProtectionParams
implements Parcelable {
    public static final Parcelable.Creator<KeyChainProtectionParams> CREATOR = new Parcelable.Creator<KeyChainProtectionParams>(){

        @Override
        public KeyChainProtectionParams createFromParcel(Parcel parcel) {
            return new KeyChainProtectionParams(parcel);
        }

        public KeyChainProtectionParams[] newArray(int n) {
            return new KeyChainProtectionParams[n];
        }
    };
    public static final int TYPE_LOCKSCREEN = 100;
    public static final int UI_FORMAT_PASSWORD = 2;
    public static final int UI_FORMAT_PATTERN = 3;
    public static final int UI_FORMAT_PIN = 1;
    private KeyDerivationParams mKeyDerivationParams;
    private Integer mLockScreenUiFormat;
    private byte[] mSecret;
    private Integer mUserSecretType;

    private KeyChainProtectionParams() {
    }

    protected KeyChainProtectionParams(Parcel parcel) {
        this.mUserSecretType = parcel.readInt();
        this.mLockScreenUiFormat = parcel.readInt();
        this.mKeyDerivationParams = parcel.readTypedObject(KeyDerivationParams.CREATOR);
        this.mSecret = parcel.createByteArray();
    }

    public void clearSecret() {
        Arrays.fill(this.mSecret, (byte)0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public KeyDerivationParams getKeyDerivationParams() {
        return this.mKeyDerivationParams;
    }

    public int getLockScreenUiFormat() {
        return this.mLockScreenUiFormat;
    }

    public byte[] getSecret() {
        return this.mSecret;
    }

    public int getUserSecretType() {
        return this.mUserSecretType;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mUserSecretType);
        parcel.writeInt(this.mLockScreenUiFormat);
        parcel.writeTypedObject(this.mKeyDerivationParams, n);
        parcel.writeByteArray(this.mSecret);
    }

    public static class Builder {
        private KeyChainProtectionParams mInstance = new KeyChainProtectionParams();

        public KeyChainProtectionParams build() {
            if (this.mInstance.mUserSecretType == null) {
                this.mInstance.mUserSecretType = 100;
            }
            Preconditions.checkNotNull(this.mInstance.mLockScreenUiFormat);
            Preconditions.checkNotNull(this.mInstance.mKeyDerivationParams);
            if (this.mInstance.mSecret == null) {
                this.mInstance.mSecret = new byte[0];
            }
            return this.mInstance;
        }

        public Builder setKeyDerivationParams(KeyDerivationParams keyDerivationParams) {
            this.mInstance.mKeyDerivationParams = keyDerivationParams;
            return this;
        }

        public Builder setLockScreenUiFormat(int n) {
            this.mInstance.mLockScreenUiFormat = n;
            return this;
        }

        public Builder setSecret(byte[] arrby) {
            this.mInstance.mSecret = arrby;
            return this;
        }

        public Builder setUserSecretType(int n) {
            this.mInstance.mUserSecretType = n;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LockScreenUiFormat {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface UserSecretType {
    }

}

