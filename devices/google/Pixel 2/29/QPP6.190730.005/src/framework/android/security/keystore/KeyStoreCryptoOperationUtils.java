/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.security.keystore;

import android.security.KeyStore;
import android.security.keystore.AndroidKeyStoreKey;
import android.security.keystore.UserNotAuthenticatedException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import libcore.util.EmptyArray;

abstract class KeyStoreCryptoOperationUtils {
    private static volatile SecureRandom sRng;

    private KeyStoreCryptoOperationUtils() {
    }

    public static GeneralSecurityException getExceptionForCipherInit(KeyStore keyStore, AndroidKeyStoreKey androidKeyStoreKey, int n) {
        if (n == 1) {
            return null;
        }
        if (n != -55) {
            if (n != -52) {
                return KeyStoreCryptoOperationUtils.getInvalidKeyExceptionForInit(keyStore, androidKeyStoreKey, n);
            }
            return new InvalidAlgorithmParameterException("Invalid IV");
        }
        return new InvalidAlgorithmParameterException("Caller-provided IV not permitted");
    }

    static InvalidKeyException getInvalidKeyExceptionForInit(KeyStore object, AndroidKeyStoreKey androidKeyStoreKey, int n) {
        if (n == 1) {
            return null;
        }
        object = ((KeyStore)object).getInvalidKeyException(androidKeyStoreKey.getAlias(), androidKeyStoreKey.getUid(), n);
        if (n == 15 && object instanceof UserNotAuthenticatedException) {
            return null;
        }
        return object;
    }

    static byte[] getRandomBytesToMixIntoKeystoreRng(SecureRandom arrby, int n) {
        if (n <= 0) {
            return EmptyArray.BYTE;
        }
        byte[] arrby2 = arrby;
        if (arrby == null) {
            arrby2 = KeyStoreCryptoOperationUtils.getRng();
        }
        arrby = new byte[n];
        arrby2.nextBytes(arrby);
        return arrby;
    }

    private static SecureRandom getRng() {
        if (sRng == null) {
            sRng = new SecureRandom();
        }
        return sRng;
    }
}

