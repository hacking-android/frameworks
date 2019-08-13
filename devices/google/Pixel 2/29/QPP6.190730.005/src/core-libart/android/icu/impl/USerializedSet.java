/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

public final class USerializedSet {
    private char[] array = new char[8];
    private int arrayOffset;
    private int bmpLength;
    private int length;

    public final boolean contains(int n) {
        char[] arrc;
        boolean bl = false;
        boolean bl2 = false;
        if (n > 1114111) {
            return false;
        }
        if (n <= 65535) {
            int n2;
            for (n2 = 0; n2 < this.bmpLength && (char)n >= this.array[n2]; ++n2) {
            }
            if ((n2 & 1) != 0) {
                bl2 = true;
            }
            return bl2;
        }
        char c = (char)(n >> 16);
        char c2 = (char)n;
        for (n = this.bmpLength; n < this.length && (c > (arrc = this.array)[n] || c == arrc[n] && c2 >= arrc[n + 1]); n += 2) {
        }
        bl2 = bl;
        if ((this.bmpLength + n & 2) != 0) {
            bl2 = true;
        }
        return bl2;
    }

    public final int countRanges() {
        int n = this.bmpLength;
        return (n + (this.length - n) / 2 + 1) / 2;
    }

    public final boolean getRange(int n, int[] arrn) {
        if (n < 0) {
            return false;
        }
        if (this.array == null) {
            this.array = new char[8];
        }
        if (arrn != null && arrn.length >= 2) {
            int n2 = this.bmpLength;
            if ((n *= 2) < n2) {
                char[] arrc = this.array;
                int n3 = n + 1;
                arrn[0] = arrc[n];
                arrn[1] = n3 < n2 ? arrc[n3] - '\u0001' : (n3 < this.length ? (arrc[n3] << 16 | arrc[n3 + 1]) - 1 : 1114111);
                return true;
            }
            int n4 = (n - n2) * 2;
            n = this.length - n2;
            if (n4 < n) {
                n2 = this.arrayOffset + n2;
                char[] arrc = this.array;
                arrn[0] = arrc[n2 + n4] << 16 | arrc[n2 + n4 + 1];
                arrn[1] = (n4 += 2) < n ? (arrc[n2 + n4] << 16 | arrc[n2 + n4 + 1]) - 1 : 1114111;
                return true;
            }
            return false;
        }
        throw new IllegalArgumentException();
    }

    public final boolean getSet(char[] arrc, int n) {
        block5 : {
            int n2;
            block4 : {
                block2 : {
                    block3 : {
                        this.array = null;
                        this.length = 0;
                        this.bmpLength = 0;
                        this.arrayOffset = 0;
                        n2 = n + 1;
                        this.length = arrc[n];
                        if ((32768 & (n = this.length)) == 0) break block2;
                        this.length = n & 32767;
                        if (arrc.length < n2 + 1 + this.length) break block3;
                        n = n2 + 1;
                        this.bmpLength = arrc[n2];
                        break block4;
                    }
                    this.length = 0;
                    throw new IndexOutOfBoundsException();
                }
                if (arrc.length < n2 + n) break block5;
                this.bmpLength = n;
                n = n2;
            }
            n2 = this.length;
            this.array = new char[n2];
            System.arraycopy(arrc, n, this.array, 0, n2);
            return true;
        }
        this.length = 0;
        throw new IndexOutOfBoundsException();
    }

    public final void setToOne(int n) {
        if (1114111 < n) {
            return;
        }
        if (n < 65535) {
            this.length = 2;
            this.bmpLength = 2;
            char[] arrc = this.array;
            arrc[0] = (char)n;
            arrc[1] = (char)(n + 1);
        } else if (n == 65535) {
            this.bmpLength = 1;
            this.length = 3;
            char[] arrc = this.array;
            arrc[0] = (char)65535;
            arrc[1] = (char)(true ? 1 : 0);
            arrc[2] = (char)(false ? 1 : 0);
        } else if (n < 1114111) {
            this.bmpLength = 0;
            this.length = 4;
            char[] arrc = this.array;
            arrc[0] = (char)(n >> 16);
            arrc[1] = (char)n;
            arrc[2] = (char)(++n >> 16);
            arrc[3] = (char)n;
        } else {
            this.bmpLength = 0;
            this.length = 2;
            char[] arrc = this.array;
            arrc[0] = (char)16;
            arrc[1] = (char)65535;
        }
    }
}

