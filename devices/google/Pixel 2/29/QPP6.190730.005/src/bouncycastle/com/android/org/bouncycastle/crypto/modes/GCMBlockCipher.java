/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.modes;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.InvalidCipherTextException;
import com.android.org.bouncycastle.crypto.OutputLengthException;
import com.android.org.bouncycastle.crypto.modes.AEADBlockCipher;
import com.android.org.bouncycastle.crypto.modes.gcm.BasicGCMExponentiator;
import com.android.org.bouncycastle.crypto.modes.gcm.GCMExponentiator;
import com.android.org.bouncycastle.crypto.modes.gcm.GCMMultiplier;
import com.android.org.bouncycastle.crypto.modes.gcm.GCMUtil;
import com.android.org.bouncycastle.crypto.modes.gcm.Tables4kGCMMultiplier;
import com.android.org.bouncycastle.crypto.params.AEADParameters;
import com.android.org.bouncycastle.crypto.params.KeyParameter;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Pack;

public class GCMBlockCipher
implements AEADBlockCipher {
    private static final int BLOCK_SIZE = 16;
    private static final long MAX_INPUT_SIZE = 68719476704L;
    private byte[] H;
    private byte[] J0;
    private byte[] S;
    private byte[] S_at;
    private byte[] S_atPre;
    private byte[] atBlock;
    private int atBlockPos;
    private long atLength;
    private long atLengthPre;
    private int blocksRemaining;
    private byte[] bufBlock;
    private int bufOff;
    private BlockCipher cipher;
    private byte[] counter;
    private GCMExponentiator exp;
    private boolean forEncryption;
    private byte[] initialAssociatedText;
    private boolean initialised;
    private byte[] lastKey;
    private byte[] macBlock;
    private int macSize;
    private GCMMultiplier multiplier;
    private byte[] nonce;
    private long totalLength;

    public GCMBlockCipher(BlockCipher blockCipher) {
        this(blockCipher, null);
    }

    public GCMBlockCipher(BlockCipher blockCipher, GCMMultiplier gCMMultiplier) {
        if (blockCipher.getBlockSize() == 16) {
            GCMMultiplier gCMMultiplier2 = gCMMultiplier;
            if (gCMMultiplier == null) {
                gCMMultiplier2 = new Tables4kGCMMultiplier();
            }
            this.cipher = blockCipher;
            this.multiplier = gCMMultiplier2;
            return;
        }
        throw new IllegalArgumentException("cipher required with a block size of 16.");
    }

    private void checkStatus() {
        if (!this.initialised) {
            if (this.forEncryption) {
                throw new IllegalStateException("GCM cipher cannot be reused for encryption");
            }
            throw new IllegalStateException("GCM cipher needs to be initialised");
        }
    }

    private void gHASH(byte[] arrby, byte[] arrby2, int n) {
        for (int i = 0; i < n; i += 16) {
            this.gHASHPartial(arrby, arrby2, i, Math.min(n - i, 16));
        }
    }

    private void gHASHBlock(byte[] arrby, byte[] arrby2) {
        GCMUtil.xor(arrby, arrby2);
        this.multiplier.multiplyH(arrby);
    }

    private void gHASHBlock(byte[] arrby, byte[] arrby2, int n) {
        GCMUtil.xor(arrby, arrby2, n);
        this.multiplier.multiplyH(arrby);
    }

    private void gHASHPartial(byte[] arrby, byte[] arrby2, int n, int n2) {
        GCMUtil.xor(arrby, arrby2, n, n2);
        this.multiplier.multiplyH(arrby);
    }

    private void getNextCTRBlock(byte[] arrby) {
        int n = this.blocksRemaining;
        if (n != 0) {
            this.blocksRemaining = n - 1;
            byte[] arrby2 = this.counter;
            n = 1 + (arrby2[15] & 255);
            arrby2[15] = (byte)n;
            n = (n >>> 8) + (arrby2[14] & 255);
            arrby2[14] = (byte)n;
            n = (n >>> 8) + (arrby2[13] & 255);
            arrby2[13] = (byte)n;
            arrby2[12] = (byte)((n >>> 8) + (arrby2[12] & 255));
            this.cipher.processBlock(arrby2, 0, arrby, 0);
            return;
        }
        throw new IllegalStateException("Attempt to process too many blocks");
    }

    private long getTotalInputSizeAfterNewInput(int n) {
        return this.totalLength + (long)n + (long)this.bufOff;
    }

    private void initCipher() {
        int n;
        if (this.atLength > 0L) {
            System.arraycopy((byte[])this.S_at, (int)0, (byte[])this.S_atPre, (int)0, (int)16);
            this.atLengthPre = this.atLength;
        }
        if ((n = this.atBlockPos) > 0) {
            this.gHASHPartial(this.S_atPre, this.atBlock, 0, n);
            this.atLengthPre += (long)this.atBlockPos;
        }
        if (this.atLengthPre > 0L) {
            System.arraycopy((byte[])this.S_atPre, (int)0, (byte[])this.S, (int)0, (int)16);
        }
    }

    private void processBlock(byte[] arrby, int n, byte[] arrby2, int n2) {
        if (arrby2.length - n2 >= 16) {
            if (this.totalLength == 0L) {
                this.initCipher();
            }
            byte[] arrby3 = new byte[16];
            this.getNextCTRBlock(arrby3);
            if (this.forEncryption) {
                GCMUtil.xor(arrby3, arrby, n);
                this.gHASHBlock(this.S, arrby3);
                System.arraycopy((byte[])arrby3, (int)0, (byte[])arrby2, (int)n2, (int)16);
            } else {
                this.gHASHBlock(this.S, arrby, n);
                GCMUtil.xor(arrby3, 0, arrby, n, arrby2, n2);
            }
            this.totalLength += 16L;
            return;
        }
        throw new OutputLengthException("Output buffer too short");
    }

    private void processPartial(byte[] arrby, int n, int n2, byte[] arrby2, int n3) {
        byte[] arrby3 = new byte[16];
        this.getNextCTRBlock(arrby3);
        if (this.forEncryption) {
            GCMUtil.xor(arrby, n, arrby3, 0, n2);
            this.gHASHPartial(this.S, arrby, n, n2);
        } else {
            this.gHASHPartial(this.S, arrby, n, n2);
            GCMUtil.xor(arrby, n, arrby3, 0, n2);
        }
        System.arraycopy((byte[])arrby, (int)n, (byte[])arrby2, (int)n3, (int)n2);
        this.totalLength += (long)n2;
    }

    private void reset(boolean bl) {
        this.cipher.reset();
        this.S = new byte[16];
        this.S_at = new byte[16];
        this.S_atPre = new byte[16];
        this.atBlock = new byte[16];
        this.atBlockPos = 0;
        this.atLength = 0L;
        this.atLengthPre = 0L;
        this.counter = Arrays.clone(this.J0);
        this.blocksRemaining = -2;
        this.bufOff = 0;
        this.totalLength = 0L;
        byte[] arrby = this.bufBlock;
        if (arrby != null) {
            Arrays.fill(arrby, (byte)0);
        }
        if (bl) {
            this.macBlock = null;
        }
        if (this.forEncryption) {
            this.initialised = false;
        } else {
            arrby = this.initialAssociatedText;
            if (arrby != null) {
                this.processAADBytes(arrby, 0, arrby.length);
            }
        }
    }

    @Override
    public int doFinal(byte[] arrby, int n) throws IllegalStateException, InvalidCipherTextException {
        block13 : {
            block14 : {
                block17 : {
                    int n2;
                    block16 : {
                        int n3;
                        block15 : {
                            byte[] arrby2;
                            block12 : {
                                block11 : {
                                    this.checkStatus();
                                    if (this.totalLength == 0L) {
                                        this.initCipher();
                                    }
                                    n3 = this.bufOff;
                                    if (!this.forEncryption) break block11;
                                    if (arrby.length - n < this.macSize + n3) {
                                        throw new OutputLengthException("Output buffer too short");
                                    }
                                    break block12;
                                }
                                n2 = this.macSize;
                                if (n3 < n2) break block13;
                                if (arrby.length - n < (n3 -= n2)) break block14;
                            }
                            if (n3 > 0) {
                                this.processPartial(this.bufBlock, 0, n3, arrby, n);
                            }
                            long l = this.atLength;
                            n2 = this.atBlockPos;
                            this.atLength = l + (long)n2;
                            if (this.atLength > this.atLengthPre) {
                                if (n2 > 0) {
                                    this.gHASHPartial(this.S_at, this.atBlock, 0, n2);
                                }
                                if (this.atLengthPre > 0L) {
                                    GCMUtil.xor(this.S_at, this.S_atPre);
                                }
                                l = this.totalLength;
                                arrby2 = new byte[16];
                                if (this.exp == null) {
                                    this.exp = new BasicGCMExponentiator();
                                    this.exp.init(this.H);
                                }
                                this.exp.exponentiateX(l * 8L + 127L >>> 7, arrby2);
                                GCMUtil.multiply(this.S_at, arrby2);
                                GCMUtil.xor(this.S, this.S_at);
                            }
                            arrby2 = new byte[16];
                            Pack.longToBigEndian(this.atLength * 8L, arrby2, 0);
                            Pack.longToBigEndian(this.totalLength * 8L, arrby2, 8);
                            this.gHASHBlock(this.S, arrby2);
                            arrby2 = new byte[16];
                            this.cipher.processBlock(this.J0, 0, arrby2, 0);
                            GCMUtil.xor(arrby2, this.S);
                            n2 = n3;
                            int n4 = this.macSize;
                            this.macBlock = new byte[n4];
                            System.arraycopy((byte[])arrby2, (int)0, (byte[])this.macBlock, (int)0, (int)n4);
                            if (!this.forEncryption) break block15;
                            System.arraycopy((byte[])this.macBlock, (int)0, (byte[])arrby, (int)(this.bufOff + n), (int)this.macSize);
                            n2 += this.macSize;
                            break block16;
                        }
                        n = this.macSize;
                        arrby = new byte[n];
                        System.arraycopy((byte[])this.bufBlock, (int)n3, (byte[])arrby, (int)0, (int)n);
                        if (!Arrays.constantTimeAreEqual(this.macBlock, arrby)) break block17;
                    }
                    this.reset(false);
                    return n2;
                }
                throw new InvalidCipherTextException("mac check in GCM failed");
            }
            throw new OutputLengthException("Output buffer too short");
        }
        throw new InvalidCipherTextException("data too short");
    }

    @Override
    public String getAlgorithmName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.cipher.getAlgorithmName());
        stringBuilder.append("/GCM");
        return stringBuilder.toString();
    }

    @Override
    public byte[] getMac() {
        byte[] arrby = this.macBlock;
        if (arrby == null) {
            return new byte[this.macSize];
        }
        return Arrays.clone(arrby);
    }

    @Override
    public int getOutputSize(int n) {
        int n2 = this.bufOff + n;
        if (this.forEncryption) {
            return this.macSize + n2;
        }
        n = this.macSize;
        n = n2 < n ? 0 : n2 - n;
        return n;
    }

    @Override
    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    @Override
    public int getUpdateOutputSize(int n) {
        int n2;
        n = n2 = this.bufOff + n;
        if (!this.forEncryption) {
            n = this.macSize;
            if (n2 < n) {
                return 0;
            }
            n = n2 - n;
        }
        return n - n % 16;
    }

    @Override
    public void init(boolean bl, CipherParameters object) throws IllegalArgumentException {
        block15 : {
            block16 : {
                block19 : {
                    block18 : {
                        block17 : {
                            byte[] arrby;
                            int n;
                            Object object2;
                            block14 : {
                                block12 : {
                                    block13 : {
                                        this.forEncryption = bl;
                                        this.macBlock = null;
                                        this.initialised = true;
                                        if (!(object instanceof AEADParameters)) break block12;
                                        object2 = (AEADParameters)object;
                                        object = ((AEADParameters)object2).getNonce();
                                        this.initialAssociatedText = ((AEADParameters)object2).getAssociatedText();
                                        n = ((AEADParameters)object2).getMacSize();
                                        if (n < 32 || n > 128 || n % 8 != 0) break block13;
                                        this.macSize = n / 8;
                                        object2 = ((AEADParameters)object2).getKey();
                                        break block14;
                                    }
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("Invalid value for MAC size: ");
                                    ((StringBuilder)object).append(n);
                                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                                }
                                if (!(object instanceof ParametersWithIV)) break block15;
                                object2 = (ParametersWithIV)object;
                                object = ((ParametersWithIV)object2).getIV();
                                this.initialAssociatedText = null;
                                this.macSize = 16;
                                object2 = (KeyParameter)((ParametersWithIV)object2).getParameters();
                            }
                            n = bl ? 16 : this.macSize + 16;
                            this.bufBlock = new byte[n];
                            if (object == null || ((Object)object).length < 1) break block16;
                            if (bl && (arrby = this.nonce) != null && Arrays.areEqual(arrby, (byte[])object)) {
                                if (object2 != null) {
                                    arrby = this.lastKey;
                                    if (arrby != null && Arrays.areEqual(arrby, ((KeyParameter)object2).getKey())) {
                                        throw new IllegalArgumentException("cannot reuse nonce for GCM encryption");
                                    }
                                } else {
                                    throw new IllegalArgumentException("cannot reuse nonce for GCM encryption");
                                }
                            }
                            this.nonce = object;
                            if (object2 != null) {
                                this.lastKey = ((KeyParameter)object2).getKey();
                            }
                            if (object2 == null) break block17;
                            this.cipher.init(true, (CipherParameters)object2);
                            this.H = new byte[16];
                            object2 = this.cipher;
                            object = this.H;
                            object2.processBlock((byte[])object, 0, (byte[])object, 0);
                            this.multiplier.init(this.H);
                            this.exp = null;
                            break block18;
                        }
                        if (this.H == null) break block19;
                    }
                    this.J0 = new byte[16];
                    object = this.nonce;
                    if (((Object)object).length == 12) {
                        System.arraycopy((byte[])object, (int)0, (byte[])this.J0, (int)0, (int)((Object)object).length);
                        this.J0[15] = (byte)(true ? 1 : 0);
                    } else {
                        this.gHASH(this.J0, (byte[])object, ((Object)object).length);
                        object = new byte[16];
                        Pack.longToBigEndian((long)this.nonce.length * 8L, (byte[])object, 8);
                        this.gHASHBlock(this.J0, (byte[])object);
                    }
                    this.S = new byte[16];
                    this.S_at = new byte[16];
                    this.S_atPre = new byte[16];
                    this.atBlock = new byte[16];
                    this.atBlockPos = 0;
                    this.atLength = 0L;
                    this.atLengthPre = 0L;
                    this.counter = Arrays.clone(this.J0);
                    this.blocksRemaining = -2;
                    this.bufOff = 0;
                    this.totalLength = 0L;
                    object = this.initialAssociatedText;
                    if (object != null) {
                        this.processAADBytes((byte[])object, 0, ((Object)object).length);
                    }
                    return;
                }
                throw new IllegalArgumentException("Key must be specified in initial init");
            }
            throw new IllegalArgumentException("IV must be at least 1 byte");
        }
        throw new IllegalArgumentException("invalid parameters passed to GCM");
    }

    @Override
    public void processAADByte(byte by) {
        this.checkStatus();
        if (this.getTotalInputSizeAfterNewInput(1) <= 68719476704L) {
            byte[] arrby = this.atBlock;
            int n = this.atBlockPos;
            arrby[n] = by;
            by = (byte)(n + 1);
            this.atBlockPos = by;
            if (by == 16) {
                this.gHASHBlock(this.S_at, arrby);
                this.atBlockPos = 0;
                this.atLength += 16L;
            }
            return;
        }
        throw new DataLengthException("Input exceeded 68719476704 bytes");
    }

    @Override
    public void processAADBytes(byte[] arrby, int n, int n2) {
        this.checkStatus();
        if (this.getTotalInputSizeAfterNewInput(n2) <= 68719476704L) {
            for (int i = 0; i < n2; ++i) {
                byte[] arrby2 = this.atBlock;
                int n3 = this.atBlockPos;
                arrby2[n3] = arrby[n + i];
                this.atBlockPos = ++n3;
                if (n3 != 16) continue;
                this.gHASHBlock(this.S_at, arrby2);
                this.atBlockPos = 0;
                this.atLength += 16L;
            }
            return;
        }
        throw new DataLengthException("Input exceeded 68719476704 bytes");
    }

    @Override
    public int processByte(byte by, byte[] arrby, int n) throws DataLengthException {
        this.checkStatus();
        if (this.getTotalInputSizeAfterNewInput(1) <= 68719476704L) {
            byte[] arrby2 = this.bufBlock;
            int n2 = this.bufOff;
            arrby2[n2] = by;
            by = (byte)(n2 + 1);
            this.bufOff = by;
            if (by == arrby2.length) {
                this.processBlock(arrby2, 0, arrby, n);
                if (this.forEncryption) {
                    this.bufOff = 0;
                } else {
                    arrby = this.bufBlock;
                    System.arraycopy((byte[])arrby, (int)16, (byte[])arrby, (int)0, (int)this.macSize);
                    this.bufOff = this.macSize;
                }
                return 16;
            }
            return 0;
        }
        throw new DataLengthException("Input exceeded 68719476704 bytes");
    }

    @Override
    public int processBytes(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws DataLengthException {
        this.checkStatus();
        if (this.getTotalInputSizeAfterNewInput(n2) <= 68719476704L) {
            if (arrby.length - n >= n2) {
                int n4 = 0;
                int n5 = 0;
                if (this.forEncryption) {
                    n4 = n5;
                    int n6 = n;
                    int n7 = n2;
                    if (this.bufOff != 0) {
                        do {
                            n4 = n5;
                            n6 = n;
                            n7 = n2;
                            if (n2 <= 0) break;
                            --n2;
                            byte[] arrby3 = this.bufBlock;
                            n6 = this.bufOff;
                            n4 = n + 1;
                            arrby3[n6] = arrby[n];
                            this.bufOff = n = n6 + 1;
                            if (n == 16) {
                                this.processBlock(arrby3, 0, arrby2, n3);
                                this.bufOff = 0;
                                n = 0 + 16;
                                n6 = n4;
                                n4 = n;
                                n7 = n2;
                                break;
                            }
                            n = n4;
                        } while (true);
                    }
                    while (n7 >= 16) {
                        this.processBlock(arrby, n6, arrby2, n3 + n4);
                        n6 += 16;
                        n7 -= 16;
                        n4 += 16;
                    }
                    n5 = n4;
                    if (n7 > 0) {
                        System.arraycopy((byte[])arrby, (int)n6, (byte[])this.bufBlock, (int)0, (int)n7);
                        this.bufOff = n7;
                        n5 = n4;
                    }
                } else {
                    int n8 = 0;
                    do {
                        n5 = n4;
                        if (n8 >= n2) break;
                        byte[] arrby4 = this.bufBlock;
                        int n9 = this.bufOff;
                        arrby4[n9] = arrby[n + n8];
                        this.bufOff = n5 = n9 + 1;
                        n9 = n4;
                        if (n5 == arrby4.length) {
                            this.processBlock(arrby4, 0, arrby2, n3 + n4);
                            arrby4 = this.bufBlock;
                            System.arraycopy((byte[])arrby4, (int)16, (byte[])arrby4, (int)0, (int)this.macSize);
                            this.bufOff = this.macSize;
                            n9 = n4 + 16;
                        }
                        ++n8;
                        n4 = n9;
                    } while (true);
                }
                return n5;
            }
            throw new DataLengthException("Input buffer too short");
        }
        throw new DataLengthException("Input exceeded 68719476704 bytes");
    }

    @Override
    public void reset() {
        this.reset(true);
    }
}

