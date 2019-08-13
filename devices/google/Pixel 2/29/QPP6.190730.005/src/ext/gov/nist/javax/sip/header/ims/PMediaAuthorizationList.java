/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.ims.PMediaAuthorization;
import java.util.List;

public class PMediaAuthorizationList
extends SIPHeaderList<PMediaAuthorization> {
    private static final long serialVersionUID = -8226328073989632317L;

    public PMediaAuthorizationList() {
        super(PMediaAuthorization.class, "P-Media-Authorization");
    }

    @Override
    public Object clone() {
        return new PMediaAuthorizationList().clonehlist(this.hlist);
    }
}

