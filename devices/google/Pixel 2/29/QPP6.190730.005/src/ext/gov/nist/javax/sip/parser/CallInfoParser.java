/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.header.CallInfo;
import gov.nist.javax.sip.header.CallInfoList;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import gov.nist.javax.sip.parser.URLParser;
import java.text.ParseException;
import javax.sip.address.URI;

public class CallInfoParser
extends ParametersParser {
    protected CallInfoParser(Lexer lexer) {
        super(lexer);
    }

    public CallInfoParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("CallInfoParser.parse");
        }
        CallInfoList callInfoList = new CallInfoList();
        try {
            this.headerName(2099);
            while (this.lexer.lookAhead(0) != '\n') {
                Object object = new CallInfo();
                ((SIPHeader)object).setHeaderName("Call-Info");
                this.lexer.SPorHT();
                this.lexer.match(60);
                Object object2 = new URLParser((Lexer)this.lexer);
                ((CallInfo)object).setInfo(((URLParser)object2).uriReference(true));
                this.lexer.match(62);
                this.lexer.SPorHT();
                super.parse((ParametersHeader)object);
                callInfoList.add(object);
                while (this.lexer.lookAhead(0) == ',') {
                    this.lexer.match(44);
                    this.lexer.SPorHT();
                    object2 = new CallInfo();
                    this.lexer.SPorHT();
                    this.lexer.match(60);
                    object = new URLParser((Lexer)this.lexer);
                    ((CallInfo)object2).setInfo(((URLParser)object).uriReference(true));
                    this.lexer.match(62);
                    this.lexer.SPorHT();
                    super.parse((ParametersHeader)object2);
                    callInfoList.add(object2);
                }
            }
            return callInfoList;
        }
        finally {
            if (debug) {
                this.dbg_leave("CallInfoParser.parse");
            }
        }
    }
}

