/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Utility;
import android.icu.text.Replaceable;
import android.icu.text.RuleBasedTransliterator;
import android.icu.text.StringMatcher;
import android.icu.text.StringReplacer;
import android.icu.text.Transliterator;
import android.icu.text.UTF16;
import android.icu.text.UnicodeMatcher;
import android.icu.text.UnicodeReplacer;
import android.icu.text.UnicodeSet;

class TransliterationRule {
    static final int ANCHOR_END = 2;
    static final int ANCHOR_START = 1;
    private StringMatcher anteContext;
    private int anteContextLength;
    private final RuleBasedTransliterator.Data data;
    byte flags;
    private StringMatcher key;
    private int keyLength;
    private UnicodeReplacer output;
    private String pattern;
    private StringMatcher postContext;
    UnicodeMatcher[] segments;

    public TransliterationRule(String string, int n, int n2, String string2, int n3, int n4, UnicodeMatcher[] arrunicodeMatcher, boolean bl, boolean bl2, RuleBasedTransliterator.Data data) {
        block10 : {
            block13 : {
                block16 : {
                    block15 : {
                        block14 : {
                            block12 : {
                                block11 : {
                                    block9 : {
                                        block8 : {
                                            this.data = data;
                                            if (n >= 0) break block8;
                                            this.anteContextLength = 0;
                                            break block9;
                                        }
                                        if (n > string.length()) break block10;
                                        this.anteContextLength = n;
                                    }
                                    if (n2 >= 0) break block11;
                                    this.keyLength = string.length() - this.anteContextLength;
                                    break block12;
                                }
                                if (n2 < this.anteContextLength || n2 > string.length()) break block13;
                                this.keyLength = n2 - this.anteContextLength;
                            }
                            if (n3 >= 0) break block14;
                            n = string2.length();
                            break block15;
                        }
                        if (n3 > string2.length()) break block16;
                        n = n3;
                    }
                    this.segments = arrunicodeMatcher;
                    this.pattern = string;
                    this.flags = (byte)(false ? 1 : 0);
                    if (bl) {
                        this.flags = (byte)(this.flags | 1);
                    }
                    if (bl2) {
                        this.flags = (byte)(this.flags | 2);
                    }
                    this.anteContext = null;
                    n2 = this.anteContextLength;
                    if (n2 > 0) {
                        this.anteContext = new StringMatcher(this.pattern.substring(0, n2), 0, this.data);
                    }
                    this.key = null;
                    n3 = this.keyLength;
                    if (n3 > 0) {
                        string = this.pattern;
                        n2 = this.anteContextLength;
                        this.key = new StringMatcher(string.substring(n2, n3 + n2), 0, this.data);
                    }
                    n3 = this.pattern.length();
                    int n5 = this.keyLength;
                    n2 = this.anteContextLength;
                    this.postContext = null;
                    if (n3 - n5 - n2 > 0) {
                        this.postContext = new StringMatcher(this.pattern.substring(n2 + n5), 0, this.data);
                    }
                    this.output = new StringReplacer(string2, n + n4, this.data);
                    return;
                }
                throw new IllegalArgumentException("Invalid cursor position");
            }
            throw new IllegalArgumentException("Invalid post context");
        }
        throw new IllegalArgumentException("Invalid ante context");
    }

    static final int posAfter(Replaceable replaceable, int n) {
        n = n >= 0 && n < replaceable.length() ? UTF16.getCharCount(replaceable.char32At(n)) + n : ++n;
        return n;
    }

    static final int posBefore(Replaceable replaceable, int n) {
        n = n > 0 ? (n -= UTF16.getCharCount(replaceable.char32At(n - 1))) : --n;
        return n;
    }

    void addSourceTargetSet(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, UnicodeSet unicodeSet3, UnicodeSet unicodeSet4) {
        int n = this.anteContextLength;
        int n2 = this.keyLength;
        unicodeSet4 = new UnicodeSet();
        UnicodeSet unicodeSet5 = new UnicodeSet();
        int n3 = this.anteContextLength;
        while (n3 < n + n2) {
            int n4 = UTF16.charAt(this.pattern, n3);
            n3 += UTF16.getCharCount(n4);
            UnicodeMatcher unicodeMatcher = this.data.lookupMatcher(n4);
            if (unicodeMatcher == null) {
                if (!unicodeSet.contains(n4)) {
                    return;
                }
                unicodeSet4.add(n4);
                continue;
            }
            try {
                if (!unicodeSet.containsSome((UnicodeSet)unicodeMatcher)) {
                    return;
                }
                unicodeMatcher.addMatchSetTo(unicodeSet4);
            }
            catch (ClassCastException classCastException) {
                unicodeSet5.clear();
                unicodeMatcher.addMatchSetTo(unicodeSet5);
                if (!unicodeSet.containsSome(unicodeSet5)) {
                    return;
                }
                unicodeSet4.addAll(unicodeSet5);
            }
        }
        unicodeSet2.addAll(unicodeSet4);
        this.output.addReplacementSetTo(unicodeSet3);
    }

    public int getAnteContextLength() {
        int n = this.anteContextLength;
        byte by = this.flags;
        int n2 = 1;
        if ((by & 1) == 0) {
            n2 = 0;
        }
        return n + n2;
    }

