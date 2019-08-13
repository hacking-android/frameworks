/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.UBiDiProps;
import android.icu.text.ArabicShapingException;
import dalvik.annotation.compat.UnsupportedAppUsage;

public final class ArabicShaping {
    private static final int ALEFTYPE = 32;
    private static final int DESHAPE_MODE = 1;
    public static final int DIGITS_AN2EN = 64;
    public static final int DIGITS_EN2AN = 32;
    public static final int DIGITS_EN2AN_INIT_AL = 128;
    public static final int DIGITS_EN2AN_INIT_LR = 96;
    public static final int DIGITS_MASK = 224;
    public static final int DIGITS_NOOP = 0;
    public static final int DIGIT_TYPE_AN = 0;
    public static final int DIGIT_TYPE_AN_EXTENDED = 256;
    public static final int DIGIT_TYPE_MASK = 256;
    private static final char HAMZA06_CHAR = '\u0621';
    private static final char HAMZAFE_CHAR = '\ufe80';
    private static final int IRRELEVANT = 4;
    public static final int LAMALEF_AUTO = 65536;
    public static final int LAMALEF_BEGIN = 3;
    public static final int LAMALEF_END = 2;
    public static final int LAMALEF_MASK = 65539;
    public static final int LAMALEF_NEAR = 1;
    public static final int LAMALEF_RESIZE = 0;
    private static final char LAMALEF_SPACE_SUB = '\uffff';
    private static final int LAMTYPE = 16;
    private static final char LAM_CHAR = '\u0644';
    public static final int LENGTH_FIXED_SPACES_AT_BEGINNING = 3;
    public static final int LENGTH_FIXED_SPACES_AT_END = 2;
    public static final int LENGTH_FIXED_SPACES_NEAR = 1;
    public static final int LENGTH_GROW_SHRINK = 0;
    public static final int LENGTH_MASK = 65539;
    public static final int LETTERS_MASK = 24;
    public static final int LETTERS_NOOP = 0;
    public static final int LETTERS_SHAPE = 8;
    public static final int LETTERS_SHAPE_TASHKEEL_ISOLATED = 24;
    public static final int LETTERS_UNSHAPE = 16;
    private static final int LINKL = 2;
    private static final int LINKR = 1;
    private static final int LINK_MASK = 3;
    private static final char NEW_TAIL_CHAR = '\ufe73';
    private static final char OLD_TAIL_CHAR = '\u200b';
    public static final int SEEN_MASK = 7340032;
    public static final int SEEN_TWOCELL_NEAR = 2097152;
    private static final char SHADDA06_CHAR = '\u0651';
    private static final char SHADDA_CHAR = '\ufe7c';
    private static final char SHADDA_TATWEEL_CHAR = '\ufe7d';
    private static final int SHAPE_MODE = 0;
    public static final int SHAPE_TAIL_NEW_UNICODE = 134217728;
    public static final int SHAPE_TAIL_TYPE_MASK = 134217728;
    public static final int SPACES_RELATIVE_TO_TEXT_BEGIN_END = 67108864;
    public static final int SPACES_RELATIVE_TO_TEXT_MASK = 67108864;
    private static final char SPACE_CHAR = ' ';
    public static final int TASHKEEL_BEGIN = 262144;
    public static final int TASHKEEL_END = 393216;
    public static final int TASHKEEL_MASK = 917504;
    public static final int TASHKEEL_REPLACE_BY_TATWEEL = 786432;
    public static final int TASHKEEL_RESIZE = 524288;
    private static final char TASHKEEL_SPACE_SUB = '\ufffe';
    private static final char TATWEEL_CHAR = '\u0640';
    public static final int TEXT_DIRECTION_LOGICAL = 0;
    public static final int TEXT_DIRECTION_MASK = 4;
    public static final int TEXT_DIRECTION_VISUAL_LTR = 4;
    public static final int TEXT_DIRECTION_VISUAL_RTL = 0;
    public static final int YEHHAMZA_MASK = 58720256;
    public static final int YEHHAMZA_TWOCELL_NEAR = 16777216;
    private static final char YEH_HAMZAFE_CHAR = '\ufe89';
    private static final char YEH_HAMZA_CHAR = '\u0626';
    private static final int[] araLink;
    private static int[] convertFEto06;
    private static final char[] convertNormalizedLamAlef;
    private static final int[] irrelevantPos;
    private static final int[] presLink;
    private static final int[][][] shapeTable;
    private static final int[] tailFamilyIsolatedFinal;
    private static final int[] tashkeelMedial;
    private static final char[] yehHamzaToYeh;
    private boolean isLogical;
    private final int options;
    private boolean spacesRelativeToTextBeginEnd;
    private char tailChar;

