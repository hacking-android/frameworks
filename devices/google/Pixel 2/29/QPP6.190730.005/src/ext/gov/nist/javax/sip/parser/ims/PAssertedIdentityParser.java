/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PAssertedIdentity;
import gov.nist.javax.sip.header.ims.PAssertedIdentityList;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.TokenTypes;
import java.text.ParseException;

public class PAssertedIdentityParser
extends AddressParametersParser
implements TokenTypes {
    protected PAssertedIdentityParser(Lexer lexer) {
        super(lexer);
    }

    public PAssertedIdentityParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("AssertedIdentityParser.parse");
        }
        PAssertedIdentityList pAssertedIdentityList = new PAssertedIdentityList();
        try {
            this.headerName(2121);
            PAssertedIdentity pAssertedIdentity = new PAssertedIdentity();
            pAssertedIdentity.setHeaderName("P-Asserted-Identity");
            super.parse(pAssertedIdentity);
            pAssertedIdentityList.add(pAssertedIdentity);
            this.lexer.SPorHT();
            while (this.lexer.lookAhead(0) == ',') {
                this.lexer.match(44);
                this.lexer.SPorHT();
                pAssertedIdentity = new PAssertedIdentity();
                super.parse(pAssertedIdentity);
                pAssertedIdentityList.add(pAssertedIdentity);
                this.lexer.SPorHT();
            }
            this.lexer.SPorHT();
            this.lexer.match(10);
            return pAssertedIdentityList;
        }
        finally {
            if (debug) {
                this.dbg_leave("AssertedIdentityParser.parse");
            }
        }
    }
}

