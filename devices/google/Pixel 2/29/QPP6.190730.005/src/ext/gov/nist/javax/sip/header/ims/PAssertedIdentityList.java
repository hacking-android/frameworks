/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.ims.PAssertedIdentity;
import java.util.List;

public class PAssertedIdentityList
extends SIPHeaderList<PAssertedIdentity> {
    private static final long serialVersionUID = -6465152445570308974L;

    public PAssertedIdentityList() {
        super(PAssertedIdentity.class, "P-Asserted-Identity");
    }

    @Override
    public Object clone() {
        return new PAssertedIdentityList().clonehlist(this.hlist);
    }
}

