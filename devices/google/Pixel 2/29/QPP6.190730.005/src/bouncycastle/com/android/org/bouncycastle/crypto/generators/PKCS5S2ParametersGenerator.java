/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.generators;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.Mac;
import com.android.org.bouncycastle.crypto.PBEParametersGenerator;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactory;
import com.android.org.bouncycastle.crypto.macs.HMac;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.org.bouncycastle.util.Arrays;

public class PKCS5S2ParametersGenerator
extends PBEParametersGenerator {
    private Mac hMac;
    private byte[] state;

    public PKCS5S2ParametersGenerator() {
        this(AndroidDigestFactory.getSHA1());
    }

    public PKCS5S2ParametersGenerator(Digest digest) {
        this.hMac = new HMac(digest);
        this.state = new byte[this.hMac.getMacSize()];
    }

    private void F(byte[] arrby, int n, byte[] object, byte[] arrby2, int n2) {
        if (n != 0) {
            if (arrby != null) {
                this.hMac.update(arrby, 0, arrby.length);
            }
            this.hMac.update((byte[])object, 0, ((byte[])object).length);
            this.hMac.doFinal(this.state, 0);
            arrby = this.state;
            System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)n2, (int)arrby.length);
            for (int i = 1; i < n; ++i) {
                object = this.hMac;
                arrby = this.state;
                object.update(arrby, 0, arrby.length);
                this.hMac.doFinal(this.state, 0);
                for (int j = 0; j != (arrby = this.state).length; ++j) {
                    int n3 = n2 + j;
                    byte by = arrby2[n3];
                    arrby2[n3] = (byte)(arrby[j] ^ by);
                }
            }
            return;
        }
        throw new IllegalArgumentException("iteration count must be at least 1.");
    }

    private byte[] generateDerivedKey(int n) {
        int n2 = this.hMac.getMacSize();
        int n3 = (n + n2 - 1) / n2;
        byte[] arrby = new byte[4];
        byte[] arrby2 = new byte[n3 * n2];
        KeyParameter keyParameter = new KeyParameter(this.password);
        this.hMac.init(keyParameter);
        int n4 = 0;
        for (n = 1; n <= n3; ++n) {
            int n5 = 3;
            do {
                byte by = (byte)(arrby[n5] + 1);
                arrby[n5] = by;
                if (by != 0) break;
                --n5;
            } while (true);
            this.F(this.salt, this.iterationCount, arrby, arrby2, n4);
            n4 += n2;
        }
        return arrby2;
    }

    @Override
    public CipherParameters generateDerivedMacParameters(int n) {
        return this.generateDerivedParameters(n);
    }

    @Override
    public CipherParameters generateDerivedParameters(int n) {
        return new KeyParameter(Arrays.copyOfRange(this.generateDerivedKey(n /= 8), 0, n), 0, n);
    }

    @Override
    public CipherParameters generateDerivedParameters(int n, int n2) {
        byte[] arrby = this.generateDerivedKey((n /= 8) + (n2 /= 8));
        return new ParametersWithIV(new KeyParameter(arrby, 0, n), arrby, n, n2);
    }
}

