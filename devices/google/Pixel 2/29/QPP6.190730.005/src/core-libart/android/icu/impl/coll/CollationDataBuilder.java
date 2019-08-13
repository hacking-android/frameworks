/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.Norm2AllModes;
import android.icu.impl.Normalizer2Impl;
import android.icu.impl.Trie2;
import android.icu.impl.Trie2Writable;
import android.icu.impl.Trie2_32;
import android.icu.impl.coll.Collation;
import android.icu.impl.coll.CollationData;
import android.icu.impl.coll.CollationFastLatinBuilder;
import android.icu.impl.coll.CollationIterator;
import android.icu.impl.coll.CollationSettings;
import android.icu.impl.coll.UVector32;
import android.icu.impl.coll.UVector64;
import android.icu.lang.UCharacter;
import android.icu.text.UnicodeSet;
import android.icu.text.UnicodeSetIterator;
import android.icu.util.CharsTrie;
import android.icu.util.CharsTrieBuilder;
import android.icu.util.StringTrieBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

final class CollationDataBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int IS_BUILDER_JAMO_CE32 = 256;
    protected CollationData base;
    protected CollationSettings baseSettings;
    protected UVector32 ce32s;
    protected UVector64 ce64s;
    protected DataBuilderCollationIterator collIter;
    protected ArrayList<ConditionalCE32> conditionalCE32s;
    protected UnicodeSet contextChars = new UnicodeSet();
    protected StringBuilder contexts = new StringBuilder();
    protected CollationFastLatinBuilder fastLatinBuilder;
    protected boolean fastLatinEnabled;
    protected boolean modified;
    protected Normalizer2Impl nfcImpl;
    protected Trie2Writable trie;
    protected UnicodeSet unsafeBackwardSet = new UnicodeSet();

    CollationDataBuilder() {
        this.nfcImpl = Norm2AllModes.getNFCInstance().impl;
        this.base = null;
        this.baseSettings = null;
        this.trie = null;
        this.ce32s = new UVector32();
        this.ce64s = new UVector64();
        this.conditionalCE32s = new ArrayList();
        this.modified = false;
        this.fastLatinEnabled = false;
        this.fastLatinBuilder = null;
        this.collIter = null;
        this.ce32s.addElement(0);
    }

    protected static int encodeOneCEAsCE32(long l) {
        long l2 = l >>> 32;
        int n = (int)l;
        int n2 = 65535 & n;
        if ((0xFFFF00FF00FFL & l) == 0L) {
            return (int)l2 | n >>> 16 | n2 >> 8;
        }
        if ((0xFFFFFFFFFFL & l) == 0x5000500L) {
            return Collation.makeLongPrimaryCE32(l2);
        }
        if (l2 == 0L && (n2 & 255) == 0) {
            return Collation.makeLongSecondaryCE32(n);
        }
        return 1;
    }

    private static void enumRangeForCopy(int n, int n2, int n3, CopyHelper copyHelper) {
        if (n3 != -1 && n3 != 192) {
            copyHelper.copyRangeCE32(n, n2, n3);
        }
    }

    protected static boolean isBuilderContextCE32(int n) {
        return Collation.hasCE32Tag(n, 7);
    }

    protected static int jamoCpFromIndex(int n) {
        if (n < 19) {
            return n + 4352;
        }
        if ((n -= 19) < 21) {
            return n + 4449;
        }
        return n - 21 + 4520;
    }

    protected static int makeBuilderContextCE32(int n) {
        return Collation.makeCE32FromTagAndIndex(7, n);
    }

    void add(CharSequence charSequence, CharSequence charSequence2, long[] arrl, int n) {
        this.addCE32(charSequence, charSequence2, this.encodeCEs(arrl, n));
    }

    protected int addCE(long l) {
        int n = this.ce64s.size();
        for (int i = 0; i < n; ++i) {
            if (l != this.ce64s.elementAti(i)) continue;
            return i;
        }
        this.ce64s.addElement(l);
        return n;
    }

    protected int addCE32(int n) {
        int n2 = this.ce32s.size();
        for (int i = 0; i < n2; ++i) {
            if (n != this.ce32s.elementAti(i)) continue;
            return i;
        }
        this.ce32s.addElement(n);
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    void addCE32(CharSequence object, CharSequence charSequence, int n) {
        int n2;
        Object object2;
        int n3;
        int n4;
        int n5;
        block16 : {
            int n6;
            block17 : {
                if (charSequence.length() == 0) {
                    throw new IllegalArgumentException("mapping from empty string");
                }
                if (!this.isMutable()) {
                    throw new IllegalStateException("attempt to add mappings after build()");
                }
                n3 = 0;
                n5 = Character.codePointAt(charSequence, 0);
                n4 = Character.charCount(n5);
                int n7 = this.trie.get(n5);
                if (object.length() != 0 || charSequence.length() > n4) {
                    n3 = 1;
                }
                n2 = n7;
                if (n7 != 192) break block16;
                object2 = this.base;
                n6 = ((CollationData)object2).getFinalCE32(((CollationData)object2).getCE32(n5));
                if (n3 != 0) break block17;
                n2 = n7;
                if (!Collation.ce32HasContext(n6)) break block16;
            }
            n2 = this.copyFromBaseCE32(n5, n6, true);
            this.trie.set(n5, n2);
        }
        if (n3 == 0) {
            if (!CollationDataBuilder.isBuilderContextCE32(n2)) {
                this.trie.set(n5, n);
            } else {
                object = this.getConditionalCE32ForCE32(n2);
                ((ConditionalCE32)object).builtCE32 = 1;
                ((ConditionalCE32)object).ce32 = n;
            }
        } else {
            if (!CollationDataBuilder.isBuilderContextCE32(n2)) {
                n2 = this.addConditionalCE32("\u0000", n2);
                n3 = CollationDataBuilder.makeBuilderContextCE32(n2);
                this.trie.set(n5, n3);
                this.contextChars.add(n5);
                object2 = this.getConditionalCE32(n2);
            } else {
                object2 = this.getConditionalCE32ForCE32(n2);
                ((ConditionalCE32)object2).builtCE32 = 1;
            }
            CharSequence charSequence2 = charSequence.subSequence(n4, charSequence.length());
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((char)object.length());
            ((StringBuilder)charSequence).append((CharSequence)object);
            ((StringBuilder)charSequence).append(charSequence2);
            charSequence = ((StringBuilder)charSequence).toString();
            this.unsafeBackwardSet.addAll(charSequence2);
            do {
                if ((n3 = ((ConditionalCE32)object2).next) < 0) {
                    ((ConditionalCE32)object2).next = this.addConditionalCE32((String)charSequence, n);
                    break;
                }
                object = this.getConditionalCE32(n3);
                n2 = ((String)charSequence).compareTo(((ConditionalCE32)object).context);
                if (n2 < 0) {
                    ((ConditionalCE32)object2).next = n = this.addConditionalCE32((String)charSequence, n);
                    this.getConditionalCE32((int)n).next = n3;
                    break;
                }
                if (n2 == 0) {
                    ((ConditionalCE32)object).ce32 = n;
                    break;
                }
                object2 = object;
            } while (true);
        }
        this.modified = true;
    }

    protected int addConditionalCE32(String object, int n) {
        int n2 = this.conditionalCE32s.size();
        if (n2 <= 524287) {
            object = new ConditionalCE32((String)object, n);
            this.conditionalCE32s.add((ConditionalCE32)object);
            return n2;
        }
        throw new IndexOutOfBoundsException("too many context-sensitive mappings");
    }

    protected int addContextTrie(int n, CharsTrieBuilder charsTrieBuilder) {
        int n2;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((char)(n >> 16));
        stringBuilder.append((char)n);
        stringBuilder.append(charsTrieBuilder.buildCharSequence(StringTrieBuilder.Option.SMALL));
        n = n2 = this.contexts.indexOf(stringBuilder.toString());
        if (n2 < 0) {
            n = this.contexts.length();
            this.contexts.append(stringBuilder);
        }
        return n;
    }

    void build(CollationData collationData) {
        this.buildMappings(collationData);
        CollationData collationData2 = this.base;
        if (collationData2 != null) {
            collationData.numericPrimary = collationData2.numericPrimary;
            collationData.compressibleBytes = this.base.compressibleBytes;
            collationData.numScripts = this.base.numScripts;
            collationData.scriptsIndex = this.base.scriptsIndex;
            collationData.scriptStarts = this.base.scriptStarts;
        }
        this.buildFastLatinTable(collationData);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected int buildContext(ConditionalCE32 var1_1) {
        var2_2 = new CharsTrieBuilder();
        var3_3 = new CharsTrieBuilder();
        var4_4 = var1_1;
        block0 : do {
            block19 : {
                block18 : {
                    var5_5 = var4_4.prefixLength();
                    var6_6 = new StringBuilder().append(var4_4.context, 0, var5_5 + 1);
                    var7_7 = var6_6.toString();
                    var8_8 = var4_4;
                    var9_9 = var4_4;
                    while (var9_9.next >= 0) {
                        var10_10 = this.getConditionalCE32(var9_9.next);
                        var9_9 = var10_10;
                        if (!var10_10.context.startsWith(var7_7)) break;
                        var8_8 = var9_9;
                    }
                    var11_11 = var5_5 + 1;
                    if (var8_8.context.length() != var11_11) break block18;
                    var11_11 = var8_8.ce32;
                    ** GOTO lbl59
                }
                var3_3.clear();
                var12_12 = 1;
                var13_13 = 0;
                if (var4_4.context.length() != var11_11) break block19;
                var14_14 = var4_4.ce32;
                var9_9 = this.getConditionalCE32(var4_4.next);
                ** GOTO lbl36
            }
            var13_13 = 0 | 256;
            var9_9 = var1_1;
            do {
                block20 : {
                    if ((var14_14 = var9_9.prefixLength()) != var5_5) break block20;
                    var9_9 = var4_4;
                    var14_14 = var12_12;
lbl36: // 2 sources:
                    var13_13 |= 512;
                    break;
                }
                if (var9_9.defaultCE32 != 1 && (var14_14 == 0 || var7_7.regionMatches(var6_6.length() - var14_14, var9_9.context, 1, var14_14))) {
                    var12_12 = var9_9.defaultCE32;
                }
                var9_9 = this.getConditionalCE32(var9_9.next);
            } while (true);
            var15_15 = var11_11;
            do {
                block21 : {
                    var10_10 = var9_9.context.substring(var15_15);
                    var12_12 = var13_13;
                    if (this.nfcImpl.getFCD16(var10_10.codePointAt(0)) <= 255) {
                        var12_12 = var13_13 & -513;
                    }
                    var11_11 = var12_12;
                    if (this.nfcImpl.getFCD16(var10_10.codePointBefore(var10_10.length())) > 255) {
                        var11_11 = var12_12 | 1024;
                    }
                    var3_3.add((CharSequence)var10_10, var9_9.ce32);
                    if (var9_9 != var8_8) break block21;
                    var12_12 = this.addContextTrie(var14_14, var3_3);
                    if (var12_12 > 524287) throw new IndexOutOfBoundsException("too many context-sensitive mappings");
                    var11_11 = Collation.makeCE32FromTagAndIndex(9, var12_12) | var11_11;
                    var8_8 = var9_9;
lbl59: // 2 sources:
                    var4_4.defaultCE32 = var11_11;
                    if (var5_5 == 0) {
                        if (var8_8.next < 0) {
                            return var11_11;
                        }
                    } else {
                        var6_6.delete(0, 1);
                        var6_6.reverse();
                        var2_2.add(var6_6, var11_11);
                        if (var8_8.next < 0) {
                            var11_11 = this.addContextTrie(var1_1.defaultCE32, var2_2);
                            if (var11_11 > 524287) throw new IndexOutOfBoundsException("too many context-sensitive mappings");
                            return Collation.makeCE32FromTagAndIndex(8, var11_11);
                        }
                    }
                    var4_4 = this.getConditionalCE32(var8_8.next);
                    continue block0;
                }
                var9_9 = this.getConditionalCE32(var9_9.next);
                var13_13 = var11_11;
            } while (true);
            break;
        } while (true);
    }

    protected void buildContexts() {
        this.contexts.setLength(0);
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(this.contextChars);
        while (unicodeSetIterator.next()) {
            int n = unicodeSetIterator.codepoint;
            int n2 = this.trie.get(n);
            if (CollationDataBuilder.isBuilderContextCE32(n2)) {
                n2 = this.buildContext(this.getConditionalCE32ForCE32(n2));
                this.trie.set(n, n2);
                continue;
            }
            throw new AssertionError((Object)"Impossible: No context data for c in contextChars.");
        }
    }

    protected void buildFastLatinTable(CollationData collationData) {
        if (!this.fastLatinEnabled) {
            return;
        }
        this.fastLatinBuilder = new CollationFastLatinBuilder();
        if (this.fastLatinBuilder.forData(collationData)) {
            char[] arrc = this.fastLatinBuilder.getHeader();
            char[] arrc2 = this.fastLatinBuilder.getTable();
            CollationData collationData2 = this.base;
            char[] arrc3 = arrc;
            char[] arrc4 = arrc2;
            if (collationData2 != null) {
                arrc3 = arrc;
                arrc4 = arrc2;
                if (Arrays.equals(arrc, collationData2.fastLatinTableHeader)) {
                    arrc3 = arrc;
                    arrc4 = arrc2;
                    if (Arrays.equals(arrc2, this.base.fastLatinTable)) {
                        this.fastLatinBuilder = null;
                        arrc3 = this.base.fastLatinTableHeader;
                        arrc4 = this.base.fastLatinTable;
                    }
                }
            }
            collationData.fastLatinTableHeader = arrc3;
            collationData.fastLatinTable = arrc4;
        } else {
            this.fastLatinBuilder = null;
        }
    }

    protected void buildMappings(CollationData collationData) {
        if (this.isMutable()) {
            int n;
            CollationData collationData2;
            int n2;
            int n3;
            this.buildContexts();
            int[] arrn = new int[67];
            int n4 = -1;
            if (this.getJamoCE32s(arrn)) {
                int n5 = this.ce32s.size();
                for (n3 = 0; n3 < 67; ++n3) {
                    this.ce32s.addElement(arrn[n3]);
                }
                n4 = 0;
                n2 = 19;
                do {
                    n3 = n4;
                    if (n2 >= 67) break;
                    if (Collation.isSpecialCE32(arrn[n2])) {
                        n3 = 1;
                        break;
                    }
                    ++n2;
                } while (true);
                int n6 = Collation.makeCE32FromTagAndIndex(12, 0);
                n4 = 44032;
                for (n2 = 0; n2 < 19; ++n2) {
                    int n7;
                    n = n7 = n6;
                    if (n3 == 0) {
                        n = n7;
                        if (!Collation.isSpecialCE32(arrn[n2])) {
                            n = n7 | 256;
                        }
                    }
                    n7 = n4 + 588;
                    this.trie.setRange(n4, n7 - 1, n, true);
                    n4 = n7;
                }
                n3 = n5;
            } else {
                n2 = 44032;
                do {
                    n3 = n4;
                    if (n2 >= 55204) break;
                    n = this.base.getCE32(n2);
                    n3 = n2 + 588;
                    this.trie.setRange(n2, n3 - 1, n, true);
                    n2 = n3;
                } while (true);
            }
            this.setDigitTags();
            this.setLeadSurrogates();
            this.ce32s.setElementAt(this.trie.get(0), 0);
            this.trie.set(0, Collation.makeCE32FromTagAndIndex(11, 0));
            collationData.trie = this.trie.toTrie2_32();
            n4 = 65536;
            n2 = 55296;
            while (n2 < 56320) {
                if (this.unsafeBackwardSet.containsSome(n4, n4 + 1023)) {
                    this.unsafeBackwardSet.add(n2);
                }
                n2 = (char)(n2 + 1);
                n4 += 1024;
            }
            this.unsafeBackwardSet.freeze();
            collationData.ce32s = this.ce32s.getBuffer();
            collationData.ces = this.ce64s.getBuffer();
            collationData.contexts = this.contexts.toString();
            collationData.base = collationData2 = this.base;
            collationData.jamoCE32s = n3 >= 0 ? arrn : collationData2.jamoCE32s;
            collationData.unsafeBackwardSet = this.unsafeBackwardSet;
            return;
        }
        throw new IllegalStateException("attempt to build() after build()");
    }

    protected void clearContexts() {
        this.contexts.setLength(0);
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(this.contextChars);
        while (unicodeSetIterator.next()) {
            int n = this.trie.get(unicodeSetIterator.codepoint);
            this.getConditionalCE32ForCE32((int)n).builtCE32 = 1;
        }
    }

    protected int copyContractionsFromBaseCE32(StringBuilder stringBuilder, int n, int n2, ConditionalCE32 conditionalCE32) {
        int n3 = Collation.indexFromCE32(n2);
        if ((n2 & 256) != 0) {
            n2 = -1;
        } else {
            n2 = this.base.getCE32FromContexts(n3);
            n2 = this.copyFromBaseCE32(n, n2, true);
            conditionalCE32.next = n2 = this.addConditionalCE32(stringBuilder.toString(), n2);
            conditionalCE32 = this.getConditionalCE32(n2);
        }
        int n4 = stringBuilder.length();
        CharsTrie.Iterator iterator = CharsTrie.iterator(this.base.contexts, n3 + 2, 0);
        while (iterator.hasNext()) {
            CharsTrie.Entry entry = iterator.next();
            stringBuilder.append(entry.chars);
            n2 = this.copyFromBaseCE32(n, entry.value, true);
            n2 = n3 = this.addConditionalCE32(stringBuilder.toString(), n2);
            conditionalCE32.next = n3;
            conditionalCE32 = this.getConditionalCE32(n2);
            stringBuilder.setLength(n4);
        }
        return n2;
    }

    void copyFrom(CollationDataBuilder collationDataBuilder, CEModifier object) {
        if (this.isMutable()) {
            object = new CopyHelper(collationDataBuilder, this, (CEModifier)object);
            for (Trie2.Range range : collationDataBuilder.trie) {
                if (range.leadSurrogate) break;
                CollationDataBuilder.enumRangeForCopy(range.startCodePoint, range.endCodePoint, range.value, (CopyHelper)object);
            }
            this.modified |= collationDataBuilder.modified;
            return;
        }
        throw new IllegalStateException("attempt to copyFrom() after build()");
    }

    /*
     * Enabled aggressive block sorting
     */
    protected int copyFromBaseCE32(int n, int n2, boolean bl) {
        if (!Collation.isSpecialCE32(n2)) {
            return n2;
        }
        int n3 = Collation.tagFromCE32(n2);
        if (n3 == 1) return n2;
        if (n3 == 2) return n2;
        if (n3 == 4) return n2;
        if (n3 == 5) {
            n = Collation.indexFromCE32(n2);
            n2 = Collation.lengthFromCE32(n2);
            return this.encodeExpansion32(this.base.ce32s, n, n2);
        }
        if (n3 == 6) {
            n = Collation.indexFromCE32(n2);
            n2 = Collation.lengthFromCE32(n2);
            return this.encodeExpansion(this.base.ces, n, n2);
        }
        if (n3 != 8) {
            if (n3 != 9) {
                if (n3 == 12) throw new UnsupportedOperationException("We forbid tailoring of Hangul syllables.");
                if (n3 == 14) return this.getCE32FromOffsetCE32(true, n, n2);
                if (n3 != 15) throw new AssertionError((Object)"copyFromBaseCE32(c, ce32, withContext) requires ce32 == base.getFinalCE32(ce32)");
                return this.encodeOneCE(Collation.unassignedCEFromCodePoint(n));
            }
            if (!bl) {
                n2 = Collation.indexFromCE32(n2);
                return this.copyFromBaseCE32(n, this.base.getCE32FromContexts(n2), false);
            }
            ConditionalCE32 conditionalCE32 = new ConditionalCE32("", 0);
            this.copyContractionsFromBaseCE32(new StringBuilder("\u0000"), n, n2, conditionalCE32);
            n2 = CollationDataBuilder.makeBuilderContextCE32(conditionalCE32.next);
            this.contextChars.add(n);
            return n2;
        }
        n3 = Collation.indexFromCE32(n2);
        n2 = this.base.getCE32FromContexts(n3);
        if (!bl) {
            return this.copyFromBaseCE32(n, n2, false);
        }
        ConditionalCE32 conditionalCE32 = new ConditionalCE32("", 0);
        StringBuilder stringBuilder = new StringBuilder("\u0000");
        if (Collation.isContractionCE32(n2)) {
            n2 = this.copyContractionsFromBaseCE32(stringBuilder, n, n2, conditionalCE32);
        } else {
            n2 = this.copyFromBaseCE32(n, n2, true);
            conditionalCE32.next = n2 = this.addConditionalCE32(stringBuilder.toString(), n2);
        }
        ConditionalCE32 conditionalCE322 = this.getConditionalCE32(n2);
        CharsTrie.Iterator iterator = CharsTrie.iterator(this.base.contexts, n3 + 2, 0);
        do {
            if (!iterator.hasNext()) {
                n2 = CollationDataBuilder.makeBuilderContextCE32(conditionalCE32.next);
                this.contextChars.add(n);
                return n2;
            }
            CharsTrie.Entry entry = iterator.next();
            stringBuilder.setLength(0);
            stringBuilder.append(entry.chars);
            stringBuilder.reverse().insert(0, (char)entry.chars.length());
            n2 = entry.value;
            if (Collation.isContractionCE32(n2)) {
                n2 = this.copyContractionsFromBaseCE32(stringBuilder, n, n2, conditionalCE322);
            } else {
                n2 = this.copyFromBaseCE32(n, n2, true);
                n2 = n3 = this.addConditionalCE32(stringBuilder.toString(), n2);
                conditionalCE322.next = n3;
            }
            conditionalCE322 = this.getConditionalCE32(n2);
        } while (true);
    }

    void enableFastLatin() {
        this.fastLatinEnabled = true;
    }

    int encodeCEs(long[] arrl, int n) {
        if (n >= 0 && n <= 31) {
            if (this.isMutable()) {
                if (n == 0) {
                    return CollationDataBuilder.encodeOneCEAsCE32(0L);
                }
                if (n == 1) {
                    return this.encodeOneCE(arrl[0]);
                }
                if (n == 2) {
                    long l = arrl[0];
                    long l2 = arrl[1];
                    long l3 = l >>> 32;
                    if ((0xFFFFFFFFFF00FFL & l) == 0x5000000L && (-4278190081L & l2) == 1280L && l3 != 0L) {
                        return (int)l3 | ((int)l & 65280) << 8 | (int)l2 >> 16 & 65280 | 192 | 4;
                    }
                }
                int[] arrn = new int[31];
                int n2 = 0;
                do {
                    if (n2 == n) {
                        return this.encodeExpansion32(arrn, 0, n);
                    }
                    int n3 = CollationDataBuilder.encodeOneCEAsCE32(arrl[n2]);
                    if (n3 == 1) {
                        return this.encodeExpansion(arrl, 0, n);
                    }
                    arrn[n2] = n3;
                    ++n2;
                } while (true);
            }
            throw new IllegalStateException("attempt to add mappings after build()");
        }
        throw new IllegalArgumentException("mapping to too many CEs");
    }

    protected int encodeExpansion(long[] arrl, int n, int n2) {
        int n3;
        int n4;
        long l = arrl[n];
        int n5 = this.ce64s.size();
        block0 : for (n3 = 0; n3 <= n5 - n2; ++n3) {
            if (l != this.ce64s.elementAti(n3)) continue;
            if (n3 <= 524287) {
                n4 = 1;
                do {
                    if (n4 == n2) {
                        return Collation.makeCE32FromTagIndexAndLength(6, n3, n2);
                    }
                    if (this.ce64s.elementAti(n3 + n4) != arrl[n + n4]) continue block0;
                    ++n4;
                } while (true);
            }
            throw new IndexOutOfBoundsException("too many mappings");
        }
        n4 = this.ce64s.size();
        if (n4 <= 524287) {
            for (n3 = 0; n3 < n2; ++n3) {
                this.ce64s.addElement(arrl[n + n3]);
            }
            return Collation.makeCE32FromTagIndexAndLength(6, n4, n2);
        }
        throw new IndexOutOfBoundsException("too many mappings");
    }

    protected int encodeExpansion32(int[] arrn, int n, int n2) {
        int n3;
        int n4;
        int n5 = arrn[n];
        int n6 = this.ce32s.size();
        block0 : for (n4 = 0; n4 <= n6 - n2; ++n4) {
            if (n5 != this.ce32s.elementAti(n4)) continue;
            if (n4 <= 524287) {
                n3 = 1;
                do {
                    if (n3 == n2) {
                        return Collation.makeCE32FromTagIndexAndLength(5, n4, n2);
                    }
                    if (this.ce32s.elementAti(n4 + n3) != arrn[n + n3]) continue block0;
                    ++n3;
                } while (true);
            }
            throw new IndexOutOfBoundsException("too many mappings");
        }
        n3 = this.ce32s.size();
        if (n3 <= 524287) {
            for (n4 = 0; n4 < n2; ++n4) {
                this.ce32s.addElement(arrn[n + n4]);
            }
            return Collation.makeCE32FromTagIndexAndLength(5, n3, n2);
        }
        throw new IndexOutOfBoundsException("too many mappings");
    }

    protected int encodeOneCE(long l) {
        int n = CollationDataBuilder.encodeOneCEAsCE32(l);
        if (n != 1) {
            return n;
        }
        n = this.addCE(l);
        if (n <= 524287) {
            return Collation.makeCE32FromTagIndexAndLength(6, n, 1);
        }
        throw new IndexOutOfBoundsException("too many mappings");
    }

    protected int getCE32FromOffsetCE32(boolean bl, int n, int n2) {
        n2 = Collation.indexFromCE32(n2);
        long l = bl ? this.base.ces[n2] : this.ce64s.elementAti(n2);
        return Collation.makeLongPrimaryCE32(Collation.getThreeBytePrimaryForOffsetData(n, l));
    }

    protected int getCEs(CharSequence charSequence, int n, long[] arrl, int n2) {
        if (this.collIter == null) {
            this.collIter = new DataBuilderCollationIterator(this, new CollationData(this.nfcImpl));
            if (this.collIter == null) {
                return 0;
            }
        }
        return this.collIter.fetchCEs(charSequence, n, arrl, n2);
    }

    int getCEs(CharSequence charSequence, CharSequence charSequence2, long[] arrl, int n) {
        int n2 = charSequence.length();
        if (n2 == 0) {
            return this.getCEs(charSequence2, 0, arrl, n);
        }
        charSequence = new StringBuilder(charSequence);
        ((StringBuilder)charSequence).append(charSequence2);
        return this.getCEs(charSequence, n2, arrl, n);
    }

    int getCEs(CharSequence charSequence, long[] arrl, int n) {
        return this.getCEs(charSequence, 0, arrl, n);
    }

    protected ConditionalCE32 getConditionalCE32(int n) {
        return this.conditionalCE32s.get(n);
    }

    protected ConditionalCE32 getConditionalCE32ForCE32(int n) {
        return this.getConditionalCE32(Collation.indexFromCE32(n));
    }

    protected boolean getJamoCE32s(int[] arrn) {
        int n;
        int n2;
        boolean bl = this.base == null;
        int n3 = 0;
        for (int i = 0; i < 67; ++i) {
            int n4 = CollationDataBuilder.jamoCpFromIndex(i);
            boolean bl2 = false;
            n = this.trie.get(n4);
            boolean bl3 = bl | Collation.isAssignedCE32(n);
            bl = bl2;
            int n5 = n;
            if (n == 192) {
                bl = true;
                n5 = this.base.getCE32(n4);
            }
            n2 = n3;
            n = n5;
            if (Collation.isSpecialCE32(n5)) {
                switch (Collation.tagFromCE32(n5)) {
                    default: {
                        n2 = n3;
                        n = n5;
                        break;
                    }
                    case 15: {
                        n = 192;
                        n2 = 1;
                        break;
                    }
                    case 14: {
                        n = this.getCE32FromOffsetCE32(bl, n4, n5);
                        n2 = n3;
                        break;
                    }
                    case 5: 
                    case 6: 
                    case 8: 
                    case 9: {
                        n2 = n3;
                        n = n5;
                        if (!bl) break;
                        n = 192;
                        n2 = 1;
                        break;
                    }
                    case 1: 
                    case 2: 
                    case 4: {
                        n2 = n3;
                        n = n5;
                        break;
                    }
                    case 0: 
                    case 3: 
                    case 7: 
                    case 10: 
                    case 11: 
                    case 12: 
                    case 13: {
                        throw new AssertionError((Object)String.format("unexpected special tag in ce32=0x%08x", n5));
                    }
                }
            }
            arrn[i] = n;
            bl = bl3;
            n3 = n2;
        }
        if (bl && n3 != 0) {
            for (n = 0; n < 67; ++n) {
                if (arrn[n] != 192) continue;
                n2 = CollationDataBuilder.jamoCpFromIndex(n);
                arrn[n] = this.copyFromBaseCE32(n2, this.base.getCE32(n2), true);
            }
        }
        return bl;
    }

    boolean hasMappings() {
        return this.modified;
    }

    void initForTailoring(CollationData collationData) {
        if (this.trie == null) {
            if (collationData != null) {
                int n;
                this.base = collationData;
                this.trie = new Trie2Writable(192, -195323);
                for (n = 192; n <= 255; ++n) {
                    this.trie.set(n, 192);
                }
                n = Collation.makeCE32FromTagAndIndex(12, 0);
                this.trie.setRange(44032, 55203, n, true);
                this.unsafeBackwardSet.addAll(collationData.unsafeBackwardSet);
                return;
            }
            throw new IllegalArgumentException("null CollationData");
        }
        throw new IllegalStateException("attempt to reuse a CollationDataBuilder");
    }

    boolean isAssigned(int n) {
        return Collation.isAssignedCE32(this.trie.get(n));
    }

    boolean isCompressibleLeadByte(int n) {
        return this.base.isCompressibleLeadByte(n);
    }

    boolean isCompressiblePrimary(long l) {
        return this.isCompressibleLeadByte((int)l >>> 24);
    }

    protected final boolean isMutable() {
        UnicodeSet unicodeSet;
        boolean bl = this.trie != null && (unicodeSet = this.unsafeBackwardSet) != null && !unicodeSet.isFrozen();
        return bl;
    }

    void optimize(UnicodeSet object) {
        if (((UnicodeSet)object).isEmpty()) {
            return;
        }
        object = new UnicodeSetIterator((UnicodeSet)object);
        while (((UnicodeSetIterator)object).next() && ((UnicodeSetIterator)object).codepoint != UnicodeSetIterator.IS_STRING) {
            int n = ((UnicodeSetIterator)object).codepoint;
            if (this.trie.get(n) != 192) continue;
            CollationData collationData = this.base;
            int n2 = this.copyFromBaseCE32(n, collationData.getFinalCE32(collationData.getCE32(n)), true);
            this.trie.set(n, n2);
        }
        this.modified = true;
    }

    protected void setDigitTags() {
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(new UnicodeSet("[:Nd:]"));
        while (unicodeSetIterator.next()) {
            int n = unicodeSetIterator.codepoint;
            int n2 = this.trie.get(n);
            if (n2 == 192 || n2 == -1) continue;
            if ((n2 = this.addCE32(n2)) <= 524287) {
                n2 = Collation.makeCE32FromTagIndexAndLength(10, n2, UCharacter.digit(n));
                this.trie.set(n, n2);
                continue;
            }
            throw new IndexOutOfBoundsException("too many mappings");
        }
    }

    protected void setLeadSurrogates() {
        int n;
        int n2 = n = 55296;
        while (n2 < 56320) {
            int n3;
            block7 : {
                n = -1;
                Iterator<Trie2.Range> iterator = this.trie.iteratorForLeadSurrogate((char)n2);
                do {
                    int n4;
                    n3 = n;
                    if (!iterator.hasNext()) break block7;
                    n3 = iterator.next().value;
                    if (n3 == -1) {
                        n3 = 0;
                    } else {
                        if (n3 != 192) break;
                        n3 = 256;
                    }
                    if (n < 0) {
                        n4 = n3;
                    } else {
                        n4 = n;
                        if (n != n3) {
                            n3 = 512;
                            break block7;
                        }
                    }
                    n = n4;
                } while (true);
                n3 = 512;
            }
            this.trie.setForLeadSurrogateCodeUnit((char)n2, Collation.makeCE32FromTagAndIndex(13, 0) | n3);
            n2 = n = (int)((char)(n2 + 1));
        }
    }

    void suppressContractions(UnicodeSet object) {
        if (((UnicodeSet)object).isEmpty()) {
            return;
        }
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator((UnicodeSet)object);
        while (unicodeSetIterator.next() && unicodeSetIterator.codepoint != UnicodeSetIterator.IS_STRING) {
            int n = unicodeSetIterator.codepoint;
            int n2 = this.trie.get(n);
            if (n2 == 192) {
                object = this.base;
                n2 = ((CollationData)object).getFinalCE32(((CollationData)object).getCE32(n));
                if (!Collation.ce32HasContext(n2)) continue;
                n2 = this.copyFromBaseCE32(n, n2, false);
                this.trie.set(n, n2);
                continue;
            }
            if (!CollationDataBuilder.isBuilderContextCE32(n2)) continue;
            n2 = this.getConditionalCE32ForCE32((int)n2).ce32;
            this.trie.set(n, n2);
            this.contextChars.remove(n);
        }
        this.modified = true;
    }

    static interface CEModifier {
        public long modifyCE(long var1);

        public long modifyCE32(int var1);
    }

    private static final class ConditionalCE32 {
        int builtCE32;
        int ce32;
        String context;
        int defaultCE32;
        int next;

        ConditionalCE32(String string, int n) {
            this.context = string;
            this.ce32 = n;
            this.defaultCE32 = 1;
            this.builtCE32 = 1;
            this.next = -1;
        }

        boolean hasContext() {
            int n = this.context.length();
            boolean bl = true;
            if (n <= 1) {
                bl = false;
            }
            return bl;
        }

        int prefixLength() {
            return this.context.charAt(0);
        }
    }

    private static final class CopyHelper {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        CollationDataBuilder dest;
        long[] modifiedCEs = new long[31];
        CEModifier modifier;
        CollationDataBuilder src;

        CopyHelper(CollationDataBuilder collationDataBuilder, CollationDataBuilder collationDataBuilder2, CEModifier cEModifier) {
            this.src = collationDataBuilder;
            this.dest = collationDataBuilder2;
            this.modifier = cEModifier;
        }

        int copyCE32(int n) {
            int n2 = n;
            if (!Collation.isSpecialCE32(n)) {
                long l = this.modifier.modifyCE32(n2);
                n = n2;
                if (l != 0x101000100L) {
                    n = this.dest.encodeOneCE(l);
                }
            } else {
                int n3 = Collation.tagFromCE32(n);
                if (n3 == 5) {
                    int[] arrn = this.src.ce32s.getBuffer();
                    int n4 = Collation.indexFromCE32(n);
                    int n5 = Collation.lengthFromCE32(n);
                    n = 0;
                    for (n3 = 0; n3 < n5; ++n3) {
                        long l;
                        int n6 = arrn[n4 + n3];
                        if (!Collation.isSpecialCE32(n6) && (l = this.modifier.modifyCE32(n6)) != 0x101000100L) {
                            n2 = n;
                            if (n == 0) {
                                for (n = 0; n < n3; ++n) {
                                    this.modifiedCEs[n] = Collation.ceFromCE32(arrn[n4 + n]);
                                }
                                n2 = 1;
                            }
                            this.modifiedCEs[n3] = l;
                        } else {
                            n2 = n;
                            if (n != 0) {
                                this.modifiedCEs[n3] = Collation.ceFromCE32(n6);
                                n2 = n;
                            }
                        }
                        n = n2;
                    }
                    n = n != 0 ? this.dest.encodeCEs(this.modifiedCEs, n5) : this.dest.encodeExpansion32(arrn, n4, n5);
                } else if (n3 == 6) {
                    long[] arrl = this.src.ce64s.getBuffer();
                    int n7 = Collation.indexFromCE32(n);
                    int n8 = Collation.lengthFromCE32(n);
                    n = 0;
                    for (n3 = 0; n3 < n8; ++n3) {
                        long l = arrl[n7 + n3];
                        long l2 = this.modifier.modifyCE(l);
                        if (l2 == 0x101000100L) {
                            n2 = n;
                            if (n != 0) {
                                this.modifiedCEs[n3] = l;
                                n2 = n;
                            }
                        } else {
                            n2 = n;
                            if (n == 0) {
                                for (n = 0; n < n3; ++n) {
                                    this.modifiedCEs[n] = arrl[n7 + n];
                                }
                                n2 = 1;
                            }
                            this.modifiedCEs[n3] = l2;
                        }
                        n = n2;
                    }
                    n = n != 0 ? this.dest.encodeCEs(this.modifiedCEs, n8) : this.dest.encodeExpansion(arrl, n7, n8);
                } else if (n3 == 7) {
                    ConditionalCE32 conditionalCE32 = this.src.getConditionalCE32ForCE32(n2);
                    n = this.dest.addConditionalCE32(conditionalCE32.context, this.copyCE32(conditionalCE32.ce32));
                    n2 = CollationDataBuilder.makeBuilderContextCE32(n);
                    while (conditionalCE32.next >= 0) {
                        conditionalCE32 = this.src.getConditionalCE32(conditionalCE32.next);
                        ConditionalCE32 conditionalCE322 = this.dest.getConditionalCE32(n);
                        n = this.dest.addConditionalCE32(conditionalCE32.context, this.copyCE32(conditionalCE32.ce32));
                        n3 = conditionalCE32.prefixLength();
                        this.dest.unsafeBackwardSet.addAll(conditionalCE32.context.substring(n3 + 1));
                        conditionalCE322.next = n;
                    }
                    n = n2;
                } else {
                    n = n2;
                }
            }
            return n;
        }

        void copyRangeCE32(int n, int n2, int n3) {
            n3 = this.copyCE32(n3);
            this.dest.trie.setRange(n, n2, n3, true);
            if (CollationDataBuilder.isBuilderContextCE32(n3)) {
                this.dest.contextChars.add(n, n2);
            }
        }
    }

    private static final class DataBuilderCollationIterator
    extends CollationIterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        protected final CollationDataBuilder builder;
        protected final CollationData builderData;
        protected final int[] jamoCE32s = new int[67];
        protected int pos;
        protected CharSequence s;

        DataBuilderCollationIterator(CollationDataBuilder collationDataBuilder, CollationData collationData) {
            super(collationData, false);
            this.builder = collationDataBuilder;
            this.builderData = collationData;
            this.builderData.base = this.builder.base;
            for (int i = 0; i < 67; ++i) {
                int n = CollationDataBuilder.jamoCpFromIndex(i);
                this.jamoCE32s[i] = Collation.makeCE32FromTagAndIndex(7, n) | 256;
            }
            this.builderData.jamoCE32s = this.jamoCE32s;
        }

        @Override
        protected void backwardNumCodePoints(int n) {
            this.pos = Character.offsetByCodePoints(this.s, this.pos, -n);
        }

        int fetchCEs(CharSequence object, int n, long[] arrl, int n2) {
            this.builderData.ce32s = this.builder.ce32s.getBuffer();
            this.builderData.ces = this.builder.ce64s.getBuffer();
            this.builderData.contexts = this.builder.contexts.toString();
            this.reset();
            this.s = object;
            this.pos = n;
            while (this.pos < this.s.length()) {
                this.clearCEs();
                int n3 = Character.codePointAt(this.s, this.pos);
                this.pos += Character.charCount(n3);
                n = this.builder.trie.get(n3);
                if (n == 192) {
                    object = this.builder.base;
                    n = this.builder.base.getCE32(n3);
                } else {
                    object = this.builderData;
                }
                this.appendCEsFromCE32((CollationData)object, n3, n, true);
                n3 = n2;
                for (n = 0; n < this.getCEsLength(); ++n) {
                    long l = this.getCE(n);
                    n2 = n3;
                    if (l != 0L) {
                        if (n3 < 31) {
                            arrl[n3] = l;
                        }
                        n2 = n3 + 1;
                    }
                    n3 = n2;
                }
                n2 = n3;
            }
            return n2;
        }

        @Override
        protected void forwardNumCodePoints(int n) {
            this.pos = Character.offsetByCodePoints(this.s, this.pos, n);
        }

        @Override
        protected int getCE32FromBuilderData(int n) {
            if ((n & 256) != 0) {
                n = Collation.indexFromCE32(n);
                return this.builder.trie.get(n);
            }
            ConditionalCE32 conditionalCE32 = this.builder.getConditionalCE32ForCE32(n);
            if (conditionalCE32.builtCE32 == 1) {
                try {
                    conditionalCE32.builtCE32 = this.builder.buildContext(conditionalCE32);
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    this.builder.clearContexts();
                    conditionalCE32.builtCE32 = this.builder.buildContext(conditionalCE32);
                }
                this.builderData.contexts = this.builder.contexts.toString();
            }
            return conditionalCE32.builtCE32;
        }

        @Override
        protected int getDataCE32(int n) {
            return this.builder.trie.get(n);
        }

        @Override
        public int getOffset() {
            return this.pos;
        }

        @Override
        public int nextCodePoint() {
            if (this.pos == this.s.length()) {
                return -1;
            }
            int n = Character.codePointAt(this.s, this.pos);
            this.pos += Character.charCount(n);
            return n;
        }

        @Override
        public int previousCodePoint() {
            int n = this.pos;
            if (n == 0) {
                return -1;
            }
            n = Character.codePointBefore(this.s, n);
            this.pos -= Character.charCount(n);
            return n;
        }

        @Override
        public void resetToOffset(int n) {
            this.reset();
            this.pos = n;
        }
    }

}

