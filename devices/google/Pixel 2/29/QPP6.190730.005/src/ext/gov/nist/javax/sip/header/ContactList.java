/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.Contact;
import gov.nist.javax.sip.header.SIPHeaderList;
import java.util.List;

public class ContactList
extends SIPHeaderList<Contact> {
    private static final long serialVersionUID = 1224806837758986814L;

    public ContactList() {
        super(Contact.class, "Contact");
    }

    @Override
    public Object clone() {
        ContactList contactList = new ContactList();
        contactList.clonehlist(this.hlist);
        return contactList;
    }
}

