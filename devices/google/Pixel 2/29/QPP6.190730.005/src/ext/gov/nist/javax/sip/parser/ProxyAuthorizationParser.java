/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AuthenticationHeader;
import gov.nist.javax.sip.header.ProxyAuthorization;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.ChallengeParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class ProxyAuthorizationParser
extends ChallengeParser {
    protected ProxyAuthorizationParser(Lexer lexer) {
        super(lexer);
    }

    public ProxyAuthorizationParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        this.headerName(2072);
        ProxyAuthorization proxyAuthorization = new ProxyAuthorization();
        super.parse(proxyAuthorization);
        return proxyAuthorization;
    }
}

