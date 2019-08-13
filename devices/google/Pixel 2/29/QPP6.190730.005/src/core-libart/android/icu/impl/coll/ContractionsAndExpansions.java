/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.Trie2;
import android.icu.impl.Trie2_32;
import android.icu.impl.coll.Collation;
import android.icu.impl.coll.CollationData;
import android.icu.impl.coll.UTF16CollationIterator;
import android.icu.text.UnicodeSet;
import android.icu.util.CharsTrie;

public final class ContractionsAndExpansions {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private boolean addPrefixes;
    private long[] ces = new long[31];
    private int checkTailored = 0;
    private UnicodeSet contractions;
    private CollationData data;
    private UnicodeSet expansions;
    private UnicodeSet ranges;
    private CESink sink;
    private String suffix;
    private UnicodeSet tailored = new UnicodeSet();
    private StringBuilder unreversedPrefix = new StringBuilder();

    public ContractionsAndExpansions(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, CESink cESink, boolean bl) {
        this.contractions = unicodeSet;
        this.expansions = unicodeSet2;
        this.sink = cESink;
        this.addPrefixes = bl;
    }

    private void enumCnERange(int n, int n2, int n3, ContractionsAndExpansions contractionsAndExpansions) {
        int n4 = contractionsAndExpansions.checkTailored;
        if (n4 != 0) {
            if (n4 < 0) {
                if (n3 == 192) {
                    return;
                }
                contractionsAndExpansions.tailored.add(n, n2);
            } else if (n == n2) {
                if (contractionsAndExpansions.tailored.contains(n)) {
                    return;
                }
            } else if (contractionsAndExpansions.tailored.containsSome(n, n2)) {
                if (contractionsAndExpansions.ranges == null) {
                    contractionsAndExpansions.ranges = new UnicodeSet();
                }
                contractionsAndExpansions.ranges.set(n, n2).removeAll(contractionsAndExpansions.tailored);
                int n5 = contractionsAndExpansions.ranges.getRangeCount();
                for (n4 = 0; n4 < n5; ++n4) {
                    contractionsAndExpansions.handleCE32(contractionsAndExpansions.ranges.getRangeStart(n4), contractionsAndExpansions.ranges.getRangeEnd(n4), n3);
                }
            }
        }
        contractionsAndExpansions.handleCE32(n, n2, n3);
    }

    private void handleCE32(int n, int n2, int n3) {
        block16 : do {
            if ((n3 & 255) < 192) {
                CESink cESink = this.sink;
                if (cESink != null) {
                    cESink.handleCE(Collation.ceFromSimpleCE32(n3));
                }
                return;
            }
            switch (Collation.tagFromCE32(n3)) {
                default: {
                    continue block16;
                }
                case 15: {
                    return;
                }
                case 14: {
                    return;
                }
                case 12: {
                    if (this.sink != null) {
                        UTF16CollationIterator uTF16CollationIterator = new UTF16CollationIterator(this.data);
                        StringBuilder stringBuilder = new StringBuilder(1);
                        for (n3 = n; n3 <= n2; ++n3) {
                            stringBuilder.setLength(0);
                            stringBuilder.appendCodePoint(n3);
                            uTF16CollationIterator.setText(false, stringBuilder, 0);
                            int n4 = uTF16CollationIterator.fetchCEs();
                            this.sink.handleExpansion(uTF16CollationIterator.getCEs(), 0, n4 - 1);
                        }
                    }
                    if (this.unreversedPrefix.length() == 0) {
                        this.addExpansions(n, n2);
                    }
                    return;
                }
                case 11: {
                    n3 = this.data.ce32s[0];
                    continue block16;
                }
                case 10: {
                    n3 = this.data.ce32s[Collation.indexFromCE32(n3)];
                    continue block16;
                }
                case 9: {
                    this.handleContractions(n, n2, n3);
                    return;
                }
                case 8: {
                    this.handlePrefixes(n, n2, n3);
                    return;
                }
                case 6: {
                    if (this.sink != null) {
                        int n5 = Collation.indexFromCE32(n3);
                        n3 = Collation.lengthFromCE32(n3);
                        this.sink.handleExpansion(this.data.ces, n5, n3);
                    }
                    if (this.unreversedPrefix.length() == 0) {
                        this.addExpansions(n, n2);
                    }
                    return;
                }
                case 5: {
                    if (this.sink != null) {
                        int n6 = Collation.indexFromCE32(n3);
                        int n7 = Collation.lengthFromCE32(n3);
                        for (n3 = 0; n3 < n7; ++n3) {
                            this.ces[n3] = Collation.ceFromCE32(this.data.ce32s[n6 + n3]);
                        }
                        this.sink.handleExpansion(this.ces, 0, n7);
                    }
                    if (this.unreversedPrefix.length() == 0) {
                        this.addExpansions(n, n2);
                    }
                    return;
                }
                case 4: {
                    if (this.sink != null) {
                        this.ces[0] = Collation.latinCE0FromCE32(n3);
                        this.ces[1] = Collation.latinCE1FromCE32(n3);
                        this.sink.handleExpansion(this.ces, 0, 2);
                    }
                    if (this.unreversedPrefix.length() == 0) {
                        this.addExpansions(n, n2);
                    }
                    return;
                }
                case 3: 
                case 7: 
                case 13: {
                    throw new AssertionError((Object)String.format("Unexpected CE32 tag type %d for ce32=0x%08x", Collation.tagFromCE32(n3), n3));
                }
                case 2: {
                    CESink cESink = this.sink;
                    if (cESink != null) {
                        cESink.handleCE(Collation.ceFromLongSecondaryCE32(n3));
                    }
                    return;
                }
                case 1: {
                    CESink cESink = this.sink;
                    if (cESink != null) {
                        cESink.handleCE(Collation.ceFromLongPrimaryCE32(n3));
                    }
                    return;
                }
                case 0: 
            }
            break;
        } while (true);
    }

