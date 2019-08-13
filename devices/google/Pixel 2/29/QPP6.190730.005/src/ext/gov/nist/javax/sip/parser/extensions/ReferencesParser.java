/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.extensions;

import gov.nist.core.LexerCore;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.extensions.References;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.text.ParseException;

public class ReferencesParser
extends ParametersParser {
    protected ReferencesParser(Lexer lexer) {
        super(lexer);
    }

    public ReferencesParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("ReasonParser.parse");
        }
        try {
            this.headerName(2146);
            References references = new References();
            this.lexer.SPorHT();
            references.setCallId(this.lexer.byteStringNoSemicolon());
            super.parse(references);
            return references;
        }
        finally {
            if (debug) {
                this.dbg_leave("ReferencesParser.parse");
            }
        }
    }
}

