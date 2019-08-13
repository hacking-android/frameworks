/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.HostPort;
import gov.nist.core.NameValueList;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.From;
import gov.nist.javax.sip.parser.Parser;
import java.text.ParseException;
import javax.sip.address.Address;
import javax.sip.header.ToHeader;

public final class To
extends AddressParametersHeader
implements ToHeader {
    private static final long serialVersionUID = -4057413800584586316L;

    public To() {
        super("To", true);
    }

    public To(From from) {
        super("To");
        this.setAddress(from.address);
        this.setParameters(from.parameters);
    }

    @Override
    public String encode() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.headerName);
        stringBuilder.append(":");
        stringBuilder.append(" ");
        stringBuilder.append(this.encodeBody());
        stringBuilder.append("\r\n");
        return stringBuilder.toString();
    }

    @Override
    protected String encodeBody() {
        return this.encodeBody(new StringBuffer()).toString();
    }

    @Override
    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        if (this.address != null) {
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
        }
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof ToHeader && super.equals(object);
        return bl;
    }

    @Override
    public String getDisplayName() {
        if (this.address == null) {
            return null;
        }
        return this.address.getDisplayName();
    }

    public HostPort getHostPort() {
        if (this.address == null) {
            return null;
        }
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
        if (this.address == null) {
            return null;
        }
        return this.address.getUserAtHostPort();
    }

    @Override
    public boolean hasTag() {
        if (this.parameters == null) {
            return false;
        }
        return this.hasParameter("tag");
    }

    @Override
    public void removeTag() {
        if (this.parameters != null) {
            this.parameters.delete("tag");
        }
    }

    @Override
    public void setTag(String string) throws ParseException {
        Parser.checkToken(string);
        this.setParameter("tag", string);
    }
}

