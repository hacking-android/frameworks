/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser.ims;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.ims.SecurityAgree;
import gov.nist.javax.sip.header.ims.SecurityVerify;
import gov.nist.javax.sip.header.ims.SecurityVerifyList;
import gov.nist.javax.sip.parser.Lexer;
import gov.nist.javax.sip.parser.ims.SecurityAgreeParser;
import java.text.ParseException;

public class SecurityVerifyParser
extends SecurityAgreeParser {
    protected SecurityVerifyParser(Lexer lexer) {
        super(lexer);
    }

    public SecurityVerifyParser(String string) {
        super(string);
    }

    @Override
    public SIPHeader parse() throws ParseException {
        this.dbg_enter("SecuriryVerify parse");
        try {
            this.headerName(2139);
            SIPHeader sIPHeader = new SecurityVerify();
            sIPHeader = (SecurityVerifyList)super.parse((SecurityAgree)sIPHeader);
            return sIPHeader;
        }
        finally {
            this.dbg_leave("SecuriryVerify parse");
        }
    }
}

