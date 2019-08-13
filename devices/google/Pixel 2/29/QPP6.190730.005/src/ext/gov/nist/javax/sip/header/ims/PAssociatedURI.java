/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.ims.PAssociatedURIHeader;
import gov.nist.javax.sip.header.ims.SIPHeaderNamesIms;
import java.text.ParseException;
import javax.sip.address.URI;
import javax.sip.header.ExtensionHeader;

public class PAssociatedURI
extends AddressParametersHeader
implements PAssociatedURIHeader,
SIPHeaderNamesIms,
ExtensionHeader {
    public PAssociatedURI() {
        super("P-Associated-URI");
    }

    public PAssociatedURI(AddressImpl addressImpl) {
        super("P-Associated-URI");
        this.address = addressImpl;
    }

    public PAssociatedURI(GenericURI genericURI) {
        super("P-Associated-URI");
        this.address = new AddressImpl();
        this.address.setURI(genericURI);
    }

    @Override
    public Object clone() {
        PAssociatedURI pAssociatedURI = (PAssociatedURI)super.clone();
        if (this.address != null) {
            pAssociatedURI.address = (AddressImpl)this.address.clone();
        }
        return pAssociatedURI;
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
    public URI getAssociatedURI() {
        return this.address.getURI();
    }

    @Override
    public void setAssociatedURI(URI uRI) throws NullPointerException {
        if (uRI != null) {
            this.address.setURI(uRI);
            return;
        }
        throw new NullPointerException("null URI");
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

