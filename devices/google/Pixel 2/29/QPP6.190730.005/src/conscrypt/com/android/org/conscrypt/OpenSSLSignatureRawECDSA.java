/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.OpenSSLKey;
import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;

public class OpenSSLSignatureRawECDSA
extends SignatureSpi {
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private OpenSSLKey key;

    private static OpenSSLKey verifyKey(OpenSSLKey openSSLKey) throws InvalidKeyException {
        if (NativeCrypto.EVP_PKEY_type(openSSLKey.getNativeRef()) == 408) {
            return openSSLKey;
        }
        throw new InvalidKeyException("Non-EC key used to initialize EC signature.");
    }

    @Override
    protected Object engineGetParameter(String string) throws InvalidParameterException {
        return null;
    }

    @Override
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        this.key = OpenSSLSignatureRawECDSA.verifyKey(OpenSSLKey.fromPrivateKey(privateKey));
    }

    @Override
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        this.key = OpenSSLSignatureRawECDSA.verifyKey(OpenSSLKey.fromPublicKey(publicKey));
    }

    @Override
    protected void engineSetParameter(String string, Object object) throws InvalidParameterException {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected byte[] engineSign() throws SignatureException {
        Object object = this.key;
        if (object == null) {
            throw new SignatureException("No key provided");
        }
        int n = NativeCrypto.ECDSA_size(object.getNativeRef());
        Object object2 = new byte[n];
        try {
            int n2 = NativeCrypto.ECDSA_sign(this.buffer.toByteArray(), object2, this.key.getNativeRef());
            if (n2 >= 0) {
                object = object2;
                if (n2 != n) {
                    object = new byte[n2];
                    System.arraycopy(object2, 0, object, 0, n2);
                }
                this.buffer.reset();
                return object;
            }
            object = new SignatureException("Could not compute signature.");
            throw object;
        }
        catch (Throwable throwable) {
        }
        catch (Exception exception) {
            object2 = new SignatureException(exception);
            throw object2;
        }
        this.buffer.reset();
        throw throwable;
    }

    @Override
    protected void engineUpdate(byte by) {
        this.buffer.write(by);
    }

    @Override
    protected void engineUpdate(byte[] arrby, int n, int n2) {
        this.buffer.write(arrby, n, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected boolean engineVerify(byte[] object) throws SignatureException {
        if (this.key == null) {
            throw new SignatureException("No key provided");
        }
        try {
            int n = NativeCrypto.ECDSA_verify(this.buffer.toByteArray(), object, this.key.getNativeRef());
            if (n != -1) {
                boolean bl = true;
                if (n != 1) {
                    bl = false;
                }
                this.buffer.reset();
                return bl;
            }
            object = new SignatureException("Could not verify signature.");
            throw object;
        }
        catch (Throwable throwable) {
        }
        catch (Exception exception) {
            SignatureException signatureException = new SignatureException(exception);
            throw signatureException;
        }
        this.buffer.reset();
        throw throwable;
    }
}

