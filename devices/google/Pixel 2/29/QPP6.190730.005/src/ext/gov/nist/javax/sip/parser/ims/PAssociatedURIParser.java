/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.core.LexerCore;
import gov.nist.core.Token;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PAssociatedURI;
import gov.nist.javax.sip.header.ims.PAssociatedURIList;
import gov.nist.javax.sip.parser.AddressParametersParser;
import gov.nist.javax.sip.parser.Lexer;
import java.text.ParseException;

public class PAssociatedURIParser
extends AddressParametersParser {
    protected PAssociatedURIParser(Lexer lexer) {
        super(lexer);
    }

    public PAssociatedURIParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        if (debug) {
            this.dbg_enter("PAssociatedURIParser.parse");
        }
        PAssociatedURIList pAssociatedURIList = new PAssociatedURIList();
        try {
            this.headerName(2129);
            PAssociatedURI pAssociatedURI = new PAssociatedURI();
            pAssociatedURI.setHeaderName("P-Associated-URI");
            super.parse(pAssociatedURI);
            pAssociatedURIList.add(pAssociatedURI);
            this.lexer.SPorHT();
            while (this.lexer.lookAhead(0) == ',') {
                this.lexer.match(44);
                this.lexer.SPorHT();
                pAssociatedURI = new PAssociatedURI();
                super.parse(pAssociatedURI);
                pAssociatedURIList.add(pAssociatedURI);
                this.lexer.SPorHT();
            }
            this.lexer.SPorHT();
            this.lexer.match(10);
            return pAssociatedURIList;
        }
        finally {
            if (debug) {
                this.dbg_leave("PAssociatedURIParser.parse");
            }
        }
    }
}

