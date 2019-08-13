/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.address.AddressFactoryImpl;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PServedUser;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import gov.nist.javax.sip.parser.TokenTypes;
import java.text.ParseException;
import javax.sip.address.Address;

public class PServedUserParser
extends ParametersParser
implements TokenTypes {
    protected PServedUserParser(Lexer lexer) {
        super(lexer);
    }

    public PServedUserParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("PServedUser.parse");
        }
        try {
            this.lexer.match(2143);
            this.lexer.SPorHT();
            this.lexer.match(58);
            this.lexer.SPorHT();
            PServedUser pServedUser = new PServedUser();
            this.lexer.SPorHT();
            String string = this.lexer.byteStringNoSemicolon();
            AddressFactoryImpl addressFactoryImpl = new AddressFactoryImpl();
            pServedUser.setAddress(addressFactoryImpl.createAddress(string));
            super.parse(pServedUser);
            return pServedUser;
        }
        finally {
            if (debug) {
                this.dbg_leave("PServedUser.parse");
            }
        }
    }
}

