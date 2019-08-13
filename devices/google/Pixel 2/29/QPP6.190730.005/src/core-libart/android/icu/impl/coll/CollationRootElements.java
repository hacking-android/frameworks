/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.coll.Collation;

public final class CollationRootElements {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int IX_COMMON_SEC_AND_TER_CE = 3;
    static final int IX_COUNT = 5;
    static final int IX_FIRST_PRIMARY_INDEX = 2;
    static final int IX_FIRST_SECONDARY_INDEX = 1;
    public static final int IX_FIRST_TERTIARY_INDEX = 0;
    static final int IX_SEC_TER_BOUNDARIES = 4;
    public static final long PRIMARY_SENTINEL = 0xFFFFFF00L;
    public static final int PRIMARY_STEP_MASK = 127;
    public static final int SEC_TER_DELTA_FLAG = 128;
    private long[] elements;

    public CollationRootElements(long[] arrl) {
        this.elements = arrl;
    }

    private int findP(long l) {
        long[] arrl = this.elements;
        int n = (int)arrl[2];
        int n2 = arrl.length - 1;
        block0 : while (n + 1 < n2) {
            long l2;
            int n3;
            block7 : {
                int n4 = (int)(((long)n + (long)n2) / 2L);
                long l3 = this.elements[n4];
                n3 = n4;
                l2 = l3;
                if ((l3 & 128L) == 0L) break block7;
                n3 = n4 + 1;
                block1 : do {
                    block9 : {
                        block8 : {
                            if (n3 == n2) break block8;
                            l3 = this.elements[n3];
                            if ((l3 & 128L) != 0L) break block9;
                            n4 = n3;
                        }
                        n3 = n4;
                        l2 = l3;
                        if ((l3 & 128L) == 0L) break;
                        n3 = n4 - 1;
                        do {
                            block12 : {
                                block11 : {
                                    block10 : {
                                        if (n3 != n) break block10;
                                        n3 = n4;
                                        break block11;
                                    }
                                    l3 = this.elements[n3];
                                    if ((l3 & 128L) != 0L) break block12;
                                }
                                l2 = l3;
                                if ((128L & l3) == 0L) break block1;
                                break block0;
                            }
                            --n3;
                        } while (true);
                    }
                    ++n3;
                } while (true);
            }
            if (l < (0xFFFFFF00L & l2)) {
                n2 = n3;
                continue;
            }
            n = n3;
        }
        return n;
    }

    private long getFirstSecTerForPrimary(int n) {
        long l = this.elements[n];
        if ((128L & l) == 0L) {
            return 0x5000500L;
        }
        if ((l &= -129L) > 0x5000500L) {
            return 0x5000500L;
        }
        return l;
    }

    private static boolean isEndOfPrimaryRange(long l) {
        boolean bl = (128L & l) == 0L && (127L & l) != 0L;
        return bl;
    }

    int findPrimary(long l) {
        int n = this.findP(l);
        return n;
    }

    long firstCEWithPrimaryAtLeast(long l) {
        if (l == 0L) {
            return 0L;
        }
        int n = this.findP(l);
        long l2 = l;
        if (l != (this.elements[n] & 0xFFFFFF00L)) {
            long[] arrl;
            while ((128L & (l2 = (arrl = this.elements)[++n])) != 0L) {
            }
        }
        return l2 << 32 | 0x5000500L;
    }

    long getFirstPrimary() {
        long[] arrl = this.elements;
        return arrl[(int)arrl[2]];
    }

    long getFirstPrimaryCE() {
        return Collation.makeCE(this.getFirstPrimary());
    }

    long getFirstSecondaryCE() {
        long[] arrl = this.elements;
        return arrl[(int)arrl[1]] & -129L;
    }

    long getFirstTertiaryCE() {
        long[] arrl = this.elements;
        return arrl[(int)arrl[0]] & -129L;
    }

    public int getLastCommonSecondary() {
        return (int)this.elements[4] >> 16 & 65280;
    }

    long getLastSecondaryCE() {
        long[] arrl = this.elements;
        return arrl[(int)arrl[2] - 1] & -129L;
    }

    long getLastTertiaryCE() {
        long[] arrl = this.elements;
        return arrl[(int)arrl[1] - 1] & -129L;
    }

    long getPrimaryAfter(long l, int n, boolean bl) {
        long l2;
        long[] arrl = this.elements;
        int n2 = n + 1;
        long l3 = l2 = arrl[n2];
        n = n2;
        if ((l2 & 128L) == 0L) {
            int n3 = (int)l2 & 127;
            l3 = l2;
            n = n2;
            if (n3 != 0) {
                if ((65535L & l) == 0L) {
                    return Collation.incTwoBytePrimaryByOffset(l, bl, n3);
                }
                return Collation.incThreeBytePrimaryByOffset(l, bl, n3);
            }
        }
        while ((l3 & 128L) != 0L) {
            arrl = this.elements;
            l3 = arrl[++n];
        }
        return l3;
    }

