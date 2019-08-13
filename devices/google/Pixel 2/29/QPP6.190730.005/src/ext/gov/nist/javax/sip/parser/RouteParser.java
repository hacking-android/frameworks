/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.Route;
import gov.nist.javax.sip.header.RouteList;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class RouteParser
extends AddressParametersParser {
    protected RouteParser(Lexer lexer) {
        super(lexer);
    }

    public RouteParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        char c;
        RouteList routeList = new RouteList();
        if (debug) {
            this.dbg_enter("parse");
        }
        try {
            this.lexer.match(2070);
            this.lexer.SPorHT();
            this.lexer.match(58);
            this.lexer.SPorHT();
            do {
                Route route = new Route();
                super.parse(route);
                routeList.add(route);
                this.lexer.SPorHT();
                c = this.lexer.lookAhead(0);
                if (c != ',') break;
                break;
            } while (true);
        }
        catch (Throwable throwable) {
            if (debug) {
                this.dbg_leave("parse");
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
                this.dbg_leave("parse");
            }
            return routeList;
        }
        throw this.createParseException("unexpected char");
    }
}

