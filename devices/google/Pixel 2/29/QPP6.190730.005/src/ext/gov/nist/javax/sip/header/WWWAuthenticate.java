/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.AuthenticationHeader;
import gov.nist.javax.sip.header.ims.WWWAuthenticateHeaderIms;
import javax.sip.address.URI;
import javax.sip.header.WWWAuthenticateHeader;

public class WWWAuthenticate
extends AuthenticationHeader
implements WWWAuthenticateHeader,
WWWAuthenticateHeaderIms {
    private static final long serialVersionUID = 115378648697363486L;

    public WWWAuthenticate() {
        super("WWW-Authenticate");
    }

    @Override
    public URI getURI() {
        return null;
    }

    @Override
    public void setURI(URI uRI) {
    }
}

