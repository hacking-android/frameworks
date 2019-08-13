/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PCalledPartyID;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class PCalledPartyIDParser
extends AddressParametersParser {
    protected PCalledPartyIDParser(Lexer lexer) {
        super(lexer);
    }

    public PCalledPartyIDParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("PCalledPartyIDParser.parse");
        }
        try {
            this.lexer.match(2128);
            this.lexer.SPorHT();
            this.lexer.match(58);
            this.lexer.SPorHT();
            PCalledPartyID pCalledPartyID = new PCalledPartyID();
            super.parse(pCalledPartyID);
            return pCalledPartyID;
        }
        finally {
            if (debug) {
                this.dbg_leave("PCalledPartyIDParser.parse");
            }
        }
    }
}

