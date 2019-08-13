/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.From;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class FromParser
extends AddressParametersParser {
    protected FromParser(Lexer lexer) {
        super(lexer);
    }

    public FromParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        From from = new From();
        this.lexer.match(2062);
        this.lexer.SPorHT();
        this.lexer.match(58);
        this.lexer.SPorHT();
        super.parse(from);
        this.lexer.match(10);
        return from;
    }
}

