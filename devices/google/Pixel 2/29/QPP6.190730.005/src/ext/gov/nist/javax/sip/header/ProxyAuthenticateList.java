/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.ProxyAuthenticate;
import gov.nist.javax.sip.header.SIPHeaderList;
import java.util.List;

public class ProxyAuthenticateList
extends SIPHeaderList<ProxyAuthenticate> {
    private static final long serialVersionUID = 1L;

    public ProxyAuthenticateList() {
        super(ProxyAuthenticate.class, "Proxy-Authenticate");
    }

    @Override
    public Object clone() {
        ProxyAuthenticateList proxyAuthenticateList = new ProxyAuthenticateList();
        proxyAuthenticateList.clonehlist(this.hlist);
        return proxyAuthenticateList;
    }
}

