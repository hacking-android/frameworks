/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.RSeq;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

public class RSeqParser
extends HeaderParser {
    protected RSeqParser(Lexer lexer) {
        super(lexer);
    }

    public RSeqParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("RSeqParser.parse");
        }
        RSeq rSeq = new RSeq();
        try {
            this.headerName(2108);
            rSeq.setHeaderName("RSeq");
            String string = this.lexer.number();
            try {
                rSeq.setSeqNumber(Long.parseLong(string));
                this.lexer.SPorHT();
                this.lexer.match(10);
            }
            catch (InvalidArgumentException invalidArgumentException) {
                throw this.createParseException(invalidArgumentException.getMessage());
            }
            return rSeq;
        }
        finally {
            if (debug) {
                this.dbg_leave("RSeqParser.parse");
            }
        }
    }
}

