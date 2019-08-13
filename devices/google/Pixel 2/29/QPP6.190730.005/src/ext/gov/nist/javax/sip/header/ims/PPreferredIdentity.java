/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ims.PPreferredIdentityHeader;
import gov.nist.javax.sip.header.ims.SIPHeaderNamesIms;
import java.text.ParseException;
import javax.sip.header.ExtensionHeader;

public class PPreferredIdentity
extends AddressParametersHeader
implements PPreferredIdentityHeader,
SIPHeaderNamesIms,
ExtensionHeader {
    public PPreferredIdentity() {
        super("P-Preferred-Identity");
    }

    public PPreferredIdentity(AddressImpl addressImpl) {
        super("P-Preferred-Identity");
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
        return stringBuffer.toString();
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

