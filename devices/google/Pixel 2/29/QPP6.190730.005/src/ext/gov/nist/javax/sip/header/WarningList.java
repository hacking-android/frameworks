/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.Warning;
import java.util.List;

public class WarningList
extends SIPHeaderList<Warning> {
    private static final long serialVersionUID = -1423278728898430175L;

    public WarningList() {
        super(Warning.class, "Warning");
    }

    @Override
    public Object clone() {
        return new WarningList().clonehlist(this.hlist);
    }
}

