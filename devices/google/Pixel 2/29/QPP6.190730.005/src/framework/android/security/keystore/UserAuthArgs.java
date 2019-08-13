/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

public interface UserAuthArgs {
    public long getBoundToSpecificSecureUserId();

    public int getUserAuthenticationValidityDurationSeconds();

    public boolean isInvalidatedByBiometricEnrollment();

    public boolean isUnlockedDeviceRequired();

    public boolean isUserAuthenticationRequired();

    public boolean isUserAuthenticationValidWhileOnBody();

    public boolean isUserConfirmationRequired();

    public boolean isUserPresenceRequired();
}

