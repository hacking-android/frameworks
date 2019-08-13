/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.HostPort;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.To;
import gov.nist.javax.sip.parser.Parser;
import java.text.ParseException;
import javax.sip.address.Address;
import javax.sip.header.FromHeader;

public final class From
extends AddressParametersHeader
implements FromHeader {
    private static final long serialVersionUID = -6312727234330643892L;

    public From() {
        super("From");
    }

    public From(To to) {
        super("From");
        this.address = to.address;
        this.parameters = to.parameters;
    }

    @Override
    protected String encodeBody() {
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

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof FromHeader && super.equals(object);
        return bl;
    }

    @Override
    public String getDisplayName() {
        return this.address.getDisplayName();
    }

    public HostPort getHostPort() {
        return this.address.getHostPort();
    }

    @Override
    public String getTag() {
        if (this.parameters == null) {
            return null;
        }
        return this.getParameter("tag");
    }

    @Override
    public String getUserAtHostPort() {
        return this.address.getUserAtHostPort();
    }

    @Override
    public boolean hasTag() {
        return this.hasParameter("tag");
    }

    @Override
    public void removeTag() {
        this.parameters.delete("tag");
    }

    @Override
    public void setAddress(Address address) {
        this.address = (AddressImpl)address;
    }

    @Override
    public void setTag(String string) throws ParseException {
        Parser.checkToken(string);
        this.setParameter("tag", string);
    }
}

