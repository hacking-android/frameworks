/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PProfileKey;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.TokenTypes;
import java.text.ParseException;

public class PProfileKeyParser
extends AddressParametersParser
implements TokenTypes {
    protected PProfileKeyParser(Lexer lexer) {
        super(lexer);
    }

    public PProfileKeyParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("PProfileKey.parse");
        }
        try {
            this.lexer.match(2142);
            this.lexer.SPorHT();
            this.lexer.match(58);
            this.lexer.SPorHT();
            PProfileKey pProfileKey = new PProfileKey();
            super.parse(pProfileKey);
            return pProfileKey;
        }
        finally {
            if (debug) {
                this.dbg_leave("PProfileKey.parse");
            }
        }
    }
}

