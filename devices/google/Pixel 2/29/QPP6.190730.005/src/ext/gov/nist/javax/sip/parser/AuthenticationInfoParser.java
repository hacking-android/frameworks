/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.NameValue;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AuthenticationInfo;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.text.ParseException;

public class AuthenticationInfoParser
extends ParametersParser {
    protected AuthenticationInfoParser(Lexer lexer) {
        super(lexer);
    }

    public AuthenticationInfoParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("AuthenticationInfoParser.parse");
        }
        try {
            this.headerName(2112);
            AuthenticationInfo authenticationInfo = new AuthenticationInfo();
            authenticationInfo.setHeaderName("Authentication-Info");
            this.lexer.SPorHT();
            authenticationInfo.setParameter(super.nameValue());
            this.lexer.SPorHT();
            while (this.lexer.lookAhead(0) == ',') {
                this.lexer.match(44);
                this.lexer.SPorHT();
                authenticationInfo.setParameter(super.nameValue());
                this.lexer.SPorHT();
            }
            this.lexer.SPorHT();
            return authenticationInfo;
        }
        finally {
            if (debug) {
                this.dbg_leave("AuthenticationInfoParser.parse");
            }
        }
    }
}

