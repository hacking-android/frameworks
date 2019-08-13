/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

public final class Collation {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int BEFORE_WEIGHT16 = 256;
    static final int BUILDER_DATA_TAG = 7;
    public static final int CASE_AND_QUATERNARY_MASK = 49344;
    static final int CASE_AND_TERTIARY_MASK = 65343;
    public static final int CASE_LEVEL = 3;
    static final int CASE_LEVEL_FLAG = 8;
    public static final int CASE_MASK = 49152;
    static final int COMMON_BYTE = 5;
    static final int COMMON_SECONDARY_CE = 83886080;
    public static final int COMMON_SEC_AND_TER_CE = 83887360;
    static final int COMMON_TERTIARY_CE = 1280;
    public static final int COMMON_WEIGHT16 = 1280;
    static final int CONTRACTION_TAG = 9;
    static final int CONTRACT_NEXT_CCC = 512;
    static final int CONTRACT_SINGLE_CP_NO_MATCH = 256;
    static final int CONTRACT_TRAILING_CCC = 1024;
    static final int DIGIT_TAG = 10;
    public static final int EQUAL = 0;
    static final int EXPANSION32_TAG = 5;
    static final int EXPANSION_TAG = 6;
    static final int FALLBACK_CE32 = 192;
    static final int FALLBACK_TAG = 0;
    static final int FFFD_CE32 = -195323;
    public static final long FFFD_PRIMARY = 4294770688L;
    static final long FIRST_TRAILING_PRIMARY = 4278321664L;
    static final long FIRST_UNASSIGNED_PRIMARY = 4261675520L;
    public static final int GREATER = 1;
    static final int HANGUL_NO_SPECIAL_JAMO = 256;
    static final int HANGUL_TAG = 12;
    public static final int IDENTICAL_LEVEL = 6;
    static final int IDENTICAL_LEVEL_FLAG = 64;
    static final int IMPLICIT_TAG = 15;
    static final int LATIN_EXPANSION_TAG = 4;
    static final int LEAD_ALL_FALLBACK = 256;
    static final int LEAD_ALL_UNASSIGNED = 0;
    static final int LEAD_MIXED = 512;
    static final int LEAD_SURROGATE_TAG = 13;
    static final int LEAD_TYPE_MASK = 768;
    public static final int LESS = -1;
    public static final int LEVEL_SEPARATOR_BYTE = 1;
    static final int LONG_PRIMARY_CE32_LOW_BYTE = 193;
    static final int LONG_PRIMARY_TAG = 1;
    static final int LONG_SECONDARY_TAG = 2;
    static final int MAX_EXPANSION_LENGTH = 31;
    static final int MAX_INDEX = 524287;
    public static final long MAX_PRIMARY = 0xFFFF0000L;
    static final int MAX_REGULAR_CE32 = -64251;
    public static final int MERGE_SEPARATOR_BYTE = 2;
    static final int MERGE_SEPARATOR_CE32 = 33555717;
    public static final long MERGE_SEPARATOR_PRIMARY = 0x2000000L;
    public static final long NO_CE = 0x101000100L;
    static final int NO_CE32 = 1;
    static final long NO_CE_PRIMARY = 1L;
    static final int NO_CE_WEIGHT16 = 256;
    public static final int NO_LEVEL = 0;
    static final int NO_LEVEL_FLAG = 1;
    static final int OFFSET_TAG = 14;
    static final int ONLY_SEC_TER_MASK = -49345;
    public static final int ONLY_TERTIARY_MASK = 16191;
    static final int PREFIX_TAG = 8;
    public static final int PRIMARY_COMPRESSION_HIGH_BYTE = 255;
    public static final int PRIMARY_COMPRESSION_LOW_BYTE = 3;
    public static final int PRIMARY_LEVEL = 1;
    static final int PRIMARY_LEVEL_FLAG = 2;
    public static final int QUATERNARY_LEVEL = 5;
    static final int QUATERNARY_LEVEL_FLAG = 32;
    public static final int QUATERNARY_MASK = 192;
    static final int RESERVED_TAG_3 = 3;
    static final int SECONDARY_AND_CASE_MASK = -16384;
    public static final int SECONDARY_LEVEL = 2;
    static final int SECONDARY_LEVEL_FLAG = 4;
    static final int SECONDARY_MASK = -65536;
    public static final int SENTINEL_CP = -1;
    static final int SPECIAL_CE32_LOW_BYTE = 192;
    public static final int TERMINATOR_BYTE = 0;
    public static final int TERTIARY_LEVEL = 4;
    static final int TERTIARY_LEVEL_FLAG = 16;
    static final int TRAIL_WEIGHT_BYTE = 255;
    static final int U0000_TAG = 11;
    static final int UNASSIGNED_CE32 = -1;
    static final int UNASSIGNED_IMPLICIT_BYTE = 254;
    public static final int ZERO_LEVEL = 7;
    static final int ZERO_LEVEL_FLAG = 128;

