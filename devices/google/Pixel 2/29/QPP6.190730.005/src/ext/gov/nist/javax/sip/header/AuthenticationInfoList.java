/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.AuthenticationInfo;
import gov.nist.javax.sip.header.SIPHeaderList;
import java.util.List;

public class AuthenticationInfoList
extends SIPHeaderList<AuthenticationInfo> {
    private static final long serialVersionUID = 1L;

    public AuthenticationInfoList() {
        super(AuthenticationInfo.class, "Authentication-Info");
    }

    @Override
    public Object clone() {
        AuthenticationInfoList authenticationInfoList = new AuthenticationInfoList();
        authenticationInfoList.clonehlist(this.hlist);
        return authenticationInfoList;
    }
}

