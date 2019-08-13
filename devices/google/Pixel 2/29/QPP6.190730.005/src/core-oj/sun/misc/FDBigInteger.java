/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.math.BigInteger;
import java.util.Arrays;

public class FDBigInteger {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final long[] LONG_5_POW;
    private static final long LONG_MASK = 0xFFFFFFFFL;
    private static final int MAX_FIVE_POW = 340;
    private static final FDBigInteger[] POW_5_CACHE;
    static final int[] SMALL_5_POW;
    public static final FDBigInteger ZERO;
    private int[] data;
    private boolean isImmutable = false;
    private int nWords;
    private int offset;

    static {
        Object object;
        int n;
        SMALL_5_POW = new int[]{1, 5, 25, 125, 625, 3125, 15625, 78125, 390625, 1953125, 9765625, 48828125, 244140625, 1220703125};
        LONG_5_POW = new long[]{1L, 5L, 25L, 125L, 625L, 3125L, 15625L, 78125L, 390625L, 1953125L, 9765625L, 48828125L, 244140625L, 1220703125L, 6103515625L, 30517578125L, 152587890625L, 762939453125L, 3814697265625L, 19073486328125L, 95367431640625L, 476837158203125L, 2384185791015625L, 11920928955078125L, 59604644775390625L, 298023223876953125L, 1490116119384765625L};
        POW_5_CACHE = new FDBigInteger[340];
        for (n = 0; n < ((int[])(object = SMALL_5_POW)).length; ++n) {
            object = new FDBigInteger(new int[]{object[n]}, 0);
            ((FDBigInteger)object).makeImmutable();
            FDBigInteger.POW_5_CACHE[n] = object;
        }
        object = POW_5_CACHE[n - 1];
        while (n < 340) {
            FDBigInteger[] arrfDBigInteger = POW_5_CACHE;
            FDBigInteger fDBigInteger = FDBigInteger.super.mult(5);
            object = fDBigInteger;
            arrfDBigInteger[n] = fDBigInteger;
            ((FDBigInteger)object).makeImmutable();
            ++n;
        }
        ZERO = new FDBigInteger(new int[0], 0);
        ZERO.makeImmutable();
    }

    public FDBigInteger(long l, char[] arrc, int n, int n2) {
        int n3;
        int n4;
        int[] arrn = this.data = new int[Math.max((n2 + 8) / 9, 2)];
        arrn[0] = (int)l;
        arrn[1] = (int)(l >>> 32);
        this.offset = 0;
        this.nWords = 2;
        while ((n4 = n) < n2 - 5) {
            n3 = arrc[n4] - 48;
            for (n = n4 + 1; n < n4 + 5; ++n) {
                n3 = n3 * 10 + arrc[n] - 48;
            }
            this.multAddMe(100000, n3);
        }
        n = 1;
        n3 = 0;
        while (n4 < n2) {
            n3 = n3 * 10 + arrc[n4] - 48;
            n *= 10;
            ++n4;
        }
        if (n != 1) {
            this.multAddMe(n, n3);
        }
        this.trimLeadingZeros();
    }

    private FDBigInteger(int[] arrn, int n) {
        this.data = arrn;
        this.offset = n;
        this.nWords = arrn.length;
        this.trimLeadingZeros();
    }

    private FDBigInteger add(FDBigInteger fDBigInteger) {
        int n;
        int n2;
        long l;
        int n3;
        int n4;
        FDBigInteger fDBigInteger2;
        int n5 = this.size();
        if (n5 >= (n3 = fDBigInteger.size())) {
            fDBigInteger2 = this;
            n2 = n5;
            n4 = n3;
        } else {
            fDBigInteger2 = fDBigInteger;
            n2 = n3;
            fDBigInteger = this;
            n4 = n5;
        }
        int[] arrn = new int[n2 + 1];
        long l2 = 0L;
        for (n = 0; n < n4; ++n) {
            int n6 = fDBigInteger2.offset;
            l = n < n6 ? 0L : (long)fDBigInteger2.data[n - n6] & 0xFFFFFFFFL;
            n6 = fDBigInteger.offset;
            long l3 = n < n6 ? 0L : (long)fDBigInteger.data[n - n6] & 0xFFFFFFFFL;
            arrn[n] = (int)(l2 += l + l3);
            l2 >>= 32;
        }
        while (n < n2) {
            n5 = fDBigInteger2.offset;
            l = n < n5 ? 0L : (long)fDBigInteger2.data[n - n5] & 0xFFFFFFFFL;
            arrn[n] = (int)(l2 += l);
            l2 >>= 32;
            ++n;
        }
        arrn[n2] = (int)l2;
        return new FDBigInteger(arrn, 0);
    }

