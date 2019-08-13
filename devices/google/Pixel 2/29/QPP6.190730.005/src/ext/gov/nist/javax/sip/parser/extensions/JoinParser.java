/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.extensions;

import gov.nist.core.LexerCore;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.Join;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.io.PrintStream;
import java.text.ParseException;

public class JoinParser
extends ParametersParser {
    protected JoinParser(Lexer lexer) {
        super(lexer);
    }

    public JoinParser(String string) {
        super(string);
    }

    public static void main(String[] object) throws ParseException {
        String[] arrstring = new String[]{"Join: 12345th5z8z\n", "Join: 12345th5z8z;to-tag=tozght6-45;from-tag=fromzght789-337-2\n"};
        for (int i = 0; i < arrstring.length; ++i) {
            object = (Join)new JoinParser(arrstring[i]).parse();
            Appendable appendable = System.out;
            Appendable appendable2 = new StringBuilder();
            ((StringBuilder)appendable2).append("Parsing => ");
            ((StringBuilder)appendable2).append(arrstring[i]);
            ((PrintStream)appendable).println(((StringBuilder)appendable2).toString());
            appendable2 = System.out;
            appendable = new StringBuilder();
            ((StringBuilder)appendable).append("encoded = ");
            ((StringBuilder)appendable).append(((SIPHeader)object).encode());
            ((StringBuilder)appendable).append("==> ");
            ((PrintStream)appendable2).print(((StringBuilder)appendable).toString());
            appendable = System.out;
            appendable2 = new StringBuilder();
            ((StringBuilder)appendable2).append("callId ");
            ((StringBuilder)appendable2).append(((Join)object).getCallId());
            ((StringBuilder)appendable2).append(" from-tag=");
            ((StringBuilder)appendable2).append(((Join)object).getFromTag());
            ((StringBuilder)appendable2).append(" to-tag=");
            ((StringBuilder)appendable2).append(((Join)object).getToTag());
            ((PrintStream)appendable).println(((StringBuilder)appendable2).toString());
        }
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("parse");
        }
        try {
            this.headerName(2140);
            Join join = new Join();
            this.lexer.SPorHT();
            String string = this.lexer.byteStringNoSemicolon();
            this.lexer.SPorHT();
            super.parse(join);
            join.setCallId(string);
            return join;
        }
        finally {
            if (debug) {
                this.dbg_leave("parse");
            }
        }
    }
}

