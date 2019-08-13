/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.AuthenticationHeader;
import javax.sip.header.ProxyAuthorizationHeader;

public class ProxyAuthorization
extends AuthenticationHeader
implements ProxyAuthorizationHeader {
    private static final long serialVersionUID = -6374966905199799098L;

    public ProxyAuthorization() {
        super("Proxy-Authorization");
    }
}

