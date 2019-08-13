/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.digests;

import com.android.org.bouncycastle.crypto.Digest;

interface AndroidDigestFactoryInterface {
    public Digest getMD5();

    public Digest getSHA1();

    public Digest getSHA224();

    public Digest getSHA256();

    public Digest getSHA384();

    public Digest getSHA512();
}

