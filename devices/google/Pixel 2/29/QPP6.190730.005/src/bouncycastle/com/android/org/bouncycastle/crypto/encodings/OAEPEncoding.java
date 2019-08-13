/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.encodings;

import com.android.org.bouncycastle.crypto.AsymmetricBlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactory;
import com.android.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.org.bouncycastle.util.Arrays;
import java.security.SecureRandom;

public class OAEPEncoding
implements AsymmetricBlockCipher {
    private byte[] defHash;
    private AsymmetricBlockCipher engine;
    private boolean forEncryption;
    private Digest mgf1Hash;
    private SecureRandom random;

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher) {
        this(asymmetricBlockCipher, AndroidDigestFactory.getSHA1(), null);
    }

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest) {
        this(asymmetricBlockCipher, digest, null);
    }

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, Digest digest2, byte[] arrby) {
        this.engine = asymmetricBlockCipher;
        this.mgf1Hash = digest2;
        this.defHash = new byte[digest.getDigestSize()];
        digest.reset();
        if (arrby != null) {
            digest.update(arrby, 0, arrby.length);
        }
        digest.doFinal(this.defHash, 0);
    }

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, byte[] arrby) {
        this(asymmetricBlockCipher, digest, digest, arrby);
    }

    private void ItoOSP(int n, byte[] arrby) {
        arrby[0] = (byte)(n >>> 24);
        arrby[1] = (byte)(n >>> 16);
        arrby[2] = (byte)(n >>> 8);
        arrby[3] = (byte)(n >>> 0);
    }

    private byte[] maskGeneratorFunction1(byte[] arrby, int n, int n2, int n3) {
        int n4;
        byte[] arrby2 = new byte[n3];
        byte[] arrby3 = new byte[this.mgf1Hash.getDigestSize()];
        byte[] arrby4 = new byte[4];
        this.mgf1Hash.reset();
        for (n4 = 0; n4 < n3 / arrby3.length; ++n4) {
            this.ItoOSP(n4, arrby4);
            this.mgf1Hash.update(arrby, n, n2);
            this.mgf1Hash.update(arrby4, 0, arrby4.length);
            this.mgf1Hash.doFinal(arrby3, 0);
            System.arraycopy((byte[])arrby3, (int)0, (byte[])arrby2, (int)(arrby3.length * n4), (int)arrby3.length);
        }
        if (arrby3.length * n4 < n3) {
            this.ItoOSP(n4, arrby4);
            this.mgf1Hash.update(arrby, n, n2);
            this.mgf1Hash.update(arrby4, 0, arrby4.length);
            this.mgf1Hash.doFinal(arrby3, 0);
            System.arraycopy((byte[])arrby3, (int)0, (byte[])arrby2, (int)(arrby3.length * n4), (int)(arrby2.length - arrby3.length * n4));
        }
        return arrby2;
    }

    public byte[] decodeBlock(byte[] arrby, int n, int n2) throws InvalidCipherTextException {
        int n3;
        byte[] arrby2 = this.engine.processBlock(arrby, n, n2);
        arrby = new byte[this.engine.getOutputBlockSize()];
        n = arrby.length < this.defHash.length * 2 + 1 ? 1 : 0;
        if (arrby2.length <= arrby.length) {
            System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)(arrby.length - arrby2.length), (int)arrby2.length);
            n2 = n;
        } else {
            System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)0, (int)arrby.length);
            n2 = 1;
        }
        arrby2 = this.defHash;
        byte[] arrby3 = this.maskGeneratorFunction1(arrby, arrby2.length, arrby.length - arrby2.length, arrby2.length);
        for (n = 0; n != (arrby2 = this.defHash).length; ++n) {
            arrby[n] = (byte)(arrby[n] ^ arrby3[n]);
        }
        arrby2 = this.maskGeneratorFunction1(arrby, 0, arrby2.length, arrby.length - arrby2.length);
        for (n = this.defHash.length; n != arrby.length; ++n) {
            arrby[n] = (byte)(arrby[n] ^ arrby2[n - this.defHash.length]);
        }
        int n4 = 0;
        for (n = 0; n != (arrby2 = this.defHash).length; ++n) {
            if (arrby2[n] == arrby[arrby2.length + n]) continue;
            n4 = 1;
        }
        int n5 = arrby.length;
        for (n = arrby2.length * 2; n != arrby.length; ++n) {
            boolean bl;
            n3 = arrby[n] != 0 ? 1 : 0;
            if (!(n3 & (bl = n5 == arrby.length))) continue;
            n5 = n;
        }
        n = n5 > arrby.length - 1 ? 1 : 0;
        n3 = arrby[n5] != 1 ? 1 : 0;
        ++n5;
        if ((n4 | n2 | (n | n3)) == 0) {
            arrby2 = new byte[arrby.length - n5];
            System.arraycopy((byte[])arrby, (int)n5, (byte[])arrby2, (int)0, (int)arrby2.length);
            return arrby2;
        }
        Arrays.fill(arrby, (byte)0);
        throw new InvalidCipherTextException("data wrong");
    }

    public byte[] encodeBlock(byte[] arrby, int n, int n2) throws InvalidCipherTextException {
        if (n2 <= this.getInputBlockSize()) {
            byte[] arrby2 = new byte[this.getInputBlockSize() + 1 + this.defHash.length * 2];
            System.arraycopy((byte[])arrby, (int)n, (byte[])arrby2, (int)(arrby2.length - n2), (int)n2);
            arrby2[arrby2.length - n2 - 1] = (byte)(true ? 1 : 0);
            arrby = this.defHash;
            System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)arrby.length, (int)arrby.length);
            byte[] arrby3 = new byte[this.defHash.length];
            this.random.nextBytes(arrby3);
            arrby = this.maskGeneratorFunction1(arrby3, 0, arrby3.length, arrby2.length - this.defHash.length);
            for (n = this.defHash.length; n != arrby2.length; ++n) {
                arrby2[n] = (byte)(arrby2[n] ^ arrby[n - this.defHash.length]);
            }
            System.arraycopy((byte[])arrby3, (int)0, (byte[])arrby2, (int)0, (int)this.defHash.length);
            arrby = this.defHash;
            arrby = this.maskGeneratorFunction1(arrby2, arrby.length, arrby2.length - arrby.length, arrby.length);
            for (n = 0; n != this.defHash.length; ++n) {
                arrby2[n] = (byte)(arrby2[n] ^ arrby[n]);
            }
            return this.engine.processBlock(arrby2, 0, arrby2.length);
        }
        throw new DataLengthException("input data too long");
    }

    @Override
    public int getInputBlockSize() {
        int n = this.engine.getInputBlockSize();
        if (this.forEncryption) {
            return n - 1 - this.defHash.length * 2;
        }
        return n;
    }

    @Override
    public int getOutputBlockSize() {
        int n = this.engine.getOutputBlockSize();
        if (this.forEncryption) {
            return n;
        }
        return n - 1 - this.defHash.length * 2;
    }

    public AsymmetricBlockCipher getUnderlyingCipher() {
        return this.engine;
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) {
        this.random = cipherParameters instanceof ParametersWithRandom ? ((ParametersWithRandom)cipherParameters).getRandom() : CryptoServicesRegistrar.getSecureRandom();
        this.engine.init(bl, cipherParameters);
        this.forEncryption = bl;
    }

    @Override
    public byte[] processBlock(byte[] arrby, int n, int n2) throws InvalidCipherTextException {
        if (this.forEncryption) {
            return this.encodeBlock(arrby, n, n2);
        }
        return this.decodeBlock(arrby, n, n2);
    }
}

