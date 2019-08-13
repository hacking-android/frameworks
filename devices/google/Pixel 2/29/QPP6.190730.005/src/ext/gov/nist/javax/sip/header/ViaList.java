/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.Via;
import java.util.List;

public final class ViaList
extends SIPHeaderList<Via> {
    private static final long serialVersionUID = 3899679374556152313L;

    public ViaList() {
        super(Via.class, "Via");
    }

    @Override
    public Object clone() {
        return new ViaList().clonehlist(this.hlist);
    }
}