    static boolean ce32HasContext(int n) {
        boolean bl = Collation.isSpecialCE32(n) && (Collation.tagFromCE32(n) == 8 || Collation.tagFromCE32(n) == 9);
        return bl;
    }

    static long ceFromCE32(int n) {
        int n2 = n & 255;
        if (n2 < 192) {
            return (long)(-65536 & n) << 32 | (long)(65280 & n) << 16 | (long)(n2 << 8);
        }
        n -= n2;
        if ((n2 & 15) == 1) {
            return (long)n << 32 | 0x5000500L;
        }
        return (long)n & 0xFFFFFFFFL;
    }

    static long ceFromLongPrimaryCE32(int n) {
        return (long)(n & -256) << 32 | 0x5000500L;
    }

    static long ceFromLongSecondaryCE32(int n) {
        return (long)n & 0xFFFFFF00L;
    }

    static long ceFromSimpleCE32(int n) {
        return (long)(-65536 & n) << 32 | (long)(65280 & n) << 16 | (long)((n & 255) << 8);
    }

    static long decThreeBytePrimaryByOneStep(long l, boolean bl, int n) {
        long l2;
        int n2 = ((int)(l >> 8) & 255) - n;
        if (n2 >= 2) {
            return 0xFFFF0000L & l | (long)(n2 << 8);
        }
        int n3 = ((int)(l >> 16) & 255) - 1;
        if (bl) {
            n = n3;
            l2 = l;
            if (n3 < 4) {
                n = 254;
                l2 = l - 0x1000000L;
            }
        } else {
            n = n3;
            l2 = l;
            if (n3 < 2) {
                n = 255;
                l2 = l - 0x1000000L;
            }
        }
        return 0xFF000000L & l2 | (long)(n << 16) | (long)(n2 + 254 << 8);
    }

    static long decTwoBytePrimaryByOneStep(long l, boolean bl, int n) {
        long l2;
        int n2 = ((int)(l >> 16) & 255) - n;
        if (bl) {
            n = n2;
            l2 = l;
            if (n2 < 4) {
                n = n2 + 251;
                l2 = l - 0x1000000L;
            }
        } else {
            n = n2;
            l2 = l;
            if (n2 < 2) {
                n = n2 + 254;
                l2 = l - 0x1000000L;
            }
        }
        return 0xFF000000L & l2 | (long)(n << 16);
    }

    static char digitFromCE32(int n) {
        return (char)(n >> 8 & 15);
    }

    static long getThreeBytePrimaryForOffsetData(int n, long l) {
        int n2 = (int)l;
        boolean bl = (n2 & 128) != 0;
        return Collation.incThreeBytePrimaryByOffset(l >>> 32, bl, (n - (n2 >> 8)) * (n2 & 127));
    }

    static boolean hasCE32Tag(int n, int n2) {
        boolean bl = Collation.isSpecialCE32(n) && Collation.tagFromCE32(n) == n2;
        return bl;
    }

