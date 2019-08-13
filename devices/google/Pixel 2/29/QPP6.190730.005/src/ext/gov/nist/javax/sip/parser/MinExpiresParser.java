/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.MinExpires;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

public class MinExpiresParser
extends HeaderParser {
    protected MinExpiresParser(Lexer lexer) {
        super(lexer);
    }

    public MinExpiresParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("MinExpiresParser.parse");
        }
        MinExpires minExpires = new MinExpires();
        try {
            this.headerName(2110);
            minExpires.setHeaderName("Min-Expires");
            String string = this.lexer.number();
            try {
                minExpires.setExpires(Integer.parseInt(string));
                this.lexer.SPorHT();
                this.lexer.match(10);
            }
            catch (InvalidArgumentException invalidArgumentException) {
                throw this.createParseException(invalidArgumentException.getMessage());
            }
            return minExpires;
        }
        finally {
            if (debug) {
                this.dbg_leave("MinExpiresParser.parse");
            }
        }
    }
}

