/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.Accept;
import gov.nist.javax.sip.header.AcceptList;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.text.ParseException;

public class AcceptParser
extends ParametersParser {
    protected AcceptParser(Lexer lexer) {
        super(lexer);
    }

    public AcceptParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("AcceptParser.parse");
        }
        AcceptList acceptList = new AcceptList();
        try {
            this.headerName(2068);
            Accept accept = new Accept();
            accept.setHeaderName("Accept");
            this.lexer.SPorHT();
            this.lexer.match(4095);
            accept.setContentType(this.lexer.getNextToken().getTokenValue());
            this.lexer.match(47);
            this.lexer.match(4095);
            accept.setContentSubType(this.lexer.getNextToken().getTokenValue());
            this.lexer.SPorHT();
            super.parse(accept);
            acceptList.add(accept);
            while (this.lexer.lookAhead(0) == ',') {
                this.lexer.match(44);
                this.lexer.SPorHT();
                accept = new Accept();
                this.lexer.match(4095);
                accept.setContentType(this.lexer.getNextToken().getTokenValue());
                this.lexer.match(47);
                this.lexer.match(4095);
                accept.setContentSubType(this.lexer.getNextToken().getTokenValue());
                this.lexer.SPorHT();
                super.parse(accept);
                acceptList.add(accept);
            }
            return acceptList;
        }
        finally {
            if (debug) {
                this.dbg_leave("AcceptParser.parse");
            }
        }
    }
}

