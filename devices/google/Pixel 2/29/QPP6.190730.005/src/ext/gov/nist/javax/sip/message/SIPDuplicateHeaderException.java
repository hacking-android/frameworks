/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.message;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.message.SIPMessage;
import java.text.ParseException;

public class SIPDuplicateHeaderException
extends ParseException {
    private static final long serialVersionUID = 8241107266407879291L;
    protected SIPHeader sipHeader;
    protected SIPMessage sipMessage;

    public SIPDuplicateHeaderException(String string) {
        super(string, 0);
    }

    public SIPHeader getSIPHeader() {
        return this.sipHeader;
    }

    public SIPMessage getSIPMessage() {
        return this.sipMessage;
    }

    public void setSIPHeader(SIPHeader sIPHeader) {
        this.sipHeader = sIPHeader;
    }

    public void setSIPMessage(SIPMessage sIPMessage) {
        this.sipMessage = sIPMessage;
    }
}

