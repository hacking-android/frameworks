/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.ims.SecurityAgree;
import gov.nist.javax.sip.header.ims.SecurityServer;
import gov.nist.javax.sip.header.ims.SecurityServerList;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ims.SecurityAgreeParser;
import java.text.ParseException;

public class SecurityServerParser
extends SecurityAgreeParser {
    protected SecurityServerParser(Lexer lexer) {
        super(lexer);
    }

    public SecurityServerParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        this.dbg_enter("SecuriryServer parse");
        try {
            this.headerName(2137);
            SIPHeader sIPHeader = new SecurityServer();
            sIPHeader = (SecurityServerList)super.parse((SecurityAgree)sIPHeader);
            return sIPHeader;
        }
        finally {
            this.dbg_leave("SecuriryServer parse");
        }
    }
}

