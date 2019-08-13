/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.extensions;

import gov.nist.core.LexerCore;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.Replaces;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.io.PrintStream;
import java.text.ParseException;

public class ReplacesParser
extends ParametersParser {
    protected ReplacesParser(Lexer lexer) {
        super(lexer);
    }

    public ReplacesParser(String string) {
        super(string);
    }

    public static void main(String[] object) throws ParseException {
        String[] arrstring = new String[]{"Replaces: 12345th5z8z\n", "Replaces: 12345th5z8z;to-tag=tozght6-45;from-tag=fromzght789-337-2\n"};
        for (int i = 0; i < arrstring.length; ++i) {
            object = (Replaces)new ReplacesParser(arrstring[i]).parse();
            Appendable appendable = System.out;
            Appendable appendable2 = new StringBuilder();
            ((StringBuilder)appendable2).append("Parsing => ");
            ((StringBuilder)appendable2).append(arrstring[i]);
            ((PrintStream)appendable).println(((StringBuilder)appendable2).toString());
            appendable = System.out;
            appendable2 = new StringBuilder();
            ((StringBuilder)appendable2).append("encoded = ");
            ((StringBuilder)appendable2).append(((SIPHeader)object).encode());
            ((StringBuilder)appendable2).append("==> ");
            ((PrintStream)appendable).print(((StringBuilder)appendable2).toString());
            appendable2 = System.out;
            appendable = new StringBuilder();
            ((StringBuilder)appendable).append("callId ");
            ((StringBuilder)appendable).append(((Replaces)object).getCallId());
            ((StringBuilder)appendable).append(" from-tag=");
            ((StringBuilder)appendable).append(((Replaces)object).getFromTag());
            ((StringBuilder)appendable).append(" to-tag=");
            ((StringBuilder)appendable).append(((Replaces)object).getToTag());
            ((PrintStream)appendable2).println(((StringBuilder)appendable).toString());
        }
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("parse");
        }
        try {
            this.headerName(2135);
            Replaces replaces = new Replaces();
            this.lexer.SPorHT();
            String string = this.lexer.byteStringNoSemicolon();
            this.lexer.SPorHT();
            super.parse(replaces);
            replaces.setCallId(string);
            return replaces;
        }
        finally {
            if (debug) {
                this.dbg_leave("parse");
            }
        }
    }
}

