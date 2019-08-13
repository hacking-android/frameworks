/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.CallID;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class CallIDParser
extends HeaderParser {
    protected CallIDParser(Lexer lexer) {
        super(lexer);
    }

    public CallIDParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("parse");
        }
        try {
            this.lexer.match(2088);
            this.lexer.SPorHT();
            this.lexer.match(58);
            this.lexer.SPorHT();
            CallID callID = new CallID();
            this.lexer.SPorHT();
            callID.setCallId(this.lexer.getRest().trim());
            return callID;
        }
        finally {
            if (debug) {
                this.dbg_leave("parse");
            }
        }
    }
}

