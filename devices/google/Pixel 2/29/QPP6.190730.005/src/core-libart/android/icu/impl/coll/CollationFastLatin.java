/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.coll.CollationData;
import android.icu.impl.coll.CollationSettings;

public final class CollationFastLatin {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int BAIL_OUT = 1;
    public static final int BAIL_OUT_RESULT = -2;
    static final int CASE_AND_TERTIARY_MASK = 31;
    static final int CASE_MASK = 24;
    static final int COMMON_SEC = 160;
    static final int COMMON_SEC_PLUS_OFFSET = 192;
    static final int COMMON_TER = 0;
    static final int COMMON_TER_PLUS_OFFSET = 32;
    static final int CONTRACTION = 1024;
    static final int CONTR_CHAR_MASK = 511;
    static final int CONTR_LENGTH_SHIFT = 9;
    static final int EOS = 2;
    static final int EXPANSION = 2048;
    static final int INDEX_MASK = 1023;
    public static final int LATIN_LIMIT = 384;
    public static final int LATIN_MAX = 383;
    static final int LATIN_MAX_UTF8_LEAD = 197;
    static final int LONG_INC = 8;
    static final int LONG_PRIMARY_MASK = 65528;
    static final int LOWER_CASE = 8;
    static final int MAX_LONG = 4088;
    static final int MAX_SEC_AFTER = 352;
    static final int MAX_SEC_BEFORE = 128;
    static final int MAX_SEC_HIGH = 992;
    static final int MAX_SHORT = 64512;
    static final int MAX_TER_AFTER = 7;
    static final int MERGE_WEIGHT = 3;
    static final int MIN_LONG = 3072;
    static final int MIN_SEC_AFTER = 192;
    static final int MIN_SEC_BEFORE = 0;
    static final int MIN_SEC_HIGH = 384;
    static final int MIN_SHORT = 4096;
    static final int NUM_FAST_CHARS = 448;
    static final int PUNCT_LIMIT = 8256;
    static final int PUNCT_START = 8192;
    static final int SECONDARY_MASK = 992;
    static final int SEC_INC = 32;
    static final int SEC_OFFSET = 32;
    static final int SHORT_INC = 1024;
    static final int SHORT_PRIMARY_MASK = 64512;
    static final int TERTIARY_MASK = 7;
    static final int TER_OFFSET = 32;
    static final int TWO_CASES_MASK = 1572888;
    static final int TWO_COMMON_SEC_PLUS_OFFSET = 12583104;
    static final int TWO_COMMON_TER_PLUS_OFFSET = 2097184;
    static final int TWO_LONG_PRIMARIES_MASK = -458760;
    static final int TWO_LOWER_CASES = 524296;
    static final int TWO_SECONDARIES_MASK = 65012704;
    static final int TWO_SEC_OFFSETS = 2097184;
    static final int TWO_SHORT_PRIMARIES_MASK = -67044352;
    static final int TWO_TERTIARIES_MASK = 458759;
    static final int TWO_TER_OFFSETS = 2097184;
    public static final int VERSION = 2;

    private CollationFastLatin() {
    }

