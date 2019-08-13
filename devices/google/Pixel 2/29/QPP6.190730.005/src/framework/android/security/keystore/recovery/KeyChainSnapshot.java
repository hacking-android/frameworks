/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore.recovery;

import android.annotation.SystemApi;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.recovery.KeyChainProtectionParams;
import android.security.keystore.recovery.RecoveryCertPath;
import android.security.keystore.recovery.WrappedApplicationKey;
import com.android.internal.util.Preconditions;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

@SystemApi
public final class KeyChainSnapshot
implements Parcelable {
    public static final Parcelable.Creator<KeyChainSnapshot> CREATOR = new Parcelable.Creator<KeyChainSnapshot>(){

        @Override
        public KeyChainSnapshot createFromParcel(Parcel parcel) {
            return new KeyChainSnapshot(parcel);
        }

        public KeyChainSnapshot[] newArray(int n) {
            return new KeyChainSnapshot[n];
        }
    };
    private static final long DEFAULT_COUNTER_ID = 1L;
    private static final int DEFAULT_MAX_ATTEMPTS = 10;
    private RecoveryCertPath mCertPath;
    private long mCounterId = 1L;
    private byte[] mEncryptedRecoveryKeyBlob;
    private List<WrappedApplicationKey> mEntryRecoveryData;
    private List<KeyChainProtectionParams> mKeyChainProtectionParams;
    private int mMaxAttempts = 10;
    private byte[] mServerParams;
    private int mSnapshotVersion;

    private KeyChainSnapshot() {
    }

    protected KeyChainSnapshot(Parcel parcel) {
        this.mSnapshotVersion = parcel.readInt();
        this.mKeyChainProtectionParams = parcel.createTypedArrayList(KeyChainProtectionParams.CREATOR);
        this.mEncryptedRecoveryKeyBlob = parcel.createByteArray();
        this.mEntryRecoveryData = parcel.createTypedArrayList(WrappedApplicationKey.CREATOR);
        this.mMaxAttempts = parcel.readInt();
        this.mCounterId = parcel.readLong();
        this.mServerParams = parcel.createByteArray();
        this.mCertPath = parcel.readTypedObject(RecoveryCertPath.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getCounterId() {
        return this.mCounterId;
    }

    public byte[] getEncryptedRecoveryKeyBlob() {
        return this.mEncryptedRecoveryKeyBlob;
    }

    public List<KeyChainProtectionParams> getKeyChainProtectionParams() {
        return this.mKeyChainProtectionParams;
    }

    public int getMaxAttempts() {
        return this.mMaxAttempts;
    }

    public byte[] getServerParams() {
        return this.mServerParams;
    }

    public int getSnapshotVersion() {
        return this.mSnapshotVersion;
    }

    public CertPath getTrustedHardwareCertPath() {
        try {
            CertPath certPath = this.mCertPath.getCertPath();
            return certPath;
        }
        catch (CertificateException certificateException) {
            throw new BadParcelableException(certificateException);
        }
    }

    public List<WrappedApplicationKey> getWrappedApplicationKeys() {
        return this.mEntryRecoveryData;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mSnapshotVersion);
        parcel.writeTypedList(this.mKeyChainProtectionParams);
        parcel.writeByteArray(this.mEncryptedRecoveryKeyBlob);
        parcel.writeTypedList(this.mEntryRecoveryData);
        parcel.writeInt(this.mMaxAttempts);
        parcel.writeLong(this.mCounterId);
        parcel.writeByteArray(this.mServerParams);
        parcel.writeTypedObject(this.mCertPath, 0);
    }

    public static class Builder {
        private KeyChainSnapshot mInstance = new KeyChainSnapshot();

        public KeyChainSnapshot build() {
            Preconditions.checkCollectionElementsNotNull(this.mInstance.mKeyChainProtectionParams, "keyChainProtectionParams");
            Preconditions.checkCollectionElementsNotNull(this.mInstance.mEntryRecoveryData, "entryRecoveryData");
            Preconditions.checkNotNull(this.mInstance.mEncryptedRecoveryKeyBlob);
            Preconditions.checkNotNull(this.mInstance.mServerParams);
            Preconditions.checkNotNull(this.mInstance.mCertPath);
            return this.mInstance;
        }

        public Builder setCounterId(long l) {
            this.mInstance.mCounterId = l;
            return this;
        }

        public Builder setEncryptedRecoveryKeyBlob(byte[] arrby) {
            this.mInstance.mEncryptedRecoveryKeyBlob = arrby;
            return this;
        }

        public Builder setKeyChainProtectionParams(List<KeyChainProtectionParams> list) {
            this.mInstance.mKeyChainProtectionParams = list;
            return this;
        }

        public Builder setMaxAttempts(int n) {
            this.mInstance.mMaxAttempts = n;
            return this;
        }

        public Builder setServerParams(byte[] arrby) {
            this.mInstance.mServerParams = arrby;
            return this;
        }

        public Builder setSnapshotVersion(int n) {
            this.mInstance.mSnapshotVersion = n;
            return this;
        }

        public Builder setTrustedHardwareCertPath(CertPath certPath) throws CertificateException {
            this.mInstance.mCertPath = RecoveryCertPath.createRecoveryCertPath(certPath);
            return this;
        }

        public Builder setWrappedApplicationKeys(List<WrappedApplicationKey> list) {
            this.mInstance.mEntryRecoveryData = list;
            return this;
        }
    }

}

