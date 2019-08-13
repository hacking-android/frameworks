/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.Subject;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class SubjectParser
extends HeaderParser {
    protected SubjectParser(Lexer lexer) {
        super(lexer);
    }

    public SubjectParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        Subject subject = new Subject();
        if (debug) {
            this.dbg_enter("SubjectParser.parse");
        }
        try {
            this.headerName(2085);
            this.lexer.SPorHT();
            subject.setSubject(this.lexer.getRest().trim());
            return subject;
        }
        finally {
            if (debug) {
                this.dbg_leave("SubjectParser.parse");
            }
        }
    }
}

