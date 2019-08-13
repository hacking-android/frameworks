/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SubscriptionState;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

public class SubscriptionStateParser
extends HeaderParser {
    protected SubscriptionStateParser(Lexer lexer) {
        super(lexer);
    }

    public SubscriptionStateParser(String string) {
        super(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("SubscriptionStateParser.parse");
        }
        SubscriptionState subscriptionState = new SubscriptionState();
        try {
            this.headerName(2104);
            subscriptionState.setHeaderName("Subscription-State");
            this.lexer.match(4095);
            subscriptionState.setState(this.lexer.getNextToken().getTokenValue());
            while (this.lexer.lookAhead(0) == ';') {
                this.lexer.match(59);
                this.lexer.SPorHT();
                this.lexer.match(4095);
                String string = this.lexer.getNextToken().getTokenValue();
                if (string.equalsIgnoreCase("reason")) {
                    this.lexer.match(61);
                    this.lexer.SPorHT();
                    this.lexer.match(4095);
                    subscriptionState.setReasonCode(this.lexer.getNextToken().getTokenValue());
                } else if (string.equalsIgnoreCase("expires")) {
                    this.lexer.match(61);
                    this.lexer.SPorHT();
                    this.lexer.match(4095);
                    string = this.lexer.getNextToken().getTokenValue();
                    try {
                        subscriptionState.setExpires(Integer.parseInt(string));
                    }
                    catch (InvalidArgumentException invalidArgumentException) {
                        throw this.createParseException(invalidArgumentException.getMessage());
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw this.createParseException(numberFormatException.getMessage());
                    }
                } else if (string.equalsIgnoreCase("retry-after")) {
                    this.lexer.match(61);
                    this.lexer.SPorHT();
                    this.lexer.match(4095);
                    string = this.lexer.getNextToken().getTokenValue();
                    try {
                        subscriptionState.setRetryAfter(Integer.parseInt(string));
                    }
                    catch (InvalidArgumentException invalidArgumentException) {
                        throw this.createParseException(invalidArgumentException.getMessage());
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw this.createParseException(numberFormatException.getMessage());
                    }
                } else {
                    this.lexer.match(61);
                    this.lexer.SPorHT();
                    this.lexer.match(4095);
                    subscriptionState.setParameter(string, this.lexer.getNextToken().getTokenValue());
                }
                this.lexer.SPorHT();
            }
            return subscriptionState;
        }
        finally {
            if (debug) {
                this.dbg_leave("SubscriptionStateParser.parse");
            }
        }
    }
}

