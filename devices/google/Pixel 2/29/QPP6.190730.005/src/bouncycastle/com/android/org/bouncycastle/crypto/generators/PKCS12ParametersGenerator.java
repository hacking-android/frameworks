/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.generators;

import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.ExtendedDigest;
import com.android.org.bouncycastle.crypto.PBEParametersGenerator;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;

public class PKCS12ParametersGenerator
extends PBEParametersGenerator {
    public static final int IV_MATERIAL = 2;
    public static final int KEY_MATERIAL = 1;
    public static final int MAC_MATERIAL = 3;
    private Digest digest;
    private int u;
    private int v;

    public PKCS12ParametersGenerator(Digest digest) {
        this.digest = digest;
        if (digest instanceof ExtendedDigest) {
            this.u = digest.getDigestSize();
            this.v = ((ExtendedDigest)digest).getByteLength();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Digest ");
        stringBuilder.append(digest.getAlgorithmName());
        stringBuilder.append(" unsupported");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void adjust(byte[] arrby, int n, byte[] arrby2) {
        int n2 = (arrby2[arrby2.length - 1] & 255) + (arrby[arrby2.length + n - 1] & 255) + 1;
        arrby[arrby2.length + n - 1] = (byte)n2;
        int n3 = n2 >>> 8;
        for (n2 = arrby2.length - 2; n2 >= 0; --n2) {
            arrby[n + n2] = (byte)(n3 += (arrby2[n2] & 255) + (arrby[n + n2] & 255));
            n3 >>>= 8;
        }
    }

    private byte[] generateDerivedKey(int n, int n2) {
        byte[] arrby;
        int n3;
        byte[] arrby2;
        int n4;
        byte[] arrby3 = new byte[this.v];
        byte[] arrby4 = new byte[n2];
        for (n4 = 0; n4 != arrby3.length; ++n4) {
            arrby3[n4] = (byte)n;
        }
        if (this.salt != null && this.salt.length != 0) {
            n4 = this.v;
            n = this.salt.length;
            n3 = this.v;
            arrby = new byte[n4 * ((n + n3 - 1) / n3)];
            for (n = 0; n != arrby.length; ++n) {
                arrby[n] = this.salt[n % this.salt.length];
            }
        } else {
            arrby = new byte[]{};
        }
        if (this.password != null && this.password.length != 0) {
            n3 = this.v;
            n4 = this.password.length;
            n = this.v;
            arrby2 = new byte[n3 * ((n4 + n - 1) / n)];
            for (n = 0; n != arrby2.length; ++n) {
                arrby2[n] = this.password[n % this.password.length];
            }
        } else {
            arrby2 = new byte[]{};
        }
        byte[] arrby5 = new byte[arrby.length + arrby2.length];
        System.arraycopy((byte[])arrby, (int)0, (byte[])arrby5, (int)0, (int)arrby.length);
        System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby5, (int)arrby.length, (int)arrby2.length);
        arrby2 = new byte[this.v];
        n = this.u;
        n4 = (n2 + n - 1) / n;
        arrby = new byte[n];
        for (n = 1; n <= n4; ++n) {
            int n5;
            this.digest.update(arrby3, 0, arrby3.length);
            this.digest.update(arrby5, 0, arrby5.length);
            this.digest.doFinal(arrby, 0);
            for (n2 = 1; n2 < this.iterationCount; ++n2) {
                this.digest.update(arrby, 0, arrby.length);
                this.digest.doFinal(arrby, 0);
            }
            for (n2 = 0; n2 != arrby2.length; ++n2) {
                arrby2[n2] = arrby[n2 % arrby.length];
            }
            for (n2 = 0; n2 != (n3 = arrby5.length) / (n5 = this.v); ++n2) {
                this.adjust(arrby5, n5 * n2, arrby2);
            }
            if (n == n4) {
                n2 = this.u;
                System.arraycopy((byte[])arrby, (int)0, (byte[])arrby4, (int)((n - 1) * n2), (int)(arrby4.length - (n - 1) * n2));
                continue;
            }
            System.arraycopy((byte[])arrby, (int)0, (byte[])arrby4, (int)((n - 1) * this.u), (int)arrby.length);
        }
        return arrby4;
    }

    @Override
    public CipherParameters generateDerivedMacParameters(int n) {
        return new KeyParameter(this.generateDerivedKey(3, n /= 8), 0, n);
    }

    @Override
    public CipherParameters generateDerivedParameters(int n) {
        return new KeyParameter(this.generateDerivedKey(1, n /= 8), 0, n);
    }

    @Override
    public CipherParameters generateDerivedParameters(int n, int n2) {
        byte[] arrby = this.generateDerivedKey(1, n /= 8);
        byte[] arrby2 = this.generateDerivedKey(2, n2 /= 8);
        return new ParametersWithIV(new KeyParameter(arrby, 0, n), arrby2, 0, n2);
    }
}

