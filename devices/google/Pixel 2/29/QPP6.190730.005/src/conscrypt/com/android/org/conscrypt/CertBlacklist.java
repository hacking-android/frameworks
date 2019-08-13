/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import java.math.BigInteger;
import java.security.PublicKey;

public interface CertBlacklist {
    public boolean isPublicKeyBlackListed(PublicKey var1);

    public boolean isSerialNumberBlackListed(BigInteger var1);
}

