/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.OpenSSLKey;
import com.android.org.conscrypt.OpenSSLX509CertificateFactory;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public final class InternalUtil {
    private InternalUtil() {
    }

    public static PublicKey logKeyToPublicKey(byte[] object) throws NoSuchAlgorithmException {
        try {
            OpenSSLKey openSSLKey = new OpenSSLKey(NativeCrypto.EVP_parse_public_key(object));
            object = openSSLKey.getPublicKey();
            return object;
        }
        catch (OpenSSLX509CertificateFactory.ParsingException parsingException) {
            throw new NoSuchAlgorithmException(parsingException);
        }
    }

    public static PublicKey readPublicKeyPem(InputStream inputStream) throws InvalidKeyException, NoSuchAlgorithmException {
        return OpenSSLKey.fromPublicKeyPemInputStream(inputStream).getPublicKey();
    }
}