    public static int compareUTF16(char[] arrc, char[] arrc2, int n, CharSequence charSequence, CharSequence charSequence2, int n2) {
        int n3 = n >> 16;
        int n4 = n & 65535;
        int n5 = n2;
        int n6 = n2;
        int n7 = 0;
        int n8 = 0;
        do {
            int n9;
            block92 : {
                long l;
                boolean bl;
                int n10;
                long l2;
                int n11;
                block91 : {
                    block90 : {
                        block85 : {
                            block89 : {
                                block87 : {
                                    block88 : {
                                        block86 : {
                                            n9 = n5;
                                            n10 = n6;
                                            n = n7;
                                            n11 = n8;
                                            if (n7 != 0) break block85;
                                            if (n5 != charSequence.length()) break block86;
                                            n = 2;
                                            n9 = n5;
                                            n10 = n6;
                                            n11 = n8;
                                            break block85;
                                        }
                                        n = n5 + 1;
                                        n9 = charSequence.charAt(n5);
                                        if (n9 > 383) break block87;
                                        n7 = arrc2[n9];
                                        if (n7 == 0) break block88;
                                        n9 = n;
                                        n10 = n6;
                                        n = n7;
                                        n11 = n8;
                                        break block85;
                                    }
                                    if (n9 <= 57 && n9 >= 48 && (n4 & 2) != 0) {
                                        return -2;
                                    }
                                    n7 = arrc[n9];
                                    break block89;
                                }
                                n7 = 8192 <= n9 && n9 < 8256 ? arrc[n9 - 8192 + 384] : CollationFastLatin.lookup(arrc, n9);
                            }
                            if (n7 >= 4096) {
                                n9 = n;
                                n10 = n6;
                                n = n7 &= 64512;
                                n11 = n8;
                            } else if (n7 > n3) {
                                n9 = n;
                                n10 = n6;
                                n = n7 &= 65528;
                                n11 = n8;
                            } else {
                                l = l2 = CollationFastLatin.nextPair(arrc, n9, n7, charSequence, n);
                                n7 = n;
                                if (l2 < 0L) {
                                    n7 = n + 1;
                                    l = l2;
                                }
                                if ((n = (int)l) == 1) {
                                    return -2;
                                }
                                n = CollationFastLatin.getPrimaries(n3, n);
                                n5 = n7;
                                n7 = n;
                                continue;
                            }
                        }
                        do {
                            n7 = n10;
                            n8 = n11;
                            if (n11 != 0) break;
                            if (n10 == charSequence2.length()) {
                                n8 = 2;
                                n7 = n10;
                                break;
                            }
                            n7 = n10 + 1;
                            n6 = charSequence2.charAt(n10);
                            if (n6 <= 383) {
                                n8 = arrc2[n6];
                                if (n8 != 0) break;
                                if (n6 <= 57 && n6 >= 48 && (n4 & 2) != 0) {
                                    return -2;
                                }
                                n8 = arrc[n6];
                            } else {
                                n8 = 8192 <= n6 && n6 < 8256 ? arrc[n6 - 8192 + 384] : CollationFastLatin.lookup(arrc, n6);
                            }
                            if (n8 >= 4096) {
                                n8 &= 64512;
                                break;
                            }
                            if (n8 > n3) {
                                n8 &= 65528;
                                break;
                            }
                            l2 = CollationFastLatin.nextPair(arrc, n6, n8, charSequence2, n7);
                            n8 = n7;
                            l = l2;
                            if (l2 < 0L) {
                                n8 = n7 + 1;
                                l = l2;
                            }
                            if ((n7 = (int)l) == 1) {
                                return -2;
                            }
                            n11 = CollationFastLatin.getPrimaries(n3, n7);
                            n10 = n8;
                        } while (true);
                        if (n != n8) break block90;
                        if (n != 2) {
                            n8 = 0;
                            n = 0;
                            n5 = n9;
                            n6 = n7;
                            n7 = n;
                            continue;
                        }
                        break block91;
                    }
                    n6 = n & 65535;
                    n10 = n8 & 65535;
                    if (n6 != n10) {
                        n = n6 < n10 ? -1 : 1;
                        return n;
                    }
                    if (n != 2) break block92;
                }
                if (CollationSettings.getStrength(n4) >= 1) {
                    n6 = n2;
                    n10 = n2;
                    n8 = 0;
                    n = 0;
                    do {
                        n9 = n10;
                        n5 = n6;
                        n7 = n;
                        n11 = n8;
                        if (n == 0) {
                            if (n10 == charSequence.length()) {
                                n7 = 2;
                                n9 = n10;
                                n5 = n6;
                                n11 = n8;
                            } else {
                                n = n10 + 1;
                                n9 = charSequence.charAt(n10);
                                n7 = n9 <= 383 ? arrc[n9] : (8192 <= n9 && n9 < 8256 ? arrc[n9 - 8192 + 384] : CollationFastLatin.lookup(arrc, n9));
                                if (n7 >= 4096) {
                                    n7 = CollationFastLatin.getSecondariesFromOneShortCE(n7);
                                    n9 = n;
                                    n5 = n6;
                                    n11 = n8;
                                } else if (n7 > n3) {
                                    n7 = 192;
                                    n9 = n;
                                    n5 = n6;
                                    n11 = n8;
                                } else {
                                    l2 = CollationFastLatin.nextPair(arrc, n9, n7, charSequence, n);
                                    n7 = n;
                                    l = l2;
                                    if (l2 < 0L) {
                                        n7 = n + 1;
                                        l = l2;
                                    }
                                    n = CollationFastLatin.getSecondaries(n3, (int)l);
                                    n10 = n7;
                                    continue;
                                }
                            }
                        }
                        do {
                            n = n5;
                            n8 = n11;
                            if (n11 != 0) break;
                            if (n5 == charSequence2.length()) {
                                n8 = 2;
                                n = n5;
                                break;
                            }
                            n = n5 + 1;
                            n6 = charSequence2.charAt(n5);
                            n8 = n6 <= 383 ? arrc[n6] : (8192 <= n6 && n6 < 8256 ? arrc[n6 - 8192 + 384] : CollationFastLatin.lookup(arrc, n6));
                            if (n8 >= 4096) {
                                n8 = CollationFastLatin.getSecondariesFromOneShortCE(n8);
                                break;
                            }
                            if (n8 > n3) {
                                n8 = 192;
                                break;
                            }
                            l2 = CollationFastLatin.nextPair(arrc, n6, n8, charSequence2, n);
                            n8 = n;
                            l = l2;
                            if (l2 < 0L) {
                                n8 = n + 1;
                                l = l2;
                            }
                            n11 = CollationFastLatin.getSecondaries(n3, (int)l);
                            n5 = n8;
                        } while (true);
                        if (n7 == n8) {
                            if (n7 == 2) break;
                            n8 = 0;
                            n7 = 0;
                            n10 = n9;
                            n6 = n;
                            n = n7;
                            continue;
                        }
                        n10 = n7 & 65535;
                        n6 = n8 & 65535;
                        if (n10 != n6) {
                            if ((n4 & 2048) != 0) {
                                return -2;
                            }
                            n = n10 < n6 ? -1 : 1;
                            return n;
                        }
                        if (n7 == 2) break;
                        n8 >>>= 16;
                        n10 = n9;
                        n6 = n;
                        n = n7 >>>= 16;
                    } while (true);
                }
                if ((n4 & 1024) != 0) {
                    bl = CollationSettings.getStrength(n4) == 0;
                    n6 = n2;
                    n9 = n2;
                    n7 = 0;
                    n = 0;
                    do {
                        n5 = n6;
                        n10 = n;
                        n8 = n7;
                        if (n == 0) {
                            if (n9 == charSequence.length()) {
                                n10 = 2;
                                n5 = n6;
                                n8 = n7;
                            } else {
                                n8 = n9 + 1;
                                n5 = charSequence.charAt(n9);
                                n = n5 <= 383 ? arrc[n5] : CollationFastLatin.lookup(arrc, n5);
                                n9 = n10 = n;
                                n = n8;
                                if (n10 < 3072) {
                                    l2 = CollationFastLatin.nextPair(arrc, n5, n10, charSequence, n8);
                                    n = n8;
                                    l = l2;
                                    if (l2 < 0L) {
                                        n = n8 + 1;
                                        l = l2;
                                    }
                                    n9 = (int)l;
                                }
                                n8 = CollationFastLatin.getCases(n3, bl, n9);
                                n9 = n;
                                n = n8;
                                continue;
                            }
                        }
                        do {
                            n = n8;
                            if (n8 != 0) break;
                            if (n5 == charSequence2.length()) {
                                n = 2;
                                break;
                            }
                            n7 = n5 + 1;
                            n = (n5 = (int)charSequence2.charAt(n5)) <= 383 ? arrc[n5] : CollationFastLatin.lookup(arrc, n5);
                            n8 = n6 = n;
                            n = n7;
                            if (n6 < 3072) {
                                l2 = CollationFastLatin.nextPair(arrc, n5, n6, charSequence2, n7);
                                n = n7;
                                l = l2;
                                if (l2 < 0L) {
                                    n = n7 + 1;
                                    l = l2;
                                }
                                n8 = (int)l;
                            }
                            n8 = CollationFastLatin.getCases(n3, bl, n8);
                            n5 = n;
                        } while (true);
                        if (n10 == n) {
                            if (n10 == 2) break;
                            n7 = 0;
                            n = 0;
                            n6 = n5;
                            continue;
                        }
                        n7 = n10 & 65535;
                        n8 = n & 65535;
                        if (n7 != n8) {
                            if ((n4 & 256) == 0) {
                                n = n7 < n8 ? -1 : 1;
                                return n;
                            }
                            n = n7 < n8 ? 1 : -1;
                            return n;
                        }
                        if (n10 == 2) break;
                        n7 = n10 >>> 16;
                        n8 = n >>> 16;
                        n6 = n5;
                        n = n7;
                        n7 = n8;
                    } while (true);
                }
                if (CollationSettings.getStrength(n4) <= 1) {
                    return 0;
                }
                bl = CollationSettings.isTertiaryWithCaseBits(n4);
                n6 = n2;
                n9 = n2;
                n7 = 0;
                n = 0;
                do {
                    block95 : {
                        block94 : {
                            block93 : {
                                n5 = n6;
                                n10 = n;
                                n8 = n7;
                                if (n == 0) {
                                    if (n9 == charSequence.length()) {
                                        n10 = 2;
                                        n5 = n6;
                                        n8 = n7;
                                    } else {
                                        n8 = n9 + 1;
                                        n5 = charSequence.charAt(n9);
                                        n = n5 <= 383 ? arrc[n5] : CollationFastLatin.lookup(arrc, n5);
                                        n9 = n10 = n;
                                        n = n8;
                                        if (n10 < 3072) {
                                            l2 = CollationFastLatin.nextPair(arrc, n5, n10, charSequence, n8);
                                            n = n8;
                                            l = l2;
                                            if (l2 < 0L) {
                                                n = n8 + 1;
                                                l = l2;
                                            }
                                            n9 = (int)l;
                                        }
                                        n8 = CollationFastLatin.getTertiaries(n3, bl, n9);
                                        n9 = n;
                                        n = n8;
                                        continue;
                                    }
                                }
                                do {
                                    n = n8;
                                    if (n8 != 0) break;
                                    if (n5 == charSequence2.length()) {
                                        n = 2;
                                        break;
                                    }
                                    n7 = n5 + 1;
                                    n = (n5 = (int)charSequence2.charAt(n5)) <= 383 ? arrc[n5] : CollationFastLatin.lookup(arrc, n5);
                                    n6 = n8 = n;
                                    n = n7;
                                    if (n8 < 3072) {
                                        l2 = CollationFastLatin.nextPair(arrc, n5, n8, charSequence2, n7);
                                        n = n7;
                                        l = l2;
                                        if (l2 < 0L) {
                                            n = n7 + 1;
                                            l = l2;
                                        }
                                        n6 = (int)l;
                                    }
                                    n8 = CollationFastLatin.getTertiaries(n3, bl, n6);
                                    n5 = n;
                                } while (true);
                                if (n10 != n) break block93;
                                if (n10 != 2) {
                                    n7 = 0;
                                    n = 0;
                                    n6 = n5;
                                    continue;
                                }
                                break block94;
                            }
                            n7 = n10 & 65535;
                            n6 = n & 65535;
                            if (n7 != n6) {
                                n8 = n7;
                                n2 = n6;
                                if (CollationSettings.sortsTertiaryUpperCaseFirst(n4)) {
                                    n = n7;
                                    if (n7 > 3) {
                                        n = n7 ^ 24;
                                    }
                                    n8 = n;
                                    n2 = n6;
                                    if (n6 > 3) {
                                        n2 = n6 ^ 24;
                                        n8 = n;
                                    }
                                }
                                n = n8 < n2 ? -1 : 1;
                                return n;
                            }
                            if (n10 != 2) break block95;
                        }
                        if (CollationSettings.getStrength(n4) <= 2) {
                            return 0;
                        }
                        n9 = n2;
                        n8 = n2;
                        n2 = 0;
                        n = 0;
                        do {
                            block98 : {
                                block97 : {
                                    block96 : {
                                        n10 = n9;
                                        n6 = n;
                                        n7 = n2;
                                        if (n == 0) {
                                            if (n8 == charSequence.length()) {
                                                n6 = 2;
                                                n10 = n9;
                                                n7 = n2;
                                            } else {
                                                n7 = n8 + 1;
                                                n10 = charSequence.charAt(n8);
                                                n = n10 <= 383 ? arrc[n10] : CollationFastLatin.lookup(arrc, n10);
                                                n8 = n6 = n;
                                                n = n7;
                                                if (n6 < 3072) {
                                                    l2 = CollationFastLatin.nextPair(arrc, n10, n6, charSequence, n7);
                                                    n = n7;
                                                    l = l2;
                                                    if (l2 < 0L) {
                                                        n = n7 + 1;
                                                        l = l2;
                                                    }
                                                    n8 = (int)l;
                                                }
                                                n7 = CollationFastLatin.getQuaternaries(n3, n8);
                                                n8 = n;
                                                n = n7;
                                                continue;
                                            }
                                        }
                                        while (n7 == 0) {
                                            if (n10 == charSequence2.length()) {
                                                n7 = 2;
                                                break;
                                            }
                                            n2 = n10 + 1;
                                            n9 = charSequence2.charAt(n10);
                                            n = n9 <= 383 ? arrc[n9] : CollationFastLatin.lookup(arrc, n9);
                                            n7 = n;
                                            if (n7 < 3072) {
                                                l2 = CollationFastLatin.nextPair(arrc, n9, n7, charSequence2, n2);
                                                n = n2;
                                                l = l2;
                                                if (l2 < 0L) {
                                                    n = n2 + 1;
                                                    l = l2;
                                                }
                                                n7 = (int)l;
                                            } else {
                                                n = n2;
                                            }
                                            n7 = CollationFastLatin.getQuaternaries(n3, n7);
                                            n10 = n;
                                        }
                                        if (n6 != n7) break block96;
                                        if (n6 != 2) {
                                            n2 = 0;
                                            n = 0;
                                            n9 = n10;
                                            continue;
                                        }
                                        break block97;
                                    }
                                    n = n6 & 65535;
                                    n2 = n7 & 65535;
                                    if (n != n2) {
                                        n = n < n2 ? -1 : 1;
                                        return n;
                                    }
                                    if (n6 != 2) break block98;
                                }
                                return 0;
                            }
                            n = n6 >>> 16;
                            n2 = n7 >>> 16;
                            n9 = n10;
                        } while (true);
                    }
                    n8 = n10 >>> 16;
                    n7 = n >>> 16;
                    n6 = n5;
                    n = n8;
                } while (true);
            }
            n8 >>>= 16;
            n5 = n9;
            n6 = n7;
            n7 = n >>>= 16;
        } while (true);
    }