    private static FDBigInteger big5pow(int n) {
        if (n < 340) {
            return POW_5_CACHE[n];
        }
        return FDBigInteger.big5powRec(n);
    }

    private static FDBigInteger big5powRec(int n) {
        if (n < 340) {
            return POW_5_CACHE[n];
        }
        int n2 = n >> 1;
        FDBigInteger fDBigInteger = FDBigInteger.big5powRec(n2);
        int[] arrn = SMALL_5_POW;
        if ((n -= n2) < arrn.length) {
            return fDBigInteger.mult(arrn[n]);
        }
        return fDBigInteger.mult(FDBigInteger.big5powRec(n));
    }

    private static int checkZeroTail(int[] arrn, int n) {
        while (n > 0) {
            int n2;
            n = n2 = n - 1;
            if (arrn[n2] == 0) continue;
            return 1;
        }
        return 0;
    }

    private static void leftShift(int[] arrn, int n, int[] arrn2, int n2, int n3, int n4) {
        int n5;
        do {
            n5 = n4;
            if (n <= 0) break;
            n4 = arrn[n - 1];
            arrn2[n] = n5 << n2 | n4 >>> n3;
            --n;
        } while (true);
        arrn2[0] = n5 << n2;
    }

    private FDBigInteger mult(int n) {
        int n2 = this.nWords;
        if (n2 == 0) {
            return this;
        }
        int[] arrn = new int[n2 + 1];
        FDBigInteger.mult(this.data, n2, n, arrn);
        return new FDBigInteger(arrn, this.offset);
    }

    private FDBigInteger mult(FDBigInteger fDBigInteger) {
        if (this.nWords == 0) {
            return this;
        }
        if (this.size() == 1) {
            return fDBigInteger.mult(this.data[0]);
        }
        if (fDBigInteger.nWords == 0) {
            return fDBigInteger;
        }
        if (fDBigInteger.size() == 1) {
            return this.mult(fDBigInteger.data[0]);
        }
        int n = this.nWords;
        int n2 = fDBigInteger.nWords;
        int[] arrn = new int[n + n2];
        FDBigInteger.mult(this.data, n, fDBigInteger.data, n2, arrn);
        return new FDBigInteger(arrn, this.offset + fDBigInteger.offset);
    }

    private static void mult(int[] arrn, int n, int n2, int n3, int[] arrn2) {
        long l = n2;
        long l2 = 0L;
        for (n2 = 0; n2 < n; ++n2) {
            l2 = ((long)arrn[n2] & 0xFFFFFFFFL) * (l & 0xFFFFFFFFL) + l2;
            arrn2[n2] = (int)l2;
            l2 >>>= 32;
        }
        arrn2[n] = (int)l2;
        l = n3;
        l2 = 0L;
        for (n2 = 0; n2 < n; ++n2) {
            l2 = ((long)arrn2[n2 + 1] & 0xFFFFFFFFL) + ((long)arrn[n2] & 0xFFFFFFFFL) * (l & 0xFFFFFFFFL) + l2;
            arrn2[n2 + 1] = (int)l2;
            l2 >>>= 32;
        }
        arrn2[n + 1] = (int)l2;
    }

    private static void mult(int[] arrn, int n, int n2, int[] arrn2) {
        long l = n2;
        long l2 = 0L;
        for (n2 = 0; n2 < n; ++n2) {
            l2 = ((long)arrn[n2] & 0xFFFFFFFFL) * (l & 0xFFFFFFFFL) + l2;
            arrn2[n2] = (int)l2;
            l2 >>>= 32;
        }
        arrn2[n] = (int)l2;
    }

    private static void mult(int[] arrn, int n, int[] arrn2, int n2, int[] arrn3) {
        for (int i = 0; i < n; ++i) {
            long l = arrn[i];
            long l2 = 0L;
            for (int j = 0; j < n2; ++j) {
                arrn3[i + j] = (int)(l2 += ((long)arrn3[i + j] & 0xFFFFFFFFL) + ((long)arrn2[j] & 0xFFFFFFFFL) * (l & 0xFFFFFFFFL));
                l2 >>>= 32;
            }
            arrn3[i + n2] = (int)l2;
        }
    }

