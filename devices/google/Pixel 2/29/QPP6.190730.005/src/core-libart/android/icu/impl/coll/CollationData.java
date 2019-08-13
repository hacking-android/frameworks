/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.Normalizer2Impl;
import android.icu.impl.Trie2_32;
import android.icu.impl.coll.Collation;
import android.icu.impl.coll.UVector32;
import android.icu.text.UnicodeSet;
import android.icu.util.ICUException;

public final class CollationData {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    static final int JAMO_CE32S_LENGTH = 67;
    static final int MAX_NUM_SPECIAL_REORDER_CODES = 8;
    static final int REORDER_RESERVED_AFTER_LATIN = 4111;
    static final int REORDER_RESERVED_BEFORE_LATIN = 4110;
    public CollationData base;
    int[] ce32s;
    long[] ces;
    public boolean[] compressibleBytes;
    String contexts;
    public char[] fastLatinTable;
    char[] fastLatinTableHeader;
    int[] jamoCE32s = new int[67];
    public Normalizer2Impl nfcImpl;
    int numScripts;
    long numericPrimary = 301989888L;
    public long[] rootElements;
    char[] scriptStarts;
    char[] scriptsIndex;
    Trie2_32 trie;
    UnicodeSet unsafeBackwardSet;

    CollationData(Normalizer2Impl normalizer2Impl) {
        this.nfcImpl = normalizer2Impl;
    }

    private int addHighScriptRange(short[] arrs, int n, int n2) {
        char c = this.scriptStarts[n + 1];
        int n3 = n2;
        if ((c & 255) > (n2 & 255)) {
            n3 = n2 - 256;
        }
        n2 = this.scriptStarts[n];
        n2 = (n3 & 65280) - ((c & 65280) - (65280 & n2)) | n2 & 255;
        arrs[n] = (short)(n2 >> 8);
        return n2;
    }

    private int addLowScriptRange(short[] arrs, int n, int n2) {
        char c = this.scriptStarts[n];
        int n3 = n2;
        if ((c & 255) < (n2 & 255)) {
            n3 = n2 + 256;
        }
        arrs[n] = (short)(n3 >> 8);
        n = this.scriptStarts[n + 1];
        return (n3 & 65280) + ((n & 65280) - (65280 & c)) | n & 255;
    }

    private int getScriptIndex(int n) {
        if (n < 0) {
            return 0;
        }
        int n2 = this.numScripts;
        if (n < n2) {
            return this.scriptsIndex[n];
        }
        if (n < 4096) {
            return 0;
        }
        if ((n -= 4096) < 8) {
            return this.scriptsIndex[n2 + n];
        }
        return 0;
    }

