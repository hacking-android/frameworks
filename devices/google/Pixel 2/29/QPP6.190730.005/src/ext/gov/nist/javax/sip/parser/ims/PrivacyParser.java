/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.ims.Privacy;
import gov.nist.javax.sip.header.ims.PrivacyList;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.TokenTypes;
import java.io.PrintStream;
import java.text.ParseException;

public class PrivacyParser
extends HeaderParser
implements TokenTypes {
    protected PrivacyParser(Lexer lexer) {
        super(lexer);
    }

    public PrivacyParser(String string) {
        super(string);
    }

    public static void main(String[] object) throws ParseException {
        String[] arrstring = new String[]{"Privacy: none\n", "Privacy: none;id;user\n"};
        for (int i = 0; i < arrstring.length; ++i) {
            object = (PrivacyList)new PrivacyParser(arrstring[i]).parse();
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("encoded = ");
            stringBuilder.append(((SIPHeaderList)object).encode());
            printStream.println(stringBuilder.toString());
        }
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("PrivacyParser.parse");
        }
        PrivacyList privacyList = new PrivacyList();
        try {
            this.headerName(2126);
            while (this.lexer.lookAhead(0) != '\n') {
                this.lexer.SPorHT();
                Privacy privacy = new Privacy();
                privacy.setHeaderName("Privacy");
                this.lexer.match(4095);
                privacy.setPrivacy(this.lexer.getNextToken().getTokenValue());
                this.lexer.SPorHT();
                privacyList.add(privacy);
                while (this.lexer.lookAhead(0) == ';') {
                    this.lexer.match(59);
                    this.lexer.SPorHT();
                    privacy = new Privacy();
                    this.lexer.match(4095);
                    privacy.setPrivacy(this.lexer.getNextToken().getTokenValue());
                    this.lexer.SPorHT();
                    privacyList.add(privacy);
                }
            }
            return privacyList;
        }
        finally {
            if (debug) {
                this.dbg_leave("PrivacyParser.parse");
            }
        }
    }
}

