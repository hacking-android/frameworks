/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.ProxyRequire;
import gov.nist.javax.sip.header.ProxyRequireList;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class ProxyRequireParser
extends HeaderParser {
    protected ProxyRequireParser(Lexer lexer) {
        super(lexer);
    }

    public ProxyRequireParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        ProxyRequireList proxyRequireList = new ProxyRequireList();
        if (debug) {
            this.dbg_enter("ProxyRequireParser.parse");
        }
        try {
            this.headerName(2074);
            while (this.lexer.lookAhead(0) != '\n') {
                ProxyRequire proxyRequire = new ProxyRequire();
                proxyRequire.setHeaderName("Proxy-Require");
                this.lexer.match(4095);
                proxyRequire.setOptionTag(this.lexer.getNextToken().getTokenValue());
                this.lexer.SPorHT();
                proxyRequireList.add(proxyRequire);
                while (this.lexer.lookAhead(0) == ',') {
                    this.lexer.match(44);
                    this.lexer.SPorHT();
                    proxyRequire = new ProxyRequire();
                    this.lexer.match(4095);
                    proxyRequire.setOptionTag(this.lexer.getNextToken().getTokenValue());
                    this.lexer.SPorHT();
                    proxyRequireList.add(proxyRequire);
                }
            }
            return proxyRequireList;
        }
        finally {
            if (debug) {
                this.dbg_leave("ProxyRequireParser.parse");
            }
        }
    }
}