    static {
        irrelevantPos = new int[]{0, 2, 4, 6, 8, 10, 12, 14};
        tailFamilyIsolatedFinal = new int[]{1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1};
        tashkeelMedial = new int[]{0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1};
        yehHamzaToYeh = new char[]{'\ufffffeef', '\ufffffef0'};
        convertNormalizedLamAlef = new char[]{'\u0622', '\u0623', '\u0625', '\u0627'};
        araLink = new int[]{4385, 4897, 5377, 5921, 6403, 7457, 7939, 8961, 9475, 10499, 11523, 12547, 13571, 14593, 15105, 15617, 16129, 16643, 17667, 18691, 19715, 20739, 21763, 22787, 23811, 0, 0, 0, 0, 0, 3, 24835, 25859, 26883, 27923, 28931, 29955, 30979, 32001, 32513, 33027, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 34049, 34561, 35073, 35585, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 33, 33, 0, 33, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 3, 3, 3, 3, 1, 1};
        presLink = new int[]{3, 3, 3, 0, 3, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 32, 33, 32, 33, 0, 1, 32, 33, 0, 2, 3, 1, 32, 33, 0, 2, 3, 1, 0, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 16, 18, 19, 17, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 1, 0, 1, 0, 2, 3, 1, 0, 1, 0, 1, 0, 1, 0, 1};
        convertFEto06 = new int[]{1611, 1611, 1612, 1612, 1613, 1613, 1614, 1614, 1615, 1615, 1616, 1616, 1617, 1617, 1618, 1618, 1569, 1570, 1570, 1571, 1571, 1572, 1572, 1573, 1573, 1574, 1574, 1574, 1574, 1575, 1575, 1576, 1576, 1576, 1576, 1577, 1577, 1578, 1578, 1578, 1578, 1579, 1579, 1579, 1579, 1580, 1580, 1580, 1580, 1581, 1581, 1581, 1581, 1582, 1582, 1582, 1582, 1583, 1583, 1584, 1584, 1585, 1585, 1586, 1586, 1587, 1587, 1587, 1587, 1588, 1588, 1588, 1588, 1589, 1589, 1589, 1589, 1590, 1590, 1590, 1590, 1591, 1591, 1591, 1591, 1592, 1592, 1592, 1592, 1593, 1593, 1593, 1593, 1594, 1594, 1594, 1594, 1601, 1601, 1601, 1601, 1602, 1602, 1602, 1602, 1603, 1603, 1603, 1603, 1604, 1604, 1604, 1604, 1605, 1605, 1605, 1605, 1606, 1606, 1606, 1606, 1607, 1607, 1607, 1607, 1608, 1608, 1609, 1609, 1610, 1610, 1610, 1610, 1628, 1628, 1629, 1629, 1630, 1630, 1631, 1631};
        int[] arrn = new int[]{0, 1, 0, 3};
        int[] arrn2 = new int[]{0, 0, 2, 2};
        int[] arrn3 = new int[]{0, 0, 1, 2};
        int[] arrn4 = new int[]{0, 1, 1, 2};
        int[] arrn5 = new int[]{0, 1, 1, 3};
        int[] arrn6 = new int[]{0, 1, 0, 3};
        int[] arrn7 = new int[]{0, 1, 0, 3};
        int[] arrn8 = new int[]{0, 0, 1, 2};
        shapeTable = new int[][][]{{{0, 0, 0, 0}, {0, 0, 0, 0}, arrn, {0, 1, 0, 1}}, {arrn2, arrn3, arrn4, arrn5}, {{0, 0, 0, 0}, {0, 0, 0, 0}, arrn6, arrn7}, {arrn8, {0, 0, 1, 2}, {0, 1, 1, 2}, {0, 1, 1, 3}}};
    }

    @UnsupportedAppUsage
    public ArabicShaping(int n) {
        this.options = n;
        if ((n & 224) <= 128) {
            boolean bl = true;
            boolean bl2 = (n & 4) == 0;
            this.isLogical = bl2;
            bl2 = (n & 67108864) == 67108864 ? bl : false;
            this.spacesRelativeToTextBeginEnd = bl2;
            this.tailChar = (n & 134217728) == 134217728 ? (char)65139 : (char)8203;
            return;
        }
        throw new IllegalArgumentException("bad DIGITS options");
    }

    private int calculateSize(char[] arrc, int n, int n2) {
        int n3;
        block14 : {
            int n4;
            block15 : {
                block12 : {
                    block13 : {
                        n3 = n2;
                        n4 = this.options & 24;
                        if (n4 == 8) break block12;
                        if (n4 == 16) break block13;
                        if (n4 == 24) break block12;
                        break block14;
                    }
                    for (n4 = n; n4 < n + n2; ++n4) {
                        int n5 = n3;
                        if (ArabicShaping.isLamAlefChar(arrc[n4])) {
                            n5 = n3 + 1;
                        }
                        n3 = n5;
                    }
                    break block14;
                }
                if (!this.isLogical) break block15;
                for (n4 = n; n4 < n + n2 - 1; ++n4) {
                    int n6;
                    block17 : {
                        block16 : {
                            if (arrc[n4] == '\u0644' && ArabicShaping.isAlefChar(arrc[n4 + 1])) break block16;
                            n6 = n3;
                            if (!ArabicShaping.isTashkeelCharFE(arrc[n4])) break block17;
                        }
                        n6 = n3 - 1;
                    }
                    n3 = n6;
                }
                break block14;
            }
            for (n4 = n + 1; n4 < n + n2; ++n4) {
                int n7;
                block19 : {
                    block18 : {
                        if (arrc[n4] == '\u0644' && ArabicShaping.isAlefChar(arrc[n4 - 1])) break block18;
                        n7 = n3;
                        if (!ArabicShaping.isTashkeelCharFE(arrc[n4])) break block19;
                    }
                    n7 = n3 - 1;
                }
                n3 = n7;
            }
        }
        return n3;
    }

    private static char changeLamAlef(char c) {
        if (c != '\u0622') {
            if (c != '\u0623') {
                if (c != '\u0625') {
                    if (c != '\u0627') {
                        return '\u0000';
                    }
                    return '\u065f';
                }
                return '\u065e';
            }
            return '\u065d';
        }
        return '\u065c';
    }

