/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.header.AlertInfo;
import gov.nist.javax.sip.header.AlertInfoList;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import gov.nist.javax.sip.parser.URLParser;
import java.text.ParseException;
import javax.sip.address.URI;

public class AlertInfoParser
extends ParametersParser {
    protected AlertInfoParser(Lexer lexer) {
        super(lexer);
    }

    public AlertInfoParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("AlertInfoParser.parse");
        }
        AlertInfoList alertInfoList = new AlertInfoList();
        try {
            this.headerName(2061);
            block3 : while (this.lexer.lookAhead(0) != '\n') {
                AlertInfo alertInfo = new AlertInfo();
                alertInfo.setHeaderName("Alert-Info");
                do {
                    this.lexer.SPorHT();
                    if (this.lexer.lookAhead(0) == '<') {
                        this.lexer.match(60);
                        URLParser uRLParser = new URLParser((Lexer)this.lexer);
                        alertInfo.setAlertInfo(uRLParser.uriReference(true));
                        this.lexer.match(62);
                    } else {
                        alertInfo.setAlertInfo(this.lexer.byteStringNoSemicolon());
                    }
                    this.lexer.SPorHT();
                    super.parse(alertInfo);
                    alertInfoList.add(alertInfo);
                    if (this.lexer.lookAhead(0) != ',') continue block3;
                    this.lexer.match(44);
                } while (true);
            }
            return alertInfoList;
        }
        finally {
            if (debug) {
                this.dbg_leave("AlertInfoParser.parse");
            }
        }
    }
}