    private void makeReorderRanges(int[] object, boolean bl, UVector32 uVector32) {
        uVector32.removeAllElements();
        int n = ((int[])object).length;
        if (n != 0 && (n != 1 || object[0] != 103)) {
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            char[] arrc;
            short[] arrs;
            block32 : {
                arrs = new short[this.scriptStarts.length - 1];
                n6 = this.scriptsIndex[this.numScripts + 4110 - 4096];
                if (n6 != 0) {
                    arrs[n6] = (short)255;
                }
                if ((n6 = this.scriptsIndex[this.numScripts + 4111 - 4096]) != 0) {
                    arrs[n6] = (short)255;
                }
                arrc = this.scriptStarts;
                n2 = arrc[1];
                n5 = arrc[arrc.length - 1];
                n4 = 0;
                for (n6 = 0; n6 < n; ++n6) {
                    n7 = object[n6] - 4096;
                    n3 = n4;
                    if (n7 >= 0) {
                        n3 = n4;
                        if (n7 < 8) {
                            n3 = n4 | 1 << n7;
                        }
                    }
                    n4 = n3;
                }
                n6 = n2;
                for (n3 = 0; n3 < 8; ++n3) {
                    n7 = this.scriptsIndex[this.numScripts + n3];
                    n2 = n6;
                    if (n7 != 0) {
                        n2 = n6;
                        if ((1 << n3 & n4) == 0) {
                            n2 = this.addLowScriptRange(arrs, n7, n6);
                        }
                    }
                    n6 = n2;
                }
                n7 = 0;
                n3 = n6;
                n2 = n7;
                if (n4 == 0) {
                    n3 = n6;
                    n2 = n7;
                    if (object[0] == 25) {
                        n3 = n6;
                        n2 = n7;
                        if (!bl) {
                            n3 = this.scriptsIndex[25];
                            n3 = this.scriptStarts[n3];
                            n2 = n3 - n6;
                        }
                    }
                }
                n4 = 0;
                n6 = n3;
                while (n4 < n) {
                    n7 = n4 + 1;
                    n3 = object[n4];
                    if (n3 == 103) {
                        n3 = n5;
                        n4 = n;
                        while (n7 < n4) {
                            if ((n5 = object[--n4]) != 103) {
                                if (n5 != -1) {
                                    n = this.getScriptIndex(n5);
                                    if (n == 0) continue;
                                    if (arrs[n] == 0) {
                                        n3 = this.addHighScriptRange(arrs, n, n3);
                                        continue;
                                    }
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("setReorderCodes(): duplicate or equivalent script ");
                                    ((StringBuilder)object).append(CollationData.scriptCodeString(n5));
                                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                                }
                                throw new IllegalArgumentException("setReorderCodes(): UScript.DEFAULT together with other scripts");
                            }
                            throw new IllegalArgumentException("setReorderCodes(): duplicate UScript.UNKNOWN");
                        }
                        n4 = 1;
                        break block32;
                    }
                    if (n3 != -1) {
                        n4 = this.getScriptIndex(n3);
                        if (n4 == 0) {
                            n4 = n7;
                            continue;
                        }
                        if (arrs[n4] == 0) {
                            n6 = this.addLowScriptRange(arrs, n4, n6);
                            n4 = n7;
                            continue;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("setReorderCodes(): duplicate or equivalent script ");
                        ((StringBuilder)object).append(CollationData.scriptCodeString(n3));
                        throw new IllegalArgumentException(((StringBuilder)object).toString());
                    }
                    throw new IllegalArgumentException("setReorderCodes(): UScript.DEFAULT together with other scripts");
                }
                n4 = 0;
                n3 = n5;
            }
            for (n5 = 1; n5 < (arrc = this.scriptStarts).length - 1; ++n5) {
                if (arrs[n5] != 0) continue;
                n = arrc[n5];
                n7 = n6;
                if (n4 == 0) {
                    n7 = n6;
                    if (n > n6) {
                        n7 = n;
                    }
                }
                n6 = this.addLowScriptRange(arrs, n5, n7);
            }
            if (n6 > n3) {
                if (n6 - (65280 & n2) <= n3) {
                    this.makeReorderRanges((int[])object, true, uVector32);
                    return;
                }
                throw new ICUException("setReorderCodes(): reordering too many partial-primary-lead-byte scripts");
            }
            n6 = 0;
            n4 = 1;
            do {
                n3 = n6;
                while (n4 < ((int[])(object = this.scriptStarts)).length - 1) {
                    n2 = arrs[n4];
                    if (n2 != 255) {
                        n3 = n2 -= object[n4] >> 8;
                        if (n2 != n6) {
                            n3 = n2;
                            break;
                        }
                    }
                    ++n4;
                }
                if (n6 != 0 || n4 < this.scriptStarts.length - 1) {
                    uVector32.addElement(this.scriptStarts[n4] << 16 | 65535 & n6);
                }
                if (n4 == this.scriptStarts.length - 1) {
                    return;
                }
                n6 = n3;
                ++n4;
            } while (true);
        }
    }

    private static String scriptCodeString(int n) {
        CharSequence charSequence;
        if (n < 4096) {
            charSequence = Integer.toString(n);
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("0x");
            ((StringBuilder)charSequence).append(Integer.toHexString(n));
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    public int getCE32(int n) {
        return this.trie.get(n);
    }

    int getCE32FromContexts(int n) {
        return this.contexts.charAt(n) << 16 | this.contexts.charAt(n + 1);
    }

    int getCE32FromSupplementary(int n) {
        return this.trie.get(n);
    }

    long getCEFromOffsetCE32(int n, int n2) {
        return Collation.makeCE(Collation.getThreeBytePrimaryForOffsetData(n, this.ces[Collation.indexFromCE32(n2)]));
    }

    public int[] getEquivalentScripts(int n) {
        int n2;
        int n3 = this.getScriptIndex(n);
        if (n3 == 0) {
            return EMPTY_INT_ARRAY;
        }
        if (n >= 4096) {
            return new int[]{n};
        }
        int n4 = 0;
        for (n2 = 0; n2 < this.numScripts; ++n2) {
            int n5 = n4;
            if (this.scriptsIndex[n2] == n3) {
                n5 = n4 + 1;
            }
            n4 = n5;
        }
        int[] arrn = new int[n4];
        if (n4 == 1) {
            arrn[0] = n;
            return arrn;
        }
        n4 = 0;
        for (n = 0; n < this.numScripts; ++n) {
            n2 = n4;
            if (this.scriptsIndex[n] == n3) {
                arrn[n4] = n;
                n2 = n4 + 1;
            }
            n4 = n2;
        }
        return arrn;
    }

    int getFCD16(int n) {
        return this.nfcImpl.getFCD16(n);
    }

    int getFinalCE32(int n) {
        int n2 = n;
        if (Collation.isSpecialCE32(n)) {
            n2 = this.getIndirectCE32(n);
        }
        return n2;
    }

    long getFirstPrimaryForGroup(int n) {
        long l = (n = this.getScriptIndex(n)) == 0 ? 0L : (long)this.scriptStarts[n] << 16;
        return l;
    }

    public int getGroupForPrimary(long l) {
        char[] arrc = this.scriptStarts;
        if ((l >>= 16) >= (long)arrc[1] && (long)arrc[arrc.length - 1] > l) {
            int n;
            char c = '\u0001';
            while (l >= (long)this.scriptStarts[c + 1]) {
                ++c;
            }
            for (n = 0; n < this.numScripts; ++n) {
                if (this.scriptsIndex[n] != c) continue;
                return n;
            }
            for (n = 0; n < 8; ++n) {
                if (this.scriptsIndex[this.numScripts + n] != c) continue;
                return n + 4096;
            }
            return -1;
        }
        return -1;
    }

    int getIndirectCE32(int n) {
        int n2 = Collation.tagFromCE32(n);
        if (n2 == 10) {
            n = this.ce32s[Collation.indexFromCE32(n)];
        } else if (n2 == 13) {
            n = -1;
        } else if (n2 == 11) {
            n = this.ce32s[0];
        }
        return n;
    }

    public long getLastPrimaryForGroup(int n) {
        if ((n = this.getScriptIndex(n)) == 0) {
            return 0L;
        }
        return ((long)this.scriptStarts[n + 1] << 16) - 1L;
    }

    long getSingleCE(int n) {
        CollationData collationData;
        int n2 = this.getCE32(n);
        if (n2 == 192) {
            collationData = this.base;
            n2 = this.base.getCE32(n);
        } else {
            collationData = this;
        }
        block12 : while (Collation.isSpecialCE32(n2)) {
            switch (Collation.tagFromCE32(n2)) {
                default: {
                    continue block12;
                }
                case 15: {
                    return Collation.unassignedCEFromCodePoint(n);
                }
                case 14: {
                    return collationData.getCEFromOffsetCE32(n, n2);
                }
                case 11: {
                    n2 = collationData.ce32s[0];
                    continue block12;
                }
                case 10: {
                    n2 = collationData.ce32s[Collation.indexFromCE32(n2)];
                    continue block12;
                }
                case 6: {
                    if (Collation.lengthFromCE32(n2) == 1) {
                        return collationData.ces[Collation.indexFromCE32(n2)];
                    }
                    throw new UnsupportedOperationException(String.format("there is not exactly one collation element for U+%04X (CE32 0x%08x)", n, n2));
                }
                case 5: {
                    if (Collation.lengthFromCE32(n2) == 1) {
                        n2 = collationData.ce32s[Collation.indexFromCE32(n2)];
                        continue block12;
                    }
                    throw new UnsupportedOperationException(String.format("there is not exactly one collation element for U+%04X (CE32 0x%08x)", n, n2));
                }
                case 4: 
                case 7: 
                case 8: 
                case 9: 
                case 12: 
                case 13: {
                    throw new UnsupportedOperationException(String.format("there is not exactly one collation element for U+%04X (CE32 0x%08x)", n, n2));
                }
                case 2: {
                    return Collation.ceFromLongSecondaryCE32(n2);
                }
                case 1: {
                    return Collation.ceFromLongPrimaryCE32(n2);
                }
                case 0: 
                case 3: 
            }
            throw new AssertionError((Object)String.format("unexpected CE32 tag for U+%04X (CE32 0x%08x)", n, n2));
        }
        return Collation.ceFromSimpleCE32(n2);
    }

    public boolean isCompressibleLeadByte(int n) {
        return this.compressibleBytes[n];
    }

    public boolean isCompressiblePrimary(long l) {
        return this.isCompressibleLeadByte((int)l >>> 24);
    }

    boolean isDigit(int n) {
        boolean bl = n < 1632 ? n <= 57 && 48 <= n : Collation.hasCE32Tag(this.getCE32(n), 10);
        return bl;
    }

    public boolean isUnsafeBackward(int n, boolean bl) {
        bl = this.unsafeBackwardSet.contains(n) || bl && this.isDigit(n);
        return bl;
    }

    void makeReorderRanges(int[] arrn, UVector32 uVector32) {
        this.makeReorderRanges(arrn, false, uVector32);
    }
}

