/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.UserAgent;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.io.PrintStream;
import java.text.ParseException;

public class UserAgentParser
extends HeaderParser {
    protected UserAgentParser(Lexer lexer) {
        super(lexer);
    }

    public UserAgentParser(String string) {
        super(string);
    }

    public static void main(String[] arrstring) throws ParseException {
        arrstring = new String[]{"User-Agent: Softphone/Beta1.5 \n", "User-Agent:Nist/Beta1 (beta version) \n", "User-Agent: Nist UA (beta version)\n", "User-Agent: Nist1.0/Beta2 Ubi/vers.1.0 (very cool) \n", "User-Agent: SJphone/1.60.299a/L (SJ Labs)\n", "User-Agent: sipXecs/3.5.11 sipXecs/sipxbridge (Linux)\n"};
        for (int i = 0; i < arrstring.length; ++i) {
            Object object = new UserAgentParser(arrstring[i]);
            UserAgent userAgent = (UserAgent)((UserAgentParser)object).parse();
            PrintStream printStream = System.out;
            object = new StringBuilder();
            ((StringBuilder)object).append("encoded = ");
            ((StringBuilder)object).append(userAgent.encode());
            printStream.println(((StringBuilder)object).toString());
        }
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("UserAgentParser.parse");
        }
        UserAgent userAgent = new UserAgent();
        try {
            block15 : {
                this.headerName(2065);
                if (this.lexer.lookAhead(0) == '\n') break block15;
                while (this.lexer.lookAhead(0) != '\n' && this.lexer.lookAhead(0) != '\u0000') {
                    block18 : {
                        block17 : {
                            CharSequence charSequence;
                            CharSequence charSequence2;
                            block16 : {
                                if (this.lexer.lookAhead(0) != '(') break block16;
                                charSequence = this.lexer.comment();
                                charSequence2 = new StringBuilder();
                                ((StringBuilder)charSequence2).append('(');
                                ((StringBuilder)charSequence2).append((String)charSequence);
                                ((StringBuilder)charSequence2).append(')');
                                userAgent.addProductToken(((StringBuilder)charSequence2).toString());
                                break block17;
                            }
                            this.getLexer().SPorHT();
                            charSequence2 = this.lexer.byteStringNoSlash();
                            if (charSequence2 == null) break block18;
                            charSequence = new StringBuffer((String)charSequence2);
                            if (this.lexer.peekNextToken().getTokenType() == 47) {
                                this.lexer.match(47);
                                this.getLexer().SPorHT();
                                charSequence2 = this.lexer.byteStringNoSlash();
                                if (charSequence2 != null) {
                                    ((StringBuffer)charSequence).append("/");
                                    ((StringBuffer)charSequence).append((String)charSequence2);
                                } else {
                                    throw this.createParseException("Expected product version");
                                }
                            }
                            userAgent.addProductToken(((StringBuffer)charSequence).toString());
                        }
                        this.lexer.SPorHT();
                        continue;
                    }
                    throw this.createParseException("Expected product string");
                }
                return userAgent;
            }
            throw this.createParseException("empty header");
        }
        finally {
            if (debug) {
                this.dbg_leave("UserAgentParser.parse");
            }
        }
    }
}

