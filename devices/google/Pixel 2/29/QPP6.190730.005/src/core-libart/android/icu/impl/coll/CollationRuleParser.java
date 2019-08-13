/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.impl.IllegalIcuArgumentException;
import android.icu.impl.PatternProps;
import android.icu.impl.coll.CollationData;
import android.icu.impl.coll.CollationSettings;
import android.icu.lang.UCharacter;
import android.icu.text.Normalizer2;
import android.icu.text.UTF16;
import android.icu.text.UnicodeSet;
import android.icu.util.ULocale;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

public final class CollationRuleParser {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String BEFORE = "[before";
    private static final int OFFSET_SHIFT = 8;
    static final Position[] POSITION_VALUES = Position.values();
    static final char POS_BASE = '\u2800';
    static final char POS_LEAD = '\ufffe';
    private static final int STARRED_FLAG = 16;
    private static final int STRENGTH_MASK = 15;
    private static final int UCOL_DEFAULT = -1;
    private static final int UCOL_OFF = 0;
    private static final int UCOL_ON = 1;
    private static final int U_PARSE_CONTEXT_LEN = 16;
    private static final String[] gSpecialReorderCodes;
    private static final String[] positions;
    private final CollationData baseData;
    private Importer importer;
    private Normalizer2 nfc = Normalizer2.getNFCInstance();
    private Normalizer2 nfd = Normalizer2.getNFDInstance();
    private final StringBuilder rawBuilder = new StringBuilder();
    private int ruleIndex;
    private String rules;
    private CollationSettings settings;
    private Sink sink;

    static {
        positions = new String[]{"first tertiary ignorable", "last tertiary ignorable", "first secondary ignorable", "last secondary ignorable", "first primary ignorable", "last primary ignorable", "first variable", "last variable", "first regular", "last regular", "first implicit", "last implicit", "first trailing", "last trailing"};
        gSpecialReorderCodes = new String[]{"space", "punct", "symbol", "currency", "digit"};
    }

    CollationRuleParser(CollationData collationData) {
        this.baseData = collationData;
    }

    private String appendErrorContext(String charSequence) {
        int n;
        charSequence = new StringBuilder((String)charSequence);
        ((StringBuilder)charSequence).append(" at index ");
        ((StringBuilder)charSequence).append(this.ruleIndex);
        ((StringBuilder)charSequence).append(" near \"");
        int n2 = this.ruleIndex - 15;
        if (n2 < 0) {
            n = 0;
        } else {
            n = n2;
            if (n2 > 0) {
                n = n2;
                if (Character.isLowSurrogate(this.rules.charAt(n2))) {
                    n = n2 + 1;
                }
            }
        }
        ((StringBuilder)charSequence).append(this.rules, n, this.ruleIndex);
        ((StringBuilder)charSequence).append('!');
        n = this.rules.length();
        int n3 = this.ruleIndex;
        n = n2 = n - n3;
        if (n2 >= 16) {
            n = 15;
            if (Character.isHighSurrogate(this.rules.charAt(n3 + 15 - 1))) {
                n = 15 - 1;
            }
        }
        String string = this.rules;
        n2 = this.ruleIndex;
        ((StringBuilder)charSequence).append(string, n2, n2 + n);
        ((StringBuilder)charSequence).append('\"');
        return ((StringBuilder)charSequence).toString();
    }

    private static int getOnOffValue(String string) {
        if (string.equals("on")) {
            return 1;
        }
        if (string.equals("off")) {
            return 0;
        }
        return -1;
    }

    public static int getReorderCode(String string) {
        int n;
        String[] arrstring;
        for (n = 0; n < (arrstring = gSpecialReorderCodes).length; ++n) {
            if (!string.equalsIgnoreCase(arrstring[n])) continue;
            return n + 4096;
        }
        try {
            n = UCharacter.getPropertyValueEnum(4106, string);
            if (n >= 0) {
                return n;
            }
        }
        catch (IllegalIcuArgumentException illegalIcuArgumentException) {
            // empty catch block
        }
        if (string.equalsIgnoreCase("others")) {
            return 103;
        }
        return -1;
    }

    private static final boolean isSurrogate(int n) {
        boolean bl = (n & -2048) == 55296;
        return bl;
    }

    private static boolean isSyntaxChar(int n) {
        boolean bl = 33 <= n && n <= 126 && (n <= 47 || 58 <= n && n <= 64 || 91 <= n && n <= 96 || 123 <= n);
        return bl;
    }