    private void multAddMe(int n, int n2) {
        long l = (long)n & 0xFFFFFFFFL;
        int[] arrn = this.data;
        long l2 = ((long)arrn[0] & 0xFFFFFFFFL) * l + ((long)n2 & 0xFFFFFFFFL);
        arrn[0] = (int)l2;
        l2 >>>= 32;
        for (n = 1; n < (n2 = this.nWords); ++n) {
            arrn = this.data;
            arrn[n] = (int)(l2 += ((long)arrn[n] & 0xFFFFFFFFL) * l);
            l2 >>>= 32;
        }
        if (l2 != 0L) {
            arrn = this.data;
            this.nWords = n2 + 1;
            arrn[n2] = (int)l2;
        }
    }

    private static int multAndCarryBy10(int[] arrn, int n, int[] arrn2) {
        long l = 0L;
        for (int i = 0; i < n; ++i) {
            l = ((long)arrn[i] & 0xFFFFFFFFL) * 10L + l;
            arrn2[i] = (int)l;
            l >>>= 32;
        }
        return (int)l;
    }

    private long multDiffMe(long l, FDBigInteger fDBigInteger) {
        long l2;
        long l3 = l2 = 0L;
        if (l != 0L) {
            int n = fDBigInteger.offset - this.offset;
            if (n >= 0) {
                int[] arrn = fDBigInteger.data;
                int[] arrn2 = this.data;
                int n2 = 0;
                while (n2 < fDBigInteger.nWords) {
                    arrn2[n] = (int)(l2 += ((long)arrn2[n] & 0xFFFFFFFFL) - ((long)arrn[n2] & 0xFFFFFFFFL) * l);
                    l2 >>= 32;
                    ++n2;
                    ++n;
                }
                l3 = l2;
            } else {
                int n3;
                int n4 = -n;
                int[] arrn = new int[this.nWords + n4];
                n = 0;
                int[] arrn3 = fDBigInteger.data;
                for (n3 = 0; n3 < n4 && n < fDBigInteger.nWords; ++n, ++n3) {
                    arrn[n3] = (int)(l2 -= ((long)arrn3[n] & 0xFFFFFFFFL) * l);
                    l2 >>= 32;
                }
                int n5 = 0;
                int[] arrn4 = this.data;
                l3 = l2;
                while (n < fDBigInteger.nWords) {
                    l2 = l3 + (((long)arrn4[n5] & 0xFFFFFFFFL) - ((long)arrn3[n] & 0xFFFFFFFFL) * l);
                    arrn[n3] = (int)l2;
                    l3 = l2 >> 32;
                    ++n;
                    ++n5;
                    ++n3;
                }
                this.nWords += n4;
                this.offset -= n4;
                this.data = arrn;
            }
        }
        return l3;
    }

    private int size() {
        return this.nWords + this.offset;
    }

    private void trimLeadingZeros() {
        int[] arrn;
        int n = this.nWords;
        if (n > 0 && (arrn = this.data)[--n] == 0) {
            while (n > 0 && this.data[n - 1] == 0) {
                --n;
            }
            this.nWords = n;
            if (n == 0) {
                this.offset = 0;
            }
        }
    }

