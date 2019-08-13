/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.ims.PAssociatedURI;
import java.util.List;

public class PAssociatedURIList
extends SIPHeaderList<PAssociatedURI> {
    private static final long serialVersionUID = 4454306052557362851L;

    public PAssociatedURIList() {
        super(PAssociatedURI.class, "P-Associated-URI");
    }

    @Override
    public Object clone() {
        return new PAssociatedURIList().clonehlist(this.hlist);
    }
}