    private static int getCases(int n, boolean bl, int n2) {
        if (n2 <= 65535) {
            if (n2 >= 4096) {
                int n3;
                n = n3 = n2 & 24;
                if (!bl) {
                    n = n3;
                    if ((n2 & 992) >= 384) {
                        n = n3 | 524288;
                    }
                }
            } else if (n2 > n) {
                n = 8;
            } else {
                n = n2;
                if (n2 >= 3072) {
                    n = 0;
                }
            }
        } else {
            int n4 = 65535 & n2;
            n = n4 >= 4096 ? (bl && (-67108864 & n2) == 0 ? n2 & 24 : n2 & 1572888) : (n4 > n ? 524296 : 0);
        }
        return n;
    }

    static int getCharIndex(char c) {
        if (c <= '\u017f') {
            return c;
        }
        if ('\u2000' <= c && c < '\u2040') {
            return c - 7808;
        }
        return -1;
    }

    public static int getOptions(CollationData arrc, CollationSettings collationSettings, char[] arrc2) {
        int n;
        int n2;
        int n3;
        block20 : {
            block19 : {
                block18 : {
                    long l;
                    char[] arrc3 = arrc.fastLatinTableHeader;
                    if (arrc3 == null) {
                        return -1;
                    }
                    if (arrc2.length != 384) {
                        return -1;
                    }
                    if ((collationSettings.options & 12) == 0) {
                        n3 = 3071;
                    } else {
                        n = arrc3[0];
                        n3 = collationSettings.getMaxVariable() + 1;
                        if (n3 >= (n & 255)) {
                            return -1;
                        }
                        n3 = arrc3[n3];
                    }
                    n = 0;
                    if (!collationSettings.hasReordering()) break block18;
                    long l2 = 0L;
                    long l3 = 0L;
                    long l4 = 0L;
                    long l5 = 0L;
                    for (n2 = 4096; n2 < 4104; ++n2) {
                        long l6;
                        long l7;
                        long l8;
                        long l9;
                        l = collationSettings.reorder(arrc.getFirstPrimaryForGroup(n2));
                        if (n2 == 4100) {
                            l9 = l5;
                            l6 = l5;
                            l8 = l2;
                            l7 = l;
                        } else {
                            l6 = l5;
                            l9 = l4;
                            l8 = l2;
                            l7 = l3;
                            if (l != 0L) {
                                if (l < l5) {
                                    return -1;
                                }
                                l8 = l2;
                                if (l3 != 0L) {
                                    l8 = l2;
                                    if (l2 == 0L) {
                                        l8 = l2;
                                        if (l5 == l4) {
                                            l8 = l;
                                        }
                                    }
                                }
                                l7 = l3;
                                l9 = l4;
                                l6 = l;
                            }
                        }
                        l5 = l6;
                        l4 = l9;
                        l2 = l8;
                        l3 = l7;
                    }
                    l = collationSettings.reorder(arrc.getFirstPrimaryForGroup(25));
                    if (l < l5) {
                        return -1;
                    }
                    l5 = l2;
                    if (l2 == 0L) {
                        l5 = l;
                    }
                    if (l4 < l3 && l3 < l5) break block19;
                    n2 = 1;
                    break block20;
                }
                n = 0;
            }
            n2 = n;
        }
        arrc = arrc.fastLatinTable;
        for (int i = 0; i < 384; ++i) {
            n = arrc[i];
            n = n >= 4096 ? (n &= 64512) : (n > n3 ? (n &= 65528) : 0);
            arrc2[i] = (char)n;
        }
        if (n2 != 0 || (collationSettings.options & 2) != 0) {
            for (n = 48; n <= 57; ++n) {
                arrc2[n] = (char)(false ? 1 : 0);
            }
        }
        return n3 << 16 | collationSettings.options;
    }

