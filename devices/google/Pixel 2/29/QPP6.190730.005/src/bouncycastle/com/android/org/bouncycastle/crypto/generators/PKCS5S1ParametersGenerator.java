/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.generators;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.PBEParametersGenerator;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;

public class PKCS5S1ParametersGenerator
extends PBEParametersGenerator {
    private Digest digest;

    public PKCS5S1ParametersGenerator(Digest digest) {
        this.digest = digest;
    }

    private byte[] generateDerivedKey() {
        byte[] arrby = new byte[this.digest.getDigestSize()];
        this.digest.update(this.password, 0, this.password.length);
        this.digest.update(this.salt, 0, this.salt.length);
        this.digest.doFinal(arrby, 0);
        for (int i = 1; i < this.iterationCount; ++i) {
            this.digest.update(arrby, 0, arrby.length);
            this.digest.doFinal(arrby, 0);
        }
        return arrby;
    }

    @Override
    public CipherParameters generateDerivedMacParameters(int n) {
        return this.generateDerivedParameters(n);
    }

    @Override
    public CipherParameters generateDerivedParameters(int n) {
        if ((n /= 8) <= this.digest.getDigestSize()) {
            return new KeyParameter(this.generateDerivedKey(), 0, n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Can't generate a derived key ");
        stringBuilder.append(n);
        stringBuilder.append(" bytes long.");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public CipherParameters generateDerivedParameters(int n, int n2) {
        if ((n /= 8) + (n2 /= 8) <= this.digest.getDigestSize()) {
            byte[] arrby = this.generateDerivedKey();
            return new ParametersWithIV(new KeyParameter(arrby, 0, n), arrby, n, n2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Can't generate a derived key ");
        stringBuilder.append(n + n2);
        stringBuilder.append(" bytes long.");
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}

