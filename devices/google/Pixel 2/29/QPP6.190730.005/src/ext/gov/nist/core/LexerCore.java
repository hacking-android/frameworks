/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import gov.nist.core.Debug;
import gov.nist.core.StringTokenizer;
import gov.nist.core.Token;
import java.text.ParseException;
import java.util.Hashtable;

public class LexerCore
extends StringTokenizer {
    public static final int ALPHA = 4099;
    static final char ALPHADIGIT_VALID_CHARS = '\ufffd';
    static final char ALPHA_VALID_CHARS = '\uffff';
    public static final int AND = 38;
    public static final int AT = 64;
    public static final int BACKSLASH = 92;
    public static final int BACK_QUOTE = 96;
    public static final int BAR = 124;
    public static final int COLON = 58;
    public static final int DIGIT = 4098;
    static final char DIGIT_VALID_CHARS = '\ufffe';
    public static final int DOLLAR = 36;
    public static final int DOT = 46;
    public static final int DOUBLEQUOTE = 34;
    public static final int END = 4096;
    public static final int EQUALS = 61;
    public static final int EXCLAMATION = 33;
    public static final int GREATER_THAN = 62;
    public static final int HAT = 94;
    public static final int HT = 9;
    public static final int ID = 4095;
    public static final int LESS_THAN = 60;
    public static final int LPAREN = 40;
    public static final int L_CURLY = 123;
    public static final int L_SQUARE_BRACKET = 91;
    public static final int MINUS = 45;
    public static final int NULL = 0;
    public static final int PERCENT = 37;
    public static final int PLUS = 43;
    public static final int POUND = 35;
    public static final int QUESTION = 63;
    public static final int QUOTE = 39;
    public static final int RPAREN = 41;
    public static final int R_CURLY = 125;
    public static final int R_SQUARE_BRACKET = 93;
    public static final int SAFE = 4094;
    public static final int SEMICOLON = 59;
    public static final int SLASH = 47;
    public static final int SP = 32;
    public static final int STAR = 42;
    public static final int START = 2048;
    public static final int TILDE = 126;
    public static final int UNDERSCORE = 95;
    public static final int WHITESPACE = 4097;
    protected static final Hashtable globalSymbolTable = new Hashtable();
    protected static final Hashtable lexerTables = new Hashtable();
    protected Hashtable currentLexer;
    protected String currentLexerName;
    protected Token currentMatch;

    protected LexerCore() {
        this.currentLexer = new Hashtable();
        this.currentLexerName = "charLexer";
    }

    public LexerCore(String string, String string2) {
        super(string2);
        this.currentLexerName = string;
    }

    public static String charAsString(char c) {
        return String.valueOf(c);
    }

    public static final boolean isTokenChar(char c) {
        if (LexerCore.isAlphaDigit(c)) {
            return true;
        }
        return c == '!' || c == '%' || c == '\'' || c == '~' || c == '*' || c == '+' || c == '-' || c == '.' || c == '_' || c == '`';
    }

    public void SPorHT() {
        char c = this.lookAhead(0);
        while (c == ' ' || c == '\t') {
            try {
                this.consume(1);
                c = this.lookAhead(0);
            }
            catch (ParseException parseException) {
                // empty catch block
                break;
            }
        }
    }

    protected void addKeyword(String string, int n) {
        Integer n2 = n;
        this.currentLexer.put(string, n2);
        if (!globalSymbolTable.containsKey(n2)) {
            globalSymbolTable.put(n2, string);
        }
    }

    protected Hashtable addLexer(String string) {
        this.currentLexer = (Hashtable)lexerTables.get(string);
        if (this.currentLexer == null) {
            this.currentLexer = new Hashtable();
            lexerTables.put(string, this.currentLexer);
        }
        return this.currentLexer;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String byteStringNoComma() {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            char c;
            while ((c = this.lookAhead(0)) != '\n') {
                if (c == ',') {
                    return stringBuffer.toString();
                }
                this.consume(1);
                stringBuffer.append(c);
            }
            return stringBuffer.toString();
        }
        catch (ParseException parseException) {
            // empty catch block
        }
        return stringBuffer.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String byteStringNoSemicolon() {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            char c;
            while ((c = this.lookAhead(0)) != '\u0000') {
                if (c == '\n') return stringBuffer.toString();
                if (c == ';') return stringBuffer.toString();
                if (c == ',') {
                    return stringBuffer.toString();
                }
                this.consume(1);
                stringBuffer.append(c);
            }
            return stringBuffer.toString();
        }
        catch (ParseException parseException) {
            return stringBuffer.toString();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String byteStringNoSlash() {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            char c;
            while ((c = this.lookAhead(0)) != '\u0000') {
                if (c == '\n') return stringBuffer.toString();
                if (c == '/') {
                    return stringBuffer.toString();
                }
                this.consume(1);
                stringBuffer.append(c);
            }
            return stringBuffer.toString();
        }
        catch (ParseException parseException) {
            return stringBuffer.toString();
        }
    }

    public String charAsString(int n) {
        return this.buffer.substring(this.ptr, this.ptr + n);
    }

    public String comment() throws ParseException {
        AbstractStringBuilder abstractStringBuilder = new StringBuffer();
        if (this.lookAhead(0) != '(') {
            return null;
        }
        this.consume(1);
        do {
            char c;
            if ((c = this.getNextChar()) == ')') {
                return ((StringBuffer)abstractStringBuilder).toString();
            }
            if (c == '\u0000') break;
            if (c == '\\') {
                ((StringBuffer)abstractStringBuilder).append(c);
                c = this.getNextChar();
                if (c != '\u0000') {
                    ((StringBuffer)abstractStringBuilder).append(c);
                    continue;
                }
                abstractStringBuilder = new StringBuilder();
                ((StringBuilder)abstractStringBuilder).append(this.buffer);
                ((StringBuilder)abstractStringBuilder).append(" : unexpected EOL");
                throw new ParseException(((StringBuilder)abstractStringBuilder).toString(), this.ptr);
            }
            ((StringBuffer)abstractStringBuilder).append(c);
        } while (true);
        abstractStringBuilder = new StringBuilder();
        ((StringBuilder)abstractStringBuilder).append(this.buffer);
        ((StringBuilder)abstractStringBuilder).append(" :unexpected EOL");
        throw new ParseException(((StringBuilder)abstractStringBuilder).toString(), this.ptr);
    }

    /*
     * Exception decompiling
     */
    public void consumeValidChars(char[] var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[CASE]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public ParseException createParseException() {
        return new ParseException(this.buffer, this.ptr);
    }

    public String getBuffer() {
        return this.buffer;
    }

    public String getNextId() {
        return this.ttoken();
    }

    public Token getNextToken() {
        return this.currentMatch;
    }

    public int getPtr() {
        return this.ptr;
    }

    public String getRest() {
        if (this.ptr >= this.buffer.length()) {
            return null;
        }
        return this.buffer.substring(this.ptr);
    }

    public String getString(char c) throws ParseException {
        char c2;
        AbstractStringBuilder abstractStringBuilder = new StringBuffer();
        while ((c2 = this.lookAhead(0)) != '\u0000') {
            if (c2 == c) {
                this.consume(1);
                return ((StringBuffer)abstractStringBuilder).toString();
            }
            if (c2 == '\\') {
                this.consume(1);
                c2 = this.lookAhead(0);
                if (c2 != '\u0000') {
                    this.consume(1);
                    ((StringBuffer)abstractStringBuilder).append(c2);
                    continue;
                }
                abstractStringBuilder = new StringBuilder();
                ((StringBuilder)abstractStringBuilder).append(this.buffer);
                ((StringBuilder)abstractStringBuilder).append("unexpected EOL");
                throw new ParseException(((StringBuilder)abstractStringBuilder).toString(), this.ptr);
            }
            this.consume(1);
            ((StringBuffer)abstractStringBuilder).append(c2);
        }
        abstractStringBuilder = new StringBuilder();
        ((StringBuilder)abstractStringBuilder).append(this.buffer);
        ((StringBuilder)abstractStringBuilder).append("unexpected EOL");
        throw new ParseException(((StringBuilder)abstractStringBuilder).toString(), this.ptr);
    }

    public String lookupToken(int n) {
        if (n > 2048) {
            return (String)globalSymbolTable.get(n);
        }
        return Character.valueOf((char)n).toString();
    }

    public int markInputPosition() {
        return this.ptr;
    }

    public Token match(int n) throws ParseException {
        Object object;
        char c;
        char c2;
        block15 : {
            block7 : {
                block11 : {
                    block14 : {
                        char c3;
                        block12 : {
                            block13 : {
                                block4 : {
                                    Object object2;
                                    block10 : {
                                        block8 : {
                                            block9 : {
                                                block5 : {
                                                    block6 : {
                                                        if (Debug.parserDebug) {
                                                            object = new StringBuilder();
                                                            ((StringBuilder)object).append("match ");
                                                            ((StringBuilder)object).append(n);
                                                            Debug.println(((StringBuilder)object).toString());
                                                        }
                                                        if (n <= 2048 || n >= 4096) break block4;
                                                        if (n != 4095) break block5;
                                                        if (!this.startsId()) break block6;
                                                        String string = this.getNextId();
                                                        this.currentMatch = new Token();
                                                        object = this.currentMatch;
                                                        ((Token)object).tokenValue = string;
                                                        ((Token)object).tokenType = 4095;
                                                        break block7;
                                                    }
                                                    object = new StringBuilder();
                                                    ((StringBuilder)object).append(this.buffer);
                                                    ((StringBuilder)object).append("\nID expected");
                                                    throw new ParseException(((StringBuilder)object).toString(), this.ptr);
                                                }
                                                if (n != 4094) break block8;
                                                if (!this.startsSafeToken()) break block9;
                                                object = this.ttokenSafe();
                                                Token token = this.currentMatch = new Token();
                                                token.tokenValue = object;
                                                token.tokenType = 4094;
                                                break block7;
                                            }
                                            object = new StringBuilder();
                                            ((StringBuilder)object).append(this.buffer);
                                            ((StringBuilder)object).append("\nID expected");
                                            throw new ParseException(((StringBuilder)object).toString(), this.ptr);
                                        }
                                        object = this.getNextId();
                                        object2 = (Integer)this.currentLexer.get(((String)object).toUpperCase());
                                        if (object2 == null || (Integer)object2 != n) break block10;
                                        this.currentMatch = new Token();
                                        object2 = this.currentMatch;
                                        ((Token)object2).tokenValue = object;
                                        ((Token)object2).tokenType = n;
                                        break block7;
                                    }
                                    object2 = new StringBuilder();
                                    ((StringBuilder)object2).append(this.buffer);
                                    ((StringBuilder)object2).append("\nUnexpected Token : ");
                                    ((StringBuilder)object2).append((String)object);
                                    throw new ParseException(((StringBuilder)object2).toString(), this.ptr);
                                }
                                if (n <= 4096) break block11;
                                c3 = this.lookAhead(0);
                                if (n != 4098) break block12;
                                if (!LexerCore.isDigit(c3)) break block13;
                                this.currentMatch = new Token();
                                this.currentMatch.tokenValue = String.valueOf(c3);
                                this.currentMatch.tokenType = n;
                                this.consume(1);
                                break block7;
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append(this.buffer);
                            ((StringBuilder)object).append("\nExpecting DIGIT");
                            throw new ParseException(((StringBuilder)object).toString(), this.ptr);
                        }
                        if (n != 4099) break block7;
                        if (!LexerCore.isAlpha(c3)) break block14;
                        this.currentMatch = new Token();
                        this.currentMatch.tokenValue = String.valueOf(c3);
                        this.currentMatch.tokenType = n;
                        this.consume(1);
                        break block7;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append(this.buffer);
                    ((StringBuilder)object).append("\nExpecting ALPHA");
                    throw new ParseException(((StringBuilder)object).toString(), this.ptr);
                }
                c = (char)n;
                c2 = this.lookAhead(0);
                if (c2 != c) break block15;
                this.consume(1);
            }
            return this.currentMatch;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(this.buffer);
        ((StringBuilder)object).append("\nExpecting  >>>");
        ((StringBuilder)object).append(c);
        ((StringBuilder)object).append("<<< got >>>");
        ((StringBuilder)object).append(c2);
        ((StringBuilder)object).append("<<<");
        throw new ParseException(((StringBuilder)object).toString(), this.ptr);
    }

    public String number() throws ParseException {
        int n = this.ptr;
        try {
            if (LexerCore.isDigit(this.lookAhead(0))) {
                this.consume(1);
                while (LexerCore.isDigit(this.lookAhead(0))) {
                    this.consume(1);
                }
                return this.buffer.substring(n, this.ptr);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.buffer);
            stringBuilder.append(": Unexpected token at ");
            stringBuilder.append(this.lookAhead(0));
            ParseException parseException = new ParseException(stringBuilder.toString(), this.ptr);
            throw parseException;
        }
        catch (ParseException parseException) {
            return this.buffer.substring(n, this.ptr);
        }
    }

    public String peekNextId() {
        int n = this.ptr;
        String string = this.ttoken();
        this.savedPtr = this.ptr;
        this.ptr = n;
        return string;
    }

    public Token peekNextToken() throws ParseException {
        return this.peekNextToken(1)[0];
    }

    public Token[] peekNextToken(int n) throws ParseException {
        int n2 = this.ptr;
        Token[] arrtoken = new Token[n];
        for (int i = 0; i < n; ++i) {
            Token token = new Token();
            if (this.startsId()) {
                String string;
                token.tokenValue = string = this.ttoken();
                token.tokenType = this.currentLexer.containsKey(string = string.toUpperCase()) ? (Integer)this.currentLexer.get(string) : 4095;
            } else {
                char c = this.getNextChar();
                token.tokenValue = String.valueOf(c);
                token.tokenType = LexerCore.isAlpha(c) ? 4099 : (LexerCore.isDigit(c) ? 4098 : (int)c);
            }
            arrtoken[i] = token;
        }
        this.savedPtr = this.ptr;
        this.ptr = n2;
        return arrtoken;
    }

    public String quotedString() throws ParseException {
        int n = this.ptr;
        if (this.lookAhead(0) != '\"') {
            return null;
        }
        this.consume(1);
        do {
            char c;
            if ((c = this.getNextChar()) == '\"') {
                return this.buffer.substring(n + 1, this.ptr - 1);
            }
            if (c == '\u0000') break;
            if (c != '\\') continue;
            this.consume(1);
        } while (true);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.buffer);
        stringBuilder.append(" :unexpected EOL");
        throw new ParseException(stringBuilder.toString(), this.ptr);
    }

    public void rewindInputPosition(int n) {
        this.ptr = n;
    }

    public void selectLexer(String string) {
        this.currentLexerName = string;
    }

    public boolean startsId() {
        try {
            boolean bl = LexerCore.isTokenChar(this.lookAhead(0));
            return bl;
        }
        catch (ParseException parseException) {
            return false;
        }
    }

    public boolean startsSafeToken() {
        block15 : {
            char c;
            try {
                c = this.lookAhead(0);
                boolean bl = LexerCore.isAlphaDigit(c);
                if (bl) {
                    return true;
                }
                if (c == '\'' || c == '=' || c == '[' || c == '*' || c == '+' || c == ':' || c == ';' || c == '?' || c == '@') break block15;
            }
            catch (ParseException parseException) {
                return false;
            }
            switch (c) {
                default: {
                    switch (c) {
                        default: {
                            switch (c) {
                                default: {
                                    switch (c) {
                                        default: {
                                            return false;
                                        }
                                        case '{': 
                                        case '|': 
                                        case '}': 
                                        case '~': 
                                    }
                                }
                                case ']': 
                                case '^': 
                                case '_': 
                                case '`': 
                            }
                        }
                        case '-': 
                        case '.': 
                        case '/': 
                    }
                }
                case '!': 
                case '\"': 
                case '#': 
                case '$': 
                case '%': 
            }
        }
        return true;
    }

    public String ttoken() {
        int n = this.ptr;
        try {
            while (this.hasMoreChars() && LexerCore.isTokenChar(this.lookAhead(0))) {
                this.consume(1);
            }
            String string = this.buffer.substring(n, this.ptr);
            return string;
        }
        catch (ParseException parseException) {
            return null;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public String ttokenSafe() {
        var1_1 = this.ptr;
        do {
            block18 : {
                while (this.hasMoreChars() != false) {
                    var2_2 = this.lookAhead(0);
                    if (LexerCore.isAlphaDigit(var2_2)) {
                        this.consume(1);
                        continue;
                    }
                    break block18;
                }
                return this.buffer.substring(var1_1, this.ptr);
            }
            var3_3 = false;
            if (var2_2 == '\'' || var2_2 == '[' || var2_2 == '*' || var2_2 == '+' || var2_2 == ':' || var2_2 == ';' || var2_2 == '?' || var2_2 == '@') break block19;
            break;
        } while (true);
        catch (ParseException var4_5) {
            return null;
        }
        {
            block19 : {
                switch (var2_2) {
                    default: {
                        switch (var2_2) {
                            default: {
                                switch (var2_2) {
                                    default: {
                                        switch (var2_2) {
                                            default: {
                                                ** break;
                                            }
                                            case '{': 
                                            case '|': 
                                            case '}': 
                                            case '~': 
                                        }
                                    }
                                    case ']': 
                                    case '^': 
                                    case '_': 
                                    case '`': 
                                }
                            }
                            case '-': 
                            case '.': 
                            case '/': 
                        }
                    }
                    case '!': 
                    case '\"': 
                    case '#': 
                    case '$': 
                    case '%': 
                }
            }
            var3_3 = true;
lbl31: // 2 sources:
            if (var3_3 == false) return this.buffer.substring(var1_1, this.ptr);
            this.consume(1);
            continue;
        }
    }
}

