/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.keystore.ArrayUtils;
import android.security.keystore.Utils;
import java.security.spec.KeySpec;
import java.util.Date;

public class KeyInfo
implements KeySpec {
    private final String[] mBlockModes;
    private final String[] mDigests;
    private final String[] mEncryptionPaddings;
    private final boolean mInsideSecureHardware;
    private final boolean mInvalidatedByBiometricEnrollment;
    private final int mKeySize;
    private final Date mKeyValidityForConsumptionEnd;
    private final Date mKeyValidityForOriginationEnd;
    private final Date mKeyValidityStart;
    private final String mKeystoreAlias;
    private final int mOrigin;
    private final int mPurposes;
    private final String[] mSignaturePaddings;
    private final boolean mTrustedUserPresenceRequired;
    private final boolean mUserAuthenticationRequired;
    private final boolean mUserAuthenticationRequirementEnforcedBySecureHardware;
    private final boolean mUserAuthenticationValidWhileOnBody;
    private final int mUserAuthenticationValidityDurationSeconds;
    private final boolean mUserConfirmationRequired;

    public KeyInfo(String string2, boolean bl, int n, int n2, Date date, Date date2, Date date3, int n3, String[] arrstring, String[] arrstring2, String[] arrstring3, String[] arrstring4, boolean bl2, int n4, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
        this.mKeystoreAlias = string2;
        this.mInsideSecureHardware = bl;
        this.mOrigin = n;
        this.mKeySize = n2;
        this.mKeyValidityStart = Utils.cloneIfNotNull(date);
        this.mKeyValidityForOriginationEnd = Utils.cloneIfNotNull(date2);
        this.mKeyValidityForConsumptionEnd = Utils.cloneIfNotNull(date3);
        this.mPurposes = n3;
        this.mEncryptionPaddings = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(arrstring));
        this.mSignaturePaddings = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(arrstring2));
        this.mDigests = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(arrstring3));
        this.mBlockModes = ArrayUtils.cloneIfNotEmpty(ArrayUtils.nullToEmpty(arrstring4));
        this.mUserAuthenticationRequired = bl2;
        this.mUserAuthenticationValidityDurationSeconds = n4;
        this.mUserAuthenticationRequirementEnforcedBySecureHardware = bl3;
        this.mUserAuthenticationValidWhileOnBody = bl4;
        this.mTrustedUserPresenceRequired = bl5;
        this.mInvalidatedByBiometricEnrollment = bl6;
        this.mUserConfirmationRequired = bl7;
    }

    public String[] getBlockModes() {
        return ArrayUtils.cloneIfNotEmpty(this.mBlockModes);
    }

    public String[] getDigests() {
        return ArrayUtils.cloneIfNotEmpty(this.mDigests);
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

    public int getOrigin() {
        return this.mOrigin;
    }

    public int getPurposes() {
        return this.mPurposes;
    }

    public String[] getSignaturePaddings() {
        return ArrayUtils.cloneIfNotEmpty(this.mSignaturePaddings);
    }

    public int getUserAuthenticationValidityDurationSeconds() {
        return this.mUserAuthenticationValidityDurationSeconds;
    }

    public boolean isInsideSecureHardware() {
        return this.mInsideSecureHardware;
    }

    public boolean isInvalidatedByBiometricEnrollment() {
        return this.mInvalidatedByBiometricEnrollment;
    }

    public boolean isTrustedUserPresenceRequired() {
        return this.mTrustedUserPresenceRequired;
    }

    public boolean isUserAuthenticationRequired() {
        return this.mUserAuthenticationRequired;
    }

    public boolean isUserAuthenticationRequirementEnforcedBySecureHardware() {
        return this.mUserAuthenticationRequirementEnforcedBySecureHardware;
    }

    public boolean isUserAuthenticationValidWhileOnBody() {
        return this.mUserAuthenticationValidWhileOnBody;
    }

    public boolean isUserConfirmationRequired() {
        return this.mUserConfirmationRequired;
    }
}

