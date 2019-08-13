/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.header.ErrorInfo;
import gov.nist.javax.sip.header.ErrorInfoList;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import gov.nist.javax.sip.parser.URLParser;
import java.text.ParseException;
import javax.sip.address.URI;

public class ErrorInfoParser
extends ParametersParser {
    protected ErrorInfoParser(Lexer lexer) {
        super(lexer);
    }

    public ErrorInfoParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("ErrorInfoParser.parse");
        }
        ErrorInfoList errorInfoList = new ErrorInfoList();
        try {
            this.headerName(2058);
            block3 : while (this.lexer.lookAhead(0) != '\n') {
                do {
                    ErrorInfo errorInfo = new ErrorInfo();
                    errorInfo.setHeaderName("Error-Info");
                    this.lexer.SPorHT();
                    this.lexer.match(60);
                    URLParser uRLParser = new URLParser((Lexer)this.lexer);
                    errorInfo.setErrorInfo(uRLParser.uriReference(true));
                    this.lexer.match(62);
                    this.lexer.SPorHT();
                    super.parse(errorInfo);
                    errorInfoList.add(errorInfo);
                    if (this.lexer.lookAhead(0) != ',') continue block3;
                    this.lexer.match(44);
                } while (true);
            }
            return errorInfoList;
        }
        finally {
            if (debug) {
                this.dbg_leave("ErrorInfoParser.parse");
            }
        }
    }
}

