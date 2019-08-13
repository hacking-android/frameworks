/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.Require;
import gov.nist.javax.sip.header.RequireList;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class RequireParser
extends HeaderParser {
    protected RequireParser(Lexer lexer) {
        super(lexer);
    }

    public RequireParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        RequireList requireList = new RequireList();
        if (debug) {
            this.dbg_enter("RequireParser.parse");
        }
        try {
            this.headerName(2089);
            while (this.lexer.lookAhead(0) != '\n') {
                Require require = new Require();
                require.setHeaderName("Require");
                this.lexer.match(4095);
                require.setOptionTag(this.lexer.getNextToken().getTokenValue());
                this.lexer.SPorHT();
                requireList.add(require);
                while (this.lexer.lookAhead(0) == ',') {
                    this.lexer.match(44);
                    this.lexer.SPorHT();
                    require = new Require();
                    this.lexer.match(4095);
                    require.setOptionTag(this.lexer.getNextToken().getTokenValue());
                    this.lexer.SPorHT();
                    requireList.add(require);
                }
            }
            return requireList;
        }
        finally {
            if (debug) {
                this.dbg_leave("RequireParser.parse");
            }
        }
    }
}

