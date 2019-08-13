/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AcceptEncoding;
import gov.nist.javax.sip.header.AcceptEncodingList;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

public class AcceptEncodingParser
extends HeaderParser {
    protected AcceptEncodingParser(Lexer lexer) {
        super(lexer);
    }

    public AcceptEncodingParser(String string) {
        super(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SIPHeader parse() throws ParseException {
        AcceptEncodingList acceptEncodingList = new AcceptEncodingList();
        if (debug) {
            this.dbg_enter("AcceptEncodingParser.parse");
        }
        try {
            this.headerName(2067);
            if (this.lexer.lookAhead(0) == '\n') {
                AcceptEncoding acceptEncoding = new AcceptEncoding();
                acceptEncodingList.add(acceptEncoding);
                return acceptEncodingList;
            }
            while (this.lexer.lookAhead(0) != '\n') {
                AcceptEncoding acceptEncoding = new AcceptEncoding();
                if (this.lexer.lookAhead(0) != ';') {
                    this.lexer.match(4095);
                    acceptEncoding.setEncoding(this.lexer.getNextToken().getTokenValue());
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
                        acceptEncoding.setQValue(Float.parseFloat(token.getTokenValue()));
                    }
                    catch (InvalidArgumentException invalidArgumentException) {
                        throw this.createParseException(invalidArgumentException.getMessage());
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw this.createParseException(numberFormatException.getMessage());
                    }
                    this.lexer.SPorHT();
                }
                acceptEncodingList.add(acceptEncoding);
                if (this.lexer.lookAhead(0) != ',') continue;
                this.lexer.match(44);
                this.lexer.SPorHT();
            }
            return acceptEncodingList;
        }
        finally {
            if (debug) {
                this.dbg_leave("AcceptEncodingParser.parse");
            }
        }
    }
}