    public static FDBigInteger valueOfMulPow52(long l, int n, int n2) {
        int n3 = (int)l;
        int n4 = (int)(l >>> 32);
        int n5 = n2 >> 5;
        int n6 = n2 & 31;
        if (n != 0) {
            int[] arrn = SMALL_5_POW;
            if (n < arrn.length) {
                long l2 = (long)arrn[n] & 0xFFFFFFFFL;
                l = ((long)n3 & 0xFFFFFFFFL) * l2;
                n = (int)l;
                l = ((long)n4 & 0xFFFFFFFFL) * l2 + (l >>> 32);
                n3 = (int)l;
                n2 = (int)(l >>> 32);
                if (n6 == 0) {
                    return new FDBigInteger(new int[]{n, n3, n2}, n5);
                }
                return new FDBigInteger(new int[]{n << n6, n3 << n6 | n >>> 32 - n6, n2 << n6 | n3 >>> 32 - n6, n2 >>> 32 - n6}, n5);
            }
            FDBigInteger fDBigInteger = FDBigInteger.big5pow(n);
            if (n4 == 0) {
                n4 = fDBigInteger.nWords;
                n = n2 != 0 ? 1 : 0;
                arrn = new int[n4 + 1 + n];
                FDBigInteger.mult(fDBigInteger.data, fDBigInteger.nWords, n3, arrn);
            } else {
                n5 = fDBigInteger.nWords;
                n = n2 != 0 ? 1 : 0;
                arrn = new int[n5 + 2 + n];
                FDBigInteger.mult(fDBigInteger.data, fDBigInteger.nWords, n3, n4, arrn);
            }
            return new FDBigInteger(arrn, fDBigInteger.offset).leftShift(n2);
        }
        if (n2 != 0) {
            if (n6 == 0) {
                return new FDBigInteger(new int[]{n3, n4}, n5);
            }
            return new FDBigInteger(new int[]{n3 << n6, n4 << n6 | n3 >>> 32 - n6, n4 >>> 32 - n6}, n5);
        }
        return new FDBigInteger(new int[]{n3, n4}, 0);
    }

    private static FDBigInteger valueOfPow2(int n) {
        return new FDBigInteger(new int[]{1 << (n & 31)}, n >> 5);
    }

    public static FDBigInteger valueOfPow52(int n, int n2) {
        if (n != 0) {
            if (n2 == 0) {
                return FDBigInteger.big5pow(n);
            }
            int[] arrn = SMALL_5_POW;
            if (n < arrn.length) {
                n = arrn[n];
                int n3 = n2 >> 5;
                if ((n2 &= 31) == 0) {
                    return new FDBigInteger(new int[]{n}, n3);
                }
                return new FDBigInteger(new int[]{n << n2, n >>> 32 - n2}, n3);
            }
            return FDBigInteger.big5pow(n).leftShift(n2);
        }
        return FDBigInteger.valueOfPow2(n2);
    }

    public int addAndCmp(FDBigInteger fDBigInteger, FDBigInteger fDBigInteger2) {
        int n;
        int n2;
        long l;
        FDBigInteger fDBigInteger3;
        int n3 = fDBigInteger.size();
        if (n3 >= (n2 = fDBigInteger2.size())) {
            n = n3;
            n3 = n2;
            fDBigInteger3 = fDBigInteger2;
            n2 = n;
        } else {
            fDBigInteger3 = fDBigInteger;
            fDBigInteger = fDBigInteger2;
        }
        int n4 = this.size();
        n = 1;
        if (n2 == 0) {
            n2 = n;
            if (n4 == 0) {
                n2 = 0;
            }
            return n2;
        }
        if (n3 == 0) {
            return this.cmp(fDBigInteger);
        }
        if (n2 > n4) {
            return -1;
        }
        if (n2 + 1 < n4) {
            return 1;
        }
        long l2 = l = (long)fDBigInteger.data[fDBigInteger.nWords - 1] & 0xFFFFFFFFL;
        if (n3 == n2) {
            l2 = l + ((long)fDBigInteger3.data[fDBigInteger3.nWords - 1] & 0xFFFFFFFFL);
        }
        if (l2 >>> 32 == 0L) {
            if (l2 + 1L >>> 32 == 0L) {
                if (n2 < n4) {
                    return 1;
                }
                l = 0xFFFFFFFFL & (long)this.data[this.nWords - 1];
                if (l < l2) {
                    return -1;
                }
                if (l > l2 + 1L) {
                    return 1;
                }
            }
        } else {
            if (n2 + 1 > n4) {
                return -1;
            }
            l = l2 >>> 32;
            l2 = 0xFFFFFFFFL & (long)this.data[this.nWords - 1];
            if (l2 < l) {
                return -1;
            }
            if (l2 > l + 1L) {
                return 1;
            }
        }
        return this.cmp(fDBigInteger.add(fDBigInteger3));
    }

