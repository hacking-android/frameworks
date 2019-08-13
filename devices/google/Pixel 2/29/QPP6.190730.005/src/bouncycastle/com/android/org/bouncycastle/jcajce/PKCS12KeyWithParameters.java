/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce;

import com.android.org.bouncycastle.jcajce.PKCS12Key;
import com.android.org.bouncycastle.util.Arrays;
import javax.crypto.interfaces.PBEKey;

public class PKCS12KeyWithParameters
extends PKCS12Key
implements PBEKey {
    private final int iterationCount;
    private final byte[] salt;

    public PKCS12KeyWithParameters(char[] arrc, boolean bl, byte[] arrby, int n) {
        super(arrc, bl);
        this.salt = Arrays.clone(arrby);
        this.iterationCount = n;
    }

    public PKCS12KeyWithParameters(char[] arrc, byte[] arrby, int n) {
        super(arrc);
        this.salt = Arrays.clone(arrby);
        this.iterationCount = n;
    }

    @Override
    public int getIterationCount() {
        return this.iterationCount;
    }

    @Override
    public byte[] getSalt() {
        return this.salt;
    }
}

