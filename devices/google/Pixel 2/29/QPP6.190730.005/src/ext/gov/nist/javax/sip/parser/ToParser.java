/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.To;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class ToParser
extends AddressParametersParser {
    protected ToParser(Lexer lexer) {
        super(lexer);
    }

    public ToParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        this.headerName(2063);
        To to = new To();
        super.parse(to);
        this.lexer.match(10);
        return to;
    }
}