    public int cmp(FDBigInteger fDBigInteger) {
        int n = this.nWords + this.offset;
        int n2 = fDBigInteger.nWords + fDBigInteger.offset;
        int n3 = 1;
        if (n > n2) {
            return 1;
        }
        if (n < n2) {
            return -1;
        }
        n = this.nWords;
        n2 = fDBigInteger.nWords;
        while (n > 0 && n2 > 0) {
            int n4;
            int[] arrn = this.data;
            int n5 = arrn[--n];
            arrn = fDBigInteger.data;
            if (n5 == (n4 = arrn[--n2])) continue;
            n2 = n3;
            if (((long)n5 & 0xFFFFFFFFL) < (0xFFFFFFFFL & (long)n4)) {
                n2 = -1;
            }
            return n2;
        }
        if (n > 0) {
            return FDBigInteger.checkZeroTail(this.data, n);
        }
        if (n2 > 0) {
            return -FDBigInteger.checkZeroTail(fDBigInteger.data, n2);
        }
        return 0;
    }

    public int cmpPow52(int n, int n2) {
        if (n == 0) {
            n = this.nWords;
            int n3 = this.offset + n;
            int n4 = n2 >> 5;
            if (n3 > n4 + 1) {
                return 1;
            }
            if (n3 < n4 + 1) {
                return -1;
            }
            int[] arrn = this.data;
            n3 = arrn[n - 1];
            if (n3 != (n2 = 1 << (n2 & 31))) {
                n = ((long)n3 & 0xFFFFFFFFL) < ((long)n2 & 0xFFFFFFFFL) ? -1 : 1;
                return n;
            }
            return FDBigInteger.checkZeroTail(arrn, n - 1);
        }
        return this.cmp(FDBigInteger.big5pow(n).leftShift(n2));
    }

    public int getNormalizationBias() {
        int n = this.nWords;
        if (n != 0) {
            n = (n = Integer.numberOfLeadingZeros(this.data[n - 1])) < 4 ? (n += 28) : (n -= 4);
            return n;
        }
        throw new IllegalArgumentException("Zero value cannot be normalized");
    }

    public FDBigInteger leftInplaceSub(FDBigInteger arrn) {
        FDBigInteger fDBigInteger = this.isImmutable ? new FDBigInteger((int[])this.data.clone(), this.offset) : this;
        int n = arrn.offset - fDBigInteger.offset;
        int[] arrn2 = arrn.data;
        int[] arrn3 = fDBigInteger.data;
        int n2 = arrn.nWords;
        int n3 = fDBigInteger.nWords;
        int n4 = n;
        int[] arrn4 = arrn3;
        int n5 = n3;
        if (n < 0) {
            n4 = n3 - n;
            if (n4 < arrn3.length) {
                System.arraycopy((Object)arrn3, 0, (Object)arrn3, -n, n3);
                Arrays.fill(arrn3, 0, -n, 0);
                arrn4 = arrn3;
            } else {
                int[] arrn5 = new int[n4];
                System.arraycopy((Object)arrn3, 0, (Object)arrn5, -n, n3);
                arrn4 = arrn5;
                fDBigInteger.data = arrn5;
            }
            fDBigInteger.offset = arrn.offset;
            n5 = n4;
            fDBigInteger.nWords = n4;
            n4 = 0;
        }
        long l = 0L;
        n3 = n4;
        arrn = arrn2;
        for (n = 0; n < n2 && n3 < n5; ++n, ++n3) {
            l = ((long)arrn4[n3] & 0xFFFFFFFFL) - ((long)arrn[n] & 0xFFFFFFFFL) + l;
            arrn4[n3] = (int)l;
            l >>= 32;
        }
        while (l != 0L && n3 < n5) {
            l = ((long)arrn4[n3] & 0xFFFFFFFFL) + l;
            arrn4[n3] = (int)l;
            l >>= 32;
            ++n3;
        }
        fDBigInteger.trimLeadingZeros();
        return fDBigInteger;
    }

