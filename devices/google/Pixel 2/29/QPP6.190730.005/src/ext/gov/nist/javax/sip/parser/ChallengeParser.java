/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.NameValue;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AuthenticationHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public abstract class ChallengeParser
extends HeaderParser {
    protected ChallengeParser(Lexer lexer) {
        super(lexer);
    }

    protected ChallengeParser(String string) {
        super(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void parse(AuthenticationHeader authenticationHeader) throws ParseException {
        this.lexer.SPorHT();
        this.lexer.match(4095);
        Token token = this.lexer.getNextToken();
        this.lexer.SPorHT();
        authenticationHeader.setScheme(token.getTokenValue());
        while (this.lexer.lookAhead(0) != '\n') {
            this.parseParameter(authenticationHeader);
            this.lexer.SPorHT();
            char c = this.lexer.lookAhead(0);
            if (c == '\n') return;
            if (c == '\u0000') {
                return;
            }
            this.lexer.match(44);
            this.lexer.SPorHT();
        }
        return;
    }

    protected void parseParameter(AuthenticationHeader authenticationHeader) throws ParseException {
        if (debug) {
            this.dbg_enter("parseParameter");
        }
        try {
            authenticationHeader.setParameter(this.nameValue('='));
            return;
        }
        finally {
            if (debug) {
                this.dbg_leave("parseParameter");
            }
        }
    }
}

