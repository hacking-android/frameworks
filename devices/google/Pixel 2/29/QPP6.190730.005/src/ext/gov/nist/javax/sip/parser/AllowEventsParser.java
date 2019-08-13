/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AllowEvents;
import gov.nist.javax.sip.header.AllowEventsList;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class AllowEventsParser
extends HeaderParser {
    protected AllowEventsParser(Lexer lexer) {
        super(lexer);
    }

    public AllowEventsParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("AllowEventsParser.parse");
        }
        AllowEventsList allowEventsList = new AllowEventsList();
        try {
            this.headerName(2113);
            AllowEvents allowEvents = new AllowEvents();
            allowEvents.setHeaderName("Allow-Events");
            this.lexer.SPorHT();
            this.lexer.match(4095);
            allowEvents.setEventType(this.lexer.getNextToken().getTokenValue());
            allowEventsList.add(allowEvents);
            this.lexer.SPorHT();
            while (this.lexer.lookAhead(0) == ',') {
                this.lexer.match(44);
                this.lexer.SPorHT();
                allowEvents = new AllowEvents();
                this.lexer.match(4095);
                allowEvents.setEventType(this.lexer.getNextToken().getTokenValue());
                allowEventsList.add(allowEvents);
                this.lexer.SPorHT();
            }
            this.lexer.SPorHT();
            this.lexer.match(10);
            return allowEventsList;
        }
        finally {
            if (debug) {
                this.dbg_leave("AllowEventsParser.parse");
            }
        }
    }
}

