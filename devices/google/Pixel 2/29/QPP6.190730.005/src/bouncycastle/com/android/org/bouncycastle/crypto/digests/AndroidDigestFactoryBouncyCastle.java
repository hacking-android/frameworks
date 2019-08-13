/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.digests;

import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactoryInterface;
import com.android.org.bouncycastle.crypto.digests.MD5Digest;
import com.android.org.bouncycastle.crypto.digests.SHA1Digest;
import com.android.org.bouncycastle.crypto.digests.SHA224Digest;
import com.android.org.bouncycastle.crypto.digests.SHA256Digest;
import com.android.org.bouncycastle.crypto.digests.SHA384Digest;
import com.android.org.bouncycastle.crypto.digests.SHA512Digest;

public class AndroidDigestFactoryBouncyCastle
implements AndroidDigestFactoryInterface {
    @Override
    public Digest getMD5() {
        return new MD5Digest();
    }

    @Override
    public Digest getSHA1() {
        return new SHA1Digest();
    }

    @Override
    public Digest getSHA224() {
        return new SHA224Digest();
    }

    @Override
    public Digest getSHA256() {
        return new SHA256Digest();
    }

    @Override
    public Digest getSHA384() {
        return new SHA384Digest();
    }

    @Override
    public Digest getSHA512() {
        return new SHA512Digest();
    }
}

