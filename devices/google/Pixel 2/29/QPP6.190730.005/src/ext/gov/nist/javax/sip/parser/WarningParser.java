/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.Warning;
import gov.nist.javax.sip.header.WarningList;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

public class WarningParser
extends HeaderParser {
    protected WarningParser(Lexer lexer) {
        super(lexer);
    }

    public WarningParser(String string) {
        super(string);
    }

    /*
     * Enabled aggressive exception aggregation
     */
    @Override
    public SIPHeader parse() throws ParseException {
        WarningList warningList = new WarningList();
        if (debug) {
            this.dbg_enter("WarningParser.parse");
        }
        try {
            this.headerName(2078);
            while (this.lexer.lookAhead(0) != '\n') {
                Object object;
                Warning warning = new Warning();
                warning.setHeaderName("Warning");
                this.lexer.match(4095);
                Token token = this.lexer.getNextToken();
                try {
                    warning.setCode(Integer.parseInt(token.getTokenValue()));
                }
                catch (InvalidArgumentException invalidArgumentException) {
                    throw this.createParseException(invalidArgumentException.getMessage());
                }
                catch (NumberFormatException numberFormatException) {
                    throw this.createParseException(numberFormatException.getMessage());
                }
                this.lexer.SPorHT();
                this.lexer.match(4095);
                Object object2 = this.lexer.getNextToken();
                char c = this.lexer.lookAhead(0);
                if (c == ':') {
                    this.lexer.match(58);
                    this.lexer.match(4095);
                    token = this.lexer.getNextToken();
                    object = new StringBuilder();
                    ((StringBuilder)object).append(((Token)object2).getTokenValue());
                    ((StringBuilder)object).append(":");
                    ((StringBuilder)object).append(token.getTokenValue());
                    warning.setAgent(((StringBuilder)object).toString());
                } else {
                    warning.setAgent(((Token)object2).getTokenValue());
                }
                this.lexer.SPorHT();
                warning.setText(this.lexer.quotedString());
                this.lexer.SPorHT();
                warningList.add(warning);
                while (this.lexer.lookAhead(0) == ',') {
                    this.lexer.match(44);
                    this.lexer.SPorHT();
                    warning = new Warning();
                    this.lexer.match(4095);
                    token = this.lexer.getNextToken();
                    try {
                        warning.setCode(Integer.parseInt(token.getTokenValue()));
                    }
                    catch (InvalidArgumentException invalidArgumentException) {
                        throw this.createParseException(invalidArgumentException.getMessage());
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw this.createParseException(numberFormatException.getMessage());
                    }
                    this.lexer.SPorHT();
                    this.lexer.match(4095);
                    object = this.lexer.getNextToken();
                    if (this.lexer.lookAhead(0) == ':') {
                        this.lexer.match(58);
                        this.lexer.match(4095);
                        token = this.lexer.getNextToken();
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append(((Token)object).getTokenValue());
                        ((StringBuilder)object2).append(":");
                        ((StringBuilder)object2).append(token.getTokenValue());
                        warning.setAgent(((StringBuilder)object2).toString());
                    } else {
                        warning.setAgent(((Token)object).getTokenValue());
                    }
                    this.lexer.SPorHT();
                    warning.setText(this.lexer.quotedString());
                    this.lexer.SPorHT();
                    warningList.add(warning);
                }
            }
            return warningList;
        }
        finally {
            if (debug) {
                this.dbg_leave("WarningParser.parse");
            }
        }
    }
}

