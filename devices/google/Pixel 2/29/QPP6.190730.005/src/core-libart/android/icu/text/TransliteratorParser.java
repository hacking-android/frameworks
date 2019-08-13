/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.IllegalIcuArgumentException;
import android.icu.impl.PatternProps;
import android.icu.impl.Utility;
import android.icu.lang.UCharacter;
import android.icu.text.FunctionReplacer;
import android.icu.text.Normalizer;
import android.icu.text.Quantifier;
import android.icu.text.RuleBasedTransliterator;
import android.icu.text.StringMatcher;
import android.icu.text.StringReplacer;
import android.icu.text.SymbolTable;
import android.icu.text.TransliterationRule;
import android.icu.text.TransliterationRuleSet;
import android.icu.text.Transliterator;
import android.icu.text.TransliteratorIDParser;
import android.icu.text.UTF16;
import android.icu.text.UnicodeMatcher;
import android.icu.text.UnicodeReplacer;
import android.icu.text.UnicodeSet;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TransliteratorParser {
    private static final char ALT_FORWARD_RULE_OP = '\u2192';
    private static final char ALT_FUNCTION = '\u2206';
    private static final char ALT_FWDREV_RULE_OP = '\u2194';
    private static final char ALT_REVERSE_RULE_OP = '\u2190';
    private static final char ANCHOR_START = '^';
    private static final char CONTEXT_ANTE = '{';
    private static final char CONTEXT_POST = '}';
    private static final char CURSOR_OFFSET = '@';
    private static final char CURSOR_POS = '|';
    private static final char DOT = '.';
    private static final String DOT_SET = "[^[:Zp:][:Zl:]\\r\\n$]";
    private static final char END_OF_RULE = ';';
    private static final char ESCAPE = '\\';
    private static final char FORWARD_RULE_OP = '>';
    private static final char FUNCTION = '&';
    private static final char FWDREV_RULE_OP = '~';
    private static final String HALF_ENDERS = "=><\u2190\u2192\u2194;";
    private static final String ID_TOKEN = "::";
    private static final int ID_TOKEN_LEN = 2;
    private static UnicodeSet ILLEGAL_FUNC;
    private static UnicodeSet ILLEGAL_SEG;
    private static UnicodeSet ILLEGAL_TOP;
    private static final char KLEENE_STAR = '*';
    private static final char ONE_OR_MORE = '+';
    private static final String OPERATORS = "=><\u2190\u2192\u2194";
    private static final char QUOTE = '\'';
    private static final char REVERSE_RULE_OP = '<';
    private static final char RULE_COMMENT_CHAR = '#';
    private static final char SEGMENT_CLOSE = ')';
    private static final char SEGMENT_OPEN = '(';
    private static final char VARIABLE_DEF_OP = '=';
    private static final char ZERO_OR_ONE = '?';
    public UnicodeSet compoundFilter;
    private RuleBasedTransliterator.Data curData;
    public List<RuleBasedTransliterator.Data> dataVector;
    private int direction;
    private int dotStandIn = -1;
    public List<String> idBlockVector;
    private ParseData parseData;
    private List<StringMatcher> segmentObjects;
    private StringBuffer segmentStandins;
    private String undefinedVariableName;
    private char variableLimit;
    private Map<String, char[]> variableNames;
    private char variableNext;
    private List<Object> variablesVector;

    static {
        ILLEGAL_TOP = new UnicodeSet("[\\)]");
        ILLEGAL_SEG = new UnicodeSet("[\\{\\}\\|\\@]");
        ILLEGAL_FUNC = new UnicodeSet("[\\^\\(\\.\\*\\+\\?\\{\\}\\|\\@]");
    }

    static /* synthetic */ RuleBasedTransliterator.Data access$100(TransliteratorParser transliteratorParser) {
        return transliteratorParser.curData;
    }

    static /* synthetic */ void access$1000(TransliteratorParser transliteratorParser, String string, StringBuffer stringBuffer) {
        transliteratorParser.appendVariableDef(string, stringBuffer);
    }

    static /* synthetic */ char access$500(TransliteratorParser transliteratorParser, String string, ParsePosition parsePosition) {
        return transliteratorParser.parseSet(string, parsePosition);
    }

    static /* synthetic */ void access$600(TransliteratorParser transliteratorParser, int n, String string, int n2) {
        transliteratorParser.checkVariableRange(n, string, n2);
    }

    static /* synthetic */ UnicodeSet access$700() {
        return ILLEGAL_SEG;
    }

    static /* synthetic */ UnicodeSet access$800() {
        return ILLEGAL_FUNC;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void appendVariableDef(String string, StringBuffer abstractStringBuilder) {
        char[] arrc = this.variableNames.get(string);
        if (arrc != null) {
            ((StringBuffer)abstractStringBuilder).append(arrc);
            return;
        }
        if (this.undefinedVariableName == null) {
            char c;
            this.undefinedVariableName = string;
            char c2 = this.variableNext;
            char c3 = this.variableLimit;
            if (c2 >= c3) throw new RuntimeException("Private use variables exhausted");
            this.variableLimit = c = (char)(c3 - '\u0001');
            ((StringBuffer)abstractStringBuilder).append(c);
            return;
        }
        abstractStringBuilder = new StringBuilder();
        ((StringBuilder)abstractStringBuilder).append("Undefined variable $");
        ((StringBuilder)abstractStringBuilder).append(string);
        throw new IllegalIcuArgumentException(((StringBuilder)abstractStringBuilder).toString());
    }

    private void checkVariableRange(int n, String string, int n2) {
        if (n >= this.curData.variablesBase && n < this.variableLimit) {
            TransliteratorParser.syntaxError("Variable range character in rule", string, n2);
        }
    }

    private int parsePragma(String string, int n, int n2) {
        int[] arrn = new int[2];
        int n3 = Utility.parsePattern(string, n += 4, n2, "~variable range # #~;", arrn);
        if (n3 >= 0) {
            this.setVariableRange(arrn[0], arrn[1]);
            return n3;
        }
        n3 = Utility.parsePattern(string, n, n2, "~maximum backup #~;", arrn);
        if (n3 >= 0) {
            this.pragmaMaximumBackup(arrn[0]);
            return n3;
        }
        n3 = Utility.parsePattern(string, n, n2, "~nfd rules~;", null);
        if (n3 >= 0) {
            this.pragmaNormalizeRules(Normalizer.NFD);
            return n3;
        }
        if ((n = Utility.parsePattern(string, n, n2, "~nfc rules~;", null)) >= 0) {
            this.pragmaNormalizeRules(Normalizer.NFC);
            return n;
        }
        return -1;
    }

    private int parseRule(String arrobject, int n, int n2) {
        int n3;
        RuleHalf ruleHalf;
        int n4;
        int n5;
        int n6;
        Object object;
        RuleHalf ruleHalf2;
        block25 : {
            block24 : {
                int n7;
                n6 = 0;
                this.segmentStandins = new StringBuffer();
                this.segmentObjects = new ArrayList<StringMatcher>();
                ruleHalf = new RuleHalf();
                ruleHalf2 = new RuleHalf();
                this.undefinedVariableName = null;
                n3 = n4 = ruleHalf.parse((String)arrobject, n, n2, this);
                if (n4 == n2) break block24;
                n5 = n4 - 1;
                n6 = n7 = arrobject.charAt(n5);
                n3 = n6;
                n4 = n5;
                if (OPERATORS.indexOf(n7) >= 0) break block25;
                n3 = n5;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("No operator pos=");
            ((StringBuilder)object).append(n3);
            TransliteratorParser.syntaxError(((StringBuilder)object).toString(), (String)arrobject, n);
            n4 = n3;
            n3 = n6;
        }
        n5 = n4 + 1;
        n6 = n3;
        n4 = n5;
        if (n3 == 60) {
            n6 = n3;
            n4 = n5;
            if (n5 < n2) {
                n6 = n3;
                n4 = n5;
                if (arrobject.charAt(n5) == '>') {
                    n4 = n5 + 1;
                    n6 = 126;
                }
            }
        }
        n3 = n6 != 8592 ? (n6 != 8594 ? (n6 != 8596 ? n6 : 126) : 62) : 60;
        n6 = n4 = ruleHalf2.parse((String)arrobject, n4, n2, this);
        if (n4 < n2) {
            n6 = n4 - 1;
            if (arrobject.charAt(n6) == ';') {
                ++n6;
            } else {
                TransliteratorParser.syntaxError("Unquoted operator", (String)arrobject, n);
            }
        }
        if (n3 == 61) {
            if (this.undefinedVariableName == null) {
                TransliteratorParser.syntaxError("Missing '$' or duplicate definition", (String)arrobject, n);
            }
            if (ruleHalf.text.length() != 1 || ruleHalf.text.charAt(0) != this.variableLimit) {
                TransliteratorParser.syntaxError("Malformed LHS", (String)arrobject, n);
            }
            if (ruleHalf.anchorStart || ruleHalf.anchorEnd || ruleHalf2.anchorStart || ruleHalf2.anchorEnd) {
                TransliteratorParser.syntaxError("Malformed variable def", (String)arrobject, n);
            }
            n = ruleHalf2.text.length();
            arrobject = new char[n];
            ruleHalf2.text.getChars(0, n, (char[])arrobject, 0);
            this.variableNames.put(this.undefinedVariableName, (char[])arrobject);
            this.variableLimit = (char)(this.variableLimit + '\u0001');
            return n6;
        }
        if (this.undefinedVariableName != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Undefined variable $");
            ((StringBuilder)object).append(this.undefinedVariableName);
            TransliteratorParser.syntaxError(((StringBuilder)object).toString(), (String)arrobject, n);
        }
        if (this.segmentStandins.length() > this.segmentObjects.size()) {
            TransliteratorParser.syntaxError("Undefined segment reference", (String)arrobject, n);
        }
        for (n2 = 0; n2 < this.segmentStandins.length(); ++n2) {
            if (this.segmentStandins.charAt(n2) != '\u0000') continue;
            TransliteratorParser.syntaxError("Internal error", (String)arrobject, n);
        }
        for (n2 = 0; n2 < this.segmentObjects.size(); ++n2) {
            if (this.segmentObjects.get(n2) != null) continue;
            TransliteratorParser.syntaxError("Internal error", (String)arrobject, n);
        }
        if (n3 != 126 && (n2 = this.direction == 0 ? 1 : 0) != (n4 = n3 == 62 ? 1 : 0)) {
            return n6;
        }
        RuleHalf ruleHalf3 = ruleHalf;
        object = ruleHalf2;
        if (this.direction == 1) {
            ruleHalf3 = ruleHalf2;
            object = ruleHalf;
        }
        if (n3 == 126) {
            ((RuleHalf)object).removeContext();
            ruleHalf3.cursor = -1;
            ruleHalf3.cursorOffset = 0;
        }
        if (ruleHalf3.ante < 0) {
            ruleHalf3.ante = 0;
        }
        if (ruleHalf3.post < 0) {
            ruleHalf3.post = ruleHalf3.text.length();
        }
        if (((RuleHalf)object).ante >= 0 || ((RuleHalf)object).post >= 0 || ruleHalf3.cursor >= 0 || ((RuleHalf)object).cursorOffset != 0 && ((RuleHalf)object).cursor < 0 || ((RuleHalf)object).anchorStart || ((RuleHalf)object).anchorEnd || !ruleHalf3.isValidInput(this) || !((RuleHalf)object).isValidOutput(this) || ruleHalf3.ante > ruleHalf3.post) {
            TransliteratorParser.syntaxError("Malformed rule", (String)arrobject, n);
        }
        arrobject = null;
        if (this.segmentObjects.size() > 0) {
            arrobject = new UnicodeMatcher[this.segmentObjects.size()];
            this.segmentObjects.toArray(arrobject);
        }
        this.curData.ruleSet.addRule(new TransliterationRule(ruleHalf3.text, ruleHalf3.ante, ruleHalf3.post, ((RuleHalf)object).text, ((RuleHalf)object).cursor, ((RuleHalf)object).cursorOffset, (UnicodeMatcher[])arrobject, ruleHalf3.anchorStart, ruleHalf3.anchorEnd, this.curData));
        return n6;
    }

    private final char parseSet(String object, ParsePosition parsePosition) {
        object = new UnicodeSet((String)object, parsePosition, this.parseData);
        if (this.variableNext < this.variableLimit) {
            ((UnicodeSet)object).compact();
            return this.generateStandInFor(object);
        }
        throw new RuntimeException("Private use variables exhausted");
    }

    private void pragmaMaximumBackup(int n) {
        throw new IllegalIcuArgumentException("use maximum backup pragma not implemented yet");
    }

    private void pragmaNormalizeRules(Normalizer.Mode mode) {
        throw new IllegalIcuArgumentException("use normalize rules pragma not implemented yet");
    }

    static boolean resemblesPragma(String string, int n, int n2) {
        boolean bl = Utility.parsePattern(string, n, n2, "use ", null) >= 0;
        return bl;
    }

    static final int ruleEnd(String string, int n, int n2) {
        int n3;
        n = n3 = Utility.quotedIndexOf(string, n, n2, ";");
        if (n3 < 0) {
            n = n2;
        }
        return n;
    }

    private void setVariableRange(int n, int n2) {
        if (n <= n2 && n >= 0 && n2 <= 65535) {
            this.curData.variablesBase = (char)n;
            if (this.dataVector.size() == 0) {
                this.variableNext = (char)n;
                this.variableLimit = (char)(n2 + 1);
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid variable range ");
        stringBuilder.append(n);
        stringBuilder.append(", ");
        stringBuilder.append(n2);
        throw new IllegalIcuArgumentException(stringBuilder.toString());
    }

    static final void syntaxError(String string, String string2, int n) {
        int n2 = TransliteratorParser.ruleEnd(string2, n, string2.length());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(" in \"");
        stringBuilder.append(Utility.escape(string2.substring(n, n2)));
        stringBuilder.append('\"');
        throw new IllegalIcuArgumentException(stringBuilder.toString());
    }

    char generateStandInFor(Object object) {
        for (int i = 0; i < this.variablesVector.size(); ++i) {
            if (this.variablesVector.get(i) != object) continue;
            return (char)(this.curData.variablesBase + i);
        }
        if (this.variableNext < this.variableLimit) {
            this.variablesVector.add(object);
            char c = this.variableNext;
            this.variableNext = (char)(c + '\u0001');
            return c;
        }
        throw new RuntimeException("Variable range exhausted");
    }

    char getDotStandIn() {
        if (this.dotStandIn == -1) {
            this.dotStandIn = this.generateStandInFor(new UnicodeSet(DOT_SET));
        }
        return (char)this.dotStandIn;
    }

    public char getSegmentStandin(int n) {
        char c;
        if (this.segmentStandins.length() < n) {
            this.segmentStandins.setLength(n);
        }
        char c2 = c = this.segmentStandins.charAt(n - 1);
        if (c == '\u0000') {
            c = this.variableNext;
            if (c < this.variableLimit) {
                this.variableNext = (char)(c + '\u0001');
                c2 = c;
                this.variablesVector.add(null);
                this.segmentStandins.setCharAt(n - 1, c2);
            } else {
                throw new RuntimeException("Variable range exhausted");
            }
        }
        return c2;
    }

    public void parse(String string, int n) {
        this.parseRules(new RuleArray(new String[]{string}), n);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    void parseRules(RuleBody var1_1, int var2_3) {
        var3_4 = 1;
        var4_5 = 0;
        this.dataVector = new ArrayList<RuleBasedTransliterator.Data>();
        this.idBlockVector = new ArrayList<String>();
        this.curData = null;
        this.direction = var2_3;
        this.compoundFilter = null;
        this.variablesVector = new ArrayList<Object>();
        this.variableNames = new HashMap<String, char[]>();
        this.parseData = new ParseData();
        var5_6 = new ArrayList<Object>();
        var6_7 = 0;
        var1_1.reset();
        var7_8 = new StringBuilder();
        this.compoundFilter = null;
        var8_9 = -1;
        var2_3 = var3_4;
        do {
            block51 : {
                var9_10 = var1_1.nextLine();
                var10_11 = 0;
                if (var9_10 != null) break block51;
                var6_7 = var2_3;
                ** GOTO lbl261
            }
            var3_4 = 0;
            var11_12 = var9_10.length();
            var12_13 = var6_7;
            var6_7 = var3_4;
            var13_14 = var4_5;
            while (var6_7 < var11_12) {
                block50 : {
                    block49 : {
                        block48 : {
                            block53 : {
                                block52 : {
                                    var4_5 = var6_7 + 1;
                                    if (PatternProps.isWhiteSpace(var6_7 = (int)var9_10.charAt(var6_7))) break block52;
                                    if (var6_7 == 35) {
                                        var6_7 = var9_10.indexOf("\n", var4_5) + 1;
                                        if (var6_7 != 0) continue;
                                        break;
                                    }
                                    if (var6_7 != 59) break block53;
                                }
                                var6_7 = var4_5;
                                continue;
                            }
                            ++var13_14;
                            var14_15 = var4_5 - 1;
                            if (var14_15 + 2 + 1 > var11_12) ** GOTO lbl50
                            var6_7 = var2_3;
                            var15_16 = var8_9;
                            var4_5 = var14_15;
                            try {
                                block56 : {
                                    block55 : {
                                        block54 : {
                                            if (var9_10.regionMatches(var14_15, "::", var10_11, 2)) break block54;
lbl50: // 2 sources:
                                            var3_4 = var2_3;
                                            if (var2_3 != 0) {
                                                var6_7 = var2_3;
                                                var15_16 = var8_9;
                                                var4_5 = var14_15;
                                                if (this.direction == 0) {
                                                    var6_7 = var2_3;
                                                    var15_16 = var8_9;
                                                    var4_5 = var14_15;
                                                    this.idBlockVector.add(var7_8.toString());
                                                } else {
                                                    var6_7 = var2_3;
                                                    var15_16 = var8_9;
                                                    var4_5 = var14_15;
                                                    this.idBlockVector.add(0, var7_8.toString());
                                                }
                                                var6_7 = var2_3;
                                                var15_16 = var8_9;
                                                var4_5 = var14_15;
                                                var7_8.delete(0, var7_8.length());
                                                var3_4 = 0;
                                                var6_7 = var2_3 = 0;
                                                var15_16 = var8_9;
                                                var4_5 = var14_15;
                                                var6_7 = var2_3;
                                                var15_16 = var8_9;
                                                var4_5 = var14_15;
                                                var16_17 /* !! */  = new RuleBasedTransliterator.Data();
                                                var6_7 = var2_3;
                                                var15_16 = var8_9;
                                                var4_5 = var14_15;
                                                this.curData = var16_17 /* !! */ ;
                                                var6_7 = var2_3;
                                                var15_16 = var8_9;
                                                var4_5 = var14_15;
                                                this.setVariableRange(61440, 63743);
                                            }
                                            var6_7 = var3_4;
                                            var15_16 = var8_9;
                                            var4_5 = var14_15;
                                            if (TransliteratorParser.resemblesPragma(var9_10, var14_15, var11_12)) {
                                                var6_7 = var3_4;
                                                var15_16 = var8_9;
                                                var4_5 = var14_15;
                                                var2_3 = this.parsePragma(var9_10, var14_15, var11_12);
                                                if (var2_3 < 0) {
                                                    var6_7 = var3_4;
                                                    var15_16 = var8_9;
                                                    var4_5 = var14_15;
                                                    TransliteratorParser.syntaxError("Unrecognized pragma", var9_10, var14_15);
                                                }
                                                var6_7 = var8_9;
                                            } else {
                                                var6_7 = var3_4;
                                                var15_16 = var8_9;
                                                var4_5 = var14_15;
                                                var2_3 = this.parseRule(var9_10, var14_15, var11_12);
                                                var6_7 = var8_9;
                                            }
                                            break block48;
                                        }
                                        var6_7 = var2_3;
                                        var15_16 = var8_9;
                                        var4_5 = var14_15 += 2;
                                        var3_4 = var9_10.charAt(var14_15);
                                        do {
                                            var6_7 = var2_3;
                                            var15_16 = var8_9;
                                            var4_5 = var14_15;
                                            if (!PatternProps.isWhiteSpace(var3_4) || var14_15 >= var11_12) break;
                                            var6_7 = var2_3;
                                            var15_16 = var8_9;
                                            var4_5 = ++var14_15;
                                            var3_4 = var9_10.charAt(var14_15);
                                        } while (true);
                                        var6_7 = var2_3;
                                        var15_16 = var8_9;
                                        var4_5 = var14_15;
                                        var16_17 /* !! */  = new int[1];
                                        var16_17 /* !! */ [var10_11] = var14_15;
                                        var3_4 = var2_3;
                                        if (var2_3 == 0) {
                                            var6_7 = var2_3;
                                            var15_16 = var8_9;
                                            var4_5 = var14_15;
                                            if (this.curData != null) {
                                                var6_7 = var2_3;
                                                var15_16 = var8_9;
                                                var4_5 = var14_15;
                                                if (this.direction == 0) {
                                                    var6_7 = var2_3;
                                                    var15_16 = var8_9;
                                                    var4_5 = var14_15;
                                                    this.dataVector.add(this.curData);
                                                } else {
                                                    var6_7 = var2_3;
                                                    var15_16 = var8_9;
                                                    var4_5 = var14_15;
                                                    this.dataVector.add(var10_11, this.curData);
                                                }
                                                var6_7 = var2_3;
                                                var15_16 = var8_9;
                                                var4_5 = var14_15;
                                                this.curData = null;
                                            }
                                            var3_4 = 1;
                                        }
                                        var6_7 = var3_4;
                                        var15_16 = var8_9;
                                        var4_5 = var14_15;
                                        var17_19 = TransliteratorIDParser.parseSingleID(var9_10, var16_17 /* !! */ , this.direction);
                                        if (var16_17 /* !! */ [var10_11] == var14_15) break block55;
                                        var6_7 = var3_4;
                                        var15_16 = var8_9;
                                        var4_5 = var14_15;
                                        if (!Utility.parseChar(var9_10, var16_17 /* !! */ , ';')) break block55;
                                        var6_7 = var3_4;
                                        var15_16 = var8_9;
                                        var4_5 = var14_15;
                                        if (this.direction == 0) {
                                            var6_7 = var3_4;
                                            var15_16 = var8_9;
                                            var4_5 = var14_15;
                                            var7_8.append(var17_19.canonID);
                                            var6_7 = var3_4;
                                            var15_16 = var8_9;
                                            var4_5 = var14_15;
                                            var7_8.append(';');
                                            var6_7 = var8_9;
                                        } else {
                                            var6_7 = var3_4;
                                            var15_16 = var8_9;
                                            var4_5 = var14_15;
                                            var6_7 = var3_4;
                                            var15_16 = var8_9;
                                            var4_5 = var14_15;
                                            var18_20 = new StringBuilder();
                                            var6_7 = var3_4;
                                            var15_16 = var8_9;
                                            var4_5 = var14_15;
                                            var18_20.append(var17_19.canonID);
                                            var6_7 = var3_4;
                                            var15_16 = var8_9;
                                            var4_5 = var14_15;
                                            var18_20.append(';');
                                            var6_7 = var3_4;
                                            var15_16 = var8_9;
                                            var4_5 = var14_15;
                                            var7_8.insert(0, var18_20.toString());
                                            var6_7 = var8_9;
                                        }
                                        break block56;
                                    }
                                    var6_7 = var3_4;
                                    var15_16 = var8_9;
                                    var4_5 = var14_15;
                                    var18_20 = new int[]{-1};
                                    var6_7 = var3_4;
                                    var15_16 = var8_9;
                                    var4_5 = var14_15;
                                    var17_19 = TransliteratorIDParser.parseGlobalFilter(var9_10, var16_17 /* !! */ , this.direction, (int[])var18_20, null);
                                    if (var17_19 == null) ** GOTO lbl-1000
                                    var6_7 = var3_4;
                                    var15_16 = var8_9;
                                    var4_5 = var14_15;
                                    if (Utility.parseChar(var9_10, var16_17 /* !! */ , ';')) {
                                        var6_7 = var3_4;
                                        var15_16 = var8_9;
                                        var4_5 = var14_15;
                                        var2_3 = this.direction == 0 ? 1 : 0;
                                        var4_5 = var18_20[0] == false ? 1 : 0;
                                        var6_7 = var8_9;
                                        if (var2_3 == var4_5) {
                                            var6_7 = var3_4;
                                            var15_16 = var8_9;
                                            var4_5 = var14_15;
                                            if (this.compoundFilter != null) {
                                                var6_7 = var3_4;
                                                var15_16 = var8_9;
                                                var4_5 = var14_15;
                                                TransliteratorParser.syntaxError("Multiple global filters", var9_10, var14_15);
                                            }
                                            var6_7 = var3_4;
                                            var15_16 = var8_9;
                                            var4_5 = var14_15;
                                            this.compoundFilter = var17_19;
                                            var6_7 = var13_14;
                                        }
                                    } else lbl-1000: // 2 sources:
                                    {
                                        var6_7 = var3_4;
                                        var15_16 = var8_9;
                                        var4_5 = var14_15;
                                        TransliteratorParser.syntaxError("Invalid ::ID", var9_10, var14_15);
                                        var6_7 = var8_9;
                                    }
                                }
                                var2_3 = var16_17 /* !! */ [0];
                            }
                            catch (IllegalArgumentException var16_18) {
                                break block49;
                            }
                        }
                        var4_5 = var2_3;
                        var2_3 = var3_4;
                        var8_9 = var6_7;
                        ** GOTO lbl308
                    }
                    if (var12_13 == 30) {
                        var1_1 = new IllegalIcuArgumentException("\nMore than 30 errors; further messages squelched");
                        var1_1.initCause(var16_18);
                        var5_6.add(var1_1);
                        var8_9 = var15_16;
                        var4_5 = var13_14;
lbl261: // 2 sources:
                        if (var6_7 != 0 && var7_8.length() > 0) {
                            if (this.direction == 0) {
                                this.idBlockVector.add(var7_8.toString());
                            } else {
                                this.idBlockVector.add(0, var7_8.toString());
                            }
                        } else if (var6_7 == 0 && (var1_1 = this.curData) != null) {
                            if (this.direction == 0) {
                                this.dataVector.add((RuleBasedTransliterator.Data)var1_1);
                            } else {
                                this.dataVector.add(0, (RuleBasedTransliterator.Data)var1_1);
                            }
                        }
                        for (var2_3 = 0; var2_3 < this.dataVector.size(); ++var2_3) {
                            var1_1 = this.dataVector.get(var2_3);
                            var1_1.variables = new Object[this.variablesVector.size()];
                            this.variablesVector.toArray(var1_1.variables);
                            var1_1.variableNames = new HashMap<String, char[]>();
                            var1_1.variableNames.putAll(this.variableNames);
                        }
                        this.variablesVector = null;
                        try {
                            if (this.compoundFilter != null && (this.direction == 0 && var8_9 != 1 || this.direction == 1 && var8_9 != var4_5)) {
                                var1_1 = new IllegalIcuArgumentException("Compound filters misplaced");
                                throw var1_1;
                            }
                            for (var2_3 = 0; var2_3 < this.dataVector.size(); ++var2_3) {
                                this.dataVector.get((int)var2_3).ruleSet.freeze();
                            }
                            if (this.idBlockVector.size() != 1 || this.idBlockVector.get(0).length() != 0) break block50;
                            this.idBlockVector.remove(0);
                        }
                        catch (IllegalArgumentException var1_2) {
                            var1_2.fillInStackTrace();
                            var5_6.add(var1_2);
                        }
                    } else {
                        var16_18.fillInStackTrace();
                        var5_6.add(var16_18);
                        ++var12_13;
                        var4_5 = TransliteratorParser.ruleEnd(var9_10, var4_5, var11_12) + 1;
                        var8_9 = var15_16;
                        var2_3 = var6_7;
lbl308: // 2 sources:
                        var10_11 = 0;
                        var6_7 = var4_5;
                        continue;
                    }
                }
                if (var5_6.size() == 0) return;
                var2_3 = var5_6.size() - 1;
                while (var2_3 > 0) {
                    var1_1 = (RuntimeException)var5_6.get(var2_3 - 1);
                    while (var1_1.getCause() != null) {
                        var1_1 = (RuntimeException)var1_1.getCause();
                    }
                    var1_1.initCause((Throwable)var5_6.get(var2_3));
                    --var2_3;
                }
                throw (RuntimeException)var5_6.get(0);
            }
            var6_7 = var12_13;
            var4_5 = var13_14;
        } while (true);
    }

    public void setSegmentObject(int n, StringMatcher stringMatcher) {
        while (this.segmentObjects.size() < n) {
            this.segmentObjects.add(null);
        }
        int n2 = this.getSegmentStandin(n) - this.curData.variablesBase;
        if (this.segmentObjects.get(n - 1) == null && this.variablesVector.get(n2) == null) {
            this.segmentObjects.set(n - 1, stringMatcher);
            this.variablesVector.set(n2, stringMatcher);
            return;
        }
        throw new RuntimeException();
    }

    private class ParseData
    implements SymbolTable {
        private ParseData() {
        }

        public boolean isMatcher(int n) {
            if ((n -= TransliteratorParser.access$100((TransliteratorParser)TransliteratorParser.this).variablesBase) >= 0 && n < TransliteratorParser.this.variablesVector.size()) {
                return TransliteratorParser.this.variablesVector.get(n) instanceof UnicodeMatcher;
            }
            return true;
        }

        public boolean isReplacer(int n) {
            if ((n -= TransliteratorParser.access$100((TransliteratorParser)TransliteratorParser.this).variablesBase) >= 0 && n < TransliteratorParser.this.variablesVector.size()) {
                return TransliteratorParser.this.variablesVector.get(n) instanceof UnicodeReplacer;
            }
            return true;
        }

        @Override
        public char[] lookup(String string) {
            return (char[])TransliteratorParser.this.variableNames.get(string);
        }

        @Override
        public UnicodeMatcher lookupMatcher(int n) {
            if ((n -= TransliteratorParser.access$100((TransliteratorParser)TransliteratorParser.this).variablesBase) >= 0 && n < TransliteratorParser.this.variablesVector.size()) {
                return (UnicodeMatcher)TransliteratorParser.this.variablesVector.get(n);
            }
            return null;
        }

        @Override
        public String parseReference(String string, ParsePosition parsePosition, int n) {
            int n2;
            int n3;
            for (n3 = n2 = parsePosition.getIndex(); n3 < n; ++n3) {
                char c = string.charAt(n3);
                if (n3 == n2 && !UCharacter.isUnicodeIdentifierStart(c) || !UCharacter.isUnicodeIdentifierPart(c)) break;
            }
            if (n3 == n2) {
                return null;
            }
            parsePosition.setIndex(n3);
            return string.substring(n2, n3);
        }
    }

    private static class RuleArray
    extends RuleBody {
        String[] array;
        int i;

        public RuleArray(String[] arrstring) {
            this.array = arrstring;
            this.i = 0;
        }

        @Override
        public String handleNextLine() {
            int n = this.i;
            Object object = this.array;
            if (n < ((String[])object).length) {
                this.i = n + 1;
                object = object[n];
            } else {
                object = null;
            }
            return object;
        }

        @Override
        public void reset() {
            this.i = 0;
        }
    }

    private static abstract class RuleBody {
        private RuleBody() {
        }

        abstract String handleNextLine();

        String nextLine() {
            CharSequence charSequence = this.handleNextLine();
            String string = charSequence;
            if (charSequence != null) {
                string = charSequence;
                if (((String)charSequence).length() > 0) {
                    string = charSequence;
                    if (((String)charSequence).charAt(((String)charSequence).length() - 1) == '\\') {
                        charSequence = new StringBuilder((String)charSequence);
                        do {
                            ((StringBuilder)charSequence).deleteCharAt(((StringBuilder)charSequence).length() - 1);
                            string = this.handleNextLine();
                            if (string == null) break;
                            ((StringBuilder)charSequence).append(string);
                        } while (string.length() > 0 && string.charAt(string.length() - 1) == '\\');
                        string = ((StringBuilder)charSequence).toString();
                    }
                }
            }
            return string;
        }

        abstract void reset();
    }

    private static class RuleHalf {
        public boolean anchorEnd = false;
        public boolean anchorStart = false;
        public int ante = -1;
        public int cursor = -1;
        public int cursorOffset = 0;
        private int cursorOffsetPos = 0;
        private int nextSegmentNumber = 1;
        public int post = -1;
        public String text;

        private RuleHalf() {
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private int parseSection(String var1_1, int var2_2, int var3_3, TransliteratorParser var4_4, StringBuffer var5_5, UnicodeSet var6_7, boolean var7_8) {
            var8_9 = new int[1];
            var9_13 = var5_5.length();
            var10_14 = null;
            var11_15 = -1;
            var12_16 = -1;
            var13_17 = -1;
            var14_18 = -1;
            var15_19 = var2_2;
            do {
                block70 : {
                    block72 : {
                        block71 : {
                            block69 : {
                                block63 : {
                                    block64 : {
                                        block65 : {
                                            block66 : {
                                                block67 : {
                                                    block68 : {
                                                        if (var15_19 >= var3_3) {
                                                            return var15_19;
                                                        }
                                                        var16_20 /* !! */  = var15_19 + 1;
                                                        var17_21 = var1_1.charAt(var15_19);
                                                        if (PatternProps.isWhiteSpace(var17_21)) {
                                                            var15_19 = var16_20 /* !! */ ;
                                                            continue;
                                                        }
                                                        if ("=><\u2190\u2192\u2194;".indexOf(var17_21) >= 0) {
                                                            if (var7_8 == false) return var16_20 /* !! */ ;
                                                            TransliteratorParser.syntaxError("Unclosed segment", var1_1, var2_2);
                                                            return var16_20 /* !! */ ;
                                                        }
                                                        if (this.anchorEnd) {
                                                            TransliteratorParser.syntaxError("Malformed variable reference", var1_1, var2_2);
                                                        }
                                                        if (UnicodeSet.resemblesPattern(var1_1, var16_20 /* !! */  - 1)) {
                                                            if (var10_14 == null) {
                                                                var10_14 = new ParsePosition(0);
                                                            }
                                                            var10_14.setIndex(var16_20 /* !! */  - 1);
                                                            var5_5.append(TransliteratorParser.access$500((TransliteratorParser)var4_4, var1_1, var10_14));
                                                            var15_19 = var10_14.getIndex();
                                                            continue;
                                                        }
                                                        if (var17_21 == '\\') {
                                                            if (var16_20 /* !! */  == var3_3) {
                                                                TransliteratorParser.syntaxError("Trailing backslash", var1_1, var2_2);
                                                            }
                                                            var8_10[0] = var16_20 /* !! */ ;
                                                            var16_20 /* !! */  = Utility.unescapeAt(var1_1, (int[])var8_10);
                                                            var15_19 = var8_10[0];
                                                            if (var16_20 /* !! */  == -1) {
                                                                TransliteratorParser.syntaxError("Malformed escape", var1_1, var2_2);
                                                            }
                                                            TransliteratorParser.access$600((TransliteratorParser)var4_4, var16_20 /* !! */ , var1_1, var2_2);
                                                            UTF16.append(var5_5, var16_20 /* !! */ );
                                                            continue;
                                                        }
                                                        if (var17_21 == '\'') {
                                                            var15_19 = var1_1.indexOf(39, var16_20 /* !! */ );
                                                            if (var15_19 == var16_20 /* !! */ ) {
                                                                var5_5.append(var17_21);
                                                                var15_19 = var16_20 /* !! */  + 1;
                                                                continue;
                                                            }
                                                            var11_15 = var5_5.length();
                                                            var12_16 = var16_20 /* !! */ ;
                                                            var16_20 /* !! */  = var15_19;
                                                            do {
                                                                if (var16_20 /* !! */  < 0) {
                                                                    TransliteratorParser.syntaxError("Unterminated quote", var1_1, var2_2);
                                                                }
                                                                var5_5.append(var1_1.substring(var12_16, var16_20 /* !! */ ));
                                                                var12_16 = var16_20 /* !! */  + 1;
                                                                if (var12_16 >= var3_3 || var1_1.charAt(var12_16) != '\'') break;
                                                                var16_20 /* !! */  = var1_1.indexOf(39, var12_16 + 1);
                                                            } while (true);
                                                            var18_22 = var5_5.length();
                                                            for (var16_20 /* !! */  = var11_15; var16_20 /* !! */  < var18_22; ++var16_20 /* !! */ ) {
                                                                TransliteratorParser.access$600((TransliteratorParser)var4_4, var5_5.charAt(var16_20 /* !! */ ), var1_1, var2_2);
                                                            }
                                                            var15_19 = var12_16;
                                                            var12_16 = var18_22;
                                                            continue;
                                                        }
                                                        TransliteratorParser.access$600((TransliteratorParser)var4_4, var17_21, var1_1, var2_2);
                                                        if (var6_7.contains(var17_21)) {
                                                            var19_23 = new StringBuilder();
                                                            var19_23.append("Illegal character '");
                                                            var19_23.append(var17_21);
                                                            var19_23.append('\'');
                                                            TransliteratorParser.syntaxError(var19_23.toString(), var1_1, var2_2);
                                                        }
                                                        if (var17_21 == '$') break block63;
                                                        if (var17_21 == '&') break block64;
                                                        if (var17_21 == '.') break block65;
                                                        if (var17_21 == '^') break block66;
                                                        if (var17_21 == '\u2206') break block64;
                                                        if (var17_21 == '?') break block67;
                                                        if (var17_21 == '@') break block68;
                                                        switch (var17_21) {
                                                            default: {
                                                                switch (var17_21) {
                                                                    default: {
                                                                        if (!(var17_21 < '!' || var17_21 > '~' || var17_21 >= '0' && var17_21 <= '9' || var17_21 >= 'A' && var17_21 <= 'Z' || var17_21 >= 'a' && var17_21 <= 'z')) {
                                                                            var19_23 = new StringBuilder();
                                                                            var19_23.append("Unquoted ");
                                                                            var19_23.append(var17_21);
                                                                            TransliteratorParser.syntaxError(var19_23.toString(), var1_1, var2_2);
                                                                        }
                                                                        var5_5.append(var17_21);
                                                                        var15_19 = var16_20 /* !! */ ;
                                                                        var16_20 /* !! */  = var12_16;
                                                                        var12_16 = var15_19;
                                                                        ** break;
                                                                    }
                                                                    case '}': {
                                                                        if (this.post >= 0) {
                                                                            TransliteratorParser.syntaxError("Multiple post contexts", var1_1, var2_2);
                                                                        }
                                                                        this.post = var5_5.length();
                                                                        var15_19 = var12_16;
                                                                        var12_16 = var16_20 /* !! */ ;
                                                                        var16_20 /* !! */  = var15_19;
                                                                        ** break;
                                                                    }
                                                                    case '|': {
                                                                        if (this.cursor >= 0) {
                                                                            TransliteratorParser.syntaxError("Multiple cursors", var1_1, var2_2);
                                                                        }
                                                                        this.cursor = var5_5.length();
                                                                        var15_19 = var16_20 /* !! */ ;
                                                                        var16_20 /* !! */  = var12_16;
                                                                        var12_16 = var15_19;
                                                                        ** break;
                                                                    }
                                                                    case '{': 
                                                                }
                                                                if (this.ante >= 0) {
                                                                    TransliteratorParser.syntaxError("Multiple ante contexts", var1_1, var2_2);
                                                                }
                                                                this.ante = var5_5.length();
                                                                var15_19 = var12_16;
                                                                var12_16 = var16_20 /* !! */ ;
                                                                var16_20 /* !! */  = var15_19;
                                                                ** break;
lbl127: // 4 sources:
                                                                break block69;
                                                            }
                                                            case '*': 
                                                            case '+': {
                                                                break block67;
                                                            }
                                                            case ')': {
                                                                return var16_20 /* !! */ ;
                                                            }
                                                            case '(': {
                                                                var18_22 = var5_5.length();
                                                                var20_24 = this.nextSegmentNumber;
                                                                this.nextSegmentNumber = var20_24 + 1;
                                                                var15_19 = this.parseSection(var1_1, var16_20 /* !! */ , var3_3, (TransliteratorParser)var4_4, var5_5, TransliteratorParser.access$700(), true);
                                                                var4_4.setSegmentObject(var20_24, new StringMatcher(var5_5.substring(var18_22), var20_24, TransliteratorParser.access$100((TransliteratorParser)var4_4)));
                                                                var5_5.setLength(var18_22);
                                                                var5_5.append(var4_4.getSegmentStandin(var20_24));
                                                                var21_25 = true;
                                                                var16_20 /* !! */  = var12_16;
                                                                ** break;
lbl144: // 1 sources:
                                                                break;
                                                            }
                                                        }
                                                        break block70;
                                                    }
                                                    var15_19 = this.cursorOffset;
                                                    if (var15_19 < 0) {
                                                        if (var5_5.length() > 0) {
                                                            var19_23 = new StringBuilder();
                                                            var19_23.append("Misplaced ");
                                                            var19_23.append(var17_21);
                                                            TransliteratorParser.syntaxError(var19_23.toString(), var1_1, var2_2);
                                                        }
                                                        --this.cursorOffset;
                                                        var15_19 = var12_16;
                                                        var12_16 = var16_20 /* !! */ ;
                                                        var16_20 /* !! */  = var15_19;
                                                    } else if (var15_19 > 0) {
                                                        if (var5_5.length() != this.cursorOffsetPos || this.cursor >= 0) {
                                                            var19_23 = new StringBuilder();
                                                            var19_23.append("Misplaced ");
                                                            var19_23.append(var17_21);
                                                            TransliteratorParser.syntaxError(var19_23.toString(), var1_1, var2_2);
                                                        }
                                                        ++this.cursorOffset;
                                                        var15_19 = var12_16;
                                                        var12_16 = var16_20 /* !! */ ;
                                                        var16_20 /* !! */  = var15_19;
                                                    } else if (this.cursor == 0 && var5_5.length() == 0) {
                                                        this.cursorOffset = -1;
                                                        var15_19 = var12_16;
                                                        var12_16 = var16_20 /* !! */ ;
                                                        var16_20 /* !! */  = var15_19;
                                                    } else if (this.cursor < 0) {
                                                        this.cursorOffsetPos = var5_5.length();
                                                        this.cursorOffset = 1;
                                                        var15_19 = var16_20 /* !! */ ;
                                                        var16_20 /* !! */  = var12_16;
                                                        var12_16 = var15_19;
                                                    } else {
                                                        var19_23 = new StringBuilder();
                                                        var19_23.append("Misplaced ");
                                                        var19_23.append(var17_21);
                                                        TransliteratorParser.syntaxError(var19_23.toString(), var1_1, var2_2);
                                                        var15_19 = var16_20 /* !! */ ;
                                                        var16_20 /* !! */  = var12_16;
                                                        var12_16 = var15_19;
                                                    }
                                                    break block69;
                                                }
                                                var22_26 = var17_21;
                                                if (var7_8 && var5_5.length() == var9_13) {
                                                    TransliteratorParser.syntaxError("Misplaced quantifier", var1_1, var2_2);
                                                    var15_19 = var16_20 /* !! */ ;
                                                    var16_20 /* !! */  = var12_16;
                                                    var12_16 = var15_19;
                                                } else {
                                                    block62 : {
                                                        if (var5_5.length() == var12_16) {
                                                            var15_19 = var11_15;
                                                            var18_22 = var12_16;
                                                        } else {
                                                            var18_22 = var5_5.length();
                                                            if (var18_22 == (var15_19 = var14_18)) {
                                                                var20_24 = var13_17;
                                                                var18_22 = var15_19;
                                                                var15_19 = var20_24;
                                                            } else {
                                                                var15_19 = var5_5.length() - 1;
                                                                var18_22 = var15_19 + 1;
                                                            }
                                                        }
                                                        var19_23 = new StringMatcher(var5_5.toString(), var15_19, var18_22, 0, TransliteratorParser.access$100((TransliteratorParser)var4_4));
                                                        var18_22 = 0;
                                                        var20_24 = Integer.MAX_VALUE;
                                                        if (var22_26 != '+') {
                                                            if (var22_26 == '?') {
                                                                var18_22 = 0;
                                                                var20_24 = 1;
                                                            }
                                                            break block62;
                                                        }
                                                        var18_22 = 1;
                                                    }
                                                    var19_23 = new Quantifier((UnicodeMatcher)var19_23, var18_22, var20_24);
                                                    var5_5.setLength(var15_19);
                                                    var5_5.append(var4_4.generateStandInFor(var19_23));
                                                    var15_19 = var12_16;
                                                    var12_16 = var16_20 /* !! */ ;
                                                    var16_20 /* !! */  = var15_19;
                                                }
                                                break block69;
                                                catch (RuntimeException var5_6) {
                                                    if (var16_20 /* !! */  < 50) {
                                                        var4_4 = var1_1.substring(0, var16_20 /* !! */ );
                                                    } else {
                                                        var4_4 = new StringBuilder();
                                                        var4_4.append("...");
                                                        var4_4.append(var1_1.substring(var16_20 /* !! */  - 50, var16_20 /* !! */ ));
                                                        var4_4 = var4_4.toString();
                                                    }
                                                    if (var3_3 - var16_20 /* !! */  <= 50) {
                                                        var1_1 = var1_1.substring(var16_20 /* !! */ , var3_3);
                                                    } else {
                                                        var6_7 = new StringBuilder();
                                                        var6_7.append(var1_1.substring(var16_20 /* !! */ , var16_20 /* !! */  + 50));
                                                        var6_7.append("...");
                                                        var1_1 = var6_7.toString();
                                                    }
                                                    var6_7 = new StringBuilder();
                                                    var6_7.append("Failure in rule: ");
                                                    var6_7.append((String)var4_4);
                                                    var6_7.append("$$$");
                                                    var6_7.append(var1_1);
                                                    throw new IllegalIcuArgumentException(var6_7.toString()).initCause(var5_6);
                                                }
                                            }
                                            if (var5_5.length() == 0 && !this.anchorStart) {
                                                this.anchorStart = true;
                                                var15_19 = var16_20 /* !! */ ;
                                                var16_20 /* !! */  = var12_16;
                                                var12_16 = var15_19;
                                            } else {
                                                TransliteratorParser.syntaxError("Misplaced anchor start", var1_1, var2_2);
                                                var15_19 = var16_20 /* !! */ ;
                                                var16_20 /* !! */  = var12_16;
                                                var12_16 = var15_19;
                                            }
                                            break block69;
                                        }
                                        var5_5.append(var4_4.getDotStandIn());
                                        var15_19 = var16_20 /* !! */ ;
                                        var16_20 /* !! */  = var12_16;
                                        var12_16 = var15_19;
                                        break block69;
                                    }
                                    var8_10[0] = var16_20 /* !! */ ;
                                    var19_23 = TransliteratorIDParser.parseFilterID(var1_1, (int[])var8_10);
                                    if (var19_23 == null || !Utility.parseChar(var1_1, (int[])var8_10, '(')) {
                                        TransliteratorParser.syntaxError("Invalid function", var1_1, var2_2);
                                    }
                                    if ((var19_23 = var19_23.getInstance()) == null) {
                                        TransliteratorParser.syntaxError("Invalid function ID", var1_1, var2_2);
                                    }
                                    var16_20 /* !! */  = var5_5.length();
                                    var15_19 = var8_10[0];
                                    var23_27 = TransliteratorParser.access$800();
                                    var15_19 = this.parseSection(var1_1, var15_19, var3_3, (TransliteratorParser)var4_4, var5_5, (UnicodeSet)var23_27, true);
                                    var19_23 = new FunctionReplacer((Transliterator)var19_23, new StringReplacer(var5_5.substring(var16_20 /* !! */ ), TransliteratorParser.access$100((TransliteratorParser)var4_4)));
                                    var5_5.setLength(var16_20 /* !! */ );
                                    var5_5.append(var4_4.generateStandInFor(var19_23));
                                    var21_25 = true;
                                    var16_20 /* !! */  = var12_16;
                                    break block70;
                                }
                                var19_23 = var8_10;
                                var15_19 = var16_20 /* !! */ ;
                                if (var15_19 != var3_3) break block71;
                                this.anchorEnd = true;
                                var8_11 = var19_23;
                                var16_20 /* !! */  = var12_16;
                                var12_16 = var15_19;
                            }
                            var21_25 = true;
                            var15_19 = var12_16;
                            break block70;
                        }
                        var16_20 /* !! */  = UCharacter.digit(var1_1.charAt(var15_19), 10);
                        if (var16_20 /* !! */  < 1 || var16_20 /* !! */  > 9) break block72;
                        var19_23[0] = var15_19;
                        var15_19 = Utility.parseNumber(var1_1, (int[])var19_23, 10);
                        if (var15_19 < 0) {
                            TransliteratorParser.syntaxError("Undefined segment reference", var1_1, var2_2);
                        }
                        var16_20 /* !! */  = (int)var19_23[0];
                        var5_5.append(var4_4.getSegmentStandin(var15_19));
                        ** GOTO lbl340
                    }
                    if (var10_14 == null) {
                        var10_14 = new ParsePosition(0);
                    }
                    var10_14.setIndex(var15_19);
                    var23_27 = TransliteratorParser.access$900((TransliteratorParser)var4_4).parseReference(var1_1, var10_14, var3_3);
                    if (var23_27 == null) {
                        var21_25 = true;
                        this.anchorEnd = true;
                        var8_12 = var19_23;
                        var16_20 /* !! */  = var12_16;
                    } else {
                        var16_20 /* !! */  = var10_14.getIndex();
                        var13_17 = var5_5.length();
                        TransliteratorParser.access$1000((TransliteratorParser)var4_4, (String)var23_27, var5_5);
                        var14_18 = var5_5.length();
lbl340: // 2 sources:
                        var21_25 = true;
                        var15_19 = var16_20 /* !! */ ;
                        var16_20 /* !! */  = var12_16;
                    }
                }
                var12_16 = var16_20 /* !! */ ;
            } while (true);
        }

        public boolean isValidInput(TransliteratorParser transliteratorParser) {
            int n;
            for (int i = 0; i < this.text.length(); i += UTF16.getCharCount((int)n)) {
                n = UTF16.charAt(this.text, i);
                if (transliteratorParser.parseData.isMatcher(n)) continue;
                return false;
            }
            return true;
        }

        public boolean isValidOutput(TransliteratorParser transliteratorParser) {
            int n;
            for (int i = 0; i < this.text.length(); i += UTF16.getCharCount((int)n)) {
                n = UTF16.charAt(this.text, i);
                if (transliteratorParser.parseData.isReplacer(n)) continue;
                return false;
            }
            return true;
        }

        public int parse(String string, int n, int n2, TransliteratorParser transliteratorParser) {
            StringBuffer stringBuffer = new StringBuffer();
            n2 = this.parseSection(string, n, n2, transliteratorParser, stringBuffer, ILLEGAL_TOP, false);
            this.text = stringBuffer.toString();
            if (this.cursorOffset > 0 && this.cursor != this.cursorOffsetPos) {
                TransliteratorParser.syntaxError("Misplaced |", string, n);
            }
            return n2;
        }

        void removeContext() {
            int n;
            int n2;
            String string = this.text;
            int n3 = n2 = this.ante;
            if (n2 < 0) {
                n3 = 0;
            }
            n2 = n = this.post;
            if (n < 0) {
                n2 = this.text.length();
            }
            this.text = string.substring(n3, n2);
            this.post = -1;
            this.ante = -1;
            this.anchorEnd = false;
            this.anchorStart = false;
        }
    }

}

