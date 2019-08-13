/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.javax.sip.header.StatusLine;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.Parser;
import java.text.ParseException;

public class StatusLineParser
extends Parser {
    public StatusLineParser(Lexer lexer) {
        this.lexer = lexer;
        this.lexer.selectLexer("status_lineLexer");
    }

    public StatusLineParser(String string) {
        this.lexer = new Lexer("status_lineLexer", string);
    }

    public StatusLine parse() throws ParseException {
        try {
            if (debug) {
                this.dbg_enter("parse");
            }
            StatusLine statusLine = new StatusLine();
            statusLine.setSipVersion(this.sipVersion());
            this.lexer.SPorHT();
            statusLine.setStatusCode(this.statusCode());
            this.lexer.SPorHT();
            statusLine.setReasonPhrase(this.reasonPhrase());
            this.lexer.SPorHT();
            return statusLine;
        }
        finally {
            if (debug) {
                this.dbg_leave("parse");
            }
        }
    }

    protected String reasonPhrase() throws ParseException {
        return this.lexer.getRest().trim();
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected int statusCode() throws ParseException {
        Throwable throwable2222;
        CharSequence charSequence = this.lexer.number();
        if (debug) {
            this.dbg_enter("statusCode");
        }
        int n = Integer.parseInt((String)charSequence);
        if (!debug) return n;
        this.dbg_leave("statusCode");
        return n;
        {
            catch (Throwable throwable2222) {
            }
            catch (NumberFormatException numberFormatException) {}
            {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(this.lexer.getBuffer());
                ((StringBuilder)charSequence).append(":");
                ((StringBuilder)charSequence).append(numberFormatException.getMessage());
                ParseException parseException = new ParseException(((StringBuilder)charSequence).toString(), this.lexer.getPtr());
                throw parseException;
            }
        }
        if (!debug) throw throwable2222;
        this.dbg_leave("statusCode");
        throw throwable2222;
    }
}

