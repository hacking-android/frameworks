/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ims.PProfileKeyHeader;
import gov.nist.javax.sip.header.ims.SIPHeaderNamesIms;
import java.text.ParseException;
import javax.sip.header.ExtensionHeader;

public class PProfileKey
extends AddressParametersHeader
implements PProfileKeyHeader,
SIPHeaderNamesIms,
ExtensionHeader {
    public PProfileKey() {
        super("P-Profile-Key");
    }

    public PProfileKey(AddressImpl addressImpl) {
        super("P-Profile-Key");
        this.address = addressImpl;
    }

    @Override
    public Object clone() {
        return (PProfileKey)super.clone();
    }

    @Override
    protected String encodeBody() {
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
    public boolean equals(Object object) {
        boolean bl = object instanceof PProfileKey && super.equals(object);
        return bl;
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

