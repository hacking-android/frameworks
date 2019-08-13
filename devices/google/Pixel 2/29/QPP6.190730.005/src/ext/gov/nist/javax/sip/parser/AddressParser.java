/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.Parser;
import gov.nist.javax.sip.parser.URLParser;
import java.text.ParseException;
import javax.sip.address.URI;

public class AddressParser
extends Parser {
    public AddressParser(Lexer lexer) {
        this.lexer = lexer;
        this.lexer.selectLexer("charLexer");
    }

    public AddressParser(String string) {
        this.lexer = new Lexer("charLexer", string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public AddressImpl address(boolean bl) throws ParseException {
        char c;
        if (debug) {
            this.dbg_enter("address");
        }
        int n = 0;
        while (this.lexer.hasMoreChars() && (c = this.lexer.lookAhead(n)) != '<' && c != '\"' && c != ':' && c != '/') {
            if (c == '\u0000') throw this.createParseException("unexpected EOL");
            ++n;
        }
        if ((n = (int)this.lexer.lookAhead(n)) == 60) return this.nameAddr();
        if (n != 34) {
            if (n != 58) {
                if (n != 47) throw this.createParseException("Bad address spec");
            }
            AddressImpl addressImpl = new AddressImpl();
            Object object = new URLParser((Lexer)this.lexer);
            object = ((URLParser)object).uriReference(bl);
            addressImpl.setAddressType(2);
            addressImpl.setURI((URI)object);
            return addressImpl;
        }
        return this.nameAddr();
    }

    protected AddressImpl nameAddr() throws ParseException {
        if (debug) {
            this.dbg_enter("nameAddr");
        }
        try {
            Object object;
            if (this.lexer.lookAhead(0) == '<') {
                this.lexer.consume(1);
                this.lexer.selectLexer("sip_urlLexer");
                this.lexer.SPorHT();
                Object object2 = new URLParser((Lexer)this.lexer);
                GenericURI genericURI = ((URLParser)object2).uriReference(true);
                object2 = new AddressImpl();
                ((AddressImpl)object2).setAddressType(1);
                ((AddressImpl)object2).setURI(genericURI);
                this.lexer.SPorHT();
                this.lexer.match(62);
                return object2;
            }
            AddressImpl addressImpl = new AddressImpl();
            addressImpl.setAddressType(1);
            if (this.lexer.lookAhead(0) == '\"') {
                object = this.lexer.quotedString();
                this.lexer.SPorHT();
            } else {
                object = this.lexer.getNextToken('<');
            }
            addressImpl.setDisplayName(((String)object).trim());
            this.lexer.match(60);
            this.lexer.SPorHT();
            object = new URLParser((Lexer)this.lexer);
            object = ((URLParser)object).uriReference(true);
            new gov.nist.javax.sip.address.AddressImpl();
            addressImpl.setAddressType(1);
            addressImpl.setURI((URI)object);
            this.lexer.SPorHT();
            this.lexer.match(62);
            return addressImpl;
        }
        finally {
            if (debug) {
                this.dbg_leave("nameAddr");
            }
        }
    }
}

