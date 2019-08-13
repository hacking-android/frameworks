/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class BitArray {
    private static final int BITS_PER_UNIT = 8;
    private static final int BYTES_PER_LINE = 8;
    private static final byte[][] NYBBLE;
    private int length;
    private byte[] repn;

    static {
        byte[] arrby = new byte[]{48, 48, 48, 48};
        byte[] arrby2 = new byte[]{48, 48, 48, 49};
        byte[] arrby3 = new byte[]{48, 48, 49, 48};
        byte[] arrby4 = new byte[]{48, 48, 49, 49};
        byte[] arrby5 = new byte[]{48, 49, 48, 49};
        byte[] arrby6 = new byte[]{48, 49, 49, 49};
        byte[] arrby7 = new byte[]{49, 48, 48, 48};
        byte[] arrby8 = new byte[]{49, 48, 48, 49};
        byte[] arrby9 = new byte[]{49, 48, 49, 48};
        byte[] arrby10 = new byte[]{49, 49, 48, 48};
        byte[] arrby11 = new byte[]{49, 49, 48, 49};
        byte[] arrby12 = new byte[]{49, 49, 49, 48};
        byte[] arrby13 = new byte[]{49, 49, 49, 49};
        NYBBLE = new byte[][]{arrby, arrby2, arrby3, arrby4, {48, 49, 48, 48}, arrby5, {48, 49, 49, 48}, arrby6, arrby7, arrby8, arrby9, {49, 48, 49, 49}, arrby10, arrby11, arrby12, arrby13};
    }

    public BitArray(int n) throws IllegalArgumentException {
        if (n >= 0) {
            this.length = n;
            this.repn = new byte[(n + 8 - 1) / 8];
            return;
        }
        throw new IllegalArgumentException("Negative length for BitArray");
    }

    public BitArray(int n, byte[] arrby) throws IllegalArgumentException {
        if (n >= 0) {
            if (arrby.length * 8 >= n) {
                this.length = n;
                int n2 = (n + 8 - 1) / 8;
                n = (byte)(255 << n2 * 8 - n);
                this.repn = new byte[n2];
                System.arraycopy(arrby, 0, this.repn, 0, n2);
                if (n2 > 0) {
                    arrby = this.repn;
                    arrby[--n2] = (byte)(arrby[n2] & n);
                }
                return;
            }
            throw new IllegalArgumentException("Byte array too short to represent bit array of given length");
        }
        throw new IllegalArgumentException("Negative length for BitArray");
    }

    private BitArray(BitArray bitArray) {
        this.length = bitArray.length;
        this.repn = (byte[])bitArray.repn.clone();
    }

    public BitArray(boolean[] arrbl) {
        this.length = arrbl.length;
        this.repn = new byte[(this.length + 7) / 8];
        for (int i = 0; i < this.length; ++i) {
            this.set(i, arrbl[i]);
        }
    }

    private static int position(int n) {
        return 1 << 7 - n % 8;
    }

    private static int subscript(int n) {
        return n / 8;
    }

    public Object clone() {
        return new BitArray(this);
    }

    public boolean equals(Object arrby) {
        if (arrby == this) {
            return true;
        }
        if (arrby != null && arrby instanceof BitArray) {
            BitArray bitArray = (BitArray)arrby;
            if (bitArray.length != this.length) {
                return false;
            }
            for (int i = 0; i < (arrby = this.repn).length; ++i) {
                if (arrby[i] == bitArray.repn[i]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean get(int n) throws ArrayIndexOutOfBoundsException {
        if (n >= 0 && n < this.length) {
            boolean bl = (this.repn[BitArray.subscript(n)] & BitArray.position(n)) != 0;
            return bl;
        }
        throw new ArrayIndexOutOfBoundsException(Integer.toString(n));
    }

    public int hashCode() {
        byte[] arrby;
        int n = 0;
        for (int i = 0; i < (arrby = this.repn).length; ++i) {
            n = n * 31 + arrby[i];
        }
        return this.length ^ n;
    }

    public int length() {
        return this.length;
    }

    public void set(int n, boolean bl) throws ArrayIndexOutOfBoundsException {
        if (n >= 0 && n < this.length) {
            int n2 = BitArray.subscript(n);
            n = BitArray.position(n);
            if (bl) {
                byte[] arrby = this.repn;
                arrby[n2] = (byte)(arrby[n2] | n);
            } else {
                byte[] arrby = this.repn;
                arrby[n2] = (byte)(arrby[n2] & n);
            }
            return;
        }
        throw new ArrayIndexOutOfBoundsException(Integer.toString(n));
    }

    public boolean[] toBooleanArray() {
        boolean[] arrbl = new boolean[this.length];
        for (int i = 0; i < this.length; ++i) {
            arrbl[i] = this.get(i);
        }
        return arrbl;
    }

    public byte[] toByteArray() {
        return (byte[])this.repn.clone();
    }

    public String toString() {
        byte[] arrby;
        int n;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (n = 0; n < (arrby = this.repn).length - 1; ++n) {
            byteArrayOutputStream.write(NYBBLE[arrby[n] >> 4 & 15], 0, 4);
            byteArrayOutputStream.write(NYBBLE[this.repn[n] & 15], 0, 4);
            if (n % 8 == 7) {
                byteArrayOutputStream.write(10);
                continue;
            }
            byteArrayOutputStream.write(32);
        }
        for (n = (arrby.length - 1) * 8; n < this.length; ++n) {
            int n2 = this.get(n) ? 49 : 48;
            byteArrayOutputStream.write(n2);
        }
        return new String(byteArrayOutputStream.toByteArray());
    }

    public BitArray truncate() {
        for (int i = this.length - 1; i >= 0; --i) {
            if (!this.get(i)) continue;
            return new BitArray(i + 1, Arrays.copyOf(this.repn, (i + 8) / 8));
        }
        return new BitArray(1);
    }
}

