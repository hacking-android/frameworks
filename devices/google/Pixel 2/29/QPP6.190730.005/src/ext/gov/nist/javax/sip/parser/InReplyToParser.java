/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.InReplyTo;
import gov.nist.javax.sip.header.InReplyToList;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class InReplyToParser
extends HeaderParser {
    protected InReplyToParser(Lexer lexer) {
        super(lexer);
    }

    public InReplyToParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("InReplyToParser.parse");
        }
        InReplyToList inReplyToList = new InReplyToList();
        try {
            this.headerName(2059);
            while (this.lexer.lookAhead(0) != '\n') {
                Object object;
                Object object2;
                Object object3 = new InReplyTo();
                ((SIPHeader)object3).setHeaderName("In-Reply-To");
                this.lexer.match(4095);
                Object object4 = this.lexer.getNextToken();
                char c = this.lexer.lookAhead(0);
                if (c == '@') {
                    this.lexer.match(64);
                    this.lexer.match(4095);
                    object2 = this.lexer.getNextToken();
                    object = new StringBuilder();
                    ((StringBuilder)object).append(((Token)object4).getTokenValue());
                    ((StringBuilder)object).append("@");
                    ((StringBuilder)object).append(((Token)object2).getTokenValue());
                    ((InReplyTo)object3).setCallId(((StringBuilder)object).toString());
                } else {
                    ((InReplyTo)object3).setCallId(((Token)object4).getTokenValue());
                }
                this.lexer.SPorHT();
                inReplyToList.add(object3);
                while (this.lexer.lookAhead(0) == ',') {
                    this.lexer.match(44);
                    this.lexer.SPorHT();
                    object4 = new InReplyTo();
                    this.lexer.match(4095);
                    object3 = this.lexer.getNextToken();
                    if (this.lexer.lookAhead(0) == '@') {
                        this.lexer.match(64);
                        this.lexer.match(4095);
                        object = this.lexer.getNextToken();
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append(((Token)object3).getTokenValue());
                        ((StringBuilder)object2).append("@");
                        ((StringBuilder)object2).append(((Token)object).getTokenValue());
                        ((InReplyTo)object4).setCallId(((StringBuilder)object2).toString());
                    } else {
                        ((InReplyTo)object4).setCallId(((Token)object3).getTokenValue());
                    }
                    inReplyToList.add(object4);
                }
            }
            return inReplyToList;
        }
        finally {
            if (debug) {
                this.dbg_leave("InReplyToParser.parse");
            }
        }
    }
}

