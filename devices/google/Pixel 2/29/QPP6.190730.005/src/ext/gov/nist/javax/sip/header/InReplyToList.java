/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.InReplyTo;
import gov.nist.javax.sip.header.SIPHeaderList;
import java.util.List;

public final class InReplyToList
extends SIPHeaderList<InReplyTo> {
    private static final long serialVersionUID = -7993498496830999237L;

    public InReplyToList() {
        super(InReplyTo.class, "In-Reply-To");
    }

    @Override
    public Object clone() {
        InReplyToList inReplyToList = new InReplyToList();
        inReplyToList.clonehlist(this.hlist);
        return inReplyToList;
    }
}

