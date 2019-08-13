/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.RecordRoute;
import gov.nist.javax.sip.header.SIPHeaderList;
import java.util.List;

public class RecordRouteList
extends SIPHeaderList<RecordRoute> {
    private static final long serialVersionUID = 1724940469426766691L;

    public RecordRouteList() {
        super(RecordRoute.class, "Record-Route");
    }

    @Override
    public Object clone() {
        RecordRouteList recordRouteList = new RecordRouteList();
        recordRouteList.clonehlist(this.hlist);
        return recordRouteList;
    }
}

