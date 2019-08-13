/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AuthenticationHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.WWWAuthenticate;
import gov.nist.javax.sip.parser.ChallengeParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class WWWAuthenticateParser
extends ChallengeParser {
    protected WWWAuthenticateParser(Lexer lexer) {
        super(lexer);
    }

    public WWWAuthenticateParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("parse");
        }
        try {
            this.headerName(2096);
            WWWAuthenticate wWWAuthenticate = new WWWAuthenticate();
            super.parse(wWWAuthenticate);
            return wWWAuthenticate;
        }
        finally {
            if (debug) {
                this.dbg_leave("parse");
            }
        }
    }
}

