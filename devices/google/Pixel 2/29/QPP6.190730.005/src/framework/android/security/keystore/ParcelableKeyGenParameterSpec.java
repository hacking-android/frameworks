/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyGenParameterSpec;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Date;
import javax.security.auth.x500.X500Principal;

public final class ParcelableKeyGenParameterSpec
implements Parcelable {
    private static final int ALGORITHM_PARAMETER_SPEC_EC = 3;
    private static final int ALGORITHM_PARAMETER_SPEC_NONE = 1;
    private static final int ALGORITHM_PARAMETER_SPEC_RSA = 2;
    public static final Parcelable.Creator<ParcelableKeyGenParameterSpec> CREATOR = new Parcelable.Creator<ParcelableKeyGenParameterSpec>(){

        @Override
        public ParcelableKeyGenParameterSpec createFromParcel(Parcel parcel) {
            return new ParcelableKeyGenParameterSpec(parcel);
        }

        public ParcelableKeyGenParameterSpec[] newArray(int n) {
            return new ParcelableKeyGenParameterSpec[n];
        }
    };
    private final KeyGenParameterSpec mSpec;

    private ParcelableKeyGenParameterSpec(Parcel parcel) {
        int n;
        block5 : {
            String string2;
            AlgorithmParameterSpec algorithmParameterSpec;
            int n2;
            int n3;
            int n4;
            block3 : {
                block4 : {
                    block2 : {
                        string2 = parcel.readString();
                        n3 = parcel.readInt();
                        n4 = parcel.readInt();
                        n2 = parcel.readInt();
                        n = parcel.readInt();
                        if (n != 1) break block2;
                        algorithmParameterSpec = null;
                        break block3;
                    }
                    if (n != 2) break block4;
                    algorithmParameterSpec = new RSAKeyGenParameterSpec(parcel.readInt(), new BigInteger(parcel.createByteArray()));
                    break block3;
                }
                if (n != 3) break block5;
                algorithmParameterSpec = new ECGenParameterSpec(parcel.readString());
            }
            this.mSpec = new KeyGenParameterSpec(string2, n4, n2, algorithmParameterSpec, new X500Principal(parcel.createByteArray()), new BigInteger(parcel.createByteArray()), new Date(parcel.readLong()), new Date(parcel.readLong()), ParcelableKeyGenParameterSpec.readDateOrNull(parcel), ParcelableKeyGenParameterSpec.readDateOrNull(parcel), ParcelableKeyGenParameterSpec.readDateOrNull(parcel), n3, parcel.createStringArray(), parcel.createStringArray(), parcel.createStringArray(), parcel.createStringArray(), parcel.readBoolean(), parcel.readBoolean(), parcel.readInt(), parcel.readBoolean(), parcel.createByteArray(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean());
            return;
        }
        throw new IllegalArgumentException(String.format("Unknown algorithm parameter spec: %d", n));
    }

    public ParcelableKeyGenParameterSpec(KeyGenParameterSpec keyGenParameterSpec) {
        this.mSpec = keyGenParameterSpec;
    }

    private static Date readDateOrNull(Parcel parcel) {
        if (parcel.readBoolean()) {
            return new Date(parcel.readLong());
        }
        return null;
    }

    private static void writeOptionalDate(Parcel parcel, Date date) {
        if (date != null) {
            parcel.writeBoolean(true);
            parcel.writeLong(date.getTime());
        } else {
            parcel.writeBoolean(false);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public KeyGenParameterSpec getSpec() {
        return this.mSpec;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        AlgorithmParameterSpec algorithmParameterSpec;
        block8 : {
            block6 : {
                block7 : {
                    block5 : {
                        parcel.writeString(this.mSpec.getKeystoreAlias());
                        parcel.writeInt(this.mSpec.getPurposes());
                        parcel.writeInt(this.mSpec.getUid());
                        parcel.writeInt(this.mSpec.getKeySize());
                        algorithmParameterSpec = this.mSpec.getAlgorithmParameterSpec();
                        if (algorithmParameterSpec != null) break block5;
                        parcel.writeInt(1);
                        break block6;
                    }
                    if (!(algorithmParameterSpec instanceof RSAKeyGenParameterSpec)) break block7;
                    algorithmParameterSpec = (RSAKeyGenParameterSpec)algorithmParameterSpec;
                    parcel.writeInt(2);
                    parcel.writeInt(((RSAKeyGenParameterSpec)algorithmParameterSpec).getKeysize());
                    parcel.writeByteArray(((RSAKeyGenParameterSpec)algorithmParameterSpec).getPublicExponent().toByteArray());
                    break block6;
                }
                if (!(algorithmParameterSpec instanceof ECGenParameterSpec)) break block8;
                algorithmParameterSpec = (ECGenParameterSpec)algorithmParameterSpec;
                parcel.writeInt(3);
                parcel.writeString(((ECGenParameterSpec)algorithmParameterSpec).getName());
            }
            parcel.writeByteArray(this.mSpec.getCertificateSubject().getEncoded());
            parcel.writeByteArray(this.mSpec.getCertificateSerialNumber().toByteArray());
            parcel.writeLong(this.mSpec.getCertificateNotBefore().getTime());
            parcel.writeLong(this.mSpec.getCertificateNotAfter().getTime());
            ParcelableKeyGenParameterSpec.writeOptionalDate(parcel, this.mSpec.getKeyValidityStart());
            ParcelableKeyGenParameterSpec.writeOptionalDate(parcel, this.mSpec.getKeyValidityForOriginationEnd());
            ParcelableKeyGenParameterSpec.writeOptionalDate(parcel, this.mSpec.getKeyValidityForConsumptionEnd());
            if (this.mSpec.isDigestsSpecified()) {
                parcel.writeStringArray(this.mSpec.getDigests());
            } else {
                parcel.writeStringArray(null);
            }
            parcel.writeStringArray(this.mSpec.getEncryptionPaddings());
            parcel.writeStringArray(this.mSpec.getSignaturePaddings());
            parcel.writeStringArray(this.mSpec.getBlockModes());
            parcel.writeBoolean(this.mSpec.isRandomizedEncryptionRequired());
            parcel.writeBoolean(this.mSpec.isUserAuthenticationRequired());
            parcel.writeInt(this.mSpec.getUserAuthenticationValidityDurationSeconds());
            parcel.writeBoolean(this.mSpec.isUserPresenceRequired());
            parcel.writeByteArray(this.mSpec.getAttestationChallenge());
            parcel.writeBoolean(this.mSpec.isUniqueIdIncluded());
            parcel.writeBoolean(this.mSpec.isUserAuthenticationValidWhileOnBody());
            parcel.writeBoolean(this.mSpec.isInvalidatedByBiometricEnrollment());
            parcel.writeBoolean(this.mSpec.isStrongBoxBacked());
            parcel.writeBoolean(this.mSpec.isUserConfirmationRequired());
            parcel.writeBoolean(this.mSpec.isUnlockedDeviceRequired());
            return;
        }
        throw new IllegalArgumentException(String.format("Unknown algorithm parameter spec: %s", algorithmParameterSpec.getClass()));
    }

}