    private ParseException makeParseException(String string) {
        return new ParseException(this.appendErrorContext(string), this.ruleIndex);
    }

    private void parse(String string) throws ParseException {
        this.rules = string;
        this.ruleIndex = 0;
        while (this.ruleIndex < this.rules.length()) {
            char c = this.rules.charAt(this.ruleIndex);
            if (PatternProps.isWhiteSpace(c)) {
                ++this.ruleIndex;
                continue;
            }
            if (c != '!') {
                if (c != '#') {
                    if (c != '&') {
                        if (c != '@') {
                            if (c != '[') {
                                this.setParseError("expected a reset or setting or comment");
                                continue;
                            }
                            this.parseSetting();
                            continue;
                        }
                        this.settings.setFlag(2048, true);
                        ++this.ruleIndex;
                        continue;
                    }
                    this.parseRuleChain();
                    continue;
                }
                this.ruleIndex = this.skipComment(this.ruleIndex + 1);
                continue;
            }
            ++this.ruleIndex;
        }
    }

    private int parseRelationOperator() {
        int n;
        int n2;
        block17 : {
            int n3;
            block16 : {
                this.ruleIndex = this.skipWhiteSpace(this.ruleIndex);
                if (this.ruleIndex >= this.rules.length()) {
                    return -1;
                }
                n = this.ruleIndex;
                String string = this.rules;
                n3 = n + 1;
                if ((n = (int)string.charAt(n)) == 44) break block16;
                switch (n) {
                    default: {
                        return -1;
                    }
                    case 61: {
                        int n4;
                        n = n4 = 15;
                        n2 = n3;
                        if (n3 < this.rules.length()) {
                            n = n4;
                            n2 = n3;
                            if (this.rules.charAt(n3) == '*') {
                                n2 = n3 + 1;
                                n = 15 | 16;
                                break;
                            }
                        }
                        break block17;
                    }
                    case 60: {
                        int n5;
                        if (n3 < this.rules.length() && this.rules.charAt(n3) == '<') {
                            if (++n3 < this.rules.length() && this.rules.charAt(n3) == '<') {
                                if (++n3 < this.rules.length() && this.rules.charAt(n3) == '<') {
                                    ++n3;
                                    n5 = 3;
                                } else {
                                    n5 = 2;
                                }
                            } else {
                                n5 = 1;
                            }
                        } else {
                            n5 = 0;
                        }
                        n = n5;
                        n2 = n3;
                        if (n3 < this.rules.length()) {
                            n = n5;
                            n2 = n3;
                            if (this.rules.charAt(n3) == '*') {
                                n2 = n3 + 1;
                                n = n5 | 16;
                                break;
                            }
                        }
                        break block17;
                    }
                    case 59: {
                        n = 1;
                        n2 = n3;
                        break;
                    }
                }
                break block17;
            }
            n = 2;
            n2 = n3;
        }
        return n2 - this.ruleIndex << 8 | n;
    }

    private void parseRelationStrings(int n, int n2) throws ParseException {
        String string = "";
        CharSequence charSequence = "";
        int n3 = this.parseTailoringString(n2, this.rawBuilder);
        int n4 = n3 < this.rules.length() ? this.rules.charAt(n3) : 0;
        int n5 = n4;
        n2 = n3;
        if (n4 == 124) {
            string = this.rawBuilder.toString();
            n4 = this.parseTailoringString(n3 + 1, this.rawBuilder);
            n2 = n4 < this.rules.length() ? (int)this.rules.charAt(n4) : 0;
            n5 = n2;
            n2 = n4;
        }
        n4 = n2;
        if (n5 == 47) {
            charSequence = new StringBuilder();
            n4 = this.parseTailoringString(n2 + 1, (StringBuilder)charSequence);
        }
        if (string.length() != 0) {
            n5 = string.codePointAt(0);
            n2 = this.rawBuilder.codePointAt(0);
            if (!this.nfc.hasBoundaryBefore(n5) || !this.nfc.hasBoundaryBefore(n2)) {
                this.setParseError("in 'prefix|str', prefix and str must each start with an NFC boundary");
                return;
            }
        }
        try {
            this.sink.addRelation(n, string, this.rawBuilder, charSequence);
            this.ruleIndex = n4;
            return;
        }
        catch (Exception exception) {
            this.setParseError("adding relation failed", exception);
            return;
        }
    }

