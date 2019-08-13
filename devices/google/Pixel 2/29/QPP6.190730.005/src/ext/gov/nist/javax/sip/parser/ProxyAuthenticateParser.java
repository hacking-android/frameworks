/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AuthenticationHeader;
import gov.nist.javax.sip.header.ProxyAuthenticate;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.ChallengeParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class ProxyAuthenticateParser
extends ChallengeParser {
    protected ProxyAuthenticateParser(Lexer lexer) {
        super(lexer);
    }

    public ProxyAuthenticateParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        this.headerName(2082);
        ProxyAuthenticate proxyAuthenticate = new ProxyAuthenticate();
        super.parse(proxyAuthenticate);
        return proxyAuthenticate;
    }
}

