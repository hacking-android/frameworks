/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.Priority;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.io.PrintStream;
import java.text.ParseException;

public class PriorityParser
extends HeaderParser {
    protected PriorityParser(Lexer lexer) {
        super(lexer);
    }

    public PriorityParser(String string) {
        super(string);
    }

    public static void main(String[] object) throws ParseException {
        String[] arrstring = new String[]{"Priority: 8;a\n"};
        for (int i = 0; i < arrstring.length; ++i) {
            Priority priority = (Priority)new PriorityParser(arrstring[i]).parse();
            object = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("encoded = ");
            stringBuilder.append(priority.encode());
            ((PrintStream)object).println(stringBuilder.toString());
        }
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("PriorityParser.parse");
        }
        Priority priority = new Priority();
        try {
            this.headerName(2081);
            priority.setHeaderName("Priority");
            this.lexer.SPorHT();
            priority.setPriority(this.lexer.ttokenSafe());
            this.lexer.SPorHT();
            this.lexer.match(10);
            return priority;
        }
        finally {
            if (debug) {
                this.dbg_leave("PriorityParser.parse");
            }
        }
    }
}

