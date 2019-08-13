/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.Reason;
import gov.nist.javax.sip.header.SIPHeaderList;
import java.util.List;

public final class ReasonList
extends SIPHeaderList<Reason> {
    private static final long serialVersionUID = 7459989997463160670L;

    public ReasonList() {
        super(Reason.class, "Reason");
    }

    @Override
    public Object clone() {
        ReasonList reasonList = new ReasonList();
        reasonList.clonehlist(this.hlist);
        return reasonList;
    }
}

