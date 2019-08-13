/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.ims.SecurityAgree;
import gov.nist.javax.sip.header.ims.SecurityClient;
import gov.nist.javax.sip.header.ims.SecurityClientList;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ims.SecurityAgreeParser;
import java.text.ParseException;

public class SecurityClientParser
extends SecurityAgreeParser {
    protected SecurityClientParser(Lexer lexer) {
        super(lexer);
    }

    public SecurityClientParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        this.dbg_enter("SecuriryClient parse");
        try {
            this.headerName(2138);
            SIPHeader sIPHeader = new SecurityClient();
            sIPHeader = (SecurityClientList)super.parse((SecurityAgree)sIPHeader);
            return sIPHeader;
        }
        finally {
            this.dbg_leave("SecuriryClient parse");
        }
    }
}

