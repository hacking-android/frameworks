/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.ExtensionHeaderImpl;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.Parser;
import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;

public class HeaderParser
extends Parser {
    protected HeaderParser(Lexer lexer) {
        this.lexer = lexer;
        this.lexer.selectLexer("command_keywordLexer");
    }

    protected HeaderParser(String string) {
        this.lexer = new Lexer("command_keywordLexer", string);
    }

    protected Calendar date() throws ParseException {
        block27 : {
            Calendar calendar;
            int n;
            try {
                calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                n = Integer.parseInt(this.lexer.number());
                if (n <= 0 || n > 31) break block27;
            }
            catch (Exception exception) {
                throw this.createParseException("bad date field");
            }
            calendar.set(5, n);
            this.lexer.match(32);
            String string = this.lexer.ttoken().toLowerCase();
            if (string.equals("jan")) {
                calendar.set(2, 0);
            } else if (string.equals("feb")) {
                calendar.set(2, 1);
            } else if (string.equals("mar")) {
                calendar.set(2, 2);
            } else if (string.equals("apr")) {
                calendar.set(2, 3);
            } else if (string.equals("may")) {
                calendar.set(2, 4);
            } else if (string.equals("jun")) {
                calendar.set(2, 5);
            } else if (string.equals("jul")) {
                calendar.set(2, 6);
            } else if (string.equals("aug")) {
                calendar.set(2, 7);
            } else if (string.equals("sep")) {
                calendar.set(2, 8);
            } else if (string.equals("oct")) {
                calendar.set(2, 9);
            } else if (string.equals("nov")) {
                calendar.set(2, 10);
            } else if (string.equals("dec")) {
                calendar.set(2, 11);
            }
            this.lexer.match(32);
            calendar.set(1, Integer.parseInt(this.lexer.number()));
            return calendar;
        }
        throw this.createParseException("Bad day ");
    }

    protected void headerName(int n) throws ParseException {
        this.lexer.match(n);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
    }

    public SIPHeader parse() throws ParseException {
        Object object = this.lexer.getNextToken(':');
        this.lexer.consume(1);
        String string = this.lexer.getLine().trim();
        object = new ExtensionHeaderImpl((String)object);
        ((ExtensionHeaderImpl)object).setValue(string);
        return object;
    }

    protected void time(Calendar calendar) throws ParseException {
        try {
            calendar.set(11, Integer.parseInt(this.lexer.number()));
            this.lexer.match(58);
            calendar.set(12, Integer.parseInt(this.lexer.number()));
            this.lexer.match(58);
            calendar.set(13, Integer.parseInt(this.lexer.number()));
            return;
        }
        catch (Exception exception) {
            throw this.createParseException("error processing time ");
        }
    }

    protected int wkday() throws ParseException {
        block15 : {
            String string;
            boolean bl;
            block14 : {
                block13 : {
                    block12 : {
                        block11 : {
                            block10 : {
                                block9 : {
                                    this.dbg_enter("wkday");
                                    try {
                                        string = this.lexer.ttoken().toLowerCase();
                                        bl = "Mon".equalsIgnoreCase(string);
                                        if (!bl) break block9;
                                    }
                                    catch (Throwable throwable) {
                                        this.dbg_leave("wkday");
                                        throw throwable;
                                    }
                                    this.dbg_leave("wkday");
                                    return 2;
                                }
                                bl = "Tue".equalsIgnoreCase(string);
                                if (!bl) break block10;
                                this.dbg_leave("wkday");
                                return 3;
                            }
                            bl = "Wed".equalsIgnoreCase(string);
                            if (!bl) break block11;
                            this.dbg_leave("wkday");
                            return 4;
                        }
                        bl = "Thu".equalsIgnoreCase(string);
                        if (!bl) break block12;
                        this.dbg_leave("wkday");
                        return 5;
                    }
                    bl = "Fri".equalsIgnoreCase(string);
                    if (!bl) break block13;
                    this.dbg_leave("wkday");
                    return 6;
                }
                bl = "Sat".equalsIgnoreCase(string);
                if (!bl) break block14;
                this.dbg_leave("wkday");
                return 7;
            }
            bl = "Sun".equalsIgnoreCase(string);
            if (!bl) break block15;
            this.dbg_leave("wkday");
            return 1;
        }
        throw this.createParseException("bad wkday");
    }
}

