/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.digests;

import com.android.org.bouncycastle.crypto.ExtendedDigest;
import java.security.DigestException;
import java.security.MessageDigest;

public class OpenSSLDigest
implements ExtendedDigest {
    private final int byteSize;
    private final MessageDigest delegate;

    public OpenSSLDigest(String string, int n) {
        try {
            this.delegate = MessageDigest.getInstance(string, "AndroidOpenSSL");
            this.byteSize = n;
            return;
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public int doFinal(byte[] arrby, int n) {
        try {
            n = this.delegate.digest(arrby, n, arrby.length - n);
            return n;
        }
        catch (DigestException digestException) {
            throw new RuntimeException(digestException);
        }
    }

    @Override
    public String getAlgorithmName() {
        return this.delegate.getAlgorithm();
    }

    @Override
    public int getByteLength() {
        return this.byteSize;
    }

    @Override
    public int getDigestSize() {
        return this.delegate.getDigestLength();
    }

    @Override
    public void reset() {
        this.delegate.reset();
    }

    @Override
    public void update(byte by) {
        this.delegate.update(by);
    }

    @Override
    public void update(byte[] arrby, int n, int n2) {
        this.delegate.update(arrby, n, n2);
    }

    public static class MD5
    extends OpenSSLDigest {
        public MD5() {
            super("MD5", 64);
        }
    }

    public static class SHA1
    extends OpenSSLDigest {
        public SHA1() {
            super("SHA-1", 64);
        }
    }

    public static class SHA224
    extends OpenSSLDigest {
        public SHA224() {
            super("SHA-224", 64);
        }
    }

    public static class SHA256
    extends OpenSSLDigest {
        public SHA256() {
            super("SHA-256", 64);
        }
    }

    public static class SHA384
    extends OpenSSLDigest {
        public SHA384() {
            super("SHA-384", 128);
        }
    }

    public static class SHA512
    extends OpenSSLDigest {
        public SHA512() {
            super("SHA-512", 128);
        }
    }

}

