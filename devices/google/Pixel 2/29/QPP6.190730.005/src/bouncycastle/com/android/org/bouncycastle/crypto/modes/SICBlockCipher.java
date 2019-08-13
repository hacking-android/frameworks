/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.modes;

import com.android.org.bouncycastle.crypto.BlockCipher;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DataLengthException;
import com.android.org.bouncycastle.crypto.SkippingStreamCipher;
import com.android.org.bouncycastle.crypto.StreamBlockCipher;
import com.android.org.bouncycastle.crypto.params.ParametersWithIV;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Pack;

public class SICBlockCipher
extends StreamBlockCipher
implements SkippingStreamCipher {
    private byte[] IV;
    private final int blockSize;
    private int byteCount;
    private final BlockCipher cipher;
    private byte[] counter;
    private byte[] counterOut;

    public SICBlockCipher(BlockCipher blockCipher) {
        super(blockCipher);
        this.cipher = blockCipher;
        int n = this.blockSize = this.cipher.getBlockSize();
        this.IV = new byte[n];
        this.counter = new byte[n];
        this.counterOut = new byte[n];
        this.byteCount = 0;
    }

    private void adjustCounter(long l) {
        if (l >= 0L) {
            long l2;
            long l3;
            long l4 = l2 = (l3 = ((long)this.byteCount + l) / (long)this.blockSize);
            if (l2 > 255L) {
                int n = 5;
                do {
                    l4 = l2;
                    if (n < 1) break;
                    l4 = 1L << n * 8;
                    while (l2 >= l4) {
                        this.incrementCounterAt(n);
                        l2 -= l4;
                    }
                    --n;
                } while (true);
            }
            this.incrementCounter((int)l4);
            this.byteCount = (int)((long)this.byteCount + l - (long)this.blockSize * l3);
        } else {
            int n;
            long l5;
            long l6;
            long l7 = l6 = (l5 = (-l - (long)this.byteCount) / (long)this.blockSize);
            if (l6 > 255L) {
                n = 5;
                do {
                    l7 = l6;
                    if (n < 1) break;
                    l7 = 1L << n * 8;
                    while (l6 > l7) {
                        this.decrementCounterAt(n);
                        l6 -= l7;
                    }
                    --n;
                } while (true);
            }
            for (l6 = 0L; l6 != l7; ++l6) {
                this.decrementCounterAt(0);
            }
            n = (int)((long)this.byteCount + l + (long)this.blockSize * l5);
            if (n >= 0) {
                this.byteCount = 0;
            } else {
                this.decrementCounterAt(0);
                this.byteCount = this.blockSize + n;
            }
        }
    }

    private void checkCounter() {
        if (this.IV.length < this.blockSize) {
            byte[] arrby;
            for (int i = 0; i != (arrby = this.IV).length; ++i) {
                if (this.counter[i] == arrby[i]) {
                    continue;
                }
                throw new IllegalStateException("Counter in CTR/SIC mode out of range.");
            }
        }
    }

    private void decrementCounterAt(int n) {
        n = this.counter.length - n;
        while (--n >= 0) {
            byte[] arrby = this.counter;
            byte by = (byte)(arrby[n] - 1);
            arrby[n] = by;
            if (by == -1) continue;
            return;
        }
    }

    private void incrementCounter(int n) {
        byte[] arrby = this.counter;
        byte by = arrby[arrby.length - 1];
        int n2 = arrby.length - 1;
        arrby[n2] = (byte)(arrby[n2] + n);
        if (by != 0 && arrby[arrby.length - 1] < by) {
            this.incrementCounterAt(1);
        }
    }

    private void incrementCounterAt(int n) {
        n = this.counter.length - n;
        while (--n >= 0) {
            byte[] arrby = this.counter;
            byte by = (byte)(arrby[n] + 1);
            arrby[n] = by;
            if (by == 0) continue;
        }
    }

    @Override
    protected byte calculateByte(byte by) throws DataLengthException, IllegalStateException {
        int n = this.byteCount;
        if (n == 0) {
            this.cipher.processBlock(this.counter, 0, this.counterOut, 0);
            byte[] arrby = this.counterOut;
            n = this.byteCount;
            this.byteCount = n + 1;
            return (byte)(arrby[n] ^ by);
        }
        byte[] arrby = this.counterOut;
        this.byteCount = n + 1;
        byte by2 = (byte)(arrby[n] ^ by);
        if (this.byteCount == this.counter.length) {
            this.byteCount = 0;
            this.incrementCounterAt(0);
            this.checkCounter();
        }
        return by2;
    }

    @Override
    public String getAlgorithmName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.cipher.getAlgorithmName());
        stringBuilder.append("/SIC");
        return stringBuilder.toString();
    }

    @Override
    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    @Override
    public long getPosition() {
        byte[] arrby = this.counter;
        byte[] arrby2 = new byte[arrby.length];
        System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)0, (int)arrby2.length);
        for (int i = arrby2.length - 1; i >= 1; --i) {
            arrby = this.IV;
            int n = i < arrby.length ? (arrby2[i] & 255) - (arrby[i] & 255) : arrby2[i] & 255;
            int n2 = n;
            if (n < 0) {
                n2 = i - 1;
                arrby2[n2] = (byte)(arrby2[n2] - 1);
                n2 = n + 256;
            }
            arrby2[i] = (byte)n2;
        }
        return Pack.bigEndianToLong(arrby2, arrby2.length - 8) * (long)this.blockSize + (long)this.byteCount;
    }

    @Override
    public void init(boolean bl, CipherParameters object) throws IllegalArgumentException {
        if (object instanceof ParametersWithIV) {
            object = (ParametersWithIV)object;
            this.IV = Arrays.clone(((ParametersWithIV)object).getIV());
            int n = this.blockSize;
            if (n >= this.IV.length) {
                int n2 = n / 2;
                int n3 = 8;
                if (8 > n2) {
                    n3 = n / 2;
                }
                if (this.blockSize - this.IV.length <= n3) {
                    if (((ParametersWithIV)object).getParameters() != null) {
                        this.cipher.init(true, ((ParametersWithIV)object).getParameters());
                    }
                    this.reset();
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("CTR/SIC mode requires IV of at least: ");
                ((StringBuilder)object).append(this.blockSize - n3);
                ((StringBuilder)object).append(" bytes.");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("CTR/SIC mode requires IV no greater than: ");
            ((StringBuilder)object).append(this.blockSize);
            ((StringBuilder)object).append(" bytes.");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("CTR/SIC mode requires ParametersWithIV");
    }

    @Override
    public int processBlock(byte[] arrby, int n, byte[] arrby2, int n2) throws DataLengthException, IllegalStateException {
        this.processBytes(arrby, n, this.blockSize, arrby2, n2);
        return this.blockSize;
    }

    @Override
    public void reset() {
        Arrays.fill(this.counter, (byte)0);
        byte[] arrby = this.IV;
        System.arraycopy((byte[])arrby, (int)0, (byte[])this.counter, (int)0, (int)arrby.length);
        this.cipher.reset();
        this.byteCount = 0;
    }

    @Override
    public long seekTo(long l) {
        this.reset();
        return this.skip(l);
    }

    @Override
    public long skip(long l) {
        this.adjustCounter(l);
        this.checkCounter();
        this.cipher.processBlock(this.counter, 0, this.counterOut, 0);
        return l;
    }
}

