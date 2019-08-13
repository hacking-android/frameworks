/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.AcceptEncoding;
import gov.nist.javax.sip.header.SIPHeaderList;
import java.util.List;

public class AcceptEncodingList
extends SIPHeaderList<AcceptEncoding> {
    public AcceptEncodingList() {
        super(AcceptEncoding.class, "Accept-Encoding");
    }

    @Override
    public Object clone() {
        AcceptEncodingList acceptEncodingList = new AcceptEncodingList();
        acceptEncodingList.clonehlist(this.hlist);
        return acceptEncodingList;
    }
}

