/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.SIPDateHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;

public class DateParser
extends HeaderParser {
    protected DateParser(Lexer lexer) {
        super(lexer);
    }

    public DateParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("DateParser.parse");
        }
        try {
            this.headerName(2080);
            this.wkday();
            this.lexer.match(44);
            this.lexer.match(32);
            Serializable serializable = this.date();
            this.lexer.match(32);
            this.time((Calendar)serializable);
            this.lexer.match(32);
            Object object = this.lexer.ttoken().toLowerCase();
            if ("gmt".equals(object)) {
                this.lexer.match(10);
                object = new SIPDateHeader();
                ((SIPDateHeader)object).setDate((Calendar)serializable);
                return object;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Bad Time Zone ");
            ((StringBuilder)serializable).append((String)object);
            throw this.createParseException(((StringBuilder)serializable).toString());
        }
        finally {
            if (debug) {
                this.dbg_leave("DateParser.parse");
            }
        }
    }
}

