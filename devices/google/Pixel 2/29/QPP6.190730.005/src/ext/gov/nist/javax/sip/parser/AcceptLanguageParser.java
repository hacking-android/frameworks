/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AcceptLanguage;
import gov.nist.javax.sip.header.AcceptLanguageList;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

public class AcceptLanguageParser
extends HeaderParser {
    protected AcceptLanguageParser(Lexer lexer) {
        super(lexer);
    }

    public AcceptLanguageParser(String string) {
        super(string);
    }

    /*
     * Enabled aggressive exception aggregation
     */
    @Override
    public SIPHeader parse() throws ParseException {
        AcceptLanguageList acceptLanguageList = new AcceptLanguageList();
        if (debug) {
            this.dbg_enter("AcceptLanguageParser.parse");
        }
        try {
            this.headerName(2095);
            while (this.lexer.lookAhead(0) != '\n') {
                AcceptLanguage acceptLanguage = new AcceptLanguage();
                acceptLanguage.setHeaderName("Accept-Language");
                if (this.lexer.lookAhead(0) != ';') {
                    this.lexer.match(4095);
                    acceptLanguage.setLanguageRange(this.lexer.getNextToken().getTokenValue());
                }
                while (this.lexer.lookAhead(0) == ';') {
                    this.lexer.match(59);
                    this.lexer.SPorHT();
                    this.lexer.match(113);
                    this.lexer.SPorHT();
                    this.lexer.match(61);
                    this.lexer.SPorHT();
                    this.lexer.match(4095);
                    Token token = this.lexer.getNextToken();
                    try {
                        acceptLanguage.setQValue(Float.parseFloat(token.getTokenValue()));
                    }
                    catch (InvalidArgumentException invalidArgumentException) {
                        throw this.createParseException(invalidArgumentException.getMessage());
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw this.createParseException(numberFormatException.getMessage());
                    }
                    this.lexer.SPorHT();
                }
                acceptLanguageList.add(acceptLanguage);
                if (this.lexer.lookAhead(0) == ',') {
                    this.lexer.match(44);
                    this.lexer.SPorHT();
                    continue;
                }
                this.lexer.SPorHT();
            }
            return acceptLanguageList;
        }
        finally {
            if (debug) {
                this.dbg_leave("AcceptLanguageParser.parse");
            }
        }
    }
}