    private static int countSpaceSub(char[] arrc, int n, char c) {
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3 = n2;
            if (arrc[i] == c) {
                n3 = n2 + 1;
            }
            n2 = n3;
        }
        return n2;
    }

    private static int countSpacesLeft(char[] arrc, int n, int n2) {
        for (int i = n; i < n + n2; ++i) {
            if (arrc[i] == ' ') continue;
            return i - n;
        }
        return n2;
    }

    private static int countSpacesRight(char[] arrc, int n, int n2) {
        int n3;
        int n4 = n + n2;
        while ((n3 = n4 - 1) >= n) {
            n4 = n3;
            if (arrc[n3] == ' ') continue;
            return n + n2 - 1 - n3;
        }
        return n2;
    }

    private int deShapeUnicode(char[] arrc, int n, int n2, int n3) throws ArabicShapingException {
        block0 : {
            n3 = this.deshapeNormalize(arrc, n, n2);
            if (n3 == 0) break block0;
            n2 = this.expandCompositChar(arrc, n, n2, n3, 1);
        }
        return n2;
    }

    private int deshapeNormalize(char[] arrc, int n, int n2) {
        int n3;
        int n4 = 0;
        int n5 = this.options;
        boolean bl = false;
        boolean bl2 = (n5 & 58720256) == 16777216;
        if ((this.options & 7340032) == 2097152) {
            bl = true;
        }
        n5 = n3 = n;
        n = n4;
        while ((n4 = n5) < n3 + n2) {
            char c = arrc[n4];
            if (bl2 && (c == '\u0621' || c == '\ufe80') && n4 < n2 - 1 && ArabicShaping.isAlefMaksouraChar(arrc[n4 + 1])) {
                arrc[n4] = (char)32;
                arrc[n4 + 1] = (char)1574;
                n5 = n;
            } else if (bl && ArabicShaping.isTailChar(c) && n4 < n2 - 1 && ArabicShaping.isSeenTailFamilyChar(arrc[n4 + 1]) == 1) {
                arrc[n4] = (char)32;
                n5 = n;
            } else {
                n5 = n;
                if (c >= '\ufe70') {
                    n5 = n;
                    if (c <= '\ufefc') {
                        n5 = n;
                        if (ArabicShaping.isLamAlefChar(c)) {
                            n5 = n + 1;
                        }
                        arrc[n4] = (char)convertFEto06[c - 65136];
                    }
                }
            }
            n = n5;
            n5 = ++n4;
        }
        return n;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int expandCompositChar(char[] arrc, int n, int n2, int n3, int n4) throws ArabicShapingException {
        int n5 = this.options;
        int n6 = 65539 & n5;
        if (!this.isLogical && !this.spacesRelativeToTextBeginEnd) {
            if (n6 != 2) {
                if (n6 == 3) {
                    n6 = 2;
                }
            } else {
                n6 = 3;
            }
        }
        if (n4 == 1) {
            if (n6 == 65536) {
                if (this.isLogical) {
                    boolean bl = this.expandCompositCharAtEnd(arrc, n, n2, n3);
                    if (bl) {
                        bl = this.expandCompositCharAtBegin(arrc, n, n2, n3);
                    }
                    boolean bl2 = bl;
                    if (bl) {
                        bl2 = this.expandCompositCharAtNear(arrc, n, n2, 0, 0, 1);
                    }
                    if (bl2) throw new ArabicShapingException("No spacefor lamalef");
                    return n2;
                }
                boolean bl = this.expandCompositCharAtBegin(arrc, n, n2, n3);
                if (bl) {
                    bl = this.expandCompositCharAtEnd(arrc, n, n2, n3);
                }
                boolean bl3 = bl;
                if (bl) {
                    bl3 = this.expandCompositCharAtNear(arrc, n, n2, 0, 0, 1);
                }
                if (bl3) throw new ArabicShapingException("No spacefor lamalef");
                return n2;
            }
            if (n6 == 2) {
                if (this.expandCompositCharAtEnd(arrc, n, n2, n3)) throw new ArabicShapingException("No spacefor lamalef");
                return n2;
            }
            if (n6 == 3) {
                if (this.expandCompositCharAtBegin(arrc, n, n2, n3)) throw new ArabicShapingException("No spacefor lamalef");
                return n2;
            }
            if (n6 == 1) {
                if (this.expandCompositCharAtNear(arrc, n, n2, 0, 0, 1)) throw new ArabicShapingException("No spacefor lamalef");
                return n2;
            }
            if (n6 != 0) return n2;
            n6 = n + n2;
            n4 = n6 + n3;
            while (--n6 >= n) {
                char c = arrc[n6];
                if (ArabicShaping.isNormalizedLamAlefChar(c)) {
                    arrc[--n4] = (char)1604;
                    arrc[--n4] = convertNormalizedLamAlef[c - 1628];
                    continue;
                }
                arrc[--n4] = c;
            }
            return n2 + n3;
        }
        if ((n5 & 7340032) == 2097152) {
            if (this.expandCompositCharAtNear(arrc, n, n2, 0, 1, 0)) throw new ArabicShapingException("No space for Seen tail expansion");
        }
        if ((n5 & 58720256) != 16777216) return n2;
        if (this.expandCompositCharAtNear(arrc, n, n2, 1, 0, 0)) throw new ArabicShapingException("No space for YehHamza expansion");
        return n2;
    }

    private boolean expandCompositCharAtBegin(char[] arrc, int n, int n2, int n3) {
        if (n3 > ArabicShaping.countSpacesRight(arrc, n, n2)) {
            return true;
        }
        n3 = n + n2 - n3;
        n2 = n + n2;
        while (--n3 >= n) {
            char c = arrc[n3];
            if (ArabicShaping.isNormalizedLamAlefChar(c)) {
                arrc[--n2] = (char)1604;
                arrc[--n2] = convertNormalizedLamAlef[c - 1628];
                continue;
            }
            arrc[--n2] = c;
        }
        return false;
    }

    private boolean expandCompositCharAtEnd(char[] arrc, int n, int n2, int n3) {
        if (n3 > ArabicShaping.countSpacesLeft(arrc, n, n2)) {
            return true;
        }
        int n4 = n + n3;
        n3 = n;
        while (n4 < n + n2) {
            char c = arrc[n4];
            if (ArabicShaping.isNormalizedLamAlefChar(c)) {
                int n5 = n3 + 1;
                arrc[n3] = convertNormalizedLamAlef[c - 1628];
                n3 = n5 + 1;
                arrc[n5] = (char)1604;
            } else {
                arrc[n3] = c;
                ++n3;
            }
            ++n4;
        }
        return false;
    }

    private boolean expandCompositCharAtNear(char[] arrc, int n, int n2, int n3, int n4, int n5) {
        int n6;
        if (ArabicShaping.isNormalizedLamAlefChar(arrc[n])) {
            return true;
        }
        n2 = n + n2;
        while ((n6 = n2 - 1) >= n) {
            char c = arrc[n6];
            if (n5 == 1 && ArabicShaping.isNormalizedLamAlefChar(c)) {
                if (n6 > n && arrc[n6 - 1] == ' ') {
                    arrc[n6] = (char)1604;
                    n2 = n6 - 1;
                    arrc[n2] = convertNormalizedLamAlef[c - 1628];
                    continue;
                }
                return true;
            }
            if (n4 == 1 && ArabicShaping.isSeenTailFamilyChar(c) == 1) {
                if (n6 > n && arrc[n6 - 1] == ' ') {
                    arrc[n6 - 1] = this.tailChar;
                    n2 = n6;
                    continue;
                }
                return true;
            }
            n2 = n6;
            if (n3 != 1) continue;
            n2 = n6;
            if (!ArabicShaping.isYehHamzaChar(c)) continue;
            if (n6 > n && arrc[n6 - 1] == ' ') {
                arrc[n6] = yehHamzaToYeh[c - 65161];
                arrc[n6 - 1] = (char)65152;
                n2 = n6;
                continue;
            }
            return true;
        }
        return false;
    }

    private static int flipArray(char[] arrc, int n, int n2, int n3) {
        int n4;
        if (n3 > n) {
            do {
                n4 = n++;
                if (n3 < n2) {
                    arrc[n] = arrc[n3];
                    ++n3;
                    continue;
                }
                break;
            } while (true);
        } else {
            n4 = n2;
        }
        return n4;
    }

    private static int getLink(char c) {
        if (c >= '\u0622' && c <= '\u06d3') {
            return araLink[c - 1570];
        }
        if (c == '\u200d') {
            return 3;
        }
        if (c >= '\u206d' && c <= '\u206f') {
            return 4;
        }
        if (c >= '\ufe70' && c <= '\ufefc') {
            return presLink[c - 65136];
        }
        return 0;
    }

    private int handleGeneratedSpaces(char[] arrc, int n, int n2) {
        int n3;
        int n4 = n2;
        int n5 = this.options;
        n2 = 65539 & n5;
        int n6 = n5 & 917504;
        int n7 = 0;
        boolean bl = false;
        n5 = n6;
        int n8 = n2;
        if ((this.isLogical ^ true) & (this.spacesRelativeToTextBeginEnd ^ true)) {
            if (n2 != 2) {
                if (n2 == 3) {
                    n2 = 2;
                }
            } else {
                n2 = 3;
            }
            if (n6 != 262144) {
                if (n6 != 393216) {
                    n5 = n6;
                    n8 = n2;
                } else {
                    n5 = 262144;
                    n8 = n2;
                }
            } else {
                n5 = 393216;
                n8 = n2;
            }
        }
        if (n8 == 1) {
            for (n = n2 = n; n < n2 + n4; ++n) {
                if (arrc[n] != '\uffff') continue;
                arrc[n] = (char)32;
            }
            n3 = n4;
        } else {
            int n9 = n + n4;
            n6 = ArabicShaping.countSpaceSub(arrc, n4, '\uffff');
            n3 = ArabicShaping.countSpaceSub(arrc, n4, '\ufffe');
            if (n8 == 2) {
                n7 = 1;
            }
            if (n5 == 393216) {
                bl = true;
            }
            n2 = n6;
            if (n7 != 0) {
                n2 = n6;
                if (n8 == 2) {
                    ArabicShaping.shiftArray(arrc, n, n9, '\uffff');
                    do {
                        n2 = --n6;
                        if (n6 <= n) break;
                        arrc[n6] = (char)32;
                    } while (true);
                }
            }
            if (bl && n5 == 393216) {
                ArabicShaping.shiftArray(arrc, n, n9, '\ufffe');
                do {
                    n6 = --n3;
                    if (n3 > n) {
                        arrc[n3] = (char)32;
                        continue;
                    }
                    break;
                } while (true);
            } else {
                n6 = n3;
            }
            boolean bl2 = false;
            bl = false;
            if (n8 == 0) {
                bl2 = true;
            }
            if (n5 == 524288) {
                bl = true;
            }
            n3 = n4;
            n7 = n2;
            if (bl2) {
                n3 = n4;
                n7 = n2;
                if (n8 == 0) {
                    ArabicShaping.shiftArray(arrc, n, n9, '\uffff');
                    n7 = ArabicShaping.flipArray(arrc, n, n9, n2);
                    n3 = n7 - n;
                }
            }
            n2 = n3;
            n4 = n6;
            if (bl) {
                n2 = n3;
                n4 = n6;
                if (n5 == 524288) {
                    ArabicShaping.shiftArray(arrc, n, n9, '\ufffe');
                    n4 = ArabicShaping.flipArray(arrc, n, n9, n6);
                    n2 = n4 - n;
                }
            }
            n3 = 0;
            n6 = 0;
            if (n8 == 3 || n8 == 65536) {
                n3 = 1;
            }
            if (n5 == 262144) {
                n6 = 1;
            }
            if (n3 != 0 && (n8 == 3 || n8 == 65536)) {
                ArabicShaping.shiftArray(arrc, n, n9, '\uffff');
                for (n3 = ArabicShaping.flipArray((char[])arrc, (int)n, (int)n9, (int)n7); n3 < n9; ++n3) {
                    arrc[n3] = (char)32;
                }
            }
            n3 = n2;
            if (n6 != 0) {
                n3 = n2;
                if (n5 == 262144) {
                    ArabicShaping.shiftArray(arrc, n, n9, '\ufffe');
                    n = ArabicShaping.flipArray(arrc, n, n9, n4);
                    do {
                        n3 = n2;
                        if (n >= n9) break;
                        arrc[n] = (char)32;
                        ++n;
                    } while (true);
                }
            }
        }
        return n3;
    }

    private static int handleTashkeelWithTatweel(char[] arrc, int n) {
        for (int i = 0; i < n; ++i) {
            if (ArabicShaping.isTashkeelOnTatweelChar(arrc[i]) == 1) {
                arrc[i] = (char)1600;
                continue;
            }
            if (ArabicShaping.isTashkeelOnTatweelChar(arrc[i]) == 2) {
                arrc[i] = (char)65149;
                continue;
            }
            if (ArabicShaping.isIsolatedTashkeelChar(arrc[i]) != 1 || arrc[i] == '\ufe7c') continue;
            arrc[i] = (char)32;
        }
        return n;
    }

    private int internalShape(char[] arrc, int n, int n2, char[] arrc2, int n3, int n4) throws ArabicShapingException {
        if (n2 == 0) {
            return 0;
        }
        if (n4 == 0) {
            n3 = this.options;
            if ((n3 & 24) != 0 && (n3 & 65539) == 0) {
                return this.calculateSize(arrc, n, n2);
            }
            return n2;
        }
        char[] arrc3 = new char[n2 * 2];
        System.arraycopy(arrc, n, arrc3, 0, n2);
        if (this.isLogical) {
            ArabicShaping.invertBuffer(arrc3, 0, n2);
        }
        n = n2;
        int n5 = this.options;
        int n6 = n5 & 24;
        if (n6 != 8) {
            if (n6 != 16) {
                if (n6 == 24) {
                    n = this.shapeUnicode(arrc3, 0, n2, n4, 1);
                }
            } else {
                n = this.deShapeUnicode(arrc3, 0, n2, n4);
            }
        } else if ((n5 & 917504) != 0 && (n5 & 917504) != 786432) {
            n = this.shapeUnicode(arrc3, 0, n2, n4, 2);
        } else {
            n = this.shapeUnicode(arrc3, 0, n2, n4, 0);
            if ((this.options & 917504) == 786432) {
                n = ArabicShaping.handleTashkeelWithTatweel(arrc3, n2);
            }
        }
        if (n <= n4) {
            n2 = this.options;
            if ((n2 & 224) != 0) {
                char c;
                if ((n2 &= 256) != 0) {
                    if (n2 != 256) {
                        n2 = 48;
                        c = n2;
                    } else {
                        n2 = 1776;
                        c = n2;
                    }
                } else {
                    n2 = 1632;
                    c = n2;
                }
                n2 = this.options & 224;
                if (n2 != 32) {
                    if (n2 != 64) {
                        if (n2 != 96) {
                            if (n2 == 128) {
                                this.shapeToArabicDigitsWithContext(arrc3, 0, n, c, true);
                            }
                        } else {
                            this.shapeToArabicDigitsWithContext(arrc3, 0, n, c, false);
                        }
                    } else {
                        n4 = (char)(c + 9);
                        for (n2 = 0; n2 < n; ++n2) {
                            n6 = arrc3[n2];
                            if (n6 > n4 || n6 < c) continue;
                            arrc3[n2] = (char)(arrc3[n2] + (48 - c));
                        }
                    }
                } else {
                    for (n2 = 0; n2 < n; ++n2) {
                        n4 = arrc3[n2];
                        if (n4 > 57 || n4 < 48) continue;
                        arrc3[n2] = (char)(arrc3[n2] + (c - 48));
                    }
                }
            }
            if (this.isLogical) {
                ArabicShaping.invertBuffer(arrc3, 0, n);
            }
            System.arraycopy(arrc3, 0, arrc2, n3, n);
            return n;
        }
        throw new ArabicShapingException("not enough room for result data");
    }

    private static void invertBuffer(char[] arrc, int n, int n2) {
        int n3 = n;
        for (n = n + n2 - 1; n3 < n; ++n3, --n) {
            n2 = arrc[n3];
            arrc[n3] = arrc[n];
            arrc[n] = (char)n2;
        }
    }

    private static boolean isAlefChar(char c) {
        boolean bl = c == '\u0622' || c == '\u0623' || c == '\u0625' || c == '\u0627';
        return bl;
    }

    @UnsupportedAppUsage
    private static boolean isAlefMaksouraChar(char c) {
        boolean bl = c == '\ufeef' || c == '\ufef0' || c == '\u0649';
        return bl;
    }

    private static int isIsolatedTashkeelChar(char c) {
        if (c >= '\ufe70' && c <= '\ufe7f' && c != '\ufe73' && c != '\ufe75') {
            return 1 - tashkeelMedial[c - 65136];
        }
        return c >= '\ufc5e' && c <= '\ufc63';
    }

    private static boolean isLamAlefChar(char c) {
        boolean bl = c >= '\ufef5' && c <= '\ufefc';
        return bl;
    }

    private static boolean isNormalizedLamAlefChar(char c) {
        boolean bl = c >= '\u065c' && c <= '\u065f';
        return bl;
    }

    private static int isSeenFamilyChar(char c) {
        return c >= '\u0633' && c <= '\u0636';
    }

    @UnsupportedAppUsage
    private static int isSeenTailFamilyChar(char c) {
        if (c >= '\ufeb1' && c < '\ufebf') {
            return tailFamilyIsolatedFinal[c - 65201];
        }
        return 0;
    }

    @UnsupportedAppUsage
    private static boolean isTailChar(char c) {
        return c == '\u200b' || c == '\ufe73';
        {
        }
    }

    private static boolean isTashkeelChar(char c) {
        boolean bl = c >= '\u064b' && c <= '\u0652';
        return bl;
    }

    private static boolean isTashkeelCharFE(char c) {
        boolean bl = c != '\ufe75' && c >= '\ufe70' && c <= '\ufe7f';
        return bl;
    }

    private static int isTashkeelOnTatweelChar(char c) {
        if (c >= '\ufe70' && c <= '\ufe7f' && c != '\ufe73' && c != '\ufe75' && c != '\ufe7d') {
            return tashkeelMedial[c - 65136];
        }
        if (c >= '\ufcf2' && c <= '\ufcf4' || c == '\ufe7d') {
            return 2;
        }
        return 0;
    }

    @UnsupportedAppUsage
    private static boolean isYehHamzaChar(char c) {
        return c == '\ufe89' || c == '\ufe8a';
        {
        }
    }

    private int normalize(char[] arrc, int n, int n2) {
        int n3;
        int n4 = 0;
        int n5 = n3 = n;
        n = n4;
        while ((n4 = n5) < n3 + n2) {
            char c = arrc[n4];
            n5 = n;
            if (c >= '\ufe70') {
                n5 = n;
                if (c <= '\ufefc') {
                    n5 = n;
                    if (ArabicShaping.isLamAlefChar(c)) {
                        n5 = n + 1;
                    }
                    arrc[n4] = (char)convertFEto06[c - 65136];
                }
            }
            n = n5;
            n5 = ++n4;
        }
        return n;
    }

    private void shapeToArabicDigitsWithContext(char[] arrc, int n, int n2, char c, boolean bl) {
        UBiDiProps uBiDiProps = UBiDiProps.INSTANCE;
        c = (char)(c - 48);
        n2 = n + n2;
        boolean bl2 = bl;
        while (--n2 >= n) {
            char c2 = arrc[n2];
            int n3 = uBiDiProps.getClass(c2);
            if (n3 != 0 && n3 != 1) {
                if (n3 != 2) {
                    bl = n3 != 13 ? bl2 : true;
                } else {
                    bl = bl2;
                    if (bl2) {
                        bl = bl2;
                        if (c2 <= '9') {
                            arrc[n2] = (char)(c2 + c);
                            bl = bl2;
                        }
                    }
                }
            } else {
                bl = false;
            }
            bl2 = bl;
        }
    }

    private int shapeUnicode(char[] arrc, int n, int n2, int n3, int n4) throws ArabicShapingException {
        block33 : {
            int n5;
            block32 : {
                n5 = this.normalize(arrc, n, n2);
                n3 = n + n2 - 1;
                int n6 = ArabicShaping.getLink(arrc[n3]);
                int n7 = -2;
                int n8 = 0;
                int n9 = 0;
                int n10 = 0;
                int n11 = 0;
                int n12 = n3;
                boolean bl = false;
                int n13 = 0;
                boolean bl2 = false;
                while (n3 >= 0) {
                    int n14;
                    int n15;
                    int n16;
                    int n17;
                    int n18;
                    boolean bl3;
                    int n19;
                    boolean bl4;
                    block28 : {
                        block31 : {
                            block29 : {
                                block30 : {
                                    block27 : {
                                        if ((65280 & n6) != 0) break block27;
                                        n19 = n3;
                                        bl3 = bl;
                                        n17 = n6;
                                        bl4 = bl2;
                                        n16 = n13;
                                        n18 = n8;
                                        n14 = n9;
                                        n15 = n11;
                                        if (!ArabicShaping.isTashkeelChar(arrc[n3])) break block28;
                                    }
                                    n17 = n3 - 1;
                                    n15 = -2;
                                    n14 = n9;
                                    n9 = n17;
                                    while (n15 < 0) {
                                        if (n9 == -1) {
                                            n14 = 0;
                                            n15 = Integer.MAX_VALUE;
                                            continue;
                                        }
                                        n14 = ArabicShaping.getLink(arrc[n9]);
                                        if ((n14 & 4) == 0) {
                                            n15 = n9;
                                            continue;
                                        }
                                        --n9;
                                    }
                                    n19 = n3;
                                    bl3 = bl;
                                    n17 = n6;
                                    n9 = n11;
                                    if ((n6 & 32) > 0) {
                                        n19 = n3;
                                        bl3 = bl;
                                        n17 = n6;
                                        n9 = n11;
                                        if ((n11 & 16) > 0) {
                                            bl3 = true;
                                            char c = ArabicShaping.changeLamAlef(arrc[n3]);
                                            n19 = n3;
                                            if (c != '\u0000') {
                                                arrc[n3] = (char)65535;
                                                arrc[n12] = c;
                                                n19 = n12;
                                            }
                                            n9 = n10;
                                            n17 = ArabicShaping.getLink(c);
                                        }
                                    }
                                    if (n19 > 0 && arrc[n19 - 1] == ' ') {
                                        if (ArabicShaping.isSeenFamilyChar(arrc[n19]) == 1) {
                                            n6 = 1;
                                            n11 = n8;
                                        } else {
                                            n6 = n13;
                                            n11 = n8;
                                            if (arrc[n19] == '\u0626') {
                                                n11 = 1;
                                                n6 = n13;
                                            }
                                        }
                                    } else {
                                        n6 = n13;
                                        n11 = n8;
                                        if (n19 == 0) {
                                            if (ArabicShaping.isSeenFamilyChar(arrc[n19]) == 1) {
                                                n6 = 1;
                                                n11 = n8;
                                            } else {
                                                n6 = n13;
                                                n11 = n8;
                                                if (arrc[n19] == '\u0626') {
                                                    n11 = 1;
                                                    n6 = n13;
                                                }
                                            }
                                        }
                                    }
                                    n8 = ArabicShaping.specialChar(arrc[n19]);
                                    n3 = shapeTable[n14 & 3][n9 & 3][n17 & 3];
                                    if (n8 == 1) {
                                        n3 &= 1;
                                    } else if (n8 == 2) {
                                        n3 = n4 == 0 && (n9 & 2) != 0 && (n14 & 1) != 0 && arrc[n19] != '\u064c' && arrc[n19] != '\u064d' && ((n14 & 32) != 32 || (n9 & 16) != 16) ? 1 : (n4 == 2 && arrc[n19] == '\u0651' ? 1 : 0);
                                    }
                                    if (n8 != 2) break block29;
                                    if (n4 != 2 || arrc[n19] == '\u0651') break block30;
                                    arrc[n19] = (char)65534;
                                    bl4 = true;
                                    n16 = n6;
                                    n7 = n15;
                                    n18 = n11;
                                    n15 = n9;
                                    break block28;
                                }
                                arrc[n19] = (char)(irrelevantPos[arrc[n19] - 1611] + 65136 + n3);
                                break block31;
                            }
                            arrc[n19] = (char)((n17 >> 8) + 65136 + n3);
                        }
                        n7 = n15;
                        n15 = n9;
                        n18 = n11;
                        n16 = n6;
                        bl4 = bl2;
                    }
                    n11 = n15;
                    if ((n17 & 4) == 0) {
                        n11 = n17;
                        n12 = n19;
                        n10 = n15;
                    }
                    if ((n3 = n19 - 1) == n7) {
                        n6 = n14;
                        n7 = -2;
                        bl = bl3;
                        bl2 = bl4;
                        n13 = n16;
                        n8 = n18;
                        n9 = n14;
                        continue;
                    }
                    if (n3 != -1) {
                        n6 = ArabicShaping.getLink(arrc[n3]);
                        bl = bl3;
                        bl2 = bl4;
                        n13 = n16;
                        n8 = n18;
                        n9 = n14;
                        continue;
                    }
                    bl = bl3;
                    n6 = n17;
                    bl2 = bl4;
                    n13 = n16;
                    n8 = n18;
                    n9 = n14;
                }
                if (bl || bl2) {
                    n2 = this.handleGeneratedSpaces(arrc, n, n2);
                }
                if (n13 != 0) break block32;
                n3 = n2;
                if (n8 == 0) break block33;
            }
            n3 = this.expandCompositChar(arrc, n, n2, n5, 0);
        }
        return n3;
    }

    private static void shiftArray(char[] arrc, int n, int n2, char c) {
        int n3;
        int n4 = n2;
        while ((n3 = n2 - 1) >= n) {
            char c2 = arrc[n3];
            n2 = n4--;
            if (c2 != c) {
                n2 = n4;
                if (n4 != n3) {
                    arrc[n4] = c2;
                    n2 = n4;
                }
            }
            n4 = n2;
            n2 = n3;
        }
    }

    private static int specialChar(char c) {
        if (!(c > '\u0621' && c < '\u0626' || c == '\u0627' || c > '\u062e' && c < '\u0633' || c > '\u0647' && c < '\u064a' || c == '\u0629')) {
            if (c >= '\u064b' && c <= '\u0652') {
                return 2;
            }
            if (!(c >= '\u0653' && c <= '\u0655' || c == '\u0670' || c >= '\ufe70' && c <= '\ufe7f')) {
                return 0;
            }
            return 3;
        }
        return 1;
    }

    public boolean equals(Object object) {
        boolean bl = object != null && object.getClass() == ArabicShaping.class && this.options == ((ArabicShaping)object).options;
        return bl;
    }

    public int hashCode() {
        return this.options;
    }

    public int shape(char[] object, int n, int n2, char[] object2, int n3, int n4) throws ArabicShapingException {
        if (object != null) {
            if (n >= 0 && n2 >= 0 && n + n2 <= ((char[])object).length) {
                if (object2 == null && n4 != 0) {
                    throw new IllegalArgumentException("null dest requires destSize == 0");
                }
                if (n4 != 0 && (n3 < 0 || n4 < 0 || n3 + n4 > ((Object)object2).length)) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("bad dest start (");
                    ((StringBuilder)object).append(n3);
                    ((StringBuilder)object).append(") or size (");
                    ((StringBuilder)object).append(n4);
                    ((StringBuilder)object).append(") for buffer of length ");
                    ((StringBuilder)object).append(((Object)object2).length);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                int n5 = this.options;
                if ((n5 & 917504) != 0 && (n5 & 917504) != 262144 && (n5 & 917504) != 393216 && (n5 & 917504) != 524288 && (n5 & 917504) != 786432) {
                    throw new IllegalArgumentException("Wrong Tashkeel argument");
                }
                n5 = this.options;
                if ((n5 & 65539) != 0 && (n5 & 65539) != 3 && (n5 & 65539) != 2 && (n5 & 65539) != 0 && (n5 & 65539) != 65536 && (n5 & 65539) != 1) {
                    throw new IllegalArgumentException("Wrong Lam Alef argument");
                }
                n5 = this.options;
                if ((917504 & n5) != 0 && (n5 & 24) == 16) {
                    throw new IllegalArgumentException("Tashkeel replacement should not be enabled in deshaping mode ");
                }
                return this.internalShape((char[])object, n, n2, (char[])object2, n3, n4);
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("bad source start (");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(") or length (");
            ((StringBuilder)object2).append(n2);
            ((StringBuilder)object2).append(") for buffer of length ");
            ((StringBuilder)object2).append(((Object)object).length);
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }
        throw new IllegalArgumentException("source can not be null");
    }

    @UnsupportedAppUsage
    public String shape(String arrc) throws ArabicShapingException {
        char[] arrc2 = arrc.toCharArray();
        int n = this.options;
        arrc = (65539 & n) == 0 && (n & 24) == 16 ? new char[arrc2.length * 2] : arrc2;
        return new String(arrc, 0, this.shape(arrc2, 0, arrc2.length, arrc, 0, arrc.length));
    }

    public void shape(char[] arrc, int n, int n2) throws ArabicShapingException {
        if ((this.options & 65539) != 0) {
            this.shape(arrc, n, n2, arrc, n, n2);
            return;
        }
        throw new ArabicShapingException("Cannot shape in place with length option resize.");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append('[');
        int n = this.options & 65539;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 65536) {
                            stringBuilder.append("lamAlef auto");
                        }
                    } else {
                        stringBuilder.append("LamAlef spaces at begin");
                    }
                } else {
                    stringBuilder.append("LamAlef spaces at end");
                }
            } else {
                stringBuilder.append("LamAlef spaces at near");
            }
        } else {
            stringBuilder.append("LamAlef resize");
        }
        n = this.options & 4;
        if (n != 0) {
            if (n == 4) {
                stringBuilder.append(", visual");
            }
        } else {
            stringBuilder.append(", logical");
        }
        n = this.options & 24;
        if (n != 0) {
            if (n != 8) {
                if (n != 16) {
                    if (n == 24) {
                        stringBuilder.append(", shape letters tashkeel isolated");
                    }
                } else {
                    stringBuilder.append(", unshape letters");
                }
            } else {
                stringBuilder.append(", shape letters");
            }
        } else {
            stringBuilder.append(", no letter shaping");
        }
        if ((this.options & 7340032) == 2097152) {
            stringBuilder.append(", Seen at near");
        }
        if ((this.options & 58720256) == 16777216) {
            stringBuilder.append(", Yeh Hamza at near");
        }
        n = this.options & 917504;
        if (n != 262144) {
            if (n != 393216) {
                if (n != 524288) {
                    if (n == 786432) {
                        stringBuilder.append(", Tashkeel replace with tatweel");
                    }
                } else {
                    stringBuilder.append(", Tashkeel resize");
                }
            } else {
                stringBuilder.append(", Tashkeel at end");
            }
        } else {
            stringBuilder.append(", Tashkeel at begin");
        }
        n = this.options & 224;
        if (n != 0) {
            if (n != 32) {
                if (n != 64) {
                    if (n != 96) {
                        if (n == 128) {
                            stringBuilder.append(", shape digits to AN contextually: default AL");
                        }
                    } else {
                        stringBuilder.append(", shape digits to AN contextually: default EN");
                    }
                } else {
                    stringBuilder.append(", shape digits to EN");
                }
            } else {
                stringBuilder.append(", shape digits to AN");
            }
        } else {
            stringBuilder.append(", no digit shaping");
        }
        n = this.options & 256;
        if (n != 0) {
            if (n == 256) {
                stringBuilder.append(", extended Arabic-Indic digits");
            }
        } else {
            stringBuilder.append(", standard Arabic-Indic digits");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

