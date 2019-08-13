/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.biometrics;

import android.security.keystore.AndroidKeyStoreProvider;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

public class CryptoObject {
    private final Object mCrypto;

    public CryptoObject(Signature signature) {
        this.mCrypto = signature;
    }

    public CryptoObject(Cipher cipher) {
        this.mCrypto = cipher;
    }

    public CryptoObject(Mac mac) {
        this.mCrypto = mac;
    }

    public Cipher getCipher() {
        Object object = this.mCrypto;
        object = object instanceof Cipher ? (Cipher)object : null;
        return object;
    }

    public Mac getMac() {
        Object object = this.mCrypto;
        object = object instanceof Mac ? (Mac)object : null;
        return object;
    }

    public final long getOpId() {
        Object object = this.mCrypto;
        long l = object != null ? AndroidKeyStoreProvider.getKeyStoreOperationHandle(object) : 0L;
        return l;
    }

    public Signature getSignature() {
        Object object = this.mCrypto;
        object = object instanceof Signature ? (Signature)object : null;
        return object;
    }
}

