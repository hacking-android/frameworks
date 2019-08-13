/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.core.LexerCore;
import gov.nist.core.NameValue;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PAccessNetworkInfo;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.TokenTypes;
import java.text.ParseException;

public class PAccessNetworkInfoParser
extends HeaderParser
implements TokenTypes {
    protected PAccessNetworkInfoParser(Lexer lexer) {
        super(lexer);
    }

    public PAccessNetworkInfoParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("AccessNetworkInfoParser.parse");
        }
        try {
            this.headerName(2127);
            PAccessNetworkInfo pAccessNetworkInfo = new PAccessNetworkInfo();
            pAccessNetworkInfo.setHeaderName("P-Access-Network-Info");
            this.lexer.SPorHT();
            this.lexer.match(4095);
            pAccessNetworkInfo.setAccessType(this.lexer.getNextToken().getTokenValue());
            this.lexer.SPorHT();
            while (this.lexer.lookAhead(0) == ';') {
                this.lexer.match(59);
                this.lexer.SPorHT();
                pAccessNetworkInfo.setParameter(super.nameValue('='));
                this.lexer.SPorHT();
            }
            this.lexer.SPorHT();
            this.lexer.match(10);
            return pAccessNetworkInfo;
        }
        finally {
            if (debug) {
                this.dbg_leave("AccessNetworkInfoParser.parse");
            }
        }
    }
}

