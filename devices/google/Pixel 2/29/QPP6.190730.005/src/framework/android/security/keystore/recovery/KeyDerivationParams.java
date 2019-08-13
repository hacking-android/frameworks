/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore.recovery;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class KeyDerivationParams
implements Parcelable {
    public static final int ALGORITHM_SCRYPT = 2;
    public static final int ALGORITHM_SHA256 = 1;
    public static final Parcelable.Creator<KeyDerivationParams> CREATOR = new Parcelable.Creator<KeyDerivationParams>(){

        @Override
        public KeyDerivationParams createFromParcel(Parcel parcel) {
            return new KeyDerivationParams(parcel);
        }

        public KeyDerivationParams[] newArray(int n) {
            return new KeyDerivationParams[n];
        }
    };
    private final int mAlgorithm;
    private final int mMemoryDifficulty;
    private final byte[] mSalt;

    private KeyDerivationParams(int n, byte[] arrby) {
        this(n, arrby, -1);
    }

    private KeyDerivationParams(int n, byte[] arrby, int n2) {
        this.mAlgorithm = n;
        this.mSalt = Preconditions.checkNotNull(arrby);
        this.mMemoryDifficulty = n2;
    }

    protected KeyDerivationParams(Parcel parcel) {
        this.mAlgorithm = parcel.readInt();
        this.mSalt = parcel.createByteArray();
        this.mMemoryDifficulty = parcel.readInt();
    }

    public static KeyDerivationParams createScryptParams(byte[] arrby, int n) {
        return new KeyDerivationParams(2, arrby, n);
    }

    public static KeyDerivationParams createSha256Params(byte[] arrby) {
        return new KeyDerivationParams(1, arrby);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getAlgorithm() {
        return this.mAlgorithm;
    }

    public int getMemoryDifficulty() {
        return this.mMemoryDifficulty;
    }

    public byte[] getSalt() {
        return this.mSalt;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mAlgorithm);
        parcel.writeByteArray(this.mSalt);
        parcel.writeInt(this.mMemoryDifficulty);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface KeyDerivationAlgorithm {
    }

}

