/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SIPIfMatch;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class SIPIfMatchParser
extends HeaderParser {
    protected SIPIfMatchParser(Lexer lexer) {
        super(lexer);
    }

    public SIPIfMatchParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("SIPIfMatch.parse");
        }
        SIPIfMatch sIPIfMatch = new SIPIfMatch();
        try {
            this.headerName(2117);
            this.lexer.SPorHT();
            this.lexer.match(4095);
            sIPIfMatch.setETag(this.lexer.getNextToken().getTokenValue());
            this.lexer.SPorHT();
            this.lexer.match(10);
            return sIPIfMatch;
        }
        finally {
            if (debug) {
                this.dbg_leave("SIPIfMatch.parse");
            }
        }
    }
}

