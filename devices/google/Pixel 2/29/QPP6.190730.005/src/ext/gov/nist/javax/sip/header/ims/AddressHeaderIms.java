/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.SIPHeader;
import javax.sip.address.Address;

public abstract class AddressHeaderIms
extends SIPHeader {
    protected AddressImpl address;

    public AddressHeaderIms(String string) {
        super(string);
    }

    @Override
    public Object clone() {
        AddressHeaderIms addressHeaderIms = (AddressHeaderIms)super.clone();
        AddressImpl addressImpl = this.address;
        if (addressImpl != null) {
            addressHeaderIms.address = (AddressImpl)addressImpl.clone();
        }
        return addressHeaderIms;
    }

    @Override
    public abstract String encodeBody();

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = (AddressImpl)address;
    }
}

