/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.Debug;
import gov.nist.core.LexerCore;
import gov.nist.core.ParserCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.TokenTypes;
import java.text.ParseException;

public abstract class Parser
extends ParserCore
implements TokenTypes {
    public static final void checkToken(String string) throws ParseException {
        if (string != null && string.length() != 0) {
            for (int i = 0; i < string.length(); ++i) {
                if (LexerCore.isTokenChar(string.charAt(i))) {
                    continue;
                }
                throw new ParseException("Invalid character(s) in string (not allowed in 'token')", i);
            }
            return;
        }
        throw new ParseException("null or empty token", -1);
    }

    protected ParseException createParseException(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.lexer.getBuffer());
        stringBuilder.append(":");
        stringBuilder.append(string);
        return new ParseException(stringBuilder.toString(), this.lexer.getPtr());
    }

    protected Lexer getLexer() {
        return (Lexer)this.lexer;
    }

    protected String method() throws ParseException {
        try {
            Object object;
            if (debug) {
                this.dbg_enter("method");
            }
            if (((Token)(object = this.lexer.peekNextToken(1)[0])).getTokenType() != 2053 && ((Token)object).getTokenType() != 2054 && ((Token)object).getTokenType() != 2056 && ((Token)object).getTokenType() != 2055 && ((Token)object).getTokenType() != 2052 && ((Token)object).getTokenType() != 2057 && ((Token)object).getTokenType() != 2101 && ((Token)object).getTokenType() != 2102 && ((Token)object).getTokenType() != 2115 && ((Token)object).getTokenType() != 2118 && ((Token)object).getTokenType() != 4095) {
                throw this.createParseException("Invalid Method");
            }
            this.lexer.consume();
            object = ((Token)object).getTokenValue();
            return object;
        }
        finally {
            if (Debug.debug) {
                this.dbg_leave("method");
            }
        }
    }

    protected String sipVersion() throws ParseException {
        if (debug) {
            this.dbg_enter("sipVersion");
        }
        try {
            if (!this.lexer.match(2051).getTokenValue().equalsIgnoreCase("SIP")) {
                this.createParseException("Expecting SIP");
            }
            this.lexer.match(47);
            if (!this.lexer.match(4095).getTokenValue().equals("2.0")) {
                this.createParseException("Expecting SIP/2.0");
            }
            return "SIP/2.0";
        }
        finally {
            if (debug) {
                this.dbg_leave("sipVersion");
            }
        }
    }
}

