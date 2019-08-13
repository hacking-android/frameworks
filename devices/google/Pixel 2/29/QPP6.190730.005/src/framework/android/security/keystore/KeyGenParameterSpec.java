/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.annotation.UnsupportedAppUsage;
import android.security.keystore.ArrayUtils;
import android.security.keystore.UserAuthArgs;
import android.security.keystore.Utils;
import android.text.TextUtils;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Date;
import javax.security.auth.x500.X500Principal;

public final class KeyGenParameterSpec
implements AlgorithmParameterSpec,
UserAuthArgs {
    private static final Date DEFAULT_CERT_NOT_AFTER;
    private static final Date DEFAULT_CERT_NOT_BEFORE;
    private static final BigInteger DEFAULT_CERT_SERIAL_NUMBER;
    private static final X500Principal DEFAULT_CERT_SUBJECT;
    private final byte[] mAttestationChallenge;
    private final String[] mBlockModes;
    private final Date mCertificateNotAfter;
    private final Date mCertificateNotBefore;
    private final BigInteger mCertificateSerialNumber;
    private final X500Principal mCertificateSubject;
    private final String[] mDigests;
    private final String[] mEncryptionPaddings;
    private final boolean mInvalidatedByBiometricEnrollment;
    private final boolean mIsStrongBoxBacked;
    private final int mKeySize;
    private final Date mKeyValidityForConsumptionEnd;
    private final Date mKeyValidityForOriginationEnd;
    private final Date mKeyValidityStart;
    private final String mKeystoreAlias;
    private final int mPurposes;
    private final boolean mRandomizedEncryptionRequired;
    private final String[] mSignaturePaddings;
    private final AlgorithmParameterSpec mSpec;
    private final int mUid;
    private final boolean mUniqueIdIncluded;
    private final boolean mUnlockedDeviceRequired;
    private final boolean mUserAuthenticationRequired;
    private final boolean mUserAuthenticationValidWhileOnBody;
    private final int mUserAuthenticationValidityDurationSeconds;
    private final boolean mUserConfirmationRequired;
    private final boolean mUserPresenceRequired;

    static {
        DEFAULT_CERT_SUBJECT = new X500Principal("CN=fake");
        DEFAULT_CERT_SERIAL_NUMBER = new BigInteger("1");
        DEFAULT_CERT_NOT_BEFORE = new Date(0L);
        DEFAULT_CERT_NOT_AFTER = new Date(2461449600000L);
    }

    public KeyGenParameterSpec(String string2, int n, int n2, AlgorithmParameterSpec algorithmParameterSpec, X500Principal x500Principal, BigInteger bigInteger, Date date, Date date2, Date date3, Date date4, Date date5, int n3, String[] arrstring, String[] arrstring2, String[] arrstring3, String[] arrstring4, boolean bl, boolean bl2, int n4, boolean bl3, byte[] arrby, boolean bl4, boolean bl5, boolean bl6, boolean bl7, boolean bl8, boolean bl9) {
        if (!TextUtils.isEmpty(string2)) {
            if (x500Principal == null) {
                x500Principal = DEFAULT_CERT_SUBJECT;
            }
            if (date == null) {
                date = DEFAULT_CERT_NOT_BEFORE;
            }
            if (date2 == null) {
                date2 = DEFAULT_CERT_NOT_AFTER;
            }
            if (bigInteger == null) {
                bigInteger = DEFAULT_CERT_SERIAL_NUMBER;
            }
            if (!date2.before(date)) {
                this.mKeystoreAlias = string2;
                this.mUid = n;
                this.mKeySize = n2;
                this.mSpec = algorithmParameterSpec;
                this.mCertificateSubject = x500Principal;
                this.mCertificateSerialNumber = bigInteger;
                this.mCertificateNotBefore = Utils.cloneIfNotNull(date);
                this.mCertificateNotAfter = Utils.cloneIfNotNull(date2);
                this.mKeyValidityStart = Utils.cloneIfNotNull(date3);
                this.mKeyValidityForOriginationEnd = Utils.cloneIfNotNull(date4);
                this.mKeyValidityForConsumptionEnd = Utils.cloneIfNotNull(date5);
                this.mPurposes = n3;
                this.mDigests = ArrayUtils.cloneIfNotEmpty(arrstring);
                this.mEncryptionPaddings = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(arrstring2));
                this.mSignaturePaddings = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(arrstring3));
                this.mBlockModes = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(arrstring4));
                this.mRandomizedEncryptionRequired = bl;
                this.mUserAuthenticationRequired = bl2;
                this.mUserPresenceRequired = bl3;
                this.mUserAuthenticationValidityDurationSeconds = n4;
                this.mAttestationChallenge = Utils.cloneIfNotNull(arrby);
                this.mUniqueIdIncluded = bl4;
                this.mUserAuthenticationValidWhileOnBody = bl5;
                this.mInvalidatedByBiometricEnrollment = bl6;
                this.mIsStrongBoxBacked = bl7;
                this.mUserConfirmationRequired = bl8;
                this.mUnlockedDeviceRequired = bl9;
                return;
            }
            throw new IllegalArgumentException("certificateNotAfter < certificateNotBefore");
        }
        throw new IllegalArgumentException("keyStoreAlias must not be empty");
    }

    public AlgorithmParameterSpec getAlgorithmParameterSpec() {
        return this.mSpec;
    }

    public byte[] getAttestationChallenge() {
        return Utils.cloneIfNotNull(this.mAttestationChallenge);
    }

    public String[] getBlockModes() {
        return ArrayUtils.cloneIfNotEmpty(this.mBlockModes);
    }

    @Override
    public long getBoundToSpecificSecureUserId() {
        return 0L;
    }

    public Date getCertificateNotAfter() {
        return Utils.cloneIfNotNull(this.mCertificateNotAfter);
    }

    public Date getCertificateNotBefore() {
        return Utils.cloneIfNotNull(this.mCertificateNotBefore);
    }

    public BigInteger getCertificateSerialNumber() {
        return this.mCertificateSerialNumber;
    }

    public X500Principal getCertificateSubject() {
        return this.mCertificateSubject;
    }

    public String[] getDigests() {
        String[] arrstring = this.mDigests;
        if (arrstring != null) {
            return ArrayUtils.cloneIfNotEmpty(arrstring);
        }
        throw new IllegalStateException("Digests not specified");
    }

    public String[] getEncryptionPaddings() {
        return ArrayUtils.cloneIfNotEmpty(this.mEncryptionPaddings);
    }

    public int getKeySize() {
        return this.mKeySize;
    }

    public Date getKeyValidityForConsumptionEnd() {
        return Utils.cloneIfNotNull(this.mKeyValidityForConsumptionEnd);
    }

    public Date getKeyValidityForOriginationEnd() {
        return Utils.cloneIfNotNull(this.mKeyValidityForOriginationEnd);
    }

    public Date getKeyValidityStart() {
        return Utils.cloneIfNotNull(this.mKeyValidityStart);
    }

    public String getKeystoreAlias() {
        return this.mKeystoreAlias;
    }

    public int getPurposes() {
        return this.mPurposes;
    }

    public String[] getSignaturePaddings() {
        return ArrayUtils.cloneIfNotEmpty(this.mSignaturePaddings);
    }

    @UnsupportedAppUsage
    public int getUid() {
        return this.mUid;
    }

    @Override
    public int getUserAuthenticationValidityDurationSeconds() {
        return this.mUserAuthenticationValidityDurationSeconds;
    }

    public boolean isDigestsSpecified() {
        boolean bl = this.mDigests != null;
        return bl;
    }

    @Override
    public boolean isInvalidatedByBiometricEnrollment() {
        return this.mInvalidatedByBiometricEnrollment;
    }

    public boolean isRandomizedEncryptionRequired() {
        return this.mRandomizedEncryptionRequired;
    }

    public boolean isStrongBoxBacked() {
        return this.mIsStrongBoxBacked;
    }

    @UnsupportedAppUsage
    public boolean isUniqueIdIncluded() {
        return this.mUniqueIdIncluded;
    }

    @Override
    public boolean isUnlockedDeviceRequired() {
        return this.mUnlockedDeviceRequired;
    }

    @Override
    public boolean isUserAuthenticationRequired() {
        return this.mUserAuthenticationRequired;
    }

    @Override
    public boolean isUserAuthenticationValidWhileOnBody() {
        return this.mUserAuthenticationValidWhileOnBody;
    }

    @Override
    public boolean isUserConfirmationRequired() {
        return this.mUserConfirmationRequired;
    }

    @Override
    public boolean isUserPresenceRequired() {
        return this.mUserPresenceRequired;
    }

    public static final class Builder {
        private byte[] mAttestationChallenge = null;
        private String[] mBlockModes;
        private Date mCertificateNotAfter;
        private Date mCertificateNotBefore;
        private BigInteger mCertificateSerialNumber;
        private X500Principal mCertificateSubject;
        private String[] mDigests;
        private String[] mEncryptionPaddings;
        private boolean mInvalidatedByBiometricEnrollment = true;
        private boolean mIsStrongBoxBacked = false;
        private int mKeySize = -1;
        private Date mKeyValidityForConsumptionEnd;
        private Date mKeyValidityForOriginationEnd;
        private Date mKeyValidityStart;
        private final String mKeystoreAlias;
        private int mPurposes;
        private boolean mRandomizedEncryptionRequired = true;
        private String[] mSignaturePaddings;
        private AlgorithmParameterSpec mSpec;
        private int mUid = -1;
        private boolean mUniqueIdIncluded = false;
        private boolean mUnlockedDeviceRequired = false;
        private boolean mUserAuthenticationRequired;
        private boolean mUserAuthenticationValidWhileOnBody;
        private int mUserAuthenticationValidityDurationSeconds = -1;
        private boolean mUserConfirmationRequired;
        private boolean mUserPresenceRequired = false;

        public Builder(KeyGenParameterSpec keyGenParameterSpec) {
            this(keyGenParameterSpec.getKeystoreAlias(), keyGenParameterSpec.getPurposes());
            this.mUid = keyGenParameterSpec.getUid();
            this.mKeySize = keyGenParameterSpec.getKeySize();
            this.mSpec = keyGenParameterSpec.getAlgorithmParameterSpec();
            this.mCertificateSubject = keyGenParameterSpec.getCertificateSubject();
            this.mCertificateSerialNumber = keyGenParameterSpec.getCertificateSerialNumber();
            this.mCertificateNotBefore = keyGenParameterSpec.getCertificateNotBefore();
            this.mCertificateNotAfter = keyGenParameterSpec.getCertificateNotAfter();
            this.mKeyValidityStart = keyGenParameterSpec.getKeyValidityStart();
            this.mKeyValidityForOriginationEnd = keyGenParameterSpec.getKeyValidityForOriginationEnd();
            this.mKeyValidityForConsumptionEnd = keyGenParameterSpec.getKeyValidityForConsumptionEnd();
            this.mPurposes = keyGenParameterSpec.getPurposes();
            if (keyGenParameterSpec.isDigestsSpecified()) {
                this.mDigests = keyGenParameterSpec.getDigests();
            }
            this.mEncryptionPaddings = keyGenParameterSpec.getEncryptionPaddings();
            this.mSignaturePaddings = keyGenParameterSpec.getSignaturePaddings();
            this.mBlockModes = keyGenParameterSpec.getBlockModes();
            this.mRandomizedEncryptionRequired = keyGenParameterSpec.isRandomizedEncryptionRequired();
            this.mUserAuthenticationRequired = keyGenParameterSpec.isUserAuthenticationRequired();
            this.mUserAuthenticationValidityDurationSeconds = keyGenParameterSpec.getUserAuthenticationValidityDurationSeconds();
            this.mUserPresenceRequired = keyGenParameterSpec.isUserPresenceRequired();
            this.mAttestationChallenge = keyGenParameterSpec.getAttestationChallenge();
            this.mUniqueIdIncluded = keyGenParameterSpec.isUniqueIdIncluded();
            this.mUserAuthenticationValidWhileOnBody = keyGenParameterSpec.isUserAuthenticationValidWhileOnBody();
            this.mInvalidatedByBiometricEnrollment = keyGenParameterSpec.isInvalidatedByBiometricEnrollment();
            this.mIsStrongBoxBacked = keyGenParameterSpec.isStrongBoxBacked();
            this.mUserConfirmationRequired = keyGenParameterSpec.isUserConfirmationRequired();
            this.mUnlockedDeviceRequired = keyGenParameterSpec.isUnlockedDeviceRequired();
        }

        public Builder(String string2, int n) {
            if (string2 != null) {
                if (!string2.isEmpty()) {
                    this.mKeystoreAlias = string2;
                    this.mPurposes = n;
                    return;
                }
                throw new IllegalArgumentException("keystoreAlias must not be empty");
            }
            throw new NullPointerException("keystoreAlias == null");
        }

        public KeyGenParameterSpec build() {
            return new KeyGenParameterSpec(this.mKeystoreAlias, this.mUid, this.mKeySize, this.mSpec, this.mCertificateSubject, this.mCertificateSerialNumber, this.mCertificateNotBefore, this.mCertificateNotAfter, this.mKeyValidityStart, this.mKeyValidityForOriginationEnd, this.mKeyValidityForConsumptionEnd, this.mPurposes, this.mDigests, this.mEncryptionPaddings, this.mSignaturePaddings, this.mBlockModes, this.mRandomizedEncryptionRequired, this.mUserAuthenticationRequired, this.mUserAuthenticationValidityDurationSeconds, this.mUserPresenceRequired, this.mAttestationChallenge, this.mUniqueIdIncluded, this.mUserAuthenticationValidWhileOnBody, this.mInvalidatedByBiometricEnrollment, this.mIsStrongBoxBacked, this.mUserConfirmationRequired, this.mUnlockedDeviceRequired);
        }

        public Builder setAlgorithmParameterSpec(AlgorithmParameterSpec algorithmParameterSpec) {
            if (algorithmParameterSpec != null) {
                this.mSpec = algorithmParameterSpec;
                return this;
            }
            throw new NullPointerException("spec == null");
        }

        public Builder setAttestationChallenge(byte[] arrby) {
            this.mAttestationChallenge = arrby;
            return this;
        }

        public Builder setBlockModes(String ... arrstring) {
            this.mBlockModes = ArrayUtils.cloneIfNotEmpty(arrstring);
            return this;
        }

        public Builder setCertificateNotAfter(Date date) {
            if (date != null) {
                this.mCertificateNotAfter = Utils.cloneIfNotNull(date);
                return this;
            }
            throw new NullPointerException("date == null");
        }

        public Builder setCertificateNotBefore(Date date) {
            if (date != null) {
                this.mCertificateNotBefore = Utils.cloneIfNotNull(date);
                return this;
            }
            throw new NullPointerException("date == null");
        }

        public Builder setCertificateSerialNumber(BigInteger bigInteger) {
            if (bigInteger != null) {
                this.mCertificateSerialNumber = bigInteger;
                return this;
            }
            throw new NullPointerException("serialNumber == null");
        }

        public Builder setCertificateSubject(X500Principal x500Principal) {
            if (x500Principal != null) {
                this.mCertificateSubject = x500Principal;
                return this;
            }
            throw new NullPointerException("subject == null");
        }

        public Builder setDigests(String ... arrstring) {
            this.mDigests = ArrayUtils.cloneIfNotEmpty(arrstring);
            return this;
        }

        public Builder setEncryptionPaddings(String ... arrstring) {
            this.mEncryptionPaddings = ArrayUtils.cloneIfNotEmpty(arrstring);
            return this;
        }

        public Builder setInvalidatedByBiometricEnrollment(boolean bl) {
            this.mInvalidatedByBiometricEnrollment = bl;
            return this;
        }

        public Builder setIsStrongBoxBacked(boolean bl) {
            this.mIsStrongBoxBacked = bl;
            return this;
        }

        public Builder setKeySize(int n) {
            if (n >= 0) {
                this.mKeySize = n;
                return this;
            }
            throw new IllegalArgumentException("keySize < 0");
        }

        public Builder setKeyValidityEnd(Date date) {
            this.setKeyValidityForOriginationEnd(date);
            this.setKeyValidityForConsumptionEnd(date);
            return this;
        }

        public Builder setKeyValidityForConsumptionEnd(Date date) {
            this.mKeyValidityForConsumptionEnd = Utils.cloneIfNotNull(date);
            return this;
        }

        public Builder setKeyValidityForOriginationEnd(Date date) {
            this.mKeyValidityForOriginationEnd = Utils.cloneIfNotNull(date);
            return this;
        }

        public Builder setKeyValidityStart(Date date) {
            this.mKeyValidityStart = Utils.cloneIfNotNull(date);
            return this;
        }

        public Builder setRandomizedEncryptionRequired(boolean bl) {
            this.mRandomizedEncryptionRequired = bl;
            return this;
        }

        public Builder setSignaturePaddings(String ... arrstring) {
            this.mSignaturePaddings = ArrayUtils.cloneIfNotEmpty(arrstring);
            return this;
        }

        public Builder setUid(int n) {
            this.mUid = n;
            return this;
        }

        public Builder setUniqueIdIncluded(boolean bl) {
            this.mUniqueIdIncluded = bl;
            return this;
        }

        public Builder setUnlockedDeviceRequired(boolean bl) {
            this.mUnlockedDeviceRequired = bl;
            return this;
        }

        public Builder setUserAuthenticationRequired(boolean bl) {
            this.mUserAuthenticationRequired = bl;
            return this;
        }

        public Builder setUserAuthenticationValidWhileOnBody(boolean bl) {
            this.mUserAuthenticationValidWhileOnBody = bl;
            return this;
        }

        public Builder setUserAuthenticationValidityDurationSeconds(int n) {
            if (n >= -1) {
                this.mUserAuthenticationValidityDurationSeconds = n;
                return this;
            }
            throw new IllegalArgumentException("seconds must be -1 or larger");
        }

        public Builder setUserConfirmationRequired(boolean bl) {
            this.mUserConfirmationRequired = bl;
            return this;
        }

        public Builder setUserPresenceRequired(boolean bl) {
            this.mUserPresenceRequired = bl;
            return this;
        }
    }

}

