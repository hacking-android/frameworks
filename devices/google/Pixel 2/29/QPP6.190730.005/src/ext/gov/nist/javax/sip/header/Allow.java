/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import java.text.ParseException;
import javax.sip.header.AllowHeader;

public final class Allow
extends SIPHeader
implements AllowHeader {
    private static final long serialVersionUID = -3105079479020693930L;
    protected String method;

    public Allow() {
        super("Allow");
    }

    public Allow(String string) {
        super("Allow");
        this.method = string;
    }

    @Override
    protected String encodeBody() {
        return this.method;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public void setMethod(String string) throws ParseException {
        if (string != null) {
            this.method = string;
            return;
        }
        throw new NullPointerException("JAIN-SIP Exception, Allow, setMethod(), the method parameter is null.");
    }
}

