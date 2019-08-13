/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.Authorization;
import gov.nist.javax.sip.header.SIPHeaderList;
import java.util.List;

public class AuthorizationList
extends SIPHeaderList<Authorization> {
    private static final long serialVersionUID = 1L;

    public AuthorizationList() {
        super(Authorization.class, "Authorization");
    }

    @Override
    public Object clone() {
        AuthorizationList authorizationList = new AuthorizationList();
        authorizationList.clonehlist(this.hlist);
        return authorizationList;
    }
}

