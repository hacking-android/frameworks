/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.Unsupported;
import java.util.List;

public class UnsupportedList
extends SIPHeaderList<Unsupported> {
    private static final long serialVersionUID = -4052610269407058661L;

    public UnsupportedList() {
        super(Unsupported.class, "Unsupported");
    }

    @Override
    public Object clone() {
        return new UnsupportedList().clonehlist(this.hlist);
    }
}