    public FDBigInteger leftShift(int n) {
        int n2;
        if (n != 0 && (n2 = this.nWords) != 0) {
            int n3 = n >> 5;
            int n4 = n & 31;
            if (this.isImmutable) {
                int[] arrn;
                if (n4 == 0) {
                    return new FDBigInteger(Arrays.copyOf(this.data, n2), this.offset + n3);
                }
                int n5 = n2 - 1;
                int n6 = this.data[n5];
                n = 32 - n4;
                int n7 = n6 >>> n;
                if (n7 != 0) {
                    arrn = new int[n2 + 1];
                    arrn[n2] = n7;
                } else {
                    arrn = new int[n2];
                }
                FDBigInteger.leftShift(this.data, n5, arrn, n4, n, n6);
                return new FDBigInteger(arrn, this.offset + n3);
            }
            if (n4 != 0) {
                int n8 = 32 - n4;
                int[] arrn = this.data;
                if (arrn[0] << n4 == 0) {
                    int n9;
                    n2 = 0;
                    n = arrn[0];
                    do {
                        n9 = n;
                        n = this.nWords;
                        if (n2 >= n - 1) break;
                        int[] arrn2 = this.data;
                        n = arrn2[n2 + 1];
                        arrn2[n2] = n9 >>> n8 | n << n4;
                        ++n2;
                    } while (true);
                    this.data[n2] = n9 >>>= n8;
                    if (n9 == 0) {
                        this.nWords = n - 1;
                    }
                    ++this.offset;
                } else {
                    int n10 = n2 - 1;
                    int n11 = arrn[n10];
                    n = n11 >>> n8;
                    int[] arrn3 = this.data;
                    int[] arrn4 = this.data;
                    if (n != 0) {
                        if (n2 == arrn.length) {
                            arrn3 = arrn = new int[n2 + 1];
                            this.data = arrn;
                        }
                        n2 = this.nWords;
                        this.nWords = n2 + 1;
                        arrn3[n2] = n;
                    }
                    FDBigInteger.leftShift(arrn4, n10, arrn3, n4, n8, n11);
                }
            }
            this.offset += n3;
            return this;
        }
        return this;
    }

    public void makeImmutable() {
        this.isImmutable = true;
    }

    public FDBigInteger multBy10() {
        int n = this.nWords;
        if (n == 0) {
            return this;
        }
        if (this.isImmutable) {
            int[] arrn = new int[n + 1];
            arrn[n] = FDBigInteger.multAndCarryBy10(this.data, n, arrn);
            return new FDBigInteger(arrn, this.offset);
        }
        int[] arrn = this.data;
        if ((n = FDBigInteger.multAndCarryBy10(arrn, n, arrn)) != 0) {
            int n2 = this.nWords;
            arrn = this.data;
            if (n2 == arrn.length) {
                if (arrn[0] == 0) {
                    this.nWords = --n2;
                    System.arraycopy((Object)arrn, 1, (Object)arrn, 0, n2);
                    ++this.offset;
                } else {
                    this.data = Arrays.copyOf(arrn, arrn.length + 1);
                }
            }
            arrn = this.data;
            n2 = this.nWords;
            this.nWords = n2 + 1;
            arrn[n2] = n;
        } else {
            this.trimLeadingZeros();
        }
        return this;
    }

    public FDBigInteger multByPow52(int n, int n2) {
        if (this.nWords == 0) {
            return this;
        }
        Object object = this;
        if (n != 0) {
            int n3 = n2 != 0 ? 1 : 0;
            Object object2 = SMALL_5_POW;
            if (n < ((int[])object2).length) {
                int n4 = this.nWords;
                object = new int[n4 + 1 + n3];
                FDBigInteger.mult(this.data, n4, (int)object2[n], object);
                object = new FDBigInteger((int[])object, this.offset);
            } else {
                object2 = FDBigInteger.big5pow(n);
                object = new int[this.nWords + FDBigInteger.super.size() + n3];
                FDBigInteger.mult(this.data, this.nWords, ((FDBigInteger)object2).data, ((FDBigInteger)object2).nWords, object);
                object = new FDBigInteger((int[])object, this.offset + ((FDBigInteger)object2).offset);
            }
        }
        return object.leftShift(n2);
    }

