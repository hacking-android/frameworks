/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.WWWAuthenticate;
import java.util.List;

public class WWWAuthenticateList
extends SIPHeaderList<WWWAuthenticate> {
    private static final long serialVersionUID = -6978902284285501346L;

    public WWWAuthenticateList() {
        super(WWWAuthenticate.class, "WWW-Authenticate");
    }

    @Override
    public Object clone() {
        return new WWWAuthenticateList().clonehlist(this.hlist);
    }
}

