/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ReplyTo;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class ReplyToParser
extends AddressParametersParser {
    protected ReplyToParser(Lexer lexer) {
        super(lexer);
    }

    public ReplyToParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        ReplyTo replyTo = new ReplyTo();
        if (debug) {
            this.dbg_enter("ReplyTo.parse");
        }
        try {
            this.headerName(2106);
            replyTo.setHeaderName("Reply-To");
            super.parse(replyTo);
            return replyTo;
        }
        finally {
            if (debug) {
                this.dbg_leave("ReplyTo.parse");
            }
        }
    }
}

