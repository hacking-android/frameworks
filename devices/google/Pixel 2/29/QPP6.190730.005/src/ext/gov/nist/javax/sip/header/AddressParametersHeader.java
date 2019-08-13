/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.ParametersHeader;
import javax.sip.address.Address;
import javax.sip.header.HeaderAddress;
import javax.sip.header.Parameters;

public abstract class AddressParametersHeader
extends ParametersHeader
implements Parameters {
    protected AddressImpl address;

    protected AddressParametersHeader(String string) {
        super(string);
    }

    protected AddressParametersHeader(String string, boolean bl) {
        super(string, bl);
    }

    @Override
    public Object clone() {
        AddressParametersHeader addressParametersHeader = (AddressParametersHeader)super.clone();
        AddressImpl addressImpl = this.address;
        if (addressImpl != null) {
            addressParametersHeader.address = (AddressImpl)addressImpl.clone();
        }
        return addressParametersHeader;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object instanceof HeaderAddress && object instanceof Parameters) {
            object = (HeaderAddress)object;
            if (!this.getAddress().equals(object.getAddress()) || !this.equalParameters((Parameters)object)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = (AddressImpl)address;
    }
}

