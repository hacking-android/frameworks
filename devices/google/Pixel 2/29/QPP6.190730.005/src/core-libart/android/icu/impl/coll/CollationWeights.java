/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import java.util.Arrays;

public final class CollationWeights {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int[] maxBytes = new int[5];
    private int middleLength;
    private int[] minBytes = new int[5];
    private int rangeCount;
    private int rangeIndex;
    private WeightRange[] ranges = new WeightRange[7];

    private boolean allocWeightsInMinLengthRanges(int n, int n2) {
        long l;
        int n3;
        int n4;
        long l2;
        int n5;
        block12 : {
            int n6;
            int n7;
            block11 : {
                n5 = 0;
                for (n4 = 0; n4 < this.rangeCount && this.ranges[n4].length == n2; ++n4) {
                    n5 += this.ranges[n4].count;
                }
                int n8 = this.countBytes(n2 + 1);
                if (n > n5 * n8) {
                    return false;
                }
                l2 = this.ranges[0].start;
                l = this.ranges[0].end;
                for (n3 = 1; n3 < n4; ++n3) {
                    long l3 = l2;
                    if (this.ranges[n3].start < l2) {
                        l3 = this.ranges[n3].start;
                    }
                    long l4 = l;
                    if (this.ranges[n3].end > l) {
                        l4 = this.ranges[n3].end;
                    }
                    l2 = l3;
                    l = l4;
                }
                n6 = (n - n5) / (n8 - 1);
                n7 = n5 - n6;
                if (n6 == 0) break block11;
                n3 = n6;
                n4 = n7;
                if (n6 * n8 + n7 >= n) break block12;
            }
            n3 = n6 + 1;
            n4 = n7 - 1;
        }
        WeightRange[] arrweightRange = this.ranges;
        arrweightRange[0].start = l2;
        if (n4 == 0) {
            arrweightRange[0].end = l;
            arrweightRange[0].count = n5;
            this.lengthenRange(arrweightRange[0]);
            this.rangeCount = 1;
        } else {
            arrweightRange[0].end = this.incWeightByOffset(l2, n2, n4 - 1);
            arrweightRange = this.ranges;
            arrweightRange[0].count = n4;
            if (arrweightRange[1] == null) {
                arrweightRange[1] = new WeightRange();
            }
            arrweightRange = this.ranges;
            arrweightRange[1].start = this.incWeight(arrweightRange[0].end, n2);
            arrweightRange = this.ranges;
            arrweightRange[1].end = l;
            arrweightRange[1].length = n2;
            arrweightRange[1].count = n3;
            this.lengthenRange(arrweightRange[1]);
            this.rangeCount = 2;
        }
        return true;
    }

    private boolean allocWeightsInShortRanges(int n, int n2) {
        for (int i = 0; i < this.rangeCount && this.ranges[i].length <= n2 + 1; ++i) {
            if (n <= this.ranges[i].count) {
                if (this.ranges[i].length > n2) {
                    this.ranges[i].count = n;
                }
                if ((n = (this.rangeCount = i + 1)) > 1) {
                    Arrays.sort(this.ranges, 0, n);
                }
                return true;
            }
            n -= this.ranges[i].count;
        }
        return false;
    }

    private int countBytes(int n) {
        return this.maxBytes[n] - this.minBytes[n] + 1;
    }

    private static long decWeightTrail(long l, int n) {
        return l - (1L << (4 - n) * 8);
    }

    private static int getWeightByte(long l, int n) {
        return CollationWeights.getWeightTrail(l, n);
    }

