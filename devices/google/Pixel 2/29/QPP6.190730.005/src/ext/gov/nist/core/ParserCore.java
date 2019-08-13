/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import gov.nist.core.Debug;
import gov.nist.core.LexerCore;
import gov.nist.core.NameValue;
import gov.nist.core.Token;
import java.io.PrintStream;
import java.text.ParseException;

public abstract class ParserCore {
    public static final boolean debug = Debug.parserDebug;
    static int nesting_level;
    protected LexerCore lexer;

    protected void dbg_enter(String string) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < nesting_level; ++i) {
            stringBuffer.append(">");
        }
        if (debug) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((Object)stringBuffer);
            stringBuilder.append(string);
            stringBuilder.append("\nlexer buffer = \n");
            stringBuilder.append(this.lexer.getRest());
            printStream.println(stringBuilder.toString());
        }
        ++nesting_level;
    }

    protected void dbg_leave(String string) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < nesting_level; ++i) {
            stringBuffer.append("<");
        }
        if (debug) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((Object)stringBuffer);
            stringBuilder.append(string);
            stringBuilder.append("\nlexer buffer = \n");
            stringBuilder.append(this.lexer.getRest());
            printStream.println(stringBuilder.toString());
        }
        --nesting_level;
    }

    protected NameValue nameValue() throws ParseException {
        return this.nameValue('=');
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected NameValue nameValue(char c) throws ParseException {
        if (debug) {
            this.dbg_enter("nameValue");
        }
        try {
            this.lexer.match(4095);
            Token token = this.lexer.getNextToken();
            this.lexer.SPorHT();
            char c2 = '\u0000';
            try {
                char c3 = this.lexer.lookAhead(0);
                if (c3 == c) {
                    String string;
                    this.lexer.consume(1);
                    this.lexer.SPorHT();
                    boolean bl = false;
                    if (this.lexer.lookAhead(0) == '\"') {
                        string = this.lexer.quotedString();
                        c = '\u0001';
                    } else {
                        this.lexer.match(4095);
                        string = this.lexer.getNextToken().tokenValue;
                        if (string == null) {
                            bl = true;
                            string = "";
                            c = c2;
                        } else {
                            c = c2;
                        }
                    }
                    NameValue nameValue = new NameValue(token.tokenValue, string, bl);
                    if (c == '\u0000') return nameValue;
                    nameValue.setQuotedValue();
                    return nameValue;
                }
                NameValue nameValue = new NameValue(token.tokenValue, "", true);
                return nameValue;
            }
            catch (ParseException parseException) {
                NameValue nameValue = new NameValue(token.tokenValue, null, false);
                return nameValue;
            }
        }
        finally {
            if (debug) {
                this.dbg_leave("nameValue");
            }
        }
    }

    protected void peekLine(String string) {
        if (debug) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" ");
            stringBuilder.append(this.lexer.peekLine());
            Debug.println(stringBuilder.toString());
        }
    }
}

