/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import gov.nist.core.Host;
import gov.nist.core.HostPort;
import gov.nist.core.LexerCore;
import gov.nist.core.ParserCore;
import java.io.PrintStream;
import java.io.Serializable;
import java.text.ParseException;

public class HostNameParser
extends ParserCore {
    private static LexerCore Lexer;
    private static final char[] VALID_DOMAIN_LABEL_CHAR;
    private boolean stripAddressScopeZones = false;

    static {
        VALID_DOMAIN_LABEL_CHAR = new char[]{'\ufffffffd', '-', '.'};
    }

    public HostNameParser(LexerCore lexerCore) {
        this.lexer = lexerCore;
        lexerCore.selectLexer("charLexer");
        this.stripAddressScopeZones = Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");
    }

    public HostNameParser(String string) {
        this.lexer = new LexerCore("charLexer", string);
        this.stripAddressScopeZones = Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");
    }

    private boolean isIPv6Address(String string) {
        LexerCore lexerCore;
        int n;
        int n2;
        block6 : {
            block5 : {
                lexerCore = Lexer;
                int n3 = string.indexOf(63);
                lexerCore = Lexer;
                n = string.indexOf(59);
                if (n3 == -1) break block5;
                n2 = n3;
                if (n == -1) break block6;
                n2 = n3;
                if (n3 <= n) break block6;
            }
            n2 = n;
        }
        n = n2;
        if (n2 == -1) {
            n = string.length();
        }
        string = string.substring(0, n);
        lexerCore = Lexer;
        n2 = string.indexOf(58);
        if (n2 == -1) {
            return false;
        }
        lexerCore = Lexer;
        return string.indexOf(58, n2 + 1) != -1;
    }

    public static void main(String[] arrstring) throws ParseException {
        arrstring = new String[]{"foo.bar.com:1234", "proxima.chaplin.bt.co.uk", "129.6.55.181:2345", ":1234", "foo.bar.com:         1234", "foo.bar.com     :      1234   ", "MIK_S:1234"};
        for (int i = 0; i < arrstring.length; ++i) {
            PrintStream printStream;
            Object object;
            try {
                object = new HostNameParser(arrstring[i]);
                object = ((HostNameParser)object).hostPort(true);
                printStream = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[");
                stringBuilder.append(((HostPort)object).encode());
                stringBuilder.append("]");
                printStream.println(stringBuilder.toString());
                continue;
            }
            catch (ParseException parseException) {
                printStream = System.out;
                object = new StringBuilder();
                ((StringBuilder)object).append("exception text = ");
                ((StringBuilder)object).append(parseException.getMessage());
                printStream.println(((StringBuilder)object).toString());
            }
        }
    }

    protected void consumeDomainLabel() throws ParseException {
        if (debug) {
            this.dbg_enter("domainLabel");
        }
        try {
            this.lexer.consumeValidChars(VALID_DOMAIN_LABEL_CHAR);
            return;
        }
        finally {
            if (debug) {
                this.dbg_leave("domainLabel");
            }
        }
    }

    public Host host() throws ParseException {
        if (debug) {
            this.dbg_enter("host");
        }
        try {
            Object object;
            if (this.lexer.lookAhead(0) == '[') {
                object = this.ipv6Reference();
            } else if (this.isIPv6Address(this.lexer.getRest())) {
                int n = this.lexer.getPtr();
                this.lexer.consumeValidChars(new char[]{'\ufffd', ':'});
                object = new StringBuffer("[");
                ((StringBuffer)object).append(this.lexer.getBuffer().substring(n, this.lexer.getPtr()));
                ((StringBuffer)object).append("]");
                object = ((StringBuffer)object).toString();
            } else {
                int n = this.lexer.getPtr();
                this.consumeDomainLabel();
                object = this.lexer.getBuffer().substring(n, this.lexer.getPtr());
            }
            if (((String)object).length() != 0) {
                object = new Host((String)object);
                return object;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.lexer.getBuffer());
            stringBuilder.append(": Missing host name");
            object = new ParseException(stringBuilder.toString(), this.lexer.getPtr());
            throw object;
        }
        finally {
            if (debug) {
                this.dbg_leave("host");
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public HostPort hostPort(boolean var1_1) throws ParseException {
        if (HostNameParser.debug) {
            this.dbg_enter("hostPort");
        }
        try {
            var2_2 /* !! */  = this.host();
            var3_6 = new HostPort();
            var3_6.setHost(var2_2 /* !! */ );
            if (var1_1) {
                this.lexer.SPorHT();
            }
            if (this.lexer.hasMoreChars() == false) return var3_6;
            var4_7 = this.lexer.lookAhead(0);
            if (var4_7 == '\t') return var3_6;
            if (var4_7 == '\n') return var3_6;
            if (var4_7 == '\r') return var3_6;
            if (var4_7 == ' ') return var3_6;
            if (var4_7 == '%') ** GOTO lbl37
            if (var4_7 == ',') return var3_6;
            if (var4_7 == '/') return var3_6;
            if (var4_7 != ':') {
                if (var4_7 == ';') return var3_6;
                if (var4_7 == '>') return var3_6;
                if (var4_7 == '?') return var3_6;
            } else {
                this.lexer.consume(1);
                if (var1_1) {
                    this.lexer.SPorHT();
                }
                try {
                    var3_6.setPort(Integer.parseInt(this.lexer.number()));
                    return var3_6;
                }
                catch (NumberFormatException var2_3) {
                    var2_4 = new StringBuilder();
                    var2_4.append(this.lexer.getBuffer());
                    var2_4.append(" :Error parsing port ");
                    var3_6 = new ParseException(var2_4.toString(), this.lexer.getPtr());
                    throw var3_6;
                }
lbl37: // 1 sources:
                if (this.stripAddressScopeZones) {
                    return var3_6;
                }
            }
            if (var1_1) {
                return var3_6;
            }
            var3_6 = new StringBuilder();
            var3_6.append(this.lexer.getBuffer());
            var3_6.append(" Illegal character in hostname:");
            var3_6.append(this.lexer.lookAhead(0));
            var2_2 /* !! */  = new ParseException(var3_6.toString(), this.lexer.getPtr());
            throw var2_2 /* !! */ ;
        }
        finally {
            if (HostNameParser.debug) {
                this.dbg_leave("hostPort");
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected String ipv6Reference() throws ParseException {
        CharSequence charSequence = new StringBuffer();
        if (debug) {
            this.dbg_enter("ipv6Reference");
        }
        try {
            Object object;
            if (this.stripAddressScopeZones) {
                while (this.lexer.hasMoreChars()) {
                    char c = this.lexer.lookAhead(0);
                    if (!LexerCore.isHexDigit(c) && c != '.' && c != ':' && c != '[') {
                        if (c == ']') {
                            this.lexer.consume(1);
                            ((StringBuffer)charSequence).append(c);
                            charSequence = ((StringBuffer)charSequence).toString();
                            return charSequence;
                        }
                        if (c == '%') {
                            int n;
                            this.lexer.consume(1);
                            object = this.lexer.getRest();
                            if (object != null && ((String)object).length() != 0 && (n = ((String)object).indexOf(93)) != -1) {
                                this.lexer.consume(n + 1);
                                ((StringBuffer)charSequence).append("]");
                                charSequence = ((StringBuffer)charSequence).toString();
                                return charSequence;
                            }
                        }
                        break;
                    }
                    this.lexer.consume(1);
                    ((StringBuffer)charSequence).append(c);
                }
            } else {
                while (this.lexer.hasMoreChars()) {
                    char c = this.lexer.lookAhead(0);
                    if (!LexerCore.isHexDigit(c) && c != '.' && c != ':' && c != '[') {
                        if (c == ']') {
                            this.lexer.consume(1);
                            ((StringBuffer)charSequence).append(c);
                            charSequence = ((StringBuffer)charSequence).toString();
                            return charSequence;
                        }
                        break;
                    }
                    this.lexer.consume(1);
                    ((StringBuffer)charSequence).append(c);
                }
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.lexer.getBuffer());
            ((StringBuilder)charSequence).append(": Illegal Host name ");
            object = new ParseException(((StringBuilder)charSequence).toString(), this.lexer.getPtr());
            throw object;
        }
        finally {
            if (debug) {
                this.dbg_leave("ipv6Reference");
            }
        }
    }
}

