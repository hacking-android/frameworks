/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.Utility;
import android.icu.text.UTF16;
import android.icu.text.UnicodeSet;

public class PatternTokenizer {
    private static final int AFTER_QUOTE = -1;
    public static final char BACK_SLASH = '\\';
    public static final int BROKEN_ESCAPE = 4;
    public static final int BROKEN_QUOTE = 3;
    public static final int DONE = 0;
    private static final int HEX = 4;
    private static int IN_QUOTE = 0;
    public static final int LITERAL = 2;
    private static final int NONE = 0;
    private static final int NORMAL_QUOTE = 2;
    private static int NO_QUOTE = 0;
    public static final char SINGLE_QUOTE = '\'';
    private static final int SLASH_START = 3;
    private static final int START_QUOTE = 1;
    public static final int SYNTAX = 1;
    public static final int UNKNOWN = 5;
    private UnicodeSet escapeCharacters = new UnicodeSet();
    private UnicodeSet extraQuotingCharacters = new UnicodeSet();
    private UnicodeSet ignorableCharacters = new UnicodeSet();
    private int limit;
    private transient UnicodeSet needingQuoteCharacters = null;
    private String pattern;
    private int start;
    private UnicodeSet syntaxCharacters = new UnicodeSet();
    private boolean usingQuote = false;
    private boolean usingSlash = false;

    static {
        NO_QUOTE = -1;
        IN_QUOTE = -2;
    }

    private void appendEscaped(StringBuffer stringBuffer, int n) {
        if (n <= 65535) {
            stringBuffer.append("\\u");
            stringBuffer.append(Utility.hex(n, 4));
        } else {
            stringBuffer.append("\\U");
            stringBuffer.append(Utility.hex(n, 8));
        }
    }

    public UnicodeSet getEscapeCharacters() {
        return (UnicodeSet)this.escapeCharacters.clone();
    }

    public UnicodeSet getExtraQuotingCharacters() {
        return (UnicodeSet)this.extraQuotingCharacters.clone();
    }

    public UnicodeSet getIgnorableCharacters() {
        return (UnicodeSet)this.ignorableCharacters.clone();
    }

    public int getLimit() {
        return this.limit;
    }

    public int getStart() {
        return this.start;
    }

    public UnicodeSet getSyntaxCharacters() {
        return (UnicodeSet)this.syntaxCharacters.clone();
    }

    public boolean isUsingQuote() {
        return this.usingQuote;
    }

