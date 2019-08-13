/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.Accept;
import gov.nist.javax.sip.header.SIPHeaderList;
import java.util.List;

public class AcceptList
extends SIPHeaderList<Accept> {
    private static final long serialVersionUID = -1800813338560484831L;

    public AcceptList() {
        super(Accept.class, "Accept");
    }

    @Override
    public Object clone() {
        AcceptList acceptList = new AcceptList();
        acceptList.clonehlist(this.hlist);
        return acceptList;
    }
}