    private void handlePrefixes(int n, int n2, int n3) {
        n3 = Collation.indexFromCE32(n3);
        this.handleCE32(n, n2, this.data.getCE32FromContexts(n3));
        if (!this.addPrefixes) {
            return;
        }
        for (CharsTrie.Entry entry : new CharsTrie(this.data.contexts, n3 + 2)) {
            this.setPrefix(entry.chars);
            this.addStrings(n, n2, this.contractions);
            this.addStrings(n, n2, this.expansions);
            this.handleCE32(n, n2, entry.value);
        }
        this.resetPrefix();
    }

    private void resetPrefix() {
        this.unreversedPrefix.setLength(0);
    }

    private void setPrefix(CharSequence charSequence) {
        this.unreversedPrefix.setLength(0);
        StringBuilder stringBuilder = this.unreversedPrefix;
        stringBuilder.append(charSequence);
        stringBuilder.reverse();
    }

    void addExpansions(int n, int n2) {
        if (this.unreversedPrefix.length() == 0 && this.suffix == null) {
            UnicodeSet unicodeSet = this.expansions;
            if (unicodeSet != null) {
                unicodeSet.add(n, n2);
            }
        } else {
            this.addStrings(n, n2, this.expansions);
        }
    }

    void addStrings(int n, int n2, UnicodeSet unicodeSet) {
        if (unicodeSet == null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(this.unreversedPrefix);
        do {
            stringBuilder.appendCodePoint(n);
            String string = this.suffix;
            if (string != null) {
                stringBuilder.append(string);
            }
            unicodeSet.add(stringBuilder);
            stringBuilder.setLength(this.unreversedPrefix.length());
        } while (++n <= n2);
    }

    public void forCodePoint(CollationData collationData, int n) {
        int n2;
        int n3 = n2 = collationData.getCE32(n);
        CollationData collationData2 = collationData;
        if (n2 == 192) {
            collationData2 = collationData.base;
            n3 = collationData2.getCE32(n);
        }
        this.data = collationData2;
        this.handleCE32(n, n, n3);
    }

    public void forData(CollationData object) {
        if (((CollationData)object).base != null) {
            this.checkTailored = -1;
        }
        this.data = object;
        for (Trie2.Range range : this.data.trie) {
            if (range.leadSurrogate) break;
            this.enumCnERange(range.startCodePoint, range.endCodePoint, range.value, this);
        }
        if (((CollationData)object).base == null) {
            return;
        }
        this.tailored.freeze();
        this.checkTailored = 1;
        this.data = ((CollationData)object).base;
        for (Trie2.Range range : this.data.trie) {
            if (range.leadSurrogate) break;
            this.enumCnERange(range.startCodePoint, range.endCodePoint, range.value, this);
        }
    }

    void handleContractions(int n, int n2, int n3) {
        int n4 = Collation.indexFromCE32(n3);
        if ((n3 & 256) == 0) {
            n3 = this.data.getCE32FromContexts(n4);
            this.handleCE32(n, n2, n3);
        }
        for (CharsTrie.Entry entry : new CharsTrie(this.data.contexts, n4 + 2)) {
            this.suffix = entry.chars.toString();
            this.addStrings(n, n2, this.contractions);
            if (this.unreversedPrefix.length() != 0) {
                this.addStrings(n, n2, this.expansions);
            }
            this.handleCE32(n, n2, entry.value);
        }
        this.suffix = null;
    }

    public static interface CESink {
        public void handleCE(long var1);

        public void handleExpansion(long[] var1, int var2, int var3);
    }

}