    public boolean isUsingSlash() {
        return this.usingSlash;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public int next(StringBuffer var1_1) {
        if (this.start >= this.limit) {
            return 0;
        }
        var2_2 = 5;
        var3_3 = 5;
        var4_4 = 0;
        var5_5 = 0;
        var6_6 = 0;
        for (var7_7 = this.start; var7_7 < (var8_8 = this.limit); var7_7 += UTF16.getCharCount((int)var9_9)) {
            block32 : {
                block28 : {
                    block29 : {
                        block30 : {
                            block33 : {
                                block34 : {
                                    block35 : {
                                        block31 : {
                                            var9_9 = UTF16.charAt(this.pattern, var7_7);
                                            if (var4_4 == -1) break block28;
                                            if (var4_4 == 1) break block29;
                                            if (var4_4 == 2) break block30;
                                            if (var4_4 == 3) break block31;
                                            if (var4_4 != 4) ** GOTO lbl109
                                            var6_6 = (var6_6 << 4) + var9_9;
                                            switch (var9_9) {
                                                default: {
                                                    switch (var9_9) {
                                                        default: {
                                                            switch (var9_9) {
                                                                default: {
                                                                    this.start = var7_7;
                                                                    return 4;
                                                                }
                                                                case 97: 
                                                                case 98: 
                                                                case 99: 
                                                                case 100: 
                                                                case 101: 
                                                                case 102: 
                                                            }
                                                            var8_8 = var6_6 - 87;
                                                            ** break;
                                                        }
                                                        case 65: 
                                                        case 66: 
                                                        case 67: 
                                                        case 68: 
                                                        case 69: 
                                                        case 70: 
                                                    }
                                                    var8_8 = var6_6 - 55;
                                                    ** break;
                                                }
                                                case 48: 
                                                case 49: 
                                                case 50: 
                                                case 51: 
                                                case 52: 
                                                case 53: 
                                                case 54: 
                                                case 55: 
                                                case 56: 
                                                case 57: 
                                            }
                                            var8_8 = var6_6 - 48;
lbl32: // 3 sources:
                                            var10_10 = var5_5 - 1;
                                            var11_11 = var2_2;
                                            var12_12 = var3_3;
                                            var5_5 = var10_10;
                                            var6_6 = var8_8;
                                            if (var10_10 == 0) {
                                                var4_4 = 0;
                                                UTF16.append(var1_1, var8_8);
                                                var11_11 = var2_2;
                                                var12_12 = var3_3;
                                                var5_5 = var10_10;
                                                var6_6 = var8_8;
                                            }
                                            break block32;
                                        }
                                        if (var9_9 == 85) break block33;
                                        if (var9_9 == 117) break block34;
                                        if (!this.usingSlash) break block35;
                                        UTF16.append(var1_1, var9_9);
                                        var4_4 = 0;
                                        var11_11 = var2_2;
                                        var12_12 = var3_3;
                                        break block32;
                                    }
                                    var1_1.append('\\');
                                    var4_4 = 0;
                                    ** GOTO lbl109
                                }
                                var4_4 = 4;
                                var5_5 = 4;
                                var6_6 = 0;
                                var11_11 = var2_2;
                                var12_12 = var3_3;
                                break block32;
                            }
                            var4_4 = 4;
                            var5_5 = 8;
                            var6_6 = 0;
                            var11_11 = var2_2;
                            var12_12 = var3_3;
                            break block32;
                        }
                        if (var9_9 == var3_3) {
                            var4_4 = -1;
                            var11_11 = var2_2;
                            var12_12 = var3_3;
                        } else {
                            UTF16.append(var1_1, var9_9);
                            var11_11 = var2_2;
                            var12_12 = var3_3;
                        }
                        break block32;
                    }
                    if (var9_9 == var3_3) {
                        UTF16.append(var1_1, var9_9);
                        var4_4 = 0;
                        var11_11 = var2_2;
                        var12_12 = var3_3;
                    } else {
                        UTF16.append(var1_1, var9_9);
                        var4_4 = 2;
                        var11_11 = var2_2;
                        var12_12 = var3_3;
                    }
                    break block32;
                }
                if (var9_9 == var3_3) {
                    UTF16.append(var1_1, var9_9);
                    var4_4 = 2;
                    var11_11 = var2_2;
                    var12_12 = var3_3;
                } else {
                    var4_4 = 0;
lbl109: // 3 sources:
                    if (this.ignorableCharacters.contains(var9_9)) {
                        var11_11 = var2_2;
                        var12_12 = var3_3;
                    } else {
                        if (this.syntaxCharacters.contains(var9_9)) {
                            if (var2_2 == 5) {
                                UTF16.append(var1_1, var9_9);
                                this.start = UTF16.getCharCount(var9_9) + var7_7;
                                return 1;
                            }
                            this.start = var7_7;
                            return var2_2;
                        }
                        var11_11 = 2;
                        if (var9_9 == 92) {
                            var4_4 = 3;
                            var12_12 = var3_3;
                        } else if (this.usingQuote && var9_9 == 39) {
                            var12_12 = var9_9;
                            var4_4 = 1;
                        } else {
                            UTF16.append(var1_1, var9_9);
                            var12_12 = var3_3;
                        }
                    }
                }
            }
            var2_2 = var11_11;
            var3_3 = var12_12;
        }
        this.start = var8_8;
        if (var4_4 == true) return 3;
        if (var4_4 == 2) return 3;
        if (var4_4 != 3) {
            if (var4_4 == 4) return 4;
            return var2_2;
        }
        if (this.usingSlash) {
            return 4;
        }
        var1_1.append('\\');
        return var2_2;
    }

    public String normalize() {
        int n = this.start;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        do {
            stringBuffer2.setLength(0);
            int n2 = this.next(stringBuffer2);
            if (n2 == 0) {
                this.start = n;
                return stringBuffer.toString();
            }
            if (n2 != 1) {
                stringBuffer.append(this.quoteLiteral(stringBuffer2));
                continue;
            }
            stringBuffer.append(stringBuffer2);
        } while (true);
    }

    public String quoteLiteral(CharSequence charSequence) {
        return this.quoteLiteral(charSequence.toString());
    }

    public String quoteLiteral(String string) {
        int n;
        if (this.needingQuoteCharacters == null) {
            this.needingQuoteCharacters = new UnicodeSet().addAll(this.syntaxCharacters).addAll(this.ignorableCharacters).addAll(this.extraQuotingCharacters);
            if (this.usingSlash) {
                this.needingQuoteCharacters.add(92);
            }
            if (this.usingQuote) {
                this.needingQuoteCharacters.add(39);
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        int n2 = NO_QUOTE;
        for (int i = 0; i < string.length(); i += UTF16.getCharCount((int)n)) {
            int n3;
            n = UTF16.charAt(string, i);
            if (this.escapeCharacters.contains(n)) {
                n3 = n2;
                if (n2 == IN_QUOTE) {
                    stringBuffer.append('\'');
                    n3 = NO_QUOTE;
                }
                this.appendEscaped(stringBuffer, n);
            } else if (this.needingQuoteCharacters.contains(n)) {
                if (n2 == IN_QUOTE) {
                    UTF16.append(stringBuffer, n);
                    n3 = n2;
                    if (this.usingQuote) {
                        n3 = n2;
                        if (n == 39) {
                            stringBuffer.append('\'');
                            n3 = n2;
                        }
                    }
                } else if (this.usingSlash) {
                    stringBuffer.append('\\');
                    UTF16.append(stringBuffer, n);
                    n3 = n2;
                } else if (this.usingQuote) {
                    if (n == 39) {
                        stringBuffer.append('\'');
                        stringBuffer.append('\'');
                        n3 = n2;
                    } else {
                        stringBuffer.append('\'');
                        UTF16.append(stringBuffer, n);
                        n3 = IN_QUOTE;
                    }
                } else {
                    this.appendEscaped(stringBuffer, n);
                    n3 = n2;
                }
            } else {
                n3 = n2;
                if (n2 == IN_QUOTE) {
                    stringBuffer.append('\'');
                    n3 = NO_QUOTE;
                }
                UTF16.append(stringBuffer, n);
            }
            n2 = n3;
        }
        if (n2 == IN_QUOTE) {
            stringBuffer.append('\'');
        }
        return stringBuffer.toString();
    }

    public PatternTokenizer setEscapeCharacters(UnicodeSet unicodeSet) {
        this.escapeCharacters = (UnicodeSet)unicodeSet.clone();
        return this;
    }

    public PatternTokenizer setExtraQuotingCharacters(UnicodeSet unicodeSet) {
        this.extraQuotingCharacters = (UnicodeSet)unicodeSet.clone();
        this.needingQuoteCharacters = null;
        return this;
    }

    public PatternTokenizer setIgnorableCharacters(UnicodeSet unicodeSet) {
        this.ignorableCharacters = (UnicodeSet)unicodeSet.clone();
        this.needingQuoteCharacters = null;
        return this;
    }

    public PatternTokenizer setLimit(int n) {
        this.limit = n;
        return this;
    }

    public PatternTokenizer setPattern(CharSequence charSequence) {
        return this.setPattern(charSequence.toString());
    }

    public PatternTokenizer setPattern(String string) {
        if (string != null) {
            this.start = 0;
            this.limit = string.length();
            this.pattern = string;
            return this;
        }
        throw new IllegalArgumentException("Inconsistent arguments");
    }

    public PatternTokenizer setStart(int n) {
        this.start = n;
        return this;
    }

    public PatternTokenizer setSyntaxCharacters(UnicodeSet unicodeSet) {
        this.syntaxCharacters = (UnicodeSet)unicodeSet.clone();
        this.needingQuoteCharacters = null;
        return this;
    }

    public PatternTokenizer setUsingQuote(boolean bl) {
        this.usingQuote = bl;
        this.needingQuoteCharacters = null;
        return this;
    }

    public PatternTokenizer setUsingSlash(boolean bl) {
        this.usingSlash = bl;
        this.needingQuoteCharacters = null;
        return this;
    }
}

