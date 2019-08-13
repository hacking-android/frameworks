/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.AllowEvents;
import gov.nist.javax.sip.header.SIPHeaderList;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class AllowEventsList
extends SIPHeaderList<AllowEvents> {
    private static final long serialVersionUID = -684763195336212992L;

    public AllowEventsList() {
        super(AllowEvents.class, "Allow-Events");
    }

    @Override
    public Object clone() {
        AllowEventsList allowEventsList = new AllowEventsList();
        allowEventsList.clonehlist(this.hlist);
        return allowEventsList;
    }

    public ListIterator<String> getMethods() {
        ListIterator listIterator = this.hlist.listIterator();
        LinkedList<String> linkedList = new LinkedList<String>();
        while (listIterator.hasNext()) {
            linkedList.add(((AllowEvents)listIterator.next()).getEventType());
        }
        return linkedList.listIterator();
    }

    public void setMethods(List<String> object) throws ParseException {
        object = object.listIterator();
        while (object.hasNext()) {
            AllowEvents allowEvents = new AllowEvents();
            allowEvents.setEventType((String)object.next());
            this.add(allowEvents);
        }
    }
}

