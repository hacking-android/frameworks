/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.Route;
import gov.nist.javax.sip.header.SIPHeaderList;
import java.util.List;
import java.util.ListIterator;

public class RouteList
extends SIPHeaderList<Route> {
    private static final long serialVersionUID = 3407603519354809748L;

    public RouteList() {
        super(Route.class, "Route");
    }

    @Override
    public Object clone() {
        RouteList routeList = new RouteList();
        routeList.clonehlist(this.hlist);
        return routeList;
    }

    @Override
    public String encode() {
        if (this.hlist.isEmpty()) {
            return "";
        }
        return super.encode();
    }

    @Override
    public boolean equals(Object listIterator) {
        if (!(listIterator instanceof RouteList)) {
            return false;
        }
        Object object = (RouteList)((Object)listIterator);
        if (this.size() != ((SIPHeaderList)object).size()) {
            return false;
        }
        listIterator = this.listIterator();
        object = ((SIPHeaderList)object).listIterator();
        while (listIterator.hasNext()) {
            if (((Route)listIterator.next()).equals((Route)object.next())) continue;
            return false;
        }
        return true;
    }
}

