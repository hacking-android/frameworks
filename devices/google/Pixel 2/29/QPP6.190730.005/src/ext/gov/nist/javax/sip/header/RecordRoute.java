/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import javax.sip.header.RecordRouteHeader;

public class RecordRoute
extends AddressParametersHeader
implements RecordRouteHeader {
    private static final long serialVersionUID = 2388023364181727205L;

    public RecordRoute() {
        super("Record-Route");
    }

    public RecordRoute(AddressImpl addressImpl) {
        super("Record-Route");
        this.address = addressImpl;
    }

    @Override
    public String encodeBody() {
        return this.encodeBody(new StringBuffer()).toString();
    }

    @Override
    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        if (this.address.getAddressType() == 2) {
            stringBuffer.append("<");
        }
        this.address.encode(stringBuffer);
        if (this.address.getAddressType() == 2) {
            stringBuffer.append(">");
        }
        if (!this.parameters.isEmpty()) {
            stringBuffer.append(";");
            this.parameters.encode(stringBuffer);
        }
        return stringBuffer;
    }
}

