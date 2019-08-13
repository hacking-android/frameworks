/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.digest;

import com.android.org.bouncycastle.crypto.Digest;
import java.security.MessageDigest;

public class BCMessageDigest
extends MessageDigest {
    protected Digest digest;

    protected BCMessageDigest(Digest digest) {
        super(digest.getAlgorithmName());
        this.digest = digest;
    }

    @Override
    public byte[] engineDigest() {
        byte[] arrby = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(arrby, 0);
        return arrby;
    }

    @Override
    public void engineReset() {
        this.digest.reset();
    }

    @Override
    public void engineUpdate(byte by) {
        this.digest.update(by);
    }

    @Override
    public void engineUpdate(byte[] arrby, int n, int n2) {
        this.digest.update(arrby, n, n2);
    }
}