    private void parseReordering(CharSequence arrn) throws ParseException {
        int n = 7;
        if (7 == arrn.length()) {
            this.settings.resetReordering();
            return;
        }
        Object object = new ArrayList();
        while (n < arrn.length()) {
            int n2;
            for (n = n2 = n + 1; n < arrn.length() && arrn.charAt(n) != ' '; ++n) {
            }
            if ((n2 = CollationRuleParser.getReorderCode(arrn.subSequence(n2, n).toString())) < 0) {
                this.setParseError("unknown script or reorder code");
                return;
            }
            ((ArrayList)object).add(n2);
        }
        if (((ArrayList)object).isEmpty()) {
            this.settings.resetReordering();
        } else {
            arrn = new int[((ArrayList)object).size()];
            n = 0;
            object = ((ArrayList)object).iterator();
            while (object.hasNext()) {
                arrn[n] = (Integer)object.next();
                ++n;
            }
            this.settings.setReordering(this.baseData, arrn);
        }
    }

    private int parseResetAndPosition() throws ParseException {
        int n;
        int n2;
        int n3 = this.skipWhiteSpace(this.ruleIndex + 1);
        if (this.rules.regionMatches(n3, BEFORE, 0, BEFORE.length()) && (n = BEFORE.length() + n3) < this.rules.length() && PatternProps.isWhiteSpace(this.rules.charAt(n)) && (n2 = this.skipWhiteSpace(n + 1)) + 1 < this.rules.length() && 49 <= (n = (int)this.rules.charAt(n2)) && n <= 51 && this.rules.charAt(n2 + 1) == ']') {
            n = n - 49 + 0;
            n3 = this.skipWhiteSpace(n2 + 2);
        } else {
            n = 15;
        }
        if (n3 >= this.rules.length()) {
            this.setParseError("reset without position");
            return -1;
        }
        n3 = this.rules.charAt(n3) == '[' ? this.parseSpecialPosition(n3, this.rawBuilder) : this.parseTailoringString(n3, this.rawBuilder);
        try {
            this.sink.addReset(n, this.rawBuilder);
            this.ruleIndex = n3;
            return n;
        }
        catch (Exception exception) {
            this.setParseError("adding reset failed", exception);
            return -1;
        }
    }

    private void parseRuleChain() throws ParseException {
        int n = this.parseResetAndPosition();
        int n2 = 1;
        do {
            int n3;
            if ((n3 = this.parseRelationOperator()) < 0) {
                if (this.ruleIndex < this.rules.length() && this.rules.charAt(this.ruleIndex) == '#') {
                    this.ruleIndex = this.skipComment(this.ruleIndex + 1);
                    continue;
                }
                if (n2 != 0) {
                    this.setParseError("reset not followed by a relation");
                }
                return;
            }
            int n4 = n3 & 15;
            if (n < 15) {
                if (n2 != 0) {
                    if (n4 != n) {
                        this.setParseError("reset-before strength differs from its first relation");
                        return;
                    }
                } else if (n4 < n) {
                    this.setParseError("reset-before strength followed by a stronger relation");
                    return;
                }
            }
            n2 = this.ruleIndex + (n3 >> 8);
            if ((n3 & 16) == 0) {
                this.parseRelationStrings(n4, n2);
            } else {
                this.parseStarredCharacters(n4, n2);
            }
            n2 = 0;
        } while (true);
    }

