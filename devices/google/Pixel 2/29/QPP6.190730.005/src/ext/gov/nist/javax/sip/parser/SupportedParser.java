/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.Supported;
import gov.nist.javax.sip.header.SupportedList;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class SupportedParser
extends HeaderParser {
    protected SupportedParser(Lexer lexer) {
        super(lexer);
    }

    public SupportedParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        SupportedList supportedList = new SupportedList();
        if (debug) {
            this.dbg_enter("SupportedParser.parse");
        }
        try {
            this.headerName(2068);
            while (this.lexer.lookAhead(0) != '\n') {
                this.lexer.SPorHT();
                Supported supported = new Supported();
                supported.setHeaderName("Supported");
                this.lexer.match(4095);
                supported.setOptionTag(this.lexer.getNextToken().getTokenValue());
                this.lexer.SPorHT();
                supportedList.add(supported);
                while (this.lexer.lookAhead(0) == ',') {
                    this.lexer.match(44);
                    this.lexer.SPorHT();
                    supported = new Supported();
                    this.lexer.match(4095);
                    supported.setOptionTag(this.lexer.getNextToken().getTokenValue());
                    this.lexer.SPorHT();
                    supportedList.add(supported);
                }
            }
            return supportedList;
        }
        finally {
            if (debug) {
                this.dbg_leave("SupportedParser.parse");
            }
        }
    }
}

