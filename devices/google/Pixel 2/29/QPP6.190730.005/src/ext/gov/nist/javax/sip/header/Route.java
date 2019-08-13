/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.HostPort;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import javax.sip.header.RouteHeader;

public class Route
extends AddressParametersHeader
implements RouteHeader {
    private static final long serialVersionUID = 5683577362998368846L;

    public Route() {
        super("Route");
    }

    public Route(AddressImpl addressImpl) {
        super("Route");
        this.address = addressImpl;
    }

    @Override
    public String encodeBody() {
        return this.encodeBody(new StringBuffer()).toString();
    }

    @Override
    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        int n = this.address.getAddressType();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        if (!bl) {
            stringBuffer.append('<');
            this.address.encode(stringBuffer);
            stringBuffer.append('>');
        } else {
            this.address.encode(stringBuffer);
        }
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";");
            this.parameters.encode(stringBuffer);
        }
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof RouteHeader && super.equals(object);
        return bl;
    }

    @Override
    public int hashCode() {
        return this.address.getHostPort().encode().toLowerCase().hashCode();
    }
}