    private void parseSetting() throws ParseException {
        block51 : {
            Object object;
            int n;
            block49 : {
                block59 : {
                    Object object2;
                    int n2;
                    Importer importer;
                    block48 : {
                        block58 : {
                            block57 : {
                                boolean bl;
                                boolean bl2;
                                block56 : {
                                    boolean bl3;
                                    block55 : {
                                        boolean bl4;
                                        block54 : {
                                            block53 : {
                                                block52 : {
                                                    block50 : {
                                                        n = this.ruleIndex;
                                                        bl4 = true;
                                                        bl2 = true;
                                                        bl = true;
                                                        n2 = n + 1;
                                                        n = this.readWords(n2, this.rawBuilder);
                                                        if (n <= n2 || this.rawBuilder.length() == 0) {
                                                            this.setParseError("expected a setting/option at '['");
                                                        }
                                                        object = this.rawBuilder.toString();
                                                        if (this.rules.charAt(n) != ']') break block49;
                                                        n2 = n + 1;
                                                        if (((String)object).startsWith("reorder") && (((String)object).length() == 7 || ((String)object).charAt(7) == ' ')) {
                                                            this.parseReordering((CharSequence)object);
                                                            this.ruleIndex = n2;
                                                            return;
                                                        }
                                                        if (((String)object).equals("backwards 2")) {
                                                            this.settings.setFlag(2048, true);
                                                            this.ruleIndex = n2;
                                                            return;
                                                        }
                                                        n = ((String)object).lastIndexOf(32);
                                                        bl3 = false;
                                                        if (n >= 0) {
                                                            object2 = ((String)object).substring(n + 1);
                                                            object = ((String)object).substring(0, n);
                                                        } else {
                                                            object2 = "";
                                                        }
                                                        if (!((String)object).equals("strength") || ((String)object2).length() != 1) break block50;
                                                        n = -1;
                                                        char c = ((String)object2).charAt(0);
                                                        if ('1' <= c && c <= '4') {
                                                            n = c - 49 + 0;
                                                        } else if (c == 'I') {
                                                            n = 15;
                                                        }
                                                        if (n != -1) {
                                                            this.settings.setStrength(n);
                                                            this.ruleIndex = n2;
                                                            return;
                                                        }
                                                        break block51;
                                                    }
                                                    if (!((String)object).equals("alternate")) break block52;
                                                    n = -1;
                                                    if (((String)object2).equals("non-ignorable")) {
                                                        n = 0;
                                                    } else if (((String)object2).equals("shifted")) {
                                                        n = 1;
                                                    }
                                                    if (n != -1) {
                                                        object2 = this.settings;
                                                        if (n <= 0) {
                                                            bl = false;
                                                        }
                                                        ((CollationSettings)object2).setAlternateHandlingShifted(bl);
                                                        this.ruleIndex = n2;
                                                        return;
                                                    }
                                                    break block51;
                                                }
                                                if (!((String)object).equals("maxVariable")) break block53;
                                                n = -1;
                                                if (((String)object2).equals("space")) {
                                                    n = 0;
                                                } else if (((String)object2).equals("punct")) {
                                                    n = 1;
                                                } else if (((String)object2).equals("symbol")) {
                                                    n = 2;
                                                } else if (((String)object2).equals("currency")) {
                                                    n = 3;
                                                }
                                                if (n != -1) {
                                                    this.settings.setMaxVariable(n, 0);
                                                    this.settings.variableTop = this.baseData.getLastPrimaryForGroup(n + 4096);
                                                    this.ruleIndex = n2;
                                                    return;
                                                }
                                                break block51;
                                            }
                                            if (!((String)object).equals("caseFirst")) break block54;
                                            n = -1;
                                            if (((String)object2).equals("off")) {
                                                n = 0;
                                            } else if (((String)object2).equals("lower")) {
                                                n = 512;
                                            } else if (((String)object2).equals("upper")) {
                                                n = 768;
                                            }
                                            if (n != -1) {
                                                this.settings.setCaseFirst(n);
                                                this.ruleIndex = n2;
                                                return;
                                            }
                                            break block51;
                                        }
                                        if (!((String)object).equals("caseLevel")) break block55;
                                        n = CollationRuleParser.getOnOffValue((String)object2);
                                        if (n != -1) {
                                            object2 = this.settings;
                                            bl = n > 0 ? bl4 : false;
                                            ((CollationSettings)object2).setFlag(1024, bl);
                                            this.ruleIndex = n2;
                                            return;
                                        }
                                        break block51;
                                    }
                                    if (!((String)object).equals("normalization")) break block56;
                                    n = CollationRuleParser.getOnOffValue((String)object2);
                                    if (n != -1) {
                                        object2 = this.settings;
                                        bl = bl3;
                                        if (n > 0) {
                                            bl = true;
                                        }
                                        ((CollationSettings)object2).setFlag(1, bl);
                                        this.ruleIndex = n2;
                                        return;
                                    }
                                    break block51;
                                }
                                if (!((String)object).equals("numericOrdering")) break block57;
                                n = CollationRuleParser.getOnOffValue((String)object2);
                                if (n != -1) {
                                    object2 = this.settings;
                                    bl = n > 0 ? bl2 : false;
                                    ((CollationSettings)object2).setFlag(2, bl);
                                    this.ruleIndex = n2;
                                    return;
                                }
                                break block51;
                            }
                            if (!((String)object).equals("hiraganaQ")) break block58;
                            n = CollationRuleParser.getOnOffValue((String)object2);
                            if (n != -1) {
                                if (n == 1) {
                                    this.setParseError("[hiraganaQ on] is not supported");
                                }
                                this.ruleIndex = n2;
                                return;
                            }
                            break block51;
                        }
                        if (!((String)object).equals("import")) break block51;
                        try {
                            object = new ULocale.Builder();
                            object2 = ((ULocale.Builder)object).setLanguageTag((String)object2).build();
                            object = ((ULocale)object2).getBaseName();
                            object2 = ((ULocale)object2).getKeywordValue("collation");
                            importer = this.importer;
                            if (importer != null) break block48;
                        }
                        catch (Exception exception) {
                            this.setParseError("expected language tag in [import langTag]", exception);
                            return;
                        }
                        this.setParseError("[import langTag] is not supported");
                        break block59;
                    }
                    if (object2 == null) {
                        object2 = "standard";
                    }
                    try {
                        object = importer.getRules((String)object, (String)object2);
                        object2 = this.rules;
                        n = this.ruleIndex;
                    }
                    catch (Exception exception) {
                        this.setParseError("[import langTag] failed", exception);
                        return;
                    }
                    try {
                        this.parse((String)object);
                    }
                    catch (Exception exception) {
                        this.ruleIndex = n;
                        this.setParseError("parsing imported rules failed", exception);
                    }
                    this.rules = object2;
                    this.ruleIndex = n2;
                }
                return;
            }
            if (this.rules.charAt(n) == '[') {
                UnicodeSet unicodeSet = new UnicodeSet();
                n = this.parseUnicodeSet(n, unicodeSet);
                if (((String)object).equals("optimize")) {
                    try {
                        this.sink.optimize(unicodeSet);
                    }
                    catch (Exception exception) {
                        this.setParseError("[optimize set] failed", exception);
                    }
                    this.ruleIndex = n;
                    return;
                }
                if (((String)object).equals("suppressContractions")) {
                    try {
                        this.sink.suppressContractions(unicodeSet);
                    }
                    catch (Exception exception) {
                        this.setParseError("[suppressContractions set] failed", exception);
                    }
                    this.ruleIndex = n;
                    return;
                }
            }
        }
        this.setParseError("not a valid setting/option");
    }

