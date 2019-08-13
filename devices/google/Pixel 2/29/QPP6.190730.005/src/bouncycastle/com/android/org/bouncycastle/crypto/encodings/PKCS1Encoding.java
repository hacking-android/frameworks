/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.encodings;

import com.android.org.bouncycastle.crypto.AsymmetricBlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.CryptoServicesRegistrar;
import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.ParametersWithRandom;
import com.android.org.bouncycastle.util.Arrays;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.SecureRandom;

public class PKCS1Encoding
implements AsymmetricBlockCipher {
    private static final int HEADER_LENGTH = 10;
    public static final String NOT_STRICT_LENGTH_ENABLED_PROPERTY = "com.android.org.bouncycastle.pkcs1.not_strict";
    public static final String STRICT_LENGTH_ENABLED_PROPERTY = "com.android.org.bouncycastle.pkcs1.strict";
    private byte[] blockBuffer;
    private AsymmetricBlockCipher engine;
    private byte[] fallback = null;
    private boolean forEncryption;
    private boolean forPrivateKey;
    private int pLen = -1;
    private SecureRandom random;
    private boolean useStrictLength;

    public PKCS1Encoding(AsymmetricBlockCipher asymmetricBlockCipher) {
        this.engine = asymmetricBlockCipher;
        this.useStrictLength = this.useStrict();
    }

    public PKCS1Encoding(AsymmetricBlockCipher asymmetricBlockCipher, int n) {
        this.engine = asymmetricBlockCipher;
        this.useStrictLength = this.useStrict();
        this.pLen = n;
    }

    public PKCS1Encoding(AsymmetricBlockCipher asymmetricBlockCipher, byte[] arrby) {
        this.engine = asymmetricBlockCipher;
        this.useStrictLength = this.useStrict();
        this.fallback = arrby;
        this.pLen = arrby.length;
    }

    private static int checkPkcs1Encoding(byte[] arrby, int n) {
        int n2 = 0 | arrby[0] ^ 2;
        int n3 = arrby.length;
        for (int i = 1; i < n3 - (n + 1); ++i) {
            int n4 = arrby[i];
            n4 |= n4 >> 1;
            n4 |= n4 >> 2;
            n2 |= ((n4 | n4 >> 4) & 1) - 1;
        }
        n = n2 | arrby[arrby.length - (n + 1)];
        n |= n >> 1;
        n |= n >> 2;
        return ((n | n >> 4) & 1) - 1;
    }

    private byte[] decodeBlock(byte[] arrby, int n, int n2) throws InvalidCipherTextException {
        if (this.pLen != -1) {
            return this.decodeBlockOrRandom(arrby, n, n2);
        }
        arrby = this.engine.processBlock(arrby, n, n2);
        int n3 = this.useStrictLength;
        n = arrby.length;
        n2 = this.engine.getOutputBlockSize();
        int n4 = 1;
        n2 = n != n2 ? 1 : 0;
        if (arrby.length < this.getOutputBlockSize()) {
            arrby = this.blockBuffer;
        }
        byte by = arrby[0];
        n = this.forPrivateKey ? (by != 2 ? 1 : 0) : (by != 1 ? 1 : 0);
        int n5 = this.findStart(by, arrby) + 1;
        if (n5 >= 10) {
            n4 = 0;
        }
        if ((n4 | n) == 0) {
            if ((n3 & n2) == 0) {
                byte[] arrby2 = new byte[arrby.length - n5];
                System.arraycopy((byte[])arrby, (int)n5, (byte[])arrby2, (int)0, (int)arrby2.length);
                return arrby2;
            }
            Arrays.fill(arrby, (byte)0);
            throw new InvalidCipherTextException("block incorrect size");
        }
        Arrays.fill(arrby, (byte)0);
        throw new InvalidCipherTextException("block incorrect");
    }

    private byte[] decodeBlockOrRandom(byte[] arrby, int n, int n2) throws InvalidCipherTextException {
        if (this.forPrivateKey) {
            int n3;
            byte[] arrby2 = this.engine.processBlock(arrby, n, n2);
            if (this.fallback == null) {
                arrby = new byte[this.pLen];
                this.random.nextBytes(arrby);
            } else {
                arrby = this.fallback;
            }
            int n4 = this.useStrictLength;
            n = arrby2.length != this.engine.getOutputBlockSize() ? 1 : 0;
            if ((n4 & n) != 0) {
                arrby2 = this.blockBuffer;
            }
            n2 = PKCS1Encoding.checkPkcs1Encoding(arrby2, this.pLen);
            byte[] arrby3 = new byte[this.pLen];
            for (n = 0; n < (n3 = this.pLen); ++n) {
                arrby3[n] = (byte)(arrby2[arrby2.length - n3 + n] & n2 | arrby[n] & n2);
            }
            Arrays.fill(arrby2, (byte)0);
            return arrby3;
        }
        throw new InvalidCipherTextException("sorry, this method is only for decryption, not for signing");
    }

    private byte[] encodeBlock(byte[] arrby, int n, int n2) throws InvalidCipherTextException {
        if (n2 <= this.getInputBlockSize()) {
            byte[] arrby2 = new byte[this.engine.getInputBlockSize()];
            if (this.forPrivateKey) {
                arrby2[0] = (byte)(true ? 1 : 0);
                for (int i = 1; i != arrby2.length - n2 - 1; ++i) {
                    arrby2[i] = (byte)-1;
                }
            } else {
                this.random.nextBytes(arrby2);
                arrby2[0] = (byte)2;
                for (int i = 1; i != arrby2.length - n2 - 1; ++i) {
                    while (arrby2[i] == 0) {
                        arrby2[i] = (byte)this.random.nextInt();
                    }
                }
            }
            arrby2[arrby2.length - n2 - 1] = (byte)(false ? 1 : 0);
            System.arraycopy((byte[])arrby, (int)n, (byte[])arrby2, (int)(arrby2.length - n2), (int)n2);
            return this.engine.processBlock(arrby2, 0, arrby2.length);
        }
        throw new IllegalArgumentException("input data too large");
    }

    private int findStart(byte by, byte[] arrby) throws InvalidCipherTextException {
        int n = -1;
        boolean bl = false;
        for (int i = 1; i != arrby.length; ++i) {
            boolean bl2;
            byte by2 = arrby[i];
            boolean bl3 = false;
            boolean bl4 = by2 == 0;
            if (bl4 & (bl2 = n < 0)) {
                n = i;
            }
            bl4 = by == 1;
            bl2 = n < 0;
            if (by2 != -1) {
                bl3 = true;
            }
            bl |= bl4 & bl2 & bl3;
        }
        if (bl) {
            return -1;
        }
        return n;
    }

    private boolean useStrict() {
        String string = (String)AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                return System.getProperty(PKCS1Encoding.STRICT_LENGTH_ENABLED_PROPERTY);
            }
        });
        String string2 = (String)AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                return System.getProperty(PKCS1Encoding.NOT_STRICT_LENGTH_ENABLED_PROPERTY);
            }
        });
        boolean bl = true;
        if (string2 != null) {
            return true ^ string2.equals("true");
        }
        boolean bl2 = bl;
        if (string != null) {
            bl2 = string.equals("true") ? bl : false;
        }
        return bl2;
    }

    @Override
    public int getInputBlockSize() {
        int n = this.engine.getInputBlockSize();
        if (this.forEncryption) {
            return n - 10;
        }
        return n;
    }

    @Override
    public int getOutputBlockSize() {
        int n = this.engine.getOutputBlockSize();
        if (this.forEncryption) {
            return n;
        }
        return n - 10;
    }

    public AsymmetricBlockCipher getUnderlyingCipher() {
        return this.engine;
    }

    @Override
    public void init(boolean bl, CipherParameters cipherParameters) {
        CipherParameters cipherParameters2;
        if (cipherParameters instanceof ParametersWithRandom) {
            cipherParameters2 = (ParametersWithRandom)cipherParameters;
            this.random = ((ParametersWithRandom)cipherParameters2).getRandom();
            cipherParameters2 = (AsymmetricKeyParameter)((ParametersWithRandom)cipherParameters2).getParameters();
        } else {
            AsymmetricKeyParameter asymmetricKeyParameter = (AsymmetricKeyParameter)cipherParameters;
            cipherParameters2 = asymmetricKeyParameter;
            if (!asymmetricKeyParameter.isPrivate()) {
                cipherParameters2 = asymmetricKeyParameter;
                if (bl) {
                    this.random = CryptoServicesRegistrar.getSecureRandom();
                    cipherParameters2 = asymmetricKeyParameter;
                }
            }
        }
        this.engine.init(bl, cipherParameters);
        this.forPrivateKey = ((AsymmetricKeyParameter)cipherParameters2).isPrivate();
        this.forEncryption = bl;
        this.blockBuffer = new byte[this.engine.getOutputBlockSize()];
        if (this.pLen > 0 && this.fallback == null && this.random == null) {
            throw new IllegalArgumentException("encoder requires random");
        }
    }

    @Override
    public byte[] processBlock(byte[] arrby, int n, int n2) throws InvalidCipherTextException {
        if (this.forEncryption) {
            return this.encodeBlock(arrby, n, n2);
        }
        return this.decodeBlock(arrby, n, n2);
    }

}