    long getPrimaryBefore(long l, boolean bl) {
        int n;
        long[] arrl = this.elements;
        int n2 = this.findPrimary(l);
        long l2 = arrl[n2];
        if (l == (l2 & 0xFFFFFF00L)) {
            int n3;
            n = n3 = (int)l2 & 127;
            if (n3 == 0) {
                n = n2;
                while ((128L & (l = (arrl = this.elements)[--n])) != 0L) {
                }
                return 0xFFFFFF00L & l;
            }
        } else {
            l2 = arrl[n2 + 1];
            n = (int)l2 & 127;
        }
        if ((65535L & l) == 0L) {
            return Collation.decTwoBytePrimaryByOneStep(l, bl, n);
        }
        return Collation.decThreeBytePrimaryByOneStep(l, bl, n);
    }

    int getSecondaryAfter(int n, int n2) {
        long l;
        long[] arrl;
        int n3;
        if (n == 0) {
            arrl = this.elements;
            n = (int)arrl[1];
            l = arrl[n];
            n3 = 65536;
        } else {
            l = this.getFirstSecTerForPrimary(n + 1);
            n3 = this.getSecondaryBoundary();
        }
        do {
            int n4;
            if ((n4 = (int)(l >> 16)) <= n2) continue;
            return n4;
        } while ((128L & (l = (arrl = this.elements)[++n])) != 0L);
        return n3;
    }

    int getSecondaryBefore(long l, int n) {
        int n2;
        int n3;
        int n4;
        if (l == 0L) {
            long[] arrl = this.elements;
            n4 = (int)arrl[1];
            n3 = 0;
            n2 = (int)(arrl[n4] >> 16);
        } else {
            n4 = 1 + this.findPrimary(l);
            n3 = 256;
            n2 = (int)this.getFirstSecTerForPrimary(n4) >>> 16;
        }
        while (n > n2) {
            n3 = n2;
            n2 = (int)(this.elements[n4] >> 16);
            ++n4;
        }
        return n3;
    }

    public int getSecondaryBoundary() {
        return (int)this.elements[4] >> 8 & 65280;
    }

    int getTertiaryAfter(int n, int n2, int n3) {
        long l;
        int n4;
        if (n == 0) {
            if (n2 == 0) {
                n = (int)this.elements[0];
                n4 = 16384;
            } else {
                n = (int)this.elements[1];
                n4 = this.getTertiaryBoundary();
            }
            l = this.elements[n] & -129L;
        } else {
            l = this.getFirstSecTerForPrimary(n + 1);
            n4 = this.getTertiaryBoundary();
        }
        long l2 = n2;
        long l3 = n3;
        do {
            if (l > ((l2 & 0xFFFFFFFFL) << 16 | l3)) {
                return (int)l & 65535;
            }
            long[] arrl = this.elements;
            if ((128L & (l = arrl[++n])) == 0L || l >> 16 > (long)n2) break;
            l &= -129L;
        } while (true);
        return n4;
    }

    int getTertiaryBefore(long l, int n, int n2) {
        int n3;
        int n4;
        if (l == 0L) {
            if (n == 0) {
                n4 = (int)this.elements[0];
                n3 = 0;
            } else {
                n4 = (int)this.elements[1];
                n3 = 256;
            }
            l = this.elements[n4] & -129L;
        } else {
            n4 = this.findPrimary(l) + 1;
            n3 = 256;
            l = this.getFirstSecTerForPrimary(n4);
        }
        long l2 = n;
        long l3 = n2;
        n2 = n3;
        while ((l2 << 16 | l3) > l) {
            if ((int)(l >> 16) == n) {
                n2 = (int)l;
            }
            l = this.elements[n4] & -129L;
            ++n4;
        }
        return 65535 & n2;
    }

    public int getTertiaryBoundary() {
        return (int)this.elements[4] << 8 & 65280;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    long lastCEWithPrimaryBefore(long l) {
        long l2;
        if (l == 0L) {
            return 0L;
        }
        long[] arrl = this.elements;
        int n = this.findP(l);
        long l3 = arrl[n];
        if (l == (l3 & 0xFFFFFF00L)) {
            l2 = arrl[n - 1];
            if ((l2 & 128L) == 0L) {
                l = l2 & 0xFFFFFF00L;
                l2 = 0x5000500L;
                return l << 32 | -129L & l2;
            }
            n -= 2;
            do {
                if (((l = this.elements[n]) & 128L) == 0L) {
                    l &= 0xFFFFFF00L;
                    return l << 32 | -129L & l2;
                }
                --n;
            } while (true);
        }
        l = 0x5000500L;
        do {
            if (((l2 = (arrl = this.elements)[++n]) & 128L) == 0L) {
                l3 = 0xFFFFFF00L & l3;
                l2 = l;
                l = l3;
                return l << 32 | -129L & l2;
            }
            l = l2;
        } while (true);
    }
}