    private static int getPrimaries(int n, int n2) {
        int n3 = 65535 & n2;
        if (n3 >= 4096) {
            return -67044352 & n2;
        }
        if (n3 > n) {
            return -458760 & n2;
        }
        if (n3 >= 3072) {
            return 0;
        }
        return n2;
    }

    private static int getQuaternaries(int n, int n2) {
        if (n2 <= 65535) {
            if (n2 >= 4096) {
                n = (n2 & 992) >= 384 ? -67044352 : 64512;
            } else if (n2 > n) {
                n = 64512;
            } else {
                n = n2;
                if (n2 >= 3072) {
                    n = n2 & 65528;
                }
            }
        } else {
            n = (65535 & n2) > n ? -67044352 : n2 & -458760;
        }
        return n;
    }

    private static int getSecondaries(int n, int n2) {
        if (n2 <= 65535) {
            if (n2 >= 4096) {
                n = CollationFastLatin.getSecondariesFromOneShortCE(n2);
            } else if (n2 > n) {
                n = 192;
            } else {
                n = n2;
                if (n2 >= 3072) {
                    n = 0;
                }
            }
        } else {
            int n3 = 65535 & n2;
            n = n3 >= 4096 ? (65012704 & n2) + 2097184 : (n3 > n ? 12583104 : 0);
        }
        return n;
    }

