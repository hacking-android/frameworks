/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.digests;

import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface;
import com.android.org.bouncycastle.crypto.digests.OpenSSLDigest;

public class AndroidDigestFactoryOpenSSL
implements AndroidDigestFactoryInterface {
    @Override
    public Digest getMD5() {
        return new OpenSSLDigest.MD5();
    }

    @Override
    public Digest getSHA1() {
        return new OpenSSLDigest.SHA1();
    }

    @Override
    public Digest getSHA224() {
        return new OpenSSLDigest.SHA224();
    }

    @Override
    public Digest getSHA256() {
        return new OpenSSLDigest.SHA256();
    }

    @Override
    public Digest getSHA384() {
        return new OpenSSLDigest.SHA384();
    }

    @Override
    public Digest getSHA512() {
        return new OpenSSLDigest.SHA512();
    }
}

