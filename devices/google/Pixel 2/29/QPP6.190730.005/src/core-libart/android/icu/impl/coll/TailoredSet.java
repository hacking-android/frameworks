/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.Normalizer2Impl;
import android.icu.impl.Trie2;
import android.icu.impl.Trie2_32;
import android.icu.impl.Utility;
import android.icu.impl.coll.Collation;
import android.icu.impl.coll.CollationData;
import android.icu.text.UnicodeSet;
import android.icu.util.CharsTrie;

public final class TailoredSet {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private CollationData baseData;
    private CollationData data;
    private String suffix;
    private UnicodeSet tailored;
    private StringBuilder unreversedPrefix = new StringBuilder();

    public TailoredSet(UnicodeSet unicodeSet) {
        this.tailored = unicodeSet;
    }

    private void add(int n) {
        if (this.unreversedPrefix.length() == 0 && this.suffix == null) {
            this.tailored.add(n);
        } else {
            StringBuilder stringBuilder = new StringBuilder(this.unreversedPrefix);
            stringBuilder.appendCodePoint(n);
            String string = this.suffix;
            if (string != null) {
                stringBuilder.append(string);
            }
            this.tailored.add(stringBuilder);
        }
    }

    private void addContractions(int n, CharSequence object, int n2) {
        object = new CharsTrie((CharSequence)object, n2).iterator();
        while (((CharsTrie.Iterator)object).hasNext()) {
            this.addSuffix(n, object.next().chars);
        }
    }

    private void addPrefix(CollationData collationData, CharSequence charSequence, int n, int n2) {
        this.setPrefix(charSequence);
        n2 = collationData.getFinalCE32(n2);
        if (Collation.isContractionCE32(n2)) {
            n2 = Collation.indexFromCE32(n2);
            this.addContractions(n, collationData.contexts, n2 + 2);
        }
        this.tailored.add(new StringBuilder(this.unreversedPrefix.appendCodePoint(n)));
        this.resetPrefix();
    }

    private void addPrefixes(CollationData collationData, int n, CharSequence object2, int n2) {
        for (Object object2 : new CharsTrie((CharSequence)object2, n2)) {
            this.addPrefix(collationData, ((CharsTrie.Entry)object2).chars, n, ((CharsTrie.Entry)object2).value);
        }
    }

