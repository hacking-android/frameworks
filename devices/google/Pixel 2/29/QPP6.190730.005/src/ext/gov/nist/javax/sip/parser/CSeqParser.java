/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.core.Debug;
import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.CSeq;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.parser.HeaderParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;

public class CSeqParser
extends HeaderParser {
    protected CSeqParser(Lexer lexer) {
        super(lexer);
    }

    public CSeqParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        try {
            CSeq cSeq = new CSeq();
            this.lexer.match(2094);
            this.lexer.SPorHT();
            this.lexer.match(58);
            this.lexer.SPorHT();
            cSeq.setSeqNumber(Long.parseLong(this.lexer.number()));
            this.lexer.SPorHT();
            cSeq.setMethod(SIPRequest.getCannonicalName(this.method()));
            this.lexer.SPorHT();
            this.lexer.match(10);
            return cSeq;
        }
        catch (InvalidArgumentException invalidArgumentException) {
            Debug.printStackTrace(invalidArgumentException);
            throw this.createParseException(invalidArgumentException.getMessage());
        }
        catch (NumberFormatException numberFormatException) {
            Debug.printStackTrace(numberFormatException);
            throw this.createParseException("Number format exception");
        }
    }
}

