/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.Expires;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

public class ExpiresParser
extends HeaderParser {
    protected ExpiresParser(Lexer lexer) {
        super(lexer);
    }

    public ExpiresParser(String string) {
        super(string);
    }

    /*
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public SIPHeader parse() throws ParseException {
        Expires expires = new Expires();
        if (debug) {
            this.dbg_enter("parse");
        }
        try {
            this.lexer.match(2090);
            this.lexer.SPorHT();
            this.lexer.match(58);
            this.lexer.SPorHT();
            String string = this.lexer.getNextId();
            this.lexer.match(10);
            try {
                expires.setExpires(Integer.parseInt(string));
                return expires;
            }
            catch (InvalidArgumentException invalidArgumentException) {
                throw this.createParseException(invalidArgumentException.getMessage());
            }
            catch (NumberFormatException numberFormatException) {
                throw this.createParseException("bad integer format");
                {
                    catch (Throwable throwable) {
                        throw throwable;
                    }
                }
            }
        }
        finally {
            if (debug) {
                this.dbg_leave("parse");
            }
        }
    }
}

