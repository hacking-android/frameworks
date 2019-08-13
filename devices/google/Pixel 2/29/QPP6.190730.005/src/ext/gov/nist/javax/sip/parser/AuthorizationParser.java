/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AuthenticationHeader;
import gov.nist.javax.sip.header.Authorization;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.ChallengeParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class AuthorizationParser
extends ChallengeParser {
    protected AuthorizationParser(Lexer lexer) {
        super(lexer);
    }

    public AuthorizationParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        this.dbg_enter("parse");
        try {
            this.headerName(2071);
            Authorization authorization = new Authorization();
            super.parse(authorization);
            return authorization;
        }
        finally {
            this.dbg_leave("parse");
        }
    }
}

