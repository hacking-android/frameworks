/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.Allow;
import gov.nist.javax.sip.header.AllowList;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class AllowParser
extends HeaderParser {
    protected AllowParser(Lexer lexer) {
        super(lexer);
    }

    public AllowParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("AllowParser.parse");
        }
        AllowList allowList = new AllowList();
        try {
            this.headerName(2069);
            Allow allow = new Allow();
            allow.setHeaderName("Allow");
            this.lexer.SPorHT();
            this.lexer.match(4095);
            allow.setMethod(this.lexer.getNextToken().getTokenValue());
            allowList.add(allow);
            this.lexer.SPorHT();
            while (this.lexer.lookAhead(0) == ',') {
                this.lexer.match(44);
                this.lexer.SPorHT();
                allow = new Allow();
                this.lexer.match(4095);
                allow.setMethod(this.lexer.getNextToken().getTokenValue());
                allowList.add(allow);
                this.lexer.SPorHT();
            }
            this.lexer.SPorHT();
            this.lexer.match(10);
            return allowList;
        }
        finally {
            if (debug) {
                this.dbg_leave("AllowParser.parse");
            }
        }
    }
}

