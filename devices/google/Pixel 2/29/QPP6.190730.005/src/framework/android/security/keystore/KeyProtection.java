/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.keystore.ArrayUtils;
import android.security.keystore.UserAuthArgs;
import android.security.keystore.Utils;
import java.security.KeyStore;
import java.util.Date;

public final class KeyProtection
implements KeyStore.ProtectionParameter,
UserAuthArgs {
    private final String[] mBlockModes;
    private final long mBoundToSecureUserId;
    private final boolean mCriticalToDeviceEncryption;
    private final String[] mDigests;
    private final String[] mEncryptionPaddings;
    private final boolean mInvalidatedByBiometricEnrollment;
    private final boolean mIsStrongBoxBacked;
    private final Date mKeyValidityForConsumptionEnd;
    private final Date mKeyValidityForOriginationEnd;
    private final Date mKeyValidityStart;
    private final int mPurposes;
    private final boolean mRandomizedEncryptionRequired;
    private final String[] mSignaturePaddings;
    private final boolean mUnlockedDeviceRequired;
    private final boolean mUserAuthenticationRequired;
    private final boolean mUserAuthenticationValidWhileOnBody;
    private final int mUserAuthenticationValidityDurationSeconds;
    private final boolean mUserConfirmationRequired;
    private final boolean mUserPresenceRequred;

    private KeyProtection(Date date, Date date2, Date date3, int n, String[] arrstring, String[] arrstring2, String[] arrstring3, String[] arrstring4, boolean bl, boolean bl2, int n2, boolean bl3, boolean bl4, boolean bl5, long l, boolean bl6, boolean bl7, boolean bl8, boolean bl9) {
        this.mKeyValidityStart = Utils.cloneIfNotNull(date);
        this.mKeyValidityForOriginationEnd = Utils.cloneIfNotNull(date2);
        this.mKeyValidityForConsumptionEnd = Utils.cloneIfNotNull(date3);
        this.mPurposes = n;
        this.mEncryptionPaddings = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(arrstring));
        this.mSignaturePaddings = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(arrstring2));
        this.mDigests = ArrayUtils.cloneIfNotEmpty(arrstring3);
        this.mBlockModes = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(arrstring4));
        this.mRandomizedEncryptionRequired = bl;
        this.mUserAuthenticationRequired = bl2;
        this.mUserAuthenticationValidityDurationSeconds = n2;
        this.mUserPresenceRequred = bl3;
        this.mUserAuthenticationValidWhileOnBody = bl4;
        this.mInvalidatedByBiometricEnrollment = bl5;
        this.mBoundToSecureUserId = l;
        this.mCriticalToDeviceEncryption = bl6;
        this.mUserConfirmationRequired = bl7;
        this.mUnlockedDeviceRequired = bl8;
        this.mIsStrongBoxBacked = bl9;
    }

    public String[] getBlockModes() {
        return ArrayUtils.cloneIfNotEmpty(this.mBlockModes);
    }

    @Override
    public long getBoundToSpecificSecureUserId() {
        return this.mBoundToSecureUserId;
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

    public Date getKeyValidityForConsumptionEnd() {
        return Utils.cloneIfNotNull(this.mKeyValidityForConsumptionEnd);
    }

    public Date getKeyValidityForOriginationEnd() {
        return Utils.cloneIfNotNull(this.mKeyValidityForOriginationEnd);
    }

    public Date getKeyValidityStart() {
        return Utils.cloneIfNotNull(this.mKeyValidityStart);
    }

    public int getPurposes() {
        return this.mPurposes;
    }

    public String[] getSignaturePaddings() {
        return ArrayUtils.cloneIfNotEmpty(this.mSignaturePaddings);
    }

    @Override
    public int getUserAuthenticationValidityDurationSeconds() {
        return this.mUserAuthenticationValidityDurationSeconds;
    }

    public boolean isCriticalToDeviceEncryption() {
        return this.mCriticalToDeviceEncryption;
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
        return this.mUserPresenceRequred;
    }

    public static final class Builder {
        private String[] mBlockModes;
        private long mBoundToSecureUserId = 0L;
        private boolean mCriticalToDeviceEncryption = false;
        private String[] mDigests;
        private String[] mEncryptionPaddings;
        private boolean mInvalidatedByBiometricEnrollment = true;
        private boolean mIsStrongBoxBacked = false;
        private Date mKeyValidityForConsumptionEnd;
        private Date mKeyValidityForOriginationEnd;
        private Date mKeyValidityStart;
        private int mPurposes;
        private boolean mRandomizedEncryptionRequired = true;
        private String[] mSignaturePaddings;
        private boolean mUnlockedDeviceRequired = false;
        private boolean mUserAuthenticationRequired;
        private boolean mUserAuthenticationValidWhileOnBody;
        private int mUserAuthenticationValidityDurationSeconds = -1;
        private boolean mUserConfirmationRequired;
        private boolean mUserPresenceRequired = false;

        public Builder(int n) {
            this.mPurposes = n;
        }

        public KeyProtection build() {
            return new KeyProtection(this.mKeyValidityStart, this.mKeyValidityForOriginationEnd, this.mKeyValidityForConsumptionEnd, this.mPurposes, this.mEncryptionPaddings, this.mSignaturePaddings, this.mDigests, this.mBlockModes, this.mRandomizedEncryptionRequired, this.mUserAuthenticationRequired, this.mUserAuthenticationValidityDurationSeconds, this.mUserPresenceRequired, this.mUserAuthenticationValidWhileOnBody, this.mInvalidatedByBiometricEnrollment, this.mBoundToSecureUserId, this.mCriticalToDeviceEncryption, this.mUserConfirmationRequired, this.mUnlockedDeviceRequired, this.mIsStrongBoxBacked);
        }

        public Builder setBlockModes(String ... arrstring) {
            this.mBlockModes = ArrayUtils.cloneIfNotEmpty(arrstring);
            return this;
        }

        public Builder setBoundToSpecificSecureUserId(long l) {
            this.mBoundToSecureUserId = l;
            return this;
        }

        public Builder setCriticalToDeviceEncryption(boolean bl) {
            this.mCriticalToDeviceEncryption = bl;
            return this;
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

