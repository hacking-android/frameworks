/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.OpenSSLKey;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KeyAgreementSpi;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

public final class OpenSSLECDHKeyAgreement
extends KeyAgreementSpi {
    private int mExpectedResultLength;
    private OpenSSLKey mOpenSslPrivateKey;
    private byte[] mResult;

    private void checkCompleted() {
        if (this.mResult != null) {
            return;
        }
        throw new IllegalStateException("Key agreement not completed");
    }

    @Override
    public Key engineDoPhase(Key object, boolean bl) throws InvalidKeyException {
        block2 : {
            block3 : {
                block4 : {
                    block5 : {
                        int n;
                        block6 : {
                            block8 : {
                                block7 : {
                                    if (this.mOpenSslPrivateKey == null) break block2;
                                    if (!bl) break block3;
                                    if (object == null) break block4;
                                    if (!(object instanceof PublicKey)) break block5;
                                    byte[] arrby = OpenSSLKey.fromPublicKey((PublicKey)object);
                                    object = new byte[this.mExpectedResultLength];
                                    n = NativeCrypto.ECDH_compute_key((byte[])object, 0, arrby.getNativeRef(), this.mOpenSslPrivateKey.getNativeRef());
                                    if (n == -1) break block6;
                                    int n2 = this.mExpectedResultLength;
                                    if (n == n2) break block7;
                                    if (n >= n2) break block8;
                                    arrby = new byte[n];
                                    byte[] arrby2 = this.mResult;
                                    System.arraycopy(object, 0, arrby2, 0, arrby2.length);
                                    object = arrby;
                                }
                                this.mResult = object;
                                return null;
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Engine produced a longer than expected result. Expected: ");
                            ((StringBuilder)object).append(this.mExpectedResultLength);
                            ((StringBuilder)object).append(", actual: ");
                            ((StringBuilder)object).append(n);
                            throw new RuntimeException(((StringBuilder)object).toString());
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Engine returned ");
                        ((StringBuilder)object).append(n);
                        throw new RuntimeException(((StringBuilder)object).toString());
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Not a public key: ");
                    stringBuilder.append(object.getClass());
                    throw new InvalidKeyException(stringBuilder.toString());
                }
                throw new InvalidKeyException("key == null");
            }
            throw new IllegalStateException("ECDH only has one phase");
        }
        throw new IllegalStateException("Not initialized");
    }

    @Override
    protected int engineGenerateSecret(byte[] object, int n) throws ShortBufferException {
        this.checkCompleted();
        int n2 = ((byte[])object).length - n;
        byte[] arrby = this.mResult;
        if (arrby.length <= n2) {
            System.arraycopy(arrby, 0, object, n, arrby.length);
            return this.mResult.length;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Needed: ");
        ((StringBuilder)object).append(this.mResult.length);
        ((StringBuilder)object).append(", available: ");
        ((StringBuilder)object).append(n2);
        throw new ShortBufferException(((StringBuilder)object).toString());
    }

    @Override
    protected SecretKey engineGenerateSecret(String string) {
        this.checkCompleted();
        return new SecretKeySpec(this.engineGenerateSecret(), string);
    }

    @Override
    protected byte[] engineGenerateSecret() {
        this.checkCompleted();
        return this.mResult;
    }

    @Override
    protected void engineInit(Key object, SecureRandom serializable) throws InvalidKeyException {
        if (object != null) {
            if (object instanceof PrivateKey) {
                object = OpenSSLKey.fromPrivateKey((PrivateKey)object);
                this.mExpectedResultLength = (NativeCrypto.EC_GROUP_get_degree(new NativeRef.EC_GROUP(NativeCrypto.EC_KEY_get1_group(((OpenSSLKey)object).getNativeRef()))) + 7) / 8;
                this.mOpenSslPrivateKey = object;
                return;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Not a private key: ");
            ((StringBuilder)serializable).append(object.getClass());
            throw new InvalidKeyException(((StringBuilder)serializable).toString());
        }
        throw new InvalidKeyException("key == null");
    }

    @Override
    protected void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameterSpec == null) {
            this.engineInit(key, secureRandom);
            return;
        }
        throw new InvalidAlgorithmParameterException("No algorithm parameters supported");
    }
}