    private boolean getWeightRanges(long l, long l2) {
        int n;
        int n2;
        Object object;
        int n3 = CollationWeights.lengthOfWeight(l2);
        if (l >= l2) {
            return false;
        }
        if (n < n3 && l == CollationWeights.truncateWeight(l2, n)) {
            return false;
        }
        WeightRange[] arrweightRange = new WeightRange[5];
        boolean bl = false;
        WeightRange weightRange = new WeightRange();
        WeightRange[] arrweightRange2 = new WeightRange[5];
        for (n = CollationWeights.lengthOfWeight((long)l); n > (n2 = this.middleLength); --n) {
            n2 = CollationWeights.getWeightTrail(l, n);
            if (n2 < this.maxBytes[n]) {
                arrweightRange[n] = new WeightRange();
                WeightRange weightRange2 = arrweightRange[n];
                object = arrweightRange;
                weightRange2.start = CollationWeights.incWeightTrail(l, n);
                object[n].end = CollationWeights.setWeightTrail(l, n, this.maxBytes[n]);
                object[n].length = n;
                object[n].count = this.maxBytes[n] - n2;
            }
            l = CollationWeights.truncateWeight(l, n - 1);
        }
        weightRange.start = l < 0xFF000000L ? CollationWeights.incWeightTrail(l, n2) : 0xFFFFFFFFL;
        l = l2;
        n = n3;
        while (n > (n3 = this.middleLength)) {
            n3 = CollationWeights.getWeightTrail(l, n);
            if (n3 > this.minBytes[n]) {
                arrweightRange2[n] = new WeightRange();
                arrweightRange2[n].start = CollationWeights.setWeightTrail(l, n, this.minBytes[n]);
                arrweightRange2[n].end = CollationWeights.decWeightTrail(l, n);
                arrweightRange2[n].length = n;
                arrweightRange2[n].count = n3 - this.minBytes[n];
            }
            l = CollationWeights.truncateWeight(l, n - 1);
            --n;
        }
        weightRange.end = CollationWeights.decWeightTrail(l, n3);
        weightRange.length = this.middleLength;
        if (weightRange.end >= weightRange.start) {
            weightRange.count = (int)(weightRange.end - weightRange.start >> (4 - this.middleLength) * 8) + 1;
        } else {
            for (n = 4; n > this.middleLength; --n) {
                if (arrweightRange[n] == null || arrweightRange2[n] == null || arrweightRange[n].count <= 0 || arrweightRange2[n].count <= 0) continue;
                long l3 = arrweightRange[n].end;
                l2 = arrweightRange2[n].start;
                n3 = 0;
                if (l3 > l2) {
                    arrweightRange[n].end = arrweightRange2[n].end;
                    arrweightRange[n].count = CollationWeights.getWeightTrail(arrweightRange[n].end, n) - CollationWeights.getWeightTrail(arrweightRange[n].start, n) + 1;
                    n3 = 1;
                } else if (l3 != l2 && this.incWeight(l3, n) == l2) {
                    arrweightRange[n].end = arrweightRange2[n].end;
                    object = arrweightRange[n];
                    object.count += arrweightRange2[n].count;
                    n3 = 1;
                }
                if (n3 != 0) {
                    arrweightRange2[n].count = 0;
                    while (--n > this.middleLength) {
                        arrweightRange2[n] = null;
                        arrweightRange[n] = null;
                    }
                    break;
                }
                bl = false;
            }
        }
        this.rangeCount = 0;
        if (weightRange.count > 0) {
            this.ranges[0] = weightRange;
            this.rangeCount = 1;
        }
        bl = true;
        for (n = this.middleLength + 1; n <= 4; ++n) {
            if (arrweightRange2[n] != null && arrweightRange2[n].count > 0) {
                object = this.ranges;
                n3 = this.rangeCount;
                this.rangeCount = n3 + 1;
                object[n3] = arrweightRange2[n];
            }
            if (arrweightRange[n] == null || arrweightRange[n].count <= 0) continue;
            object = this.ranges;
            n3 = this.rangeCount;
            this.rangeCount = n3 + 1;
            object[n3] = arrweightRange[n];
        }
        if (this.rangeCount <= 0) {
            bl = false;
        }
        return bl;
    }

    private static int getWeightTrail(long l, int n) {
        return (int)(l >> (4 - n) * 8) & 255;
    }

    private long incWeight(long l, int n) {
        int n2;
        while ((n2 = CollationWeights.getWeightByte(l, n)) >= this.maxBytes[n]) {
            l = CollationWeights.setWeightByte(l, n, this.minBytes[n]);
            --n;
        }
        return CollationWeights.setWeightByte(l, n, n2 + 1);
    }

    private long incWeightByOffset(long l, int n, int n2) {
        while ((n2 += CollationWeights.getWeightByte(l, n)) > this.maxBytes[n]) {
            int[] arrn = this.minBytes;
            l = CollationWeights.setWeightByte(l, n, arrn[n] + (n2 -= arrn[n]) % this.countBytes(n));
            n2 /= this.countBytes(n);
            --n;
        }
        return CollationWeights.setWeightByte(l, n, n2);
    }

    private static long incWeightTrail(long l, int n) {
        return (1L << (4 - n) * 8) + l;
    }

