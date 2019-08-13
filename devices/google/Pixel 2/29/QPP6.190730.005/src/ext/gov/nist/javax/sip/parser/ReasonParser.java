/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.Reason;
import gov.nist.javax.sip.header.ReasonList;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.text.ParseException;

public class ReasonParser
extends ParametersParser {
    protected ReasonParser(Lexer lexer) {
        super(lexer);
    }

    public ReasonParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        ReasonList reasonList = new ReasonList();
        if (debug) {
            this.dbg_enter("ReasonParser.parse");
        }
        try {
            this.headerName(2107);
            this.lexer.SPorHT();
            while (this.lexer.lookAhead(0) != '\n') {
                Reason reason = new Reason();
                this.lexer.match(4095);
                reason.setProtocol(this.lexer.getNextToken().getTokenValue());
                super.parse(reason);
                reasonList.add(reason);
                if (this.lexer.lookAhead(0) == ',') {
                    this.lexer.match(44);
                    this.lexer.SPorHT();
                    continue;
                }
                this.lexer.SPorHT();
            }
            return reasonList;
        }
        finally {
            if (debug) {
                this.dbg_leave("ReasonParser.parse");
            }
        }
    }
}

