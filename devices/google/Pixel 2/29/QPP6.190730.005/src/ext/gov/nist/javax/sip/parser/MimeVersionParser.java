/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.MimeVersion;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

public class MimeVersionParser
extends HeaderParser {
    protected MimeVersionParser(Lexer lexer) {
        super(lexer);
    }

    public MimeVersionParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("MimeVersionParser.parse");
        }
        MimeVersion mimeVersion = new MimeVersion();
        try {
            this.headerName(2060);
            mimeVersion.setHeaderName("MIME-Version");
            try {
                mimeVersion.setMajorVersion(Integer.parseInt(this.lexer.number()));
                this.lexer.match(46);
                mimeVersion.setMinorVersion(Integer.parseInt(this.lexer.number()));
                this.lexer.SPorHT();
                this.lexer.match(10);
            }
            catch (InvalidArgumentException invalidArgumentException) {
                throw this.createParseException(invalidArgumentException.getMessage());
            }
            return mimeVersion;
        }
        finally {
            if (debug) {
                this.dbg_leave("MimeVersionParser.parse");
            }
        }
    }
}

