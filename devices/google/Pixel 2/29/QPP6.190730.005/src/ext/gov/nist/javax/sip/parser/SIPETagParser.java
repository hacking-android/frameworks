/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.SIPETag;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class SIPETagParser
extends HeaderParser {
    protected SIPETagParser(Lexer lexer) {
        super(lexer);
    }

    public SIPETagParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("SIPEtag.parse");
        }
        SIPETag sIPETag = new SIPETag();
        try {
            this.headerName(2116);
            this.lexer.SPorHT();
            this.lexer.match(4095);
            sIPETag.setETag(this.lexer.getNextToken().getTokenValue());
            this.lexer.SPorHT();
            this.lexer.match(10);
            return sIPETag;
        }
        finally {
            if (debug) {
                this.dbg_leave("SIPEtag.parse");
            }
        }
    }
}

