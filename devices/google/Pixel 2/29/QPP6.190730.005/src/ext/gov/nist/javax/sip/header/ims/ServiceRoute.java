/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ims.SIPHeaderNamesIms;
import gov.nist.javax.sip.header.ims.ServiceRouteHeader;
import java.text.ParseException;
import javax.sip.header.ExtensionHeader;

public class ServiceRoute
extends AddressParametersHeader
implements ServiceRouteHeader,
SIPHeaderNamesIms,
ExtensionHeader {
    public ServiceRoute() {
        super("Service-Route");
    }

    public ServiceRoute(AddressImpl addressImpl) {
        super("Service-Route");
        this.address = addressImpl;
    }

    @Override
    public String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.address.getAddressType() == 2) {
            stringBuffer.append("<");
        }
        stringBuffer.append(this.address.encode());
        if (this.address.getAddressType() == 2) {
            stringBuffer.append(">");
        }
        if (!this.parameters.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(";");
            stringBuilder.append(this.parameters.encode());
            stringBuffer.append(stringBuilder.toString());
        }
        return stringBuffer.toString();
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

