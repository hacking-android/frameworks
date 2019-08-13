/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.ServiceRoute;
import gov.nist.javax.sip.header.ims.ServiceRouteList;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class ServiceRouteParser
extends AddressParametersParser {
    protected ServiceRouteParser(Lexer lexer) {
        super(lexer);
    }

    public ServiceRouteParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        block6 : {
            ServiceRouteList serviceRouteList;
            block7 : {
                serviceRouteList = new ServiceRouteList();
                if (debug) {
                    this.dbg_enter("ServiceRouteParser.parse");
                }
                try {
                    this.lexer.match(2120);
                    this.lexer.SPorHT();
                    this.lexer.match(58);
                    this.lexer.SPorHT();
                    do {
                        ServiceRoute serviceRoute = new ServiceRoute();
                        super.parse(serviceRoute);
                        serviceRouteList.add(serviceRoute);
                        this.lexer.SPorHT();
                        if (this.lexer.lookAhead(0) != ',') break;
                        this.lexer.match(44);
                        this.lexer.SPorHT();
                    } while (true);
                    char c = this.lexer.lookAhead(0);
                    if (c != '\n') break block6;
                    if (!debug) break block7;
                }
                catch (Throwable throwable) {
                    if (debug) {
                        this.dbg_leave("ServiceRouteParser.parse");
                    }
                    throw throwable;
                }
                this.dbg_leave("ServiceRouteParser.parse");
            }
            return serviceRouteList;
        }
        throw this.createParseException("unexpected char");
    }
}