    private int parseSpecialPosition(int n, StringBuilder stringBuilder) throws ParseException {
        int n2 = this.readWords(n + 1, this.rawBuilder);
        if (n2 > n && this.rules.charAt(n2) == ']' && this.rawBuilder.length() != 0) {
            String[] arrstring;
            int n3 = n2 + 1;
            String string = this.rawBuilder.toString();
            stringBuilder.setLength(0);
            for (n2 = 0; n2 < (arrstring = positions).length; ++n2) {
                if (!string.equals(arrstring[n2])) continue;
                stringBuilder.append('\ufffe');
                stringBuilder.append((char)(n2 + 10240));
                return n3;
            }
            if (string.equals("top")) {
                stringBuilder.append('\ufffe');
                stringBuilder.append((char)(Position.LAST_REGULAR.ordinal() + 10240));
                return n3;
            }
            if (string.equals("variable top")) {
                stringBuilder.append('\ufffe');
                stringBuilder.append((char)(Position.LAST_VARIABLE.ordinal() + 10240));
                return n3;
            }
        }
        this.setParseError("not a valid special reset position");
        return n;
    }

    private void parseStarredCharacters(int n, int n2) throws ParseException {
        int n3 = this.parseString(this.skipWhiteSpace(n2), this.rawBuilder);
        if (this.rawBuilder.length() == 0) {
            this.setParseError("missing starred-relation string");
            return;
        }
        n2 = -1;
        int n4 = 0;
        do {
            if (n4 < this.rawBuilder.length()) {
                n2 = this.rawBuilder.codePointAt(n4);
                if (!this.nfd.isInert(n2)) {
                    this.setParseError("starred-relation string is not all NFD-inert");
                    return;
                }
                try {
                    this.sink.addRelation(n, "", UTF16.valueOf(n2), "");
                }
                catch (Exception exception) {
                    this.setParseError("adding relation failed", exception);
                    return;
                }
                n4 += Character.charCount(n2);
                continue;
            }
            if (n3 >= this.rules.length() || this.rules.charAt(n3) != '-') break;
            if (n2 < 0) {
                this.setParseError("range without start in starred-relation string");
                return;
            }
            n3 = this.parseString(n3 + 1, this.rawBuilder);
            if (this.rawBuilder.length() == 0) {
                this.setParseError("range without end in starred-relation string");
                return;
            }
            int n5 = this.rawBuilder.codePointAt(0);
            n4 = n2;
            if (n5 < n2) {
                this.setParseError("range start greater than end in starred-relation string");
                return;
            }
            while (++n4 <= n5) {
                if (!this.nfd.isInert(n4)) {
                    this.setParseError("starred-relation string range is not all NFD-inert");
                    return;
                }
                if (CollationRuleParser.isSurrogate(n4)) {
                    this.setParseError("starred-relation string range contains a surrogate");
                    return;
                }
                if (65533 <= n4 && n4 <= 65535) {
                    this.setParseError("starred-relation string range contains U+FFFD, U+FFFE or U+FFFF");
                    return;
                }
                try {
                    this.sink.addRelation(n, "", UTF16.valueOf(n4), "");
                }
                catch (Exception exception) {
                    this.setParseError("adding relation failed", exception);
                    return;
                }
            }
            n2 = -1;
            n4 = Character.charCount(n5);
        } while (true);
        this.ruleIndex = this.skipWhiteSpace(n3);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private int parseString(int var1_1, StringBuilder var2_2) throws ParseException {
        var2_2.setLength(0);
        block0 : while (var1_1 < this.rules.length()) {
            var3_3 = this.rules;
            var4_4 = var1_1 + 1;
            var5_5 = var3_3.charAt(var1_1);
            if (!CollationRuleParser.isSyntaxChar(var5_5)) ** GOTO lbl28
            if (var5_5 == '\'') {
                var1_1 = var4_4;
                if (var4_4 < this.rules.length()) {
                    var1_1 = var4_4;
                    if (this.rules.charAt(var4_4) == '\'') {
                        var2_2.append('\'');
                        var1_1 = var4_4 + 1;
                        continue;
                    }
                }
            } else {
                if (var5_5 == '\\') {
                    if (var4_4 == this.rules.length()) {
                        this.setParseError("backslash escape at the end of the rule string");
                        return var4_4;
                    }
                    var1_1 = this.rules.codePointAt(var4_4);
                    var2_2.appendCodePoint(var1_1);
                    var1_1 = var4_4 + Character.charCount(var1_1);
                    continue;
                }
                var1_1 = var4_4 - 1;
                break;
lbl28: // 1 sources:
                if (PatternProps.isWhiteSpace(var5_5)) {
                    var1_1 = var4_4 - 1;
                    break;
                }
                var2_2.append(var5_5);
                var1_1 = var4_4;
                continue;
            }
            do {
                if (var1_1 == this.rules.length()) {
                    this.setParseError("quoted literal text missing terminating apostrophe");
                    return var1_1;
                }
                var3_3 = this.rules;
                var4_4 = var1_1 + 1;
                var5_5 = var3_3.charAt(var1_1);
                if (var5_5 != '\'') ** GOTO lbl48
                if (var4_4 < this.rules.length() && this.rules.charAt(var4_4) == '\'') {
                    var1_1 = var4_4 + 1;
                } else {
                    var1_1 = var4_4;
                    continue block0;
lbl48: // 1 sources:
                    var1_1 = var4_4;
                }
                var2_2.append(var5_5);
            } while (true);
        }
        var4_4 = 0;
        while (var4_4 < var2_2.length()) {
            var6_6 = var2_2.codePointAt(var4_4);
            if (CollationRuleParser.isSurrogate(var6_6)) {
                this.setParseError("string contains an unpaired surrogate");
                return var1_1;
            }
            if (65533 <= var6_6 && var6_6 <= 65535) {
                this.setParseError("string contains U+FFFD, U+FFFE or U+FFFF");
                return var1_1;
            }
            var4_4 += Character.charCount(var6_6);
        }
        return var1_1;
    }

    private int parseTailoringString(int n, StringBuilder stringBuilder) throws ParseException {
        n = this.parseString(this.skipWhiteSpace(n), stringBuilder);
        if (stringBuilder.length() == 0) {
            this.setParseError("missing relation string");
        }
        return this.skipWhiteSpace(n);
    }

    private int parseUnicodeSet(int n, UnicodeSet object) throws ParseException {
        int n2 = 0;
        int n3 = n;
        do {
            if (n3 == this.rules.length()) {
                this.setParseError("unbalanced UnicodeSet pattern brackets");
                return n3;
            }
            String string = this.rules;
            int n4 = n3 + 1;
            char c = string.charAt(n3);
            if (c == '[') {
                n3 = n2 + 1;
            } else {
                n3 = n2--;
                if (c == ']') {
                    n3 = n2;
                    if (n2 == 0) {
                        try {
                            ((UnicodeSet)object).applyPattern(this.rules.substring(n, n4));
                        }
                        catch (Exception exception) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("not a valid UnicodeSet pattern: ");
                            ((StringBuilder)object).append(exception.getMessage());
                            this.setParseError(((StringBuilder)object).toString());
                        }
                        n = this.skipWhiteSpace(n4);
                        if (n != this.rules.length() && this.rules.charAt(n) == ']') {
                            return n + 1;
                        }
                        this.setParseError("missing option-terminating ']' after UnicodeSet pattern");
                        return n;
                    }
                }
            }
            n2 = n3;
            n3 = n4;
        } while (true);
    }

