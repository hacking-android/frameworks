/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.HostNameParser;
import gov.nist.core.HostPort;
import gov.nist.core.LexerCore;
import gov.nist.core.NameValue;
import gov.nist.core.NameValueList;
import gov.nist.core.Token;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.address.SipUri;
import gov.nist.javax.sip.address.TelURLImpl;
import gov.nist.javax.sip.address.TelephoneNumber;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.Parser;
import java.io.PrintStream;
import java.io.Serializable;
import java.text.ParseException;

public class URLParser
extends Parser {
    public URLParser(Lexer lexer) {
        this.lexer = lexer;
        this.lexer.selectLexer("sip_urlLexer");
    }

    public URLParser(String string) {
        this.lexer = new Lexer("sip_urlLexer", string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String base_phone_number() throws ParseException {
        CharSequence charSequence = new StringBuffer();
        if (debug) {
            this.dbg_enter("base_phone_number");
        }
        int n = 0;
        while (this.lexer.hasMoreChars()) {
            char c = this.lexer.lookAhead(0);
            if (!Lexer.isDigit(c) && c != '-' && c != '.' && c != '(' && c != ')') {
                if (n > 0) return ((StringBuffer)charSequence).toString();
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("unexpected ");
                ((StringBuilder)charSequence).append(c);
                throw this.createParseException(((StringBuilder)charSequence).toString());
            }
            this.lexer.consume(1);
            ((StringBuffer)charSequence).append(c);
            ++n;
        }
        return ((StringBuffer)charSequence).toString();
    }

    private final TelephoneNumber global_phone_number(boolean bl) throws ParseException {
        TelephoneNumber telephoneNumber;
        block6 : {
            if (debug) {
                this.dbg_enter("global_phone_number");
            }
            telephoneNumber = new TelephoneNumber();
            telephoneNumber.setGlobal(true);
            this.lexer.match(43);
            telephoneNumber.setPhoneNumber(this.base_phone_number());
            if (!this.lexer.hasMoreChars() || this.lexer.lookAhead(0) != ';' || !bl) break block6;
            this.lexer.consume(1);
            telephoneNumber.setParameters(this.tel_parameters());
        }
        return telephoneNumber;
        finally {
            if (debug) {
                this.dbg_leave("global_phone_number");
            }
        }
    }

    protected static boolean isMark(char c) {
        if (c != '!' && c != '_' && c != '~' && c != '-' && c != '.') {
            switch (c) {
                default: {
                    return false;
                }
                case '\'': 
                case '(': 
                case ')': 
                case '*': 
            }
        }
        return true;
    }

    protected static boolean isReserved(char c) {
        return c == '$' || c == '&' || c == '/' || c == '=' || c == '+' || c == ',' || c == ':' || c == ';' || c == '?' || c == '@';
    }

    protected static boolean isReservedNoSlash(char c) {
        return c == '$' || c == '&' || c == '+' || c == ',' || c == ':' || c == ';' || c == '?' || c == '@';
    }

    protected static boolean isUnreserved(char c) {
        boolean bl = Lexer.isAlphaDigit(c) || URLParser.isMark(c);
        return bl;
    }

    protected static boolean isUserUnreserved(char c) {
        return c == '#' || c == '$' || c == '&' || c == '/' || c == ';' || c == '=' || c == '?' || c == '+' || c == ',';
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String local_number() throws ParseException {
        CharSequence charSequence = new StringBuffer();
        if (debug) {
            this.dbg_enter("local_number");
        }
        int n = 0;
        while (this.lexer.hasMoreChars()) {
            char c = this.lexer.lookAhead(0);
            if (c != '*' && c != '#' && c != '-' && c != '.' && c != '(' && c != ')' && !Lexer.isHexDigit(c)) {
                if (n > 0) return ((StringBuffer)charSequence).toString();
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("unexepcted ");
                ((StringBuilder)charSequence).append(c);
                throw this.createParseException(((StringBuilder)charSequence).toString());
            }
            this.lexer.consume(1);
            ((StringBuffer)charSequence).append(c);
            ++n;
        }
        return ((StringBuffer)charSequence).toString();
    }

    private TelephoneNumber local_phone_number(boolean bl) throws ParseException {
        TelephoneNumber telephoneNumber;
        block7 : {
            if (debug) {
                this.dbg_enter("local_phone_number");
            }
            telephoneNumber = new TelephoneNumber();
            telephoneNumber.setGlobal(false);
            telephoneNumber.setPhoneNumber(this.local_number());
            if (!this.lexer.hasMoreChars()) break block7;
            if (this.lexer.peekNextToken().getTokenType() != 59) {
                break block7;
            }
            if (!bl) break block7;
            this.lexer.consume(1);
            telephoneNumber.setParameters(this.tel_parameters());
        }
        return telephoneNumber;
        finally {
            if (debug) {
                this.dbg_leave("local_phone_number");
            }
        }
    }

    public static void main(String[] object) throws ParseException {
        String[] arrstring = new String[]{"sip:alice@example.com", "sips:alice@examples.com", "sip:3Zqkv5dajqaaas0tCjCxT0xH2ZEuEMsFl0xoasip%3A%2B3519116786244%40siplab.domain.com@213.0.115.163:7070"};
        for (int i = 0; i < arrstring.length; ++i) {
            object = new URLParser(arrstring[i]).parse();
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("uri type returned ");
            stringBuilder.append(object.getClass().getName());
            printStream.println(stringBuilder.toString());
            printStream = System.out;
            stringBuilder = new StringBuilder();
            stringBuilder.append(arrstring[i]);
            stringBuilder.append(" is SipUri? ");
            stringBuilder.append(((GenericURI)object).isSipURI());
            stringBuilder.append(">");
            stringBuilder.append(((GenericURI)object).encode());
            printStream.println(stringBuilder.toString());
        }
    }

    private NameValue phone_context() throws ParseException {
        char c;
        block4 : {
            CharSequence charSequence;
            block3 : {
                block2 : {
                    this.lexer.match(61);
                    c = this.lexer.lookAhead(0);
                    if (c != '+') break block2;
                    this.lexer.consume(1);
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("+");
                    ((StringBuilder)charSequence).append(this.base_phone_number());
                    charSequence = ((StringBuilder)charSequence).toString();
                    break block3;
                }
                if (!Lexer.isAlphaDigit(c)) break block4;
                charSequence = this.lexer.match(4095).getTokenValue();
            }
            return new NameValue("phone-context", charSequence, false);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid phone-context:");
        stringBuilder.append(c);
        throw new ParseException(stringBuilder.toString(), -1);
    }

    private NameValueList tel_parameters() throws ParseException {
        NameValueList nameValueList = new NameValueList();
        do {
            Object object;
            if (((String)(object = this.paramNameOrValue())).equalsIgnoreCase("phone-context")) {
                object = this.phone_context();
            } else if (this.lexer.lookAhead(0) == '=') {
                this.lexer.consume(1);
                object = new NameValue((String)object, this.paramNameOrValue(), false);
            } else {
                object = new NameValue((String)object, "", true);
            }
            nameValueList.set((NameValue)object);
            if (this.lexer.lookAhead(0) != ';') break;
            this.lexer.consume(1);
        } while (true);
        return nameValueList;
    }

    private NameValue uriParam() throws ParseException {
        Object object;
        boolean bl;
        String string;
        block11 : {
            block12 : {
                int n;
                block10 : {
                    if (debug) {
                        this.dbg_enter("uriParam");
                    }
                    object = "";
                    string = this.paramNameOrValue();
                    n = this.lexer.lookAhead(0);
                    bl = true;
                    if (n != 61) break block10;
                    this.lexer.consume(1);
                    object = this.paramNameOrValue();
                    bl = false;
                }
                if (string.length() != 0) break block11;
                if (object == null) break block12;
                n = ((String)object).length();
                if (n != 0) break block11;
            }
            if (debug) {
                this.dbg_leave("uriParam");
            }
            return null;
        }
        try {
            object = new NameValue(string, object, bl);
            return object;
        }
        finally {
            if (debug) {
                this.dbg_leave("uriParam");
            }
        }
    }

    protected String escaped() throws ParseException {
        if (debug) {
            this.dbg_enter("escaped");
        }
        try {
            CharSequence charSequence = new StringBuffer();
            char c = this.lexer.lookAhead(0);
            char c2 = this.lexer.lookAhead(1);
            char c3 = this.lexer.lookAhead(2);
            if (c == '%' && Lexer.isHexDigit(c2) && Lexer.isHexDigit(c3)) {
                this.lexer.consume(3);
                charSequence.append(c);
                charSequence.append(c2);
                charSequence.append(c3);
                charSequence = charSequence.toString();
                return charSequence;
            }
            throw this.createParseException("escaped");
        }
        finally {
            if (debug) {
                this.dbg_leave("escaped");
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected String hvalue() throws ParseException {
        var1_1 = new StringBuffer();
        while (this.lexer.hasMoreChars() != false) {
            var2_2 = this.lexer.lookAhead(0);
            var3_3 = false;
            if (var2_2 != '!' && var2_2 != '\"' && var2_2 != '$' && var2_2 != ':' && var2_2 != '?' && var2_2 != '[' && var2_2 != ']' && var2_2 != '_' && var2_2 != '~') {
                switch (var2_2) {
                    default: {
                        switch (var2_2) {
                            default: {
                                ** break;
                            }
                            case '-': 
                            case '.': 
                            case '/': 
                        }
                    }
                    case '(': 
                    case ')': 
                    case '*': 
                    case '+': 
                }
            }
            var3_3 = true;
lbl14: // 2 sources:
            if (!var3_3 && !Lexer.isAlphaDigit(var2_2)) {
                if (var2_2 != '%') return var1_1.toString();
                var1_1.append(this.escaped());
                continue;
            }
            this.lexer.consume(1);
            var1_1.append(var2_2);
        }
        return var1_1.toString();
    }

    protected boolean isEscaped() {
        boolean bl = false;
        try {
            boolean bl2;
            if (this.lexer.lookAhead(0) == '%' && Lexer.isHexDigit(this.lexer.lookAhead(1)) && (bl2 = Lexer.isHexDigit(this.lexer.lookAhead(2)))) {
                bl = true;
            }
            return bl;
        }
        catch (Exception exception) {
            return false;
        }
    }

    protected String mark() throws ParseException {
        if (debug) {
            this.dbg_enter("mark");
        }
        try {
            char c = this.lexer.lookAhead(0);
            if (URLParser.isMark(c)) {
                this.lexer.consume(1);
                String string = new String(new char[]{c});
                return string;
            }
            throw this.createParseException("mark");
        }
        finally {
            if (debug) {
                this.dbg_leave("mark");
            }
        }
    }

    protected String paramNameOrValue() throws ParseException {
        int n = this.lexer.getPtr();
        while (this.lexer.hasMoreChars()) {
            char c = this.lexer.lookAhead(0);
            boolean bl = false;
            if (c == '$' || c == '&' || c == '+' || c == '/' || c == ':' || c == '[' || c == ']') {
                bl = true;
            }
            if (!bl && !URLParser.isUnreserved(c)) {
                if (!this.isEscaped()) break;
                this.lexer.consume(3);
                continue;
            }
            this.lexer.consume(1);
        }
        return this.lexer.getBuffer().substring(n, this.lexer.getPtr());
    }

    public GenericURI parse() throws ParseException {
        return this.uriReference(true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final TelephoneNumber parseTelephoneNumber(boolean bl) throws ParseException {
        if (debug) {
            this.dbg_enter("telephone_subscriber");
        }
        this.lexer.selectLexer("charLexer");
        try {
            TelephoneNumber telephoneNumber;
            char c = this.lexer.lookAhead(0);
            if (c == '+') {
                telephoneNumber = this.global_phone_number(bl);
                return telephoneNumber;
            }
            if (!Lexer.isHexDigit(c) && c != '#' && c != '*' && c != '-' && c != '.' && c != '(' && c != ')') {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unexpected char ");
                stringBuilder.append(c);
                throw this.createParseException(stringBuilder.toString());
            }
            telephoneNumber = this.local_phone_number(bl);
            return telephoneNumber;
        }
        finally {
            if (debug) {
                this.dbg_leave("telephone_subscriber");
            }
        }
    }

    protected String password() throws ParseException {
        int n = this.lexer.getPtr();
        do {
            char c = this.lexer.lookAhead(0);
            boolean bl = false;
            if (c == '$' || c == '&' || c == '=' || c == '+' || c == ',') {
                bl = true;
            }
            if (!bl && !URLParser.isUnreserved(c)) {
                if (this.isEscaped()) {
                    this.lexer.consume(3);
                    continue;
                }
                return this.lexer.getBuffer().substring(n, this.lexer.getPtr());
            }
            this.lexer.consume(1);
        } while (true);
    }

    public String peekScheme() throws ParseException {
        Token[] arrtoken = this.lexer.peekNextToken(1);
        if (arrtoken.length == 0) {
            return null;
        }
        return arrtoken[0].getTokenValue();
    }

    protected NameValue qheader() throws ParseException {
        String string = this.lexer.getNextToken('=');
        this.lexer.consume(1);
        return new NameValue(string, this.hvalue(), false);
    }

    protected String reserved() throws ParseException {
        char c = this.lexer.lookAhead(0);
        if (URLParser.isReserved(c)) {
            this.lexer.consume(1);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(c);
            return stringBuffer.toString();
        }
        throw this.createParseException("reserved");
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public SipUri sipURL(boolean bl) throws ParseException {
        Throwable throwable2222;
        int n;
        Object object;
        if (debug) {
            this.dbg_enter("sipURL");
        }
        Serializable serializable = new SipUri();
        if (this.lexer.peekNextToken().getTokenType() == 2136) {
            object = "sips";
            n = 2136;
        } else {
            object = "sip";
            n = 2051;
        }
        this.lexer.match(n);
        this.lexer.match(58);
        ((SipUri)serializable).setScheme((String)object);
        n = this.lexer.markInputPosition();
        String string = this.user();
        object = null;
        if (this.lexer.lookAhead() == ':') {
            this.lexer.consume(1);
            object = this.password();
        }
        if (this.lexer.lookAhead() == '@') {
            this.lexer.consume(1);
            ((SipUri)serializable).setUser(string);
            if (object != null) {
                ((SipUri)serializable).setUserPassword((String)object);
            }
        } else {
            this.lexer.rewindInputPosition(n);
        }
        object = new HostNameParser(this.getLexer());
        ((SipUri)serializable).setHostPort(((HostNameParser)object).hostPort(false));
        this.lexer.selectLexer("charLexer");
        while (this.lexer.hasMoreChars() && this.lexer.lookAhead(0) == ';' && bl) {
            this.lexer.consume(1);
            object = this.uriParam();
            if (object == null) continue;
            ((SipUri)serializable).setUriParameter((NameValue)object);
        }
        if (this.lexer.hasMoreChars() && this.lexer.lookAhead(0) == '?') {
            this.lexer.consume(1);
            while (this.lexer.hasMoreChars()) {
                ((SipUri)serializable).setQHeader(this.qheader());
                if (this.lexer.hasMoreChars() && this.lexer.lookAhead(0) != '&') break;
                this.lexer.consume(1);
            }
        }
        if (!debug) return serializable;
        this.dbg_leave("sipURL");
        return serializable;
        {
            catch (Throwable throwable2222) {
            }
            catch (RuntimeException runtimeException) {}
            {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid URL: ");
                stringBuilder.append(this.lexer.getBuffer());
                serializable = new ParseException(stringBuilder.toString(), -1);
                throw serializable;
            }
        }
        if (!debug) throw throwable2222;
        this.dbg_leave("sipURL");
        throw throwable2222;
    }

    public TelURLImpl telURL(boolean bl) throws ParseException {
        this.lexer.match(2105);
        this.lexer.match(58);
        TelephoneNumber telephoneNumber = this.parseTelephoneNumber(bl);
        TelURLImpl telURLImpl = new TelURLImpl();
        telURLImpl.setTelephoneNumber(telephoneNumber);
        return telURLImpl;
    }

    protected String unreserved() throws ParseException {
        char c = this.lexer.lookAhead(0);
        if (URLParser.isUnreserved(c)) {
            this.lexer.consume(1);
            return String.valueOf(c);
        }
        throw this.createParseException("unreserved");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public GenericURI uriReference(boolean bl) throws ParseException {
        if (debug) {
            this.dbg_enter("uriReference");
        }
        Object object = this.lexer.peekNextToken(2);
        Object object2 = object[0];
        object = object[1];
        try {
            int n = ((Token)object2).getTokenType();
            if (n != 2051 && ((Token)object2).getTokenType() != 2136) {
                if (((Token)object2).getTokenType() == 2105) {
                    if (((Token)object).getTokenType() != 58) throw this.createParseException("Expecting ':'");
                    object2 = this.telURL(bl);
                    return object2;
                } else {
                    object2 = this.uricString();
                    try {
                        object2 = new GenericURI((String)object2);
                        return object2;
                    }
                    catch (ParseException parseException) {
                        throw this.createParseException(parseException.getMessage());
                    }
                }
            } else {
                if (((Token)object).getTokenType() != 58) throw this.createParseException("Expecting ':'");
                object2 = this.sipURL(bl);
            }
            return object2;
        }
        finally {
            if (debug) {
                this.dbg_leave("uriReference");
            }
        }
    }

    protected String uric() {
        if (debug) {
            this.dbg_enter("uric");
        }
        try {
            char c = this.lexer.lookAhead(0);
            if (URLParser.isUnreserved(c)) {
                this.lexer.consume(1);
                String string = Lexer.charAsString(c);
                return string;
            }
            if (URLParser.isReserved(c)) {
                this.lexer.consume(1);
                String string = Lexer.charAsString(c);
                return string;
            }
            if (this.isEscaped()) {
                String string = this.lexer.charAsString(3);
                this.lexer.consume(3);
                return string;
            }
            return null;
        }
        catch (Exception exception) {
            return null;
        }
        finally {
            if (debug) {
                this.dbg_leave("uric");
            }
        }
    }

    protected String uricNoSlash() {
        if (debug) {
            this.dbg_enter("uricNoSlash");
        }
        try {
            char c = this.lexer.lookAhead(0);
            if (this.isEscaped()) {
                String string = this.lexer.charAsString(3);
                this.lexer.consume(3);
                return string;
            }
            if (URLParser.isUnreserved(c)) {
                this.lexer.consume(1);
                String string = Lexer.charAsString(c);
                return string;
            }
            if (URLParser.isReservedNoSlash(c)) {
                this.lexer.consume(1);
                String string = Lexer.charAsString(c);
                return string;
            }
            return null;
        }
        catch (ParseException parseException) {
            return null;
        }
        finally {
            if (debug) {
                this.dbg_leave("uricNoSlash");
            }
        }
    }

    protected String uricString() throws ParseException {
        StringBuffer stringBuffer = new StringBuffer();
        do {
            String string;
            if ((string = this.uric()) == null) {
                if (this.lexer.lookAhead(0) == '[') {
                    stringBuffer.append(new HostNameParser(this.getLexer()).hostPort(false).toString());
                    continue;
                }
                return stringBuffer.toString();
            }
            stringBuffer.append(string);
        } while (true);
    }

    protected String urlString() throws ParseException {
        char c;
        StringBuffer stringBuffer = new StringBuffer();
        this.lexer.selectLexer("charLexer");
        while (this.lexer.hasMoreChars() && (c = this.lexer.lookAhead(0)) != ' ' && c != '\t' && c != '\n' && c != '>' && c != '<') {
            this.lexer.consume(0);
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }

    protected String user() throws ParseException {
        if (debug) {
            this.dbg_enter("user");
        }
        try {
            int n = this.lexer.getPtr();
            while (this.lexer.hasMoreChars()) {
                char c = this.lexer.lookAhead(0);
                if (!URLParser.isUnreserved(c) && !URLParser.isUserUnreserved(c)) {
                    if (!this.isEscaped()) break;
                    this.lexer.consume(3);
                    continue;
                }
                this.lexer.consume(1);
            }
            String string = this.lexer.getBuffer().substring(n, this.lexer.getPtr());
            return string;
        }
        finally {
            if (debug) {
                this.dbg_leave("user");
            }
        }
    }
}

