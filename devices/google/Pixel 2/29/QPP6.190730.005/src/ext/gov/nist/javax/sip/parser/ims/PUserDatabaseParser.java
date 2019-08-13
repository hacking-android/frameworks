/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PUserDatabase;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ParametersParser;
import gov.nist.javax.sip.parser.TokenTypes;
import java.text.ParseException;

public class PUserDatabaseParser
extends ParametersParser
implements TokenTypes {
    public PUserDatabaseParser(Lexer lexer) {
        super(lexer);
    }

    public PUserDatabaseParser(String string) {
        super(string);
    }

    private void parseheader(PUserDatabase pUserDatabase) throws ParseException {
        StringBuffer stringBuffer = new StringBuffer();
        this.lexer.match(60);
        while (this.lexer.hasMoreChars()) {
            char c = this.lexer.getNextChar();
            if (c == '>' || c == '\n') continue;
            stringBuffer.append(c);
        }
        pUserDatabase.setDatabaseName(stringBuffer.toString());
        super.parse(pUserDatabase);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("PUserDatabase.parse");
        }
        try {
            this.lexer.match(2141);
            this.lexer.SPorHT();
            this.lexer.match(58);
            this.lexer.SPorHT();
            PUserDatabase pUserDatabase = new PUserDatabase();
            this.parseheader(pUserDatabase);
            return pUserDatabase;
        }
        finally {
            if (debug) {
                this.dbg_leave("PUserDatabase.parse");
            }
        }
    }
}

