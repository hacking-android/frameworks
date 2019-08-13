/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.UBiDiProps;
import android.icu.lang.UCharacter;
import android.icu.text.BidiClassifier;
import android.icu.text.BidiLine;
import android.icu.text.BidiRun;
import android.icu.text.BidiWriter;
import android.icu.text.UTF16;
import java.awt.font.NumericShaper;
import java.awt.font.TextAttribute;
import java.lang.reflect.Array;
import java.text.AttributedCharacterIterator;
import java.util.Arrays;

public class Bidi {
    static final byte AL = 13;
    static final byte AN = 5;
    static final byte B = 7;
    static final byte BN = 18;
    @Deprecated
    public static final int CLASS_DEFAULT = 23;
    private static final char CR = '\r';
    static final byte CS = 6;
    public static final int DIRECTION_DEFAULT_LEFT_TO_RIGHT = 126;
    public static final int DIRECTION_DEFAULT_RIGHT_TO_LEFT = 127;
    public static final int DIRECTION_LEFT_TO_RIGHT = 0;
    public static final int DIRECTION_RIGHT_TO_LEFT = 1;
    public static final short DO_MIRRORING = 2;
    static final int[] DirPropFlagE;
    static final int[] DirPropFlagLR;
    static final int DirPropFlagMultiRuns;
    static final int[] DirPropFlagO;
    static final byte EN = 2;
    static final byte ENL = 23;
    static final byte ENR = 24;
    static final byte ES = 3;
    static final byte ET = 4;
    static final int FIRSTALLOC = 10;
    static final byte FOUND_L;
    static final byte FOUND_R;
    static final byte FSI = 19;
    private static final int IMPTABLEVELS_COLUMNS = 8;
    private static final int IMPTABLEVELS_RES = 7;
    private static final int IMPTABPROPS_COLUMNS = 16;
    private static final int IMPTABPROPS_RES = 15;
    public static final short INSERT_LRM_FOR_NUMERIC = 4;
    static final int ISOLATE = 256;
    public static final short KEEP_BASE_COMBINING = 1;
    static final byte L = 0;
    public static final byte LEVEL_DEFAULT_LTR = 126;
    public static final byte LEVEL_DEFAULT_RTL = 127;
    public static final byte LEVEL_OVERRIDE = -128;
    private static final char LF = '\n';
    static final int LOOKING_FOR_PDI = 3;
    static final byte LRE = 11;
    static final byte LRI = 20;
    static final int LRM_AFTER = 2;
    static final int LRM_BEFORE = 1;
    static final byte LRO = 12;
    public static final byte LTR = 0;
    public static final int MAP_NOWHERE = -1;
    static final int MASK_BN_EXPLICIT;
    static final int MASK_B_S;
    static final int MASK_EMBEDDING;
    static final int MASK_EXPLICIT;
    static final int MASK_ISO;
    static final int MASK_LTR;
    static final int MASK_POSSIBLE_N;
    static final int MASK_RTL;
    static final int MASK_R_AL;
    static final int MASK_STRONG_EN_AN;
    static final int MASK_WS;
    public static final byte MAX_EXPLICIT_LEVEL = 125;
    public static final byte MIXED = 2;
    public static final byte NEUTRAL = 3;
    static final int NOT_SEEKING_STRONG = 0;
    static final byte NSM = 17;
    static final byte ON = 10;
    public static final int OPTION_DEFAULT = 0;
    public static final int OPTION_INSERT_MARKS = 1;
    public static final int OPTION_REMOVE_CONTROLS = 2;
    public static final int OPTION_STREAMING = 4;
    public static final short OUTPUT_REVERSE = 16;
    static final byte PDF = 16;
    static final byte PDI = 22;
    static final byte R = 1;
    public static final short REMOVE_BIDI_CONTROLS = 8;
    static final short REORDER_COUNT = 7;
    public static final short REORDER_DEFAULT = 0;
    public static final short REORDER_GROUP_NUMBERS_WITH_R = 2;
    public static final short REORDER_INVERSE_FOR_NUMBERS_SPECIAL = 6;
    public static final short REORDER_INVERSE_LIKE_DIRECT = 5;
    public static final short REORDER_INVERSE_NUMBERS_AS_L = 4;
    static final short REORDER_LAST_LOGICAL_TO_VISUAL = 1;
    public static final short REORDER_NUMBERS_SPECIAL = 1;
    public static final short REORDER_RUNS_ONLY = 3;
    static final byte RLE = 14;
    static final byte RLI = 21;
    static final int RLM_AFTER = 8;
    static final int RLM_BEFORE = 4;
    static final byte RLO = 15;
    public static final byte RTL = 1;
    static final byte S = 8;
    static final int SEEKING_STRONG_FOR_FSI = 2;
    static final int SEEKING_STRONG_FOR_PARA = 1;
    static final int SIMPLE_OPENINGS_COUNT = 20;
    static final int SIMPLE_PARAS_COUNT = 10;
    static final byte WS = 9;
    private static final short _AN = 3;
    private static final short _B = 6;
    private static final short _EN = 2;
    private static final short _L = 0;
    private static final short _ON = 4;
    private static final short _R = 1;
    private static final short _S = 5;
    private static final short[] groupProp;
    private static final short[] impAct0;
    private static final short[] impAct1;
    private static final short[] impAct2;
    private static final short[] impAct3;
    private static final byte[][] impTabL_DEFAULT;
    private static final byte[][] impTabL_GROUP_NUMBERS_WITH_R;
    private static final byte[][] impTabL_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS;
    private static final byte[][] impTabL_INVERSE_LIKE_DIRECT_WITH_MARKS;
    private static final byte[][] impTabL_INVERSE_NUMBERS_AS_L;
    private static final byte[][] impTabL_NUMBERS_SPECIAL;
    private static final short[][] impTabProps;
    private static final byte[][] impTabR_DEFAULT;
    private static final byte[][] impTabR_GROUP_NUMBERS_WITH_R;
    private static final byte[][] impTabR_INVERSE_LIKE_DIRECT;
    private static final byte[][] impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS;
    private static final byte[][] impTabR_INVERSE_NUMBERS_AS_L;
    private static final ImpTabPair impTab_DEFAULT;
    private static final ImpTabPair impTab_GROUP_NUMBERS_WITH_R;
    private static final ImpTabPair impTab_INVERSE_FOR_NUMBERS_SPECIAL;
    private static final ImpTabPair impTab_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS;
    private static final ImpTabPair impTab_INVERSE_LIKE_DIRECT;
    private static final ImpTabPair impTab_INVERSE_LIKE_DIRECT_WITH_MARKS;
    private static final ImpTabPair impTab_INVERSE_NUMBERS_AS_L;
    private static final ImpTabPair impTab_NUMBERS_SPECIAL;
    final UBiDiProps bdp;
    int controlCount;
    BidiClassifier customClassifier = null;
    byte defaultParaLevel;
    byte[] dirProps;
    byte[] dirPropsMemory = new byte[1];
    byte direction;
    String epilogue;
    int flags;
    ImpTabPair impTabPair;
    InsertPoints insertPoints = new InsertPoints();
    boolean isGoodLogicalToVisualRunsMap;
    boolean isInverse;
    int isolateCount;
    Isolate[] isolates;
    int lastArabicPos;
    int length;
    byte[] levels;
    byte[] levelsMemory = new byte[1];
    int[] logicalToVisualRunsMap;
    boolean mayAllocateRuns;
    boolean mayAllocateText;
    boolean orderParagraphsLTR;
    int originalLength;
    Bidi paraBidi;
    int paraCount;
    byte paraLevel;
    byte[] paras_level = new byte[10];
    int[] paras_limit = new int[10];
    String prologue;
    int reorderingMode;
    int reorderingOptions;
    int resultLength;
    int runCount;
    BidiRun[] runs;
    BidiRun[] runsMemory = new BidiRun[0];
    BidiRun[] simpleRuns = new BidiRun[]{new BidiRun()};
    char[] text;
    int trailingWSStart;

