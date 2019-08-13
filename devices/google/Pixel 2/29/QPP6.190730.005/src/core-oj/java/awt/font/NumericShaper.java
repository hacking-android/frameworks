/*
 * Decompiled with CFR 0.145.
 */
package java.awt.font;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;

public final class NumericShaper
implements Serializable {
    public static final int ALL_RANGES = 524287;
    public static final int ARABIC = 2;
    private static final int ARABIC_KEY = 1;
    public static final int BENGALI = 16;
    private static final int BENGALI_KEY = 4;
    private static final int BSEARCH_THRESHOLD = 3;
    private static final int CONTEXTUAL_MASK = Integer.MIN_VALUE;
    public static final int DEVANAGARI = 8;
    private static final int DEVANAGARI_KEY = 3;
    public static final int EASTERN_ARABIC = 4;
    private static final int EASTERN_ARABIC_KEY = 2;
    public static final int ETHIOPIC = 65536;
    private static final int ETHIOPIC_KEY = 16;
    public static final int EUROPEAN = 1;
    private static final int EUROPEAN_KEY = 0;
    public static final int GUJARATI = 64;
    private static final int GUJARATI_KEY = 6;
    public static final int GURMUKHI = 32;
    private static final int GURMUKHI_KEY = 5;
    public static final int KANNADA = 1024;
    private static final int KANNADA_KEY = 10;
    public static final int KHMER = 131072;
    private static final int KHMER_KEY = 17;
    public static final int LAO = 8192;
    private static final int LAO_KEY = 13;
    public static final int MALAYALAM = 2048;
    private static final int MALAYALAM_KEY = 11;
    public static final int MONGOLIAN = 262144;
    private static final int MONGOLIAN_KEY = 18;
    public static final int MYANMAR = 32768;
    private static final int MYANMAR_KEY = 15;
    private static final int NUM_KEYS = 19;
    public static final int ORIYA = 128;
    private static final int ORIYA_KEY = 7;
    public static final int TAMIL = 256;
    private static final int TAMIL_KEY = 8;
    public static final int TELUGU = 512;
    private static final int TELUGU_KEY = 9;
    public static final int THAI = 4096;
    private static final int THAI_KEY = 12;
    public static final int TIBETAN = 16384;
    private static final int TIBETAN_KEY = 14;
    private static final char[] bases = new char[]{'\u0000', '\u0630', '\u06c0', '\u0936', '\u09b6', '\u0a36', '\u0ab6', '\u0b36', '\u0bb6', '\u0c36', '\u0cb6', '\u0d36', '\u0e20', '\u0ea0', '\u0ef0', '\u1010', '\u1338', '\u17b0', '\u17e0'};
    private static final char[] contexts = new char[]{'\u0000', '\u0300', '\u0600', '\u0780', '\u0600', '\u0780', '\u0900', '\u0980', '\u0980', '\u0a00', '\u0a00', '\u0a80', '\u0a80', '\u0b00', '\u0b00', '\u0b80', '\u0b80', '\u0c00', '\u0c00', '\u0c80', '\u0c80', '\u0d00', '\u0d00', '\u0d80', '\u0e00', '\u0e80', '\u0e80', '\u0f00', '\u0f00', '\u1000', '\u1000', '\u1080', '\u1200', '\u1380', '\u1780', '\u1800', '\u1800', '\u1900', '\uffffffff'};
    private static int ctCache = 0;
    private static int ctCacheLimit = 0;
    private static final long serialVersionUID = -8022764705923730308L;
    private static int[] strongTable;
    private volatile transient Range currentRange = Range.EUROPEAN;
    private int key;
    private int mask;
    private transient Range[] rangeArray;
    private transient Set<Range> rangeSet;
    private Range shapingRange;
    private volatile transient int stCache = 0;

    static {
        ctCache = 0;
        ctCacheLimit = contexts.length - 2;
        strongTable = new int[]{0, 65, 91, 97, 123, 170, 171, 181, 182, 186, 187, 192, 215, 216, 247, 248, 697, 699, 706, 720, 722, 736, 741, 750, 751, 880, 884, 886, 894, 902, 903, 904, 1014, 1015, 1155, 1162, 1418, 1470, 1471, 1472, 1473, 1475, 1476, 1478, 1479, 1488, 1536, 1544, 1545, 1547, 1548, 1549, 1550, 1563, 1611, 1645, 1648, 1649, 1750, 1765, 1767, 1774, 1776, 1786, 1809, 1810, 1840, 1869, 1958, 1969, 2027, 2036, 2038, 2042, 2070, 2074, 2075, 2084, 2085, 2088, 2089, 2096, 2137, 2142, 2276, 2307, 2362, 2363, 2364, 2365, 2369, 2377, 2381, 2382, 2385, 2392, 2402, 2404, 2433, 2434, 2492, 2493, 2497, 2503, 2509, 2510, 2530, 2534, 2546, 2548, 2555, 2563, 2620, 2622, 2625, 2649, 2672, 2674, 2677, 2691, 2748, 2749, 2753, 2761, 2765, 2768, 2786, 2790, 2801, 2818, 2876, 2877, 2879, 2880, 2881, 2887, 2893, 2903, 2914, 2918, 2946, 2947, 3008, 3009, 3021, 3024, 3059, 3073, 3134, 3137, 3142, 3160, 3170, 3174, 3192, 3199, 3260, 3261, 3276, 3285, 3298, 3302, 3393, 3398, 3405, 3406, 3426, 3430, 3530, 3535, 3538, 3544, 3633, 3634, 3636, 3648, 3655, 3663, 3761, 3762, 3764, 3773, 3784, 3792, 3864, 3866, 3893, 3894, 3895, 3896, 3897, 3902, 3953, 3967, 3968, 3973, 3974, 3976, 3981, 4030, 4038, 4039, 4141, 4145, 4146, 4152, 4153, 4155, 4157, 4159, 4184, 4186, 4190, 4193, 4209, 4213, 4226, 4227, 4229, 4231, 4237, 4238, 4253, 4254, 4957, 4960, 5008, 5024, 5120, 5121, 5760, 5761, 5787, 5792, 5906, 5920, 5938, 5941, 5970, 5984, 6002, 6016, 6068, 6070, 6071, 6078, 6086, 6087, 6089, 6100, 6107, 6108, 6109, 6112, 6128, 6160, 6313, 6314, 6432, 6435, 6439, 6441, 6450, 6451, 6457, 6470, 6622, 6656, 6679, 6681, 6742, 6743, 6744, 6753, 6754, 6755, 6757, 6765, 6771, 6784, 6912, 6916, 6964, 6965, 6966, 6971, 6972, 6973, 6978, 6979, 7019, 7028, 7040, 7042, 7074, 7078, 7080, 7082, 7083, 7084, 7142, 7143, 7144, 7146, 7149, 7150, 7151, 7154, 7212, 7220, 7222, 7227, 7376, 7379, 7380, 7393, 7394, 7401, 7405, 7406, 7412, 7413, 7616, 7680, 8125, 8126, 8127, 8130, 8141, 8144, 8157, 8160, 8173, 8178, 8189, 8206, 8208, 8305, 8308, 8319, 8320, 8336, 8352, 8450, 8451, 8455, 8456, 8458, 8468, 8469, 8470, 8473, 8478, 8484, 8485, 8486, 8487, 8488, 8489, 8490, 8494, 8495, 8506, 8508, 8512, 8517, 8522, 8526, 8528, 8544, 8585, 9014, 9083, 9109, 9110, 9372, 9450, 9900, 9901, 10240, 10496, 11264, 11493, 11499, 11503, 11506, 11513, 11520, 11647, 11648, 11744, 12293, 12296, 12321, 12330, 12337, 12342, 12344, 12349, 12353, 12441, 12445, 12448, 12449, 12539, 12540, 12736, 12784, 12829, 12832, 12880, 12896, 12924, 12927, 12977, 12992, 13004, 13008, 13175, 13179, 13278, 13280, 13311, 13312, 19904, 19968, 42128, 42192, 42509, 42512, 42607, 42624, 42655, 42656, 42736, 42738, 42752, 42786, 42888, 42889, 43010, 43011, 43014, 43015, 43019, 43020, 43045, 43047, 43048, 43056, 43064, 43072, 43124, 43136, 43204, 43214, 43232, 43250, 43302, 43310, 43335, 43346, 43392, 43395, 43443, 43444, 43446, 43450, 43452, 43453, 43561, 43567, 43569, 43571, 43573, 43584, 43587, 43588, 43596, 43597, 43696, 43697, 43698, 43701, 43703, 43705, 43710, 43712, 43713, 43714, 43756, 43758, 43766, 43777, 44005, 44006, 44008, 44009, 44013, 44016, 64286, 64287, 64297, 64298, 64830, 64848, 65021, 65136, 65279, 65313, 65339, 65345, 65371, 65382, 65504, 65536, 65793, 65794, 65856, 66000, 66045, 66176, 67871, 67872, 68097, 68112, 68152, 68160, 68409, 68416, 69216, 69632, 69633, 69634, 69688, 69703, 69714, 69734, 69760, 69762, 69811, 69815, 69817, 69819, 69888, 69891, 69927, 69932, 69933, 69942, 70016, 70018, 70070, 70079, 71339, 71340, 71341, 71342, 71344, 71350, 71351, 71360, 94095, 94099, 119143, 119146, 119155, 119171, 119173, 119180, 119210, 119214, 119296, 119648, 120539, 120540, 120597, 120598, 120655, 120656, 120713, 120714, 120771, 120772, 120782, 126464, 126704, 127248, 127338, 127344, 127744, 128140, 128141, 128292, 128293, 131072, 917505, 983040, 1114110, 1114111};
    }

    private NumericShaper(int n, int n2) {
        this.key = n;
        this.mask = n2;
    }

    private NumericShaper(Range object, Set<Range> set) {
        this.shapingRange = object;
        this.rangeSet = EnumSet.copyOf(set);
        if (this.rangeSet.contains((Object)Range.EASTERN_ARABIC) && this.rangeSet.contains((Object)Range.ARABIC)) {
            this.rangeSet.remove((Object)Range.ARABIC);
        }
        if (this.rangeSet.contains((Object)Range.TAI_THAM_THAM) && this.rangeSet.contains((Object)Range.TAI_THAM_HORA)) {
            this.rangeSet.remove((Object)Range.TAI_THAM_HORA);
        }
        object = this.rangeSet;
        this.rangeArray = object.toArray((T[])new Range[object.size()]);
        object = this.rangeArray;
        if (((Object)object).length > 3) {
            Arrays.sort(object, new Comparator<Range>(){

                @Override
                public int compare(Range range, Range range2) {
                    int n = range.base > range2.base ? 1 : (range.base == range2.base ? 0 : -1);
                    return n;
                }
            });
        }
    }

    private void checkParams(char[] arrc, int n, int n2) {
        if (arrc != null) {
            if (n >= 0 && n <= arrc.length && n + n2 >= 0 && n + n2 <= arrc.length) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("bad start or count for text of length ");
            stringBuilder.append(arrc.length);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        throw new NullPointerException("text is null");
    }

    private static int getContextKey(char c) {
        char[] arrc = contexts;
        int n = ctCache;
        if (c < arrc[n]) {
            while ((n = ctCache) > 0 && c < contexts[n]) {
                ctCache = n - 1;
            }
        } else if (c >= arrc[n + 1]) {
            while ((n = ctCache) < ctCacheLimit && c >= contexts[n + 1]) {
                ctCache = n + 1;
            }
        }
        c = ((c = (char)ctCache) & '\u0001') == 0 ? (char)(c / 2) : (char)'\u0000';
        return c;
    }

    public static NumericShaper getContextualShaper(int n) {
        return new NumericShaper(0, n | Integer.MIN_VALUE);
    }

    public static NumericShaper getContextualShaper(int n, int n2) {
        return new NumericShaper(NumericShaper.getKeyFromMask(n2), n | Integer.MIN_VALUE);
    }

    public static NumericShaper getContextualShaper(Set<Range> object) {
        object = new NumericShaper(Range.EUROPEAN, (Set<Range>)object);
        ((NumericShaper)object).mask = Integer.MIN_VALUE;
        return object;
    }

    public static NumericShaper getContextualShaper(Set<Range> object, Range range) {
        if (range != null) {
            object = new NumericShaper(range, (Set<Range>)object);
            ((NumericShaper)object).mask = Integer.MIN_VALUE;
            return object;
        }
        throw new NullPointerException();
    }

    private static int getHighBit(int n) {
        if (n <= 0) {
            return -32;
        }
        int n2 = 0;
        int n3 = n;
        if (n >= 65536) {
            n3 = n >> 16;
            n2 = 0 + 16;
        }
        int n4 = n2;
        int n5 = n3;
        if (n3 >= 256) {
            n5 = n3 >> 8;
            n4 = n2 + 8;
        }
        n = n4;
        n3 = n5;
        if (n5 >= 16) {
            n3 = n5 >> 4;
            n = n4 + 4;
        }
        n4 = n;
        n5 = n3;
        if (n3 >= 4) {
            n5 = n3 >> 2;
            n4 = n + 2;
        }
        n = n4;
        if (n5 >= 2) {
            n = n4 + 1;
        }
        return n;
    }

    private static int getKeyFromMask(int n) {
        int n2;
        for (n2 = 0; n2 < 19 && (1 << n2 & n) == 0; ++n2) {
        }
        if (n2 != 19 && (1 << n2 & n) == 0) {
            return n2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid shaper: ");
        stringBuilder.append(Integer.toHexString(n));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static NumericShaper getShaper(int n) {
        return new NumericShaper(NumericShaper.getKeyFromMask(n), n);
    }

    public static NumericShaper getShaper(Range range) {
        return new NumericShaper(range, EnumSet.of(range));
    }

    private boolean isStrongDirectional(char c) {
        int n = this.stCache;
        int[] arrn = strongTable;
        int n2 = arrn[n];
        boolean bl = false;
        if (c < n2) {
            n2 = NumericShaper.search(c, arrn, 0, n);
        } else {
            n2 = n;
            if (c >= arrn[n + 1]) {
                n2 = NumericShaper.search(c, arrn, n + 1, arrn.length - n - 1);
            }
        }
        if ((n2 & 1) == 1) {
            bl = true;
        }
        this.stCache = n2;
        return bl;
    }

    private Range rangeForCodePoint(int n) {
        if (this.currentRange.inRange(n)) {
            return this.currentRange;
        }
        Range[] arrrange = this.rangeArray;
        if (arrrange.length > 3) {
            int n2 = 0;
            int n3 = arrrange.length - 1;
            while (n2 <= n3) {
                int n4 = (n2 + n3) / 2;
                Range range = arrrange[n4];
                if (n < range.start) {
                    n3 = n4 - 1;
                    continue;
                }
                if (n >= range.end) {
                    n2 = n4 + 1;
                    continue;
                }
                this.currentRange = range;
                return range;
            }
        } else {
            for (int i = 0; i < arrrange.length; ++i) {
                if (!arrrange[i].inRange(n)) continue;
                return arrrange[i];
            }
        }
        return Range.EUROPEAN;
    }

    private static int search(int n, int[] arrn, int n2, int n3) {
        int n4 = 1 << NumericShaper.getHighBit(n3);
        int n5 = n3 - n4;
        n3 = n4;
        int n6 = n2;
        n4 = n3;
        n2 = n6;
        if (n >= arrn[n6 + n5]) {
            n2 = n6 + n5;
            n4 = n3;
        }
        while (n4 > 1) {
            n4 = n3 = n4 >> 1;
            if (n < arrn[n2 + n3]) continue;
            n2 += n3;
            n4 = n3;
        }
        return n2;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void shapeContextually(char[] arrc, int n, int n2, int n3) {
        synchronized (this) {
            int n4;
            int n5 = (this.mask & true << n4) == 0 ? 0 : n4;
            int n6 = bases[n5];
            n4 = n5 == 16 ? 49 : 48;
            synchronized (NumericShaper.class) {
                void var2_2;
                int n7 = var2_2;
                int n8 = n5;
                n5 = n7;
                n7 = n4;
                void var3_3;
                while (n5 < var2_2 + var3_3) {
                    int n9 = arrc[n5];
                    if (n9 >= n7 && n9 <= 57) {
                        arrc[n5] = (char)(n9 + n6);
                    }
                    int n10 = n8;
                    int n11 = n7;
                    n4 = n6;
                    if (this.isStrongDirectional((char)n9)) {
                        int n12 = NumericShaper.getContextKey((char)n9);
                        n10 = n8;
                        n11 = n7;
                        n4 = n6;
                        if (n12 != n8) {
                            n6 = n12;
                            if ((this.mask & 4) != 0 && (n12 == 1 || n12 == 2)) {
                                n4 = 2;
                            } else if ((this.mask & 2) != 0 && (n12 == 1 || n12 == 2)) {
                                n4 = 1;
                            } else {
                                n4 = n12;
                                if ((this.mask & 1 << n12) == 0) {
                                    n4 = 0;
                                }
                            }
                            n7 = bases[n4];
                            n4 = n4 == 16 ? 49 : 48;
                            n11 = n4;
                            n4 = n7;
                            n10 = n6;
                        }
                    }
                    ++n5;
                    n8 = n10;
                    n7 = n11;
                    n6 = n4;
                }
                return;
            }
        }
    }

    private void shapeContextually(char[] arrc, int n, int n2, Range range) {
        Range range2;
        block9 : {
            block8 : {
                if (range == null) break block8;
                range2 = range;
                if (this.rangeSet.contains((Object)range)) break block9;
            }
            range2 = Range.EUROPEAN;
        }
        range = range2;
        int n3 = range2.getDigitBase();
        char c = (char)(range2.getNumericBase() + 48);
        range2 = range;
        for (int i = n; i < n + n2; ++i) {
            int n4;
            char c2;
            char c3 = arrc[i];
            if (c3 >= c && c3 <= '9') {
                arrc[i] = (char)(c3 + n3);
                range = range2;
                n4 = n3;
                c2 = c;
            } else {
                range = range2;
                n4 = n3;
                c2 = c;
                if (this.isStrongDirectional(c3)) {
                    Range range3 = this.rangeForCodePoint(c3);
                    range = range2;
                    n4 = n3;
                    c2 = c;
                    if (range3 != range2) {
                        range = range3;
                        n4 = range3.getDigitBase();
                        c2 = (char)(range3.getNumericBase() + 48);
                    }
                }
            }
            range2 = range;
            n3 = n4;
            c = c2;
        }
    }

    private void shapeNonContextually(char[] arrc, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = 48;
        char[] arrc2 = this.shapingRange;
        if (arrc2 != null) {
            n4 = ((Range)arrc2).getDigitBase();
            n6 = (char)(this.shapingRange.getNumericBase() + 48);
        } else {
            arrc2 = bases;
            n5 = this.key;
            n4 = n3 = arrc2[n5];
            if (n5 == 16) {
                n6 = (char)(48 + 1);
                n4 = n3;
            }
        }
        for (n3 = n; n3 < n + n2; ++n3) {
            n5 = arrc[n3];
            if (n5 < n6 || n5 > 57) continue;
            arrc[n3] = (char)(n5 + n4);
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        int n;
        Object object = this.shapingRange;
        if (object != null && (n = Range.toRangeIndex(object)) >= 0) {
            this.key = n;
        }
        if ((object = this.rangeSet) != null) {
            n = this.mask;
            this.mask = Range.toRangeMask((Set)object) | n;
        }
        objectOutputStream.defaultWriteObject();
    }

    public boolean equals(Object object) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        if (object != null) {
            NumericShaper numericShaper;
            block10 : {
                block11 : {
                    numericShaper = (NumericShaper)object;
                    if (this.rangeSet == null) break block10;
                    if (numericShaper.rangeSet == null) break block11;
                    if (this.isContextual() == numericShaper.isContextual() && this.rangeSet.equals(numericShaper.rangeSet) && this.shapingRange == numericShaper.shapingRange) {
                        bl4 = true;
                    }
                    return bl4;
                }
                bl4 = this.isContextual() == numericShaper.isContextual() && this.rangeSet.equals(Range.maskToRangeSet(numericShaper.mask)) && this.shapingRange == Range.indexToRange(numericShaper.key) ? true : bl;
                return bl4;
            }
            if (numericShaper.rangeSet != null) {
                Set set = Range.maskToRangeSet(this.mask);
                object = Range.indexToRange(this.key);
                bl4 = this.isContextual() == numericShaper.isContextual() && set.equals(numericShaper.rangeSet) && object == numericShaper.shapingRange ? true : bl2;
                return bl4;
            }
            bl4 = bl3;
            try {
                if (numericShaper.mask == this.mask) {
                    int n = numericShaper.key;
                    int n2 = this.key;
                    bl4 = bl3;
                    if (n == n2) {
                        bl4 = true;
                    }
                }
                return bl4;
            }
            catch (ClassCastException classCastException) {
                // empty catch block
            }
        }
        return false;
    }

    public Set<Range> getRangeSet() {
        Set<Range> set = this.rangeSet;
        if (set != null) {
            return EnumSet.copyOf(set);
        }
        return Range.maskToRangeSet(this.mask);
    }

    public int getRanges() {
        return this.mask & Integer.MAX_VALUE;
    }

    public int hashCode() {
        int n = this.mask;
        Set<Range> set = this.rangeSet;
        int n2 = n;
        if (set != null) {
            n2 = n & Integer.MIN_VALUE ^ set.hashCode();
        }
        return n2;
    }

    public boolean isContextual() {
        boolean bl = (this.mask & Integer.MIN_VALUE) != 0;
        return bl;
    }

    public void shape(char[] arrc, int n, int n2) {
        this.checkParams(arrc, n, n2);
        if (this.isContextual()) {
            if (this.rangeSet == null) {
                this.shapeContextually(arrc, n, n2, this.key);
            } else {
                this.shapeContextually(arrc, n, n2, this.shapingRange);
            }
        } else {
            this.shapeNonContextually(arrc, n, n2);
        }
    }

    public void shape(char[] arrc, int n, int n2, int n3) {
        this.checkParams(arrc, n, n2);
        if (this.isContextual()) {
            n3 = NumericShaper.getKeyFromMask(n3);
            if (this.rangeSet == null) {
                this.shapeContextually(arrc, n, n2, n3);
            } else {
                this.shapeContextually(arrc, n, n2, Range.values()[n3]);
            }
        } else {
            this.shapeNonContextually(arrc, n, n2);
        }
    }

    public void shape(char[] arrc, int n, int n2, Range range) {
        this.checkParams(arrc, n, n2);
        if (range != null) {
            if (this.isContextual()) {
                if (this.rangeSet != null) {
                    this.shapeContextually(arrc, n, n2, range);
                } else {
                    int n3 = Range.toRangeIndex(range);
                    if (n3 >= 0) {
                        this.shapeContextually(arrc, n, n2, n3);
                    } else {
                        this.shapeContextually(arrc, n, n2, this.shapingRange);
                    }
                }
            } else {
                this.shapeNonContextually(arrc, n, n2);
            }
            return;
        }
        throw new NullPointerException("context is null");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append("[contextual:");
        stringBuilder.append(this.isContextual());
        if (this.isContextual()) {
            Range range;
            stringBuilder.append(", context:");
            Range range2 = range = this.shapingRange;
            if (range == null) {
                range2 = Range.values()[this.key];
            }
            stringBuilder.append((Object)range2);
        }
        if (this.rangeSet == null) {
            stringBuilder.append(", range(s): ");
            boolean bl = true;
            for (int i = 0; i < 19; ++i) {
                boolean bl2 = bl;
                if ((this.mask & 1 << i) != 0) {
                    if (bl) {
                        bl = false;
                    } else {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append((Object)Range.values()[i]);
                    bl2 = bl;
                }
                bl = bl2;
            }
        } else {
            stringBuilder.append(", range set: ");
            stringBuilder.append(this.rangeSet);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public static enum Range {
        EUROPEAN(48, 0, 768),
        ARABIC(1632, 1536, 1920),
        EASTERN_ARABIC(1776, 1536, 1920),
        DEVANAGARI(2406, 2304, 2432),
        BENGALI(2534, 2432, 2560),
        GURMUKHI(2662, 2560, 2688),
        GUJARATI(2790, 2816, 2944),
        ORIYA(2918, 2816, 2944),
        TAMIL(3046, 2944, 3072),
        TELUGU(3174, 3072, 3200),
        KANNADA(3302, 3200, 3328),
        MALAYALAM(3430, 3328, 3456),
        THAI(3664, 3584, 3712),
        LAO(3792, 3712, 3840),
        TIBETAN(3872, 3840, 4096),
        MYANMAR(4160, 4096, 4224),
        ETHIOPIC(4969, 4608, 4992){

            @Override
            char getNumericBase() {
                return '\u0001';
            }
        }
        ,
        KHMER(6112, 6016, 6144),
        MONGOLIAN(6160, 6144, 6400),
        NKO(1984, 1984, 2048),
        MYANMAR_SHAN(4240, 4096, 4256),
        LIMBU(6470, 6400, 6480),
        NEW_TAI_LUE(6608, 6528, 6624),
        BALINESE(6992, 6912, 7040),
        SUNDANESE(7088, 7040, 7104),
        LEPCHA(7232, 7168, 7248),
        OL_CHIKI(7248, 7248, 7296),
        VAI(42528, 42240, 42560),
        SAURASHTRA(43216, 43136, 43232),
        KAYAH_LI(43264, 43264, 43312),
        CHAM(43600, 43520, 43616),
        TAI_THAM_HORA(6784, 6688, 6832),
        TAI_THAM_THAM(6800, 6688, 6832),
        JAVANESE(43472, 43392, 43488),
        MEETEI_MAYEK(44016, 43968, 44032);
        
        private final int base;
        private final int end;
        private final int start;

        private Range(int n2, int n3, int n4) {
            this.base = n2 - (this.getNumericBase() + 48);
            this.start = n3;
            this.end = n4;
        }

        private int getDigitBase() {
            return this.base;
        }

        private boolean inRange(int n) {
            boolean bl = this.start <= n && n < this.end;
            return bl;
        }

        private static Range indexToRange(int n) {
            Range range = n < 19 ? Range.values()[n] : null;
            return range;
        }

        private static Set<Range> maskToRangeSet(int n) {
            EnumSet<Range> enumSet = EnumSet.noneOf(Range.class);
            Range[] arrrange = Range.values();
            for (int i = 0; i < 19; ++i) {
                if ((1 << i & n) == 0) continue;
                enumSet.add(arrrange[i]);
            }
            return enumSet;
        }

        private static int toRangeIndex(Range range) {
            int n = range.ordinal();
            if (n >= 19) {
                n = -1;
            }
            return n;
        }

        private static int toRangeMask(Set<Range> object) {
            int n = 0;
            object = object.iterator();
            while (object.hasNext()) {
                int n2 = ((Range)((Object)object.next())).ordinal();
                int n3 = n;
                if (n2 < 19) {
                    n3 = n | 1 << n2;
                }
                n = n3;
            }
            return n;
        }

        char getNumericBase() {
            return '\u0000';
        }

    }

}

