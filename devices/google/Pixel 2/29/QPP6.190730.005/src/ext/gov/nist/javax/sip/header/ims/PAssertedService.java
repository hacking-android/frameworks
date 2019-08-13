/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header.ims;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.ims.PAssertedServiceHeader;
import gov.nist.javax.sip.header.ims.SIPHeaderNamesIms;
import java.text.ParseException;
import javax.sip.header.ExtensionHeader;

public class PAssertedService
extends SIPHeader
implements PAssertedServiceHeader,
SIPHeaderNamesIms,
ExtensionHeader {
    private String subAppIds;
    private String subServiceIds;

    public PAssertedService() {
        super("P-Asserted-Service");
    }

    protected PAssertedService(String string) {
        super("P-Asserted-Service");
    }

    @Override
    public Object clone() {
        return (PAssertedService)super.clone();
    }

    @Override
    protected String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("urn:urn-7:");
        if (this.subServiceIds != null) {
            stringBuffer.append("3gpp-service");
            stringBuffer.append(".");
            stringBuffer.append(this.getSubserviceIdentifiers());
        } else if (this.subAppIds != null) {
            stringBuffer.append("3gpp-application");
            stringBuffer.append(".");
            stringBuffer.append(this.getApplicationIdentifiers());
        }
        return stringBuffer.toString();
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = object instanceof PAssertedServiceHeader && super.equals(object);
        return bl;
    }

    @Override
    public String getApplicationIdentifiers() {
        if (this.subAppIds.charAt(0) == '.') {
            return this.subAppIds.substring(1);
        }
        return this.subAppIds;
    }

    @Override
    public String getSubserviceIdentifiers() {
        if (this.subServiceIds.charAt(0) == '.') {
            return this.subServiceIds.substring(1);
        }
        return this.subServiceIds;
    }

    @Override
    public void setApplicationIdentifiers(String string) {
        this.subAppIds = string;
    }

    @Override
    public void setSubserviceIdentifiers(String string) {
        this.subServiceIds = string;
    }

    @Override
    public void setValue(String string) throws ParseException {
        throw new ParseException(string, 0);
    }
}

