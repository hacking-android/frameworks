/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PMediaAuthorization;
import gov.nist.javax.sip.header.ims.PMediaAuthorizationList;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.TokenTypes;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

public class PMediaAuthorizationParser
extends HeaderParser
implements TokenTypes {
    public PMediaAuthorizationParser(Lexer lexer) {
        super(lexer);
    }

    public PMediaAuthorizationParser(String string) {
        super(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SIPHeader parse() throws ParseException {
        PMediaAuthorizationList pMediaAuthorizationList = new PMediaAuthorizationList();
        if (debug) {
            this.dbg_enter("MediaAuthorizationParser.parse");
        }
        try {
            this.headerName(2130);
            PMediaAuthorization pMediaAuthorization = new PMediaAuthorization();
            pMediaAuthorization.setHeaderName("P-Media-Authorization");
            while (this.lexer.lookAhead(0) != '\n') {
                this.lexer.match(4095);
                Token token = this.lexer.getNextToken();
                try {
                    pMediaAuthorization.setMediaAuthorizationToken(token.getTokenValue());
                }
                catch (InvalidArgumentException invalidArgumentException) {
                    throw this.createParseException(invalidArgumentException.getMessage());
                }
                pMediaAuthorizationList.add(pMediaAuthorization);
                this.lexer.SPorHT();
                if (this.lexer.lookAhead(0) == ',') {
                    this.lexer.match(44);
                    pMediaAuthorization = new PMediaAuthorization();
                }
                this.lexer.SPorHT();
            }
            return pMediaAuthorizationList;
        }
        finally {
            if (debug) {
                this.dbg_leave("MediaAuthorizationParser.parse");
            }
        }
    }
}