    private void addSuffix(int n, CharSequence charSequence) {
        UnicodeSet unicodeSet = this.tailored;
        StringBuilder stringBuilder = new StringBuilder(this.unreversedPrefix).appendCodePoint(n);
        stringBuilder.append(charSequence);
        unicodeSet.add(stringBuilder);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void compare(int var1_1, int var2_2, int var3_3) {
        block28 : {
            block27 : {
                block26 : {
                    if (!Collation.isPrefixCE32(var2_2)) break block26;
                    var2_2 = Collation.indexFromCE32(var2_2);
                    var4_4 = this.data;
                    var5_5 = var4_4.getFinalCE32(var4_4.getCE32FromContexts(var2_2));
                    if (Collation.isPrefixCE32(var3_3)) {
                        var3_3 = Collation.indexFromCE32(var3_3);
                        var4_4 = this.baseData;
                        var6_6 = var4_4.getFinalCE32(var4_4.getCE32FromContexts(var3_3));
                        this.comparePrefixes(var1_1, this.data.contexts, var2_2 + 2, this.baseData.contexts, var3_3 + 2);
                    } else {
                        var4_4 = this.data;
                        this.addPrefixes((CollationData)var4_4, var1_1, var4_4.contexts, var2_2 + 2);
                        var6_6 = var3_3;
                    }
                    ** GOTO lbl-1000
                }
                var5_5 = var2_2;
                var6_6 = var3_3;
                if (Collation.isPrefixCE32(var3_3)) {
                    var5_5 = Collation.indexFromCE32(var3_3);
                    var4_4 = this.baseData;
                    var3_3 = var4_4.getFinalCE32(var4_4.getCE32FromContexts(var5_5));
                    var4_4 = this.baseData;
                    this.addPrefixes((CollationData)var4_4, var1_1, var4_4.contexts, var5_5 + 2);
                } else lbl-1000: // 3 sources:
                {
                    var3_3 = var6_6;
                    var2_2 = var5_5;
                }
                if (!Collation.isContractionCE32(var2_2)) break block27;
                var5_5 = Collation.indexFromCE32(var2_2);
                if ((var2_2 & 256) != 0) {
                    var2_2 = 1;
                } else {
                    var4_4 = this.data;
                    var2_2 = var4_4.getFinalCE32(var4_4.getCE32FromContexts(var5_5));
                }
                if (Collation.isContractionCE32(var3_3)) {
                    var6_6 = Collation.indexFromCE32(var3_3);
                    if ((var3_3 & 256) != 0) {
                        var3_3 = 1;
                    } else {
                        var4_4 = this.baseData;
                        var3_3 = var4_4.getFinalCE32(var4_4.getCE32FromContexts(var6_6));
                    }
                    this.compareContractions(var1_1, this.data.contexts, var5_5 + 2, this.baseData.contexts, var6_6 + 2);
                    var5_5 = var2_2;
                    var6_6 = var3_3;
                } else {
                    this.addContractions(var1_1, this.data.contexts, var5_5 + 2);
                    var5_5 = var2_2;
                    var6_6 = var3_3;
                }
                ** GOTO lbl-1000
            }
            var5_5 = var2_2;
            var6_6 = var3_3;
            if (Collation.isContractionCE32(var3_3)) {
                var3_3 = Collation.indexFromCE32(var3_3);
                var4_4 = this.baseData;
                var6_6 = var4_4.getFinalCE32(var4_4.getCE32FromContexts(var3_3));
                this.addContractions(var1_1, this.baseData.contexts, var3_3 + 2);
            } else lbl-1000: // 3 sources:
            {
                var2_2 = var5_5;
            }
            var3_3 = Collation.isSpecialCE32(var2_2) != false ? Collation.tagFromCE32(var2_2) : -1;
            var5_5 = Collation.isSpecialCE32(var6_6) != false ? Collation.tagFromCE32(var6_6) : -1;
            if (var5_5 == 14) {
                if (!Collation.isLongPrimaryCE32(var2_2)) {
                    this.add(var1_1);
                    return;
                }
                var7_7 = Collation.getThreeBytePrimaryForOffsetData(var1_1, this.baseData.ces[Collation.indexFromCE32(var6_6)]);
                if (Collation.primaryFromLongPrimaryCE32(var2_2) != var7_7) {
                    this.add(var1_1);
                    return;
                }
            }
            if (var3_3 != var5_5) {
                this.add(var1_1);
                return;
            }
            if (var3_3 != 5) break block28;
            var3_3 = Collation.lengthFromCE32(var2_2);
            if (var3_3 != Collation.lengthFromCE32(var6_6)) {
                this.add(var1_1);
                return;
            }
            ** GOTO lbl96
        }
        if (var3_3 == 6) {
            var3_3 = Collation.lengthFromCE32(var2_2);
            if (var3_3 != Collation.lengthFromCE32(var6_6)) {
                this.add(var1_1);
                return;
            }
        } else {
            if (var3_3 != 12) {
                if (var2_2 == var6_6) return;
                this.add(var1_1);
                return;
            }
            var4_4 = new StringBuilder();
            var2_2 = Normalizer2Impl.Hangul.decompose(var1_1, (Appendable)var4_4);
            if (!this.tailored.contains(var4_4.charAt(0)) && !this.tailored.contains(var4_4.charAt(1))) {
                if (var2_2 != 3) return;
                if (this.tailored.contains(var4_4.charAt(2)) == false) return;
            }
            this.add(var1_1);
            return;
lbl96: // 1 sources:
            var5_5 = Collation.indexFromCE32(var2_2);
            var6_6 = Collation.indexFromCE32(var6_6);
            var2_2 = 0;
            while (var2_2 < var3_3) {
                if (this.data.ce32s[var5_5 + var2_2] != this.baseData.ce32s[var6_6 + var2_2]) {
                    this.add(var1_1);
                    return;
                }
                ++var2_2;
            }
            return;
        }
        var5_5 = Collation.indexFromCE32(var2_2);
        var6_6 = Collation.indexFromCE32(var6_6);
        var2_2 = 0;
        while (var2_2 < var3_3) {
            if (this.data.ces[var5_5 + var2_2] != this.baseData.ces[var6_6 + var2_2]) {
                this.add(var1_1);
                return;
            }
            ++var2_2;
        }
    }

    private void compareContractions(int n, CharSequence object, int n2, CharSequence charSequence, int n3) {
        CharsTrie.Iterator iterator = new CharsTrie((CharSequence)object, n2).iterator();
        CharsTrie.Iterator iterator2 = new CharsTrie(charSequence, n3).iterator();
        CharSequence charSequence2 = null;
        String string = null;
        object = null;
        CharsTrie.Entry entry = null;
        do {
            charSequence = charSequence2;
            if (charSequence2 == null) {
                if (iterator.hasNext()) {
                    object = iterator.next();
                    charSequence = ((CharsTrie.Entry)object).chars.toString();
                } else {
                    object = null;
                    charSequence = "\uffff\uffff";
                }
            }
            String string2 = string;
            if (string == null) {
                if (iterator2.hasNext()) {
                    entry = iterator2.next();
                    string2 = entry.chars.toString();
                } else {
                    entry = null;
                    string2 = "\uffff\uffff";
                }
            }
            if (Utility.sameObjects(charSequence, "\uffff\uffff") && Utility.sameObjects(string2, "\uffff\uffff")) {
                return;
            }
            n2 = ((String)charSequence).compareTo(string2);
            if (n2 < 0) {
                this.addSuffix(n, charSequence);
                object = null;
                charSequence = null;
            } else if (n2 > 0) {
                this.addSuffix(n, string2);
                entry = null;
                string2 = null;
            } else {
                this.suffix = charSequence;
                this.compare(n, ((CharsTrie.Entry)object).value, entry.value);
                this.suffix = null;
                entry = null;
                object = null;
                string2 = null;
                charSequence = null;
            }
            charSequence2 = charSequence;
            string = string2;
        } while (true);
    }

    private void comparePrefixes(int n, CharSequence object, int n2, CharSequence charSequence, int n3) {
        CharsTrie.Iterator iterator = new CharsTrie((CharSequence)object, n2).iterator();
        CharsTrie.Iterator iterator2 = new CharsTrie(charSequence, n3).iterator();
        CharSequence charSequence2 = null;
        String string = null;
        object = null;
        CharsTrie.Entry entry = null;
        do {
            charSequence = charSequence2;
            if (charSequence2 == null) {
                if (iterator.hasNext()) {
                    object = iterator.next();
                    charSequence = ((CharsTrie.Entry)object).chars.toString();
                } else {
                    object = null;
                    charSequence = "\uffff";
                }
            }
            String string2 = string;
            if (string == null) {
                if (iterator2.hasNext()) {
                    entry = iterator2.next();
                    string2 = entry.chars.toString();
                } else {
                    entry = null;
                    string2 = "\uffff";
                }
            }
            if (Utility.sameObjects(charSequence, "\uffff") && Utility.sameObjects(string2, "\uffff")) {
                return;
            }
            n2 = ((String)charSequence).compareTo(string2);
            if (n2 < 0) {
                this.addPrefix(this.data, charSequence, n, ((CharsTrie.Entry)object).value);
                object = null;
                charSequence = null;
            } else if (n2 > 0) {
                this.addPrefix(this.baseData, string2, n, entry.value);
                entry = null;
                string2 = null;
            } else {
                this.setPrefix(charSequence);
                this.compare(n, ((CharsTrie.Entry)object).value, entry.value);
                this.resetPrefix();
                entry = null;
                object = null;
                string2 = null;
                charSequence = null;
            }
            charSequence2 = charSequence;
            string = string2;
        } while (true);
    }

    private void enumTailoredRange(int n, int n2, int n3, TailoredSet tailoredSet) {
        if (n3 == 192) {
            return;
        }
        tailoredSet.handleCE32(n, n2, n3);
    }

    private void handleCE32(int n, int n2, int n3) {
        int n4 = n;
        int n5 = n3;
        if (Collation.isSpecialCE32(n3)) {
            n3 = this.data.getIndirectCE32(n3);
            n4 = n;
            n5 = n3;
            if (n3 == 192) {
                return;
            }
        }
        do {
            CollationData collationData = this.baseData;
            n = collationData.getFinalCE32(collationData.getCE32(n4));
            if (Collation.isSelfContainedCE32(n5) && Collation.isSelfContainedCE32(n)) {
                if (n5 == n) continue;
                this.tailored.add(n4);
                continue;
            }
            this.compare(n4, n5, n);
        } while (++n4 <= n2);
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

    public void forData(CollationData object) {
        this.data = object;
        this.baseData = ((CollationData)object).base;
        for (Trie2.Range range : this.data.trie) {
            if (range.leadSurrogate) break;
            this.enumTailoredRange(range.startCodePoint, range.endCodePoint, range.value, this);
        }
    }
}