    private int readWords(int n, StringBuilder stringBuilder) {
        stringBuilder.setLength(0);
        n = this.skipWhiteSpace(n);
        while (n < this.rules.length()) {
            char c = this.rules.charAt(n);
            if (CollationRuleParser.isSyntaxChar(c) && c != '-' && c != '_') {
                if (stringBuilder.length() == 0) {
                    return n;
                }
                int n2 = stringBuilder.length() - 1;
                if (stringBuilder.charAt(n2) == ' ') {
                    stringBuilder.setLength(n2);
                }
                return n;
            }
            if (PatternProps.isWhiteSpace(c)) {
                stringBuilder.append(' ');
                n = this.skipWhiteSpace(n + 1);
                continue;
            }
            stringBuilder.append(c);
            ++n;
        }
        return 0;
    }

    private void setParseError(String string) throws ParseException {
        throw this.makeParseException(string);
    }

    private void setParseError(String object, Exception exception) throws ParseException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)object);
        stringBuilder.append(": ");
        stringBuilder.append(exception.getMessage());
        object = this.makeParseException(stringBuilder.toString());
        ((Throwable)object).initCause(exception);
        throw object;
    }

    private int skipComment(int n) {
        int n2;
        do {
            n2 = n;
            if (n >= this.rules.length()) break;
            String string = this.rules;
            n2 = n + 1;
            if ((n = (int)string.charAt(n)) == 10 || n == 12 || n == 13 || n == 133 || n == 8232 || n == 8233) break;
            n = n2;
        } while (true);
        return n2;
    }

    private int skipWhiteSpace(int n) {
        while (n < this.rules.length() && PatternProps.isWhiteSpace(this.rules.charAt(n))) {
            ++n;
        }
        return n;
    }

    void parse(String string, CollationSettings collationSettings) throws ParseException {
        this.settings = collationSettings;
        this.parse(string);
    }

    void setImporter(Importer importer) {
        this.importer = importer;
    }

    void setSink(Sink sink) {
        this.sink = sink;
    }

    static interface Importer {
        public String getRules(String var1, String var2);
    }

    static enum Position {
        FIRST_TERTIARY_IGNORABLE,
        LAST_TERTIARY_IGNORABLE,
        FIRST_SECONDARY_IGNORABLE,
        LAST_SECONDARY_IGNORABLE,
        FIRST_PRIMARY_IGNORABLE,
        LAST_PRIMARY_IGNORABLE,
        FIRST_VARIABLE,
        LAST_VARIABLE,
        FIRST_REGULAR,
        LAST_REGULAR,
        FIRST_IMPLICIT,
        LAST_IMPLICIT,
        FIRST_TRAILING,
        LAST_TRAILING;
        
    }

    static abstract class Sink {
        Sink() {
        }

        abstract void addRelation(int var1, CharSequence var2, CharSequence var3, CharSequence var4);

        abstract void addReset(int var1, CharSequence var2);

        void optimize(UnicodeSet unicodeSet) {
        }

        void suppressContractions(UnicodeSet unicodeSet) {
        }
    }

}