    private static int getSecondariesFromOneShortCE(int n) {
        if ((n &= 992) < 384) {
            return n + 32;
        }
        return n + 32 << 16 | 192;
    }

    private static int getTertiaries(int n, boolean bl, int n2) {
        if (n2 <= 65535) {
            if (n2 >= 4096) {
                if (bl) {
                    n = (n2 & 31) + 32;
                    if ((n2 & 992) >= 384) {
                        n = 2621440 | n;
                    }
                } else {
                    n = (n2 & 7) + 32;
                    if ((n2 & 992) >= 384) {
                        n = 2097152 | n;
                    }
                }
            } else if (n2 > n) {
                n = n2 = (n2 & 7) + 32;
                if (bl) {
                    n = n2 | 8;
                }
            } else {
                n = n2;
                if (n2 >= 3072) {
                    n = 0;
                }
            }
        } else {
            int n3 = 65535 & n2;
            if (n3 >= 4096) {
                n = bl ? n2 & 2031647 : n2 & 458759;
                n += 2097184;
            } else if (n3 > n) {
                n = n2 = (n2 & 458759) + 2097184;
                if (bl) {
                    n = n2 | 524296;
                }
            } else {
                n = 0;
            }
        }
        return n;
    }

    private static int lookup(char[] arrc, int n) {
        if (8192 <= n && n < 8256) {
            return arrc[n - 8192 + 384];
        }
        if (n == 65534) {
            return 3;
        }
        if (n == 65535) {
            return 64680;
        }
        return 1;
    }