    public static int lengthOfWeight(long l) {
        if ((0xFFFFFFL & l) == 0L) {
            return 1;
        }
        if ((65535L & l) == 0L) {
            return 2;
        }
        if ((255L & l) == 0L) {
            return 3;
        }
        return 4;
    }

    private void lengthenRange(WeightRange weightRange) {
        int n = weightRange.length + 1;
        weightRange.start = CollationWeights.setWeightTrail(weightRange.start, n, this.minBytes[n]);
        weightRange.end = CollationWeights.setWeightTrail(weightRange.end, n, this.maxBytes[n]);
        weightRange.count *= this.countBytes(n);
        weightRange.length = n;
    }

    private static long setWeightByte(long l, int n, int n2) {
        long l2 = (n *= 8) < 32 ? 0xFFFFFFFFL >> n : 0L;
        n = 32 - n;
        return l & (l2 | 0xFFFFFF00L << n) | (long)n2 << n;
    }

    private static long setWeightTrail(long l, int n, int n2) {
        n = (4 - n) * 8;
        return 0xFFFFFF00L << n & l | (long)n2 << n;
    }

    private static long truncateWeight(long l, int n) {
        return 0xFFFFFFFFL << (4 - n) * 8 & l;
    }

    public boolean allocWeights(long l, long l2, int n) {
        if (!this.getWeightRanges(l, l2)) {
            return false;
        }
        block0 : do {
            int n2;
            block9 : {
                block8 : {
                    if (this.allocWeightsInShortRanges(n, n2 = this.ranges[0].length)) break block8;
                    if (n2 == 4) {
                        return false;
                    }
                    if (!this.allocWeightsInMinLengthRanges(n, n2)) break block9;
                }
                this.rangeIndex = 0;
                n = this.rangeCount;
                WeightRange[] arrweightRange = this.ranges;
                if (n < arrweightRange.length) {
                    arrweightRange[n] = null;
                }
                return true;
            }
            int n3 = 0;
            do {
                if (n3 >= this.rangeCount || this.ranges[n3].length != n2) continue block0;
                this.lengthenRange(this.ranges[n3]);
                ++n3;
            } while (true);
            break;
        } while (true);
    }

    public void initForPrimary(boolean bl) {
        this.middleLength = 1;
        int[] arrn = this.minBytes;
        arrn[1] = 3;
        int[] arrn2 = this.maxBytes;
        arrn2[1] = 255;
        if (bl) {
            arrn[2] = 4;
            arrn2[2] = 254;
        } else {
            arrn[2] = 2;
            arrn2[2] = 255;
        }
        arrn2 = this.minBytes;
        arrn2[3] = 2;
        arrn = this.maxBytes;
        arrn[3] = 255;
        arrn2[4] = 2;
        arrn[4] = 255;
    }

    public void initForSecondary() {
        this.middleLength = 3;
        int[] arrn = this.minBytes;
        arrn[1] = 0;
        int[] arrn2 = this.maxBytes;
        arrn2[1] = 0;
        arrn[2] = 0;
        arrn2[2] = 0;
        arrn[3] = 2;
        arrn2[3] = 255;
        arrn[4] = 2;
        arrn2[4] = 255;
    }

    public void initForTertiary() {
        this.middleLength = 3;
        int[] arrn = this.minBytes;
        arrn[1] = 0;
        int[] arrn2 = this.maxBytes;
        arrn2[1] = 0;
        arrn[2] = 0;
        arrn2[2] = 0;
        arrn[3] = 2;
        arrn2[3] = 63;
        arrn[4] = 2;
        arrn2[4] = 63;
    }

    public long nextWeight() {
        int n = this.rangeIndex;
        if (n >= this.rangeCount) {
            return 0xFFFFFFFFL;
        }
        WeightRange weightRange = this.ranges[n];
        long l = weightRange.start;
        weightRange.count = n = weightRange.count - 1;
        if (n == 0) {
            ++this.rangeIndex;
        } else {
            weightRange.start = this.incWeight(l, weightRange.length);
        }
        return l;
    }

    private static final class WeightRange
    implements Comparable<WeightRange> {
        int count;
        long end;
        int length;
        long start;

        private WeightRange() {
        }

        @Override
        public int compareTo(WeightRange weightRange) {
            long l = this.start;
            long l2 = weightRange.start;
            if (l < l2) {
                return -1;
            }
            return l > l2;
        }
    }

}

