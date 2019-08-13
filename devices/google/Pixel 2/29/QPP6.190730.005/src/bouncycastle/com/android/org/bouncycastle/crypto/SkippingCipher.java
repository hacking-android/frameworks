/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto;

public interface SkippingCipher {
    public long getPosition();

    public long seekTo(long var1);

    public long skip(long var1);
}

