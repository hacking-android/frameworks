/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.RecordRoute;
import gov.nist.javax.sip.header.RecordRouteList;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class RecordRouteParser
extends AddressParametersParser {
    protected RecordRouteParser(Lexer lexer) {
        super(lexer);
    }

    public RecordRouteParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        char c;
        RecordRouteList recordRouteList = new RecordRouteList();
        if (debug) {
            this.dbg_enter("RecordRouteParser.parse");
        }
        try {
            this.lexer.match(2092);
            this.lexer.SPorHT();
            this.lexer.match(58);
            this.lexer.SPorHT();
            do {
                RecordRoute recordRoute = new RecordRoute();
                super.parse(recordRoute);
                recordRouteList.add(recordRoute);
                this.lexer.SPorHT();
                c = this.lexer.lookAhead(0);
                if (c != ',') break;
                break;
            } while (true);
        }
        catch (Throwable throwable) {
            if (debug) {
                this.dbg_leave("RecordRouteParser.parse");
            }
            throw throwable;
        }
        {
            this.lexer.match(44);
            this.lexer.SPorHT();
            continue;
        }
        if (c == '\n') {
            if (debug) {
                this.dbg_leave("RecordRouteParser.parse");
            }
            return recordRouteList;
        }
        throw this.createParseException("unexpected char");
    }
}

