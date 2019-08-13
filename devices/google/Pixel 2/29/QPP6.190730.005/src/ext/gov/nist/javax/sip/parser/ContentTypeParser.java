/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.ContentType;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import java.text.ParseException;

public class ContentTypeParser
extends ParametersParser {
    protected ContentTypeParser(Lexer lexer) {
        super(lexer);
    }

    public ContentTypeParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        ContentType contentType = new ContentType();
        if (debug) {
            this.dbg_enter("ContentTypeParser.parse");
        }
        try {
            this.headerName(2086);
            this.lexer.match(4095);
            Token token = this.lexer.getNextToken();
            this.lexer.SPorHT();
            contentType.setContentType(token.getTokenValue());
            this.lexer.match(47);
            this.lexer.match(4095);
            token = this.lexer.getNextToken();
            this.lexer.SPorHT();
            contentType.setContentSubType(token.getTokenValue());
            super.parse(contentType);
            this.lexer.match(10);
            return contentType;
        }
        finally {
            if (debug) {
                this.dbg_leave("ContentTypeParser.parse");
            }
        }
    }
}

