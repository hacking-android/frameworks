/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PMediaAuthorizationHeader;
import gov.nist.javax.sip.header.ims.SIPHeaderNamesIms;
import java.text.ParseException;
import javax.sip.InvalidArgumentException;
import javax.sip.header.ExtensionHeader;

public class PMediaAuthorization
extends SIPHeader
implements PMediaAuthorizationHeader,
SIPHeaderNamesIms,
ExtensionHeader {
    private static final long serialVersionUID = -6463630258703731133L;
    private String token;

    public PMediaAuthorization() {
        super("P-Media-Authorization");
    }

    @Override
    public Object clone() {
        PMediaAuthorization pMediaAuthorization = (PMediaAuthorization)super.clone();
        String string = this.token;
        if (string != null) {
            pMediaAuthorization.token = string;
        }
        return pMediaAuthorization;
    }

    @Override
    protected String encodeBody() {
        return this.token;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof PMediaAuthorizationHeader) {
            object = (PMediaAuthorizationHeader)object;
            return this.getToken().equals(object.getToken());
        }
        return false;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public void setMediaAuthorizationToken(String string) throws InvalidArgumentException {
        if (string != null && string.length() != 0) {
            this.token = string;
            return;
        }
        throw new InvalidArgumentException(" the Media-Authorization-Token parameter is null or empty");
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

