/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.extensions;

import gov.nist.core.LexerCore;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.MinSE;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.io.PrintStream;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

public class MinSEParser
extends ParametersParser {
    protected MinSEParser(Lexer lexer) {
        super(lexer);
    }

    public MinSEParser(String string) {
        super(string);
    }

    public static void main(String[] object) throws ParseException {
        String[] arrstring = new String[]{"Min-SE: 30\n", "Min-SE: 45;some-param=somevalue\n"};
        for (int i = 0; i < arrstring.length; ++i) {
            object = (MinSE)new MinSEParser(arrstring[i]).parse();
            Appendable appendable = System.out;
            Appendable appendable2 = new StringBuilder();
            ((StringBuilder)appendable2).append("encoded = ");
            ((StringBuilder)appendable2).append(((SIPHeader)object).encode());
            ((PrintStream)appendable).println(((StringBuilder)appendable2).toString());
            appendable = System.out;
            appendable2 = new StringBuilder();
            ((StringBuilder)appendable2).append("\ntime=");
            ((StringBuilder)appendable2).append(((MinSE)object).getExpires());
            ((PrintStream)appendable).println(((StringBuilder)appendable2).toString());
            if (((ParametersHeader)object).getParameter("some-param") == null) continue;
            appendable2 = System.out;
            appendable = new StringBuilder();
            ((StringBuilder)appendable).append("some-param=");
            ((StringBuilder)appendable).append(((ParametersHeader)object).getParameter("some-param"));
            ((PrintStream)appendable2).println(((StringBuilder)appendable).toString());
        }
    }

    /*
     * Loose catch block
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public SIPHeader parse() throws ParseException {
        MinSE minSE = new MinSE();
        if (debug) {
            this.dbg_enter("parse");
        }
        try {
            this.headerName(2134);
            String string = this.lexer.getNextId();
            try {
                minSE.setExpires(Integer.parseInt(string));
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
            super.parse(minSE);
            return minSE;
        }
        finally {
            if (debug) {
                this.dbg_leave("parse");
            }
        }
    }
}

