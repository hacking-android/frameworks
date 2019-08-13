/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.Contact;
import gov.nist.javax.sip.header.ContactList;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class ContactParser
extends AddressParametersParser {
    protected ContactParser(Lexer lexer) {
        super(lexer);
        this.lexer = lexer;
    }

    public ContactParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        char c;
        this.headerName(2087);
        ContactList contactList = new ContactList();
        do {
            Contact contact = new Contact();
            if (this.lexer.lookAhead(0) == '*') {
                c = this.lexer.lookAhead(1);
                if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                    super.parse(contact);
                } else {
                    this.lexer.match(42);
                    contact.setWildCardFlag(true);
                }
            } else {
                super.parse(contact);
            }
            contactList.add(contact);
            this.lexer.SPorHT();
            c = this.lexer.lookAhead(0);
            if (c != ',') break;
            this.lexer.match(44);
            this.lexer.SPorHT();
        } while (true);
        if (c != '\n' && c != '\u0000') {
            throw this.createParseException("unexpected char");
        }
        return contactList;
    }
}