    static {
        FOUND_L = (byte)Bidi.DirPropFlag((byte)0);
        FOUND_R = (byte)Bidi.DirPropFlag((byte)1);
        DirPropFlagMultiRuns = Bidi.DirPropFlag((byte)31);
        DirPropFlagLR = new int[]{Bidi.DirPropFlag((byte)0), Bidi.DirPropFlag((byte)1)};
        DirPropFlagE = new int[]{Bidi.DirPropFlag((byte)11), Bidi.DirPropFlag((byte)14)};
        DirPropFlagO = new int[]{Bidi.DirPropFlag((byte)12), Bidi.DirPropFlag((byte)15)};
        MASK_LTR = Bidi.DirPropFlag((byte)0) | Bidi.DirPropFlag((byte)2) | Bidi.DirPropFlag((byte)23) | Bidi.DirPropFlag((byte)24) | Bidi.DirPropFlag((byte)5) | Bidi.DirPropFlag((byte)11) | Bidi.DirPropFlag((byte)12) | Bidi.DirPropFlag((byte)20);
        MASK_RTL = Bidi.DirPropFlag((byte)1) | Bidi.DirPropFlag((byte)13) | Bidi.DirPropFlag((byte)14) | Bidi.DirPropFlag((byte)15) | Bidi.DirPropFlag((byte)21);
        MASK_R_AL = Bidi.DirPropFlag((byte)1) | Bidi.DirPropFlag((byte)13);
        MASK_STRONG_EN_AN = Bidi.DirPropFlag((byte)0) | Bidi.DirPropFlag((byte)1) | Bidi.DirPropFlag((byte)13) | Bidi.DirPropFlag((byte)2) | Bidi.DirPropFlag((byte)5);
        MASK_EXPLICIT = Bidi.DirPropFlag((byte)11) | Bidi.DirPropFlag((byte)12) | Bidi.DirPropFlag((byte)14) | Bidi.DirPropFlag((byte)15) | Bidi.DirPropFlag((byte)16);
        MASK_BN_EXPLICIT = Bidi.DirPropFlag((byte)18) | MASK_EXPLICIT;
        MASK_ISO = Bidi.DirPropFlag((byte)20) | Bidi.DirPropFlag((byte)21) | Bidi.DirPropFlag((byte)19) | Bidi.DirPropFlag((byte)22);
        MASK_B_S = Bidi.DirPropFlag((byte)7) | Bidi.DirPropFlag((byte)8);
        MASK_WS = MASK_B_S | Bidi.DirPropFlag((byte)9) | MASK_BN_EXPLICIT | MASK_ISO;
        MASK_POSSIBLE_N = Bidi.DirPropFlag((byte)10) | Bidi.DirPropFlag((byte)6) | Bidi.DirPropFlag((byte)3) | Bidi.DirPropFlag((byte)4) | MASK_WS;
        MASK_EMBEDDING = Bidi.DirPropFlag((byte)17) | MASK_POSSIBLE_N;
        groupProp = new short[]{0, 1, 2, 7, 8, 3, 9, 6, 5, 4, 4, 10, 10, 12, 10, 10, 10, 11, 10, 4, 4, 4, 4, 13, 14};
        short[] arrs = new short[]{1, 2, 4, 5, 7, 15, 17, 7, 9, 7, 0, 7, 3, 18, 21, 4};
        short[] arrs2 = new short[]{33, 34, 38, 38, 8, 48, 49, 8, 8, 8, 8, 8, 35, 50, 53, 4};
        short[] arrs3 = new short[]{97, 98, 4, 101, 135, 111, 113, 135, 142, 135, 10, 135, 99, 18, 21, 2};
        short[] arrs4 = new short[]{97, 98, 6, 6, 136, 112, 113, 136, 136, 136, 13, 136, 99, 18, 21, 3};
        short[] arrs5 = new short[]{33, 34, 18, 37, 39, 47, 49, 39, 20, 39, 20, 20, 35, 18, 21, 0};
        impTabProps = new short[][]{arrs, {1, 34, 36, 37, 39, 47, 49, 39, 41, 39, 1, 1, 35, 50, 53, 0}, {33, 2, 36, 37, 39, 47, 49, 39, 41, 39, 2, 2, 35, 50, 53, 1}, {33, 34, 38, 38, 40, 48, 49, 40, 40, 40, 3, 3, 3, 50, 53, 1}, {33, 34, 4, 37, 39, 47, 49, 74, 11, 74, 4, 4, 35, 18, 21, 2}, {33, 34, 36, 5, 39, 47, 49, 39, 41, 76, 5, 5, 35, 50, 53, 3}, {33, 34, 6, 6, 40, 48, 49, 40, 40, 77, 6, 6, 35, 18, 21, 3}, {33, 34, 36, 37, 7, 47, 49, 7, 78, 7, 7, 7, 35, 50, 53, 4}, arrs2, {33, 34, 4, 37, 7, 47, 49, 7, 9, 7, 9, 9, 35, 18, 21, 4}, arrs3, {33, 34, 4, 37, 39, 47, 49, 39, 11, 39, 11, 11, 35, 18, 21, 2}, {97, 98, 100, 5, 135, 111, 113, 135, 142, 135, 12, 135, 99, 114, 117, 3}, arrs4, {33, 34, 132, 37, 7, 47, 49, 7, 14, 7, 14, 14, 35, 146, 149, 4}, {33, 34, 36, 37, 39, 15, 49, 39, 41, 39, 15, 39, 35, 50, 53, 5}, {33, 34, 38, 38, 40, 16, 49, 40, 40, 40, 16, 40, 35, 50, 53, 5}, {33, 34, 36, 37, 39, 47, 17, 39, 41, 39, 17, 39, 35, 50, 53, 6}, {33, 34, 18, 37, 39, 47, 49, 83, 20, 83, 18, 18, 35, 18, 21, 0}, {97, 98, 18, 101, 135, 111, 113, 135, 142, 135, 19, 135, 99, 18, 21, 0}, arrs5, {33, 34, 21, 37, 39, 47, 49, 86, 23, 86, 21, 21, 35, 18, 21, 3}, {97, 98, 21, 101, 135, 111, 113, 135, 142, 135, 22, 135, 99, 18, 21, 3}, {33, 34, 21, 37, 39, 47, 49, 39, 23, 39, 23, 23, 35, 18, 21, 3}};
        arrs = new byte[]{0, 1, 0, 2, 21, 21, 0, 2};
        impTabL_DEFAULT = new byte[][]{{0, 1, 0, 2, 0, 0, 0, 0}, {0, 1, 3, 3, 20, 20, 0, 1}, arrs, {0, 1, 3, 3, 20, 20, 0, 2}, {0, 33, 51, 51, 4, 4, 0, 0}, {0, 33, 0, 50, 5, 5, 0, 0}};
        arrs = new byte[]{1, 0, 2, 2, 0, 0, 0, 0};
        arrs2 = new byte[]{1, 0, 1, 3, 20, 20, 0, 1};
        arrs3 = new byte[]{1, 0, 2, 2, 0, 0, 0, 1};
        arrs4 = new byte[]{1, 0, 1, 3, 5, 5, 0, 1};
        arrs5 = new byte[]{1, 0, 1, 3, 5, 5, 0, 0};
        impTabR_DEFAULT = new byte[][]{arrs, arrs2, arrs3, arrs4, {33, 0, 33, 3, 4, 4, 0, 0}, arrs5};
        impAct0 = new short[]{0, 1, 2, 3, 4};
        arrs = impTabL_DEFAULT;
        arrs3 = impTabR_DEFAULT;
        arrs2 = impAct0;
        impTab_DEFAULT = new ImpTabPair((byte[][])arrs, (byte[][])arrs3, arrs2, arrs2);
        impTabL_NUMBERS_SPECIAL = new byte[][]{{0, 2, 17, 17, 0, 0, 0, 0}, {0, 66, 1, 1, 0, 0, 0, 0}, {0, 2, 4, 4, 19, 19, 0, 1}, {0, 34, 52, 52, 3, 3, 0, 0}, {0, 2, 4, 4, 19, 19, 0, 2}};
        arrs = impTabL_NUMBERS_SPECIAL;
        arrs2 = impTabR_DEFAULT;
        arrs3 = impAct0;
        impTab_NUMBERS_SPECIAL = new ImpTabPair((byte[][])arrs, (byte[][])arrs2, arrs3, arrs3);
        impTabL_GROUP_NUMBERS_WITH_R = new byte[][]{{0, 3, 17, 17, 0, 0, 0, 0}, {32, 3, 1, 1, 2, 32, 32, 2}, {32, 3, 1, 1, 2, 32, 32, 1}, {0, 3, 5, 5, 20, 0, 0, 1}, {32, 3, 5, 5, 4, 32, 32, 1}, {0, 3, 5, 5, 20, 0, 0, 2}};
        impTabR_GROUP_NUMBERS_WITH_R = new byte[][]{{2, 0, 1, 1, 0, 0, 0, 0}, {2, 0, 1, 1, 0, 0, 0, 1}, {2, 0, 20, 20, 19, 0, 0, 1}, {34, 0, 4, 4, 3, 0, 0, 0}, {34, 0, 4, 4, 3, 0, 0, 1}};
        arrs3 = impTabL_GROUP_NUMBERS_WITH_R;
        arrs2 = impTabR_GROUP_NUMBERS_WITH_R;
        arrs = impAct0;
        impTab_GROUP_NUMBERS_WITH_R = new ImpTabPair((byte[][])arrs3, (byte[][])arrs2, arrs, arrs);
        impTabL_INVERSE_NUMBERS_AS_L = new byte[][]{{0, 1, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 20, 20, 0, 1}, {0, 1, 0, 0, 21, 21, 0, 2}, {0, 1, 0, 0, 20, 20, 0, 2}, {32, 1, 32, 32, 4, 4, 32, 1}, {32, 1, 32, 32, 5, 5, 32, 1}};
        arrs = new byte[]{1, 0, 1, 1, 0, 0, 0, 0};
        arrs2 = new byte[]{1, 0, 1, 1, 20, 20, 0, 1};
        arrs3 = new byte[]{1, 0, 1, 1, 5, 5, 0, 1};
        arrs4 = new byte[]{33, 0, 33, 33, 4, 4, 0, 0};
        impTabR_INVERSE_NUMBERS_AS_L = new byte[][]{arrs, arrs2, {1, 0, 1, 1, 0, 0, 0, 1}, arrs3, arrs4, {1, 0, 1, 1, 5, 5, 0, 0}};
        arrs = impTabL_INVERSE_NUMBERS_AS_L;
        arrs3 = impTabR_INVERSE_NUMBERS_AS_L;
        arrs2 = impAct0;
        impTab_INVERSE_NUMBERS_AS_L = new ImpTabPair((byte[][])arrs, (byte[][])arrs3, arrs2, arrs2);
        arrs = new byte[]{33, 48, 6, 4, 3, 3, 48, 0};
        arrs2 = new byte[]{33, 48, 6, 4, 5, 5, 48, 3};
        arrs3 = new byte[]{33, 48, 6, 4, 5, 5, 48, 2};
        impTabR_INVERSE_LIKE_DIRECT = new byte[][]{{1, 0, 2, 2, 0, 0, 0, 0}, {1, 0, 1, 2, 19, 19, 0, 1}, {1, 0, 2, 2, 0, 0, 0, 1}, arrs, arrs2, arrs3, {33, 48, 6, 4, 3, 3, 48, 1}};
        impAct1 = new short[]{0, 1, 13, 14};
        impTab_INVERSE_LIKE_DIRECT = new ImpTabPair(impTabL_DEFAULT, impTabR_INVERSE_LIKE_DIRECT, impAct0, impAct1);
        arrs = new byte[]{0, 99, 0, 1, 0, 0, 0, 0};
        arrs2 = new byte[]{32, 99, 32, 1, 2, 48, 32, 3};
        arrs3 = new byte[]{0, 99, 85, 86, 20, 48, 0, 3};
        arrs4 = new byte[]{48, 67, 85, 86, 4, 48, 48, 3};
        arrs5 = new byte[]{48, 67, 5, 86, 20, 48, 48, 4};
        byte[] arrby = new byte[]{48, 67, 85, 6, 20, 48, 48, 4};
        impTabL_INVERSE_LIKE_DIRECT_WITH_MARKS = new byte[][]{arrs, {0, 99, 0, 1, 18, 48, 0, 4}, arrs2, arrs3, arrs4, arrs5, arrby};
        arrs = new byte[]{83, 64, 5, 54, 4, 64, 64, 0};
        impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS = new byte[][]{{19, 0, 1, 1, 0, 0, 0, 0}, {35, 0, 1, 1, 2, 64, 0, 1}, {35, 0, 1, 1, 2, 64, 0, 0}, {3, 0, 3, 54, 20, 64, 0, 1}, arrs, {83, 64, 5, 54, 4, 64, 64, 1}, {83, 64, 6, 6, 4, 64, 64, 3}};
        impAct2 = new short[]{0, 1, 2, 5, 6, 7, 8};
        impAct3 = new short[]{0, 1, 9, 10, 11, 12};
        impTab_INVERSE_LIKE_DIRECT_WITH_MARKS = new ImpTabPair(impTabL_INVERSE_LIKE_DIRECT_WITH_MARKS, impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS, impAct2, impAct3);
        impTab_INVERSE_FOR_NUMBERS_SPECIAL = new ImpTabPair(impTabL_NUMBERS_SPECIAL, impTabR_INVERSE_LIKE_DIRECT, impAct0, impAct1);
        impTabL_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS = new byte[][]{{0, 98, 1, 1, 0, 0, 0, 0}, {0, 98, 1, 1, 0, 48, 0, 4}, {0, 98, 84, 84, 19, 48, 0, 3}, {48, 66, 84, 84, 3, 48, 48, 3}, {48, 66, 4, 4, 19, 48, 48, 4}};
        impTab_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS = new ImpTabPair(impTabL_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS, impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS, impAct2, impAct3);
    }

    public Bidi() {
        this(0, 0);
    }

