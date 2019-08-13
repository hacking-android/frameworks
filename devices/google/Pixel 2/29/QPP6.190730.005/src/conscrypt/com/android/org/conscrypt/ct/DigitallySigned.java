/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt.ct;

import com.android.org.conscrypt.ct.Serialization;
import com.android.org.conscrypt.ct.SerializationException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class DigitallySigned {
    private final HashAlgorithm hashAlgorithm;
    private final byte[] signature;
    private final SignatureAlgorithm signatureAlgorithm;

    public DigitallySigned(int n, int n2, byte[] arrby) {
        this(HashAlgorithm.valueOf(n), SignatureAlgorithm.valueOf(n2), arrby);
    }

    public DigitallySigned(HashAlgorithm hashAlgorithm, SignatureAlgorithm signatureAlgorithm, byte[] arrby) {
        this.hashAlgorithm = hashAlgorithm;
        this.signatureAlgorithm = signatureAlgorithm;
        this.signature = arrby;
    }

    public static DigitallySigned decode(InputStream object) throws SerializationException {
        try {
            object = new DigitallySigned(Serialization.readNumber((InputStream)object, 1), Serialization.readNumber((InputStream)object, 1), Serialization.readVariableBytes((InputStream)object, 2));
            return object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new SerializationException(illegalArgumentException);
        }
    }

    public static DigitallySigned decode(byte[] arrby) throws SerializationException {
        return DigitallySigned.decode(new ByteArrayInputStream(arrby));
    }

    public String getAlgorithm() {
        return String.format("%swith%s", new Object[]{this.hashAlgorithm, this.signatureAlgorithm});
    }

    public HashAlgorithm getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public byte[] getSignature() {
        return this.signature;
    }

    public SignatureAlgorithm getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    public static enum HashAlgorithm {
        NONE,
        MD5,
        SHA1,
        SHA224,
        SHA256,
        SHA384,
        SHA512;
        
        private static HashAlgorithm[] values;

        static {
            values = HashAlgorithm.values();
        }

        public static HashAlgorithm valueOf(String string) {
            return Enum.valueOf(HashAlgorithm.class, string);
        }
    }

    public static enum SignatureAlgorithm {
        ANONYMOUS,
        RSA,
        DSA,
        ECDSA;
        
        private static SignatureAlgorithm[] values;

        static {
            values = SignatureAlgorithm.values();
        }

        public static SignatureAlgorithm valueOf(String string) {
            return Enum.valueOf(SignatureAlgorithm.class, string);
        }
    }

}

