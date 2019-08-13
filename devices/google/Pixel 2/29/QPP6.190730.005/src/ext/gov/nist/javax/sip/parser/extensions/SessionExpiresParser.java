/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.extensions;

import gov.nist.core.LexerCore;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.SessionExpires;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.io.PrintStream;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

public class SessionExpiresParser
extends ParametersParser {
    protected SessionExpiresParser(Lexer lexer) {
        super(lexer);
    }

    public SessionExpiresParser(String string) {
        super(string);
    }

    public static void main(String[] object) throws ParseException {
        String[] arrstring = new String[]{"Session-Expires: 30\n", "Session-Expires: 45;refresher=uac\n"};
        for (int i = 0; i < arrstring.length; ++i) {
            object = (SessionExpires)new SessionExpiresParser(arrstring[i]).parse();
            Appendable appendable = System.out;
            Appendable appendable2 = new StringBuilder();
            ((StringBuilder)appendable2).append("encoded = ");
            ((StringBuilder)appendable2).append(((SIPHeader)object).encode());
            ((PrintStream)appendable).println(((StringBuilder)appendable2).toString());
            appendable2 = System.out;
            appendable = new StringBuilder();
            ((StringBuilder)appendable).append("\ntime=");
            ((StringBuilder)appendable).append(((SessionExpires)object).getExpires());
            ((PrintStream)appendable2).println(((StringBuilder)appendable).toString());
            if (((ParametersHeader)object).getParameter("refresher") == null) continue;
            appendable = System.out;
            appendable2 = new StringBuilder();
            ((StringBuilder)appendable2).append("refresher=");
            ((StringBuilder)appendable2).append(((ParametersHeader)object).getParameter("refresher"));
            ((PrintStream)appendable).println(((StringBuilder)appendable2).toString());
        }
    }

    /*
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public SIPHeader parse() throws ParseException {
        SessionExpires sessionExpires = new SessionExpires();
        if (debug) {
            this.dbg_enter("parse");
        }
        try {
            this.headerName(2133);
            String string = this.lexer.getNextId();
            try {
                sessionExpires.setExpires(Integer.parseInt(string));
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
            this.lexer.SPorHT();
            super.parse(sessionExpires);
            return sessionExpires;
        }
        finally {
            if (debug) {
                this.dbg_leave("parse");
            }
        }
    }
}