    public Bidi(int n, int n2) {
        if (n >= 0 && n2 >= 0) {
            this.bdp = UBiDiProps.INSTANCE;
            if (n > 0) {
                this.getInitialDirPropsMemory(n);
                this.getInitialLevelsMemory(n);
            } else {
                this.mayAllocateText = true;
            }
            if (n2 > 0) {
                if (n2 > 1) {
                    this.getInitialRunsMemory(n2);
                }
            } else {
                this.mayAllocateRuns = true;
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    public Bidi(String string, int n) {
        this(string.toCharArray(), 0, null, 0, string.length(), n);
    }

    public Bidi(AttributedCharacterIterator attributedCharacterIterator) {
        this();
        this.setPara(attributedCharacterIterator);
    }

    public Bidi(char[] arrc, int n, byte[] arrobject, int n2, int n3, int n4) {
        this();
        byte by;
        byte[] arrby;
        if (n4 != 1) {
            if (n4 != 126) {
                if (n4 != 127) {
                    n4 = 0;
                    by = n4;
                } else {
                    n4 = 127;
                    by = n4;
                }
            } else {
                n4 = 126;
                by = n4;
            }
        } else {
            n4 = 1;
            by = n4;
        }
        if (arrobject == null) {
            arrby = null;
        } else {
            byte[] arrby2 = new byte[n3];
            n4 = 0;
            do {
                byte by2;
                arrby = arrby2;
                if (n4 >= n3) break;
                byte by3 = by2 = arrobject[n4 + n2];
                if (by2 < 0) {
                    by3 = (byte)(-by2 | -128);
                }
                arrby2[n4] = by3;
                ++n4;
            } while (true);
        }
        if (n == 0 && n3 == arrc.length) {
            this.setPara(arrc, by, arrby);
        } else {
            arrobject = new char[n3];
            System.arraycopy(arrc, n, arrobject, 0, n3);
            this.setPara((char[])arrobject, by, arrby);
        }
    }

    static final byte DirFromStrong(byte by) {
        byte by2 = by == 0 ? (by = 0) : (by = 1);
        return by2;
    }

    static int DirPropFlag(byte by) {
        return 1 << by;
    }

    static final int DirPropFlagE(byte by) {
        return DirPropFlagE[by & 1];
    }

    static final int DirPropFlagLR(byte by) {
        return DirPropFlagLR[by & 1];
    }

    static final int DirPropFlagO(byte by) {
        return DirPropFlagO[by & 1];
    }

    private static short GetAction(byte by) {
        return (short)(by >> 4);
    }

    private static short GetActionProps(short s) {
        return (short)(s >> 5);
    }

    static byte GetLRFromLevel(byte by) {
        return (byte)(by & 1);
    }

    private static short GetState(byte by) {
        return (short)(by & 15);
    }

    private static short GetStateProps(short s) {
        return (short)(s & 31);
    }

    static boolean IsBidiControlChar(int n) {
        boolean bl = (n & -4) == 8204 || n >= 8234 && n <= 8238 || n >= 8294 && n <= 8297;
        return bl;
    }

    static boolean IsDefaultLevel(byte by) {
        boolean bl = (by & 126) == 126;
        return bl;
    }

    static final byte NoOverride(byte by) {
        return (byte)(by & 127);
    }

    private void addPoint(int n, int n2) {
        int n3;
        Object object;
        Point point = new Point();
        int n4 = n3 = this.insertPoints.points.length;
        if (n3 == 0) {
            this.insertPoints.points = new Point[10];
            n4 = 10;
        }
        if (this.insertPoints.size >= n4) {
            object = this.insertPoints.points;
            InsertPoints insertPoints = this.insertPoints;
            insertPoints.points = new Point[n4 * 2];
            System.arraycopy(object, 0, insertPoints.points, 0, n4);
        }
        point.pos = n;
        point.flag = n2;
        this.insertPoints.points[this.insertPoints.size] = point;
        object = this.insertPoints;
        ++object.size;
    }

    private void adjustWSLevels() {
        if ((this.flags & MASK_WS) != 0) {
            int n = this.trailingWSStart;
            block0 : while (n > 0) {
                byte[] arrby;
                int n2;
                int n3;
                do {
                    n3 = n;
                    if (n <= 0) break;
                    arrby = this.dirProps;
                    n2 = Bidi.DirPropFlag(arrby[--n]);
                    n3 = n;
                    if ((n2 & MASK_WS) == 0) break;
                    if (this.orderParagraphsLTR && (Bidi.DirPropFlag((byte)7) & n2) != 0) {
                        this.levels[n] = (byte)(false ? 1 : 0);
                        continue;
                    }
                    this.levels[n] = this.GetParaLevelAt(n);
                } while (true);
                do {
                    n = n3;
                    if (n3 <= 0) continue block0;
                    arrby = this.dirProps;
                    n = n3 - 1;
                    n2 = Bidi.DirPropFlag(arrby[n]);
                    if ((MASK_BN_EXPLICIT & n2) != 0) {
                        arrby = this.levels;
                        arrby[n] = arrby[n + 1];
                        n3 = n;
                        continue;
                    }
                    if (this.orderParagraphsLTR && (Bidi.DirPropFlag((byte)7) & n2) != 0) {
                        this.levels[n] = (byte)(false ? 1 : 0);
                        continue block0;
                    }
                    n3 = n;
                    if ((MASK_B_S & n2) != 0) break;
                } while (true);
                this.levels[n] = this.GetParaLevelAt(n);
            }
        }
    }

    private void bracketAddOpening(BracketData arropening, char c, int n) {
        int n2;
        Object object;
        IsoRun isoRun = arropening.isoRuns[arropening.isoRunLast];
        if (isoRun.limit >= arropening.openings.length) {
            object = arropening.openings;
            try {
                n2 = arropening.openings.length;
                arropening.openings = new Opening[n2 * 2];
            }
            catch (Exception exception) {
                throw new OutOfMemoryError("Failed to allocate memory for openings");
            }
            System.arraycopy(object, 0, arropening.openings, 0, n2);
        }
        Opening opening = arropening.openings[isoRun.limit];
        object = opening;
        if (opening == null) {
            arropening = arropening.openings;
            n2 = isoRun.limit;
            arropening[n2] = object = new Opening();
        }
        object.position = n;
        object.match = c;
        object.contextDir = isoRun.contextDir;
        object.contextPos = isoRun.contextPos;
        object.flags = (short)(false ? 1 : 0);
        isoRun.limit = (short)(isoRun.limit + 1);
    }

    private void bracketInit(BracketData bracketData) {
        boolean bl = false;
        bracketData.isoRunLast = 0;
        bracketData.isoRuns[0] = new IsoRun();
        bracketData.isoRuns[0].start = (short)(false ? 1 : 0);
        bracketData.isoRuns[0].limit = (short)(false ? 1 : 0);
        bracketData.isoRuns[0].level = this.GetParaLevelAt(0);
        IsoRun isoRun = bracketData.isoRuns[0];
        IsoRun isoRun2 = bracketData.isoRuns[0];
        IsoRun isoRun3 = bracketData.isoRuns[0];
        int n = this.GetParaLevelAt(0) & 1;
        isoRun3.contextDir = (byte)n;
        isoRun2.lastBase = (byte)n;
        isoRun.lastStrong = (byte)n;
        bracketData.isoRuns[0].contextPos = 0;
        bracketData.openings = new Opening[20];
        n = this.reorderingMode;
        if (n == 1 || n == 6) {
            bl = true;
        }
        bracketData.isNumbersSpecial = bl;
    }

    private void bracketProcessB(BracketData bracketData, byte by) {
        bracketData.isoRunLast = 0;
        bracketData.isoRuns[0].limit = (short)(false ? 1 : 0);
        bracketData.isoRuns[0].level = by;
        IsoRun isoRun = bracketData.isoRuns[0];
        IsoRun isoRun2 = bracketData.isoRuns[0];
        IsoRun isoRun3 = bracketData.isoRuns[0];
        by = (byte)(by & 1);
        isoRun3.contextDir = by;
        isoRun2.lastBase = by;
        isoRun.lastStrong = by;
        bracketData.isoRuns[0].contextPos = 0;
    }

    private void bracketProcessBoundary(BracketData object, int n, byte by, byte by2) {
        object = ((BracketData)object).isoRuns[((BracketData)object).isoRunLast];
        if ((Bidi.DirPropFlag(this.dirProps[n]) & MASK_ISO) != 0) {
            return;
        }
        byte by3 = by;
        if (Bidi.NoOverride(by2) > Bidi.NoOverride(by)) {
            by3 = by2;
        }
        ((IsoRun)object).limit = ((IsoRun)object).start;
        ((IsoRun)object).level = by2;
        by3 = (byte)(by3 & 1);
        ((IsoRun)object).contextDir = by3;
        ((IsoRun)object).lastBase = by3;
        ((IsoRun)object).lastStrong = by3;
        ((IsoRun)object).contextPos = n;
    }

    private void bracketProcessChar(BracketData bracketData, int n) {
        int n2;
        short s;
        IsoRun isoRun = bracketData.isoRuns[bracketData.isoRunLast];
        int n3 = this.dirProps[n];
        if (n3 == 10) {
            int n4;
            block29 : {
                short s2 = this.text[n];
                n2 = isoRun.limit - 1;
                do {
                    s = s2;
                    if (n2 < isoRun.start) break block29;
                    if (bracketData.openings[n2].match == s2) break;
                    --n2;
                } while (true);
                s = this.bracketProcessClosing(bracketData, n2, n);
                if (s == 10) {
                    s = 0;
                } else {
                    Object object;
                    isoRun.lastBase = (byte)10;
                    isoRun.contextDir = (byte)s;
                    isoRun.contextPos = n;
                    s = this.levels[n];
                    if ((s & -128) != 0) {
                        n3 = (byte)(s & 1);
                        isoRun.lastStrong = (byte)n3;
                        s2 = (short)Bidi.DirPropFlag((byte)n3);
                        for (s = isoRun.start; s < n2; s = (short)(s + 1)) {
                            object = bracketData.openings[s];
                            object.flags = (short)(object.flags | s2);
                        }
                        object = this.levels;
                        object[n] = (byte)(object[n] & 127);
                    }
                    object = this.levels;
                    n = bracketData.openings[n2].position;
                    object[n] = (byte)(object[n] & 127);
                    return;
                }
            }
            if (s != 0) {
                n2 = (char)UCharacter.getBidiPairedBracket(s);
                n4 = n2;
            } else {
                n2 = 0;
                n4 = n2;
            }
            if (n4 != s && UCharacter.getIntPropertyValue(s, 4117) == 1) {
                if (n4 == 9002) {
                    this.bracketAddOpening(bracketData, '\u3009', n);
                } else if (n4 == 12297) {
                    this.bracketAddOpening(bracketData, '\u232a', n);
                }
                this.bracketAddOpening(bracketData, (char)n4, n);
            }
        }
        if (((n2 = this.levels[n]) & -128) != 0) {
            n2 = (byte)(n2 & 1);
            if (n3 != 8 && n3 != 9 && n3 != 10) {
                this.dirProps[n] = (byte)n2;
            }
            isoRun.lastBase = (byte)n2;
            isoRun.lastStrong = (byte)n2;
            isoRun.contextDir = (byte)n2;
            isoRun.contextPos = n;
            n3 = n2;
        } else if (n3 > 1 && n3 != 13) {
            if (n3 == 2) {
                isoRun.lastBase = (byte)2;
                if (isoRun.lastStrong == 0) {
                    n2 = 0;
                    if (!bracketData.isNumbersSpecial) {
                        this.dirProps[n] = (byte)23;
                    }
                    isoRun.contextDir = (byte)(false ? 1 : 0);
                    isoRun.contextPos = n;
                    n3 = n2;
                } else {
                    n2 = 1;
                    this.dirProps[n] = isoRun.lastStrong == 13 ? (byte)5 : (byte)24;
                    isoRun.contextDir = (byte)(true ? 1 : 0);
                    isoRun.contextPos = n;
                    n3 = n2;
                }
            } else if (n3 == 5) {
                n2 = 1;
                isoRun.lastBase = (byte)5;
                isoRun.contextDir = (byte)(true ? 1 : 0);
                isoRun.contextPos = n;
                n3 = n2;
            } else if (n3 == 17) {
                n2 = isoRun.lastBase;
                n3 = n2;
                if (n2 == 10) {
                    this.dirProps[n] = (byte)n2;
                    n3 = n2;
                }
            } else {
                n2 = n3;
                isoRun.lastBase = (byte)n3;
                n3 = n2;
            }
        } else {
            n2 = Bidi.DirFromStrong((byte)n3);
            isoRun.lastBase = n3;
            isoRun.lastStrong = n3;
            isoRun.contextDir = (byte)n2;
            isoRun.contextPos = n;
            n3 = n2;
        }
        if (n3 <= 1 || n3 == 13) {
            s = (short)Bidi.DirPropFlag(Bidi.DirFromStrong((byte)n3));
            for (n2 = isoRun.start; n2 < isoRun.limit; ++n2) {
                if (n <= bracketData.openings[n2].position) continue;
                Opening opening = bracketData.openings[n2];
                opening.flags = (short)(opening.flags | s);
            }
        }
    }

    private byte bracketProcessClosing(BracketData bracketData, int n, int n2) {
        block12 : {
            Opening opening;
            int n3;
            IsoRun isoRun;
            byte by;
            block11 : {
                byte by2;
                block10 : {
                    isoRun = bracketData.isoRuns[bracketData.isoRunLast];
                    opening = bracketData.openings[n];
                    by2 = (byte)(isoRun.level & 1);
                    n3 = 1;
                    if ((by2 != 0 || (opening.flags & FOUND_L) <= 0) && (by2 != 1 || (opening.flags & FOUND_R) <= 0)) break block10;
                    by = by2;
                    break block11;
                }
                if ((opening.flags & (FOUND_L | FOUND_R)) == 0) break block12;
                n3 = n == isoRun.start ? 1 : 0;
                by = by2 != opening.contextDir ? (by2 = opening.contextDir) : by2;
            }
            this.dirProps[opening.position] = by;
            this.dirProps[n2] = by;
            this.fixN0c(bracketData, n, opening.position, by);
            if (n3 != 0) {
                isoRun.limit = (short)n;
                while (isoRun.limit > isoRun.start && bracketData.openings[isoRun.limit - 1].position == opening.position) {
                    isoRun.limit = (short)(isoRun.limit - 1);
                }
            } else {
                opening.match = -n2;
                for (n3 = n - 1; n3 >= isoRun.start && bracketData.openings[n3].position == opening.position; --n3) {
                    bracketData.openings[n3].match = 0;
                }
                ++n;
                while (n < isoRun.limit) {
                    opening = bracketData.openings[n];
                    if (opening.position < n2) {
                        if (opening.match > 0) {
                            opening.match = 0;
                        }
                        ++n;
                        continue;
                    }
                    break;
                }
            }
            return by;
        }
        isoRun.limit = (short)n;
        return 10;
    }

    private void bracketProcessLRI_RLI(BracketData bracketData, byte by) {
        IsoRun isoRun = bracketData.isoRuns[bracketData.isoRunLast];
        isoRun.lastBase = (byte)10;
        short s = isoRun.limit;
        ++bracketData.isoRunLast;
        IsoRun[] arrisoRun = bracketData.isoRuns[bracketData.isoRunLast];
        isoRun = arrisoRun;
        if (arrisoRun == null) {
            arrisoRun = bracketData.isoRuns;
            int n = bracketData.isoRunLast;
            arrisoRun[n] = isoRun = new IsoRun();
        }
        isoRun.limit = s;
        isoRun.start = s;
        isoRun.level = by;
        by = (byte)(by & 1);
        isoRun.contextDir = by;
        isoRun.lastBase = by;
        isoRun.lastStrong = by;
        isoRun.contextPos = 0;
    }

    private void bracketProcessPDI(BracketData bracketData) {
        --bracketData.isoRunLast;
        bracketData.isoRuns[bracketData.isoRunLast].lastBase = (byte)10;
    }

    private byte checkExplicitLevels() {
        int n = 0;
        this.flags = 0;
        this.isolateCount = 0;
        int n2 = 0;
        int n3 = this.paras_limit[0];
        int n4 = this.paraLevel;
        for (int i = 0; i < this.length; ++i) {
            byte[] arrby;
            block19 : {
                int n5;
                int n6;
                byte by;
                int n7;
                int n8;
                block18 : {
                    block17 : {
                        byte by2 = this.levels[i];
                        by = this.dirProps[i];
                        if (by != 20 && by != 21) {
                            if (by == 22) {
                                --n;
                            } else if (by == 7) {
                                n = 0;
                            }
                        } else {
                            n = n5 = n + 1;
                            if (n5 > this.isolateCount) {
                                this.isolateCount = n5;
                                n = n5;
                            }
                        }
                        n8 = n3;
                        n7 = n2;
                        n5 = n4;
                        if (this.defaultParaLevel != 0) {
                            n8 = n3;
                            n7 = n2;
                            n5 = n4;
                            if (i == n3) {
                                n8 = n3;
                                n7 = n2;
                                n5 = n4;
                                if (n2 + 1 < this.paraCount) {
                                    arrby = this.paras_level;
                                    n7 = n2 + 1;
                                    n5 = arrby[n7];
                                    n8 = this.paras_limit[n7];
                                }
                            }
                        }
                        n2 = by2 & -128;
                        n4 = (byte)(by2 & 127);
                        if (n4 < n5) break block17;
                        n6 = n4;
                        if (125 >= n4) break block18;
                    }
                    if (n4 != 0) break block19;
                    if (by == 7) {
                        n6 = n4;
                    } else {
                        n4 = n5;
                        this.levels[i] = (byte)(n4 | n2);
                        n6 = n4;
                    }
                }
                this.flags = n2 != 0 ? (this.flags |= Bidi.DirPropFlagO((byte)n6)) : (this.flags |= Bidi.DirPropFlagE((byte)n6) | Bidi.DirPropFlag(by));
                n3 = n8;
                n2 = n7;
                n4 = n5;
                continue;
            }
            arrby = new StringBuilder();
            arrby.append("level ");
            arrby.append(n4);
            arrby.append(" out of bounds at ");
            arrby.append(i);
            throw new IllegalArgumentException(arrby.toString());
        }
        n = this.flags;
        if ((MASK_EMBEDDING & n) != 0) {
            this.flags = n | Bidi.DirPropFlagLR(this.paraLevel);
        }
        return this.directionFromFlags();
    }

    private void checkParaCount() {
        int n = this.paraCount;
        byte[] arrby = this.paras_level;
        if (n <= arrby.length) {
            return;
        }
        int n2 = arrby.length;
        int[] arrn = this.paras_limit;
        arrby = this.paras_level;
        try {
            this.paras_limit = new int[n * 2];
            this.paras_level = new byte[n * 2];
        }
        catch (Exception exception) {
            throw new OutOfMemoryError("Failed to allocate memory for paras");
        }
        System.arraycopy(arrn, 0, this.paras_limit, 0, n2);
        System.arraycopy((byte[])arrby, (int)0, (byte[])this.paras_level, (int)0, (int)n2);
    }

    private byte directionFromFlags() {
        int n = this.flags;
        if ((MASK_RTL & n) == 0 && ((n & Bidi.DirPropFlag((byte)5)) == 0 || (this.flags & MASK_POSSIBLE_N) == 0)) {
            return 0;
        }
        if ((this.flags & MASK_LTR) == 0) {
            return 1;
        }
        return 2;
    }

    private byte firstL_R_AL() {
        int n = 10;
        int n2 = 0;
        int n3 = n;
        while (n2 < this.prologue.length()) {
            block6 : {
                int n4;
                block4 : {
                    block5 : {
                        n4 = this.prologue.codePointAt(n2);
                        n = n2 + Character.charCount(n4);
                        n4 = (byte)this.getCustomizedClass(n4);
                        if (n3 != 10) break block4;
                        if (n4 == 0 || n4 == 1) break block5;
                        n2 = n3;
                        if (n4 != 13) break block6;
                    }
                    n2 = n4;
                    break block6;
                }
                n2 = n3;
                if (n4 == 7) {
                    n2 = 10;
                }
            }
            n3 = n2;
            n2 = n;
        }
        return (byte)n3;
    }

    private byte firstL_R_AL_EN_AN() {
        int n;
        for (int i = 0; i < this.epilogue.length(); i += Character.charCount((int)n)) {
            n = this.epilogue.codePointAt(i);
            if ((n = (int)((byte)this.getCustomizedClass(n))) == 0) {
                return 0;
            }
            if (n != 1 && n != 13) {
                if (n != 2) continue;
                return 2;
            }
            return 1;
        }
        return 4;
    }

    private void fixN0c(BracketData bracketData, int n, int n2, byte by) {
        IsoRun isoRun = bracketData.isoRuns[bracketData.isoRunLast];
        ++n;
        while (n < isoRun.limit) {
            Opening opening = bracketData.openings[n];
            if (opening.match < 0) {
                if (n2 < opening.contextPos) break;
                if (n2 < opening.position) {
                    if (by == opening.contextDir) break;
                    int n3 = opening.position;
                    this.dirProps[n3] = by;
                    int n4 = -opening.match;
                    this.dirProps[n4] = by;
                    opening.match = 0;
                    this.fixN0c(bracketData, n, n3, by);
                    this.fixN0c(bracketData, n, n4, by);
                }
            }
            ++n;
        }
    }

    public static byte getBaseDirection(CharSequence charSequence) {
        if (charSequence != null && charSequence.length() != 0) {
            int n = charSequence.length();
            int n2 = 0;
            while (n2 < n) {
                byte by = UCharacter.getDirectionality(UCharacter.codePointAt(charSequence, n2));
                if (by == 0) {
                    return 0;
                }
                if (by != 1 && by != 13) {
                    n2 = UCharacter.offsetByCodePoints(charSequence, n2, 1);
                    continue;
                }
                return 1;
            }
            return 3;
        }
        return 3;
    }

    private void getDirProps() {
        int n;
        this.flags = 0;
        boolean bl = Bidi.IsDefaultLevel(this.paraLevel);
        boolean bl2 = bl && ((n = this.reorderingMode) == 5 || n == 6);
        this.lastArabicPos = -1;
        int n2 = 0;
        boolean bl3 = (this.reorderingOptions & 2) != 0;
        int n3 = 10;
        int[] arrn = new int[126];
        byte[] arrby = new byte[126];
        int n4 = -1;
        if ((this.reorderingOptions & 4) != 0) {
            this.length = 0;
        }
        n = this.paraLevel;
        int n5 = n & 1;
        if (bl) {
            this.paras_level[0] = (byte)n5;
            n3 = n5;
            if (this.prologue != null && (n = (int)this.firstL_R_AL()) != 10) {
                this.paras_level[0] = n == 0 ? (byte)(false ? 1 : 0) : (byte)(true ? 1 : 0);
                n = 0;
            } else {
                n = 1;
            }
        } else {
            this.paras_level[0] = (byte)n;
            n = 0;
        }
        int n6 = 0;
        do {
            byte[] arrby2;
            int n7;
            int n8 = n6;
            n6 = this.originalLength;
            if (n8 >= n6) break;
            int n9 = UTF16.charAt(this.text, 0, n6, n8);
            int n10 = n8 + UTF16.getCharCount(n9);
            n6 = n10 - 1;
            byte by = (byte)this.getCustomizedClass(n9);
            this.flags |= Bidi.DirPropFlag(by);
            this.dirProps[n6] = by;
            if (n6 > n8) {
                this.flags |= Bidi.DirPropFlag((byte)18);
                do {
                    arrby2 = this.dirProps;
                    arrby2[--n6] = (byte)18;
                } while (n6 > n8);
            }
            int n11 = n2;
            if (bl3) {
                n11 = n2;
                if (Bidi.IsBidiControlChar(n9)) {
                    n11 = n2 + 1;
                }
            }
            if (by == 0) {
                if (n == 1) {
                    this.paras_level[this.paraCount - 1] = (byte)(false ? 1 : 0);
                    n8 = 0;
                } else {
                    n8 = n;
                    if (n == 2) {
                        if (n4 <= 125) {
                            this.flags |= Bidi.DirPropFlag((byte)20);
                        }
                        n8 = 3;
                    }
                }
                n3 = 0;
                n6 = n10;
                n2 = n11;
                n = n8;
                continue;
            }
            if (by != 1 && by != 13) {
                if (by >= 19 && by <= 21) {
                    if (++n4 <= 125) {
                        arrn[n4] = n10 - 1;
                        arrby[n4] = (byte)n;
                    }
                    if (by == 19) {
                        this.dirProps[n10 - 1] = (byte)20;
                        n = 2;
                        n6 = n10;
                        n2 = n11;
                        continue;
                    }
                    n = 3;
                    n6 = n10;
                    n2 = n11;
                    continue;
                }
                if (by == 22) {
                    if (n == 2 && n4 <= 125) {
                        this.flags |= Bidi.DirPropFlag((byte)20);
                    }
                    n8 = n3;
                    n7 = n;
                    if (n4 >= 0) {
                        if (n4 <= 125) {
                            n = arrby[n4];
                        }
                        --n4;
                        n6 = n10;
                        n2 = n11;
                        continue;
                    }
                } else {
                    n8 = n3;
                    n7 = n;
                    if (by == 7) {
                        if (n10 < this.originalLength && n9 == 13 && this.text[n10] == '\n') {
                            n8 = n3;
                            n7 = n;
                        } else {
                            arrby2 = this.paras_limit;
                            n6 = this.paraCount;
                            arrby2[n6 - 1] = n10;
                            if (bl2 && n3 == 1) {
                                this.paras_level[n6 - 1] = (byte)(true ? 1 : 0);
                            }
                            if ((this.reorderingOptions & 4) != 0) {
                                this.length = n10;
                                this.controlCount = n11;
                            }
                            n8 = n3;
                            n7 = n;
                            if (n10 < this.originalLength) {
                                ++this.paraCount;
                                this.checkParaCount();
                                if (bl) {
                                    this.paras_level[this.paraCount - 1] = (byte)n5;
                                    n = 1;
                                    n3 = n5;
                                } else {
                                    this.paras_level[this.paraCount - 1] = this.paraLevel;
                                    n = 0;
                                }
                                n4 = -1;
                                n6 = n10;
                                n2 = n11;
                                continue;
                            }
                        }
                    }
                }
            } else {
                if (n == 1) {
                    this.paras_level[this.paraCount - 1] = (byte)(true ? 1 : 0);
                    n3 = 0;
                } else {
                    n3 = n;
                    if (n == 2) {
                        if (n4 <= 125) {
                            this.dirProps[arrn[n4]] = (byte)21;
                            n = this.flags;
                            this.flags = Bidi.DirPropFlag((byte)21) | n;
                        }
                        n3 = 3;
                    }
                }
                n8 = n = 1;
                n7 = n3;
                if (by == 13) {
                    this.lastArabicPos = n10 - 1;
                    n7 = n3;
                    n8 = n;
                }
            }
            n6 = n10;
            n2 = n11;
            n3 = n8;
            n = n7;
        } while (true);
        n5 = n4;
        if (n4 > 125) {
            n5 = 125;
            n = 2;
        }
        while (n5 >= 0) {
            if (n == 2) {
                this.flags |= Bidi.DirPropFlag((byte)20);
                break;
            }
            n = arrby[n5];
            --n5;
        }
        if ((this.reorderingOptions & 4) != 0) {
            if (this.length < this.originalLength) {
                --this.paraCount;
            }
        } else {
            this.paras_limit[this.paraCount - 1] = this.originalLength;
            this.controlCount = n2;
        }
        if (bl2 && n3 == 1) {
            this.paras_level[this.paraCount - 1] = (byte)(true ? 1 : 0);
        }
        if (bl) {
            this.paraLevel = this.paras_level[0];
        }
        for (n = 0; n < this.paraCount; ++n) {
            this.flags |= Bidi.DirPropFlagLR(this.paras_level[n]);
        }
        if (this.orderParagraphsLTR && (this.flags & Bidi.DirPropFlag((byte)7)) != 0) {
            this.flags |= Bidi.DirPropFlag((byte)0);
        }
    }

    private void getDirPropsMemory(boolean bl, int n) {
        this.dirPropsMemory = (byte[])this.getMemory("DirProps", this.dirPropsMemory, Byte.TYPE, bl, n);
    }

    private void getInitialDirPropsMemory(int n) {
        this.getDirPropsMemory(true, n);
    }

    private void getInitialLevelsMemory(int n) {
        this.getLevelsMemory(true, n);
    }

    private void getInitialRunsMemory(int n) {
        this.getRunsMemory(true, n);
    }

    private void getLevelsMemory(boolean bl, int n) {
        this.levelsMemory = (byte[])this.getMemory("Levels", this.levelsMemory, Byte.TYPE, bl, n);
    }

    private Object getMemory(String string, Object object, Class<?> class_, boolean bl, int n) {
        int n2 = Array.getLength(object);
        if (n == n2) {
            return object;
        }
        if (!bl) {
            if (n <= n2) {
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to allocate memory for ");
            ((StringBuilder)object).append(string);
            throw new OutOfMemoryError(((StringBuilder)object).toString());
        }
        try {
            object = Array.newInstance(class_, n);
            return object;
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to allocate memory for ");
            stringBuilder.append(string);
            throw new OutOfMemoryError(stringBuilder.toString());
        }
    }

    private void getRunsMemory(boolean bl, int n) {
        this.runsMemory = (BidiRun[])this.getMemory("Runs", this.runsMemory, BidiRun.class, bl, n);
    }

    public static int[] invertMap(int[] arrn) {
        if (arrn == null) {
            return null;
        }
        return BidiLine.invertMap(arrn);
    }

    private byte lastL_R_AL() {
        int n;
        for (int i = this.prologue.length(); i > 0; i -= Character.charCount((int)n)) {
            n = this.prologue.codePointBefore(i);
            if ((n = (int)((byte)this.getCustomizedClass(n))) == 0) {
                return 0;
            }
            if (n != 1 && n != 13) {
                if (n != 7) continue;
                return 4;
            }
            return 1;
        }
        return 4;
    }

    private void processPropertySeq(LevState levState, short n, int n2, int n3) {
        byte by;
        byte by2;
        block37 : {
            block36 : {
                Object object = levState.impTab;
                short[] arrs = levState.impAct;
                int n4 = levState.state;
                by = object[n4][n];
                levState.state = Bidi.GetState(by);
                short s = arrs[Bidi.GetAction(by)];
                by2 = object[levState.state][7];
                if (s == 0) break block36;
                switch (s) {
                    default: {
                        throw new IllegalStateException("Internal ICU error in processPropertySeq");
                    }
                    case 14: {
                        n4 = (byte)(levState.runLevel + 1);
                        for (n = (int)(n2 - 1); n >= levState.startON; n = (int)(n - 1)) {
                            object = this.levels;
                            if (object[n] <= n4) continue;
                            object[n] = (byte[])((byte)(object[n] - 2));
                        }
                        break;
                    }
                    case 13: {
                        s = levState.runLevel;
                        n = n2 - 1;
                        while (n >= levState.startON) {
                            n4 = n;
                            if (this.levels[n] == s + 3) {
                                n4 = n;
                                do {
                                    object = this.levels;
                                    n = --n4;
                                    if (object[n4] != s + 3) break;
                                    object[n4] = (byte[])((byte)(object[n4] - 2));
                                } while (true);
                                do {
                                    n4 = --n;
                                } while (this.levels[n] == s);
                            }
                            object[n4] = (object = this.levels)[n4] == s + 2 ? (Object)((byte)s) : (byte[])((byte)(s + 1));
                            n = n4 - 1;
                        }
                        break;
                    }
                    case 12: {
                        n4 = (byte)(levState.runLevel + by2);
                        for (n = (int)levState.startON; n < n2; n = (int)(n + 1)) {
                            object = this.levels;
                            if (object[n] >= n4) continue;
                            object[n] = (byte[])((byte)n4);
                        }
                        object = this.insertPoints;
                        object.confirmed = object.size;
                        levState.startON = n2;
                        break;
                    }
                    case 11: {
                        object = this.insertPoints;
                        object.size = object.confirmed;
                        if (n != 5) break;
                        this.addPoint(n2, 4);
                        object = this.insertPoints;
                        object.confirmed = object.size;
                        break;
                    }
                    case 10: {
                        this.addPoint(n2, 1);
                        this.addPoint(n2, 2);
                        break;
                    }
                    case 9: {
                        for (n = (int)(n2 - 1); n >= 0 && (this.levels[n] & 1) == 0; n = (int)(n - 1)) {
                        }
                        if (n >= 0) {
                            this.addPoint(n, 4);
                            object = this.insertPoints;
                            object.confirmed = object.size;
                        }
                        levState.startON = n2;
                        break;
                    }
                    case 8: {
                        levState.lastStrongRTL = n3 - 1;
                        levState.startON = -1;
                        break;
                    }
                    case 7: {
                        if (n == 3 && this.dirProps[n2] == 5 && this.reorderingMode != 6) {
                            if (levState.startL2EN == -1) {
                                levState.lastStrongRTL = n3 - 1;
                                break;
                            }
                            if (levState.startL2EN >= 0) {
                                this.addPoint(levState.startL2EN, 1);
                                levState.startL2EN = -2;
                            }
                            this.addPoint(n2, 1);
                            break;
                        }
                        if (levState.startL2EN != -1) break;
                        levState.startL2EN = n2;
                        break;
                    }
                    case 6: {
                        if (this.insertPoints.points.length > 0) {
                            object = this.insertPoints;
                            object.size = object.confirmed;
                        }
                        levState.startON = -1;
                        levState.startL2EN = -1;
                        levState.lastStrongRTL = n3 - 1;
                        break;
                    }
                    case 5: {
                        if (levState.startL2EN >= 0) {
                            this.addPoint(levState.startL2EN, 1);
                        }
                        levState.startL2EN = -1;
                        if (this.insertPoints.points.length != 0 && this.insertPoints.size > this.insertPoints.confirmed) {
                            for (n4 = levState.lastStrongRTL + 1; n4 < n2; ++n4) {
                                object = this.levels;
                                object[n4] = (byte[])((byte)(object[n4] - 2 & -2));
                            }
                            object = this.insertPoints;
                            object.confirmed = object.size;
                            levState.lastStrongRTL = -1;
                            if (n != 5) break;
                            this.addPoint(n2, 1);
                            object = this.insertPoints;
                            object.confirmed = object.size;
                            break;
                        }
                        levState.lastStrongRTL = -1;
                        n4 = (object[n4][7] & 1) != 0 && levState.startON > 0 ? levState.startON : n2;
                        if (n == 5) {
                            this.addPoint(n2, 1);
                            object = this.insertPoints;
                            object.confirmed = object.size;
                        }
                        n = n4;
                        break block37;
                    }
                    case 4: {
                        by = (byte)(levState.runLevel + 2);
                        this.setLevelsOutsideIsolates(levState.startON, n2, by);
                        break;
                    }
                    case 3: {
                        by = (byte)(levState.runLevel + 1);
                        this.setLevelsOutsideIsolates(levState.startON, n2, by);
                        break;
                    }
                    case 2: {
                        n = levState.startON;
                        break block37;
                    }
                    case 1: {
                        levState.startON = n2;
                    }
                }
            }
            n = n2;
        }
        if (by2 != 0 || n < n2) {
            by = (byte)(levState.runLevel + by2);
            if (n >= levState.runStart) {
                while (n < n3) {
                    this.levels[n] = by;
                    n = n + 1;
                }
            } else {
                this.setLevelsOutsideIsolates(n, n3, by);
            }
        }
    }

    public static int[] reorderLogical(byte[] arrby) {
        return BidiLine.reorderLogical(arrby);
    }

    public static int[] reorderVisual(byte[] arrby) {
        return BidiLine.reorderVisual(arrby);
    }

    public static void reorderVisually(byte[] arrobject, int n, Object[] arrobject2, int n2, int n3) {
        byte[] arrby = new byte[n3];
        System.arraycopy((byte[])arrobject, (int)n, (byte[])arrby, (int)0, (int)n3);
        arrby = Bidi.reorderVisual(arrby);
        arrobject = new Object[n3];
        System.arraycopy(arrobject2, n2, arrobject, 0, n3);
        for (n = 0; n < n3; ++n) {
            arrobject2[n2 + n] = arrobject[arrby[n]];
        }
    }

    public static boolean requiresBidi(char[] arrc, int n, int n2) {
        while (n < n2) {
            if ((1 << UCharacter.getDirection(arrc[n]) & 57378) != 0) {
                return true;
            }
            ++n;
        }
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private byte resolveExplicitLevels() {
        block45 : {
            block44 : {
                block43 : {
                    var1_1 = this.GetParaLevelAt(0);
                    this.isolateCount = 0;
                    var2_2 = this.directionFromFlags();
                    if (var2_2 != 2) {
                        return (byte)var2_2;
                    }
                    var3_3 = this.reorderingMode;
                    var4_4 = true;
                    if (var3_3 > 1) break block43;
                    if ((this.flags & (Bidi.MASK_EXPLICIT | Bidi.MASK_ISO)) == 0) break block44;
                    var10_17 = var1_1;
                    var5_7 = var1_1;
                    var6_10 = 0;
                    var9_16 = new short[127];
                    var3_3 = 0;
                    var11_18 = 0;
                    var12_19 = 0;
                    var13_20 = 0;
                    var14_21 = new BracketData();
                    this.bracketInit(var14_21);
                    var9_16[0] = (short)var1_1;
                    this.flags = 0;
                    var2_2 = var5_7;
                    var8_14 = var10_17;
                    break block45;
                }
                var3_3 = 0;
                while (var3_3 < this.paraCount) {
                    var5_5 = this.paras_limit[var3_3];
                    var6_8 = this.paras_level[var3_3];
                    for (var1_1 = var3_3 == 0 ? 0 : this.paras_limit[var3_3 - 1]; var1_1 < var5_5; ++var1_1) {
                        this.levels[var1_1] = var6_8;
                    }
                    ++var3_3;
                }
                return (byte)var2_2;
            }
            var7_11 = new BracketData();
            this.bracketInit(var7_11);
            var3_3 = 0;
            while (var3_3 < this.paraCount) {
                var5_6 = this.paras_limit[var3_3];
                var8_13 = this.paras_level[var3_3];
                for (var1_1 = var3_3 == 0 ? 0 : this.paras_limit[var3_3 - 1]; var1_1 < var5_6; ++var1_1) {
                    this.levels[var1_1] = var8_13;
                    var6_9 = this.dirProps[var1_1];
                    if (var6_9 == 18) continue;
                    if (var6_9 == 7) {
                        if (var1_1 + 1 >= this.length || (var9_15 = this.text)[var1_1] == '\r' && var9_15[var1_1 + 1] == '\n') continue;
                        this.bracketProcessB(var7_11, var8_13);
                        continue;
                    }
                    this.bracketProcessChar(var7_11, var1_1);
                }
                ++var3_3;
            }
            return (byte)var2_2;
        }
        for (var1_1 = 0; var1_1 < this.length; ++var1_1) {
            block47 : {
                block46 : {
                    var15_22 = this.dirProps[var1_1];
                    switch (var15_22) {
                        default: {
                            if (Bidi.NoOverride((byte)var8_14) != Bidi.NoOverride((byte)var2_2)) {
                                this.bracketProcessBoundary(var14_21, var6_10, (byte)var2_2, (byte)var8_14);
                                this.flags |= Bidi.DirPropFlagMultiRuns;
                                if ((var8_14 & -128) == 0) break;
                                this.flags |= Bidi.DirPropFlagO((byte)var8_14);
                            }
                            break block46;
                        }
                        case 22: {
                            if (Bidi.NoOverride((byte)var8_14) != Bidi.NoOverride((byte)var2_2)) {
                                this.bracketProcessBoundary(var14_21, var6_10, (byte)var2_2, (byte)var8_14);
                                this.flags |= Bidi.DirPropFlagMultiRuns;
                            }
                            if (var11_18 > 0) {
                                --var11_18;
                                this.dirProps[var1_1] = (byte)9;
                            } else if (var13_20 > 0) {
                                this.flags |= Bidi.DirPropFlag((byte)22);
                                var6_10 = var1_1;
                                var12_19 = 0;
                                while (var9_16[var3_3] < 256) {
                                    --var3_3;
                                }
                                --var3_3;
                                --var13_20;
                                this.bracketProcessPDI(var14_21);
                            } else {
                                this.dirProps[var1_1] = (byte)9;
                            }
                            var8_14 = (byte)(var9_16[var3_3] & -257);
                            this.flags |= Bidi.DirPropFlag((byte)10) | Bidi.DirPropFlagLR((byte)var8_14);
                            var10_17 = var8_14;
                            this.levels[var1_1] = Bidi.NoOverride((byte)var8_14);
                            var5_7 = var10_17;
                            var4_4 = true;
                            break block47;
                        }
                        case 20: 
                        case 21: {
                            this.flags |= Bidi.DirPropFlag((byte)10) | Bidi.DirPropFlagLR((byte)var8_14);
                            this.levels[var1_1] = Bidi.NoOverride((byte)var8_14);
                            if (Bidi.NoOverride((byte)var8_14) != Bidi.NoOverride((byte)var2_2)) {
                                this.bracketProcessBoundary(var14_21, var6_10, (byte)var2_2, (byte)var8_14);
                                this.flags |= Bidi.DirPropFlagMultiRuns;
                            }
                            if ((var5_7 = (int)(var15_22 == 20 ? (byte)(var8_14 + 2 & 126) : (byte)(Bidi.NoOverride((byte)var8_14) + 1 | 1))) <= 125 && var11_18 == 0 && var12_19 == 0) {
                                this.flags |= Bidi.DirPropFlag(var15_22);
                                if (++var13_20 > this.isolateCount) {
                                    this.isolateCount = var13_20;
                                }
                                var2_2 = var5_7;
                                var9_16[++var3_3] = (short)(var2_2 + 256);
                                this.bracketProcessLRI_RLI(var14_21, (byte)var2_2);
                                var6_10 = var1_1;
                                var4_4 = true;
                                var5_7 = var8_14;
                                var10_17 = var2_2;
                            } else {
                                this.dirProps[var1_1] = (byte)9;
                                ++var11_18;
                                var5_7 = var8_14;
                                var4_4 = true;
                                var10_17 = var8_14;
                            }
                            break block47;
                        }
                        case 18: {
                            this.levels[var1_1] = var2_2;
                            this.flags |= Bidi.DirPropFlag((byte)18);
                            var4_4 = true;
                            var10_17 = var8_14;
                            var5_7 = var2_2;
                            break block47;
                        }
                        case 16: {
                            this.flags |= Bidi.DirPropFlag((byte)18);
                            this.levels[var1_1] = var2_2;
                            if (var11_18 > 0) {
                                var4_4 = true;
                                var10_17 = var8_14;
                                var5_7 = var2_2;
                            } else if (var12_19 > 0) {
                                --var12_19;
                                var4_4 = true;
                                var10_17 = var8_14;
                                var5_7 = var2_2;
                            } else if (var3_3 > 0 && var9_16[var3_3] < 256) {
                                var10_17 = (byte)var9_16[--var3_3];
                                var6_10 = var1_1;
                                var4_4 = true;
                                var5_7 = var2_2;
                            } else {
                                var4_4 = true;
                                var10_17 = var8_14;
                                var5_7 = var2_2;
                            }
                            break block47;
                        }
                        case 11: 
                        case 12: 
                        case 14: 
                        case 15: {
                            this.flags |= Bidi.DirPropFlag((byte)18);
                            this.levels[var1_1] = var2_2;
                            var5_7 = var15_22 != 11 && var15_22 != 12 ? (byte)(Bidi.NoOverride((byte)var8_14) + 1 | 1) : (byte)(var8_14 + 2 & 126);
                            var4_4 = true;
                            if (var5_7 > 125 || var11_18 != 0 || var12_19 != 0) ** GOTO lbl163
                            if (var15_22 == 12) ** GOTO lbl158
                            var10_17 = var5_7;
                            if (var15_22 != 15) ** GOTO lbl159
lbl158: // 2 sources:
                            var10_17 = (byte)(var5_7 | -128);
lbl159: // 2 sources:
                            var9_16[++var3_3] = (short)var10_17;
                            var6_10 = var1_1;
                            var5_7 = var2_2;
                            break block47;
lbl163: // 1 sources:
                            if (var11_18 == 0) {
                                ++var12_19;
                                var10_17 = var8_14;
                                var5_7 = var2_2;
                            } else {
                                var10_17 = var8_14;
                                var5_7 = var2_2;
                            }
                            break block47;
                        }
                        case 7: {
                            this.flags |= Bidi.DirPropFlag((byte)7);
                            this.levels[var1_1] = this.GetParaLevelAt(var1_1);
                            if (var1_1 + 1 < this.length) {
                                var7_12 = this.text;
                                if (var7_12[var1_1] == '\r' && var7_12[var1_1 + 1] == '\n') {
                                    var10_17 = var8_14;
                                    var5_7 = var2_2;
                                } else {
                                    var3_3 = 0;
                                    var8_14 = var5_7 = this.GetParaLevelAt(var1_1 + 1);
                                    var9_16[0] = (short)var8_14;
                                    this.bracketProcessB(var14_21, (byte)var8_14);
                                    var13_20 = 0;
                                    var12_19 = 0;
                                    var11_18 = 0;
                                    var10_17 = var8_14;
                                }
                            } else {
                                var10_17 = var8_14;
                                var5_7 = var2_2;
                            }
                            break block47;
                        }
                    }
                    this.flags |= Bidi.DirPropFlagE((byte)var8_14);
                }
                this.levels[var1_1] = var8_14;
                this.bracketProcessChar(var14_21, var1_1);
                this.flags |= Bidi.DirPropFlag(this.dirProps[var1_1]);
                var5_7 = var8_14;
                var10_17 = var8_14;
            }
            var8_14 = var10_17;
            var2_2 = var5_7;
        }
        var1_1 = this.flags;
        if ((Bidi.MASK_EMBEDDING & var1_1) != 0) {
            this.flags = var1_1 | Bidi.DirPropFlagLR(this.paraLevel);
        }
        if (this.orderParagraphsLTR == false) return this.directionFromFlags();
        if ((this.flags & Bidi.DirPropFlag((byte)7)) == 0) return this.directionFromFlags();
        this.flags |= Bidi.DirPropFlag((byte)0);
        return this.directionFromFlags();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void resolveImplicitLevels(int n, int n2, short s, short s2) {
        int n3;
        short s3;
        int n4;
        short s4;
        LevState levState = new LevState();
        int n5 = 1;
        int n6 = -1;
        n3 = n < this.lastArabicPos && (this.GetParaLevelAt(n) & 1) > 0 && ((n3 = this.reorderingMode) == 5 || n3 == 6) ? 1 : 0;
        levState.startL2EN = -1;
        levState.lastStrongRTL = -1;
        levState.runStart = n;
        levState.runLevel = this.levels[n];
        levState.impTab = this.impTabPair.imptab[levState.runLevel & 1];
        levState.impAct = this.impTabPair.impact[levState.runLevel & 1];
        short s5 = n == 0 && this.prologue != null && (n4 = this.lastL_R_AL()) != 4 ? (s = (short)n4) : s;
        byte[] arrby = this.dirProps;
        if (arrby[n] == 22) {
            levState.startON = this.isolates[this.isolateCount].startON;
            n4 = this.isolates[this.isolateCount].start1;
            s4 = this.isolates[this.isolateCount].stateImp;
            levState.state = this.isolates[this.isolateCount].state;
            --this.isolateCount;
        } else {
            levState.startON = -1;
            n4 = n;
            s4 = arrby[n] == 17 ? (short)(s5 + 1) : (short)0;
            levState.state = (short)(false ? 1 : 0);
            this.processPropertySeq(levState, s5, n, n);
        }
        int n7 = n;
        s = (short)n;
        int n8 = n4;
        int n9 = n3;
        n3 = n6;
        n4 = n5;
        do {
            s3 = s4;
            if (s > n2) break;
            if (s >= n2) {
                for (n5 = n2 - 1; n5 > n && (Bidi.DirPropFlag(this.dirProps[n5]) & MASK_BN_EXPLICIT) != 0; --n5) {
                }
                if ((n5 = this.dirProps[n5]) == 20 || n5 == 21) break;
                n5 = s2;
            } else {
                int n10 = this.dirProps[s];
                if (n10 == 7) {
                    this.isolateCount = -1;
                }
                s4 = (short)n4;
                n6 = n3;
                int n11 = n10;
                if (n9 != 0) {
                    if (n10 == 13) {
                        n11 = 1;
                        s4 = (short)n4;
                        n6 = n3;
                    } else {
                        s4 = (short)n4;
                        n6 = n3;
                        n11 = n10;
                        if (n10 == 2) {
                            block28 : {
                                n5 = n4;
                                n4 = n3;
                                if (n3 <= s) {
                                    n6 = 1;
                                    s4 = (short)n2;
                                    n3 = s + 1;
                                    do {
                                        n5 = n6;
                                        n4 = s4;
                                        if (n3 >= n2) break block28;
                                        n4 = this.dirProps[n3];
                                        if (n4 == 0 || n4 == 1 || n4 == 13) break;
                                        ++n3;
                                    } while (true);
                                    n5 = (short)n4;
                                    n4 = n3;
                                }
                            }
                            s4 = (short)n5;
                            n6 = n4;
                            n11 = n10;
                            if (n5 == 13) {
                                n11 = 5;
                                n6 = n4;
                                s4 = (short)n5;
                            }
                        }
                    }
                }
                n5 = groupProp[n11];
                n3 = n6;
                n4 = s4;
            }
            s5 = impTabProps[s3][n5];
            n6 = Bidi.GetStateProps(s5);
            s4 = Bidi.GetActionProps(s5);
            n5 = s4;
            if (s == n2) {
                n5 = s4;
                if (s4 == 0) {
                    n5 = 1;
                }
            }
            if (n5 != 0) {
                s5 = impTabProps[s3][15];
                if (n5 != 1) {
                    if (n5 != 2) {
                        if (n5 != 3) {
                            if (n5 != 4) throw new IllegalStateException("Internal ICU error in resolveImplicitLevels");
                            this.processPropertySeq(levState, s5, n8, n7);
                            s4 = s;
                            n5 = n7;
                            n7 = s4;
                        } else {
                            this.processPropertySeq(levState, s5, n8, n7);
                            this.processPropertySeq(levState, (short)4, n7, s);
                            n5 = s;
                        }
                    } else {
                        n7 = s;
                        n5 = n8;
                    }
                } else {
                    this.processPropertySeq(levState, s5, n8, s);
                    n5 = s;
                }
            } else {
                n5 = n8;
            }
            s = (short)(s + 1);
            s4 = (short)n6;
            n8 = n5;
        } while (true);
        s5 = n2 == this.length && this.epilogue != null && (s = (short)this.firstL_R_AL_EN_AN()) != 4 ? (s = (short)s) : s2;
        for (s = (short)(n2 - 1); s > n && (Bidi.DirPropFlag(this.dirProps[s]) & MASK_BN_EXPLICIT) != 0; s = (short)(s - 1)) {
        }
        n = this.dirProps[s];
        if ((n == 20 || n == 21) && n2 < this.length) {
            ++this.isolateCount;
            Isolate[] arrisolate = this.isolates;
            n = this.isolateCount;
            if (arrisolate[n] == null) {
                arrisolate[n] = new Isolate();
            }
            Isolate[] arrisolate2 = this.isolates;
            n = this.isolateCount;
            arrisolate2[n].stateImp = s3;
            arrisolate2[n].state = levState.state;
            Isolate[] arrisolate3 = this.isolates;
            n = this.isolateCount;
            arrisolate3[n].start1 = n8;
            arrisolate3[n].startON = levState.startON;
            return;
        } else {
            this.processPropertySeq(levState, s5, n2, n2);
        }
    }

    private void setLevelsOutsideIsolates(int n, int n2, byte by) {
        int n3 = 0;
        for (int i = n; i < n2; ++i) {
            byte by2 = this.dirProps[i];
            n = n3;
            if (by2 == 22) {
                n = n3 - 1;
            }
            if (n == 0) {
                this.levels[i] = by;
            }
            if (by2 != 20) {
                n3 = n;
                if (by2 != 21) continue;
            }
            n3 = n + 1;
        }
    }

    private void setParaSuccess() {
        this.prologue = null;
        this.epilogue = null;
        this.paraBidi = this;
    }

    public static String writeReverse(String string, int n) {
        if (string != null) {
            if (string.length() > 0) {
                return BidiWriter.writeReverse(string, n);
            }
            return "";
        }
        throw new IllegalArgumentException();
    }

    int Bidi_Abs(int n) {
        if (n < 0) {
            n = -n;
        }
        return n;
    }

    int Bidi_Min(int n, int n2) {
        if (n >= n2) {
            n = n2;
        }
        return n;
    }

    byte GetParaLevelAt(int n) {
        if (this.defaultParaLevel != 0 && n >= this.paras_limit[0]) {
            int n2;
            for (n2 = 1; n2 < this.paraCount && n >= this.paras_limit[n2]; ++n2) {
            }
            int n3 = this.paraCount;
            n = n2;
            if (n2 >= n3) {
                n = n3 - 1;
            }
            return this.paras_level[n];
        }
        return this.paraLevel;
    }

    public boolean baseIsLeftToRight() {
        boolean bl = this.getParaLevel() == 0;
        return bl;
    }

    public int countParagraphs() {
        this.verifyValidParaOrLine();
        return this.paraCount;
    }

    public int countRuns() {
        this.verifyValidParaOrLine();
        BidiLine.getRuns(this);
        return this.runCount;
    }

    public Bidi createLineBidi(int n, int n2) {
        return this.setLine(n, n2);
    }

    public int getBaseLevel() {
        return this.getParaLevel();
    }

    public BidiClassifier getCustomClassifier() {
        return this.customClassifier;
    }

    public int getCustomizedClass(int n) {
        int n2;
        block5 : {
            block4 : {
                int n3;
                BidiClassifier bidiClassifier = this.customClassifier;
                if (bidiClassifier == null) break block4;
                n2 = n3 = bidiClassifier.classify(n);
                if (n3 != 23) break block5;
            }
            n2 = this.bdp.getClass(n);
        }
        n = n2;
        if (n2 >= 23) {
            n = 10;
        }
        return n;
    }

    void getDirPropsMemory(int n) {
        this.getDirPropsMemory(this.mayAllocateText, n);
    }

    public byte getDirection() {
        this.verifyValidParaOrLine();
        return this.direction;
    }

    public int getLength() {
        this.verifyValidParaOrLine();
        return this.originalLength;
    }

    public byte getLevelAt(int n) {
        this.verifyValidParaOrLine();
        this.verifyRange(n, 0, this.length);
        return BidiLine.getLevelAt(this, n);
    }

    public byte[] getLevels() {
        this.verifyValidParaOrLine();
        if (this.length <= 0) {
            return new byte[0];
        }
        return BidiLine.getLevels(this);
    }

    void getLevelsMemory(int n) {
        this.getLevelsMemory(this.mayAllocateText, n);
    }

    public int getLogicalIndex(int n) {
        this.verifyValidParaOrLine();
        this.verifyRange(n, 0, this.resultLength);
        if (this.insertPoints.size == 0 && this.controlCount == 0) {
            byte by = this.direction;
            if (by == 0) {
                return n;
            }
            if (by == 1) {
                return this.length - n - 1;
            }
        }
        BidiLine.getRuns(this);
        return BidiLine.getLogicalIndex(this, n);
    }

    public int[] getLogicalMap() {
        this.countRuns();
        if (this.length <= 0) {
            return new int[0];
        }
        return BidiLine.getLogicalMap(this);
    }

    public BidiRun getLogicalRun(int n) {
        this.verifyValidParaOrLine();
        this.verifyRange(n, 0, this.length);
        return BidiLine.getLogicalRun(this, n);
    }

    void getLogicalToVisualRunsMap() {
        int n;
        if (this.isGoodLogicalToVisualRunsMap) {
            return;
        }
        int n2 = this.countRuns();
        int[] arrn = this.logicalToVisualRunsMap;
        if (arrn == null || arrn.length < n2) {
            this.logicalToVisualRunsMap = new int[n2];
        }
        arrn = new long[n2];
        for (n = 0; n < n2; ++n) {
            arrn[n] = (int)(((long)this.runs[n].start << 32) + (long)n);
        }
        Arrays.sort((long[])arrn);
        for (n = 0; n < n2; ++n) {
            this.logicalToVisualRunsMap[n] = arrn[n] & -1L;
        }
        this.isGoodLogicalToVisualRunsMap = true;
    }

    public byte getParaLevel() {
        this.verifyValidParaOrLine();
        return this.paraLevel;
    }

    public BidiRun getParagraph(int n) {
        this.verifyValidParaOrLine();
        Bidi bidi = this.paraBidi;
        this.verifyRange(n, 0, bidi.length);
        int n2 = 0;
        while (n >= bidi.paras_limit[n2]) {
            ++n2;
        }
        return this.getParagraphByIndex(n2);
    }

    public BidiRun getParagraphByIndex(int n) {
        this.verifyValidParaOrLine();
        this.verifyRange(n, 0, this.paraCount);
        Bidi bidi = this.paraBidi;
        int n2 = n == 0 ? 0 : bidi.paras_limit[n - 1];
        BidiRun bidiRun = new BidiRun();
        bidiRun.start = n2;
        bidiRun.limit = bidi.paras_limit[n];
        bidiRun.level = this.GetParaLevelAt(n2);
        return bidiRun;
    }

    public int getParagraphIndex(int n) {
        this.verifyValidParaOrLine();
        Bidi bidi = this.paraBidi;
        this.verifyRange(n, 0, bidi.length);
        int n2 = 0;
        while (n >= bidi.paras_limit[n2]) {
            ++n2;
        }
        return n2;
    }

    public int getProcessedLength() {
        this.verifyValidParaOrLine();
        return this.length;
    }

    public int getReorderingMode() {
        return this.reorderingMode;
    }

    public int getReorderingOptions() {
        return this.reorderingOptions;
    }

    public int getResultLength() {
        this.verifyValidParaOrLine();
        return this.resultLength;
    }

    public int getRunCount() {
        return this.countRuns();
    }

    public int getRunLevel(int n) {
        this.verifyValidParaOrLine();
        BidiLine.getRuns(this);
        this.verifyRange(n, 0, this.runCount);
        this.getLogicalToVisualRunsMap();
        return this.runs[this.logicalToVisualRunsMap[n]].level;
    }

    public int getRunLimit(int n) {
        this.verifyValidParaOrLine();
        BidiLine.getRuns(this);
        this.verifyRange(n, 0, this.runCount);
        this.getLogicalToVisualRunsMap();
        int n2 = this.logicalToVisualRunsMap[n];
        n = n2 == 0 ? this.runs[n2].limit : this.runs[n2].limit - this.runs[n2 - 1].limit;
        return this.runs[n2].start + n;
    }

    public int getRunStart(int n) {
        this.verifyValidParaOrLine();
        BidiLine.getRuns(this);
        this.verifyRange(n, 0, this.runCount);
        this.getLogicalToVisualRunsMap();
        return this.runs[this.logicalToVisualRunsMap[n]].start;
    }

    void getRunsMemory(int n) {
        this.getRunsMemory(this.mayAllocateRuns, n);
    }

    public char[] getText() {
        this.verifyValidParaOrLine();
        return this.text;
    }

    public String getTextAsString() {
        this.verifyValidParaOrLine();
        return new String(this.text);
    }

    public int getVisualIndex(int n) {
        this.verifyValidParaOrLine();
        this.verifyRange(n, 0, this.length);
        return BidiLine.getVisualIndex(this, n);
    }

    public int[] getVisualMap() {
        this.countRuns();
        if (this.resultLength <= 0) {
            return new int[0];
        }
        return BidiLine.getVisualMap(this);
    }

    public BidiRun getVisualRun(int n) {
        this.verifyValidParaOrLine();
        BidiLine.getRuns(this);
        this.verifyRange(n, 0, this.runCount);
        return BidiLine.getVisualRun(this, n);
    }

    public boolean isInverse() {
        return this.isInverse;
    }

    public boolean isLeftToRight() {
        byte by = this.getDirection();
        boolean bl = true;
        if (by != 0 || (this.paraLevel & 1) != 0) {
            bl = false;
        }
        return bl;
    }

    public boolean isMixed() {
        boolean bl = !this.isLeftToRight() && !this.isRightToLeft();
        return bl;
    }

    public boolean isOrderParagraphsLTR() {
        return this.orderParagraphsLTR;
    }

    public boolean isRightToLeft() {
        byte by = this.getDirection();
        boolean bl = true;
        if (by != 1 || (this.paraLevel & 1) != 1) {
            bl = false;
        }
        return bl;
    }

    public void orderParagraphsLTR(boolean bl) {
        this.orderParagraphsLTR = bl;
    }

    public void setContext(String string, String string2) {
        Object var3_3 = null;
        if (string == null || string.length() <= 0) {
            string = null;
        }
        this.prologue = string;
        string = var3_3;
        if (string2 != null) {
            string = var3_3;
            if (string2.length() > 0) {
                string = string2;
            }
        }
        this.epilogue = string;
    }

    public void setCustomClassifier(BidiClassifier bidiClassifier) {
        this.customClassifier = bidiClassifier;
    }

    public void setInverse(boolean bl) {
        this.isInverse = bl;
        int n = bl ? 4 : 0;
        this.reorderingMode = n;
    }

    public Bidi setLine(int n, int n2) {
        this.verifyValidPara();
        this.verifyRange(n, 0, n2);
        this.verifyRange(n2, 0, this.length + 1);
        if (this.getParagraphIndex(n) == this.getParagraphIndex(n2 - 1)) {
            return BidiLine.setLine(this, n, n2);
        }
        throw new IllegalArgumentException();
    }

    public void setPara(String string, byte by, byte[] arrby) {
        if (string == null) {
            this.setPara(new char[0], by, arrby);
        } else {
            this.setPara(string.toCharArray(), by, arrby);
        }
    }

    public void setPara(AttributedCharacterIterator object) {
        int n;
        byte[] arrby = (byte[])object.getAttribute(TextAttribute.RUN_DIRECTION);
        int n2 = arrby == null ? (n = 126) : (arrby.equals(TextAttribute.RUN_DIRECTION_LTR) ? (n = 0) : (n = 1));
        byte[] arrby2 = null;
        int n3 = object.getEndIndex() - object.getBeginIndex();
        byte[] arrby3 = new byte[n3];
        char[] arrc = new char[n3];
        n = 0;
        char c = object.first();
        while (c != '\uffff') {
            arrc[n] = c;
            Integer n4 = (Integer)object.getAttribute(TextAttribute.BIDI_EMBEDDING);
            arrby = arrby2;
            if (n4 != null) {
                c = (char)n4.byteValue();
                if (c == '\u0000') {
                    arrby = arrby2;
                } else if (c < '\u0000') {
                    arrby = arrby3;
                    arrby3[n] = (byte)('\u0000' - c | -128);
                } else {
                    arrby = arrby3;
                    arrby3[n] = (byte)c;
                }
            }
            c = object.next();
            ++n;
            arrby2 = arrby;
        }
        if ((object = (NumericShaper)object.getAttribute(TextAttribute.NUMERIC_SHAPING)) != null) {
            ((NumericShaper)object).shape(arrc, 0, n3);
        }
        this.setPara(arrc, (byte)n2, arrby2);
    }

    public void setPara(char[] object, byte by, byte[] arrby) {
        int n;
        int n2;
        int n3;
        int n4;
        if (by < 126) {
            this.verifyRange(by, 0, 126);
        }
        char[] arrc = object;
        if (object == null) {
            arrc = new char[]{};
        }
        if (this.reorderingMode == 3) {
            this.setParaRunsOnly(arrc, by);
            return;
        }
        this.paraBidi = null;
        this.text = arrc;
        this.resultLength = n4 = this.text.length;
        this.originalLength = n4;
        this.length = n4;
        this.paraLevel = by;
        this.direction = (byte)(by & 1);
        this.paraCount = 1;
        this.dirProps = new byte[0];
        this.levels = new byte[0];
        this.runs = new BidiRun[0];
        this.isGoodLogicalToVisualRunsMap = false;
        object = this.insertPoints;
        object.size = 0;
        object.confirmed = 0;
        n4 = Bidi.IsDefaultLevel(by) ? (int)by : 0;
        this.defaultParaLevel = (byte)n4;
        n4 = this.length;
        if (n4 == 0) {
            if (Bidi.IsDefaultLevel(by)) {
                this.paraLevel = (byte)(1 & this.paraLevel);
                this.defaultParaLevel = (byte)(false ? 1 : 0);
            }
            this.flags = Bidi.DirPropFlagLR(by);
            this.runCount = 0;
            this.paraCount = 0;
            this.setParaSuccess();
            return;
        }
        this.runCount = -1;
        this.getDirPropsMemory(n4);
        this.dirProps = this.dirPropsMemory;
        this.getDirProps();
        this.trailingWSStart = n4 = this.length;
        if (arrby == null) {
            this.getLevelsMemory(n4);
            this.levels = this.levelsMemory;
            this.direction = this.resolveExplicitLevels();
        } else {
            this.levels = arrby;
            this.direction = this.checkExplicitLevels();
        }
        n4 = this.isolateCount;
        if (n4 > 0 && ((object = this.isolates) == null || ((char[])object).length < n4)) {
            this.isolates = new Isolate[this.isolateCount + 3];
        }
        this.isolateCount = -1;
        n4 = this.direction;
        if (n4 != 0) {
            if (n4 != 1) {
                switch (this.reorderingMode) {
                    default: {
                        break;
                    }
                    case 6: {
                        if ((this.reorderingOptions & 1) != 0) {
                            this.impTabPair = impTab_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS;
                            break;
                        }
                        this.impTabPair = impTab_INVERSE_FOR_NUMBERS_SPECIAL;
                        break;
                    }
                    case 5: {
                        if ((this.reorderingOptions & 1) != 0) {
                            this.impTabPair = impTab_INVERSE_LIKE_DIRECT_WITH_MARKS;
                            break;
                        }
                        this.impTabPair = impTab_INVERSE_LIKE_DIRECT;
                        break;
                    }
                    case 4: {
                        this.impTabPair = impTab_INVERSE_NUMBERS_AS_L;
                        break;
                    }
                    case 3: {
                        throw new InternalError("Internal ICU error in setPara");
                    }
                    case 2: {
                        this.impTabPair = impTab_GROUP_NUMBERS_WITH_R;
                        break;
                    }
                    case 1: {
                        this.impTabPair = impTab_NUMBERS_SPECIAL;
                        break;
                    }
                    case 0: {
                        this.impTabPair = impTab_DEFAULT;
                    }
                }
                if (arrby == null && this.paraCount <= 1 && (this.flags & DirPropFlagMultiRuns) == 0) {
                    this.resolveImplicitLevels(0, this.length, Bidi.GetLRFromLevel(this.GetParaLevelAt(0)), Bidi.GetLRFromLevel(this.GetParaLevelAt(this.length - 1)));
                } else {
                    n4 = 0;
                    byte by2 = this.GetParaLevelAt(0);
                    n2 = by2 < (by = this.levels[0]) ? (int)Bidi.GetLRFromLevel(by) : (int)Bidi.GetLRFromLevel(by2);
                    do {
                        by2 = by;
                        n = n4;
                        int n5 = n > 0 && this.dirProps[n - 1] == 7 ? (n2 = (int)Bidi.GetLRFromLevel(this.GetParaLevelAt(n))) : n2;
                        while ((n3 = n4 + 1) < this.length) {
                            n4 = n3;
                            if (this.levels[n3] == by2) continue;
                            if ((Bidi.DirPropFlag(this.dirProps[n3]) & MASK_BN_EXPLICIT) == 0) break;
                            n4 = n3;
                        }
                        if (n3 < (n4 = this.length)) {
                            n4 = this.levels[n3];
                            by = (byte)n4;
                        } else {
                            n4 = this.GetParaLevelAt(n4 - 1);
                            by = (byte)n4;
                        }
                        int n6 = Bidi.NoOverride(by2) < Bidi.NoOverride(by) ? (n4 = (int)Bidi.GetLRFromLevel(by)) : (n4 = (int)Bidi.GetLRFromLevel(by2));
                        n4 = n;
                        if ((by2 & -128) == 0) {
                            this.resolveImplicitLevels(n, n3, (short)n5, (short)n6);
                        } else {
                            do {
                                object = this.levels;
                                n2 = n4 + 1;
                                object[n4] = (char)(object[n4] & 127);
                                n4 = n2;
                            } while (n2 < n3);
                        }
                        n4 = n3;
                        n2 = n6;
                    } while (n3 < this.length);
                }
                this.adjustWSLevels();
            } else {
                this.trailingWSStart = 0;
            }
        } else {
            this.trailingWSStart = 0;
        }
        if (this.defaultParaLevel > 0 && (this.reorderingOptions & 1) != 0 && ((n4 = this.reorderingMode) == 5 || n4 == 6)) {
            block12 : for (n2 = 0; n2 < this.paraCount; ++n2) {
                object = this.paras_limit;
                n4 = object[n2] - '\u0001';
                if (this.paras_level[n2] == 0) continue;
                n3 = n2 == 0 ? 0 : object[n2 - 1];
                for (n = n4; n >= n3; --n) {
                    by = this.dirProps[n];
                    if (by == 0) {
                        n3 = n4;
                        if (n < n4) {
                            do {
                                n3 = --n4;
                            } while (this.dirProps[n4] == 7);
                        }
                        this.addPoint(n3, 4);
                        continue block12;
                    }
                    if ((Bidi.DirPropFlag(by) & MASK_R_AL) != 0) continue block12;
                }
            }
        }
        this.resultLength = (this.reorderingOptions & 2) != 0 ? (this.resultLength -= this.controlCount) : (this.resultLength += this.insertPoints.size);
        this.setParaSuccess();
    }

    void setParaRunsOnly(char[] arrc, byte n) {
        int n2;
        Object object;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        this.reorderingMode = 0;
        int n8 = arrc.length;
        if (n8 == 0) {
            this.setPara(arrc, (byte)n, null);
            this.reorderingMode = 3;
            return;
        }
        int n9 = this.reorderingOptions;
        if ((n9 & 1) > 0) {
            this.reorderingOptions &= -2;
            this.reorderingOptions |= 2;
        }
        n = (byte)(n & 1);
        this.setPara(arrc, (byte)n, null);
        byte[] arrby = new byte[this.length];
        System.arraycopy((byte[])this.getLevels(), (int)0, (byte[])arrby, (int)0, (int)this.length);
        int n10 = this.trailingWSStart;
        String string = this.writeReordered(2);
        int[] arrn = this.getVisualMap();
        this.reorderingOptions = n9;
        int n11 = this.length;
        int n12 = this.direction;
        this.reorderingMode = 5;
        n = (byte)(n ^ 1);
        this.setPara(string, (byte)n, null);
        BidiLine.getRuns(this);
        int n13 = this.runCount;
        int n14 = 0;
        int n15 = 0;
        int n16 = 0;
        int n17 = n;
        while (n15 < n13) {
            block21 : {
                n2 = this.runs[n15].limit - n16;
                if (n2 < 2) break block21;
                n7 = this.runs[n15].start;
                for (n5 = n7 + 1; n5 < n7 + n2; ++n5) {
                    block23 : {
                        block22 : {
                            n4 = arrn[n5];
                            n6 = arrn[n5 - 1];
                            if (this.Bidi_Abs(n4 - n6) != 1) break block22;
                            n3 = n14;
                            if (arrby[n4] == arrby[n6]) break block23;
                        }
                        n3 = n14 + 1;
                    }
                    n14 = n3;
                }
            }
            ++n15;
            n16 += n2;
        }
        if (n14 > 0) {
            this.getRunsMemory(n13 + n14);
            n9 = this.runCount;
            if (n9 == 1) {
                this.runsMemory[0] = this.runs[0];
            } else {
                System.arraycopy(this.runs, 0, this.runsMemory, 0, n9);
            }
            this.runs = this.runsMemory;
            this.runCount += n14;
            for (n9 = n13; n9 < this.runCount; ++n9) {
                object = this.runs;
                if (object[n9] != null) continue;
                object[n9] = new BidiRun(0, 0, 0);
            }
        }
        n16 = n14;
        n9 = n12;
        n14 = n10;
        for (n5 = n13 - 1; n5 >= 0; --n5) {
            n6 = n5 + n16;
            n15 = n5 == 0 ? this.runs[0].limit : this.runs[n5].limit - this.runs[n5 - 1].limit;
            n7 = this.runs[n5].start;
            n4 = this.runs[n5].level & 1;
            if (n15 < 2) {
                if (n16 > 0) {
                    object = this.runs;
                    object[n6].copyFrom(object[n5]);
                }
                n17 = arrn[n7];
                object = this.runs;
                object[n6].start = n17;
                object[n6].level = (byte)(arrby[n17] ^ n4);
                n17 = n9;
                n9 = n14;
                n14 = n17;
            } else {
                if (n4 > 0) {
                    n17 = n7;
                    n3 = n7 + n15 - 1;
                    n10 = 1;
                } else {
                    n17 = n7 + n15 - 1;
                    n3 = n7;
                    n10 = -1;
                }
                n12 = n17;
                n2 = n17;
                n17 = n6;
                while (n12 != n3) {
                    int n18 = arrn[n12];
                    n6 = arrn[n12 + n10];
                    if (this.Bidi_Abs(n18 - n6) != 1 || arrby[n18] != arrby[n6]) {
                        n6 = this.Bidi_Min(arrn[n2], n18);
                        object = this.runs;
                        object[n17].start = n6;
                        object[n17].level = (byte)(arrby[n6] ^ n4);
                        object[n17].limit = object[n5].limit;
                        object = this.runs[n5];
                        object.limit -= this.Bidi_Abs(n12 - n2) + 1;
                        n2 = this.runs[n5].insertRemove & 10;
                        object = this.runs;
                        object[n17].insertRemove = n2;
                        object = object[n5];
                        object.insertRemove &= n2;
                        n2 = n12 + n10;
                        --n16;
                        --n17;
                    }
                    n12 += n10;
                }
                n15 = n14;
                n14 = n9;
                if (n16 > 0) {
                    object = this.runs;
                    object[n17].copyFrom(object[n5]);
                }
                n9 = this.Bidi_Min(arrn[n2], arrn[n3]);
                object = this.runs;
                object[n17].start = n9;
                object[n17].level = (byte)(arrby[n9] ^ n4);
                n9 = n15;
            }
            n17 = n9;
            n9 = n14;
            n14 = n17;
        }
        this.paraLevel = (byte)(this.paraLevel ^ 1);
        this.text = arrc;
        this.length = n11;
        this.originalLength = n8;
        this.direction = (byte)n9;
        this.levels = arrby;
        this.trailingWSStart = n14;
        if (this.runCount > 1) {
            this.direction = (byte)2;
        }
        this.reorderingMode = 3;
    }

    public void setReorderingMode(int n) {
        if (n >= 0 && n < 7) {
            this.reorderingMode = n;
            boolean bl = n == 4;
            this.isInverse = bl;
            return;
        }
    }

    public void setReorderingOptions(int n) {
        this.reorderingOptions = (n & 2) != 0 ? n & -2 : n;
    }

    boolean testDirPropFlagAt(int n, int n2) {
        boolean bl = (Bidi.DirPropFlag(this.dirProps[n2]) & n) != 0;
        return bl;
    }

    void verifyRange(int n, int n2, int n3) {
        if (n >= n2 && n < n3) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Value ");
        stringBuilder.append(n);
        stringBuilder.append(" is out of range ");
        stringBuilder.append(n2);
        stringBuilder.append(" to ");
        stringBuilder.append(n3);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    void verifyValidPara() {
        if (this == this.paraBidi) {
            return;
        }
        throw new IllegalStateException();
    }

    void verifyValidParaOrLine() {
        Bidi bidi = this.paraBidi;
        if (this == bidi) {
            return;
        }
        if (bidi != null && bidi == bidi.paraBidi) {
            return;
        }
        throw new IllegalStateException();
    }

    public String writeReordered(int n) {
        this.verifyValidParaOrLine();
        if (this.length == 0) {
            return "";
        }
        return BidiWriter.writeReordered(this, n);
    }

    static class BracketData {
        boolean isNumbersSpecial;
        int isoRunLast;
        IsoRun[] isoRuns = new IsoRun[127];
        Opening[] openings = new Opening[20];

        BracketData() {
        }
    }

    private static class ImpTabPair {
        short[][] impact;
        byte[][][] imptab;

        ImpTabPair(byte[][] arrby, byte[][] arrby2, short[] arrs, short[] arrs2) {
            this.imptab = new byte[][][]{arrby, arrby2};
            this.impact = new short[][]{arrs, arrs2};
        }
    }

    static class InsertPoints {
        int confirmed;
        Point[] points = new Point[0];
        int size;

        InsertPoints() {
        }
    }

    static class IsoRun {
        byte contextDir;
        int contextPos;
        byte lastBase;
        byte lastStrong;
        byte level;
        short limit;
        short start;

        IsoRun() {
        }
    }

    static class Isolate {
        int start1;
        int startON;
        short state;
        short stateImp;

        Isolate() {
        }
    }

    private static class LevState {
        short[] impAct;
        byte[][] impTab;
        int lastStrongRTL;
        byte runLevel;
        int runStart;
        int startL2EN;
        int startON;
        short state;

        private LevState() {
        }
    }

    static class Opening {
        byte contextDir;
        int contextPos;
        short flags;
        int match;
        int position;

        Opening() {
        }
    }

    static class Point {
        int flag;
        int pos;

        Point() {
        }
    }

}