    private static long nextPair(char[] arrc, int n, int n2, CharSequence charSequence, int n3) {
        if (n2 < 3072 && n2 >= 1024) {
            if (n2 >= 2048) {
                n = (n2 & 1023) + 448;
                return (long)arrc[n + 1] << 16 | (long)arrc[n];
            }
            n2 = (n2 & 1023) + 448;
            int n4 = 0;
            int n5 = n2;
            int n6 = n4;
            if (n3 != charSequence.length()) {
                int n7;
                n = n3 = (int)charSequence.charAt(n3);
                if (n3 > 383) {
                    if (8192 <= n3 && n3 < 8256) {
                        n = n3 - 8192 + 384;
                    } else {
                        if (n3 != 65534 && n3 != 65535) {
                            return 1L;
                        }
                        n = -1;
                    }
                }
                n6 = n2;
                n5 = arrc[n6];
                do {
                    n3 = n6 + (n5 >> 9);
                    n5 = arrc[n3];
                    n7 = n5 & 511;
                    n6 = n3;
                } while (n7 < n);
                n5 = n2;
                n6 = n4;
                if (n7 == n) {
                    n6 = 1;
                    n5 = n3;
                }
            }
            if ((n2 = arrc[n5] >> 9) == 1) {
                return 1L;
            }
            n = arrc[n5 + 1];
            long l = n2 == 2 ? (long)n : (long)arrc[n5 + 2] << 16 | (long)n;
            if (n6 != 0) {
                // empty if block
            }
            return l;
        }
        return n2;
    }
}

