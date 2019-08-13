/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.LexerCore;
import gov.nist.core.NameValue;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public abstract class ParametersParser
extends HeaderParser {
    protected ParametersParser(Lexer lexer) {
        super(lexer);
    }

    protected ParametersParser(String string) {
        super(string);
    }

    protected void parse(ParametersHeader parametersHeader) throws ParseException {
        this.lexer.SPorHT();
        while (this.lexer.lookAhead(0) == ';') {
            this.lexer.consume(1);
            this.lexer.SPorHT();
            parametersHeader.setParameter(this.nameValue());
            this.lexer.SPorHT();
        }
    }

    protected void parseNameValueList(ParametersHeader parametersHeader) throws ParseException {
        parametersHeader.removeParameters();
        do {
            this.lexer.SPorHT();
            NameValue nameValue = this.nameValue();
            parametersHeader.setParameter(nameValue.getName(), (String)nameValue.getValueAsObject());
            this.lexer.SPorHT();
            if (this.lexer.lookAhead(0) != ';') {
                return;
            }
            this.lexer.consume(1);
        } while (true);
    }
}

