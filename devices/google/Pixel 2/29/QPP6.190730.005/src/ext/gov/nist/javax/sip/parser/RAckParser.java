/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.RAck;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

public class RAckParser
extends HeaderParser {
    protected RAckParser(Lexer lexer) {
        super(lexer);
    }

    public RAckParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("RAckParser.parse");
        }
        RAck rAck = new RAck();
        try {
            this.headerName(2109);
            rAck.setHeaderName("RAck");
            try {
                rAck.setRSequenceNumber(Long.parseLong(this.lexer.number()));
                this.lexer.SPorHT();
                rAck.setCSequenceNumber(Long.parseLong(this.lexer.number()));
                this.lexer.SPorHT();
                this.lexer.match(4095);
                rAck.setMethod(this.lexer.getNextToken().getTokenValue());
                this.lexer.SPorHT();
                this.lexer.match(10);
            }
            catch (InvalidArgumentException invalidArgumentException) {
                throw this.createParseException(invalidArgumentException.getMessage());
            }
            return rAck;
        }
        finally {
            if (debug) {
                this.dbg_leave("RAckParser.parse");
            }
        }
    }
}