    final int getIndexValue() {
        int n = this.anteContextLength;
        int n2 = this.pattern.length();
        int n3 = -1;
        if (n == n2) {
            return -1;
        }
        n = UTF16.charAt(this.pattern, this.anteContextLength);
        if (this.data.lookupMatcher(n) == null) {
            n3 = n & 255;
        }
        return n3;
    }

    public boolean masks(TransliterationRule transliterationRule) {
        int n;
        int n2;
        int n3;
        boolean bl;
        int n4;
        int n5;
        block4 : {
            block5 : {
                boolean bl2;
                block6 : {
                    n3 = this.pattern.length();
                    n5 = this.anteContextLength;
                    n = transliterationRule.anteContextLength;
                    n4 = this.pattern.length() - n5;
                    n2 = transliterationRule.pattern.length() - n;
                    bl = true;
                    bl2 = true;
                    if (n5 != n || n4 != n2 || this.keyLength > transliterationRule.keyLength || !transliterationRule.pattern.regionMatches(0, this.pattern, 0, n3)) break block4;
                    n2 = this.flags;
                    bl = bl2;
                    if (n2 == transliterationRule.flags) break block5;
                    if ((n2 & 1) != 0) break block6;
                    bl = bl2;
                    if ((n2 & 2) == 0) break block5;
                }
                bl = ((n2 = (int)transliterationRule.flags) & 1) != 0 && (n2 & 2) != 0 ? bl2 : false;
            }
            return bl;
        }
        if (n5 > n || n4 >= n2 && (n4 != n2 || this.keyLength > transliterationRule.keyLength) || !transliterationRule.pattern.regionMatches(n - n5, this.pattern, 0, n3)) {
            bl = false;
        }
        return bl;
    }

    public int matchAndReplace(Replaceable replaceable, Transliterator.Position position, boolean bl) {
        int n;
        Object[] arrobject;
        if (this.segments != null) {
            for (n = 0; n < (arrobject = this.segments).length; ++n) {
                ((StringMatcher)arrobject[n]).resetMatch();
            }
        }
        arrobject = new int[1];
        int n2 = TransliterationRule.posBefore(replaceable, position.contextStart);
        arrobject[0] = TransliterationRule.posBefore(replaceable, position.start);
        StringMatcher stringMatcher = this.anteContext;
        if (stringMatcher != null && stringMatcher.matches(replaceable, (int[])arrobject, n2, false) != 2) {
            return 0;
        }
        int n3 = arrobject[0];
        n = TransliterationRule.posAfter(replaceable, n3);
        if ((this.flags & 1) != 0 && n3 != n2) {
            return 0;
        }
        arrobject[0] = position.start;
        stringMatcher = this.key;
        if (stringMatcher != null && (n3 = stringMatcher.matches(replaceable, (int[])arrobject, position.limit, bl)) != 2) {
            return n3;
        }
        n2 = arrobject[0];
        if (this.postContext != null) {
            if (bl && n2 == position.limit) {
                return 1;
            }
            n3 = this.postContext.matches(replaceable, (int[])arrobject, position.contextLimit, bl);
            if (n3 != 2) {
                return n3;
            }
        }
        n3 = arrobject[0];
        if ((this.flags & 2) != 0) {
            if (n3 != position.contextLimit) {
                return 0;
            }
            if (bl) {
                return 1;
            }
        }
        int n4 = this.output.replace(replaceable, position.start, n2, (int[])arrobject) - (n2 - position.start);
        n2 = arrobject[0];
        position.limit += n4;
        position.contextLimit += n4;
        position.start = Math.max(n, Math.min(Math.min(n3 + n4, position.limit), n2));
        return 2;
    }

    final boolean matchesIndexValue(int n) {
        StringMatcher stringMatcher = this.key;
        if (stringMatcher == null) {
            stringMatcher = this.postContext;
        }
        boolean bl = stringMatcher != null ? stringMatcher.matchesIndexValue(n) : true;
        return bl;
    }

    public String toRule(boolean bl) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        boolean bl2 = this.anteContext != null || this.postContext != null;
        if ((this.flags & 1) != 0) {
            stringBuffer.append('^');
        }
        Utility.appendToRule(stringBuffer, this.anteContext, bl, stringBuffer2);
        if (bl2) {
            Utility.appendToRule(stringBuffer, 123, true, bl, stringBuffer2);
        }
        Utility.appendToRule(stringBuffer, this.key, bl, stringBuffer2);
        if (bl2) {
            Utility.appendToRule(stringBuffer, 125, true, bl, stringBuffer2);
        }
        Utility.appendToRule(stringBuffer, this.postContext, bl, stringBuffer2);
        if ((this.flags & 2) != 0) {
            stringBuffer.append('$');
        }
        Utility.appendToRule(stringBuffer, " > ", true, bl, stringBuffer2);
        Utility.appendToRule(stringBuffer, this.output.toReplacerPattern(bl), true, bl, stringBuffer2);
        Utility.appendToRule(stringBuffer, 59, true, bl, stringBuffer2);
        return stringBuffer.toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        stringBuilder.append(this.toRule(true));
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

