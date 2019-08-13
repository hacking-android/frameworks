/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.ProxyRequire;
import gov.nist.javax.sip.header.SIPHeaderList;
import java.util.List;

public class ProxyRequireList
extends SIPHeaderList<ProxyRequire> {
    private static final long serialVersionUID = 5648630649476486042L;

    public ProxyRequireList() {
        super(ProxyRequire.class, "Proxy-Require");
    }

    @Override
    public Object clone() {
        ProxyRequireList proxyRequireList = new ProxyRequireList();
        proxyRequireList.clonehlist(this.hlist);
        return proxyRequireList;
    }
}

