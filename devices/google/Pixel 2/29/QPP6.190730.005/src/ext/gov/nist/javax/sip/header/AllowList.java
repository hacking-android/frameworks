/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.Allow;
import gov.nist.javax.sip.header.SIPHeaderList;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class AllowList
extends SIPHeaderList<Allow> {
    private static final long serialVersionUID = -4699795429662562358L;

    public AllowList() {
        super(Allow.class, "Allow");
    }

    @Override
    public Object clone() {
        AllowList allowList = new AllowList();
        allowList.clonehlist(this.hlist);
        return allowList;
    }

    public ListIterator<String> getMethods() {
        LinkedList<String> linkedList = new LinkedList<String>();
        Iterator iterator = this.hlist.iterator();
        while (iterator.hasNext()) {
            linkedList.add(((Allow)iterator.next()).getMethod());
        }
        return linkedList.listIterator();
    }

    public void setMethods(List<String> object) throws ParseException {
        object = object.listIterator();
        while (object.hasNext()) {
            Allow allow = new Allow();
            allow.setMethod((String)object.next());
            this.add(allow);
        }
    }
}

