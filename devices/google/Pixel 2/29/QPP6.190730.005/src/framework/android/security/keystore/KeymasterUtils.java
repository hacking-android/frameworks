/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.content.pm.PackageManager;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.security.GateKeeper;
import android.security.KeyStore;
import android.security.keymaster.KeymasterArguments;
import android.security.keystore.KeyProperties;
import android.security.keystore.UserAuthArgs;
import com.android.internal.util.ArrayUtils;
import java.math.BigInteger;
import java.security.ProviderException;
import java.util.ArrayList;

public abstract class KeymasterUtils {
    private KeymasterUtils() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void addMinMacLengthAuthorizationIfNecessary(KeymasterArguments object, int n, int[] arrn, int[] arrn2) {
        if (n == 32) {
            if (!ArrayUtils.contains(arrn, 32)) return;
            ((KeymasterArguments)object).addUnsignedInt(805306376, 96L);
            return;
        }
        if (n != 128) {
            return;
        }
        if (arrn2.length != 1) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported number of authorized digests for HMAC key: ");
            ((StringBuilder)object).append(arrn2.length);
            ((StringBuilder)object).append(". Exactly one digest must be authorized");
            throw new ProviderException(((StringBuilder)object).toString());
        }
        int n2 = arrn2[0];
        n = KeymasterUtils.getDigestOutputSizeBits(n2);
        if (n != -1) {
            ((KeymasterArguments)object).addUnsignedInt(805306376, n);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("HMAC key authorized for unsupported digest: ");
        ((StringBuilder)object).append(KeyProperties.Digest.fromKeymaster(n2));
        throw new ProviderException(((StringBuilder)object).toString());
    }

    public static void addUserAuthArgs(KeymasterArguments keymasterArguments, UserAuthArgs userAuthArgs) {
        if (userAuthArgs.isUserConfirmationRequired()) {
            keymasterArguments.addBoolean(1879048700);
        }
        if (userAuthArgs.isUserPresenceRequired()) {
            keymasterArguments.addBoolean(1879048699);
        }
        if (userAuthArgs.isUnlockedDeviceRequired()) {
            keymasterArguments.addBoolean(1879048701);
        }
        if (!userAuthArgs.isUserAuthenticationRequired()) {
            keymasterArguments.addBoolean(1879048695);
            return;
        }
        if (userAuthArgs.getUserAuthenticationValidityDurationSeconds() == -1) {
            PackageManager packageManager = KeyStore.getApplicationContext().getPackageManager();
            Object object = null;
            FaceManager faceManager = null;
            if (packageManager.hasSystemFeature("android.hardware.fingerprint")) {
                object = KeyStore.getApplicationContext().getSystemService(FingerprintManager.class);
            }
            if (packageManager.hasSystemFeature("android.hardware.biometrics.face")) {
                faceManager = KeyStore.getApplicationContext().getSystemService(FaceManager.class);
            }
            long l = object != null ? ((FingerprintManager)object).getAuthenticatorId() : 0L;
            long l2 = faceManager != null ? faceManager.getAuthenticatorId() : 0L;
            if (l == 0L && l2 == 0L) {
                throw new IllegalStateException("At least one biometric must be enrolled to create keys requiring user authentication for every use");
            }
            object = new ArrayList();
            if (userAuthArgs.getBoundToSpecificSecureUserId() != 0L) {
                object.add(userAuthArgs.getBoundToSpecificSecureUserId());
            } else if (userAuthArgs.isInvalidatedByBiometricEnrollment()) {
                object.add(l);
                object.add(l2);
            } else {
                object.add(KeymasterUtils.getRootSid());
            }
            for (int i = 0; i < object.size(); ++i) {
                keymasterArguments.addUnsignedLong(-1610612234, KeymasterArguments.toUint64((Long)object.get(i)));
            }
            keymasterArguments.addEnum(268435960, 2);
            if (userAuthArgs.isUserAuthenticationValidWhileOnBody()) {
                throw new ProviderException("Key validity extension while device is on-body is not supported for keys requiring fingerprint authentication");
            }
        } else {
            long l = userAuthArgs.getBoundToSpecificSecureUserId() != 0L ? userAuthArgs.getBoundToSpecificSecureUserId() : KeymasterUtils.getRootSid();
            keymasterArguments.addUnsignedLong(-1610612234, KeymasterArguments.toUint64(l));
            keymasterArguments.addEnum(268435960, 3);
            keymasterArguments.addUnsignedInt(805306873, userAuthArgs.getUserAuthenticationValidityDurationSeconds());
            if (userAuthArgs.isUserAuthenticationValidWhileOnBody()) {
                keymasterArguments.addBoolean(1879048698);
            }
        }
    }

    public static int getDigestOutputSizeBits(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown digest: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 6: {
                return 512;
            }
            case 5: {
                return 384;
            }
            case 4: {
                return 256;
            }
            case 3: {
                return 224;
            }
            case 2: {
                return 160;
            }
            case 1: {
                return 128;
            }
            case 0: 
        }
        return -1;
    }

    private static long getRootSid() {
        long l = GateKeeper.getSecureUserId();
        if (l != 0L) {
            return l;
        }
        throw new IllegalStateException("Secure lock screen must be enabled to create keys requiring user authentication");
    }

    public static boolean isKeymasterBlockModeIndCpaCompatibleWithSymmetricCrypto(int n) {
        if (n != 1) {
            if (n != 2 && n != 3 && n != 32) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported block mode: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            return true;
        }
        return false;
    }

    public static boolean isKeymasterPaddingSchemeIndCpaCompatibleWithAsymmetricCrypto(int n) {
        if (n != 1) {
            if (n != 2 && n != 4) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported asymmetric encryption padding scheme: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            return true;
        }
        return false;
    }
}

