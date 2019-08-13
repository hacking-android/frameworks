/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.generators;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.PBEParametersGenerator;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactory;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;

public class OpenSSLPBEParametersGenerator
extends PBEParametersGenerator {
    private Digest digest = AndroidDigestFactory.getMD5();

    private byte[] generateDerivedKey(int n) {
        byte[] arrby = new byte[this.digest.getDigestSize()];
        byte[] arrby2 = new byte[n];
        int n2 = 0;
        do {
            this.digest.update(this.password, 0, this.password.length);
            this.digest.update(this.salt, 0, this.salt.length);
            this.digest.doFinal(arrby, 0);
            int n3 = n > arrby.length ? arrby.length : n;
            System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)n2, (int)n3);
            n2 += n3;
            if ((n -= n3) == 0) {
                return arrby2;
            }
            this.digest.reset();
            this.digest.update(arrby, 0, arrby.length);
        } while (true);
    }

    @Override
    public CipherParameters generateDerivedMacParameters(int n) {
        return this.generateDerivedParameters(n);
    }

    @Override
    public CipherParameters generateDerivedParameters(int n) {
        return new KeyParameter(this.generateDerivedKey(n /= 8), 0, n);
    }

    @Override
    public CipherParameters generateDerivedParameters(int n, int n2) {
        byte[] arrby = this.generateDerivedKey((n /= 8) + (n2 /= 8));
        return new ParametersWithIV(new KeyParameter(arrby, 0, n), arrby, n, n2);
    }

    public void init(byte[] arrby, byte[] arrby2) {
        super.init(arrby, arrby2, 1);
    }
}

