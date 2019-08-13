/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.Event;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.text.ParseException;

public class EventParser
extends ParametersParser {
    protected EventParser(Lexer lexer) {
        super(lexer);
    }

    public EventParser(String string) {
        super(string);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public SIPHeader parse() throws ParseException {
        Throwable throwable2222;
        if (debug) {
            this.dbg_enter("EventParser.parse");
        }
        this.headerName(2111);
        this.lexer.SPorHT();
        Event event = new Event();
        this.lexer.match(4095);
        event.setEventType(this.lexer.getNextToken().getTokenValue());
        super.parse(event);
        this.lexer.SPorHT();
        this.lexer.match(10);
        if (!debug) return event;
        this.dbg_leave("EventParser.parse");
        return event;
        {
            catch (Throwable throwable2222) {
            }
            catch (ParseException parseException) {}
            {
                throw this.createParseException(parseException.getMessage());
            }
        }
        if (!debug) throw throwable2222;
        this.dbg_leave("EventParser.parse");
        throw throwable2222;
    }
}

