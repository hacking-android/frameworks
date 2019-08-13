/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.Unsupported;
import gov.nist.javax.sip.header.UnsupportedList;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class UnsupportedParser
extends HeaderParser {
    protected UnsupportedParser(Lexer lexer) {
        super(lexer);
    }

    public UnsupportedParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        UnsupportedList unsupportedList = new UnsupportedList();
        if (debug) {
            this.dbg_enter("UnsupportedParser.parse");
        }
        try {
            this.headerName(2076);
            while (this.lexer.lookAhead(0) != '\n') {
                this.lexer.SPorHT();
                Unsupported unsupported = new Unsupported();
                unsupported.setHeaderName("Unsupported");
                this.lexer.match(4095);
                unsupported.setOptionTag(this.lexer.getNextToken().getTokenValue());
                this.lexer.SPorHT();
                unsupportedList.add(unsupported);
                while (this.lexer.lookAhead(0) == ',') {
                    this.lexer.match(44);
                    this.lexer.SPorHT();
                    unsupported = new Unsupported();
                    this.lexer.match(4095);
                    unsupported.setOptionTag(this.lexer.getNextToken().getTokenValue());
                    this.lexer.SPorHT();
                    unsupportedList.add(unsupported);
                }
            }
            return unsupportedList;
        }
        finally {
            if (debug) {
                this.dbg_leave("UnsupportedParser.parse");
            }
        }
    }
}

