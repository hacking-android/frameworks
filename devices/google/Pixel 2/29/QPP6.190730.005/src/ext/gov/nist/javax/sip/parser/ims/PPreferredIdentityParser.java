/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PPreferredIdentity;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.TokenTypes;
import java.text.ParseException;

public class PPreferredIdentityParser
extends AddressParametersParser
implements TokenTypes {
    protected PPreferredIdentityParser(Lexer lexer) {
        super(lexer);
    }

    public PPreferredIdentityParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("PreferredIdentityParser.parse");
        }
        try {
            this.lexer.match(2122);
            this.lexer.SPorHT();
            this.lexer.match(58);
            this.lexer.SPorHT();
            PPreferredIdentity pPreferredIdentity = new PPreferredIdentity();
            super.parse(pPreferredIdentity);
            return pPreferredIdentity;
        }
        finally {
            if (debug) {
                this.dbg_leave("PreferredIdentityParser.parse");
            }
        }
    }
}