    public int quoRemIteration(FDBigInteger arrn) throws IllegalArgumentException {
        int n;
        int n2 = this.size();
        if (n2 < (n = arrn.size())) {
            arrn = this.data;
            n = FDBigInteger.multAndCarryBy10(arrn, this.nWords, arrn);
            if (n != 0) {
                arrn = this.data;
                n2 = this.nWords;
                this.nWords = n2 + 1;
                arrn[n2] = n;
            } else {
                this.trimLeadingZeros();
            }
            return 0;
        }
        if (n2 <= n) {
            long l = ((long)this.data[this.nWords - 1] & 0xFFFFFFFFL) / ((long)arrn.data[arrn.nWords - 1] & 0xFFFFFFFFL);
            if (this.multDiffMe(l, (FDBigInteger)arrn) != 0L) {
                long l2 = 0L;
                int n3 = arrn.offset;
                int n4 = this.offset;
                arrn = arrn.data;
                int[] arrn2 = this.data;
                while (l2 == 0L) {
                    int n5 = 0;
                    for (n = n3 - n4; n < this.nWords; ++n) {
                        arrn2[n] = (int)(l2 += ((long)arrn2[n] & 0xFFFFFFFFL) + ((long)arrn[n5] & 0xFFFFFFFFL));
                        l2 >>>= 32;
                        ++n5;
                    }
                    --l;
                }
            }
            arrn = this.data;
            FDBigInteger.multAndCarryBy10(arrn, this.nWords, arrn);
            this.trimLeadingZeros();
            return (int)l;
        }
        throw new IllegalArgumentException("disparate values");
    }

    public FDBigInteger rightInplaceSub(FDBigInteger arrn) {
        int n;
        int n2;
        int[] arrn2 = this;
        int[] arrn3 = arrn;
        if (arrn.isImmutable) {
            arrn3 = new FDBigInteger((int[])arrn.data.clone(), arrn.offset);
        }
        int n3 = arrn2.offset - arrn3.offset;
        int[] arrn4 = arrn3.data;
        int[] arrn5 = arrn2.data;
        int n4 = arrn3.nWords;
        int n5 = arrn2.nWords;
        if (n3 < 0) {
            if (n5 < arrn4.length) {
                System.arraycopy((Object)arrn4, 0, (Object)arrn4, -n3, n4);
                Arrays.fill(arrn4, 0, -n3, 0);
                arrn = arrn4;
            } else {
                int[] arrn6 = new int[n5];
                System.arraycopy((Object)arrn4, 0, (Object)arrn6, -n3, n4);
                arrn = arrn6;
                arrn3.data = arrn6;
            }
            arrn3.offset = arrn2.offset;
            n = n4 - n3;
            n2 = 0;
        } else {
            int n6 = n5 + n3;
            n2 = n3;
            arrn = arrn4;
            n = n4;
            if (n6 >= arrn4.length) {
                arrn = arrn4 = Arrays.copyOf(arrn4, n6);
                arrn3.data = arrn4;
                n = n4;
                n2 = n3;
            }
        }
        long l = 0L;
        for (n4 = 0; n4 < n2; ++n4) {
            l = 0L - ((long)arrn[n4] & 0xFFFFFFFFL) + l;
            arrn[n4] = (int)l;
            l >>= 32;
        }
        arrn2 = arrn5;
        for (n2 = 0; n2 < n5; ++n2) {
            l = ((long)arrn2[n2] & 0xFFFFFFFFL) - ((long)arrn[n4] & 0xFFFFFFFFL) + l;
            arrn[n4] = (int)l;
            l >>= 32;
            ++n4;
        }
        arrn3.nWords = n4;
        FDBigInteger.super.trimLeadingZeros();
        return arrn3;
    }

    public BigInteger toBigInteger() {
        byte[] arrby = new byte[this.nWords * 4 + 1];
        for (int i = 0; i < this.nWords; ++i) {
            int n = this.data[i];
            arrby[arrby.length - i * 4 - 1] = (byte)n;
            arrby[arrby.length - i * 4 - 2] = (byte)(n >> 8);
            arrby[arrby.length - i * 4 - 3] = (byte)(n >> 16);
            arrby[arrby.length - i * 4 - 4] = (byte)(n >> 24);
        }
        return new BigInteger(arrby).shiftLeft(this.offset * 32);
    }

    public String toHexString() {
        int n = this.nWords;
        if (n == 0) {
            return "0";
        }
        StringBuilder stringBuilder = new StringBuilder((n + this.offset) * 8);
        for (n = this.nWords - 1; n >= 0; --n) {
            String string = Integer.toHexString(this.data[n]);
            for (int i = string.length(); i < 8; ++i) {
                stringBuilder.append('0');
            }
            stringBuilder.append(string);
        }
        for (n = this.offset; n > 0; --n) {
            stringBuilder.append("00000000");
        }
        return stringBuilder.toString();
    }

    public String toString() {
        return this.toBigInteger().toString();
    }
}