    public static long incThreeBytePrimaryByOffset(long l, boolean bl, int n) {
        long l2 = (n += ((int)(l >> 8) & 255) - 2) % 254 + 2 << 8;
        n /= 254;
        if (bl) {
            l2 |= (long)((n += ((int)(l >> 16) & 255) - 4) % 251 + 4 << 16);
            n /= 251;
        } else {
            l2 |= (long)((n += ((int)(l >> 16) & 255) - 2) % 254 + 2 << 16);
            n /= 254;
        }
        return (0xFF000000L & l) + ((long)n << 24) | l2;
    }

    public static long incTwoBytePrimaryByOffset(long l, boolean bl, int n) {
        long l2;
        if (bl) {
            l2 = (n += ((int)(l >> 16) & 255) - 4) % 251 + 4 << 16;
            n /= 251;
        } else {
            l2 = (n += ((int)(l >> 16) & 255) - 2) % 254 + 2 << 16;
            n /= 254;
        }
        return (0xFF000000L & l) + ((long)n << 24) | l2;
    }

    static int indexFromCE32(int n) {
        return n >>> 13;
    }

    static boolean isAssignedCE32(int n) {
        boolean bl = n != 192 && n != -1;
        return bl;
    }

    static boolean isContractionCE32(int n) {
        return Collation.hasCE32Tag(n, 9);
    }

    static boolean isLongPrimaryCE32(int n) {
        return Collation.hasCE32Tag(n, 1);
    }

    static boolean isPrefixCE32(int n) {
        return Collation.hasCE32Tag(n, 8);
    }

    static boolean isSelfContainedCE32(int n) {
        boolean bl;
        block0 : {
            boolean bl2 = Collation.isSpecialCE32(n);
            bl = true;
            if (!bl2 || Collation.tagFromCE32(n) == 1 || Collation.tagFromCE32(n) == 2 || Collation.tagFromCE32(n) == 4) break block0;
            bl = false;
        }
        return bl;
    }

    static boolean isSimpleOrLongCE32(int n) {
        boolean bl;
        block0 : {
            boolean bl2 = Collation.isSpecialCE32(n);
            bl = true;
            if (!bl2 || Collation.tagFromCE32(n) == 1 || Collation.tagFromCE32(n) == 2) break block0;
            bl = false;
        }
        return bl;
    }

    static boolean isSpecialCE32(int n) {
        boolean bl = (n & 255) >= 192;
        return bl;
    }

    static long latinCE0FromCE32(int n) {
        return (long)(-16777216 & n) << 32 | 0x5000000L | (long)((16711680 & n) >> 8);
    }

    static long latinCE1FromCE32(int n) {
        return ((long)n & 65280L) << 16 | 1280L;
    }

    static int lengthFromCE32(int n) {
        return n >> 8 & 31;
    }

    public static long makeCE(long l) {
        return l << 32 | 0x5000500L;
    }

    static long makeCE(long l, int n, int n2, int n3) {
        return l << 32 | (long)n << 16 | (long)n2 | (long)(n3 << 6);
    }

    static int makeCE32FromTagAndIndex(int n, int n2) {
        return n2 << 13 | 192 | n;
    }

    static int makeCE32FromTagIndexAndLength(int n, int n2, int n3) {
        return n2 << 13 | n3 << 8 | 192 | n;
    }

    static int makeLongPrimaryCE32(long l) {
        return (int)(193L | l);
    }

    static int makeLongSecondaryCE32(int n) {
        return n | 192 | 2;
    }

    static long primaryFromLongPrimaryCE32(int n) {
        return (long)n & 0xFFFFFF00L;
    }

    static int tagFromCE32(int n) {
        return n & 15;
    }

    static long unassignedCEFromCodePoint(int n) {
        return Collation.makeCE(Collation.unassignedPrimaryFromCodePoint(n));
    }

    static long unassignedPrimaryFromCodePoint(int n) {
        long l = ++n % 18 * 14 + 2;
        return 4261412864L | (l | (long)((n /= 18) % 254 + 2 << 8) | (long)(n / 254 % 251 + 4 << 16));
    }
}

